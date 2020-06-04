package cn.shaikuba.mock.common.process.selector;

import cn.shaikuba.mock.common.process.handler.MockRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MockRequestHandlerManager
 * 
 * @author Ray.Xu 
 */
@Slf4j
public class MockRequestHandlerManager implements MockRequestHandlerSelector<MockRequestHandler, Class> {

    private final Map<String, MockRequestHandlerEntity> handlerMap = new ConcurrentHashMap<>();

    @Override
    public MockRequestHandler select(Class handlerClassifier) {
        MockRequestHandler mockRequestHandler = null;
        MockRequestHandlerEntity handlerEntity = handlerMap.get(handlerClassifier.getName());
        if (handlerEntity != null) {
            log.debug(String.format("Route mock request type %s to handler %s.",
                    handlerClassifier.getName(), handlerEntity.getHandler().getClass().getName()));
            mockRequestHandler = handlerEntity.getHandler();
            if (handlerEntity.isOneOff()) {
                handlerMap.remove(handlerClassifier);
            }
        } else {
            log.debug(String.format("No registered handler found for mock request type %s.",
                    handlerClassifier.getName()));
        }
        return mockRequestHandler;
    }

    /**
     * Register handler to selector.
     *
     * @param handler handler instance implement of {@link MockRequestHandler} with generic type of message type.
     */
    public void registerHandler(MockRequestHandler handler) {
        registerHandler(handler, false);
    }

    /**
     * Register handler to selector.
     * This method provide register a instance of {@link MockRequestHandler} with generic type of mock request type.
     *
     * @param handler handler instance implement of {@link MockRequestHandler} with generic type of mock request type.
     * @param oneOff  If this is a one-off handler.
     */
    public void registerHandler(MockRequestHandler handler, boolean oneOff) {
        // Validate
        if (handler == null) {
            throw new IllegalArgumentException("Handler instance is required.");
        }

        // Find generic
        Class genericClass = null;

        Type[] genericSuperclass = handler.getClass().getGenericInterfaces();
        for (Type type : genericSuperclass) {
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                if (actualTypeArguments == null || actualTypeArguments.length == 0) {
                    continue;
                }
                genericClass = (Class) actualTypeArguments[0];
            }
        }

        if (genericClass == null) {
            throw new IllegalArgumentException(String.format("No generic type found in handler %s.", handler.getClass()));
        }

        registerHandler(handler.getClass().getName(), handler, oneOff);
    }

    /**
     * Register handler to router.
     *
     * @param handlerClassifier handler classifier.
     * @param handler handler instance implement of {@link MockRequestHandler}.
     * @param oneOff  If this is a one-off handler.
     */
    public void registerHandler(String handlerClassifier, MockRequestHandler handler, boolean oneOff) {

        // Validate
        if (handlerClassifier == null) {
            throw new IllegalArgumentException("mock request type must be specified.");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Handler instance is required.");
        }

        // Register
        handlerMap.put(handlerClassifier, new MockRequestHandlerEntity(handler, oneOff));
        log.debug(String.format("Handler %s registered.", handler.getClass().getName()));
    }

    /**
     * MessageHandlerEntity provide extend one-off property for {@link MockRequestHandler} instance.
     */
    private class MockRequestHandlerEntity {

        private final MockRequestHandler handler;
        private final boolean oneOff;

        public MockRequestHandlerEntity(MockRequestHandler handler, boolean oneOff) {
            if (handler == null) {
                throw new IllegalArgumentException("Invalid handler.");
            }
            this.handler = handler;
            this.oneOff = oneOff;
        }

        public MockRequestHandler getHandler() {
            return handler;
        }

        public boolean isOneOff() {
            return oneOff;
        }
    }

}
