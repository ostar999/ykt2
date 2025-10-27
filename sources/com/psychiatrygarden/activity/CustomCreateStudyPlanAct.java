package com.psychiatrygarden.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.Key;
import cn.hutool.core.date.DatePattern;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.knowledge.DailyTasksActivity;
import com.psychiatrygarden.event.CustomPlanEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class CustomCreateStudyPlanAct extends BaseActivity {
    private AnimationDrawable animationDrawable;
    private String count;
    private String countDesc;
    private String day;
    private String dayCount;
    private String dayCountDesc;
    private String dayDesc;
    private TextView mBtnCreate;
    private ImageView mImgBack;
    private ImageView mImgLoading;
    private ImageView mImgOutCircle;
    private LinearLayout mLyCount;
    private LinearLayout mLyDay;
    private LinearLayout mLyDayCount;
    private View mTabbar;
    private TextView mTvCount;
    private TextView mTvCountInfo;
    private TextView mTvDay;
    private TextView mTvDayCount;
    private TextView mTvDayCountInfo;
    private TextView mTvDayInfo;
    private TextView mTvTitle;
    private ObjectAnimator rotateAnim;
    private String title;
    private String statusCode = "";
    private String message = "";
    private int currentIndex = 0;
    private int currentDayIndex = 0;
    private int currentDayDescIndex = 0;
    private int currentCountIndex = 0;
    private int currentCountDescIndex = 0;
    private int mDayCountIndex = 0;
    private int mDayCountDescIndex = 0;
    private int textHandlerDelatMillis = 50;
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.CustomCreateStudyPlanAct.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what != 1 || CustomCreateStudyPlanAct.this.mTvTitle == null) {
                return;
            }
            if (CustomCreateStudyPlanAct.this.currentIndex <= CustomCreateStudyPlanAct.this.title.length()) {
                CustomCreateStudyPlanAct.this.mTvTitle.setText(CustomCreateStudyPlanAct.this.title.substring(0, CustomCreateStudyPlanAct.this.currentIndex));
                CustomCreateStudyPlanAct.access$108(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (CustomCreateStudyPlanAct.this.currentDayIndex <= CustomCreateStudyPlanAct.this.day.length()) {
                if (CustomCreateStudyPlanAct.this.mLyDay.getVisibility() == 8) {
                    CustomCreateStudyPlanAct.this.mLyDay.setVisibility(0);
                }
                CustomCreateStudyPlanAct.this.mTvDay.setText(CustomCreateStudyPlanAct.this.day.substring(0, CustomCreateStudyPlanAct.this.currentDayIndex));
                CustomCreateStudyPlanAct.access$508(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (CustomCreateStudyPlanAct.this.currentDayDescIndex <= CustomCreateStudyPlanAct.this.dayDesc.length()) {
                CustomCreateStudyPlanAct.this.mTvDayInfo.setText(CustomCreateStudyPlanAct.this.dayDesc.substring(0, CustomCreateStudyPlanAct.this.currentDayDescIndex));
                CustomCreateStudyPlanAct.access$908(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (CustomCreateStudyPlanAct.this.currentCountIndex < CustomCreateStudyPlanAct.this.count.length()) {
                if (CustomCreateStudyPlanAct.this.mLyCount.getVisibility() == 8) {
                    CustomCreateStudyPlanAct.this.mLyCount.setVisibility(0);
                }
                CustomCreateStudyPlanAct.this.mTvCount.setText(CustomCreateStudyPlanAct.this.count.substring(0, CustomCreateStudyPlanAct.this.currentCountIndex));
                CustomCreateStudyPlanAct.access$1208(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (CustomCreateStudyPlanAct.this.currentCountDescIndex < CustomCreateStudyPlanAct.this.countDesc.length()) {
                CustomCreateStudyPlanAct.this.mTvCountInfo.setText(CustomCreateStudyPlanAct.this.countDesc.substring(0, CustomCreateStudyPlanAct.this.currentCountDescIndex));
                CustomCreateStudyPlanAct.access$1608(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (CustomCreateStudyPlanAct.this.mDayCountIndex < CustomCreateStudyPlanAct.this.dayCount.length()) {
                if (CustomCreateStudyPlanAct.this.mLyDayCount.getVisibility() == 8) {
                    CustomCreateStudyPlanAct.this.mLyDayCount.setVisibility(0);
                }
                CustomCreateStudyPlanAct.this.mTvDayCount.setText(CustomCreateStudyPlanAct.this.dayCount.substring(0, CustomCreateStudyPlanAct.this.mDayCountIndex));
                CustomCreateStudyPlanAct.access$1908(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (CustomCreateStudyPlanAct.this.mDayCountDescIndex < CustomCreateStudyPlanAct.this.dayCountDesc.length()) {
                CustomCreateStudyPlanAct.this.mTvDayCountInfo.setText(CustomCreateStudyPlanAct.this.dayCountDesc.substring(0, CustomCreateStudyPlanAct.this.mDayCountDescIndex));
                CustomCreateStudyPlanAct.access$2308(CustomCreateStudyPlanAct.this);
                CustomCreateStudyPlanAct.this.mHandler.sendEmptyMessageDelayed(1, CustomCreateStudyPlanAct.this.textHandlerDelatMillis);
                return;
            }
            if (!TextUtils.isEmpty(CustomCreateStudyPlanAct.this.message)) {
                CustomCreateStudyPlanAct customCreateStudyPlanAct = CustomCreateStudyPlanAct.this;
                ToastUtil.shortToast(customCreateStudyPlanAct, customCreateStudyPlanAct.message);
                CustomCreateStudyPlanAct.this.message = "";
            }
            CustomCreateStudyPlanAct.this.mBtnCreate.setVisibility(0);
            CustomCreateStudyPlanAct.this.mHandler.removeCallbacksAndMessages(null);
        }
    };

    public static /* synthetic */ int access$108(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.currentIndex;
        customCreateStudyPlanAct.currentIndex = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$1208(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.currentCountIndex;
        customCreateStudyPlanAct.currentCountIndex = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$1608(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.currentCountDescIndex;
        customCreateStudyPlanAct.currentCountDescIndex = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$1908(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.mDayCountIndex;
        customCreateStudyPlanAct.mDayCountIndex = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$2308(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.mDayCountDescIndex;
        customCreateStudyPlanAct.mDayCountDescIndex = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$508(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.currentDayIndex;
        customCreateStudyPlanAct.currentDayIndex = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$908(CustomCreateStudyPlanAct customCreateStudyPlanAct) {
        int i2 = customCreateStudyPlanAct.currentDayDescIndex;
        customCreateStudyPlanAct.currentDayDescIndex = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (this.statusCode.isEmpty()) {
            ToastUtil.shortToast(this, "生成中，请稍后");
        }
        if (this.statusCode.equals("200")) {
            this.animationDrawable.stop();
            startActivity(new Intent(this.mContext, (Class<?>) DailyTasksActivity.class));
            finish();
        }
        if (this.statusCode.equals(Constants.DEFAULT_UIN)) {
            showMinQuestionDialog();
            return;
        }
        this.currentIndex = 0;
        this.currentDayIndex = 0;
        this.currentDayDescIndex = 0;
        this.currentCountIndex = 0;
        this.currentCountDescIndex = 0;
        this.mDayCountIndex = 0;
        this.mDayCountDescIndex = 0;
        this.mTvDayInfo.setText("");
        this.mTvTitle.setText("");
        this.mTvDay.setText("");
        this.mTvCount.setText("");
        this.mTvCountInfo.setText("");
        this.mTvDayCount.setText("");
        this.mTvDayCountInfo.setText("");
        this.mLyDay.setVisibility(8);
        this.mLyCount.setVisibility(8);
        this.mLyDayCount.setVisibility(8);
        this.mBtnCreate.setVisibility(8);
        this.mHandler.sendEmptyMessageDelayed(1, 100L);
        EventBus.getDefault().post(EventBusConstant.EVENT_REPOST_CUSTOM_PLAN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        this.animationDrawable.stop();
        this.rotateAnim.cancel();
        if (!this.statusCode.equals("200")) {
            finish();
        } else {
            startActivity(new Intent(this.mContext, (Class<?>) DailyTasksActivity.class));
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showMinQuestionDialog$2() {
    }

    public static void newIntent(Context context, String allDays, String startTime, String endTime, String allQuestion, String dayQuestion) {
        Intent intent = new Intent(context, (Class<?>) CustomCreateStudyPlanAct.class);
        intent.putExtra("allDays", allDays);
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
        intent.putExtra("allQuestion", allQuestion);
        intent.putExtra("dayQuestion", dayQuestion);
        context.startActivity(intent);
    }

    private void showMinQuestionDialog() {
        new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.n8
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
            public final void mClickIml() {
                CustomCreateStudyPlanAct.lambda$showMinQuestionDialog$2();
            }
        }, new SpannableStringBuilder("检测到每日试题量≤1题，这样的互动深度可能难以达到刷题效果，请重新设置~"), "温馨提示", "", "确定")).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("allDays");
        String stringExtra2 = getIntent().getStringExtra("startTime");
        String stringExtra3 = getIntent().getStringExtra("endTime");
        String stringExtra4 = getIntent().getStringExtra("allQuestion");
        String stringExtra5 = getIntent().getStringExtra("dayQuestion");
        this.title = "正在帮你生成一个刷题规划";
        this.day = "规划周期：" + stringExtra + "天 ";
        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtil.getTodayDate(DatePattern.NORM_YEAR_PATTERN));
        sb.append("");
        String string = sb.toString();
        String strConvertDateToYear = CommonUtil.convertDateToYear(stringExtra2);
        String strConvertDateToYear2 = CommonUtil.convertDateToYear(stringExtra3);
        Log.e("phone_dip", ";startTimeStr=" + strConvertDateToYear + "endTimeStr=>" + strConvertDateToYear2);
        String str = strConvertDateToYear.split("年")[0];
        String str2 = strConvertDateToYear2.split("年")[0];
        if (str.equals(string) && str2.equals(string)) {
            strConvertDateToYear = strConvertDateToYear.substring(5);
            strConvertDateToYear2 = strConvertDateToYear2.substring(5);
        }
        this.dayDesc = "你的规划从" + strConvertDateToYear + "至" + strConvertDateToYear2 + "结束，共" + stringExtra + "天 ";
        StringBuilder sb2 = new StringBuilder();
        sb2.append("题目数量：");
        sb2.append(stringExtra4);
        sb2.append("题 ");
        this.count = sb2.toString();
        this.countDesc = "根据您的试题筛选条件为您筛选出" + stringExtra4 + "题 ";
        this.dayCount = "平均每天：" + stringExtra5 + "道 ";
        this.dayCountDesc = "根据规划周期与试题数计算，每天大约需要刷题" + stringExtra5 + "道 ";
        this.mTabbar = findViewById(R.id.tabbar);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mImgLoading = (ImageView) findViewById(R.id.iv_loading);
        this.mImgOutCircle = (ImageView) findViewById(R.id.iv_out_anim);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mLyDay = (LinearLayout) findViewById(R.id.ly_day);
        this.mTvDay = (TextView) findViewById(R.id.tv_day);
        this.mTvDayInfo = (TextView) findViewById(R.id.tv_day_desc);
        this.mLyCount = (LinearLayout) findViewById(R.id.ly_count);
        this.mTvCount = (TextView) findViewById(R.id.tv_count);
        this.mTvCountInfo = (TextView) findViewById(R.id.tv_count_desc);
        this.mLyDayCount = (LinearLayout) findViewById(R.id.ly_day_count);
        this.mTvDayCount = (TextView) findViewById(R.id.tv_day_count);
        this.mTvDayCountInfo = (TextView) findViewById(R.id.tv_day_count_desc);
        TextView textView = (TextView) findViewById(R.id.btn_create);
        this.mBtnCreate = textView;
        textView.setText("生成中，请稍后");
        this.mBtnCreate.setBackgroundResource(R.drawable.shape_gray_color_no_night_round12);
        this.mBtnCreate.setTextColor(getResources().getColor(R.color.forth_txt_color));
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTabbar.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        this.mTabbar.setLayoutParams(layoutParams);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mImgOutCircle, Key.ROTATION, 360.0f, 0.0f);
        this.rotateAnim = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(1500L);
        this.rotateAnim.setRepeatCount(-1);
        this.rotateAnim.setInterpolator(new LinearInterpolator());
        this.rotateAnim.start();
        AnimationDrawable animationDrawable = (AnimationDrawable) this.mImgLoading.getBackground();
        this.animationDrawable = animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        this.mHandler.sendEmptyMessageDelayed(1, 100L);
        this.mBtnCreate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.l8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12658c.lambda$init$0(view);
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (!this.statusCode.equals("200")) {
            finish();
        } else {
            startActivity(new Intent(this.mContext, (Class<?>) DailyTasksActivity.class));
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        ObjectAnimator objectAnimator = this.rotateAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Subscribe
    public void onEventMainThread(CustomPlanEvent event) {
        this.statusCode = event.getCode();
        this.message = "";
        if (event.getCode().equals("200")) {
            this.mBtnCreate.setText("规划已生成，查看规划");
            this.mBtnCreate.setBackgroundResource(R.drawable.shape_main_theme_color_no_night_round12);
            this.mBtnCreate.setTextColor(getResources().getColor(R.color.new_bg_one_color));
        } else {
            if (!event.getCode().equals(Constants.DEFAULT_UIN)) {
                this.mBtnCreate.setText("规划生成失败，点击重新生成");
                this.mBtnCreate.setBackgroundResource(R.drawable.shape_main_theme_color_no_night_round12);
                this.mBtnCreate.setTextColor(getResources().getColor(R.color.new_bg_one_color));
                this.message = event.getMessage();
                return;
            }
            this.mBtnCreate.setText("规划生成失败");
            this.mBtnCreate.setBackgroundResource(R.drawable.shape_gray_color_no_night_round12);
            this.mBtnCreate.setTextColor(getResources().getColor(R.color.forth_txt_color));
            this.message = event.getMessage();
            showMinQuestionDialog();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.view_create_study_plan);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.m8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12691c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
