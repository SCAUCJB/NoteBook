package scau.edu.cn.notebook.eventset;

import android.content.Context;

import java.util.List;

import scau.edu.cn.notebook.dao.EventSetDao;
import scau.edu.cn.notebook.entity.EventSet;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;
import scau.edu.cn.notebook.task.BaseAsyncTask;

public class LoadEventSetTask extends BaseAsyncTask<List<EventSet>> {

    private Context mContext;

    public LoadEventSetTask(Context context, OnTaskFinishedListener<List<EventSet>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected List<EventSet> doInBackground(Void... params) {
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.getAllEventSet();
    }

}
