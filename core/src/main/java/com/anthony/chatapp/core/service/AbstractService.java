package com.anthony.chatapp.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/6/28.
 */
public abstract class AbstractService implements Service {



    protected int port;

    public AbstractService(int port) {
        this.port = port;
    }


}
