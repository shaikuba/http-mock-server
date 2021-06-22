package cn.shaikuba.mock.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public abstract class MockMvcTester {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

//    @Value("#{T(common.Constants).PARTNER_SERVICE_KEY_PREFIX + '${server.ws.key}'}")
//    private String serviceKey;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })
                .alwaysDo(print())
                .build();
    }

}
