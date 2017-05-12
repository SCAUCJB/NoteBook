package scau.edu.cn.notebook.task;

import android.content.Context;

import java.util.List;

import scau.edu.cn.notebook.dao.ScheduleDao;
import scau.edu.cn.notebook.entity.Schedule;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class GetScheduleTask extends BaseAsyncTask<List<Schedule>> {

    private int mId;

    public GetScheduleTask(Context context, OnTaskFinishedListener<List<Schedule>> onTaskFinishedListener, int id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected List<Schedule> doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.getScheduleByEventSetId(mId);
    }

}
