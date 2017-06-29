package com.anthony.chatapp.core.protocol;

/**
 * Created by chend on 2017/6/29.
 */
public enum HeaderType {
    TYPE("type"), FILE_NAME("filename"), OPERATION("operation"), LENGTH("length"), FROM("from"), TO("to");

    private String value;

    HeaderType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static boolean contain(String value) {
        boolean flag;
        switch (value) {
            case "type":
            case "filename":
            case "operation":
            case "length":
            case "from":
            case "to":
                flag = true;
                break;
            default:
                flag = false;
        }

        return flag;
    }
}
