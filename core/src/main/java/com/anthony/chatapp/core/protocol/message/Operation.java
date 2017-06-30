package com.anthony.chatapp.core.protocol.message;

import com.anthony.chatapp.core.protocol.constant.Header;
import com.anthony.chatapp.core.protocol.constant.MessageTypes;

/**
 * Created by chend on 2017/6/30.
 */
public class Operation extends Message {
    public Operation() {
        super();
        addHeader(Header.TYPE, MessageTypes.OPERATION);
        setBody(null);
    }

    public Operation(String operation) {
        this();
        addHeader(Header.OPERATION, operation);
    }
}
