package com.anthony.chatapp.core.protocol;

/**
 * Created by chend on 2017/6/29.
 */
public class TextMessage extends Message {
    public TextMessage() {
        super();
        addHeader(HeaderType.TYPE, MessageTypes.TEXT);
    }

    public TextMessage(String text) {
        this();
        setTextBody(text);
    }

    public void setTextBody(String message) {
        byte[] data = message.getBytes(charset);
        setBody(data);
    }


}
