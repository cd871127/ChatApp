package com.anthony.chatapp.core.protocol;

import org.junit.Test;

/**
 * Created by chend on 2017/6/29.
 */
public class MessageTest {
    @Test
    public void toString1() {
        String a="*";
        byte[] b=a.getBytes();
        char c='*';
        System.out.println((int)c);
        System.out.println(b[0]);
    }

}