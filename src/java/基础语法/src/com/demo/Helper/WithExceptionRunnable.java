package com.demo.Helper;

@FunctionalInterface
public interface WithExceptionRunnable {
    void run() throws Exception;
}
