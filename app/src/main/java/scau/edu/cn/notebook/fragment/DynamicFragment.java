package scau.edu.cn.notebook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.maxwin.view.XListView;
import scau.edu.cn.notebook.R;
import scau.edu.cn.notebook.activity.DynamicDetailActivity;
import scau.edu.cn.notebook.activity.LoginActivity;
import scau.edu.cn.notebook.activity.SendDynamicActivity;
import scau.edu.cn.notebook.adapter.DynamicAdapter;
import scau.edu.cn.notebook.entity.DynamicItem;
import scau.edu.cn.notebook.entity.User;
import scau.edu.cn.notebook.impl.NoteModelImpl;
import scau.edu.cn.notebook.model.UserModel;
import scau.edu.cn.notebook.presenter.DynamicFragmentPresenter;
import scau.edu.cn.notebook.utils.NetUtil;
import scau.edu.cn.notebook.view.IDynamicFragment;

/**
 * 作用：朋友圈fragment
 */
public class DynamicFragment extends Fragment implements IDynamicFragment, XListView.IXListViewListener {
    @Bind(R.id.publish)
    ImageView publish;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.xListView)
    XListView xListView;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;

    private DynamicFragmentPresenter mPresenter;
    private DynamicAdapter mAdapter;
    private List<DynamicItem> mList = new ArrayList<>();

    private UserModel mUserModel = new UserModel();

    private List<DynamicItem> mDynamicList;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_main, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new DynamicFragmentPresenter(this);
        mAdapter = new DynamicAdapter(getActivity(), R.layout.dynamic_listviewother_item, mList);
        xListView.setAdapter(mAdapter);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(false);
        xListView.setXListViewListener(this);
        mPresenter.onRefresh();
        if (NetUtil.checkNet(getActivity())) {
            mPresenter.onRefresh();
        } else {
            loading.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
            xListView.setVisibility(View.GONE);
        }
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DynamicItem item = mDynamicList.get(position-1);
                Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DYNAMIC", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.publish)
    public void onClick() {
        if (new UserModel().isLogin()) {
            mUserModel.getUser(mUserModel.getUserLocal().getObjectId(), new NoteModelImpl.BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    User user = (User) o;
                    Intent intent = new Intent(getActivity(), SendDynamicActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", user);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }

                @Override
                public void getFailure() {

                }
            });
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onLoadMore(List<DynamicItem> list) {

    }

    @Override
    public void onRefresh(List<DynamicItem> list) {
        mDynamicList = list;
        loading.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        xListView.setVisibility(View.VISIBLE);
        xListView.stopRefresh();
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMore() {

    }
}
