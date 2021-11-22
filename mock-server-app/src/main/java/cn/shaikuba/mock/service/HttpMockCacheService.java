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
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpMockCacheService {

    @Autowired
    private MockRequestHandlerManager handlerManager;

    @Value("${mock.server.http.data-loader}")
    private String dataLoader;

    @Autowired
    private MockDataLoader mockDataLoader;

    @Cacheable(cacheNames = "http-mock-response", keyGenerator = "httpMockKey")
    public HttpMockRequest handle(HttpMockRequest mockRequest) {

        HttpMockRequestHandler mockRequestHandler = (HttpMockRequestHandler) handlerManager.select(mockRequest.getClass());

        mockRequestHandler.setUpDataSource(new MockDataSourceAdapter(mockDataLoader));
        HttpMockRequest mockResponse = mockRequestHandler.handle(mockRequest);
        log.info("Mock response {}", JSON.toJSONString(mockResponse));

        return mockResponse;
    }

}
