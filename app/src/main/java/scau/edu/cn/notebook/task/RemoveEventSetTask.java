package scau.edu.cn.notebook.task;

import android.content.Context;

import scau.edu.cn.notebook.dao.EventSetDao;
import scau.edu.cn.notebook.dao.ScheduleDao;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class RemoveEventSetTask extends BaseAsyncTask<Boolean> {

    private int mId;

    public RemoveEventSetTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, int id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao scheduleDao = ScheduleDao.getInstance(mContext);
        scheduleDao.removeScheduleByEventSetId(mId);
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.removeEventSet(mId);
    }
}
