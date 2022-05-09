package com.atdahai.reiji.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前用户登录的id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentLocal(long id){
        threadLocal.set(id);
    }
    public static long getCurrentId(){
        return threadLocal.get();
    }
}
