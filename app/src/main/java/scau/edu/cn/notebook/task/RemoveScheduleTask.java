package scau.edu.cn.notebook.task;

import android.content.Context;

import scau.edu.cn.notebook.dao.ScheduleDao;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class RemoveScheduleTask extends BaseAsyncTask<Boolean> {

    private long mId;

    public RemoveScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, long id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.removeSchedule(mId);
    }
}
