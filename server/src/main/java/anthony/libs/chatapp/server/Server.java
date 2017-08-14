package anthony.libs.chatapp.server;

import anthony.libs.chatapp.core.manager.ServiceManager;
import anthony.libs.chatapp.core.service.impl.ConnectionListener;
import anthony.libs.chatapp.core.service.impl.MessageProcessService;
import anthony.libs.chatapp.core.service.impl.MessageListener;

/**
 * Created by chend on 2017/8/11.
 */
public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public Server() {
        init();
    }

    private ServiceManager serviceManager;

    private void init() {
        serviceManager = new ServiceManager(6);
        serviceManager.registerService(ConnectionListener.getInstance());
        serviceManager.registerService(MessageListener.getInstance());
        serviceManager.registerService(MessageProcessService.getInstance());
    }

    public void start() {
        serviceManager.start();
    }

}
