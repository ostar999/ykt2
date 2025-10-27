package com.psychiatrygarden.fragmenthome.material;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.material.MaterialListAdp;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MaterialListFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private MaterialListAdp mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private String tabId = null;
    private RecyclerView rvList = null;
    private boolean isLoadMore = false;
    private int page = 1;

    private String getValue(String key) {
        Bundle arguments = getArguments();
        return arguments != null ? arguments.getString(key, "") : "";
    }

    private void loadData() {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_material_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }
}
