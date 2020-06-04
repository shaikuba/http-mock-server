package cn.shaikuba.mock.common.process.loader;

/**
 * MockDataLoader
 *
 * @author Ray.Xu
 * @param <IN>
 * @param <OUT>
 */
public interface MockDataLoader<IN, OUT> {

    /**
     * Load mock data from file system or other net resources.
     * @param in
     * @return
     */
    OUT load(IN in);
}
