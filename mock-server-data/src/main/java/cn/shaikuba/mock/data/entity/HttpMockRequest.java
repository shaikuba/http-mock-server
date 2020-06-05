package cn.shaikuba.mock.data.entity;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;
import cn.shaikuba.mock.data.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

@Builder
@Data
public class HttpMockRequest extends BaseEntity<HttpMockRequest> implements MockRequest, MockResponse {

    private RequestMethod requestMethod;
    private String requestUrl;
    private String queryString;
    private String formData;
    private Integer statusCode;
    private String contentType;
    private String requestHeaders;
    private String requestBody;
    private String responseHeaders;
    private String responseBody;
    private String description;

}
