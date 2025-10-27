package com.psychiatrygarden.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.date.DatePattern;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.adapter.StudyPlanAdapter;
import com.psychiatrygarden.bean.StudyPlanListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.BuyVipSuccessEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.ScoreTrendInfoDialog;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class StudyPlanListActivity extends BaseActivity {
    private StudyPlanAdapter adapter;
    private TextView customPlanTv;
    private CardView cvCustomPlan;
    private String desc;
    private ImageView ivBack;
    private ImageView ivRightQa;
    private String planId;
    private StudyPlanListBean.DataBean.PlanInProgressBean planInProgressBean;
    private TimePickerView pvCustomTime;
    private RecyclerView rvStudyPlans;
    private String scoreDescTwo;
    private List<StudyPlanListBean.DataBean.SystemPlanBean> studyPlanList = new ArrayList();
    private StudyPlanListBean.DataBean.SystemPlanBean systemPlanBean;
    private String title;
    private TextView tvSubtitle;
    private TextView tvTitle;

    /* renamed from: com.psychiatrygarden.activity.StudyPlanListActivity$5, reason: invalid class name */
    public class AnonymousClass5 extends StudyPlanAdapter.setOnReceiveLisenter {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setItemClickAction$0(String str) {
            StudyPlanListActivity.this.planId = str;
            StudyPlanListActivity.this.initCustomTimePicker();
        }

        @Override // com.psychiatrygarden.adapter.StudyPlanAdapter.setOnReceiveLisenter
        public void setItemClickAction(final String plan_id) {
            if (StudyPlanListActivity.this.planInProgressBean.getId() != null) {
                new XPopup.Builder(StudyPlanListActivity.this).asCustom(new DeleteDownloadDialog(StudyPlanListActivity.this, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.xk
                    @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                    public final void mClickIml() {
                        this.f14188a.lambda$setItemClickAction$0(plan_id);
                    }
                }, new SpannableStringBuilder("已存在学习规划，是否领取并替换原规划？"), "温馨提示", "取消", "确定")).show();
            } else {
                StudyPlanListActivity.this.planId = plan_id;
                StudyPlanListActivity.this.initCustomTimePicker();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserStudyPlanList() {
        this.studyPlanList.clear();
        StudyPlanAdapter studyPlanAdapter = this.adapter;
        if (studyPlanAdapter != null) {
            studyPlanAdapter.setListData(this.studyPlanList);
            this.adapter.notifyDataSetChanged();
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.userStudyPlanList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    StudyPlanListBean studyPlanListBean = (StudyPlanListBean) new Gson().fromJson(s2, StudyPlanListBean.class);
                    if (studyPlanListBean.getCode().equals("200")) {
                        StudyPlanListActivity.this.planInProgressBean = studyPlanListBean.getData().getPlan_in_progress();
                        if (StudyPlanListActivity.this.planInProgressBean.getId() != null) {
                            StudyPlanListActivity.this.systemPlanBean = new StudyPlanListBean.DataBean.SystemPlanBean();
                            StudyPlanListActivity.this.systemPlanBean.setId(StudyPlanListActivity.this.planInProgressBean.getId());
                            StudyPlanListActivity.this.systemPlanBean.setName(StudyPlanListActivity.this.planInProgressBean.getName());
                            StudyPlanListActivity.this.systemPlanBean.setDays(StudyPlanListActivity.this.planInProgressBean.getDays());
                            StudyPlanListActivity.this.systemPlanBean.setComplete_days(StudyPlanListActivity.this.planInProgressBean.getComplete_days());
                            StudyPlanListActivity.this.systemPlanBean.setStatus(StudyPlanListActivity.this.planInProgressBean.getStatus());
                            StudyPlanListActivity.this.systemPlanBean.setStart_time(StudyPlanListActivity.this.planInProgressBean.getStart_time());
                            StudyPlanListActivity.this.systemPlanBean.setType(StudyPlanListActivity.this.planInProgressBean.getType());
                            StudyPlanListActivity.this.systemPlanBean.setDescription(StudyPlanListActivity.this.planInProgressBean.getDescription());
                            StudyPlanListActivity.this.systemPlanBean.setCompletion(StudyPlanListActivity.this.planInProgressBean.getCompletion());
                            StudyPlanListActivity.this.systemPlanBean.setItem_type(StudyPlanListBean.DataBean.SystemPlanBean.ItemType.WITH_PROGRESS);
                            StudyPlanListActivity.this.studyPlanList.add(StudyPlanListActivity.this.systemPlanBean);
                        }
                        StudyPlanListActivity.this.studyPlanList.addAll(studyPlanListBean.getData().getSystem_plan());
                        StudyPlanListActivity.this.scoreDescTwo = studyPlanListBean.getData().getRule();
                        StudyPlanListActivity.this.showFirstHintDialog();
                        StudyPlanListActivity.this.title = studyPlanListBean.getData().getTitle();
                        StudyPlanListActivity.this.desc = studyPlanListBean.getData().getDesc();
                        StudyPlanListActivity.this.tvTitle.setText(StudyPlanListActivity.this.title);
                        StudyPlanListActivity.this.tvSubtitle.setText(StudyPlanListActivity.this.desc);
                        if (StudyPlanListActivity.this.studyPlanList.isEmpty()) {
                            return;
                        }
                        StudyPlanListActivity.this.setupRecyclerView();
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCustomTimePicker() {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(CommonUtil.getTodayDate(DatePattern.NORM_YEAR_PATTERN) - 5, 1, CommonUtil.getTodayDate("dd"));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(CommonUtil.getTodayDate(DatePattern.NORM_YEAR_PATTERN) + 20, 1, CommonUtil.getTodayDate("dd"));
        TimePickerBuilder timePickerBuilderIsAlphaGradient = new TimePickerBuilder(this, new OnTimeSelectListener() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.3
            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
            public void onTimeSelect(Date date, View v2) {
                StudyPlanListActivity.this.toReceiveStudyPlan(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        }).setDate(calendar).setRangDate(calendar2, calendar3).setLayoutRes(R.layout.layout_custom_time_picker, new CustomListener() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.2
            @Override // com.bigkoo.pickerview.listener.CustomListener
            public void customLayout(View v2) {
                TextView textView = (TextView) v2.findViewById(R.id.tv_finish);
                TextView textView2 = (TextView) v2.findViewById(R.id.tv_cancel);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v3) throws ParseException {
                        StudyPlanListActivity.this.pvCustomTime.returnData();
                        StudyPlanListActivity.this.pvCustomTime.dismiss();
                    }
                });
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.2.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v3) {
                        StudyPlanListActivity.this.pvCustomTime.dismiss();
                    }
                });
            }
        }).setContentTextSize(16).setType(new boolean[]{true, true, true, false, false, false}).setLabel("年", "月", "日", "时", "分", "秒").setLineSpacingMultiplier(3.5f).setTextXOffset(0, 0, 0, 40, 0, -40).isCenterLabel(false).setDividerColor(16777215).isAlphaGradient(true);
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
        TimePickerView timePickerViewBuild = textColorCenter.setTextColorOut(resources2.getColor(i3)).setBgColor(0).setItemVisibleCount(5).build();
        this.pvCustomTime = timePickerViewBuild;
        timePickerViewBuild.show();
    }

    private void initData() {
        this.studyPlanList = new ArrayList();
        getUserStudyPlanList();
    }

    private void initViews() {
        this.mActionBar.hide();
        this.ivBack = (ImageView) findViewById(R.id.iv_back);
        this.cvCustomPlan = (CardView) findViewById(R.id.cv_custom_plan);
        this.rvStudyPlans = (RecyclerView) findViewById(R.id.rv_study_plans);
        this.ivRightQa = (ImageView) findViewById(R.id.iv_right_qa);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.tvSubtitle = (TextView) findViewById(R.id.tv_subtitle);
        this.customPlanTv = (TextView) findViewById(R.id.custom_plan_tv);
        if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            this.customPlanTv.setText("开启会员定制个人专属规划");
        } else {
            this.customPlanTv.setText("自定义刷题规划");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            this.mContext.startActivity(new Intent(this.mContext, (Class<?>) MemberCenterActivity.class));
        } else {
            if (this.planInProgressBean.getId() != null) {
                new XPopup.Builder(this).asCustom(new DeleteDownloadDialog(this, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.activity.tk
                    @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                    public final void mClickIml() {
                        this.f13957a.lambda$setListenerForWidget$1();
                    }
                }, new SpannableStringBuilder("是否放弃当前规划，并制定新规划？"), "温馨提示", "取消", "放弃并重置")).show();
                return;
            }
            Intent intent = new Intent(this, (Class<?>) CustomStudyPlanActivity.class);
            intent.putExtra("ongoing_plan", this.planInProgressBean.getId() == null ? "" : this.planInProgressBean.getId());
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (CommonUtil.isFastClick() || this.scoreDescTwo.isEmpty()) {
            return;
        }
        new XPopup.Builder(this).popupAnimation(null).asCustom(new ScoreTrendInfoDialog(this, this.scoreDescTwo, "刷题规划说明", true)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExpandClick(int position) {
        this.studyPlanList.get(position).setExpanded(!r0.isExpanded());
        this.adapter.notifyItemChanged(position);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupRecyclerView() {
        this.adapter = new StudyPlanAdapter(this, this.studyPlanList, new StudyPlanAdapter.OnExpandClickListener() { // from class: com.psychiatrygarden.activity.sk
            @Override // com.psychiatrygarden.adapter.StudyPlanAdapter.OnExpandClickListener
            public final void onExpandClick(int i2) {
                this.f13925a.onExpandClick(i2);
            }
        });
        this.rvStudyPlans.setLayoutManager(new LinearLayoutManager(this));
        this.rvStudyPlans.setAdapter(this.adapter);
        this.adapter.setOnItemClickLisenter(new AnonymousClass5());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFirstHintDialog() {
        if ("1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.FIRST_COME_IN_CUSTOMER_PLAN_PAGE, this))) {
            return;
        }
        ScoreTrendInfoDialog scoreTrendInfoDialog = new ScoreTrendInfoDialog(this, this.scoreDescTwo, "刷题规划说明", true, true);
        XPopup.Builder builder = new XPopup.Builder(this);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).popupAnimation(null).asCustom(scoreTrendInfoDialog).show();
        SharePreferencesUtils.writeStrConfig(CommonParameter.FIRST_COME_IN_CUSTOMER_PLAN_PAGE, "1", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toReceiveStudyPlan(String startTime) {
        showProgressDialog("");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("start_date", startTime);
        ajaxParams.put("plan_id", this.planId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.receiveUserStudyPlan, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                StudyPlanListActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                StudyPlanListActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_DAILY_TASK);
                        StudyPlanListActivity.this.getUserStudyPlanList();
                    } else {
                        ToastUtil.shortToast(StudyPlanListActivity.this.mContext, jSONObject.optString("message"));
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: toRedoTask, reason: merged with bridge method [inline-methods] */
    public void lambda$setListenerForWidget$1() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanRedoEveryTask, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.StudyPlanListActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_DAILY_TASK);
                        StudyPlanListActivity.this.startActivity(new Intent(StudyPlanListActivity.this, (Class<?>) CustomStudyPlanActivity.class));
                    } else {
                        ToastUtil.shortToast(StudyPlanListActivity.this.mContext, jSONObject.optString("message"));
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        initViews();
        initStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, 1 == SkinManager.getCurrentSkinType(this) ? R.color.corlor_appbar_study_plan_night : R.color.corlor_appbar_study_plan), 0);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    @Subscribe
    public void onEventMainThread(BuyVipSuccessEvent event) {
        if (event.getSuccess()) {
            UserConfig.getInstance().getUser().setIs_vip("1");
            this.customPlanTv.setText("自定义学习规划");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        initData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_study_plan_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.uk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13993c.lambda$setListenerForWidget$0(view);
            }
        });
        this.cvCustomPlan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14122c.lambda$setListenerForWidget$2(view);
            }
        });
        this.ivRightQa.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14157c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}
