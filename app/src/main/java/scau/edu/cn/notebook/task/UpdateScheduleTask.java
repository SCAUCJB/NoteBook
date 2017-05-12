package scau.edu.cn.notebook.task;

import android.content.Context;

import scau.edu.cn.notebook.dao.ScheduleDao;
import scau.edu.cn.notebook.entity.Schedule;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class UpdateScheduleTask extends BaseAsyncTask<Boolean> {

    private Schedule mSchedule;

    public UpdateScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, Schedule schedule) {
        super(context, onTaskFinishedListener);
        mSchedule = schedule;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            return dao.updateSchedule(mSchedule);
        } else {
            return false;
        }
    }
}
