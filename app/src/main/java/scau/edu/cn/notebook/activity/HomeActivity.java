package scau.edu.cn.notebook.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.fragment.DynamicFragment;
import scau.edu.cn.notebook.fragment.ScheduleFragment;
import scau.edu.cn.notebook.fragment.UserFragment;

public class HomeActivity extends FragmentActivity {
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.noteListIv)
    ImageView noteListIv;
    @Bind(R.id.noteListTv)
    TextView noteListTv;
    @Bind(R.id.noteListPage)
    RelativeLayout noteListPage;
    @Bind(R.id.dynamicIv)
    ImageView dynamicIv;
    @Bind(R.id.dynamicTv)
    TextView dynamicTv;
    @Bind(R.id.dynamicPage)
    RelativeLayout dynamicPage;
    @Bind(R.id.personIv)
    ImageView personIv;
    @Bind(R.id.personTv)
    TextView personTv;
    @Bind(R.id.personPage)
    RelativeLayout personPage;

    public FragmentManager fragmentmanager;
    public FragmentTransaction fragmenttransaction;

    private UserFragment mUserFragment;
    //private NoteListFragment mNoteListFragment;
    private ScheduleFragment mScheduleFragment;
    private DynamicFragment mDynamicFragment;

    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    private DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_activity_main);
        ButterKnife.bind(this);
        //BmobUpdateAgent.update(this);
        init();
    }

    public void init() {
        imageLoader.init(ImageLoaderConfiguration.createDefault(HomeActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        fragmentmanager = getSupportFragmentManager();
        fragmenttransaction = fragmentmanager.beginTransaction();
        mScheduleFragment = new ScheduleFragment();
        fragmenttransaction.replace(R.id.content, mScheduleFragment, "SFTag");
        fragmenttransaction.commit();
    }

    @OnClick({R.id.noteListPage, R.id.dynamicPage, R.id.personPage})
    public void onClick(View view) {
        FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
        hideFragments(fragmenttransaction);
        switch (view.getId()) {
            case R.id.noteListPage:
                clearView();
                noteListIv.setImageResource(R.drawable.main_recipe_red);
                noteListTv.setTextColor(Color.parseColor("#FD7575"));
                if (mScheduleFragment != null) {
                    fragmenttransaction.show(mScheduleFragment);
                } else {
                    mScheduleFragment = new ScheduleFragment();
                    fragmenttransaction.add(R.id.content, mScheduleFragment);
                }
                break;

            case R.id.dynamicPage:
                clearView();
                dynamicIv.setImageResource(R.drawable.main_home_red);
                dynamicTv.setTextColor(Color.parseColor("#FD7575"));
                if (mDynamicFragment != null) {
                    fragmenttransaction.show(mDynamicFragment);
                } else {
                    mDynamicFragment = new DynamicFragment();
                    fragmenttransaction.add(R.id.content, mDynamicFragment);
                }
                break;

            case R.id.personPage:
                clearView();
                personIv.setImageResource(R.drawable.main_user_red);
                personTv.setTextColor(Color.parseColor("#FD7575"));
                if (mUserFragment != null) {
                    fragmenttransaction.show(mUserFragment);
                } else {
                    mUserFragment = new UserFragment();
                    fragmenttransaction.add(R.id.content, mUserFragment);
                }
                break;
        }
        fragmenttransaction.commit();
    }

    public void clearView() {
        noteListIv.setImageResource(R.drawable.main_recipe_gray);
        noteListTv.setTextColor(Color.parseColor("#828383"));
        dynamicIv.setImageResource(R.drawable.main_home_gray);
        dynamicTv.setTextColor(Color.parseColor("#828383"));
        personIv.setImageResource(R.drawable.main_user_gray);
        personTv.setTextColor(Color.parseColor("#828383"));
    }


    private void hideFragments(
            android.support.v4.app.FragmentTransaction fragment) {
        if (mScheduleFragment != null) {
            fragment.hide(mScheduleFragment);
        }
        if (mUserFragment != null) {
            fragment.hide(mUserFragment);
        }
        if (mDynamicFragment != null) {
            fragment.hide(mDynamicFragment);
        }
    }

}

