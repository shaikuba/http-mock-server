package cn.shaikuba.mock.manage;

import cn.shaikuba.mock.controller.HttpMockManagementController;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.service.impl.HttpMockRequestService;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StandaloneManageMockTestCase {

    @InjectMocks
    private HttpMockManagementController managementController;

    @Mock
    private HttpMockRequestService requestService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(managementController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })
                .alwaysDo(print())
                .build();
    }


    @Test
    void mockListTest() throws Exception {

        HttpMockRequest mockRequest_1 = new HttpMockRequest();
        HttpMockRequest mockRequest_2 = new HttpMockRequest();
        mockRequest_1.setId(1l);
        mockRequest_1.setTitle("mock request 1");

        mockRequest_2.setId(2l);
        mockRequest_2.setTitle("mock request 2");

        List<HttpMockRequest> mockRequestList = new ArrayList<>();
        mockRequestList.add(mockRequest_1);
        mockRequestList.add(mockRequest_2);

        doReturn(mockRequestList).when(requestService).findMockRequests(any());
        doReturn(20).when(requestService).countBy(any());

        Criteria<HttpMockRequest> criteria = Criteria.<HttpMockRequest>newCriteria()
                .criteria(new HttpMockRequest());
        // send and verify
        this.mockMvc.perform(post("/mock/manage/list")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(JSON.toJSONString(criteria))
        )
                .andExpect(status().isOk())
                .andExpect(matchAll(
                        jsonPath("$.status").value(true)
                        , jsonPath("$.code").value("0000")
                ))
                .andReturn();

        verify(requestService, times(1)).findMockRequests(any());
        verify(requestService, times(1)).countBy(any());

    }

}
