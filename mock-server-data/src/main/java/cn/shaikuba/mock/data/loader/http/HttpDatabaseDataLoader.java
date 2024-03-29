package cn.shaikuba.mock.data.loader.http;

import cn.shaikuba.mock.common.exception.UnifiedRuntimeException;
import cn.shaikuba.mock.common.process.loader.JsonMockDataLoader;
import cn.shaikuba.mock.common.util.CollectionUtils;
import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.description.BehaviorDescription;
import cn.shaikuba.mock.data.loader.message.JsonMessageConverter;
import cn.shaikuba.mock.data.loader.message.XmlMessageConverter;
import cn.shaikuba.mock.data.matchers.FormDataMatcher;
import cn.shaikuba.mock.data.matchers.QueryStringMatcher;
import cn.shaikuba.mock.data.mybatis.mapper.HttpMockMapper;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * DatabaseDataLoader
 */
@Slf4j
@ConditionalOnProperty(prefix = "mock.server.http", name = "data-loader", havingValue = "database")
@Component
public class HttpDatabaseDataLoader extends JsonMockDataLoader<HttpMockRequest, HttpMockRequest> {

    @Autowired
    private HttpMockMapper mockMapper;

    @Autowired
    private JsonMessageConverter jsonMessageConverter;

    @Autowired
    private XmlMessageConverter xmlMessageConverter;

    @Override
    public HttpMockRequest load(HttpMockRequest mockRequest) {

        List<HttpMockRequest> mockRequestList = mockMapper.findAllByUrlAndMethod(mockRequest);

        if (CollectionUtils.isEmpty(mockRequestList)) {
            return null;
        }
        HttpMockRequest foundMockRequest = mockRequestList.stream().filter(targetMockRequest -> {
            BehaviorDescription behaviorDescription = targetMockRequest.behaviorDescription();
            if (behaviorDescription == null) {
                return true;
            }

            Map<String, String> matchers = behaviorDescription.getMatchers();
            if (CollectionUtils.isEmpty(matchers)) {
                return true;
            }

            AtomicBoolean matched = new AtomicBoolean(true);
            //1. match query string first
            QueryStringMatcher queryStringMatcher = new QueryStringMatcher(matchers);
            if (queryStringMatcher.matches(mockRequest.getQueryString())) {
                return true;
            }

            //2. match with form data
            FormDataMatcher formDataMatcher = new FormDataMatcher(matchers);
            if (formDataMatcher.matches(JSONObject.parseObject(mockRequest.getFormData()))) {
                return true;
            }

            //2. match request body when query string match failed
            String requestBodyString = mockRequest.getRequestBody();

            JSONObject jsonBody;
            if ((jsonBody = jsonMessageConverter.convert(requestBodyString)) != null) {

                try {
                    matchers.forEach((key, value) -> {
                        if (!jsonBody.containsKey(key)
                                || !jsonBody.getString(key).matches(value)) {
                            matched.set(false);
                            throw new UnifiedRuntimeException("bread foreach");
                        }
                    });
                } catch (UnifiedRuntimeException e) {
                    log.info(e.getMessage());
                }

                if (matched.get()) {
                    return true;
                }
            }

            Document xmlBody;
            if ((xmlBody = xmlMessageConverter.convert(requestBodyString)) != null
                    && xmlBody.getRootElement().hasContent()) {
                try {
                    matchers.forEach((key, value) -> {
                        if (xmlBody.getRootElement().element(key) == null
                                || !xmlBody.getRootElement().element(key).getTextTrim().matches(value)) {
                            matched.set(false);
                            throw new UnifiedRuntimeException("break foreach");
                        }
                    });
                } catch (UnifiedRuntimeException e) {
                    log.info(e.getMessage());
                }

                if (matched.get()) {
                    return true;
                }

            }

            return false;
        }).findFirst().orElse(null);

        if (foundMockRequest != null) {
            merge(mockRequest, foundMockRequest);
        }
        return foundMockRequest;
    }

    /**
     * Merge original http request info to the eligible mock response
     *
     * @param mockRequest
     * @param mockResponse
     */
    private void merge(HttpMockRequest mockRequest, HttpMockRequest mockResponse) {
        mockResponse.setRequestMethod(mockRequest.getRequestMethod());
        mockResponse.setRequestUrl(mockRequest.getRequestUrl());
        mockResponse.setRequestHeaders(mockRequest.getRequestHeaders());

        mockResponse.setQueryString(mockRequest.getQueryString());
        //mockResponse.setFormData(mockRequest.getFormData());
        mockResponse.setRequestBody(mockRequest.getRequestBody());
    }

}

