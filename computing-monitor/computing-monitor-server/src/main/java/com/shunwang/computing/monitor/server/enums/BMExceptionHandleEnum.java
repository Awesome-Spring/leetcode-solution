package com.shunwang.computing.monitor.server.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * Created on 2024/11/8 13:55
 *
 * @author jiang tao
 */
@Getter
public enum BMExceptionHandleEnum {
    /**
     * 禁用
     */
    DISABLE(1, "禁用"),
    /**
     * 评分(扣分)
     */
    DEDUCT_SCORE(2, "评分"),

    ;


    private final Integer code;

    private final String name;


    BMExceptionHandleEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据 code 获取对应的枚举对象 name
     *
     * @param code 枚举的 code
     * @return 对应的枚举对象的 name，如果没有找到则返回 ""
     */
    public static String getNameByCode(Integer code) {

        BMExceptionHandleEnum enums = Arrays.stream(BMExceptionHandleEnum.values()).filter(e -> e.getCode().equals(code)).findAny().orElseGet(null);
        return enums == null ? "" : enums.getName();
    }


}
