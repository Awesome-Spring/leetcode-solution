package com.shunwang.computing.monitor.server.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created on 2024/11/8 13:55
 *
 * @author jiang tao
 */
@Getter
public enum BMExceptionFrequencyEnum {
    /**
     * 出现一次
     */
    ONCE(1, 1, "出现一次"),
    /**
     * 连续两次
     */
    SUCCESSIVE_TWICE(2, 2, "连续出现2次"),

    ;

    /**
     *  数量窗口
     */
    private final Integer countWindow;

    /**
     *  在数量窗口中出现的次数
     */
    private final Integer occurrenceCount;
    /**
     * name
     */
    private final String name;


    BMExceptionFrequencyEnum(int countWindow, int occurrenceCount, String name) {
        this.countWindow = countWindow;
        this.occurrenceCount = occurrenceCount;
        this.name = name;
    }

    public static String getNameByCode(Integer countWindow, Integer occurrenceCount) {
        Optional<BMExceptionFrequencyEnum> optional = Arrays.stream(BMExceptionFrequencyEnum.values()).filter(e -> e.getCountWindow().equals(countWindow) && e.getOccurrenceCount().equals(occurrenceCount)).findAny();
        return optional.isPresent() ? optional.get().getName() : String.format("%d次(连续)出现%d次", countWindow, occurrenceCount);
    }
}
