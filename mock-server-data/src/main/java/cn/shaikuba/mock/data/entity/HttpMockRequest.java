package cn.shaikuba.mock.data.entity;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;
import cn.shaikuba.mock.data.annoation.MockField;
import cn.shaikuba.mock.data.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.shaikuba.mock.common.util.NoOp.noOp;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HttpMockRequest extends BaseEntity<HttpMockRequest> implements MockRequest, MockResponse {

    @MockField(required = true)
    private String requestUrl;

    @MockField(required = true)
    private String requestMethod;

    private String queryString;
    private String formData;
    private String requestHeaders;
    private String requestBody;

    @MockField(required = true)
    private Integer statusCode;
    private String responseHeaders;

    @MockField(required = true)
    private String responseBody;

    @MockField(required = true)
    private String contentType;

    private String description;

    public boolean isValid() {
        return validate().length == 0;
    }

    public String[] validate() {
        return Stream.of(this.getClass().getDeclaredFields())
                .filter(field -> {
                            try {
                                return field.getAnnotation(MockField.class) != null
                                        && field.getAnnotation(MockField.class).required()
                                        && (field.get(this) == null || StringUtils.isEmpty(field.get(this).toString()));
                            } catch (IllegalAccessException e) {
                                noOp();
                            }
                            return false;
                        }
                )
                .map(field -> field.getName())
                .collect(Collectors.toList())
                .toArray(new String[]{});

    }

    public BehaviorDescription getMockBehavior() {
        return BehaviorDescription.genBehavior(this.description);
    }
}
