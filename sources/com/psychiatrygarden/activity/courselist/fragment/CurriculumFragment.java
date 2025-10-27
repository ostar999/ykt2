package com.psychiatrygarden.activity.courselist.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.adapter.CurriculumAdapterNew;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.event.CourseRefreshChannelData;
import com.psychiatrygarden.fragmenthome.AllCourseFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CurriculumFragment extends BaseFragment {
    private static final String TAG = "CurriculumFragment";
    public CurriculumAdapterNew adapter;
    private int channel_id;
    public CurriculumItemBean.DataDTO dataDTOCourseAll;
    private CustomEmptyView emptyView;
    public String id;
    private LinearLayoutManager linearLayoutManager;
    private ImageView loadingView;
    private View lyLoading;
    private AnimationDrawable mAnimationDrawable;
    public SmartRefreshLayout refreshLayout;
    public String title;
    public RecyclerView viewid;
    public int page = 1;
    public final int pageSize = 20;
    public ArrayList<String> arrayList = new ArrayList<>();
    public String activity_id = "0";
    public String course_id = "0";
    public String type = "0";
    private boolean isLoading = false;
    private boolean isLastPage = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLoadMore(boolean success) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof AllCourseFragment) {
            ((AllCourseFragment) parentFragment).finishLoadMore(success, this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLoadMoreNoMoreData() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof AllCourseFragment) {
            ((AllCourseFragment) parentFragment).finishLoadMoreNoMoreData(this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishRefresh(boolean success) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof AllCourseFragment) {
            ((AllCourseFragment) parentFragment).finishRefresh(success, this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCourseListData() {
        getCourseListDataInterface();
    }

    private void getCourseListDataInterface() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("category_id", "" + this.id);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("page_size", "20");
        showLoadingView();
        Log.d(TAG, "getCourseListData: --page:" + this.page + "--收到通知数据id后请求id::" + this.id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseMainList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CurriculumFragment.this.finishRefresh(false);
                CurriculumFragment.this.finishLoadMoreNoMoreData();
                CurriculumFragment.this.setEmptyView(1);
                CurriculumFragment.this.isLoading = false;
                CurriculumFragment.this.hideLoadingView();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CurriculumFragment.this.isLoading = true;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                CurriculumFragment.this.hideLoadingView();
                try {
                    CurriculumItemBean curriculumItemBean = (CurriculumItemBean) new Gson().fromJson(t2, CurriculumItemBean.class);
                    CurriculumFragment curriculumFragment = CurriculumFragment.this;
                    if (curriculumFragment.page == 1) {
                        curriculumFragment.finishRefresh(true);
                    } else {
                        curriculumFragment.finishLoadMore(true);
                    }
                    if (curriculumItemBean.getCode().equals("200")) {
                        List<CurriculumItemBean.DataDTO> data = curriculumItemBean.getData();
                        CurriculumFragment.this.isLastPage = data == null || data.isEmpty();
                        CurriculumFragment curriculumFragment2 = CurriculumFragment.this;
                        if (curriculumFragment2.page == 1) {
                            curriculumFragment2.adapter.setList(curriculumItemBean.getData());
                            if (CurriculumFragment.this.isLastPage) {
                                CurriculumFragment.this.finishLoadMoreNoMoreData();
                                CurriculumFragment.this.setEmptyView(0);
                            }
                        } else if (curriculumFragment2.isLastPage) {
                            CurriculumFragment.this.finishLoadMoreNoMoreData();
                        } else {
                            CurriculumFragment.this.adapter.addData((Collection) data);
                        }
                    } else {
                        CurriculumFragment.this.finishRefresh(false);
                        CurriculumFragment.this.finishLoadMoreNoMoreData();
                        CurriculumFragment.this.setEmptyView(1);
                    }
                } catch (Exception e2) {
                    Log.d(CurriculumFragment.TAG, "error: " + e2.getMessage());
                    CurriculumFragment.this.finishRefresh(false);
                    CurriculumFragment.this.finishLoadMoreNoMoreData();
                    CurriculumFragment.this.setEmptyView(1);
                }
                CurriculumFragment.this.isLoading = false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoadingView() {
        this.lyLoading.setVisibility(8);
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    private void init() {
        if (getArguments().getBinder("dataDTO") == null) {
            getCourseListData();
            return;
        }
        if (getArguments().getBinder("dataDTO") instanceof CurriculumItemBean.DataDTO) {
            this.activity_id = getArguments().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
            CurriculumItemBean.DataDTO dataDTO = (CurriculumItemBean.DataDTO) getArguments().getBinder("dataDTO");
            this.dataDTOCourseAll = dataDTO;
            this.adapter.setList((List) dataDTO.getSetMeal());
            if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                this.viewid.setBackgroundResource(R.color.tertiary_backgroup_color_night);
            } else {
                this.viewid.setBackgroundResource(R.color.cfafafa);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getCourseListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page++;
        getCourseListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEmptyView$2(View view) {
        this.page = 1;
        getCourseListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(int viewType) {
        if (getActivity() != null) {
            this.adapter.setList(new ArrayList());
            this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.g3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11954c.lambda$setEmptyView$2(view);
                }
            });
            if (viewType == 1) {
                this.emptyView.setLoadFileResUi(this.mContext);
                this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
            } else if (viewType == 2) {
                this.emptyView.restartAnim();
            }
            this.emptyView.showEmptyView();
            this.adapter.setEmptyView(this.emptyView);
        }
    }

    private void showLoadingView() {
        this.lyLoading.setVisibility(0);
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_curriculum;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.id = getArguments().getString("id", "");
        this.course_id = getArguments().getString("course_id", "");
        this.title = getArguments().getString("title", "");
        this.type = getArguments().getString("type", "");
        this.arrayList = getArguments().getStringArrayList("set_meal_id");
        this.viewid = (RecyclerView) holder.get(R.id.viewid);
        this.loadingView = (ImageView) holder.get(R.id.iv_loading);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.loadingView.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.loading_night_customer));
        }
        this.mAnimationDrawable = (AnimationDrawable) this.loadingView.getBackground();
        View view = holder.get(R.id.customer_ly_loading);
        this.lyLoading = view;
        view.setVisibility(0);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        this.emptyView = customEmptyView;
        customEmptyView.changeEmptyViewNewBgTwoColor();
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.refreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableRefresh(false);
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.h3
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11959c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.i3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11965c.lambda$initViews$1(refreshLayout);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.linearLayoutManager = linearLayoutManager;
        this.viewid.setLayoutManager(linearLayoutManager);
        CurriculumAdapterNew curriculumAdapterNew = new CurriculumAdapterNew(getActivity());
        this.adapter = curriculumAdapterNew;
        curriculumAdapterNew.title = this.title;
        this.viewid.setAdapter(curriculumAdapterNew);
        View view2 = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = CommonUtil.dip2px(this.mContext, 10.0f);
        view2.setLayoutParams(layoutParams);
        this.adapter.addHeaderView(view2);
        this.viewid.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CurriculumFragment curriculumFragment = CurriculumFragment.this;
                if (curriculumFragment.dataDTOCourseAll != null || curriculumFragment.adapter.getData().isEmpty() || CurriculumFragment.this.linearLayoutManager == null || CurriculumFragment.this.adapter.getItemCount() - CurriculumFragment.this.linearLayoutManager.findLastVisibleItemPosition() > 4 || CurriculumFragment.this.isLoading || CurriculumFragment.this.isLastPage) {
                    return;
                }
                CurriculumFragment.this.isLoading = true;
                CurriculumFragment curriculumFragment2 = CurriculumFragment.this;
                curriculumFragment2.page++;
                curriculumFragment2.getCourseListData();
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        String string = arguments != null ? arguments.getString("id", "") : "";
        if (TextUtils.isEmpty(string)) {
            return;
        }
        try {
            this.channel_id = Integer.parseInt(string);
        } catch (Exception e2) {
            Log.d(TAG, "onCreate: " + e2.getMessage());
        }
    }

    public void onEventMainThread(CourseRefreshChannelData data) {
        if (String.valueOf(this.channel_id).equals(data.getChannelId())) {
            if (data.getIsRefresh()) {
                this.page = 1;
            } else {
                this.page++;
            }
            Log.d(TAG, "onEventMainThread: 收到通知数据id:" + this.channel_id);
            getCourseListData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        init();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        str.hashCode();
        switch (str) {
            case "BuySuccess1":
            case "BuySuccess":
            case "REFRESH_COURSE_LIST":
                this.page = 1;
                getCourseListData();
                break;
        }
    }
}
