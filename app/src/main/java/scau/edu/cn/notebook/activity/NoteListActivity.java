package scau.edu.cn.notebook.activity;

import android.support.v4.app.ListFragment;

import scau.edu.cn.notebook.fragment.NoteListFragment;

/**
 * Created by CJB on 2016/11/7.
 */

public class NoteListActivity extends SingleFragmentActivity {

    @Override
    protected ListFragment createFragment(){
        return new NoteListFragment();
    }

}

