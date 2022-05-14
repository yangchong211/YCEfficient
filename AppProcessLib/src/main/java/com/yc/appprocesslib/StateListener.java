package com.yc.appprocesslib;

/**
 * @author: 杨充
 * @email : yangchong211@163.com
 * @time : 2018/04/15
 * @desc : 前后台监听listener
 * @revise :
 */
public interface StateListener {

    /**
     * 在前台
     */
    void onInForeground();

    /**
     * 在后台
     */
    void onInBackground();

}
