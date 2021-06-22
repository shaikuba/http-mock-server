package cn.shaikuba.mock.data.matchers;

import cn.shaikuba.mock.common.exception.UnifiedRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class QueryStringMatcher extends StringPropertyMatcher<String> {

    public QueryStringMatcher(Map<String, String> matchers) {
        super(matchers);
    }

    @Override
    public boolean matches(String queryString) {

        if (matchersIsEmpty()) {
            return true;
        }

        //1. match query string first
        if (StringUtils.isEmpty(queryString)) {
            return false;
        }

        String[] kvPairs = queryString.split("&");
        if (kvPairs.length == 0) {
            return false;
        }

        AtomicBoolean matched = new AtomicBoolean(true);
        try {
            matchers.forEach((key, value) -> {
                boolean matchRes = Arrays.stream(kvPairs).anyMatch(paramPair -> {
                            String[] params = paramPair.split("=");
                            if (params.length != 2) {
                                return false;
                            }
                            return params[0].equals(key) && params[1].matches(value);
                        }
                );
                if (!matchRes) { // exit match process when there is one matcher match failed
                    matched.set(false);
                    throw new UnifiedRuntimeException("bread foreach");
                }
            });
        } catch (UnifiedRuntimeException e) {
        }

        return matched.get();

    }
}
