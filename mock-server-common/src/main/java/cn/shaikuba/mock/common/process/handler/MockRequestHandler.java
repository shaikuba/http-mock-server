package cn.shaikuba.mock.common.process.handler;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;

/**
 * @author Ray.Xu
 * @param <IN>
 * @param <OUT>
 */
@FunctionalInterface
public interface MockRequestHandler<IN extends MockRequest, OUT extends MockResponse> {

    /**
     * Handle mock request, and return mock response.
     * @param in
     * @return
     */
    OUT handle(IN in);

}
