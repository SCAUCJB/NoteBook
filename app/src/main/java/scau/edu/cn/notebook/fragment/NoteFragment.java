package scau.edu.cn.notebook.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Date;
import java.util.UUID;

import scau.edu.cn.notebook.entity.Note;
import scau.edu.cn.notebook.NoteLab;
import scau.edu.cn.notebook.entity.Photo;
import scau.edu.cn.notebook.utils.PictureUtils;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.activity.NoteCameraActivity;
import scau.edu.cn.notebook.activity.NotePagerActivity;


public class NoteFragment extends Fragment {
    public static final String TAG = "NoteFragment";
    public static final String EXTRA_NOTE_ID = ".note_id";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_IMAGE = "image";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_PHOTO = 1;
    private Note mNote;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mContent;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    public static NoteFragment newInstance(UUID crimeID){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NOTE_ID,crimeID);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID noteID = (UUID)getArguments().getSerializable(EXTRA_NOTE_ID);
        mNote = NoteLab.get(getActivity()).getNote(noteID);
        setHasOptionsMenu(true);
    }

    public void updateDate(){
        mDateButton.setText(mNote.getmDate().toString());
    }

    @TargetApi(11)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note,container,false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity())!=null) {
                ((NotePagerActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField = (EditText)v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.note_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mNote.getmDate());
                dialog.setTargetFragment(NoteFragment.this,REQUEST_DATE);
                dialog.show(fm,DIALOG_DATE);
            }
        });
        mContent = (EditText)v.findViewById(R.id.note_content);
        mContent.setText(mNote.getmContent());
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.setmContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mPhotoButton = (ImageButton)v.findViewById(R.id.note_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),NoteCameraActivity.class);
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView)v.findViewById(R.id.note_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p = mNote.getmPhoto();
                if(p == null)
                    return;

                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm,DIALOG_IMAGE);
            }
        });

        registerForContextMenu(mPhotoView);

        return v;
    }

    private void showPhoto(){
        Photo p = mNote.getmPhoto();
        BitmapDrawable b = null;
        if(p != null){
            String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
            b = PictureUtils.getScaleDrawable(getActivity(), path);
        }
        mPhotoView.setImageDrawable(b);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode != Activity.RESULT_OK)return;
        if(requestCode == REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mNote.setmDate(date);
            updateDate();
        }else if(requestCode == REQUEST_PHOTO){
            String filename = data.getStringExtra(NoteCameraFragment.EXTRA_PHOTO_FILENAME);
            if(filename != null){
                //Log.i(TAG,"filename: "+ filename);
                Photo p = new Photo(filename);
                mNote.setmPhoto(p);
                //Log.i(TAG,"Note: "+mNote.getmTitle() + " has a photo");
                showPhoto();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity())!=null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        NoteLab.get(getActivity()).saveNotes();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.note_photo_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_note_photo:
                mNote.deletePhoto();
                showPhoto();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
