package com.anthony.chatapp.core.message.entity;

/**
 * Created by chend on 2017/7/4.
 */
public class CachedMessage {
    private Message message;
    private long cachedTime;

    public CachedMessage() {
    }

    public CachedMessage(Message message, long cachedTime) {
        this.message = message;
        this.cachedTime = cachedTime;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getCachedTime() {
        return cachedTime;
    }

    public void setCachedTime(long cachedTime) {
        this.cachedTime = cachedTime;
    }
}
