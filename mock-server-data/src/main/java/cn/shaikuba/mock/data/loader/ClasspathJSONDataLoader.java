package cn.shaikuba.mock.data.loader;

import cn.shaikuba.mock.common.process.loader.JsonMockDataLoader;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClasspathJSONDataLoader
 *
 * @author Ray.Xu
 */
@Slf4j
@Component
public class ClasspathJSONDataLoader extends JsonMockDataLoader<HttpMockRequest, HttpMockRequest> {

    @Override
    public HttpMockRequest load(HttpMockRequest httpMockRequest) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }


}

