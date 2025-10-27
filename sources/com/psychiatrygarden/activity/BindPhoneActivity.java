package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.TextView;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AlphaButton;
import com.psychiatrygarden.widget.AuthDialogView;
import com.psychiatrygarden.widget.ClearEditText;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class BindPhoneActivity extends BaseActivity {
    private boolean bindWx;
    private TextView btnGetCode;
    private AlphaButton btnLogin;
    private ClearEditText etAuthCode;
    private ClearEditText etPhone;
    private boolean hasCallGetCode;
    private boolean isCountDown;
    private String mAuthCode;
    private CountDownTimer mCountDownTimer;
    private Disposable mDisposable;
    private String mOpenId;
    private String mUserName;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getCode, reason: merged with bridge method [inline-methods] */
    public void lambda$setListenerForWidget$2(String captchaVerifyParam) {
        if (TextUtils.isEmpty(this.etPhone.getText()) || !this.etPhone.getText().toString().matches("^1[3-9]\\d{9}$")) {
            ToastUtil.shortToast(this, "请填写正确的手机号码~");
            return;
        }
        HashMap map = new HashMap();
        map.put("mobile", this.etPhone.getText().toString().trim());
        map.put("type", Constants.VIA_SHARE_TYPE_INFO);
        map.put("captchaVerifyParam", captchaVerifyParam);
        map.put("is_verify", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mYAnzheng, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.l1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12651c.lambda$getCode$5((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.m1
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12684c.lambda$getCode$6(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCode$5(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.optString("code").equals("200")) {
                String strOptString = jSONObject.optString("message", "");
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                AlertToast(strOptString);
                return;
            }
            this.mAuthCode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data"));
            if (!this.hasCallGetCode) {
                this.hasCallGetCode = true;
            }
            this.mCountDownTimer.start();
            AlertToast("发送成功");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCode$6(VolleyError volleyError, String str) {
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$init$0(CharSequence charSequence, CharSequence charSequence2) throws Exception {
        return Boolean.valueOf(charSequence != null && charSequence.toString().matches("^1[3-9]\\d{9}$") && !TextUtils.isEmpty(charSequence2) && TextUtils.equals(this.mAuthCode, this.etAuthCode.getText()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(Boolean bool) throws Exception {
        this.btnLogin.setEnabled(bool.booleanValue());
        this.btnGetCode.setEnabled((TextUtils.isEmpty(this.etPhone.getText()) || this.etPhone.getText().toString().length() != 11 || this.isCountDown) ? false : true);
        AlphaButton alphaButton = this.btnLogin;
        boolean zBooleanValue = bool.booleanValue();
        int i2 = R.drawable.shape_full_red;
        alphaButton.setBackgroundResource(zBooleanValue ? R.drawable.shape_full_red : R.drawable.shape_full_red_disable);
        TextView textView = this.btnGetCode;
        if (!textView.isEnabled()) {
            i2 = R.drawable.shape_full_red_disable;
        }
        textView.setBackgroundResource(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        new XPopup.Builder(this).asCustom(new AuthDialogView(this, new AuthDialogView.OnAuthDataListener() { // from class: com.psychiatrygarden.activity.k1
            @Override // com.psychiatrygarden.widget.AuthDialogView.OnAuthDataListener
            public final void onConfirm(String str) {
                this.f12574a.lambda$setListenerForWidget$2(str);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        login();
    }

    private void login() {
        if (!this.hasCallGetCode) {
            ToastUtil.shortToast(this, "请先获取验证码");
            return;
        }
        if (TextUtils.equals(this.etAuthCode.getText(), this.mAuthCode)) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("type", "wechat");
            ajaxParams.put("mobile", this.etPhone.getText().toString().trim());
            ajaxParams.put("code", this.etAuthCode.getText().toString());
            ajaxParams.put(Constants.JumpUrlConstants.URL_KEY_OPENID, this.mOpenId);
            ajaxParams.put("username", this.mUserName);
            ajaxParams.put("beta_version", "0");
            YJYHttpUtils.post(this, NetworkRequestsURL.bindMobileLogin, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.BindPhoneActivity.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    if (strMsg != null) {
                        ToastUtil.shortToast(BindPhoneActivity.this, strMsg);
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass2) s2);
                    if (s2 == null) {
                        return;
                    }
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        if (!jSONObject.optString("code", "").equals("200")) {
                            String strOptString = jSONObject.optString("message", "");
                            if (TextUtils.isEmpty(strOptString)) {
                                return;
                            }
                            BindPhoneActivity.this.AlertToast(strOptString);
                            return;
                        }
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null) {
                            String strOptString2 = jSONObjectOptJSONObject.optString("bind_status", "");
                            UserInfoBean.DataBean dataBean = (UserInfoBean.DataBean) new Gson().fromJson(jSONObjectOptJSONObject.toString(), UserInfoBean.DataBean.class);
                            if (!TextUtils.equals(strOptString2, "bind_success")) {
                                BindPhoneActivity.this.startActivity(new Intent(BindPhoneActivity.this, (Class<?>) SetPasswordActivity.class).putExtra(AliyunLogCommon.TERMINAL_TYPE, BindPhoneActivity.this.etPhone.getText().toString()).putExtra(Constants.JumpUrlConstants.URL_KEY_OPENID, dataBean.getOpen_id()).putExtra("socialite_username", dataBean.getUsername()).putExtra("code", BindPhoneActivity.this.mAuthCode));
                                BindPhoneActivity.this.finish();
                                return;
                            }
                            if (BindPhoneActivity.this.bindWx) {
                                BindPhoneActivity.this.AlertToast("绑定成功");
                            }
                            UserConfig.getInstance().saveUser(dataBean);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.TEL, BindPhoneActivity.this.etPhone.getText().toString().trim(), BindPhoneActivity.this.mContext);
                            BindPhoneActivity.this.setResult(-1);
                            BindPhoneActivity.this.finish();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("绑定手机号");
        if (this.bindWx) {
            setTitle("微信登录");
            this.btnLogin.setText("绑定");
        }
        this.mCountDownTimer = new CountDownTimer(120000L, 1000L) { // from class: com.psychiatrygarden.activity.BindPhoneActivity.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (!TextUtils.isEmpty(BindPhoneActivity.this.etPhone.getText()) && BindPhoneActivity.this.etPhone.getText().toString().matches("^1[3-9]\\d{9}$")) {
                    BindPhoneActivity.this.btnGetCode.setBackgroundResource(R.drawable.shape_full_red);
                    BindPhoneActivity.this.btnGetCode.setEnabled(true);
                }
                BindPhoneActivity.this.btnGetCode.setText("获取验证码");
                BindPhoneActivity.this.isCountDown = false;
            }

            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                BindPhoneActivity.this.btnGetCode.setText(String.format(Locale.CHINA, "%d秒后重新获取", Long.valueOf(millisUntilFinished / 1000)));
                if (BindPhoneActivity.this.isCountDown) {
                    return;
                }
                BindPhoneActivity.this.isCountDown = true;
                BindPhoneActivity.this.btnGetCode.setEnabled(false);
                BindPhoneActivity.this.btnGetCode.setBackgroundResource(R.drawable.shape_full_red_disable);
            }
        };
        this.mDisposable = Observable.combineLatest(RxTextView.textChanges(this.etPhone), RxTextView.textChanges(this.etAuthCode), new BiFunction() { // from class: com.psychiatrygarden.activity.n1
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f13033c.lambda$init$0((CharSequence) obj, (CharSequence) obj2);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.o1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f13068c.lambda$init$1((Boolean) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.isCountDown) {
            this.mCountDownTimer.cancel();
        }
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_login_bind_phone);
        this.etPhone = (ClearEditText) findViewById(R.id.et_phone);
        this.etAuthCode = (ClearEditText) findViewById(R.id.et_auth_code);
        this.btnGetCode = (TextView) findViewById(R.id.bt_get_auth_code);
        this.btnLogin = (AlphaButton) findViewById(R.id.bt_ok);
        this.mOpenId = getIntent().getStringExtra(Constants.JumpUrlConstants.URL_KEY_OPENID);
        this.mUserName = getIntent().getStringExtra("username");
        this.bindWx = getIntent().getBooleanExtra("bindWx", false);
        this.etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        this.etPhone.setKeyListener(DigitsKeyListener.getInstance(RandomUtil.BASE_NUMBER));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.btnGetCode.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.p1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13528c.lambda$setListenerForWidget$3(view);
            }
        });
        this.btnLogin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.q1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13725c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
