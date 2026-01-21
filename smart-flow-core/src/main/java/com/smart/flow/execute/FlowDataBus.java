package com.smart.flow.execute;

import com.smart.flow.Context;
import com.smart.flow.FlowEngineConfigHelper;
import com.smart.flow.execute.spi.TaskExecutable;
import com.smart.flow.property.FlowEngineConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author pw
 * @Date 2025/11/26 13:17
 * @Version 1.0
 **/
@Slf4j
public class FlowDataBus {
    private static ConcurrentLinkedQueue<Integer> QUEUE;

    private static ConcurrentHashMap<Integer, Context> PARAMS;

    private static Map<String, Class<? extends TaskExecutable>> ORIGINAL_TASK_EXECUTABLE_MAP = new ConcurrentHashMap<>();
    private static Integer currentThreadMaxValue;
    public static void init() {
        if (ObjectUtils.isEmpty(PARAMS)) {
            PARAMS = new ConcurrentHashMap<>();
            FlowEngineConfig flowEngineConfig = FlowEngineConfigHelper.getFlowEngineConfig();
            currentThreadMaxValue = flowEngineConfig.getCurrentThreadMaxValue();
            QUEUE = IntStream.range(0, currentThreadMaxValue)
                    .boxed()
                    .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
        }
    }

    public static Integer getSlotIndex(Context context) {
        try{
            Integer index = QUEUE.poll();
            if (ObjectUtils.isEmpty(index)) {
                synchronized (FlowDataBus.class) {
                    index = QUEUE.poll();
                    if  (ObjectUtils.isEmpty(index)) {
                        int newCurrentIndexMaxValue = (int) Math.round(currentThreadMaxValue * 1.75);
                        QUEUE.addAll(IntStream.range(currentThreadMaxValue, newCurrentIndexMaxValue)
                                .boxed()
                                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new)));
                        currentThreadMaxValue = newCurrentIndexMaxValue;
                        index = QUEUE.poll();
                    }

                }

            }
            if (ObjectUtils.isNotEmpty(index)) {
                PARAMS.put(index, context);
                return index;
            }
        }catch (Exception e){
            logger.error("getSlotIndex exception", e);
            return -9999;
        }
        return -9999;
    }

    public static Context getCurrentThreadContext(Integer index) {
        return PARAMS.get(index);
    }



    public static void clear(Integer slotIndex) {
        if (PARAMS.containsKey(slotIndex)) {
            logger.info("clear slot {}", slotIndex);
            PARAMS.remove(slotIndex);
            QUEUE.add(slotIndex);

        }
    }

    public static void addOriginalTaskExecutableMap(String executableName, Class<? extends TaskExecutable> clazz) {
        if (ORIGINAL_TASK_EXECUTABLE_MAP.containsKey(executableName)) {
           return;
        }
        ORIGINAL_TASK_EXECUTABLE_MAP.put(executableName, clazz);
    }

    public static Class<? extends TaskExecutable> getOriginalTaskExecutableMap(String executableName) {
        return ORIGINAL_TASK_EXECUTABLE_MAP.get(executableName);
    }
}
