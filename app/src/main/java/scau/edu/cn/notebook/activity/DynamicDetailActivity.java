package scau.edu.cn.notebook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.adapter.DynamicPhotoAdapter;
import scau.edu.cn.notebook.customview.FixedListView;
import scau.edu.cn.notebook.customview.RoundImageView;
import scau.edu.cn.notebook.entity.DynamicItem;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.UserModel;

/**
 * 作用：朋友圈详细
 */
public class DynamicDetailActivity extends Activity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.write_photo)
    RoundImageView writePhoto;
    @Bind(R.id.write_name)
    TextView writeName;
    @Bind(R.id.write_date)
    TextView writeDate;
    @Bind(R.id.dynamic_text)
    TextView dynamicText;
    @Bind(R.id.dynamic_photo)
    FixedListView dynamicPhoto;


    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dynamicdetail_activity_main);
        ButterKnife.bind(this);
        imageLoader.init(ImageLoaderConfiguration.createDefault(DynamicDetailActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        title.setText("详情");
        DynamicItem dynamicItem = (DynamicItem) getIntent().getSerializableExtra("DYNAMIC");
        new UserModel().getUser(dynamicItem.getWriter().getObjectId(), new NoteModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                //added by cjb
                if(user.getPhoto()!=null)
                imageLoader.displayImage(user.getPhoto().getUrl(), writePhoto, options);
                writeName.setText(user.getName());
            }

            @Override
            public void getFailure() {

            }
        });
        writeDate.setText(dynamicItem.getCreatedAt());
        dynamicText.setText(dynamicItem.getText());
        dynamicPhoto.setAdapter(new DynamicPhotoAdapter(DynamicDetailActivity.this, R.layout.dynamicdetail_listview_item, dynamicItem.getPhotoList()));
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
