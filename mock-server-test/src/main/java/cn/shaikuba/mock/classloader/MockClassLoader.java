package cn.shaikuba.mock.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Ray.Xu
 * @classname MockClassLoader
 * @description TODO
 * @date 9/4/2020 4:39 PM
 */
public class MockClassLoader extends URLClassLoader {

    private MockClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static ClassLoader classLoader(URL... urls) {
        return new MockClassLoader(urls, null);
    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        ClassLoader classLoader = MockClassLoader.classLoader(new File("D:\\Document\\Teach\\github\\http-mock-server\\mock-server-app\\target\\classes").toURI().toURL());

        Class<?> clazz = classLoader.loadClass("cn.shaikuba.mock.TestPeople");

        System.out.println(clazz.getName());


        Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass("cn.shaikuba.mock.TestPeople");

        System.out.println(aClass.getName());

        System.out.println(clazz == aClass); // false
    }
}
