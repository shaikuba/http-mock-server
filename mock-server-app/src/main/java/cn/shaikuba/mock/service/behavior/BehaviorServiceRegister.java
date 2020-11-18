package cn.shaikuba.mock.service.behavior;

import cn.shaikuba.mock.data.entity.BehaviorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ray.Xu
 * @classname AbstractBehaviorService
 * @description TODO
 * @date 9/4/2020 1:35 PM
 */
@Slf4j
@Service
public class BehaviorServiceRegister {

    private List<BehaviorService> behaviorServiceList = new ArrayList<>();

    public void action(BehaviorDescription description) {
        behaviorServiceList.stream().forEach(behaviorService -> behaviorService.action(description));
    }

    public void register(BehaviorService behaviorService) {
        this.behaviorServiceList.add(behaviorService);
    }

    public void unRegister(BehaviorService behaviorService) {
        this.behaviorServiceList.remove(behaviorService);
    }

}
