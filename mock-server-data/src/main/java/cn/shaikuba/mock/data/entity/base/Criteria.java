package cn.shaikuba.mock.data.entity.base;

import lombok.Data;

/**
 * Unified query body, pageable and sortable provided.
 *
 * @author Ray.Xu
 */

@Data
public class Criteria<T> {

    protected T criteria;

    private Pageable page;

    private Sortable sort;

    public static <T>Criteria<T> newCriteria() {
        return new Criteria<>();
    }

    public Criteria criteria(T criteria) {
        this.criteria = criteria;
        return this;
    }

    public Criteria pageable(Pageable page) {
        this.page = page;
        return this;
    }

    public Criteria sort(Sortable sort) {
        this.sort = sort;
        return this;
    }

}
