package scau.edu.cn.notebook.impl;


import scau.edu.cn.notebook.entity.User;

/**
 * 作用：
 */
public interface DynamicModelImpl {
    void getDynamicItem(NoteModelImpl.BaseListener listener);
    void getDynamicItemByPhone(User user, NoteModelImpl.BaseListener listener);

}
