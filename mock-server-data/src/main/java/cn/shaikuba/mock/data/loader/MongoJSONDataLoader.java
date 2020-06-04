package cn.shaikuba.mock.data.loader;

import cn.shaikuba.mock.common.process.loader.JsonMockDataLoader;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.mongodb.CartMockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * MongoJSONDataLoader
 *
 * @author Ray.Xu
 */
@Slf4j
@Component
public class MongoJSONDataLoader extends JsonMockDataLoader<HttpMockRequest, HttpMockRequest> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CartMockRepository mockRepository;

    @Override
    public HttpMockRequest load(HttpMockRequest mockRequest) {

//        CartDocument ddapMockDocument = mockRepository.findByMockKeyEquals("");
//
//        HttpMockResponse ddapMockResponse = new HttpMockResponse();
//        ddapMockResponse.setMockKey(ddapMockDocument.getMockKey());
//        ddapMockResponse.setResponseBody(ddapMockDocument.getMockResponse());
//
//        return ddapMockResponse;

        throw new UnsupportedOperationException("Not implemented yet.");

    }

}

