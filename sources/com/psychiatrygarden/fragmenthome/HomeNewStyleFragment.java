package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomMasterTable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.lang.RegexPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ContactCustomerServiceNewActivity;
import com.psychiatrygarden.activity.NewHomeSearchAct;
import com.psychiatrygarden.activity.StoreListAct;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.activity.mine.SignInActivity;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.activity.setting.AppTestInfoAct;
import com.psychiatrygarden.adapter.AllCourseLiveBannerAdapter;
import com.psychiatrygarden.adapter.BannerQuestionAdsAdapter;
import com.psychiatrygarden.adapter.CurriculumAdapterNew;
import com.psychiatrygarden.adapter.GoodsListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.HomeInfoItemBean;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.LiveBannerBean;
import com.psychiatrygarden.bean.LiveBannerListBean;
import com.psychiatrygarden.bean.NewHomeKingKongItem;
import com.psychiatrygarden.bean.NewHomeTabBean;
import com.psychiatrygarden.bean.NewHomeTodayStatisticsBean;
import com.psychiatrygarden.bean.ShopInfoBean;
import com.psychiatrygarden.bean.UserSignInfo;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.HomeNewStyleFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.ranking.RankingAct;
import com.psychiatrygarden.ranking.StatisticsMainActivity;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.HeaderScrollView;
import com.psychiatrygarden.widget.ViewPager2Indicator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000ë\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012*\u00012\u0018\u0000 \u0088\u00012\u00020\u0001:\u0002\u0088\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010T\u001a\u00020UH\u0002J(\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\n2\u0006\u0010Z\u001a\u00020W2\u0006\u0010[\u001a\u00020WH\u0002J\u0018\u0010\\\u001a\u00020U2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020\nH\u0002J\u0010\u0010`\u001a\u00020\u00122\u0006\u0010a\u001a\u00020\nH\u0002J\b\u0010b\u001a\u00020UH\u0002J\b\u0010c\u001a\u00020UH\u0002J\u001e\u0010c\u001a\b\u0012\u0004\u0012\u00020\n0d2\u0006\u0010]\u001a\u00020^2\u0006\u0010e\u001a\u00020\nH\u0002J\b\u0010f\u001a\u00020UH\u0002J\"\u0010g\u001a\u000e\u0012\u0004\u0012\u00020i\u0012\u0004\u0012\u0002080h2\f\u0010j\u001a\b\u0012\u0004\u0012\u00020i0dH\u0002J\b\u0010k\u001a\u00020\nH\u0014J\u0010\u0010l\u001a\u00020\n2\u0006\u0010j\u001a\u00020mH\u0002J\b\u0010n\u001a\u00020UH\u0002J\b\u0010o\u001a\u00020UH\u0002J\u0014\u0010p\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020806H\u0002J\b\u0010q\u001a\u00020UH\u0002J\u0012\u0010r\u001a\u00020U2\b\u0010j\u001a\u0004\u0018\u00010sH\u0002J\b\u0010t\u001a\u00020UH\u0002J\u001a\u0010u\u001a\u00020U2\u0006\u0010v\u001a\u00020w2\b\u0010x\u001a\u0004\u0018\u00010\"H\u0014J\u001e\u0010y\u001a\u00020U2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070d2\u0006\u0010z\u001a\u00020\u0012H\u0002J\b\u0010{\u001a\u00020UH\u0002J\b\u0010|\u001a\u00020UH\u0002J\b\u0010}\u001a\u00020UH\u0002J\b\u0010~\u001a\u00020UH\u0002J\b\u0010\u007f\u001a\u00020UH\u0002J\t\u0010\u0080\u0001\u001a\u00020UH\u0002J\t\u0010\u0081\u0001\u001a\u00020UH\u0002J\t\u0010\u0082\u0001\u001a\u00020UH\u0002J\u0014\u0010\u0083\u0001\u001a\u00020U2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010^H\u0016J\t\u0010\u0085\u0001\u001a\u00020UH\u0016J\t\u0010\u0086\u0001\u001a\u00020UH\u0002J\u0011\u0010\u0087\u0001\u001a\u00020U2\u0006\u0010a\u001a\u00020\nH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\fX\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u0006j\b\u0012\u0004\u0012\u00020\u000f`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\n0\u0006j\b\u0012\u0004\u0012\u00020\n`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010\u001a\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u001b0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\"0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010.\u001a\u00020\n2\u0006\u0010-\u001a\u00020\n@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b/\u00100R\u0010\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0004\n\u0002\u00103R\u000e\u00104\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u001a\u00105\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020806X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020>X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020>X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020>X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020CX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020FX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020HX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020RX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020RX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0089\u0001"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/HomeNewStyleFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "adSwitchRunnable", "Ljava/lang/Runnable;", "ads", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/HomepageSmallAdBean$DataDTO$DataAd$AdsDTO;", "Lkotlin/collections/ArrayList;", "apiCount", "", "banner", "Lcom/youth/banner/Banner;", "Lcom/psychiatrygarden/adapter/BannerQuestionAdsAdapter;", "bottomTabList", "Landroidx/fragment/app/Fragment;", "bottomTabTypes", "closeAd", "", "currentIndex", "expEnable", "guideItemPositionMap", "Landroid/util/ArrayMap;", "guideOverlay", "Landroid/view/ViewGroup;", "guideView", "guideViewMap", "Lkotlin/Pair;", "guideViewPositionMap", "imageSwitcher", "Landroid/widget/ImageSwitcher;", "isGuideLayout", "isRefresh", "ivClose", "Landroid/view/View;", "ivCloseBanner", "ivCollapse", "ivDelete", "ivNormal", "ivSign", "kingkongItemsViews", "llCourse", "llGoods", "llLive", "loadComplete", "value", "loadedApiCount", "setLoadedApiCount", "(I)V", "mAdapter", "com/psychiatrygarden/fragmenthome/HomeNewStyleFragment$mAdapter$1", "Lcom/psychiatrygarden/fragmenthome/HomeNewStyleFragment$mAdapter$1;", "mImgHidden", "mTabAdapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/NewHomeTabBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "refreshLayout", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "rlBuyBook", "rlQuestionBanner", "rvCourse", "Landroidx/recyclerview/widget/RecyclerView;", "rvGoods", "rvLive", "rvTab", "scrollView", "Lcom/psychiatrygarden/widget/HeaderScrollView;", "totalItemCount", "treeIndicator", "Lcom/psychiatrygarden/widget/ViewPager2Indicator;", "tvCorrection", "Landroid/widget/TextView;", "tvExamInfo", "tvGotoQuestionBank", "tvLearnTime", "tvMoreCourse", "tvMoreGoods", "tvQuestionCount", "tvRank", "tvUpdateTime", "viewPager", "Landroidx/viewpager2/widget/ViewPager2;", "vpKingKong", "addTab", "", "calculateGuideViewX", "", "targetPosition", "screenWidth", "density", "guideViewWidth", "checkAddGuideView", "type", "", "pagePosition", "checkCanShow", "guideType", "checkExpEnable", "checkShowGuide", "", "position", "getCircleList", "getKingKongAdapter", "Lcom/chad/library/adapter/base/BaseMultiItemQuickAdapter;", "Lcom/psychiatrygarden/bean/NewHomeKingKongItem;", "data", "getLayoutId", "getLiveStatus", "Lcom/psychiatrygarden/bean/LiveBannerBean;", "getSignInfo", "getSmallRedDot", "getTabAdapter", com.umeng.socialize.tracker.a.f23806c, "initSwitcher", "Lcom/psychiatrygarden/bean/HomepageSmallAdBean$DataDTO;", "initTab", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "loadAdSwitcher", "isForce", "loadBannerData", "loadCourseData", "loadGoodsData", "loadHandoutData", "loadInfoData", "loadKingKongArea", "loadLiveData", "loadTodayStatistics", "onEventMainThread", "str", "onSupportVisible", "setListener", "showGuide", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHomeNewStyleFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeNewStyleFragment.kt\ncom/psychiatrygarden/fragmenthome/HomeNewStyleFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1583:1\n1864#2,3:1584\n*S KotlinDebug\n*F\n+ 1 HomeNewStyleFragment.kt\ncom/psychiatrygarden/fragmenthome/HomeNewStyleFragment\n*L\n1424#1:1584,3\n*E\n"})
/* loaded from: classes5.dex */
public final class HomeNewStyleFragment extends BaseFragment {
    public static final int POSITION_CENTER = 2;
    public static final int POSITION_LEFT = 1;
    public static final int POSITION_RIGHT = 3;
    public static final int TYPE_MOCK_EXAM = 2;
    public static final int TYPE_QUESTION_SHEET = 1;
    public static final int TYPE_SELF_COMBINE_QUESTION = 3;

    @Nullable
    private Runnable adSwitchRunnable;
    private int apiCount;
    private Banner<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO, BannerQuestionAdsAdapter> banner;
    private boolean closeAd;
    private int currentIndex;
    private boolean expEnable;

    @Nullable
    private ViewGroup guideOverlay;

    @Nullable
    private ViewGroup guideView;
    private ImageSwitcher imageSwitcher;
    private boolean isGuideLayout;
    private boolean isRefresh;
    private View ivClose;
    private View ivCloseBanner;
    private View ivCollapse;
    private View ivDelete;
    private View ivNormal;
    private View ivSign;
    private View llCourse;
    private View llGoods;
    private View llLive;
    private boolean loadComplete;
    private int loadedApiCount;
    private View mImgHidden;
    private BaseQuickAdapter<NewHomeTabBean, BaseViewHolder> mTabAdapter;
    private SmartRefreshLayout refreshLayout;
    private View rlBuyBook;
    private View rlQuestionBanner;
    private RecyclerView rvCourse;
    private RecyclerView rvGoods;
    private RecyclerView rvLive;
    private RecyclerView rvTab;
    private HeaderScrollView scrollView;
    private int totalItemCount;
    private ViewPager2Indicator treeIndicator;
    private TextView tvCorrection;
    private TextView tvExamInfo;
    private View tvGotoQuestionBank;
    private TextView tvLearnTime;
    private View tvMoreCourse;
    private View tvMoreGoods;
    private TextView tvQuestionCount;
    private TextView tvRank;
    private TextView tvUpdateTime;
    private ViewPager2 viewPager;
    private ViewPager2 vpKingKong;

    @NotNull
    private final ArrayMap<Integer, View> kingkongItemsViews = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, Pair<Integer, Integer>> guideViewMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, Integer> guideViewPositionMap = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, Integer> guideItemPositionMap = new ArrayMap<>();

    @NotNull
    private final ArrayList<Fragment> bottomTabList = new ArrayList<>();

    @NotNull
    private final ArrayList<Integer> bottomTabTypes = new ArrayList<>();

    @NotNull
    private final ArrayList<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads = new ArrayList<>();

    @NotNull
    private final HomeNewStyleFragment$mAdapter$1 mAdapter = new BaseQuickAdapter<List<? extends NewHomeKingKongItem>, BaseViewHolder>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$mAdapter$1
        {
            super(R.layout.home_kk_tab, null, 2, null);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull List<? extends NewHomeKingKongItem> item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.rvKingKong);
            recyclerView.setLayoutManager(new GridLayoutManager(((BaseFragment) this.this$0).mContext, 5, 1, false));
            recyclerView.setAdapter(this.this$0.getKingKongAdapter(item));
        }
    };

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/fragmenthome/HomeNewStyleFragment$getKingKongAdapter$1", "Lcom/chad/library/adapter/base/BaseMultiItemQuickAdapter;", "Lcom/psychiatrygarden/bean/NewHomeKingKongItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nHomeNewStyleFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeNewStyleFragment.kt\ncom/psychiatrygarden/fragmenthome/HomeNewStyleFragment$getKingKongAdapter$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1583:1\n1#2:1584\n*E\n"})
    /* renamed from: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$getKingKongAdapter$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06041 extends BaseMultiItemQuickAdapter<NewHomeKingKongItem, BaseViewHolder> {
        final /* synthetic */ int $itemWidth;
        final /* synthetic */ HomeNewStyleFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06041(List<? extends NewHomeKingKongItem> list, int i2, HomeNewStyleFragment homeNewStyleFragment) {
            super(null, 1, null);
            this.$itemWidth = i2;
            this.this$0 = homeNewStyleFragment;
            addItemType(0, R.layout.item_home_king_kong_child);
            addItemType(1, R.layout.item_kingkong_area_normal);
            setList(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$1(NewHomeKingKongItem item, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (TextUtils.isEmpty(item.getPush_type())) {
                item.setPush_type(item.getJpush_type());
            }
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(item));
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull final NewHomeKingKongItem item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            ((ViewGroup.MarginLayoutParams) layoutParams2).width = this.$itemWidth;
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = holder.getLayoutPosition() > 4 ? SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 8) : 0;
            holder.itemView.setLayoutParams(layoutParams2);
            boolean z2 = !TextUtils.isEmpty(item.getIs_live_broadcast()) && Intrinsics.areEqual(item.getIs_live_broadcast(), "1");
            BaseViewHolder text = holder.setText(R.id.tv_label, item.getLabel()).setText(R.id.tv_title, item.getName());
            String label = item.getLabel();
            text.setGone(R.id.tv_label, (label == null || label.length() == 0) || z2);
            if (z2) {
                ImageView imageView = (ImageView) holder.getView(R.id.img_live);
                holder.getView(R.id.ly_live).setVisibility(0);
                imageView.setBackgroundResource(SkinManager.getCurrentSkinType(((BaseFragment) this.this$0).mContext) == 1 ? R.drawable.live_calendar_living_animation_night : R.drawable.live_calendar_living_animation);
                Drawable background = imageView.getBackground();
                Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
                ((AnimationDrawable) background).start();
            } else {
                View view = holder.getView(R.id.tv_label);
                holder.getView(R.id.tv_label).setSelected(SkinManager.getCurrentSkinType(((BaseFragment) this.this$0).mContext) == 1);
                int iDp2px = SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 5);
                int iDp2px2 = SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 11);
                if (item.getLabel() == null || item.getLabel().length() != 1) {
                    view.setPadding(iDp2px, view.getPaddingTop(), iDp2px, view.getPaddingBottom());
                } else {
                    view.setPadding(iDp2px2, view.getPaddingTop(), iDp2px2, view.getPaddingBottom());
                }
            }
            final ImageView imageView2 = (ImageView) holder.getView(R.id.iv_icon);
            ViewExtensionsKt.setCornerRadius(imageView2, 12);
            if (!TextUtils.isEmpty(item.getIcon())) {
                String icon = item.getIcon();
                Intrinsics.checkNotNullExpressionValue(icon, "item.icon");
                if (new Regex("(?i).*\\.gif$").matches(icon)) {
                    Glide.with(((BaseFragment) this.this$0).mContext).asGif().load(item.getIcon()).into((RequestBuilder<GifDrawable>) new CustomTarget<GifDrawable>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$getKingKongAdapter$1$convert$1
                        @Override // com.bumptech.glide.request.target.Target
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override // com.bumptech.glide.request.target.Target
                        public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                            onResourceReady((GifDrawable) obj, (Transition<? super GifDrawable>) transition);
                        }

                        public void onResourceReady(@NotNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                            Intrinsics.checkNotNullParameter(resource, "resource");
                            resource.setLoopCount(-1);
                            imageView2.setImageDrawable(resource);
                            resource.start();
                        }
                    });
                } else {
                    GlideUtils.loadImage(((BaseFragment) this.this$0).mContext, item.getIcon(), (ImageView) holder.getView(R.id.iv_icon));
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.b7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    HomeNewStyleFragment.C06041.convert$lambda$1(item, view2);
                }
            });
        }
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/fragmenthome/HomeNewStyleFragment$getTabAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/NewHomeTabBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nHomeNewStyleFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeNewStyleFragment.kt\ncom/psychiatrygarden/fragmenthome/HomeNewStyleFragment$getTabAdapter$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1583:1\n1864#2,3:1584\n*S KotlinDebug\n*F\n+ 1 HomeNewStyleFragment.kt\ncom/psychiatrygarden/fragmenthome/HomeNewStyleFragment$getTabAdapter$1\n*L\n425#1:1584,3\n*E\n"})
    /* renamed from: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$getTabAdapter$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06071 extends BaseQuickAdapter<NewHomeTabBean, BaseViewHolder> {
        final /* synthetic */ int $normal;
        final /* synthetic */ int $select;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06071(final HomeNewStyleFragment homeNewStyleFragment, int i2, int i3) {
            super(R.layout.item_new_home_style_tab, null, 2, null);
            this.$select = i2;
            this.$normal = i3;
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.c7
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i4) {
                    HomeNewStyleFragment.C06071._init_$lambda$1(homeNewStyleFragment, baseQuickAdapter, view, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$1(HomeNewStyleFragment this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            if (CommonUtil.isFastClick()) {
                return;
            }
            BaseQuickAdapter baseQuickAdapter2 = this$0.mTabAdapter;
            BaseQuickAdapter baseQuickAdapter3 = null;
            if (baseQuickAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
                baseQuickAdapter2 = null;
            }
            int i3 = 0;
            for (Object obj : baseQuickAdapter2.getData()) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                ((NewHomeTabBean) obj).setSelect(i3 == i2);
                i3 = i4;
            }
            ViewPager2 viewPager2 = this$0.viewPager;
            if (viewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager2 = null;
            }
            viewPager2.setCurrentItem(i2);
            BaseQuickAdapter baseQuickAdapter4 = this$0.mTabAdapter;
            if (baseQuickAdapter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
            } else {
                baseQuickAdapter3 = baseQuickAdapter4;
            }
            baseQuickAdapter3.notifyDataSetChanged();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull NewHomeTabBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            TextView textView = (TextView) holder.getView(R.id.tv_title);
            holder.setText(R.id.tv_title, item.getTitle()).setTextColor(R.id.tv_title, item.isSelect() ? this.$select : this.$normal).setVisible(R.id.iv_tag, item.isSelect());
            if (item.isSelect()) {
                textView.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                textView.setTypeface(Typeface.DEFAULT);
            }
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/fragmenthome/HomeNewStyleFragment$loadBannerData$1", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadBannerData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06091 extends AjaxCallBack<String> {
        public C06091() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$0(HomeNewStyleFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            View view2 = this$0.rlQuestionBanner;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rlQuestionBanner");
                view2 = null;
            }
            ViewExtensionsKt.gone(view2);
            SharePreferencesUtils.writeLongConfig("DISMESS_TIME_QUESTION_TOP_AD", Long.valueOf(System.currentTimeMillis()), ((BaseFragment) this$0).mContext);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$1(List list, HomeNewStyleFragment this$0, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            List list2 = list;
            if ((list2 == null || list2.isEmpty()) || !this$0.isLogin() || CommonUtil.isFastClick()) {
                return;
            }
            this$0.pointCount(((BaseFragment) this$0).mContext, "1");
            try {
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(adsDTO.getExtra()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
            homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            View view = HomeNewStyleFragment.this.rlQuestionBanner;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rlQuestionBanner");
                view = null;
            }
            ViewExtensionsKt.gone(view);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String t2) {
            HomepageSmallAdBean homepageSmallAdBean;
            int iLongValue;
            Intrinsics.checkNotNullParameter(t2, "t");
            super.onSuccess((C06091) t2);
            HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
            homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            View view = null;
            try {
                if (!Intrinsics.areEqual("200", new JSONObject(t2).optString("code")) || (homepageSmallAdBean = (HomepageSmallAdBean) new Gson().fromJson(t2, HomepageSmallAdBean.class)) == null || homepageSmallAdBean.getData() == null) {
                    return;
                }
                if (homepageSmallAdBean.getData().getBanner() == null) {
                    View view2 = HomeNewStyleFragment.this.rlQuestionBanner;
                    if (view2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rlQuestionBanner");
                        view2 = null;
                    }
                    ViewExtensionsKt.gone(view2);
                    return;
                }
                final List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads = homepageSmallAdBean.getData().getBanner().getAds();
                if (ads.isEmpty()) {
                    View view3 = HomeNewStyleFragment.this.rlQuestionBanner;
                    if (view3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rlQuestionBanner");
                        view3 = null;
                    }
                    ViewExtensionsKt.gone(view3);
                    return;
                }
                Long lastTime = SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", ((BaseFragment) HomeNewStyleFragment.this).mContext, 0L);
                if (lastTime != null && lastTime.longValue() == 0) {
                    iLongValue = 0;
                } else {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    Intrinsics.checkNotNullExpressionValue(lastTime, "lastTime");
                    iLongValue = (int) (((jCurrentTimeMillis - lastTime.longValue()) / 1000) - homepageSmallAdBean.getData().getBanner().getTime_interval());
                }
                boolean zAreEqual = Intrinsics.areEqual(homepageSmallAdBean.getData().getBanner().getForce(), "1");
                if (iLongValue >= 0) {
                    View view4 = HomeNewStyleFragment.this.rlQuestionBanner;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rlQuestionBanner");
                        view4 = null;
                    }
                    ViewExtensionsKt.visible(view4);
                    if (zAreEqual) {
                        View view5 = HomeNewStyleFragment.this.ivCloseBanner;
                        if (view5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ivCloseBanner");
                            view5 = null;
                        }
                        ViewExtensionsKt.gone(view5);
                    } else {
                        View view6 = HomeNewStyleFragment.this.ivCloseBanner;
                        if (view6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ivCloseBanner");
                            view6 = null;
                        }
                        ViewExtensionsKt.visible(view6);
                        View view7 = HomeNewStyleFragment.this.ivCloseBanner;
                        if (view7 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ivCloseBanner");
                            view7 = null;
                        }
                        final HomeNewStyleFragment homeNewStyleFragment2 = HomeNewStyleFragment.this;
                        view7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.d7
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view8) {
                                HomeNewStyleFragment.C06091.onSuccess$lambda$0(homeNewStyleFragment2, view8);
                            }
                        });
                    }
                    int pxByDp = ProjectApp.instance().getResources().getDisplayMetrics().widthPixels - ScreenUtil.getPxByDp(((BaseFragment) HomeNewStyleFragment.this).mContext, 40);
                    int i2 = pxByDp / 3;
                    if (AndroidBaseUtils.isPad(((BaseFragment) HomeNewStyleFragment.this).mContext) && AndroidBaseUtils.isCurOriLand(((BaseFragment) HomeNewStyleFragment.this).mContext)) {
                        i2 = pxByDp / 5;
                    }
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ProjectApp.instance().getResources().getDisplayMetrics().widthPixels, i2);
                    layoutParams.setMargins(0, ScreenUtil.getPxByDp(((BaseFragment) HomeNewStyleFragment.this).mContext, 10), 0, 0);
                    Banner banner = HomeNewStyleFragment.this.banner;
                    if (banner == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("banner");
                        banner = null;
                    }
                    banner.setLayoutParams(layoutParams);
                    Banner banner2 = HomeNewStyleFragment.this.banner;
                    if (banner2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("banner");
                        banner2 = null;
                    }
                    banner2.addBannerLifecycleObserver(HomeNewStyleFragment.this.getActivity()).setLoopTime(5000L).setAdapter(new BannerQuestionAdsAdapter(ads, false)).setPageTransformer(new AlphaPageTransformer()).setIndicator(new CircleIndicator(((BaseFragment) HomeNewStyleFragment.this).mContext)).setIndicatorRadius(ScreenUtil.getPxByDp(((BaseFragment) HomeNewStyleFragment.this).mContext, 4)).setIndicatorNormalColor(Color.parseColor("#88FFFFFF")).setIndicatorSelectedColor(ContextCompat.getColor(((BaseFragment) HomeNewStyleFragment.this).mContext, R.color.app_theme_red));
                    Banner banner3 = HomeNewStyleFragment.this.banner;
                    if (banner3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("banner");
                        banner3 = null;
                    }
                    final HomeNewStyleFragment homeNewStyleFragment3 = HomeNewStyleFragment.this;
                    banner3.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.fragmenthome.e7
                        @Override // com.youth.banner.listener.OnBannerListener
                        public final void OnBannerClick(Object obj, int i3) {
                            HomeNewStyleFragment.C06091.onSuccess$lambda$1(ads, homeNewStyleFragment3, (HomepageSmallAdBean.DataDTO.DataAd.AdsDTO) obj, i3);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                View view8 = HomeNewStyleFragment.this.rlQuestionBanner;
                if (view8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rlQuestionBanner");
                } else {
                    view = view8;
                }
                ViewExtensionsKt.gone(view);
            }
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"com/psychiatrygarden/fragmenthome/HomeNewStyleFragment$loadKingKongArea$1", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", AliyunVodHttpCommon.Format.FORMAT_JSON, "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nHomeNewStyleFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeNewStyleFragment.kt\ncom/psychiatrygarden/fragmenthome/HomeNewStyleFragment$loadKingKongArea$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1583:1\n1#2:1584\n*E\n"})
    /* renamed from: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadKingKongArea$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06141 extends AjaxCallBack<String> {
        public C06141() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$4(HomeNewStyleFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            HeaderScrollView headerScrollView = this$0.scrollView;
            if (headerScrollView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scrollView");
                headerScrollView = null;
            }
            headerScrollView.scrollTo(0, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$5(int i2) {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
            homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            ViewPager2Indicator viewPager2Indicator = HomeNewStyleFragment.this.treeIndicator;
            if (viewPager2Indicator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                viewPager2Indicator = null;
            }
            ViewExtensionsKt.gone(viewPager2Indicator);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
            homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            ViewPager2Indicator viewPager2Indicator = null;
            try {
                JSONObject jSONObject = new JSONObject(json);
                if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                    List dataList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends NewHomeKingKongItem>>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadKingKongArea$1$onSuccess$dataList$1
                    }.getType());
                    HomeNewStyleFragment.this.guideItemPositionMap.clear();
                    int size = dataList.size();
                    int i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        NewHomeKingKongItem newHomeKingKongItem = (NewHomeKingKongItem) dataList.get(i3);
                        String push_type = newHomeKingKongItem.getPush_type();
                        if (TextUtils.isEmpty(push_type)) {
                            push_type = newHomeKingKongItem.getJpush_type();
                        }
                        if (push_type != null) {
                            int iHashCode = push_type.hashCode();
                            if (iHashCode != 1607) {
                                if (iHashCode != 1637) {
                                    if (iHashCode != 1661) {
                                        if (iHashCode == 1662 && push_type.equals(RoomMasterTable.DEFAULT_ID)) {
                                            HomeNewStyleFragment.this.guideItemPositionMap.put(2, Integer.valueOf(i3));
                                        }
                                    } else if (push_type.equals("41")) {
                                        HomeNewStyleFragment.this.guideItemPositionMap.put(2, Integer.valueOf(i3));
                                    }
                                } else if (push_type.equals("38")) {
                                    HomeNewStyleFragment.this.guideItemPositionMap.put(3, Integer.valueOf(i3));
                                }
                            } else if (push_type.equals("29")) {
                                HomeNewStyleFragment.this.guideItemPositionMap.put(1, Integer.valueOf(i3));
                            }
                        }
                    }
                    if (dataList.isEmpty()) {
                        return;
                    }
                    if (dataList.size() <= 5) {
                        ViewPager2 viewPager2 = HomeNewStyleFragment.this.vpKingKong;
                        if (viewPager2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager2 = null;
                        }
                        HomeNewStyleFragment$mAdapter$1 homeNewStyleFragment$mAdapter$1 = HomeNewStyleFragment.this.mAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        homeNewStyleFragment$mAdapter$1.setList(CollectionsKt__CollectionsKt.arrayListOf(dataList));
                        viewPager2.setAdapter(homeNewStyleFragment$mAdapter$1);
                    } else if (dataList.size() > 10) {
                        ViewPager2 viewPager22 = HomeNewStyleFragment.this.vpKingKong;
                        if (viewPager22 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager22 = null;
                        }
                        HomeNewStyleFragment$mAdapter$1 homeNewStyleFragment$mAdapter$12 = HomeNewStyleFragment.this.mAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        homeNewStyleFragment$mAdapter$12.setList(CollectionsKt___CollectionsKt.chunked(dataList, 10));
                        viewPager22.setAdapter(homeNewStyleFragment$mAdapter$12);
                    } else if (dataList.size() < 7) {
                        ViewPager2 viewPager23 = HomeNewStyleFragment.this.vpKingKong;
                        if (viewPager23 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager23 = null;
                        }
                        HomeNewStyleFragment$mAdapter$1 homeNewStyleFragment$mAdapter$13 = HomeNewStyleFragment.this.mAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        homeNewStyleFragment$mAdapter$13.setList(CollectionsKt___CollectionsKt.chunked(dataList, 5));
                        viewPager23.setAdapter(homeNewStyleFragment$mAdapter$13);
                    } else {
                        ViewPager2 viewPager24 = HomeNewStyleFragment.this.vpKingKong;
                        if (viewPager24 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager24 = null;
                        }
                        HomeNewStyleFragment$mAdapter$1 homeNewStyleFragment$mAdapter$14 = HomeNewStyleFragment.this.mAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        homeNewStyleFragment$mAdapter$14.setList(CollectionsKt__CollectionsKt.arrayListOf(dataList));
                        viewPager24.setAdapter(homeNewStyleFragment$mAdapter$14);
                    }
                    HomeNewStyleFragment.this.guideViewMap.clear();
                    HomeNewStyleFragment.this.guideViewPositionMap.clear();
                    HomeNewStyleFragment.this.totalItemCount = dataList.size();
                    final HomeNewStyleFragment homeNewStyleFragment2 = HomeNewStyleFragment.this;
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.f7
                        @Override // java.lang.Runnable
                        public final void run() {
                            HomeNewStyleFragment.C06141.onSuccess$lambda$4(homeNewStyleFragment2);
                        }
                    }, 500L);
                    ViewPager2Indicator viewPager2Indicator2 = HomeNewStyleFragment.this.treeIndicator;
                    if (viewPager2Indicator2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                        viewPager2Indicator2 = null;
                    }
                    if (dataList.size() != 6 && dataList.size() <= 10) {
                        i2 = 8;
                    }
                    viewPager2Indicator2.setVisibility(i2);
                    ViewPager2Indicator viewPager2Indicator3 = HomeNewStyleFragment.this.treeIndicator;
                    if (viewPager2Indicator3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                        viewPager2Indicator3 = null;
                    }
                    ViewPager2 viewPager25 = HomeNewStyleFragment.this.vpKingKong;
                    if (viewPager25 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                        viewPager25 = null;
                    }
                    viewPager2Indicator3.bindViewPager2(viewPager25, new ViewPager2Indicator.PageSelectListener() { // from class: com.psychiatrygarden.fragmenthome.g7
                        @Override // com.psychiatrygarden.widget.ViewPager2Indicator.PageSelectListener
                        public final void onPageSelect(int i4) {
                            HomeNewStyleFragment.C06141.onSuccess$lambda$5(i4);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                ViewPager2Indicator viewPager2Indicator4 = HomeNewStyleFragment.this.treeIndicator;
                if (viewPager2Indicator4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                } else {
                    viewPager2Indicator = viewPager2Indicator4;
                }
                ViewExtensionsKt.gone(viewPager2Indicator);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addTab() {
        if (!this.bottomTabTypes.isEmpty()) {
            initTab();
            if (!this.expEnable && this.bottomTabTypes.contains(2)) {
                this.bottomTabTypes.remove((Object) 2);
            }
            ArrayList arrayList = new ArrayList();
            this.bottomTabList.clear();
            int i2 = 0;
            for (Object obj : this.bottomTabTypes) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                int iIntValue = ((Number) obj).intValue();
                this.bottomTabList.add(NewHomeBottomListFragment.INSTANCE.newInstance(iIntValue));
                if (iIntValue == 1) {
                    arrayList.add(new NewHomeTabBean("推荐热帖", i2 == 0));
                } else if (iIntValue == 2) {
                    arrayList.add(new NewHomeTabBean("过考经验", i2 == 0));
                } else if (iIntValue == 3) {
                    arrayList.add(new NewHomeTabBean("推荐资讯", i2 == 0));
                }
                i2 = i3;
            }
            BaseQuickAdapter<NewHomeTabBean, BaseViewHolder> baseQuickAdapter = this.mTabAdapter;
            ViewPager2 viewPager2 = null;
            if (baseQuickAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
                baseQuickAdapter = null;
            }
            baseQuickAdapter.setList(arrayList);
            ViewPager2 viewPager22 = this.viewPager;
            if (viewPager22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager22 = null;
            }
            viewPager22.setOffscreenPageLimit(arrayList.size());
            ViewPager2 viewPager23 = this.viewPager;
            if (viewPager23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager23 = null;
            }
            viewPager23.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), requireActivity().getLifecycle()) { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.addTab.2
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter
                @NotNull
                public Fragment createFragment(int position) {
                    Object obj2 = HomeNewStyleFragment.this.bottomTabList.get(position);
                    Intrinsics.checkNotNullExpressionValue(obj2, "bottomTabList[position]");
                    return (Fragment) obj2;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return HomeNewStyleFragment.this.bottomTabList.size();
                }
            });
            ViewPager2 viewPager24 = this.viewPager;
            if (viewPager24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            } else {
                viewPager2 = viewPager24;
            }
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.addTab.3
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageSelected(int position) {
                    BaseQuickAdapter baseQuickAdapter2 = HomeNewStyleFragment.this.mTabAdapter;
                    BaseQuickAdapter baseQuickAdapter3 = null;
                    if (baseQuickAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
                        baseQuickAdapter2 = null;
                    }
                    int i4 = 0;
                    for (Object obj2 : baseQuickAdapter2.getData()) {
                        int i5 = i4 + 1;
                        if (i4 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        ((NewHomeTabBean) obj2).setSelect(i4 == position);
                        i4 = i5;
                    }
                    BaseQuickAdapter baseQuickAdapter4 = HomeNewStyleFragment.this.mTabAdapter;
                    if (baseQuickAdapter4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
                    } else {
                        baseQuickAdapter3 = baseQuickAdapter4;
                    }
                    baseQuickAdapter3.notifyDataSetChanged();
                }
            });
        }
    }

    private final float calculateGuideViewX(int targetPosition, int screenWidth, float density, float guideViewWidth) {
        float f2 = 10 * density;
        float f3 = screenWidth;
        float f4 = (targetPosition + 0.5f) * (f3 / 5.0f);
        if (targetPosition > 1) {
            f2 = targetPosition == 2 ? guideViewWidth / 2 : guideViewWidth - f2;
        }
        float fDp2px = (f4 - f2) - (targetPosition == 2 ? 0 : SizeUtil.dp2px(this.mContext, 7));
        if (targetPosition > 2) {
            fDp2px += SizeUtil.dp2px(this.mContext, 15);
        }
        return Math.max(0.0f, Math.min(fDp2px, f3 - guideViewWidth));
    }

    private final void checkAddGuideView(String type, int pagePosition) {
        List<Integer> listCheckShowGuide = checkShowGuide(type, pagePosition);
        int i2 = ProjectApp.instance().getResources().getDisplayMetrics().widthPixels;
        Iterator<Integer> it = listCheckShowGuide.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            int i3 = iIntValue % 5;
            int i4 = (i3 == 0 || i3 == 1) ? 1 : (i3 == 3 || i3 == 4) ? 3 : 2;
            View view = this.kingkongItemsViews.get(Integer.valueOf(iIntValue));
            if (view != null) {
                int i5 = Intrinsics.areEqual("29", type) ? 1 : (Intrinsics.areEqual("41", type) || Intrinsics.areEqual(RoomMasterTable.DEFAULT_ID, type)) ? 2 : 3;
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                int iCalculateGuideViewX = (int) calculateGuideViewX(i3, i2, ProjectApp.instance().getResources().getDisplayMetrics().density, SizeUtil.dp2px(ProjectApp.instance(), i5 != 3 ? 200 : 220) * 1.0f);
                this.guideViewPositionMap.put(Integer.valueOf(i5), Integer.valueOf(i4));
                this.guideViewMap.put(Integer.valueOf(i5), new Pair<>(Integer.valueOf(iCalculateGuideViewX), Integer.valueOf(iArr[1] - SizeUtil.dp2px(ProjectApp.instance(), 18))));
            }
        }
    }

    private final boolean checkCanShow(int guideType) {
        Pair<Integer, Integer> pair = this.guideViewMap.get(Integer.valueOf(guideType));
        Integer num = this.guideItemPositionMap.get(Integer.valueOf(guideType));
        ViewPager2 viewPager2 = this.vpKingKong;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
            viewPager2 = null;
        }
        int currentItem = viewPager2.getCurrentItem();
        if (pair == null || num == null) {
            return false;
        }
        if (this.totalItemCount == 6 && currentItem == 1 && num.intValue() == 5) {
            return false;
        }
        if (this.totalItemCount > 10) {
            if (num.intValue() >= (currentItem + 1) * 10) {
                ViewGroup viewGroup = this.guideOverlay;
                if (viewGroup != null) {
                    ViewExtensionsKt.gone(viewGroup);
                }
                return false;
            }
            if ((num.intValue() + 1) / 10 != currentItem) {
                ViewGroup viewGroup2 = this.guideOverlay;
                if (viewGroup2 != null) {
                    ViewExtensionsKt.gone(viewGroup2);
                }
                return false;
            }
        }
        return true;
    }

    private final void checkExpEnable() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.enableExpModule, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.checkExpEnable.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment.this.getCircleList();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                JSONObject jSONObjectOptJSONObject;
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null) {
                        HomeNewStyleFragment.this.expEnable = Intrinsics.areEqual(jSONObjectOptJSONObject.optString("exp_enable"), "1");
                    }
                    HomeNewStyleFragment.this.getCircleList();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    HomeNewStyleFragment.this.getCircleList();
                }
            }
        });
    }

    private final void checkShowGuide() {
        if (this.guideViewMap.get(1) != null) {
            showGuide(1);
        } else if (this.guideViewMap.get(2) != null) {
            showGuide(2);
        } else if (this.guideViewMap.get(3) != null) {
            showGuide(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getCircleList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "-1");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("limit", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.articleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.getCircleList.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment.this.loadHandoutData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        HomeNewStyleFragment.this.loadHandoutData();
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CirclrListBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$getCircleList$1$onSuccess$circleList$1
                    }.getType());
                    if (!(list == null || list.isEmpty()) && !HomeNewStyleFragment.this.bottomTabTypes.contains(1)) {
                        HomeNewStyleFragment.this.bottomTabTypes.add(1);
                    }
                    HomeNewStyleFragment.this.loadHandoutData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BaseMultiItemQuickAdapter<NewHomeKingKongItem, BaseViewHolder> getKingKongAdapter(List<? extends NewHomeKingKongItem> data) {
        return new C06041(data, getResources().getDisplayMetrics().widthPixels / 5, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getLiveStatus(com.psychiatrygarden.bean.LiveBannerBean r11) {
        /*
            r10 = this;
            java.lang.String r0 = r11.getEnd_live_time()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r1 = 1
            if (r0 != 0) goto L5b
            java.lang.String r0 = r11.getStart_live_time()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L16
            goto L5b
        L16:
            java.lang.String r0 = r11.getVideo_id()
            java.lang.String r2 = r11.getStart_live_time()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            long r2 = java.lang.Long.parseLong(r2)
            r4 = 1000(0x3e8, float:1.401E-42)
            long r4 = (long) r4
            long r2 = r2 * r4
            java.lang.String r11 = r11.getEnd_live_time()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11)
            long r6 = java.lang.Long.parseLong(r11)
            long r6 = r6 * r4
            long r4 = java.lang.System.currentTimeMillis()
            r8 = 1
            long r8 = r8 + r2
            int r11 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            int r11 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r11 >= 0) goto L43
            goto L44
        L43:
            r1 = 0
        L44:
            int r11 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r11 <= 0) goto L5b
            boolean r11 = android.text.TextUtils.isEmpty(r0)
            if (r11 != 0) goto L59
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            int r11 = java.lang.Integer.parseInt(r0)
            if (r11 <= 0) goto L59
            r11 = 2
            goto L5a
        L59:
            r11 = 3
        L5a:
            r1 = r11
        L5b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.getLiveStatus(com.psychiatrygarden.bean.LiveBannerBean):int");
    }

    private final void getSignInfo() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mineGetIconInfo, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.getSignInfo.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String s2) {
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                try {
                    UserSignInfo userSignInfo = (UserSignInfo) new Gson().fromJson(s2, UserSignInfo.class);
                    View view = null;
                    if (!Intrinsics.areEqual(userSignInfo.getCode(), "200")) {
                        View view2 = HomeNewStyleFragment.this.ivSign;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ivSign");
                        } else {
                            view = view2;
                        }
                        view.setVisibility(8);
                        return;
                    }
                    if (Intrinsics.areEqual(userSignInfo.getData().getSign_is_open(), "0")) {
                        View view3 = HomeNewStyleFragment.this.ivSign;
                        if (view3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ivSign");
                        } else {
                            view = view3;
                        }
                        view.setVisibility(8);
                        return;
                    }
                    View view4 = HomeNewStyleFragment.this.ivSign;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ivSign");
                    } else {
                        view = view4;
                    }
                    view.setVisibility(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void getSmallRedDot() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.homePageSmallAdApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.getSmallRedDot.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        HomepageSmallAdBean.DataDTO dataDTO = (HomepageSmallAdBean.DataDTO) new Gson().fromJson(jSONObject.optString("data"), HomepageSmallAdBean.DataDTO.class);
                        HomeNewStyleFragment.this.adSwitchRunnable = null;
                        HomeNewStyleFragment.this.initSwitcher(dataDTO);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final BaseQuickAdapter<NewHomeTabBean, BaseViewHolder> getTabAdapter() {
        TypedArray typedArrayObtainStyledAttributes = requireContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_color, R.attr.third_txt_color});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "requireContext().theme.o… R.attr.third_txt_color))");
        int color = typedArrayObtainStyledAttributes.getColor(1, this.mContext.getColor(R.color.third_txt_color));
        int color2 = typedArrayObtainStyledAttributes.getColor(0, this.mContext.getColor(R.color.main_theme_color));
        typedArrayObtainStyledAttributes.recycle();
        return new C06071(this, color2, color);
    }

    private final void initData() {
        getSignInfo();
        loadBannerData();
        loadKingKongArea();
        loadTodayStatistics();
        loadLiveData();
        View view = null;
        if (Intrinsics.areEqual("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, this.mContext))) {
            View view2 = this.llGoods;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llGoods");
                view2 = null;
            }
            ViewExtensionsKt.gone(view2);
        } else {
            loadGoodsData();
        }
        if (Intrinsics.areEqual("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, this.mContext))) {
            View view3 = this.llCourse;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llCourse");
            } else {
                view = view3;
            }
            ViewExtensionsKt.gone(view);
        } else {
            loadCourseData();
        }
        checkExpEnable();
        getSmallRedDot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r12v2, types: [android.view.View] */
    public final void initSwitcher(HomepageSmallAdBean.DataDTO data) {
        long jLongValue;
        ImageSwitcher imageSwitcher = null;
        if (data == null) {
            ?? r12 = this.rlBuyBook;
            if (r12 == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
            } else {
                imageSwitcher = r12;
            }
            imageSwitcher.setVisibility(8);
            return;
        }
        List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads = data.getLower_right_corner_ad().getAds();
        if (ads.size() <= 0) {
            ?? r122 = this.rlBuyBook;
            if (r122 == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
            } else {
                imageSwitcher = r122;
            }
            imageSwitcher.setVisibility(8);
            return;
        }
        this.ads.clear();
        this.ads.addAll(ads);
        Long longConfig = SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME, this.mContext, 0L);
        if (longConfig != null && longConfig.longValue() == 0) {
            jLongValue = 0;
        } else {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Long time2 = SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME, this.mContext, 0L);
            Intrinsics.checkNotNullExpressionValue(time2, "time2");
            jLongValue = ((jCurrentTimeMillis - time2.longValue()) / 1000) - data.getLower_right_corner_ad().getTime_interval();
        }
        if (jLongValue < 0) {
            View view = this.rlBuyBook;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
                view = null;
            }
            view.setVisibility(8);
        }
        ImageSwitcher imageSwitcher2 = this.imageSwitcher;
        if (imageSwitcher2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
            imageSwitcher2 = null;
        }
        if (imageSwitcher2.getChildCount() > 0) {
            ImageSwitcher imageSwitcher3 = this.imageSwitcher;
            if (imageSwitcher3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
                imageSwitcher3 = null;
            }
            imageSwitcher3.removeAllViews();
            this.currentIndex = 0;
        }
        if (ads.size() > 1) {
            ImageSwitcher imageSwitcher4 = this.imageSwitcher;
            if (imageSwitcher4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
                imageSwitcher4 = null;
            }
            imageSwitcher4.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_right_in));
            ImageSwitcher imageSwitcher5 = this.imageSwitcher;
            if (imageSwitcher5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
                imageSwitcher5 = null;
            }
            imageSwitcher5.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
        }
        ImageSwitcher imageSwitcher6 = this.imageSwitcher;
        if (imageSwitcher6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
        } else {
            imageSwitcher = imageSwitcher6;
        }
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() { // from class: com.psychiatrygarden.fragmenthome.q6
            @Override // android.widget.ViewSwitcher.ViewFactory
            public final View makeView() {
                return HomeNewStyleFragment.initSwitcher$lambda$23(this.f15936a);
            }
        });
        Intrinsics.checkNotNullExpressionValue(ads, "ads");
        loadAdSwitcher(ads, Intrinsics.areEqual(data.getLower_right_corner_ad().getForce(), "1"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final View initSwitcher$lambda$23(HomeNewStyleFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ImageView imageView = new ImageView(this$0.getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return imageView;
    }

    private final void initTab() {
        this.mTabAdapter = getTabAdapter();
        RecyclerView recyclerView = this.rvTab;
        BaseQuickAdapter<NewHomeTabBean, BaseViewHolder> baseQuickAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvTab");
            recyclerView = null;
        }
        BaseQuickAdapter<NewHomeTabBean, BaseViewHolder> baseQuickAdapter2 = this.mTabAdapter;
        if (baseQuickAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTabAdapter");
        } else {
            baseQuickAdapter = baseQuickAdapter2;
        }
        recyclerView.setAdapter(baseQuickAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initViews$lambda$0(View v2, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(v2, "v");
        return v2.getVisibility() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$1(HomeNewStyleFragment this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.bottomTabTypes.clear();
        this$0.bottomTabList.clear();
        this$0.isRefresh = true;
        this$0.setLoadedApiCount(0);
        this$0.loadComplete = false;
        this$0.initData();
    }

    private final void loadAdSwitcher(final List<? extends HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads, final boolean isForce) {
        if (this.adSwitchRunnable == null) {
            this.adSwitchRunnable = new Runnable() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadAdSwitcher.1
                @Override // java.lang.Runnable
                public void run() {
                    HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                    int i2 = 0;
                    if (ads.size() != 1 && HomeNewStyleFragment.this.currentIndex != ads.size() - 1) {
                        HomeNewStyleFragment homeNewStyleFragment2 = HomeNewStyleFragment.this;
                        homeNewStyleFragment2.currentIndex++;
                        i2 = homeNewStyleFragment2.currentIndex;
                    }
                    homeNewStyleFragment.currentIndex = i2;
                    if (HomeNewStyleFragment.this.getActivity() != null && HomeNewStyleFragment.this.currentIndex >= 0 && HomeNewStyleFragment.this.currentIndex < ads.size()) {
                        int pxByDp = ScreenUtil.getPxByDp(HomeNewStyleFragment.this.getContext(), 100);
                        int pxByDp2 = ScreenUtil.getPxByDp(HomeNewStyleFragment.this.getContext(), 100);
                        FragmentActivity activity = HomeNewStyleFragment.this.getActivity();
                        Intrinsics.checkNotNull(activity);
                        RequestBuilder requestBuilderPlaceholder = Glide.with(activity).load((Object) GlideUtils.generateUrl(ads.get(HomeNewStyleFragment.this.currentIndex).getImg())).override(pxByDp, pxByDp2).placeholder(R.drawable.app_icon);
                        final HomeNewStyleFragment homeNewStyleFragment3 = HomeNewStyleFragment.this;
                        final boolean z2 = isForce;
                        requestBuilderPlaceholder.into((RequestBuilder) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadAdSwitcher$1$run$1
                            @Override // com.bumptech.glide.request.target.Target
                            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                                onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
                            }

                            public void onResourceReady(@NotNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                Intrinsics.checkNotNullParameter(resource, "resource");
                                ImageSwitcher imageSwitcher = homeNewStyleFragment3.imageSwitcher;
                                View view = null;
                                if (imageSwitcher == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
                                    imageSwitcher = null;
                                }
                                imageSwitcher.setImageDrawable(resource);
                                View view2 = homeNewStyleFragment3.rlBuyBook;
                                if (view2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
                                    view2 = null;
                                }
                                view2.setVisibility(0);
                                if (z2) {
                                    View view3 = homeNewStyleFragment3.ivDelete;
                                    if (view3 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("ivDelete");
                                    } else {
                                        view = view3;
                                    }
                                    view.setVisibility(8);
                                    return;
                                }
                                View view4 = homeNewStyleFragment3.ivDelete;
                                if (view4 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("ivDelete");
                                } else {
                                    view = view4;
                                }
                                view.setVisibility(0);
                            }
                        });
                    }
                    if (ads.size() > 1) {
                        ImageSwitcher imageSwitcher = HomeNewStyleFragment.this.imageSwitcher;
                        if (imageSwitcher == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
                            imageSwitcher = null;
                        }
                        imageSwitcher.postDelayed(this, 5000L);
                    }
                }
            };
            ImageSwitcher imageSwitcher = this.imageSwitcher;
            if (imageSwitcher == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
                imageSwitcher = null;
            }
            imageSwitcher.postDelayed(this.adSwitchRunnable, 1000L);
        }
    }

    private final void loadBannerData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.homePageSmallAdApi, ajaxParams, new C06091());
    }

    private final void loadCourseData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        ajaxParams.put("category_id", "0");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseMainList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadCourseData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                View view = HomeNewStyleFragment.this.llCourse;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llCourse");
                    view = null;
                }
                ViewExtensionsKt.gone(view);
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                View view = null;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        View view2 = HomeNewStyleFragment.this.llCourse;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llCourse");
                            view2 = null;
                        }
                        ViewExtensionsKt.gone(view2);
                        return;
                    }
                    List courseList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CurriculumItemBean.DataDTO>>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadCourseData$1$onSuccess$courseList$1
                    }.getType());
                    List list = courseList;
                    if (list == null || list.isEmpty()) {
                        View view3 = HomeNewStyleFragment.this.llCourse;
                        if (view3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llCourse");
                            view3 = null;
                        }
                        ViewExtensionsKt.gone(view3);
                        return;
                    }
                    View view4 = HomeNewStyleFragment.this.llCourse;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llCourse");
                        view4 = null;
                    }
                    ViewExtensionsKt.visible(view4);
                    RecyclerView recyclerView = HomeNewStyleFragment.this.rvCourse;
                    if (recyclerView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvCourse");
                        recyclerView = null;
                    }
                    CurriculumAdapterNew curriculumAdapterNew = new CurriculumAdapterNew(HomeNewStyleFragment.this.getActivity(), true);
                    if (courseList.size() <= 3) {
                        Intrinsics.checkNotNullExpressionValue(courseList, "courseList");
                    } else {
                        courseList = courseList.subList(0, 3);
                    }
                    curriculumAdapterNew.setList(courseList);
                    recyclerView.setAdapter(curriculumAdapterNew);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    View view5 = HomeNewStyleFragment.this.llCourse;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llCourse");
                    } else {
                        view = view5;
                    }
                    ViewExtensionsKt.gone(view);
                }
            }
        });
    }

    private final void loadGoodsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cat_id", "0");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("page_size", "20");
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.goodsList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadGoodsData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                View view = HomeNewStyleFragment.this.llGoods;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llGoods");
                    view = null;
                }
                ViewExtensionsKt.gone(view);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                super.onSuccess((C06111) json);
                View view = null;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        View view2 = HomeNewStyleFragment.this.llGoods;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llGoods");
                            view2 = null;
                        }
                        ViewExtensionsKt.gone(view2);
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends ShopInfoBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadGoodsData$1$onSuccess$goodsList$1
                    }.getType());
                    List list2 = list;
                    if (list2 == null || list2.isEmpty()) {
                        View view3 = HomeNewStyleFragment.this.llGoods;
                        if (view3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llGoods");
                            view3 = null;
                        }
                        ViewExtensionsKt.gone(view3);
                        return;
                    }
                    View view4 = HomeNewStyleFragment.this.llGoods;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llGoods");
                        view4 = null;
                    }
                    ViewExtensionsKt.visible(view4);
                    if (list.size() <= 3) {
                        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(((BaseFragment) HomeNewStyleFragment.this).mContext, list, true);
                        RecyclerView recyclerView = HomeNewStyleFragment.this.rvGoods;
                        if (recyclerView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvGoods");
                            recyclerView = null;
                        }
                        recyclerView.setAdapter(goodsListAdapter);
                    } else {
                        RecyclerView recyclerView2 = HomeNewStyleFragment.this.rvGoods;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvGoods");
                            recyclerView2 = null;
                        }
                        recyclerView2.setAdapter(new GoodsListAdapter(((BaseFragment) HomeNewStyleFragment.this).mContext, list.subList(0, 3), true));
                    }
                    RecyclerView recyclerView3 = HomeNewStyleFragment.this.rvGoods;
                    if (recyclerView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvGoods");
                        recyclerView3 = null;
                    }
                    RecyclerView.Adapter adapter = recyclerView3.getAdapter();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    View view5 = HomeNewStyleFragment.this.llGoods;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llGoods");
                    } else {
                        view = view5;
                    }
                    ViewExtensionsKt.gone(view);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadHandoutData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("search_type", "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("limit", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put("code", "exp");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "");
        ajaxParams.put("search_app_id", "");
        ajaxParams.put("ranking", "");
        ajaxParams.put("time_range", "");
        ajaxParams.put("time_sort", "");
        ajaxParams.put("score_type", "");
        ajaxParams.put("ctime", "desc");
        ajaxParams.put("test_time", "");
        ajaxParams.put("u_cid", "");
        ajaxParams.put("p_cid", "");
        ajaxParams.put("m_cid", "");
        ajaxParams.put("file_type", "");
        ajaxParams.put("category_id", "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.searchArticleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadHandoutData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment.this.loadInfoData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        HomeNewStyleFragment.this.loadInfoData();
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CirclrListBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadHandoutData$1$onSuccess$dataList$1
                    }.getType());
                    if (!(list == null || list.isEmpty()) && !HomeNewStyleFragment.this.bottomTabTypes.contains(2)) {
                        HomeNewStyleFragment.this.bottomTabTypes.add(2);
                    }
                    HomeNewStyleFragment.this.loadInfoData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadInfoData() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.newHomeInfo;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadInfoData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment.this.addTab();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        HomeNewStyleFragment.this.addTab();
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    List list = (List) new Gson().fromJson(jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("list") : null, new TypeToken<List<? extends HomeInfoItemBean>>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment$loadInfoData$2$onSuccess$dataList$1
                    }.getType());
                    if (!(list == null || list.isEmpty()) && !HomeNewStyleFragment.this.bottomTabTypes.contains(3)) {
                        HomeNewStyleFragment.this.bottomTabTypes.add(3);
                    }
                    HomeNewStyleFragment.this.addTab();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    HomeNewStyleFragment.this.addTab();
                }
            }
        });
    }

    private final void loadKingKongArea() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.newHomeKingKongArea, new AjaxParams(), new C06141());
    }

    private final void loadLiveData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext()));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.courseBannerLiving, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadLiveData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                View view = HomeNewStyleFragment.this.llLive;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("llLive");
                    view = null;
                }
                ViewExtensionsKt.gone(view);
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                ArrayList arrayList;
                Intrinsics.checkNotNullParameter(json, "json");
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                super.onSuccess((C06151) json);
                View view = null;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        View view2 = HomeNewStyleFragment.this.llLive;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llLive");
                            view2 = null;
                        }
                        ViewExtensionsKt.gone(view2);
                        return;
                    }
                    Object objFromJson = new Gson().fromJson(jSONObject.optString("data"), (Class<Object>) LiveBannerListBean.class);
                    Intrinsics.checkNotNullExpressionValue(objFromJson, "Gson().fromJson(obj.optS…nnerListBean::class.java)");
                    List<LiveBannerBean> list = ((LiveBannerListBean) objFromJson).getList();
                    if (list != null) {
                        HomeNewStyleFragment homeNewStyleFragment2 = HomeNewStyleFragment.this;
                        arrayList = new ArrayList();
                        for (Object obj : list) {
                            if (homeNewStyleFragment2.getLiveStatus((LiveBannerBean) obj) == 0) {
                                arrayList.add(obj);
                            }
                        }
                    } else {
                        arrayList = null;
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList2 == null || arrayList2.isEmpty()) {
                        View view3 = HomeNewStyleFragment.this.llLive;
                        if (view3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llLive");
                            view3 = null;
                        }
                        ViewExtensionsKt.gone(view3);
                    } else {
                        View view4 = HomeNewStyleFragment.this.llLive;
                        if (view4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llLive");
                            view4 = null;
                        }
                        ViewExtensionsKt.visible(view4);
                    }
                    ArrayList arrayList3 = arrayList;
                    if (arrayList3 == null || arrayList3.isEmpty()) {
                        return;
                    }
                    AllCourseLiveBannerAdapter allCourseLiveBannerAdapter = new AllCourseLiveBannerAdapter();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((LiveBannerBean) it.next()).setItemType(arrayList.size() == 1 ? 1 : 0);
                    }
                    allCourseLiveBannerAdapter.setList(arrayList);
                    allCourseLiveBannerAdapter.setNewHome(true);
                    RecyclerView recyclerView = HomeNewStyleFragment.this.rvLive;
                    if (recyclerView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvLive");
                        recyclerView = null;
                    }
                    recyclerView.setAdapter(allCourseLiveBannerAdapter);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    View view5 = HomeNewStyleFragment.this.llLive;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llLive");
                    } else {
                        view = view5;
                    }
                    ViewExtensionsKt.gone(view);
                }
            }
        });
    }

    private final void loadTodayStatistics() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.newHomeTodayStatistics, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HomeNewStyleFragment.loadTodayStatistics.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) throws NumberFormatException {
                TextView textView;
                TextView textView2;
                TextView textView3;
                Intrinsics.checkNotNullParameter(t2, "t");
                HomeNewStyleFragment homeNewStyleFragment = HomeNewStyleFragment.this;
                homeNewStyleFragment.setLoadedApiCount(homeNewStyleFragment.loadedApiCount + 1);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        NewHomeTodayStatisticsBean newHomeTodayStatisticsBean = (NewHomeTodayStatisticsBean) new Gson().fromJson(jSONObject.optString("data"), NewHomeTodayStatisticsBean.class);
                        Regex regex = new Regex(RegexPool.NUMBERS);
                        TextView textView4 = HomeNewStyleFragment.this.tvUpdateTime;
                        if (textView4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvUpdateTime");
                            textView4 = null;
                        }
                        textView4.setText("更新时间 " + newHomeTodayStatisticsBean.getRefresh_time());
                        String exam_count_down = newHomeTodayStatisticsBean.getExam_count_down();
                        Intrinsics.checkNotNullExpressionValue(exam_count_down, "bean.exam_count_down");
                        if (regex.matches(exam_count_down)) {
                            View view = HomeNewStyleFragment.this.getViewHolder().get(R.id.ll_exam_info);
                            Intrinsics.checkNotNullExpressionValue(view, "viewHolder.get<View>(R.id.ll_exam_info)");
                            ViewExtensionsKt.visible(view);
                            String exam_count_down2 = newHomeTodayStatisticsBean.getExam_count_down();
                            Intrinsics.checkNotNullExpressionValue(exam_count_down2, "bean.exam_count_down");
                            int i2 = Integer.parseInt(exam_count_down2);
                            TypedArray typedArrayObtainStyledAttributes = ((BaseFragment) HomeNewStyleFragment.this).mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_color, R.attr.first_txt_color});
                            Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "mContext.theme.obtainSty… R.attr.first_txt_color))");
                            int color = typedArrayObtainStyledAttributes.getColor(1, ((BaseFragment) HomeNewStyleFragment.this).mContext.getColor(R.color.first_txt_color));
                            int color2 = typedArrayObtainStyledAttributes.getColor(0, ((BaseFragment) HomeNewStyleFragment.this).mContext.getColor(R.color.main_theme_color));
                            typedArrayObtainStyledAttributes.recycle();
                            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("距离考试还有 ");
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 0, spannableStringBuilder.length(), 18);
                            spannableStringBuilder.append((CharSequence) String.valueOf(i2)).setSpan(new ForegroundColorSpan(color2), spannableStringBuilder.length() - String.valueOf(i2).length(), spannableStringBuilder.length(), 17);
                            spannableStringBuilder.append((CharSequence) " 天").setSpan(new ForegroundColorSpan(color), spannableStringBuilder.length() - 2, spannableStringBuilder.length(), 18);
                            TextView textView5 = HomeNewStyleFragment.this.tvExamInfo;
                            if (textView5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvExamInfo");
                                textView5 = null;
                            }
                            textView5.setText(spannableStringBuilder);
                        } else {
                            View view2 = HomeNewStyleFragment.this.getViewHolder().get(R.id.ll_exam_info);
                            Intrinsics.checkNotNullExpressionValue(view2, "viewHolder.get<View>(R.id.ll_exam_info)");
                            ViewExtensionsKt.gone(view2);
                        }
                        View view3 = HomeNewStyleFragment.this.getViewHolder().get(R.id.ll_info);
                        Intrinsics.checkNotNullExpressionValue(view3, "viewHolder.get<View>(R.id.ll_info)");
                        ViewExtensionsKt.visible(view3);
                        String question_all_num = newHomeTodayStatisticsBean.getQuestion_all_num();
                        Intrinsics.checkNotNullExpressionValue(question_all_num, "bean.question_all_num");
                        if (regex.matches(question_all_num)) {
                            String question_all_num2 = newHomeTodayStatisticsBean.getQuestion_all_num();
                            Intrinsics.checkNotNullExpressionValue(question_all_num2, "bean.question_all_num");
                            if (Integer.parseInt(question_all_num2) == 0) {
                                View view4 = HomeNewStyleFragment.this.getViewHolder().get(R.id.ll_info);
                                Intrinsics.checkNotNullExpressionValue(view4, "viewHolder.get<View>(R.id.ll_info)");
                                ViewExtensionsKt.gone(view4);
                                View view5 = HomeNewStyleFragment.this.getViewHolder().get(R.id.ll_no_do);
                                Intrinsics.checkNotNullExpressionValue(view5, "viewHolder.get<View>(R.id.ll_no_do)");
                                ViewExtensionsKt.visible(view5);
                                return;
                            }
                            View view6 = HomeNewStyleFragment.this.getViewHolder().get(R.id.ll_no_do);
                            Intrinsics.checkNotNullExpressionValue(view6, "viewHolder.get<View>(R.id.ll_no_do)");
                            ViewExtensionsKt.gone(view6);
                            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(newHomeTodayStatisticsBean.getQuestion_all_num());
                            spannableStringBuilder2.setSpan(new AbsoluteSizeSpan(14, true), 0, spannableStringBuilder2.length(), 18);
                            spannableStringBuilder2.append((CharSequence) "题").setSpan(new AbsoluteSizeSpan(10, true), spannableStringBuilder2.length() - 1, spannableStringBuilder2.length(), 18);
                            TextView textView6 = HomeNewStyleFragment.this.tvQuestionCount;
                            if (textView6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvQuestionCount");
                                textView6 = null;
                            }
                            textView6.setText(spannableStringBuilder2);
                        }
                        String duration_days = newHomeTodayStatisticsBean.getDuration_days();
                        Intrinsics.checkNotNullExpressionValue(duration_days, "bean.duration_days");
                        if (regex.matches(duration_days)) {
                            String duration_days2 = newHomeTodayStatisticsBean.getDuration_days();
                            Intrinsics.checkNotNullExpressionValue(duration_days2, "bean.duration_days");
                            int i3 = Integer.parseInt(duration_days2);
                            int i4 = i3 / 60;
                            int i5 = i3 % 60;
                            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
                            if (i4 > 0) {
                                spannableStringBuilder3.append((CharSequence) String.valueOf(i4)).setSpan(new AbsoluteSizeSpan(14, true), 0, spannableStringBuilder3.length(), 18);
                                spannableStringBuilder3.append((CharSequence) "h").setSpan(new AbsoluteSizeSpan(10, true), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
                                spannableStringBuilder3.append((CharSequence) String.valueOf(i5)).setSpan(new AbsoluteSizeSpan(14, true), spannableStringBuilder3.length() - String.valueOf(i5).length(), spannableStringBuilder3.length(), 18);
                                spannableStringBuilder3.append((CharSequence) "m").setSpan(new AbsoluteSizeSpan(10, true), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
                                TextView textView7 = HomeNewStyleFragment.this.tvLearnTime;
                                if (textView7 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("tvLearnTime");
                                    textView7 = null;
                                }
                                textView7.setText(spannableStringBuilder3);
                            } else {
                                spannableStringBuilder3.append((CharSequence) String.valueOf(i5)).setSpan(new AbsoluteSizeSpan(14, true), 0, spannableStringBuilder3.length(), 18);
                                spannableStringBuilder3.append((CharSequence) "m").setSpan(new AbsoluteSizeSpan(10, true), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
                                TextView textView8 = HomeNewStyleFragment.this.tvLearnTime;
                                if (textView8 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("tvLearnTime");
                                    textView8 = null;
                                }
                                textView8.setText(spannableStringBuilder3);
                            }
                        }
                        SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(newHomeTodayStatisticsBean.getRight_count());
                        spannableStringBuilder4.setSpan(new AbsoluteSizeSpan(14, true), 0, spannableStringBuilder4.length(), 18);
                        spannableStringBuilder4.append((CharSequence) "%").setSpan(new AbsoluteSizeSpan(10, true), spannableStringBuilder4.length() - 1, spannableStringBuilder4.length(), 18);
                        TextView textView9 = HomeNewStyleFragment.this.tvCorrection;
                        if (textView9 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvCorrection");
                            textView9 = null;
                        }
                        textView9.setText(spannableStringBuilder4);
                        String days_rank = newHomeTodayStatisticsBean.getDays_rank();
                        Intrinsics.checkNotNullExpressionValue(days_rank, "bean.days_rank");
                        if (!regex.matches(days_rank)) {
                            TextView textView10 = HomeNewStyleFragment.this.tvRank;
                            if (textView10 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvRank");
                                textView = null;
                            } else {
                                textView = textView10;
                            }
                            ViewExtensionsKt.gone(textView);
                            return;
                        }
                        TextView textView11 = HomeNewStyleFragment.this.tvRank;
                        if (textView11 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvRank");
                            textView11 = null;
                        }
                        ViewExtensionsKt.visible(textView11);
                        String days_rank2 = newHomeTodayStatisticsBean.getDays_rank();
                        Intrinsics.checkNotNullExpressionValue(days_rank2, "bean.days_rank");
                        int i6 = Integer.parseInt(days_rank2);
                        if (i6 == 0) {
                            TextView textView12 = HomeNewStyleFragment.this.tvRank;
                            if (textView12 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvRank");
                                textView12 = null;
                            }
                            textView12.setText("未上榜");
                            TextView textView13 = HomeNewStyleFragment.this.tvRank;
                            if (textView13 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvRank");
                                textView3 = null;
                            } else {
                                textView3 = textView13;
                            }
                            textView3.setTextColor(SkinManager.getThemeColor(((BaseFragment) HomeNewStyleFragment.this).mContext, R.attr.first_txt_color));
                            return;
                        }
                        TypedArray typedArrayObtainStyledAttributes2 = ((BaseFragment) HomeNewStyleFragment.this).mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color, R.attr.third_txt_color});
                        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes2, "mContext.theme.obtainSty… R.attr.third_txt_color))");
                        int color3 = typedArrayObtainStyledAttributes2.getColor(0, ((BaseFragment) HomeNewStyleFragment.this).mContext.getColor(R.color.first_txt_color));
                        SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder(String.valueOf(i6));
                        spannableStringBuilder5.setSpan(new AbsoluteSizeSpan(14, true), 0, spannableStringBuilder5.length(), 18);
                        spannableStringBuilder5.append((CharSequence) "名").setSpan(new AbsoluteSizeSpan(10, true), spannableStringBuilder5.length() - 1, spannableStringBuilder5.length(), 18);
                        spannableStringBuilder5.setSpan(new ForegroundColorSpan(color3), 0, spannableStringBuilder5.length(), 18);
                        TextView textView14 = HomeNewStyleFragment.this.tvRank;
                        if (textView14 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvRank");
                            textView2 = null;
                        } else {
                            textView2 = textView14;
                        }
                        textView2.setText(spannableStringBuilder5);
                        typedArrayObtainStyledAttributes2.recycle();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void setListener() {
        View view = this.tvGotoQuestionBank;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvGotoQuestionBank");
            view = null;
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.t6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                HomeNewStyleFragment.setListener$lambda$2(view3);
            }
        });
        View view3 = this.tvMoreGoods;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvMoreGoods");
            view3 = null;
        }
        view3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.g6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                HomeNewStyleFragment.setListener$lambda$3(this.f15618c, view4);
            }
        });
        View view4 = this.tvMoreCourse;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvMoreCourse");
            view4 = null;
        }
        view4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.h6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                HomeNewStyleFragment.setListener$lambda$4(view5);
            }
        });
        this.apiCount = 6;
        if (!Intrinsics.areEqual("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, this.mContext))) {
            this.apiCount++;
        }
        if (!Intrinsics.areEqual("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, this.mContext))) {
            this.apiCount++;
        }
        getViewHolder().get(R.id.iv_switch).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.i6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                HomeNewStyleFragment.setListener$lambda$5(this.f15663c, view5);
            }
        });
        getViewHolder().get(R.id.customimg).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.j6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                HomeNewStyleFragment.setListener$lambda$6(this.f15686c, view5);
            }
        });
        getViewHolder().get(R.id.iv_search).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                HomeNewStyleFragment.setListener$lambda$7(this.f15713c, view5);
            }
        });
        TextView textView = this.tvLearnTime;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLearnTime");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.l6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                HomeNewStyleFragment.setListener$lambda$8(this.f15808c, view5);
            }
        });
        View view5 = this.ivClose;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivClose");
            view5 = null;
        }
        view5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.m6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view6) {
                HomeNewStyleFragment.setListener$lambda$9(this.f15832c, view6);
            }
        });
        View view6 = this.ivCollapse;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivCollapse");
            view6 = null;
        }
        view6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.n6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view7) {
                HomeNewStyleFragment.setListener$lambda$10(this.f15866c, view7);
            }
        });
        View view7 = this.ivNormal;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivNormal");
            view7 = null;
        }
        view7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.p6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view8) {
                HomeNewStyleFragment.setListener$lambda$11(this.f15910c, view8);
            }
        });
        if (Intrinsics.areEqual("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, this.mContext)) && Intrinsics.areEqual("1", SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, this.mContext))) {
            View view8 = getViewHolder().get(R.id.iv_search);
            Intrinsics.checkNotNullExpressionValue(view8, "viewHolder.get<View>(R.id.iv_search)");
            ViewExtensionsKt.gone(view8);
        }
        View view9 = this.ivSign;
        if (view9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivSign");
            view9 = null;
        }
        view9.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.u6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view10) {
                HomeNewStyleFragment.setListener$lambda$12(this.f16036c, view10);
            }
        });
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_two_color});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "mContext.theme.obtainSty…R.attr.new_bg_two_color))");
        FragmentActivity activity = getActivity();
        Window window = activity != null ? activity.getWindow() : null;
        if (window != null) {
            window.setStatusBarColor(typedArrayObtainStyledAttributes.getColor(0, this.mContext.getColor(R.color.new_bg_two_color)));
        }
        getViewHolder().get(R.id.tv_question_rank).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.v6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view10) {
                HomeNewStyleFragment.setListener$lambda$13(this.f16064c, view10);
            }
        });
        View view10 = this.rlBuyBook;
        if (view10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
            view10 = null;
        }
        view10.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.w6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view11) {
                HomeNewStyleFragment.setListener$lambda$14(this.f16094c, view11);
            }
        });
        View view11 = this.ivDelete;
        if (view11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivDelete");
            view11 = null;
        }
        view11.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.x6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view12) {
                HomeNewStyleFragment.setListener$lambda$15(this.f16112c, view12);
            }
        });
        View view12 = this.mImgHidden;
        if (view12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgHidden");
        } else {
            view2 = view12;
        }
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.y6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view13) {
                HomeNewStyleFragment.setListener$lambda$16(this.f16139c, view13);
            }
        });
        getViewHolder().get(R.id.ll_count).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.z6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view13) {
                HomeNewStyleFragment.setListener$lambda$17(this.f16161c, view13);
            }
        });
        getViewHolder().get(R.id.ll_time).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.a7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view13) {
                HomeNewStyleFragment.setListener$lambda$18(this.f15437c, view13);
            }
        });
        getViewHolder().get(R.id.ll_right).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.e6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view13) {
                HomeNewStyleFragment.setListener$lambda$19(this.f15561c, view13);
            }
        });
        getViewHolder().get(R.id.ll_rank).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view13) {
                HomeNewStyleFragment.setListener$lambda$20(this.f15588c, view13);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$10(HomeNewStyleFragment this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        ViewExtensionsKt.gone(it);
        View view = this$0.ivClose;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivClose");
            view = null;
        }
        ViewExtensionsKt.visible(view);
        View view3 = this$0.ivNormal;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivNormal");
        } else {
            view2 = view3;
        }
        ViewExtensionsKt.visible(view2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$11(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!CommonUtil.isFastClick() && this$0.isLogin()) {
            Intent intentPutExtra = new Intent(this$0.mContext, (Class<?>) AppTestInfoAct.class).putExtra("web_url", NetworkRequestsURL.appTestFeedBack).putExtra("title", "医考帮试用中心");
            Intrinsics.checkNotNullExpressionValue(intentPutExtra, "Intent(mContext, AppTest…Extra(\"title\", \"医考帮试用中心\")");
            this$0.mContext.startActivity(intentPutExtra);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$12(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!CommonUtil.isFastClick() && this$0.isLogin()) {
            this$0.startActivity(new Intent(this$0.getContext(), (Class<?>) SignInActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$13(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!CommonUtil.isFastClick() && this$0.isLogin()) {
            this$0.startActivity(new Intent(this$0.mContext, (Class<?>) RankingAct.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$14(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!CommonUtil.isFastClick() && this$0.isLogin()) {
            try {
                HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO = this$0.ads.get(this$0.currentIndex);
                Intrinsics.checkNotNullExpressionValue(adsDTO, "ads[currentIndex]");
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(adsDTO.getExtra()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$15(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View view2 = this$0.rlBuyBook;
        ImageSwitcher imageSwitcher = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
            view2 = null;
        }
        View view3 = this$0.mImgHidden;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgHidden");
            view3 = null;
        }
        AnimUtil.slideOut(view2, view3, view.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME, Long.valueOf(System.currentTimeMillis()), this$0.mContext);
        if (this$0.closeAd) {
            return;
        }
        this$0.closeAd = true;
        if (this$0.adSwitchRunnable != null) {
            ImageSwitcher imageSwitcher2 = this$0.imageSwitcher;
            if (imageSwitcher2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
            } else {
                imageSwitcher = imageSwitcher2;
            }
            imageSwitcher.removeCallbacks(this$0.adSwitchRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$16(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View view2 = this$0.rlBuyBook;
        ImageSwitcher imageSwitcher = null;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rlBuyBook");
            view2 = null;
        }
        View view3 = this$0.mImgHidden;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgHidden");
            view3 = null;
        }
        AnimUtil.slideIn(view2, view3, 0);
        this$0.closeAd = false;
        if (this$0.adSwitchRunnable != null) {
            ImageSwitcher imageSwitcher2 = this$0.imageSwitcher;
            if (imageSwitcher2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imageSwitcher");
            } else {
                imageSwitcher = imageSwitcher2;
            }
            imageSwitcher.postDelayed(this$0.adSwitchRunnable, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$17(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLogin()) {
            StatisticsMainActivity.Companion companion = StatisticsMainActivity.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "it.context");
            companion.navigationToStatisticsMain(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$18(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLogin()) {
            StatisticsMainActivity.Companion companion = StatisticsMainActivity.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "it.context");
            companion.navigationToStatisticsMain(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$19(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLogin()) {
            StatisticsMainActivity.Companion companion = StatisticsMainActivity.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "it.context");
            companion.navigationToStatisticsMain(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$2(View view) {
        EventBus.getDefault().post("jump2QuestionBank");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$20(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLogin()) {
            RankingAct.newIntent(view.getContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$3(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0.mContext, (Class<?>) StoreListAct.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$4(View view) {
        EventBus.getDefault().post("jump2CourseList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$5(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0.getActivity(), (Class<?>) SelectIdentityNewActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("appbeanname", "");
        this$0.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$6(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLogin()) {
            this$0.startActivity(new Intent(this$0.getActivity(), (Class<?>) ContactCustomerServiceNewActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$7(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!CommonUtil.isFastClick() && this$0.isLogin()) {
            this$0.startActivity(NewHomeSearchAct.newIntent(this$0.getContext()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$8(HomeNewStyleFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!CommonUtil.isFastClick() && this$0.isLogin()) {
            StatisticsMainActivity.Companion companion = StatisticsMainActivity.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "it.context");
            companion.navigationToStatisticsMain(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListener$lambda$9(HomeNewStyleFragment this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View view = this$0.ivCollapse;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivCollapse");
            view = null;
        }
        ViewExtensionsKt.visible(view);
        View view3 = this$0.ivNormal;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivNormal");
        } else {
            view2 = view3;
        }
        ViewExtensionsKt.gone(view2);
        Intrinsics.checkNotNullExpressionValue(it, "it");
        ViewExtensionsKt.gone(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setLoadedApiCount(int i2) {
        this.loadedApiCount = i2;
        if (i2 == this.apiCount) {
            this.loadComplete = true;
            SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
            if (smartRefreshLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("refreshLayout");
                smartRefreshLayout = null;
            }
            smartRefreshLayout.finishRefresh();
        }
    }

    private final void showGuide(final int guideType) {
        View viewFindViewById;
        Pair<Integer, Integer> pair = this.guideViewMap.get(Integer.valueOf(guideType));
        Integer num = this.guideViewPositionMap.get(Integer.valueOf(guideType));
        Integer num2 = this.guideItemPositionMap.get(Integer.valueOf(guideType));
        ViewPager2 viewPager2 = this.vpKingKong;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
            viewPager2 = null;
        }
        int currentItem = viewPager2.getCurrentItem();
        if (pair == null || num2 == null) {
            return;
        }
        if (this.totalItemCount == 6 && currentItem == 1 && num2.intValue() == 5) {
            return;
        }
        if (this.totalItemCount > 10) {
            if (num2.intValue() >= (currentItem + 1) * 10) {
                ViewGroup viewGroup = this.guideOverlay;
                if (viewGroup != null) {
                    ViewExtensionsKt.gone(viewGroup);
                    return;
                }
                return;
            }
            if ((num2.intValue() + 1) / 10 != currentItem) {
                ViewGroup viewGroup2 = this.guideOverlay;
                if (viewGroup2 != null) {
                    ViewExtensionsKt.gone(viewGroup2);
                    return;
                }
                return;
            }
        }
        int iDp2px = SizeUtil.dp2px(this.mContext, guideType == 3 ? 220 : 200);
        ViewGroup viewGroup3 = this.guideView;
        Intrinsics.checkNotNull(viewGroup3);
        ViewGroup.LayoutParams layoutParams = viewGroup3.getLayoutParams();
        layoutParams.width = iDp2px;
        ViewGroup viewGroup4 = this.guideView;
        Intrinsics.checkNotNull(viewGroup4);
        viewGroup4.setLayoutParams(layoutParams);
        ViewGroup viewGroup5 = this.guideView;
        TextView textView = viewGroup5 != null ? (TextView) viewGroup5.findViewById(R.id.tv_content) : null;
        if (textView != null) {
            textView.setText(guideType != 1 ? guideType != 2 ? "自主组题移到这里了" : "模考移到这里了" : "题单移到这里了");
        }
        ViewGroup viewGroup6 = this.guideView;
        Intrinsics.checkNotNull(viewGroup6);
        View viewFindViewById2 = viewGroup6.findViewById(R.id.iv_down_triangle);
        ViewGroup.LayoutParams layoutParams2 = viewFindViewById2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) layoutParams2;
        if (num != null && num.intValue() == 1) {
            layoutParams3.gravity = 3;
        } else if (num != null && num.intValue() == 2) {
            layoutParams3.gravity = 17;
        } else if (num != null && num.intValue() == 3) {
            layoutParams3.gravity = GravityCompat.END;
        }
        viewFindViewById2.setLayoutParams(layoutParams3);
        ViewGroup viewGroup7 = this.guideView;
        if (viewGroup7 != null && (viewFindViewById = viewGroup7.findViewById(R.id.tv_ok)) != null) {
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.d6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeNewStyleFragment.showGuide$lambda$21(this.f15537c, guideType, view);
                }
            });
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.o6
            @Override // java.lang.Runnable
            public final void run() {
                HomeNewStyleFragment.showGuide$lambda$22();
            }
        }, 500L);
        ViewGroup viewGroup8 = this.guideView;
        Intrinsics.checkNotNull(viewGroup8);
        viewGroup8.setX(pair.getFirst().intValue());
        ViewGroup viewGroup9 = this.guideView;
        Intrinsics.checkNotNull(viewGroup9);
        viewGroup9.setY(pair.getSecond().intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGuide$lambda$21(HomeNewStyleFragment this$0, int i2, View view) {
        ViewGroup viewGroup;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.guideViewMap.remove(Integer.valueOf(i2));
        this$0.guideViewPositionMap.remove(Integer.valueOf(i2));
        if (i2 == 1) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_ANSWER_SHEET_TIP, true, this$0.mContext);
            if (this$0.checkCanShow(2)) {
                this$0.showGuide(2);
            } else if (this$0.checkCanShow(3)) {
                this$0.showGuide(3);
            }
        } else if (i2 == 2) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_MOCK_EXAM_TIP, true, this$0.mContext);
            if (this$0.checkCanShow(1)) {
                this$0.showGuide(1);
            } else if (this$0.checkCanShow(3)) {
                this$0.showGuide(3);
            }
        } else if (i2 == 3) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_SELF_COMBINE_QUESTION_TIP, true, this$0.mContext);
            if (this$0.checkCanShow(2)) {
                this$0.showGuide(2);
            } else if (this$0.checkCanShow(1)) {
                this$0.showGuide(1);
            }
        }
        if (!this$0.guideViewMap.isEmpty() || (viewGroup = this$0.guideOverlay) == null) {
            return;
        }
        ViewExtensionsKt.gone(viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showGuide$lambda$22() {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_home_new_style;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.tv_question_count);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.tv_question_count)");
        this.tvQuestionCount = (TextView) view;
        View view2 = holder.get(R.id.scrollView);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.scrollView)");
        this.scrollView = (HeaderScrollView) view2;
        View view3 = holder.get(R.id.tv_correction);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.tv_correction)");
        this.tvCorrection = (TextView) view3;
        View view4 = holder.get(R.id.refreshLayout);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.refreshLayout)");
        this.refreshLayout = (SmartRefreshLayout) view4;
        this.guideView = (ViewGroup) holder.get(R.id.root);
        ViewGroup viewGroup = (ViewGroup) holder.get(R.id.guide_overlay);
        this.guideOverlay = viewGroup;
        if (viewGroup != null) {
            viewGroup.setVisibility(8);
        }
        View view5 = holder.get(R.id.banner);
        Intrinsics.checkNotNullExpressionValue(view5, "holder.get(R.id.banner)");
        this.banner = (Banner) view5;
        View view6 = holder.get(R.id.tree_indicator);
        Intrinsics.checkNotNullExpressionValue(view6, "holder.get(R.id.tree_indicator)");
        this.treeIndicator = (ViewPager2Indicator) view6;
        View view7 = holder.get(R.id.tv_goto_question_bank);
        Intrinsics.checkNotNullExpressionValue(view7, "holder.get(R.id.tv_goto_question_bank)");
        this.tvGotoQuestionBank = view7;
        View view8 = holder.get(R.id.vpKingKong);
        Intrinsics.checkNotNullExpressionValue(view8, "holder.get(R.id.vpKingKong)");
        this.vpKingKong = (ViewPager2) view8;
        View view9 = holder.get(R.id.rl_question_banner);
        Intrinsics.checkNotNullExpressionValue(view9, "holder.get(R.id.rl_question_banner)");
        this.rlQuestionBanner = view9;
        View view10 = holder.get(R.id.tv_update_time);
        Intrinsics.checkNotNullExpressionValue(view10, "holder.get(R.id.tv_update_time)");
        this.tvUpdateTime = (TextView) view10;
        View view11 = holder.get(R.id.tv_learn_time);
        Intrinsics.checkNotNullExpressionValue(view11, "holder.get(R.id.tv_learn_time)");
        this.tvLearnTime = (TextView) view11;
        View view12 = holder.get(R.id.tv_exam_info);
        Intrinsics.checkNotNullExpressionValue(view12, "holder.get(R.id.tv_exam_info)");
        this.tvExamInfo = (TextView) view12;
        View view13 = holder.get(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(view13, "holder.get(R.id.viewpager)");
        this.viewPager = (ViewPager2) view13;
        View view14 = holder.get(R.id.rvLive);
        Intrinsics.checkNotNullExpressionValue(view14, "holder.get(R.id.rvLive)");
        this.rvLive = (RecyclerView) view14;
        View view15 = holder.get(R.id.rvTab);
        Intrinsics.checkNotNullExpressionValue(view15, "holder.get(R.id.rvTab)");
        this.rvTab = (RecyclerView) view15;
        View view16 = holder.get(R.id.rvCourse);
        Intrinsics.checkNotNullExpressionValue(view16, "holder.get(R.id.rvCourse)");
        this.rvCourse = (RecyclerView) view16;
        View view17 = holder.get(R.id.rvGoods);
        Intrinsics.checkNotNullExpressionValue(view17, "holder.get(R.id.rvGoods)");
        this.rvGoods = (RecyclerView) view17;
        View view18 = holder.get(R.id.iv_ad_close);
        Intrinsics.checkNotNullExpressionValue(view18, "holder.get(R.id.iv_ad_close)");
        this.ivCloseBanner = view18;
        View view19 = holder.get(R.id.tv_more_course);
        Intrinsics.checkNotNullExpressionValue(view19, "holder.get(R.id.tv_more_course)");
        this.tvMoreCourse = view19;
        View view20 = holder.get(R.id.tv_more_goods);
        Intrinsics.checkNotNullExpressionValue(view20, "holder.get(R.id.tv_more_goods)");
        this.tvMoreGoods = view20;
        View view21 = holder.get(R.id.tv_question_rank);
        Intrinsics.checkNotNullExpressionValue(view21, "holder.get(R.id.tv_question_rank)");
        this.tvRank = (TextView) view21;
        View view22 = holder.get(R.id.ll_course);
        Intrinsics.checkNotNullExpressionValue(view22, "holder.get(R.id.ll_course)");
        this.llCourse = view22;
        View view23 = holder.get(R.id.ll_goods);
        Intrinsics.checkNotNullExpressionValue(view23, "holder.get(R.id.ll_goods)");
        this.llGoods = view23;
        View view24 = holder.get(R.id.ll_live);
        Intrinsics.checkNotNullExpressionValue(view24, "holder.get(R.id.ll_live)");
        this.llLive = view24;
        View view25 = holder.get(R.id.iv_sign);
        Intrinsics.checkNotNullExpressionValue(view25, "holder.get(R.id.iv_sign)");
        this.ivSign = view25;
        View view26 = holder.get(R.id.iv_close);
        Intrinsics.checkNotNullExpressionValue(view26, "holder.get(R.id.iv_close)");
        this.ivClose = view26;
        View view27 = holder.get(R.id.iv_collapse);
        Intrinsics.checkNotNullExpressionValue(view27, "holder.get(R.id.iv_collapse)");
        this.ivCollapse = view27;
        View view28 = holder.get(R.id.iv_normal);
        Intrinsics.checkNotNullExpressionValue(view28, "holder.get(R.id.iv_normal)");
        this.ivNormal = view28;
        View view29 = holder.get(R.id.rl_buy_book);
        Intrinsics.checkNotNullExpressionValue(view29, "holder.get(R.id.rl_buy_book)");
        this.rlBuyBook = view29;
        View view30 = holder.get(R.id.iv_delete);
        Intrinsics.checkNotNullExpressionValue(view30, "holder.get(R.id.iv_delete)");
        this.ivDelete = view30;
        View view31 = holder.get(R.id.imageSwitcher);
        Intrinsics.checkNotNullExpressionValue(view31, "holder.get(R.id.imageSwitcher)");
        this.imageSwitcher = (ImageSwitcher) view31;
        View view32 = holder.get(R.id.img_hidden);
        Intrinsics.checkNotNullExpressionValue(view32, "holder.get(R.id.img_hidden)");
        this.mImgHidden = view32;
        ViewGroup viewGroup2 = this.guideOverlay;
        if (viewGroup2 != null) {
            viewGroup2.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.fragmenthome.r6
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view33, MotionEvent motionEvent) {
                    return HomeNewStyleFragment.initViews$lambda$0(view33, motionEvent);
                }
            });
        }
        ViewPager2Indicator viewPager2Indicator = this.treeIndicator;
        SmartRefreshLayout smartRefreshLayout = null;
        if (viewPager2Indicator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
            viewPager2Indicator = null;
        }
        ViewExtensionsKt.gone(viewPager2Indicator);
        SmartRefreshLayout smartRefreshLayout2 = this.refreshLayout;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refreshLayout");
        } else {
            smartRefreshLayout = smartRefreshLayout2;
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.s6
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                HomeNewStyleFragment.initViews$lambda$1(this.f15983c, refreshLayout);
            }
        });
        setListener();
        initData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(@Nullable String str) {
        super.onEventMainThread(str);
        if (!Intrinsics.areEqual(str, "LoginSuccess")) {
            if (Intrinsics.areEqual(EventBusConstant.EVENT_REFRESH_DAILY_TASK, str)) {
                loadKingKongArea();
            }
        } else {
            SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
            if (smartRefreshLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("refreshLayout");
                smartRefreshLayout = null;
            }
            smartRefreshLayout.autoRefresh();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    private final List<Integer> checkShowGuide(String type, int position) {
        List<? extends NewHomeKingKongItem> item = getItem(position);
        ArrayList arrayList = new ArrayList();
        int size = item.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            NewHomeKingKongItem newHomeKingKongItem = item.get(i2);
            if (!TextUtils.equals(type, TextUtils.isEmpty(newHomeKingKongItem.getPush_type()) ? newHomeKingKongItem.getJpush_type() : newHomeKingKongItem.getPush_type())) {
                i2++;
            } else if (TextUtils.equals(type, "29")) {
                if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_ANSWER_SHEET_TIP, false, this.mContext)) {
                    arrayList.add(Integer.valueOf(i2));
                }
            } else if (!TextUtils.equals(type, "41") && !TextUtils.equals(type, RoomMasterTable.DEFAULT_ID)) {
                if (TextUtils.equals(type, "38") && !SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_SELF_COMBINE_QUESTION_TIP, false, this.mContext)) {
                    arrayList.add(Integer.valueOf(i2));
                }
            } else if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_MOCK_EXAM_TIP, false, this.mContext)) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        return arrayList;
    }
}
