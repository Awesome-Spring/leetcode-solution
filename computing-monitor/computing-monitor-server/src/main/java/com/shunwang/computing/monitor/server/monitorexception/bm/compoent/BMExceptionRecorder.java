package com.shunwang.computing.monitor.server.monitorexception.bm.compoent;

import com.shunwang.computing.monitor.enums.BMExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BMExceptionRecorder {

    private static final Integer NORMAL = 0;
    public static final Integer EXCEPTION = 1;

    // 私有构造函数
    private BMExceptionRecorder() {
    }

    // 用于存储每个异常对应不同裸金属的记录
    private static final Map<Integer, BMRecorder> EXCEPTION_RECORDER_MAP = new ConcurrentHashMap<>();

    static {
        for (BMExceptionEnum bmException : BMExceptionEnum.values()) {
            EXCEPTION_RECORDER_MAP.putIfAbsent(bmException.getCode(), BMRecorder.create(bmException == BMExceptionEnum.POWER_ON_FAILED ? 2 : 1));
        }
    }

    // 记录异常
    public static void recordException(Integer bmId, Integer exceptionCode) {
        BMRecorder bmrecorder = EXCEPTION_RECORDER_MAP.get(exceptionCode);
        if (bmrecorder != null) {
            bmrecorder.recordException(bmId);
        }
    }

    // 记录正常
    public static void recordNormal(Integer bmId, Integer exceptionCode) {
        BMRecorder bmrecorder = EXCEPTION_RECORDER_MAP.get(exceptionCode);
        if (bmrecorder != null) {
            bmrecorder.recordNormal(bmId);
        }

    }

    // 获取某个裸金属的某种异常的总计数
    public static List<Integer> getBMRecordList(Integer bmId, Integer exceptionCode) {
        BMRecorder bmrecorder = EXCEPTION_RECORDER_MAP.get(exceptionCode);
        if (bmrecorder != null) {
            return bmrecorder.getExceptionRecord(bmId);
        }
        return new LinkedList<>();
    }

    public static void reset(Integer bmId, Integer exceptionCode) {
        BMRecorder BMRecorder = EXCEPTION_RECORDER_MAP.get(exceptionCode);
        if (BMRecorder != null) {
            BMRecorder.reset(bmId);
        }
    }

    @Data
    @AllArgsConstructor
    private static class BMRecorder {

        private int maxSize;

        private Map<Integer, LinkedList<Integer>> bmRecorder;


        public void recordNormal(Integer bmId) {
            addElement(bmId, NORMAL);
        }

        public void recordException(Integer bmId) {
            addElement(bmId, EXCEPTION);
        }

        private void addElement(Integer bmId, Integer element) {
            LinkedList<Integer> exceptionRecorder = bmRecorder.computeIfAbsent(bmId, i -> new LinkedList<>());
            synchronized (exceptionRecorder) {
                //左进右出
                exceptionRecorder.addFirst(element);
                while (exceptionRecorder.size() > maxSize) {
                    exceptionRecorder.removeLast();
                }
            }
        }

        public LinkedList<Integer> getExceptionRecord(Integer bmId) {
            LinkedList<Integer> list = bmRecorder.get(bmId);
            if (list != null) {
                return list;
            }
            return new LinkedList<>();
        }


        public void reset(Integer bmId) {
            LinkedList<Integer> list = bmRecorder.get(bmId);
            if (list != null) {
                synchronized (list) {
                    list.clear();
                }
            }
        }

        public static BMRecorder create(Integer maxSize) {
            return new BMRecorder(maxSize == null ? 0 : maxSize, new ConcurrentHashMap<>());
        }
    }


}
