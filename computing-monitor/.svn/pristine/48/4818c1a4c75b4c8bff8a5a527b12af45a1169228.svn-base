package com.shunwang.computing.monitor.enums;

import lombok.Getter;

/**
 * Created on 2024/11/8 13:55
 *
 * @author jiang tao
 */
@Getter
public enum BMEventEnum {
    /**
     * 串流程序卡死
     */
    STREAM_PROGRAM_STUCK_EVENT(1001, "串流程序卡死", BMExceptionEnum.STREAM_PROGRAM_STUCK),
    /**
     * 系统程序卡死
     */
    SYSTEM_STUCK_EVENT(1002, "系统卡死", BMExceptionEnum.SYSTEM_STUCK),

    /**
     * 上电失败
     */
    POWER_ON_FAILED_EVENT(1003, "上电失败", BMExceptionEnum.POWER_ON_FAILED),
    /**
     *cpu节能未关闭
     */
    CPU_ENERGY_SAVING_IS_ON_EVENT(1004, "cpu节能未关闭", BMExceptionEnum.CPU_ENERGY_SAVING_IS_ON),

    /**
     * 无独立显卡
     */
    NO_GPU_EVENT(1005, "无独立显卡", BMExceptionEnum.NO_GPU),

    /**
     * 未安装显卡驱动
     */
    NO_GPU_DRIVER_EVENT(1006, "未安装显卡驱动", BMExceptionEnum.NO_GPU_DRIVER),

    /**
     *显卡异常
     */
    GPU_ABNORMAL_EVENT(1007, "显卡异常", BMExceptionEnum.GPU_ABNORMAL),
    /**
     * 显存为0
     */
    GPU_MEMORY_ZERO_EVENT(1008, "GPU显存为0", BMExceptionEnum.GPU_MEMORY_ZERO),

    /**
     * 开机失败
     */
    BOOT_FAIL_EVENT(1009, "开机失败", BMExceptionEnum.BOOT_FAIL),

    /**
     *网络百兆
     */
    NETWORK_100M_EVENT(1010, "网络百兆", BMExceptionEnum.NETWORK_100M),

    /**
     * 主机异常断电
     */
    POWERED_OFF_ABNORMALLY_EVENT(1011, "主机异常断电", BMExceptionEnum.POWERED_OFF_ABNORMALLY),
    /**
     * ip 冲突断链
     */
    IP_CONFLICTS_DISCONNECTED_EVENT(1012, "ip冲突断链", BMExceptionEnum.IP_CONFLICTS_DISCONNECTED),

    /**
     * 蓝屏断链
     */
    BSOD_DISCONNECTED_EVENT(1013, "蓝屏断链", BMExceptionEnum.BSOD_DISCONNECTED),

    ;
    /**
     * code
     */
    private final Integer eventCode;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 对应异常
     */
    private final BMExceptionEnum bmException;


    BMEventEnum(Integer eventCode, String name, BMExceptionEnum bmException) {
        this.eventCode = eventCode;
        this.desc = name;
        this.bmException = bmException;
    }
    /**
     * 根据 eventCode 获取对应的枚举对象
     *
     * @param eventCode 枚举的 eventType
     * @return 对应的枚举对象，如果没有找到则返回 null
     */
    public static BMEventEnum getByEventCode(Integer eventCode) {
        for (BMEventEnum bmEventEnum : BMEventEnum.values()) {
            if (bmEventEnum.getEventCode().equals(eventCode)) {
                return bmEventEnum;
            }
        }
        return null;
    }



    public Integer getBmExceptionCode() {
        BMExceptionEnum exception = getBmException();
        return exception == null ? null : exception.getCode();
    }
}
