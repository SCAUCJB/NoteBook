package scau.edu.cn.notebook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.DeleteListener;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.adapter.DynamicAdapter;
import scau.edu.cn.notebook.application.BaseApplication;
import scau.edu.cn.notebook.entity.DynamicItem;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.DynamicModel;
import scau.edu.cn.notebook.model.UserModel;

/**
 * 作用：我的动态
 */
public class MyDynamicActivity extends Activity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listview)
    ListView listview;

    private UserModel mUserModel = new UserModel();
    private DynamicModel mDynamicModel = new DynamicModel();
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mynote_activity_main);
        ButterKnife.bind(this);
        title.setText("我的动态");
        Log.i("TAG", "getSuccess: ");
        Log.i("TAG", "getSuccess: "+mUserModel.getUserLocal().getObjectId());
        mUserModel.getUser(mUserModel.getUserLocal().getObjectId(), new NoteModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                mDynamicModel.getDynamicItemByPhone(user, new NoteModelImpl.BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        //final List<DynamicItem> list = (List<DynamicItem>) o;
                        list = (List<DynamicItem>) o;
                        listview.setAdapter(new DynamicAdapter(MyDynamicActivity.this, R.layout.dynamic_listviewother_item, list));
                        //registerForContextMenu(listview);

                        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
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
                               /* View view = mode.getCustomView();
                                CheckBox cb = null;
                                if(view != null){
                                     cb = (CheckBox)view.findViewById(R.id.check);
                                    //cb.setChecked(true);
                                    cb.setVisibility(VISIBLE);
                                }*/
                                switch (item.getItemId()) {
                                    case R.id.menu_item_delete_note:
                                        Adapter adapter = listview.getAdapter();
                                        for(int i = adapter.getCount() - 1; i >= 0; i--){
                                            if(listview.isItemChecked(i)){
                                                final DynamicItem dynamicItem = (DynamicItem) adapter.getItem(i);
                                                dynamicItem.delete(BaseApplication.getmContext(), new DeleteListener() {
                                                    @Override
                                                    public void onSuccess() {
                                                        list.remove(dynamicItem);
                                                    }

                                                    @Override
                                                    public void onFailure(int i, String s) {

                                                    }
                                                });

                                            }
                                        }
                                        mode.finish();
                                        return true;
                                    default:
                                        return false;
                                }
                            }

                            @Override
                            public void onDestroyActionMode(ActionMode mode) {

                            }
                        });
                    }

                    @Override
                    public void getFailure() {

                    }
                });


            }

            @Override
            public void getFailure() {

            }
        });



    }

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        this.getMenuInflater().inflate(R.menu.note_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete_note:
                Adapter adapter = listview.getAdapter();
                for(int i = adapter.getCount() - 1; i >= 0; i--){
                    if(listview.isItemChecked(i)){
                        final DynamicItem dynamicItem = (DynamicItem) adapter.getItem(i);
                        dynamicItem.delete(BaseApplication.getmContext(), new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                list.remove(dynamicItem);
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });

                    }
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }*/

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
