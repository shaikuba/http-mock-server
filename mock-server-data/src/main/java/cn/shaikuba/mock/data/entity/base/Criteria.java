package cn.shaikuba.mock.data.entity.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Unified query body, pageable and sortable provided.
 *
 * @author Ray.Xu
 */

@Data
public class Criteria<K, V> {

    private Map<K, V> criteria = new HashMap<>();

    private Pageable page;

    private Sortable sort;

    public static <K, V>Criteria<K, V> newCriteria() {
        return new Criteria<>();
    }

    public Criteria criteria(Map<K, V> criteria) {
        this.criteria = criteria;
        return this;
    }

    public Criteria addCriteria(K key, V value) {
        criteria.put(key, value);
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
