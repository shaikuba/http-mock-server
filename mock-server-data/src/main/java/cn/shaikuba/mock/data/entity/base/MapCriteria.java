package cn.shaikuba.mock.data.entity.base;

import java.util.Map;

public class MapCriteria<V> extends Criteria<Map<String, V>> {

    public Criteria addCriteria(String key, V value) {
        criteria.put(key, value);
        return this;
    }
}
