package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolInfoActivity;
import com.psychiatrygarden.adapter.CircleSchoolHomeAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class CircleSchoolHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    private CircleSchoolHomeAdapter circleSchoolHomeAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRefresh$0() {
        this.circleSchoolHomeAdapter.addData((CircleSchoolHomeAdapter) "");
        this.mSwipeRefreshLayout.setRefreshing(false);
    }

    public static CircleSchoolHomeFragment newInstance() {
        Bundle bundle = new Bundle();
        CircleSchoolHomeFragment circleSchoolHomeFragment = new CircleSchoolHomeFragment();
        circleSchoolHomeFragment.setArguments(bundle);
        return circleSchoolHomeFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_circle_school_home;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.rc_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CircleSchoolHomeAdapter circleSchoolHomeAdapter = new CircleSchoolHomeAdapter(R.layout.item_circle_school_home, new ArrayList());
        this.circleSchoolHomeAdapter = circleSchoolHomeAdapter;
        circleSchoolHomeAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
        recyclerView.setAdapter(this.circleSchoolHomeAdapter);
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        this.circleSchoolHomeAdapter.setList(arrayList);
        this.circleSchoolHomeAdapter.setOnItemClickListener(this);
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        startActivity(new Intent(getActivity(), (Class<?>) CircleSchoolInfoActivity.class));
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        if (this.mSwipeRefreshLayout.isRefreshing()) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.h0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15633c.lambda$onRefresh$0();
                }
            }, 1000L);
        }
    }
}
