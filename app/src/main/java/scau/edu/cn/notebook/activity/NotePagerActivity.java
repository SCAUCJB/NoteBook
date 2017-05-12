package scau.edu.cn.notebook.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

import scau.edu.cn.notebook.entity.Note;
import scau.edu.cn.notebook.fragment.NoteFragment;
import scau.edu.cn.notebook.NoteLab;
import scau.edu.cn.notebook.R;


/**
 * Created by CJB on 2016/3/18.
 */
public class NotePagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ArrayList<Note> mNotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mNotes = NoteLab.get(this).getmNotes();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Note note = mNotes.get(position);
                return NoteFragment.newInstance(note.getmId());
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Note note = mNotes.get(position);
                if(note.getmTitle()!=null){
                    setTitle(note.getmTitle());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        UUID noteId = (UUID)getIntent().getSerializableExtra(NoteFragment.EXTRA_NOTE_ID);
        for(int i=0;i<mNotes.size();i++){
           if(mNotes.get(i).getmId().equals(noteId)){
               mViewPager.setCurrentItem(i);
               break;
           }
        }
    }


}
