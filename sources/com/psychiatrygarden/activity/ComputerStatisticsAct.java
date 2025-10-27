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
import com.psychiatrygarden.activity.ComputerStatisticsAct;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.ComputerStatisticsTypeAdp;
import com.psychiatrygarden.bean.ComputerStatisticsBean;
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
public class ComputerStatisticsAct extends BaseActivity {
    private CompositeDisposable compositeDisposable;
    private EsexBean.DataBean dataBean;
    private String examId;
    private String file;
    private TextView mBtnResetTest;
    private TextView mBtnReviewTest;
    private ComputerStatisticsTypeAdp mKaodianAdp;
    private RecyclerView mKaodianecycler;
    private TextView mTvLevel;
    private TextView mTvPass;
    private TextView mTvScore;
    private TextView mTvUserName;
    private ComputerStatisticsTypeAdp mTypeAdp;
    private RecyclerView mTypeRecycler;
    private CircleImageView mUserHead;
    private Timer timer;
    private TimerTask timerTask;
    private String title;
    private String date_end_timestamp = "";
    private long secondend = 0;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.ComputerStatisticsAct.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 5) {
                if (ComputerStatisticsAct.this.secondend - 1 > 0) {
                    ComputerStatisticsAct.this.secondend--;
                    return;
                }
                ComputerStatisticsAct.this.timer.cancel();
                ComputerStatisticsAct.this.timerTask.cancel();
                ComputerStatisticsAct.this.mBtnReviewTest.setEnabled(true);
                if (SkinManager.getCurrentSkinType(ComputerStatisticsAct.this.mContext) == 1) {
                    ComputerStatisticsAct.this.mBtnReviewTest.setBackgroundResource(R.drawable.linetype6_night);
                    ComputerStatisticsAct.this.mBtnReviewTest.setTextColor(ContextCompat.getColor(ComputerStatisticsAct.this, R.color.line_txt_color_night));
                } else {
                    ComputerStatisticsAct.this.mBtnReviewTest.setBackgroundResource(R.drawable.linetype6);
                    ComputerStatisticsAct.this.mBtnReviewTest.setTextColor(ContextCompat.getColor(ComputerStatisticsAct.this, R.color.app_theme_red));
                }
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.ComputerStatisticsAct$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onFailure$2(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFailure$3(Date date) throws Exception {
            Intent intent = new Intent(ComputerStatisticsAct.this.mContext, (Class<?>) SubQuestionCheshiActivity.class);
            intent.putExtra("exam_id", ComputerStatisticsAct.this.examId);
            intent.putExtra("date_start_timestamp", ComputerStatisticsAct.this.dataBean.getDate_start_timestamp());
            intent.putExtra("question_file", ComputerStatisticsAct.this.file);
            intent.putExtra("date_end_timestamp", ComputerStatisticsAct.this.dataBean.getDate_end_timestamp());
            intent.putExtra("startData", "" + ComputerStatisticsAct.this.df.format(date).toString());
            intent.putExtra("typeData", ComputerStatisticsAct.this.dataBean.getType() + "");
            intent.putExtra("date_start", ComputerStatisticsAct.this.dataBean.getDate_start());
            intent.putExtra("statusData", ComputerStatisticsAct.this.dataBean.getState() + "");
            intent.putExtra("duration_minute", ComputerStatisticsAct.this.dataBean.getDuration_minute());
            intent.putExtra("is_esaydta", ComputerStatisticsAct.this.dataBean.getIs_estimate());
            intent.putExtra("title", ComputerStatisticsAct.this.title);
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.parseInt("" + ComputerStatisticsAct.this.dataBean.getAnswer_number()) + 1);
            sb.append("");
            intent.putExtra("answer_number", sb.toString());
            ComputerStatisticsAct.this.startActivity(intent);
            ComputerStatisticsAct.this.hideProgressDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(Date date) throws Exception {
            Intent intent = new Intent(ComputerStatisticsAct.this.mContext, (Class<?>) SubQuestionCheshiActivity.class);
            intent.putExtra("exam_id", ComputerStatisticsAct.this.examId);
            intent.putExtra("date_start_timestamp", ComputerStatisticsAct.this.dataBean.getDate_start_timestamp());
            intent.putExtra("question_file", ComputerStatisticsAct.this.file);
            intent.putExtra("date_end_timestamp", ComputerStatisticsAct.this.dataBean.getDate_end_timestamp());
            intent.putExtra("startData", "" + ComputerStatisticsAct.this.df.format(date));
            intent.putExtra("typeData", ComputerStatisticsAct.this.dataBean.getType() + "");
            intent.putExtra("date_start", ComputerStatisticsAct.this.dataBean.getDate_start());
            intent.putExtra("submit_minute", ComputerStatisticsAct.this.dataBean.getSubmitMinute());
            intent.putExtra("statusData", ComputerStatisticsAct.this.dataBean.getState() + "");
            intent.putExtra("duration_minute", ComputerStatisticsAct.this.dataBean.getDuration_minute());
            intent.putExtra("is_esaydta", ComputerStatisticsAct.this.dataBean.getIs_estimate());
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.parseInt("" + ComputerStatisticsAct.this.dataBean.getAnswer_number()) + 1);
            sb.append("");
            intent.putExtra("answer_number", sb.toString());
            intent.putExtra("title", ComputerStatisticsAct.this.title);
            ComputerStatisticsAct.this.startActivity(intent);
            ComputerStatisticsAct.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ComputerStatisticsAct.this.hideProgressDialog();
            ComputerStatisticsAct.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.u7
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    ComputerStatisticsAct.AnonymousClass3.lambda$onFailure$2(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.v7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f14014c.lambda$onFailure$3((Date) obj);
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
                List list = (List) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data")), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.ComputerStatisticsAct.3.1
                }.getType());
                Collections.sort(list);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamDataList.addAll(list);
                ComputerStatisticsAct.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.w7
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerStatisticsAct.AnonymousClass3.lambda$onSuccess$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.x7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f14173c.lambda$onSuccess$1((Date) obj);
                    }
                }));
            } catch (Exception e2) {
                e2.printStackTrace();
                ComputerStatisticsAct.this.hideProgressDialog();
            }
        }
    }

    private void getExamInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.minfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerStatisticsAct.2
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
                        ComputerStatisticsAct.this.AlertToast(strOptString);
                        return;
                    }
                    SharePreferencesUtils.writeStrConfig("statisticsPermission", esexBean.getData().getShare_activity_rights(), ComputerStatisticsAct.this);
                    ComputerStatisticsAct.this.dataBean = esexBean.getData();
                    if (esexBean.getData().getShare_activity_rights().equals("1")) {
                        ComputerStatisticsAct.this.getInfo();
                    }
                    ComputerStatisticsAct computerStatisticsAct = ComputerStatisticsAct.this;
                    computerStatisticsAct.date_end_timestamp = computerStatisticsAct.dataBean.getDate_end_timestamp();
                    if (ComputerStatisticsAct.this.dataBean.getState() == 6 && !ComputerStatisticsAct.this.dataBean.getIs_estimate().equals("1") && ComputerStatisticsAct.this.dataBean.getType().equals("1")) {
                        ComputerStatisticsAct.this.getFinishDate(Long.parseLong(esexBean.getServer_time() + ""));
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
                TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.activity.ComputerStatisticsAct.4
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 5;
                        ComputerStatisticsAct.this.mHandler.sendMessage(messageObtain);
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
        YJYHttpUtils.get(this, NetworkRequestsURL.getComputerStatisticsInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerStatisticsAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ComputerStatisticsBean computerStatisticsBean = (ComputerStatisticsBean) new Gson().fromJson(s2, ComputerStatisticsBean.class);
                    if (!computerStatisticsBean.getCode().equals("200") || computerStatisticsBean.getData() == null) {
                        return;
                    }
                    if (TextUtils.isEmpty(computerStatisticsBean.getData().getExam_again()) || computerStatisticsBean.getData().getExam_again().equals("0")) {
                        ComputerStatisticsAct.this.mBtnResetTest.setVisibility(8);
                    } else {
                        ComputerStatisticsAct.this.mBtnResetTest.setVisibility(0);
                    }
                    if (computerStatisticsBean.getData().getRanking() != null) {
                        ComputerStatisticsAct.this.mTvScore.setText("得分：" + computerStatisticsBean.getData().getRanking().getScore() + "分");
                        ComputerStatisticsAct.this.mTvLevel.setText("排名：" + computerStatisticsBean.getData().getRanking().getRank());
                        ComputerStatisticsAct.this.mTvPass.setText(computerStatisticsBean.getData().getRanking().getPass());
                        if (computerStatisticsBean.getData().getRanking().getPass().equals("及格")) {
                            ComputerStatisticsAct.this.mTvPass.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(ComputerStatisticsAct.this) == 0 ? "#81CB30" : "#6AA064"));
                        } else {
                            ComputerStatisticsAct.this.mTvPass.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(ComputerStatisticsAct.this) == 0 ? "#F95843" : "#B2575C"));
                        }
                    }
                    ComputerStatisticsAct.this.mTypeAdp.setList(computerStatisticsBean.getData().getWong_type());
                    ComputerStatisticsAct.this.mKaodianAdp.setList(computerStatisticsBean.getData().getWong_center());
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
        if (z2) {
            EventBus.getDefault().post("closePage");
            ComputerPersonalInfoSureAct.newIntent(this, this.dataBean.getApp_name(), this.dataBean.getTitle(), this.dataBean.getQuestion_number(), this.dataBean.getTotal_points(), this.file, this.examId, this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute());
        } else {
            EventBus.getDefault().post("closePage");
            getQuestionData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$7(AlertDialog alertDialog, View view) {
        alertDialog.cancel();
        ProjectApp.questExamList.clear();
        ProjectApp.questExamDataList.clear();
        if (this.dataBean.getComputer_based_test().equals("1")) {
            new ChooseMockTestMethodDialog(this.mContext, new ChooseMockTestMethodDialog.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.l7
                @Override // com.psychiatrygarden.widget.ChooseMockTestMethodDialog.ProjectChoosedInterface
                public final void mItemLinsenter(boolean z2) {
                    this.f12657a.lambda$mCommDialog$6(z2);
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
                this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.q7
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerStatisticsAct.lambda$setListenerForWidget$2(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.r7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f13760c.lambda$setListenerForWidget$3((Date) obj);
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
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.s7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13821c.lambda$mCommDialog$7(alertDialogCreate, view);
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.t7
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
        Intent intent = new Intent(context, (Class<?>) ComputerStatisticsAct.class);
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
        this.mTvLevel = (TextView) findViewById(R.id.tv_level);
        this.mTvPass = (TextView) findViewById(R.id.tv_score_status);
        this.mTypeRecycler = (RecyclerView) findViewById(R.id.type_recycler);
        this.mKaodianecycler = (RecyclerView) findViewById(R.id.kaodian_recycler);
        this.mBtnReviewTest = (TextView) findViewById(R.id.btn_review_test);
        this.mBtnResetTest = (TextView) findViewById(R.id.btn_reset_test);
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), this.mUserHead);
        this.mTvUserName.setText(UserConfig.getInstance().getUser().getNickname());
        ComputerStatisticsTypeAdp computerStatisticsTypeAdp = new ComputerStatisticsTypeAdp(true);
        this.mTypeAdp = computerStatisticsTypeAdp;
        this.mTypeRecycler.setAdapter(computerStatisticsTypeAdp);
        ComputerStatisticsTypeAdp computerStatisticsTypeAdp2 = new ComputerStatisticsTypeAdp(false);
        this.mKaodianAdp = computerStatisticsTypeAdp2;
        this.mKaodianecycler.setAdapter(computerStatisticsTypeAdp2);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.o7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13072c.lambda$init$0(view);
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
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.p7
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f13533a.lambda$init$1();
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
        setContentView(R.layout.layout_computer_statistics);
        this.compositeDisposable = new CompositeDisposable();
        getWindow().addFlags(1024);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnReviewTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.m7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12690c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnResetTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.n7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13039c.lambda$setListenerForWidget$5(view);
            }
        });
    }
}
