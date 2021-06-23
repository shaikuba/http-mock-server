package cn.shaikuba.mock.data.mybatis.mapper.base;

import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.data.entity.base.Pageable;
import cn.shaikuba.mock.data.entity.base.Sortable;

import java.io.Serializable;
import java.util.List;

/**
 * Extension of {@link CrudRepository} to provide additional methods to retrieve entities using the pagination and
 * sorting abstraction.
 *
 * @see Sortable
 * @see Pageable
 */
public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
//    Iterable<T> findAll(Sortable sort);

    /**
     * Returns a {@link Pageable} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
//    List<T> findAll(Pageable pageable);

    /**
     * @see #findAll(Sortable) {@link #findAll(Pageable)}
     * @param pageable
     * @param sort
     * @return
     */
//    List<T> findAll(Pageable pageable, Sortable sort);

    /**
     * @param criteria contains query conditions and sortable, pageable
     * @return
     */
    List<T> findAll(Criteria criteria);

    /**
     * Count the elements conformed with criteria
     *
     * @param criteria
     * @return
     */
    int countByCriteria(Criteria criteria);

}

