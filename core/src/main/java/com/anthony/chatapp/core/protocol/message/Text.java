package com.anthony.chatapp.core.protocol.message;

import com.anthony.chatapp.core.protocol.constant.Header;
import com.anthony.chatapp.core.protocol.constant.MessageTypes;

/**
 * Created by chend on 2017/6/29.
 */
public class Text extends Message {
    public Text() {
        super();
        addHeader(Header.TYPE, MessageTypes.TEXT);
    }

    public Text(String text) {
        this();
        setTextBody(text);
    }

    public void setTextBody(String message) {
        byte[] data = message.getBytes(charset);
        setBody(data);
    }


}
