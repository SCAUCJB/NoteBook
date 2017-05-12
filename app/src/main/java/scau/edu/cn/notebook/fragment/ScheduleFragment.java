package scau.edu.cn.notebook.fragment;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.adapter.ScheduleAdapter;
import scau.edu.cn.notebook.dialog.SelectDateDialog;
import scau.edu.cn.notebook.entity.Schedule;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.entity.UserLocal;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.layout.ScheduleLayout;
import scau.edu.cn.notebook.listener.OnCalendarClickListener;
import scau.edu.cn.notebook.listener.OnDragFinishedListener;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;
import scau.edu.cn.notebook.model.UserModel;
import scau.edu.cn.notebook.task.AddScheduleTask;
import scau.edu.cn.notebook.task.LoadScheduleTask;
import scau.edu.cn.notebook.utils.DeviceUtils;
import scau.edu.cn.notebook.utils.ToastUtils;
import scau.edu.cn.notebook.view.ScheduleRecyclerView;


public class ScheduleFragment extends BaseFragment implements OnCalendarClickListener, View.OnClickListener,
        OnTaskFinishedListener<List<Schedule>>, SelectDateDialog.OnSelectDateListener, OnDragFinishedListener {

    private ScheduleLayout slSchedule;
    private ScheduleRecyclerView rvScheduleList;
    private EditText etInputContent;
    private RelativeLayout rLNoTask;
    private RelativeLayout rLUnlogined;
    private ImageButton iBMainClock, iBMainOk;

    private LinearLayout llTitleDate;
    private TextView tvTitleMonth, tvTitleDay;

    private String mCurrentUserNumber;
    private ScheduleAdapter mScheduleAdapter;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;
    private long mTime;

    private String[] mMonthText;

    private UserLocal mUserLocal;
    private UserModel mUserModel = new UserModel();

    private LocalBroadcastManager broadcastManager;

    public static ScheduleFragment getInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mUserLocal = mUserModel.getUserLocal();
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    protected void bindView() {
        slSchedule = searchViewById(R.id.slSchedule);
        etInputContent = searchViewById(R.id.etInputContent);
        etInputContent.setVisibility(mUserLocal != null ? View.VISIBLE : View.GONE);
        rLNoTask = searchViewById(R.id.rlNoTask);
        iBMainClock = searchViewById(R.id.ibMainClock);
        iBMainClock.setOnClickListener(this);
        iBMainClock.setVisibility(mUserLocal != null ? View.VISIBLE : View.GONE);
        iBMainOk = searchViewById(R.id.ibMainOk);
        iBMainOk.setOnClickListener(this);
        iBMainOk.setVisibility(mUserLocal != null ? View.VISIBLE : View.GONE);
        slSchedule.setOnCalendarClickListener(this);
        llTitleDate = searchViewById(R.id.llTitleDate);
        tvTitleMonth = searchViewById(R.id.tvTitleMonth);
        tvTitleDay = searchViewById(R.id.tvTitleDay);
        llTitleDate.setVisibility(View.VISIBLE);
        initScheduleList();
        rvScheduleList.setVisibility(mUserLocal != null ? View.VISIBLE : View.GONE);
        initUi();
        initBottomInputBar();
    }

    private void initUi() {
        mMonthText = getResources().getStringArray(R.array.calendar_month);
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitleMonth.setText(mMonthText[Calendar.getInstance().get(Calendar.MONTH)]);
        tvTitleDay.setText(getString(R.string.calendar_today));
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
    }

    @Override
    protected void initData() {
        super.initData();
        initDate();

    }

    @Override
    protected void bindData() {
        super.bindData();
        mCurrentUserNumber = getCurrentUserNumber();
        if(mCurrentUserNumber != null){
            resetScheduleList();
        }

    }

    public void resetScheduleList() {
        new LoadScheduleTask(mActivity, this, mCurrentUserNumber, mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        setCurrentSelectDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClickDate(int year, int month, int day) {
        setCurrentSelectDate(year, month, day);
        if(mCurrentUserNumber != null){
            resetScheduleList();
        }
    }

    private void initScheduleList() {
        rvScheduleList = slSchedule.getSchedulerRecyclerView();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvScheduleList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvScheduleList.setItemAnimator(itemAnimator);
        mScheduleAdapter = new ScheduleAdapter(mActivity, this);
        rvScheduleList.setAdapter(mScheduleAdapter);
    }

    private void initBottomInputBar() {
        etInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etInputContent.setGravity(s.length() == 0 ? Gravity.CENTER : Gravity.CENTER_VERTICAL);
            }
        });
        etInputContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
    }

    public void resetMainTitleDate(int year, int month, int day) {
        llTitleDate.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(Calendar.YEAR) &&
                month == calendar.get(Calendar.MONTH) &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            tvTitleMonth.setText(mMonthText[month]);
            tvTitleDay.setText(getString(R.string.calendar_today));
        } else {
            if (year == calendar.get(Calendar.YEAR)) {
                tvTitleMonth.setText(mMonthText[month]);
            } else {
                tvTitleMonth.setText(String.format("%s%s", String.format(getString(R.string.calendar_year), year),
                        mMonthText[month]));
            }
            tvTitleDay.setText(String.format(getString(R.string.calendar_day), day));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMainClock:
                showSelectDateDialog();
                break;
            case R.id.ibMainOk:
                addSchedule();
                break;
        }
    }

    private void showSelectDateDialog() {
        new SelectDateDialog(mActivity, this, mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay, slSchedule.getMonthCalendar().getCurrentItem()).show();
    }

    private void closeSoftInput() {
        etInputContent.clearFocus();
        DeviceUtils.closeSoftInput(mActivity, etInputContent);
    }

    private void addSchedule() {
        String content = etInputContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort(mActivity, R.string.schedule_input_content_is_no_null);
        } else {
            closeSoftInput();
            final Schedule schedule = new Schedule();
            //edited by cjb
            schedule.setUser_number(mCurrentUserNumber);

            schedule.setTitle(content);
            schedule.setState(0);
            schedule.setTime(mTime);
            schedule.setYear(mCurrentSelectYear);
            schedule.setMonth(mCurrentSelectMonth);
            schedule.setDay(mCurrentSelectDay);
            new AddScheduleTask(mActivity, new OnTaskFinishedListener<Schedule>() {
                @Override
                public void onTaskFinished(Schedule data) {
                    if (data != null) {
                        mScheduleAdapter.insertItem(data);
                        etInputContent.getText().clear();
                        rLNoTask.setVisibility(View.GONE);
                        mTime = 0;
                        updateTaskHintUi(mScheduleAdapter.getItemCount() - 2);
                    }
                }
            }, schedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month;
        mCurrentSelectDay = day;
        resetMainTitleDate(year, month, day);
    }

    //edited by cjb
    private String getCurrentUserNumber() {
        if(mUserModel.isLogin()){
            mUserModel.getUser(mUserLocal.getObjectId(), new NoteModelImpl.BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    mCurrentUserNumber = ((User) o).getNumber();
                }

                @Override
                public void getFailure() {

                }
            });
        }
        return mCurrentUserNumber;
    }

    @Override
    public void onTaskFinished(List<Schedule> data) {
        mScheduleAdapter.changeAllData(data);
        rLNoTask.setVisibility(data.size() == 0 ? View.VISIBLE : View.GONE);
        updateTaskHintUi(data.size());

    }

    private void updateTaskHintUi(int size) {
        if (size == 0) {
            if (slSchedule.getMonthCalendar().getCurrentMonthView() != null) {
                slSchedule.getMonthCalendar().getCurrentMonthView().removeTaskHint(mCurrentSelectDay);
            }
            if (slSchedule.getWeekCalendar().getCurrentWeekView() != null) {
                slSchedule.getWeekCalendar().getCurrentWeekView().removeTaskHint(mCurrentSelectDay);
            }
        } else {
            if (slSchedule.getMonthCalendar().getCurrentMonthView() != null) {
                slSchedule.getMonthCalendar().getCurrentMonthView().addTaskHint(mCurrentSelectDay);
            }
            if (slSchedule.getWeekCalendar().getCurrentWeekView() != null) {
                slSchedule.getWeekCalendar().getCurrentWeekView().addTaskHint(mCurrentSelectDay);
            }
        }
    }

    @Override
    public void onSelectDate(final int year, final int month, final int day, long time, int position) {
        slSchedule.getMonthCalendar().setCurrentItem(position);
        slSchedule.postDelayed(new Runnable() {
            @Override
            public void run() {
                slSchedule.getMonthCalendar().getCurrentMonthView().clickThisMonth(year, month, day);
            }
        }, 100);
        mTime = time;
    }

    public int getCurrentCalendarPosition() {
        return slSchedule.getMonthCalendar().getCurrentItem();
    }

    @Override
    public void onSelectDisplayBoard(int x, int y) {
        ToastUtils.showShort(mActivity, "x:" + x + ";y:" + y);
    }

    @Override
    public void onSelectTopBarRightButton() {
        ToastUtils.showShort(mActivity, "Delete");
    }

    @Override
    public void onSelectOther() {
        ToastUtils.showShort(mActivity, "Other");
    }

   /* @Override
    public void onDestroyView() {
        if(this.getView() != null)
        ((ViewGroup)this.getView().getParent()).removeView(this.getView());
        super.onDestroyView();
    }*/
    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){

        }else{

        }
    }*/

    /**
     * 注册广播接收器
     */
  /*  private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("logout");
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }

    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String change = intent.getStringExtra("change");
            if ("yes".equals(change)) {
                new Handler().post(new Runnable() {
                    public void run() {
                        synchronized(getView()){
                            getView().notify();
                        }
                    }
                });
            }
        }
    };
*/
    /**
     * 注销广播
     */
  /*  @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mAdDownLoadReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        registerReceiver();

    }*/
}
