package cn.shaikuba.mock.data.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ray.Xu
 * @classname MockFiled
 * @description TODO
 * @date 8/24/2020 1:22 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockFiled {

    String value() default "";

    boolean required() default false;
}
