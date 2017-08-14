package anthony.libs.chatapp.server.container;

import anthony.libs.chatapp.core.container.AbstractMapBasedContainer;
import anthony.libs.chatapp.core.user.UserInfo;

public class UserContainer extends AbstractMapBasedContainer<String, UserInfo> {
    private static UserContainer ourInstance = new UserContainer();

    public static UserContainer getInstance() {
        return ourInstance;
    }

    private UserContainer() {
        super();
    }
}
