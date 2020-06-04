package cn.shaikuba.mock.common.process.loader;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import lombok.Data;

/**
 * MockDataSourceAdapter
 *
 * @author Ray.Xu
 */
@Data
public class MockDataSourceAdapter<IN extends MockRequest, OUT> implements MockDataLoader<IN, OUT> {

    protected MockDataLoader<IN, OUT> dataLoader;

    public MockDataSourceAdapter(MockDataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public OUT load(IN in) {
        return dataLoader.load(in);
    }
}
