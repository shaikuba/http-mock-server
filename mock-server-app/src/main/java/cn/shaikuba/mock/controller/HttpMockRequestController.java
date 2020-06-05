package cn.shaikuba.mock.controller;

import cn.shaikuba.mock.common.util.CollectionUtils;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.base.Criteria;
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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("mock/api")
public class HttpMockRequestController {

    @Autowired
    private HttpMockRequestService mockRequestService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE
            , MediaType.TEXT_PLAIN_VALUE
            , MediaType.APPLICATION_FORM_URLENCODED_VALUE
            , MediaType.APPLICATION_XHTML_XML_VALUE
            , MediaType.APPLICATION_XML_VALUE
    })
    public void getMockObj(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

        try {
            HttpMockRequest mockRequest = objectConverter(httpRequest);
            Criteria<HttpMockRequest> criteria = Criteria.<HttpMockRequest>newCriteria()
                    .criteria(mockRequest);
            List<HttpMockRequest> mockRequestList = mockRequestService.findMockRequests(criteria);

            if (mockRequestList.size() == 0) {
                serviceNotFound(httpResponse);
            }
            HttpMockRequest mockResponse = mockRequestList.get(0);
            httpResponse.setStatus(mockResponse.getStatusCode());
            httpResponse.setContentType(MediaType.parseMediaType(mockResponse.getContentType()).getType());
            httpResponse.setCharacterEncoding("UTF-8");
            if (StringUtils.isNotEmpty(mockResponse.getResponseBody())) {
                httpResponse.getWriter().write(mockResponse.getResponseBody());
                httpResponse.getWriter().flush();
            } else {
                httpResponse.getWriter().write("");
                httpResponse.getWriter().flush();
            }

        } catch (IOException e) {
            mockServiceException(httpResponse, e);
        } finally {
            httpResponse.getWriter()
                    .close();
        }

    }

    private HttpMockRequest objectConverter(HttpServletRequest servletRequest) throws IOException {
        HttpMockRequest.HttpMockRequestBuilder mockRequestBuilder = HttpMockRequest.builder()
                .requestMethod(RequestMethod.valueOf(servletRequest.getMethod()))
                .requestUrl(servletRequest.getRequestURI())
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
    }

    private void mockServiceException(HttpServletResponse response, Throwable throwable) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .println(JSON.toJSONString(CollectionUtils.buildMap(throwable.getMessage(), ExceptionUtils.getRootCauseMessage(throwable))));
        response.getWriter()
                .flush();
    }

}
