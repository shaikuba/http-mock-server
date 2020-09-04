package cn.shaikuba.mock.data.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author Ray.Xu
 * @classname BehaviorDescription
 * @description To describe the mock server how to respond client with specified behavior
 * @date 9/4/2020 11:33 AM
 */
@Data
public class BehaviorDescription {

    private Long await = 0l; // time in milliseconds


    public static BehaviorDescription genBehavior(String description) {
        BehaviorDescription behaviorDescription = null;
        try {
            behaviorDescription = JSON.parseObject(description, BehaviorDescription.class);
        } catch (Exception e) {
            //throw new RuntimeException("Parse behavior description exceptionally, please check the format carefully.");
        }

        return behaviorDescription;
    }
}
