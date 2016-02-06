package sicxe.view.user;

/**
 * Created by maciek on 28/01/16.
 */
public class UserStatus {
    UserStatusEnum userStatus;

    public UserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    public UserStatusEnum getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }
}
