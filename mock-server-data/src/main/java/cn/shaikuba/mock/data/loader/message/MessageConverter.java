package cn.shaikuba.mock.data.loader.message;

public interface MessageConverter<T, R> {

    boolean isSupport(String origin);

    R convert(T origin);

    T format(T origin);
}
