package cn.shaikuba.mock;

import org.springframework.stereotype.Service;

/**
 * @author Ray.Xu
 * @classname TestPeople
 * @description TODO
 * @date 9/4/2020 2:21 PM
 */
@Service
public class TestPeople {

    public void sayHello() {
        System.out.println("================== Real TestPeople.sayHello");
    }
}
