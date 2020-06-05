package cn.shaikuba.mock.data.mybatis.mapper.base;

public interface CommonMapper<T> extends PagingAndSortingRepository<T, Long>, CrudRepository<T, Long> {
}
