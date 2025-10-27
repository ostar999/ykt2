package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.plv.livescenes.playback.video.PLVPlaybackVideoView;
import com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowMajorsBean;
import com.psychiatrygarden.adapter.MajorsListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.CustomEmptyDayView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class FollowMajorsFragment extends BaseFragment {
    private CustomEmptyDayView emptyView;
    private String listType;
    private boolean mEditingState;
    private LinearLayout mLlEmpty;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private String majorType;
    private MajorsListAdapter majorsListAdapter;
    private int scrollHeight = 0;
    private int page = 1;
    private int pageSize = 20;
    private List<FollowMajorsBean.DataBean> mDetailList = new ArrayList();
    private boolean hasBeenStopped = false;

    public static /* synthetic */ int access$210(FollowMajorsFragment followMajorsFragment) {
        int i2 = followMajorsFragment.page;
        followMajorsFragment.page = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(int i2) {
        if (this.mDetailList.get(i2).isEditing_state()) {
            return;
        }
        startActivity(SchoolByMajorActivity.newIntent(getActivity(), this.mDetailList.get(i2).getMajor_id()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEditingState$5(FollowMajorsBean.DataBean dataBean) {
        dataBean.setEditing_state(this.mEditingState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUI$6(FollowMajorsBean.DataBean dataBean) {
        dataBean.setEditing_state(this.mEditingState);
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        StringBuilder sb = new StringBuilder();
        sb.append(this.page);
        String str = "";
        sb.append("");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, sb.toString());
        ajaxParams.put("page_size", this.pageSize + "");
        if ("ranking".equals(this.listType)) {
            str = NetworkRequestsURL.hotMajorList;
        } else if ("follow".equals(this.listType)) {
            str = NetworkRequestsURL.followMajor;
        } else if ("MajorList".equals(this.listType)) {
            str = NetworkRequestsURL.indexForMajorList;
            ajaxParams.put("major_type", this.majorType);
        }
        AjaxCallBack<String> ajaxCallBack = new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.FollowMajorsFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                FollowMajorsFragment.this.hideProgressDialog();
                FollowMajorsFragment.this.mRefresh.finishRefresh();
                NewToast.showShort(FollowMajorsFragment.this.getActivity(), "网络连接失败", 0).show();
                if (FollowMajorsFragment.this.page == 1) {
                    FollowMajorsFragment.this.showEmptyView();
                } else {
                    FollowMajorsFragment.access$210(FollowMajorsFragment.this);
                    FollowMajorsFragment.this.mRefresh.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                FollowMajorsFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                FollowMajorsFragment.this.hideProgressDialog();
                FollowMajorsFragment.this.mRefresh.finishRefresh();
                try {
                    FollowMajorsBean followMajorsBean = (FollowMajorsBean) new Gson().fromJson(s2, FollowMajorsBean.class);
                    if ("200".equals(followMajorsBean.getCode())) {
                        FollowMajorsFragment.this.updateUI(followMajorsBean);
                    } else {
                        NewToast.showShort(FollowMajorsFragment.this.getActivity(), followMajorsBean.getMessage(), 0).show();
                        if (FollowMajorsFragment.this.page == 1) {
                            FollowMajorsFragment.this.showEmptyView();
                        } else {
                            FollowMajorsFragment.access$210(FollowMajorsFragment.this);
                            FollowMajorsFragment.this.mRefresh.finishLoadMore(false);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(FollowMajorsFragment.this.getActivity(), "数据解析出错", 0).show();
                    if (FollowMajorsFragment.this.page == 1) {
                        FollowMajorsFragment.this.showEmptyView();
                    } else {
                        FollowMajorsFragment.access$210(FollowMajorsFragment.this);
                        FollowMajorsFragment.this.mRefresh.finishLoadMore(false);
                    }
                }
            }
        };
        if (NetworkRequestsURL.hotMajorList.equals(str)) {
            YJYHttpUtils.post(getActivity(), str, ajaxParams, ajaxCallBack);
        } else if (NetworkRequestsURL.followMajor.equals(str) || NetworkRequestsURL.indexForMajorList.equals(str)) {
            YJYHttpUtils.get(getActivity(), str, ajaxParams, ajaxCallBack);
        }
    }

    public static FollowMajorsFragment newInstance(String listType) {
        Bundle bundle = new Bundle();
        bundle.putString(PLVPlaybackVideoView.LIST_TYPE, listType);
        FollowMajorsFragment followMajorsFragment = new FollowMajorsFragment();
        followMajorsFragment.setArguments(bundle);
        return followMajorsFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.mLlEmpty.setVisibility(0);
        this.mRefresh.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(FollowMajorsBean detailBean) {
        if (detailBean.getData() == null || detailBean.getData() == null || detailBean.getData().isEmpty()) {
            if (this.page != 1) {
                this.mRefresh.finishLoadMoreWithNoMoreData();
                return;
            }
            this.mDetailList.clear();
            showEmptyView();
            this.mRefresh.finishLoadMoreWithNoMoreData();
            return;
        }
        List<FollowMajorsBean.DataBean> data = detailBean.getData();
        if (this.page != 1) {
            int size = this.mDetailList.size();
            this.mDetailList.addAll(data);
            this.majorsListAdapter.notifyItemRangeInserted(size, data.size());
            if (data.size() < this.pageSize) {
                this.mRefresh.finishLoadMoreWithNoMoreData();
                return;
            } else {
                this.mRefresh.finishLoadMore();
                return;
            }
        }
        this.mDetailList.clear();
        this.mDetailList.addAll(data);
        if (this.mEditingState && Build.VERSION.SDK_INT >= 24) {
            this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.n
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f11278c.lambda$updateUI$6((FollowMajorsBean.DataBean) obj);
                }
            });
        }
        this.majorsListAdapter.notifyDataSetChanged();
        this.mLlEmpty.setVisibility(8);
        this.mRefresh.setVisibility(0);
        if (data.size() < this.pageSize) {
            this.mRefresh.finishLoadMoreWithNoMoreData();
        } else {
            this.mRefresh.finishLoadMore();
        }
    }

    public List<FollowMajorsBean.DataBean> getDetailList() {
        return this.mDetailList;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.follow_school_layout;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mRefresh = (SmartRefreshLayout) holder.get(R.id.follow_refresh);
        this.mRecyclerView = (RecyclerView) holder.get(R.id.follow_recycler);
        this.mLlEmpty = (LinearLayout) holder.get(R.id.ll_empty);
        this.listType = getArguments().getString(PLVPlaybackVideoView.LIST_TYPE, "");
        this.majorType = getArguments().getString("major_type", "");
        MajorsListAdapter majorsListAdapter = new MajorsListAdapter(getActivity(), this.mDetailList, this.listType);
        this.majorsListAdapter = majorsListAdapter;
        this.mRecyclerView.setAdapter(majorsListAdapter);
        if (this.listType.equals("ranking") || "follow".equals(this.listType)) {
            this.mRefresh.setEnableLoadMore(false);
        }
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.o
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11279c.lambda$initViews$0(refreshLayout);
            }
        });
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.p
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11280c.lambda$initViews$1(refreshLayout);
            }
        });
        CustomEmptyDayView customEmptyDayView = new CustomEmptyDayView(this.mContext, 0, "", "");
        this.emptyView = customEmptyDayView;
        customEmptyDayView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11281c.lambda$initViews$2(view);
            }
        });
        this.majorsListAdapter.setOnItemClickListener(new MajorsListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.r
            @Override // com.psychiatrygarden.adapter.MajorsListAdapter.OnItemClickListener
            public final void onItemClick(int i2) {
                this.f11282a.lambda$initViews$3(i2);
            }
        });
        loadData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.page = 1;
        loadData();
        if (this.hasBeenStopped) {
            this.hasBeenStopped = false;
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.hasBeenStopped = true;
    }

    public void reflashList() {
        this.mEditingState = true;
        this.page = 1;
        loadData();
    }

    public void selectAll(final boolean selectAll) {
        if (this.mDetailList.isEmpty() || Build.VERSION.SDK_INT < 24) {
            return;
        }
        this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.s
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FollowMajorsBean.DataBean) obj).setItem_select(selectAll);
            }
        });
        this.majorsListAdapter.notifyDataSetChanged();
    }

    public void setEditingState(Boolean editingState) {
        this.mEditingState = editingState.booleanValue();
        if (this.mDetailList.isEmpty() || Build.VERSION.SDK_INT < 24) {
            return;
        }
        this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.m
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f11277c.lambda$setEditingState$5((FollowMajorsBean.DataBean) obj);
            }
        });
        this.majorsListAdapter.notifyDataSetChanged();
    }

    public static FollowMajorsFragment newInstance(String listType, String major_type) {
        Bundle bundle = new Bundle();
        bundle.putString(PLVPlaybackVideoView.LIST_TYPE, listType);
        bundle.putString("major_type", major_type);
        FollowMajorsFragment followMajorsFragment = new FollowMajorsFragment();
        followMajorsFragment.setArguments(bundle);
        return followMajorsFragment;
    }
}
