package cn.shaikuba.mock.data.mapper.base;

public interface CommonMapper<T> extends PagingAndSortingRepository<T, Long>, CrudRepository<T, Long> {
}
