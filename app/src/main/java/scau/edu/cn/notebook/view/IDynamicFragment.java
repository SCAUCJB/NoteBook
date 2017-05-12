package scau.edu.cn.notebook.view;


import java.util.List;

import scau.edu.cn.notebook.entity.DynamicItem;

/**
 * 作用：DynamicFragment的View接口
 */
public interface IDynamicFragment {
    //加载更多
    void onLoadMore(List<DynamicItem> list);

    //下拉刷新
    void onRefresh(List<DynamicItem> list);
}
