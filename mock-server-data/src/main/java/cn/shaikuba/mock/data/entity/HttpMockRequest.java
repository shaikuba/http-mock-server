package cn.shaikuba.mock.data.entity;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;
import cn.shaikuba.mock.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

@Data
public class HttpMockRequest extends BaseEntity<HttpMockRequest> implements MockRequest, MockResponse {

    private String requestUrl;

    private RequestMethod requestMethod;

    private String formData;

    private String requestBody;

    private String requestHeaders;

    private String responseHeaders;

    private String statusCode;

    private String responseBody;

    private String description;

}
