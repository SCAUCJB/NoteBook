package scau.edu.cn.notebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.smssdk.SMSSDK;
import scau.edu.cn.notebook.R;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends Activity {
    static final String TAG = "ss";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity_main);
        //设置BmobConfig：允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey(必填)
                .setApplicationId("a67d34b9861a024e7a89ac8716526e39")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(5500)
                .build();
        Bmob.initialize(config);
        SMSSDK.initSDK(this, "1b1d757a6defe", "1dada909f86c58d79f171f8e983394d1");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                Log.i(TAG,"success!");
                finish();
            }
        }, 2000);
    }
}
