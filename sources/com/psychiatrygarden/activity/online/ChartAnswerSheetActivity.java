package com.psychiatrygarden.activity.online;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.AnswerSheetFragment;
import com.psychiatrygarden.fragmenthome.KnowledgeListFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.NoScrollViewPager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\r\u0018\u0000 J2\u00020\u0001:\u0001JB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u00103\u001a\u00020\bJ\u0006\u00104\u001a\u000205J\u000e\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\nJ\b\u00107\u001a\u000205H\u0016J\b\u00108\u001a\u000205H\u0016J\u0012\u00109\u001a\u0002052\b\u0010:\u001a\u0004\u0018\u00010\nH\u0016J \u0010;\u001a\u0002052\u0018\u0010<\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0>0=J\u0010\u0010?\u001a\u0002052\u0006\u0010@\u001a\u00020\nH\u0002J\u0010\u0010A\u001a\u0002052\u0006\u0010B\u001a\u00020\u0004H\u0002J\b\u0010C\u001a\u000205H\u0016J\b\u0010D\u001a\u000205H\u0016J\u000e\u0010E\u001a\u0002052\u0006\u0010F\u001a\u00020\nJ\u0010\u0010G\u001a\u0002052\u0006\u0010H\u001a\u00020\u0004H\u0002J\u000e\u0010I\u001a\u0002052\u0006\u0010,\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00060\rj\b\u0012\u0004\u0012\u00020\u0006`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\rj\b\u0012\u0004\u0012\u00020\u001d`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\n0\rj\b\u0012\u0004\u0012\u00020\n`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\n0\rj\b\u0012\u0004\u0012\u00020\n`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082.¢\u0006\u0002\n\u0000¨\u0006K"}, d2 = {"Lcom/psychiatrygarden/activity/online/ChartAnswerSheetActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "bottom_statistics", "", "ctl", "Landroid/view/View;", "currentPosition", "", "day", "", "fromQuestionCombine", "guideViewList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "guideViewRedo", "guideViewSheet", "guideViewStatistics", "guideViewZsd", "hasNextNode", "identity_id", "isClick", "isDailyTaskPage", "ivRedo", "ivStatistics", "knowledgeId", "knowledgeTabViewX", "mChartAnseerMask", "mFragments", "Landroidx/fragment/app/Fragment;", "mImgArrowOne", "Landroid/widget/ImageView;", "map_node", "nodeId", "nodeIdList", "nodeIdTitleList", "nodeTitle", "node_activity_id", "node_parent_id", "paperId", "paperTitle", "question_bank_id", "rvTab", "Landroidx/recyclerview/widget/RecyclerView;", "title", "tvSingleTab", "Landroid/widget/TextView;", "tvTitle", "type", "viewpager", "Lcom/psychiatrygarden/widget/NoScrollViewPager;", "getPageSize", "gotoStatistics", "", "knowledge", "init", "onBackPressedSupport", "onEventMainThread", "str", "refreshAnswerSheet", "params", "Landroid/util/ArrayMap;", "", "save", "key", "setAppbarCorlor", "showMask", "setContentView", "setListenerForWidget", "setUnlockActivityId", "id", "showGuide", "isAnswerSheet", "updateTitle", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChartAnswerSheetActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChartAnswerSheetActivity.kt\ncom/psychiatrygarden/activity/online/ChartAnswerSheetActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,659:1\n1855#2,2:660\n1#3:662\n*S KotlinDebug\n*F\n+ 1 ChartAnswerSheetActivity.kt\ncom/psychiatrygarden/activity/online/ChartAnswerSheetActivity\n*L\n160#1:660,2\n*E\n"})
/* loaded from: classes5.dex */
public final class ChartAnswerSheetActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private boolean bottom_statistics;
    private View ctl;
    private int currentPosition;
    private boolean fromQuestionCombine;
    private View guideViewRedo;
    private View guideViewSheet;
    private View guideViewStatistics;
    private View guideViewZsd;
    private boolean hasNextNode;
    private String identity_id;
    private boolean isClick;
    private boolean isDailyTaskPage;
    private View ivRedo;
    private View ivStatistics;
    private String knowledgeId;
    private int knowledgeTabViewX;
    private View mChartAnseerMask;
    private ImageView mImgArrowOne;
    private boolean map_node;
    private String nodeId;
    private String nodeTitle;

    @Nullable
    private String node_activity_id;

    @Nullable
    private String paperId;

    @Nullable
    private String paperTitle;
    private RecyclerView rvTab;
    private String title;
    private TextView tvSingleTab;
    private TextView tvTitle;
    private NoScrollViewPager viewpager;

    @NotNull
    private final ArrayList<View> guideViewList = new ArrayList<>();

    @NotNull
    private final ArrayList<String> nodeIdList = new ArrayList<>();

    @NotNull
    private final ArrayList<String> nodeIdTitleList = new ArrayList<>();

    @NotNull
    private final ArrayList<Fragment> mFragments = new ArrayList<>();

    @NotNull
    private String type = "";

    @NotNull
    private String day = "";

    @NotNull
    private String node_parent_id = "";

    @NotNull
    private String question_bank_id = "";

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/activity/online/ChartAnswerSheetActivity$Companion;", "", "()V", "startActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "paperId", "", "paperTitle", "type", "fromQuestionCombine", "", "questionBankId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(@NotNull Context context, @NonNull @NotNull String paperId, @NonNull @NotNull String paperTitle, @NotNull String type, boolean fromQuestionCombine, @NotNull String questionBankId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(paperId, "paperId");
            Intrinsics.checkNotNullParameter(paperTitle, "paperTitle");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(questionBankId, "questionBankId");
            context.startActivity(new Intent(context, (Class<?>) ChartAnswerSheetActivity.class).putExtra("paperId", paperId).putExtra("paperTitle", paperTitle).putExtra("type", type).putExtra("fromQuestionCombine", fromQuestionCombine).putExtra("question_bank_id", questionBankId));
        }
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/activity/online/ChartAnswerSheetActivity$init$3", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.online.ChartAnswerSheetActivity$init$3, reason: invalid class name */
    public static final class AnonymousClass3 extends BaseQuickAdapter<String, BaseViewHolder> {
        final /* synthetic */ int $normalColor;
        final /* synthetic */ int $selectColor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(int i2, int i3) {
            super(R.layout.item_question_category, null, 2, 0 == true ? 1 : 0);
            this.$selectColor = i2;
            this.$normalColor = i3;
            if (ChartAnswerSheetActivity.this.map_node || TextUtils.isEmpty(ChartAnswerSheetActivity.this.paperTitle)) {
                setList(CollectionsKt__CollectionsKt.arrayListOf("考点", "答题卡"));
            } else {
                String str = ChartAnswerSheetActivity.this.paperTitle;
                Intrinsics.checkNotNull(str);
                setList(CollectionsKt__CollectionsKt.arrayListOf(str));
            }
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.b1
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i4) throws Resources.NotFoundException {
                    ChartAnswerSheetActivity.AnonymousClass3._init_$lambda$3(chartAnswerSheetActivity, baseQuickAdapter, view, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$3(final ChartAnswerSheetActivity this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            View view2 = this$0.ctl;
            NoScrollViewPager noScrollViewPager = null;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ctl");
                view2 = null;
            }
            LogUtils.e("view_vis", "is===>" + (view2.getVisibility() == 0));
            View view3 = this$0.ctl;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ctl");
                view3 = null;
            }
            if (view3.getVisibility() == 0) {
                return;
            }
            if (i2 != 1) {
                NoScrollViewPager noScrollViewPager2 = this$0.viewpager;
                if (noScrollViewPager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                } else {
                    noScrollViewPager = noScrollViewPager2;
                }
                noScrollViewPager.setCurrentItem(i2);
                return;
            }
            Object obj = this$0.mFragments.get(0);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.KnowledgeListFragment");
            if (!((KnowledgeListFragment) obj).hasPermission()) {
                String str = this$0.node_activity_id;
                if (str == null || str.length() == 0) {
                    return;
                }
                MemInterface.getInstance().setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.activity.online.y0
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
                    public final void shareSuccess(String str2) {
                        ChartAnswerSheetActivity.AnonymousClass3.lambda$3$lambda$0(this$0, str2);
                    }
                });
                MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.online.z0
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        ChartAnswerSheetActivity.AnonymousClass3.lambda$3$lambda$1(this$0);
                    }
                });
                MemInterface memInterface = MemInterface.getInstance();
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, this$0.node_activity_id);
                ajaxParams.put("alwaysShow", "1");
                Unit unit = Unit.INSTANCE;
                memInterface.getMemData((Activity) this$0, ajaxParams, NetworkRequestsURL.vipApi, 0, false);
                return;
            }
            NoScrollViewPager noScrollViewPager3 = this$0.viewpager;
            if (noScrollViewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                noScrollViewPager3 = null;
            }
            noScrollViewPager3.setCurrentItem(i2);
            RecyclerView recyclerView = this$0.rvTab;
            if (recyclerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvTab");
                recyclerView = null;
            }
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            this$0.currentPosition = i2;
            if (this$0.mFragments.get(1) instanceof AnswerSheetFragment) {
                Object obj2 = this$0.mFragments.get(1);
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
                AnswerSheetFragment answerSheetFragment = (AnswerSheetFragment) obj2;
                String str2 = this$0.nodeId;
                if (str2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("nodeId");
                    str2 = null;
                }
                answerSheetFragment.loadQuestionData(str2, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$4(BaseViewHolder holder, ChartAnswerSheetActivity this$0) {
            Intrinsics.checkNotNullParameter(holder, "$holder");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            int[] iArr = new int[2];
            holder.itemView.getLocationOnScreen(iArr);
            this$0.knowledgeTabViewX = iArr[0] + (holder.itemView.getWidth() / 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void lambda$3$lambda$0(ChartAnswerSheetActivity this$0, String str) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Object obj = this$0.mFragments.get(0);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.KnowledgeListFragment");
            ((KnowledgeListFragment) obj).refreshList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void lambda$3$lambda$1(ChartAnswerSheetActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Object obj = this$0.mFragments.get(0);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.KnowledgeListFragment");
            ((KnowledgeListFragment) obj).refreshList();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull final BaseViewHolder holder, @NotNull String item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            holder.setText(R.id.tv_column_name, item).setVisible(R.id.img_choose, holder.getLayoutPosition() == ChartAnswerSheetActivity.this.currentPosition && getData().size() > 1);
            TextView textView = (TextView) holder.getView(R.id.tv_column_name);
            textView.setTypeface(holder.getLayoutPosition() == ChartAnswerSheetActivity.this.currentPosition ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            textView.setTextSize(holder.getLayoutPosition() == ChartAnswerSheetActivity.this.currentPosition ? 16.0f : 14.0f);
            textView.setTextColor(holder.getLayoutPosition() == ChartAnswerSheetActivity.this.currentPosition ? this.$selectColor : this.$normalColor);
            if (getData().size() == 1) {
                holder.setGone(R.id.img_choose, true);
            }
            if (holder.getLayoutPosition() == 0 && getData().size() == 2) {
                View view = holder.itemView;
                final ChartAnswerSheetActivity chartAnswerSheetActivity = ChartAnswerSheetActivity.this;
                view.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.a1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChartAnswerSheetActivity.AnonymousClass3.convert$lambda$4(holder, chartAnswerSheetActivity);
                    }
                });
            }
        }
    }

    @Metadata(d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J \u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0005H\u0016¨\u0006\f"}, d2 = {"com/psychiatrygarden/activity/online/ChartAnswerSheetActivity$init$5", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "onPageScrollStateChanged", "", "state", "", "onPageScrolled", "position", "positionOffset", "", "positionOffsetPixels", "onPageSelected", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.online.ChartAnswerSheetActivity$init$5, reason: invalid class name */
    public static final class AnonymousClass5 implements ViewPager.OnPageChangeListener {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onPageSelected$lambda$0(ChartAnswerSheetActivity this$0, String str) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Object obj = this$0.mFragments.get(0);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.KnowledgeListFragment");
            ((KnowledgeListFragment) obj).refreshList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onPageSelected$lambda$1(ChartAnswerSheetActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Object obj = this$0.mFragments.get(0);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.KnowledgeListFragment");
            ((KnowledgeListFragment) obj).refreshList();
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            ChartAnswerSheetActivity.this.currentPosition = position;
            RecyclerView recyclerView = ChartAnswerSheetActivity.this.rvTab;
            if (recyclerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvTab");
                recyclerView = null;
            }
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            View view = ChartAnswerSheetActivity.this.ivRedo;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                view = null;
            }
            view.setVisibility(position == 0 ? 8 : 0);
            boolean z2 = ChartAnswerSheetActivity.this.mFragments.get(position) instanceof AnswerSheetFragment;
            View view2 = ChartAnswerSheetActivity.this.ivStatistics;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                view2 = null;
            }
            view2.setVisibility(z2 ? 0 : 8);
            View view3 = ChartAnswerSheetActivity.this.ivRedo;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                view3 = null;
            }
            view3.setVisibility(z2 ? 0 : 8);
            String str = ChartAnswerSheetActivity.this.paperId;
            if (!(str == null || str.length() == 0) || ChartAnswerSheetActivity.this.map_node) {
                View view4 = ChartAnswerSheetActivity.this.ivStatistics;
                if (view4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                    view4 = null;
                }
                ViewExtensionsKt.gone(view4);
            }
            ChartAnswerSheetActivity.this.showGuide(z2);
            Object obj = ChartAnswerSheetActivity.this.mFragments.get(position);
            Intrinsics.checkNotNullExpressionValue(obj, "mFragments[position]");
            if (((Fragment) obj) instanceof AnswerSheetFragment) {
                Object obj2 = ChartAnswerSheetActivity.this.mFragments.get(0);
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.KnowledgeListFragment");
                if (((KnowledgeListFragment) obj2).hasPermission()) {
                    if (ChartAnswerSheetActivity.this.mFragments.get(1) instanceof AnswerSheetFragment) {
                        Object obj3 = ChartAnswerSheetActivity.this.mFragments.get(1);
                        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
                        AnswerSheetFragment answerSheetFragment = (AnswerSheetFragment) obj3;
                        String str2 = ChartAnswerSheetActivity.this.nodeId;
                        if (str2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("nodeId");
                            str2 = null;
                        }
                        answerSheetFragment.loadQuestionData(str2, null);
                        return;
                    }
                    return;
                }
                String str3 = ChartAnswerSheetActivity.this.node_activity_id;
                if (str3 == null || str3.length() == 0) {
                    return;
                }
                MemInterface memInterface = MemInterface.getInstance();
                final ChartAnswerSheetActivity chartAnswerSheetActivity = ChartAnswerSheetActivity.this;
                memInterface.setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.activity.online.c1
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
                    public final void shareSuccess(String str4) {
                        ChartAnswerSheetActivity.AnonymousClass5.onPageSelected$lambda$0(chartAnswerSheetActivity, str4);
                    }
                });
                MemInterface memInterface2 = MemInterface.getInstance();
                final ChartAnswerSheetActivity chartAnswerSheetActivity2 = ChartAnswerSheetActivity.this;
                memInterface2.setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.online.d1
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        ChartAnswerSheetActivity.AnonymousClass5.onPageSelected$lambda$1(chartAnswerSheetActivity2);
                    }
                });
                MemInterface memInterface3 = MemInterface.getInstance();
                ChartAnswerSheetActivity chartAnswerSheetActivity3 = ChartAnswerSheetActivity.this;
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, ChartAnswerSheetActivity.this.node_activity_id);
                ajaxParams.put("alwaysShow", "1");
                Unit unit = Unit.INSTANCE;
                memInterface3.getMemData((Activity) chartAnswerSheetActivity3, ajaxParams, NetworkRequestsURL.vipApi, 0, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$6(ChartAnswerSheetActivity this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView recyclerView = this$0.rvTab;
        View view = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvTab");
            recyclerView = null;
        }
        int measuredWidth = recyclerView.getMeasuredWidth() / 4;
        View view2 = this$0.guideViewZsd;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("guideViewZsd");
            view2 = null;
        }
        int width = (i2 - (view2.getWidth() / 2)) - measuredWidth;
        View view3 = this$0.guideViewZsd;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("guideViewZsd");
            view3 = null;
        }
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        layoutParams2.setMarginStart(width);
        View view4 = this$0.guideViewZsd;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("guideViewZsd");
            view4 = null;
        }
        view4.setLayoutParams(layoutParams2);
        int iDip2px = (i2 - (UIUtil.dip2px(this$0.mContext, 280.0d) / 2)) - measuredWidth;
        View view5 = this$0.guideViewSheet;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("guideViewSheet");
            view5 = null;
        }
        ViewGroup.LayoutParams layoutParams3 = view5.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) layoutParams3;
        layoutParams4.setMarginEnd(iDip2px);
        View view6 = this$0.guideViewSheet;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("guideViewSheet");
        } else {
            view = view6;
        }
        view.setLayoutParams(layoutParams4);
    }

    private final void save(String key) {
        SharePreferencesUtils.writeBooleanConfig(key, true, this.mContext);
    }

    private final void setAppbarCorlor(boolean showMask) {
        if (showMask) {
            if (SkinManager.getCurrentSkinType(this.mContext) != 1) {
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black_gray), 0);
                return;
            } else {
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.corlor_appbar_mask_night), 0);
                getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) != 1) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_white_color), 0);
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setContentView$lambda$1(ChartAnswerSheetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View view2 = this$0.ctl;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
            view2 = null;
        }
        if (view2.getVisibility() == 0 || this$0.map_node || this$0.guideViewList.size() > 0) {
            return;
        }
        for (Fragment fragment : this$0.mFragments) {
            if (fragment instanceof AnswerSheetFragment) {
                ((AnswerSheetFragment) fragment).gotoStatistics();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public static final void setListenerForWidget$lambda$16(ChartAnswerSheetActivity this$0, View view) {
        AnswerSheetFragment next;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View view2 = this$0.ctl;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
            view2 = null;
        }
        if (view2.getVisibility() != 0 && this$0.guideViewList.size() <= 0) {
            Iterator it = this$0.mFragments.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = 0;
                    break;
                } else {
                    next = it.next();
                    if (((Fragment) next) instanceof AnswerSheetFragment) {
                        break;
                    }
                }
            }
            AnswerSheetFragment answerSheetFragment = next instanceof AnswerSheetFragment ? next : null;
            if (answerSheetFragment != null) {
                answerSheetFragment.showRedoDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$17(ChartAnswerSheetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.mFragments.size() == 1) {
            if (this$0.mFragments.get(0) instanceof AnswerSheetFragment) {
                Fragment fragment = this$0.mFragments.get(0);
                Intrinsics.checkNotNull(fragment, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
                if (Intrinsics.areEqual(((AnswerSheetFragment) fragment).getAnswerMode(), Constants.ANSWER_MODE.TEST_MODE)) {
                    Fragment fragment2 = this$0.mFragments.get(0);
                    Intrinsics.checkNotNull(fragment2, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
                    ((AnswerSheetFragment) fragment2).submitAnswer("1");
                    return;
                }
            }
            this$0.finish();
            return;
        }
        NoScrollViewPager noScrollViewPager = this$0.viewpager;
        if (noScrollViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            noScrollViewPager = null;
        }
        if (noScrollViewPager.getCurrentItem() == 0) {
            this$0.finish();
            return;
        }
        Fragment fragment3 = this$0.mFragments.get(1);
        Intrinsics.checkNotNull(fragment3, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
        if (!Intrinsics.areEqual(((AnswerSheetFragment) fragment3).getAnswerMode(), Constants.ANSWER_MODE.TEST_MODE)) {
            this$0.finish();
            return;
        }
        Fragment fragment4 = this$0.mFragments.get(1);
        Intrinsics.checkNotNull(fragment4, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
        ((AnswerSheetFragment) fragment4).submitAnswer("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$18(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$19(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showGuide(final boolean isAnswerSheet) {
        if (this.isDailyTaskPage) {
            return;
        }
        View view = null;
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.KNOWLEDGE_POINT_DO_QUESTION_GUIDE, false, this)) {
            ArrayList<View> arrayList = this.guideViewList;
            final View view2 = this.guideViewZsd;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("guideViewZsd");
                view2 = null;
            }
            view2.findViewById(R.id.tv_ok_zsd).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.u0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    ChartAnswerSheetActivity.showGuide$lambda$8$lambda$7(view2, this, view3);
                }
            });
            arrayList.add(view2);
        }
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.NO_KNOWLEDGE_POINT_DO_QUESTION_GUIDE, false, this)) {
            ArrayList<View> arrayList2 = this.guideViewList;
            final View view3 = this.guideViewSheet;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("guideViewSheet");
                view3 = null;
            }
            view3.findViewById(R.id.tv_ok_bf_zsd).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.v0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view4) {
                    ChartAnswerSheetActivity.showGuide$lambda$10$lambda$9(view3, this, isAnswerSheet, view4);
                }
            });
            arrayList2.add(view3);
        }
        if (!isAnswerSheet) {
            View view4 = this.ivStatistics;
            if (view4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                view4 = null;
            }
            if (view4.getVisibility() == 0 && !SharePreferencesUtils.readBooleanConfig(CommonParameter.CHAPTER_STATISTICS_GUIDE, false, this)) {
                ArrayList<View> arrayList3 = this.guideViewList;
                final View view5 = this.guideViewStatistics;
                if (view5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("guideViewStatistics");
                    view5 = null;
                }
                view5.findViewById(R.id.tv_ok_statistics).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.x0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view6) {
                        ChartAnswerSheetActivity.showGuide$lambda$14$lambda$13(view5, this, view6);
                    }
                });
                arrayList3.add(view5);
            }
        } else if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.REDO_TEXT_TIPS_GUIDE, false, this)) {
            ArrayList<View> arrayList4 = this.guideViewList;
            final View view6 = this.guideViewRedo;
            if (view6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("guideViewRedo");
                view6 = null;
            }
            view6.findViewById(R.id.tv_ok_redo).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.w0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view7) {
                    ChartAnswerSheetActivity.showGuide$lambda$12$lambda$11(view6, this, view7);
                }
            });
            arrayList4.add(view6);
        }
        if (this.guideViewList.size() > 0) {
            View view7 = this.ctl;
            if (view7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ctl");
                view7 = null;
            }
            ViewExtensionsKt.visible(view7);
            View view8 = this.mChartAnseerMask;
            if (view8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
                view8 = null;
            }
            ViewExtensionsKt.visible(view8);
            setAppbarCorlor(true);
            View view9 = this.guideViewList.get(0);
            Intrinsics.checkNotNullExpressionValue(view9, "guideViewList[0]");
            ViewExtensionsKt.visible(view9);
        } else {
            View view10 = this.ctl;
            if (view10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ctl");
                view10 = null;
            }
            ViewExtensionsKt.gone(view10);
            View view11 = this.mChartAnseerMask;
            if (view11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
                view11 = null;
            }
            ViewExtensionsKt.gone(view11);
            setAppbarCorlor(false);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("is===>");
        View view12 = this.ctl;
        if (view12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
        } else {
            view = view12;
        }
        sb.append(view.getVisibility() == 0);
        LogUtils.e("view_vis", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGuide$lambda$10$lambda$9(View this_apply, ChartAnswerSheetActivity this$0, boolean z2, View view) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewExtensionsKt.gone(this_apply);
        this$0.save(CommonParameter.NO_KNOWLEDGE_POINT_DO_QUESTION_GUIDE);
        this$0.guideViewList.remove(this_apply);
        if (this$0.guideViewList.size() > 0) {
            View view2 = this$0.guideViewList.get(0);
            Intrinsics.checkNotNullExpressionValue(view2, "guideViewList[0]");
            ViewExtensionsKt.visible(view2);
        }
        View view3 = null;
        if (!z2) {
            View view4 = this$0.ctl;
            if (view4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ctl");
                view4 = null;
            }
            ViewExtensionsKt.gone(view4);
            View view5 = this$0.mChartAnseerMask;
            if (view5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
                view5 = null;
            }
            ViewExtensionsKt.gone(view5);
            this$0.setAppbarCorlor(false);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("is===>");
        View view6 = this$0.ctl;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
        } else {
            view3 = view6;
        }
        sb.append(view3.getVisibility() == 0);
        LogUtils.e("view_vis", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGuide$lambda$12$lambda$11(View this_apply, ChartAnswerSheetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewExtensionsKt.gone(this_apply);
        this$0.save(CommonParameter.REDO_TEXT_TIPS_GUIDE);
        this$0.guideViewList.remove(this_apply);
        if (this$0.guideViewList.size() > 0) {
            View view2 = this$0.guideViewList.get(0);
            Intrinsics.checkNotNullExpressionValue(view2, "guideViewList[0]");
            ViewExtensionsKt.visible(view2);
        }
        View view3 = this$0.ctl;
        View view4 = null;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
            view3 = null;
        }
        ViewExtensionsKt.gone(view3);
        View view5 = this$0.mChartAnseerMask;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
        } else {
            view4 = view5;
        }
        ViewExtensionsKt.gone(view4);
        this$0.setAppbarCorlor(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGuide$lambda$14$lambda$13(View this_apply, ChartAnswerSheetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewExtensionsKt.gone(this_apply);
        this$0.save(CommonParameter.CHAPTER_STATISTICS_GUIDE);
        this$0.guideViewList.remove(this_apply);
        if (this$0.guideViewList.size() > 0) {
            View view2 = this$0.guideViewList.get(0);
            Intrinsics.checkNotNullExpressionValue(view2, "guideViewList[0]");
            ViewExtensionsKt.visible(view2);
        }
        View view3 = this$0.ctl;
        View view4 = null;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
            view3 = null;
        }
        ViewExtensionsKt.gone(view3);
        View view5 = this$0.mChartAnseerMask;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
        } else {
            view4 = view5;
        }
        ViewExtensionsKt.gone(view4);
        this$0.setAppbarCorlor(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGuide$lambda$8$lambda$7(View this_apply, ChartAnswerSheetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewExtensionsKt.gone(this_apply);
        this$0.guideViewList.remove(this_apply);
        this$0.save(CommonParameter.KNOWLEDGE_POINT_DO_QUESTION_GUIDE);
        if (this$0.guideViewList.size() > 0) {
            View view2 = this$0.guideViewList.get(0);
            Intrinsics.checkNotNullExpressionValue(view2, "guideViewList[0]");
            ViewExtensionsKt.visible(view2);
        }
    }

    public final int getPageSize() {
        return this.mFragments.size();
    }

    public final void gotoStatistics() {
        Iterator<Fragment> it = this.mFragments.iterator();
        while (it.hasNext()) {
            Fragment next = it.next();
            if (next instanceof AnswerSheetFragment) {
                ((AnswerSheetFragment) next).submitAnswer("3");
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        RecyclerView recyclerView;
        final ArrayList arrayList = new ArrayList();
        boolean z2 = true;
        if (this.map_node) {
            KnowledgeListFragment knowledgeListFragment = new KnowledgeListFragment();
            Bundle bundle = new Bundle();
            String str = this.nodeId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodeId");
                str = null;
            }
            bundle.putString("node_id", str);
            String str2 = this.identity_id;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("identity_id");
                str2 = null;
            }
            bundle.putString("identity_id", str2);
            String str3 = this.title;
            if (str3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("title");
                str3 = null;
            }
            bundle.putString("title", str3);
            bundle.putBoolean("bottom_statistics", true);
            bundle.putString("desc", getIntent().getStringExtra("desc"));
            if (this.node_parent_id.length() > 0) {
                bundle.putString("node_parent_id", this.node_parent_id);
            }
            if (this.question_bank_id.length() > 0) {
                bundle.putString("question_bank_id", this.question_bank_id);
            }
            knowledgeListFragment.setArguments(bundle);
            arrayList.add(knowledgeListFragment);
        }
        AnswerSheetFragment answerSheetFragment = new AnswerSheetFragment();
        Bundle bundle2 = new Bundle();
        if (!this.nodeIdList.isEmpty()) {
            bundle2.putStringArrayList("nodeIdList", this.nodeIdList);
        }
        if (!this.nodeIdTitleList.isEmpty()) {
            bundle2.putStringArrayList("nodeIdTitleList", this.nodeIdTitleList);
        }
        bundle2.putBoolean("isDailyTaskPage", this.isDailyTaskPage);
        bundle2.putString("day", this.day);
        String str4 = this.paperId;
        if (!(str4 == null || str4.length() == 0)) {
            bundle2.putString("paperId", this.paperId);
        }
        if (this.map_node || this.isDailyTaskPage) {
            bundle2.putBoolean("isNode", true);
        }
        String str5 = this.nodeId;
        if (str5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodeId");
            str5 = null;
        }
        if (str5.length() > 0) {
            String str6 = this.nodeId;
            if (str6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodeId");
                str6 = null;
            }
            bundle2.putString("node_id", str6);
        }
        if (!this.map_node) {
            String str7 = this.knowledgeId;
            if (str7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("knowledgeId");
                str7 = null;
            }
            if (str7.length() > 0) {
                String str8 = this.knowledgeId;
                if (str8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("knowledgeId");
                    str8 = null;
                }
                bundle2.putString("knowledge_id", str8);
                String str9 = this.paperId;
                bundle2.putBoolean("isKnowledge", str9 == null || str9.length() == 0);
            }
        }
        if (this.question_bank_id.length() > 0) {
            bundle2.putString("question_bank_id", this.question_bank_id);
        }
        if (this.node_parent_id.length() > 0) {
            bundle2.putString("node_parent_id", this.node_parent_id);
        }
        String str10 = this.nodeTitle;
        if (str10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodeTitle");
            str10 = null;
        }
        if (str10.length() > 0) {
            String str11 = this.nodeTitle;
            if (str11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodeTitle");
                str11 = null;
            }
            bundle2.putString("node_title", str11);
        }
        bundle2.putBoolean("bottom_statistics", this.map_node ? true : this.bottom_statistics);
        bundle2.putBoolean("fromQuestionCombine", this.fromQuestionCombine);
        bundle2.putString("type", !TextUtils.isEmpty(this.type) ? this.type : "all");
        String str12 = this.title;
        if (str12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("title");
            str12 = null;
        }
        bundle2.putString("subject_title", str12);
        String str13 = this.identity_id;
        if (str13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("identity_id");
            str13 = null;
        }
        bundle2.putString("identity_id", str13);
        String str14 = this.nodeId;
        if (str14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodeId");
            str14 = null;
        }
        bundle2.putString("node_id", str14);
        bundle2.putString("desc", getIntent().getStringExtra("desc"));
        answerSheetFragment.setArguments(bundle2);
        arrayList.add(answerSheetFragment);
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color, R.attr.third_txt_color});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "theme.obtainStyledAttrib… R.attr.third_txt_color))");
        int color = typedArrayObtainStyledAttributes.getColor(1, getColor(R.color.third_txt_color));
        int color2 = typedArrayObtainStyledAttributes.getColor(0, getColor(R.color.first_txt_color));
        typedArrayObtainStyledAttributes.recycle();
        if (arrayList.size() == 1) {
            if (Intrinsics.areEqual(ClientCookie.COMMENT_ATTR, this.type) || Intrinsics.areEqual("praise", this.type)) {
                View view = this.ivRedo;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                    view = null;
                }
                ViewExtensionsKt.gone(view);
                View view2 = this.ivStatistics;
                if (view2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                    view2 = null;
                }
                ViewExtensionsKt.gone(view2);
            } else {
                View view3 = this.ivRedo;
                if (view3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                    view3 = null;
                }
                ViewExtensionsKt.visible(view3);
                View view4 = this.ivStatistics;
                if (view4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                    view4 = null;
                }
                ViewExtensionsKt.visible(view4);
                if (this.isDailyTaskPage) {
                    View view5 = this.ivStatistics;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                        view5 = null;
                    }
                    ViewExtensionsKt.gone(view5);
                }
            }
            TextView textView = this.tvSingleTab;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvSingleTab");
                textView = null;
            }
            ViewExtensionsKt.visible(textView);
            TextView textView2 = this.tvSingleTab;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvSingleTab");
                textView2 = null;
            }
            textView2.setText(this.paperTitle);
            String str15 = this.title;
            if (str15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("title");
                str15 = null;
            }
            if (str15.length() > 0) {
                TextView textView3 = this.tvSingleTab;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvSingleTab");
                    textView3 = null;
                }
                String str16 = this.title;
                if (str16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("title");
                    str16 = null;
                }
                textView3.setText(str16);
            }
            RecyclerView recyclerView2 = this.rvTab;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvTab");
                recyclerView2 = null;
            }
            ViewExtensionsKt.gone(recyclerView2);
        } else {
            ProjectApp.clearNodeList();
            RecyclerView recyclerView3 = this.rvTab;
            if (recyclerView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvTab");
                recyclerView3 = null;
            }
            ViewExtensionsKt.visible(recyclerView3);
            View view6 = this.ivRedo;
            if (view6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
                view6 = null;
            }
            ViewExtensionsKt.gone(view6);
            TextView textView4 = this.tvSingleTab;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvSingleTab");
                textView4 = null;
            }
            ViewExtensionsKt.gone(textView4);
            RecyclerView recyclerView4 = this.rvTab;
            if (recyclerView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvTab");
                recyclerView4 = null;
            }
            recyclerView4.setAdapter(new AnonymousClass3(color2, color));
        }
        String str17 = this.paperId;
        if (str17 != null && str17.length() != 0) {
            z2 = false;
        }
        if (!z2 || this.map_node) {
            View view7 = this.ivStatistics;
            if (view7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                view7 = null;
            }
            ViewExtensionsKt.gone(view7);
        }
        NoScrollViewPager noScrollViewPager = this.viewpager;
        if (noScrollViewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            noScrollViewPager = null;
        }
        noScrollViewPager.setOffscreenPageLimit(arrayList.size());
        NoScrollViewPager noScrollViewPager2 = this.viewpager;
        if (noScrollViewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            noScrollViewPager2 = null;
        }
        noScrollViewPager2.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) { // from class: com.psychiatrygarden.activity.online.ChartAnswerSheetActivity.init.4
            @Override // androidx.viewpager.widget.PagerAdapter
            /* renamed from: getCount */
            public int getSize() {
                return arrayList.size();
            }

            @Override // androidx.fragment.app.FragmentStatePagerAdapter
            @NotNull
            public Fragment getItem(int position) {
                Fragment fragment = arrayList.get(position);
                Intrinsics.checkNotNullExpressionValue(fragment, "fragments[position]");
                return fragment;
            }
        });
        NoScrollViewPager noScrollViewPager3 = this.viewpager;
        if (noScrollViewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            noScrollViewPager3 = null;
        }
        noScrollViewPager3.addOnPageChangeListener(new AnonymousClass5());
        this.mFragments.addAll(arrayList);
        final int screenWidth = UIUtil.getScreenWidth(this.mContext) / 2;
        RecyclerView recyclerView5 = this.rvTab;
        if (recyclerView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvTab");
            recyclerView = null;
        } else {
            recyclerView = recyclerView5;
        }
        recyclerView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.t0
            @Override // java.lang.Runnable
            public final void run() {
                ChartAnswerSheetActivity.init$lambda$6(this.f13483c, screenWidth);
            }
        });
        showGuide(false);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, cn.webdemo.com.supporfragment.ISupportActivity
    public void onBackPressedSupport() {
        findViewById(R.id.iv_back).performClick();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) throws Resources.NotFoundException {
        NoScrollViewPager noScrollViewPager = null;
        if (Intrinsics.areEqual(CommonParameter.GOTO_KNOWLEDGE_NODE_LIST, str)) {
            if (this.mFragments.size() == 1) {
                finish();
                return;
            }
            if (this.mFragments.size() == 2) {
                NoScrollViewPager noScrollViewPager2 = this.viewpager;
                if (noScrollViewPager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                } else {
                    noScrollViewPager = noScrollViewPager2;
                }
                noScrollViewPager.setCurrentItem(0);
                return;
            }
            return;
        }
        if (!Intrinsics.areEqual("cancelUnlock", str)) {
            if (Intrinsics.areEqual("jump2KnowledgeChapter", str)) {
                finish();
            }
        } else {
            NoScrollViewPager noScrollViewPager3 = this.viewpager;
            if (noScrollViewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            } else {
                noScrollViewPager = noScrollViewPager3;
            }
            noScrollViewPager.setCurrentItem(0);
        }
    }

    public final void refreshAnswerSheet(@NotNull ArrayMap<String, List<String>> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        if (this.mFragments.size() == 2 && (this.mFragments.get(1) instanceof AnswerSheetFragment)) {
            Fragment fragment = this.mFragments.get(1);
            Intrinsics.checkNotNull(fragment, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.AnswerSheetFragment");
            ((AnswerSheetFragment) fragment).refreshFilterParams(params);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_chart_anseer_sheet);
        this.mActionBar.hide();
        View viewFindViewById = findViewById(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.viewpager)");
        this.viewpager = (NoScrollViewPager) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.rvTab);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.rvTab)");
        this.rvTab = (RecyclerView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tv_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_title)");
        this.tvTitle = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.iv_statistics);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.iv_statistics)");
        this.ivStatistics = viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.iv_redo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.iv_redo)");
        this.ivRedo = viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.ll_zsd);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.ll_zsd)");
        this.guideViewZsd = viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.ll_answer_sheet);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.ll_answer_sheet)");
        this.guideViewSheet = viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.ll_redo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.ll_redo)");
        this.guideViewRedo = viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.ll_statistics);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.ll_statistics)");
        this.guideViewStatistics = viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.tvSingleTab);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.tvSingleTab)");
        this.tvSingleTab = (TextView) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.ctl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.ctl)");
        this.ctl = viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.img_arrow_one);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.img_arrow_one)");
        this.mImgArrowOne = (ImageView) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.chart_anseer_mask);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.chart_anseer_mask)");
        this.mChartAnseerMask = viewFindViewById13;
        String stringExtra = getIntent().getStringExtra("node_title");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.nodeTitle = stringExtra;
        String stringExtra2 = getIntent().getStringExtra("question_bank_id");
        if (stringExtra2 == null) {
            stringExtra2 = "";
        }
        this.question_bank_id = stringExtra2;
        String stringExtra3 = getIntent().getStringExtra("title");
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        this.title = stringExtra3;
        String stringExtra4 = getIntent().getStringExtra("node_parent_id");
        if (stringExtra4 == null) {
            stringExtra4 = "";
        }
        this.node_parent_id = stringExtra4;
        this.paperId = getIntent().getStringExtra("paperId");
        String stringExtra5 = getIntent().getStringExtra("day");
        if (stringExtra5 == null) {
            stringExtra5 = "";
        }
        this.day = stringExtra5;
        this.paperTitle = getIntent().getStringExtra("paperTitle");
        String stringExtra6 = getIntent().getStringExtra("identity_id");
        if (stringExtra6 == null) {
            stringExtra6 = "";
        }
        this.identity_id = stringExtra6;
        this.map_node = getIntent().getBooleanExtra("map_node", false);
        this.fromQuestionCombine = getIntent().getBooleanExtra("fromQuestionCombine", false);
        this.bottom_statistics = getIntent().getBooleanExtra("bottom_statistics", false);
        String stringExtra7 = getIntent().getStringExtra("type");
        if (stringExtra7 == null) {
            stringExtra7 = "";
        }
        this.type = stringExtra7;
        this.hasNextNode = getIntent().getBooleanExtra("hasNextNode", false);
        boolean booleanExtra = getIntent().getBooleanExtra("isDailyTaskPage", false);
        this.isDailyTaskPage = booleanExtra;
        String str = null;
        if (booleanExtra) {
            View view = this.mChartAnseerMask;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
                view = null;
            }
            ViewExtensionsKt.gone(view);
        }
        View view2 = this.ivStatistics;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
            view2 = null;
        }
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChartAnswerSheetActivity.setContentView$lambda$1(this.f13479c, view3);
            }
        });
        if (this.map_node) {
            TextView textView = this.tvTitle;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                textView = null;
            }
            ViewExtensionsKt.visible(textView);
            View view3 = this.ivStatistics;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivStatistics");
                view3 = null;
            }
            ViewExtensionsKt.gone(view3);
        }
        TextView textView2 = this.tvTitle;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView2 = null;
        }
        String str2 = this.title;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("title");
            str2 = null;
        }
        textView2.setText(str2);
        String stringExtra8 = getIntent().getStringExtra("node_id");
        if (stringExtra8 == null) {
            stringExtra8 = "";
        }
        this.nodeId = stringExtra8;
        String stringExtra9 = getIntent().getStringExtra("knowledge_id");
        this.knowledgeId = stringExtra9 != null ? stringExtra9 : "";
        if (TextUtils.isEmpty(this.paperTitle)) {
            String str3 = this.title;
            if (str3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("title");
                str3 = null;
            }
            if (str3.length() > 0) {
                String str4 = this.title;
                if (str4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("title");
                } else {
                    str = str4;
                }
                this.paperTitle = str;
            }
        }
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra("nodeIdList");
        ArrayList<String> stringArrayListExtra2 = getIntent().getStringArrayListExtra("nodeIdTitleList");
        if (stringArrayListExtra == null || stringArrayListExtra.isEmpty()) {
            return;
        }
        if (stringArrayListExtra2 == null || stringArrayListExtra2.isEmpty()) {
            return;
        }
        this.nodeIdList.addAll(stringArrayListExtra);
        this.nodeIdTitleList.addAll(stringArrayListExtra2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        View view = this.ivRedo;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivRedo");
            view = null;
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChartAnswerSheetActivity.setListenerForWidget$lambda$16(this.f13460c, view3);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChartAnswerSheetActivity.setListenerForWidget$lambda$17(this.f13466c, view3);
            }
        });
        View view3 = this.ctl;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ctl");
            view3 = null;
        }
        view3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                ChartAnswerSheetActivity.setListenerForWidget$lambda$18(view4);
            }
        });
        View view4 = this.mChartAnseerMask;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mChartAnseerMask");
        } else {
            view2 = view4;
        }
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                ChartAnswerSheetActivity.setListenerForWidget$lambda$19(view5);
            }
        });
    }

    public final void setUnlockActivityId(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        this.node_activity_id = id;
    }

    public final void updateTitle(@NotNull String title) {
        Intrinsics.checkNotNullParameter(title, "title");
        if (this.mFragments.size() == 1) {
            TextView textView = this.tvSingleTab;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvSingleTab");
                textView = null;
            }
            textView.setText(title);
        }
    }

    public final void gotoStatistics(@NotNull String knowledge) {
        Intrinsics.checkNotNullParameter(knowledge, "knowledge");
        Iterator<Fragment> it = this.mFragments.iterator();
        while (it.hasNext()) {
            Fragment next = it.next();
            if (next instanceof AnswerSheetFragment) {
                AnswerSheetFragment answerSheetFragment = (AnswerSheetFragment) next;
                answerSheetFragment.setKnowledgeId(knowledge);
                answerSheetFragment.submitAnswer("3");
            }
        }
    }
}
