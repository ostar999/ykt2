package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.knowledge.DailyTasksActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.bean.NodeKnowledgeInfo;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.NodeUpdateEvent;
import com.psychiatrygarden.event.RefreshChapterKnowledgeEvent;
import com.psychiatrygarden.event.RefreshLastDoEvent;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import net.cachapa.expandablelayout.ExpandableLayout;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\n2\b\b\u0002\u0010*\u001a\u00020\u0012H\u0002J\u0010\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020\u0005H\u0002J\u001e\u0010-\u001a\u00020(2\u0006\u0010.\u001a\u00020\n2\u0006\u0010/\u001a\u00020\n2\u0006\u00100\u001a\u00020\nJ\u0012\u00101\u001a\u0004\u0018\u00010 2\u0006\u0010)\u001a\u00020\nH\u0002J\u0018\u00102\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\f0\u0004J\b\u00103\u001a\u00020\u0005H\u0014J\b\u00104\u001a\u00020(H\u0002J\u0018\u00105\u001a\u00020(2\u0006\u00106\u001a\u00020 2\u0006\u00107\u001a\u000208H\u0002J\b\u00109\u001a\u00020(H\u0002J\u0012\u0010:\u001a\u00020(2\b\b\u0002\u0010;\u001a\u00020\u0012H\u0002J\u0016\u0010<\u001a\u00020(2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020 0\fH\u0002J\u001a\u0010>\u001a\u00020(2\u0006\u0010?\u001a\u00020@2\b\u0010A\u001a\u0004\u0018\u00010\u0006H\u0014J$\u0010B\u001a\u00020(2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0D2\b\b\u0002\u0010;\u001a\u00020\u0012J\u0014\u0010E\u001a\u00020(2\n\u0010F\u001a\u0006\u0012\u0002\b\u00030GH\u0007J\u0010\u0010E\u001a\u00020(2\u0006\u0010H\u001a\u00020IH\u0007J\u0010\u0010E\u001a\u00020(2\u0006\u0010H\u001a\u00020JH\u0007J\u0010\u0010E\u001a\u00020(2\u0006\u0010H\u001a\u00020KH\u0007J\u0018\u0010L\u001a\u00020(2\u0006\u0010)\u001a\u00020\n2\u0006\u0010M\u001a\u00020\nH\u0002J\u0006\u0010N\u001a\u00020(J \u0010O\u001a\u00020(2\u0018\u0010;\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\f0\u0004J\b\u0010P\u001a\u00020(H\u0002J\u000e\u0010Q\u001a\u00020(2\u0006\u0010R\u001a\u00020\nJ\u000e\u0010S\u001a\u00020(2\u0006\u0010R\u001a\u00020\nR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010!\u001a\u0012\u0012\u0004\u0012\u00020 0\"j\b\u0012\u0004\u0012\u00020 `#X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010$\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000¨\u0006T"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/QuestionKnowledgeListFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "chapterChildViewsMap", "Landroid/util/ArrayMap;", "", "Landroid/view/View;", "child", "Lcom/psychiatrygarden/bean/SelectIdentityBean$DataBean;", "day", "", "defaultFilterMap", "", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "filterStr", "filterValue", "isDailyTaskPage", "", "llEmpty", "llFilterInfo", "llPointsList", "Landroid/widget/LinearLayout;", "loadingView", "Landroid/widget/ImageView;", "lyCustomLoading", "mAnimationDrawable", "Landroid/graphics/drawable/AnimationDrawable;", "mChapterChildViewMap", "mChapterViewMap", "mChildChapterHeightMap", "mDailyTaskKnowledgeList", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "mDataList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "mDataMap", "tvFilterInfo", "Landroid/widget/TextView;", "addOrUpdateChild", "", "id", PLVUpdateMicSiteEvent.EVENT_NAME, "addOrUpdateParent", "position", "dailyTaskUpdate", "jieId", "finishCount", "rate", "getChildItem", "getFilterDataMap", "getLayoutId", "hideLoadingView", "initChildView", "item", "llChild", "Landroid/view/ViewGroup;", "initDailyTaskData", "initLastDoShow", "filter", "initViewList", "data", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "loadData", "param", "", "onEventMainThread", "msg", "Lcom/psychiatrygarden/bean/EventBusMessage;", AliyunLogKey.KEY_EVENT, "Lcom/psychiatrygarden/event/NodeUpdateEvent;", "Lcom/psychiatrygarden/event/RefreshChapterKnowledgeEvent;", "Lcom/psychiatrygarden/event/RefreshLastDoEvent;", "refreshNodeInfo", "nodeId", "scroll2SpecifiedChapter", "setFilterDataMap", "showLoadingView", "writeFilterStr", "value", "writeFilterValue", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nQuestionKnowledgeListFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 QuestionKnowledgeListFragment.kt\ncom/psychiatrygarden/fragmenthome/QuestionKnowledgeListFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 View.kt\nandroidx/core/view/ViewKt\n*L\n1#1,801:1\n1855#2:802\n1864#2,3:803\n1856#2:806\n1855#2,2:807\n1855#2:809\n1864#2,3:810\n1856#2:813\n1864#2,2:814\n1855#2,2:816\n1866#2:818\n1864#2,2:819\n1864#2,3:821\n1866#2:824\n1864#2,2:825\n1864#2,3:827\n1866#2:830\n1855#2,2:831\n1864#2,3:835\n1864#2,3:838\n1855#2:841\n1864#2,3:842\n1856#2:845\n1864#2,3:846\n1864#2,3:849\n1#3:833\n260#4:834\n*S KotlinDebug\n*F\n+ 1 QuestionKnowledgeListFragment.kt\ncom/psychiatrygarden/fragmenthome/QuestionKnowledgeListFragment\n*L\n110#1:802\n111#1:803,3\n110#1:806\n134#1:807,2\n166#1:809\n167#1:810,3\n166#1:813\n272#1:814,2\n273#1:816,2\n272#1:818\n314#1:819,2\n322#1:821,3\n314#1:824\n335#1:825,2\n350#1:827,3\n335#1:830\n356#1:831,2\n708#1:835,3\n757#1:838,3\n791#1:841\n792#1:842,3\n791#1:845\n601#1:846,3\n627#1:849,3\n568#1:834\n*E\n"})
/* loaded from: classes5.dex */
public final class QuestionKnowledgeListFragment extends BaseFragment {

    @Nullable
    private SelectIdentityBean.DataBean child;
    private CustomEmptyView emptyView;
    private boolean isDailyTaskPage;
    private View llEmpty;
    private View llFilterInfo;
    private LinearLayout llPointsList;
    private ImageView loadingView;
    private View lyCustomLoading;

    @Nullable
    private AnimationDrawable mAnimationDrawable;
    private TextView tvFilterInfo;

    @NotNull
    private final ArrayMap<Integer, Integer> mChildChapterHeightMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<String, List<String>> defaultFilterMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, View> chapterChildViewsMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, View> mChapterViewMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<String, View> mChapterChildViewMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, List<KnowledgeChartNodeBean>> mDataMap = new ArrayMap<>();

    @NotNull
    private final ArrayList<KnowledgeChartNodeBean> mDataList = new ArrayList<>();

    @NotNull
    private String filterValue = "";

    @NotNull
    private String filterStr = "";

    @NotNull
    private List<? extends KnowledgeChartNodeBean> mDailyTaskKnowledgeList = new ArrayList();

    @NotNull
    private String day = "";

    private final void addOrUpdateChild(String id, boolean update) {
        View view = this.mChapterChildViewMap.get(id);
        if (view == null) {
            return;
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_percent);
        TextView textView3 = (TextView) view.findViewById(R.id.tv_count);
        TextView textView4 = (TextView) view.findViewById(R.id.tv_knowledge_point_count);
        TextView tvContinueDoNormal = (TextView) view.findViewById(R.id.tv_continue_do_normal);
        View viewFindViewById = view.findViewById(R.id.line);
        view.findViewById(R.id.ll_right);
        KnowledgeChartNodeBean childItem = getChildItem(id);
        if (childItem == null) {
            return;
        }
        textView.setText(childItem.getName());
        textView3.setText(childItem.getUser_answer_total() + '/' + childItem.getQuestion_total());
        if (this.isDailyTaskPage) {
            textView4.setText("去做题");
        } else {
            textView4.setText("考点 " + childItem.getKnowledge_count());
        }
        textView2.setText("正确率" + childItem.getRight_rate());
        viewFindViewById.setVisibility(!childItem.isLast() ? 0 : 8);
        if (!childItem.isContinueDo() || !UserConfig.isLogin() || this.isDailyTaskPage) {
            Intrinsics.checkNotNullExpressionValue(tvContinueDoNormal, "tvContinueDoNormal");
            ViewExtensionsKt.gone(tvContinueDoNormal);
        } else {
            Intrinsics.checkNotNullExpressionValue(tvContinueDoNormal, "tvContinueDoNormal");
            ViewExtensionsKt.visible(tvContinueDoNormal);
            textView.setMaxWidth(UIUtil.getScreenWidth(this.mContext) - CommonUtil.dip2px(this.mContext, 160.0f));
        }
    }

    public static /* synthetic */ void addOrUpdateChild$default(QuestionKnowledgeListFragment questionKnowledgeListFragment, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        questionKnowledgeListFragment.addOrUpdateChild(str, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addOrUpdateParent(int position) {
        int i2;
        int i3;
        boolean z2 = true;
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            i2 = R.drawable.ic_kp_down_night;
            i3 = R.drawable.ic_kp_up_night;
        } else {
            i2 = R.drawable.ic_kp_down_day;
            i3 = R.drawable.ic_kp_up_day;
        }
        final View view = this.mChapterViewMap.get(Integer.valueOf(position));
        if (view == null) {
            return;
        }
        KnowledgeChartNodeBean knowledgeChartNodeBean = this.mDataList.get(position);
        Intrinsics.checkNotNullExpressionValue(knowledgeChartNodeBean, "mDataList[position]");
        final KnowledgeChartNodeBean knowledgeChartNodeBean2 = knowledgeChartNodeBean;
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_exp_col);
        if (knowledgeChartNodeBean2.isExpand()) {
            i2 = i3;
        }
        imageView.setImageResource(i2);
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        TextView tvContinueDo = (TextView) view.findViewById(R.id.tv_continue_do);
        textView.setText(knowledgeChartNodeBean2.getName());
        int i4 = 8;
        tvContinueDo.setVisibility((!knowledgeChartNodeBean2.isContinueDo() || knowledgeChartNodeBean2.isExpand() || !UserConfig.isLogin() || this.isDailyTaskPage) ? 8 : 0);
        Intrinsics.checkNotNullExpressionValue(tvContinueDo, "tvContinueDo");
        if (tvContinueDo.getVisibility() == 0) {
            textView.setMaxWidth(UIUtil.getScreenWidth(this.mContext) - CommonUtil.dip2px(this.mContext, 140.0f));
        }
        View lineView = view.findViewById(R.id.line);
        if (knowledgeChartNodeBean2.isExpand()) {
            List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean2.getChildren();
            if (children != null && !children.isEmpty()) {
                z2 = false;
            }
            if (!z2) {
                i4 = 0;
            }
        }
        lineView.setVisibility(i4);
        if (knowledgeChartNodeBean2.isExpand() && !TextUtils.isEmpty(knowledgeChartNodeBean2.getRecommend_image())) {
            Intrinsics.checkNotNullExpressionValue(lineView, "lineView");
            ViewExtensionsKt.gone(lineView);
        }
        RoundedImageView imageView2 = (RoundedImageView) view.findViewById(R.id.iv_img);
        if (TextUtils.isEmpty(knowledgeChartNodeBean2.getRecommend_image())) {
            Intrinsics.checkNotNullExpressionValue(imageView2, "imageView");
            ViewExtensionsKt.gone(imageView2);
        } else {
            if (knowledgeChartNodeBean2.isExpand()) {
                Intrinsics.checkNotNullExpressionValue(imageView2, "imageView");
                ViewExtensionsKt.visible(imageView2);
            } else {
                Intrinsics.checkNotNullExpressionValue(imageView2, "imageView");
                ViewExtensionsKt.gone(imageView2);
            }
            Glide.with(this.mContext).load(knowledgeChartNodeBean2.getRecommend_image()).into(imageView2);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.nb
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    QuestionKnowledgeListFragment.addOrUpdateParent$lambda$16(this.f15871c, knowledgeChartNodeBean2, view2);
                }
            });
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ob
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                QuestionKnowledgeListFragment.addOrUpdateParent$lambda$23(this.f15893c, view, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addOrUpdateParent$lambda$16(QuestionKnowledgeListFragment this$0, KnowledgeChartNodeBean item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        ActCourseOrGoodsDetail.Companion companion = ActCourseOrGoodsDetail.INSTANCE;
        Context mContext = this$0.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        String recommend_course_id = item.getRecommend_course_id();
        Intrinsics.checkNotNullExpressionValue(recommend_course_id, "item.recommend_course_id");
        companion.navigationToCourseOrGoodsDetail(mContext, recommend_course_id, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addOrUpdateParent$lambda$23(final QuestionKnowledgeListFragment this$0, final View chapterView, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(chapterView, "$chapterView");
        Object tag = view.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type com.psychiatrygarden.bean.KnowledgeChartNodeBean");
        KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) tag;
        int iIndexOf = this$0.mDataList.indexOf(knowledgeChartNodeBean);
        if (iIndexOf < 0) {
            return;
        }
        if (!knowledgeChartNodeBean.isInitChildren()) {
            this$0.showProgressDialog();
            View viewFindViewById = chapterView.findViewById(R.id.ll_child);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "chapterView.findViewById…earLayout>(R.id.ll_child)");
            this$0.initChildView(knowledgeChartNodeBean, (ViewGroup) viewFindViewById);
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.lb
                @Override // java.lang.Runnable
                public final void run() {
                    QuestionKnowledgeListFragment.addOrUpdateParent$lambda$23$lambda$17(this.f15812c);
                }
            }, 300L);
            knowledgeChartNodeBean.setInitChildren(true);
        }
        Iterator<T> it = this$0.mDataList.iterator();
        int i2 = 0;
        while (true) {
            Object obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            KnowledgeChartNodeBean knowledgeChartNodeBean2 = (KnowledgeChartNodeBean) next;
            if (Intrinsics.areEqual(knowledgeChartNodeBean2.getId(), knowledgeChartNodeBean.getId())) {
                knowledgeChartNodeBean.setExpand(!knowledgeChartNodeBean.isExpand());
                List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                if (!(children == null || children.isEmpty())) {
                    List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean.getChildren();
                    Intrinsics.checkNotNullExpressionValue(children2, "chapterItem.children");
                    Iterator<T> it2 = children2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Object next2 = it2.next();
                        if (((KnowledgeChartNodeBean) next2).isContinueDo()) {
                            obj = next2;
                            break;
                        }
                    }
                    KnowledgeChartNodeBean knowledgeChartNodeBean3 = (KnowledgeChartNodeBean) obj;
                    if (knowledgeChartNodeBean3 != null) {
                        knowledgeChartNodeBean3.setContinueDo(true);
                        knowledgeChartNodeBean.setContinueDo(!knowledgeChartNodeBean.isExpand());
                        knowledgeChartNodeBean3.setExpand(knowledgeChartNodeBean.isExpand());
                    }
                }
                this$0.addOrUpdateParent(iIndexOf);
            } else {
                List<KnowledgeChartNodeBean> children3 = knowledgeChartNodeBean2.getChildren();
                if (children3 != null) {
                    Intrinsics.checkNotNullExpressionValue(children3, "children");
                    Iterator<T> it3 = children3.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        Object next3 = it3.next();
                        if (((KnowledgeChartNodeBean) next3).isContinueDo()) {
                            obj = next3;
                            break;
                        }
                    }
                    obj = (KnowledgeChartNodeBean) obj;
                }
                if (obj == null) {
                    knowledgeChartNodeBean2.setContinueDo(false);
                }
                this$0.addOrUpdateParent(i2);
            }
            i2 = i3;
        }
        if (!knowledgeChartNodeBean.isInitChildren()) {
            this$0.showProgressDialog();
            View viewFindViewById2 = chapterView.findViewById(R.id.ll_child);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "chapterView.findViewById…earLayout>(R.id.ll_child)");
            this$0.initChildView(knowledgeChartNodeBean, (ViewGroup) viewFindViewById2);
        }
        List<KnowledgeChartNodeBean> children4 = knowledgeChartNodeBean.getChildren();
        if (children4 != null) {
            int i4 = 0;
            for (Object obj2 : children4) {
                int i5 = i4 + 1;
                if (i4 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                KnowledgeChartNodeBean knowledgeChartNodeBean4 = (KnowledgeChartNodeBean) obj2;
                if (Intrinsics.areEqual(knowledgeChartNodeBean4.getParentId(), knowledgeChartNodeBean.getId())) {
                    knowledgeChartNodeBean4.setExpand(knowledgeChartNodeBean.isExpand());
                    String id = knowledgeChartNodeBean4.getId();
                    Intrinsics.checkNotNullExpressionValue(id, "knowledgeChartNodeBean.id");
                    addOrUpdateChild$default(this$0, id, false, 2, null);
                }
                i4 = i5;
            }
        }
        if (knowledgeChartNodeBean.isInitChildren()) {
            ((ExpandableLayout) chapterView.findViewById(R.id.llExp)).toggle();
        } else {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.mb
                @Override // java.lang.Runnable
                public final void run() {
                    QuestionKnowledgeListFragment.addOrUpdateParent$lambda$23$lambda$22(this.f15847c, chapterView);
                }
            }, 200L);
            knowledgeChartNodeBean.setInitChildren(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addOrUpdateParent$lambda$23$lambda$17(QuestionKnowledgeListFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addOrUpdateParent$lambda$23$lambda$22(QuestionKnowledgeListFragment this$0, View chapterView) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(chapterView, "$chapterView");
        this$0.hideProgressDialog();
        ((ExpandableLayout) chapterView.findViewById(R.id.llExp)).toggle();
    }

    private final KnowledgeChartNodeBean getChildItem(String id) {
        Iterator<T> it = this.mDataList.iterator();
        while (it.hasNext()) {
            List<KnowledgeChartNodeBean> children = ((KnowledgeChartNodeBean) it.next()).getChildren();
            if (children != null) {
                Intrinsics.checkNotNullExpressionValue(children, "children");
                int i2 = 0;
                for (Object obj : children) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
                    if (Intrinsics.areEqual(id, knowledgeChartNodeBean.getId())) {
                        return knowledgeChartNodeBean;
                    }
                    i2 = i3;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideLoadingView() {
        View view = this.lyCustomLoading;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lyCustomLoading");
            view = null;
        }
        ViewExtensionsKt.gone(view);
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    private final void initChildView(KnowledgeChartNodeBean item, ViewGroup llChild) {
        llChild.removeAllViews();
        List<KnowledgeChartNodeBean> children = item.getChildren();
        if (children == null || children.isEmpty()) {
            return;
        }
        List<KnowledgeChartNodeBean> children2 = item.getChildren();
        Intrinsics.checkNotNullExpressionValue(children2, "item.children");
        int i2 = 0;
        for (Object obj : children2) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
            View viewInflate = View.inflate(this.mContext, R.layout.item_home_chart_child, null);
            llChild.addView(viewInflate, new LinearLayout.LayoutParams(-1, -2));
            this.mChapterChildViewMap.put(knowledgeChartNodeBean.getId(), viewInflate);
            viewInflate.setTag(knowledgeChartNodeBean);
            String id = knowledgeChartNodeBean.getId();
            Intrinsics.checkNotNullExpressionValue(id, "childItem.id");
            addOrUpdateChild$default(this, id, false, 2, null);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.pb
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuestionKnowledgeListFragment.initChildView$lambda$27$lambda$26(this.f15917c, view);
                }
            });
            i2 = i3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initChildView$lambda$27$lambda$26(QuestionKnowledgeListFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLogin()) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, this$0.filterValue, this$0.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, this$0.filterStr, this$0.mContext);
            Object tag = view.getTag();
            Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type com.psychiatrygarden.bean.KnowledgeChartNodeBean");
            KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) tag;
            Intent intentPutExtra = new Intent(this$0.mContext, (Class<?>) ChartAnswerSheetActivity.class).putExtra("node_id", knowledgeChartNodeBean.getId()).putExtra("node_parent_id", knowledgeChartNodeBean.getParentId()).putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, knowledgeChartNodeBean.getActivity_id());
            SelectIdentityBean.DataBean dataBean = this$0.child;
            Intent intentPutExtra2 = intentPutExtra.putExtra("question_bank_id", dataBean != null ? dataBean.getQuestion_bank_id() : null);
            SelectIdentityBean.DataBean dataBean2 = this$0.child;
            Intent intentPutExtra3 = intentPutExtra2.putExtra("identity_id", dataBean2 != null ? dataBean2.getId() : null).putExtra("map_node", !this$0.isDailyTaskPage).putExtra("isKnowledge", !this$0.isDailyTaskPage).putExtra("isDailyTaskPage", this$0.isDailyTaskPage).putExtra("title", knowledgeChartNodeBean.getName()).putExtra("node_title", knowledgeChartNodeBean.getName());
            String describe = knowledgeChartNodeBean.getDescribe();
            if (describe == null) {
                describe = "";
            }
            this$0.startActivity(intentPutExtra3.putExtra("desc", describe).putExtra("day", this$0.day));
        }
    }

    private final void initDailyTaskData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            Serializable serializable = arguments.getSerializable("dailyTaskKnowledgeList");
            Intrinsics.checkNotNull(serializable, "null cannot be cast to non-null type kotlin.collections.List<com.psychiatrygarden.bean.KnowledgeChartNodeBean>");
            this.mDailyTaskKnowledgeList = (List) serializable;
            String string = arguments.getString("day", "");
            Intrinsics.checkNotNullExpressionValue(string, "it.getString(\"day\", \"\")");
            this.day = string;
            int i2 = 0;
            for (Object obj : this.mDailyTaskKnowledgeList) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
                List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                if (!(children == null || children.isEmpty())) {
                    ListIterator<KnowledgeChartNodeBean> listIterator = knowledgeChartNodeBean.getChildren().listIterator();
                    while (listIterator.hasNext()) {
                        KnowledgeChartNodeBean next = listIterator.next();
                        if (Intrinsics.areEqual(next.getQuestion_count(), "0")) {
                            listIterator.remove();
                        } else {
                            SelectIdentityBean.DataBean dataBean = this.child;
                            next.setDescribe(dataBean != null ? dataBean.getDescribe() : null);
                            next.setParentId(knowledgeChartNodeBean.getId());
                            next.setParentTitle(knowledgeChartNodeBean.getName());
                        }
                    }
                    List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean.getChildren();
                    Intrinsics.checkNotNullExpressionValue(children2, "child.children");
                    int i4 = 0;
                    for (Object obj2 : children2) {
                        int i5 = i4 + 1;
                        if (i4 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        ((KnowledgeChartNodeBean) obj2).setLast(i4 == knowledgeChartNodeBean.getChildren().size() - 1);
                        i4 = i5;
                    }
                    this.mDataMap.put(Integer.valueOf(i2), knowledgeChartNodeBean.getChildren());
                }
                i2 = i3;
            }
            Iterator<T> it = this.mDailyTaskKnowledgeList.iterator();
            while (it.hasNext()) {
                ((KnowledgeChartNodeBean) it.next()).setExpand(false);
            }
            this.mDataList.clear();
            this.mDataList.addAll(this.mDailyTaskKnowledgeList);
            initViewList(this.mDailyTaskKnowledgeList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initLastDoShow(boolean filter) {
        String strConfig = SharePreferencesUtils.readStrConfig("LAST_DO_NODE_KNOWLEDGE_ID_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext, "");
        if (strConfig == null || !StringsKt__StringsKt.contains$default((CharSequence) strConfig, (CharSequence) StrPool.UNDERLINE, false, 2, (Object) null)) {
            return;
        }
        String str = (String) StringsKt__StringsKt.split$default((CharSequence) strConfig, new String[]{StrPool.UNDERLINE}, false, 0, 6, (Object) null).get(0);
        String str2 = (String) StringsKt__StringsKt.split$default((CharSequence) strConfig, new String[]{StrPool.UNDERLINE}, false, 0, 6, (Object) null).get(1);
        int i2 = 0;
        for (Object obj : this.mDataList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
            knowledgeChartNodeBean.setContinueDo(Intrinsics.areEqual(knowledgeChartNodeBean.getId(), str));
            if (Intrinsics.areEqual(knowledgeChartNodeBean.getId(), str)) {
                knowledgeChartNodeBean.setContinueDo(!knowledgeChartNodeBean.isExpand());
            } else {
                knowledgeChartNodeBean.setContinueDo(false);
            }
            addOrUpdateParent(i2);
            List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
            if (children != null) {
                Intrinsics.checkNotNullExpressionValue(children, "children");
                int i4 = 0;
                for (Object obj2 : children) {
                    int i5 = i4 + 1;
                    if (i4 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    KnowledgeChartNodeBean knowledgeChartNodeBean2 = (KnowledgeChartNodeBean) obj2;
                    knowledgeChartNodeBean2.setContinueDo(Intrinsics.areEqual(knowledgeChartNodeBean2.getId(), str2));
                    knowledgeChartNodeBean2.setExpand(knowledgeChartNodeBean.isExpand());
                    String id = knowledgeChartNodeBean2.getId();
                    Intrinsics.checkNotNullExpressionValue(id, "childItem.id");
                    addOrUpdateChild$default(this, id, false, 2, null);
                    i4 = i5;
                }
            }
            i2 = i3;
        }
    }

    public static /* synthetic */ void initLastDoShow$default(QuestionKnowledgeListFragment questionKnowledgeListFragment, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        questionKnowledgeListFragment.initLastDoShow(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initViewList(List<? extends KnowledgeChartNodeBean> data) {
        this.mChapterViewMap.clear();
        this.mChapterChildViewMap.clear();
        LinearLayout linearLayout = this.llPointsList;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llPointsList");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        int i2 = 0;
        for (Object obj : data) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            View viewInflate = View.inflate(this.mContext, R.layout.item_home_chart, null);
            viewInflate.setTag((KnowledgeChartNodeBean) obj);
            LinearLayout linearLayout2 = this.llPointsList;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llPointsList");
                linearLayout2 = null;
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            layoutParams.bottomMargin = SizeUtil.dp2px(this.mContext, 12);
            Unit unit = Unit.INSTANCE;
            linearLayout2.addView(viewInflate, layoutParams);
            this.mChapterViewMap.put(Integer.valueOf(i2), viewInflate);
            addOrUpdateParent(i2);
            i2 = i3;
        }
    }

    public static /* synthetic */ void loadData$default(QuestionKnowledgeListFragment questionKnowledgeListFragment, Map map, boolean z2, int i2, Object obj) throws JSONException {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        questionKnowledgeListFragment.loadData(map, z2);
    }

    private final void refreshNodeInfo(String id, final String nodeId) {
        Context context = this.mContext;
        String str = NetworkRequestsURL.nodeKnowledgeInfo;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("node_id", nodeId);
        ajaxParams.put("knowledge_id", id);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeListFragment.refreshNodeInfo.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                NodeKnowledgeInfo.NodeInfo nodeInfo;
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        NodeKnowledgeInfo nodeKnowledgeInfo = (NodeKnowledgeInfo) new Gson().fromJson(jSONObject.optString("data"), NodeKnowledgeInfo.class);
                        if (nodeKnowledgeInfo != null) {
                            NodeKnowledgeInfo.NodeInfo node = nodeKnowledgeInfo.getNode();
                            ArrayList arrayList = QuestionKnowledgeListFragment.this.mDataList;
                            QuestionKnowledgeListFragment questionKnowledgeListFragment = QuestionKnowledgeListFragment.this;
                            String str2 = nodeId;
                            int i2 = 0;
                            for (Object obj : arrayList) {
                                int i3 = i2 + 1;
                                if (i2 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
                                String id2 = "";
                                List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                                boolean z2 = true;
                                if (children != null) {
                                    Intrinsics.checkNotNullExpressionValue(children, "children");
                                    for (KnowledgeChartNodeBean knowledgeChartNodeBean2 : children) {
                                        if (Intrinsics.areEqual(knowledgeChartNodeBean2.getId(), str2)) {
                                            id2 = knowledgeChartNodeBean.getId();
                                            Intrinsics.checkNotNullExpressionValue(id2, "it.id");
                                            knowledgeChartNodeBean2.setUser_answer_total(node.getUser_answer_total());
                                            knowledgeChartNodeBean2.setRight_rate(node.getRight_rate());
                                            knowledgeChartNodeBean2.setContinueDo(z2);
                                            QuestionKnowledgeListFragment.addOrUpdateChild$default(questionKnowledgeListFragment, str2, false, 2, null);
                                            LogUtils.d("refresh_node", "新增节点  " + knowledgeChartNodeBean2.getName() + " 为继续做");
                                        } else {
                                            if (knowledgeChartNodeBean2.isContinueDo()) {
                                                knowledgeChartNodeBean2.setContinueDo(false);
                                                String id3 = knowledgeChartNodeBean2.getId();
                                                Intrinsics.checkNotNullExpressionValue(id3, "child.id");
                                                nodeInfo = node;
                                                QuestionKnowledgeListFragment.addOrUpdateChild$default(questionKnowledgeListFragment, id3, false, 2, null);
                                                LogUtils.d("refresh_node", "去掉节点 " + knowledgeChartNodeBean2.getName() + " 继续做");
                                            }
                                            node = nodeInfo;
                                            z2 = true;
                                        }
                                        nodeInfo = node;
                                        node = nodeInfo;
                                        z2 = true;
                                    }
                                }
                                NodeKnowledgeInfo.NodeInfo nodeInfo2 = node;
                                if (Intrinsics.areEqual(knowledgeChartNodeBean.getId(), id2)) {
                                    knowledgeChartNodeBean.setContinueDo(true);
                                    LogUtils.d("refresh_node", "新增章节 " + knowledgeChartNodeBean.getName() + " 继续做");
                                    questionKnowledgeListFragment.addOrUpdateParent(i2);
                                } else {
                                    if (knowledgeChartNodeBean.isContinueDo()) {
                                        knowledgeChartNodeBean.setContinueDo(false);
                                        LogUtils.d("refresh_node", "去掉章节 " + knowledgeChartNodeBean.getName() + " 继续做");
                                        questionKnowledgeListFragment.addOrUpdateParent(i2);
                                    }
                                    i2 = i3;
                                    node = nodeInfo2;
                                }
                                i2 = i3;
                                node = nodeInfo2;
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void showLoadingView() {
        View view = this.lyCustomLoading;
        ImageView imageView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lyCustomLoading");
            view = null;
        }
        ViewExtensionsKt.visible(view);
        if (this.mAnimationDrawable == null) {
            ImageView imageView2 = this.loadingView;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadingView");
            } else {
                imageView = imageView2;
            }
            Drawable background = imageView.getBackground();
            Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
            this.mAnimationDrawable = (AnimationDrawable) background;
        }
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    public final void dailyTaskUpdate(@NotNull String jieId, @NotNull String finishCount, @NotNull String rate) {
        Intrinsics.checkNotNullParameter(jieId, "jieId");
        Intrinsics.checkNotNullParameter(finishCount, "finishCount");
        Intrinsics.checkNotNullParameter(rate, "rate");
        Iterator<T> it = this.mDataList.iterator();
        while (it.hasNext()) {
            List<KnowledgeChartNodeBean> children = ((KnowledgeChartNodeBean) it.next()).getChildren();
            if (children != null) {
                Intrinsics.checkNotNullExpressionValue(children, "children");
                int i2 = 0;
                for (Object obj : children) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
                    if (Intrinsics.areEqual(knowledgeChartNodeBean.getId(), jieId)) {
                        knowledgeChartNodeBean.setRight_rate(rate);
                        knowledgeChartNodeBean.setUser_answer_total(finishCount);
                        knowledgeChartNodeBean.setContinueDo(true);
                        String id = knowledgeChartNodeBean.getId();
                        Intrinsics.checkNotNullExpressionValue(id, "item.id");
                        addOrUpdateChild$default(this, id, false, 2, null);
                    }
                    i2 = i3;
                }
            }
        }
    }

    @NotNull
    public final ArrayMap<String, List<String>> getFilterDataMap() {
        return this.defaultFilterMap;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_knowledge_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) throws JSONException {
        SelectIdentityBean.DataBean dataBean;
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.tv_filter_info);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.tv_filter_info)");
        this.tvFilterInfo = (TextView) view;
        View view2 = holder.get(R.id.ll_filter_info);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.ll_filter_info)");
        this.llFilterInfo = view2;
        View view3 = holder.get(R.id.iv_loading);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.iv_loading)");
        this.loadingView = (ImageView) view3;
        View view4 = holder.get(R.id.ll_chapter_list);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.ll_chapter_list)");
        this.llPointsList = (LinearLayout) view4;
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            ImageView imageView = this.loadingView;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("loadingView");
                imageView = null;
            }
            imageView.setBackgroundResource(R.drawable.loading_night_customer);
        }
        View view5 = holder.get(R.id.ll_empty);
        Intrinsics.checkNotNullExpressionValue(view5, "holder.get(R.id.ll_empty)");
        this.llEmpty = view5;
        View view6 = holder.get(R.id.customer_ly_loading);
        Intrinsics.checkNotNullExpressionValue(view6, "holder.get(R.id.customer_ly_loading)");
        this.lyCustomLoading = view6;
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, 0, (String) null);
        this.emptyView = customEmptyView;
        customEmptyView.stopAnim();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.child = (SelectIdentityBean.DataBean) arguments.getSerializable("child");
            boolean z2 = arguments.getBoolean("isDailyTask");
            this.isDailyTaskPage = z2;
            if (!z2 && (dataBean = this.child) != null) {
                Intrinsics.checkNotNull(dataBean);
                loadData$default(this, MapsKt__MapsJVMKt.mapOf(TuplesKt.to(KnowledgeQuestionListFragment.EXTRA_TREE_ID, dataBean.getId())), false, 2, null);
            }
            if (this.isDailyTaskPage) {
                initDailyTaskData();
            }
        }
    }

    public final void loadData(@NotNull Map<String, String> param, final boolean filter) throws JSONException {
        Intrinsics.checkNotNullParameter(param, "param");
        if (this.child == null) {
            hideLoadingView();
            return;
        }
        showLoadingView();
        this.mChildChapterHeightMap.clear();
        this.chapterChildViewsMap.clear();
        this.mDataMap.clear();
        AjaxParams ajaxParams = new AjaxParams();
        SelectIdentityBean.DataBean dataBean = this.child;
        Intrinsics.checkNotNull(dataBean);
        ajaxParams.put(KnowledgeQuestionListFragment.EXTRA_TREE_ID, dataBean.getId());
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (!Intrinsics.areEqual(entry.getKey(), KnowledgeQuestionListFragment.EXTRA_TREE_ID)) {
                jSONObject.put(entry.getKey(), entry.getValue());
            }
        }
        if (jSONObject.length() > 0) {
            ajaxParams.put("type", jSONObject.toString());
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.questionChartTree, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeListFragment.loadData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionKnowledgeListFragment.this.hideLoadingView();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                JSONObject jSONObjectOptJSONObject;
                Intrinsics.checkNotNullParameter(t2, "t");
                QuestionKnowledgeListFragment.this.hideLoadingView();
                try {
                    JSONObject jSONObject2 = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject2.optString("code")) || (jSONObjectOptJSONObject = jSONObject2.optJSONObject("data")) == null) {
                        return;
                    }
                    String strOptString = jSONObjectOptJSONObject.optString("chapter_list");
                    String strOptString2 = jSONObjectOptJSONObject.optString("all_knowledge_count");
                    String strOptString3 = jSONObjectOptJSONObject.optString("all_knowledge_question_count");
                    LinearLayout linearLayout = null;
                    if (filter) {
                        TextView textView = QuestionKnowledgeListFragment.this.tvFilterInfo;
                        if (textView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvFilterInfo");
                            textView = null;
                        }
                        textView.setText("筛选出 " + strOptString2 + " 个考点，包含 " + strOptString3 + " 题");
                        View view = QuestionKnowledgeListFragment.this.llFilterInfo;
                        if (view == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llFilterInfo");
                            view = null;
                        }
                        ViewExtensionsKt.visible(view);
                        View view2 = QuestionKnowledgeListFragment.this.llFilterInfo;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llFilterInfo");
                            view2 = null;
                        }
                        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                        layoutParams2.bottomMargin = SizeUtil.dp2px(((BaseFragment) QuestionKnowledgeListFragment.this).mContext, 16);
                        View view3 = QuestionKnowledgeListFragment.this.llFilterInfo;
                        if (view3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llFilterInfo");
                            view3 = null;
                        }
                        view3.setLayoutParams(layoutParams2);
                    } else {
                        View view4 = QuestionKnowledgeListFragment.this.llFilterInfo;
                        if (view4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llFilterInfo");
                            view4 = null;
                        }
                        ViewExtensionsKt.gone(view4);
                    }
                    Object objFromJson = new Gson().fromJson(strOptString, new TypeToken<List<? extends KnowledgeChartNodeBean>>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeListFragment$loadData$1$onSuccess$data$1
                    }.getType());
                    Intrinsics.checkNotNullExpressionValue(objFromJson, "Gson().fromJson<List<Kno…hartNodeBean>>() {}.type)");
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((Iterable) objFromJson).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        List<KnowledgeChartNodeBean> children = ((KnowledgeChartNodeBean) next).getChildren();
                        if (!(children == null || children.isEmpty())) {
                            arrayList.add(next);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        View view5 = QuestionKnowledgeListFragment.this.llEmpty;
                        if (view5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llEmpty");
                            view5 = null;
                        }
                        ViewExtensionsKt.visible(view5);
                        LinearLayout linearLayout2 = QuestionKnowledgeListFragment.this.llPointsList;
                        if (linearLayout2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llPointsList");
                        } else {
                            linearLayout = linearLayout2;
                        }
                        ViewExtensionsKt.gone(linearLayout);
                        return;
                    }
                    View view6 = QuestionKnowledgeListFragment.this.llEmpty;
                    if (view6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llEmpty");
                        view6 = null;
                    }
                    ViewExtensionsKt.gone(view6);
                    LinearLayout linearLayout3 = QuestionKnowledgeListFragment.this.llPointsList;
                    if (linearLayout3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llPointsList");
                        linearLayout3 = null;
                    }
                    ViewExtensionsKt.visible(linearLayout3);
                    QuestionKnowledgeListFragment questionKnowledgeListFragment = QuestionKnowledgeListFragment.this;
                    int i2 = 0;
                    for (Object obj : arrayList) {
                        int i3 = i2 + 1;
                        if (i2 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
                        List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean.getChildren();
                        if (!(children2 == null || children2.isEmpty())) {
                            ListIterator<KnowledgeChartNodeBean> listIterator = knowledgeChartNodeBean.getChildren().listIterator();
                            while (listIterator.hasNext()) {
                                KnowledgeChartNodeBean next2 = listIterator.next();
                                if (Intrinsics.areEqual(next2.getQuestion_count(), "0")) {
                                    listIterator.remove();
                                } else {
                                    SelectIdentityBean.DataBean dataBean2 = questionKnowledgeListFragment.child;
                                    next2.setDescribe(dataBean2 != null ? dataBean2.getDescribe() : null);
                                    next2.setParentId(knowledgeChartNodeBean.getId());
                                    next2.setParentTitle(knowledgeChartNodeBean.getName());
                                }
                            }
                            List<KnowledgeChartNodeBean> children3 = knowledgeChartNodeBean.getChildren();
                            Intrinsics.checkNotNullExpressionValue(children3, "child.children");
                            int i4 = 0;
                            for (Object obj2 : children3) {
                                int i5 = i4 + 1;
                                if (i4 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                ((KnowledgeChartNodeBean) obj2).setLast(i4 == knowledgeChartNodeBean.getChildren().size() - 1);
                                i4 = i5;
                            }
                            questionKnowledgeListFragment.mDataMap.put(Integer.valueOf(i2), knowledgeChartNodeBean.getChildren());
                        }
                        i2 = i3;
                    }
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        ((KnowledgeChartNodeBean) it2.next()).setExpand(false);
                    }
                    QuestionKnowledgeListFragment.this.mDataList.clear();
                    QuestionKnowledgeListFragment.this.mDataList.addAll(arrayList);
                    QuestionKnowledgeListFragment.this.initLastDoShow(filter);
                    QuestionKnowledgeListFragment.this.initViewList(arrayList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public final void onEventMainThread(@NotNull RefreshChapterKnowledgeEvent e2) throws JSONException {
        Intrinsics.checkNotNullParameter(e2, "e");
        if (this.isDailyTaskPage) {
            return;
        }
        String treeId = e2.getTreeId();
        SelectIdentityBean.DataBean dataBean = this.child;
        Intrinsics.checkNotNull(dataBean);
        if (TextUtils.equals(treeId, dataBean.getId())) {
            Map<String, String> arrayMap = new ArrayMap<>();
            for (Map.Entry<String, List<String>> entry : this.defaultFilterMap.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "entry.value");
                arrayMap.put(key, CollectionsKt___CollectionsKt.joinToString$default(value, ",", null, null, 0, null, null, 62, null));
            }
            loadData(arrayMap, !arrayMap.isEmpty());
        }
    }

    public final void scroll2SpecifiedChapter() {
        if (this.child != null) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_PART_ID, "", this.mContext);
            SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", "", this.mContext);
        }
    }

    public final void setFilterDataMap(@NotNull ArrayMap<String, List<String>> filter) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        this.defaultFilterMap.clear();
        this.defaultFilterMap.putAll((ArrayMap<? extends String, ? extends List<String>>) filter);
    }

    public final void writeFilterStr(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.filterStr = value;
    }

    public final void writeFilterValue(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.filterValue = value;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEventMainThread(@NotNull NodeUpdateEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        Iterator<T> it = this.mDataList.iterator();
        while (it.hasNext()) {
            List<KnowledgeChartNodeBean> children = ((KnowledgeChartNodeBean) it.next()).getChildren();
            if (children != null) {
                Intrinsics.checkNotNullExpressionValue(children, "children");
                int i2 = 0;
                for (Object obj : children) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
                    if (Intrinsics.areEqual(knowledgeChartNodeBean.getId(), e2.getNodeId())) {
                        knowledgeChartNodeBean.setRight_rate(e2.getRight_rate());
                        knowledgeChartNodeBean.setUser_answer_total(e2.getUser_answer_total());
                        knowledgeChartNodeBean.setContinueDo(true);
                        String id = knowledgeChartNodeBean.getId();
                        Intrinsics.checkNotNullExpressionValue(id, "item.id");
                        addOrUpdateChild$default(this, id, false, 2, null);
                    }
                    i2 = i3;
                }
            }
        }
    }

    @Subscribe
    public final void onEventMainThread(@NotNull EventBusMessage<?> msg) {
        Object valueObj;
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (this.isDailyTaskPage) {
            if (Intrinsics.areEqual(msg.getKey(), EventBusConstant.EVENT_QUESTION_UPDATE_DAILY_TASK_LIST)) {
                FragmentActivity activity = getActivity();
                Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.psychiatrygarden.activity.knowledge.DailyTasksActivity");
                SelectIdentityBean.DataBean dataBean = this.child;
                Intrinsics.checkNotNull(dataBean);
                String id = dataBean.getId();
                Intrinsics.checkNotNullExpressionValue(id, "child!!.id");
                Iterator<KnowledgeChartNodeBean> it = ((DailyTasksActivity) activity).getFragmentData(id).iterator();
                while (it.hasNext()) {
                    List<KnowledgeChartNodeBean> children = it.next().getChildren();
                    if (children != null) {
                        for (KnowledgeChartNodeBean knowledgeChartNodeBean : children) {
                            String id2 = knowledgeChartNodeBean.getId();
                            Intrinsics.checkNotNullExpressionValue(id2, "it.id");
                            String user_answer_total = knowledgeChartNodeBean.getUser_answer_total();
                            Intrinsics.checkNotNullExpressionValue(user_answer_total, "it.user_answer_total");
                            String right_rate = knowledgeChartNodeBean.getRight_rate();
                            Intrinsics.checkNotNullExpressionValue(right_rate, "it.right_rate");
                            dailyTaskUpdate(id2, user_answer_total, right_rate);
                        }
                    }
                }
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(msg.getKey(), "EVENT_QUESTION_ANSWER_REFRESH_KNOWLEDGE") && (valueObj = msg.getValueObj()) != null && (valueObj instanceof String)) {
            List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) String.valueOf(valueObj), new String[]{"-"}, false, 0, 6, (Object) null);
            if ((!listSplit$default.isEmpty()) && listSplit$default.size() == 2) {
                refreshNodeInfo((String) listSplit$default.get(1), (String) listSplit$default.get(0));
            }
        }
        Intrinsics.areEqual(msg.getKey(), EventBusConstant.REFRESH_KNOWLEDGE_LIST);
    }

    @Subscribe
    public final void onEventMainThread(@NotNull RefreshLastDoEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        String knowledgeId = e2.getKnowledgeId();
        String nodeId = e2.getNodeId();
        if (TextUtils.isEmpty(knowledgeId) || TextUtils.isEmpty(nodeId)) {
            return;
        }
        int i2 = 0;
        for (Object obj : this.mDataList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) obj;
            List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
            boolean z2 = true;
            if (children != null) {
                Intrinsics.checkNotNullExpressionValue(children, "children");
                for (KnowledgeChartNodeBean knowledgeChartNodeBean2 : children) {
                    if (Intrinsics.areEqual(knowledgeChartNodeBean2.getId(), knowledgeId)) {
                        knowledgeChartNodeBean2.setContinueDo(z2);
                        Intrinsics.checkNotNullExpressionValue(knowledgeId, "knowledgeId");
                        addOrUpdateChild$default(this, knowledgeId, false, 2, null);
                        LogUtils.d("refresh_node", "新增节点  " + knowledgeChartNodeBean2.getName() + " 为继续做");
                    } else if (knowledgeChartNodeBean2.isContinueDo()) {
                        knowledgeChartNodeBean2.setContinueDo(false);
                        String id = knowledgeChartNodeBean2.getId();
                        Intrinsics.checkNotNullExpressionValue(id, "child.id");
                        addOrUpdateChild$default(this, id, false, 2, null);
                        LogUtils.d("refresh_node", "去掉节点 " + knowledgeChartNodeBean2.getName() + " 继续做");
                    }
                    z2 = true;
                }
            }
            if (Intrinsics.areEqual(knowledgeChartNodeBean.getId(), nodeId)) {
                knowledgeChartNodeBean.setContinueDo(true);
                LogUtils.d("refresh_node", "新增章节 " + knowledgeChartNodeBean.getName() + " 继续做");
                addOrUpdateParent(i2);
            } else if (knowledgeChartNodeBean.isContinueDo()) {
                knowledgeChartNodeBean.setContinueDo(false);
                LogUtils.d("refresh_node", "去掉章节 " + knowledgeChartNodeBean.getName() + " 继续做");
                addOrUpdateParent(i2);
            }
            i2 = i3;
        }
    }
}
