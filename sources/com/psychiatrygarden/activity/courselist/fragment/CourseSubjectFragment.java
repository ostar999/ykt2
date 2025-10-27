package com.psychiatrygarden.activity.courselist.fragment;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.courselist.CourseSubjectActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.adapter.CurriculumAdapterNew;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.SpecialListData;
import com.psychiatrygarden.db.SharePreferencesUtils;
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
public class CourseSubjectFragment extends BaseFragment {
    public CurriculumAdapterNew adapter;
    private CustomEmptyView emptyView;
    public String id;
    private LinearLayoutManager linearLayoutManager;
    public RecyclerView recyclerView;
    public SmartRefreshLayout refreshLayout;
    public String title = "";
    public int page = 1;
    public final int pageSize = 10;
    public ArrayList<String> arrayList = new ArrayList<>();
    public String courseSpecialId = "0";
    public String type = "0";
    public String activity_id = "0";
    private boolean hasSetEmptyView = false;
    private boolean isLastPage = false;

    private void getSpecialListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.courseSpecialId);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.courseSpecialList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseSubjectFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseSubjectFragment.this.adapter.setList(new ArrayList());
                CourseSubjectFragment courseSubjectFragment = CourseSubjectFragment.this;
                courseSubjectFragment.adapter.setEmptyView(courseSubjectFragment.emptyView);
                CourseSubjectFragment.this.emptyView.setLoadFileResUi(CourseSubjectFragment.this.getActivity());
                CourseSubjectFragment.this.refreshLayout.finishRefresh(false);
                CourseSubjectFragment.this.refreshLayout.setNoMoreData(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    SpecialListData specialListData = (SpecialListData) new Gson().fromJson(t2, SpecialListData.class);
                    CourseSubjectFragment courseSubjectFragment = CourseSubjectFragment.this;
                    if (courseSubjectFragment.page == 1) {
                        courseSubjectFragment.refreshLayout.finishRefresh();
                    } else {
                        courseSubjectFragment.refreshLayout.setNoMoreData(true);
                    }
                    if (!"200".equals(specialListData.getCode())) {
                        CourseSubjectFragment.this.refreshLayout.finishRefresh(false);
                        CourseSubjectFragment.this.refreshLayout.setNoMoreData(true);
                        CourseSubjectFragment.this.adapter.setList(new ArrayList());
                        CourseSubjectFragment courseSubjectFragment2 = CourseSubjectFragment.this;
                        courseSubjectFragment2.adapter.setEmptyView(courseSubjectFragment2.emptyView);
                        CourseSubjectFragment.this.emptyView.uploadEmptyViewResUi();
                        CourseSubjectFragment.this.toastOnUiThread(specialListData.getMessage());
                        return;
                    }
                    if (specialListData.getData() == null || specialListData.getData().getItems() == null) {
                        CourseSubjectFragment courseSubjectFragment3 = CourseSubjectFragment.this;
                        if (courseSubjectFragment3.page == 1) {
                            courseSubjectFragment3.adapter.setList(new ArrayList());
                            CourseSubjectFragment courseSubjectFragment4 = CourseSubjectFragment.this;
                            courseSubjectFragment4.adapter.setEmptyView(courseSubjectFragment4.emptyView);
                            CourseSubjectFragment.this.emptyView.uploadEmptyViewResUi();
                        }
                        CourseSubjectFragment.this.refreshLayout.finishRefresh(false);
                        CourseSubjectFragment.this.refreshLayout.setNoMoreData(true);
                        return;
                    }
                    List<CurriculumItemBean.DataDTO> items = specialListData.getData().getItems();
                    int size = items.size();
                    CourseSubjectFragment.this.isLastPage = size < 10;
                    CourseSubjectFragment courseSubjectFragment5 = CourseSubjectFragment.this;
                    if (courseSubjectFragment5.page == 1) {
                        courseSubjectFragment5.adapter.setList(items);
                        if (size == 0) {
                            CourseSubjectFragment.this.refreshLayout.setNoMoreData(true);
                            CourseSubjectFragment.this.adapter.setList(new ArrayList());
                            CourseSubjectFragment courseSubjectFragment6 = CourseSubjectFragment.this;
                            courseSubjectFragment6.adapter.setEmptyView(courseSubjectFragment6.emptyView);
                            CourseSubjectFragment.this.emptyView.uploadEmptyViewResUi();
                        }
                    } else if (size == 0) {
                        courseSubjectFragment5.refreshLayout.setNoMoreData(true);
                    } else {
                        courseSubjectFragment5.adapter.addData((Collection) items);
                    }
                    if (CourseSubjectFragment.this.isLastPage) {
                        CourseSubjectFragment.this.refreshLayout.setNoMoreData(true);
                    }
                } catch (Exception unused) {
                    CourseSubjectFragment.this.adapter.setList(new ArrayList());
                    CourseSubjectFragment courseSubjectFragment7 = CourseSubjectFragment.this;
                    courseSubjectFragment7.adapter.setEmptyView(courseSubjectFragment7.emptyView);
                    CourseSubjectFragment.this.emptyView.uploadEmptyViewResUi();
                    CourseSubjectFragment.this.refreshLayout.finishRefresh(false);
                    CourseSubjectFragment.this.refreshLayout.setNoMoreData(true);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getSpecialListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page++;
        getSpecialListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        this.page = 1;
        getSpecialListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_subject;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.courseSpecialId = getArguments().getString(CourseSubjectActivity.SPECIAL_ID, "");
        this.recyclerView = (RecyclerView) holder.get(R.id.viewid);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.refreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableRefresh(true);
        this.refreshLayout.setEnableLoadMore(true);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.p2
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12013c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.q2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12022c.lambda$initViews$1(refreshLayout);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.linearLayoutManager = linearLayoutManager;
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.adapter = new CurriculumAdapterNew(getActivity());
        View view = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = CommonUtil.dip2px(this.mContext, 20.0f);
        view.setLayoutParams(layoutParams);
        this.adapter.addHeaderView(view);
        this.recyclerView.setAdapter(this.adapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.r2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f12029c.lambda$initViews$2(view2);
            }
        });
        this.adapter.setEmptyView(this.emptyView);
        getSpecialListData();
    }
}
