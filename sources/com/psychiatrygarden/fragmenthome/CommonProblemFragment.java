package com.psychiatrygarden.fragmenthome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CommonProblem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CommonProblemFragment extends BaseFragment {
    private List<CommonProblem> data = new ArrayList();
    private SmartRefreshLayout refresh;

    public static class CommonProblemAdapter extends BaseQuickAdapter<CommonProblem, BaseViewHolder> {
        public CommonProblemAdapter() {
            super(R.layout.item_common_problem);
        }

        @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
        public void convert(BaseViewHolder holder, CommonProblem item) {
            TextView textView = (TextView) holder.getView(R.id.tvTitle);
            TextView textView2 = (TextView) holder.getView(R.id.tvContent);
            textView.setText("Q:" + item.getQuestion());
            textView2.setText(item.getAnswer());
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_common_problem;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.data = (List) new Gson().fromJson(arguments.getString("data"), new TypeToken<List<CommonProblem>>() { // from class: com.psychiatrygarden.fragmenthome.CommonProblemFragment.1
            }.getType());
        }
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshCommonProblem);
        this.refresh = smartRefreshLayout;
        smartRefreshLayout.setEnableRefresh(false);
        this.refresh.setEnableLoadMore(false);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recyclerCommonProblem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommonProblemAdapter commonProblemAdapter = new CommonProblemAdapter();
        recyclerView.setAdapter(commonProblemAdapter);
        commonProblemAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, (ViewGroup) null));
        commonProblemAdapter.setNewData(this.data);
    }
}
