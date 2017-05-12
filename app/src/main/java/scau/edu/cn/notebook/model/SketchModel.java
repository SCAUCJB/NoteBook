package scau.edu.cn.notebook.model;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import scau.edu.cn.notebook.application.BaseApplication;
import scau.edu.cn.notebook.entity.SketchItem;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.impl.NoteModelImpl;


/**
 * 作用：对手帐数据操作的model
 */
public class SketchModel  {



    /**
     * 获取当前用户的所有手绘
     *
     * @param user
     * @param listener
     */
    public void getSketchItemByPhone(User user, final NoteModelImpl.BaseListener listener) {
        BmobQuery<SketchItem> query = new BmobQuery<SketchItem>();
        query.addWhereEqualTo("Writer", user);
        query.findObjects(BaseApplication.getmContext(), new FindListener<SketchItem>() {
            @Override
            public void onSuccess(List<SketchItem> object) {
                if (object != null && object.size() != 0) {
                    listener.getSuccess(object);
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }


    /**
     * 上传手绘
     *
     * @param sketchitem
     */
    public void sendSketchItem(final SketchItem sketchitem, final NoteModelImpl.BaseListener listener) {
        String path = sketchitem.getSketch().getLocalFile().getAbsolutePath();
        if (path != null) {
                Log.i("path", "sendSketchItem: " + path + " " + sketchitem.getSketch());

        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(BaseApplication.getmContext(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                //final SketchItem sketchItem = new SketchItem();
                sketchitem.setSketch(bmobFile);
                sketchitem.save(BaseApplication.getmContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        listener.getSuccess(sketchitem);
                        Toast.makeText(BaseApplication.getmContext(), "上传成功", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        listener.getFailure();
                        Toast.makeText(BaseApplication.getmContext(), "上传失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                listener.getFailure();
            }
        });
        } else {
            Toast.makeText(BaseApplication.getmContext(), "获取不到图片链接", Toast.LENGTH_LONG).show();

        }
    }




}
