package com.psychiatrygarden.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0014J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0015H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/forum/CircleHotSearchRankFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "codeType", "", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "mAdapter", "Lcom/psychiatrygarden/forum/CircleHotSearchRankAdp;", "mLyMore", "Landroid/widget/LinearLayout;", "pageShow", "rvRankList", "Landroidx/recyclerview/widget/RecyclerView;", "scrollView", "Landroidx/core/widget/NestedScrollView;", "showItemBackground", "", "getLayoutId", "", "initViews", "", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "loadData", "mockData", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CircleHotSearchRankFragment extends BaseFragment {

    @NotNull
    private static final String CODE_TYPE = "CODE_TYPE";

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String RANK_TYPE = "RANK_TYPE";
    public static final int TYPE_BOOK = 3;
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_EXP = 2;
    public static final int TYPE_INFO = 4;
    private CustomEmptyView emptyView;
    private CircleHotSearchRankAdp mAdapter;
    private LinearLayout mLyMore;
    private RecyclerView rvRankList;
    private NestedScrollView scrollView;
    private boolean showItemBackground = true;

    @NotNull
    private String pageShow = "1";

    @NotNull
    private String codeType = "bbs";

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/forum/CircleHotSearchRankFragment$Companion;", "", "()V", CircleHotSearchRankFragment.CODE_TYPE, "", CircleHotSearchRankFragment.RANK_TYPE, "TYPE_BOOK", "", "TYPE_CIRCLE", "TYPE_EXP", "TYPE_INFO", "newInstance", "Lcom/psychiatrygarden/forum/CircleHotSearchRankFragment;", "type", "code", "isMoreHot", "", "position", "showItemBackground", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nCircleHotSearchRankFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CircleHotSearchRankFragment.kt\ncom/psychiatrygarden/forum/CircleHotSearchRankFragment$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,311:1\n1#2:312\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ CircleHotSearchRankFragment newInstance$default(Companion companion, int i2, String str, boolean z2, int i3, boolean z3, int i4, Object obj) {
            if ((i4 & 16) != 0) {
                z3 = true;
            }
            return companion.newInstance(i2, str, z2, i3, z3);
        }

        @JvmStatic
        @NotNull
        public final CircleHotSearchRankFragment newInstance(int type, @NotNull String code, boolean isMoreHot, int position, boolean showItemBackground) {
            Intrinsics.checkNotNullParameter(code, "code");
            Bundle bundle = new Bundle();
            bundle.putInt(CircleHotSearchRankFragment.RANK_TYPE, type);
            bundle.putString(CircleHotSearchRankFragment.CODE_TYPE, code);
            bundle.putBoolean("isMoreHot", isMoreHot);
            bundle.putInt("position", position);
            bundle.putBoolean("showItemBackground", showItemBackground);
            CircleHotSearchRankFragment circleHotSearchRankFragment = new CircleHotSearchRankFragment();
            circleHotSearchRankFragment.setArguments(bundle);
            return circleHotSearchRankFragment;
        }
    }

    private final void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("show_page", this.pageShow);
        ajaxParams.put("module_type", this.codeType);
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.getForumHotRank, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.CircleHotSearchRankFragment.loadData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable error, int errorNo, @Nullable String strMsg) {
                super.onFailure(error, errorNo, strMsg);
                if (error != null) {
                    error.printStackTrace();
                }
                CustomEmptyView customEmptyView = CircleHotSearchRankFragment.this.emptyView;
                CustomEmptyView customEmptyView2 = null;
                if (customEmptyView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    customEmptyView = null;
                }
                customEmptyView.setLoadFileResUi(((BaseFragment) CircleHotSearchRankFragment.this).mContext);
                CustomEmptyView customEmptyView3 = CircleHotSearchRankFragment.this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView2 = customEmptyView3;
                }
                customEmptyView2.setVisibility(0);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String result) {
                super.onSuccess((AnonymousClass1) result);
                if (result != null) {
                    CircleHotSearchRankFragment circleHotSearchRankFragment = CircleHotSearchRankFragment.this;
                    CustomEmptyView customEmptyView = null;
                    try {
                        CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(result, CirclrListBean.class);
                        if (!circlrListBean.getCode().equals("200")) {
                            LinearLayout linearLayout = circleHotSearchRankFragment.mLyMore;
                            if (linearLayout == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mLyMore");
                                linearLayout = null;
                            }
                            linearLayout.setVisibility(8);
                            return;
                        }
                        if (circlrListBean.getData() == null || circlrListBean.getData().size() <= 0) {
                            LinearLayout linearLayout2 = circleHotSearchRankFragment.mLyMore;
                            if (linearLayout2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mLyMore");
                                linearLayout2 = null;
                            }
                            linearLayout2.setVisibility(8);
                            CustomEmptyView customEmptyView2 = circleHotSearchRankFragment.emptyView;
                            if (customEmptyView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                                customEmptyView2 = null;
                            }
                            customEmptyView2.setVisibility(0);
                            return;
                        }
                        CustomEmptyView customEmptyView3 = circleHotSearchRankFragment.emptyView;
                        if (customEmptyView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                            customEmptyView3 = null;
                        }
                        customEmptyView3.setVisibility(8);
                        CircleHotSearchRankAdp circleHotSearchRankAdp = circleHotSearchRankFragment.mAdapter;
                        if (circleHotSearchRankAdp == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            circleHotSearchRankAdp = null;
                        }
                        circleHotSearchRankAdp.setNewData(circlrListBean.getData());
                        if (circleHotSearchRankFragment.pageShow.equals("1")) {
                            if (circlrListBean.getData().get(0).getIs_show_more().equals("1")) {
                                LinearLayout linearLayout3 = circleHotSearchRankFragment.mLyMore;
                                if (linearLayout3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mLyMore");
                                    linearLayout3 = null;
                                }
                                linearLayout3.setVisibility(0);
                                return;
                            }
                            LinearLayout linearLayout4 = circleHotSearchRankFragment.mLyMore;
                            if (linearLayout4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mLyMore");
                                linearLayout4 = null;
                            }
                            linearLayout4.setVisibility(8);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        CustomEmptyView customEmptyView4 = circleHotSearchRankFragment.emptyView;
                        if (customEmptyView4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                            customEmptyView4 = null;
                        }
                        customEmptyView4.setLoadFileResUi(((BaseFragment) circleHotSearchRankFragment).mContext);
                        CustomEmptyView customEmptyView5 = circleHotSearchRankFragment.emptyView;
                        if (customEmptyView5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                        } else {
                            customEmptyView = customEmptyView5;
                        }
                        customEmptyView.setVisibility(0);
                    }
                }
            }
        });
    }

    private final void mockData() {
        Bundle arguments = getArguments();
        LinearLayout linearLayout = null;
        final Integer numValueOf = arguments != null ? Integer.valueOf(arguments.getInt(RANK_TYPE)) : null;
        Bundle arguments2 = getArguments();
        this.codeType = String.valueOf(arguments2 != null ? arguments2.getString(CODE_TYPE) : null);
        Bundle arguments3 = getArguments();
        final Integer numValueOf2 = arguments3 != null ? Integer.valueOf(arguments3.getInt("position")) : null;
        Bundle arguments4 = getArguments();
        boolean z2 = arguments4 != null && arguments4.getBoolean("isMoreHot");
        Bundle arguments5 = getArguments();
        boolean z3 = arguments5 != null && arguments5.getBoolean("showItemBackground");
        LinearLayout linearLayout2 = this.mLyMore;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLyMore");
            linearLayout2 = null;
        }
        linearLayout2.setVisibility(z2 ? 8 : 0);
        this.pageShow = z2 ? "2" : "1";
        if (numValueOf != null && numValueOf.intValue() == 1) {
            this.mAdapter = new CircleHotSearchRankAdp(numValueOf.intValue(), R.layout.item_circle_exp_hot_search_rank, z3);
        } else if (numValueOf != null && numValueOf.intValue() == 2) {
            this.mAdapter = new CircleHotSearchRankAdp(numValueOf.intValue(), R.layout.item_circle_exp_hot_search_rank, z3);
        } else if (numValueOf != null && numValueOf.intValue() == 3) {
            this.mAdapter = new CircleHotSearchRankAdp(numValueOf.intValue(), R.layout.item_book_hot_search_rank, z3);
        } else if (numValueOf != null && numValueOf.intValue() == 4) {
            this.mAdapter = new CircleHotSearchRankAdp(numValueOf.intValue(), R.layout.item_information_hot_search_rank, z3);
        }
        int screenHeight = (ScreenUtil.getScreenHeight(getActivity()) - ((int) (ScreenUtil.getScreenWidth(getActivity()) / 1.99d))) - ScreenUtil.getPxByDp(this.mContext, 48);
        CustomEmptyView customEmptyView = this.emptyView;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        customEmptyView.getLayoutParams().height = screenHeight;
        RecyclerView recyclerView = this.rvRankList;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvRankList");
            recyclerView = null;
        }
        CircleHotSearchRankAdp circleHotSearchRankAdp = this.mAdapter;
        if (circleHotSearchRankAdp == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            circleHotSearchRankAdp = null;
        }
        recyclerView.setAdapter(circleHotSearchRankAdp);
        CustomEmptyView customEmptyView2 = this.emptyView;
        if (customEmptyView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView2 = null;
        }
        customEmptyView2.showEmptyView();
        CustomEmptyView customEmptyView3 = this.emptyView;
        if (customEmptyView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView3 = null;
        }
        customEmptyView3.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CircleHotSearchRankFragment.mockData$lambda$0(this.f15311c, view);
            }
        });
        CircleHotSearchRankAdp circleHotSearchRankAdp2 = this.mAdapter;
        if (circleHotSearchRankAdp2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            circleHotSearchRankAdp2 = null;
        }
        circleHotSearchRankAdp2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.forum.b
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                CircleHotSearchRankFragment.mockData$lambda$2(this.f15316a, numValueOf, baseQuickAdapter, view, i2);
            }
        });
        LinearLayout linearLayout3 = this.mLyMore;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLyMore");
        } else {
            linearLayout = linearLayout3;
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CircleHotSearchRankFragment.mockData$lambda$4(this.f15321c, numValueOf2, view);
            }
        });
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mockData$lambda$0(CircleHotSearchRankFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mockData$lambda$2(final CircleHotSearchRankFragment this$0, Integer num, BaseQuickAdapter baseQuickAdapter, View view, final int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!UserConfig.isLogin()) {
            this$0.startActivity(new Intent(this$0.mContext, (Class<?>) LoginActivity.class));
            return;
        }
        CircleHotSearchRankAdp circleHotSearchRankAdp = this$0.mAdapter;
        if (circleHotSearchRankAdp == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            circleHotSearchRankAdp = null;
        }
        CirclrListBean.DataBean item = circleHotSearchRankAdp.getItem(i2);
        Intrinsics.checkNotNull(item);
        final CirclrListBean.DataBean dataBean = item;
        if (num != null && num.intValue() == 1) {
            if (Intrinsics.areEqual("1", dataBean.getNo_access())) {
                this$0.startActivity(new Intent(ProjectApp.instance(), (Class<?>) MemberCenterActivity.class));
                return;
            }
            Intent intent = new Intent(ProjectApp.instance(), (Class<?>) CircleInfoActivity.class);
            intent.putExtra("channel_id", "");
            intent.putExtra("article_id", dataBean.getId());
            intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
            intent.putExtra("app_id", dataBean.getApp_id());
            this$0.startActivity(intent);
            return;
        }
        if (num != null && num.intValue() == 2) {
            Intent intent2 = new Intent(ProjectApp.instance(), (Class<?>) HandoutsInfoActivity.class);
            intent2.putExtra("cat_id", dataBean.getCid());
            intent2.putExtra("article", dataBean.getId());
            intent2.putExtra("json_path", dataBean.getJson_path());
            intent2.putExtra("html_path", dataBean.getHtml_path());
            intent2.putExtra("h5_path", dataBean.getH5_path());
            intent2.putExtra("is_rich_text", dataBean.getIs_rich_text());
            intent2.putExtra("index", dataBean.getCid() + '_' + dataBean.getId());
            intent2.putExtra("app_id", dataBean.getApp_id());
            this$0.startActivity(intent2);
            return;
        }
        if (num == null || num.intValue() != 3) {
            if (num != null && num.intValue() == 4) {
                if (!TextUtils.isEmpty(dataBean.getIs_rights()) && Intrinsics.areEqual(dataBean.getIs_rights(), "1")) {
                    InformationPreviewAct.newIntent(this$0.mContext, dataBean.getId(), dataBean.getUrl(), false);
                    return;
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("enclosure_id", dataBean.getId());
                MemInterface.getInstance().getFilePermission(this$0.getActivity(), ajaxParams);
                MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.forum.d
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        CircleHotSearchRankFragment.mockData$lambda$2$lambda$1(dataBean, this$0, i2);
                    }
                });
                return;
            }
            return;
        }
        String appId = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance, "1");
        String admin = UserConfig.getInstance().getUser().getAdmin();
        String avatar = UserConfig.getInstance().getUser().getAvatar();
        String token = UserConfig.getInstance().getUser().getToken();
        String secret = UserConfig.getInstance().getUser().getSecret();
        ReadBookActivity.Companion companion = ReadBookActivity.INSTANCE;
        Context mContext = this$0.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        String id = dataBean.getId();
        Intrinsics.checkNotNullExpressionValue(id, "item.id");
        String userId = UserConfig.getUserId();
        Intrinsics.checkNotNullExpressionValue(userId, "getUserId()");
        Intrinsics.checkNotNullExpressionValue(appId, "appId");
        Intrinsics.checkNotNullExpressionValue(admin, "admin");
        Intrinsics.checkNotNullExpressionValue(avatar, "avatar");
        Intrinsics.checkNotNullExpressionValue(token, "token");
        Intrinsics.checkNotNullExpressionValue(secret, "secret");
        this$0.startActivity(companion.newIntent(mContext, id, userId, appId, admin, avatar, token, secret));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mockData$lambda$2$lambda$1(CirclrListBean.DataBean item, CircleHotSearchRankFragment this$0, int i2) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        item.setIs_rights("1");
        CircleHotSearchRankAdp circleHotSearchRankAdp = this$0.mAdapter;
        if (circleHotSearchRankAdp == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            circleHotSearchRankAdp = null;
        }
        circleHotSearchRankAdp.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mockData$lambda$4(CircleHotSearchRankFragment this$0, Integer num, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity activity = this$0.getActivity();
        if (activity != null) {
            Object systemService = activity.getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            InputMethodManager inputMethodManager = (InputMethodManager) systemService;
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
        if (!UserConfig.isLogin()) {
            this$0.startActivity(new Intent(this$0.mContext, (Class<?>) LoginActivity.class));
            return;
        }
        Intent intent = new Intent(this$0.mContext, (Class<?>) HotSearchMoreAct.class);
        intent.putExtra("position", num);
        this$0.startActivity(intent);
    }

    @JvmStatic
    @NotNull
    public static final CircleHotSearchRankFragment newInstance(int i2, @NotNull String str, boolean z2, int i3, boolean z3) {
        return INSTANCE.newInstance(i2, str, z2, i3, z3);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_circle_hot_search_rank;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @NotNull View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(root, "root");
        Bundle arguments = getArguments();
        Boolean boolValueOf = arguments != null ? Boolean.valueOf(arguments.getBoolean("showItemBackground", true)) : null;
        Intrinsics.checkNotNull(boolValueOf);
        this.showItemBackground = boolValueOf.booleanValue();
        View view = holder.get(R.id.rvRankList);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.rvRankList)");
        this.rvRankList = (RecyclerView) view;
        View view2 = holder.get(R.id.ly_more);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.ly_more)");
        this.mLyMore = (LinearLayout) view2;
        View view3 = holder.get(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.empty_view)");
        this.emptyView = (CustomEmptyView) view3;
        View view4 = holder.get(R.id.scrollView);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.scrollView)");
        this.scrollView = (NestedScrollView) view4;
        mockData();
    }
}
