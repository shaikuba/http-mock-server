package cn.shaikuba.mock.service.behavior;

import cn.shaikuba.mock.data.entity.description.BehaviorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ray.Xu
 * @classname WaitBehaviorService
 * @description Simulate the service delay to response client
 * @date 9/4/2020 1:45 PM
 */
@Slf4j
@Service
public class WaitBehaviorService extends AbstractBehaviorService<BehaviorDescription> {

    @Autowired
    public WaitBehaviorService(BehaviorServiceRegister serviceRegister) {
        super(serviceRegister);
    }

    @Override
    public void action(BehaviorDescription description) {
        needWait(description.getAwait());
    }

    private void needWait(long timeoutInMills) {
        if (timeoutInMills != 0) {
            try {
                log.info("Mock behavior to wait: {}ms", timeoutInMills);
                synchronized (this) {
                    this.wait(timeoutInMills);
                }
                log.info("Mock behavior wait complete");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
