package cn.shaikuba.mock.controller;

import cn.shaikuba.mock.common.util.CollectionUtils;
import cn.shaikuba.mock.data.entity.description.BehaviorDescription;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.service.HttpMockCacheService;
import cn.shaikuba.mock.service.behavior.BehaviorServiceRegister;
import cn.shaikuba.mock.service.impl.HttpMockRequestService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/mock")
public class HttpMockRequestController {

    @Autowired
    private HttpMockRequestService mockRequestService;

    @Autowired
    private HttpMockCacheService httpMockCacheService;

    @Autowired
    private BehaviorServiceRegister behaviorServiceRegister;

    @RequestMapping(value = "/api/**", consumes = {
            MediaType.ALL_VALUE
            , MediaType.APPLICATION_JSON_VALUE
            , MediaType.TEXT_PLAIN_VALUE
            , MediaType.APPLICATION_FORM_URLENCODED_VALUE
            , MediaType.APPLICATION_XHTML_XML_VALUE
            , MediaType.APPLICATION_XML_VALUE

    })
    public void getMockObj(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

        try {
            HttpMockRequest mockRequest = objectConverter(httpRequest);
            if (mockRequest.validateMockRequest().size() > 0) {
                serviceNotFound(httpResponse);
                return;
            }
            HttpMockRequest mockResponse = httpMockCacheService.handle(mockRequest);
            if (mockResponse == null) {
                serviceNotFound(httpResponse);
                return;
            }
            httpResponse.setStatus(mockResponse.getStatusCode());
            // application/json or application/xml
            httpResponse.setContentType(MediaType.parseMediaType(mockResponse.getContentType()).toString());
            //httpResponse.setCharacterEncoding("UTF-8"); // instead of invoking setContentType("application/json;charset=gbk")

            BehaviorDescription behaviorDescription = mockResponse.getMockBehavior();
            if (behaviorDescription != null) {
                behaviorServiceRegister.action(behaviorDescription);
            }

            if (StringUtils.isNotEmpty(mockResponse.getResponseBody())) {
                PrintWriter printWriter = httpResponse.getWriter();
                printWriter.write(mockResponse.getResponseBody());
                printWriter.flush();
            } else {
                PrintWriter printWriter = httpResponse.getWriter();
                printWriter.write("");
                printWriter.flush();
            }

        } catch (IOException e) {
            mockServiceException(httpResponse, e);
        } finally {
        }

    }

    private HttpMockRequest objectConverter(HttpServletRequest servletRequest) throws IOException {
        HttpMockRequest.HttpMockRequestBuilder mockRequestBuilder = HttpMockRequest.builder()
                .requestMethod(servletRequest.getMethod())
                .requestUrl(servletRequest.getRequestURI().substring(servletRequest.getRequestURI().indexOf("api") + 3))
                .queryString(servletRequest.getQueryString())
                .formData(servletRequest.getQueryString());

        // headers
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, servletRequest.getHeader(headerName));
        }
        mockRequestBuilder.requestHeaders(JSON.toJSONString(headers));

        // request body
        Reader reader = servletRequest.getReader();
        mockRequestBuilder.requestBody(IOUtils.toString(reader));
        reader.close();

        return mockRequestBuilder.build();
    }

    private void serviceNotFound(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setCharacterEncoding("UTF-8");
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Mock service is not found");
        return;
    }

    private void mockServiceException(HttpServletResponse httpResponse, Throwable throwable) throws IOException {
        httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter printWriter = httpResponse.getWriter();
        printWriter.println(JSON.toJSONString(CollectionUtils.buildMap(throwable.getMessage(), ExceptionUtils.getRootCauseMessage(throwable))));
        printWriter.flush();
    }

}
