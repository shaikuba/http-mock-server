package cn.shaikuba.mock.data.loader.http;

import cn.shaikuba.mock.common.process.loader.JsonMockDataLoader;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.data.mybatis.mapper.HttpMockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DatabaseDataLoader
 */
@Slf4j
@Component
public class HttpDatabaseDataLoader extends JsonMockDataLoader<HttpMockRequest, HttpMockRequest> {

    @Autowired
    private HttpMockMapper mockMapper;

    @Override
    public HttpMockRequest load(HttpMockRequest mockRequest) {

        HttpMockRequest foundMockRequest = mockMapper.findOne(mockRequest);

        return foundMockRequest;
    }

}

