package com.anthony.chatapp.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/7/4.
 */
public abstract class Service implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public Service() {

        logger.debug("created");
    }
}
