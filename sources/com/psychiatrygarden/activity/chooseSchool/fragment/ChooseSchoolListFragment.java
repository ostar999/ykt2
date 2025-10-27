package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolSchoolListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0014J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u001a\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\u0012\u0010\u001e\u001a\u00020\u00182\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006H\u0016J\u0010\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolListFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "adapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolListAdapter;", "attr", "", UriUtil.QUERY_CATEGORY, "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "filterProvince", "isLastPage", "", "major_id", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "score", "type", "getLayoutId", "getListData", "", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onEventMainThread", "str", "setEmptyView", "emptyData", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolListFragment extends BaseFragment {
    private ChooseSchoolListAdapter adapter;

    @Nullable
    private String attr;

    @Nullable
    private String category;
    private CustomEmptyView emptyView;

    @Nullable
    private String filterProvince;
    private boolean isLastPage;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh;
    private int page = 1;

    @Nullable
    private String type = "";

    @Nullable
    private String major_id = "";

    @Nullable
    private String score = "";

    private final void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("major_id", this.major_id);
        ajaxParams.put("user_score", this.score);
        ajaxParams.put("type", this.type);
        String str = this.filterProvince;
        if (!(str == null || str.length() == 0)) {
            ajaxParams.put("province_id", this.filterProvince);
        }
        String str2 = this.attr;
        if (!(str2 == null || str2.length() == 0)) {
            ajaxParams.put("attr", "");
        }
        String str3 = this.category;
        if (!(str3 == null || str3.length() == 0)) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, this.category);
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolSchoolList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolListFragment.getListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                SmartRefreshLayout smartRefreshLayout = ChooseSchoolListFragment.this.refresh;
                SmartRefreshLayout smartRefreshLayout2 = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh(false);
                SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolListFragment.this.refresh;
                if (smartRefreshLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                } else {
                    smartRefreshLayout2 = smartRefreshLayout3;
                }
                smartRefreshLayout2.finishLoadMoreWithNoMoreData();
                ChooseSchoolListFragment.this.setEmptyView(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                SmartRefreshLayout smartRefreshLayout = null;
                try {
                    if (ChooseSchoolListFragment.this.page == 1) {
                        SmartRefreshLayout smartRefreshLayout2 = ChooseSchoolListFragment.this.refresh;
                        if (smartRefreshLayout2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout2 = null;
                        }
                        smartRefreshLayout2.finishRefresh(true);
                    } else {
                        SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolListFragment.this.refresh;
                        if (smartRefreshLayout3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout3 = null;
                        }
                        smartRefreshLayout3.finishLoadMore();
                    }
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        SmartRefreshLayout smartRefreshLayout4 = ChooseSchoolListFragment.this.refresh;
                        if (smartRefreshLayout4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout4 = null;
                        }
                        smartRefreshLayout4.finishRefresh(false);
                        SmartRefreshLayout smartRefreshLayout5 = ChooseSchoolListFragment.this.refresh;
                        if (smartRefreshLayout5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout5 = null;
                        }
                        smartRefreshLayout5.finishLoadMoreWithNoMoreData();
                        ChooseSchoolListFragment.this.setEmptyView(true);
                        return;
                    }
                    List dataList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends ChooseSchoolSchoolListBean>>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolListFragment$getListData$1$onSuccess$dataList$1
                    }.getType());
                    ChooseSchoolListFragment.this.isLastPage = dataList == null || dataList.isEmpty();
                    if (ChooseSchoolListFragment.this.isLastPage) {
                        SmartRefreshLayout smartRefreshLayout6 = ChooseSchoolListFragment.this.refresh;
                        if (smartRefreshLayout6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout6 = null;
                        }
                        smartRefreshLayout6.finishLoadMoreWithNoMoreData();
                        if (ChooseSchoolListFragment.this.page == 1) {
                            ChooseSchoolListFragment.this.setEmptyView(true);
                        }
                    }
                    if (ChooseSchoolListFragment.this.page == 1) {
                        ChooseSchoolListAdapter chooseSchoolListAdapter = ChooseSchoolListFragment.this.adapter;
                        if (chooseSchoolListAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            chooseSchoolListAdapter = null;
                        }
                        chooseSchoolListAdapter.setList(dataList);
                        return;
                    }
                    ChooseSchoolListAdapter chooseSchoolListAdapter2 = ChooseSchoolListFragment.this.adapter;
                    if (chooseSchoolListAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                        chooseSchoolListAdapter2 = null;
                    }
                    Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                    chooseSchoolListAdapter2.addData((Collection) dataList);
                } catch (Exception unused) {
                    SmartRefreshLayout smartRefreshLayout7 = ChooseSchoolListFragment.this.refresh;
                    if (smartRefreshLayout7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                        smartRefreshLayout7 = null;
                    }
                    smartRefreshLayout7.finishRefresh(false);
                    SmartRefreshLayout smartRefreshLayout8 = ChooseSchoolListFragment.this.refresh;
                    if (smartRefreshLayout8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    } else {
                        smartRefreshLayout = smartRefreshLayout8;
                    }
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    ChooseSchoolListFragment.this.setEmptyView(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$1(ChooseSchoolListFragment this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        this$0.getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$2(ChooseSchoolListFragment this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        this$0.getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setEmptyView(boolean emptyData) {
        if (getActivity() != null) {
            CustomEmptyView customEmptyView = null;
            if (emptyData) {
                CustomEmptyView customEmptyView2 = this.emptyView;
                if (customEmptyView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    customEmptyView2 = null;
                }
                customEmptyView2.showEmptyView();
                ChooseSchoolListAdapter chooseSchoolListAdapter = this.adapter;
                if (chooseSchoolListAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    chooseSchoolListAdapter = null;
                }
                CustomEmptyView customEmptyView3 = this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView = customEmptyView3;
                }
                chooseSchoolListAdapter.setEmptyView(customEmptyView);
                return;
            }
            ChooseSchoolListAdapter chooseSchoolListAdapter2 = this.adapter;
            if (chooseSchoolListAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                chooseSchoolListAdapter2 = null;
            }
            chooseSchoolListAdapter2.setList(new ArrayList());
            CustomEmptyView customEmptyView4 = this.emptyView;
            if (customEmptyView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView4 = null;
            }
            customEmptyView4.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChooseSchoolListFragment.setEmptyView$lambda$3(this.f11266c, view);
                }
            });
            CustomEmptyView customEmptyView5 = this.emptyView;
            if (customEmptyView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView5 = null;
            }
            customEmptyView5.setLoadFileResUi(this.mContext);
            CustomEmptyView customEmptyView6 = this.emptyView;
            if (customEmptyView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView = customEmptyView6;
            }
            customEmptyView.setIsShowReloadBtn(true, "点击刷新页面");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setEmptyView$lambda$3(ChooseSchoolListFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.page = 1;
        this$0.getListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_choose_school_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.type = arguments.getString("type");
            this.major_id = arguments.getString("major_id");
            this.score = arguments.getString("score");
        }
        View view = holder.get(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.refresh)");
        this.refresh = (SmartRefreshLayout) view;
        View view2 = holder.get(R.id.recyclerView);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.recyclerView)");
        this.recyclerView = (RecyclerView) view2;
        this.adapter = new ChooseSchoolListAdapter();
        RecyclerView recyclerView = this.recyclerView;
        SmartRefreshLayout smartRefreshLayout = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ChooseSchoolListAdapter chooseSchoolListAdapter = this.adapter;
        if (chooseSchoolListAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolListAdapter = null;
        }
        recyclerView.setAdapter(chooseSchoolListAdapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        this.emptyView = customEmptyView;
        customEmptyView.changeEmptyViewNewBgTwoNewColor();
        ChooseSchoolListAdapter chooseSchoolListAdapter2 = this.adapter;
        if (chooseSchoolListAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolListAdapter2 = null;
        }
        chooseSchoolListAdapter2.setType(this.type);
        ChooseSchoolListAdapter chooseSchoolListAdapter3 = this.adapter;
        if (chooseSchoolListAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolListAdapter3 = null;
        }
        CustomEmptyView customEmptyView2 = this.emptyView;
        if (customEmptyView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView2 = null;
        }
        chooseSchoolListAdapter3.setEmptyView(customEmptyView2);
        SmartRefreshLayout smartRefreshLayout2 = this.refresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        smartRefreshLayout2.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.d
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ChooseSchoolListFragment.initViews$lambda$1(this.f11267c, refreshLayout);
            }
        });
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout3;
        }
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.e
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                ChooseSchoolListFragment.initViews$lambda$2(this.f11268c, refreshLayout);
            }
        });
        getListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(@Nullable String str) {
        super.onEventMainThread(str);
        if (Intrinsics.areEqual(EventBusConstant.EVENT_CHOOSE_SCHOOL_FILTER_CHANGE, str)) {
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity");
            List<String> selectFilterData = ((ChooseSchoolListActivity) activity).getSelectFilterData();
            this.filterProvince = selectFilterData.get(0);
            this.attr = selectFilterData.get(1);
            this.category = selectFilterData.get(2);
            getListData();
        }
    }
}
