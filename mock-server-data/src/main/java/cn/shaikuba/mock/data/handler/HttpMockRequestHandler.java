package cn.shaikuba.mock.data.handler;

import cn.shaikuba.mock.common.process.handler.MockRequestHandler;
import cn.shaikuba.mock.common.process.loader.MockDataSourceAdapter;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ray.Xu
 */
@Slf4j
@Data
public class HttpMockRequestHandler implements MockRequestHandler<HttpMockRequest, HttpMockRequest> {

    protected MockDataSourceAdapter<HttpMockRequest, HttpMockRequest> dataSourceAdapter;

    public void setUpDataSource(MockDataSourceAdapter dataSourceAdapter) {
        this.dataSourceAdapter = dataSourceAdapter;
    }

    @Override
    public HttpMockRequest handle(HttpMockRequest httpMockRequest) {
        log.info("Loading mock response data using loader: {}", dataSourceAdapter.getDataLoader().getClass().getName());
        HttpMockRequest mockResponse = dataSourceAdapter.load(httpMockRequest);

        log.info("Http Mock response is: {}", mockResponse);
        return mockResponse;
    }

}
