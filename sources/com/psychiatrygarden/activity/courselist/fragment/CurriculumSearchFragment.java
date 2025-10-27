package com.psychiatrygarden.activity.courselist.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.adapter.CurriculumAdapterNew;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CourseSearchData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CurriculumSearchFragment extends BaseFragment {
    private static final String TAG = "CurriculumSearchFragment";
    public CurriculumAdapterNew adapter;
    public CurriculumItemBean.DataDTO dataDTOCourseAll;
    private CustomEmptyView emptyView;
    public String id;
    private LinearLayoutManager linearLayoutManager;
    public RecyclerView recyclerView;
    public SmartRefreshLayout refreshLayout;
    public String title;
    public int page = 1;
    public final int pageSize = 20;
    public String activity_id = "0";
    public String course_id = "0";
    public String type = "0";
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private String searchKeyword = "";
    private boolean haveMargin = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLoadMore() {
        this.refreshLayout.finishLoadMore(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLoadMoreNoMoreData() {
        this.refreshLayout.finishLoadMoreWithNoMoreData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishRefresh(boolean success) {
        this.refreshLayout.finishRefresh(success);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCourseListData() {
        getCourseListDataBySearch(this.searchKeyword);
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
    public void setEmptyView(int type) {
        if (getActivity() != null) {
            this.adapter.setList(new ArrayList());
            this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.w3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12062c.lambda$setEmptyView$2(view);
                }
            });
            if (type == 1) {
                this.emptyView.setLoadFileResUi(this.mContext);
                this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
            } else if (type == 2) {
                this.emptyView.restartAnim();
            }
            this.emptyView.showEmptyView();
            this.adapter.setEmptyView(this.emptyView);
        }
    }

    public void getCourseListDataBySearch(final String searchKeyword) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, searchKeyword);
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        ajaxParams.put("category_id", "0");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseMainListSearch, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumSearchFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CurriculumSearchFragment.this.finishRefresh(false);
                CurriculumSearchFragment.this.finishLoadMoreNoMoreData();
                CurriculumSearchFragment.this.setEmptyView(1);
                CurriculumSearchFragment.this.isLoading = false;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CurriculumSearchFragment.this.isLoading = true;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                try {
                    CurriculumItemBean curriculumItemBean = (CurriculumItemBean) new Gson().fromJson(t2, CurriculumItemBean.class);
                    CurriculumSearchFragment curriculumSearchFragment = CurriculumSearchFragment.this;
                    if (curriculumSearchFragment.page == 1) {
                        curriculumSearchFragment.finishRefresh(true);
                    } else {
                        curriculumSearchFragment.finishLoadMore();
                    }
                    if (curriculumItemBean.getCode().equals("200")) {
                        List<CurriculumItemBean.DataDTO> data = curriculumItemBean.getData();
                        CurriculumSearchFragment.this.isLastPage = data == null || data.isEmpty();
                        CurriculumSearchFragment curriculumSearchFragment2 = CurriculumSearchFragment.this;
                        if (curriculumSearchFragment2.page == 1) {
                            curriculumSearchFragment2.adapter.setList(curriculumItemBean.getData());
                            if (CurriculumSearchFragment.this.isLastPage) {
                                CurriculumSearchFragment.this.finishLoadMoreNoMoreData();
                                CurriculumSearchFragment.this.setEmptyView(0);
                            }
                        } else if (curriculumSearchFragment2.isLastPage) {
                            CurriculumSearchFragment.this.finishLoadMoreNoMoreData();
                        } else {
                            CurriculumSearchFragment.this.adapter.addData((Collection) data);
                        }
                        CurriculumSearchFragment.this.adapter.setKeyword(searchKeyword);
                    } else {
                        CurriculumSearchFragment.this.finishRefresh(false);
                        CurriculumSearchFragment.this.finishLoadMoreNoMoreData();
                        CurriculumSearchFragment.this.setEmptyView(0);
                    }
                } catch (Exception e2) {
                    Log.d(CurriculumSearchFragment.TAG, "error: " + e2.getMessage());
                    CurriculumSearchFragment.this.finishRefresh(false);
                    CurriculumSearchFragment.this.finishLoadMoreNoMoreData();
                    CurriculumSearchFragment.this.setEmptyView(1);
                }
                CurriculumSearchFragment.this.isLoading = false;
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_curriculum_search;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.title = arguments.getString("title", "");
            this.type = arguments.getString("type", "");
        }
        this.recyclerView = (RecyclerView) holder.get(R.id.viewid);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        this.emptyView = customEmptyView;
        customEmptyView.changeEmptyViewNewBgTwoColor();
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.refreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableRefresh(true);
        this.refreshLayout.setEnableLoadMore(true);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.u3
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12050c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.v3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12056c.lambda$initViews$1(refreshLayout);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.linearLayoutManager = linearLayoutManager;
        this.recyclerView.setLayoutManager(linearLayoutManager);
        CurriculumAdapterNew curriculumAdapterNew = new CurriculumAdapterNew(getActivity());
        this.adapter = curriculumAdapterNew;
        curriculumAdapterNew.title = this.title;
        this.recyclerView.setAdapter(curriculumAdapterNew);
        View view = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = CommonUtil.dip2px(this.mContext, 20.0f);
        view.setLayoutParams(layoutParams);
        this.adapter.addHeaderView(view);
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumSearchFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CurriculumSearchFragment curriculumSearchFragment = CurriculumSearchFragment.this;
                if (curriculumSearchFragment.dataDTOCourseAll != null || curriculumSearchFragment.adapter.getData().isEmpty() || CurriculumSearchFragment.this.linearLayoutManager == null || CurriculumSearchFragment.this.adapter.getItemCount() - CurriculumSearchFragment.this.linearLayoutManager.findLastVisibleItemPosition() > 4 || CurriculumSearchFragment.this.isLoading || CurriculumSearchFragment.this.isLastPage) {
                    return;
                }
                CurriculumSearchFragment.this.isLoading = true;
                CurriculumSearchFragment curriculumSearchFragment2 = CurriculumSearchFragment.this;
                curriculumSearchFragment2.page++;
                curriculumSearchFragment2.getCourseListData();
            }
        });
        if (this.haveMargin) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(CommonUtil.dip2px(this.mContext, 16.0f), 0, CommonUtil.dip2px(this.mContext, 10.0f), 0);
            this.recyclerView.setLayoutParams(marginLayoutParams);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onEventMainThread(CourseSearchData data) {
        if (data.isRefresh()) {
            this.page = 1;
        } else {
            this.page++;
        }
        Log.d(TAG, "onEventMainThread: 收到的通知，搜索关键字:" + data.getKeyword());
        getCourseListData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    public void setHaveMargin(boolean haveMargin) {
        this.haveMargin = haveMargin;
    }

    public void setSearchKeyWord(String keyWord, boolean isReload) {
        this.page = 1;
        this.searchKeyword = keyWord;
        if (isReload) {
            getCourseListDataBySearch(keyWord);
        } else if (this.adapter.getItemCount() == 0) {
            getCourseListDataBySearch(this.searchKeyword);
        }
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
