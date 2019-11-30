package com.permission.asyn;

/**
 * @auther: shenke
 * @date: 2019/11/29 22:24
 * @description: Http异步请求回调接口
 */
public interface HttpSyncCallback <T> {

    /**
     * 异步请求时回调
     * @param response
     */
    void callback(T response);

}
