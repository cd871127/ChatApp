package com.anthony.chatapp.core.protocol.message;

import com.anthony.chatapp.core.exception.MessageHeaderLengthException;
import com.anthony.chatapp.core.protocol.constant.Header;
import com.anthony.chatapp.core.protocol.constant.MessageTypes;
import com.anthony.chatapp.core.protocol.constant.Operations;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, String> headers;
    private byte[] body;
    protected static Charset charset = Charset.forName("UTF-8");
    public static final int HEADER_LENGTH = 256;


    public Message() {
        headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }

    public void setFrom(String src) {
        addHeader(Header.FROM, src);
    }

    public void setTo(String desc) {
        addHeader(Header.TO, desc);
    }


    public String getMessageType() {
        return headers.get(Header.TYPE);
    }

    public Operations getOperation()
    {
        return null;
    }


    public void setBody(byte[] bytes) {
        this.body = bytes;
        addHeader(Header.LENGTH, String.valueOf(bytes==null?0:bytes.length));
    }

    public int getBodyLength() {
        return Integer.valueOf(headers.get(Header.LENGTH));
    }

    public byte[] encode() throws MessageHeaderLengthException {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append("\n"));

        byte[] tmp = stringBuilder.toString().getBytes(charset);
        if (tmp.length > HEADER_LENGTH)
            throw new MessageHeaderLengthException("Max length of header is 256 byte, current is " + tmp.length + " bytes.");
        byte[] data = new byte[HEADER_LENGTH + (body == null ? 0 : body.length)];
        System.arraycopy(tmp, 0, data, 0, tmp.length);
        if (body != null)
            System.arraycopy(body, 0, data, HEADER_LENGTH, body.length);
        return data;
    }

    public static Map<String, String> decodeHeaders(final byte[] headerBytes) {
        HashMap<String, String> headers = new HashMap<>();

//        byte[] headerBytes = new byte[HEADER_LENGTH];
//        System.arraycopy(data, 0, headerBytes, 0, HEADER_LENGTH);

        //消息头变为string
        String headerStr = new String(headerBytes, charset);
        String[] headerArray = headerStr.split("\n");

        //convert header to map
        for (String headerLine : headerArray) {
            String[] header = headerLine.split(":");
            if (Header.contain(header[0])) {
                headers.put(header[0], header[1].trim());
            }
        }
        return headers;
    }

    public static void decodeBody(final byte[] data, Message message) {


    }

    public static Message decode(InputStream in) throws IOException {
        Message message = new Message();
        byte[] header = new byte[HEADER_LENGTH];
        int readCount = in.read(header, 0, HEADER_LENGTH);
        if (HEADER_LENGTH != readCount)
            throw new IOException();
        message.headers = decodeHeaders(header);

        int bodyLength = Integer.valueOf(message.headers.get(Header.LENGTH));
        message.body = new byte[bodyLength];
        int readBodyLength = in.read(message.body, 0, bodyLength);
        if (readBodyLength != bodyLength)
            throw new IOException();
        return message;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append("\n"));
        stringBuilder.append("\n").append("body length: ").append(body == null ? 0 : body.length).append("\n");
        if (MessageTypes.TEXT.toString().equals(headers.get(Header.TYPE)))
            stringBuilder.append("body: \n").append(new String(body, charset));
        return stringBuilder.toString();
    }

}
