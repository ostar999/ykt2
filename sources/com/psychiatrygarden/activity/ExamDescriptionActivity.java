package com.psychiatrygarden.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.bean.ExamInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ExamDescriptionActivity extends BaseActivity {
    private String code;
    private String examId;
    private ExamInfoBean.DataBean examInfoBean;
    private boolean hasAnswerRecord;
    private Disposable loopDisposable;
    private TextView tvBtn;
    private TextView tvExamType;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private boolean isLate = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void getAnswerRecord() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        YJYHttpUtils.post(this, NetworkRequestsURL.GET_USER_ANSWER_RECORD, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ExamDescriptionActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (TextUtils.isEmpty(s2)) {
                        return;
                    }
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optString("message");
                    if (jSONObject.optInt("code", 0) != 200) {
                        ExamDescriptionActivity.this.tvBtn.setEnabled(false);
                        ExamDescriptionActivity.this.tvBtn.setClickable(false);
                        if (TextUtils.isEmpty(strOptString)) {
                            return;
                        }
                        ExamDescriptionActivity.this.AlertToast(strOptString);
                        return;
                    }
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                    if (jSONArrayOptJSONArray != null) {
                        ExamDescriptionActivity.this.hasAnswerRecord = jSONArrayOptJSONArray.length() > 0;
                        int status = ExamDescriptionActivity.this.examInfoBean.getStatus();
                        if (status == 1) {
                            if (ExamDescriptionActivity.this.hasAnswerRecord) {
                                ExamDescriptionActivity.this.tvBtn.setEnabled(ExamDescriptionActivity.this.examInfoBean.getAnswer_remain_times() > 0 && !ExamDescriptionActivity.this.isLate);
                                ExamDescriptionActivity.this.tvBtn.setClickable(ExamDescriptionActivity.this.examInfoBean.getAnswer_remain_times() > 0 && !ExamDescriptionActivity.this.isLate);
                                ExamDescriptionActivity.this.tvBtn.setText(ExamDescriptionActivity.this.isLate ? "考试中，入口关闭" : String.format(Locale.CHINA, "重新考试（剩余%d次）", Integer.valueOf(ExamDescriptionActivity.this.examInfoBean.getAnswer_remain_times())));
                                return;
                            }
                            return;
                        }
                        if (status != 2) {
                            return;
                        }
                        if (!ExamDescriptionActivity.this.hasAnswerRecord) {
                            ExamDescriptionActivity.this.tvBtn.setText("考试已结束");
                            ExamDescriptionActivity.this.tvBtn.setEnabled(false);
                            ExamDescriptionActivity.this.tvBtn.setClickable(false);
                        } else {
                            ExamDescriptionActivity.this.tvBtn.setEnabled(true);
                            ExamDescriptionActivity.this.tvBtn.setClickable(true);
                            ExamDescriptionActivity.this.tvBtn.setText("回顾试卷");
                            ExamDescriptionActivity.this.tvBtn.setBackgroundResource(R.drawable.linetype6);
                            ExamDescriptionActivity.this.tvBtn.setTextColor(ContextCompat.getColor(ExamDescriptionActivity.this.mContext, R.color.app_theme_red));
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    ExamDescriptionActivity.this.tvBtn.setText("已结束");
                    ExamDescriptionActivity.this.tvBtn.setEnabled(false);
                    ExamDescriptionActivity.this.tvBtn.setClickable(false);
                }
            }
        });
    }

    private void getExamData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.examId)) {
            ajaxParams.put("exam_id", this.examId);
        } else if (!TextUtils.isEmpty(this.code)) {
            ajaxParams.put("code", this.code);
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.GET_EXAM_INFO, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ExamDescriptionActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ExamDescriptionActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                ExamDescriptionActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                int lateMinute;
                Date date;
                super.onSuccess((AnonymousClass1) s2);
                ExamDescriptionActivity.this.hideProgressDialog();
                try {
                    ExamInfoBean examInfoBean = (ExamInfoBean) new Gson().fromJson(s2, ExamInfoBean.class);
                    if (examInfoBean == null || examInfoBean.getCode() != 200) {
                        if (examInfoBean == null || TextUtils.isEmpty(examInfoBean.getMessage())) {
                            return;
                        }
                        ExamDescriptionActivity.this.AlertToast(examInfoBean.getMessage());
                        return;
                    }
                    ExamDescriptionActivity.this.examInfoBean = examInfoBean.getData();
                    if (ExamDescriptionActivity.this.examInfoBean.getStatus() == 1 && (lateMinute = ExamDescriptionActivity.this.examInfoBean.getLateMinute()) > 0 && (date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(ExamDescriptionActivity.this.examInfoBean.getStart_time())) != null) {
                        ExamDescriptionActivity.this.isLate = System.currentTimeMillis() > date.getTime() + (((long) (lateMinute * 60)) * 1000);
                    }
                    ExamDescriptionActivity.this.tvExamType.setText(String.format(Locale.CHINA, "%s阶段测试", ExamDescriptionActivity.this.examInfoBean.getAppName()));
                    String str = "考试中，入口关闭";
                    if (examInfoBean.getData().getExam_time() > 0) {
                        ((TextView) ExamDescriptionActivity.this.findViewById(R.id.exam_status)).setText(String.format(Locale.CHINA, "%d分  %s", Integer.valueOf(examInfoBean.getData().getUser_score()), ExamDescriptionActivity.this.getExamTimeStr(examInfoBean.getData().getExam_time())));
                    } else {
                        ((TextView) ExamDescriptionActivity.this.findViewById(R.id.exam_status)).setText(ExamDescriptionActivity.this.examInfoBean.getStatus() == 0 ? "等待考试开始" : ExamDescriptionActivity.this.examInfoBean.getStatus() == 1 ? ExamDescriptionActivity.this.isLate ? "考试中，入口关闭" : "考试进行中" : "考试已结束");
                    }
                    ((TextView) ExamDescriptionActivity.this.findViewById(R.id.exam_count)).setText(String.format(Locale.CHINA, "%d题", Integer.valueOf(examInfoBean.getData().getQuestion_number())));
                    ((TextView) ExamDescriptionActivity.this.findViewById(R.id.exam_score)).setText(String.format(Locale.CHINA, "%d分", Integer.valueOf(examInfoBean.getData().getScore())));
                    ((TextView) ExamDescriptionActivity.this.findViewById(R.id.pass_score)).setText(String.format(Locale.CHINA, "%d分", Integer.valueOf(examInfoBean.getData().getPass_score())));
                    ((TextView) ExamDescriptionActivity.this.findViewById(R.id.exam_duration)).setText(examInfoBean.getData().getAnswer_time() <= 0 ? "不限时" : String.format(Locale.CHINA, "%d分钟", Integer.valueOf(examInfoBean.getData().getAnswer_time())));
                    ((TextView) ExamDescriptionActivity.this.findViewById(R.id.exam_time)).setText(String.format(Locale.CHINA, "%s ~ %s", examInfoBean.getData().getStart_time(), examInfoBean.getData().getEnt_time()));
                    ((TextView) ExamDescriptionActivity.this.findViewById(R.id.tips)).setText(examInfoBean.getData().getDescription());
                    int status = examInfoBean.getData().getStatus();
                    if (status == 0) {
                        ExamDescriptionActivity.this.tvBtn.setText("等待考试开始");
                        ExamDescriptionActivity.this.tvBtn.setEnabled(false);
                        ExamDescriptionActivity.this.tvBtn.setClickable(false);
                        ExamDescriptionActivity.this.tvBtn.setBackgroundResource(R.drawable.linetype1);
                        ExamDescriptionActivity.this.tvBtn.setTextColor(ContextCompat.getColor(ExamDescriptionActivity.this.mContext, R.color.black));
                        return;
                    }
                    if (status != 1) {
                        if (status != 2) {
                            return;
                        }
                        ExamDescriptionActivity.this.tvBtn.setText("考试已结束");
                        ExamDescriptionActivity.this.tvBtn.setBackgroundResource(R.drawable.linetype1);
                        ExamDescriptionActivity.this.tvBtn.setTextColor(ContextCompat.getColor(ExamDescriptionActivity.this.mContext, R.color.gray_light));
                        ExamDescriptionActivity.this.tvBtn.setEnabled(false);
                        ExamDescriptionActivity.this.tvBtn.setClickable(false);
                        ExamDescriptionActivity.this.getAnswerRecord();
                        return;
                    }
                    int iMax = Math.max(examInfoBean.getData().getAnswer_number() - examInfoBean.getData().getTime(), 0);
                    ExamDescriptionActivity.this.examInfoBean.setAnswer_remain_times(iMax);
                    ExamDescriptionActivity.this.tvBtn.setEnabled(iMax > 0 && !ExamDescriptionActivity.this.isLate);
                    ExamDescriptionActivity.this.tvBtn.setClickable(iMax > 0 && !ExamDescriptionActivity.this.isLate);
                    TextView textView = ExamDescriptionActivity.this.tvBtn;
                    if (!ExamDescriptionActivity.this.isLate) {
                        str = String.format(Locale.CHINA, "开始考试(剩余%d次)", Integer.valueOf(iMax));
                    }
                    textView.setText(str);
                    ExamDescriptionActivity.this.tvBtn.setTextColor(ContextCompat.getColor(ExamDescriptionActivity.this, R.color.white));
                    ExamDescriptionActivity.this.tvBtn.setBackgroundResource(R.drawable.linetype3);
                    if (iMax <= 0 || ExamDescriptionActivity.this.isLate) {
                        ExamDescriptionActivity.this.tvBtn.setEnabled(false);
                        ExamDescriptionActivity.this.tvBtn.setClickable(false);
                    } else {
                        ExamDescriptionActivity.this.loopCheckStatus();
                    }
                    ExamDescriptionActivity.this.getAnswerRecord();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ExamDescriptionActivity.this.AlertToast(e2.getMessage());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getExamTimeStr(long remain) {
        String str;
        String str2;
        int i2 = (int) (remain / 3600);
        long j2 = remain % 3600;
        int i3 = (int) (j2 / 60);
        int i4 = (int) (j2 % 60);
        StringBuilder sb = new StringBuilder();
        String str3 = "";
        if (i2 <= 0) {
            str = "";
        } else {
            str = i2 + "时";
        }
        sb.append(str);
        if (i3 <= 0) {
            str2 = "";
        } else {
            str2 = i3 + "分";
        }
        sb.append(str2);
        if (i4 > 0) {
            str3 = i4 + "秒";
        }
        sb.append(str3);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: gotoExamPage, reason: merged with bridge method [inline-methods] */
    public void lambda$setListenerForWidget$2(long startTime, long endTime) {
        if (TextUtils.isEmpty(this.examInfoBean.getQuestion_file())) {
            AlertToast("试卷数据错误");
            this.tvBtn.setEnabled(false);
            this.tvBtn.setClickable(false);
            return;
        }
        final Intent intent = new Intent(this.mContext, (Class<?>) SubQuestionCheshiActivity.class);
        intent.putExtra("exam_id", getIntent().getExtras().getString("exam_id"));
        intent.putExtra("date_start_timestamp", "" + startTime);
        intent.putExtra("date_end_timestamp", "" + endTime);
        intent.putExtra("question_file", this.examInfoBean.getQuestion_file());
        intent.putExtra(com.heytap.mcssdk.constant.b.f7194s, this.examInfoBean.getStart_time());
        intent.putExtra(com.heytap.mcssdk.constant.b.f7195t, this.examInfoBean.getEnt_time());
        intent.putExtra("title", this.examInfoBean.getTitle());
        intent.putExtra("needCountDown", this.examInfoBean.getAnswer_time() > 0);
        intent.putExtra("is_esaydta", "0");
        intent.putExtra("typeData", "1");
        intent.putExtra("submit_minute", String.valueOf(this.examInfoBean.getSubmitMinute()));
        intent.putExtra("duration_minute", Long.parseLong(this.examInfoBean.getAnswer_time() + ""));
        intent.putExtra("from_my_exam", true);
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.z9
            @Override // java.lang.Runnable
            public final void run() {
                this.f14240c.lambda$gotoExamPage$6(intent);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$gotoExamPage$5(Intent intent, long j2) {
        intent.putExtra("startData", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Long.valueOf(j2)));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$gotoExamPage$6(final Intent intent) {
        final long time = CommonUtil.getNetTime().getTime();
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.ea
            @Override // java.lang.Runnable
            public final void run() {
                this.f12277c.lambda$gotoExamPage$5(intent, time);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$loopCheckStatus$7(Long l2) throws Exception {
        return this.examInfoBean == null ? Boolean.FALSE : Boolean.valueOf(CommonUtil.getNetTime().after(this.format.parse(this.examInfoBean.getEnt_time())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loopCheckStatus$8(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            getExamData();
            this.loopDisposable.dispose();
            this.loopDisposable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Long lambda$setListenerForWidget$0(String str) throws Exception {
        return Long.valueOf(CommonUtil.getNetTime().getTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setListenerForWidget$1() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(final long j2, View view, final long j3, Long l2) throws Exception {
        if (l2.longValue() <= j2) {
            if (this.hasAnswerRecord) {
                new XPopup.Builder(this.mContext).autoDismiss(Boolean.TRUE).asConfirm("", "重新考试的成绩将不进行排名分析，是否继续？", "继续", "取消", new OnConfirmListener() { // from class: com.psychiatrygarden.activity.aa
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        ExamDescriptionActivity.lambda$setListenerForWidget$1();
                    }
                }, new OnCancelListener() { // from class: com.psychiatrygarden.activity.ba
                    @Override // com.lxj.xpopup.interfaces.OnCancelListener
                    public final void onCancel() {
                        this.f11103a.lambda$setListenerForWidget$2(j3, j2);
                    }
                }, false).show();
                return;
            } else {
                lambda$setListenerForWidget$2(j3, j2);
                return;
            }
        }
        AlertToast("考试已结束");
        view.setEnabled(false);
        view.setClickable(false);
        this.tvBtn.setBackgroundResource(R.drawable.linetype1);
        this.tvBtn.setTextColor(getResources().getColor(R.color.gray_light));
        this.examInfoBean.setStatus(2);
        ((TextView) findViewById(R.id.exam_status)).setText("考试已结束");
        getAnswerRecord();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(final View view) throws ParseException {
        if (this.examInfoBean.getStatus() == 1) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Date date = simpleDateFormat.parse(this.examInfoBean.getStart_time());
                Date date2 = simpleDateFormat.parse(this.examInfoBean.getEnt_time());
                final long jCurrentTimeMillis = date == null ? System.currentTimeMillis() : date.getTime();
                final long jCurrentTimeMillis2 = date2 == null ? System.currentTimeMillis() : date2.getTime();
                Observable.just("").subscribeOn(Schedulers.io()).map(new Function() { // from class: com.psychiatrygarden.activity.fa
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return ExamDescriptionActivity.lambda$setListenerForWidget$0((String) obj);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.ga
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f12434c.lambda$setListenerForWidget$3(jCurrentTimeMillis2, view, jCurrentTimeMillis, (Long) obj);
                    }
                });
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (this.examInfoBean.getStatus() == 2 && this.hasAnswerRecord) {
            Intent intent = new Intent(this, (Class<?>) SubQuestionCeshiDaActivity.class);
            intent.putExtra("title", this.examInfoBean.getTitle());
            intent.putExtra("isTestEntrance", false);
            intent.putExtra("answer_number", this.examInfoBean.getAnswer_number() + "");
            intent.putExtra("exam_id", this.examInfoBean.getId() + "");
            intent.putExtra("question_file", this.examInfoBean.getQuestion_file());
            intent.putExtra("from_my_exam", true);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loopCheckStatus() {
        if (this.loopDisposable == null) {
            this.loopDisposable = Observable.interval(1000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).map(new Function() { // from class: com.psychiatrygarden.activity.ca
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return this.f11140c.lambda$loopCheckStatus$7((Long) obj);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.da
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f12239c.lambda$loopCheckStatus$8((Boolean) obj);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.tvBtn = (TextView) findViewById(R.id.tv_btn);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.loopDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.loopDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getExamData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Disposable disposable = this.loopDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.loopDisposable.dispose();
        this.loopDisposable = null;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_exam_description);
        String stringExtra = getIntent().getStringExtra("title");
        if (stringExtra == null) {
            stringExtra = getString(R.string.exam_desc);
        }
        setTitle(stringExtra);
        this.examId = getIntent().getStringExtra("exam_id");
        this.code = getIntent().getStringExtra("code");
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.icon_avatar);
        TextView textView = (TextView) findViewById(R.id.user_name);
        this.tvExamType = (TextView) findViewById(R.id.exam_type);
        textView.setText(UserConfig.getInstance().getUser().getNickname());
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), circleImageView);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tvBtn.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ha
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws ParseException {
                this.f12472c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
