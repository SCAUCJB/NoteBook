package scau.edu.cn.notebook.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 作用：手绘Item
 */
public class SketchItem extends BmobObject implements Serializable{

    public User getWriter() {
        return Writer;
    }

    public void setWriter(User writer) {
        Writer = writer;
    }

    public User Writer;

    //作者上传图片集合
    public BmobFile Sketch;

    public void setSketch(BmobFile sketch) {
         Sketch = sketch;
    }

    public BmobFile getSketch() {
        return Sketch;
    }


}
