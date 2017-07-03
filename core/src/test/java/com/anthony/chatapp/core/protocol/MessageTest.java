package com.anthony.chatapp.core.protocol;

import com.alibaba.fastjson.JSON;
import com.anthony.chatapp.core.message.entity.File;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import org.junit.Test;

/**
 * Created by chend on 2017/6/29.
 */
public class MessageTest {
    @Test
    public void toString1() {
        Message text = new Message.MessageBuilder("text", "server").build();
        Message opr = new Message.MessageBuilder(Operation.OperationTypes.LOGIN, "server").build();
        Message file = new Message.MessageBuilder("1.txt", 100, File.FileTypes.AUDIO, "server").build();


        String t=JSON.toJSONString(text);
        String o=JSON.toJSONString(opr);
        String f=JSON.toJSONString(file);

        System.out.println("JSON String:");
        System.out.println(t);
        System.out.println(o);
        System.out.println(f);


        System.out.println(text);

    }

}