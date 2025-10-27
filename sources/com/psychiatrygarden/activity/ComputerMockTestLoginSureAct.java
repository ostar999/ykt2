package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ComputerMockTestLoginSureAct;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ComputerMockTestLoginSureAct extends BaseActivity {
    private CompositeDisposable compositeDisposable;
    private String dateEnd;
    private long durationMinute;
    private String examId;
    private String file;
    private TextView mBtnBack;
    private TextView mBtnStart;
    private ImageView mImgBack;
    private TextView mTvMockName;
    private TextView mTvNickName;
    private TextView mTvSex;
    private TextView mTvSubjectName;
    private TextView mTvTestNumber;
    private String submitMinute;
    private String title;

    /* renamed from: com.psychiatrygarden.activity.ComputerMockTestLoginSureAct$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(CommonUtil.getNetTime());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(Date date) throws Exception {
            ComputerMockTestLoginSureAct computerMockTestLoginSureAct = ComputerMockTestLoginSureAct.this;
            ComputerMockTestModeTwoAct.newIntent(computerMockTestLoginSureAct, computerMockTestLoginSureAct.examId, ComputerMockTestLoginSureAct.this.title, ComputerMockTestLoginSureAct.this.dateEnd, ComputerMockTestLoginSureAct.this.durationMinute, ComputerMockTestLoginSureAct.this.submitMinute, ComputerMockTestLoginSureAct.this.file, ComputerMockTestLoginSureAct.this.mTvTestNumber.getText().toString());
            ComputerMockTestLoginSureAct.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ComputerMockTestLoginSureAct.this.hideProgressDialog();
            ToastUtil.shortToast(ComputerMockTestLoginSureAct.this, "获取试卷失败!");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass1) t2);
            try {
                List list = (List) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(t2).optString("data")), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.ComputerMockTestLoginSureAct.1.1
                }.getType());
                Collections.sort(list);
                ProjectApp.questExamDataList.clear();
                ProjectApp.questExamDataList.addAll(list);
                ComputerMockTestLoginSureAct.this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.y5
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        ComputerMockTestLoginSureAct.AnonymousClass1.lambda$onSuccess$0(observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.z5
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f14236c.lambda$onSuccess$1((Date) obj);
                    }
                }));
            } catch (Exception e2) {
                e2.printStackTrace();
                ComputerMockTestLoginSureAct.this.hideProgressDialog();
            }
        }
    }

    private void getQuestionData() {
        showProgressDialog("加载中...");
        YJYHttpUtils.get(getApplicationContext(), this.file, new AjaxParams(), new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (isLogin()) {
            getQuestionData();
        }
    }

    public static void newIntent(Context context, String examId, String title, String dateEnd, long durationMin, String submitMinute, String file, String number, String project) {
        Intent intent = new Intent(context, (Class<?>) ComputerMockTestLoginSureAct.class);
        intent.putExtra("titleName", title);
        intent.putExtra("examId", examId);
        intent.putExtra("dateEnd", dateEnd);
        intent.putExtra("durationMinute", durationMin);
        intent.putExtra("submitMinute", submitMinute);
        intent.putExtra("file", file);
        intent.putExtra(Constant.LOGIN_ACTIVITY_NUMBER, number);
        intent.putExtra("project", project);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.title = getIntent().getStringExtra("titleName");
        this.examId = getIntent().getStringExtra("examId");
        this.dateEnd = getIntent().getStringExtra("dateEnd");
        this.durationMinute = getIntent().getLongExtra("durationMinute", 0L);
        this.file = getIntent().getStringExtra("file");
        this.submitMinute = getIntent().getStringExtra("submitMinute");
        this.mTvMockName = (TextView) findViewById(R.id.tv_mock_name);
        this.mImgBack = (ImageView) findViewById(R.id.img_back);
        this.mBtnBack = (TextView) findViewById(R.id.btn_back);
        this.mBtnStart = (TextView) findViewById(R.id.btn_start);
        this.mTvNickName = (TextView) findViewById(R.id.tv_nickname);
        this.mTvSex = (TextView) findViewById(R.id.tv_sex);
        this.mTvTestNumber = (TextView) findViewById(R.id.tv_test_number);
        this.mTvSubjectName = (TextView) findViewById(R.id.tv_subject);
        this.mTvMockName.setText(this.title);
        this.mTvSubjectName.setText(getIntent().getStringExtra("project"));
        this.mTvNickName.setText(UserConfig.getInstance().getUser().getNickname());
        this.mTvTestNumber.setText(getIntent().getStringExtra(Constant.LOGIN_ACTIVITY_NUMBER));
        if (UserConfig.getInstance().getUser().getSex().equals("1")) {
            this.mTvSex.setText("男");
        } else {
            this.mTvSex.setText("女");
        }
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
        this.mActionBar.hide();
        setContentView(R.layout.layout_computer_mock_login_sure);
        this.compositeDisposable = new CompositeDisposable();
        getWindow().addFlags(1024);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14012c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.w5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14139c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnStart.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.x5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14171c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}
