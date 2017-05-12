package scau.edu.cn.notebook.task;

import android.content.Context;

import scau.edu.cn.notebook.dao.ScheduleDao;
import scau.edu.cn.notebook.entity.Schedule;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;


public class AddScheduleTask extends BaseAsyncTask<Schedule> {

    private Schedule mSchedule;

    public AddScheduleTask(Context context, OnTaskFinishedListener<Schedule> onTaskFinishedListener, Schedule schedule) {
        super(context, onTaskFinishedListener);
        mSchedule = schedule;
    }

    @Override
    protected Schedule doInBackground(Void... params) {
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            int id = dao.addSchedule(mSchedule);
            if (id != 0) {
                mSchedule.setId(id);
                return mSchedule;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
