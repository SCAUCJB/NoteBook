package scau.edu.cn.notebook.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import cn.bmob.v3.BmobObject;

/**
 * Created by CJB on 2016/11/7.
 */

public class Note extends BmobObject implements Serializable {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mContent;
    private Photo mPhoto;

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";


    public Note(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Note(JSONObject json)throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        if (json.has(JSON_CONTENT)) {
            mContent = json.getString(JSON_CONTENT);
        }
        mDate = new Date(json.getLong(JSON_DATE));
        if(json.has(JSON_PHOTO)){
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        }
    }

    public JSONObject toJSON()throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID,mId.toString());
        json.put(JSON_TITLE,mTitle);
        json.put(JSON_CONTENT,mContent);
        json.put(JSON_DATE,mDate.getTime());
        if(mPhoto != null)
            json.put(JSON_PHOTO,mPhoto.toJSON());
        return json;
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public Photo getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Photo mPhoto) {
        this.mPhoto = mPhoto;
    }

    public void deletePhoto(){
        this.mPhoto = null;
    }

    @Override
    public String toString() {
        return mTitle;
    }

}
