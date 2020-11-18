package cn.shaikuba.mock.service.behavior.message;

public interface MessageConverter<T, R> {

    boolean isSupport(MessageType messageType);

    R convert(T origin);
}
