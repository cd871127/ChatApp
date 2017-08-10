package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.service.Service;

/**
 * Created by chend on 2017/8/10.
 */
public class ServiceContainer extends AbstractMapBasedContainer<String,Service> {
    private static ServiceContainer ourInstance = new ServiceContainer();

    public static ServiceContainer getInstance() {
        return ourInstance;
    }

    private ServiceContainer() {
    }

}
