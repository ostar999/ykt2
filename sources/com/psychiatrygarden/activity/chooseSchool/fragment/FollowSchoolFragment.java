package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.plv.livescenes.playback.video.PLVPlaybackVideoView;
import com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.adapter.TargetSchoolListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.CustomEmptyDayView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class FollowSchoolFragment extends BaseFragment {
    private CustomEmptyDayView emptyView;
    private String listType;
    private TargetSchoolListAdapter mAdapter;
    private boolean mEditingState;
    private LinearLayout mLlEmpty;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private int scrollHeight = 0;
    private int page = 1;
    private int pageSize = 20;
    private List<FollowSchoolBean.DataBean> mDetailList = new ArrayList();
    private boolean hasBeenStopped = false;

    public static /* synthetic */ int access$210(FollowSchoolFragment followSchoolFragment) {
        int i2 = followSchoolFragment.page;
        followSchoolFragment.page = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(int i2, FollowSchoolBean.DataBean dataBean) {
        if (dataBean.isEditing_state()) {
            return;
        }
        SchoolDetailsAct.newIntent(this.mContext, dataBean.getSchool_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEditingState$5(FollowSchoolBean.DataBean dataBean) {
        dataBean.setEditing_state(this.mEditingState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUI$6(FollowSchoolBean.DataBean dataBean) {
        dataBean.setEditing_state(this.mEditingState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUI$7(FollowSchoolBean.DataBean dataBean) {
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
            str = NetworkRequestsURL.hotSchoolList;
        } else if ("follow".equals(this.listType)) {
            str = NetworkRequestsURL.followSchoolList;
        }
        AjaxCallBack<String> ajaxCallBack = new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.FollowSchoolFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                FollowSchoolFragment.this.hideProgressDialog();
                FollowSchoolFragment.this.mRefresh.finishRefresh();
                NewToast.showShort(FollowSchoolFragment.this.getActivity(), "网络连接失败", 0).show();
                if (FollowSchoolFragment.this.page == 1) {
                    FollowSchoolFragment.this.showEmptyView();
                } else {
                    FollowSchoolFragment.access$210(FollowSchoolFragment.this);
                    FollowSchoolFragment.this.mRefresh.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                FollowSchoolFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                FollowSchoolFragment.this.hideProgressDialog();
                FollowSchoolFragment.this.mRefresh.finishRefresh();
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if ("200".equals(followSchoolBean.getCode())) {
                        FollowSchoolFragment.this.updateUI(followSchoolBean);
                    } else {
                        NewToast.showShort(FollowSchoolFragment.this.getActivity(), followSchoolBean.getMessage(), 0).show();
                        if (FollowSchoolFragment.this.page == 1) {
                            FollowSchoolFragment.this.showEmptyView();
                        } else {
                            FollowSchoolFragment.access$210(FollowSchoolFragment.this);
                            FollowSchoolFragment.this.mRefresh.finishLoadMore(false);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(FollowSchoolFragment.this.getActivity(), "数据解析出错", 0).show();
                    if (FollowSchoolFragment.this.page == 1) {
                        FollowSchoolFragment.this.showEmptyView();
                    } else {
                        FollowSchoolFragment.access$210(FollowSchoolFragment.this);
                        FollowSchoolFragment.this.mRefresh.finishLoadMore(false);
                    }
                }
            }
        };
        if (NetworkRequestsURL.hotSchoolList.equals(str)) {
            YJYHttpUtils.post(getActivity(), str, ajaxParams, ajaxCallBack);
        } else if (NetworkRequestsURL.followSchoolList.equals(str)) {
            YJYHttpUtils.get(getActivity(), str, ajaxParams, ajaxCallBack);
        }
    }

    public static FollowSchoolFragment newInstance(String listType) {
        Bundle bundle = new Bundle();
        bundle.putString(PLVPlaybackVideoView.LIST_TYPE, listType);
        FollowSchoolFragment followSchoolFragment = new FollowSchoolFragment();
        followSchoolFragment.setArguments(bundle);
        return followSchoolFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.mLlEmpty.setVisibility(0);
        this.mRefresh.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(FollowSchoolBean detailBean) {
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
        List<FollowSchoolBean.DataBean> data = detailBean.getData();
        if (this.page != 1) {
            int size = this.mDetailList.size();
            this.mDetailList.addAll(data);
            if (this.mEditingState && Build.VERSION.SDK_INT >= 24) {
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.w
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f11287c.lambda$updateUI$7((FollowSchoolBean.DataBean) obj);
                    }
                });
            }
            this.mAdapter.notifyItemRangeInserted(size, data.size());
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
            this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.v
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f11286c.lambda$updateUI$6((FollowSchoolBean.DataBean) obj);
                }
            });
        }
        this.mAdapter.notifyDataSetChanged();
        this.mLlEmpty.setVisibility(8);
        this.mRefresh.setVisibility(0);
        if (data.size() < this.pageSize) {
            this.mRefresh.finishLoadMoreWithNoMoreData();
        } else {
            this.mRefresh.finishLoadMore();
        }
    }

    public List<FollowSchoolBean.DataBean> getDetailList() {
        return this.mDetailList;
    }

    public String getFirstLogo() {
        return this.mDetailList.size() > 0 ? this.mDetailList.get(0).getCover() : "";
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
        TargetSchoolListAdapter targetSchoolListAdapter = new TargetSchoolListAdapter(getActivity(), this.mDetailList, this.listType);
        this.mAdapter = targetSchoolListAdapter;
        this.mRecyclerView.setAdapter(targetSchoolListAdapter);
        this.mAdapter.setOnItemClickListener(new TargetSchoolListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.x
            @Override // com.psychiatrygarden.adapter.TargetSchoolListAdapter.OnItemClickListener
            public final void onItemClick(int i2, FollowSchoolBean.DataBean dataBean) {
                this.f11288a.lambda$initViews$0(i2, dataBean);
            }
        });
        this.mAdapter.setItemClickSelectListener(new TargetSchoolListAdapter.ItemClickSelectListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.FollowSchoolFragment.1
            @Override // com.psychiatrygarden.adapter.TargetSchoolListAdapter.ItemClickSelectListener
            public void onItemClickSelect(int position, FollowSchoolBean.DataBean item) {
                EventBus.getDefault().post(EventBusConstant.EVENT_SELECT_SCHOOL_UPDATE);
            }
        });
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.y
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11289c.lambda$initViews$1(refreshLayout);
            }
        });
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.z
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11290c.lambda$initViews$2(refreshLayout);
            }
        });
        this.mRefresh.setEnableLoadMore(false);
        CustomEmptyDayView customEmptyDayView = new CustomEmptyDayView(this.mContext, 0, "", "");
        this.emptyView = customEmptyDayView;
        customEmptyDayView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11264c.lambda$initViews$3(view);
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
        this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.t
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FollowSchoolBean.DataBean) obj).setItem_select(selectAll);
            }
        });
        this.mAdapter.notifyDataSetChanged();
    }

    public void setEditingState(Boolean editingState) {
        this.mEditingState = editingState.booleanValue();
        if (this.mDetailList.isEmpty() || Build.VERSION.SDK_INT < 24) {
            return;
        }
        this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.u
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f11285c.lambda$setEditingState$5((FollowSchoolBean.DataBean) obj);
            }
        });
        this.mAdapter.notifyDataSetChanged();
    }
}
