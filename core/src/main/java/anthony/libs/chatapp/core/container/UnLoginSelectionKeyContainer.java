package anthony.libs.chatapp.core.container;

import java.nio.channels.SelectionKey;
import java.util.Map;

/**
 * Created by chend on 2017/8/10.
 *
 */
public class UnLoginSelectionKeyContainer extends AbstractMapBasedContainer<String,SelectionKey> {
    private static UnLoginSelectionKeyContainer ourInstance = new UnLoginSelectionKeyContainer();

    public static UnLoginSelectionKeyContainer getInstance() {
        return ourInstance;
    }

    private UnLoginSelectionKeyContainer() {

    }


}
