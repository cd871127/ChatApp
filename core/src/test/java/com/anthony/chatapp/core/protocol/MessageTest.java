package com.anthony.chatapp.core.protocol;

import org.junit.Test;

/**
 * Created by chend on 2017/6/29.
 */
public class MessageTest {
    @Test
    public void toString1() {
        Message m=new Message();
        m.addHeader(HeaderType.OPERATION,Operations.USER_LOGOUT);
        m.addHeader(HeaderType.FROM,"chendongistc@hotmail.com");
        m.addHeader(HeaderType.TO,"chendongistc@hotmail.com");
        m.addHeader(HeaderType.TYPE,MessageTypes.TEXT);
        m.addHeader(HeaderType.FILE_NAME,"1");
        m.setStringBody("i am the body");
        byte[] b=m.encode();
        Message a=Message.decode(b);
        System.out.println(a);
    }

}