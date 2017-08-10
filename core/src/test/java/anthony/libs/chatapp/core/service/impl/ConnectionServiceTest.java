package anthony.libs.chatapp.core.service.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chend on 2017/8/10.
 */
public class ConnectionServiceTest {
    @Test
    public void run() throws Exception {
        ConnectionService c=new ConnectionService();
        c.start();
    }

}