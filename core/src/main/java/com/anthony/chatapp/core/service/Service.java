package com.anthony.chatapp.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/7/4.
 */
public abstract class Service implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    //true表示服务启动,false表示服务关闭
    protected boolean status = false;

    public Service() {

        logger.debug("created");
    }

    //关闭服务
    public void shutdown() {
        status = false;
    }
}
