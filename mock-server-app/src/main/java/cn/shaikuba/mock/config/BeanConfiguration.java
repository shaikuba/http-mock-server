package cn.shaikuba.mock.config;

import cn.shaikuba.mock.common.process.handler.MockRequestHandler;
import cn.shaikuba.mock.common.process.selector.MockRequestHandlerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ServiceLoader;

@Configuration
public class BeanConfiguration {


    @Bean
    public MockRequestHandlerManager handlerManager() {
        MockRequestHandlerManager handlerManager = new MockRequestHandlerManager();

        ServiceLoader<MockRequestHandler> serviceLoader = ServiceLoader.load(MockRequestHandler.class);
        serviceLoader.forEach(mockRequestHandler -> handlerManager.registerHandler(mockRequestHandler));

        return handlerManager;
    }

}
