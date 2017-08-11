package anthony.libs.chatapp.core.manager;

import anthony.libs.chatapp.core.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/8/11.
 */
public class ServiceManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ExecutorService es;
    private ArrayList<AbstractService> serviceList;

    public ServiceManager(int serviceCount) {
        this.es = Executors.newFixedThreadPool(serviceCount);
        this.serviceList = new ArrayList<>();
    }

    public void registerService(AbstractService service) {
        serviceList.add(service);
    }

    public void start() {
        serviceList.forEach((v) -> es.submit(v));
    }

    public void stop() {
        serviceList.forEach(AbstractService::stop);
        es.shutdown();
    }

}
