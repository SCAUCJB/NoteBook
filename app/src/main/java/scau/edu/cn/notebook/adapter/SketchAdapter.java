package scau.edu.cn.notebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.customview.RoundImageView;
import scau.edu.cn.notebook.entity.SketchItem;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.UserModel;

/**
 * 作用：手绘的adapter
 */
public class SketchAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<SketchItem> mDatas;
    private int mLayoutRes;
    private Context mContext;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public SketchAdapter(Context context, int layoutRes, List<SketchItem> datas) {
        this.mContext=context;
        this.mDatas = datas;
        this.mLayoutRes = layoutRes;
        mInflater = LayoutInflater.from(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }


    public List<SketchItem> returnmDatas() {
        return this.mDatas;
    }

    public void addAll(List<SketchItem> mDatas) {
        this.mDatas.addAll(mDatas);
    }

    public void setDatas(List<SketchItem> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutRes, null);
            holder = new ViewHolder();
            holder.write_photo = (RoundImageView) convertView.findViewById(R.id.write_photo);
            holder.write_name = (TextView) convertView.findViewById(R.id.write_name);
            holder.write_date = (TextView) convertView.findViewById(R.id.write_date);
            holder.sketch = (ImageView) convertView.findViewById(R.id.sketch);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SketchItem sketchItem = mDatas.get(position);
        final ViewHolder viewHolder = holder;
        new UserModel().getUser(sketchItem.getWriter().getObjectId(), new NoteModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                //added by cjb
                if(user.getPhoto()!=null)
                imageLoader.displayImage(user.getPhoto().getUrl(), viewHolder.write_photo, options);
                viewHolder.write_name.setText(user.getName());
            }

            @Override
            public void getFailure() {

            }
        });
        viewHolder.write_date.setText(sketchItem.getCreatedAt());
        //holder.sketch.setImageBitmap(BitmapFactory.decodeFile(sketchItem.getSketch().getLocalFile().getAbsolutePath()));
        //holder.sketch.setAdapter(new DynamicPhotoAdapter(mContext,R.layout.dynamic_gridview_item,dynamicItem.getPhotoList()));
        if(sketchItem.getSketch()!=null)
        imageLoader.displayImage(sketchItem.getSketch().getUrl(),viewHolder.sketch, options);
        return convertView;
    }

    private final class ViewHolder {
        RoundImageView write_photo;
        TextView write_name;
        TextView write_date;
        ImageView sketch;
    }
}
