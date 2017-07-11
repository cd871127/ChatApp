package com.anthony.chatapp.core.message.entity;

/**
 * Created by chend on 2017/7/3.
 */
public class Operation extends Message {
    private OperationType operationType;
    private Object attachment;

    private Operation() {
    }

    public Operation(OperationType type, String receiver) {
        this(type, null, receiver);
    }

    public Operation(OperationType type, Object attachment, String receiver) {
        super(receiver);
        this.operationType = type;
        this.attachment = attachment;
    }


    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
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
        return super.toString() + "operationType= " + operationType + "\n" + "attachment= " + (attachment==null?"null":attachment.toString()) + "\n";
    }

    @Override
    protected void encode(byte[] data) {
        data[0] = Type.OPERATION.value();
    }

    public enum OperationType {
        LOGIN, LOGOUT, ACK, ACKACK, LC
    }
}
