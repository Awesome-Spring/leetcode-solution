package com.shunwang.computing.monitor.api.service;

import com.shunwang.computing.common.model.DataGrid;
import com.shunwang.computing.common.model.Result;
import com.shunwang.computing.monitor.api.model.request.BMExceptionPageRequest;
import com.shunwang.computing.monitor.api.model.response.BMExceptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 2024/11/12 13:40
 *
 * @author jiang tao
 */
@FeignClient(name = "${computing.monitor.server:computing-monitor-server}")
public interface BMExceptionServiceI {


    /**
     * 裸金属异常(定义)分页
     * @param request req
     * @return 分页数据
     */
    @PostMapping("/bmException/page")
    Result<DataGrid<BMExceptionResponse>> bmExceptionPage(@RequestBody BMExceptionPageRequest request);



}
