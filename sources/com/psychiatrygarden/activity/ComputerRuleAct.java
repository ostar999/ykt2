package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ComputerRuleAct;
import com.psychiatrygarden.bean.ComputerRuleBean;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
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
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ComputerRuleAct extends BaseActivity {
    private CompositeDisposable compositeDisposable;
    private String dateEnd;
    private long durationMinute;
    private String examId;
    private String file;
    private TextView mBtnClose;
    private TextView mBtnJoinTest;
    private TextView mTvKnow;
    private TextView mTvPromise;
    private TextView mTvRule;
    private String submitMinute;
    private String title;
    private int mStep = 1;
    List<ExesQuestionBean> questBeans = new ArrayList();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /* renamed from: com.psychiatrygarden.activity.ComputerRuleAct$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(Date date) throws Exception {
            ComputerRuleAct computerRuleAct = ComputerRuleAct.this;
            ComputerMockTestAct.newIntent(computerRuleAct, computerRuleAct.examId, ComputerRuleAct.this.title, ComputerRuleAct.this.dateEnd, ComputerRuleAct.this.durationMinute, ComputerRuleAct.this.submitMinute, ComputerRuleAct.this.file);
            ComputerRuleAct.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ComputerRuleAct.this.hideProgressDialog();
            ToastUtil.shortToast(ComputerRuleAct.this, "获取试卷失败!");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass1) t2);
            try {
                String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data"));
                Gson gson = new Gson();
                ComputerRuleAct.this.questBeans = (List) gson.fromJson(strDecode, new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.ComputerRuleAct.1.1
                }.getType());
                Collections.sort(ComputerRuleAct.this.questBeans);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamDataList.addAll(ComputerRuleAct.this.questBeans);
                ComputerRuleAct.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.j7
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerRuleAct.AnonymousClass1.lambda$onSuccess$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.k7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f12581c.lambda$onSuccess$1((Date) obj);
                    }
                }));
            } catch (Exception e2) {
                e2.printStackTrace();
                ComputerRuleAct.this.hideProgressDialog();
            }
        }
    }

    private void getQuestionData() {
        showProgressDialog("加载中...");
        YJYHttpUtils.get(getApplicationContext(), getIntent().getExtras().getString("questionFile"), new AjaxParams(), new AnonymousClass1());
    }

    private void getRuleInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("exam_id", "" + getIntent().getExtras().getString("examId"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.getComputerRuleInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ComputerRuleAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ComputerRuleBean computerRuleBean = (ComputerRuleBean) new Gson().fromJson(s2, ComputerRuleBean.class);
                    if (!computerRuleBean.getCode().equals("200") || computerRuleBean.getData() == null) {
                        return;
                    }
                    ComputerRuleAct.this.mTvKnow.setText(computerRuleBean.getData().getInstruction());
                    ComputerRuleAct.this.mTvRule.setText(computerRuleBean.getData().getRules());
                    ComputerRuleAct.this.mTvPromise.setText(computerRuleBean.getData().getPromise());
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        int i2 = this.mStep;
        if (i2 == 1) {
            finish();
        } else {
            this.mStep = i2 - 1;
        }
        int i3 = this.mStep;
        if (i3 == 1) {
            setTitle("考生须知");
            this.mTvKnow.setVisibility(0);
            this.mTvRule.setVisibility(8);
            this.mTvPromise.setVisibility(8);
            this.mBtnClose.setVisibility(8);
            this.mBtnJoinTest.setText("确认须知");
            return;
        }
        if (i3 != 2) {
            return;
        }
        setTitle("考生规则");
        this.mTvKnow.setVisibility(8);
        this.mTvRule.setVisibility(0);
        this.mTvPromise.setVisibility(8);
        this.mBtnClose.setVisibility(8);
        this.mBtnJoinTest.setText("确认规则");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        EventBus.getDefault().post("closePage");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2() {
        if (isLogin()) {
            getQuestionData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        int i2 = this.mStep;
        if (i2 == 3) {
            new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.i7
                @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
                public final void mClickIml() {
                    this.f12499a.lambda$setListenerForWidget$2();
                }
            }, "进入考场，开始考试", "", "取消", "确定")).show();
            return;
        }
        int i3 = i2 + 1;
        this.mStep = i3;
        if (i3 == 1) {
            setTitle("考生须知");
            this.mTvKnow.setVisibility(0);
            this.mTvRule.setVisibility(8);
            this.mTvPromise.setVisibility(8);
            this.mBtnJoinTest.setText("确认须知");
            return;
        }
        if (i3 == 2) {
            setTitle("考生规则");
            this.mTvKnow.setVisibility(8);
            this.mTvRule.setVisibility(0);
            this.mTvPromise.setVisibility(8);
            this.mBtnJoinTest.setText("确认规则");
            return;
        }
        if (i3 != 3) {
            return;
        }
        setTitle("考生承诺");
        this.mTvKnow.setVisibility(8);
        this.mTvRule.setVisibility(8);
        this.mTvPromise.setVisibility(0);
        this.mBtnClose.setVisibility(0);
        this.mBtnJoinTest.setText("同意");
    }

    public static void newIntent(Context context, String title, String examId, String questionFile, String dateEnd, long durationMin, String submitMinute, String file) {
        Intent intent = new Intent(context, (Class<?>) ComputerRuleAct.class);
        intent.putExtra("title", title);
        intent.putExtra("examId", examId);
        intent.putExtra("questionFile", questionFile);
        intent.putExtra("dateEnd", dateEnd);
        intent.putExtra("durationMinute", durationMin);
        intent.putExtra("submitMinute", submitMinute);
        intent.putExtra("file", file);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("考生须知");
        this.examId = getIntent().getStringExtra("examId");
        this.title = getIntent().getStringExtra("title");
        this.dateEnd = getIntent().getStringExtra("dateEnd");
        this.durationMinute = getIntent().getLongExtra("durationMinute", 0L);
        this.submitMinute = getIntent().getStringExtra("submitMinute");
        this.file = getIntent().getStringExtra("file");
        this.mTvKnow = (TextView) findViewById(R.id.tv_know);
        this.mTvRule = (TextView) findViewById(R.id.tv_rule);
        this.mTvPromise = (TextView) findViewById(R.id.tv_promise);
        this.mBtnClose = (TextView) findViewById(R.id.btn_close);
        this.mBtnJoinTest = (TextView) findViewById(R.id.btn_join_test);
        getRuleInfo();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_computer_rule);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.f7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12345c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnClose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.g7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12431c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnJoinTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12469c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}
