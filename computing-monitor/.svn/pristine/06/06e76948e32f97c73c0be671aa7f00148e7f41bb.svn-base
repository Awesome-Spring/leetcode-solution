package com.shunwang.computing.monitor.api.service;


import com.shunwang.computing.common.model.DataGrid;
import com.shunwang.computing.common.model.Result;
import com.shunwang.computing.monitor.api.model.request.BMExceptionRecordReportPageRequest;
import com.shunwang.computing.monitor.api.model.response.BMExceptionRecordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 2024/11/12 13:40
 *
 * @author jiang tao
 */
@FeignClient(name = "${computing.monitor.server:computing-monitor-server}")
public interface BMExceptionReportRecordServiceI {


    /**
     * 裸金属异常记录分页
     * @param request req
     * @return 分页数据
     */
    @PostMapping("/bmExceptionReportRecord/page")
    public Result<DataGrid<BMExceptionRecordResponse>> bmExceptionReportRecordPage(@RequestBody BMExceptionRecordReportPageRequest request);

}
