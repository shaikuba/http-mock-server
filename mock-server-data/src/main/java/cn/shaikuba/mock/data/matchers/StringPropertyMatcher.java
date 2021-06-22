package cn.shaikuba.mock.data.matchers;

import java.util.Map;

public abstract class StringPropertyMatcher<T> implements MockMatcher<T> {

    protected Map<String, String> matchers;

    public StringPropertyMatcher(Map<String, String> matchers) {
        this.matchers = matchers;
    }

    protected boolean matchersIsEmpty() {
        return matchers == null || matchers.isEmpty();
    }

}
