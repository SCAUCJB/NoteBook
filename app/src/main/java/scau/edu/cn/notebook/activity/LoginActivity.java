package scau.edu.cn.notebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.entity.UserLocal;
import scau.edu.cn.notebook.eventbus.UserEventBus;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.UserModel;
import scau.edu.cn.notebook.utils.ToastUtils;

/**
 * 作用：登录activity
 */
public class LoginActivity extends Activity {
    @Bind(R.id.login_back)
    ImageView loginBack;
    @Bind(R.id.login_register)
    TextView loginRegister;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.login_uname)
    EditText loginUname;
    @Bind(R.id.login_pass)
    EditText loginPass;

    private UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity_main);
        ButterKnife.bind(this);
        mUserModel = new UserModel();
    }

    @OnClick({R.id.login_back, R.id.login_register, R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                startActivity(new Intent(LoginActivity.this, PhoneValidateActivity.class));
                finish();
                break;
            case R.id.login_btn:
                if (!TextUtils.isEmpty(loginUname.getText().toString()) && !TextUtils.isEmpty(loginPass.getText().toString())) {
                    mUserModel.getUser(loginUname.getText().toString(), loginPass.getText().toString(), new NoteModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            ToastUtils.showLong(LoginActivity.this, "登录成功");
                            User user = (User) o;
                            UserLocal userLocal = new UserLocal();
                            userLocal.setName(user.getName());
                            userLocal.setObjectId(user.getObjectId());
                            userLocal.setNumber(user.getNumber());
                            if (user.getPhoto() != null) {
                                userLocal.setPhoto(user.getPhoto().getUrl());
                            }
                            mUserModel.putUserLocal(userLocal);
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            EventBus.getDefault().post(new UserEventBus(userLocal));
                            finish();
                        }

                        @Override
                        public void getFailure() {
                            ToastUtils.showLong(LoginActivity.this, "登录失败");
                        }
                    });
                }
                break;
        }
    }
}
