package com.anthony.chatapp.core.newcore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/7/12.
 * 抽象服务类，显示各种异常
 */
public abstract class AbstractService implements Service {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected boolean status = false;

    //关闭服务
    public void shutdown() {
        status = false;
    }

    @Override
    public void run() {
        status = true;
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void start() throws Exception;
}
