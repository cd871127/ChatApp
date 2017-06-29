package com.anthony.chatapp.core.exception;

/**
 * Created by chend on 2017/6/29.
 */
public class MessageHeaderLengthException extends RuntimeException {
    public MessageHeaderLengthException() {
        super("Length of header of message is too long, max length is 256 bytes");
    }

    public MessageHeaderLengthException(String mess) {
        super(mess);
    }

}
