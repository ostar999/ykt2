package com.psychiatrygarden.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.adapter.StoreListAdapter;
import com.psychiatrygarden.bean.ShopInfoBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001cH\u0002J\b\u0010\u001f\u001a\u00020\u0019H\u0016J\b\u0010 \u001a\u00020\u0019H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0014j\b\u0012\u0004\u0012\u00020\u0015`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/activity/GoodsSearchAct;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "etSearch", "Lcom/psychiatrygarden/widget/ClearEditText;", "hasMoreData", "", "isLoading", "lastLoadTime", "", "lvGoods", "Landroid/widget/ListView;", "mAdapter", "Lcom/psychiatrygarden/adapter/StoreListAdapter;", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", "preloadThreshold", "resultList", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/ShopInfoBean$DataBean;", "Lkotlin/collections/ArrayList;", "throttleTime", "init", "", "onEventMainThread", "str", "", "search", "text", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class GoodsSearchAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private ClearEditText etSearch;
    private boolean hasMoreData;
    private boolean isLoading;
    private long lastLoadTime;
    private ListView lvGoods;
    private StoreListAdapter mAdapter;

    @NotNull
    private final ArrayList<ShopInfoBean.DataBean> resultList = new ArrayList<>();
    private int preloadThreshold = 3;
    private int throttleTime = 500;
    private int page = 1;

    /* JADX INFO: Access modifiers changed from: private */
    public final void search(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, text);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("page_size", "20");
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this));
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        YJYHttpUtils.get(this, NetworkRequestsURL.goodsSearch, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.GoodsSearchAct.search.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (GoodsSearchAct.this.page > 1) {
                    GoodsSearchAct goodsSearchAct = GoodsSearchAct.this;
                    goodsSearchAct.page--;
                }
                if (GoodsSearchAct.this.isLoading) {
                    GoodsSearchAct.this.isLoading = false;
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                boolean z2 = false;
                if (GoodsSearchAct.this.isLoading) {
                    GoodsSearchAct.this.isLoading = false;
                }
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        if (GoodsSearchAct.this.page > 1) {
                            GoodsSearchAct goodsSearchAct = GoodsSearchAct.this;
                            goodsSearchAct.page--;
                            return;
                        }
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends ShopInfoBean.DataBean>>() { // from class: com.psychiatrygarden.activity.GoodsSearchAct$search$1$onSuccess$dataList$1
                    }.getType());
                    List list2 = list;
                    StoreListAdapter storeListAdapter = null;
                    CustomEmptyView customEmptyView = null;
                    StoreListAdapter storeListAdapter2 = null;
                    if ((list2 == null || list2.isEmpty()) == true) {
                        StoreListAdapter storeListAdapter3 = GoodsSearchAct.this.mAdapter;
                        if (storeListAdapter3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            storeListAdapter3 = null;
                        }
                        if (storeListAdapter3.getCount() == 0) {
                            CustomEmptyView customEmptyView2 = GoodsSearchAct.this.emptyView;
                            if (customEmptyView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                            } else {
                                customEmptyView = customEmptyView2;
                            }
                            ViewExtensionsKt.visible(customEmptyView);
                        } else {
                            if (GoodsSearchAct.this.page == 1) {
                                GoodsSearchAct.this.resultList.clear();
                            }
                            CustomEmptyView customEmptyView3 = GoodsSearchAct.this.emptyView;
                            if (customEmptyView3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                                customEmptyView3 = null;
                            }
                            ViewExtensionsKt.gone(customEmptyView3);
                            StoreListAdapter storeListAdapter4 = GoodsSearchAct.this.mAdapter;
                            if (storeListAdapter4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            } else {
                                storeListAdapter2 = storeListAdapter4;
                            }
                            storeListAdapter2.notifyDataSetChanged();
                        }
                    } else {
                        StoreListAdapter storeListAdapter5 = GoodsSearchAct.this.mAdapter;
                        if (storeListAdapter5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            storeListAdapter5 = null;
                        }
                        if (storeListAdapter5.getCount() == 0 || GoodsSearchAct.this.page == 1) {
                            CustomEmptyView customEmptyView4 = GoodsSearchAct.this.emptyView;
                            if (customEmptyView4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                                customEmptyView4 = null;
                            }
                            ViewExtensionsKt.gone(customEmptyView4);
                            GoodsSearchAct.this.resultList.clear();
                        }
                        GoodsSearchAct.this.resultList.addAll(list);
                        StoreListAdapter storeListAdapter6 = GoodsSearchAct.this.mAdapter;
                        if (storeListAdapter6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        } else {
                            storeListAdapter = storeListAdapter6;
                        }
                        storeListAdapter.notifyDataSetChanged();
                    }
                    GoodsSearchAct goodsSearchAct2 = GoodsSearchAct.this;
                    List list3 = list;
                    if ((list3 == null || list3.isEmpty()) == false && list.size() >= 5) {
                        z2 = true;
                    }
                    goodsSearchAct2.hasMoreData = z2;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (GoodsSearchAct.this.page > 1) {
                        GoodsSearchAct goodsSearchAct3 = GoodsSearchAct.this;
                        goodsSearchAct3.page--;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setContentView$lambda$0(GoodsSearchAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(GoodsSearchAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (CommonUtil.isFastClick()) {
            return;
        }
        ClearEditText clearEditText = this$0.etSearch;
        ClearEditText clearEditText2 = null;
        if (clearEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etSearch");
            clearEditText = null;
        }
        if (TextUtils.isEmpty(clearEditText.getText())) {
            NewToast.showShort(this$0.mContext, "请输入关键词");
            return;
        }
        StoreListAdapter storeListAdapter = this$0.mAdapter;
        if (storeListAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            storeListAdapter = null;
        }
        ClearEditText clearEditText3 = this$0.etSearch;
        if (clearEditText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etSearch");
            clearEditText3 = null;
        }
        storeListAdapter.setSearchWord(clearEditText3.getText().toString());
        this$0.page = 1;
        ClearEditText clearEditText4 = this$0.etSearch;
        if (clearEditText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etSearch");
        } else {
            clearEditText2 = clearEditText4;
        }
        this$0.search(clearEditText2.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setListenerForWidget$lambda$2(GoodsSearchAct this$0, TextView textView, int i2, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i2 == 3) {
            this$0.findViewById(R.id.btn_search).performClick();
        }
        return i2 == 84;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_goods_search);
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.hide();
        }
        this.mAdapter = new StoreListAdapter(this, this.resultList, true);
        View viewFindViewById = findViewById(R.id.lvGoods);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.lvGoods)");
        this.lvGoods = (ListView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.empty_view)");
        CustomEmptyView customEmptyView = (CustomEmptyView) viewFindViewById2;
        this.emptyView = customEmptyView;
        ListView listView = null;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        customEmptyView.stopAnim();
        View viewFindViewById3 = findViewById(R.id.ed_search);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ed_search)");
        this.etSearch = (ClearEditText) viewFindViewById3;
        findViewById(R.id.iv_actionbar_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.lb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GoodsSearchAct.setContentView$lambda$0(this.f12660c, view);
            }
        });
        ListView listView2 = this.lvGoods;
        if (listView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lvGoods");
            listView2 = null;
        }
        StoreListAdapter storeListAdapter = this.mAdapter;
        if (storeListAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            storeListAdapter = null;
        }
        listView2.setAdapter((ListAdapter) storeListAdapter);
        ListView listView3 = this.lvGoods;
        if (listView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lvGoods");
        } else {
            listView = listView3;
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.activity.GoodsSearchAct.setContentView.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(@Nullable AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((totalItemCount - ((firstVisibleItem + visibleItemCount) - 1)) - 1 > GoodsSearchAct.this.preloadThreshold || GoodsSearchAct.this.isLoading || !GoodsSearchAct.this.hasMoreData) {
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - GoodsSearchAct.this.lastLoadTime > GoodsSearchAct.this.throttleTime) {
                    GoodsSearchAct.this.lastLoadTime = jCurrentTimeMillis;
                    GoodsSearchAct.this.isLoading = true;
                    GoodsSearchAct.this.page++;
                    GoodsSearchAct goodsSearchAct = GoodsSearchAct.this;
                    ClearEditText clearEditText = goodsSearchAct.etSearch;
                    if (clearEditText == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("etSearch");
                        clearEditText = null;
                    }
                    goodsSearchAct.search(clearEditText.getText().toString());
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(@Nullable AbsListView view, int scrollState) {
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ClearEditText clearEditText = this.etSearch;
        ClearEditText clearEditText2 = null;
        if (clearEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etSearch");
            clearEditText = null;
        }
        clearEditText.setHint("输入关键词搜索商品");
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GoodsSearchAct.setListenerForWidget$lambda$1(this.f12760c, view);
            }
        });
        ClearEditText clearEditText3 = this.etSearch;
        if (clearEditText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etSearch");
        } else {
            clearEditText2 = clearEditText3;
        }
        clearEditText2.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.nb
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return GoodsSearchAct.setListenerForWidget$lambda$2(this.f13042c, textView, i2, keyEvent);
            }
        });
    }
}
