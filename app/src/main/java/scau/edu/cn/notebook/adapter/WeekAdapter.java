package scau.edu.cn.notebook.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import scau.edu.cn.notebook.view.WeekCalendarView;
import scau.edu.cn.notebook.view.WeekView;

public class WeekAdapter extends PagerAdapter {

    private SparseArray<WeekView> mViews;
    private Context mContext;
    private TypedArray mArray;
    private WeekCalendarView mWeekCalendarView;
    private DateTime mStartDate;
    private int mWeekCount = 220;

    public WeekAdapter(Context context, TypedArray array, WeekCalendarView weekCalendarView) {
        mContext = context;
        mArray = array;
        mWeekCalendarView = weekCalendarView;
        mViews = new SparseArray<>();
        initStartDate();
    }

    private void initStartDate() {
        mStartDate = new DateTime();
        mStartDate = mStartDate.plusDays(-mStartDate.getDayOfWeek() % 7);
    }

    @Override
    public int getCount() {
        return mWeekCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews.get(position) == null) {
            instanceWeekView(position);
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.remove(position);
    }

    public SparseArray<WeekView> getViews() {
        return mViews;
    }

    public int getWeekCount() {
        return mWeekCount;
    }

    public WeekView instanceWeekView(int position) {
        WeekView weekView = new WeekView(mContext, mArray, mStartDate.plusWeeks(position - mWeekCount / 2));
        weekView.setId(position);
        weekView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        weekView.setOnWeekClickListener(mWeekCalendarView);
        weekView.invalidate();
        mViews.put(position, weekView);
        return weekView;
    }

}
