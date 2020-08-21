package cn.shaikuba.mock.service.impl;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.data.mybatis.mapper.HttpMockMapper;
import cn.shaikuba.mock.service.MockRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("httpMockRequestService")
public class HttpMockRequestService implements MockRequestService<HttpMockRequest> {

    @Autowired
    private HttpMockMapper mockMapper;

    @Override
    public HttpMockRequest saveMockRequest(HttpMockRequest mockRequest) {
        if (mockRequest.getRequestMethod() != null && mockRequest.getRequestUrl() != null) {
            mockMapper.save(mockRequest);
            return mockRequest;
        } else {
            throw new IllegalArgumentException("Request method and request URL is required.");
        }
    }

    @Override
    public void deleteMockRequest(List<Long> idList) {
        mockMapper.delete(idList);
    }

    @Override
    public void updateMockRequest(HttpMockRequest mockRequest) {
        mockMapper.update(mockRequest);
    }

    @Override
    public HttpMockRequest findMockRequest(Long mockRequestId) {
        return mockMapper.findOne(mockRequestId);
    }

    @Override
    public <C> List<HttpMockRequest> findMockRequests(Criteria<C> criteria) {
        return mockMapper.findAll(criteria);
    }


}
