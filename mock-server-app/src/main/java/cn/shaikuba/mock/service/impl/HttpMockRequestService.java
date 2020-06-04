package cn.shaikuba.mock.service.impl;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.service.MockRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("httpMockRequestService")
public class HttpMockRequestService implements MockRequestService<HttpMockRequest> {
    @Override
    public HttpMockRequest saveMockRequest(HttpMockRequest mockRequest) {
        return null;
    }

    @Override
    public void deleteMockRequest(List<Long> idList) {

    }

    @Override
    public void updateMockRequest(HttpMockRequest mockRequest) {

    }

    @Override
    public HttpMockRequest findMockRequest(Long mockRequestId) {
        return null;
    }

    @Override
    public List<HttpMockRequest> findMockRequests(Criteria<String, Object> criteria) {
        return null;
    }
}
