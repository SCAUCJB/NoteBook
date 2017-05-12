package scau.edu.cn.notebook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import scau.edu.cn.notebook.entity.Note;
import scau.edu.cn.notebook.NoteLab;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.activity.NotePagerActivity;
import scau.edu.cn.notebook.activity.SingleFragmentActivity;

/**
 * Created by CJB on 2016/11/7.
 */

public class NoteListFragment extends ListFragment {
    private ArrayList<Note> mNotes;
    private boolean mSubtitleVisible;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.notes_title);
        mNotes = NoteLab.get(getActivity()).getmNotes();

        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);

        setRetainInstance(true);
        mSubtitleVisible = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mSubtitleVisible){
            ((SingleFragmentActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
        }
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.note_list_item_context,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_delete_note:
                        NoteAdapter adapter = (NoteAdapter)getListAdapter();
                        NoteLab noteLab = NoteLab.get(getActivity());
                        for(int i = adapter.getCount() - 1; i >= 0; i--){
                            if(getListView().isItemChecked(i)){
                                noteLab.deleteNote(adapter.getItem(i));
                            }
                        }
                        mode.finish();
                        adapter.notifyDataSetChanged();
                        noteLab.saveNotes();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Note n = ((NoteAdapter)getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(),NotePagerActivity.class);
        i.putExtra(NoteFragment.EXTRA_NOTE_ID,n.getmId());
        startActivity(i);
    }

    private class NoteAdapter extends ArrayAdapter<Note>{
        public NoteAdapter(ArrayList<Note> notes){
            super(getActivity(),0,notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_note,null);
            }

            Note n = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.note_list_titleTextView);
            titleTextView.setText(n.getmTitle());
            TextView dateTextView = (TextView)convertView.findViewById(R.id.note_list_dateTextView);
            dateTextView.setText(n.getmDate().toString());

            return convertView;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_note_list,menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible && showSubtitle != null){
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_note:
                Note note = new Note();
                NoteLab.get(getActivity()).addNote(note);
                Intent i = new Intent(getActivity(),NotePagerActivity.class);
                i.putExtra(NoteFragment.EXTRA_NOTE_ID,note.getmId());
                startActivityForResult(i,0);
                return true;
            case R.id.menu_item_show_subtitle:
                if(((SingleFragmentActivity)getActivity()).getSupportActionBar().getSubtitle() == null){
                    ((SingleFragmentActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                }else {
                    ((SingleFragmentActivity)getActivity()).getSupportActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
