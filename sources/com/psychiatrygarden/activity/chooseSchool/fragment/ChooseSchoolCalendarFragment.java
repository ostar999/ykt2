package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolCalendarAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolCalendarListBean;
import com.psychiatrygarden.bean.ChooseSchoolCalendarListItemBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u001a\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u0012\u0010\u0016\u001a\u00020\u00102\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u00102\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolCalendarFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "adapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolCalendarAdapter;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "type", "", "getLayoutId", "", "getListData", "", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onLazyInitView", "savedInstanceState", "Landroid/os/Bundle;", "setAdapterData", "dataList", "", "Lcom/psychiatrygarden/bean/ChooseSchoolCalendarListBean;", "setEmptyView", "emptyData", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolCalendarFragment extends BaseFragment {
    private ChooseSchoolCalendarAdapter adapter;
    private CustomEmptyView emptyView;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh;

    @Nullable
    private String type;

    private final void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", this.type);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolCalendar, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolCalendarFragment.getListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                SmartRefreshLayout smartRefreshLayout = ChooseSchoolCalendarFragment.this.refresh;
                SmartRefreshLayout smartRefreshLayout2 = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh(false);
                SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolCalendarFragment.this.refresh;
                if (smartRefreshLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                } else {
                    smartRefreshLayout2 = smartRefreshLayout3;
                }
                smartRefreshLayout2.finishLoadMoreWithNoMoreData();
                ChooseSchoolCalendarFragment.this.setEmptyView(false);
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
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        SmartRefreshLayout smartRefreshLayout2 = ChooseSchoolCalendarFragment.this.refresh;
                        if (smartRefreshLayout2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout2 = null;
                        }
                        smartRefreshLayout2.finishRefresh(false);
                        ChooseSchoolCalendarFragment.this.setEmptyView(true);
                        return;
                    }
                    ChooseSchoolCalendarFragment.this.setAdapterData((List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends ChooseSchoolCalendarListBean>>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolCalendarFragment$getListData$1$onSuccess$dataBean$1
                    }.getType()));
                    SmartRefreshLayout smartRefreshLayout3 = ChooseSchoolCalendarFragment.this.refresh;
                    if (smartRefreshLayout3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                        smartRefreshLayout3 = null;
                    }
                    smartRefreshLayout3.finishRefresh(true);
                } catch (Exception unused) {
                    SmartRefreshLayout smartRefreshLayout4 = ChooseSchoolCalendarFragment.this.refresh;
                    if (smartRefreshLayout4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    } else {
                        smartRefreshLayout = smartRefreshLayout4;
                    }
                    smartRefreshLayout.finishRefresh(false);
                    ChooseSchoolCalendarFragment.this.setEmptyView(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(ChooseSchoolCalendarFragment this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAdapterData(List<ChooseSchoolCalendarListBean> dataList) {
        ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter = null;
        if (dataList == null) {
            ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter2 = this.adapter;
            if (chooseSchoolCalendarAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
            } else {
                chooseSchoolCalendarAdapter = chooseSchoolCalendarAdapter2;
            }
            chooseSchoolCalendarAdapter.setList(new ArrayList());
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (ChooseSchoolCalendarListBean chooseSchoolCalendarListBean : dataList) {
            arrayList.add(new ChooseSchoolCalendarListItemBean(chooseSchoolCalendarListBean.getTag(), chooseSchoolCalendarListBean.getTag(), chooseSchoolCalendarListBean.getTag(), chooseSchoolCalendarListBean.getTag(), null, 0));
            List<ChooseSchoolCalendarListItemBean> list = chooseSchoolCalendarListBean.getList();
            if (!(list == null || list.isEmpty())) {
                Iterator<ChooseSchoolCalendarListItemBean> it = chooseSchoolCalendarListBean.getList().iterator();
                while (it.hasNext()) {
                    it.next().setItemType(1);
                }
                arrayList.addAll(chooseSchoolCalendarListBean.getList());
            }
        }
        ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter3 = this.adapter;
        if (chooseSchoolCalendarAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            chooseSchoolCalendarAdapter = chooseSchoolCalendarAdapter3;
        }
        chooseSchoolCalendarAdapter.setList(arrayList);
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
                ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter = this.adapter;
                if (chooseSchoolCalendarAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    chooseSchoolCalendarAdapter = null;
                }
                CustomEmptyView customEmptyView3 = this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView = customEmptyView3;
                }
                chooseSchoolCalendarAdapter.setEmptyView(customEmptyView);
                return;
            }
            ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter2 = this.adapter;
            if (chooseSchoolCalendarAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                chooseSchoolCalendarAdapter2 = null;
            }
            chooseSchoolCalendarAdapter2.setList(new ArrayList());
            CustomEmptyView customEmptyView4 = this.emptyView;
            if (customEmptyView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView4 = null;
            }
            customEmptyView4.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChooseSchoolCalendarFragment.setEmptyView$lambda$1(this.f11265c, view);
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
    public static final void setEmptyView$lambda$1(ChooseSchoolCalendarFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_choose_school_calendar;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Bundle arguments = getArguments();
        SmartRefreshLayout smartRefreshLayout = null;
        this.type = arguments != null ? arguments.getString("type") : null;
        View view = holder.get(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.refresh)");
        this.refresh = (SmartRefreshLayout) view;
        View view2 = holder.get(R.id.recyclerView);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.recyclerView)");
        this.recyclerView = (RecyclerView) view2;
        this.emptyView = new CustomEmptyView(getActivity(), 0, "暂无数据");
        ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter = new ChooseSchoolCalendarAdapter();
        this.adapter = chooseSchoolCalendarAdapter;
        CustomEmptyView customEmptyView = this.emptyView;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        chooseSchoolCalendarAdapter.setEmptyView(customEmptyView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ChooseSchoolCalendarAdapter chooseSchoolCalendarAdapter2 = this.adapter;
        if (chooseSchoolCalendarAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseSchoolCalendarAdapter2 = null;
        }
        recyclerView.setAdapter(chooseSchoolCalendarAdapter2);
        SmartRefreshLayout smartRefreshLayout2 = this.refresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        smartRefreshLayout2.setEnableLoadMore(false);
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout3;
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.a
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ChooseSchoolCalendarFragment.initViews$lambda$0(this.f11263c, refreshLayout);
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getListData();
    }
}
