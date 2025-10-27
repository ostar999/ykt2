package com.psychiatrygarden.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import cn.hutool.core.lang.RegexPool;
import cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.CourseCombineDireListActivity;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.pop.NotSaleAloneUnlockPop;
import com.psychiatrygarden.bean.BottomBtn;
import com.psychiatrygarden.bean.CommonProblem;
import com.psychiatrygarden.bean.CourseDetailBean;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseGiftItem;
import com.psychiatrygarden.bean.CourseStructureParams;
import com.psychiatrygarden.bean.CourseTeacher;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.bean.ParentCourseBean;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.bean.SkuItem;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.HaveFreeWatchEvent;
import com.psychiatrygarden.event.RefreshCourseDownloadedEvent;
import com.psychiatrygarden.event.RefreshVideoProgressEvent;
import com.psychiatrygarden.event.ShareEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.BaseContentWidget;
import com.psychiatrygarden.widget.CommonLoadingPop;
import com.psychiatrygarden.widget.CommonProblemWidget;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DetailCourseCommentWidget;
import com.psychiatrygarden.widget.DetailCourseContentWidget;
import com.psychiatrygarden.widget.DetailCourseDirectoryWidget;
import com.psychiatrygarden.widget.DetailCourseStructureWidget;
import com.psychiatrygarden.widget.DetailCourseTeacherInfoWidget;
import com.psychiatrygarden.widget.DetailCourseTopInfoWidget;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000Á\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011*\u0001\b\u0018\u0000 v2\u00020\u00012\u00020\u0002:\u0001vB\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u001eJ\b\u0010M\u001a\u00020KH\u0002J\b\u0010N\u001a\u00020KH\u0002J\u000e\u0010O\u001a\u00020K2\u0006\u0010P\u001a\u00020QJ\b\u0010R\u001a\u00020KH\u0002J\u0010\u0010S\u001a\u00020K2\u0006\u0010T\u001a\u00020\u0015H\u0002J\b\u0010U\u001a\u00020KH\u0016J\u0012\u0010V\u001a\u00020K2\b\u0010I\u001a\u0004\u0018\u00010\u000bH\u0002J\u0012\u0010W\u001a\u00020K2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u000bJ\u000e\u0010X\u001a\u00020K2\u0006\u0010\u0016\u001a\u00020\u000bJ\"\u0010Y\u001a\u00020K2\u0006\u0010Z\u001a\u00020\u00102\u0006\u0010[\u001a\u00020\u00102\b\u0010\\\u001a\u0004\u0018\u00010]H\u0014J\u0012\u0010^\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010_\u001a\u00020KH\u0014J\u0010\u0010`\u001a\u00020K2\u0006\u0010a\u001a\u00020bH\u0007J\u0010\u0010`\u001a\u00020K2\u0006\u0010c\u001a\u00020dH\u0007J\u0010\u0010`\u001a\u00020K2\u0006\u0010a\u001a\u00020eH\u0007J\u0010\u0010`\u001a\u00020K2\u0006\u0010c\u001a\u00020fH\u0007J\u0012\u0010`\u001a\u00020K2\b\u0010g\u001a\u0004\u0018\u00010\u000bH\u0016J\u0006\u0010h\u001a\u00020KJ\u000e\u0010i\u001a\u00020K2\u0006\u0010j\u001a\u00020\u0015J\b\u0010k\u001a\u00020KH\u0016J\b\u0010l\u001a\u00020KH\u0016J\b\u0010m\u001a\u00020KH\u0002J\u0012\u0010n\u001a\u00020K2\b\u0010o\u001a\u0004\u0018\u00010\u000bH\u0002J\b\u0010p\u001a\u00020KH\u0002J\u0010\u0010q\u001a\u00020K2\u0006\u0010\\\u001a\u00020\u0015H\u0002J\u0010\u0010r\u001a\u00020K2\u0006\u0010I\u001a\u00020\u0010H\u0002J\u0012\u0010s\u001a\u00020K2\b\b\u0002\u0010t\u001a\u00020\u0005H\u0002J\u0010\u0010u\u001a\u00020K2\u0006\u0010I\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u00100\u000fj\b\u0012\u0004\u0012\u00020\u0010`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u000fj\b\u0012\u0004\u0012\u00020\u0013`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00050;X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020?X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00050;X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010B\u001a\u0012\u0012\u0004\u0012\u00020C0\u000fj\b\u0012\u0004\u0012\u00020C`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010EX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020GX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006w"}, d2 = {"Lcom/psychiatrygarden/activity/ActCourseOrGoodsDetail;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "buyNotSaleAlone", "", "calcHeight", "clickTabHandler", "com/psychiatrygarden/activity/ActCourseOrGoodsDetail$clickTabHandler$1", "Lcom/psychiatrygarden/activity/ActCourseOrGoodsDetail$clickTabHandler$1;", "collectionTitle", "", "commonNavigator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/CommonNavigator;", "contentHeightList", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "contentViewList", "Lcom/psychiatrygarden/widget/BaseContentWidget;", "courseDetailBean", "Lcom/psychiatrygarden/bean/CourseDetailBean;", "courseId", "courseTitle", "Landroid/widget/TextView;", "courseTopHeight", "detailBean", "detailType", "directoryExpandOrCollapse", "directoryView", "Landroid/view/View;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "finalVerticalDisplacement", "firstLoadSku", "goodsId", "hasPermission", "initHeight", "initPermission", "initialScrollPosition", "isClickScroll", "isCourseCollection", "isGoodsDetail", "ivBack2Top", "Landroid/widget/ImageView;", "llBuyNow", "Landroid/widget/LinearLayout;", "llBuyVipStudy", "llContent", "llGoods", "llJoinActivitiesStudy", "llNotSaleAlone", "llSoldOut", "llStartStudy", "llStopSale", "llTakeNow", "llTopTitle", "llUnlockCourse", "loadingMap", "Landroid/util/ArrayMap;", "mFragmentContainerHelper", "Lcn/webdemo/com/supporfragment/tablayout/FragmentContainerHelper;", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "sendNotice", "showPopMap", "skuList", "Lcom/psychiatrygarden/bean/SkuItem;", "skuLoadingPop", "Lcom/psychiatrygarden/widget/CommonLoadingPop;", com.alipay.sdk.sys.a.f3323h, "Landroidx/core/widget/NestedScrollView;", "tvGoods", "type", "btnCLick", "", "v", "calculateViewLocation", "checkShowCoupon", "clickDirectoryItem", "w", "Lcom/psychiatrygarden/widget/DetailCourseDirectoryWidget;", "getCourse", "gotoOrderConfirm", AdvanceSetting.NETWORK_TYPE, "init", "initTopNavigator", "loadDetailData", "loadSkuItemDetail", "onActivityResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "resultCode", "data", "Landroid/content/Intent;", "onClick", "onDestroy", "onEventMainThread", NotificationCompat.CATEGORY_EVENT, "Lcom/psychiatrygarden/event/HaveFreeWatchEvent;", AliyunLogKey.KEY_EVENT, "Lcom/psychiatrygarden/event/RefreshCourseDownloadedEvent;", "Lcom/psychiatrygarden/event/RefreshVideoProgressEvent;", "Lcom/psychiatrygarden/event/ShareEvent;", "str", "refresh", "setChildCourseData", "d", "setContentView", "setListenerForWidget", "setOnScrollListener", "shareGetCourse", AdUnitActivity.EXTRA_ACTIVITY_ID, "showBottomBtn", "showCourseDetail", "unlock", "updateTrySeeMargin", "isSelect", "vipTakeCourse", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nActCourseOrGoodsDetail.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActCourseOrGoodsDetail.kt\ncom/psychiatrygarden/activity/ActCourseOrGoodsDetail\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1676:1\n1#2:1677\n1855#3,2:1678\n1864#3,3:1680\n1789#3,3:1683\n766#3:1686\n857#3,2:1687\n766#3:1689\n857#3,2:1690\n766#3:1692\n857#3,2:1693\n1855#3,2:1695\n1855#3,2:1697\n1789#3,3:1699\n1789#3,3:1702\n1789#3,3:1705\n*S KotlinDebug\n*F\n+ 1 ActCourseOrGoodsDetail.kt\ncom/psychiatrygarden/activity/ActCourseOrGoodsDetail\n*L\n887#1:1678,2\n958#1:1680,3\n1016#1:1683,3\n1129#1:1686\n1129#1:1687,2\n1238#1:1689\n1238#1:1690,2\n1240#1:1692\n1240#1:1693,2\n1600#1:1695,2\n1613#1:1697,2\n1006#1:1699,3\n1027#1:1702,3\n1039#1:1705,3\n*E\n"})
/* loaded from: classes5.dex */
public final class ActCourseOrGoodsDetail extends BaseActivity implements View.OnClickListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final int DETAIL_TYPE_COURSE = 1;
    public static final int DETAIL_TYPE_GOODS = 2;
    private boolean buyNotSaleAlone;
    private boolean calcHeight;

    @Nullable
    private String collectionTitle;
    private CommonNavigator commonNavigator;

    @Nullable
    private CourseDetailBean courseDetailBean;
    private String courseId;
    private TextView courseTitle;
    private int courseTopHeight;

    @Nullable
    private CourseDetailBean detailBean;
    private int detailType;
    private boolean directoryExpandOrCollapse;

    @Nullable
    private View directoryView;
    private CustomEmptyView emptyView;
    private int finalVerticalDisplacement;
    private String goodsId;
    private boolean hasPermission;
    private boolean initHeight;
    private boolean initPermission;
    private int initialScrollPosition;
    private boolean isClickScroll;
    private boolean isCourseCollection;
    private boolean isGoodsDetail;
    private ImageView ivBack2Top;
    private LinearLayout llBuyNow;
    private LinearLayout llBuyVipStudy;
    private LinearLayout llContent;
    private LinearLayout llGoods;
    private LinearLayout llJoinActivitiesStudy;
    private LinearLayout llNotSaleAlone;
    private LinearLayout llSoldOut;
    private LinearLayout llStartStudy;
    private LinearLayout llStopSale;
    private LinearLayout llTakeNow;
    private LinearLayout llTopTitle;
    private LinearLayout llUnlockCourse;
    private FragmentContainerHelper mFragmentContainerHelper;
    private MagicIndicator magicIndicator;
    private boolean sendNotice;

    @Nullable
    private CommonLoadingPop skuLoadingPop;
    private NestedScrollView sv;
    private TextView tvGoods;
    private String type;

    @NotNull
    private final ArrayList<SkuItem> skuList = new ArrayList<>();

    @NotNull
    private final ArrayList<BaseContentWidget> contentViewList = new ArrayList<>();

    @NotNull
    private final ArrayMap<String, Boolean> loadingMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<String, Boolean> showPopMap = new ArrayMap<>();

    @NotNull
    private final ArrayList<Integer> contentHeightList = new ArrayList<>();
    private boolean firstLoadSku = true;

    @NotNull
    private final ActCourseOrGoodsDetail$clickTabHandler$1 clickTabHandler = new Handler() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$clickTabHandler$1
        @Override // android.os.Handler
        public void handleMessage(@NotNull Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            super.handleMessage(msg);
            this.this$0.isClickScroll = false;
        }
    };

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/activity/ActCourseOrGoodsDetail$Companion;", "", "()V", "DETAIL_TYPE_COURSE", "", "DETAIL_TYPE_GOODS", "navigationToCourseOrGoodsDetail", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "courseId", "", "goodId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToCourseOrGoodsDetail(@NotNull Context context, @NotNull String courseId, @NotNull String goodId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(courseId, "courseId");
            Intrinsics.checkNotNullParameter(goodId, "goodId");
            Intent intent = new Intent(context, (Class<?>) ActCourseOrGoodsDetail.class);
            if (!TextUtils.isEmpty(goodId)) {
                intent.putExtra("detailType", 2);
            }
            if (!TextUtils.isEmpty(courseId)) {
                intent.putExtra("detailType", 1);
            }
            intent.putExtra("course_id", courseId);
            intent.putExtra("goods_id", goodId);
            context.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u001a\u0010\b\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/ActCourseOrGoodsDetail$initTopNavigator$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nActCourseOrGoodsDetail.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActCourseOrGoodsDetail.kt\ncom/psychiatrygarden/activity/ActCourseOrGoodsDetail$initTopNavigator$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1676:1\n1#2:1677\n*E\n"})
    /* renamed from: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$initTopNavigator$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05601 extends CommonNavigatorAdapter {
        final /* synthetic */ ArrayList<String> $indexListStr;
        final /* synthetic */ ActCourseOrGoodsDetail this$0;

        public C05601(ArrayList<String> arrayList, ActCourseOrGoodsDetail actCourseOrGoodsDetail) {
            this.$indexListStr = arrayList;
            this.this$0 = actCourseOrGoodsDetail;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$4$lambda$3(final ActCourseOrGoodsDetail this$0, int i2, View view) {
            Object next;
            Object next2;
            int top2;
            int bottom;
            Object next3;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            LinearLayout linearLayout = this$0.llTopTitle;
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                linearLayout = null;
            }
            if (linearLayout.getAlpha() < 1.0f) {
                return;
            }
            if (!this$0.initHeight) {
                this$0.calculateViewLocation();
            }
            FragmentContainerHelper fragmentContainerHelper = this$0.mFragmentContainerHelper;
            if (fragmentContainerHelper == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFragmentContainerHelper");
                fragmentContainerHelper = null;
            }
            fragmentContainerHelper.handlePageSelected(i2);
            this$0.clickTabHandler.removeCallbacksAndMessages(null);
            int[] iArr = new int[2];
            LinearLayout linearLayout3 = this$0.llTopTitle;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                linearLayout3 = null;
            }
            linearLayout3.getLocationOnScreen(iArr);
            this$0.isClickScroll = true;
            if (i2 != 1) {
                if (i2 == 2) {
                    if (this$0.detailType == 2) {
                        int iIntValue = ((Number) this$0.contentHeightList.get(0)).intValue();
                        Object obj = this$0.contentHeightList.get(1);
                        Intrinsics.checkNotNullExpressionValue(obj, "contentHeightList[1]");
                        top2 = iIntValue + ((Number) obj).intValue();
                        LinearLayout linearLayout4 = this$0.llTopTitle;
                        if (linearLayout4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                            linearLayout4 = null;
                        }
                        bottom = linearLayout4.getBottom();
                    } else {
                        Iterator it = this$0.contentViewList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next2 = null;
                                break;
                            } else {
                                next2 = it.next();
                                if (((BaseContentWidget) next2) instanceof DetailCourseCommentWidget) {
                                    break;
                                }
                            }
                        }
                        Object obj2 = (BaseContentWidget) next2;
                        if (obj2 == null) {
                            return;
                        }
                        top2 = ((View) obj2).getTop();
                        LinearLayout linearLayout5 = this$0.llTopTitle;
                        if (linearLayout5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                            linearLayout5 = null;
                        }
                        bottom = linearLayout5.getLayoutParams().height;
                    }
                    int i3 = top2 - bottom;
                    NestedScrollView nestedScrollView = this$0.sv;
                    if (nestedScrollView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
                        nestedScrollView = null;
                    }
                    nestedScrollView.smoothScrollTo(0, i3);
                } else if (this$0.detailType == 1) {
                    Iterator it2 = this$0.contentViewList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            next3 = null;
                            break;
                        }
                        next3 = it2.next();
                        BaseContentWidget baseContentWidget = (BaseContentWidget) next3;
                        if ((baseContentWidget instanceof DetailCourseDirectoryWidget) || (baseContentWidget instanceof DetailCourseStructureWidget)) {
                            break;
                        }
                    }
                    Object obj3 = (BaseContentWidget) next3;
                    if (obj3 == null) {
                        return;
                    }
                    NestedScrollView nestedScrollView2 = this$0.sv;
                    if (nestedScrollView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
                        nestedScrollView2 = null;
                    }
                    int top3 = ((View) obj3).getTop();
                    LinearLayout linearLayout6 = this$0.llTopTitle;
                    if (linearLayout6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                        linearLayout6 = null;
                    }
                    nestedScrollView2.smoothScrollTo(0, top3 - linearLayout6.getLayoutParams().height);
                    LinearLayout linearLayout7 = this$0.llTopTitle;
                    if (linearLayout7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                        linearLayout7 = null;
                    }
                    linearLayout7.setAlpha(1.0f);
                    LinearLayout linearLayout8 = this$0.llTopTitle;
                    if (linearLayout8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                        linearLayout8 = null;
                    }
                    ViewExtensionsKt.visible(linearLayout8);
                    View viewFindViewById = this$0.findViewById(R.id.iv_back_);
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById, "this@ActCourseOrGoodsDet…ById<View>(R.id.iv_back_)");
                    ViewExtensionsKt.gone(viewFindViewById);
                } else {
                    NestedScrollView nestedScrollView3 = this$0.sv;
                    if (nestedScrollView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
                        nestedScrollView3 = null;
                    }
                    nestedScrollView3.smoothScrollTo(0, 0);
                    View viewFindViewById2 = this$0.findViewById(R.id.iv_back_);
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "this@ActCourseOrGoodsDet…ById<View>(R.id.iv_back_)");
                    ViewExtensionsKt.visible(viewFindViewById2);
                }
            } else if (this$0.detailType == 1) {
                Iterator it3 = this$0.contentViewList.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        next = null;
                        break;
                    } else {
                        next = it3.next();
                        if (((BaseContentWidget) next) instanceof DetailCourseContentWidget) {
                            break;
                        }
                    }
                }
                Object obj4 = (BaseContentWidget) next;
                if (obj4 == null) {
                    return;
                }
                NestedScrollView nestedScrollView4 = this$0.sv;
                if (nestedScrollView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
                    nestedScrollView4 = null;
                }
                int top4 = ((View) obj4).getTop();
                LinearLayout linearLayout9 = this$0.llTopTitle;
                if (linearLayout9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                    linearLayout9 = null;
                }
                nestedScrollView4.smoothScrollTo(0, top4 - linearLayout9.getLayoutParams().height);
                View viewFindViewById3 = this$0.findViewById(R.id.iv_back_);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "this@ActCourseOrGoodsDet…ById<View>(R.id.iv_back_)");
                ViewExtensionsKt.visible(viewFindViewById3);
            } else {
                int iIntValue2 = ((Number) this$0.contentHeightList.get(0)).intValue();
                LinearLayout linearLayout10 = this$0.llTopTitle;
                if (linearLayout10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                    linearLayout10 = null;
                }
                int i4 = iIntValue2 - linearLayout10.getLayoutParams().height;
                NestedScrollView nestedScrollView5 = this$0.sv;
                if (nestedScrollView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
                    nestedScrollView5 = null;
                }
                nestedScrollView5.smoothScrollTo(0, i4);
            }
            this$0.clickTabHandler.sendEmptyMessageDelayed(1, 1000L);
            if (i2 != 0 || this$0.detailType == 1) {
                return;
            }
            LinearLayout linearLayout11 = this$0.llTopTitle;
            if (linearLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                linearLayout11 = null;
            }
            ViewExtensionsKt.gone(linearLayout11);
            LinearLayout linearLayout12 = this$0.llTopTitle;
            if (linearLayout12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
            } else {
                linearLayout2 = linearLayout12;
            }
            linearLayout2.animate().alpha(0.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$initTopNavigator$1$getTitleView$1$1$1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(@NotNull Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(@NotNull Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                    LinearLayout linearLayout13 = this$0.llTopTitle;
                    if (linearLayout13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
                        linearLayout13 = null;
                    }
                    ViewExtensionsKt.gone(linearLayout13);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(@NotNull Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(@NotNull Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                }
            }).start();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.$indexListStr.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerIndicator getIndicator(@Nullable Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(this.this$0);
            ActCourseOrGoodsDetail actCourseOrGoodsDetail = this.this$0;
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 16.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 100.0d));
            Integer[] numArr = new Integer[1];
            numArr[0] = Integer.valueOf(actCourseOrGoodsDetail.getColor(SkinManager.getCurrentSkinType(context) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color));
            linePagerIndicator.setColors(numArr);
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@Nullable Context context, final int index) {
            final ActCourseOrGoodsDetail actCourseOrGoodsDetail = this.this$0;
            GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$initTopNavigator$1$getTitleView$titleView$1
                {
                    super(this.this$0);
                }

                @Override // com.psychiatrygarden.widget.GoodsSimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
                public void onDeselected(int index2, int totalCount) {
                    super.onDeselected(index2, totalCount);
                    setTextSize(2, 14.0f);
                    if (index2 == 0) {
                        this.this$0.updateTrySeeMargin(false);
                    }
                }

                @Override // com.psychiatrygarden.widget.GoodsSimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
                public void onSelected(int index2, int totalCount) {
                    super.onSelected(index2, totalCount);
                    setTextSize(2, 16.0f);
                    if (index2 == 0) {
                        this.this$0.updateTrySeeMargin(true);
                    }
                }
            };
            if (index == 1) {
                this.this$0.directoryView = goodsSimplePagerTitleView;
            }
            final ActCourseOrGoodsDetail actCourseOrGoodsDetail2 = this.this$0;
            ArrayList<String> arrayList = this.$indexListStr;
            int color = actCourseOrGoodsDetail2.getColor(R.color.third_txt_color);
            int color2 = actCourseOrGoodsDetail2.getColor(R.color.first_txt_color);
            if (SkinManager.getCurrentSkinType(context) == 1) {
                color = actCourseOrGoodsDetail2.getColor(R.color.third_txt_color_night);
                color2 = actCourseOrGoodsDetail2.getColor(R.color.first_txt_color_night);
            }
            goodsSimplePagerTitleView.setSelectedColor(color2);
            goodsSimplePagerTitleView.setNormalColor(color);
            goodsSimplePagerTitleView.setText(arrayList.get(index));
            goodsSimplePagerTitleView.setTextSize(2, index == 0 ? 16.0f : 14.0f);
            goodsSimplePagerTitleView.setMinWidth(CommonUtil.dip2px(actCourseOrGoodsDetail2, 70.0f));
            goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActCourseOrGoodsDetail.C05601.getTitleView$lambda$4$lambda$3(actCourseOrGoodsDetail2, index, view);
                }
            });
            return goodsSimplePagerTitleView;
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/ActCourseOrGoodsDetail$loadDetailData$2", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$loadDetailData$2, reason: invalid class name */
    public static final class AnonymousClass2 extends AjaxCallBack<String> {
        final /* synthetic */ String $courseId;

        public AnonymousClass2(String str) {
            this.$courseId = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onFailure$lambda$2(ActCourseOrGoodsDetail this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            CommonLoadingPop commonLoadingPop = this$0.skuLoadingPop;
            if (commonLoadingPop != null) {
                commonLoadingPop.toggle();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onFailure$lambda$3(ActCourseOrGoodsDetail this$0, View view) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            String str = this$0.goodsId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str = null;
            }
            this$0.loadDetailData(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$0(ActCourseOrGoodsDetail this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            CommonLoadingPop commonLoadingPop = this$0.skuLoadingPop;
            if (commonLoadingPop != null) {
                commonLoadingPop.dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$1(ActCourseOrGoodsDetail this$0, View view) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            String str = this$0.goodsId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str = null;
            }
            this$0.loadDetailData(str);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CustomEmptyView customEmptyView = null;
            if (!ActCourseOrGoodsDetail.this.firstLoadSku) {
                LinearLayout linearLayout = ActCourseOrGoodsDetail.this.llContent;
                if (linearLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llContent");
                    linearLayout = null;
                }
                final ActCourseOrGoodsDetail actCourseOrGoodsDetail = ActCourseOrGoodsDetail.this;
                linearLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.w
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActCourseOrGoodsDetail.AnonymousClass2.onFailure$lambda$2(actCourseOrGoodsDetail);
                    }
                }, 200L);
            }
            if (!TextUtils.isEmpty(strMsg)) {
                NewToast.showShort(ActCourseOrGoodsDetail.this.mContext, strMsg);
            }
            CustomEmptyView customEmptyView2 = ActCourseOrGoodsDetail.this.emptyView;
            if (customEmptyView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView2 = null;
            }
            customEmptyView2.stopAnim();
            CustomEmptyView customEmptyView3 = ActCourseOrGoodsDetail.this.emptyView;
            if (customEmptyView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView3 = null;
            }
            ViewExtensionsKt.visible(customEmptyView3);
            CustomEmptyView customEmptyView4 = ActCourseOrGoodsDetail.this.emptyView;
            if (customEmptyView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView4 = null;
            }
            customEmptyView4.setLoadFileResUi(ActCourseOrGoodsDetail.this);
            CustomEmptyView customEmptyView5 = ActCourseOrGoodsDetail.this.emptyView;
            if (customEmptyView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView = customEmptyView5;
            }
            final ActCourseOrGoodsDetail actCourseOrGoodsDetail2 = ActCourseOrGoodsDetail.this;
            customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.x
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws PackageManager.NameNotFoundException {
                    ActCourseOrGoodsDetail.AnonymousClass2.onFailure$lambda$3(actCourseOrGoodsDetail2, view);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:67:0x01c4  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x01c7 A[Catch: Exception -> 0x0255, TryCatch #0 {Exception -> 0x0255, blocks: (B:17:0x006e, B:19:0x0097, B:21:0x009f, B:23:0x00a7, B:25:0x00b6, B:27:0x00be, B:28:0x00cc, B:30:0x00d4, B:32:0x00dc, B:33:0x00f1, B:35:0x00f9, B:36:0x00ff, B:38:0x0124, B:41:0x013b, B:43:0x0145, B:46:0x014d, B:48:0x0161, B:50:0x0170, B:56:0x017c, B:58:0x0190, B:75:0x0225, B:79:0x0237, B:78:0x022e, B:60:0x0198, B:61:0x01ac, B:63:0x01bb, B:69:0x01c7, B:73:0x0204, B:72:0x01ff), top: B:127:0x006e }] */
        @Override // net.tsz.afinal.http.AjaxCallBack
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onSuccess(@org.jetbrains.annotations.NotNull java.lang.String r8) {
            /*
                Method dump skipped, instructions count: 796
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.AnonymousClass2.onSuccess(java.lang.String):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v27, types: [T, java.lang.Object] */
    public final void calculateViewLocation() {
        ArrayList<BaseContentWidget> arrayList = this.contentViewList;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (!this.calcHeight || this.directoryExpandOrCollapse) {
            this.courseTopHeight = 0;
            this.initHeight = true;
            ArrayList arrayList2 = new ArrayList();
            int i2 = 0;
            for (Object obj : this.contentViewList) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                Object obj2 = (BaseContentWidget) obj;
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type android.view.View");
                int[] iArr = new int[2];
                ((View) obj2).getLocationOnScreen(iArr);
                arrayList2.add(iArr);
                i2 = i3;
            }
            if (this.detailType == 1) {
                int i4 = this.courseTopHeight + ((int[]) arrayList2.get(0))[1];
                this.courseTopHeight = i4;
                this.courseTopHeight = i4 + ((int[]) arrayList2.get(1))[1];
            }
            int i5 = this.detailType == 2 ? ((int[]) arrayList2.get(1))[1] : ((int[]) arrayList2.get(3))[1];
            int i6 = ((int[]) arrayList2.get(1))[1];
            if (this.contentHeightList.isEmpty()) {
                this.contentHeightList.add(Integer.valueOf(i5));
                this.contentHeightList.add(Integer.valueOf(i6));
                ArrayList<Integer> arrayList3 = this.contentHeightList;
                ArrayList<BaseContentWidget> arrayList4 = this.contentViewList;
                Object obj3 = arrayList4.get(arrayList4.size() - 1);
                Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type android.view.View");
                arrayList3.add(Integer.valueOf(((View) obj3).getHeight()));
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            ?? r3 = this.contentViewList.get(2);
            Intrinsics.checkNotNullExpressionValue(r3, "contentViewList[2]");
            objectRef.element = r3;
            if (this.detailType == 2) {
                ?? r32 = this.contentViewList.get(1);
                Intrinsics.checkNotNullExpressionValue(r32, "contentViewList[1]");
                objectRef.element = r32;
            }
            Rect rect = new Rect();
            T t2 = objectRef.element;
            if (t2 instanceof DetailCourseDirectoryWidget) {
                if (this.directoryExpandOrCollapse) {
                    ArrayList<BaseContentWidget> arrayList5 = this.contentViewList;
                    Object obj4 = arrayList5.get(arrayList5.size() - 2);
                    Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type android.view.View");
                    ((View) obj4).postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.k
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActCourseOrGoodsDetail.calculateViewLocation$lambda$9(this.f12572c);
                        }
                    }, 500L);
                } else if (this.contentHeightList.size() >= 2) {
                    ((DetailCourseDirectoryWidget) objectRef.element).getGlobalVisibleRect(rect);
                    this.contentHeightList.set(1, Integer.valueOf(rect.height()));
                    Iterator<T> it = this.contentHeightList.iterator();
                    int iIntValue = 0;
                    while (it.hasNext()) {
                        iIntValue += ((Number) it.next()).intValue();
                    }
                    LogUtils.d("total_height", String.valueOf(iIntValue));
                }
            } else if (t2 instanceof DetailCourseStructureWidget) {
                ((DetailCourseStructureWidget) t2).getGlobalVisibleRect(rect);
                ((DetailCourseStructureWidget) objectRef.element).postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.l
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActCourseOrGoodsDetail.calculateViewLocation$lambda$12(this.f12649c, objectRef);
                    }
                }, 300L);
            } else if (t2 instanceof DetailCourseContentWidget) {
                ((DetailCourseContentWidget) t2).getGlobalVisibleRect(rect);
                ((DetailCourseContentWidget) objectRef.element).postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.m
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActCourseOrGoodsDetail.calculateViewLocation$lambda$14(this.f12681c, objectRef);
                    }
                }, 500L);
            }
            if (this.directoryExpandOrCollapse && this.detailType == 2) {
                this.directoryExpandOrCollapse = false;
            }
            if (this.calcHeight) {
                return;
            }
            this.calcHeight = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void calculateViewLocation$lambda$12(ActCourseOrGoodsDetail this$0, Ref.ObjectRef view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        if (this$0.contentHeightList.size() >= 2) {
            this$0.contentHeightList.set(1, Integer.valueOf(((DetailCourseStructureWidget) view.element).getMeasuredHeight()));
            Iterator<T> it = this$0.contentHeightList.iterator();
            int iIntValue = 0;
            while (it.hasNext()) {
                iIntValue += ((Number) it.next()).intValue();
            }
            LogUtils.d("total_height", String.valueOf(iIntValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void calculateViewLocation$lambda$14(ActCourseOrGoodsDetail this$0, Ref.ObjectRef view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        if (this$0.contentHeightList.size() >= 2) {
            this$0.contentHeightList.set(1, Integer.valueOf(((DetailCourseContentWidget) view.element).getMeasuredHeight()));
            Iterator<T> it = this$0.contentHeightList.iterator();
            int iIntValue = 0;
            while (it.hasNext()) {
                iIntValue += ((Number) it.next()).intValue();
            }
            LogUtils.d("total_height", String.valueOf(iIntValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void calculateViewLocation$lambda$9(ActCourseOrGoodsDetail this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.contentHeightList.size() >= 2) {
            ArrayList<Integer> arrayList = this$0.contentHeightList;
            Object obj = this$0.contentViewList.get(this$0.detailType == 2 ? 1 : 3);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.View");
            arrayList.set(1, Integer.valueOf(((View) obj).getMeasuredHeight()));
            Iterator<T> it = this$0.contentHeightList.iterator();
            int iIntValue = 0;
            while (it.hasNext()) {
                iIntValue += ((Number) it.next()).intValue();
            }
            LogUtils.d("total_height", String.valueOf(iIntValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkShowCoupon() {
        int i2 = this.detailType;
        String str = i2 == 1 ? "ENTER_COURSE_DETAIL" : "ENTER_GOODS_DETAIL";
        String str2 = i2 == 1 ? "2" : "1";
        PopupShowManager popupShowManager = PopupShowManager.getInstance(this);
        String str3 = this.goodsId;
        if (str3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str3 = null;
        }
        popupShowManager.checkShowCoupon(this, str, "1", str2, str3);
    }

    private final void getCourse() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this);
        AjaxParams ajaxParams = new AjaxParams();
        String str = this.goodsId;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str = null;
        }
        ajaxParams.put("id", str);
        ajaxParams.put("identity_id", strConfig);
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.getFreeCourse, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.getCourse.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ActCourseOrGoodsDetail.this.hideProgressDialog();
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                ActCourseOrGoodsDetail.this.AlertToast(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ActCourseOrGoodsDetail.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                ActCourseOrGoodsDetail.this.hideProgressDialog();
                super.onSuccess((C05591) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            ActCourseOrGoodsDetail.this.AlertToast(strOptString);
                        }
                        ActCourseOrGoodsDetail.loadDetailData$default(ActCourseOrGoodsDetail.this, null, 1, null);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void gotoOrderConfirm(com.psychiatrygarden.bean.CourseDetailBean r13) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.gotoOrderConfirm(com.psychiatrygarden.bean.CourseDetailBean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(ActCourseOrGoodsDetail this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NestedScrollView nestedScrollView = this$0.sv;
        if (nestedScrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView = null;
        }
        nestedScrollView.smoothScrollTo(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initTopNavigator(java.lang.String r5) {
        /*
            r4 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int r1 = r4.detailType
            r2 = 1
            if (r1 != r2) goto L39
            java.lang.String r1 = "3"
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r1)
            if (r5 == 0) goto L29
            com.psychiatrygarden.bean.CourseDetailBean r5 = r4.courseDetailBean
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            java.lang.String r5 = r5.getShowMode()
            java.lang.String r1 = "1"
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r1)
            if (r5 != 0) goto L29
            java.lang.String r5 = "课程体系"
            r0.add(r5)
            goto L2e
        L29:
            java.lang.String r5 = "课程目录"
            r0.add(r5)
        L2e:
            java.lang.String r5 = "课程简介"
            r0.add(r5)
            java.lang.String r5 = "课程评价"
            r0.add(r5)
            goto L48
        L39:
            java.lang.String r5 = "商品"
            r0.add(r5)
            java.lang.String r5 = "详情"
            r0.add(r5)
            java.lang.String r5 = "评价"
            r0.add(r5)
        L48:
            cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator r5 = new cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator
            r5.<init>(r4)
            r4.commonNavigator = r5
            r5.setAdjustMode(r2)
            cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator r5 = r4.commonNavigator
            java.lang.String r1 = "commonNavigator"
            r2 = 0
            if (r5 != 0) goto L5d
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r5 = r2
        L5d:
            com.psychiatrygarden.activity.ActCourseOrGoodsDetail$initTopNavigator$1 r3 = new com.psychiatrygarden.activity.ActCourseOrGoodsDetail$initTopNavigator$1
            r3.<init>(r0, r4)
            r5.setAdapter(r3)
            cn.webdemo.com.supporfragment.tablayout.MagicIndicator r5 = r4.magicIndicator
            java.lang.String r0 = "magicIndicator"
            if (r5 != 0) goto L6f
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r5 = r2
        L6f:
            cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator r3 = r4.commonNavigator
            if (r3 != 0) goto L77
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r3 = r2
        L77:
            r5.setNavigator(r3)
            cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper r5 = r4.mFragmentContainerHelper
            java.lang.String r1 = "mFragmentContainerHelper"
            if (r5 != 0) goto L84
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r5 = r2
        L84:
            cn.webdemo.com.supporfragment.tablayout.MagicIndicator r3 = r4.magicIndicator
            if (r3 != 0) goto L8c
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            r3 = r2
        L8c:
            r5.attachMagicIndicator(r3)
            cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper r5 = r4.mFragmentContainerHelper
            if (r5 != 0) goto L97
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r5 = r2
        L97:
            android.view.animation.LinearInterpolator r0 = new android.view.animation.LinearInterpolator
            r0.<init>()
            r5.setInterpolator(r0)
            cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper r5 = r4.mFragmentContainerHelper
            if (r5 != 0) goto La7
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            goto La8
        La7:
            r2 = r5
        La8:
            r5 = 0
            r2.handlePageSelected(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.initTopNavigator(java.lang.String):void");
    }

    public static /* synthetic */ void loadDetailData$default(ActCourseOrGoodsDetail actCourseOrGoodsDetail, String str, int i2, Object obj) throws PackageManager.NameNotFoundException {
        if ((i2 & 1) != 0) {
            str = null;
        }
        actCourseOrGoodsDetail.loadDetailData(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onEventMainThread$lambda$52(ActCourseOrGoodsDetail this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.calcHeight = false;
        this$0.calculateViewLocation();
        NestedScrollView nestedScrollView = this$0.sv;
        CustomEmptyView customEmptyView = null;
        if (nestedScrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView = null;
        }
        nestedScrollView.setClickable(true);
        NestedScrollView nestedScrollView2 = this$0.sv;
        if (nestedScrollView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView2 = null;
        }
        nestedScrollView2.setFocusableInTouchMode(true);
        CustomEmptyView customEmptyView2 = this$0.emptyView;
        if (customEmptyView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView2 = null;
        }
        customEmptyView2.stopAnim();
        CustomEmptyView customEmptyView3 = this$0.emptyView;
        if (customEmptyView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
        } else {
            customEmptyView = customEmptyView3;
        }
        ViewExtensionsKt.gone(customEmptyView);
        View viewFindViewById = this$0.findViewById(R.id.iv_back_);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(R.id.iv_back_)");
        ViewExtensionsKt.visible(viewFindViewById);
    }

    private final void setOnScrollListener() {
        NestedScrollView nestedScrollView;
        LinearLayout linearLayout = this.llTopTitle;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
            linearLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        layoutParams2.height += statusBarHeight > 0 ? statusBarHeight : CommonUtil.dip2px(this, 25.0f);
        LinearLayout linearLayout2 = this.llTopTitle;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
            linearLayout2 = null;
        }
        linearLayout2.setPadding(0, statusBarHeight, 0, 0);
        LinearLayout linearLayout3 = this.llTopTitle;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
            linearLayout3 = null;
        }
        linearLayout3.setLayoutParams(layoutParams2);
        final int i2 = layoutParams2.height;
        final Rect rect = new Rect();
        final Ref.IntRef intRef = new Ref.IntRef();
        final Ref.FloatRef floatRef = new Ref.FloatRef();
        final float f2 = 1.0f;
        NestedScrollView nestedScrollView2 = this.sv;
        if (nestedScrollView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView2 = null;
        }
        nestedScrollView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.n
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return ActCourseOrGoodsDetail.setOnScrollListener$lambda$1(this.f13031c, view, motionEvent);
            }
        });
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        NestedScrollView nestedScrollView3 = this.sv;
        if (nestedScrollView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView3 = null;
        }
        View childAt = nestedScrollView3.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.view.ViewGroup");
        final ViewGroup viewGroup = (ViewGroup) childAt;
        final Ref.IntRef intRef2 = new Ref.IntRef();
        intRef2.element = -1;
        final Ref.LongRef longRef = new Ref.LongRef();
        final Rect rect2 = new Rect();
        final Rect rect3 = new Rect();
        NestedScrollView nestedScrollView4 = this.sv;
        if (nestedScrollView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView4 = null;
        }
        nestedScrollView4.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() { // from class: com.psychiatrygarden.activity.o
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                ActCourseOrGoodsDetail.setOnScrollListener$lambda$2(this.f13061a, longRef, rect2, rect3, viewGroup, intRef2);
            }
        });
        NestedScrollView nestedScrollView5 = this.sv;
        if (nestedScrollView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView = null;
        } else {
            nestedScrollView = nestedScrollView5;
        }
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.p
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i3, int i4, int i5, int i6) {
                ActCourseOrGoodsDetail.setOnScrollListener$lambda$3(this.f13519c, rect, intRef, booleanRef, i2, floatRef, f2, view, i3, i4, i5, i6);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setOnScrollListener$lambda$1(ActCourseOrGoodsDetail this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CustomEmptyView customEmptyView = this$0.emptyView;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        if (customEmptyView.getVisibility() == 0) {
            return true;
        }
        if (this$0.calcHeight && this$0.initHeight) {
            return false;
        }
        this$0.calculateViewLocation();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setOnScrollListener$lambda$2(ActCourseOrGoodsDetail this$0, Ref.LongRef lastEventTime, Rect scrollBounds, Rect viewBounds, ViewGroup llContainer, Ref.IntRef lastVisibleViewIndex) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(lastEventTime, "$lastEventTime");
        Intrinsics.checkNotNullParameter(scrollBounds, "$scrollBounds");
        Intrinsics.checkNotNullParameter(viewBounds, "$viewBounds");
        Intrinsics.checkNotNullParameter(llContainer, "$llContainer");
        Intrinsics.checkNotNullParameter(lastVisibleViewIndex, "$lastVisibleViewIndex");
        if (this$0.contentHeightList.size() < 3 || this$0.isClickScroll || this$0.detailType == 2) {
            return;
        }
        if (System.currentTimeMillis() - lastEventTime.element < 200) {
            lastEventTime.element = System.currentTimeMillis();
            return;
        }
        NestedScrollView nestedScrollView = this$0.sv;
        FragmentContainerHelper fragmentContainerHelper = null;
        if (nestedScrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            nestedScrollView = null;
        }
        nestedScrollView.getHitRect(scrollBounds);
        viewBounds.top = scrollBounds.top;
        int i2 = scrollBounds.bottom;
        LinearLayout linearLayout = this$0.llTopTitle;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llTopTitle");
            linearLayout = null;
        }
        viewBounds.bottom = i2 + linearLayout.getLayoutParams().height;
        viewBounds.left = scrollBounds.left;
        viewBounds.right = scrollBounds.right;
        LogUtils.d("viewTreeObserver", "top = " + scrollBounds.top + " bottom = " + viewBounds.bottom);
        int childCount = llContainer.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = llContainer.getChildAt(i3);
            Intrinsics.checkNotNullExpressionValue(childAt, "llContainer.getChildAt(i)");
            if (((childAt instanceof DetailCourseDirectoryWidget) || (childAt instanceof DetailCourseStructureWidget) || (childAt instanceof DetailCourseCommentWidget) || (childAt instanceof DetailCourseContentWidget)) && childAt.getLocalVisibleRect(viewBounds)) {
                if (i3 != lastVisibleViewIndex.element) {
                    lastVisibleViewIndex.element = i3;
                    if (childAt instanceof DetailCourseContentWidget) {
                        FragmentContainerHelper fragmentContainerHelper2 = this$0.mFragmentContainerHelper;
                        if (fragmentContainerHelper2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mFragmentContainerHelper");
                        } else {
                            fragmentContainerHelper = fragmentContainerHelper2;
                        }
                        fragmentContainerHelper.handlePageSelected(1, true);
                        return;
                    }
                    if (childAt instanceof DetailCourseDirectoryWidget ? true : childAt instanceof DetailCourseStructureWidget) {
                        FragmentContainerHelper fragmentContainerHelper3 = this$0.mFragmentContainerHelper;
                        if (fragmentContainerHelper3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mFragmentContainerHelper");
                        } else {
                            fragmentContainerHelper = fragmentContainerHelper3;
                        }
                        fragmentContainerHelper.handlePageSelected(0, true);
                        return;
                    }
                    if (childAt instanceof DetailCourseCommentWidget) {
                        FragmentContainerHelper fragmentContainerHelper4 = this$0.mFragmentContainerHelper;
                        if (fragmentContainerHelper4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mFragmentContainerHelper");
                        } else {
                            fragmentContainerHelper = fragmentContainerHelper4;
                        }
                        fragmentContainerHelper.handlePageSelected(2, true);
                        return;
                    }
                    return;
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c9  */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void setOnScrollListener$lambda$3(com.psychiatrygarden.activity.ActCourseOrGoodsDetail r17, android.graphics.Rect r18, kotlin.jvm.internal.Ref.IntRef r19, kotlin.jvm.internal.Ref.BooleanRef r20, int r21, kotlin.jvm.internal.Ref.FloatRef r22, float r23, android.view.View r24, int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instructions count: 650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.setOnScrollListener$lambda$3(com.psychiatrygarden.activity.ActCourseOrGoodsDetail, android.graphics.Rect, kotlin.jvm.internal.Ref$IntRef, kotlin.jvm.internal.Ref$BooleanRef, int, kotlin.jvm.internal.Ref$FloatRef, float, android.view.View, int, int, int, int):void");
    }

    private final void shareGetCourse(String activityId) {
        String str = NetworkRequestsURL.shareGetCourse;
        AjaxParams ajaxParams = new AjaxParams();
        String str2 = this.goodsId;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str2 = null;
        }
        ajaxParams.put("id", str2);
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, activityId);
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.shareGetCourse.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ActCourseOrGoodsDetail.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ActCourseOrGoodsDetail.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C05612) t2);
                ActCourseOrGoodsDetail.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        ActCourseOrGoodsDetail.loadDetailData$default(ActCourseOrGoodsDetail.this, null, 1, null);
                    } else {
                        ActCourseOrGoodsDetail.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:221:0x0327  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void showBottomBtn() {
        /*
            Method dump skipped, instructions count: 1483
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.showBottomBtn():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10, types: [com.psychiatrygarden.widget.CommonProblemWidget] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.psychiatrygarden.widget.DetailCourseTopInfoWidget] */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.psychiatrygarden.widget.DetailCourseTeacherInfoWidget] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.psychiatrygarden.widget.DetailCourseContentWidget] */
    /* JADX WARN: Type inference failed for: r4v8, types: [com.psychiatrygarden.widget.DetailCourseStructureWidget] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.psychiatrygarden.widget.DetailCourseCommentWidget] */
    public final void showCourseDetail(CourseDetailBean data) {
        DetailCourseDirectoryWidget detailCourseTopInfoWidget;
        ArrayList<GoodsDetailItem> arrayList = new ArrayList();
        LinearLayout linearLayout = null;
        if (data.getDataType() == 1) {
            String str = this.goodsId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str = null;
            }
            arrayList.add(new GoodsDetailItem(str, "课程简介", 1, data));
            ArrayList<CourseTeacher> teacher = data.getTeacher();
            if (!(teacher == null || teacher.isEmpty())) {
                String str2 = this.goodsId;
                if (str2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                    str2 = null;
                }
                arrayList.add(new GoodsDetailItem(str2, "讲师介绍", 2, data));
            }
            if (Intrinsics.areEqual("3", data.getType())) {
                this.isCourseCollection = true;
                CourseStructureParams courseStructureParams = new CourseStructureParams(getIntent().getStringExtra("type_course"), this.collectionTitle, data.getCategoryId());
                String str3 = this.goodsId;
                if (str3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                    str3 = null;
                }
                GoodsDetailItem goodsDetailItem = new GoodsDetailItem(str3, "课程体系", 5, data);
                goodsDetailItem.setExtraData(new Gson().toJson(courseStructureParams));
                arrayList.add(goodsDetailItem);
            } else {
                String str4 = this.goodsId;
                if (str4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                    str4 = null;
                }
                arrayList.add(new GoodsDetailItem(str4, "课程目录", 4, data));
            }
            String str5 = this.goodsId;
            if (str5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str5 = null;
            }
            arrayList.add(new GoodsDetailItem(str5, "课程详情", 3, data));
            String str6 = this.goodsId;
            if (str6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str6 = null;
            }
            arrayList.add(new GoodsDetailItem(str6, "评论列表", 6, data));
        } else {
            String str7 = this.goodsId;
            if (str7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str7 = null;
            }
            arrayList.add(new GoodsDetailItem(str7, "商品简介", 1, data));
            String str8 = this.goodsId;
            if (str8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str8 = null;
            }
            arrayList.add(new GoodsDetailItem(str8, "商品详情", 3, data));
            String str9 = this.goodsId;
            if (str9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str9 = null;
            }
            arrayList.add(new GoodsDetailItem(str9, "评价列表", 7, data));
            ArrayList<CommonProblem> commonProblem = data.getCommonProblem();
            if (!(commonProblem == null || commonProblem.isEmpty())) {
                String str10 = this.goodsId;
                if (str10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                    str10 = null;
                }
                arrayList.add(new GoodsDetailItem(str10, "常见问题", 8, data));
            }
        }
        LinearLayout linearLayout2 = this.llContent;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llContent");
            linearLayout2 = null;
        }
        linearLayout2.removeAllViews();
        this.contentViewList.clear();
        for (GoodsDetailItem goodsDetailItem2 : arrayList) {
            switch (goodsDetailItem2.getShowType()) {
                case 1:
                    detailCourseTopInfoWidget = new DetailCourseTopInfoWidget(this, null, 0);
                    break;
                case 2:
                    detailCourseTopInfoWidget = new DetailCourseTeacherInfoWidget(this, null, 0);
                    break;
                case 3:
                    detailCourseTopInfoWidget = new DetailCourseContentWidget(this, null, 0);
                    break;
                case 4:
                    detailCourseTopInfoWidget = new DetailCourseDirectoryWidget(this, null, 0);
                    break;
                case 5:
                    detailCourseTopInfoWidget = new DetailCourseStructureWidget(this, null, 0);
                    break;
                case 6:
                case 7:
                    detailCourseTopInfoWidget = new DetailCourseCommentWidget(this, null, 0);
                    break;
                case 8:
                    detailCourseTopInfoWidget = new CommonProblemWidget(this, null, 0);
                    break;
                default:
                    detailCourseTopInfoWidget = null;
                    break;
            }
            if (detailCourseTopInfoWidget != null) {
                this.contentViewList.add(detailCourseTopInfoWidget);
                LinearLayout linearLayout3 = this.llContent;
                LinearLayout linearLayout4 = linearLayout3;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llContent");
                    linearLayout4 = null;
                }
                linearLayout4.addView(detailCourseTopInfoWidget);
                detailCourseTopInfoWidget.initData(goodsDetailItem2);
                if (detailCourseTopInfoWidget instanceof DetailCourseDirectoryWidget) {
                    clickDirectoryItem(detailCourseTopInfoWidget);
                }
            }
        }
        if (data.getDataType() == 1) {
            ArrayList<BottomBtn> btnList = data.getBtnList();
            if (btnList == null || btnList.isEmpty()) {
                return;
            }
            showBottomBtn();
            return;
        }
        TextView textView = this.tvGoods;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvGoods");
            textView = null;
        }
        textView.setEnabled(Intrinsics.areEqual("0", data.getBtnGray()));
        LinearLayout linearLayout5 = this.llGoods;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llGoods");
            linearLayout5 = null;
        }
        linearLayout5.setEnabled(Intrinsics.areEqual("0", data.getBtnGray()));
        LinearLayout linearLayout6 = this.llGoods;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llGoods");
            linearLayout6 = null;
        }
        ViewExtensionsKt.visible(linearLayout6);
        if (!TextUtils.isEmpty(data.getBtnText())) {
            TextView textView2 = this.tvGoods;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvGoods");
                textView2 = null;
            }
            textView2.setText(data.getBtnText());
        } else if (Intrinsics.areEqual("1", data.isStopSale())) {
            TextView textView3 = this.tvGoods;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvGoods");
                textView3 = null;
            }
            textView3.setText("停售");
        }
        TextView textView4 = this.tvGoods;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvGoods");
            textView4 = null;
        }
        if (textView4.isEnabled()) {
            LinearLayout linearLayout7 = this.llGoods;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llGoods");
            } else {
                linearLayout = linearLayout7;
            }
            linearLayout.setOnClickListener(this);
        }
    }

    private final void unlock(int type) {
        Object next;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", String.valueOf(type));
        String str = this.goodsId;
        LinearLayout linearLayout = null;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str = null;
        }
        ajaxParams.put("id", str);
        CourseDetailBean courseDetailBean = this.courseDetailBean;
        if (courseDetailBean != null) {
            ArrayList<CourseGiftItem> gift = courseDetailBean.getGift();
            if (!(gift == null || gift.isEmpty())) {
                Iterator<T> it = courseDetailBean.getGift().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    } else {
                        next = it.next();
                        if (Intrinsics.areEqual(((CourseGiftItem) next).getGoods_type(), "1")) {
                            break;
                        }
                    }
                }
                if (((CourseGiftItem) next) != null) {
                    ajaxParams.put("giveWayType", "1");
                }
            }
        }
        showProgressDialog("");
        MemInterface.getInstance().setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.activity.q
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
            public final void shareSuccess(String str2) throws PackageManager.NameNotFoundException {
                ActCourseOrGoodsDetail.unlock$lambda$46(this.f13723a, str2);
            }
        });
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.r
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() throws PackageManager.NameNotFoundException {
                ActCourseOrGoodsDetail.unlock$lambda$47(this.f13752a);
            }
        });
        MemInterface.getInstance().courseGetMemData(this, ajaxParams, NetworkRequestsURL.courseUnlock, 0, false);
        LinearLayout linearLayout2 = this.llContent;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llContent");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.s
            @Override // java.lang.Runnable
            public final void run() {
                ActCourseOrGoodsDetail.unlock$lambda$48(this.f13811c);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unlock$lambda$46(ActCourseOrGoodsDetail this$0, String str) throws PackageManager.NameNotFoundException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.shareGetCourse(str);
        if (this$0.buyNotSaleAlone) {
            Activity currentLastActivity = ProjectApp.instance.getCurrentLastActivity();
            if (currentLastActivity instanceof ActCourseOrGoodsDetail) {
                ((ActCourseOrGoodsDetail) currentLastActivity).refresh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unlock$lambda$47(ActCourseOrGoodsDetail this$0) throws PackageManager.NameNotFoundException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        loadDetailData$default(this$0, null, 1, null);
        if (this$0.buyNotSaleAlone) {
            Activity currentLastActivity = ProjectApp.instance.getCurrentLastActivity();
            if (currentLastActivity instanceof ActCourseOrGoodsDetail) {
                ((ActCourseOrGoodsDetail) currentLastActivity).refresh();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unlock$lambda$48(ActCourseOrGoodsDetail this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTrySeeMargin(boolean isSelect) {
        CommonNavigator commonNavigator = this.commonNavigator;
        if (commonNavigator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commonNavigator");
            commonNavigator = null;
        }
        View viewFindViewById = commonNavigator.findViewById(R.id.title_container);
        ImageView imageView = (ImageView) findViewById(R.id.tv_try_see);
        if (viewFindViewById == null || !(viewFindViewById instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) viewFindViewById;
        if (viewGroup.getChildCount() <= 1 || imageView.getVisibility() != 0) {
            return;
        }
        View childAt = viewGroup.getChildAt(0);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        int width = (int) ((childAt.getWidth() / 2) * (isSelect ? 1.37f : 1.35f));
        if (layoutParams2.leftMargin != width) {
            LogUtils.e("marin_left", "margin_left=" + width);
            layoutParams2.leftMargin = width;
            imageView.setLayoutParams(layoutParams2);
        }
    }

    public static /* synthetic */ void updateTrySeeMargin$default(ActCourseOrGoodsDetail actCourseOrGoodsDetail, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        actCourseOrGoodsDetail.updateTrySeeMargin(z2);
    }

    private final void vipTakeCourse(int type) {
        String str = NetworkRequestsURL.vipGetCourse;
        AjaxParams ajaxParams = new AjaxParams();
        String str2 = this.goodsId;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str2 = null;
        }
        ajaxParams.put("id", str2);
        ajaxParams.put("type", String.valueOf(type));
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.vipTakeCourse.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((C05622) t2);
                try {
                    if (t2 == null) {
                        t2 = "";
                    }
                    if (Intrinsics.areEqual(new JSONObject(t2).optString("code"), "200")) {
                        ActCourseOrGoodsDetail.this.AlertToast("领取成功");
                        ActCourseOrGoodsDetail.loadDetailData$default(ActCourseOrGoodsDetail.this, null, 1, null);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public final void btnCLick(@NotNull View v2) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(v2, "v");
        onClick(v2);
    }

    public final void clickDirectoryItem(@NotNull DetailCourseDirectoryWidget w2) {
        Intrinsics.checkNotNullParameter(w2, "w");
        w2.setClickListenerCallBack(new Function5<String, String, String, String, CourseDirectoryContentItem, Unit>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail.clickDirectoryItem.1
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3, String str4, CourseDirectoryContentItem courseDirectoryContentItem) {
                invoke2(str, str2, str3, str4, courseDirectoryContentItem);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String rootChapterId, @NotNull String chapterId, @NotNull String rootChapterTitle, @NotNull String chapterTitle, @NotNull CourseDirectoryContentItem item) {
                Intrinsics.checkNotNullParameter(rootChapterId, "rootChapterId");
                Intrinsics.checkNotNullParameter(chapterId, "chapterId");
                Intrinsics.checkNotNullParameter(rootChapterTitle, "rootChapterTitle");
                Intrinsics.checkNotNullParameter(chapterTitle, "chapterTitle");
                Intrinsics.checkNotNullParameter(item, "item");
                if (CommonUtil.isFastClick()) {
                    return;
                }
                if (Intrinsics.areEqual("2", item.getType()) && !TextUtils.isEmpty(item.getStart_live_time())) {
                    String start_live_time = item.getStart_live_time();
                    Intrinsics.checkNotNullExpressionValue(start_live_time, "item.start_live_time");
                    if (new Regex(RegexPool.NUMBERS).matches(start_live_time)) {
                        String start_live_time2 = item.getStart_live_time();
                        Intrinsics.checkNotNullExpressionValue(start_live_time2, "item.start_live_time");
                        if (Long.parseLong(start_live_time2) > System.currentTimeMillis() / 1000) {
                            ToastUtil.shortToast(ActCourseOrGoodsDetail.this.mContext, "直播未开始");
                            return;
                        }
                    }
                }
                CourseDetailBean courseDetailBean = ActCourseOrGoodsDetail.this.detailBean;
                if (courseDetailBean == null) {
                    courseDetailBean = ActCourseOrGoodsDetail.this.courseDetailBean;
                    Intrinsics.checkNotNull(courseDetailBean);
                }
                if (TextUtils.equals("1", item.getType()) || (Intrinsics.areEqual("2", item.getType()) && !TextUtils.isEmpty(item.getVid()))) {
                    if (!courseDetailBean.hasPermission() && !Intrinsics.areEqual("1", item.getFree_watch())) {
                        ToastUtil.shortToast(ActCourseOrGoodsDetail.this.mContext, "暂无查看权限");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("obj_id", item.getObj_id());
                    bundle.putString("video_id", item.getVideo_id());
                    bundle.putString("seeDuration", item.getCurrent_duration());
                    bundle.putString("chapter_id", courseDetailBean.getId());
                    bundle.putString("course_id", courseDetailBean.getId());
                    bundle.putString("vid", item.getVid());
                    bundle.putString("type", item.getType());
                    bundle.putInt("module_type", 8);
                    bundle.putString("watch_permission", courseDetailBean.isPermission());
                    bundle.putString("isFreeWatch", "1");
                    bundle.putSerializable("commentEnum", DiscussStatus.CourseReview);
                    Intent intentPutExtra = new Intent(ActCourseOrGoodsDetail.this.mContext, (Class<?>) AliPlayerVideoPlayActivity.class).putExtra("rootChapterId", rootChapterId).putExtra("title", courseDetailBean.getTitle()).putExtra("pid", rootChapterId).putExtra("chapterId", chapterId).putExtra("rootChapterTitle", rootChapterTitle).putExtra("fromCourseDetail", true).putExtra("chapterTitle", chapterTitle).putExtras(bundle).putExtra("video_title", item.getTitle()).putExtra("hasChapter", !TextUtils.isEmpty(item.getChapter_id()));
                    Intrinsics.checkNotNullExpressionValue(intentPutExtra, "Intent(mContext, AliPlay…isEmpty(item.chapter_id))");
                    if (Intrinsics.areEqual("1", item.getFree_watch()) && !TextUtils.isEmpty(item.getDuration())) {
                        String duration = item.getDuration();
                        Intrinsics.checkNotNullExpressionValue(duration, "item.duration");
                        if (new Regex(RegexPool.NUMBERS).matches(duration)) {
                            String duration2 = item.getDuration();
                            Intrinsics.checkNotNullExpressionValue(duration2, "item.duration");
                            intentPutExtra.putExtra("free_watch_time", Long.parseLong(duration2));
                        }
                    }
                    ActCourseOrGoodsDetail.this.sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
                    ActCourseOrGoodsDetail.this.startActivity(intentPutExtra);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws PackageManager.NameNotFoundException {
        String stringExtra = getIntent().getStringExtra("goods_id");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.goodsId = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            String stringExtra2 = getIntent().getStringExtra("course_id");
            if (stringExtra2 == null) {
                stringExtra2 = "";
            }
            this.goodsId = stringExtra2;
        }
        String str = this.goodsId;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str = null;
        }
        this.courseId = str;
        String stringExtra3 = getIntent().getStringExtra("type");
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        this.type = stringExtra3;
        String stringExtra4 = getIntent().getStringExtra("collectionTitle");
        this.collectionTitle = stringExtra4 != null ? stringExtra4 : "";
        this.detailType = getIntent().getIntExtra("detailType", 1);
        this.buyNotSaleAlone = getIntent().getBooleanExtra("buyNotSaleAlone", false);
        View viewFindViewById = findViewById(R.id.magic_indicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.magic_indicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.iv_back_2_top);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.iv_back_2_top)");
        ImageView imageView = (ImageView) viewFindViewById2;
        this.ivBack2Top = imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivBack2Top");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActCourseOrGoodsDetail.init$lambda$0(this.f12540c, view);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_back_).setOnClickListener(this);
        View viewFindViewById3 = findViewById(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.empty_view)");
        this.emptyView = (CustomEmptyView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.ll_top_guide);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.ll_top_guide)");
        this.llTopTitle = (LinearLayout) viewFindViewById4;
        setOnScrollListener();
        String str2 = this.goodsId;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goodsId");
            str2 = null;
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        loadDetailData$default(this, null, 1, null);
    }

    public final void loadDetailData(@Nullable String courseId) throws PackageManager.NameNotFoundException {
        String str;
        String str2 = this.detailType == 1 ? "POST" : "GET";
        Context applicationContext = getApplicationContext();
        String str3 = this.detailType == 1 ? NetworkRequestsURL.courseDetail : NetworkRequestsURL.goodsDetailNewURL;
        AjaxParams ajaxParams = new AjaxParams();
        String str4 = this.detailType == 1 ? "id" : "goods_id";
        if (TextUtils.isEmpty(courseId)) {
            str = this.goodsId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str = null;
            }
        } else {
            str = courseId;
        }
        ajaxParams.put(str4, str);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.request(str2, applicationContext, str3, ajaxParams, new AnonymousClass2(courseId));
    }

    public final void loadSkuItemDetail(@NotNull String courseId) throws PackageManager.NameNotFoundException {
        CommonLoadingPop commonLoadingPop;
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        this.initPermission = false;
        this.goodsId = courseId;
        Boolean bool = this.loadingMap.get(courseId);
        if (bool == null) {
            this.loadingMap.put(courseId, Boolean.TRUE);
        }
        if (this.firstLoadSku) {
            this.firstLoadSku = false;
        } else {
            if (this.skuLoadingPop == null) {
                XPopup.Builder builder = new XPopup.Builder(this);
                Boolean bool2 = Boolean.FALSE;
                BasePopupView basePopupViewAsCustom = builder.dismissOnBackPressed(bool2).dismissOnTouchOutside(bool2).asCustom(new CommonLoadingPop(this));
                Intrinsics.checkNotNull(basePopupViewAsCustom, "null cannot be cast to non-null type com.psychiatrygarden.widget.CommonLoadingPop");
                this.skuLoadingPop = (CommonLoadingPop) basePopupViewAsCustom;
            }
            if (bool == null && (commonLoadingPop = this.skuLoadingPop) != null) {
                commonLoadingPop.toggle();
            }
        }
        this.initHeight = false;
        this.directoryExpandOrCollapse = false;
        loadDetailData(courseId);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) throws PackageManager.NameNotFoundException {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            String str = this.courseId;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("courseId");
                str = null;
            }
            loadDetailData(str);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View v2) throws NumberFormatException {
        CourseDetailBean courseDetailBean;
        List<ParentCourseBean> parentCourse;
        if (CommonUtil.isFastClick()) {
            return;
        }
        Integer numValueOf = v2 != null ? Integer.valueOf(v2.getId()) : null;
        boolean z2 = false;
        if (numValueOf != null && numValueOf.intValue() == R.id.ll_goods) {
            final CourseDetailBean courseDetailBean2 = this.courseDetailBean;
            if (courseDetailBean2 != null) {
                ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon = courseDetailBean2.getCoupon();
                if (coupon == null || coupon.isEmpty()) {
                    gotoOrderConfirm(courseDetailBean2);
                    return;
                }
                ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon2 = courseDetailBean2.getCoupon();
                ArrayList arrayList = new ArrayList();
                for (Object obj : coupon2) {
                    RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem = (RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj;
                    if (Intrinsics.areEqual(redEnvelopeCouponsDataItem.getIs_receive(), "0") && !Intrinsics.areEqual(redEnvelopeCouponsDataItem.getType(), "2")) {
                        arrayList.add(obj);
                    }
                }
                ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon3 = courseDetailBean2.getCoupon();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : coupon3) {
                    RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem redEnvelopeCouponsDataItem2 = (RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem) obj2;
                    if (Intrinsics.areEqual(redEnvelopeCouponsDataItem2.getIs_receive(), "0") && Intrinsics.areEqual(redEnvelopeCouponsDataItem2.getType(), "2")) {
                        arrayList2.add(obj2);
                    }
                }
                if (!(!arrayList.isEmpty()) && !(!arrayList2.isEmpty())) {
                    gotoOrderConfirm(courseDetailBean2);
                    return;
                }
                AjaxParams ajaxParams = new AjaxParams();
                if (!arrayList.isEmpty()) {
                    ajaxParams.put("coupon", CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, 0, null, new Function1<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, CharSequence>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$onClick$1$couponIds$1
                        @Override // kotlin.jvm.functions.Function1
                        @NotNull
                        public final CharSequence invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem c3) {
                            Intrinsics.checkNotNullParameter(c3, "c");
                            String id = c3.getId();
                            Intrinsics.checkNotNullExpressionValue(id, "c.id");
                            return id;
                        }
                    }, 30, null));
                }
                if (!arrayList2.isEmpty()) {
                    ajaxParams.put("red_envelope", CollectionsKt___CollectionsKt.joinToString$default(arrayList2, ",", null, null, 0, null, new Function1<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem, CharSequence>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$onClick$1$ids$1
                        @Override // kotlin.jvm.functions.Function1
                        @NotNull
                        public final CharSequence invoke(@NotNull RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem c3) {
                            Intrinsics.checkNotNullParameter(c3, "c");
                            String id = c3.getId();
                            Intrinsics.checkNotNullExpressionValue(id, "c.id");
                            return id;
                        }
                    }, 30, null));
                }
                YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCoupon, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseOrGoodsDetail$onClick$1$1
                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) throws NumberFormatException {
                        super.onFailure(t2, errorNo, strMsg);
                        this.this$0.gotoOrderConfirm(courseDetailBean2);
                    }

                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onSuccess(@NotNull String t2) throws NumberFormatException {
                        Intrinsics.checkNotNullParameter(t2, "t");
                        super.onSuccess((ActCourseOrGoodsDetail$onClick$1$1) t2);
                        this.this$0.gotoOrderConfirm(courseDetailBean2);
                    }
                });
                return;
            }
            return;
        }
        if ((((numValueOf != null && numValueOf.intValue() == R.id.ll_not_sale_alone) || (numValueOf != null && numValueOf.intValue() == R.id.ll_buy_now)) || (numValueOf != null && numValueOf.intValue() == R.id.ll_buy_vip_study)) || (numValueOf != null && numValueOf.intValue() == R.id.ll_join_activity_study)) {
            Object tag = v2.getTag();
            Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
            unlock(((Integer) tag).intValue());
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.ll_unlock_course) {
            CourseDetailBean courseDetailBean3 = this.courseDetailBean;
            if (courseDetailBean3 == null || (parentCourse = courseDetailBean3.getParentCourse()) == null) {
                return;
            }
            if (parentCourse.size() == 1) {
                startActivity(new Intent(this, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("course_id", parentCourse.get(0).getId()));
                return;
            } else {
                if (parentCourse.size() > 1) {
                    new XPopup.Builder(this).asCustom(new NotSaleAloneUnlockPop(this, parentCourse)).show();
                    return;
                }
                return;
            }
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.ll_take_now) {
            Object tag2 = v2.getTag();
            if (tag2 instanceof Integer) {
                if (Intrinsics.areEqual(tag2, (Object) 10) || Intrinsics.areEqual(tag2, (Object) 9)) {
                    vipTakeCourse(((Number) tag2).intValue());
                    return;
                } else {
                    getCourse();
                    return;
                }
            }
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.ll_start_study) {
            CourseDetailBean courseDetailBean4 = this.courseDetailBean;
            if (Intrinsics.areEqual(courseDetailBean4 != null ? courseDetailBean4.getType() : null, "3")) {
                CourseCombineDireListActivity.Companion companion = CourseCombineDireListActivity.INSTANCE;
                String str = this.goodsId;
                if (str == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                } else {
                    str = str;
                }
                companion.startActivity(this, str);
                return;
            }
            String str2 = this.goodsId;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goodsId");
                str2 = null;
            }
            CourseDetailBean courseDetailBean5 = this.courseDetailBean;
            CourseDirectoryActivity.goToCourseDirectoryActivity(this, str2, courseDetailBean5 != null ? courseDetailBean5.getType() : null);
            return;
        }
        if ((numValueOf != null && numValueOf.intValue() == R.id.iv_back) || (numValueOf != null && numValueOf.intValue() == R.id.iv_back_)) {
            z2 = true;
        }
        if (z2) {
            finish();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.ll_customer && isLogin() && (courseDetailBean = this.courseDetailBean) != null) {
            if (courseDetailBean.hasPermission()) {
                if (courseDetailBean.getAfterCs() != null) {
                    CommonUtil.onlineService(this, courseDetailBean.getAfterCs());
                }
            } else if (courseDetailBean.getBeforeCs() != null) {
                CommonUtil.onlineService(this, courseDetailBean.getBeforeCs());
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Subscribe
    public final void onEventMainThread(@NotNull ShareEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        shareGetCourse(e2.getActivityId());
    }

    public final void refresh() throws PackageManager.NameNotFoundException {
        String str = this.courseId;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("courseId");
            str = null;
        }
        loadDetailData(str);
    }

    public final void setChildCourseData(@NotNull CourseDetailBean d3) {
        Intrinsics.checkNotNullParameter(d3, "d");
        this.detailBean = d3;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentFillFromStatusBar(false);
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.act_course_goods_detail);
        this.mFragmentContainerHelper = new FragmentContainerHelper();
        View viewFindViewById = findViewById(R.id.ll_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.ll_content)");
        this.llContent = (LinearLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.sv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.sv)");
        this.sv = (NestedScrollView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.ll_buy_now);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ll_buy_now)");
        this.llBuyNow = (LinearLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.ll_sold_out);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.ll_sold_out)");
        this.llSoldOut = (LinearLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.ll_buy_vip_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.ll_buy_vip_study)");
        this.llBuyVipStudy = (LinearLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.ll_join_activity_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.ll_join_activity_study)");
        this.llJoinActivitiesStudy = (LinearLayout) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.ll_stop_sale);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.ll_stop_sale)");
        this.llStopSale = (LinearLayout) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.ll_not_sale_alone);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.ll_not_sale_alone)");
        this.llNotSaleAlone = (LinearLayout) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.ll_start_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.ll_start_study)");
        this.llStartStudy = (LinearLayout) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.ll_take_now);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.ll_take_now)");
        this.llTakeNow = (LinearLayout) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.tv_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.tv_title)");
        this.courseTitle = (TextView) viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.ll_unlock_course);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.ll_unlock_course)");
        this.llUnlockCourse = (LinearLayout) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.ll_goods);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.ll_goods)");
        this.llGoods = (LinearLayout) viewFindViewById13;
        View viewFindViewById14 = findViewById(R.id.tv_goods);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "findViewById(R.id.tv_goods)");
        this.tvGoods = (TextView) viewFindViewById14;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.ll_customer).setOnClickListener(this);
    }

    @Subscribe
    public final void onEventMainThread(@NotNull RefreshCourseDownloadedEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        for (BaseContentWidget baseContentWidget : this.contentViewList) {
            if (baseContentWidget instanceof DetailCourseDirectoryWidget) {
                List<String> vidList = e2.getVidList();
                Intrinsics.checkNotNullExpressionValue(vidList, "e.vidList");
                ((DetailCourseDirectoryWidget) baseContentWidget).updateDownloadedFlag(vidList);
            }
        }
    }

    @Subscribe
    public final void onEventMainThread(@NotNull RefreshVideoProgressEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        try {
            for (BaseContentWidget baseContentWidget : this.contentViewList) {
                if (baseContentWidget instanceof DetailCourseDirectoryWidget) {
                    String vid = event.getVid();
                    Intrinsics.checkNotNullExpressionValue(vid, "event.vid");
                    String progress = event.getProgress();
                    Intrinsics.checkNotNullExpressionValue(progress, "event.progress");
                    ((DetailCourseDirectoryWidget) baseContentWidget).updateVideoProgress(vid, progress);
                }
            }
        } catch (Exception e2) {
            Log.e("Error:", "onEventMainThread: " + e2.getMessage());
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) throws PackageManager.NameNotFoundException {
        NestedScrollView nestedScrollView = null;
        if (Intrinsics.areEqual("BuySuccess", str)) {
            loadDetailData$default(this, null, 1, null);
            if (this.buyNotSaleAlone) {
                Activity currentLastActivity = ProjectApp.instance.getCurrentLastActivity();
                if (currentLastActivity instanceof ActCourseOrGoodsDetail) {
                    ((ActCourseOrGoodsDetail) currentLastActivity).refresh();
                    return;
                }
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(EventBusConstant.DIRECTORY_FOLD_OR_EXPAND, str)) {
            this.directoryExpandOrCollapse = true;
            calculateViewLocation();
        } else if (Intrinsics.areEqual("CALCULATE", str)) {
            NestedScrollView nestedScrollView2 = this.sv;
            if (nestedScrollView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(com.alipay.sdk.sys.a.f3323h);
            } else {
                nestedScrollView = nestedScrollView2;
            }
            nestedScrollView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.i
                @Override // java.lang.Runnable
                public final void run() {
                    ActCourseOrGoodsDetail.onEventMainThread$lambda$52(this.f12489c);
                }
            }, 200L);
        }
    }

    @Subscribe
    public final void onEventMainThread(@NotNull HaveFreeWatchEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getHaveFreeWatch()) {
            View viewFindViewById = findViewById(R.id.tv_try_see);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(R.id.tv_try_see)");
            ViewExtensionsKt.visible(viewFindViewById);
            updateTrySeeMargin(false);
            return;
        }
        View viewFindViewById2 = findViewById(R.id.tv_try_see);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<View>(R.id.tv_try_see)");
        ViewExtensionsKt.gone(viewFindViewById2);
    }
}
