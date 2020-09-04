package cn.shaikuba.mock.service.behavior;

/**
 * @author Ray.Xu
 * @classname AbstractBehaviorService
 * @description TODO
 * @date 9/4/2020 1:53 PM
 */
public abstract class AbstractBehaviorService<M> implements BehaviorService<M> {

    public AbstractBehaviorService(BehaviorServiceRegister serviceRegister) {
        serviceRegister.register(this);
    }
}
