package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolQuestionAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolQuestionBean;
import com.psychiatrygarden.bean.ChooseSchoolQuestionData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolQuestionFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "adapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolQuestionAdapter;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "getLayoutId", "", "getListData", "", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "setEmptyView", "emptyData", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionFragment extends BaseFragment {
    private ChooseSchoolQuestionAdapter adapter;
    private CustomEmptyView emptyView;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh;

    private final void getListData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolQuestionList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionFragment.getListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                SmartRefreshLayout smartRefreshLayout = ChooseSchoolQuestionFragment.this.refresh;
                SmartRefreshLayout smartRefreshLayout2 = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh(false);
                SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolQuestionFragment.this.refresh;
                if (smartRefreshLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                } else {
                    smartRefreshLayout2 = smartRefreshLayout3;
                }
                smartRefreshLayout2.finishLoadMoreWithNoMoreData();
                ChooseSchoolQuestionFragment.this.setEmptyView(false);
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
                    ChooseSchoolQuestionBean chooseSchoolQuestionBean = (ChooseSchoolQuestionBean) new Gson().fromJson(t2, ChooseSchoolQuestionBean.class);
                    SmartRefreshLayout smartRefreshLayout2 = ChooseSchoolQuestionFragment.this.refresh;
                    if (smartRefreshLayout2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                        smartRefreshLayout2 = null;
                    }
                    smartRefreshLayout2.finishRefresh(true);
                    if (Intrinsics.areEqual(chooseSchoolQuestionBean.getCode(), "200")) {
                        List<ChooseSchoolQuestionData> data = chooseSchoolQuestionBean.getData();
                        ChooseSchoolQuestionAdapter chooseSchoolQuestionAdapter = ChooseSchoolQuestionFragment.this.adapter;
                        if (chooseSchoolQuestionAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            chooseSchoolQuestionAdapter = null;
                        }
                        chooseSchoolQuestionAdapter.setList(data);
                        return;
                    }
                    SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolQuestionFragment.this.refresh;
                    if (smartRefreshLayout3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                        smartRefreshLayout3 = null;
                    }
                    smartRefreshLayout3.finishRefresh(false);
                    ChooseSchoolQuestionFragment.this.setEmptyView(true);
                } catch (Exception unused) {
                    SmartRefreshLayout smartRefreshLayout4 = ChooseSchoolQuestionFragment.this.refresh;
                    if (smartRefreshLayout4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    } else {
                        smartRefreshLayout = smartRefreshLayout4;
                    }
                    smartRefreshLayout.finishRefresh(false);
                    ChooseSchoolQuestionFragment.this.setEmptyView(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(ChooseSchoolQuestionFragment this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
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
                ChooseSchoolQuestionAdapter chooseSchoolQuestionAdapter = this.adapter;
                if (chooseSchoolQuestionAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    chooseSchoolQuestionAdapter = null;
                }
                CustomEmptyView customEmptyView3 = this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView = customEmptyView3;
                }
                chooseSchoolQuestionAdapter.setEmptyView(customEmptyView);
                return;
            }
            ChooseSchoolQuestionAdapter chooseSchoolQuestionAdapter2 = this.adapter;
            if (chooseSchoolQuestionAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                chooseSchoolQuestionAdapter2 = null;
            }
            chooseSchoolQuestionAdapter2.setList(new ArrayList());
            CustomEmptyView customEmptyView4 = this.emptyView;
            if (customEmptyView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView4 = null;
            }
            customEmptyView4.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChooseSchoolQuestionFragment.setEmptyView$lambda$1(this.f11272c, view);
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
    public static final void setEmptyView$lambda$1(ChooseSchoolQuestionFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_choose_school_question;
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
        this.emptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        ChooseSchoolQuestionAdapter chooseSchoolQuestionAdapter = new ChooseSchoolQuestionAdapter();
        this.adapter = chooseSchoolQuestionAdapter;
        CustomEmptyView customEmptyView = this.emptyView;
        SmartRefreshLayout smartRefreshLayout = null;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        chooseSchoolQuestionAdapter.setEmptyView(customEmptyView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ChooseSchoolQuestionAdapter chooseSchoolQuestionAdapter2 = this.adapter;
        if (chooseSchoolQuestionAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolQuestionAdapter2 = null;
        }
        recyclerView.setAdapter(chooseSchoolQuestionAdapter2);
        SmartRefreshLayout smartRefreshLayout2 = this.refresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        ViewGroup.LayoutParams layoutParams = smartRefreshLayout2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.topMargin = DpOrPxUtils.dip2px(getActivity(), 12.0f);
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout3 = null;
        }
        smartRefreshLayout3.setLayoutParams(marginLayoutParams);
        SmartRefreshLayout smartRefreshLayout4 = this.refresh;
        if (smartRefreshLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout4 = null;
        }
        smartRefreshLayout4.setEnableLoadMore(false);
        SmartRefreshLayout smartRefreshLayout5 = this.refresh;
        if (smartRefreshLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout5;
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.j
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ChooseSchoolQuestionFragment.initViews$lambda$0(this.f11273c, refreshLayout);
            }
        });
        getListData();
    }
}
