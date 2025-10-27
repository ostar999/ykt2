package com.psychiatrygarden.activity.chooseSchool;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseMajorAdapter;
import com.psychiatrygarden.bean.ChooseSchoolMajorItemBean;
import com.psychiatrygarden.bean.ChooseSchoolMajorListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u0019H\u0016J\u0012\u0010\u001c\u001a\u00020\u00192\b\u0010\u001d\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u001e\u001a\u00020\u0019H\u0016J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\nH\u0002J\b\u0010!\u001a\u00020\u0019H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseMajorActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "adapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseMajorAdapter;", "edSearch", "Lcom/psychiatrygarden/widget/ClearEditText;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "isLastPage", "", "ivActionbarBack", "Landroid/widget/ImageView;", "majorType", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "searchKey", "txtActionbarTitle", "Landroid/widget/TextView;", "getListData", "", "maJorType", "init", "onEventMainThread", "str", "setContentView", "setEmptyView", "emptyData", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChooseMajorActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChooseMajorActivity.kt\ncom/psychiatrygarden/activity/chooseSchool/ChooseMajorActivity\n+ 2 Strings.kt\nkotlin/text/StringsKt__StringsKt\n*L\n1#1,203:1\n107#2:204\n79#2,22:205\n*S KotlinDebug\n*F\n+ 1 ChooseMajorActivity.kt\ncom/psychiatrygarden/activity/chooseSchool/ChooseMajorActivity\n*L\n71#1:204\n71#1:205,22\n*E\n"})
/* loaded from: classes5.dex */
public final class ChooseMajorActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private ChooseMajorAdapter adapter;
    private ClearEditText edSearch;
    private CustomEmptyView emptyView;
    private boolean isLastPage;
    private ImageView ivActionbarBack;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh;
    private TextView txtActionbarTitle;
    private int page = 1;

    @Nullable
    private String majorType = "";

    @NotNull
    private String searchKey = "";

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseMajorActivity$Companion;", "", "()V", "navigationToChooseMajorActivity", "", com.umeng.analytics.pro.d.R, "Landroid/app/Activity;", "majorType", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToChooseMajorActivity(@NotNull Activity context, @NotNull String majorType) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(majorType, "majorType");
            Intent intent = new Intent(context, (Class<?>) ChooseMajorActivity.class);
            intent.putExtra("major_type", majorType);
            context.startActivityForResult(intent, 11);
        }
    }

    private final void getListData(String maJorType) {
        ClearEditText clearEditText = this.edSearch;
        if (clearEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edSearch");
            clearEditText = null;
        }
        this.searchKey = StringsKt__StringsKt.trim((CharSequence) clearEditText.getText().toString()).toString();
        AjaxParams ajaxParams = new AjaxParams();
        boolean z2 = true;
        if (this.searchKey.length() > 0) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.searchKey);
        }
        String str = this.majorType;
        if (str != null && str.length() != 0) {
            z2 = false;
        }
        if (z2) {
            return;
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("major_type", maJorType);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolMajorList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseMajorActivity.getListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                SmartRefreshLayout smartRefreshLayout = ChooseMajorActivity.this.refresh;
                SmartRefreshLayout smartRefreshLayout2 = null;
                if (smartRefreshLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    smartRefreshLayout = null;
                }
                smartRefreshLayout.finishRefresh(false);
                SmartRefreshLayout smartRefreshLayout3 = ChooseMajorActivity.this.refresh;
                if (smartRefreshLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                } else {
                    smartRefreshLayout2 = smartRefreshLayout3;
                }
                smartRefreshLayout2.finishLoadMoreWithNoMoreData();
                ChooseMajorActivity.this.setEmptyView(false);
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
                    ChooseSchoolMajorListBean chooseSchoolMajorListBean = (ChooseSchoolMajorListBean) new Gson().fromJson(t2, ChooseSchoolMajorListBean.class);
                    if (ChooseMajorActivity.this.page == 1) {
                        SmartRefreshLayout smartRefreshLayout2 = ChooseMajorActivity.this.refresh;
                        if (smartRefreshLayout2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout2 = null;
                        }
                        smartRefreshLayout2.finishRefresh(true);
                    } else {
                        SmartRefreshLayout smartRefreshLayout3 = ChooseMajorActivity.this.refresh;
                        if (smartRefreshLayout3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout3 = null;
                        }
                        smartRefreshLayout3.finishLoadMore();
                    }
                    if (!Intrinsics.areEqual(chooseSchoolMajorListBean.getCode(), "200")) {
                        SmartRefreshLayout smartRefreshLayout4 = ChooseMajorActivity.this.refresh;
                        if (smartRefreshLayout4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout4 = null;
                        }
                        smartRefreshLayout4.finishRefresh(false);
                        SmartRefreshLayout smartRefreshLayout5 = ChooseMajorActivity.this.refresh;
                        if (smartRefreshLayout5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            smartRefreshLayout5 = null;
                        }
                        smartRefreshLayout5.finishLoadMoreWithNoMoreData();
                        ChooseMajorActivity.this.setEmptyView(true);
                        return;
                    }
                    List<ChooseSchoolMajorItemBean> data = chooseSchoolMajorListBean.getData();
                    ChooseMajorActivity.this.isLastPage = data == null || data.isEmpty();
                    if (ChooseMajorActivity.this.isLastPage) {
                        if (ChooseMajorActivity.this.page == 1) {
                            ChooseMajorActivity.this.setEmptyView(true);
                        }
                        ChooseMajorAdapter chooseMajorAdapter = ChooseMajorActivity.this.adapter;
                        if (chooseMajorAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            chooseMajorAdapter = null;
                        }
                        chooseMajorAdapter.setList(new ArrayList());
                        return;
                    }
                    if (ChooseMajorActivity.this.page == 1) {
                        ChooseMajorAdapter chooseMajorAdapter2 = ChooseMajorActivity.this.adapter;
                        if (chooseMajorAdapter2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            chooseMajorAdapter2 = null;
                        }
                        Intrinsics.checkNotNull(data);
                        chooseMajorAdapter2.setList(data);
                        return;
                    }
                    ChooseMajorAdapter chooseMajorAdapter3 = ChooseMajorActivity.this.adapter;
                    if (chooseMajorAdapter3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                        chooseMajorAdapter3 = null;
                    }
                    Intrinsics.checkNotNull(data);
                    chooseMajorAdapter3.addData((Collection) data);
                } catch (Exception unused) {
                    SmartRefreshLayout smartRefreshLayout6 = ChooseMajorActivity.this.refresh;
                    if (smartRefreshLayout6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                        smartRefreshLayout6 = null;
                    }
                    smartRefreshLayout6.finishRefresh(false);
                    SmartRefreshLayout smartRefreshLayout7 = ChooseMajorActivity.this.refresh;
                    if (smartRefreshLayout7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                    } else {
                        smartRefreshLayout = smartRefreshLayout7;
                    }
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    ChooseMajorActivity.this.setEmptyView(false);
                }
            }
        });
    }

    public static /* synthetic */ void getListData$default(ChooseMajorActivity chooseMajorActivity, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        chooseMajorActivity.getListData(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(ChooseMajorActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean init$lambda$3(ChooseMajorActivity this$0, TextView textView, int i2, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i2 != 3) {
            return false;
        }
        ClearEditText clearEditText = this$0.edSearch;
        if (clearEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edSearch");
            clearEditText = null;
        }
        String string = clearEditText.getText().toString();
        int length = string.length() - 1;
        int i3 = 0;
        boolean z2 = false;
        while (i3 <= length) {
            boolean z3 = Intrinsics.compare((int) string.charAt(!z2 ? i3 : length), 32) <= 0;
            if (z2) {
                if (!z3) {
                    break;
                }
                length--;
            } else if (z3) {
                i3++;
            } else {
                z2 = true;
            }
        }
        this$0.searchKey = string.subSequence(i3, length + 1).toString();
        this$0.getListData(this$0.majorType);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$4(ChooseMajorActivity this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        this$0.getListData(this$0.majorType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setEmptyView(boolean emptyData) {
        CustomEmptyView customEmptyView = null;
        if (emptyData) {
            CustomEmptyView customEmptyView2 = this.emptyView;
            if (customEmptyView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView2 = null;
            }
            customEmptyView2.showEmptyView();
            ChooseMajorAdapter chooseMajorAdapter = this.adapter;
            if (chooseMajorAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                chooseMajorAdapter = null;
            }
            CustomEmptyView customEmptyView3 = this.emptyView;
            if (customEmptyView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView = customEmptyView3;
            }
            chooseMajorAdapter.setEmptyView(customEmptyView);
            return;
        }
        ChooseMajorAdapter chooseMajorAdapter2 = this.adapter;
        if (chooseMajorAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseMajorAdapter2 = null;
        }
        chooseMajorAdapter2.setList(new ArrayList());
        CustomEmptyView customEmptyView4 = this.emptyView;
        if (customEmptyView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView4 = null;
        }
        customEmptyView4.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseMajorActivity.setEmptyView$lambda$5(this.f11324c, view);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setEmptyView$lambda$5(ChooseMajorActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.page = 1;
        this$0.getListData(this$0.majorType);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Intent intent = getIntent();
        if (intent != null) {
            this.majorType = intent.getStringExtra("major_type");
        }
        View viewFindViewById = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.iv_actionbar_back)");
        ImageView imageView = (ImageView) viewFindViewById;
        this.ivActionbarBack = imageView;
        SmartRefreshLayout smartRefreshLayout = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivActionbarBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseMajorActivity.init$lambda$1(this.f11333c, view);
            }
        });
        View viewFindViewById2 = findViewById(R.id.txt_actionbar_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.txt_actionbar_title)");
        TextView textView = (TextView) viewFindViewById2;
        this.txtActionbarTitle = textView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtActionbarTitle");
            textView = null;
        }
        textView.setText("专业");
        View viewFindViewById3 = findViewById(R.id.ed_search);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ed_search)");
        ClearEditText clearEditText = (ClearEditText) viewFindViewById3;
        this.edSearch = clearEditText;
        if (clearEditText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edSearch");
            clearEditText = null;
        }
        clearEditText.setHint("输入关键词搜索课程");
        ClearEditText clearEditText2 = this.edSearch;
        if (clearEditText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("edSearch");
            clearEditText2 = null;
        }
        clearEditText2.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return ChooseMajorActivity.init$lambda$3(this.f11341c, textView2, i2, keyEvent);
            }
        });
        View viewFindViewById4 = findViewById(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.refresh)");
        SmartRefreshLayout smartRefreshLayout2 = (SmartRefreshLayout) viewFindViewById4;
        this.refresh = smartRefreshLayout2;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        smartRefreshLayout2.setEnableLoadMore(false);
        View viewFindViewById5 = findViewById(R.id.recyclerView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.recyclerView)");
        this.recyclerView = (RecyclerView) viewFindViewById5;
        this.adapter = new ChooseMajorAdapter(0);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ChooseMajorAdapter chooseMajorAdapter = this.adapter;
        if (chooseMajorAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseMajorAdapter = null;
        }
        recyclerView.setAdapter(chooseMajorAdapter);
        this.emptyView = new CustomEmptyView(this, 0, "暂无数据");
        ChooseMajorAdapter chooseMajorAdapter2 = this.adapter;
        if (chooseMajorAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseMajorAdapter2 = null;
        }
        CustomEmptyView customEmptyView = this.emptyView;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        chooseMajorAdapter2.setEmptyView(customEmptyView);
        ChooseMajorAdapter chooseMajorAdapter3 = this.adapter;
        if (chooseMajorAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            chooseMajorAdapter3 = null;
        }
        chooseMajorAdapter3.setItemClick(new Function2<String, String, Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseMajorActivity.init.4
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2) {
                invoke2(str, str2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable String str, @Nullable String str2) {
                Intent intent2 = new Intent();
                intent2.putExtra("major_id", str);
                intent2.putExtra("major_title", str2);
                ChooseMajorActivity.this.setResult(-1, intent2);
                ChooseMajorActivity.this.finish();
            }
        });
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout3;
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ChooseMajorActivity.init$lambda$4(this.f11349c, refreshLayout);
            }
        });
        getListData(this.majorType);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_choose_major);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
