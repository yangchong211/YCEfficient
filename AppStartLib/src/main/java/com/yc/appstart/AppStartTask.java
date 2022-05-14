package com.yc.appstart;

import android.os.Process;


import com.yc.easyexecutor.DelegateTaskExecutor;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author: 杨充
 * @email : yangchong211@163.com
 * @time : 2018/04/15
 * @desc : app启动任务分发器
 * @revise :
 * GitHub : https://github.com/yangchong211/YCEfficient
 */
public abstract class AppStartTask implements TaskInterface {

    /**
     * 当前Task依赖的Task数量（等父亲们执行完了，孩子才能执行），默认没有依赖
     */
    private final CountDownLatch mDepends = new CountDownLatch(getTaskSize());

    /**
     * 当前Task等待，让父亲Task先执行
     */
    public void waitToNotify() {
        try {
            mDepends.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int priority() {
        //优先级
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    private int getTaskSize() {
        return getDependsTaskList() == null ? 0 : getDependsTaskList().size();
    }

    /**
     * 执行任务代码，让子类实现
     */
    public abstract void run();

    /**
     * 刷新
     */
    public void notifyTask() {
        mDepends.countDown();
    }

    @Override
    public Executor runOnExecutor() {
        return DelegateTaskExecutor.getInstance().getIOThreadExecutor();
    }

    @Override
    public List<Class<? extends AppStartTask>> getDependsTaskList() {
        return null;
    }

    @Override
    public boolean needWait() {
        return false;
    }

    /**
     * 是否在主线程执行
     *
     * @return true表示在主线程
     */
    public abstract boolean isRunOnMainThread();

}
