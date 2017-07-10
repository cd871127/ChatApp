package com.anthony.chatapp.core.message.entity;

/**
 * Created by chend on 2017/7/10.
 */
public class Text extends Message {

    private Text() {
    }

    public Text(String receiver) {
        super(receiver);
    }

    public Text(String text, String receiver) {
        this(receiver);
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return super.toString() + "text=" + text + "\n";
    }

    @Override
    protected void encode(byte[] data) {
        data[0] = Type.TEXT.value();
    }
}
