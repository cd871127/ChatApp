package com.anthony.chatapp.core.message.entity;

import com.alibaba.fastjson.JSON;
import com.anthony.chatapp.core.system.Parameters;

import java.io.*;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by chend on 2017/6/29.
 * headers保存消息头
 * 消息头包括
 * type:    消息类型   图片 文本 视频 音频 操作
 * filename: 如果是文件则有文件名
 * operation: 如果是操作 则有操作类型
 * length: 消息体长度
 * from:   发送用户
 * to:    接受用户
 * <p>
 * body保存消息体
 */
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    //消息头的字节数
    public static final int HEADER_LENGTH;
    public static final int HEADER_TYPE;

    //整个头部的长度
    public static final int TOTAL_HEADER_LENGTH;

    static {
        HEADER_LENGTH = 4;
        HEADER_TYPE = 1;
        TOTAL_HEADER_LENGTH = HEADER_LENGTH + HEADER_TYPE;
    }

    private String id;
    private String sender;
    private String receiver;
    private String timestamp;


    @Override
    public String toString() {
        return "id= " + id + "\n" + "sender= " + sender + "\n" + "receiver= " + receiver + "\n" + "timestamp= " + timestamp + "\n";
    }

    protected Message() {
    }

    public Message(String receiver) {
        this.sender = Parameters.SENDER;
        timestamp = String.valueOf(System.currentTimeMillis());
        id = UUID.randomUUID().toString().replace("-", "");
        this.receiver = receiver;
    }

//    private Message(MessageBuilder builder) {
//        this();
//        receiver = builder.receiver;
//        timestamp = builder.timestamp;
//        sender = builder.sender;
//        id = builder.id;
//    }

    private static Charset charset = Charset.forName("UTF-8");

    //编码以后 第一位是消息类型,之后4位是长度
    public byte[] encode() {
        //消息体的字节数组
        byte[] jsonByte = JSON.toJSONString(this).getBytes(charset);
        //消息头+消息体的字节数组
        byte[] data = new byte[TOTAL_HEADER_LENGTH + jsonByte.length];

        System.arraycopy(jsonByte, 0, data, TOTAL_HEADER_LENGTH, jsonByte.length);

        try {
            System.arraycopy(intToByteArray(jsonByte.length), 0, data, 1, HEADER_LENGTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        encode(data);

        return data;
    }

    public Type getType() {
        Type type = null;
        if (this instanceof Text)
            type = Type.TEXT;
        if (this instanceof Operation)
            type = Type.OPERATION;
        if (this instanceof File)
            type = Type.FILE;
        return type;
    }

    protected abstract void encode(byte[] data);

    public static Message decode(byte[] data, byte type) {
        Type t = Type.getType(type);

        java.lang.reflect.Type clazz = null;
        switch (t) {
            case TEXT:
                clazz = Text.class;
                break;
            case OPERATION:
                clazz = Operation.class;
                break;
            case FILE:
                clazz = File.class;
                break;
        }
        return JSON.parseObject(data, clazz);
    }
//    {
//        Message message = JSON.parseObject(data, Message.class);
//        Object object = message.getAttachment();
//        if (object != null && !(object instanceof String)) {
//            JSONObject jsonObject = (JSONObject) object;
//            switch (message.getType()) {
//                case OPERATION:
//                    message.setAttachment(jsonObject.toJavaObject(Operation.class));
//                    switch (((Operation) message.attachment).getOperationType()) {
//                        case LOGIN:
//                            Operation operation = (Operation) message.attachment;
//                            JSONObject operationAttachment = (JSONObject) operation.getAttachment();
//                            if (operationAttachment != null)
//                                operation.setAttachment(operationAttachment.toJavaObject(UserInfo.class));
//                            break;
//                    }
//                    break;
//                case FILE:
//                    message.setAttachment(jsonObject.toJavaObject(File.class));
//                    break;
//            }
//        }
//        return message;
//    }

    private byte[] intToByteArray(int i) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = byteArrayOutputStream.toByteArray();
        dataOutputStream.close();
        byteArrayOutputStream.close();
        return buf;
    }

    public static int getMessageLength(byte[] data, int pos, int length) {
        byte[] buffer = new byte[length];
        System.arraycopy(data, pos, buffer, 0, length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        int res = -1;
        try {
            res = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public enum Type {
        TEXT((byte) 1), OPERATION((byte) 2), FILE((byte) 3);

        byte value;

        Type(byte value) {
            this.value = value;
        }

        public byte value() {
            return value;
        }

        public static Type getType(byte value) {
            Type type = null;
            switch (value) {
                case 1:
                    type = TEXT;
                    break;
                case 2:
                    type = OPERATION;
                    break;
                case 3:
                    type = FILE;
                    break;
            }
            return type;
        }

    }

//    public static class MessageBuilder {
//        private String receiver;
//        private String timestamp;
//        private String sender;
//        private String id;
//
//        private MessageBuilder() {
//            this.sender = Parameters.SENDER;
//            timestamp = String.valueOf(System.currentTimeMillis());
//            id = UUID.randomUUID().toString().replace("-", "");
//        }
//
//
//        /**
//         * build text message
//         **/
//
//
//        /**
//         * build operation message
//         **/
//        public MessageBuilder(Operation.OperationTypes operationType, Object attachment, String receiver) {
//            this();
//            this.receiver = receiver;
//            this.type = MessageTypes.OPERATION;
//            Operation operation = new Operation();
//            operation.setAttachment(attachment);
//            operation.setOperationType(operationType);
//            this.attachment = operation;
//        }
//
//        public MessageBuilder(Operation.OperationTypes operationType, String receiver) {
//            this(operationType, null, receiver);
//        }
//
//        /**
//         * build file message
//         **/
//        public MessageBuilder(String fileName, long fileSize, File.FileTypes fileType, String receiver) {
//            this();
//            this.receiver = receiver;
//            this.type = MessageTypes.FILE;
//            File file = new File();
//            file.setFileName(fileName);
//            file.setFileSize(fileSize);
//            file.setFileType(fileType);
//            attachment = file;
//        }
//
//
//        public MessageBuilder attachment(String attachment) {
//            this.attachment = attachment;
//            return this;
//        }
//
//        public MessageBuilder sender(String sender) {
//            this.sender = sender;
//            return this;
//        }
//
//        public MessageBuilder receiver(String receiver) {
//            this.receiver = receiver;
//            return this;
//        }
//
//        public MessageBuilder timestamp(String timestamp) {
//            this.timestamp = timestamp;
//            return this;
//        }
//
//        public MessageBuilder type(MessageTypes type) {
//            this.type = type;
//            return this;
//        }
//
//        public Message build() {
//            return new Message(this);
//        }
//    }

}
