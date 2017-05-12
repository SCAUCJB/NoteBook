package scau.edu.cn.notebook.task;

import android.content.Context;

import java.util.Map;

import scau.edu.cn.notebook.dao.EventSetDao;
import scau.edu.cn.notebook.entity.EventSet;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class LoadEventSetMapTask extends BaseAsyncTask<Map<Integer, EventSet>> {

    private Context mContext;

    public LoadEventSetMapTask(Context context, OnTaskFinishedListener<Map<Integer, EventSet>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected Map<Integer, EventSet> doInBackground(Void... params) {
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.getAllEventSetMap();
    }

}
