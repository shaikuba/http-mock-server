package cn.shaikuba.mock.service.behavior;

import cn.shaikuba.mock.data.entity.BehaviorDescription;

/**
 * @author Ray.Xu
 * @classname WaitBehaviorService
 * @description Simulate the service delay to response client
 * @date 9/4/2020 1:45 PM
 */
public class WaitBehaviorService extends AbstractBehaviorService<BehaviorDescription> {

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
                synchronized (this) {
                    Thread.currentThread().wait(timeoutInMills);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
