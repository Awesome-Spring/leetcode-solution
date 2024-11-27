package com.shunwang.computing.monitor.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.shunwang.computing.common.model.DataGrid;
import com.shunwang.computing.common.model.Result;
import com.shunwang.computing.monitor.api.model.request.BMExceptionPageRequest;
import com.shunwang.computing.monitor.api.model.response.BMExceptionResponse;
import com.shunwang.computing.monitor.api.service.BMExceptionServiceI;
import com.shunwang.computing.monitor.server.config.BMExceptionEnabledConfig;
import com.shunwang.computing.monitor.server.db.entity.BMException;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2024/11/14 09:56
 *
 * @author jiang tao
 */
@RestController
@Slf4j
public class BMExceptionServiceImpl  implements BMExceptionServiceI {

    @Resource
    private BMExceptionService bmExceptionService;
    @Resource
    private BMExceptionEnabledConfig bmExceptionEnabledConfig;

    @Override
    public Result<DataGrid<BMExceptionResponse>> bmExceptionPage(BMExceptionPageRequest request) {
        long current = request.getCurrent();
        long pageSize = request.getPageSize();
        Integer exceptionId = request.getExceptionId();

        Page<BMExceptionResponse> page = new Page<>(current, pageSize);
        MPJLambdaWrapper<BMException> queryWrapper = new MPJLambdaWrapper<>();
        //设置选择字段
        queryWrapper.select(BMException::getId)
                .select(BMException::getName)
                .select(BMException::getCode)
                .select(BMException::getSource)
                .select(BMException::getDetail)
                .selectAs(BMException::getUpdatedTime, BMExceptionResponse::getEffectiveTime);
        queryWrapper.eq(exceptionId != null, BMException::getId, exceptionId);
        // 执行查询
        Page<BMExceptionResponse> bmExceptionPage = bmExceptionService.selectJoinListPage(page, BMExceptionResponse.class, queryWrapper);
        List<BMExceptionResponse> records = bmExceptionPage.getRecords();
        records.forEach(this::setEnabled);
        return Result.success(DataGrid.build(records, current, pageSize, bmExceptionPage.getTotal()));
    }

    private void setEnabled(BMExceptionResponse bmExceptionResponse) {
        bmExceptionResponse.setEnabled(bmExceptionEnabledConfig.getBMExceptionEnabledById(bmExceptionResponse.getCode()));
    }

}
