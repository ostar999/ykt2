package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.bean.HomeInfoItemBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u00007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007*\u0001\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0012\u0010\u0013\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0015\u001a\u00020\u0011H\u0016J\b\u0010\u0016\u001a\u00020\u0011H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/activity/HomeInfoListAct;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "hasMore", "", "isLoading", "mAdapter", "com/psychiatrygarden/activity/HomeInfoListAct$mAdapter$1", "Lcom/psychiatrygarden/activity/HomeInfoListAct$mAdapter$1;", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "totalCount", "type", "", "init", "", "loadInfoData", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class HomeInfoListAct extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private boolean hasMore;
    private boolean isLoading;
    private RecyclerView rvList;
    private int totalCount;
    private int page = 1;

    @NotNull
    private String type = "1";

    @NotNull
    private final HomeInfoListAct$mAdapter$1 mAdapter = new HomeInfoListAct$mAdapter$1(this);

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/HomeInfoListAct$Companion;", "", "()V", "navigationToHomeInfoListActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "type", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToHomeInfoListActivity(@NotNull Context context, @NotNull String type) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(type, "type");
            Intent intent = new Intent(context, (Class<?>) HomeInfoListAct.class);
            intent.putExtra("type", type);
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadInfoData() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.newHomeInfo;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("type", this.type);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HomeInfoListAct.loadInfoData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (HomeInfoListAct.this.page > 1) {
                    HomeInfoListAct homeInfoListAct = HomeInfoListAct.this;
                    homeInfoListAct.page--;
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        if (HomeInfoListAct.this.page > 1) {
                            HomeInfoListAct homeInfoListAct = HomeInfoListAct.this;
                            homeInfoListAct.page--;
                            return;
                        }
                        return;
                    }
                    List dataList = (List) new Gson().fromJson(jSONObject.optJSONObject("data").optString("list"), new TypeToken<List<? extends HomeInfoItemBean>>() { // from class: com.psychiatrygarden.activity.HomeInfoListAct$loadInfoData$2$onSuccess$dataList$1
                    }.getType());
                    List list = dataList;
                    HomeInfoListAct.this.hasMore = !(list == null || list.isEmpty());
                    List list2 = dataList;
                    if (list2 == null || list2.isEmpty()) {
                        return;
                    }
                    List<HomeInfoItemBean> data = HomeInfoListAct.this.mAdapter.getData();
                    if (data == null || data.isEmpty()) {
                        HomeInfoListAct.this.mAdapter.setList(dataList);
                        return;
                    }
                    HomeInfoListAct$mAdapter$1 homeInfoListAct$mAdapter$1 = HomeInfoListAct.this.mAdapter;
                    Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                    homeInfoListAct$mAdapter$1.addData((Collection) dataList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (HomeInfoListAct.this.page > 1) {
                        HomeInfoListAct homeInfoListAct2 = HomeInfoListAct.this;
                        homeInfoListAct2.page--;
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("type");
        if (stringExtra == null) {
            stringExtra = "1";
        }
        this.type = stringExtra;
        View viewFindViewById = findViewById(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvList)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        this.rvList = recyclerView;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvList");
            recyclerView = null;
        }
        recyclerView.setAdapter(this.mAdapter);
        RecyclerView recyclerView3 = this.rvList;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvList");
        } else {
            recyclerView2 = recyclerView3;
        }
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.HomeInfoListAct.init.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NotNull RecyclerView recyclerView4, int dx, int dy) {
                Intrinsics.checkNotNullParameter(recyclerView4, "recyclerView");
                super.onScrolled(recyclerView4, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView4.getLayoutManager();
                Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
                if (((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() + 3 < HomeInfoListAct.this.mAdapter.getData().size() || !HomeInfoListAct.this.hasMore || HomeInfoListAct.this.isLoading) {
                    return;
                }
                LogUtils.d("onScrolled", "预加载数据");
                HomeInfoListAct.this.page++;
                HomeInfoListAct.this.isLoading = true;
                HomeInfoListAct.this.loadInfoData();
            }
        });
        loadInfoData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_home_info_lsit);
        setTitle("推荐资讯");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
