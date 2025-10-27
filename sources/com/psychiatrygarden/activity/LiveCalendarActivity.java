package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.necer.calendar.NCalendar;
import com.necer.enumeration.CalendarState;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;
import com.necer.painter.InnerPainter;
import com.psychiatrygarden.adapter.LiveCalendarAdapter;
import com.psychiatrygarden.bean.LiveDataList;
import com.psychiatrygarden.bean.LiveDateBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LiveStatus;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class LiveCalendarActivity extends BaseActivity {
    private static final String TAG = "LiveCalendarActivity";
    private Drawable drawableClose;
    private Drawable drawableExpand;
    private CustomEmptyView emptyView;
    private ImageView imgExpand;
    private TextView liveCalendarToday;
    private NCalendar monthCalendar;
    private RecyclerView recyclerView;
    private LiveCalendarAdapter recyclerViewAdapter;
    private TextView tv_result;
    private HashSet<String> calendarData = new HashSet<>();
    private HashMap<String, Boolean> monthHaveData = new HashMap<>();

    /* renamed from: com.psychiatrygarden.activity.LiveCalendarActivity$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$necer$enumeration$CalendarState;
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$utils$LiveStatus;

        static {
            int[] iArr = new int[CalendarState.values().length];
            $SwitchMap$com$necer$enumeration$CalendarState = iArr;
            try {
                iArr[CalendarState.WEEK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$necer$enumeration$CalendarState[CalendarState.MONTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$necer$enumeration$CalendarState[CalendarState.MONTH_STRETCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[LiveStatus.values().length];
            $SwitchMap$com$psychiatrygarden$utils$LiveStatus = iArr2;
            try {
                iArr2[LiveStatus.NO_BEGIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.CUTTING.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.LIVING.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.HAVE_VID.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    private void getLiveDate(String dateStr) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        ajaxParams.put("date", dateStr);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.liveCalendarLiveDate, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.LiveCalendarActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<LiveDateBean>>() { // from class: com.psychiatrygarden.activity.LiveCalendarActivity.1.1
                        }.getType());
                        Log.d(LiveCalendarActivity.TAG, "onSuccess: size:liveDate:" + list.size());
                        LiveCalendarActivity.this.putLiveDate(list);
                    }
                } catch (Exception e2) {
                    Log.e("Error直播date:", e2.getMessage());
                }
            }
        });
    }

    private void getLiveList(String dateString) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        ajaxParams.put("date", dateString);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.liveCalendarLiveDataList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.LiveCalendarActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LiveCalendarActivity.this.recyclerViewAdapter.setEmptyView(LiveCalendarActivity.this.emptyView);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200")) {
                        LiveCalendarActivity.this.recyclerViewAdapter.setNewData(new ArrayList());
                        LiveCalendarActivity.this.recyclerViewAdapter.setEmptyView(LiveCalendarActivity.this.emptyView);
                        LiveCalendarActivity.this.emptyView.uploadEmptyViewResUi();
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<LiveDataList>>() { // from class: com.psychiatrygarden.activity.LiveCalendarActivity.2.1
                    }.getType());
                    if (list.isEmpty()) {
                        LiveCalendarActivity.this.recyclerViewAdapter.setNewData(new ArrayList());
                        LiveCalendarActivity.this.recyclerViewAdapter.setEmptyView(LiveCalendarActivity.this.emptyView);
                        LiveCalendarActivity.this.emptyView.uploadEmptyViewResUi();
                    } else {
                        LiveCalendarActivity.this.recyclerViewAdapter.setNewData(list);
                    }
                    Log.d(LiveCalendarActivity.TAG, "onSuccess日历直播: --" + list.size());
                } catch (Exception e2) {
                    Log.e("Error", e2.getMessage());
                    LiveCalendarActivity.this.recyclerViewAdapter.setNewData(new ArrayList());
                    LiveCalendarActivity.this.recyclerViewAdapter.setEmptyView(LiveCalendarActivity.this.emptyView);
                    LiveCalendarActivity.this.emptyView.setLoadFileResUi(LiveCalendarActivity.this);
                }
            }
        });
    }

    private boolean isCurrentMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1) == year && calendar.get(2) + 1 == month;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.monthCalendar.toLastPager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        this.monthCalendar.toNextPager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        this.monthCalendar.toToday();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(int i2, int i3, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
        String str;
        Log.d(TAG, "init: 选中的日历变化了：----" + localDate.toString());
        if (i3 < 10) {
            str = i2 + "年0" + i3 + "月";
        } else {
            str = i2 + "年" + i3 + "月";
        }
        this.tv_result.setText(str);
        isCurrentMonth(i2, i3);
        Boolean bool = Boolean.TRUE;
        if (!bool.equals(this.monthHaveData.get(str))) {
            getLiveDate(localDate.toString());
            this.monthHaveData.put(str, bool);
        }
        getLiveList(localDate.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(CalendarState calendarState) {
        int i2 = AnonymousClass3.$SwitchMap$com$necer$enumeration$CalendarState[calendarState.ordinal()];
        if (i2 == 1) {
            Log.d(TAG, "onCalendarStateChange: 显示week日历");
            this.imgExpand.setImageDrawable(this.drawableExpand);
        } else if (i2 == 2) {
            this.imgExpand.setImageDrawable(this.drawableClose);
            Log.d(TAG, "onCalendarStateChange: 显示月日历");
        } else {
            if (i2 != 3) {
                return;
            }
            Log.d(TAG, "onCalendarStateChange: 拉伸中");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        getLiveList(getCurrentDate());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        LiveDataList liveDataList = this.recyclerViewAdapter.getData().get(i2);
        int i3 = AnonymousClass3.$SwitchMap$com$psychiatrygarden$utils$LiveStatus[TreeNodeUtilKt.getLivingStatus(liveDataList.getStart_live_time(), liveDataList.getEnd_live_time(), liveDataList.getVideo_id()).ordinal()];
        if (i3 == 1) {
            if ("0".equals(liveDataList.is_permission())) {
                Intent intent = new Intent(this, (Class<?>) ActCourseOrGoodsDetail.class);
                intent.putExtra("course_id", liveDataList.getCourse_id());
                startActivity(intent);
                return;
            } else if (!DateTimeUtilKt.timeWithinHalfAnHour(liveDataList.getStart_live_time())) {
                NewToast.showShort(this, getString(R.string.livingNoBegin));
                return;
            } else {
                if (TextUtils.isEmpty(liveDataList.getLive_id())) {
                    return;
                }
                CommonUtil.launchLiving(this, liveDataList.getUser_id(), liveDataList.getApp_id(), liveDataList.getApp_secret(), liveDataList.getRoom_id(), liveDataList.getCourse_id(), liveDataList.getLive_id());
                return;
            }
        }
        if (i3 == 2) {
            NewToast.showShort(this, getString(R.string.livingCutting));
            return;
        }
        if (i3 == 3) {
            if ("0".equals(liveDataList.is_permission())) {
                Intent intent2 = new Intent(this, (Class<?>) ActCourseOrGoodsDetail.class);
                intent2.putExtra("course_id", liveDataList.getCourse_id());
                startActivity(intent2);
                return;
            } else {
                if (TextUtils.isEmpty(liveDataList.getLive_id())) {
                    return;
                }
                CommonUtil.launchLiving(this, liveDataList.getUser_id(), liveDataList.getApp_id(), liveDataList.getApp_secret(), liveDataList.getRoom_id(), liveDataList.getCourse_id(), liveDataList.getLive_id());
                return;
            }
        }
        if (i3 != 4) {
            return;
        }
        if ("0".equals(liveDataList.is_permission())) {
            Intent intent3 = new Intent(this, (Class<?>) ActCourseOrGoodsDetail.class);
            intent3.putExtra("course_id", liveDataList.getCourse_id());
            startActivity(intent3);
        } else {
            sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
            TreeNodeUtilKt.initWaitPlayList(liveDataList.getVideo_id(), liveDataList.getId(), liveDataList.getTitle(), "0", "2");
            NavigationUtilKt.goToAliPlayerVideoPlayActivity(this, liveDataList.getLive_id(), liveDataList.getCourse_id(), liveDataList.getId(), liveDataList.getCover(), "2");
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) LiveCalendarActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putLiveDate(List<LiveDateBean> data) {
        ArrayList arrayList = new ArrayList();
        if (data != null && !data.isEmpty()) {
            for (int i2 = 0; i2 < data.size(); i2++) {
                LiveDateBean liveDateBean = data.get(i2);
                String year_month = liveDateBean.getYear_month();
                this.monthHaveData.put(year_month, Boolean.TRUE);
                List<String> day = liveDateBean.getDay();
                if (day != null) {
                    for (int i3 = 0; i3 < day.size(); i3++) {
                        arrayList.add(year_month + "-" + day.get(i3));
                    }
                }
            }
        }
        updateCalendarTime(arrayList);
    }

    private void updateCalendarTime(List<String> data) {
        if (data != null && !data.isEmpty()) {
            this.calendarData.addAll(data);
        }
        ((InnerPainter) this.monthCalendar.getCalendarPainter()).setPointList(new ArrayList(this.calendarData));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.imgExpand = (ImageView) findViewById(R.id.imgExpand);
        ImageView imageView = (ImageView) findViewById(R.id.liveCalendarActionbarBack);
        imageView.setSelected(true);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ed
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12312c.lambda$init$0(view);
            }
        });
        ((TextView) findViewById(R.id.liveCalendarActionbarTitle)).setText("直播日历");
        this.tv_result = (TextView) findViewById(R.id.tv_result);
        this.monthCalendar = (NCalendar) findViewById(R.id.miui10Calendar);
        ImageView imageView2 = (ImageView) findViewById(R.id.liveCalendarPreMonth);
        ImageView imageView3 = (ImageView) findViewById(R.id.liveCalendarNextMonth);
        this.liveCalendarToday = (TextView) findViewById(R.id.liveCalendarToday);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12350c.lambda$init$1(view);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12439c.lambda$init$2(view);
            }
        });
        this.liveCalendarToday.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12474c.lambda$init$3(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.liveCalendarRecyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LiveCalendarAdapter liveCalendarAdapter = new LiveCalendarAdapter();
        this.recyclerViewAdapter = liveCalendarAdapter;
        this.recyclerView.setAdapter(liveCalendarAdapter);
        this.monthCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() { // from class: com.psychiatrygarden.activity.id
            @Override // com.necer.listener.OnCalendarChangedListener
            public final void onCalendarChange(int i2, int i3, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
                this.f12507a.lambda$init$4(i2, i3, localDate, dateChangeBehavior);
            }
        });
        this.monthCalendar.setOnCalendarStateChangedListener(new OnCalendarStateChangedListener() { // from class: com.psychiatrygarden.activity.jd
            @Override // com.necer.listener.OnCalendarStateChangedListener
            public final void onCalendarStateChange(CalendarState calendarState) {
                this.f12556a.lambda$init$5(calendarState);
            }
        });
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.icon_live_calendar_close, R.attr.icon_live_calendar_expand});
        this.drawableClose = typedArrayObtainStyledAttributes.getDrawable(0);
        this.drawableExpand = typedArrayObtainStyledAttributes.getDrawable(1);
        typedArrayObtainStyledAttributes.recycle();
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.kd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12586c.lambda$init$6(view);
            }
        });
        this.recyclerViewAdapter.setEmptyView(this.emptyView);
        this.emptyView.uploadEmptyViewResUi();
        this.recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ld
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12663c.lambda$init$7(baseQuickAdapter, view, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_live_calendar);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
