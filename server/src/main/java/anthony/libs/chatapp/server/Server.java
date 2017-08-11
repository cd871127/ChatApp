package anthony.libs.chatapp.server;

import anthony.libs.chatapp.core.manager.ServiceManager;
import anthony.libs.chatapp.core.service.impl.ConnectionListener;
import anthony.libs.chatapp.core.service.impl.MessageListener;

/**
 * Created by chend on 2017/8/11.
 */
public class Server {
    public static void main(String[] args) {
        ServiceManager serviceManager = new ServiceManager(6);
        serviceManager.registerService(ConnectionListener.getInstance());
        serviceManager.registerService(MessageListener.getInstance());
        serviceManager.start();
    }

}
