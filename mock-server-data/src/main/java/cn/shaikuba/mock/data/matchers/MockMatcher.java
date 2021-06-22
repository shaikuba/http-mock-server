package cn.shaikuba.mock.data.matchers;

public interface MockMatcher<T> {

    boolean matches(T source);
}
