package cn.shaikuba.mock;

import org.springframework.stereotype.Service;

/**
 * @author Ray.Xu
 * @classname cn.shaikuba.mock.TestPeople
 * @description TODO
 * @date 9/4/2020 2:21 PM
 */
@Service
public class TestPeople {

    public static String role = "Fake People";

    public void sayHello() {
        System.out.println("================== Fake cn.shaikuba.mock.TestPeople.sayHello");
    }
}
