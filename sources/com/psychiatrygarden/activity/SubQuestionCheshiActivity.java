package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.lang.RegexPool;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.MockTestAdapter;
import com.psychiatrygarden.adapter.QuestListAdapter;
import com.psychiatrygarden.bean.AnswerBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ExamTimeHintPop;
import com.psychiatrygarden.widget.ViewPagerCompat;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubQuestionCheshiActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static ViewPagerCompat viewPager;
    private TextView cusposition;
    private ImageView gufenjh;
    private ExamTimeHintPop mHintPop;
    private MockTestAdapter msAdapter;
    private ArrayMap<String, List<ExesQuestionBean>> shareStemMap;
    private boolean showPop;
    private long startTime;
    private int submitMinute;
    public long timepubstr;
    private Timer timer;
    private TimerTask timerTask;
    TextView timerstrs;
    private TextView timestr;
    private TextView tvTitle;
    private TextView tv_jj;
    private List<ExesQuestionBean> questBeans = new ArrayList();
    private boolean isShow = false;
    private boolean needCountDown = false;
    private boolean from_my_exam = false;
    long minute = 0;
    long second = 0;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private long startExamTime = 0;
    private final ArrayList<Disposable> disposables = new ArrayList<>(0);
    private List<ExesQuestionBean> shareStemExamList = new ArrayList();
    private long startDoTime = 0;
    private int mCurrentPosition = -1;
    private String bindSchool = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) throws Resources.NotFoundException {
            StringBuilder sb;
            String string;
            StringBuilder sb2;
            String string2;
            StringBuilder sb3;
            StringBuilder sb4;
            StringBuilder sb5;
            StringBuilder sb6;
            StringBuilder sb7;
            StringBuilder sb8;
            List list;
            int actualStemIndex;
            int i2 = msg.what;
            if (i2 != 0) {
                if (i2 == 1) {
                    int i3 = msg.arg1;
                    if (msg.arg2 == ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(SubQuestionCheshiActivity.this.questBeans.size() - 1)).getActualStemIndex() && i3 == SubQuestionCheshiActivity.this.shareStemExamList.size() - 1) {
                        SubQuestionCheshiActivity.this.AlertToast("已是最后一题");
                        return;
                    } else if (i3 < SubQuestionCheshiActivity.this.shareStemExamList.size() - 1) {
                        SubQuestionCheshiActivity.viewPager.setCurrentItem(i3 + 1, false);
                        return;
                    } else {
                        SubQuestionCheshiActivity.this.AlertToast("已是最后一题");
                        return;
                    }
                }
                if (i2 == 2) {
                    int i4 = msg.arg1;
                    if (i4 >= SubQuestionCheshiActivity.this.questBeans.size() || SubQuestionCheshiActivity.this.shareStemExamList.isEmpty()) {
                        return;
                    }
                    String public_number = ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(i4)).getPublic_number();
                    if (TextUtils.isEmpty(public_number)) {
                        for (int i5 = 0; i5 < SubQuestionCheshiActivity.this.shareStemExamList.size(); i5++) {
                            if (TextUtils.equals(((ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(i5)).getQuestion_id(), ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(i4)).getQuestion_id())) {
                                SubQuestionCheshiActivity.viewPager.setCurrentItem(i5, false);
                                return;
                            }
                        }
                        return;
                    }
                    for (int i6 = 0; i6 < SubQuestionCheshiActivity.this.shareStemExamList.size(); i6++) {
                        ExesQuestionBean exesQuestionBean = (ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(i6);
                        View pageView = SubQuestionCheshiActivity.this.msAdapter.getPageView(i6);
                        if (TextUtils.equals(exesQuestionBean.getPublic_number(), public_number) && (list = (List) SubQuestionCheshiActivity.this.shareStemMap.get(public_number)) != null) {
                            Iterator it = list.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    actualStemIndex = 0;
                                    break;
                                }
                                ExesQuestionBean exesQuestionBean2 = (ExesQuestionBean) it.next();
                                if (TextUtils.equals(exesQuestionBean2.getQuestion_id(), ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(i4)).getQuestion_id())) {
                                    actualStemIndex = exesQuestionBean2.getActualStemIndex();
                                    break;
                                }
                            }
                            if (pageView == null) {
                                ((ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(i6)).setJumpPosition(actualStemIndex);
                                SubQuestionCheshiActivity.viewPager.setCurrentItem(i6, false);
                            } else {
                                ViewPager viewPager2 = (ViewPager) pageView.findViewById(R.id.viewpager);
                                int i7 = 0;
                                while (true) {
                                    if (i7 >= list.size()) {
                                        break;
                                    }
                                    if (((ExesQuestionBean) list.get(i7)).getActualStemIndex() == ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(i4)).getActualStemIndex()) {
                                        SubQuestionCheshiActivity.viewPager.setCurrentItem(i6, false);
                                        viewPager2.setCurrentItem(i7, false);
                                        break;
                                    }
                                    i7++;
                                }
                            }
                        }
                    }
                    return;
                }
                if (i2 == 3) {
                    try {
                        long time = new Date().getTime() / 1000;
                        if (time == 0) {
                            time = System.currentTimeMillis() / 1000;
                        }
                        String string3 = SubQuestionCheshiActivity.this.getIntent().getExtras().getString("date_end_timestamp", "0");
                        long j2 = (TextUtils.isEmpty(string3) || !string3.matches("^\\d+$")) ? 0L : Long.parseLong(SubQuestionCheshiActivity.this.getIntent().getExtras().getString("date_end_timestamp", "0"));
                        if (j2 <= 0 || j2 - time >= 0) {
                            SubQuestionCheshiActivity.this.startTimer();
                            return;
                        }
                        if (SubQuestionCheshiActivity.this.timestr != null) {
                            SubQuestionCheshiActivity.this.timestr.setText("考试时间结束!");
                        }
                        SubQuestionCheshiActivity.this.getScore(2);
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                if (i2 != 999) {
                    return;
                }
                int size = SubQuestionCheshiActivity.this.questBeans.size();
                String str = msg.obj + "";
                String str2 = SkinManager.getCurrentSkinType(SubQuestionCheshiActivity.this.mContext) == 1 ? "#64729F" : "#000000";
                if (SkinManager.getCurrentSkinType(SubQuestionCheshiActivity.this.mContext) != 1) {
                    SubQuestionCheshiActivity.this.cusposition.setText(CharacterParser.getSpannableColorSize(str + " /" + size, 0, str.length(), str2));
                    return;
                }
                SpannableString spannableString = new SpannableString(str + " /" + size);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SubQuestionCheshiActivity.this.mContext, R.color.first_text_color_night)), 0, (str + "").length(), 18);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SubQuestionCheshiActivity.this.mContext, R.color.tertiary_text_color_night)), (str + "").length(), spannableString.length(), 34);
                SubQuestionCheshiActivity.this.cusposition.setText(spannableString);
                return;
            }
            if (!SubQuestionCheshiActivity.this.isShow) {
                boolean zEquals = SubQuestionCheshiActivity.this.getIntent().getExtras().getString("is_esaydta").equals("1");
                int i8 = R.color.question_color_night;
                if (zEquals) {
                    SubQuestionCheshiActivity.this.gufenjh.setImageResource(R.drawable.gufenjj);
                    SubQuestionCheshiActivity.this.gufenjh.setImageResource(SkinManager.getCurrentSkinType(SubQuestionCheshiActivity.this.mContext) == 0 ? R.drawable.gufenjj : R.drawable.gufenjj_night);
                    TextView textView = SubQuestionCheshiActivity.this.tv_jj;
                    Context context = SubQuestionCheshiActivity.this.mContext;
                    if (SkinManager.getCurrentSkinType(context) == 0) {
                        i8 = R.color.question_color;
                    }
                    textView.setTextColor(ContextCompat.getColor(context, i8));
                } else {
                    SubQuestionCheshiActivity subQuestionCheshiActivity = SubQuestionCheshiActivity.this;
                    subQuestionCheshiActivity.isShow = subQuestionCheshiActivity.minute < subQuestionCheshiActivity.timepubstr - 30;
                    ImageView imageView = SubQuestionCheshiActivity.this.gufenjh;
                    SubQuestionCheshiActivity subQuestionCheshiActivity2 = SubQuestionCheshiActivity.this;
                    imageView.setImageResource(subQuestionCheshiActivity2.minute < subQuestionCheshiActivity2.timepubstr - 30 ? SkinManager.getCurrentSkinType(subQuestionCheshiActivity2.mContext) == 0 ? R.drawable.gufenjj : R.drawable.gufenjj_night : SkinManager.getCurrentSkinType(subQuestionCheshiActivity2.mContext) == 0 ? R.drawable.gufenjh : R.drawable.gufenjh_night);
                    TextView textView2 = SubQuestionCheshiActivity.this.tv_jj;
                    SubQuestionCheshiActivity subQuestionCheshiActivity3 = SubQuestionCheshiActivity.this;
                    Context context2 = subQuestionCheshiActivity3.mContext;
                    if (subQuestionCheshiActivity3.minute < subQuestionCheshiActivity3.timepubstr - 30) {
                        if (SkinManager.getCurrentSkinType(context2) == 0) {
                            i8 = R.color.black_gray;
                        }
                    } else if (SkinManager.getCurrentSkinType(context2) == 0) {
                        i8 = R.color.gray_light_new;
                    }
                    textView2.setTextColor(ContextCompat.getColor(context2, i8));
                }
            }
            SubQuestionCheshiActivity subQuestionCheshiActivity4 = SubQuestionCheshiActivity.this;
            if (subQuestionCheshiActivity4.timepubstr - subQuestionCheshiActivity4.minute <= 30) {
                subQuestionCheshiActivity4.timestr.setTextColor(ContextCompat.getColor(SubQuestionCheshiActivity.this.mContext, R.color.app_theme_red));
                SubQuestionCheshiActivity subQuestionCheshiActivity5 = SubQuestionCheshiActivity.this;
                TextView textView3 = subQuestionCheshiActivity5.timerstrs;
                if (textView3 != null) {
                    textView3.setTextColor(ContextCompat.getColor(subQuestionCheshiActivity5.mContext, R.color.app_theme_red));
                }
            }
            SubQuestionCheshiActivity subQuestionCheshiActivity6 = SubQuestionCheshiActivity.this;
            long j3 = subQuestionCheshiActivity6.minute;
            if (j3 <= 0) {
                long j4 = subQuestionCheshiActivity6.second;
                if (j4 <= 0) {
                    subQuestionCheshiActivity6.timestr.setText("考试时间结束!");
                    TextView textView4 = SubQuestionCheshiActivity.this.timerstrs;
                    if (textView4 != null) {
                        textView4.setText("考试时间结束!");
                    }
                    if (SubQuestionCheshiActivity.this.timer != null) {
                        SubQuestionCheshiActivity.this.timer.cancel();
                        SubQuestionCheshiActivity.this.timer = null;
                    }
                    if (SubQuestionCheshiActivity.this.timerTask != null) {
                        SubQuestionCheshiActivity.this.timerTask = null;
                    }
                    SubQuestionCheshiActivity.this.getScore(2);
                    return;
                }
                subQuestionCheshiActivity6.second = j4 - 1;
                TextView textView5 = subQuestionCheshiActivity6.timestr;
                if (SubQuestionCheshiActivity.this.second >= 10) {
                    sb7 = new StringBuilder();
                    sb7.append("0");
                    sb7.append(SubQuestionCheshiActivity.this.minute);
                    sb7.append(":");
                } else {
                    sb7 = new StringBuilder();
                    sb7.append("0");
                    sb7.append(SubQuestionCheshiActivity.this.minute);
                    sb7.append(":0");
                }
                sb7.append(SubQuestionCheshiActivity.this.second);
                textView5.setText(sb7.toString());
                SubQuestionCheshiActivity subQuestionCheshiActivity7 = SubQuestionCheshiActivity.this;
                TextView textView6 = subQuestionCheshiActivity7.timerstrs;
                if (textView6 != null) {
                    if (subQuestionCheshiActivity7.second >= 10) {
                        sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(SubQuestionCheshiActivity.this.minute);
                        sb8.append(":");
                    } else {
                        sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(SubQuestionCheshiActivity.this.minute);
                        sb8.append(":0");
                    }
                    sb8.append(SubQuestionCheshiActivity.this.second);
                    textView6.setText(sb8.toString());
                    return;
                }
                return;
            }
            long j5 = subQuestionCheshiActivity6.second;
            if (j5 == 0) {
                subQuestionCheshiActivity6.second = 59L;
                subQuestionCheshiActivity6.minute = j3 - 1;
                TextView textView7 = subQuestionCheshiActivity6.timestr;
                if (SubQuestionCheshiActivity.this.minute >= 10) {
                    sb5 = new StringBuilder();
                } else {
                    sb5 = new StringBuilder();
                    sb5.append("0");
                }
                sb5.append(SubQuestionCheshiActivity.this.minute);
                sb5.append(":");
                sb5.append(SubQuestionCheshiActivity.this.second);
                textView7.setText(sb5.toString());
                SubQuestionCheshiActivity subQuestionCheshiActivity8 = SubQuestionCheshiActivity.this;
                TextView textView8 = subQuestionCheshiActivity8.timerstrs;
                if (textView8 != null) {
                    if (subQuestionCheshiActivity8.minute >= 10) {
                        sb6 = new StringBuilder();
                    } else {
                        sb6 = new StringBuilder();
                        sb6.append("0");
                    }
                    sb6.append(SubQuestionCheshiActivity.this.minute);
                    sb6.append(":");
                    sb6.append(SubQuestionCheshiActivity.this.second);
                    textView8.setText(sb6.toString());
                    return;
                }
                return;
            }
            subQuestionCheshiActivity6.second = j5 - 1;
            TextView textView9 = subQuestionCheshiActivity6.timestr;
            SubQuestionCheshiActivity subQuestionCheshiActivity9 = SubQuestionCheshiActivity.this;
            if (subQuestionCheshiActivity9.second >= 10) {
                if (subQuestionCheshiActivity9.minute >= 10) {
                    sb4 = new StringBuilder();
                } else {
                    sb4 = new StringBuilder();
                    sb4.append("0");
                }
                sb4.append(SubQuestionCheshiActivity.this.minute);
                sb4.append(":");
                sb4.append(SubQuestionCheshiActivity.this.second);
                string = sb4.toString();
            } else {
                if (subQuestionCheshiActivity9.minute >= 10) {
                    sb = new StringBuilder();
                } else {
                    sb = new StringBuilder();
                    sb.append("0");
                }
                sb.append(SubQuestionCheshiActivity.this.minute);
                sb.append(":0");
                sb.append(SubQuestionCheshiActivity.this.second);
                string = sb.toString();
            }
            textView9.setText(string);
            SubQuestionCheshiActivity subQuestionCheshiActivity10 = SubQuestionCheshiActivity.this;
            TextView textView10 = subQuestionCheshiActivity10.timerstrs;
            if (textView10 != null) {
                if (subQuestionCheshiActivity10.second >= 10) {
                    if (subQuestionCheshiActivity10.minute >= 10) {
                        sb3 = new StringBuilder();
                    } else {
                        sb3 = new StringBuilder();
                        sb3.append("0");
                    }
                    sb3.append(SubQuestionCheshiActivity.this.minute);
                    sb3.append(":");
                    sb3.append(SubQuestionCheshiActivity.this.second);
                    string2 = sb3.toString();
                } else {
                    if (subQuestionCheshiActivity10.minute >= 10) {
                        sb2 = new StringBuilder();
                    } else {
                        sb2 = new StringBuilder();
                        sb2.append("0");
                    }
                    sb2.append(SubQuestionCheshiActivity.this.minute);
                    sb2.append(":0");
                    sb2.append(SubQuestionCheshiActivity.this.second);
                    string2 = sb2.toString();
                }
                textView10.setText(string2);
            }
        }
    };

    @SuppressLint({"HandlerLeak"})
    private final Handler handler = new Handler() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.6
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                String string = msg.getData().getString("date_end");
                String string2 = msg.getData().getString("score");
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, false, SubQuestionCheshiActivity.this.mContext);
                ProjectApp.questExamList.clear();
                ProjectApp.questExamList.addAll(SubQuestionCheshiActivity.this.questBeans);
                if (SubQuestionCheshiActivity.this.from_my_exam || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1").equals(Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
                    SubQuestionCheshiActivity.this.AlertToast("提交成功");
                } else if (SubQuestionCheshiActivity.this.getIntent().getExtras().getString("is_esaydta").equals("1")) {
                    SubQuestionCheshiActivity subQuestionCheshiActivity = SubQuestionCheshiActivity.this;
                    long time = subQuestionCheshiActivity.df.parse(string).getTime() / 1000;
                    SubQuestionCheshiActivity subQuestionCheshiActivity2 = SubQuestionCheshiActivity.this;
                    SubQuestionCheshiActivity.this.getLockMethod(subQuestionCheshiActivity.getTimeFromInt(time - (subQuestionCheshiActivity2.df.parse(subQuestionCheshiActivity2.getIntent().getExtras().getString("startData", "0")).getTime() / 1000)), string2);
                } else if (!SubQuestionCheshiActivity.this.getIntent().getExtras().getString("typeData").equals("1")) {
                    SubQuestionCheshiActivity subQuestionCheshiActivity3 = SubQuestionCheshiActivity.this;
                    long time2 = subQuestionCheshiActivity3.df.parse(string).getTime() / 1000;
                    SubQuestionCheshiActivity subQuestionCheshiActivity4 = SubQuestionCheshiActivity.this;
                    SubQuestionCheshiActivity.this.getLockMethod(subQuestionCheshiActivity3.getTimeFromInt(time2 - (subQuestionCheshiActivity4.df.parse(subQuestionCheshiActivity4.getIntent().getExtras().getString("startData", "0")).getTime() / 1000)), string2);
                }
                SubQuestionCheshiActivity.this.hideProgressDialog();
                EventBus.getDefault().post("FinishTheJob");
                SubQuestionCheshiActivity.this.finish();
            } catch (Exception e2) {
                SubQuestionCheshiActivity.this.hideProgressDialog();
                e2.printStackTrace();
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void getLockMethod(String timers, String score) {
        if (SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
            MemInterface.getInstance().getMemData(this, ajaxParams, true, 2);
            MemInterface.getInstance().setmMoreLockListener(new MemInterface.MoreMethodLisener() { // from class: com.psychiatrygarden.activity.sl
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.MoreMethodLisener
                public final void mMoreMethodLock() {
                    this.f13926a.lambda$getLockMethod$5();
                }
            });
            return;
        }
        MockKnowledgePointStatisticsAct.newIntent(this, getIntent().getExtras().getString("exam_id"), this.bindSchool, getIntent().getExtras().getString("title"), getIntent().getExtras().getString("is_esaydta"), getIntent().getExtras().getString("is_school_rank") + "");
    }

    private void getQuestionData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.from_my_exam) {
            ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        }
        YJYHttpUtils.get(getApplicationContext(), getIntent().getStringExtra("question_file"), ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    if (SubQuestionCheshiActivity.this.questBeans != null && SubQuestionCheshiActivity.this.questBeans.size() > 0) {
                        SubQuestionCheshiActivity.this.setData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SubQuestionCheshiActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SubQuestionCheshiActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass7) t2);
                try {
                    if (SubQuestionCheshiActivity.this.from_my_exam) {
                        JSONArray jSONArray = new JSONArray(t2);
                        int length = jSONArray.length();
                        Gson gson = new Gson();
                        ArrayList arrayList = null;
                        int i2 = 0;
                        while (i2 < length) {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                            String strOptString = jSONObjectOptJSONObject.optString(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION);
                            if (!TextUtils.isEmpty(strOptString)) {
                                arrayList = (ArrayList) gson.fromJson(strOptString, new TypeToken<ArrayList<ExesQuestionBean.OptionBean>>() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.7.1
                                }.getType());
                                jSONObjectOptJSONObject.remove(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION);
                            }
                            ExesQuestionBean exesQuestionBean = (ExesQuestionBean) gson.fromJson(jSONObjectOptJSONObject.toString(), ExesQuestionBean.class);
                            if (arrayList != null) {
                                exesQuestionBean.setOption(arrayList);
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append("");
                            i2++;
                            sb.append(i2);
                            exesQuestionBean.setNumber(sb.toString());
                            SubQuestionCheshiActivity.this.questBeans.add(exesQuestionBean);
                        }
                    } else {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data"));
                        SubQuestionCheshiActivity.this.questBeans.clear();
                        SubQuestionCheshiActivity.this.questBeans = (List) new Gson().fromJson(strDecode, new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.7.2
                        }.getType());
                        Collections.sort(SubQuestionCheshiActivity.this.questBeans);
                    }
                    if (!SubQuestionCheshiActivity.this.questBeans.isEmpty()) {
                        SubQuestionCheshiActivity.this.startExamTime = System.currentTimeMillis();
                        for (int i3 = 0; i3 < SubQuestionCheshiActivity.this.questBeans.size(); i3++) {
                            String number = ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(i3)).getNumber();
                            if (TextUtils.isEmpty(number) || !number.matches(RegexPool.NUMBERS)) {
                                ((ExesQuestionBean) SubQuestionCheshiActivity.this.questBeans.get(i3)).setNumber((i3 + 1) + "");
                            }
                        }
                        SubQuestionCheshiActivity.this.setData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    SubQuestionCheshiActivity.this.AlertToast("题型解析错误");
                    SubQuestionCheshiActivity.this.finish();
                }
                SubQuestionCheshiActivity.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getScore(int type) {
        if (type == 1) {
            long jElapsedRealtime = ((SystemClock.elapsedRealtime() - this.startTime) / 1000) / 60;
            int i2 = this.submitMinute;
            if (jElapsedRealtime < i2) {
                AlertToast(String.format(Locale.CHINA, "考试时长不满足交卷条件，最少做题%d分钟", Integer.valueOf(i2)));
                return;
            }
        }
        if (type != 1) {
            if (this.needCountDown) {
                final String str = "0.0";
                Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.vl
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        SubQuestionCheshiActivity.lambda$getScore$10(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.wl
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f14158c.lambda$getScore$11(str, (Date) obj);
                    }
                });
                mCommDialog("答题时间已到，系统自动交卷！", 2);
                return;
            }
            return;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.questBeans.size(); i4++) {
            if (this.questBeans.get(i4).getOwnAns() == null || this.questBeans.get(i4).getOwnAns().equals("")) {
                i3++;
            }
        }
        scoreTwoDialog("0.0", i3 == 0 ? "交卷将结束此次考试，是否交卷？" : "您还有" + i3 + "题未作答，交卷将结束此次考试，是否交卷?");
    }

    private String getTypeStr(String type) throws Resources.NotFoundException {
        int i2;
        String[] stringArray = getResources().getStringArray(R.array.exam_type);
        if (type == null) {
            return null;
        }
        if (type.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE)) {
            return "判断题";
        }
        if (!type.matches("-?\\d+.?\\d*") || (i2 = Integer.parseInt(type)) < 1) {
            return null;
        }
        int i3 = i2 - 1;
        if (i3 < stringArray.length) {
            return stringArray[i3];
        }
        if (i3 == stringArray.length) {
            return stringArray[stringArray.length - 1];
        }
        return null;
    }

    private void handleShareStemQuestion() {
        boolean z2 = false;
        for (int i2 = 0; i2 < this.questBeans.size(); i2++) {
            String public_number = this.questBeans.get(i2).getPublic_number();
            this.questBeans.get(i2).setActualStemIndex(i2);
            if (TextUtils.isEmpty(public_number)) {
                if (z2) {
                    z2 = false;
                }
                this.shareStemExamList.add(this.questBeans.get(i2));
            } else {
                if (this.shareStemMap.get(public_number) == null) {
                    this.shareStemExamList.add(this.questBeans.get(i2));
                    this.shareStemMap.put(public_number, new ArrayList());
                }
                this.shareStemMap.get(public_number).add(this.questBeans.get(i2));
                z2 = true;
            }
        }
    }

    public static void i(String tag, String msg) {
        int length = 2001 - tag.length();
        while (msg.length() > length) {
            Log.i(tag, msg.substring(0, length));
            msg = msg.substring(length);
        }
        Log.i(tag, msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLockMethod$5() {
        MockKnowledgePointStatisticsAct.newIntent(this, getIntent().getExtras().getString("exam_id"), this.bindSchool, getIntent().getExtras().getString("title"), getIntent().getExtras().getString("is_esaydta"), getIntent().getExtras().getString("is_school_rank") + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getScore$10(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(CommonUtil.getNetTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handDialog$12(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handDialog$13(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, false, this.mContext);
        ProjectApp.questExamDataList.clear();
        ProjectApp.questExamList.clear();
        customDialog.dismissNoAnimaltion();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mCommDialog$18(int i2, AlertDialog alertDialog, View view) {
        if (i2 != 1) {
            alertDialog.dismiss();
        } else {
            alertDialog.dismiss();
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.EXAMIDTRUE, false, this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$6() {
        this.mHandler.sendEmptyMessage(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$scoreTwoDialog$14(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(CommonUtil.getNetTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scoreTwoDialog$16(CustomDialog customDialog, final String str, View view) {
        customDialog.dismissNoAnimaltion();
        Disposable disposableSubscribe = Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.jl
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                SubQuestionCheshiActivity.lambda$scoreTwoDialog$14(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.kl
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f12595c.lambda$scoreTwoDialog$15(str, (Date) obj);
            }
        });
        if (this.disposables.contains(disposableSubscribe)) {
            return;
        }
        this.disposables.add(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(int i2) {
        if (i2 == 1 && viewPager.getCurrentItem() == this.shareStemExamList.size() - 1) {
            ToastUtil.shortToast(this, "已是最后一题");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1(View view) {
        handDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$2(View view) {
        getScore(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$3(View view) {
        getScore(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$4(View view) {
        showSelectPop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showSelectPop$7(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$8(View view) {
        getScore(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$9(TextView textView, AlertDialog alertDialog, AdapterView adapterView, View view, int i2, long j2) {
        textView.setText(this.questBeans.get(i2).getActualStemIndex() + "/" + this.questBeans.size());
        Message message = new Message();
        message.what = 2;
        message.arg1 = i2;
        this.mHandler.sendMessage(message);
        alertDialog.dismiss();
    }

    private void postAnswerToAliyun(List<AnswerBean> mLists) {
        for (int i2 = 0; i2 < mLists.size(); i2++) {
            if (!TextUtils.isEmpty(mLists.get(i2).getAnswer()) && !TextUtils.isEmpty(mLists.get(i2).getDuration())) {
                QuestionDetailBean questionDetailBean = new QuestionDetailBean();
                questionDetailBean.setTime_used_ms((Integer.parseInt(mLists.get(i2).getDuration()) * 1000) + "");
                questionDetailBean.setModule_type("1");
                questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : questionDetailBean.getIs_redo());
                questionDetailBean.setExam_title(getIntent().getExtras().getString("title"));
                questionDetailBean.setExam_id(getIntent().getExtras().getString("exam_id"));
                questionDetailBean.setUser_answer(mLists.get(i2).getAnswer());
                questionDetailBean.setIs_right(mLists.get(i2).getIs_right());
                String json = ProjectApp.gson.toJson(questionDetailBean);
                String str = "[\"" + this.questBeans.get(i2).getQuestion_id() + "\"]";
                String str2 = "[\"" + this.questBeans.get(i2).getTitle() + "\"]";
                AliyunEvent aliyunEvent = AliyunEvent.SubmitAnswer;
                CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData() throws Resources.NotFoundException {
        handleShareStemQuestion();
        MockTestAdapter mockTestAdapter = new MockTestAdapter(this.shareStemMap, this.shareStemExamList, this.mHandler);
        this.msAdapter = mockTestAdapter;
        viewPager.setAdapter(mockTestAdapter);
        updateTitle(0);
        this.cusposition.setText(String.format(Locale.CHINA, "%d/%d", 1, Integer.valueOf(this.questBeans.size())));
        if (getIntent().getExtras().getString("typeData").equals("2") && SharePreferencesUtils.readBooleanConfig(CommonParameter.EXAMIDTRUE, true, this.mContext)) {
            mCommDialog("为确保数据真实可靠，仅对考生第一次作答成绩进行排名分析，请务必认真对待！", 1);
        }
        this.startTime = SystemClock.elapsedRealtime();
        startTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: upLoadData, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$scoreTwoDialog$15(final String str, Date date) {
        final String str2 = this.df.format(date);
        ArrayList arrayList = new ArrayList();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getIntent().getExtras().getString("exam_id"));
        try {
            ajaxParams.put("date_end_timestamp", (date.getTime() / 1000) + "");
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.df.parse(getIntent().getExtras().getString("startData", System.currentTimeMillis() + "")).getTime() / 1000);
            ajaxParams.put("date_start_timestamp", sb.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.questBeans.size(); i4++) {
            AnswerBean answerBean = new AnswerBean();
            answerBean.setAnswer(this.questBeans.get(i4).getOwnAns());
            boolean z2 = true;
            i2 += (this.questBeans.get(i4).getOwnAns() == null || TextUtils.isEmpty(this.questBeans.get(i4).getOwnAns())) ? 0 : 1;
            String ownAns = this.questBeans.get(i4).getOwnAns();
            String answer = this.questBeans.get(i4).getAnswer();
            if (ownAns != null && answer != null) {
                z2 = false;
            }
            answerBean.setIs_right(z2 ? "0" : this.questBeans.get(i4).getIsRight());
            i3 += TextUtils.equals(this.questBeans.get(i4).getIsRight(), "1") ? 1 : 0;
            answerBean.setQuestion_id(this.questBeans.get(i4).getQuestion_id());
            answerBean.setDuration(this.questBeans.get(i4).getDoDuration());
            answerBean.setSubject_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
            answerBean.setKnowledge_id(TextUtils.isEmpty(this.questBeans.get(i4).getKnowledge_id()) ? "" : this.questBeans.get(i4).getKnowledge_id());
            arrayList.add(answerBean);
        }
        long time = date.getTime();
        long j2 = (time - this.startExamTime) / 1000;
        LogUtils.d(getClass().getSimpleName(), "考试时间：" + (j2 / 60) + " 分钟");
        if (this.from_my_exam) {
            ajaxParams.put("date_start", this.df.format(new Date(this.startExamTime)));
            ajaxParams.put("date_end", this.df.format(new Date(time)));
        }
        ajaxParams.put(CommonParameter.EXAM_TIME, String.valueOf(j2));
        ajaxParams.put("right", String.valueOf(i3));
        ajaxParams.put("answer_num", String.valueOf(i2));
        ajaxParams.put("answer", new Gson().toJson(arrayList));
        showProgressDialog("提交中", false);
        postAnswerToAliyun(arrayList);
        YJYHttpUtils.post(this.mContext, !this.from_my_exam ? NetworkRequestsURL.mPostBatchUrl : NetworkRequestsURL.SUBMIT_ANSWER_RECORD, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubQuestionCheshiActivity.this.hideProgressDialog();
                SubQuestionCheshiActivity.this.AlertToast("答题记录上传失败，请手动交卷，否则答题记录将不被保存！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("date_end", str2);
                        bundle.putString("score", str);
                        message.setData(bundle);
                        message.what = 0;
                        SubQuestionCheshiActivity.this.handler.sendMessageDelayed(message, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    } else {
                        SubQuestionCheshiActivity.this.hideProgressDialog();
                        SubQuestionCheshiActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                    }
                } catch (Exception e3) {
                    SubQuestionCheshiActivity.this.hideProgressDialog();
                    e3.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTitle(int position) throws Resources.NotFoundException {
        String question_type;
        if (this.questBeans.isEmpty() || position >= this.questBeans.size()) {
            return;
        }
        ExesQuestionBean exesQuestionBean = this.questBeans.get(position);
        boolean zIsEmpty = true;
        if (this.from_my_exam) {
            question_type = null;
            if (TextUtils.isEmpty(exesQuestionBean.getType())) {
                zIsEmpty = false;
            } else {
                String typeStr = getTypeStr(exesQuestionBean.getType());
                zIsEmpty = true ^ TextUtils.isEmpty(typeStr);
                if (typeStr != null) {
                    question_type = typeStr + "，" + exesQuestionBean.getScore() + "分";
                }
            }
        } else {
            boolean z2 = !TextUtils.isEmpty(exesQuestionBean.getQuestion_type());
            question_type = exesQuestionBean.getQuestion_type();
            if (!TextUtils.isEmpty(question_type) || TextUtils.isEmpty(exesQuestionBean.getType_str())) {
                zIsEmpty = z2;
            } else {
                question_type = exesQuestionBean.getType_str() + "，" + exesQuestionBean.getScore() + "分";
            }
        }
        this.tvTitle.setVisibility((zIsEmpty && !TextUtils.isEmpty(question_type) && TextUtils.isEmpty(this.questBeans.get(position).getPublic_number())) ? 0 : 8);
        if (zIsEmpty) {
            this.tvTitle.setText(question_type);
        }
    }

    public String getTimeFromInt(long time) {
        if (time <= 0) {
            return "0小时0分钟0秒";
        }
        long j2 = (time / 3600) % 24;
        long j3 = (time / 60) % 60;
        long j4 = time % 60;
        if (j2 <= 0) {
            if (j3 <= 0) {
                return j4 + "秒";
            }
            return j3 + "分钟" + j4 + "秒";
        }
        if (j2 == 3) {
            return j2 + "小时";
        }
        return j2 + "小时" + j3 + "分钟" + j4 + "秒";
    }

    public void handDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage("返回将退出此次考试，是否退出？");
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubQuestionCheshiActivity.lambda$handDialog$12(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13803c.lambda$handDialog$13(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    public void mCommDialog(String textstr, final int type) {
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
            window.setContentView(R.layout.activity_commdialog);
            window.setGravity(17);
            window.setWindowAnimations(R.style.mystyle);
            alertDialogCreate.setCanceledOnTouchOutside(false);
            alertDialogCreate.setCancelable(false);
            TextView textView = (TextView) alertDialogCreate.findViewById(R.id.text);
            TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.mclick);
            textView.setText(textstr);
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xl
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14190c.lambda$mCommDialog$18(type, alertDialogCreate, view);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        Iterator<Disposable> it = this.disposables.iterator();
        while (it.hasNext()) {
            Disposable next = it.next();
            if (next != null && !next.isDisposed()) {
                next.dispose();
            }
        }
        this.disposables.clear();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        } else if (str.equals("bindSuccess")) {
            this.bindSchool = "1";
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        handDialog();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (getIntent().getExtras().getString("is_esaydta").equals("1") || this.from_my_exam) {
            return;
        }
        stopTimer();
        ExamTimeHintPop examTimeHintPop = this.mHintPop;
        if (examTimeHintPop == null) {
            this.mHandler.sendEmptyMessage(3);
            return;
        }
        if (!this.showPop) {
            examTimeHintPop.show();
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.ll
            @Override // java.lang.Runnable
            public final void run() {
                this.f12672c.lambda$onResume$6();
            }
        }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public void scoreTwoDialog(final String score, final String txt) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage(txt);
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.tl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13958c.lambda$scoreTwoDialog$16(customDialog, score, view);
            }
        });
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ul
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customDialog.dismissNoAnimaltion();
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() throws Resources.NotFoundException {
        setSwipeBackEnable(false);
        this.shareStemMap = new ArrayMap<>();
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isAddMock, true, this.mContext);
        setTitle(getIntent().getExtras().getString("title"));
        this.from_my_exam = getIntent().getBooleanExtra("from_my_exam", false);
        this.needCountDown = getIntent().getBooleanExtra("needCountDown", true);
        this.bindSchool = getIntent().getExtras().getString("bindSchool");
        String stringExtra = getIntent().getStringExtra("submit_minute");
        if (stringExtra == null || !stringExtra.matches("^\\d*$")) {
            this.submitMinute = 30;
        } else {
            this.submitMinute = Integer.parseInt(stringExtra);
        }
        this.mBtnActionbarRight.setVisibility(8);
        setContentView(R.layout.activity_subceshiqusetion);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linejj);
        this.tvTitle = (TextView) findViewById(R.id.questiondetails_tv_title_gufen);
        this.gufenjh = (ImageView) findViewById(R.id.gufenjh);
        this.tv_jj = (TextView) findViewById(R.id.tv_jj);
        this.timestr = (TextView) findViewById(R.id.timestr);
        this.cusposition = (TextView) findViewById(R.id.cusposition);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.linlb);
        TextView textView = this.cusposition;
        int currentSkinType = SkinManager.getCurrentSkinType(this.mContext);
        int i2 = R.color.question_color_night;
        textView.setTextColor(ContextCompat.getColor(this, currentSkinType == 0 ? R.color.black_gray : R.color.question_color_night));
        this.timestr.setTextColor(ContextCompat.getColor(this, R.color.black_gray));
        this.timestr.setVisibility(this.needCountDown ? 0 : 4);
        ViewPagerCompat viewPagerCompat = (ViewPagerCompat) findViewById(R.id.questiondetails_viewPager);
        viewPager = viewPagerCompat;
        viewPagerCompat.setOnListener(new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.ml
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i3) {
                this.f13021a.lambda$setContentView$0(i3);
            }
        });
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.nl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13054c.lambda$setContentView$1(view);
            }
        });
        this.gufenjh.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ol
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13084c.lambda$setContentView$2(view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pl
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13548c.lambda$setContentView$3(view);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ql
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13740c.lambda$setContentView$4(view);
            }
        });
        List<ExesQuestionBean> list = ProjectApp.questExamDataList;
        if (list == null || list.size() <= 0) {
            getQuestionData();
        } else {
            this.questBeans.addAll(ProjectApp.questExamDataList);
            handleShareStemQuestion();
            MockTestAdapter mockTestAdapter = new MockTestAdapter(this.shareStemMap, this.shareStemExamList, this.mHandler);
            this.msAdapter = mockTestAdapter;
            viewPager.setAdapter(mockTestAdapter);
            viewPager.setOffscreenPageLimit(this.questBeans.size() <= 10 ? 5 : 10);
            updateTitle(0);
            int size = this.questBeans.size();
            String str = SkinManager.getCurrentSkinType(this.mContext) == 1 ? "#64729F" : "#000000";
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                SpannableString spannableString = new SpannableString("1 /" + size);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R.color.first_text_color_night)), 0, ("1").length(), 18);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R.color.tertiary_text_color_night)), ("1").length(), spannableString.length(), 34);
                this.cusposition.setText(spannableString);
            } else {
                this.cusposition.setText(CharacterParser.getSpannableColorSize("1 /" + size, 0, 1, str));
            }
            if (getIntent().getExtras().getString("is_esaydta").equals("1")) {
                if (SharePreferencesUtils.readBooleanConfig(CommonParameter.EXAMIDTRUE, true, this.mContext)) {
                    mCommDialog("为确保数据真实可靠，仅对考生第一次作答成绩进行排名分析，请勿必认真对待！", 1);
                }
            } else if (getIntent().getExtras().getString("typeData").equals("2") && SharePreferencesUtils.readBooleanConfig(CommonParameter.EXAMIDTRUE, true, this.mContext)) {
                mCommDialog("为确保数据真实可靠，仅对考生第一次作答成绩进行排名分析，请勿必认真对待！", 1);
            }
            this.startTime = SystemClock.elapsedRealtime();
            this.startExamTime = System.currentTimeMillis();
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) throws Resources.NotFoundException {
                List list2;
                SubQuestionCheshiActivity.this.startDoTime = System.currentTimeMillis() / 1000;
                ExesQuestionBean exesQuestionBean = (ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(position);
                int actualStemIndex = exesQuestionBean.getActualStemIndex();
                if (!TextUtils.isEmpty(exesQuestionBean.getPublic_number())) {
                    SubQuestionCheshiActivity.this.tvTitle.setVisibility(8);
                    List list3 = (List) SubQuestionCheshiActivity.this.shareStemMap.get(exesQuestionBean.getPublic_number());
                    if (list3 != null && list3.size() > 0) {
                        actualStemIndex = ((ExesQuestionBean) list3.get(0)).getActualStemIndex();
                    }
                }
                SubQuestionCheshiActivity.this.updateTitle(actualStemIndex);
                int size2 = SubQuestionCheshiActivity.this.questBeans.size();
                int actualStemIndex2 = ((ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(position)).getActualStemIndex() + 1;
                if (!TextUtils.isEmpty(exesQuestionBean.getPublic_number())) {
                    actualStemIndex2 = ((ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(position)).getCurrentStemIndex() + 1;
                }
                if (TextUtils.isEmpty(exesQuestionBean.getPublic_number())) {
                    exesQuestionBean.setDoStartDuration(System.currentTimeMillis() / 1000);
                } else if (SubQuestionCheshiActivity.this.mCurrentPosition >= 0 && (list2 = (List) SubQuestionCheshiActivity.this.shareStemMap.get(((ExesQuestionBean) SubQuestionCheshiActivity.this.shareStemExamList.get(position)).getPublic_number())) != null && list2.size() > 0) {
                    int size3 = list2.size();
                    if (position < SubQuestionCheshiActivity.this.mCurrentPosition) {
                        ((ExesQuestionBean) list2.get(size3 - 1)).setDoStartDuration(SubQuestionCheshiActivity.this.startDoTime);
                    } else if (position > SubQuestionCheshiActivity.this.mCurrentPosition) {
                        ((ExesQuestionBean) list2.get(0)).setDoStartDuration(SubQuestionCheshiActivity.this.startDoTime);
                    }
                }
                String str2 = SkinManager.getCurrentSkinType(SubQuestionCheshiActivity.this.mContext) == 1 ? "#64729F" : "#000000";
                if (SkinManager.getCurrentSkinType(SubQuestionCheshiActivity.this.mContext) == 1) {
                    SpannableString spannableString2 = new SpannableString(actualStemIndex2 + " /" + size2);
                    spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SubQuestionCheshiActivity.this.mContext, R.color.first_text_color_night)), 0, (actualStemIndex2 + "").length(), 18);
                    spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SubQuestionCheshiActivity.this.mContext, R.color.tertiary_text_color_night)), (actualStemIndex2 + "").length(), spannableString2.length(), 34);
                    SubQuestionCheshiActivity.this.cusposition.setText(spannableString2);
                } else {
                    SubQuestionCheshiActivity.this.cusposition.setText(CharacterParser.getSpannableColorSize(actualStemIndex2 + " /" + size2, 0, String.valueOf(actualStemIndex2).length(), str2));
                }
                if (position != SubQuestionCheshiActivity.this.mCurrentPosition) {
                    SubQuestionCheshiActivity.this.mCurrentPosition = position;
                }
            }
        });
        try {
            long j2 = getIntent().getExtras().getLong("duration_minute");
            this.timepubstr = j2 != 0 ? j2 : com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this)) ? 120L : 180L;
            this.timestr.setVisibility((!(this.from_my_exam || getIntent().getExtras().getString("is_esaydta").equals("1")) || this.from_my_exam) ? 0 : 8);
            this.minute = this.timepubstr;
            this.second = 0L;
            boolean z2 = this.from_my_exam;
            int i3 = R.drawable.gufenjj;
            if (z2 || getIntent().getExtras().getString("is_esaydta").equals("1")) {
                ImageView imageView = this.gufenjh;
                if (SkinManager.getCurrentSkinType(this.mContext) != 0) {
                    i3 = R.drawable.gufenjj_night;
                }
                imageView.setImageResource(i3);
                TextView textView2 = this.tv_jj;
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    i2 = R.color.black_gray;
                }
                textView2.setTextColor(ContextCompat.getColor(this, i2));
            } else {
                ImageView imageView2 = this.gufenjh;
                if (SkinManager.getCurrentSkinType(this.mContext) != 0) {
                    i3 = this.minute < this.timepubstr - 30 ? R.drawable.gufenjj_night : R.drawable.gufenjh_night;
                } else if (this.minute >= this.timepubstr - 30) {
                    i3 = R.drawable.gufenjh;
                }
                imageView2.setImageResource(i3);
                TextView textView3 = this.tv_jj;
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    i2 = this.minute < this.timepubstr - 30 ? R.color.black_gray : R.color.gray_light_new;
                }
                textView3.setTextColor(ContextCompat.getColor(this, i2));
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String stringExtra2 = getIntent().getStringExtra("startData");
            if (!TextUtils.isEmpty(stringExtra2)) {
                long time = simpleDateFormat.parse(stringExtra2).getTime();
                String stringExtra3 = getIntent().getStringExtra("date_end_timestamp");
                if (stringExtra3 == null) {
                    stringExtra3 = "0";
                }
                if (stringExtra3.matches("^\\d+$") && Long.parseLong(stringExtra3) > 0) {
                    Date date = new Date(Long.parseLong(stringExtra3) * 1000);
                    if (date.getTime() - time < j2 * 60 * 1000) {
                        this.minute = ((date.getTime() - time) / 60) / 1000;
                        this.mHintPop = (ExamTimeHintPop) new XPopup.Builder(this).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.3
                            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                            public void onDismiss(BasePopupView popupView) {
                                super.onDismiss(popupView);
                                SubQuestionCheshiActivity.this.showPop = true;
                            }
                        }).asCustom(new ExamTimeHintPop(this, this.minute, j2));
                    }
                }
            }
            this.timestr.setText(String.format(Locale.CHINA, "%d:%d", Long.valueOf(this.minute), Long.valueOf(this.second)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showSelectPop() {
        try {
            if (this.mContext == null) {
                return;
            }
            final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
            alertDialogCreate.show();
            WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
            attributes.width = -1;
            attributes.height = ((CommonUtil.getScreenHeight(this.mContext) / 5) * 3) + 100;
            alertDialogCreate.getWindow().setAttributes(attributes);
            Window window = alertDialogCreate.getWindow();
            window.setContentView(R.layout.activity_questlist);
            window.setGravity(80);
            window.setWindowAnimations(R.style.mystyle);
            GridView gridView = (GridView) alertDialogCreate.findViewById(R.id.gridView1);
            alertDialogCreate.setCanceledOnTouchOutside(true);
            LinearLayout linearLayout = (LinearLayout) alertDialogCreate.findViewById(R.id.linview);
            RelativeLayout relativeLayout = (RelativeLayout) alertDialogCreate.findViewById(R.id.relda);
            RelativeLayout relativeLayout2 = (RelativeLayout) alertDialogCreate.findViewById(R.id.reljj);
            LinearLayout linearLayout2 = (LinearLayout) alertDialogCreate.findViewById(R.id.tijiao);
            this.timerstrs = (TextView) alertDialogCreate.findViewById(R.id.timerstrs);
            final TextView textView = (TextView) alertDialogCreate.findViewById(R.id.cuspos);
            ImageView imageView = (ImageView) alertDialogCreate.findViewById(R.id.tijiaoimg);
            TextView textView2 = (TextView) alertDialogCreate.findViewById(R.id.jiaojuans);
            if (getIntent().getExtras().getString("is_esaydta").equals("1")) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setImageResource(R.drawable.gufenjj);
                    textView2.setTextColor(ContextCompat.getColor(this, R.color.black_gray));
                    linearLayout.setBackgroundResource(R.drawable.background_view_rounded_top);
                } else {
                    imageView.setImageResource(R.drawable.gufenjj_night);
                    textView2.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
                    linearLayout.setBackgroundResource(R.drawable.background_view_rounded_top_night);
                }
                this.timerstrs.setVisibility(8);
            } else {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setImageResource(R.drawable.gufenjh);
                    textView2.setTextColor(ContextCompat.getColor(this, R.color.gray_light_new));
                    linearLayout.setBackgroundResource(R.drawable.background_view_rounded_top);
                } else {
                    imageView.setImageResource(R.drawable.gufenjh_night);
                    textView2.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
                    linearLayout.setBackgroundResource(R.drawable.background_view_rounded_top_night);
                }
                this.timerstrs.setVisibility(0);
            }
            relativeLayout.setVisibility(8);
            relativeLayout2.setVisibility(0);
            this.timerstrs.setText(this.timestr.getText().toString());
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yl
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubQuestionCheshiActivity.lambda$showSelectPop$7(view);
                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zl
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14255c.lambda$showSelectPop$8(view);
                }
            });
            textView.setText(this.cusposition.getText().toString());
            gridView.setAdapter((ListAdapter) new QuestListAdapter(this.mContext, this.questBeans, this.mHandler, 1));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.il
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                    this.f12517c.lambda$showSelectPop$9(textView, alertDialogCreate, adapterView, view, i2, j2);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void startTimer() {
        if (this.timer == null) {
            this.timer = new Timer();
        }
        if (this.timerTask == null) {
            TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.activity.SubQuestionCheshiActivity.4
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Message message = new Message();
                    message.what = 0;
                    SubQuestionCheshiActivity.this.mHandler.sendMessage(message);
                }
            };
            this.timerTask = timerTask;
            this.timer.schedule(timerTask, 0L, 1000L);
        }
    }

    public void stopTimer() {
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
        }
        TimerTask timerTask = this.timerTask;
        if (timerTask != null) {
            timerTask.cancel();
            this.timerTask = null;
        }
    }
}
