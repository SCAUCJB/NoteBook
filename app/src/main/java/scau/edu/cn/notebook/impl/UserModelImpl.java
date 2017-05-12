package scau.edu.cn.notebook.impl;

/**
 * 作用：获取用户接口
 */
public interface UserModelImpl {
    void getUser(String name, String passoword, NoteModelImpl.BaseListener listener);
    void getUser(String objectId, final NoteModelImpl.BaseListener listener);
}
