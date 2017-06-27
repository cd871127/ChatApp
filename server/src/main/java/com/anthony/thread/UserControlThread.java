package com.anthony.thread;

/**
 * Created by chend on 2017/6/27.
 */
public class UserControlThread implements Runnable {



    @Override
    public void run() {
        while (true)
        {
            System.out.println(1);
        }
    }
}
