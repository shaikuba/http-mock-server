package cn.shaikuba.mock.controller;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.api.ResultVO;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.data.entity.base.Pageable;
import cn.shaikuba.mock.data.mybatis.mapper.HttpMockMapper;
import cn.shaikuba.mock.service.impl.HttpMockRequestService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("mock/manage")
@CrossOrigin(origins = "*")
public class HttpMockManagementController {

    @Resource(name = "httpMockRequestService")
    private HttpMockRequestService httpMockService;

    @Resource
    private HttpMockMapper mockMapper;

    @GetMapping("/{id}")
    public ResultVO<HttpMockRequest> getMockObj(@PathVariable Long id) {
        HttpMockRequest queryObj = new HttpMockRequest();
        queryObj.setId(id);
        HttpMockRequest mockRequest = httpMockService.findMockRequest(queryObj);
        jsonBodyFormat(mockRequest);
        return ResultVO.<HttpMockRequest>success()
                .withData(mockRequest);
    }

    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<Pageable<HttpMockRequest>> getMockObjList(@RequestBody Criteria<HttpMockRequest> criteria) {
        List<HttpMockRequest> mockRequestList = httpMockService.findMockRequests(criteria);
        int count = mockMapper.countByCriteria(criteria);

        Pageable<HttpMockRequest> pageable = new Pageable<>();
        pageable.setDataCount(count);
        pageable.setElements(mockRequestList);

        ResultVO<Pageable<HttpMockRequest>> resultVO = ResultVO.<Pageable<HttpMockRequest>>success()
                .withData(pageable);
        return resultVO;
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO saveMockRequest(@RequestBody HttpMockRequest mockRequest) {
        if (!mockRequest.isValid()) {
            return ResultVO.fail(String.format("Some required fields' value is empty, required non-empty fields: %s!", StringUtils.join(mockRequest.validateCreateMockRequest())));
        }
        if (mockRequest.getId() != null && mockRequest.getId() > 0) {
            httpMockService.updateMockRequest(mockRequest);
        } else {
            httpMockService.saveMockRequest(mockRequest);
        }
        return ResultVO.success("Save Successfully");
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO updateMockRequest(@RequestBody HttpMockRequest mockRequest) {
        jsonBodyFormat(mockRequest);
        httpMockService.updateMockRequest(mockRequest);
        return ResultVO.success("Update Successfully");
    }

    private void jsonBodyFormat(HttpMockRequest mockRequest) {
        mockRequest.setResponseBody(JSON.parseObject(mockRequest.getResponseBody()).toJSONString());
    }

    @DeleteMapping(value = "delete", params = "idList")
    public ResultVO deleteMockRequest(@RequestParam String idList) {
        String[] ids = idList.split(",");

        httpMockService.deleteMockRequest(Stream.of(ids)
                .map(Long::parseLong)
                .collect(Collectors.toList())
        );

        return ResultVO.success("Delete successfully");
    }

}
