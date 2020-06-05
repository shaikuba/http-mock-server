package cn.shaikuba.mock.data.mybatis.mapper;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.mybatis.mapper.base.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HttpMockMapper extends CommonMapper<HttpMockRequest> {
}
