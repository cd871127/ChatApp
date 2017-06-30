package com.anthony.chatapp.core.protocol.constant;

/**
 * Created by chend on 2017/6/29.
 */
public class Header {

    public static final String TYPE = "type";
    public static final String FILE_NAME = "filename";
    public static final String OPERATION = "operation";
    public static final String LENGTH = "length";
    public static final String FROM = "from";
    public static final String TO = "to";

    public static boolean contain(String str) {
        return TYPE.equals(str) ||
                FILE_NAME.equals(str) ||
                OPERATION.equals(str) ||
                LENGTH.equals(str) ||
                FROM.equals(str) ||
                TO.equals(str);
    }


}
