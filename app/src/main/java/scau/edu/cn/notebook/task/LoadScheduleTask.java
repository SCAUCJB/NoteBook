package scau.edu.cn.notebook.task;

import android.content.Context;

import java.util.List;

import scau.edu.cn.notebook.dao.ScheduleDao;
import scau.edu.cn.notebook.entity.Schedule;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class LoadScheduleTask extends BaseAsyncTask<List<Schedule>> {

    //edited by cjb
    private String mUsernumber;
    //
    private int mYear;
    private int mMonth;
    private int mDay;

    public LoadScheduleTask(Context context, OnTaskFinishedListener<List<Schedule>> onTaskFinishedListener, String usernumber, int year, int month, int day) {
        super(context, onTaskFinishedListener);
        mUsernumber = usernumber;
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    @Override
    protected List<Schedule> doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.getScheduleByDate(mUsernumber,mYear, mMonth,mDay);
    }
}
