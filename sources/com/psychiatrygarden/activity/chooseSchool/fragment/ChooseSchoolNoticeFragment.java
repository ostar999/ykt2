package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolNoticeAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolNoticeBean;
import com.psychiatrygarden.bean.ChooseSchoolNoticeData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\nH\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u001a\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolNoticeFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "adapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolNoticeAdapter;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "isLastPage", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "getLayoutId", "getListData", "", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "setEmptyView", "emptyData", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolNoticeFragment extends BaseFragment {
    private ChooseSchoolNoticeAdapter adapter;
    private CustomEmptyView emptyView;
    private boolean isLastPage;
    private int page = 1;
    private final int pageSize = 20;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh;

    private final void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("page_size", String.valueOf(this.pageSize));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolNoticeList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolNoticeFragment.getListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                SmartRefreshLayout smartRefreshLayout = ChooseSchoolNoticeFragment.this.refresh;
                SmartRefreshLayout smartRefreshLayout2 = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh(false);
                SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolNoticeFragment.this.refresh;
                if (smartRefreshLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                } else {
                    smartRefreshLayout2 = smartRefreshLayout3;
                }
                smartRefreshLayout2.finishLoadMoreWithNoMoreData();
                ChooseSchoolNoticeFragment.this.setEmptyView(false);
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
                    ChooseSchoolNoticeBean chooseSchoolNoticeBean = (ChooseSchoolNoticeBean) new Gson().fromJson(t2, ChooseSchoolNoticeBean.class);
                    if (ChooseSchoolNoticeFragment.this.page == 1) {
                        SmartRefreshLayout smartRefreshLayout2 = ChooseSchoolNoticeFragment.this.refresh;
                        if (smartRefreshLayout2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout2 = null;
                        }
                        smartRefreshLayout2.finishRefresh(true);
                    } else {
                        SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolNoticeFragment.this.refresh;
                        if (smartRefreshLayout3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout3 = null;
                        }
                        smartRefreshLayout3.finishLoadMore();
                    }
                    if (!Intrinsics.areEqual(chooseSchoolNoticeBean.getCode(), "200")) {
                        SmartRefreshLayout smartRefreshLayout4 = ChooseSchoolNoticeFragment.this.refresh;
                        if (smartRefreshLayout4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout4 = null;
                        }
                        smartRefreshLayout4.finishRefresh(false);
                        SmartRefreshLayout smartRefreshLayout5 = ChooseSchoolNoticeFragment.this.refresh;
                        if (smartRefreshLayout5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout5 = null;
                        }
                        smartRefreshLayout5.finishLoadMoreWithNoMoreData();
                        ChooseSchoolNoticeFragment.this.setEmptyView(true);
                        return;
                    }
                    List<ChooseSchoolNoticeData> data = chooseSchoolNoticeBean.getData();
                    ChooseSchoolNoticeFragment.this.isLastPage = data == null || data.isEmpty() || data.size() < ChooseSchoolNoticeFragment.this.pageSize;
                    if (ChooseSchoolNoticeFragment.this.isLastPage) {
                        SmartRefreshLayout smartRefreshLayout6 = ChooseSchoolNoticeFragment.this.refresh;
                        if (smartRefreshLayout6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout6 = null;
                        }
                        smartRefreshLayout6.finishLoadMoreWithNoMoreData();
                        if (ChooseSchoolNoticeFragment.this.page == 1) {
                            ChooseSchoolNoticeFragment.this.setEmptyView(true);
                        }
                    }
                    if (ChooseSchoolNoticeFragment.this.page == 1) {
                        ChooseSchoolNoticeAdapter chooseSchoolNoticeAdapter = ChooseSchoolNoticeFragment.this.adapter;
                        if (chooseSchoolNoticeAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            chooseSchoolNoticeAdapter = null;
                        }
                        chooseSchoolNoticeAdapter.setList(data);
                        return;
                    }
                    ChooseSchoolNoticeAdapter chooseSchoolNoticeAdapter2 = ChooseSchoolNoticeFragment.this.adapter;
                    if (chooseSchoolNoticeAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                        chooseSchoolNoticeAdapter2 = null;
                    }
                    Intrinsics.checkNotNull(data);
                    chooseSchoolNoticeAdapter2.addData((Collection) data);
                } catch (Exception unused) {
                    SmartRefreshLayout smartRefreshLayout7 = ChooseSchoolNoticeFragment.this.refresh;
                    if (smartRefreshLayout7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                        smartRefreshLayout7 = null;
                    }
                    smartRefreshLayout7.finishRefresh(false);
                    SmartRefreshLayout smartRefreshLayout8 = ChooseSchoolNoticeFragment.this.refresh;
                    if (smartRefreshLayout8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    } else {
                        smartRefreshLayout = smartRefreshLayout8;
                    }
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    ChooseSchoolNoticeFragment.this.setEmptyView(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(ChooseSchoolNoticeFragment this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        this$0.getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$1(ChooseSchoolNoticeFragment this$0, RefreshLayout it) {
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
                ChooseSchoolNoticeAdapter chooseSchoolNoticeAdapter = this.adapter;
                if (chooseSchoolNoticeAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    chooseSchoolNoticeAdapter = null;
                }
                CustomEmptyView customEmptyView3 = this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView = customEmptyView3;
                }
                chooseSchoolNoticeAdapter.setEmptyView(customEmptyView);
                return;
            }
            ChooseSchoolNoticeAdapter chooseSchoolNoticeAdapter2 = this.adapter;
            if (chooseSchoolNoticeAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                chooseSchoolNoticeAdapter2 = null;
            }
            chooseSchoolNoticeAdapter2.setList(new ArrayList());
            CustomEmptyView customEmptyView4 = this.emptyView;
            if (customEmptyView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView4 = null;
            }
            customEmptyView4.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChooseSchoolNoticeFragment.setEmptyView$lambda$3(this.f11271c, view);
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
    public static final void setEmptyView$lambda$3(ChooseSchoolNoticeFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.page = 1;
        this$0.getListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_choose_school_notice;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.refresh)");
        this.refresh = (SmartRefreshLayout) view;
        View view2 = holder.get(R.id.recyclerView);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.recyclerView)");
        this.recyclerView = (RecyclerView) view2;
        this.adapter = new ChooseSchoolNoticeAdapter();
        RecyclerView recyclerView = this.recyclerView;
        SmartRefreshLayout smartRefreshLayout = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ChooseSchoolNoticeAdapter chooseSchoolNoticeAdapter = this.adapter;
        if (chooseSchoolNoticeAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolNoticeAdapter = null;
        }
        recyclerView.setAdapter(chooseSchoolNoticeAdapter);
        SmartRefreshLayout smartRefreshLayout2 = this.refresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        ViewGroup.LayoutParams layoutParams = smartRefreshLayout2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.topMargin = CommonUtil.dip2px(getActivity(), 12.0f);
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout3 = null;
        }
        smartRefreshLayout3.setLayoutParams(marginLayoutParams);
        this.emptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        ChooseSchoolNoticeAdapter chooseSchoolNoticeAdapter2 = this.adapter;
        if (chooseSchoolNoticeAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolNoticeAdapter2 = null;
        }
        CustomEmptyView customEmptyView = this.emptyView;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        chooseSchoolNoticeAdapter2.setEmptyView(customEmptyView);
        SmartRefreshLayout smartRefreshLayout4 = this.refresh;
        if (smartRefreshLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout4 = null;
        }
        smartRefreshLayout4.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.f
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ChooseSchoolNoticeFragment.initViews$lambda$0(this.f11269c, refreshLayout);
            }
        });
        SmartRefreshLayout smartRefreshLayout5 = this.refresh;
        if (smartRefreshLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout5;
        }
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.g
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                ChooseSchoolNoticeFragment.initViews$lambda$1(this.f11270c, refreshLayout);
            }
        });
        getListData();
    }
}
