package com.anthony.chatapp.core.message.entity;

/**
 * Created by chend on 2017/7/3.
 */
public class Operation {
    private OperationTypes operationType;
    private Object attachment;

    public OperationTypes getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypes operationType) {
        this.operationType = operationType;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationType=" + operationType +
                ", attachment=" + attachment +
                '}';
    }

    public enum OperationTypes {
        LOGIN, LOGOUT
    }
}
