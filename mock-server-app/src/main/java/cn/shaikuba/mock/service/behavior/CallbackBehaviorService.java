package cn.shaikuba.mock.service.behavior;

import cn.shaikuba.mock.config.ApplicationContextHelper;
import cn.shaikuba.mock.data.entity.BehaviorDescription;
import cn.shaikuba.mock.data.entity.MockCallbackRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
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

        ApplicationContextHelper.getBean("callbackTaskExecutor", ThreadPoolTaskExecutor.class)
                .execute(new CallbackExecutor(callbackRequest));
    }

    private class CallbackExecutor implements Runnable {

        private MockCallbackRequest callbackRequest;

        private final Object mutex = new Object();

        public CallbackExecutor(MockCallbackRequest callbackRequest) {
            this.callbackRequest = callbackRequest;
        }

        @Override
        public void run() {
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

            HttpEntity<?> httpEntity = HttpEntity.EMPTY;
            if (StringUtils.isNotBlank(callbackRequest.getRequestBody())) {
                httpEntity = new HttpEntity<>(callbackRequest.getRequestBody());
            }

            ResponseEntity<Map> responseEntity = ApplicationContextHelper.getBean(RestTemplate.class)
                    .exchange(callbackRequest.getUrl()
                            , HttpMethod.resolve(callbackRequest.getMethod())
                            , httpEntity
                            , Map.class
                    );
            log.info("Mock callback service got message: {}", responseEntity.toString());
        }

    }


}
