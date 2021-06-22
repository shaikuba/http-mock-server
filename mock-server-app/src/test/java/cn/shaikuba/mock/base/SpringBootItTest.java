package cn.shaikuba.mock.base;

import cn.shaikuba.mock.MockServerHttpApplication;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = MockServerHttpApplication.class
        , webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public @interface SpringBootItTest {
}
