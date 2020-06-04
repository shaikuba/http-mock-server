package cn.shaikuba.mock.service;

import cn.shaikuba.mock.common.process.loader.MockDataLoader;
import cn.shaikuba.mock.common.process.loader.MockDataSourceAdapter;
import cn.shaikuba.mock.common.process.selector.MockRequestHandlerManager;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.handler.HttpMockRequestHandler;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpMockService {

    @Autowired
    private MockRequestHandlerManager handlerManager;

    @Value("${mock.server.http.data-loader}")
    private String dataLoader;

    @Autowired
    private ApplicationContext appContext;

    @Cacheable(cacheNames = "http-mock-response" , keyGenerator =  "httpMockKey")
    public HttpMockRequest handle(HttpMockRequest mockRequest) {

        HttpMockRequestHandler mockRequestHandler = (HttpMockRequestHandler)handlerManager.select(mockRequest.getClass());

        HttpMockRequest mockResponse = null;
        try {
            mockRequestHandler.setUpDataSource(new MockDataSourceAdapter((MockDataLoader) appContext.getBean(Class.forName(dataLoader))));
            mockResponse = mockRequestHandler.handle(mockRequest);
            log.info("Mock response {}", JSON.toJSONString(mockResponse));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return mockResponse;
    }

}
