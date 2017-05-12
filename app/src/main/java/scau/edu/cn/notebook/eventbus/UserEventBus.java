package scau.edu.cn.notebook.eventbus;


import scau.edu.cn.notebook.entity.UserLocal;

/**
 * 作用：用户登录的EventBus传递对象
 */
public class UserEventBus {
    public UserLocal getmUser() {
        return mUser;
    }

    public void setmUser(UserLocal mUser) {
        this.mUser = mUser;
    }

    private UserLocal mUser;

    public UserEventBus(UserLocal mUser) {
        this.mUser = mUser;
    }
}
