package com.psychiatrygarden.activity.mine.active;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ActiveListAct extends BaseActivity implements OnRefreshListener {
    private View addFooterView;
    private CustomEmptyView emptyView;
    private boolean isCanLoadNextPage;
    private ActiveListAdp mAdapter;
    private RelativeLayout mLyLoading;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView mTvNoData;
    private boolean isNoMoreData = false;
    private int mPage = 1;

    public static /* synthetic */ int access$312(ActiveListAct activeListAct, int i2) {
        int i3 = activeListAct.mPage + i2;
        activeListAct.mPage = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        onRefresh(this.mRefresh);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) ActiveListAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("活动");
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ActiveListAdp activeListAdp = new ActiveListAdp();
        this.mAdapter = activeListAdp;
        this.mRecyclerView.setAdapter(activeListAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.active.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12769c.lambda$init$0(view);
            }
        });
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        this.mRefresh.setEnableLoadMore(false);
        this.mRefresh.setOnRefreshListener(this);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.mine.active.ActiveListAct.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) ActiveListAct.this.mRecyclerView.getLayoutManager();
                int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    int itemCount = linearLayoutManager.getItemCount() - iFindFirstVisibleItemPosition;
                    LogUtils.e("dis_count", "count====>" + itemCount);
                    if (itemCount > 11 || ActiveListAct.this.isCanLoadNextPage || ActiveListAct.this.isNoMoreData) {
                        return;
                    }
                    ActiveListAct.this.isCanLoadNextPage = true;
                    ActiveListAct.access$312(ActiveListAct.this, 1);
                    ActiveListAct.this.getData();
                    LogUtils.e("load_next", "加载下一页数据:" + ActiveListAct.this.mPage);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.mPage = 1;
        this.isNoMoreData = false;
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_active_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
