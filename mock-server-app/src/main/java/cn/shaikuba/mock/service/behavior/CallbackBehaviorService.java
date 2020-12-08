package cn.shaikuba.mock.service.behavior;

import cn.shaikuba.mock.common.util.CollectionUtils;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.description.BehaviorDescription;
import cn.shaikuba.mock.data.entity.description.MockCallbackRequest;
import cn.shaikuba.mock.data.loader.message.JsonMessageConverter;
import cn.shaikuba.mock.data.loader.message.XmlMessageConverter;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Ray.Xu
 * @classname CallbackBehaviorService
 * @description Simulate the service invoke the callback API with specific behavior
 * @date 9/4/2020 1:45 PM
 */
@Slf4j
@Service
public class CallbackBehaviorService extends AbstractBehaviorService<BehaviorDescription> {

    @Qualifier("callbackTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor callbackTaskExecutor;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JsonMessageConverter jsonMessageConverter;

    @Autowired
    private XmlMessageConverter xmlMessageConverter;

    @Autowired
    public CallbackBehaviorService(BehaviorServiceRegister serviceRegister) {
        super(serviceRegister);
    }

    @Override
    public void action(BehaviorDescription description) {
        MockCallbackRequest callbackRequest = description.getMockCallback();

        if (callbackRequest == null) {
            return;
        }

        Set<ConstraintViolation<MockCallbackRequest>> violationSet;
        if (!(violationSet = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(callbackRequest)).isEmpty()) {
            violationSet.forEach(violation -> log.error(violation.getMessage()));
            return;
        }

        callbackTaskExecutor.execute(new CallbackExecutor(description));
    }

    private class CallbackExecutor implements Runnable {

        private BehaviorDescription description;

        private final Object mutex = new Object();

        public CallbackExecutor(BehaviorDescription description) {
            this.description = description;
        }

        @Override
        public void run() {
            MockCallbackRequest callbackRequest = description.getMockCallback();
            MockCallbackRequest.AdvancedBehavior advancedBehavior = callbackRequest.getAdvancedBehavior();

            int invokeTimes = advancedBehavior.getInvokeTimes() > 0 ? advancedBehavior.getInvokeTimes() : 1;
            while (invokeTimes > 0) {
                if (invokeTimes == advancedBehavior.getInvokeTimes()) {
                    if (advancedBehavior.getDelayInFirstInvoke() > 0) {
                        delayInvoke(advancedBehavior.getDelayInFirstInvoke());
                    }
                } else {
                    if (advancedBehavior.getIntervalInMillis() > 0) {
                        delayInvoke(advancedBehavior.getIntervalInMillis());
                    }
                }
                invokeCallback(callbackRequest);
                invokeTimes--;
            }

        }

        private void delayInvoke(long delayTime) {
            synchronized (mutex) {
                try {
                    mutex.wait(delayTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void invokeCallback(MockCallbackRequest callbackRequest) {

            try {
                HttpHeaders httpHeaders = new HttpHeaders();
                if (!CollectionUtils.isEmpty(callbackRequest.getHeaders())) {
                    httpHeaders.setAll(callbackRequest.getHeaders());
                }

                // set content-type
                try {
                    MediaType mediaType = MediaType.parseMediaType(callbackRequest.getContentType());
                    httpHeaders.setContentType(mediaType);
                } catch (Exception e) {
                    log.warn("Can not parse {} to MediaType", callbackRequest.getContentType());
                }

                // generate body string according to content type
                String body = generateBodyString(callbackRequest);

                HttpEntity<String> httpEntity = new HttpEntity(body, httpHeaders);
                ResponseEntity<String> responseEntity = CallbackBehaviorService.this.restTemplate
                        .exchange(callbackRequest.getUrl()
                                , HttpMethod.resolve(callbackRequest.getMethod().toUpperCase())
                                , httpEntity
                                , String.class
                        );
                log.info("Mock callback service got message: {}", responseEntity.toString());

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        private String generateBodyString(MockCallbackRequest callbackRequest) {
            Map<String, String> bodyParams = callbackRequest.getBodyParams();
            String callbackContentType = callbackRequest.getContentType();

            if (StringUtils.isBlank(callbackContentType)) {
                log.warn("ContentType cannot be null to generate request body.");
                return null;
            }
            if (CollectionUtils.isEmpty(bodyParams)) {
                log.warn("BodyParams cannot be empty to generate request body.");
                return null;
            }

            HttpMockRequest mockRequest = description.getOriginalRequest();

            Map<String, String> escapedParams = new HashMap<>();
            bodyParams.forEach((String key, String value) -> {

                if (value.matches("\\$\\{request\\..+}")) {
                    String bodyKey = StringUtils.remove(value, "${request.").replace("}", "");

                    // json request body
                    JSONObject jsonBody;
                    if ((jsonBody = jsonMessageConverter.convert(mockRequest.getRequestBody())) != null) {
                        if (jsonBody.getString(bodyKey) != null) {
                            escapedParams.put(key, jsonBody.getString(bodyKey));
                        } else {
                            escapedParams.put(key, value);
                        }
                        return;
                    }

                    // xml request body
                    Document xmlBody;
                    if ((xmlBody = xmlMessageConverter.convert(mockRequest.getRequestBody())) != null) {
                        if (xmlBody.getRootElement().hasContent()
                                && xmlBody.getRootElement() != null
                                && xmlBody.getRootElement().element(bodyKey) != null) {
                            escapedParams.put(key, xmlBody.getRootElement().element(bodyKey).getTextTrim());
                        } else {
                            escapedParams.put(key, value);
                        }
                    }


                } else {
                    escapedParams.put(key, value);
                }

            });

            String callbackBody = null;
            // transfer callback body params to xml/json string
            if (MediaType.parseMediaType(callbackContentType).equals(MediaType.APPLICATION_XML)) {
                Document document = DocumentHelper.createDocument();
                document.setXMLEncoding("UTF-8");
                Element root = document.addElement("xml");
                escapedParams.forEach((key, value) -> {
                    root.addElement(key)
                            .addText(value);
                });

                callbackBody = document.asXML();
            }

            if (MediaType.parseMediaType(callbackContentType).equals(MediaType.APPLICATION_JSON)) {
                callbackBody = JSONObject.toJSONString(escapedParams);
            }
            log.info("Callback url: {}", callbackRequest.getUrl());
            log.info("Callback request body: \n{}", callbackBody);

            return callbackBody;
        }

    }

    public static void main(String[] args) {
        System.out.println("${reques.aaa}".matches("\\$\\{request\\..+}"));

        Map<String, String> temp = new HashMap<>();

        temp.put("key1", "value1");
        temp.put("key2", "value2");

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        temp.forEach((key, value) -> {
            root.addElement(key)
                    .addText(value);
        });

        System.out.println(document.asXML());
    }

}
