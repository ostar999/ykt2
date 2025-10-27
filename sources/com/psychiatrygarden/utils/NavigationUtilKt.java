package com.psychiatrygarden.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.StudyPlanListActivity;
import com.psychiatrygarden.activity.chooseSchool.SearchTargetSchoolActivity;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.CurriculumDownLoadManageActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment;
import com.psychiatrygarden.activity.purchase.activity.ChangeAddrActivity;
import com.psychiatrygarden.activity.purchase.activity.ShouhuodizhiActivity;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\r\u001a2\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u001a4\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f\u001a@\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u00052\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u001a\"\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u001a\u001e\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005\u001a:\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001b\u001a\u00020\f2\u0010\u0010\u001c\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001f\u001a\u001e\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\f\u001a:\u0010!\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u00052\b\u0010$\u001a\u0004\u0018\u00010\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0005\u001a\u0016\u0010&\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u0005\u001a\u000e\u0010(\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010)\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010*\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010+\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006,"}, d2 = {"getToCurriculumDownLoadManage", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "courseId", "", "type", "title", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "goToActSubmitGoodsComment", "orderNo", "courseTypeNerVersion", "", "newTask", "goToAliPlayerVideoPlayActivity", "obj_id", "chapterId", "vid", "video_type", "goToChangeAddressActivity", "dataBean", "Lcom/psychiatrygarden/activity/purchase/beans/ShowAddressBean$DataBean;", "goToCourseOrGoodsDetailNewTask", "goodId", "goToShouHuoDiZhiActivity", "Landroid/app/Activity;", "fromType", "duoXuan", "addressDataBeans", "", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "", "duoxuan", "gotoChartAnswerSheetActivity", "knowledgeId", "domainType", KnowledgeQuestionListFragment.EXTRA_LEVEL1_ID, "node_id", "gotoGoodsDetail", "goodsId", "gotoSearchTargetSchoolActivity", "gotoStudyPlanListActivity", "gotoVipCenter", "gotoVipCenterDailyTask", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNavigationUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NavigationUtil.kt\ncom/psychiatrygarden/utils/NavigationUtilKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,232:1\n1#2:233\n*E\n"})
/* loaded from: classes6.dex */
public final class NavigationUtilKt {
    public static final void getToCurriculumDownLoadManage(@NotNull Context context, @NotNull String courseId, @NotNull String type, @Nullable String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        Intrinsics.checkNotNullParameter(type, "type");
        Bundle bundle = new Bundle();
        bundle.putString("course_id", courseId);
        bundle.putString("type", type);
        if (str == null) {
            str = "";
        }
        bundle.putString("title", str);
        if (str2 == null) {
            str2 = "";
        }
        bundle.putString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, str2);
        Intent intent = new Intent(context, (Class<?>) CurriculumDownLoadManageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static final void goToActSubmitGoodsComment(@NotNull Context context, @Nullable String str, @Nullable String str2, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (str == null || str.length() == 0) {
            return;
        }
        if (str2 == null || str2.length() == 0) {
            return;
        }
        Intent intent = new Intent(context, (Class<?>) ActSubmitGoodsComment.class);
        intent.putExtra("course_id", str);
        intent.putExtra(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, str2);
        intent.putExtra(ActSubmitGoodsComment.EXTRA_DATA_TYPE, z2);
        if (z3) {
            intent.setFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static /* synthetic */ void goToActSubmitGoodsComment$default(Context context, String str, String str2, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            z3 = false;
        }
        goToActSubmitGoodsComment(context, str, str2, z2, z3);
    }

    public static final void goToAliPlayerVideoPlayActivity(@NotNull Context context, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) AliPlayerVideoPlayActivity.class);
        intent.putExtra("obj_id", str);
        intent.putExtra("chapter_id", str2);
        intent.putExtra("vid", str3);
        intent.putExtra("watch_permission", "1");
        intent.putExtra("expire_str", "");
        intent.putExtra("module_type", 8);
        intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "");
        intent.putExtra("seeDuration", "0");
        intent.putExtra("commentEnum", DiscussStatus.CourseReview);
        intent.putExtra(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, str4);
        intent.putExtra("type", str5);
        context.startActivity(intent);
    }

    public static final void goToChangeAddressActivity(@NotNull Context context, @Nullable ShowAddressBean.DataBean dataBean, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) ChangeAddrActivity.class);
        if (dataBean != null) {
            intent.putExtra("dataBean", dataBean);
        }
        if (str != null) {
            intent.putExtra(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, str);
        }
        context.startActivity(intent);
    }

    public static final void goToCourseOrGoodsDetailNewTask(@NotNull Context context, @NotNull String courseId, @NotNull String goodId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        Intrinsics.checkNotNullParameter(goodId, "goodId");
        Intent intent = new Intent(context, (Class<?>) ActCourseOrGoodsDetail.class);
        intent.putExtra("course_id", courseId);
        intent.putExtra("goods_id", goodId);
        if (!TextUtils.isEmpty(goodId)) {
            intent.putExtra("detailType", 2);
        }
        if (!TextUtils.isEmpty(courseId)) {
            intent.putExtra("detailType", 1);
        }
        context.startActivity(intent);
    }

    public static final void goToShouHuoDiZhiActivity(@NotNull Context context, @NotNull String fromType, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fromType, "fromType");
        Intent intent = new Intent(context, (Class<?>) ShouhuodizhiActivity.class);
        intent.putExtra("isDuoxuan", z2 ? "1" : "0");
        intent.putExtra(ShouhuodizhiActivity.FROM_TYPE, fromType);
        context.startActivity(intent);
    }

    public static final void gotoChartAnswerSheetActivity(@NotNull Context context, @NotNull String title, @NotNull String knowledgeId, @NotNull String domainType, @Nullable String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(knowledgeId, "knowledgeId");
        Intrinsics.checkNotNullParameter(domainType, "domainType");
        Intent intent = new Intent(context, (Class<?>) ChartAnswerSheetActivity.class);
        intent.putExtra("knowledge_id", knowledgeId);
        intent.putExtra("isKnowledge", true);
        intent.putExtra("type", domainType);
        intent.putExtra("title", title);
        if (!TextUtils.isEmpty(str2)) {
            intent.putExtra("node_id", str2);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("question_bank_id", str);
        }
        context.startActivity(intent);
    }

    public static final void gotoGoodsDetail(@NotNull Context context, @NotNull String goodsId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(goodsId, "goodsId");
        Intent intent = new Intent(context, (Class<?>) ActCourseOrGoodsDetail.class);
        intent.putExtra("goods_id", goodsId);
        intent.putExtra("detailType", 2);
        context.startActivity(intent);
    }

    public static final void gotoSearchTargetSchoolActivity(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.startActivity(SearchTargetSchoolActivity.newIntent(context));
    }

    public static final void gotoStudyPlanListActivity(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.startActivity(new Intent(context, (Class<?>) StudyPlanListActivity.class));
    }

    public static final void gotoVipCenter(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) MemberCenterActivity.class);
        intent.putExtra("psotision", 1);
        context.startActivity(intent);
    }

    public static final void gotoVipCenterDailyTask(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) MemberCenterActivity.class);
        intent.putExtra("psotision", 1);
        intent.putExtra("fromDailyTask", "1");
        context.startActivity(intent);
    }

    public static final void goToShouHuoDiZhiActivity(@NotNull Activity context, @Nullable String str, boolean z2, @Nullable List<? extends ShowAddressBean.DataBean> list, int i2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) ShouhuodizhiActivity.class);
        intent.putExtra("addList", (Serializable) list);
        intent.putExtra("isDuoxuan", z2 ? "1" : "0");
        intent.putExtra(ShouhuodizhiActivity.FROM_TYPE, str);
        intent.putExtra("orderConfirmNeedAddress", true);
        intent.putExtra(ShouhuodizhiActivity.FROM_TYPE, ShouhuodizhiActivity.FROM_TYPE_VALUE_ORDER_CONFIRM);
        context.startActivityForResult(intent, i2);
    }
}
