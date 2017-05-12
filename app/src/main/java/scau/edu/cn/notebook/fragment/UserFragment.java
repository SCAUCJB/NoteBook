package scau.edu.cn.notebook.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.activity.HomeActivity;
import scau.edu.cn.notebook.activity.LoginActivity;
import scau.edu.cn.notebook.activity.MyDynamicActivity;
import scau.edu.cn.notebook.activity.MySketchActivity;
import scau.edu.cn.notebook.dialog.DialogBuilder;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.entity.UserLocal;
import scau.edu.cn.notebook.eventbus.UserEventBus;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.UserModel;
import scau.edu.cn.notebook.presenter.UserFragmentPresenter;
import scau.edu.cn.notebook.utils.ToastUtils;


public class UserFragment extends Fragment {
    @Bind(R.id.UserPhoto)
    ImageView UserPhoto;
    @Bind(R.id.loginText)
    TextView loginText;
    @Bind(R.id.createSketch)
    TextView createSketch;
    @Bind(R.id.sendDynamic)
    TextView sendDynamic;
    @Bind(R.id.love)
    TextView love;
    @Bind(R.id.mydynamic)
    TextView mydynamic;
    @Bind(R.id.mysketch)
    TextView mysketch;
    @Bind(R.id.guanyu)
    TextView guanyu;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    private final String LOGINUSER = "loginuser";
    private UserLocal mUserLocal;

    private UserFragmentPresenter mUserFragmentPresenter;
    private final int REQUEST_CODE = 0x01;

    private UserModel mUserModel = new UserModel();

    private Dialog mLoadingDialog;
    private Dialog mLoadingFinishDialog;
    private AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUserLocal = mUserModel.getUserLocal();
        View v = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.bind(this, v);
        EventBus.getDefault().register(this);
        mUserFragmentPresenter = new UserFragmentPresenter();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_empty_dish)
                .showImageForEmptyUri(R.drawable.ic_empty_dish)
                .showImageOnFail(R.drawable.ic_empty_dish).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        if (mUserLocal != null) {
            imageLoader.displayImage(mUserLocal.getPhoto(), UserPhoto, options);
            loginText.setText(mUserLocal.getName());
        }
        mLoadingDialog = DialogBuilder.createLoadingDialog(getActivity(), "正在上传图片");
        mLoadingFinishDialog = DialogBuilder.createLoadingfinishDialog(getActivity(), "上传完成");
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.UserPhoto, R.id.loginText, R.id.createSketch, R.id.sendDynamic, R.id.love, R.id.mydynamic, R.id.mysketch, R.id.guanyu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UserPhoto:
                if (mUserModel.isLogin()) {
                    final PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                    intent.setPhotoCount(1);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.loginText:
                if (!mUserModel.isLogin()) {
                    mUserFragmentPresenter.onLogin(getActivity());
                }else{
                    showDialog();
                }
                break;
            case R.id.createSketch:
                if (mUserModel.isLogin()) {
                    mUserModel.getUser(mUserLocal.getObjectId(), new NoteModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            mUserFragmentPresenter.onCreateSketch(getActivity(), (User) o);
                        }

                        @Override
                        public void getFailure() {

                        }
                    });
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.sendDynamic:
                if (mUserModel.isLogin()) {
                    mUserModel.getUser(mUserLocal.getObjectId(), new NoteModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            mUserFragmentPresenter.onSendDynamic(getActivity(), (User) o);
                        }

                        @Override
                        public void getFailure() {

                        }
                    });
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.love:
                ToastUtils.showLong(getActivity(),"尚未开发，敬请期待");
                //startActivity(new Intent(getActivity(), FoodLoveActivity.class));
                break;
            case R.id.mydynamic:
                if (mUserModel.isLogin()) {
                    startActivity(new Intent(getActivity(), MyDynamicActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.mysketch:
                if (mUserModel.isLogin()) {
                    startActivity(new Intent(getActivity(), MySketchActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.guanyu:

                break;
        }
    }

    /**
     * Eventbus的处理函数
     *
     * @param event
     */
    public void onEventMainThread(UserEventBus event) {
        mUserLocal = event.getmUser();
        if (mUserLocal != null) {
            if (event.getmUser().getPhoto() != null) {
                imageLoader.displayImage(event.getmUser().getPhoto(), UserPhoto, options);
            }
            loginText.setText(event.getmUser().getName());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选择结果回调
        if (requestCode == REQUEST_CODE && data != null) {
            mLoadingDialog.show();
            List<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            mUserModel.updateUserPhoto(pathList.get(0), mUserLocal.getObjectId(), new NoteModelImpl.BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    mLoadingDialog.dismiss();
                    mLoadingFinishDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingFinishDialog.dismiss();
                        }
                    }, 500);
                    ToastUtils.showLong(getActivity(), "头像修改成功");
                    User user = (User) o;
                    imageLoader.displayImage(user.getPhoto().getUrl(), UserPhoto, options);
                    UserLocal userLocal = new UserLocal();
                    userLocal.setName(user.getName());
                    userLocal.setObjectId(user.getObjectId());
                    userLocal.setNumber(user.getNumber());
                    if (user.getPhoto() != null) {
                        userLocal.setPhoto(user.getPhoto().getUrl());
                    }
                    mUserModel.putUserLocal(userLocal);
                }

                @Override
                public void getFailure() {

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("确定退出用户吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserLocal.delete();
                imageLoader.clearDiscCache();
                imageLoader.clearMemoryCache();
                imageLoader.displayImage(null, UserPhoto, options);
                loginText.setText("点击登陆");
               /* Intent intent = new Intent("logout");
                intent.putExtra("change", "yes");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*/
                startActivity(new Intent(getActivity(),HomeActivity.class));
            }
        });
        builder.show();
    }
}
