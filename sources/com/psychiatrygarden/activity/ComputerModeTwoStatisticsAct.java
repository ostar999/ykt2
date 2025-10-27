package com.psychiatrygarden.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.ComputerTwoStatisticsAdp;
import com.psychiatrygarden.bean.ComputerTwoStatisticsBean;
import com.psychiatrygarden.bean.EsexBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ChooseMockTestMethodDialog;
import com.psychiatrygarden.widget.CircleImageView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ComputerModeTwoStatisticsAct extends BaseActivity {
    private CompositeDisposable compositeDisposable;
    private EsexBean.DataBean dataBean;
    private String examId;
    private String file;
    private TextView mBtnResetTest;
    private TextView mBtnReviewTest;
    private TextView mTvAllNumber;
    private TextView mTvLevel;
    private TextView mTvPass;
    private TextView mTvScore;
    private TextView mTvUserName;
    private ComputerTwoStatisticsAdp mTypeAdp;
    private RecyclerView mTypeRecycler;
    private CircleImageView mUserHead;
    private Timer timer;
    private TimerTask timerTask;
    private String title;
    private String date_end_timestamp = "";
    private long secondend = 0;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 5) {
                if (ComputerModeTwoStatisticsAct.this.secondend - 1 > 0) {
                    ComputerModeTwoStatisticsAct.this.secondend--;
                    return;
                }
                ComputerModeTwoStatisticsAct.this.timer.cancel();
                ComputerModeTwoStatisticsAct.this.timerTask.cancel();
                ComputerModeTwoStatisticsAct.this.mBtnReviewTest.setEnabled(true);
                if (SkinManager.getCurrentSkinType(ComputerModeTwoStatisticsAct.this.mContext) == 1) {
                    ComputerModeTwoStatisticsAct.this.mBtnReviewTest.setBackgroundResource(R.drawable.linetype6_night);
                    ComputerModeTwoStatisticsAct.this.mBtnReviewTest.setTextColor(ContextCompat.getColor(ComputerModeTwoStatisticsAct.this, R.color.line_txt_color_night));
                } else {
                    ComputerModeTwoStatisticsAct.this.mBtnReviewTest.setBackgroundResource(R.drawable.linetype6);
                    ComputerModeTwoStatisticsAct.this.mBtnReviewTest.setTextColor(ContextCompat.getColor(ComputerModeTwoStatisticsAct.this, R.color.app_theme_red));
                }
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onFailure$2(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFailure$3(Date date) throws Exception {
            Intent intent = new Intent(ComputerModeTwoStatisticsAct.this.mContext, (Class<?>) SubQuestionCheshiActivity.class);
            intent.putExtra("exam_id", ComputerModeTwoStatisticsAct.this.examId);
            intent.putExtra("date_start_timestamp", ComputerModeTwoStatisticsAct.this.dataBean.getDate_start_timestamp());
            intent.putExtra("question_file", ComputerModeTwoStatisticsAct.this.file);
            intent.putExtra("date_end_timestamp", ComputerModeTwoStatisticsAct.this.dataBean.getDate_end_timestamp());
            intent.putExtra("startData", "" + ComputerModeTwoStatisticsAct.this.df.format(date).toString());
            intent.putExtra("typeData", ComputerModeTwoStatisticsAct.this.dataBean.getType() + "");
            intent.putExtra("date_start", ComputerModeTwoStatisticsAct.this.dataBean.getDate_start());
            intent.putExtra("statusData", ComputerModeTwoStatisticsAct.this.dataBean.getState() + "");
            intent.putExtra("duration_minute", ComputerModeTwoStatisticsAct.this.dataBean.getDuration_minute());
            intent.putExtra("is_esaydta", ComputerModeTwoStatisticsAct.this.dataBean.getIs_estimate());
            intent.putExtra("title", ComputerModeTwoStatisticsAct.this.title);
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.parseInt("" + ComputerModeTwoStatisticsAct.this.dataBean.getAnswer_number()) + 1);
            sb.append("");
            intent.putExtra("answer_number", sb.toString());
            ComputerModeTwoStatisticsAct.this.startActivity(intent);
            ComputerModeTwoStatisticsAct.this.hideProgressDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(Date date) throws Exception {
            Intent intent = new Intent(ComputerModeTwoStatisticsAct.this.mContext, (Class<?>) SubQuestionCheshiActivity.class);
            intent.putExtra("exam_id", ComputerModeTwoStatisticsAct.this.examId);
            intent.putExtra("date_start_timestamp", ComputerModeTwoStatisticsAct.this.dataBean.getDate_start_timestamp());
            intent.putExtra("question_file", ComputerModeTwoStatisticsAct.this.file);
            intent.putExtra("date_end_timestamp", ComputerModeTwoStatisticsAct.this.dataBean.getDate_end_timestamp());
            intent.putExtra("startData", "" + ComputerModeTwoStatisticsAct.this.df.format(date));
            intent.putExtra("typeData", ComputerModeTwoStatisticsAct.this.dataBean.getType() + "");
            intent.putExtra("date_start", ComputerModeTwoStatisticsAct.this.dataBean.getDate_start());
            intent.putExtra("submit_minute", ComputerModeTwoStatisticsAct.this.dataBean.getSubmitMinute());
            intent.putExtra("statusData", ComputerModeTwoStatisticsAct.this.dataBean.getState() + "");
            intent.putExtra("duration_minute", ComputerModeTwoStatisticsAct.this.dataBean.getDuration_minute());
            intent.putExtra("is_esaydta", ComputerModeTwoStatisticsAct.this.dataBean.getIs_estimate());
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.parseInt("" + ComputerModeTwoStatisticsAct.this.dataBean.getAnswer_number()) + 1);
            sb.append("");
            intent.putExtra("answer_number", sb.toString());
            intent.putExtra("title", ComputerModeTwoStatisticsAct.this.title);
            ComputerModeTwoStatisticsAct.this.startActivity(intent);
            ComputerModeTwoStatisticsAct.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ComputerModeTwoStatisticsAct.this.hideProgressDialog();
            ComputerModeTwoStatisticsAct.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.c7
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    ComputerModeTwoStatisticsAct.AnonymousClass3.lambda$onFailure$2(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.d7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f12236c.lambda$onFailure$3((Date) obj);
                }
            }));
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass3) t2);
            try {
                List list = (List) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data")), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct.3.1
                }.getType());
                Collections.sort(list);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamDataList.addAll(list);
                ComputerModeTwoStatisticsAct.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.a7
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerModeTwoStatisticsAct.AnonymousClass3.lambda$onSuccess$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.b7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f11099c.lambda$onSuccess$1((Date) obj);
                    }
                }));
            } catch (Exception e2) {
                e2.printStackTrace();
                ComputerModeTwoStatisticsAct.this.hideProgressDialog();
            }
        }
    }

    private void getExamInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.minfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    EsexBean esexBean = (EsexBean) new Gson().fromJson(s2, EsexBean.class);
                    if (!esexBean.getCode().equals("200")) {
                        String strOptString = new JSONObject(s2).optString("message", "");
                        if (TextUtils.isEmpty(strOptString)) {
                            return;
                        }
                        ComputerModeTwoStatisticsAct.this.AlertToast(strOptString);
                        return;
                    }
                    SharePreferencesUtils.writeStrConfig("statisticsPermission", esexBean.getData().getShare_activity_rights(), ComputerModeTwoStatisticsAct.this);
                    ComputerModeTwoStatisticsAct.this.dataBean = esexBean.getData();
                    if (esexBean.getData().getShare_activity_rights().equals("1")) {
                        ComputerModeTwoStatisticsAct.this.getInfo();
                    }
                    ComputerModeTwoStatisticsAct computerModeTwoStatisticsAct = ComputerModeTwoStatisticsAct.this;
                    computerModeTwoStatisticsAct.date_end_timestamp = computerModeTwoStatisticsAct.dataBean.getDate_end_timestamp();
                    if (ComputerModeTwoStatisticsAct.this.dataBean.getState() == 6 && !ComputerModeTwoStatisticsAct.this.dataBean.getIs_estimate().equals("1") && ComputerModeTwoStatisticsAct.this.dataBean.getType().equals("1")) {
                        ComputerModeTwoStatisticsAct.this.getFinishDate(Long.parseLong(esexBean.getServer_time() + ""));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFinishDate(long time) {
        try {
            long j2 = Long.parseLong(this.date_end_timestamp) - time;
            this.secondend = j2;
            if (j2 > 0) {
                this.mBtnReviewTest.setEnabled(false);
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.mBtnReviewTest.setBackgroundResource(R.drawable.linetype1);
                    this.mBtnReviewTest.setTextColor(ContextCompat.getColor(this, R.color.gray_light_new));
                } else {
                    this.mBtnReviewTest.setBackgroundResource(R.drawable.linetype1_night);
                    this.mBtnReviewTest.setTextColor(ContextCompat.getColor(this, R.color.jiucuo_night));
                }
                Timer timer = this.timer;
                if (timer != null) {
                    timer.cancel();
                }
                this.timer = new Timer();
                TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct.4
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 5;
                        ComputerModeTwoStatisticsAct.this.mHandler.sendMessage(messageObtain);
                    }
                };
                this.timerTask = timerTask;
                this.timer.schedule(timerTask, 1000L, 1000L);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("exam_id", "" + getIntent().getExtras().getString("examId"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.getMockModeStatisticsInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerModeTwoStatisticsAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ComputerTwoStatisticsBean computerTwoStatisticsBean = (ComputerTwoStatisticsBean) new Gson().fromJson(s2, ComputerTwoStatisticsBean.class);
                    if (!computerTwoStatisticsBean.getCode().equals("200") || computerTwoStatisticsBean.getData() == null) {
                        return;
                    }
                    if (TextUtils.isEmpty(computerTwoStatisticsBean.getData().getExam_again()) || computerTwoStatisticsBean.getData().getExam_again().equals("0")) {
                        ComputerModeTwoStatisticsAct.this.mBtnResetTest.setVisibility(8);
                    } else {
                        ComputerModeTwoStatisticsAct.this.mBtnResetTest.setVisibility(0);
                    }
                    ComputerModeTwoStatisticsAct.this.mTvScore.setText("得分：" + computerTwoStatisticsBean.getData().getScore() + "分");
                    ComputerModeTwoStatisticsAct.this.mTvAllNumber.setText("共" + computerTwoStatisticsBean.getData().getNum_count() + "道题");
                    ComputerModeTwoStatisticsAct.this.mTvLevel.setText("答对：" + computerTwoStatisticsBean.getData().getRight_count() + "道");
                    ComputerModeTwoStatisticsAct.this.mTvPass.setText(computerTwoStatisticsBean.getData().getPass());
                    if (computerTwoStatisticsBean.getData().getPass().equals("及格")) {
                        ComputerModeTwoStatisticsAct.this.mTvPass.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(ComputerModeTwoStatisticsAct.this) == 0 ? "#81CB30" : "#6AA064"));
                    } else {
                        ComputerModeTwoStatisticsAct.this.mTvPass.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(ComputerModeTwoStatisticsAct.this) == 0 ? "#F95843" : "#B2575C"));
                    }
                    ComputerModeTwoStatisticsAct.this.mTypeAdp.setList(computerTwoStatisticsBean.getData().getUser_answer());
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    private void getQuestionData() {
        showProgressDialog("加载中...");
        YJYHttpUtils.get(getApplicationContext(), this.file, new AjaxParams(), new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        EventBus.getDefault().post("closePage");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", this);
        getInfo();
        getExamInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$6(boolean z2) {
        EventBus.getDefault().post("closePage");
        if (!z2) {
            getQuestionData();
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
        if (strConfig.equals("30") || strConfig.equals("33") || strConfig.equals("50") || strConfig.equals("60") || strConfig.equals("61") || strConfig.equals("62") || strConfig.equals("82")) {
            ComputerMockTestLoginAct.newIntent(this, getIntent().getExtras().getString("exam_id"), this.dataBean.getTitle(), this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute(), getIntent().getExtras().getString("question_file"), this.dataBean.getApp_name());
        } else {
            ComputerPersonalInfoSureAct.newIntent(this, this.dataBean.getApp_name(), this.dataBean.getTitle(), this.dataBean.getQuestion_number(), this.dataBean.getTotal_points(), this.file, this.examId, this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$7(AlertDialog alertDialog, View view) {
        alertDialog.cancel();
        ProjectApp.questExamList.clear();
        ProjectApp.questExamDataList.clear();
        if (this.dataBean.getComputer_based_test().equals("1")) {
            new ChooseMockTestMethodDialog(this.mContext, new ChooseMockTestMethodDialog.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.t6
                @Override // com.psychiatrygarden.widget.ChooseMockTestMethodDialog.ProjectChoosedInterface
                public final void mItemLinsenter(boolean z2) {
                    this.f13944a.lambda$mCommDialog$6(z2);
                }
            }).show();
        } else {
            getQuestionData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setListenerForWidget$2(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(CommonUtil.getNetTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(Date date) throws Exception {
        if (this.dataBean.getType().equals("1") && date.getTime() / 1000 < Long.parseLong(this.dataBean.getDate_end_timestamp())) {
            AlertToast("全场考试结束后可回顾试卷，并获得模考成绩及排名");
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) SubQuestionCeshiDaActivity.class);
        intent.putExtra("exam_id", this.examId);
        intent.putExtra("question_file", this.file);
        intent.putExtra("title", this.title);
        intent.putExtra("user_exam_time", this.dataBean.getUser_exam_time());
        intent.putExtra("score", this.dataBean.getScore());
        intent.putExtra("typeData", this.dataBean.getType() + "");
        intent.putExtra("statusData", this.dataBean.getState() + "");
        intent.putExtra("score", this.dataBean.getScore());
        intent.putExtra("is_esaydta", this.dataBean.getIs_estimate());
        intent.putExtra("answer_number", "" + this.dataBean.getAnswer_number());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (this.dataBean.getState() == 6) {
            if (!this.dataBean.getIs_estimate().equals("1")) {
                this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.r6
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerModeTwoStatisticsAct.lambda$setListenerForWidget$2(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.s6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f13820c.lambda$setListenerForWidget$3((Date) obj);
                    }
                }));
                return;
            }
            EventBus.getDefault().post("closePage");
            Intent intent = new Intent(this.mContext, (Class<?>) SubQuestionCeshiDaActivity.class);
            intent.putExtra("exam_id", this.examId);
            intent.putExtra("question_file", this.file);
            intent.putExtra("title", this.title);
            intent.putExtra("user_exam_time", this.dataBean.getUser_exam_time());
            intent.putExtra("score", this.dataBean.getScore());
            intent.putExtra("typeData", this.dataBean.getType() + "");
            intent.putExtra("statusData", this.dataBean.getState() + "");
            intent.putExtra("is_esaydta", this.dataBean.getIs_estimate());
            intent.putExtra("answer_number", "" + this.dataBean.getAnswer_number());
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        mCommDialog();
    }

    private void mCommDialog() {
        try {
            if (this.mContext == null) {
                return;
            }
            final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
            alertDialogCreate.show();
            WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
            attributes.width = -1;
            attributes.height = -2;
            alertDialogCreate.getWindow().setAttributes(attributes);
            Window window = alertDialogCreate.getWindow();
            window.setContentView(R.layout.activity_relode);
            window.setGravity(17);
            window.setWindowAnimations(R.style.mystyle);
            alertDialogCreate.setCanceledOnTouchOutside(true);
            TextView textView = (TextView) alertDialogCreate.findViewById(R.id.continute);
            TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.cancle);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.u6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13977c.lambda$mCommDialog$7(alertDialogCreate, view);
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    alertDialogCreate.dismiss();
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void newIntent(Context context, String title, String examId, String file) {
        Intent intent = new Intent(context, (Class<?>) ComputerModeTwoStatisticsAct.class);
        intent.putExtra("title", title);
        intent.putExtra("examId", examId);
        intent.putExtra("file", file);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.title = getIntent().getStringExtra("title");
        this.examId = getIntent().getStringExtra("examId");
        this.file = getIntent().getStringExtra("file");
        setTitle(this.title);
        this.mUserHead = (CircleImageView) findViewById(R.id.user_head);
        this.mTvUserName = (TextView) findViewById(R.id.tv_username);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mTvAllNumber = (TextView) findViewById(R.id.tv_all_number);
        this.mTvLevel = (TextView) findViewById(R.id.tv_level);
        this.mTvPass = (TextView) findViewById(R.id.tv_score_status);
        this.mTypeRecycler = (RecyclerView) findViewById(R.id.type_recycler);
        this.mBtnReviewTest = (TextView) findViewById(R.id.btn_review_test);
        this.mBtnResetTest = (TextView) findViewById(R.id.btn_reset_test);
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), this.mUserHead);
        this.mTvUserName.setText(UserConfig.getInstance().getUser().getNickname());
        ComputerTwoStatisticsAdp computerTwoStatisticsAdp = new ComputerTwoStatisticsAdp();
        this.mTypeAdp = computerTwoStatisticsAdp;
        this.mTypeRecycler.setAdapter(computerTwoStatisticsAdp);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.w6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14140c.lambda$init$0(view);
            }
        });
        if (!SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            getInfo();
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
        MemInterface.getInstance().getMemData(this, ajaxParams, true, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.x6
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f14172a.lambda$init$1();
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post("closePage");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
        }
        TimerTask timerTask = this.timerTask;
        if (timerTask != null) {
            timerTask.cancel();
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getExamInfo();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_computer_mode_two_statistics);
        this.compositeDisposable = new CompositeDisposable();
        getWindow().addFlags(1024);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnReviewTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.y6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14207c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnResetTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.z6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14237c.lambda$setListenerForWidget$5(view);
            }
        });
    }
}
