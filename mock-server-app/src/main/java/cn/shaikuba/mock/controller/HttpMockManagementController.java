package cn.shaikuba.mock.controller;

import cn.shaikuba.mock.data.entity.HttpMockRequest;
import cn.shaikuba.mock.data.entity.api.ResultVO;
import cn.shaikuba.mock.data.entity.base.Criteria;
import cn.shaikuba.mock.service.impl.HttpMockRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("mock/manage")
public class HttpMockManagementController {

    @Resource(name = "httpMockRequestService")
    private HttpMockRequestService httpMockService;

    @GetMapping("/{id}")
    public ResultVO<HttpMockRequest> getMockObj(@PathVariable Long id) {
        HttpMockRequest mockRequest = httpMockService.findMockRequest(id);

        return ResultVO.<HttpMockRequest>success()
                .withData(mockRequest);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<List<HttpMockRequest>> getMockObjList(@RequestBody Criteria<HttpMockRequest> criteria) {
        List<HttpMockRequest> mockRequestList = httpMockService.findMockRequests(criteria);

        return ResultVO.<List<HttpMockRequest>>success()
                .withData(mockRequestList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO saveMockRequest(@RequestBody HttpMockRequest mockRequest) {
        httpMockService.saveMockRequest(mockRequest);
        return ResultVO.success("Save Successfully");
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO updateMockRequest(@RequestBody HttpMockRequest mockRequest) {
        httpMockService.updateMockRequest(mockRequest);
        return ResultVO.success("Update Successfully");
    }

    @DeleteMapping(params = "idList")
    public ResultVO deleteMockRequest(@RequestParam String idList) {
        String[] ids = idList.split(",");

        httpMockService.deleteMockRequest(Stream.of(ids)
                .map(Long::parseLong)
                .collect(Collectors.toList())
        );

        return ResultVO.success("Delete successfully");
    }

}
