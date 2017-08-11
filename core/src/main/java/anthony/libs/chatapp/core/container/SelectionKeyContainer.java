package anthony.libs.chatapp.core.container;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/8/10.
 */
public class SelectionKeyContainer extends AbstractMapBasedContainer<String, SelectionKey> {
    private static SelectionKeyContainer ourInstance = new SelectionKeyContainer();

    public static SelectionKeyContainer getInstance() {
        return ourInstance;
    }

    private SelectionKeyContainer() {

    }


}
