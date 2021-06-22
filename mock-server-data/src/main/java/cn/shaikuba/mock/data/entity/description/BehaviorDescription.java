package cn.shaikuba.mock.data.entity.description;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Map;

/**
 * @author Ray.Xu
 * @classname BehaviorDescription
 * @description To describe the mock server how to respond client with specified behavior
 * @json {
 * "matchers": {
 * "key1": "value1",
 * "key2": "regexp"
 * }
 * "await": 1000,
 * "mockCallback": {
 * "url": "http://domain:port/mock/path",
 * "method": "get",
 * "headers": {
 *     "Accept": "*\/*"
 * }
 * "contentType": "application/xml"
 * "bodyParams": "{"key": "value"}",
 * "advancedBehavior": {
 * "invokeTimes": 2,
 * "delayInFirstInvoke": 1000,
 * "intervalInMillis": 1000
 * }
 * }
 * }
 * @date 9/4/2020 11:33 AM
 */
@Data
public class BehaviorDescription {

    private Long await = 0l; // time in milliseconds

    private Map<String, String> matchers; // Match request parameter first, if match failed, then try to match with request body formed by json or xml

    private HttpMockRequest originalRequest;

    private MockCallbackRequest mockCallback;

    public static BehaviorDescription genBehavior(HttpMockRequest mockRequest) {
        BehaviorDescription behaviorDescription = null;
        try {
            behaviorDescription = JSON.parseObject(mockRequest.getDescription(), BehaviorDescription.class);
            behaviorDescription.setOriginalRequest(mockRequest);
        } catch (Exception e) {
            //throw new RuntimeException("Parse behavior description exceptionally, please check the format carefully.");
        }

        return behaviorDescription;
    }

}
