package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.date.DatePattern;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.CustomStudyPlanActivity;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.bean.StudyPlanListBean;
import com.psychiatrygarden.config.DailyTaskTimeConfig;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CustomPlanEvent;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.PopCustomPlanFilter;
import com.psychiatrygarden.widget.QuestionYearFilterDialog;
import com.psychiatrygarden.widget.ScoreTrendInfoDialog;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CustomStudyPlanActivity extends BaseActivity implements View.OnClickListener {
    private int allDays;
    private String chapter_ids;
    private int dayQuestion;
    private String end_time;
    private EditText etPlanName;
    private boolean isRest;
    private TextView mBtnSave;
    private LinearLayout mLlChapters;
    private LinearLayout mLlEndTime;
    private LinearLayout mLlExamYear;
    private LinearLayout mLlReminderTime;
    private LinearLayout mLlStartTime;
    private LinearLayout mLlTestFilter;
    private TextView mTvChapters;
    private TextView mTvEndTime;
    private TextView mTvExamYear;
    private TextView mTvReminderTime;
    private TextView mTvStartTime;
    private TextView mTvTestFilter;
    private View mViewExamYearLine;
    private View mViewLine;
    private String name;
    private String node_ids;
    private String notice_time;
    private String part_ids;
    private OptionsPickerView pvCustomOptions;
    private TimePickerView pvCustomTime;
    private String questionCount;
    private String scoreDescTwo;
    private String selectChildIdList;
    private String selectNodeCount;
    private String start_time;
    private Switch switch_notification;
    private String title;
    private TextView tvFilterResult;
    private List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearList;
    private String year_status;
    private String mChooseChapterIds = "";
    private ArrayList<ChartFilterBean> chartFilterList = new ArrayList<>();
    private ArrayList<String> cardItem = new ArrayList<>();
    private String type = "";
    private String year = "";
    private ArrayMap<String, List<String>> selectMap = new ArrayMap<>();
    private int defaultSelect = 1;

    /* renamed from: com.psychiatrygarden.activity.CustomStudyPlanActivity$11, reason: invalid class name */
    public class AnonymousClass11 extends AjaxCallBack<String> {
        public AnonymousClass11() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(ChartFilterBean chartFilterBean, ChartFilterBean.ChartFilterValue chartFilterValue) {
            chartFilterValue.setType(chartFilterBean.getType());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$1(final ChartFilterBean chartFilterBean) {
            chartFilterBean.getValue().forEach(new Consumer() { // from class: com.psychiatrygarden.activity.s8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CustomStudyPlanActivity.AnonymousClass11.lambda$onSuccess$0(chartFilterBean, (ChartFilterBean.ChartFilterValue) obj);
                }
            });
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass11) t2);
            try {
                JSONObject jSONObjectOptJSONObject = new JSONObject(t2).optJSONObject("data");
                List list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("list"), new TypeToken<List<ChartFilterBean>>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.11.1
                }.getType());
                if (Build.VERSION.SDK_INT >= 24) {
                    list.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.r8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            CustomStudyPlanActivity.AnonymousClass11.lambda$onSuccess$1((ChartFilterBean) obj);
                        }
                    });
                }
                CustomStudyPlanActivity.this.chartFilterList.addAll(list);
                CustomStudyPlanActivity.this.showFilterPoP();
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.CustomStudyPlanActivity$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            CustomStudyPlanActivity.this.toRedoTask();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass4) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    ToastUtil.shortToast(CustomStudyPlanActivity.this, jSONObject.optString("message"));
                } else if (jSONObject.optJSONObject("data").optString("status").equals("1")) {
                    new XPopup.Builder(CustomStudyPlanActivity.this).asCustom(new DeleteDownloadDialog(CustomStudyPlanActivity.this, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.t8
                        @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                        public final void mClickIml() {
                            this.f13946a.lambda$onSuccess$0();
                        }
                    }, new SpannableStringBuilder("当前有正在进行规划，是否确定结束当前规划，并制定新规划？"), "温馨提示", "取消", "确定")).show();
                } else {
                    CustomStudyPlanActivity.this.drawUpStydyPlan(false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void checkStudyPlanInProgress() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanInProgress, new AjaxParams(), new AnonymousClass4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawUpStydyPlan(boolean repost) {
        if (!repost) {
            CustomCreateStudyPlanAct.newIntent(this, this.allDays + "", this.start_time, this.end_time, this.questionCount, this.dayQuestion + "");
        }
        AjaxParams ajaxParams = new AjaxParams();
        String str = this.part_ids;
        if (str == null) {
            str = "";
        }
        ajaxParams.put("part_ids", str);
        String str2 = this.chapter_ids;
        if (str2 == null) {
            str2 = "";
        }
        ajaxParams.put("chapter_ids", str2);
        String str3 = this.node_ids;
        if (str3 == null) {
            str3 = "";
        }
        ajaxParams.put("node_ids", str3);
        ajaxParams.put("type", this.type);
        ajaxParams.put("year", this.year.equals("-1") ? "" : this.year);
        ajaxParams.put("name", this.name);
        ajaxParams.put(com.umeng.analytics.pro.d.f22694p, this.start_time);
        ajaxParams.put(com.umeng.analytics.pro.d.f22695q, this.end_time);
        ajaxParams.put(CommonParameter.notice, this.switch_notification.isChecked() ? "1" : "0");
        if (this.switch_notification.isChecked() && !TextUtils.isEmpty(this.notice_time)) {
            ajaxParams.put("notice_time", this.notice_time.replaceAll(" ", ""));
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanDrawUp, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    final JSONObject jSONObject = new JSONObject(s2);
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EventBus.getDefault().post(new CustomPlanEvent(jSONObject.optString("code"), jSONObject.optString("message")));
                        }
                    }, 600L);
                    if (jSONObject.optString("code").equals("200")) {
                        CustomStudyPlanActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String formatFilter(String input, String title) {
        String[] strArrSplit = input.split("/");
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            String str = strArrSplit[i2];
            if (i2 >= strArrSplit.length - 1 || !str.endsWith(title)) {
                arrayList.add(str);
            } else {
                arrayList.add(str.substring(0, str.length() - title.length()));
            }
        }
        return q2.a("/", arrayList);
    }

    private void getFilterData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionChartFilter, new AjaxParams(), new AnonymousClass11());
    }

    private void getPlanConfig() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanPlanConfig, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.16
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass16) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        CustomStudyPlanActivity.this.year_status = jSONObject.optJSONObject("data").optString("year_status");
                        if (CustomStudyPlanActivity.this.year_status.equals("1")) {
                            CustomStudyPlanActivity.this.mViewExamYearLine.setVisibility(0);
                            CustomStudyPlanActivity.this.mLlExamYear.setVisibility(0);
                        } else {
                            CustomStudyPlanActivity.this.mViewExamYearLine.setVisibility(8);
                            CustomStudyPlanActivity.this.mLlExamYear.setVisibility(8);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getQuestionCount() {
        AjaxParams ajaxParams = new AjaxParams();
        String str = this.part_ids;
        if (str == null) {
            str = "";
        }
        ajaxParams.put("part_ids", str);
        String str2 = this.chapter_ids;
        if (str2 == null) {
            str2 = "";
        }
        ajaxParams.put("chapter_ids", str2);
        String str3 = this.node_ids;
        if (str3 == null) {
            str3 = "";
        }
        ajaxParams.put("node_ids", str3);
        ajaxParams.put("type", this.type);
        ajaxParams.put("year", this.year.equals("-1") ? "" : this.year);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanQuestionCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.15
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass15) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        CustomStudyPlanActivity.this.questionCount = jSONObject.optJSONObject("data").optString("count");
                        CustomStudyPlanActivity.this.tvFilterResult.setText("筛选出" + CustomStudyPlanActivity.this.questionCount + "题");
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void getStydyList() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.userStudyPlanList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    StudyPlanListBean studyPlanListBean = (StudyPlanListBean) new Gson().fromJson(s2, StudyPlanListBean.class);
                    if (studyPlanListBean.getCode().equals("200")) {
                        CustomStudyPlanActivity.this.scoreDescTwo = studyPlanListBean.getData().getRule();
                        CustomStudyPlanActivity.this.title = studyPlanListBean.getData().getTitle();
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void getYearList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("field", "year");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                JSONArray jSONArrayOptJSONArray;
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200") || (jSONArrayOptJSONArray = jSONObject.optJSONArray("data")) == null) {
                        return;
                    }
                    CustomStudyPlanActivity.this.yearList = (List) new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO>>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.2.1
                    }.getType());
                } catch (Exception unused) {
                }
            }
        });
    }

    private void initCustomOptionPicker() {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        OptionsPickerBuilder dividerColor = new OptionsPickerBuilder(this, new OnOptionsSelectListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.8
            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
            public void onOptionsSelect(int options1, int option2, int options3, View v2) {
                CustomStudyPlanActivity.this.defaultSelect = options1;
                CustomStudyPlanActivity customStudyPlanActivity = CustomStudyPlanActivity.this;
                customStudyPlanActivity.notice_time = (String) customStudyPlanActivity.cardItem.get(options1);
                CustomStudyPlanActivity.this.mTvReminderTime.setText("每天" + CustomStudyPlanActivity.this.notice_time);
            }
        }).setLayoutRes(R.layout.pickview_custom_time, new CustomListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.7
            @Override // com.bigkoo.pickerview.listener.CustomListener
            public void customLayout(View v2) {
                TextView textView = (TextView) v2.findViewById(R.id.tv_finish);
                ImageView imageView = (ImageView) v2.findViewById(R.id.iv_close);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.7.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v3) {
                        CustomStudyPlanActivity.this.pvCustomOptions.returnData();
                        CustomStudyPlanActivity.this.pvCustomOptions.dismiss();
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.7.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v3) {
                        CustomStudyPlanActivity.this.pvCustomOptions.dismiss();
                    }
                });
            }
        }).setContentTextSize(16).setLineSpacingMultiplier(3.5f).isCenterLabel(false).setDividerColor(16777215);
        if (1 == SkinManager.getCurrentSkinType(this)) {
            resources = getResources();
            i2 = R.color.first_txt_color_night;
        } else {
            resources = getResources();
            i2 = R.color.first_txt_color;
        }
        OptionsPickerBuilder textColorCenter = dividerColor.setTextColorCenter(resources.getColor(i2));
        if (1 == SkinManager.getCurrentSkinType(this)) {
            resources2 = getResources();
            i3 = R.color.second_txt_color_night;
        } else {
            resources2 = getResources();
            i3 = R.color.second_txt_color;
        }
        OptionsPickerView optionsPickerViewBuild = textColorCenter.setTextColorOut(resources2.getColor(i3)).setBgColor(0).setOutSideCancelable(true).setSelectOptions(this.defaultSelect).build();
        this.pvCustomOptions = optionsPickerViewBuild;
        optionsPickerViewBuild.setPicker(this.cardItem);
        this.pvCustomOptions.show();
    }

    private void initCustomTimePicker(final String timeType) {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        Calendar dateToCalendar = timeType.equals("start") ? CommonUtil.parseDateToCalendar(this.start_time) : TextUtils.isEmpty(this.end_time) ? Calendar.getInstance() : CommonUtil.parseDateToCalendar(this.end_time);
        Calendar calendar = Calendar.getInstance();
        calendar.set(CommonUtil.getTodayDate(DatePattern.NORM_YEAR_PATTERN) - 5, 1, CommonUtil.getTodayDate("dd"));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(CommonUtil.getTodayDate(DatePattern.NORM_YEAR_PATTERN) + 20, 1, CommonUtil.getTodayDate("dd"));
        TimePickerBuilder timePickerBuilderIsAlphaGradient = new TimePickerBuilder(this, new OnTimeSelectListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.10
            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
            public void onTimeSelect(Date date, View v2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (!timeType.equals("start")) {
                    CustomStudyPlanActivity customStudyPlanActivity = CustomStudyPlanActivity.this;
                    if (CommonUtil.validateEndTime(customStudyPlanActivity, customStudyPlanActivity.start_time, simpleDateFormat.format(date))) {
                        if (CommonUtil.convertDateToTimestamp(simpleDateFormat.format(date)) < CommonUtil.convertDateToTimestamp(CommonUtil.getTodayDate())) {
                            ToastUtil.shortToast(CustomStudyPlanActivity.this.mContext, "结束时间必须大于当前时间");
                            return;
                        }
                        CustomStudyPlanActivity.this.end_time = simpleDateFormat.format(date);
                        CustomStudyPlanActivity.this.mTvEndTime.setText(CustomStudyPlanActivity.this.end_time);
                        return;
                    }
                    return;
                }
                if (TextUtils.isEmpty(CustomStudyPlanActivity.this.end_time)) {
                    if (CommonUtil.convertDateToTimestamp(simpleDateFormat.format(date)) < CommonUtil.convertDateToTimestamp(CommonUtil.getTodayDate())) {
                        ToastUtil.shortToast(CustomStudyPlanActivity.this.mContext, "开始时间不能小于当前时间");
                        return;
                    }
                    CustomStudyPlanActivity.this.start_time = simpleDateFormat.format(date);
                    CustomStudyPlanActivity.this.mTvStartTime.setText(CustomStudyPlanActivity.this.start_time);
                    return;
                }
                if (CommonUtil.validateEndTime(CustomStudyPlanActivity.this, simpleDateFormat.format(date), CustomStudyPlanActivity.this.end_time)) {
                    long jConvertDateToTimestamp = CommonUtil.convertDateToTimestamp(CustomStudyPlanActivity.this.start_time);
                    String str = simpleDateFormat.format(date);
                    if (CommonUtil.convertDateToTimestamp(str) < jConvertDateToTimestamp) {
                        ToastUtil.shortToast(CustomStudyPlanActivity.this.mContext, "开始时间不能小于当前时间");
                    } else {
                        CustomStudyPlanActivity.this.start_time = str;
                        CustomStudyPlanActivity.this.mTvStartTime.setText(CustomStudyPlanActivity.this.start_time);
                    }
                }
            }
        }).setDate(dateToCalendar).setRangDate(calendar, calendar2).setLayoutRes(R.layout.layout_custom_time_picker, new CustomListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.9
            @Override // com.bigkoo.pickerview.listener.CustomListener
            public void customLayout(View v2) {
                TextView textView = (TextView) v2.findViewById(R.id.tv_finish);
                TextView textView2 = (TextView) v2.findViewById(R.id.tv_cancel);
                TextView textView3 = (TextView) v2.findViewById(R.id.tv_select_time_title);
                if (timeType.equals("end")) {
                    textView3.setText("选择规划结束时间");
                }
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.9.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v3) throws ParseException {
                        CustomStudyPlanActivity.this.pvCustomTime.returnData();
                        CustomStudyPlanActivity.this.pvCustomTime.dismiss();
                    }
                });
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.9.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v3) {
                        CustomStudyPlanActivity.this.pvCustomTime.dismiss();
                    }
                });
            }
        }).setContentTextSize(16).setType(new boolean[]{true, true, true, false, false, false}).setLabel("年", "月", "日", "时", "分", "秒").setLineSpacingMultiplier(3.5f).setTextXOffset(0, 0, 0, 0, 0, 0).isCenterLabel(false).setDividerColor(16777215).isAlphaGradient(false);
        if (1 == SkinManager.getCurrentSkinType(this)) {
            resources = getResources();
            i2 = R.color.first_txt_color_night;
        } else {
            resources = getResources();
            i2 = R.color.first_txt_color;
        }
        TimePickerBuilder textColorCenter = timePickerBuilderIsAlphaGradient.setTextColorCenter(resources.getColor(i2));
        if (1 == SkinManager.getCurrentSkinType(this)) {
            resources2 = getResources();
            i3 = R.color.second_txt_color_night;
        } else {
            resources2 = getResources();
            i3 = R.color.second_txt_color;
        }
        TimePickerView timePickerViewBuild = textColorCenter.setTextColorOut(resources2.getColor(i3)).setBgColor(getResources().getColor(R.color.transparent)).setItemVisibleCount(5).build();
        this.pvCustomTime = timePickerViewBuild;
        timePickerViewBuild.show();
    }

    private void initData() {
        this.tvFilterResult.setText("筛选出0题");
        SharePreferencesUtils.writeStrConfig("FILTER_YEAR_TO_QUETION_DATACustomStudyPlan", "", this);
        this.cardItem = DailyTaskTimeConfig.getDailyTaskTimeRange();
        String todayDate = CommonUtil.getTodayDate();
        this.start_time = todayDate;
        this.mTvStartTime.setText(todayDate);
        if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            this.mBtnSave.setBackgroundResource(R.drawable.shape_app_theme_corners_yellow_12);
            this.mBtnSave.setText("开启会员 定制个人专属规划");
            this.mBtnSave.setTextColor(getResources().getColor(1 == SkinManager.getCurrentSkinType(this) ? R.color.first_txt_color_night : R.color.first_txt_color));
        } else {
            this.mBtnSave.setBackgroundResource(R.drawable.shape_app_theme_corners_12);
            this.mBtnSave.setText("保存");
            this.mBtnSave.setTextColor(getResources().getColor(1 == SkinManager.getCurrentSkinType(this) ? R.color.tran_img_colors : R.color.white));
        }
        getStydyList();
        getYearList();
        getPlanConfig();
    }

    private void initViews() {
        setTitle("刷题规划");
        this.isRest = getIntent().getBooleanExtra("isRest", false);
        this.mLlExamYear = (LinearLayout) findViewById(R.id.ll_exam_year);
        this.mViewExamYearLine = findViewById(R.id.view_exam_year_line);
        this.mTvExamYear = (TextView) findViewById(R.id.tv_exam_year);
        this.mLlChapters = (LinearLayout) findViewById(R.id.ll_chapters);
        this.mTvChapters = (TextView) findViewById(R.id.tv_chapters);
        this.mLlTestFilter = (LinearLayout) findViewById(R.id.ll_test_filter);
        this.mTvTestFilter = (TextView) findViewById(R.id.tv_test_filter);
        this.mLlStartTime = (LinearLayout) findViewById(R.id.ll_start_time);
        this.mLlReminderTime = (LinearLayout) findViewById(R.id.ll_reminder_time);
        this.mTvReminderTime = (TextView) findViewById(R.id.tv_reminder_time);
        this.mTvStartTime = (TextView) findViewById(R.id.tv_start_time);
        this.mLlEndTime = (LinearLayout) findViewById(R.id.ll_end_time);
        this.mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        this.etPlanName = (EditText) findViewById(R.id.et_plan_name);
        this.mBtnSave = (TextView) findViewById(R.id.btn_save);
        this.switch_notification = (Switch) findViewById(R.id.switch_notification);
        this.tvFilterResult = (TextView) findViewById(R.id.tv_filter_result);
        this.mViewLine = findViewById(R.id.view_line);
        this.mLlExamYear.setOnClickListener(this);
        this.mLlChapters.setOnClickListener(this);
        this.mLlTestFilter.setOnClickListener(this);
        this.mLlStartTime.setOnClickListener(this);
        this.mLlReminderTime.setOnClickListener(this);
        this.mLlEndTime.setOnClickListener(this);
        this.mBtnSave.setOnClickListener(this);
        this.iv_right_img.setImageResource(1 == SkinManager.getCurrentSkinType(this) ? R.mipmap.ic_personal_help_center_night : R.mipmap.ic_personal_help_center);
        this.iv_right_img.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_right_img.setEnabled(true);
        this.iv_right_img.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (CommonUtil.isFastClick() || CustomStudyPlanActivity.this.scoreDescTwo.isEmpty()) {
                    return;
                }
                CustomStudyPlanActivity customStudyPlanActivity = CustomStudyPlanActivity.this;
                new XPopup.Builder(CustomStudyPlanActivity.this).popupAnimation(null).asCustom(new ScoreTrendInfoDialog(customStudyPlanActivity, customStudyPlanActivity.scoreDescTwo, CustomStudyPlanActivity.this.title, true)).show();
            }
        });
        this.switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.o8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13073c.lambda$initViews$0(compoundButton, z2);
            }
        });
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.p8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13534c.lambda$initViews$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(CompoundButton compoundButton, boolean z2) {
        if (z2) {
            this.mViewLine.setVisibility(0);
            this.mLlReminderTime.setVisibility(0);
        } else {
            this.mViewLine.setVisibility(8);
            this.mLlReminderTime.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (this.isRest) {
            NavigationUtilKt.gotoStudyPlanListActivity(this);
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showMinQuestionDialog$2() {
    }

    public static ArrayMap<String, List<String>> mapConvert(Map<String, String> input) {
        ArrayMap<String, List<String>> arrayMap = new ArrayMap<>();
        for (Map.Entry<String, String> entry : input.entrySet()) {
            arrayMap.put(entry.getKey(), Arrays.asList(entry.getValue().split("\\s*,\\s*")));
        }
        return arrayMap;
    }

    public static void navigationToCustomStudyPlanActivity(Context activity) {
        activity.startActivity(new Intent(activity, (Class<?>) CustomStudyPlanActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFilterPoP() {
        new XPopup.Builder(this).asCustom(new PopCustomPlanFilter(this.mContext, this.chartFilterList, "", this.selectMap, new PopCustomPlanFilter.ConfirmListener() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.12
            @Override // com.psychiatrygarden.widget.PopCustomPlanFilter.ConfirmListener
            public void onConfirm(@NonNull Map<String, String> params) {
                if (Build.VERSION.SDK_INT >= 24) {
                    ArrayList arrayList = new ArrayList();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        Iterator it = CustomStudyPlanActivity.this.chartFilterList.iterator();
                        while (it.hasNext()) {
                            ChartFilterBean chartFilterBean = (ChartFilterBean) it.next();
                            if (chartFilterBean.getType().equals(entry.getKey())) {
                                if (entry.getValue().contains(",")) {
                                    String[] strArrSplit = entry.getValue().split(",");
                                    ArrayList arrayList2 = new ArrayList();
                                    for (String str : strArrSplit) {
                                        for (int i2 = 0; i2 < chartFilterBean.getValue().size(); i2++) {
                                            ChartFilterBean.ChartFilterValue chartFilterValue = chartFilterBean.getValue().get(i2);
                                            if (chartFilterValue.getKey().equals(str)) {
                                                arrayList2.add(chartFilterValue.getName() + chartFilterValue.getTitle());
                                            }
                                        }
                                    }
                                    String strA = q2.a("/", arrayList2);
                                    arrayList.add(CustomStudyPlanActivity.this.formatFilter(strA, strA.substring(strA.length() - 1)));
                                } else {
                                    for (ChartFilterBean.ChartFilterValue chartFilterValue2 : chartFilterBean.getValue()) {
                                        if (chartFilterValue2.getKey().equals(entry.getValue())) {
                                            arrayList.add(chartFilterValue2.getName() + chartFilterValue2.getTitle());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (arrayList.isEmpty()) {
                        CustomStudyPlanActivity.this.mTvTestFilter.setText("选择考点");
                        CustomStudyPlanActivity.this.type = "";
                        CustomStudyPlanActivity.this.selectMap.clear();
                        CustomStudyPlanActivity.this.getQuestionCount();
                        return;
                    }
                    CustomStudyPlanActivity.this.mTvTestFilter.setText(q2.a(" ", arrayList));
                    CustomStudyPlanActivity.this.type = new Gson().toJson(params);
                    CustomStudyPlanActivity.this.selectMap = CustomStudyPlanActivity.mapConvert(params);
                    CustomStudyPlanActivity.this.getQuestionCount();
                }
            }
        })).show();
    }

    private void showFilterYearDialog() {
        String strConfig = SharePreferencesUtils.readStrConfig("FILTER_YEAR_TO_QUETION_DATACustomStudyPlan", this, "");
        if (!TextUtils.isEmpty(strConfig)) {
            this.yearList = (List) new Gson().fromJson(strConfig, new TypeToken<List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO>>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.13
            }.getType());
        }
        final QuestionYearFilterDialog questionYearFilterDialog = new QuestionYearFilterDialog(this.mContext, this.yearList);
        questionYearFilterDialog.setOnItemActionLisenter(new QuestionYearFilterDialog.OnItemClickLisenter() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.14
            @Override // com.psychiatrygarden.widget.QuestionYearFilterDialog.OnItemClickLisenter
            public void setOnItemClick(String value, List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearData, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item) {
                try {
                    SharePreferencesUtils.writeStrConfig("FILTER_YEAR_TO_QUETION_DATACustomStudyPlan", new Gson().toJson(yearData), CustomStudyPlanActivity.this);
                    if (item == null) {
                        questionYearFilterDialog.dismissNoAnimaltion();
                        return;
                    }
                    if (item.getId().equals("free_year")) {
                        CustomStudyPlanActivity.this.year = value;
                    } else {
                        CustomStudyPlanActivity.this.year = item.getId();
                    }
                    CustomStudyPlanActivity.this.mTvExamYear.setText(value);
                    questionYearFilterDialog.dismissNoAnimaltion();
                    CustomStudyPlanActivity.this.getQuestionCount();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        questionYearFilterDialog.show();
    }

    private void showMinQuestionDialog() {
        new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.q8
            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
            public final void mClickIml() {
                CustomStudyPlanActivity.lambda$showMinQuestionDialog$2();
            }
        }, new SpannableStringBuilder("检测到每日试题量≤1题，这样的互动深度可能难以达到刷题效果，请重新设置~"), "温馨提示", "", "确定")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toRedoTask() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanRedoEveryTask, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CustomStudyPlanActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_DAILY_TASK);
                        CustomStudyPlanActivity.this.drawUpStydyPlan(false);
                    } else {
                        ToastUtil.shortToast(CustomStudyPlanActivity.this.mContext, jSONObject.optString("message"));
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        initViews();
        initData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == -1 && data != null) {
            this.selectNodeCount = data.getStringExtra("selectNodeCount");
            this.part_ids = data.getStringExtra("part_ids");
            this.chapter_ids = data.getStringExtra("chapter_ids");
            this.node_ids = data.getStringExtra("node_ids");
            this.selectChildIdList = data.getStringExtra("selectChildIdList");
            getQuestionCount();
            if (TextUtils.isEmpty(this.selectNodeCount)) {
                return;
            }
            this.mTvChapters.setText("已选择" + this.selectNodeCount + "个章节");
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws NumberFormatException {
        CommonUtil.hideKeyboard(this);
        switch (v2.getId()) {
            case R.id.btn_save /* 2131362387 */:
                if (!FastClickUtil.isFastClick()) {
                    if (!UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                        String strTrim = this.etPlanName.getText().toString().trim();
                        this.name = strTrim;
                        if (!TextUtils.isEmpty(strTrim)) {
                            if (!this.year_status.equals("1") || !TextUtils.isEmpty(this.year)) {
                                if (!TextUtils.isEmpty(this.selectNodeCount)) {
                                    if (!TextUtils.isEmpty(this.end_time)) {
                                        if (!TextUtils.isEmpty(this.notice_time) || !this.switch_notification.isChecked()) {
                                            this.allDays = CommonUtil.getDaysBetween(this.start_time, this.end_time) + 1;
                                            int i2 = Integer.parseInt(this.questionCount);
                                            int i3 = this.allDays;
                                            this.dayQuestion = i2 / i3;
                                            if (i3 >= 0) {
                                                if (!TextUtils.isEmpty(this.questionCount) && !this.questionCount.equals("0") && Integer.parseInt(this.questionCount) >= this.allDays) {
                                                    checkStudyPlanInProgress();
                                                    break;
                                                } else {
                                                    showMinQuestionDialog();
                                                    break;
                                                }
                                            } else {
                                                ToastUtil.shortToast(this, "结束时间必须大于开始时间");
                                                break;
                                            }
                                        } else {
                                            ToastUtil.shortToast(this, "请选择提醒时间");
                                            break;
                                        }
                                    } else {
                                        ToastUtil.shortToast(this, "请选择结束时间");
                                        break;
                                    }
                                } else {
                                    ToastUtil.shortToast(this, "请选择章节");
                                    break;
                                }
                            } else {
                                ToastUtil.shortToast(this, "请选择试题年份");
                                break;
                            }
                        } else {
                            ToastUtil.shortToast(this, "请输入规划名称");
                            break;
                        }
                    } else {
                        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) MemberCenterActivity.class));
                        break;
                    }
                }
                break;
            case R.id.iv_right_img /* 2131364209 */:
                if (!CommonUtil.isFastClick() && !this.scoreDescTwo.isEmpty()) {
                    new XPopup.Builder(this).popupAnimation(null).asCustom(new ScoreTrendInfoDialog(this, this.scoreDescTwo, this.title, true)).show();
                    break;
                }
                break;
            case R.id.ll_chapters /* 2131364747 */:
                Intent intentPutExtra = new Intent(this, (Class<?>) KnowledgeListEditZuTiActivity.class).putExtra("studyPlanSelect", true);
                if (!TextUtils.isEmpty(this.part_ids)) {
                    intentPutExtra.putExtra("part_ids", this.part_ids);
                }
                if (!TextUtils.isEmpty(this.chapter_ids)) {
                    intentPutExtra.putExtra("chapter_ids", this.chapter_ids);
                }
                if (!TextUtils.isEmpty(this.node_ids)) {
                    intentPutExtra.putExtra("node_ids", this.node_ids);
                }
                if (!TextUtils.isEmpty(this.selectChildIdList)) {
                    intentPutExtra.putExtra("selectChildIdList", this.selectChildIdList);
                }
                intentPutExtra.putExtra("selectNodeCount", this.selectNodeCount);
                startActivityForResult(intentPutExtra, 1001);
                break;
            case R.id.ll_end_time /* 2131364790 */:
                initCustomTimePicker("end");
                break;
            case R.id.ll_exam_year /* 2131364792 */:
                if (!CommonUtil.isFastClick()) {
                    showFilterYearDialog();
                    break;
                }
                break;
            case R.id.ll_reminder_time /* 2131364868 */:
                initCustomOptionPicker();
                break;
            case R.id.ll_start_time /* 2131364894 */:
                initCustomTimePicker("start");
                break;
            case R.id.ll_test_filter /* 2131364907 */:
                if (!CommonUtil.isFastClick()) {
                    if (!this.chartFilterList.isEmpty()) {
                        showFilterPoP();
                        break;
                    } else {
                        getFilterData();
                        break;
                    }
                }
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_REPOST_CUSTOM_PLAN)) {
            drawUpStydyPlan(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        if (this.isRest) {
            NavigationUtilKt.gotoStudyPlanListActivity(this);
        }
        finish();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_cuscom_study_plan);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public static void navigationToCustomStudyPlanActivity(Context activity, boolean isRest) {
        Intent intent = new Intent(activity, (Class<?>) CustomStudyPlanActivity.class);
        intent.putExtra("isRest", isRest);
        activity.startActivity(intent);
    }
}
