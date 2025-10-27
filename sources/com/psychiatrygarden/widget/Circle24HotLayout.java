package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.forum.Circle24HotListActivity;
import com.psychiatrygarden.adapter.HotCircleAdp;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.HotCircleBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class Circle24HotLayout extends LinearLayout {
    private HotCircleAdp mAdapter;
    private RecyclerView mRecyclerView;

    public Circle24HotLayout(Context context) {
        this(context, null);
    }

    private void init() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_circle_24, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.rv24Circles);
        this.mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(gridLayoutManager);
        this.mRecyclerView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        HotCircleAdp hotCircleAdp = new HotCircleAdp();
        this.mAdapter = hotCircleAdp;
        this.mRecyclerView.setAdapter(hotCircleAdp);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.m2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16697c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        HotCircleBean hotCircleBean = (HotCircleBean) this.mAdapter.getItem(i2);
        if (hotCircleBean.getItemType() != 1) {
            if (hotCircleBean.getItemType() == 2) {
                Circle24HotListActivity.newIntent(getContext());
                return;
            }
            return;
        }
        CirclrListBean.DataBean circleInfo = hotCircleBean.getCircleInfo();
        Intent intent = new Intent(ProjectApp.instance(), (Class<?>) CircleInfoActivity.class);
        intent.putExtra("article_id", "" + circleInfo.getId());
        intent.putExtra("commentCount", circleInfo.getComment_count());
        intent.putExtra("channel_id", "");
        intent.putExtra("module_type", circleInfo.getModule_type());
        intent.setFlags(268435456);
        getContext().startActivity(intent);
    }

    public void setData(boolean isShowMore, List<CirclrListBean.DataBean> list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            HotCircleBean hotCircleBean = new HotCircleBean();
            hotCircleBean.setItemType(1);
            hotCircleBean.setCircleInfo(list.get(i2));
            arrayList.add(hotCircleBean);
        }
        if (isShowMore) {
            ((HotCircleBean) arrayList.get(list.size() - 1)).setItemType(2);
        }
        this.mAdapter.setList(arrayList);
    }

    public Circle24HotLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Circle24HotLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
