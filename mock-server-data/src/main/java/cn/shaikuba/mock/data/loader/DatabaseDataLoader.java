package cn.shaikuba.mock.data.loader;

import cn.shaikuba.mock.common.process.loader.JsonMockDataLoader;
import cn.shaikuba.mock.common.util.CollectionUtils;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.data.mapper.HttpMockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DatabaseDataLoader
 */
@Slf4j
@Component
public class DatabaseDataLoader extends JsonMockDataLoader<HttpMockRequest, HttpMockRequest> {

    @Autowired
    private HttpMockMapper mockMapper;


    @Override
    public HttpMockRequest load(HttpMockRequest mockRequest) {

        List<HttpMockRequest> mockRequestList = mockMapper.findAll(Criteria.<String, Object>newCriteria()
                .criteria(CollectionUtils.objectToMap(mockRequest))
        );

        if (mockRequestList != null && mockRequestList.size() != 0) {
            return mockRequestList.get(0);
        }

        return null;
    }

}

