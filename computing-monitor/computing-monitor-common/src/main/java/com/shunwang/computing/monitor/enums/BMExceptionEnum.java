package com.shunwang.computing.monitor.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created on 2024/11/8 13:55
 *
 * @author jiang tao
 */
@Getter
public enum BMExceptionEnum {
    /**
     * 串流程序卡死
     */
    STREAM_PROGRAM_STUCK(1, "串流程序卡死", "收到desktop_start上报未知断链;电源状态为开机;未查询到蓝屏信息;算力主机135端口通;算力主机8800端口不通", BMExceptionSourceEnum.SELF_ANALYTICS.getDesc()),
    /**
     * 系统程序卡死
     */
    SYSTEM_STUCK(2, "系统卡死", "收到desktop_start上报未知断链;电源状态为开机;未查询到蓝屏信息;算力主机135端口不通", BMExceptionSourceEnum.SELF_ANALYTICS.getDesc()),

    /**
     * 上电失败
     */
    POWER_ON_FAILED(3, "上电失败", "电源盒接口上电返回失败 或 返回成功后查询电源状态3次都失败", BMExceptionSourceEnum.SELF_ANALYTICS.getDesc()),
    /**
     *cpu节能未关闭
     */
    CPU_ENERGY_SAVING_IS_ON(4, "cpu节能未关闭", "Host_Monitor开机检查", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),

    /**
     * 无独立显卡
     */
    NO_GPU(5, "无独立显卡", "Host_Monitor轮询检查（wmi接口）", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),

    /**
     * 未安装显卡驱动
     */
    NO_GPU_DRIVER(6, "未安装显卡驱动", "Host_Monitor轮询检查（wmi接口）", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),

    /**
     *显卡异常
     */
    GPU_ABNORMAL(7, "显卡异常", "Host_Monitor轮询检查（wmi接口）", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),
    /**
     * 显存为0
     */
    GPU_MEMORY_ZERO(8, "GPU显存为0", "Host_Monitor轮询检查（wmi接口）", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),

    /**
     * 开机失败
     */
    BOOT_FAIL(9, "开机失败", "未收到Host_Monitor上报开机成功事件", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),

    /**
     *网络百兆
     */
    NETWORK_100M(10, "网络百兆", "Host_Monitor探测到网络百兆", BMExceptionSourceEnum.HOST_MONITOR_REPORT.getDesc()),

    /**
     * 主机异常断电
     */
    POWERED_OFF_ABNORMALLY(11, "主机异常断电", "desktop_start（瘦终端串流独立进程）上报未知断链，电源已关", BMExceptionSourceEnum.SELF_ANALYTICS.getDesc()),
    /**
     *
     */
    IP_CONFLICTS_DISCONNECTED(12, "ip冲突断链", "desktop_start（瘦终端串流独立进程）上报IP冲突断链", BMExceptionSourceEnum.DESKTOP_START_REPORT.getDesc()),

    /**
     * 蓝屏断链
     */
    BSOD_DISCONNECTED(13, "蓝屏断链", "desktop_start（瘦终端串流独立进程）上报蓝屏断链", BMExceptionSourceEnum.DESKTOP_START_REPORT.getDesc()),

    ;

    private final Integer code;
    /**
     * 名称
     */
    private final String name;

    /**
     * 详情
     */
    private final String detail;
    /**
     * 来源
     */
    private final String source;

    BMExceptionEnum(Integer code, String name, String detail, String source) {
        this.code = code;
        this.name = name;
        this.detail = detail;
        this.source = source;
    }

    public static String getNameByCode(Integer exceptionCode) {
        Optional<BMExceptionEnum> optional = Arrays.stream(BMExceptionEnum.values()).filter(e -> Objects.equals(e.getCode(), exceptionCode)).findAny();
        return optional.isPresent() ? optional.get().getName() : "";
    }
}
