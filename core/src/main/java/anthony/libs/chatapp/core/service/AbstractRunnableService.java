package anthony.libs.chatapp.core.service;

/**
 * Created by chend on 2017/8/10.
 */
public abstract class AbstractRunnableService extends AbstractService implements Runnable {
    @Override
    protected final void execute() {
        run();
    }
}
