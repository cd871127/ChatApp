package com.anthony.chatapp.core.protocol.message;

import com.alibaba.fastjson.JSON;
import com.anthony.chatapp.core.system.Parameters;

import java.io.*;
import java.nio.charset.Charset;

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

    private static final String sender = Parameters.SENDER;
    private String receiver;
    private String timestamp;
    private MessageTypes type;
    private String text;
    private String fileName;
    private FileTypes fileType;

    @Override
    public String toString() {
        return "Message{" +
                "receiver='" + receiver + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", type=" + type +
                ", text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                '}';
    }

    private Message() {
    }

    private Message(MessageBuilder builder) {
        receiver = builder.receiver;
        timestamp = builder.timestamp;
        type = builder.type;
        text = builder.text;
        fileName = builder.fileName;
        fileType = builder.fileType;
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
        return JSON.parseObject(data, Message.class);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTypes getFileType() {
        return fileType;
    }

    public void setFileType(FileTypes fileType) {
        this.fileType = fileType;
    }

    public String getSender() {
        return sender;
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

    public enum Operations {
        LOGIN, LOGOUT;
    }

    public enum MessageTypes {
        FILE, TEXT, OPERATION
    }

    public enum FileTypes {
        VEDIO, IMAGE, AUDIO, NORMAL
    }


    public static class MessageBuilder {
        private String receiver;
        private String timestamp;
        private MessageTypes type;
        private String text;
        private String fileName;
        private FileTypes fileType;

        public MessageBuilder(String receiver, MessageTypes type, String timestamp) {
            this.receiver = receiver;
            this.type = type;
            this.timestamp = timestamp;
        }

        public MessageBuilder(String receiver, String text, String timestamp) {
            this(receiver, MessageTypes.TEXT, timestamp);
            this.text = text;
        }

        public MessageBuilder(String receiver, String fileName, FileTypes fileType, String timestamp) {
            this(receiver, MessageTypes.FILE, timestamp);
            this.fileName = fileName;
            this.fileType = fileType;
        }

        public MessageBuilder text(String text) {
            this.text = text;
            return this;
        }

        public MessageBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public MessageBuilder fileType(FileTypes fileType) {
            this.fileType = fileType;
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
