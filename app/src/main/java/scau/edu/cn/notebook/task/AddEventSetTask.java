package scau.edu.cn.notebook.task;

import android.content.Context;

import scau.edu.cn.notebook.dao.EventSetDao;
import scau.edu.cn.notebook.entity.EventSet;
import scau.edu.cn.notebook.listener.OnTaskFinishedListener;

public class AddEventSetTask extends BaseAsyncTask<EventSet> {

    private EventSet mEventSet;

    public AddEventSetTask(Context context, OnTaskFinishedListener<EventSet> onTaskFinishedListener, EventSet eventSet) {
        super(context, onTaskFinishedListener);
        mEventSet = eventSet;
    }

    @Override
    protected EventSet doInBackground(Void... params) {
        if (mEventSet != null) {
            EventSetDao dao = EventSetDao.getInstance(mContext);
            int id = dao.addEventSet(mEventSet);
            if (id != 0) {
                mEventSet.setId(id);
                return mEventSet;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
