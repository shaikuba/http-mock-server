package cn.shaikuba.mock.data.entity;

import cn.shaikuba.mock.common.process.entity.MockRequest;
import cn.shaikuba.mock.common.process.entity.MockResponse;
import cn.shaikuba.mock.data.annoation.MockField;
import cn.shaikuba.mock.data.entity.base.BaseEntity;
import cn.shaikuba.mock.data.entity.description.BehaviorDescription;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.shaikuba.mock.common.util.NoOp.noOp;

@Slf4j
@Data
public class HttpMockRequest extends BaseEntity<HttpMockRequest> implements MockRequest, MockResponse {

    public HttpMockRequest() {
    }

    public HttpMockRequest(String requestMethod, String requestUrl) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
    }

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private String title;

    private List<String> tags;

    @Pattern(regexp = "/.+", message = "Request url should start with char '/'")
    @MockField(required = true)
    private String requestUrl;

    @Pattern(regexp = "(GET|POST|PUT|DELETE)", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Support request method: get, post, put, delete")
    @MockField(required = true)
    private String requestMethod;

    private String queryString;
    private String formData;
    private String requestHeaders;

    //    private String requestContentType;  // identify request body content-type dynamically
    private String requestBody;

    // mock response message
    @MockField(required = true)
    private Integer statusCode;
    private String responseHeaders;

    @MockField(required = true)
    private String responseBody;

    @MockField(required = true)
    private String contentType;

    private String description;

    private String status = "a"; //a: active, n:deactivation

    public Set<ConstraintViolation<HttpMockRequest>> validateMockRequest() {
        return validator.validate(this);
    }

    public boolean isValid() {
        return validateCreateMockRequest().length == 0;
    }

    /**
     * Validate required fields for creating mock request and mock response
     *
     * @return Fields validate failed
     */
    public String[] validateCreateMockRequest() {
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

    public BehaviorDescription behaviorDescription() {
        return BehaviorDescription.genBehavior(this);
    }

}
