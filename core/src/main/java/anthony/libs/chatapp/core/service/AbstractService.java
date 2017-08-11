package anthony.libs.chatapp.core.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/8/10.
 */
public abstract class AbstractService implements Runnable {
    //服务状态,false 表示停止, true表示运行
    private boolean status = false;

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected AbstractService() {
        logger.info(getClass().getSimpleName() + " created");
    }

    public Logger getLogger() {
        return logger;
    }

    protected boolean getStatus() {
        return this.status;
    }

    protected void setStatus(boolean status) {
        this.status = status;
    }

    private void start() {
        this.status = true;
        logger.info(getClass().getSimpleName() + " started");
        execute();
    }

    public final void stop() {
        this.status = false;
        logger.info(getClass().getSimpleName() + " stopped");
    }

    @Override
    public void run() {
        start();
    }

    protected abstract void execute();

}
