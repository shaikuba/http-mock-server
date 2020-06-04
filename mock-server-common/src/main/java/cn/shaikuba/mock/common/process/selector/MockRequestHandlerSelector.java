package cn.shaikuba.mock.common.process.selector;

import cn.shaikuba.mock.common.process.handler.MockRequestHandler;

/**
 * MockRequestHandlerSelector
 *
 * @author Ray.Xu
 * @param <R>
 */
public interface MockRequestHandlerSelector<R extends MockRequestHandler, S> {

    R select(S selector);

}
