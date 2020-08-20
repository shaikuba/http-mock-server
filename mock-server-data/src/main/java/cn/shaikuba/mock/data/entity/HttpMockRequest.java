package cn.shaikuba.mock.data.entity;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;
import cn.shaikuba.mock.data.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HttpMockRequest extends BaseEntity<HttpMockRequest> implements MockRequest, MockResponse {

    private String requestUrl;
    private String requestMethod;
    private String queryString;
    private String formData;
    private String requestHeaders;
    private String requestBody;
    private Integer statusCode;
    private String responseHeaders;
    private String responseBody;
    private String contentType;
    private String description;

}
