package cn.shaikuba.mock.common.cache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public abstract class BaseCacheKeyGenerator implements KeyGenerator {

    private String keyPrefix = "mock-server";

    @Override
    public String generate(Object target, Method method, Object... params) {
        char sp = ':';
        StringBuilder keyString = new StringBuilder(30);
        keyString.append(keyPrefix);
        keyString.append(sp);
        // class name
        keyString.append(target.getClass().getSimpleName());
        keyString.append(sp);
        // method name
        keyString.append(method.getName());
        keyString.append(sp);

        // key identifier
        keyString.append(params.length == 0 ? 0 : keySuffix(params));

        return keyString.toString();
    }

    public abstract String keySuffix(Object... params);

}
