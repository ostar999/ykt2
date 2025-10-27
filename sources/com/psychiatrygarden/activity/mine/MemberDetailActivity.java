package com.psychiatrygarden.activity.mine;

import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.adapter.MemberDetailAdapter;
import com.psychiatrygarden.activity.mine.bean.MemberDetailBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MemberDetailActivity extends BaseActivity {
    private MemberDetailAdapter mAdapter;
    private CustomEmptyView mEmptyView;
    private LinearLayout mLlEmpty;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvMemberDetail;
    private int page = 1;
    private int pageSize = 20;
    private List<MemberDetailBean.RewardItem> mDetailList = new ArrayList();

    public static /* synthetic */ int access$210(MemberDetailActivity memberDetailActivity) {
        int i2 = memberDetailActivity.page;
        memberDetailActivity.page = i2 - 1;
        return i2;
    }

    private void initRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.mine.c
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12777c.lambda$initRefreshLayout$0(refreshLayout);
            }
        });
        this.mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.mine.d
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12833c.lambda$initRefreshLayout$1(refreshLayout);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.mEmptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12834c.lambda$initRefreshLayout$2(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$0(RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$1(RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$2(View view) {
        this.page = 1;
        loadData();
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put(DatabaseManager.SIZE, this.pageSize + "");
        YJYHttpUtils.get(this, NetworkRequestsURL.getRewardList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.MemberDetailActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MemberDetailActivity.this.hideProgressDialog();
                MemberDetailActivity.this.mRefreshLayout.finishRefresh();
                NewToast.showShort(MemberDetailActivity.this, "网络连接失败", 0).show();
                if (MemberDetailActivity.this.page == 1) {
                    MemberDetailActivity.this.showEmptyView();
                } else {
                    MemberDetailActivity.access$210(MemberDetailActivity.this);
                    MemberDetailActivity.this.mRefreshLayout.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                MemberDetailActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                MemberDetailActivity.this.hideProgressDialog();
                MemberDetailActivity.this.mRefreshLayout.finishRefresh();
                try {
                    MemberDetailBean memberDetailBean = (MemberDetailBean) new Gson().fromJson(s2, MemberDetailBean.class);
                    if ("200".equals(memberDetailBean.getCode())) {
                        MemberDetailActivity.this.updateUI(memberDetailBean);
                    } else {
                        NewToast.showShort(MemberDetailActivity.this, memberDetailBean.getMessage(), 0).show();
                        if (MemberDetailActivity.this.page == 1) {
                            MemberDetailActivity.this.showEmptyView();
                        } else {
                            MemberDetailActivity.access$210(MemberDetailActivity.this);
                            MemberDetailActivity.this.mRefreshLayout.finishLoadMore(false);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(MemberDetailActivity.this, "数据解析出错", 0).show();
                    if (MemberDetailActivity.this.page == 1) {
                        MemberDetailActivity.this.showEmptyView();
                    } else {
                        MemberDetailActivity.access$210(MemberDetailActivity.this);
                        MemberDetailActivity.this.mRefreshLayout.finishLoadMore(false);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.mLlEmpty.setVisibility(0);
        this.mRefreshLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(MemberDetailBean detailBean) {
        if (detailBean.getData() == null || detailBean.getData().getList() == null || detailBean.getData().getList().isEmpty()) {
            if (this.page != 1) {
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                showEmptyView();
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
        }
        List<MemberDetailBean.RewardItem> list = detailBean.getData().getList();
        if (this.page != 1) {
            int size = this.mDetailList.size();
            this.mDetailList.addAll(list);
            this.mAdapter.notifyItemRangeInserted(size, list.size());
            if (list.size() < this.pageSize) {
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                this.mRefreshLayout.finishLoadMore();
                return;
            }
        }
        this.mDetailList.clear();
        this.mDetailList.addAll(list);
        this.mAdapter.notifyDataSetChanged();
        this.mLlEmpty.setVisibility(8);
        this.mRefreshLayout.setVisibility(0);
        if (list.size() < this.pageSize) {
            this.mRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            this.mRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("明细");
        this.mRvMemberDetail = (RecyclerView) findViewById(R.id.rv_member_detail);
        this.mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        this.mAdapter = new MemberDetailAdapter(this, this.mDetailList);
        this.mRvMemberDetail.setLayoutManager(new LinearLayoutManager(this));
        this.mRvMemberDetail.setAdapter(this.mAdapter);
        initRefreshLayout();
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_member_detail);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
