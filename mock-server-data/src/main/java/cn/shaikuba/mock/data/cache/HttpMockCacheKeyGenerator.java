package cn.shaikuba.mock.data.cache;

import cn.shaikuba.mock.common.cache.BaseCacheKeyGenerator;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import org.springframework.stereotype.Component;

@Component("httpMockKey")
public class HttpMockCacheKeyGenerator extends BaseCacheKeyGenerator {

    @Override
    public String keySuffix(Object... params) {
        if (params.length == 0) {
            return "0";
        }
        HttpMockRequest mockRequest = (HttpMockRequest) params[0];
        String suffix = String.format("%s-%s", mockRequest.getRequestUrl(),
                mockRequest.getRequestMethod());

        return suffix;
    }
}
