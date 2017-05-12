package scau.edu.cn.notebook.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import scau.edu.cn.notebook.activity.LoginActivity;
import scau.edu.cn.notebook.activity.SendDynamicActivity;
import scau.edu.cn.notebook.activity.SketchActivity;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.model.UserModel;

/**
 * 作用：用户页面Presenter
 */
public class UserFragmentPresenter {
    private UserModel mUserModel = new UserModel();

    public UserFragmentPresenter() {
    }

    /**
     * 登录
     *
     * @param context
     */
    public void onLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    /**
     * 发说说
     *
     * @param context
     */
    public void onSendDynamic(Context context, User user) {
        if (mUserModel.isLogin()) {
            Intent intent = new Intent(context, SendDynamicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("User", user);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            onLogin(context);
        }
    }

    public void onCreateSketch(Context context, User user){
        if (mUserModel.isLogin()) {
            Intent intent = new Intent(context, SketchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("User", user);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            onLogin(context);
        }
    }

}
