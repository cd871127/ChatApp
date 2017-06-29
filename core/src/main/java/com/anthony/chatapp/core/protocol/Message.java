package com.anthony.chatapp.core.protocol;

import com.anthony.chatapp.core.exception.MessageHeaderLengthException;

import java.nio.charset.Charset;
import java.util.HashMap;

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
public class Message {

    private HashMap<String, String> headers;
    private byte[] body;
    protected static Charset charset = Charset.forName("UTF-8");
    public static final int HEADER_LENGTH = 256;

    public Message() {
        headers = new HashMap<>();
    }

    public void addHeader(HeaderType type, String value) {
        headers.put(type.toString(), value);
    }

    public void addHeader(HeaderType type, MessageTypes messageTypes) {
        headers.put(type.toString(), messageTypes.toString());
    }

    public void addHeader(HeaderType type, Operations operation) {
        headers.put(type.toString(), operation.toString());
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void removeHeader(HeaderType type) {
        headers.remove(type.toString());
    }

    public void setFrom(String src) {
        addHeader(HeaderType.FROM, src);
    }

    public void setTo(String desc) {
        addHeader(HeaderType.TO, desc);
    }
//    public void appendBody(String content) {
//        byte[] bytes = content.getBytes(charset);
//        for (byte b : bytes) {
//            body.add(b);
//        }
//    }

    public void setBody(byte[] bytes) {
        this.body = bytes;
        addHeader(HeaderType.LENGTH, String.valueOf(bytes.length));
    }

    public int getBodyLength()
    {
        return Integer.valueOf(headers.get(HeaderType.LENGTH.toString()));
    }

    public byte[] encode() throws MessageHeaderLengthException {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append("\n"));
        byte[] data = new byte[HEADER_LENGTH + (body == null ? 0 : body.length)];
        byte[] tmp = stringBuilder.toString().getBytes(charset);
        if (tmp.length > HEADER_LENGTH)
            throw new MessageHeaderLengthException("Max length of header is 256 byte, current is " + tmp.length + " bytes.");
        System.arraycopy(tmp, 0, data, 0, tmp.length);
        if (body != null)
            System.arraycopy(body, 0, data, HEADER_LENGTH, body.length);
        return data;
    }

    public static void decodeHeaders(final byte[] data, Message message) {
        HashMap<String, String> headers = new HashMap<>();

        byte[] headerBytes = new byte[HEADER_LENGTH];
        System.arraycopy(data, 0, headerBytes, 0, HEADER_LENGTH);

        //消息头变为string
        String headerStr = new String(headerBytes, charset);
        String[] headerArray = headerStr.split("\n");

        //convert header to map
        for (String headerLine : headerArray) {
            String[] header = headerLine.split(":");
            if (HeaderType.contain(header[0])) {
                headers.put(header[0], header[1].trim());
            }
        }
        message.headers = headers;
    }

    public static void decodeBody(final byte[] data, Message message) {
        int bodyLength = Integer.valueOf(message.headers.get(HeaderType.LENGTH.toString()));
        //data copy to body
        byte[] tmp = new byte[bodyLength];
        System.arraycopy(data, HEADER_LENGTH, tmp, 0, bodyLength);
        message.body = tmp;
    }

    public static Message decode(final byte[] data) {
        Message message = new TextMessage();
        decodeHeaders(data, message);
        decodeBody(data, message);
        return message;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append("\n"));
        stringBuilder.append("\n").append("body length: ").append(body == null ? 0 : body.length).append("\n");
        if (MessageTypes.TEXT.toString().equals(headers.get(HeaderType.TYPE.toString())))
            stringBuilder.append("body: \n").append(new String(body, charset));
        return stringBuilder.toString();
    }

}
