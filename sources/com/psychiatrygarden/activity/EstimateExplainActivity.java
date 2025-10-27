package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.material.timepicker.TimeModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.EstimateExplainActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.EsexBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.QuestioBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ChooseMockTestMethodDialog;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogAcitivtyInput;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.UMShareAPI;
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
import java.util.ArrayList;
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
public class EstimateExplainActivity extends BaseActivity {
    private static final int UPDATE_TIME_WHAT = 10012;
    private TextView chongzuo;
    private CompositeDisposable compositeDisposable;
    private EsexBean.DataBean dataBean;
    private boolean iscoment;
    private boolean ispraise;
    private TextView juanmianfenzhi;
    private TextView kaoshileixing;
    private TextView kaoshishijian;
    private TextView kaoshitiliang;
    private QuestioBean.LiveAbout liveAbout;
    private TextView live_video;
    private TextView quexian;
    private GlideImageView shoucang;
    private TextView tiaozhuan;
    private Timer timer;
    private TimerTask timerTask;
    private TextView unreadnum;
    private TextView wenxintishi;
    private LinearLayout xiaohongshuruihuan;
    private int status = 1000;
    private String type = "";
    private String close_entrance_timestamp = "";
    private String date_start_timestamp = "";
    private String date_end_timestamp = "";
    List<ExesQuestionBean> questBeans = new ArrayList();
    private long second = 0;
    private long secondend = 0;
    private String is_collected = "0";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private boolean isLate = false;

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i2 = msg.what;
            if (i2 != 1) {
                if (i2 == 4) {
                    EstimateExplainActivity.this.getFinishDate(((Long) msg.obj).longValue());
                    return;
                }
                if (i2 == 5) {
                    if (EstimateExplainActivity.this.secondend - 1 > 0) {
                        EstimateExplainActivity.this.secondend--;
                        return;
                    }
                    EstimateExplainActivity.this.timer.cancel();
                    EstimateExplainActivity.this.timerTask.cancel();
                    if (SkinManager.getCurrentSkinType(EstimateExplainActivity.this.mContext) == 1) {
                        EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype6_night_new);
                        EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.line_txt_color_night));
                    } else {
                        EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype6_new);
                        EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.app_theme_red));
                    }
                    EstimateExplainActivity.this.tiaozhuan.setText("回顾试卷");
                    return;
                }
                if (i2 == 10012) {
                    try {
                        if (EstimateExplainActivity.this.dataBean != null && "0".equals(EstimateExplainActivity.this.dataBean.getLive_about().getLive_status()) && EstimateExplainActivity.this.live_video != null) {
                            EstimateExplainActivity.this.live_video.setText(DateTimeUtilKt.getTimeFromInt(Long.parseLong(EstimateExplainActivity.this.dataBean.getLive_about().getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)));
                        }
                    } catch (Exception e2) {
                        Log.d("RedactionActivity", "handlerMessage: " + e2.getMessage());
                    }
                    EstimateExplainActivity.this.mHandler.sendEmptyMessageDelayed(10012, 1000L);
                    return;
                }
                return;
            }
            if (EstimateExplainActivity.this.second - 1 > 0) {
                EstimateExplainActivity.this.second--;
                EstimateExplainActivity estimateExplainActivity = EstimateExplainActivity.this;
                EstimateExplainActivity.this.tiaozhuan.setText(estimateExplainActivity.getTimeFromInt(estimateExplainActivity.second));
                return;
            }
            EstimateExplainActivity.this.timer.cancel();
            EstimateExplainActivity.this.timerTask.cancel();
            if (!EstimateExplainActivity.this.dataBean.getIs_estimate().equals("1")) {
                EstimateExplainActivity.this.status = 3;
                if (SkinManager.getCurrentSkinType(EstimateExplainActivity.this.mContext) == 0) {
                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_new);
                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.white));
                    return;
                } else {
                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_night_new);
                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.line_txt_color_night));
                    return;
                }
            }
            if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                EstimateExplainActivity.this.status = 1;
                try {
                    EstimateExplainActivity.this.tiaozhuan.setText(EstimateExplainActivity.this.dataBean.getShare().getTips());
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    EstimateExplainActivity.this.tiaozhuan.setText("点击分享解锁");
                    return;
                }
            }
            EstimateExplainActivity.this.status = 3;
            if (SkinManager.getCurrentSkinType(EstimateExplainActivity.this.mContext) == 0) {
                EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_new);
                EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.white));
            } else {
                EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_night_new);
                EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.line_txt_color_night));
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.EstimateExplainActivity$7, reason: invalid class name */
    public class AnonymousClass7 extends AjaxCallBack<String> {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(Date date) throws Exception {
            Intent intent = new Intent(EstimateExplainActivity.this.mContext, (Class<?>) SubQuestionCheshiActivity.class);
            intent.putExtra("exam_id", EstimateExplainActivity.this.getIntent().getExtras().getString("exam_id"));
            intent.putExtra("date_start_timestamp", EstimateExplainActivity.this.date_start_timestamp);
            intent.putExtra("question_file", EstimateExplainActivity.this.getIntent().getExtras().getString("question_file"));
            intent.putExtra("date_end_timestamp", EstimateExplainActivity.this.date_end_timestamp);
            intent.putExtra("startData", "" + EstimateExplainActivity.this.df.format(date));
            intent.putExtra("typeData", EstimateExplainActivity.this.dataBean.getType() + "");
            intent.putExtra("date_start", EstimateExplainActivity.this.dataBean.getDate_start());
            intent.putExtra("submit_minute", EstimateExplainActivity.this.dataBean.getSubmitMinute());
            intent.putExtra("statusData", EstimateExplainActivity.this.status + "");
            intent.putExtra("duration_minute", EstimateExplainActivity.this.dataBean.getDuration_minute());
            intent.putExtra("is_esaydta", EstimateExplainActivity.this.dataBean.getIs_estimate());
            intent.putExtra("is_school_rank", EstimateExplainActivity.this.dataBean.getIs_school_rank());
            intent.putExtra("bindSchool", EstimateExplainActivity.this.dataBean.getUser_exam_info());
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.parseInt("" + EstimateExplainActivity.this.dataBean.getAnswer_number()) + 1);
            sb.append("");
            intent.putExtra("answer_number", sb.toString());
            intent.putExtra("title", EstimateExplainActivity.this.getIntent().getExtras().getString("title"));
            EstimateExplainActivity.this.startActivity(intent);
            EstimateExplainActivity.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            EstimateExplainActivity.this.hideProgressDialog();
            ToastUtil.shortToast(EstimateExplainActivity.this, "获取试卷失败!");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass7) t2);
            try {
                String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data"));
                Gson gson = new Gson();
                EstimateExplainActivity.this.questBeans = (List) gson.fromJson(strDecode, new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.7.1
                }.getType());
                Collections.sort(EstimateExplainActivity.this.questBeans);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamDataList.addAll(EstimateExplainActivity.this.questBeans);
                EstimateExplainActivity.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.w9
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        EstimateExplainActivity.AnonymousClass7.lambda$onSuccess$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.x9
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f14176c.lambda$onSuccess$1((Date) obj);
                    }
                }));
            } catch (Exception e2) {
                e2.printStackTrace();
                EstimateExplainActivity.this.hideProgressDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getEsExData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.minfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    EsexBean esexBean = (EsexBean) new Gson().fromJson(s2, EsexBean.class);
                    boolean z2 = SkinManager.getCurrentSkinType(EstimateExplainActivity.this.mContext) == 0;
                    if (esexBean.getCode().equals("200")) {
                        EstimateExplainActivity.this.dataBean = esexBean.getData();
                        String comment_count = EstimateExplainActivity.this.dataBean.getComment_count();
                        EstimateExplainActivity.this.initCommentNum(TextUtils.isEmpty(comment_count) ? 0 : Integer.parseInt(comment_count));
                        SharePreferencesUtils.writeStrConfig("statisticsPermission", EstimateExplainActivity.this.dataBean.getShare_activity_rights(), EstimateExplainActivity.this);
                        SharePreferencesUtils.writeStrConfig("statisticsActiveId", EstimateExplainActivity.this.dataBean.getShare_activity_id(), EstimateExplainActivity.this);
                        if (EstimateExplainActivity.this.dataBean != null && EstimateExplainActivity.this.dataBean.getLive_about() != null) {
                            EstimateExplainActivity.this.liveAbout = esexBean.getData().getLive_about();
                            EstimateExplainActivity estimateExplainActivity = EstimateExplainActivity.this;
                            estimateExplainActivity.setLiveButton(estimateExplainActivity.liveAbout);
                        }
                        EstimateExplainActivity estimateExplainActivity2 = EstimateExplainActivity.this;
                        estimateExplainActivity2.type = estimateExplainActivity2.dataBean.getType();
                        EstimateExplainActivity.this.quexian.setText(EstimateExplainActivity.this.dataBean.getTop_state_description());
                        EstimateExplainActivity.this.kaoshileixing.setText(EstimateExplainActivity.this.dataBean.getType_str());
                        EstimateExplainActivity.this.kaoshitiliang.setText(EstimateExplainActivity.this.dataBean.getQuestion_number());
                        EstimateExplainActivity.this.juanmianfenzhi.setText(EstimateExplainActivity.this.dataBean.getTotal_points());
                        EstimateExplainActivity.this.kaoshishijian.setText(EstimateExplainActivity.this.dataBean.getExam_time());
                        EstimateExplainActivity.this.wenxintishi.setText(EstimateExplainActivity.this.dataBean.getTips());
                        EstimateExplainActivity.this.tiaozhuan.setText(EstimateExplainActivity.this.dataBean.getBottom_state_description());
                        EstimateExplainActivity estimateExplainActivity3 = EstimateExplainActivity.this;
                        estimateExplainActivity3.status = estimateExplainActivity3.dataBean.getState();
                        EstimateExplainActivity estimateExplainActivity4 = EstimateExplainActivity.this;
                        estimateExplainActivity4.close_entrance_timestamp = estimateExplainActivity4.dataBean.getClose_entrance_timestamp();
                        EstimateExplainActivity estimateExplainActivity5 = EstimateExplainActivity.this;
                        estimateExplainActivity5.date_start_timestamp = estimateExplainActivity5.dataBean.getDate_start_timestamp();
                        EstimateExplainActivity estimateExplainActivity6 = EstimateExplainActivity.this;
                        estimateExplainActivity6.date_end_timestamp = estimateExplainActivity6.dataBean.getDate_end_timestamp();
                        if (!TextUtils.isEmpty(EstimateExplainActivity.this.date_start_timestamp) && EstimateExplainActivity.this.dataBean.getLateMinute() != null && EstimateExplainActivity.this.status == 3) {
                            long j2 = Long.parseLong(EstimateExplainActivity.this.date_start_timestamp);
                            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                            int i2 = Integer.parseInt(EstimateExplainActivity.this.dataBean.getLateMinute());
                            if (!TextUtils.isEmpty(EstimateExplainActivity.this.date_end_timestamp) && i2 > 0) {
                                EstimateExplainActivity.this.isLate = jCurrentTimeMillis > j2 + (((long) i2) * 60);
                            }
                        }
                        int i3 = EstimateExplainActivity.this.status;
                        int i4 = R.drawable.linetype1_new;
                        switch (i3) {
                            case 1:
                            case 2:
                                TextView textView = EstimateExplainActivity.this.tiaozhuan;
                                if (!z2) {
                                    i4 = R.drawable.linetype1_night_new;
                                }
                                textView.setBackgroundResource(i4);
                                EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, z2 ? R.color.black : R.color.line_txt_color_night));
                                if (EstimateExplainActivity.this.status == 2) {
                                    EstimateExplainActivity.this.tiaozhuan.setEnabled(false);
                                    EstimateExplainActivity.this.tiaozhuan.setClickable(false);
                                    EstimateExplainActivity.this.getFromatData(esexBean.getServer_time());
                                    break;
                                }
                                break;
                            case 3:
                                if (!EstimateExplainActivity.this.isLate) {
                                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.shape_new_fail_color_3);
                                    if (SkinManager.getCurrentSkinType(EstimateExplainActivity.this.mContext) == 0) {
                                        EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.computer_statistics_top_end_color));
                                    } else {
                                        EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.computer_statistics_top_end_color_night));
                                    }
                                } else if (z2) {
                                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_new);
                                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.gray_light));
                                } else {
                                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_night_new);
                                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.line_txt_color_night));
                                }
                                EstimateExplainActivity.this.tiaozhuan.setEnabled(!EstimateExplainActivity.this.isLate);
                                EstimateExplainActivity.this.tiaozhuan.setClickable(!EstimateExplainActivity.this.isLate);
                                if (EstimateExplainActivity.this.isLate) {
                                    EstimateExplainActivity.this.tiaozhuan.setText("考试中，入口关闭");
                                    EstimateExplainActivity.this.quexian.setText("考试中，入口关闭");
                                    break;
                                }
                                break;
                            case 4:
                            case 5:
                                if (z2) {
                                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_new);
                                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.gray_light));
                                } else {
                                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_night_new);
                                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.line_txt_color_night));
                                }
                                EstimateExplainActivity.this.tiaozhuan.setEnabled(false);
                                EstimateExplainActivity.this.tiaozhuan.setClickable(false);
                                break;
                            case 6:
                                if (z2) {
                                    EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype6_new);
                                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.app_theme_red));
                                } else {
                                    GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(EstimateExplainActivity.this.mContext, R.drawable.linetype6_new);
                                    if (gradientDrawable != null) {
                                        gradientDrawable.setColor(Color.parseColor("#28202E"));
                                        gradientDrawable.setStroke(CommonUtil.dip2px(EstimateExplainActivity.this.mContext, 0.5f), Color.parseColor("#733334"));
                                        EstimateExplainActivity.this.tiaozhuan.setBackground(gradientDrawable);
                                    } else {
                                        EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype6_night_new);
                                    }
                                    EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.app_theme_red_night));
                                }
                                if (!EstimateExplainActivity.this.dataBean.getIs_estimate().equals("1") && EstimateExplainActivity.this.type.equals("1")) {
                                    Message message = new Message();
                                    message.what = 4;
                                    message.obj = Long.valueOf(Long.parseLong(esexBean.getServer_time() + ""));
                                    EstimateExplainActivity.this.mHandler.sendMessage(message);
                                    break;
                                }
                                break;
                        }
                        if (EstimateExplainActivity.this.dataBean.getAcesss_cdkey().equals("1")) {
                            EstimateExplainActivity.this.xiaohongshuruihuan.setVisibility(0);
                        } else {
                            EstimateExplainActivity.this.xiaohongshuruihuan.setVisibility(8);
                        }
                        if (EstimateExplainActivity.this.status == 6 && EstimateExplainActivity.this.dataBean.getRedo().equals("1")) {
                            EstimateExplainActivity.this.chongzuo.setVisibility(0);
                        } else {
                            EstimateExplainActivity.this.chongzuo.setVisibility(8);
                        }
                    } else {
                        String strOptString = new JSONObject(s2).optString("message", "");
                        if (!TextUtils.isEmpty(strOptString)) {
                            EstimateExplainActivity.this.AlertToast(strOptString);
                        }
                    }
                    if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isAddMock, false, EstimateExplainActivity.this.mContext) && EstimateExplainActivity.this.type.equals("1")) {
                        if (EstimateExplainActivity.this.status == 3 || EstimateExplainActivity.this.status == 4) {
                            EstimateExplainActivity.this.status = 3;
                            if (z2) {
                                EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_new);
                                EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.white));
                            } else {
                                EstimateExplainActivity.this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_night_new);
                                EstimateExplainActivity.this.tiaozhuan.setTextColor(ContextCompat.getColor(EstimateExplainActivity.this, R.color.line_txt_color_night));
                            }
                        }
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
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_new);
                    this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.gray_light_new));
                } else {
                    this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_night_new);
                    this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.jiucuo_night));
                }
                this.tiaozhuan.setText("回顾试卷");
                Timer timer = this.timer;
                if (timer != null) {
                    timer.cancel();
                }
                this.timer = new Timer();
                TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.4
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 5;
                        EstimateExplainActivity.this.mHandler.sendMessage(messageObtain);
                    }
                };
                this.timerTask = timerTask;
                this.timer.schedule(timerTask, 1000L, 1000L);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void getQuestionData() {
        showProgressDialog("加载中...");
        YJYHttpUtils.get(getApplicationContext(), getIntent().getExtras().getString("question_file"), new AjaxParams(), new AnonymousClass7());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTimeFromInt(long time) {
        String str;
        String str2;
        String str3;
        if (time <= 0) {
            return "0天0小时0分钟0秒";
        }
        long j2 = time / 86400;
        long j3 = (time / 3600) % 24;
        long j4 = (time / 60) % 60;
        long j5 = time % 60;
        if (j3 < 10) {
            str = "0" + j3;
        } else {
            str = j3 + "";
        }
        if (j4 < 10) {
            str2 = "0" + j4;
        } else {
            str2 = j4 + "";
        }
        if (j5 < 10) {
            str3 = "0" + j5;
        } else {
            str3 = j5 + "";
        }
        return j2 + "天" + str + "小时" + str2 + "分钟" + str3 + "秒";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCommentNum(int comment_count) {
        this.unreadnum.setVisibility(0);
        this.unreadnum.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(comment_count)));
        this.iscoment = false;
        this.ispraise = false;
        this.is_collected = "0";
        if ("0".equals("1")) {
            this.shoucang.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.shoucangtwo : R.drawable.shoucangtwo_night);
        } else {
            this.shoucang.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.shoucang : R.drawable.shoucang_night);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(String str, String str2, String str3) {
        getActivityData(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        new DialogAcitivtyInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.u9
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f13981a.lambda$init$0(str, str2, str3);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        putCollectData(this.is_collected.equals("1") ? "0" : "1");
        this.is_collected = this.is_collected.equals("1") ? "0" : "1";
        boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 0;
        if (this.is_collected.equals("1")) {
            this.shoucang.setImageResource(z2 ? R.drawable.shoucang : R.drawable.shoucang_night);
        } else {
            this.shoucang.setImageResource(z2 ? R.drawable.shoucangtwo : R.drawable.shoucangtwo_night);
        }
        EventBus.getDefault().post(EventBusConstant.EVENT_QUESTION_SHEET_COLLECT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        Intent intent = new Intent(this, (Class<?>) DiscussPublicActivity.class);
        intent.putExtra("obj_id", getIntent().getExtras().getString("collection_id", "0") + "");
        intent.putExtra("module_type", 31);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", false);
        intent.putExtra("isZantongTrue", false);
        intent.putExtra("title", "" + getIntent().getExtras().getString("title"));
        intent.putExtra("commentEnum", DiscussStatus.CommentsOnTheEaluationModelTestQuestionnaire);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$11(boolean z2) {
        if (!z2) {
            getQuestionData();
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
        if (strConfig.equals("30") || strConfig.equals("33") || strConfig.equals("50") || strConfig.equals("60") || strConfig.equals("61") || strConfig.equals("62") || strConfig.equals("82")) {
            ComputerMockTestLoginAct.newIntent(this, getIntent().getExtras().getString("exam_id"), this.dataBean.getTitle(), this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute(), getIntent().getExtras().getString("question_file"), this.dataBean.getApp_name());
        } else {
            ComputerPersonalInfoSureAct.newIntent(this, this.dataBean.getApp_name(), this.dataBean.getTitle(), this.dataBean.getQuestion_number(), this.dataBean.getTotal_points(), getIntent().getExtras().getString("question_file"), getIntent().getExtras().getString("exam_id"), this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$12(AlertDialog alertDialog, View view) {
        alertDialog.cancel();
        ProjectApp.questExamList.clear();
        ProjectApp.questExamDataList.clear();
        if (this.dataBean.getComputer_based_test().equals("1")) {
            new ChooseMockTestMethodDialog(this.mContext, new ChooseMockTestMethodDialog.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.o9
                @Override // com.psychiatrygarden.widget.ChooseMockTestMethodDialog.ProjectChoosedInterface
                public final void mItemLinsenter(boolean z2) {
                    this.f13074a.lambda$mCommDialog$11(z2);
                }
            }).show();
        } else {
            getQuestionData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        int i2 = this.status;
        if (i2 == 1) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.dataBean.getActivity_id());
            ajaxParams.put("module_name", "exam");
            ajaxParams.put("id", getIntent().getExtras().getString("exam_id"));
            ajaxParams.put("c_id", this.dataBean.getC_id());
            MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.j9
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f12551a.lambda$setListenerForWidget$6();
                }
            });
            return;
        }
        if (i2 == 3) {
            if (this.isLate) {
                return;
            }
            if (this.dataBean.getIs_estimate().equals("1") || SharePreferencesUtils.readBooleanConfig(CommonParameter.isAddMock, false, this.mContext) || !this.type.equals("1") || (new Date().getTime() / 1000) - Long.parseLong(this.close_entrance_timestamp) <= 0) {
                ProjectApp.questExamList.clear();
                ProjectApp.questExamDataList.clear();
                if (this.dataBean.getComputer_based_test().equals("1")) {
                    new ChooseMockTestMethodDialog(this.mContext, new ChooseMockTestMethodDialog.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.k9
                        @Override // com.psychiatrygarden.widget.ChooseMockTestMethodDialog.ProjectChoosedInterface
                        public final void mItemLinsenter(boolean z2) {
                            this.f12583a.lambda$setListenerForWidget$7(z2);
                        }
                    }).show();
                    return;
                } else {
                    getQuestionData();
                    return;
                }
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_new);
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.gray_light));
            } else {
                this.tiaozhuan.setBackgroundResource(R.drawable.linetype1_night_new);
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.jiucuo_night));
            }
            this.tiaozhuan.setText("考试中，入口已关闭");
            this.quexian.setText("考试中，入口已关闭");
            return;
        }
        if (i2 != 6) {
            if (i2 != 1000) {
                return;
            }
            AlertToast("页面请求失败，请重新打开本页！");
            return;
        }
        if (!this.dataBean.getIs_estimate().equals("1")) {
            this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.l9
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    EstimateExplainActivity.lambda$setListenerForWidget$8(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.m9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f12692c.lambda$setListenerForWidget$9((Date) obj);
                }
            }));
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) SubQuestionCeshiDaActivity.class);
        intent.putExtra("exam_id", getIntent().getExtras().getString("exam_id"));
        intent.putExtra("question_file", getIntent().getExtras().getString("question_file"));
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        intent.putExtra("user_exam_time", this.dataBean.getUser_exam_time());
        intent.putExtra("score", this.dataBean.getScore());
        intent.putExtra("typeData", this.dataBean.getType() + "");
        intent.putExtra("statusData", this.status + "");
        intent.putExtra("is_esaydta", this.dataBean.getIs_estimate());
        intent.putExtra("is_school_rank", this.dataBean.getIs_school_rank());
        intent.putExtra("bindSchool", this.dataBean.getUser_exam_info());
        intent.putExtra("answer_number", "" + this.dataBean.getAnswer_number());
        intent.putExtra("score", this.dataBean.getScore());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        mCommDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6() {
        this.status = 3;
        if (this.isLate) {
            this.tiaozhuan.setText("考试中，入口关闭");
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_new);
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.white));
            } else {
                this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_night_new);
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.line_txt_color_night));
            }
        } else {
            this.tiaozhuan.setText("开始考试");
            this.tiaozhuan.setBackgroundResource(R.drawable.shape_new_fail_color_12);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.computer_statistics_top_end_color));
            } else {
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.computer_statistics_top_end_color_night));
            }
        }
        this.tiaozhuan.setEnabled(!this.isLate);
        this.tiaozhuan.setClickable(!this.isLate);
        this.quexian.setText(this.isLate ? "考试中，入口关闭" : "考试入口已开放");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(boolean z2) {
        if (!z2) {
            getQuestionData();
            return;
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
        if (strConfig.equals("30") || strConfig.equals("33") || strConfig.equals("50") || strConfig.equals("60") || strConfig.equals("61") || strConfig.equals("62") || strConfig.equals("82")) {
            ComputerMockTestLoginAct.newIntent(this, getIntent().getExtras().getString("exam_id"), this.dataBean.getTitle(), this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute(), getIntent().getExtras().getString("question_file"), this.dataBean.getApp_name());
        } else {
            ComputerPersonalInfoSureAct.newIntent(this, this.dataBean.getApp_name(), this.dataBean.getTitle(), this.dataBean.getQuestion_number(), this.dataBean.getTotal_points(), getIntent().getExtras().getString("question_file"), getIntent().getExtras().getString("exam_id"), this.dataBean.getDate_end_timestamp(), this.dataBean.getDuration_minute(), this.dataBean.getSubmitMinute());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setListenerForWidget$8(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(CommonUtil.getNetTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(Date date) throws Exception {
        if (this.type.equals("1") && date.getTime() / 1000 < Long.parseLong(this.date_end_timestamp)) {
            AlertToast("全场考试结束后可回顾试卷，并获得模考成绩及排名");
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) SubQuestionCeshiDaActivity.class);
        intent.putExtra("exam_id", getIntent().getExtras().getString("exam_id"));
        intent.putExtra("question_file", getIntent().getExtras().getString("question_file"));
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        intent.putExtra("user_exam_time", this.dataBean.getUser_exam_time());
        intent.putExtra("score", this.dataBean.getScore());
        intent.putExtra("typeData", this.dataBean.getType() + "");
        intent.putExtra("statusData", this.status + "");
        intent.putExtra("score", this.dataBean.getScore());
        intent.putExtra("is_esaydta", this.dataBean.getIs_estimate());
        intent.putExtra("is_school_rank", this.dataBean.getIs_school_rank());
        intent.putExtra("bindSchool", this.dataBean.getUser_exam_info());
        intent.putExtra("answer_number", "" + this.dataBean.getAnswer_number());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLiveButton$4(String str, QuestioBean.LiveAbout liveAbout, String str2, View view) {
        if ("3".equals(str)) {
            ToastUtil.shortToast(this, "剪辑未完成");
            return;
        }
        if ("2".equals(str)) {
            if (TextUtils.isEmpty(liveAbout.getLive_info().getVid())) {
                return;
            }
            ShowVideoDialog.newInstance(this.mContext, liveAbout.getLive_info().getVid(), "0").showDialog(this.mContext, getWindow().getDecorView());
        } else {
            boolean z2 = "0".equals(str) && (((Long.parseLong(liveAbout.getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)) > 1800L ? 1 : ((Long.parseLong(liveAbout.getLive_info().getStart_live_time()) - (System.currentTimeMillis() / 1000)) == 1800L ? 0 : -1)) <= 0);
            if ("1".equals(str) || z2) {
                CommonUtil.launchLiving(this, liveAbout.getLive_ini().getPolyv_user_id(), liveAbout.getLive_ini().getPolyv_app_id(), liveAbout.getLive_ini().getPolyv_app_secret(), liveAbout.getLive_info().getRoom_id(), "0", liveAbout.getLive_info().getLive_id());
            } else {
                NewToast.showShort(this, str2);
            }
        }
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
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.i9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12501c.lambda$mCommDialog$12(alertDialogCreate, view);
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.n9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    alertDialogCreate.dismiss();
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void putCollectData(String operation) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("collection_id", "" + getIntent().getStringExtra("collection_id"));
        ajaxParams.put("operation", "" + operation);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCollectSetUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ToastUtil.shortToast(EstimateExplainActivity.this, new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLiveButton(final QuestioBean.LiveAbout dataBean) {
        if (dataBean == null) {
            return;
        }
        try {
            boolean z2 = SkinManager.getCurrentSkinType(this) == 0;
            TextView textView = this.live_video;
            int i2 = R.drawable.linetype1_new;
            textView.setBackgroundResource(z2 ? R.drawable.linetype1_new : R.drawable.linetype1_night_new);
            TextView textView2 = this.live_video;
            int i3 = R.color.black;
            int i4 = R.color.line_txt_color_night;
            textView2.setTextColor(ContextCompat.getColor(this, z2 ? R.color.black : R.color.line_txt_color_night));
            final String live_status = dataBean.getLive_status();
            String show_live_button = dataBean.getShow_live_button();
            final String live_tips = dataBean.getLive_tips();
            if ("".equals(live_status) || "0".equals(show_live_button)) {
                this.live_video.setVisibility(8);
            } else {
                this.live_video.setVisibility(0);
                if ("1".equals(live_status) || "2".equals(live_status)) {
                    this.live_video.setBackgroundResource(z2 ? R.drawable.linetype3_new : R.drawable.linetype3_night_new);
                    TextView textView3 = this.live_video;
                    if (z2) {
                        i4 = R.color.white;
                    }
                    textView3.setTextColor(ContextCompat.getColor(this, i4));
                } else {
                    TextView textView4 = this.tiaozhuan;
                    if (!z2) {
                        i2 = R.drawable.linetype1_night_new;
                    }
                    textView4.setBackgroundResource(i2);
                    TextView textView5 = this.tiaozhuan;
                    if (!z2) {
                        i3 = R.color.line_txt_color_night;
                    }
                    textView5.setTextColor(ContextCompat.getColor(this, i3));
                }
            }
            if (!TextUtils.isEmpty(live_tips) && !"0".equals(live_status)) {
                this.live_video.setText(live_tips);
            }
            if ("0".equals(live_status)) {
                this.mHandler.sendEmptyMessageDelayed(10012, 1000L);
            }
            this.live_video.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14016c.lambda$setLiveButton$4(live_status, dataBean, live_tips, view);
                }
            });
        } catch (Exception e2) {
            Log.d(this.TAG, "setLiveButton: " + e2.getMessage());
        }
    }

    public void getActivityData(String text) {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        ajaxParams.put("active_code", text);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mactiveurl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                EstimateExplainActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EstimateExplainActivity.this.getEsExData();
                    }
                    EstimateExplainActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                EstimateExplainActivity.this.hideProgressDialog();
            }
        });
    }

    public void getFromatData(String timestr) throws NumberFormatException {
        try {
            this.second = Long.parseLong(this.date_start_timestamp) - Long.parseLong(timestr);
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
            }
            if (this.second > 0) {
                this.timer = new Timer();
                TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.activity.EstimateExplainActivity.5
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 1;
                        EstimateExplainActivity.this.mHandler.sendMessage(messageObtain);
                    }
                };
                this.timerTask = timerTask;
                this.timer.schedule(timerTask, 1000L, 1000L);
                return;
            }
            if (!this.dataBean.getIs_estimate().equals("1")) {
                this.status = 3;
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_new);
                    this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.white));
                    return;
                } else {
                    this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_night_new);
                    this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.line_txt_color_night));
                    return;
                }
            }
            if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                this.status = 1;
                try {
                    this.tiaozhuan.setText(this.dataBean.getShare().getTips());
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    this.tiaozhuan.setText("点击分享解锁");
                    return;
                }
            }
            this.status = 3;
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_new);
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.white));
            } else {
                this.tiaozhuan.setBackgroundResource(R.drawable.linetype3_night_new);
                this.tiaozhuan.setTextColor(ContextCompat.getColor(this, R.color.line_txt_color_night));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.live_video = (TextView) findViewById(R.id.live_video);
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.iconheader);
        TextView textView = (TextView) findViewById(R.id.user_name);
        ((LinearLayout) findViewById(R.id.lineview)).setVisibility(0);
        GlideImageView glideImageView = (GlideImageView) findViewById(R.id.shoucang);
        this.shoucang = glideImageView;
        glideImageView.setVisibility(8);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pinglunrel);
        GlideImageView glideImageView2 = (GlideImageView) findViewById(R.id.pinglunimg);
        this.mBtnActionbarRight.setVisibility(8);
        this.unreadnum = (TextView) findViewById(R.id.unreadnum);
        this.quexian = (TextView) findViewById(R.id.quexian);
        this.kaoshileixing = (TextView) findViewById(R.id.kaoshileixing);
        this.kaoshitiliang = (TextView) findViewById(R.id.kaoshitiliang);
        this.juanmianfenzhi = (TextView) findViewById(R.id.juanmianfenzhi);
        this.kaoshishijian = (TextView) findViewById(R.id.kaoshishijian);
        this.wenxintishi = (TextView) findViewById(R.id.wenxintishi);
        this.tiaozhuan = (TextView) findViewById(R.id.tiaozhuan);
        this.chongzuo = (TextView) findViewById(R.id.chongzuo);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.xiaohongshuruihuan);
        this.xiaohongshuruihuan = linearLayout;
        linearLayout.setVisibility(8);
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), circleImageView);
        textView.setText(UserConfig.getInstance().getUser().getNickname());
        glideImageView2.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.pinglunimg_night : R.drawable.pinglunimg);
        this.xiaohongshuruihuan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.p9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13535c.lambda$init$1(view);
            }
        });
        try {
            this.shoucang.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.q9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13730c.lambda$init$2(view);
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.r9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13761c.lambda$init$3(view);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.chongzuo.setBackgroundResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.linetype1_night_new : R.drawable.linetype1_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, resultCode, data);
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
        if (!this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
        SharePreferencesUtils.removeConfig("statisticsPermission", this);
        SharePreferencesUtils.removeConfig("statisticsActiveId", this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
        }
        TimerTask timerTask = this.timerTask;
        if (timerTask != null) {
            timerTask.cancel();
        }
        getEsExData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_estimate_explain);
        setTitle(getIntent().getExtras().getString("title"));
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.chongzuo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.s9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13824c.lambda$setListenerForWidget$5(view);
            }
        });
        this.tiaozhuan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.t9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13947c.lambda$setListenerForWidget$10(view);
            }
        });
    }
}
