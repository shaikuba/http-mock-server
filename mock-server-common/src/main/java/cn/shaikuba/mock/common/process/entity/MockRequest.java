package cn.shaikuba.mock.common.process.entity;

public interface MockRequest {

    default <T> T classifier() {
        return (T) null;
    }

    default <S> S datasource() {
        return null;
    }

}
