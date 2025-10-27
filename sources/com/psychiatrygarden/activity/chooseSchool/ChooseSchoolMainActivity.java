package com.psychiatrygarden.activity.chooseSchool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.HomeInfoListAct;
import com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolProgressAdapter;
import com.psychiatrygarden.activity.chooseSchool.adapter.TargetSchoolAdapter;
import com.psychiatrygarden.activity.chooseSchool.adapter.ViewPageNoticeAdapter;
import com.psychiatrygarden.activity.chooseSchool.adapter.WxInfoAdapter;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.activity.chooseSchool.widget.GetFreePopupView;
import com.psychiatrygarden.activity.chooseSchool.widget.WaveChartViewNew;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.ChooseInfo;
import com.psychiatrygarden.bean.ChooseInfoSchoolInfo;
import com.psychiatrygarden.bean.ChooseInfoScore;
import com.psychiatrygarden.bean.ChooseSchoolCalendarListItemBean;
import com.psychiatrygarden.bean.ChooseSchoolTargetSchoolBean;
import com.psychiatrygarden.bean.ChooseSchoolTargetSchoolBeanItem;
import com.psychiatrygarden.bean.ChooseSchoolTopInfo;
import com.psychiatrygarden.bean.GetFreePermissionBean;
import com.psychiatrygarden.bean.GetFreePermissionBeanData;
import com.psychiatrygarden.bean.GetPermissionBean;
import com.psychiatrygarden.bean.GetPermissionBeanData;
import com.psychiatrygarden.bean.HomeInfoItemBean;
import com.psychiatrygarden.bean.NewHomeKingKongItem;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.FlowLayout;
import com.psychiatrygarden.utils.FlowLayoutUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StringUtil;
import com.psychiatrygarden.utils.ViewPager2SlowScrollHelper;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.ViewPager2Indicator;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000Ó\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016*\u0001.\u0018\u0000 \u008a\u00012\u00020\u0001:\u0002\u008a\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\u00072\u0006\u0010\\\u001a\u00020\u0007H\u0002J\b\u0010]\u001a\u00020ZH\u0002J\u001a\u0010^\u001a\u00020Z2\u0006\u0010_\u001a\u00020\u00072\b\b\u0002\u0010`\u001a\u00020\fH\u0002J\"\u0010a\u001a\u00020\u00042\b\u0010b\u001a\u0004\u0018\u00010\u00072\u000e\u0010c\u001a\n\u0012\u0004\u0012\u00020e\u0018\u00010dH\u0002J\"\u0010f\u001a\u000e\u0012\u0004\u0012\u00020h\u0012\u0004\u0012\u00020i0g2\f\u0010j\u001a\b\u0012\u0004\u0012\u00020h0dH\u0002J\b\u0010k\u001a\u00020ZH\u0016J\b\u0010l\u001a\u00020ZH\u0002J\u0018\u0010m\u001a\u00020Z2\u000e\u0010n\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010dH\u0002J\u0010\u0010o\u001a\u00020Z2\u0006\u0010p\u001a\u00020qH\u0002J\u0018\u0010r\u001a\u00020Z2\u0006\u0010_\u001a\u00020\u00072\u0006\u0010s\u001a\u00020\u0007H\u0002J\u0010\u0010t\u001a\u00020Z2\u0006\u0010j\u001a\u00020uH\u0002J\b\u0010v\u001a\u00020ZH\u0002J\b\u0010w\u001a\u00020\fH\u0002J\b\u0010x\u001a\u00020ZH\u0002J\b\u0010y\u001a\u00020ZH\u0002J\b\u0010z\u001a\u00020ZH\u0002J\b\u0010{\u001a\u00020ZH\u0002J\b\u0010|\u001a\u00020ZH\u0002J\b\u0010}\u001a\u00020ZH\u0002J\b\u0010~\u001a\u00020ZH\u0002J\b\u0010\u007f\u001a\u00020ZH\u0002J\u0012\u0010\u0080\u0001\u001a\u00020Z2\u0007\u0010\u0081\u0001\u001a\u00020\u0004H\u0002J\u0014\u0010\u0082\u0001\u001a\u00020Z2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u0007H\u0016J\t\u0010\u0084\u0001\u001a\u00020ZH\u0014J\t\u0010\u0085\u0001\u001a\u00020ZH\u0016J\u0012\u0010\u0086\u0001\u001a\u00020Z2\u0007\u0010\u0087\u0001\u001a\u00020\fH\u0002J\t\u0010\u0088\u0001\u001a\u00020ZH\u0016J\u001d\u0010\u0089\u0001\u001a\u00020Z2\b\u0010_\u001a\u0004\u0018\u00010\u00072\b\u0010s\u001a\u0004\u0018\u00010\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020.X\u0082\u0004¢\u0006\u0004\n\u0002\u0010/R\u000e\u00100\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000206X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000206X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020?X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010P\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020RX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020TX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020TX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020WX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010X\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u008b\u0001"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "SCROLL_BEGIN", "", "SCROLL_END", "baoCount", "", "chooseSchoolScrollView", "Landroidx/core/widget/NestedScrollView;", "congCount", "firstDialog", "", "flowLayout", "Lcom/psychiatrygarden/utils/FlowLayout;", "handler", "Lcom/psychiatrygarden/utils/WeakHandler;", "haveMoreWxInfo", "hint", "Landroid/widget/TextView;", "imgBack", "Landroid/widget/ImageView;", "imgQuestionMark1", "imgQuestionMark2", "imgQuestionMark3", "isHaveScore", "layoutBaoDi", "Landroid/widget/RelativeLayout;", "layoutBottom1V1", "layoutChongJi", "layoutEditScore", "layoutKyCalendar", "layoutNotify", "layoutSchoolTitle", "Landroid/widget/LinearLayout;", "layoutScoreLine", "layoutWenTuo", "layoutZx", "lineGap", "Landroid/view/View;", "mActivityId", "mAdapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/WxInfoAdapter;", "mAdapterKy", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolProgressAdapter;", "mKingKongAdapter", "com/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$mKingKongAdapter$1", "Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$mKingKongAdapter$1;", "mType", "major_id", "nanCount", "noticeAdapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ViewPageNoticeAdapter;", "recyclerViewKy", "Landroidx/recyclerview/widget/RecyclerView;", "recyclerViewTargetSchool", "recyclerViewZx", "str1v1QrCode", "str1v1QrId", "str1v1Success", "targetSchoolAdapter", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/TargetSchoolAdapter;", "treeIndicator", "Lcom/psychiatrygarden/widget/ViewPager2Indicator;", "tv1", "tv2", "tvActionBarBg", "tvAddTargetSchool", "tvBtnGetPermission", "tvCountBaoDi", "tvCountChongJi", "tvCountWenTuo", "tvNotify", "tvPermissionTitle", "tvSchoolCount", "tvScore", "tvSubTitle", "tvTargetSchoolMore", "tvTitle", "tvZYCount", "user_score", "viewPage2NotifyHelp", "Lcom/psychiatrygarden/utils/ViewPager2SlowScrollHelper;", "viewPager2Notify", "Landroidx/viewpager2/widget/ViewPager2;", "vpKingKong", "waveChart", "Lcom/psychiatrygarden/activity/chooseSchool/widget/WaveChartViewNew;", "wenCount", "delTargetSchool", "", "schoolId", "schoolName", "getFreeCount", "getFreePermission", AdUnitActivity.EXTRA_ACTIVITY_ID, "isOnRestart", "getIndex", "userScore", "scoreCount", "", "Lcom/psychiatrygarden/bean/ChooseInfoScore;", "getKingKongAdapter", "Lcom/chad/library/adapter/base/BaseMultiItemQuickAdapter;", "Lcom/psychiatrygarden/bean/NewHomeKingKongItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "data", "init", "initKingKong", "initNotify", "listData", "initPermissionBtn", "itemData", "Lcom/psychiatrygarden/bean/GetPermissionBeanData;", "initPermissionList", "type", "initScore", "Lcom/psychiatrygarden/bean/ChooseInfo;", "initWxInfo", "isNight", "load1v1QrCode", "loadChooseInfo", "loadKingKongArea", "loadKyCalendar", "loadPermissionBtnInfo", "loadTargetSchool", "loadTopDataInfo", "loadWXInfoData", "navigateToChooseSchool", "index", "onEventMainThread", "str", "onRestart", "setContentView", "setHave1V1Btn", "have1v1", "setListenerForWidget", "showGetFreeDialog", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChooseSchoolMainActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChooseSchoolMainActivity.kt\ncom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1273:1\n1864#2,3:1274\n1864#2,3:1277\n1855#2,2:1280\n*S KotlinDebug\n*F\n+ 1 ChooseSchoolMainActivity.kt\ncom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity\n*L\n1095#1:1274,3\n1126#1:1277,3\n1154#1:1280,2\n*E\n"})
/* loaded from: classes5.dex */
public final class ChooseSchoolMainActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private NestedScrollView chooseSchoolScrollView;
    private FlowLayout flowLayout;
    private WeakHandler handler;
    private TextView hint;
    private ImageView imgBack;
    private ImageView imgQuestionMark1;
    private ImageView imgQuestionMark2;
    private ImageView imgQuestionMark3;
    private boolean isHaveScore;
    private RelativeLayout layoutBaoDi;
    private RelativeLayout layoutBottom1V1;
    private RelativeLayout layoutChongJi;
    private RelativeLayout layoutEditScore;
    private RelativeLayout layoutKyCalendar;
    private RelativeLayout layoutNotify;
    private LinearLayout layoutSchoolTitle;
    private LinearLayout layoutScoreLine;
    private RelativeLayout layoutWenTuo;
    private RelativeLayout layoutZx;
    private View lineGap;
    private WxInfoAdapter mAdapter;
    private ChooseSchoolProgressAdapter mAdapterKy;
    private ViewPageNoticeAdapter noticeAdapter;
    private RecyclerView recyclerViewKy;
    private RecyclerView recyclerViewTargetSchool;
    private RecyclerView recyclerViewZx;
    private boolean str1v1Success;
    private TargetSchoolAdapter targetSchoolAdapter;
    private ViewPager2Indicator treeIndicator;
    private TextView tv1;
    private TextView tv2;
    private TextView tvActionBarBg;
    private TextView tvAddTargetSchool;
    private TextView tvBtnGetPermission;
    private TextView tvCountBaoDi;
    private TextView tvCountChongJi;
    private TextView tvCountWenTuo;
    private TextView tvNotify;
    private TextView tvPermissionTitle;
    private TextView tvSchoolCount;
    private TextView tvScore;
    private TextView tvSubTitle;
    private TextView tvTargetSchoolMore;
    private TextView tvTitle;
    private TextView tvZYCount;
    private ViewPager2SlowScrollHelper viewPage2NotifyHelp;
    private ViewPager2 viewPager2Notify;
    private ViewPager2 vpKingKong;
    private WaveChartViewNew waveChart;

    @NotNull
    private String str1v1QrCode = "";

    @NotNull
    private String str1v1QrId = "";

    @Nullable
    private String baoCount = "";

    @Nullable
    private String wenCount = "";

    @Nullable
    private String congCount = "";

    @Nullable
    private String nanCount = "";
    private boolean haveMoreWxInfo = true;
    private boolean firstDialog = true;
    private final int SCROLL_BEGIN = 100;
    private final int SCROLL_END = 500;

    @NotNull
    private String mActivityId = "";

    @NotNull
    private String mType = "";

    @NotNull
    private final ChooseSchoolMainActivity$mKingKongAdapter$1 mKingKongAdapter = new BaseQuickAdapter<List<? extends NewHomeKingKongItem>, BaseViewHolder>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$mKingKongAdapter$1
        {
            super(R.layout.home_kk_tab, null, 2, null);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull List<? extends NewHomeKingKongItem> item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.rvKingKong);
            recyclerView.setLayoutManager(new GridLayoutManager(this.this$0.mContext, 5, 1, false));
            recyclerView.setAdapter(this.this$0.getKingKongAdapter(item));
        }
    };

    @Nullable
    private String major_id = "";

    @Nullable
    private String user_score = "";

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$Companion;", "", "()V", "navigationToChooseSchoolMainActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToChooseSchoolMainActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) ChooseSchoolMainActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$getKingKongAdapter$1", "Lcom/chad/library/adapter/base/BaseMultiItemQuickAdapter;", "Lcom/psychiatrygarden/bean/NewHomeKingKongItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nChooseSchoolMainActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChooseSchoolMainActivity.kt\ncom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$getKingKongAdapter$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1273:1\n1#2:1274\n*E\n"})
    /* renamed from: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$getKingKongAdapter$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05691 extends BaseMultiItemQuickAdapter<NewHomeKingKongItem, BaseViewHolder> {
        final /* synthetic */ int $itemWidth;
        final /* synthetic */ ChooseSchoolMainActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05691(List<? extends NewHomeKingKongItem> list, int i2, ChooseSchoolMainActivity chooseSchoolMainActivity) {
            super(null, 1, null);
            this.$itemWidth = i2;
            this.this$0 = chooseSchoolMainActivity;
            addItemType(0, R.layout.item_home_king_kong_child);
            addItemType(1, R.layout.item_kingkong_area_normal);
            setList(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$1(NewHomeKingKongItem item, ChooseSchoolMainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (TextUtils.isEmpty(item.getPush_type())) {
                item.setPush_type(item.getJpush_type());
            }
            if (Intrinsics.areEqual(item.getPush_type(), "50") && this$0.str1v1Success) {
                item.setQr_code(this$0.str1v1QrCode);
                item.setWe_chat_id(this$0.str1v1QrId);
            }
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(item));
            AliYunLogUtil.getInstance().addLogTouchName(AliyunEvent.HomePageBannerArea, item.getName());
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull final NewHomeKingKongItem item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            ((ViewGroup.MarginLayoutParams) layoutParams2).width = this.$itemWidth;
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = holder.getLayoutPosition() > 4 ? SizeUtil.dp2px(this.this$0.mContext, 8) : 0;
            holder.itemView.setLayoutParams(layoutParams2);
            boolean z2 = !TextUtils.isEmpty(item.getIs_live_broadcast()) && Intrinsics.areEqual(item.getIs_live_broadcast(), "1");
            BaseViewHolder text = holder.setText(R.id.tv_label, item.getLabel()).setText(R.id.tv_title, item.getName());
            String label = item.getLabel();
            text.setGone(R.id.tv_label, (label == null || label.length() == 0) || z2);
            ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
            imageView.getLayoutParams().width = SizeUtil.dp2px(this.this$0.mContext, 32);
            imageView.getLayoutParams().height = SizeUtil.dp2px(this.this$0.mContext, 32);
            if (z2) {
                ImageView imageView2 = (ImageView) holder.getView(R.id.img_live);
                holder.getView(R.id.ly_live).setVisibility(0);
                imageView2.setBackgroundResource(SkinManager.getCurrentSkinType(this.this$0.mContext) == 1 ? R.drawable.live_calendar_living_animation_night : R.drawable.live_calendar_living_animation);
                Drawable background = imageView2.getBackground();
                Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
                ((AnimationDrawable) background).start();
            } else {
                View view = holder.getView(R.id.tv_label);
                holder.getView(R.id.tv_label).setSelected(SkinManager.getCurrentSkinType(this.this$0.mContext) == 1);
                int iDp2px = SizeUtil.dp2px(this.this$0.mContext, 5);
                int iDp2px2 = SizeUtil.dp2px(this.this$0.mContext, 11);
                if (item.getLabel() == null || item.getLabel().length() != 1) {
                    view.setPadding(iDp2px, view.getPaddingTop(), iDp2px, view.getPaddingBottom());
                } else {
                    view.setPadding(iDp2px2, view.getPaddingTop(), iDp2px2, view.getPaddingBottom());
                }
            }
            final ImageView imageView3 = (ImageView) holder.getView(R.id.iv_icon);
            if (!TextUtils.isEmpty(item.getIcon())) {
                String icon = item.getIcon();
                Intrinsics.checkNotNullExpressionValue(icon, "item.icon");
                if (new Regex("(?i).*\\.gif$").matches(icon)) {
                    Glide.with(this.this$0.mContext).asGif().load(item.getIcon()).into((RequestBuilder<GifDrawable>) new CustomTarget<GifDrawable>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$getKingKongAdapter$1$convert$1
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
                            imageView3.setImageDrawable(resource);
                            resource.start();
                        }
                    });
                } else {
                    GlideUtils.loadImage(this.this$0.mContext, item.getIcon(), (ImageView) holder.getView(R.id.iv_icon));
                }
            }
            View view2 = holder.itemView;
            final ChooseSchoolMainActivity chooseSchoolMainActivity = this.this$0;
            view2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    ChooseSchoolMainActivity.C05691.convert$lambda$1(item, chooseSchoolMainActivity, view3);
                }
            });
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"com/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$loadKingKongArea$1", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", AliyunVodHttpCommon.Format.FORMAT_JSON, "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nChooseSchoolMainActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChooseSchoolMainActivity.kt\ncom/psychiatrygarden/activity/chooseSchool/ChooseSchoolMainActivity$loadKingKongArea$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1273:1\n1#2:1274\n*E\n"})
    /* renamed from: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$loadKingKongArea$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05721 extends AjaxCallBack<String> {
        public C05721() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$4(int i2) {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ViewPager2Indicator viewPager2Indicator = ChooseSchoolMainActivity.this.treeIndicator;
            if (viewPager2Indicator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                viewPager2Indicator = null;
            }
            ViewExtensionsKt.gone(viewPager2Indicator);
            ChooseSchoolMainActivity.this.setHave1V1Btn(false);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String json) {
            Object next;
            Intrinsics.checkNotNullParameter(json, "json");
            ViewPager2Indicator viewPager2Indicator = null;
            try {
                JSONObject jSONObject = new JSONObject(json);
                if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                    List dataList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends NewHomeKingKongItem>>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$loadKingKongArea$1$onSuccess$dataList$1
                    }.getType());
                    List list = dataList;
                    boolean z2 = true;
                    if (list == null || list.isEmpty()) {
                        ChooseSchoolMainActivity.this.setHave1V1Btn(false);
                        return;
                    }
                    if (dataList.size() <= 5) {
                        ViewPager2 viewPager2 = ChooseSchoolMainActivity.this.vpKingKong;
                        if (viewPager2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager2 = null;
                        }
                        ChooseSchoolMainActivity$mKingKongAdapter$1 chooseSchoolMainActivity$mKingKongAdapter$1 = ChooseSchoolMainActivity.this.mKingKongAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        chooseSchoolMainActivity$mKingKongAdapter$1.setList(CollectionsKt__CollectionsKt.arrayListOf(dataList));
                        viewPager2.setAdapter(chooseSchoolMainActivity$mKingKongAdapter$1);
                    } else if (dataList.size() > 10) {
                        ViewPager2 viewPager22 = ChooseSchoolMainActivity.this.vpKingKong;
                        if (viewPager22 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager22 = null;
                        }
                        ChooseSchoolMainActivity$mKingKongAdapter$1 chooseSchoolMainActivity$mKingKongAdapter$12 = ChooseSchoolMainActivity.this.mKingKongAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        chooseSchoolMainActivity$mKingKongAdapter$12.setList(CollectionsKt___CollectionsKt.chunked(dataList, 10));
                        viewPager22.setAdapter(chooseSchoolMainActivity$mKingKongAdapter$12);
                    } else if (dataList.size() < 7) {
                        ViewPager2 viewPager23 = ChooseSchoolMainActivity.this.vpKingKong;
                        if (viewPager23 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager23 = null;
                        }
                        ChooseSchoolMainActivity$mKingKongAdapter$1 chooseSchoolMainActivity$mKingKongAdapter$13 = ChooseSchoolMainActivity.this.mKingKongAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        chooseSchoolMainActivity$mKingKongAdapter$13.setList(CollectionsKt___CollectionsKt.chunked(dataList, 5));
                        viewPager23.setAdapter(chooseSchoolMainActivity$mKingKongAdapter$13);
                    } else {
                        ViewPager2 viewPager24 = ChooseSchoolMainActivity.this.vpKingKong;
                        if (viewPager24 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                            viewPager24 = null;
                        }
                        ChooseSchoolMainActivity$mKingKongAdapter$1 chooseSchoolMainActivity$mKingKongAdapter$14 = ChooseSchoolMainActivity.this.mKingKongAdapter;
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        chooseSchoolMainActivity$mKingKongAdapter$14.setList(CollectionsKt__CollectionsKt.arrayListOf(dataList));
                        viewPager24.setAdapter(chooseSchoolMainActivity$mKingKongAdapter$14);
                    }
                    ViewPager2Indicator viewPager2Indicator2 = ChooseSchoolMainActivity.this.treeIndicator;
                    if (viewPager2Indicator2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                        viewPager2Indicator2 = null;
                    }
                    viewPager2Indicator2.setVisibility((dataList.size() == 6 || dataList.size() > 10) ? 0 : 8);
                    ViewPager2Indicator viewPager2Indicator3 = ChooseSchoolMainActivity.this.treeIndicator;
                    if (viewPager2Indicator3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                        viewPager2Indicator3 = null;
                    }
                    ViewPager2 viewPager25 = ChooseSchoolMainActivity.this.vpKingKong;
                    if (viewPager25 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("vpKingKong");
                        viewPager25 = null;
                    }
                    viewPager2Indicator3.bindViewPager2(viewPager25, new ViewPager2Indicator.PageSelectListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v0
                        @Override // com.psychiatrygarden.widget.ViewPager2Indicator.PageSelectListener
                        public final void onPageSelect(int i2) {
                            ChooseSchoolMainActivity.C05721.onSuccess$lambda$4(i2);
                        }
                    });
                    Iterator it = dataList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            if (Intrinsics.areEqual(((NewHomeKingKongItem) next).getPush_type(), "50")) {
                                break;
                            }
                        }
                    }
                    NewHomeKingKongItem newHomeKingKongItem = (NewHomeKingKongItem) next;
                    ChooseSchoolMainActivity chooseSchoolMainActivity = ChooseSchoolMainActivity.this;
                    if (newHomeKingKongItem == null) {
                        z2 = false;
                    }
                    chooseSchoolMainActivity.setHave1V1Btn(z2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                ViewPager2Indicator viewPager2Indicator4 = ChooseSchoolMainActivity.this.treeIndicator;
                if (viewPager2Indicator4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                } else {
                    viewPager2Indicator = viewPager2Indicator4;
                }
                ViewExtensionsKt.gone(viewPager2Indicator);
                ChooseSchoolMainActivity.this.setHave1V1Btn(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void delTargetSchool(final String schoolId, final String schoolName) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", schoolId);
        ajaxParams.put("operate", PLVRemoveMicSiteEvent.EVENT_NAME);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchoolTargetSchoolOpera, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.delTargetSchool.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    if (Intrinsics.areEqual("200", new JSONObject(t2).optString("code"))) {
                        ChooseSchoolMainActivity.this.loadTargetSchool();
                        AliYunLogUtil.getInstance().addLog(AliyunEvent.DeleSchool, schoolId, schoolName);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void getFreeCount() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchoolGetPermissionGetFreePop, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.getFreeCount.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                GetFreePermissionBeanData data;
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    GetFreePermissionBean getFreePermissionBean = (GetFreePermissionBean) new Gson().fromJson(json, GetFreePermissionBean.class);
                    if (!Intrinsics.areEqual("200", getFreePermissionBean.getCode()) || (data = getFreePermissionBean.getData()) == null) {
                        return;
                    }
                    ChooseSchoolMainActivity chooseSchoolMainActivity = ChooseSchoolMainActivity.this;
                    String free_times = data.getFree_times();
                    if (free_times == null || free_times.length() == 0) {
                        return;
                    }
                    String free_times2 = data.getFree_times();
                    Intrinsics.checkNotNull(free_times2);
                    if (Integer.parseInt(free_times2) > 0) {
                        chooseSchoolMainActivity.showGetFreeDialog(data.getFree_activity_id(), data.getType());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ViewPager2Indicator viewPager2Indicator = ChooseSchoolMainActivity.this.treeIndicator;
                    if (viewPager2Indicator == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                        viewPager2Indicator = null;
                    }
                    ViewExtensionsKt.gone(viewPager2Indicator);
                    ChooseSchoolMainActivity.this.setHave1V1Btn(false);
                }
            }
        });
    }

    private final void getFreePermission(String activityId, final boolean isOnRestart) {
        Context context = this.mContext;
        String str = NetworkRequestsURL.chooseSchoolGetPermissionFree;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, activityId);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.getFreePermission.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (isOnRestart) {
                    this.mActivityId = "";
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    if (Intrinsics.areEqual(new JSONObject(json).optString("code"), "200")) {
                        this.loadPermissionBtnInfo();
                    }
                    if (isOnRestart) {
                        this.mActivityId = "";
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ViewPager2Indicator viewPager2Indicator = this.treeIndicator;
                    if (viewPager2Indicator == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("treeIndicator");
                        viewPager2Indicator = null;
                    }
                    ViewExtensionsKt.gone(viewPager2Indicator);
                    this.setHave1V1Btn(false);
                }
            }
        });
    }

    public static /* synthetic */ void getFreePermission$default(ChooseSchoolMainActivity chooseSchoolMainActivity, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        chooseSchoolMainActivity.getFreePermission(str, z2);
    }

    private final int getIndex(String userScore, List<ChooseInfoScore> scoreCount) {
        int i2 = 0;
        int i3 = 1;
        if (!(userScore == null || userScore.length() == 0)) {
            List<ChooseInfoScore> list = scoreCount;
            if (!(list == null || list.isEmpty())) {
                float f2 = 2.1474836E9f;
                for (Object obj : scoreCount) {
                    int i4 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    ChooseInfoScore chooseInfoScore = (ChooseInfoScore) obj;
                    if (chooseInfoScore.getScore() != null) {
                        float fAbs = Math.abs(Float.parseFloat(userScore) - Float.parseFloat(chooseInfoScore.getScore()));
                        if (fAbs < f2) {
                            i3 = i2;
                            f2 = fAbs;
                        }
                    }
                    i2 = i4;
                }
                return i3;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BaseMultiItemQuickAdapter<NewHomeKingKongItem, BaseViewHolder> getKingKongAdapter(List<? extends NewHomeKingKongItem> data) {
        return new C05691(data, getResources().getDisplayMetrics().widthPixels / 5, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(Message message) {
        Intrinsics.checkNotNull(message);
        int i2 = message.what;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(TargetSchoolListActivity.newIntent(this$0));
    }

    private final void initKingKong() {
        loadKingKongArea();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initNotify(List<String> listData) {
        List<String> list = listData;
        RelativeLayout relativeLayout = null;
        if (list == null || list.isEmpty()) {
            RelativeLayout relativeLayout2 = this.layoutNotify;
            if (relativeLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutNotify");
            } else {
                relativeLayout = relativeLayout2;
            }
            ViewExtensionsKt.gone(relativeLayout);
            return;
        }
        RelativeLayout relativeLayout3 = this.layoutNotify;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutNotify");
            relativeLayout3 = null;
        }
        ViewExtensionsKt.visible(relativeLayout3);
        TextView textView = this.tvNotify;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvNotify");
            textView = null;
        }
        textView.setText(listData.get(0));
        RelativeLayout relativeLayout4 = this.layoutNotify;
        if (relativeLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutNotify");
        } else {
            relativeLayout = relativeLayout4;
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.initNotify$lambda$11(this.f11411c, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNotify$lambda$11(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ChooseSchoolQuestionActivity.INSTANCE.navigationToChooseSchoolQuestionActivity(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initPermissionBtn(final GetPermissionBeanData itemData) throws NumberFormatException {
        String str = "1";
        boolean zAreEqual = Intrinsics.areEqual("1", itemData.getPermission());
        LinearLayout linearLayout = this.layoutSchoolTitle;
        FlowLayout flowLayout = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSchoolTitle");
            linearLayout = null;
        }
        boolean z2 = false;
        linearLayout.setVisibility(zAreEqual ? 8 : 0);
        Log.d(this.TAG, "initPermissionBtn:11 ");
        TextView textView = this.tvPermissionTitle;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvPermissionTitle");
            textView = null;
        }
        textView.setText(itemData.getTitle());
        TextView textView2 = this.tvSubTitle;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSubTitle");
            textView2 = null;
        }
        textView2.setText(itemData.getSub_title());
        TextView textView3 = this.hint;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hint");
            textView3 = null;
        }
        textView3.setText(itemData.getTip());
        TextView textView4 = this.hint;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hint");
            textView4 = null;
        }
        TextView textView5 = this.hint;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hint");
            textView5 = null;
        }
        StringUtil.setTargetTextColor(textView4, textView5.getText().toString(), CollectionsKt__CollectionsKt.mutableListOf("*"), isNight() ? getResources().getColor(R.color.main_theme_color_night, null) : getResources().getColor(R.color.main_theme_color, null));
        if (zAreEqual) {
            TextView textView6 = this.tvBtnGetPermission;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvBtnGetPermission");
                textView6 = null;
            }
            ViewExtensionsKt.gone(textView6);
        } else {
            if (Intrinsics.areEqual(itemData.getFree_times_status(), "0")) {
                getFreeCount();
            }
            TextView textView7 = this.tvBtnGetPermission;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvBtnGetPermission");
                textView7 = null;
            }
            ViewExtensionsKt.visible(textView7);
            TextView textView8 = this.tvBtnGetPermission;
            if (textView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvBtnGetPermission");
                textView8 = null;
            }
            textView8.setText("获取权限");
            String free_times = itemData.getFree_times();
            if (!(free_times == null || free_times.length() == 0)) {
                String free_times2 = itemData.getFree_times();
                Intrinsics.checkNotNull(free_times2);
                int i2 = Integer.parseInt(free_times2);
                if (i2 > 0) {
                    TextView textView9 = this.tvBtnGetPermission;
                    if (textView9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvBtnGetPermission");
                        textView9 = null;
                    }
                    ViewExtensionsKt.visible(textView9);
                    String str2 = "您还有 " + i2 + "次免费查询次数";
                    TextView textView10 = this.tvBtnGetPermission;
                    if (textView10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvBtnGetPermission");
                        textView10 = null;
                    }
                    StringUtil.setTargetTextBold(textView10, str2, CollectionsKt__CollectionsKt.mutableListOf(String.valueOf(i2)));
                    z2 = true;
                }
            }
        }
        TextView textView11 = this.tvBtnGetPermission;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvBtnGetPermission");
            textView11 = null;
        }
        textView11.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.initPermissionBtn$lambda$13(this.f11254c, itemData, view);
            }
        });
        FlowLayout flowLayout2 = this.flowLayout;
        if (flowLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flowLayout");
            flowLayout2 = null;
        }
        flowLayout2.removeAllViews();
        FlowLayout flowLayout3 = this.flowLayout;
        if (flowLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flowLayout");
        } else {
            flowLayout = flowLayout3;
        }
        FlowLayoutUtil.initChildLabelViewsSource(flowLayout, itemData.getContent(), this);
        if (!zAreEqual && !z2) {
            str = "0";
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, str, this);
        String activity_id = itemData.getActivity_id();
        if (activity_id == null) {
            activity_id = "";
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_ACTIVITY_ID, activity_id, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPermissionBtn$lambda$13(ChooseSchoolMainActivity this$0, GetPermissionBeanData itemData, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(itemData, "$itemData");
        String activity_id = itemData.getActivity_id();
        Intrinsics.checkNotNull(activity_id);
        String type = itemData.getType();
        Intrinsics.checkNotNull(type);
        this$0.initPermissionList(activity_id, type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initPermissionList(String activityId, String type) {
        this.mActivityId = activityId;
        this.mType = type;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, activityId);
        ajaxParams.put("type", type);
        MemInterface.getInstance().setDisType(1);
        MemInterface memInterface = MemInterface.getInstance();
        Context context = this.mContext;
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
        memInterface.getMemData((Activity) context, ajaxParams, false, 0, NetworkRequestsURL.chooseSchoolGetPermissionList);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                ChooseSchoolMainActivity.initPermissionList$lambda$15(this.f11292a);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPermissionList$lambda$15(ChooseSchoolMainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getFreePermission(this$0.mActivityId, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void initScore(ChooseInfo data) {
        RelativeLayout relativeLayout = null;
        if (!Intrinsics.areEqual("1", data.is_open())) {
            RelativeLayout relativeLayout2 = this.layoutZx;
            if (relativeLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutZx");
            } else {
                relativeLayout = relativeLayout2;
            }
            ViewExtensionsKt.gone(relativeLayout);
            return;
        }
        RelativeLayout relativeLayout3 = this.layoutZx;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutZx");
            relativeLayout3 = null;
        }
        ViewExtensionsKt.visible(relativeLayout3);
        String user_score = data.getUser_score();
        int i2 = 0;
        this.isHaveScore = !(user_score == null || user_score.length() == 0) && Float.parseFloat(data.getUser_score()) > 0.0f;
        ArrayList arrayList = new ArrayList();
        for (Object obj : data.getScore_count()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            ChooseInfoScore chooseInfoScore = (ChooseInfoScore) obj;
            String count = chooseInfoScore.getCount();
            arrayList.add(Float.valueOf(count != null ? Float.parseFloat(count) : 0.0f));
            String user_score2 = data.getUser_score();
            Float fValueOf = user_score2 != null ? Float.valueOf(Float.parseFloat(user_score2)) : null;
            String score = chooseInfoScore.getScore();
            Intrinsics.areEqual(fValueOf, score != null ? Float.valueOf(Float.parseFloat(score)) : null);
            i2 = i3;
        }
        if (this.isHaveScore) {
            TextView textView = this.tv1;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tv1");
                textView = null;
            }
            textView.setText("总分");
            TextView textView2 = this.tv2;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tv2");
                textView2 = null;
            }
            ViewExtensionsKt.visible(textView2);
            TextView textView3 = this.tvScore;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvScore");
                textView3 = null;
            }
            ViewExtensionsKt.visible(textView3);
            TextView textView4 = this.tvScore;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvScore");
                textView4 = null;
            }
            textView4.setText(data.getUser_score());
        } else {
            TextView textView5 = this.tv1;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tv1");
                textView5 = null;
            }
            textView5.setText("未录入分值");
            TextView textView6 = this.tv2;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tv2");
                textView6 = null;
            }
            ViewExtensionsKt.invisible(textView6);
            TextView textView7 = this.tvScore;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvScore");
                textView7 = null;
            }
            ViewExtensionsKt.invisible(textView7);
        }
        int index = getIndex(data.getUser_score(), data.getScore_count());
        WaveChartViewNew waveChartViewNew = this.waveChart;
        if (waveChartViewNew == null) {
            Intrinsics.throwUninitializedPropertyAccessException("waveChart");
            waveChartViewNew = null;
        }
        waveChartViewNew.setData(arrayList);
        WaveChartViewNew waveChartViewNew2 = this.waveChart;
        if (waveChartViewNew2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("waveChart");
            waveChartViewNew2 = null;
        }
        waveChartViewNew2.setPointIndex(index);
        if (this.isHaveScore) {
            ImageView imageView = this.imgQuestionMark1;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQuestionMark1");
                imageView = null;
            }
            ViewExtensionsKt.gone(imageView);
            ImageView imageView2 = this.imgQuestionMark2;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQuestionMark2");
                imageView2 = null;
            }
            ViewExtensionsKt.gone(imageView2);
            ImageView imageView3 = this.imgQuestionMark3;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQuestionMark3");
                imageView3 = null;
            }
            ViewExtensionsKt.gone(imageView3);
            TextView textView8 = this.tvCountBaoDi;
            if (textView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountBaoDi");
                textView8 = null;
            }
            ViewExtensionsKt.visible(textView8);
            TextView textView9 = this.tvCountWenTuo;
            if (textView9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountWenTuo");
                textView9 = null;
            }
            ViewExtensionsKt.visible(textView9);
            TextView textView10 = this.tvCountChongJi;
            if (textView10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountChongJi");
                textView10 = null;
            }
            ViewExtensionsKt.visible(textView10);
            for (ChooseInfoSchoolInfo chooseInfoSchoolInfo : data.getChoose_school()) {
                String type = chooseInfoSchoolInfo.getType();
                if (type != null) {
                    switch (type.hashCode()) {
                        case 49:
                            if (type.equals("1")) {
                                this.nanCount = chooseInfoSchoolInfo.getCount();
                                break;
                            } else {
                                break;
                            }
                        case 50:
                            if (type.equals("2")) {
                                TextView textView11 = this.tvCountChongJi;
                                if (textView11 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("tvCountChongJi");
                                    textView11 = null;
                                }
                                textView11.setText(chooseInfoSchoolInfo.getCount());
                                this.congCount = chooseInfoSchoolInfo.getCount();
                                break;
                            } else {
                                break;
                            }
                        case 51:
                            if (type.equals("3")) {
                                TextView textView12 = this.tvCountWenTuo;
                                if (textView12 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("tvCountWenTuo");
                                    textView12 = null;
                                }
                                textView12.setText(chooseInfoSchoolInfo.getCount());
                                this.wenCount = chooseInfoSchoolInfo.getCount();
                                break;
                            } else {
                                break;
                            }
                        case 52:
                            if (type.equals("4")) {
                                TextView textView13 = this.tvCountBaoDi;
                                if (textView13 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("tvCountBaoDi");
                                    textView13 = null;
                                }
                                textView13.setText(chooseInfoSchoolInfo.getCount());
                                this.baoCount = chooseInfoSchoolInfo.getCount();
                                break;
                            } else {
                                break;
                            }
                    }
                }
            }
            this.major_id = data.getMajor_id();
            this.user_score = data.getUser_score();
            LinearLayout linearLayout = this.layoutScoreLine;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutScoreLine");
                linearLayout = null;
            }
            ViewExtensionsKt.visible(linearLayout);
            View view = this.lineGap;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lineGap");
                view = null;
            }
            ViewExtensionsKt.visible(view);
        } else {
            ImageView imageView4 = this.imgQuestionMark1;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQuestionMark1");
                imageView4 = null;
            }
            ViewExtensionsKt.visible(imageView4);
            ImageView imageView5 = this.imgQuestionMark2;
            if (imageView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQuestionMark2");
                imageView5 = null;
            }
            ViewExtensionsKt.visible(imageView5);
            ImageView imageView6 = this.imgQuestionMark3;
            if (imageView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgQuestionMark3");
                imageView6 = null;
            }
            ViewExtensionsKt.visible(imageView6);
            TextView textView14 = this.tvCountBaoDi;
            if (textView14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountBaoDi");
                textView14 = null;
            }
            ViewExtensionsKt.gone(textView14);
            TextView textView15 = this.tvCountWenTuo;
            if (textView15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountWenTuo");
                textView15 = null;
            }
            ViewExtensionsKt.gone(textView15);
            TextView textView16 = this.tvCountChongJi;
            if (textView16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountChongJi");
                textView16 = null;
            }
            ViewExtensionsKt.gone(textView16);
            LinearLayout linearLayout2 = this.layoutScoreLine;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutScoreLine");
                linearLayout2 = null;
            }
            ViewExtensionsKt.gone(linearLayout2);
            View view2 = this.lineGap;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lineGap");
                view2 = null;
            }
            ViewExtensionsKt.gone(view2);
        }
        RelativeLayout relativeLayout4 = this.layoutBaoDi;
        if (relativeLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutBaoDi");
            relativeLayout4 = null;
        }
        relativeLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChooseSchoolMainActivity.initScore$lambda$20(this.f11301c, view3);
            }
        });
        RelativeLayout relativeLayout5 = this.layoutWenTuo;
        if (relativeLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutWenTuo");
            relativeLayout5 = null;
        }
        relativeLayout5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChooseSchoolMainActivity.initScore$lambda$21(this.f11311c, view3);
            }
        });
        RelativeLayout relativeLayout6 = this.layoutChongJi;
        if (relativeLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutChongJi");
            relativeLayout6 = null;
        }
        relativeLayout6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChooseSchoolMainActivity.initScore$lambda$22(this.f11325c, view3);
            }
        });
        RelativeLayout relativeLayout7 = this.layoutBottom1V1;
        if (relativeLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutBottom1V1");
        } else {
            relativeLayout = relativeLayout7;
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ChooseSchoolMainActivity.initScore$lambda$23(this.f11334c, view3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initScore$lambda$20(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isHaveScore) {
            this$0.navigateToChooseSchool(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initScore$lambda$21(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isHaveScore) {
            this$0.navigateToChooseSchool(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initScore$lambda$22(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isHaveScore) {
            this$0.navigateToChooseSchool(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initScore$lambda$23(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.str1v1Success) {
            CommonUtil.jump1v1(this$0, this$0.str1v1QrCode, this$0.str1v1QrId);
        } else {
            this$0.load1v1QrCode();
        }
    }

    private final void initWxInfo() {
        this.mAdapter = new WxInfoAdapter();
        View viewFindViewById = findViewById(R.id.recyclerViewZx);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.recyclerViewZx)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        this.recyclerViewZx = recyclerView;
        WxInfoAdapter wxInfoAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewZx");
            recyclerView = null;
        }
        WxInfoAdapter wxInfoAdapter2 = this.mAdapter;
        if (wxInfoAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            wxInfoAdapter = wxInfoAdapter2;
        }
        recyclerView.setAdapter(wxInfoAdapter);
        loadWXInfoData();
    }

    private final boolean isNight() {
        return SkinManager.getCurrentSkinType(this.mContext) == 1;
    }

    private final void load1v1QrCode() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchool1V1, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.load1v1QrCode.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                JSONObject jSONObjectOptJSONObject;
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual(jSONObject.optString("code"), "200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    ChooseSchoolMainActivity chooseSchoolMainActivity = ChooseSchoolMainActivity.this;
                    chooseSchoolMainActivity.str1v1Success = true;
                    String strOptString = jSONObjectOptJSONObject.optString("qr_code");
                    Intrinsics.checkNotNullExpressionValue(strOptString, "it.optString(\"qr_code\")");
                    chooseSchoolMainActivity.str1v1QrCode = strOptString;
                    String strOptString2 = jSONObjectOptJSONObject.optString("we_chat_id");
                    Intrinsics.checkNotNullExpressionValue(strOptString2, "it.optString(\"we_chat_id\")");
                    chooseSchoolMainActivity.str1v1QrId = strOptString2;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void loadChooseInfo() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolChooseInfo, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.loadChooseInfo.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        ChooseInfo data = (ChooseInfo) new Gson().fromJson(jSONObject.optString("data"), ChooseInfo.class);
                        ChooseSchoolMainActivity chooseSchoolMainActivity = ChooseSchoolMainActivity.this;
                        Intrinsics.checkNotNullExpressionValue(data, "data");
                        chooseSchoolMainActivity.initScore(data);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void loadKingKongArea() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolKingKongArea, new AjaxParams(), new C05721());
    }

    private final void loadKyCalendar() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "-1");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolCalendar, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.loadKyCalendar.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C05731) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                        RelativeLayout relativeLayout = ChooseSchoolMainActivity.this.layoutKyCalendar;
                        RecyclerView recyclerView = null;
                        if (relativeLayout == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutKyCalendar");
                            relativeLayout = null;
                        }
                        int i2 = 0;
                        relativeLayout.setVisibility((jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() == 0) ? 8 : 0);
                        if (jSONArrayOptJSONArray != null) {
                            List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends ChooseSchoolCalendarListItemBean>>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$loadKyCalendar$1$onSuccess$dataBean$1
                            }.getType());
                            Iterator it = list.iterator();
                            int i3 = 0;
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                int i4 = i3 + 1;
                                if (Intrinsics.areEqual(((ChooseSchoolCalendarListItemBean) it.next()).getTimeline_status(), "1")) {
                                    i2 = i3;
                                    break;
                                }
                                i3 = i4;
                            }
                            ChooseSchoolProgressAdapter chooseSchoolProgressAdapter = ChooseSchoolMainActivity.this.mAdapterKy;
                            if (chooseSchoolProgressAdapter == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mAdapterKy");
                                chooseSchoolProgressAdapter = null;
                            }
                            chooseSchoolProgressAdapter.setList(list);
                            RecyclerView recyclerView2 = ChooseSchoolMainActivity.this.recyclerViewKy;
                            if (recyclerView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("recyclerViewKy");
                            } else {
                                recyclerView = recyclerView2;
                            }
                            recyclerView.scrollToPosition(i2);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadPermissionBtnInfo() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchoolPermissionBtn, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.loadPermissionBtnInfo.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    GetPermissionBean getPermissionBean = (GetPermissionBean) new Gson().fromJson(json, GetPermissionBean.class);
                    if (!Intrinsics.areEqual(getPermissionBean.getCode(), "200") || getPermissionBean.getData() == null) {
                        return;
                    }
                    ChooseSchoolMainActivity chooseSchoolMainActivity = ChooseSchoolMainActivity.this;
                    GetPermissionBeanData data = getPermissionBean.getData();
                    Intrinsics.checkNotNull(data);
                    chooseSchoolMainActivity.initPermissionBtn(data);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadTargetSchool() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.chooseSchoolTargetSchool;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("limit", "3");
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.loadTargetSchool.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                TextView textView = null;
                try {
                    ChooseSchoolTargetSchoolBean chooseSchoolTargetSchoolBean = (ChooseSchoolTargetSchoolBean) new Gson().fromJson(json, ChooseSchoolTargetSchoolBean.class);
                    if (Intrinsics.areEqual(chooseSchoolTargetSchoolBean.getCode(), "200")) {
                        List<ChooseSchoolTargetSchoolBeanItem> data = chooseSchoolTargetSchoolBean.getData();
                        List<ChooseSchoolTargetSchoolBeanItem> list = data;
                        if (list == null || list.isEmpty()) {
                            RecyclerView recyclerView = ChooseSchoolMainActivity.this.recyclerViewTargetSchool;
                            if (recyclerView == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("recyclerViewTargetSchool");
                                recyclerView = null;
                            }
                            ViewExtensionsKt.gone(recyclerView);
                            TextView textView2 = ChooseSchoolMainActivity.this.tvTargetSchoolMore;
                            if (textView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvTargetSchoolMore");
                                textView2 = null;
                            }
                            ViewExtensionsKt.gone(textView2);
                            TextView textView3 = ChooseSchoolMainActivity.this.tvAddTargetSchool;
                            if (textView3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("tvAddTargetSchool");
                                textView3 = null;
                            }
                            ViewExtensionsKt.visible(textView3);
                            return;
                        }
                        TargetSchoolAdapter targetSchoolAdapter = ChooseSchoolMainActivity.this.targetSchoolAdapter;
                        if (targetSchoolAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("targetSchoolAdapter");
                            targetSchoolAdapter = null;
                        }
                        targetSchoolAdapter.setList(data);
                        RecyclerView recyclerView2 = ChooseSchoolMainActivity.this.recyclerViewTargetSchool;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewTargetSchool");
                            recyclerView2 = null;
                        }
                        ViewExtensionsKt.visible(recyclerView2);
                        TextView textView4 = ChooseSchoolMainActivity.this.tvTargetSchoolMore;
                        if (textView4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvTargetSchoolMore");
                            textView4 = null;
                        }
                        ViewExtensionsKt.visible(textView4);
                        TextView textView5 = ChooseSchoolMainActivity.this.tvAddTargetSchool;
                        if (textView5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvAddTargetSchool");
                            textView5 = null;
                        }
                        textView5.setVisibility(data.size() >= 3 ? 8 : 0);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    RecyclerView recyclerView3 = ChooseSchoolMainActivity.this.recyclerViewTargetSchool;
                    if (recyclerView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("recyclerViewTargetSchool");
                        recyclerView3 = null;
                    }
                    ViewExtensionsKt.gone(recyclerView3);
                    TextView textView6 = ChooseSchoolMainActivity.this.tvTargetSchoolMore;
                    if (textView6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvTargetSchoolMore");
                    } else {
                        textView = textView6;
                    }
                    ViewExtensionsKt.gone(textView);
                }
            }
        });
    }

    private final void loadTopDataInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "-1");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolTopSchoolInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.loadTopDataInfo.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C05761) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        ChooseSchoolTopInfo chooseSchoolTopInfo = (ChooseSchoolTopInfo) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolTopInfo.class);
                        TextView textView = ChooseSchoolMainActivity.this.tvZYCount;
                        TextView textView2 = null;
                        if (textView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvZYCount");
                            textView = null;
                        }
                        textView.setText("覆盖" + chooseSchoolTopInfo.getMajor_num() + "个专业");
                        TextView textView3 = ChooseSchoolMainActivity.this.tvSchoolCount;
                        if (textView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvSchoolCount");
                        } else {
                            textView2 = textView3;
                        }
                        textView2.setText("覆盖" + chooseSchoolTopInfo.getSchool_num() + "所院校");
                        String notice = chooseSchoolTopInfo.getNotice();
                        if (notice == null || notice.length() == 0) {
                            return;
                        }
                        ChooseSchoolMainActivity.this.initNotify(CollectionsKt__CollectionsKt.mutableListOf(chooseSchoolTopInfo.getNotice()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void loadWXInfoData() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.newHomeInfo;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("page_size", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("type", "2");
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.loadWXInfoData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        List dataList = (List) new Gson().fromJson(jSONObject.optJSONObject("data").optString("list"), new TypeToken<List<? extends HomeInfoItemBean>>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$loadWXInfoData$2$onSuccess$dataList$1
                        }.getType());
                        List list = dataList;
                        ChooseSchoolMainActivity.this.haveMoreWxInfo = !(list == null || list.isEmpty()) && dataList.size() == 10;
                        List list2 = dataList;
                        if (list2 == null || list2.isEmpty()) {
                            return;
                        }
                        WxInfoAdapter wxInfoAdapter = ChooseSchoolMainActivity.this.mAdapter;
                        WxInfoAdapter wxInfoAdapter2 = null;
                        if (wxInfoAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            wxInfoAdapter = null;
                        }
                        if (wxInfoAdapter.getData().isEmpty()) {
                            WxInfoAdapter wxInfoAdapter3 = ChooseSchoolMainActivity.this.mAdapter;
                            if (wxInfoAdapter3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            } else {
                                wxInfoAdapter2 = wxInfoAdapter3;
                            }
                            wxInfoAdapter2.setList(dataList);
                            return;
                        }
                        WxInfoAdapter wxInfoAdapter4 = ChooseSchoolMainActivity.this.mAdapter;
                        if (wxInfoAdapter4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        } else {
                            wxInfoAdapter2 = wxInfoAdapter4;
                        }
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        wxInfoAdapter2.addData((Collection) dataList);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void navigateToChooseSchool(int index) {
        ChooseSchoolListActivity.INSTANCE.navigationToChooseSchoolActivity(this, this.baoCount, this.wenCount, this.congCount, this.nanCount, this.major_id, this.user_score, index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setHave1V1Btn(boolean have1v1) {
        RelativeLayout relativeLayout = null;
        if (have1v1) {
            RelativeLayout relativeLayout2 = this.layoutBottom1V1;
            if (relativeLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutBottom1V1");
            } else {
                relativeLayout = relativeLayout2;
            }
            ViewExtensionsKt.visible(relativeLayout);
            SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_1v1, "1", this);
            return;
        }
        RelativeLayout relativeLayout3 = this.layoutBottom1V1;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutBottom1V1");
        } else {
            relativeLayout = relativeLayout3;
        }
        ViewExtensionsKt.gone(relativeLayout);
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_1v1, "0", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$10(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NavigationUtilKt.gotoSearchTargetSchoolActivity(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$2(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(ChooseSchoolMainActivity this$0, View view, int i2, int i3, int i4, int i5) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextView textView = null;
        if (i3 < this$0.SCROLL_BEGIN) {
            TextView textView2 = this$0.tvActionBarBg;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvActionBarBg");
                textView2 = null;
            }
            textView2.setAlpha(0.0f);
            TextView textView3 = this$0.tvTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            } else {
                textView = textView3;
            }
            textView.setAlpha(0.0f);
            Log.d("TEST", "---------设置透明度为0f");
            return;
        }
        if (i3 <= this$0.SCROLL_END) {
            float f2 = (i3 - r3) / (r1 - r3);
            TextView textView4 = this$0.tvActionBarBg;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvActionBarBg");
            } else {
                textView = textView4;
            }
            textView.setAlpha(f2);
            Log.d("TEST", "---------设置透明度为:" + f2);
            return;
        }
        TextView textView5 = this$0.tvActionBarBg;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvActionBarBg");
            textView5 = null;
        }
        textView5.setAlpha(1.0f);
        TextView textView6 = this$0.tvTitle;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
        } else {
            textView = textView6;
        }
        textView.setAlpha(1.0f);
        Log.d("TEST", "---------设置透明度为1f");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$4(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        AliYunLogUtil.getInstance().addLog(AliyunEvent.CheckUniversities);
        this$0.startActivity(SchoolListActivity.newIntent(this$0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$5(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        PostgraduateCalendarActivity.INSTANCE.goToPostgraduateCalendarActivity(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$6(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        HomeInfoListAct.INSTANCE.navigationToHomeInfoListActivity(this$0, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$7(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        HomeInfoListAct.INSTANCE.navigationToHomeInfoListActivity(this$0, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$8(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        AliYunLogUtil.getInstance().addLog(AliyunEvent.CheckMajor);
        this$0.startActivity(MajorListActivity.newIntent(this$0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$9(ChooseSchoolMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        AliYunLogUtil.getInstance().addLog(AliyunEvent.SelectSchool);
        ChooseSchoolEditMsgActivity.INSTANCE.navigationToChooseSchoolActivity(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showGetFreeDialog(final String activityId, final String type) {
        if (!(activityId == null || activityId.length() == 0) && this.firstDialog) {
            this.firstDialog = false;
            XPopup.Builder builder = new XPopup.Builder(this);
            Boolean bool = Boolean.FALSE;
            Intrinsics.checkNotNullExpressionValue(builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$showGetFreeDialog$popupView$1
                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onDismiss(@NotNull BasePopupView popupView) {
                    Intrinsics.checkNotNullParameter(popupView, "popupView");
                    super.onDismiss(popupView);
                    ProjectApp.instance.dismissPop(popupView);
                }
            }).asCustom(new GetFreePopupView(this, new Function0<Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity$showGetFreeDialog$popupView$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Log.d(this.this$0.TAG, "showGetFreeDialog: ");
                    if (Intrinsics.areEqual("0", activityId)) {
                        ChooseSchoolMainActivity.getFreePermission$default(this.this$0, activityId, false, 2, null);
                        return;
                    }
                    ChooseSchoolMainActivity chooseSchoolMainActivity = this.this$0;
                    String str = activityId;
                    String str2 = type;
                    Intrinsics.checkNotNull(str2);
                    chooseSchoolMainActivity.initPermissionList(str, str2);
                }
            })).show(), "private fun showGetFreeD…          }).show()\n    }");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.handler = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.chooseSchool.d0
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                ChooseSchoolMainActivity.init$lambda$0(message);
            }
        });
        View viewFindViewById = findViewById(R.id.tvTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tvTitle)");
        this.tvTitle = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.layoutSchoolTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.layoutSchoolTitle)");
        this.layoutSchoolTitle = (LinearLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.layoutKyCalendar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.layoutKyCalendar)");
        this.layoutKyCalendar = (RelativeLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.lineGap);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.lineGap)");
        this.lineGap = viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.layoutScoreLine);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.layoutScoreLine)");
        this.layoutScoreLine = (LinearLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.tvNotify);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.tvNotify)");
        this.tvNotify = (TextView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.layoutNotify);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.layoutNotify)");
        this.layoutNotify = (RelativeLayout) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.tvZYCount);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.tvZYCount)");
        this.tvZYCount = (TextView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.tvSchoolCount);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.tvSchoolCount)");
        this.tvSchoolCount = (TextView) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.tvScore);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.tvScore)");
        this.tvScore = (TextView) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.tv1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.tv1)");
        this.tv1 = (TextView) viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.tv2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.tv2)");
        this.tv2 = (TextView) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.tvCountBaoDi);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.tvCountBaoDi)");
        this.tvCountBaoDi = (TextView) viewFindViewById13;
        View viewFindViewById14 = findViewById(R.id.imgQuestionMark1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "findViewById(R.id.imgQuestionMark1)");
        this.imgQuestionMark1 = (ImageView) viewFindViewById14;
        View viewFindViewById15 = findViewById(R.id.tvCountWenTuo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById15, "findViewById(R.id.tvCountWenTuo)");
        this.tvCountWenTuo = (TextView) viewFindViewById15;
        View viewFindViewById16 = findViewById(R.id.imgQuestionMark2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById16, "findViewById(R.id.imgQuestionMark2)");
        this.imgQuestionMark2 = (ImageView) viewFindViewById16;
        View viewFindViewById17 = findViewById(R.id.tvCountChongJi);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById17, "findViewById(R.id.tvCountChongJi)");
        this.tvCountChongJi = (TextView) viewFindViewById17;
        View viewFindViewById18 = findViewById(R.id.imgQuestionMark3);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById18, "findViewById(R.id.imgQuestionMark3)");
        this.imgQuestionMark3 = (ImageView) viewFindViewById18;
        View viewFindViewById19 = findViewById(R.id.layoutBaoDi);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById19, "findViewById(R.id.layoutBaoDi)");
        this.layoutBaoDi = (RelativeLayout) viewFindViewById19;
        View viewFindViewById20 = findViewById(R.id.layoutWenTuo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById20, "findViewById(R.id.layoutWenTuo)");
        this.layoutWenTuo = (RelativeLayout) viewFindViewById20;
        View viewFindViewById21 = findViewById(R.id.layoutChongJi);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById21, "findViewById(R.id.layoutChongJi)");
        this.layoutChongJi = (RelativeLayout) viewFindViewById21;
        View viewFindViewById22 = findViewById(R.id.tvSubTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById22, "findViewById(R.id.tvSubTitle)");
        this.tvSubTitle = (TextView) viewFindViewById22;
        View viewFindViewById23 = findViewById(R.id.layoutZx);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById23, "findViewById(R.id.layoutZx)");
        this.layoutZx = (RelativeLayout) viewFindViewById23;
        View viewFindViewById24 = findViewById(R.id.layoutEditScore);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById24, "findViewById(R.id.layoutEditScore)");
        this.layoutEditScore = (RelativeLayout) viewFindViewById24;
        View viewFindViewById25 = findViewById(R.id.tvBtnGetPermission);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById25, "findViewById(R.id.tvBtnGetPermission)");
        this.tvBtnGetPermission = (TextView) viewFindViewById25;
        View viewFindViewById26 = findViewById(R.id.tvPermissionTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById26, "findViewById(R.id.tvPermissionTitle)");
        this.tvPermissionTitle = (TextView) viewFindViewById26;
        View viewFindViewById27 = findViewById(R.id.tvTargetSchoolMore);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById27, "findViewById(R.id.tvTargetSchoolMore)");
        TextView textView = (TextView) viewFindViewById27;
        this.tvTargetSchoolMore = textView;
        ChooseSchoolProgressAdapter chooseSchoolProgressAdapter = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTargetSchoolMore");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.init$lambda$1(this.f11245c, view);
            }
        });
        View viewFindViewById28 = findViewById(R.id.recyclerViewTargetSchool);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById28, "findViewById(R.id.recyclerViewTargetSchool)");
        this.recyclerViewTargetSchool = (RecyclerView) viewFindViewById28;
        TargetSchoolAdapter targetSchoolAdapter = new TargetSchoolAdapter();
        this.targetSchoolAdapter = targetSchoolAdapter;
        targetSchoolAdapter.setItemClick(new Function3<String, String, String, Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.init.3
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3) {
                invoke2(str, str2, str3);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String schoolId, @NotNull String type, @NotNull String schoolName) {
                Intrinsics.checkNotNullParameter(schoolId, "schoolId");
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(schoolName, "schoolName");
                if (Intrinsics.areEqual(type, "1")) {
                    SchoolDetailsAct.newIntent(ChooseSchoolMainActivity.this, schoolId);
                } else if (Intrinsics.areEqual(type, "2")) {
                    ChooseSchoolMainActivity.this.delTargetSchool(schoolId, schoolName);
                }
            }
        });
        RecyclerView recyclerView = this.recyclerViewTargetSchool;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewTargetSchool");
            recyclerView = null;
        }
        TargetSchoolAdapter targetSchoolAdapter2 = this.targetSchoolAdapter;
        if (targetSchoolAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("targetSchoolAdapter");
            targetSchoolAdapter2 = null;
        }
        recyclerView.setAdapter(targetSchoolAdapter2);
        View viewFindViewById29 = findViewById(R.id.tvAddTargetSchool);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById29, "findViewById(R.id.tvAddTargetSchool)");
        this.tvAddTargetSchool = (TextView) viewFindViewById29;
        View viewFindViewById30 = findViewById(R.id.layoutBottom1V1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById30, "findViewById(R.id.layoutBottom1V1)");
        this.layoutBottom1V1 = (RelativeLayout) viewFindViewById30;
        View viewFindViewById31 = findViewById(R.id.hint);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById31, "findViewById(R.id.hint)");
        this.hint = (TextView) viewFindViewById31;
        View viewFindViewById32 = findViewById(R.id.waveChart);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById32, "findViewById(R.id.waveChart)");
        this.waveChart = (WaveChartViewNew) viewFindViewById32;
        View viewFindViewById33 = findViewById(R.id.flowLayout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById33, "findViewById(R.id.flowLayout)");
        this.flowLayout = (FlowLayout) viewFindViewById33;
        View viewFindViewById34 = findViewById(R.id.vpKingKong);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById34, "findViewById(R.id.vpKingKong)");
        this.vpKingKong = (ViewPager2) viewFindViewById34;
        View viewFindViewById35 = findViewById(R.id.tree_indicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById35, "findViewById(R.id.tree_indicator)");
        this.treeIndicator = (ViewPager2Indicator) viewFindViewById35;
        View viewFindViewById36 = findViewById(R.id.tvActionBarBg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById36, "findViewById(R.id.tvActionBarBg)");
        this.tvActionBarBg = (TextView) viewFindViewById36;
        View viewFindViewById37 = findViewById(R.id.chooseSchoolScrollView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById37, "findViewById(R.id.chooseSchoolScrollView)");
        this.chooseSchoolScrollView = (NestedScrollView) viewFindViewById37;
        View viewFindViewById38 = findViewById(R.id.imgBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById38, "findViewById(R.id.imgBack)");
        this.imgBack = (ImageView) viewFindViewById38;
        View viewFindViewById39 = findViewById(R.id.viewPager2Notify);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById39, "findViewById(R.id.viewPager2Notify)");
        ViewPager2 viewPager2 = (ViewPager2) viewFindViewById39;
        this.viewPager2Notify = viewPager2;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager2Notify");
            viewPager2 = null;
        }
        this.viewPage2NotifyHelp = new ViewPager2SlowScrollHelper(viewPager2, 1000L);
        this.mAdapterKy = new ChooseSchoolProgressAdapter();
        View viewFindViewById40 = findViewById(R.id.recyclerViewKy);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById40, "findViewById(R.id.recyclerViewKy)");
        RecyclerView recyclerView2 = (RecyclerView) viewFindViewById40;
        this.recyclerViewKy = recyclerView2;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerViewKy");
            recyclerView2 = null;
        }
        ChooseSchoolProgressAdapter chooseSchoolProgressAdapter2 = this.mAdapterKy;
        if (chooseSchoolProgressAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapterKy");
            chooseSchoolProgressAdapter2 = null;
        }
        recyclerView2.setAdapter(chooseSchoolProgressAdapter2);
        ChooseSchoolProgressAdapter chooseSchoolProgressAdapter3 = this.mAdapterKy;
        if (chooseSchoolProgressAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapterKy");
        } else {
            chooseSchoolProgressAdapter = chooseSchoolProgressAdapter3;
        }
        chooseSchoolProgressAdapter.setTaskClick(new Function1<String, Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolMainActivity.init.4
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        });
        initWxInfo();
        initKingKong();
        loadTargetSchool();
        loadPermissionBtnInfo();
        loadChooseInfo();
        loadKyCalendar();
        loadTopDataInfo();
        load1v1QrCode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
        if (Intrinsics.areEqual(EventBusConstant.EVENT_CHOOSE_SCHOOL_SAVE_SCORE, str)) {
            loadChooseInfo();
        }
        if (Intrinsics.areEqual(EventBusConstant.EVENT_ADD_TARGET_SCHOOL_SUCCESS, str) || Intrinsics.areEqual(EventBusConstant.EVENT_DEL_TARGET_SCHOOL_SUCCESS, str)) {
            loadTargetSchool();
        }
        if (Intrinsics.areEqual(EventBusConstant.EVENT_CHOOSE_SCHOOL_PERMISSION_SHARE, str)) {
            if ((this.mActivityId.length() > 0) && !Intrinsics.areEqual("0", this.mActivityId) && Intrinsics.areEqual("1", this.mType)) {
                getFreePermission(this.mActivityId, true);
            }
        }
        if (Intrinsics.areEqual("BuySuccess", str)) {
            if ((this.mActivityId.length() > 0) && Intrinsics.areEqual("1", this.mType)) {
                getFreePermission(this.mActivityId, true);
            }
        }
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        Log.d(this.TAG, "onRestart: ---");
        loadPermissionBtnInfo();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentFillFromStatusBar(false);
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_choose_school_main);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ImageView imageView = this.imgBack;
        TextView textView = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$2(this.f11226c, view);
            }
        });
        NestedScrollView nestedScrollView = this.chooseSchoolScrollView;
        if (nestedScrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("chooseSchoolScrollView");
            nestedScrollView = null;
        }
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l0
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$3(this.f11342c, view, i2, i3, i4, i5);
            }
        });
        ((RelativeLayout) findViewById(R.id.layoutChooseSchool)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$4(this.f11350c, view);
            }
        });
        ((TextView) findViewById(R.id.tvKyCalendarNameMore)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$5(this.f11358c, view);
            }
        });
        ((TextView) findViewById(R.id.tvWxInfoNameMore)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$6(this.f11369c, view);
            }
        });
        ((TextView) findViewById(R.id.tvKyZXMore)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$7(this.f11377c, view);
            }
        });
        ((RelativeLayout) findViewById(R.id.layoutMajor)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$8(this.f11385c, view);
            }
        });
        RelativeLayout relativeLayout = this.layoutEditScore;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutEditScore");
            relativeLayout = null;
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$9(this.f11393c, view);
            }
        });
        TextView textView2 = this.tvAddTargetSchool;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvAddTargetSchool");
        } else {
            textView = textView2;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolMainActivity.setListenerForWidget$lambda$10(this.f11401c, view);
            }
        });
    }
}
