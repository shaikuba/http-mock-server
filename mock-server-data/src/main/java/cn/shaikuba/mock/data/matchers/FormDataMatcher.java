package cn.shaikuba.mock.data.matchers;

import cn.shaikuba.mock.common.exception.UnifiedRuntimeException;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FormDataMatcher extends StringPropertyMatcher<JSONObject> {

    public FormDataMatcher(Map<String, String> matchers) {
        super(matchers);
    }

    @Override
    public boolean matches(JSONObject formData) {

        if (matchersIsEmpty()) {
            return true;
        }
        //1. match query string first
        if (formData == null || formData.isEmpty()) {
            return false;
        }

        AtomicBoolean matched = new AtomicBoolean(false);
        try {
            matchers.forEach((key, value) -> {
                if (formData.getString(key) != null) {
                    if (formData.getString(key).equals(value)) {
                        matched.set(true);
                        throw new UnifiedRuntimeException("bread foreach");
                    }
                }
            });
        } catch (UnifiedRuntimeException e) {
        }

        return matched.get();

    }
}
