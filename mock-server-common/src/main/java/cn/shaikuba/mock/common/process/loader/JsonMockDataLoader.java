package cn.shaikuba.mock.common.process.loader;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * JSONDataLoader
 *
 * @author Ray.Xu
 */
@Slf4j
public abstract class JsonMockDataLoader<IN extends MockRequest, OUT extends MockResponse> implements MockDataLoader<IN, OUT> {

    public abstract OUT load(IN in);

}

