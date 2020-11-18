package cn.shaikuba.mock.data.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MockCallbackRequest {

    @NotBlank(message = "Callback url can not be empty.")
    private String url;

    @Pattern(regexp = "(GET|POST)", message = "Callback request method should be GET or POST.")
    private String method;

    private String requestBody;

    private String contentType;

    private AdvancedBehavior advancedBehavior;

    @Data
    public static class AdvancedBehavior {
        private int invokeTimes = 1;
        private long delayInFirstInvoke = 5000l;
        private long intervalInMillis = 5000l;

        //private List<MockCallbackRequest> callbackList;
    }
}
