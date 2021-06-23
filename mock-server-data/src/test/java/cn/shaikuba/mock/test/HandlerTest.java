package cn.shaikuba.mock.test;

import cn.shaikuba.mock.common.process.handler.MockRequestHandler;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.handler.HttpMockRequestHandler;

public class HandlerTest {


    public void handlerClass() {
        MockRequestHandler<HttpMockRequest, HttpMockRequest> mockRequestHandler = new HttpMockRequestHandler();
        System.out.println(mockRequestHandler.getClass().getTypeName());
        System.out.println(mockRequestHandler.getClass().getName());
        System.out.println(HttpMockRequestHandler.class.getTypeName());
        System.out.println(HttpMockRequestHandler.class.getName());
        System.out.println(HttpMockRequestHandler.class.getSimpleName());
        System.out.println(HttpMockRequestHandler.class.getGenericSuperclass().getTypeName());
        System.out.println(HttpMockRequestHandler.class.getInterfaces()[0].getTypeName());
        System.out.println(HttpMockRequestHandler.class.getGenericInterfaces()[0].getTypeName());
    }
}
