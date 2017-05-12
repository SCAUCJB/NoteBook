package scau.edu.cn.notebook.presenter;


import java.util.List;

import scau.edu.cn.notebook.entity.DynamicItem;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.DynamicModel;
import scau.edu.cn.notebook.view.IDynamicFragment;

/**
 * 作用：朋友圈的Presenter
 */
public class DynamicFragmentPresenter {
    private DynamicModel mDynamicModel = new DynamicModel();
    private IDynamicFragment mView;

    public DynamicFragmentPresenter(IDynamicFragment mView) {
        this.mView = mView;
    }

    public void onRefresh(){
        mDynamicModel.getDynamicItem(new NoteModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                List<DynamicItem> list= (List<DynamicItem>) o;
                mView.onRefresh(list);
            }

            @Override
            public void getFailure() {
            }
        });
    }

    public void onLoadMore(){

    }

}
