package com.demo.Others;

import java.util.HashMap;

public class ThreadHelper {
    private static final ThreadLocal<HashMap<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    /**
     * 针对当前线程的map，根据key取值
     * 调用方法： Xxx xx = ThreadHelper.get("xxxxxxx")
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) THREAD_LOCAL.get().get(key);
    }

    /**
     * 针对当前线程的map，加个key
     * @param key
     * @param value
     * @return
     */
    public static Object set(String key, Object value) {
        return THREAD_LOCAL.get().put(key, value);
    }

    /**
     * 清空当前线程的map
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}