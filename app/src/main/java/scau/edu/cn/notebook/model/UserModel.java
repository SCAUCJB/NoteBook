package scau.edu.cn.notebook.model;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import scau.edu.cn.notebook.application.BaseApplication;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.entity.UserLocal;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.impl.UserModelImpl;
import scau.edu.cn.notebook.utils.SPUtils;
import scau.edu.cn.notebook.utils.ToastUtils;

/**
 * 作用：用户信息Model
 */
public class UserModel implements UserModelImpl {

    private final String LOGINUSER = "loginuser";

    /**
     * 用户登录验证
     *
     * @param phone
     * @param passoword
     * @param listener
     */
    @Override
    public void getUser(String phone, String passoword, final NoteModelImpl.BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("Number", phone);
        query.addWhereEqualTo("Password", passoword);
        query.setLimit(1);
        query.findObjects(BaseApplication.getmContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                if (object != null && object.size() != 0) {
                    SPUtils.put(BaseApplication.getmContext(), LOGINUSER, object.get(0));
                    listener.getSuccess(object.get(0));
                } else {
                    listener.getFailure();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 根据objectId获取User
     *
     * @param objectId
     * @param listener
     */
    @Override
    public void getUser(String objectId, final NoteModelImpl.BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", objectId);
        query.setLimit(1);
        query.findObjects(BaseApplication.getmContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                if (object != null && object.size() != 0) {
                    SPUtils.put(BaseApplication.getmContext(), LOGINUSER, object.get(0));
                    listener.getSuccess(object.get(0));
                } else {
                    listener.getFailure();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 更换用户的头像
     *
     * @param path
     * @param listener
     */
    public void updateUserPhoto(String path, final String objectId, final NoteModelImpl.BaseListener listener) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(BaseApplication.getmContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                final User user = new User();
                user.setPhoto(bmobFile);
                user.update(BaseApplication.getmContext(), objectId, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        listener.getSuccess(user);

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        listener.getFailure();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                listener.getFailure();
            }
        });
    }

    /**
     * 判断当前用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        List<UserLocal> list = new Select().from(UserLocal.class).execute();
        if (list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将当前登录的对象保持到数据库中
     *
     * @param userLocal
     */
    public void putUserLocal(UserLocal userLocal) {
        new Delete().from(UserLocal.class).execute();
        userLocal.save();
    }

    /**
     * 获取当前登录的对象
     *
     * @return.
     */
    public UserLocal getUserLocal() {
        return new Select().from(UserLocal.class).executeSingle();

    }


    /**
     * 注册功能
     *
     * @param user
     */
    public void onRegister(User user, final NoteModelImpl.BaseListener listener) {
        user.save(BaseApplication.getmContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showLong(BaseApplication.getmContext(), "注册成功");
                listener.getSuccess(null);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showLong(BaseApplication.getmContext(), "注册失败");
                listener.getFailure();
            }
        });
    }

    /**
     * 判断当前手机号码是否注册
     *
     * @param phone
     * @param listener
     * @return
     */
    public void isPhoneRegister(String phone, final NoteModelImpl.BaseListener listener) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("Number", phone);
        query.setLimit(1);
        query.findObjects(BaseApplication.getmContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                if (object != null && object.size() != 0) {
                    listener.getSuccess(object.get(0));
                } else {
                    listener.getFailure();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }


}
