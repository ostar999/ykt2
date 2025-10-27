package com.psychiatrygarden.activity.knowledge;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.necer.calendar.NCalendar;
import com.necer.enumeration.CalendarState;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;
import com.necer.painter.CalendarPainter;
import com.necer.painter.InnerPainter;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CustomStudyPlanActivity;
import com.psychiatrygarden.activity.knowledge.DailyTasksActivity;
import com.psychiatrygarden.bean.DailyDateData;
import com.psychiatrygarden.bean.DailyTaskBean;
import com.psychiatrygarden.bean.DailyTaskCalendarDataBean;
import com.psychiatrygarden.bean.DailyTaskCalendarPlanBean;
import com.psychiatrygarden.bean.DateTabBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.bean.MoreContentCache;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.bean.StatisticsData;
import com.psychiatrygarden.bean.StudyPanUserDetail;
import com.psychiatrygarden.bean.StudyPlanListBean;
import com.psychiatrygarden.config.DailyTaskTimeConfig;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.DailyTaskShowStatisticsEvent;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.DailyTaskPop;
import com.psychiatrygarden.widget.DailyTaskSettingPopupWindow;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.HeaderScrollView;
import com.psychiatrygarden.widget.QuestionStatisticsPop;
import com.psychiatrygarden.widget.ReadMoreIcon;
import com.psychiatrygarden.widget.ScoreTrendInfoDialog;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0018\u0018\u0000 y2\u00020\u0001:\u0001yB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u00108\u001a\u00020/2\u0006\u00109\u001a\u00020\n2\u0006\u0010:\u001a\u00020\nH\u0002J\u0010\u0010;\u001a\u00020/2\u0006\u00109\u001a\u00020\nH\u0002J\b\u0010<\u001a\u00020\nH\u0002J\u0012\u0010=\u001a\u0004\u0018\u00010\n2\u0006\u0010>\u001a\u00020\nH\u0002J\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@2\u0006\u0010B\u001a\u00020\nJ\b\u0010C\u001a\u00020DH\u0002J\b\u0010E\u001a\u00020DH\u0002J\u0012\u0010F\u001a\u00020D2\b\b\u0002\u0010G\u001a\u00020+H\u0002J\u001a\u0010H\u001a\u00020D2\u0006\u0010I\u001a\u00020\n2\b\b\u0002\u0010G\u001a\u00020+H\u0002J\b\u0010J\u001a\u00020DH\u0016J\b\u0010K\u001a\u00020DH\u0002J\u0012\u0010L\u001a\u00020D2\b\b\u0002\u0010M\u001a\u00020+H\u0002J\u0010\u0010N\u001a\u00020D2\u0006\u0010O\u001a\u00020\nH\u0002J\b\u0010P\u001a\u00020DH\u0002J\b\u0010Q\u001a\u00020DH\u0002J\u0010\u0010R\u001a\u00020D2\u0006\u0010S\u001a\u00020(H\u0002J\b\u0010T\u001a\u00020DH\u0016J\b\u0010U\u001a\u00020DH\u0002J\b\u0010V\u001a\u00020DH\u0002J\b\u0010W\u001a\u00020+H\u0002J\u0006\u0010X\u001a\u00020+J\b\u0010Y\u001a\u00020DH\u0016J\u0014\u0010Z\u001a\u00020D2\n\u0010[\u001a\u0006\u0012\u0002\b\u00030\\H\u0007J\u0010\u0010Z\u001a\u00020D2\u0006\u0010]\u001a\u00020^H\u0007J\u0012\u0010Z\u001a\u00020D2\b\u0010_\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010`\u001a\u00020D2\b\u0010a\u001a\u0004\u0018\u00010bH\u0014J\u0006\u0010c\u001a\u00020DJ\b\u0010d\u001a\u00020DH\u0016J\b\u0010e\u001a\u00020DH\u0016JH\u0010f\u001a\u00020D2\u000e\u0010g\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0@2\u000e\u0010h\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0@2\u000e\u0010i\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0@2\u000e\u0010j\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0@H\u0002J\b\u0010k\u001a\u00020DH\u0002J\b\u0010l\u001a\u00020DH\u0002J\b\u0010m\u001a\u00020DH\u0002J\u0018\u0010n\u001a\u00020D2\u0006\u0010o\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010p\u001a\u00020DH\u0002J\u0010\u0010q\u001a\u00020D2\u0006\u0010_\u001a\u00020\nH\u0002J\u0018\u0010r\u001a\u00020D2\u0006\u0010s\u001a\u00020+2\u0006\u0010t\u001a\u00020\nH\u0002J\b\u0010u\u001a\u00020DH\u0002J\u0018\u0010v\u001a\u00020D2\u0006\u0010w\u001a\u00020\n2\u0006\u0010x\u001a\u00020+H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R2\u0010\b\u001a&\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n \u000b*\u0012\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0018\u00010\t0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00160\tj\b\u0012\u0004\u0012\u00020\u0016`\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010!\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020(X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082.¢\u0006\u0002\n\u0000¨\u0006z"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/DailyTasksActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "actionbarBack", "Landroid/widget/ImageView;", "actionbarMore", "calendar", "Lcom/necer/calendar/NCalendar;", "cardItem", "Ljava/util/ArrayList;", "", "kotlin.jvm.PlatformType", "dailyDateData", "Lcom/psychiatrygarden/bean/DailyDateData;", "dailyTaskCalendarDataBean", "Lcom/psychiatrygarden/bean/DailyTaskCalendarDataBean;", "day", "firstFailDay", "imgExpand", "layoutTop", "Landroid/widget/RelativeLayout;", "listTabs", "Lcom/psychiatrygarden/bean/SelectIdentityBean$DataBean;", "Lkotlin/collections/ArrayList;", "liveCalendarNextMonth", "liveCalendarPreMonth", "liveCalendarToday", "Landroid/widget/TextView;", "lyBarLayout", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "moreContentCache", "Lcom/psychiatrygarden/bean/MoreContentCache;", "pvCustomOptions", "Lcom/bigkoo/pickerview/view/OptionsPickerView;", "getPvCustomOptions", "()Lcom/bigkoo/pickerview/view/OptionsPickerView;", "setPvCustomOptions", "(Lcom/bigkoo/pickerview/view/OptionsPickerView;)V", "seekbar", "Landroid/widget/SeekBar;", "seekbarValue", "showPlanFinishDialog", "", "showTaskFailDialog", "showVipDialog", "systemPlanNum", "", "taskFailDayNum", "tvTaskCalendarCount", "tvTaskCalendarTime", "tvTaskDes", "Lcom/psychiatrygarden/widget/ReadMoreIcon;", "tvTaskName", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "compareTo", "targetStr", "curDay", "compareToToDay", "getCurDateStr", "getDay", "localDate", "getFragmentData", "", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "id", "getResetTaskData", "", "getSystemPlanList", "getTaskData", "onlyUpdateData", "getTaskDataByDate", "date", "init", "initCalendar", "initCalendarDay", "isOnlyUpdateCalendar", "initDes", "des", "initScrollView", "initSeekbar", "initSeekbarProgress", "seekBar", "initStatusBar", "initTab", "initTopData", "isNightTheme", "isOpenMonth", "onBackPressed", "onEventMainThread", "msg", "Lcom/psychiatrygarden/bean/EventBusMessage;", AliyunLogKey.KEY_EVENT, "Lcom/psychiatrygarden/event/DailyTaskShowStatisticsEvent;", "str", "onNewIntent", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "openTask", "setContentView", "setListenerForWidget", "setTaskCalendarData", "listTask", "listTaskFinish", "listTaskFail", "listTaskTodo", "showDialogSequence", "showResetTaskDialog", "showSettingDialog", "showSignInTaskDialog", "failDayNum", "showTaskFinishDialog", "showTaskHintDialog", "showTimeSelectDialog", "open", "selectTime", "showVipTaskDialog", "updateTimeNotice", "noticeTime", "openNotice", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDailyTasksActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DailyTasksActivity.kt\ncom/psychiatrygarden/activity/knowledge/DailyTasksActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1201:1\n1855#2,2:1202\n1855#2,2:1204\n1855#2,2:1206\n*S KotlinDebug\n*F\n+ 1 DailyTasksActivity.kt\ncom/psychiatrygarden/activity/knowledge/DailyTasksActivity\n*L\n629#1:1202,2\n1096#1:1204,2\n1143#1:1206,2\n*E\n"})
/* loaded from: classes5.dex */
public final class DailyTasksActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private ImageView actionbarBack;
    private ImageView actionbarMore;
    private NCalendar calendar;
    private final ArrayList<String> cardItem;

    @Nullable
    private DailyDateData dailyDateData;

    @Nullable
    private DailyTaskCalendarDataBean dailyTaskCalendarDataBean;

    @NotNull
    private String day = "1";

    @NotNull
    private String firstFailDay;
    private ImageView imgExpand;
    private RelativeLayout layoutTop;

    @NotNull
    private final ArrayList<SelectIdentityBean.DataBean> listTabs;
    private ImageView liveCalendarNextMonth;
    private ImageView liveCalendarPreMonth;
    private TextView liveCalendarToday;
    private RelativeLayout lyBarLayout;
    private MagicIndicator magicIndicator;

    @Nullable
    private MoreContentCache moreContentCache;

    @Nullable
    private OptionsPickerView<String> pvCustomOptions;
    private SeekBar seekbar;
    private TextView seekbarValue;
    private boolean showPlanFinishDialog;
    private boolean showTaskFailDialog;
    private boolean showVipDialog;
    private int systemPlanNum;
    private int taskFailDayNum;
    private TextView tvTaskCalendarCount;
    private TextView tvTaskCalendarTime;
    private ReadMoreIcon tvTaskDes;
    private TextView tvTaskName;
    private ViewPager viewPager;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/DailyTasksActivity$Companion;", "", "()V", "navigationToDailyTasksActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToDailyTasksActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) DailyTasksActivity.class));
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CalendarState.values().length];
            try {
                iArr[CalendarState.WEEK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CalendarState.MONTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CalendarState.MONTH_STRETCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/knowledge/DailyTasksActivity$initTab$2", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.knowledge.DailyTasksActivity$initTab$2, reason: invalid class name */
    public static final class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(DailyTasksActivity this$0, int i2, View view) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ViewPager viewPager = this$0.viewPager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return DailyTasksActivity.this.listTabs.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @Nullable
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull final Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(DailyTasksActivity.this);
            commonPagerTitleView.setContentView(R.layout.item_tabs_zuti);
            commonPagerTitleView.findViewById(R.id.viewStart).setVisibility(index == 0 ? 0 : 8);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_tabs_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            textView.setText(((SelectIdentityBean.DataBean) DailyTasksActivity.this.listTabs.get(index)).getName());
            final DailyTasksActivity dailyTasksActivity = DailyTasksActivity.this;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.v
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    DailyTasksActivity.AnonymousClass2.getTitleView$lambda$0(dailyTasksActivity, index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity$initTab$2$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(2, 14.0f);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.third_txt_color));
                    imageView.setVisibility(4);
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onSelected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    textView.setTextSize(2, 16.0f);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_text_color));
                    imageView.setVisibility(0);
                }
            });
            return commonPagerTitleView;
        }
    }

    public DailyTasksActivity() {
        this.TAG = "DailyTaskActivity";
        this.cardItem = DailyTaskTimeConfig.getDailyTaskTimeRange();
        this.listTabs = new ArrayList<>();
        this.firstFailDay = "";
    }

    private final int compareTo(String targetStr, String curDay) throws ParseException {
        boolean zAfter;
        boolean zBefore;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(targetStr);
        Date date2 = simpleDateFormat.parse(curDay);
        if (date == null || date2 == null) {
            zAfter = false;
            zBefore = false;
        } else {
            zBefore = date.before(date2);
            zAfter = date.after(date2);
        }
        if (zBefore) {
            return -1;
        }
        return zAfter ? 1 : 0;
    }

    private final int compareToToDay(String targetStr) throws ParseException {
        boolean zAfter;
        boolean zBefore;
        System.out.println((Object) ("date compare --- target: " + targetStr));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Date time = calendar.getTime();
        Date date = simpleDateFormat.parse(targetStr);
        if (date != null) {
            zBefore = date.before(time);
            zAfter = date.after(time);
        } else {
            zAfter = false;
            zBefore = false;
        }
        if (zBefore) {
            System.out.println((Object) ("date compare --- target: " + targetStr + "  --- : -1 "));
            return -1;
        }
        if (zAfter) {
            System.out.println((Object) ("date compare --- target: " + targetStr + "  --- : 1 "));
            return 1;
        }
        System.out.println((Object) ("date compare --- target: " + targetStr + "  --- : 0 "));
        return 0;
    }

    private final String getCurDateStr() {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Intrinsics.checkNotNullExpressionValue(str, "SimpleDateFormat(\"yyyy-MM-dd\").format(Date())");
        return str;
    }

    private final String getDay(String localDate) {
        List<StudyPanUserDetail> study_plan_user_detail;
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = this.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean == null || (study_plan_user_detail = dailyTaskCalendarDataBean.getStudy_plan_user_detail()) == null) {
            return "";
        }
        for (StudyPanUserDetail studyPanUserDetail : study_plan_user_detail) {
            String date = studyPanUserDetail.getDate();
            Intrinsics.checkNotNull(date);
            if (compareTo(date, localDate) == 0) {
                return studyPanUserDetail.getDay();
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getResetTaskData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.dailyTaskReset, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.getResetTaskData.1
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
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((AnonymousClass1) t2);
                if (t2 != null) {
                    DailyTasksActivity dailyTasksActivity = DailyTasksActivity.this;
                    if (Intrinsics.areEqual("200", new JSONObject(t2).optString("code"))) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_DAILY_TASK);
                        CustomStudyPlanActivity.navigationToCustomStudyPlanActivity(dailyTasksActivity, true);
                        dailyTasksActivity.finish();
                    }
                }
            }
        });
    }

    private final void getSystemPlanList() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.userStudyPlanList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.getSystemPlanList.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String s2) {
                super.onSuccess((C05851) s2);
                try {
                    StudyPlanListBean studyPlanListBean = (StudyPlanListBean) new Gson().fromJson(s2, StudyPlanListBean.class);
                    if (Intrinsics.areEqual(studyPlanListBean.getCode(), "200")) {
                        studyPlanListBean.getData().getPlan_in_progress();
                        List<StudyPlanListBean.DataBean.SystemPlanBean> system_plan = studyPlanListBean.getData().getSystem_plan();
                        DailyTasksActivity.this.systemPlanNum = system_plan.size();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void getTaskData(final boolean onlyUpdateData) {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.dailyTaskData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.getTaskData.1
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
            public void onSuccess(@Nullable String t2) throws ParseException {
                super.onSuccess((C05861) t2);
                DailyTaskBean dailyTaskBean = (DailyTaskBean) new Gson().fromJson(t2, DailyTaskBean.class);
                if (Intrinsics.areEqual("200", dailyTaskBean.getCode())) {
                    DailyTasksActivity.this.dailyTaskCalendarDataBean = dailyTaskBean.getData();
                    DailyTasksActivity.this.initCalendarDay(onlyUpdateData);
                    DailyTasksActivity.this.initTopData();
                    DailyTasksActivity.this.showDialogSequence();
                }
            }
        });
    }

    public static /* synthetic */ void getTaskData$default(DailyTasksActivity dailyTasksActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        dailyTasksActivity.getTaskData(z2);
    }

    private final void getTaskDataByDate(String date, final boolean onlyUpdateData) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("day", date);
        this.day = date;
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.dailyDataByDate, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.getTaskDataByDate.1
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
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((C05871) t2);
                try {
                    DailyTasksActivity.this.dailyDateData = (DailyDateData) new Gson().fromJson(t2, DailyDateData.class);
                    DailyDateData dailyDateData = DailyTasksActivity.this.dailyDateData;
                    if (Intrinsics.areEqual("200", dailyDateData != null ? dailyDateData.getCode() : null)) {
                        if (onlyUpdateData) {
                            EventBus.getDefault().post(EventBusConstant.EVENT_QUESTION_UPDATE_DAILY_TASK_LIST);
                            EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_UPDATE_DAILY_TASK_LIST, ""));
                            return;
                        }
                        DailyDateData dailyDateData2 = DailyTasksActivity.this.dailyDateData;
                        List<DateTabBean> data = dailyDateData2 != null ? dailyDateData2.getData() : null;
                        DailyTasksActivity.this.listTabs.clear();
                        if (data != null) {
                            DailyTasksActivity dailyTasksActivity = DailyTasksActivity.this;
                            for (DateTabBean dateTabBean : data) {
                                SelectIdentityBean.DataBean dataBean = new SelectIdentityBean.DataBean();
                                dataBean.setId(dateTabBean.getId());
                                dataBean.setIdentity_id(dateTabBean.getId());
                                dataBean.setName(dateTabBean.getName());
                                DailyTaskCalendarDataBean dailyTaskCalendarDataBean = dailyTasksActivity.dailyTaskCalendarDataBean;
                                dataBean.setQuestion_bank_id(dailyTaskCalendarDataBean != null ? dailyTaskCalendarDataBean.getQuestion_category_id() : null);
                                dailyTasksActivity.listTabs.add(dataBean);
                            }
                        }
                        DailyTasksActivity.this.initTab();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static /* synthetic */ void getTaskDataByDate$default(DailyTasksActivity dailyTasksActivity, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        dailyTasksActivity.getTaskDataByDate(str, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.systemPlanNum > 0) {
            NavigationUtilKt.gotoStudyPlanListActivity(this$0);
        }
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showSettingDialog();
    }

    private final void initCalendar() {
        View viewFindViewById = findViewById(R.id.tvTaskCalendarTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tvTaskCalendarTime)");
        this.tvTaskCalendarTime = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.liveCalendarPreMonth);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.liveCalendarPreMonth)");
        this.liveCalendarPreMonth = (ImageView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.liveCalendarNextMonth);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.liveCalendarNextMonth)");
        this.liveCalendarNextMonth = (ImageView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.liveCalendarToday);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.liveCalendarToday)");
        this.liveCalendarToday = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.viewPager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.viewPager)");
        this.viewPager = (ViewPager) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.calendar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.calendar)");
        this.calendar = (NCalendar) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.imgExpand);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.imgExpand)");
        this.imgExpand = (ImageView) viewFindViewById8;
        ImageView imageView = this.liveCalendarPreMonth;
        NCalendar nCalendar = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("liveCalendarPreMonth");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.initCalendar$lambda$6(this.f12612c, view);
            }
        });
        ImageView imageView2 = this.liveCalendarNextMonth;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("liveCalendarNextMonth");
            imageView2 = null;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.initCalendar$lambda$7(this.f12614c, view);
            }
        });
        TextView textView = this.liveCalendarToday;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("liveCalendarToday");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.initCalendar$lambda$8(this.f12616c, view);
            }
        });
        ImageView imageView3 = this.imgExpand;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgExpand");
            imageView3 = null;
        }
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.initCalendar$lambda$9(this.f12618c, view);
            }
        });
        NCalendar nCalendar2 = this.calendar;
        if (nCalendar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar2 = null;
        }
        nCalendar2.setOnCalendarChangedListener(new OnCalendarChangedListener() { // from class: com.psychiatrygarden.activity.knowledge.j
            @Override // com.necer.listener.OnCalendarChangedListener
            public final void onCalendarChange(int i2, int i3, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                DailyTasksActivity.initCalendar$lambda$10(this.f12620a, i2, i3, localDate, dateChangeBehavior);
            }
        });
        NCalendar nCalendar3 = this.calendar;
        if (nCalendar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar3 = null;
        }
        nCalendar3.setWeekHoldEnable(true);
        NCalendar nCalendar4 = this.calendar;
        if (nCalendar4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
        } else {
            nCalendar = nCalendar4;
        }
        nCalendar.setOnCalendarStateChangedListener(new OnCalendarStateChangedListener() { // from class: com.psychiatrygarden.activity.knowledge.k
            @Override // com.necer.listener.OnCalendarStateChangedListener
            public final void onCalendarStateChange(CalendarState calendarState) {
                DailyTasksActivity.initCalendar$lambda$11(this.f12622a, calendarState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCalendar$lambda$10(DailyTasksActivity this$0, int i2, int i3, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
        String string;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        if (i3 < 10) {
            string = i2 + "年0" + i3 + (char) 26376;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append((char) 24180);
            sb.append(i3);
            sb.append((char) 26376);
            string = sb.toString();
        }
        TextView textView = this$0.tvTaskCalendarTime;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskCalendarTime");
            textView = null;
        }
        textView.setText(string);
        String string2 = localDate.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "localDate.toString()");
        String day = this$0.getDay(string2);
        if (TextUtils.isEmpty(day)) {
            return;
        }
        Intrinsics.checkNotNull(day);
        getTaskDataByDate$default(this$0, day, false, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCalendar$lambda$11(DailyTasksActivity this$0, CalendarState calendarState) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int i2 = calendarState == null ? -1 : WhenMappings.$EnumSwitchMapping$0[calendarState.ordinal()];
        ImageView imageView = null;
        if (i2 == 1) {
            ImageView imageView2 = this$0.imgExpand;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgExpand");
            } else {
                imageView = imageView2;
            }
            imageView.setImageResource(this$0.isNightTheme() ? R.drawable.icon_live_calendar_expand_new_night : R.drawable.icon_live_calendar_expand_new_day);
            return;
        }
        if (i2 != 2) {
            return;
        }
        ImageView imageView3 = this$0.imgExpand;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgExpand");
        } else {
            imageView = imageView3;
        }
        imageView.setImageResource(this$0.isNightTheme() ? R.drawable.icon_live_calendar_close_new_night : R.drawable.icon_live_calendar_close_new_day);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCalendar$lambda$6(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NCalendar nCalendar = this$0.calendar;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        nCalendar.toLastPager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCalendar$lambda$7(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NCalendar nCalendar = this$0.calendar;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        nCalendar.toNextPager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCalendar$lambda$8(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NCalendar nCalendar = this$0.calendar;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        nCalendar.toToday();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCalendar$lambda$9(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NCalendar nCalendar = this$0.calendar;
        NCalendar nCalendar2 = null;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        if (nCalendar.getCalendarState() == CalendarState.WEEK) {
            NCalendar nCalendar3 = this$0.calendar;
            if (nCalendar3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("calendar");
            } else {
                nCalendar2 = nCalendar3;
            }
            nCalendar2.toMonth();
            return;
        }
        NCalendar nCalendar4 = this$0.calendar;
        if (nCalendar4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
        } else {
            nCalendar2 = nCalendar4;
        }
        nCalendar2.toWeek();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initCalendarDay(boolean isOnlyUpdateCalendar) throws ParseException {
        Object days;
        List<StudyPanUserDetail> listEmptyList;
        String status;
        DailyTaskCalendarPlanBean study_plan_user;
        DailyTaskCalendarPlanBean study_plan_user2;
        TextView textView = this.tvTaskCalendarCount;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskCalendarCount");
            textView = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(共");
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = this.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean == null || (study_plan_user2 = dailyTaskCalendarDataBean.getStudy_plan_user()) == null || (days = study_plan_user2.getDays()) == null) {
            days = 0;
        }
        sb.append(days);
        sb.append("天)");
        textView.setText(sb.toString());
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean2 = this.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean2 == null || (listEmptyList = dailyTaskCalendarDataBean2.getStudy_plan_user_detail()) == null) {
            listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        Iterator<StudyPanUserDetail> it = listEmptyList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StudyPanUserDetail next = it.next();
            String status2 = next.getStatus();
            String date = next.getDate();
            if (date != null) {
                arrayList.add(date);
                if (Intrinsics.areEqual("1", status2)) {
                    arrayList2.add(date);
                } else {
                    int iCompareToToDay = compareToToDay(date);
                    if (iCompareToToDay == -1) {
                        arrayList3.add(date);
                    } else if (iCompareToToDay == 0) {
                        arrayList4.add(date);
                    } else if (iCompareToToDay == 1) {
                        arrayList4.add(date);
                    }
                }
            }
        }
        if (!isOnlyUpdateCalendar) {
            DailyTaskCalendarDataBean dailyTaskCalendarDataBean3 = this.dailyTaskCalendarDataBean;
            if (dailyTaskCalendarDataBean3 == null || (study_plan_user = dailyTaskCalendarDataBean3.getStudy_plan_user()) == null || (status = study_plan_user.getStatus()) == null) {
                status = "";
            }
            boolean z2 = arrayList.size() > 0 && compareToToDay(arrayList.get(0)) == 1;
            if (Intrinsics.areEqual(status, "1") || z2) {
                NCalendar nCalendar = this.calendar;
                if (nCalendar == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendar");
                    nCalendar = null;
                }
                nCalendar.jumpDate(arrayList.get(0));
                getTaskDataByDate$default(this, "1", false, 2, null);
            } else {
                NCalendar nCalendar2 = this.calendar;
                if (nCalendar2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendar");
                    nCalendar2 = null;
                }
                nCalendar2.jumpDate(getCurDateStr());
                String day = getDay(getCurDateStr());
                if (TextUtils.isEmpty(day)) {
                    getTaskDataByDate$default(this, "1", false, 2, null);
                } else {
                    Intrinsics.checkNotNull(day);
                    getTaskDataByDate$default(this, day, false, 2, null);
                }
            }
        }
        setTaskCalendarData(arrayList, arrayList2, arrayList3, arrayList4);
    }

    public static /* synthetic */ void initCalendarDay$default(DailyTasksActivity dailyTasksActivity, boolean z2, int i2, Object obj) throws ParseException {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        dailyTasksActivity.initCalendarDay(z2);
    }

    private final void initDes(final String des) {
        ReadMoreIcon readMoreIcon = this.tvTaskDes;
        RelativeLayout relativeLayout = null;
        if (readMoreIcon == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskDes");
            readMoreIcon = null;
        }
        readMoreIcon.setContentUpdateListener(new ReadMoreIcon.OnStateChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.initDes.1
            @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
            public void onChange(boolean isExpand) {
                ReadMoreIcon readMoreIcon2 = DailyTasksActivity.this.tvTaskDes;
                if (readMoreIcon2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTaskDes");
                    readMoreIcon2 = null;
                }
                readMoreIcon2.setText(des);
            }

            @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
            public void updateContent(@Nullable CharSequence expandText, @Nullable CharSequence collapseText) {
            }

            @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
            public void updateLineCount(int count) {
            }

            @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
            public void updateLineIndex(int index) {
            }
        });
        ReadMoreIcon readMoreIcon2 = this.tvTaskDes;
        if (readMoreIcon2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskDes");
            readMoreIcon2 = null;
        }
        readMoreIcon2.setOriginalText(des);
        ReadMoreIcon readMoreIcon3 = this.tvTaskDes;
        if (readMoreIcon3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskDes");
            readMoreIcon3 = null;
        }
        readMoreIcon3.setText(des);
        ReadMoreIcon readMoreIcon4 = this.tvTaskDes;
        if (readMoreIcon4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskDes");
            readMoreIcon4 = null;
        }
        readMoreIcon4.setVisibility(TextUtils.isEmpty(des) ? 8 : 0);
        RelativeLayout relativeLayout2 = this.layoutTop;
        if (relativeLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTop");
        } else {
            relativeLayout = relativeLayout2;
        }
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.knowledge.r
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                DailyTasksActivity.initDes$lambda$5(this.f12632c);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initDes$lambda$5(DailyTasksActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RelativeLayout relativeLayout = this$0.layoutTop;
        RelativeLayout relativeLayout2 = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTop");
            relativeLayout = null;
        }
        relativeLayout.getMeasuredHeight();
        RelativeLayout relativeLayout3 = this$0.layoutTop;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTop");
        } else {
            relativeLayout2 = relativeLayout3;
        }
        ViewGroup.LayoutParams layoutParams = relativeLayout2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
    }

    private final void initScrollView() {
        final HeaderScrollView headerScrollView = (HeaderScrollView) findViewById(R.id.scrollView);
        headerScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.s
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                DailyTasksActivity.initScrollView$lambda$27(headerScrollView, this, view, i2, i3, i4, i5);
            }
        });
        ((LinearLayout) findViewById(R.id.layoutViewPage)).setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.knowledge.t
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return DailyTasksActivity.initScrollView$lambda$28(this.f12635c, view, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initScrollView$lambda$27(HeaderScrollView headerScrollView, DailyTasksActivity this$0, View view, int i2, int i3, int i4, int i5) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d("calendar", "initScrollView: " + i3 + ", --- " + headerScrollView.getMaxScrollY());
        int iAbs = Math.abs(i3 - headerScrollView.getMaxScrollY());
        NCalendar nCalendar = this$0.calendar;
        NCalendar nCalendar2 = null;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        if (nCalendar.getCalendarState() == CalendarState.MONTH) {
            if (iAbs >= 5) {
                NCalendar nCalendar3 = this$0.calendar;
                if (nCalendar3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendar");
                } else {
                    nCalendar2 = nCalendar3;
                }
                nCalendar2.setShouldChangeToWeek(false);
                return;
            }
            NCalendar nCalendar4 = this$0.calendar;
            if (nCalendar4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("calendar");
                nCalendar4 = null;
            }
            nCalendar4.toWeek();
            NCalendar nCalendar5 = this$0.calendar;
            if (nCalendar5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("calendar");
            } else {
                nCalendar2 = nCalendar5;
            }
            nCalendar2.setShouldChangeToWeek(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initScrollView$lambda$28(DailyTasksActivity this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (motionEvent.getAction() != 2) {
            return super.onTouchEvent(motionEvent);
        }
        Log.d("calendar", "initScrollView: ACTION_DOWN");
        return super.onTouchEvent(motionEvent);
    }

    private final void initSeekbar() {
        SeekBar seekBar = this.seekbar;
        if (seekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbar");
            seekBar = null;
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.initSeekbar.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(@NotNull SeekBar seekBar2, int i2, boolean b3) {
                Intrinsics.checkNotNullParameter(seekBar2, "seekBar");
                System.out.println((Object) ("this is onProgressChanged : " + seekBar2.getProgress()));
                DailyTasksActivity.this.initSeekbarProgress(seekBar2);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(@NotNull SeekBar seekBar2) {
                Intrinsics.checkNotNullParameter(seekBar2, "seekBar");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(@NotNull SeekBar seekBar2) {
                Intrinsics.checkNotNullParameter(seekBar2, "seekBar");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initSeekbarProgress(SeekBar seekBar) {
        TextView textView = this.seekbarValue;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbarValue");
            textView = null;
        }
        int width = textView.getWidth();
        TextView textView3 = this.seekbarValue;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbarValue");
            textView3 = null;
        }
        int height = textView3.getHeight();
        int intrinsicWidth = seekBar.getThumb().getIntrinsicWidth();
        int intrinsicHeight = seekBar.getThumb().getIntrinsicHeight();
        int thumbOffset = seekBar.getThumbOffset();
        int i2 = (intrinsicWidth - width) / 2;
        int progress = (int) (((seekBar.getProgress() / seekBar.getMax()) * ((((seekBar.getWidth() - seekBar.getPaddingLeft()) - seekBar.getPaddingRight()) - intrinsicWidth) + (thumbOffset * 2))) + 0.5f);
        TextView textView4 = this.seekbarValue;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbarValue");
            textView4 = null;
        }
        ViewGroup.LayoutParams layoutParams = textView4.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        int i3 = (intrinsicHeight - height) / 2;
        layoutParams2.setMargins(((progress + i2) + seekBar.getPaddingLeft()) - thumbOffset, 0, 0, 0);
        TextView textView5 = this.seekbarValue;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbarValue");
            textView5 = null;
        }
        textView5.setLayoutParams(layoutParams2);
        TextView textView6 = this.seekbarValue;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbarValue");
        } else {
            textView2 = textView6;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(seekBar.getProgress());
        sb.append('%');
        textView2.setText(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initTab() throws Resources.NotFoundException {
        List<DateTabBean> data;
        ArrayList arrayList = new ArrayList();
        int size = this.listTabs.size();
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("child", this.listTabs.get(i2));
            bundle.putBoolean("isDailyTask", true);
            String id = this.listTabs.get(i2).getId();
            bundle.putString("day", this.day);
            DailyDateData dailyDateData = this.dailyDateData;
            if (dailyDateData != null && (data = dailyDateData.getData()) != null) {
                for (DateTabBean dateTabBean : data) {
                    if (Intrinsics.areEqual(id, dateTabBean.getId()) && dateTabBean.getChildren() != null) {
                        List<KnowledgeChartNodeBean> children = dateTabBean.getChildren();
                        Intrinsics.checkNotNull(children, "null cannot be cast to non-null type java.io.Serializable");
                        bundle.putSerializable("dailyTaskKnowledgeList", (Serializable) children);
                    }
                }
            }
            arrayList.add(new BaseViewPagerAdapter.PagerInfo("", DailyTaskFragment.class, bundle));
        }
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.viewPager;
        MagicIndicator magicIndicator = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass2());
        MagicIndicator magicIndicator2 = this.magicIndicator;
        if (magicIndicator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator2 = null;
        }
        magicIndicator2.setNavigator(commonNavigator);
        MagicIndicator magicIndicator3 = this.magicIndicator;
        if (magicIndicator3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator3 = null;
        }
        ViewPager viewPager2 = this.viewPager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        }
        ViewPagerHelper.bind(magicIndicator3, viewPager2);
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager3 = null;
        }
        viewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.initTab.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
            }
        });
        ViewPager viewPager4 = this.viewPager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager4 = null;
        }
        viewPager4.setOffscreenPageLimit(this.listTabs.size());
        MagicIndicator magicIndicator4 = this.magicIndicator;
        if (magicIndicator4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
        } else {
            magicIndicator = magicIndicator4;
        }
        magicIndicator.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initTopData() {
        String name;
        DailyTaskCalendarPlanBean study_plan_user;
        String type;
        DailyTaskCalendarPlanBean study_plan_user2;
        String completion;
        DailyTaskCalendarPlanBean study_plan_user3;
        String description;
        DailyTaskCalendarPlanBean study_plan_user4;
        TextView textView = this.tvTaskName;
        SeekBar seekBar = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTaskName");
            textView = null;
        }
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = this.dailyTaskCalendarDataBean;
        String str = "";
        if (dailyTaskCalendarDataBean == null || (study_plan_user4 = dailyTaskCalendarDataBean.getStudy_plan_user()) == null || (name = study_plan_user4.getName()) == null) {
            name = "";
        }
        textView.setText(name);
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean2 = this.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean2 != null && (study_plan_user3 = dailyTaskCalendarDataBean2.getStudy_plan_user()) != null && (description = study_plan_user3.getDescription()) != null) {
            str = description;
        }
        initDes(str);
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean3 = this.dailyTaskCalendarDataBean;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, (dailyTaskCalendarDataBean3 == null || (study_plan_user2 = dailyTaskCalendarDataBean3.getStudy_plan_user()) == null || (completion = study_plan_user2.getCompletion()) == null) ? 0 : Integer.parseInt(completion));
        valueAnimatorOfInt.setDuration(200L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.knowledge.e
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DailyTasksActivity.initTopData$lambda$20(this.f12610c, valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
        SeekBar seekBar2 = this.seekbar;
        if (seekBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbar");
        } else {
            seekBar = seekBar2;
        }
        seekBar.setEnabled(false);
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean4 = this.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean4 == null || (study_plan_user = dailyTaskCalendarDataBean4.getStudy_plan_user()) == null || (type = study_plan_user.getType()) == null) {
            return;
        }
        if (Intrinsics.areEqual("1", type)) {
            this.systemPlanNum = 1;
        } else {
            getSystemPlanList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTopData$lambda$20(DailyTasksActivity this$0, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animation, "animation");
        SeekBar seekBar = this$0.seekbar;
        if (seekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekbar");
            seekBar = null;
        }
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        seekBar.setProgress(((Integer) animatedValue).intValue());
    }

    private final boolean isNightTheme() {
        return SkinManager.getCurrentSkinType(this) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onEventMainThread$lambda$4() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(DailyTasksActivity this$0, View view) {
        String rule;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = this$0.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean == null || (rule = dailyTaskCalendarDataBean.getRule()) == null) {
            return;
        }
        this$0.showTaskHintDialog(rule);
    }

    private final void setTaskCalendarData(List<String> listTask, List<String> listTaskFinish, List<String> listTaskFail, List<String> listTaskTodo) {
        NCalendar nCalendar = this.calendar;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        CalendarPainter calendarPainter = nCalendar.getCalendarPainter();
        Intrinsics.checkNotNull(calendarPainter, "null cannot be cast to non-null type com.necer.painter.InnerPainter");
        ((InnerPainter) calendarPainter).setDailyTaskDataList(listTask, listTaskFinish, listTaskFail, listTaskTodo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDialogSequence() {
        List<StudyPanUserDetail> listEmptyList;
        DailyTaskCalendarPlanBean study_plan_user;
        boolean zAreEqual = Intrinsics.areEqual(UserConfig.getInstance().getUser().getIs_vip(), "1");
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = this.dailyTaskCalendarDataBean;
        this.showPlanFinishDialog = Intrinsics.areEqual((dailyTaskCalendarDataBean == null || (study_plan_user = dailyTaskCalendarDataBean.getStudy_plan_user()) == null) ? null : study_plan_user.getStatus(), "1");
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean2 = this.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean2 == null || (listEmptyList = dailyTaskCalendarDataBean2.getStudy_plan_user_detail()) == null) {
            listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (StudyPanUserDetail studyPanUserDetail : listEmptyList) {
            String status = studyPanUserDetail.getStatus();
            String date = studyPanUserDetail.getDate();
            if (date != null && Intrinsics.areEqual("0", status) && -1 == compareToToDay(date)) {
                arrayList.add(date);
            }
        }
        this.showTaskFailDialog = arrayList.size() > 0;
        this.showVipDialog = !zAreEqual;
        if (arrayList.size() > 0) {
            this.firstFailDay = (String) arrayList.get(0);
        }
        this.taskFailDayNum = arrayList.size();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.knowledge.c
            @Override // java.lang.Runnable
            public final void run() {
                DailyTasksActivity.showDialogSequence$lambda$24(this.f12606c);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialogSequence$lambda$24(DailyTasksActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.showVipDialog) {
            this$0.showVipTaskDialog();
        } else if (this$0.showPlanFinishDialog) {
            this$0.showTaskFinishDialog();
        } else if (this$0.showTaskFailDialog) {
            this$0.showSignInTaskDialog(String.valueOf(this$0.taskFailDayNum), this$0.firstFailDay);
        }
    }

    private final void showResetTaskDialog() {
        new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml2() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.showResetTaskDialog.1
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml2
            public void mClickCancel() {
            }

            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml2
            public void mClickIml() {
                DailyTasksActivity.this.getResetTaskData();
            }
        }, new SpannableStringBuilder("是否放弃当前规划，并制定新规划？"), "温馨提示", "取消", "放弃并重置")).show();
    }

    private final void showSettingDialog() {
        new XPopup.Builder(this).asCustom(new DailyTaskSettingPopupWindow(this, new DailyTaskSettingPopupWindow.ClickIml() { // from class: com.psychiatrygarden.activity.knowledge.d
            @Override // com.psychiatrygarden.widget.DailyTaskSettingPopupWindow.ClickIml
            public final void mClickIml(int i2) {
                DailyTasksActivity.showSettingDialog$lambda$18(this.f12608a, i2);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSettingDialog$lambda$18(DailyTasksActivity this$0, int i2) {
        DailyTaskCalendarPlanBean study_plan_user;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i2 != 0) {
            if (i2 != 1) {
                return;
            }
            this$0.showResetTaskDialog();
            return;
        }
        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = this$0.dailyTaskCalendarDataBean;
        if (dailyTaskCalendarDataBean == null || (study_plan_user = dailyTaskCalendarDataBean.getStudy_plan_user()) == null) {
            return;
        }
        boolean zAreEqual = Intrinsics.areEqual("1", study_plan_user.getIs_notice());
        String notice_time = study_plan_user.getNotice_time();
        if (notice_time == null) {
            notice_time = "";
        }
        this$0.showTimeSelectDialog(zAreEqual, notice_time);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSignInTaskDialog(String failDayNum, final String firstFailDay) {
        new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml2() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.showSignInTaskDialog.1
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml2
            public void mClickCancel() {
            }

            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml2
            public void mClickIml() {
                NCalendar nCalendar = DailyTasksActivity.this.calendar;
                if (nCalendar == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("calendar");
                    nCalendar = null;
                }
                nCalendar.jumpDate(firstFailDay);
            }
        }, new SpannableStringBuilder("检测到学习规划有" + failDayNum + "天没完成，今天的坚持，是明天从容的底气，加油!"), "温馨提示", "取消", "去补卡")).show();
    }

    private final void showTaskFinishDialog() {
        final DailyTaskPop dailyTaskPop = new DailyTaskPop(this, "2");
        XPopup.Builder builder = new XPopup.Builder(this);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).moveUpToKeyboard(bool).asCustom(dailyTaskPop).show();
        dailyTaskPop.setShareListener(new DailyTaskPop.ShareListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.showTaskFinishDialog.1
            @Override // com.psychiatrygarden.widget.DailyTaskPop.ShareListener
            public void mShareCloseListener() {
                dailyTaskPop.dismiss();
                if (this.showTaskFailDialog) {
                    DailyTasksActivity dailyTasksActivity = this;
                    dailyTasksActivity.showSignInTaskDialog(String.valueOf(dailyTasksActivity.taskFailDayNum), this.firstFailDay);
                }
            }

            @Override // com.psychiatrygarden.widget.DailyTaskPop.ShareListener
            public void mShareDataListener(@Nullable String type) {
                dailyTaskPop.dismiss();
                if ("1".equals(type)) {
                    NavigationUtilKt.gotoVipCenter(this);
                } else {
                    this.getResetTaskData();
                }
                if (this.showTaskFailDialog) {
                    DailyTasksActivity dailyTasksActivity = this;
                    dailyTasksActivity.showSignInTaskDialog(String.valueOf(dailyTasksActivity.taskFailDayNum), this.firstFailDay);
                }
            }
        });
    }

    private final void showTaskHintDialog(String str) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        new XPopup.Builder(this).popupAnimation(null).asCustom(new ScoreTrendInfoDialog(this, str, "刷题规划说明", true)).show();
    }

    private final void showTimeSelectDialog(final boolean open, String selectTime) {
        Iterator<String> it = this.cardItem.iterator();
        int i2 = 1;
        int i3 = 0;
        while (it.hasNext()) {
            int i4 = i3 + 1;
            if (Intrinsics.areEqual(it.next(), selectTime)) {
                i2 = i3;
            }
            i3 = i4;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        OptionsPickerView<String> optionsPickerViewBuild = new OptionsPickerBuilder(this, new OnOptionsSelectListener() { // from class: com.psychiatrygarden.activity.knowledge.p
            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
            public final void onOptionsSelect(int i5, int i6, int i7, View view) {
                DailyTasksActivity.showTimeSelectDialog$lambda$12(this.f12627a, booleanRef, i5, i6, i7, view);
            }
        }).setLayoutRes(R.layout.pickview_custom_time, new CustomListener() { // from class: com.psychiatrygarden.activity.knowledge.q
            @Override // com.bigkoo.pickerview.listener.CustomListener
            public final void customLayout(View view) {
                DailyTasksActivity.showTimeSelectDialog$lambda$16(open, booleanRef, this, view);
            }
        }).setLineSpacingMultiplier(3.5f).isCenterLabel(false).setSelectOptions(i2).setDividerColor(16777215).setTextColorCenter(ContextCompat.getColor(this, z2 ? R.color.first_txt_color_night : R.color.first_txt_color)).setTextColorOut(ContextCompat.getColor(this, z2 ? R.color.second_txt_color_night : R.color.second_txt_color)).setBgColor(ContextCompat.getColor(this, z2 ? R.color.new_bg_one_color_night : R.color.new_bg_one_color)).setOutSideCancelable(true).build();
        this.pvCustomOptions = optionsPickerViewBuild;
        Intrinsics.checkNotNull(optionsPickerViewBuild);
        optionsPickerViewBuild.setPicker(this.cardItem);
        OptionsPickerView<String> optionsPickerView = this.pvCustomOptions;
        if (optionsPickerView != null) {
            optionsPickerView.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTimeSelectDialog$lambda$12(DailyTasksActivity this$0, Ref.BooleanRef openSwitch, int i2, int i3, int i4, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(openSwitch, "$openSwitch");
        String str = this$0.cardItem.get(i2);
        Intrinsics.checkNotNullExpressionValue(str, "cardItem.get(options1)");
        this$0.updateTimeNotice(str, openSwitch.element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTimeSelectDialog$lambda$16(boolean z2, final Ref.BooleanRef openSwitch, final DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(openSwitch, "$openSwitch");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View viewFindViewById = view.findViewById(R.id.tv_finish);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type android.widget.TextView");
        View viewFindViewById2 = view.findViewById(R.id.iv_close);
        Intrinsics.checkNotNull(viewFindViewById2, "null cannot be cast to non-null type android.widget.ImageView");
        ((TextView) viewFindViewById).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DailyTasksActivity.showTimeSelectDialog$lambda$16$lambda$13(this.f12600c, view2);
            }
        });
        View viewFindViewById3 = view.findViewById(R.id.layoutSwitch);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "v.findViewById(R.id.layoutSwitch)");
        ViewExtensionsKt.visible((RelativeLayout) viewFindViewById3);
        View viewFindViewById4 = view.findViewById(R.id.switchTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "v.findViewById(R.id.switchTime)");
        Switch r6 = (Switch) viewFindViewById4;
        r6.setChecked(z2);
        openSwitch.element = z2;
        r6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.l
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                DailyTasksActivity.showTimeSelectDialog$lambda$16$lambda$14(openSwitch, compoundButton, z3);
            }
        });
        ((ImageView) viewFindViewById2).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DailyTasksActivity.showTimeSelectDialog$lambda$16$lambda$15(this.f12626c, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTimeSelectDialog$lambda$16$lambda$13(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OptionsPickerView<String> optionsPickerView = this$0.pvCustomOptions;
        Intrinsics.checkNotNull(optionsPickerView);
        optionsPickerView.returnData();
        OptionsPickerView<String> optionsPickerView2 = this$0.pvCustomOptions;
        Intrinsics.checkNotNull(optionsPickerView2);
        optionsPickerView2.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTimeSelectDialog$lambda$16$lambda$14(Ref.BooleanRef openSwitch, CompoundButton compoundButton, boolean z2) {
        Intrinsics.checkNotNullParameter(openSwitch, "$openSwitch");
        openSwitch.element = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTimeSelectDialog$lambda$16$lambda$15(DailyTasksActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OptionsPickerView<String> optionsPickerView = this$0.pvCustomOptions;
        Intrinsics.checkNotNull(optionsPickerView);
        optionsPickerView.dismiss();
    }

    private final void showVipTaskDialog() {
        new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml2() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.showVipTaskDialog.1
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml2
            public void mClickCancel() {
                DailyTasksActivity.this.onBackPressed();
            }

            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml2
            public void mClickIml() {
                NavigationUtilKt.gotoVipCenterDailyTask(DailyTasksActivity.this);
                DailyTasksActivity.this.finish();
            }
        }, new SpannableStringBuilder("尊敬的会员，您的会员服务已到期，当前功能暂时无法使用。为了继续享受完整服务体验，请您及时续费开通会员。我们衷心感谢您一直以来的支持!"), "温馨提示", "取消", "开通会员")).show();
    }

    private final void updateTimeNotice(final String noticeTime, final boolean openNotice) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("notice_time", noticeTime);
        ajaxParams.put("is_notice", openNotice ? "1" : "0");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.dailyUpdateTime, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTasksActivity.updateTimeNotice.1
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
            public void onSuccess(@Nullable String t2) {
                DailyTaskCalendarPlanBean study_plan_user;
                super.onSuccess((C05941) t2);
                if (t2 != null) {
                    DailyTasksActivity dailyTasksActivity = DailyTasksActivity.this;
                    String str = noticeTime;
                    boolean z2 = openNotice;
                    if (Intrinsics.areEqual(new JSONObject(t2).optString("code"), "200")) {
                        NewToast.showLong(dailyTasksActivity, "修改成功");
                        DailyTaskCalendarDataBean dailyTaskCalendarDataBean = dailyTasksActivity.dailyTaskCalendarDataBean;
                        if (dailyTaskCalendarDataBean == null || (study_plan_user = dailyTaskCalendarDataBean.getStudy_plan_user()) == null) {
                            return;
                        }
                        study_plan_user.setNotice_time(str);
                        study_plan_user.set_notice(z2 ? "1" : "0");
                    }
                }
            }
        });
    }

    @NotNull
    public final List<KnowledgeChartNodeBean> getFragmentData(@NotNull String id) {
        List<DateTabBean> data;
        Intrinsics.checkNotNullParameter(id, "id");
        ArrayList arrayList = new ArrayList();
        DailyDateData dailyDateData = this.dailyDateData;
        if (dailyDateData != null && (data = dailyDateData.getData()) != null) {
            for (DateTabBean dateTabBean : data) {
                if (Intrinsics.areEqual(id, dateTabBean.getId()) && dateTabBean.getChildren() != null) {
                    arrayList.addAll(dateTabBean.getChildren());
                }
            }
        }
        return arrayList;
    }

    @Nullable
    public final OptionsPickerView<String> getPvCustomOptions() {
        return this.pvCustomOptions;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        initStatusBar();
        View viewFindViewById = findViewById(R.id.tvTaskName);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tvTaskName)");
        this.tvTaskName = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tvTaskDes);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tvTaskDes)");
        this.tvTaskDes = (ReadMoreIcon) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.layoutTop);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.layoutTop)");
        this.layoutTop = (RelativeLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tvTaskCalendarCount);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tvTaskCalendarCount)");
        this.tvTaskCalendarCount = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.actionbarMore);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.actionbarMore)");
        this.actionbarMore = (ImageView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.actionbarBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.actionbarBack)");
        ImageView imageView = (ImageView) viewFindViewById6;
        this.actionbarBack = imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("actionbarBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.init$lambda$0(this.f12636c, view);
            }
        });
        ImageView imageView2 = this.actionbarMore;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("actionbarMore");
            imageView2 = null;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.init$lambda$1(this.f12604c, view);
            }
        });
        View viewFindViewById7 = findViewById(R.id.seekbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.seekbar)");
        this.seekbar = (SeekBar) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.seekbar_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.seekbar_value)");
        this.seekbarValue = (TextView) viewFindViewById8;
        initSeekbar();
        initCalendar();
        getTaskData$default(this, false, 1, null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, isNightTheme() ? R.color.corlor_appbar_study_plan_night : R.color.corlor_appbar_study_plan), 0);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    public final boolean isOpenMonth() {
        NCalendar nCalendar = this.calendar;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        return nCalendar.getCalendarState() == CalendarState.MONTH;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (this.systemPlanNum > 0) {
            NavigationUtilKt.gotoStudyPlanListActivity(this);
        }
        finish();
    }

    @Subscribe
    public final void onEventMainThread(@NotNull EventBusMessage<?> msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (Intrinsics.areEqual(msg.getKey(), "EVENT_QUESTION_ANSWER_REFRESH_KNOWLEDGE")) {
            getTaskDataByDate(this.day, true);
            getTaskData(true);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(@Nullable Intent intent) {
        super.onNewIntent(intent);
        getTaskData(false);
    }

    public final void openTask() {
        NCalendar nCalendar = this.calendar;
        if (nCalendar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("calendar");
            nCalendar = null;
        }
        nCalendar.toWeek();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_daily_tasks);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ((ImageView) findViewById(R.id.actionbarQuestionMark)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DailyTasksActivity.setListenerForWidget$lambda$3(this.f12625c, view);
            }
        });
    }

    public final void setPvCustomOptions(@Nullable OptionsPickerView<String> optionsPickerView) {
        this.pvCustomOptions = optionsPickerView;
    }

    @Subscribe
    public final void onEventMainThread(@NotNull DailyTaskShowStatisticsEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        XPopup.Builder builder = new XPopup.Builder(this.mContext);
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        StatisticsData data = e2.getData();
        Intrinsics.checkNotNullExpressionValue(data, "e.getData()");
        builder.asCustom(new QuestionStatisticsPop(mContext, data, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.knowledge.o
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                DailyTasksActivity.onEventMainThread$lambda$4();
            }
        })).show();
    }
}
