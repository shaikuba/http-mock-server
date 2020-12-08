package cn.shaikuba.mock.data.entity.description;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Data
public class MockCallbackRequest {

    @NotBlank(message = "Callback url can not be empty.")
    private String url;

    @Pattern(regexp = "(GET|POST)", message = "Callback request method should be GET or POST.")
    private String method;

    private String requestBody;

    private Map<String, String> bodyParams;

    private String contentType;

    private Map<String, String> headers;

    private AdvancedBehavior advancedBehavior;

    @Data
    public static class AdvancedBehavior {
        private int invokeTimes = 1;
        private long delayInFirstInvoke = 0l;
        private long intervalInMillis = 0l;

        //private List<MockCallbackRequest> callbackList;
    }
}
