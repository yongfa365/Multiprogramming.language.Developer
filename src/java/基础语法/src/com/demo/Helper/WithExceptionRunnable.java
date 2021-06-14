package com.demo.Helper;

// 想找类似的可以看：org.apache.commons.lang3.Functions或 org.apache.commons.lang3.function.Failable
@FunctionalInterface
public interface WithExceptionRunnable {
    void run() throws Exception;
}
