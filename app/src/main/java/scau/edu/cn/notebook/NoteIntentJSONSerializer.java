package scau.edu.cn.notebook;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import scau.edu.cn.notebook.entity.Note;

/**
 * Created by CJB on 2016/11/14.
 */

public class NoteIntentJSONSerializer {
    private Context mcontext;
    private String mFilename;

    public NoteIntentJSONSerializer(Context c,String f){
        mcontext = c;
        mFilename = f;
    }

    public ArrayList<Note> LoadNotes()throws IOException,JSONException{
        ArrayList<Note> notes = new ArrayList<>();
        BufferedReader reader = null;
        try{
            InputStream in = mcontext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!=null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for(int i =0;i < array.length(); i++){
                notes.add(new Note(array.getJSONObject(i)));
            }
        }catch (FileNotFoundException e){

        }finally {
            if (reader != null)
                reader.close();
        }
        return notes;
    }

    public void saveNotes(ArrayList<Note> notes)throws JSONException,IOException{
        JSONArray array = new JSONArray();
        for (Note n : notes)
            array.put(n.toJSON());

        Writer writer = null;
        try{
            OutputStream out = mcontext.openFileOutput(mFilename,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if (writer!=null)
                writer.close();
        }
    }
}
