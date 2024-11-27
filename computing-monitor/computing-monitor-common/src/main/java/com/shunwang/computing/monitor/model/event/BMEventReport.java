package com.shunwang.computing.monitor.model.event;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class BMEventReport {
    /**
     * 裸金属id
     */
    private Integer bmId;
    /**
     * 裸金属mac
     */
    private String bmMac;
    /**
     * 裸金属 region
     */
    private Integer regionId;
    /**
     * 裸金属调度记录id
     */
    private Long bmScheduledRecordId;

}
