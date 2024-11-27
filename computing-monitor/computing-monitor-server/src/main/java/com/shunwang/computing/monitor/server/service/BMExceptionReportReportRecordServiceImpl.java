package com.shunwang.computing.monitor.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.shunwang.computing.common.model.DataGrid;
import com.shunwang.computing.common.model.Result;
import com.shunwang.computing.common.validator.HibernateValidatorHelper;
import com.shunwang.computing.monitor.api.model.request.BMExceptionRecordReportPageRequest;
import com.shunwang.computing.monitor.api.model.response.BMExceptionRecordResponse;
import com.shunwang.computing.monitor.api.service.BMExceptionReportRecordServiceI;
import com.shunwang.computing.monitor.server.db.entity.BMException;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionHandleRecord;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionReportRecord;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionReportRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 2024/11/12 20:21
 *
 * @author jiang tao
 */
@RestController
@Slf4j
public class BMExceptionReportReportRecordServiceImpl  implements BMExceptionReportRecordServiceI {

    @Resource
    private BMExceptionReportRecordService  bmExceptionReportRecordService;


    @Override
    public Result<DataGrid<BMExceptionRecordResponse>> bmExceptionReportRecordPage(BMExceptionRecordReportPageRequest request) {
        HibernateValidatorHelper.verify(request);
        List<Integer> bmIdList = request.getBmIdList();
        Integer regionId = request.getRegionId();
        Integer exceptionId = request.getExceptionId();
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        LocalDateTime beforeCreateTime = request.getBeforeCreateTime();
        long current = request.getCurrent();
        long pageSize = request.getPageSize();

        Page<BMExceptionRecordResponse> page = new Page<>(current, pageSize);
        MPJLambdaWrapper<BMExceptionReportRecord> queryWrapper = new MPJLambdaWrapper<>();
        //设置选择字段
        queryWrapper.select(BMExceptionReportRecord::getId)
                .select(BMExceptionReportRecord::getExceptionId)
                .select(BMExceptionReportRecord::getExceptionCode)
                .selectAs(BMException::getName, BMExceptionRecordResponse::getExceptionName)
                .selectAs(BMExceptionReportRecord::getBmScheduledRecordId, BMExceptionRecordResponse::getBmScheduledRecordId)
                .select(BMExceptionReportRecord::getRegionId)
                .select(BMExceptionReportRecord::getBmId)
                .select(BMExceptionReportRecord::getBmMac)
                .selectAs(BMExceptionHandleRecord::getId, BMExceptionRecordResponse::getHandleRecordId)
                .select(BMExceptionHandleRecord::getHandleType)
                .select(BMExceptionHandleRecord::getDeductScore)
                .selectAs(BMExceptionReportRecord::getCreatedTime,BMExceptionRecordResponse::getReportTime);

        //连表
        queryWrapper.leftJoin(BMExceptionHandleRecord.class, BMExceptionHandleRecord::getBmExceptionReportRecordId, BMExceptionReportRecord::getId);
        queryWrapper.leftJoin(BMException.class, BMException::getId, BMExceptionReportRecord::getExceptionId);
        //设置条件
        queryWrapper.in(CollectionUtils.isNotEmpty(bmIdList), BMExceptionReportRecord::getBmId, bmIdList);
        queryWrapper.eq(exceptionId != null, BMExceptionReportRecord::getExceptionId, exceptionId);
        queryWrapper.eq(regionId != null, BMExceptionReportRecord::getRegionId, regionId);
        queryWrapper.le(beforeCreateTime != null, BMExceptionReportRecord::getCreatedTime, beforeCreateTime);
        queryWrapper.ge(startTime != null, BMExceptionReportRecord::getCreatedTime, startTime);
        queryWrapper.le(endTime != null, BMExceptionReportRecord::getCreatedTime, endTime);
        queryWrapper.orderByDesc(BMExceptionReportRecord::getCreatedTime);
        // 执行查询
        Page<BMExceptionRecordResponse> bmExceptionRecordPage = bmExceptionReportRecordService.selectJoinListPage(page, BMExceptionRecordResponse.class, queryWrapper);

        return Result.success(DataGrid.build(bmExceptionRecordPage.getRecords(), current, pageSize, bmExceptionRecordPage.getTotal()));
    }
}
