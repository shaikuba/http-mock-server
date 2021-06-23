package cn.shaikuba.mock.data.mybatis.mapper;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.mybatis.base.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HttpMockMapper extends CommonMapper<HttpMockRequest> {

    List<HttpMockRequest> findAllByUrlAndMethod(HttpMockRequest mockRequest);
}
