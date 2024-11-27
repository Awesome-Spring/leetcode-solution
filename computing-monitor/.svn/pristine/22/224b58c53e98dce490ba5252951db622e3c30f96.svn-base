package com.shunwang.computing.monitor.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.shunwang.computing.common.model.DataGrid;
import com.shunwang.computing.common.model.Result;
import com.shunwang.computing.monitor.api.model.request.BMExceptionRulerPageRequest;
import com.shunwang.computing.monitor.api.model.response.BMExceptionRulerResponse;
import com.shunwang.computing.monitor.api.service.BMExceptionRulerServiceI;
import com.shunwang.computing.monitor.server.config.BMExceptionEnabledConfig;
import com.shunwang.computing.monitor.server.db.entity.BMException;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionRuler;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionRulerRel;
import com.shunwang.computing.monitor.server.enums.BMExceptionFrequencyEnum;
import com.shunwang.computing.monitor.server.enums.BMExceptionHandleEnum;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionRulerService;
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
public class BMExceptionRulerServiceImpl extends BMExceptionRulerService implements BMExceptionRulerServiceI {

    @Resource
    private BMExceptionEnabledConfig bmExceptionEnabledConfig;


    @Override
    public Result<DataGrid<BMExceptionRulerResponse>> bmExceptionRulerPage(BMExceptionRulerPageRequest request) {
        long current = request.getCurrent();
        long pageSize = request.getPageSize();
        Page<BMExceptionRulerResponse> page = new Page<>(current, pageSize);
        MPJLambdaWrapper<BMExceptionRuler> queryWrapper = new MPJLambdaWrapper<>();
        //设置选择字段
        queryWrapper.select(BMExceptionRuler::getId)
                .select(BMExceptionRuler::getName)
                .select(BMExceptionRuler::getCountWindow)
                .select(BMExceptionRuler::getOccurrenceCount)
                .select(BMExceptionRuler::getHandleType)
                .select(BMExceptionRuler::getDeductScore)
                .selectAs(BMException::getId, BMExceptionRulerResponse::getExceptionId)
                .selectAs(BMException::getCode, BMExceptionRulerResponse::getExceptionCode)
                .selectAs(BMException::getName, BMExceptionRulerResponse::getExceptionName)
                .selectAs(BMException::getUpdatedTime, BMExceptionRulerResponse::getEffectiveTime);
        //连表
        queryWrapper.leftJoin(BMExceptionRulerRel.class, BMExceptionRulerRel::getRulerId, BMExceptionRuler::getId);
        queryWrapper.leftJoin(BMException.class, BMException::getId, BMExceptionRulerRel::getExceptionId);
        //设置条件
        // queryWrapper.eq(exceptionId != null, BMException::getId, exceptionId);
        // 执行查询
        Page<BMExceptionRulerResponse> rulerResponsePage = selectJoinListPage(page, BMExceptionRulerResponse.class, queryWrapper);
        List<BMExceptionRulerResponse> records = rulerResponsePage.getRecords();
        records.forEach(this::setEnabledAndDescInfo);
        return Result.success(DataGrid.build(records, current, pageSize, rulerResponsePage.getTotal()));

    }


    private void setEnabledAndDescInfo(BMExceptionRulerResponse bmExceptionRulerResponse) {
        bmExceptionRulerResponse.setEnabled(bmExceptionEnabledConfig.getBMExceptionRulerEnabledById(bmExceptionRulerResponse.getId()));
        bmExceptionRulerResponse.setDetailDesc(BMExceptionFrequencyEnum.getNameByCode(bmExceptionRulerResponse.getCountWindow(), bmExceptionRulerResponse.getOccurrenceCount()));
        bmExceptionRulerResponse.setHandleTypeDesc(BMExceptionHandleEnum.getNameByCode(bmExceptionRulerResponse.getHandleType()));
    }
}
