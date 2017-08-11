package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.service.AbstractService;

/**
 * Created by chend on 2017/8/10.
 */
public class ServiceContainer extends AbstractMapBasedContainer<String, AbstractService> {
    private static ServiceContainer ourInstance = new ServiceContainer();

    public static ServiceContainer getInstance() {
        return ourInstance;
    }

    private ServiceContainer() {
    }

}
