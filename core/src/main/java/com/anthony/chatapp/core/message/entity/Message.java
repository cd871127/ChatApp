package com.anthony.chatapp.core.message.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anthony.chatapp.core.system.Parameters;
import com.anthony.chatapp.core.user.UserInfo;

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
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int HEADER_BYTE_COUNT = 4;

    private String id;
    private String sender;
    private String receiver;
    private String timestamp;
    private MessageTypes type;
    private Object attachment;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                "receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", type=" + type +
                ", attachment='" + attachment + '\'' +
                '}';
    }

    private Message() {

    }

    private Message(MessageBuilder builder) {
        this();
        receiver = builder.receiver;
        timestamp = builder.timestamp;
        type = builder.type;
        sender = builder.sender;
        attachment = builder.attachment;
        id = builder.id;
    }

    protected static Charset charset = Charset.forName("UTF-8");

    public byte[] encode() {
        byte[] jsonByte = JSON.toJSONString(this).getBytes(charset);
        byte[] data = new byte[HEADER_BYTE_COUNT + jsonByte.length];
        System.arraycopy(jsonByte, 0, data, HEADER_BYTE_COUNT, jsonByte.length);

        try {
            System.arraycopy(intToByteArray(jsonByte.length), 0, data, 0, HEADER_BYTE_COUNT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Message decode(byte[] data) {
        Message message = JSON.parseObject(data, Message.class);
        Object object = message.getAttachment();
        if (object != null && !(object instanceof String)) {
            JSONObject jsonObject = (JSONObject) object;
            switch (message.getType()) {
                case OPERATION:
                    message.setAttachment(jsonObject.toJavaObject(Operation.class));
                    switch (((Operation) message.attachment).getOperationType()) {
                        case LOGIN:
                            Operation operation= (Operation) message.attachment;
                            JSONObject operationAttachment= (JSONObject) operation.getAttachment();
                            operation.setAttachment(operationAttachment.toJavaObject(UserInfo.class));
                            break;
                    }
                    break;
                case FILE:
                    message.setAttachment(jsonObject.toJavaObject(File.class));
                    break;
            }
        }
        return message;
    }

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

    public static int getMessageLength(byte[] data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
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

    public MessageTypes getType() {
        return type;
    }

    public void setType(MessageTypes type) {
        this.type = type;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }


    public enum MessageTypes {
        FILE, TEXT, OPERATION
    }

    public static class MessageBuilder {
        private String receiver;
        private String timestamp;
        private MessageTypes type;
        private String sender;
        private Object attachment;
        private String id;

        private MessageBuilder() {
            this.sender = Parameters.SENDER;
            timestamp = String.valueOf(System.currentTimeMillis());
            id = UUID.randomUUID().toString().replace("-", "");
            System.out.println(id);
        }

        public MessageBuilder(String receiver, MessageTypes type, Object attachment) {
            this();
            this.receiver = receiver;
            this.type = type;
            this.attachment = attachment;
        }

        /**
         * build text message
         **/
        public MessageBuilder(String text, String receiver) {
            this();
            this.receiver = receiver;
            this.type = MessageTypes.TEXT;
            attachment = text;
        }

        /**
         * build operation message
         **/
        public MessageBuilder(Operation.OperationTypes operationType, Object attachment, String receiver) {
            this();
            this.receiver = receiver;
            this.type = MessageTypes.OPERATION;
            Operation operation = new Operation();
            operation.setAttachment(attachment);
            operation.setOperationType(operationType);
            this.attachment = operation;
        }

        public MessageBuilder(Operation.OperationTypes operationType, String receiver) {
            this(operationType, null, receiver);
        }

        /**
         * build file message
         **/
        public MessageBuilder(String fileName, long fileSize, File.FileTypes fileType, String receiver) {
            this();
            this.receiver = receiver;
            this.type = MessageTypes.FILE;
            File file = new File();
            file.setFileName(fileName);
            file.setFileSize(fileSize);
            file.setFileType(fileType);
            attachment = file;
        }


        public MessageBuilder attachment(String attachment) {
            this.attachment = attachment;
            return this;
        }

        public MessageBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public MessageBuilder receiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public MessageBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MessageBuilder type(MessageTypes type) {
            this.type = type;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

}
