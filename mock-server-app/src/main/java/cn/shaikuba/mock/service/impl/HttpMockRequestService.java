package cn.shaikuba.mock.service.impl;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.service.MockRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("httpMockRequestService")
public class HttpMockRequestService implements MockRequestService<HttpMockRequest> {

    @Autowired
    //private HttpMockMapper mockMapper;

    @Override
    public HttpMockRequest saveMockRequest(HttpMockRequest mockRequest) {
        if (mockRequest.getRequestMethod() != null && mockRequest.getRequestUrl() != null) {
            //return mockMapper.save(mockRequest);
            return null;
        } else {
            throw new IllegalArgumentException("");
        }
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
    public <C> List<HttpMockRequest> findMockRequests(Criteria<C> criteria) {
        return null;
    }


}
