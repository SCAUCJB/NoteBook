package scau.edu.cn.notebook;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import scau.edu.cn.notebook.entity.Note;

/**
 * Created by CJB on 2016/11/7.
 */

public class NoteLab {
    private static final String TAG = "NoteLab";
    private static final String FILENAME = "notes.json";

    private ArrayList<Note> mNotes;
    private NoteIntentJSONSerializer mSerializer;

    private static NoteLab sNoteLab;
    private Context mAppcontext;

    private NoteLab(Context appContext){
        mAppcontext = appContext;
        mSerializer = new NoteIntentJSONSerializer(mAppcontext,FILENAME);

        try {
            mNotes = mSerializer.LoadNotes();
        }catch (Exception e){
            mNotes = new ArrayList<>();
            Log.e(TAG,"Error loading notes: ",e);
        }
    }

    public void addNote(Note n){
        mNotes.add(n);
    }

    public void deleteNote(Note n){
        mNotes.remove(n);
    }

    public boolean saveNotes(){
        try{
            mSerializer.saveNotes(mNotes);
            Log.d(TAG,"notes saved to file");
            return  true;
        }catch (Exception e){
            Log.e(TAG,"Error saving notes: ",e);
            return false;
        }
    }

    public static NoteLab get(Context c){
        if(sNoteLab == null){
            sNoteLab = new NoteLab(c.getApplicationContext());
        }
        return sNoteLab;
    }

    public ArrayList<Note> getmNotes() {
        return mNotes;
    }

    public Note getNote(UUID id){
        for (Note n : mNotes){
            if(n.getmId().equals(id))
                return n;
        }
        return null;
    }
}
