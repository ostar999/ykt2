package com.psychiatrygarden.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.WXAuthEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class LoginActivity extends BaseActivity {
    public static final int READ_PHONE_STATE_PERMISSION_GRANTED = 3;
    private Drawable disableDrawable;
    private Drawable enableDrawable;
    private boolean isAnim;
    private LinearLayout llAgree;
    private CheckBox logincheck;
    private Button mBtRegister;
    private Disposable mDisposable;
    private EditText mEdtPhone;
    private EditText mEdtPwd;
    private ImageView mImgEyes;
    private TextView mTvForgetPwd;
    private TextView mTvLogin;
    private boolean isOpenEyes = false;
    private boolean inputMatch = false;
    private final View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.LoginActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            int id = v2.getId();
            if (id == R.id.iv_wx_login) {
                IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(v2.getContext(), SdkConstant.getWxAppId());
                if (LoginActivity.this.logincheck.isChecked()) {
                    if (!iwxapiCreateWXAPI.isWXAppInstalled()) {
                        ToastUtil.shortToast(v2.getContext(), "您的设备未安装微信客户端");
                        return;
                    }
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "com.yikaobang.yixueykb";
                    iwxapiCreateWXAPI.sendReq(req);
                    return;
                }
                if (LoginActivity.this.isAnim) {
                    return;
                }
                LoginActivity.this.isAnim = true;
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(LoginActivity.this.llAgree, "translationX", 0.0f, -50.0f, 50.0f, -20.0f, 20.0f, 0.0f);
                objectAnimatorOfFloat.setDuration(800L);
                objectAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
                objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.activity.LoginActivity.1.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        LoginActivity.this.llAgree.setTranslationX(0.0f);
                        LoginActivity.this.isAnim = false;
                    }
                });
                objectAnimatorOfFloat.start();
                return;
            }
            if (id == R.id.tv_forget_pwd) {
                LoginActivity.this.startActivity(new Intent().setClass(LoginActivity.this.mContext, ForgetPwdPhoneActivity.class).putExtra("type", 1));
                return;
            }
            if (id == R.id.tv_login) {
                if (LoginActivity.this.logincheck.isChecked()) {
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.agreelogin, true, LoginActivity.this);
                    LoginActivity.this.getLogin();
                    return;
                } else {
                    if (LoginActivity.this.isAnim) {
                        return;
                    }
                    LoginActivity.this.isAnim = true;
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(LoginActivity.this.llAgree, "translationX", 0.0f, -50.0f, 50.0f, -20.0f, 20.0f, 0.0f);
                    objectAnimatorOfFloat2.setDuration(800L);
                    objectAnimatorOfFloat2.setInterpolator(new AccelerateDecelerateInterpolator());
                    objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.activity.LoginActivity.1.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            LoginActivity.this.llAgree.setTranslationX(0.0f);
                            LoginActivity.this.isAnim = false;
                        }
                    });
                    objectAnimatorOfFloat2.start();
                    return;
                }
            }
            if (id == R.id.bt_register) {
                LoginActivity.this.goActivity(RegisterActivity.class);
                return;
            }
            if (id == R.id.img_eyes) {
                if (LoginActivity.this.isOpenEyes) {
                    LoginActivity.this.mEdtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    LoginActivity.this.mEdtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                LoginActivity.this.mEdtPwd.setSelection(LoginActivity.this.mEdtPwd.getText().length());
                LoginActivity loginActivity = LoginActivity.this;
                loginActivity.setImageResource(loginActivity.isOpenEyes);
                LoginActivity.this.isOpenEyes = !r8.isOpenEyes;
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) WebLongSaveActivity.class).putExtra("web_url", NetworkRequestsURL.userAgreementApi).putExtra("title", "用户协议"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) WebLongSaveActivity.class).putExtra("web_url", NetworkRequestsURL.getPrivacyApi).putExtra("title", "隐私政策"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$init$2(CharSequence charSequence, CharSequence charSequence2) throws Exception {
        boolean z2 = false;
        int length = TextUtils.isEmpty(charSequence2) ? 0 : charSequence2.length();
        if (CommonUtil.isMobileSimple(charSequence) && length >= 6 && length <= 16) {
            z2 = true;
        }
        return Boolean.valueOf(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(Boolean bool) throws Exception {
        this.mTvLogin.setEnabled(bool.booleanValue());
        this.mTvLogin.setBackground(bool.booleanValue() ? this.enableDrawable : this.disableDrawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(CompoundButton compoundButton, boolean z2) {
        boolean z3 = false;
        int length = TextUtils.isEmpty(this.mEdtPwd.getText()) ? 0 : this.mEdtPwd.getText().length();
        if (CommonUtil.isMobileSimple(this.mEdtPhone.getText()) && length >= 6 && length <= 16) {
            z3 = true;
        }
        this.inputMatch = z3;
        this.mTvLogin.setBackground(z3 ? this.enableDrawable : this.disableDrawable);
        this.mTvLogin.setEnabled(this.inputMatch);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageResource(boolean isOpenEyes) {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            if (isOpenEyes) {
                this.mImgEyes.setImageResource(R.drawable.ic_eyes_open);
                return;
            } else {
                this.mImgEyes.setImageResource(R.drawable.ic_eyes_close);
                return;
            }
        }
        if (isOpenEyes) {
            this.mImgEyes.setImageResource(R.drawable.ic_eyes_open_night);
        } else {
            this.mImgEyes.setImageResource(R.drawable.ic_eyes_close_night);
        }
    }

    private void wxLogin(String code) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "wechat");
        ajaxParams.put("code", code);
        ajaxParams.put("beta_version", "0");
        YJYHttpUtils.post(this, NetworkRequestsURL.WxLogin, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.LoginActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                LoginActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code", "").equals("200")) {
                        LoginActivity.this.AlertToast(jSONObject.optString("message", ""));
                        return;
                    }
                    UserInfoBean.DataBean data = ((UserInfoBean) new Gson().fromJson(s2, UserInfoBean.class)).getData();
                    if (data != null) {
                        if (!data.getLogin_status().equals("login_success")) {
                            Intent intent = new Intent(LoginActivity.this, (Class<?>) BindPhoneActivity.class);
                            intent.putExtra(Constants.JumpUrlConstants.URL_KEY_OPENID, data.getOpen_id());
                            intent.putExtra("username", data.getUsername());
                            LoginActivity.this.startActivityForResult(intent, 999);
                            return;
                        }
                        UserConfig.getInstance().saveUser(data);
                        if (!TextUtils.isEmpty(LoginActivity.this.mEdtPwd.getText().toString())) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.password, LoginActivity.this.mEdtPwd.getText().toString(), LoginActivity.this.mContext);
                        }
                        SharePreferencesUtils.writeStrConfig(CommonParameter.TEL, LoginActivity.this.mEdtPhone.getText().toString().trim(), LoginActivity.this.mContext);
                        LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(new Intent("refreshPersonal"));
                        EventBus.getDefault().post("LoginSuccess");
                        LoginActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getLogin() {
        showProgressDialog("数据同步中...");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("mobile", "" + this.mEdtPhone.getText().toString());
        ajaxParams.put(CommonParameter.password, Md5Util.MD5Encode(this.mEdtPwd.getText().toString()));
        ajaxParams.put("beta_version", "0");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mLoginUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.LoginActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LoginActivity.this.hideProgressDialog();
                if (CommonUtil.isNetworkConnected(LoginActivity.this.mContext)) {
                    LoginActivity.this.AlertToast("请求失败，请切换网络");
                } else {
                    LoginActivity.this.AlertToast("网络连接失败");
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                LoginActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        if (jSONObject.optString("code").equals(Constants.DEFAULT_UIN)) {
                            LoginActivity loginActivity = LoginActivity.this;
                            loginActivity.startActivity(ImproveUserInfoAct.startActivity(loginActivity, loginActivity.mEdtPhone.getText().toString(), LoginActivity.this.mEdtPwd.getText().toString()));
                            return;
                        } else {
                            NewToast.showShort(LoginActivity.this.mContext, jSONObject.optString("message"), 0).show();
                            LoginActivity.this.hideProgressDialog();
                            return;
                        }
                    }
                    UserConfig.getInstance().saveUser(((UserInfoBean) new Gson().fromJson(s2, UserInfoBean.class)).getData());
                    if (!TextUtils.isEmpty(LoginActivity.this.mEdtPwd.getText().toString())) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.password, LoginActivity.this.mEdtPwd.getText().toString(), LoginActivity.this.mContext);
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.TEL, LoginActivity.this.mEdtPhone.getText().toString().trim(), LoginActivity.this.mContext);
                    LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(new Intent("refreshPersonal"));
                    EventBus.getDefault().post("LoginSuccess");
                    LoginActivity.this.finish();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner, R.attr.login_btn_en_disable});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            this.enableDrawable = drawable;
        }
        if (drawable2 != null) {
            this.disableDrawable = drawable2;
        }
        typedArrayObtainStyledAttributes.recycle();
        TextView textView = (TextView) findViewById(R.id.yonghuxieyi);
        TextView textView2 = (TextView) findViewById(R.id.yinsixieyi);
        this.mImgEyes = (ImageView) findViewById(R.id.img_eyes);
        this.logincheck = (CheckBox) findViewById(R.id.logincheck);
        this.llAgree = (LinearLayout) findViewById(R.id.ll_agree);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.od
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13077c.lambda$init$0(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13539c.lambda$init$1(view);
            }
        });
        if (WXAPIFactory.createWXAPI(this, SdkConstant.getWxAppId()).isWXAppInstalled()) {
            findViewById(R.id.iv_wx_login).setOnClickListener(this.mOnclick);
        } else {
            findViewById(R.id.iv_wx_login).setVisibility(8);
        }
        this.mEdtPhone = (EditText) findViewById(R.id.edt_phone);
        this.mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        this.mTvLogin = (TextView) findViewById(R.id.tv_login);
        this.mBtRegister = (Button) findViewById(R.id.bt_register);
        this.mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        this.mDisposable = Observable.combineLatest(RxTextView.textChanges(this.mEdtPhone), RxTextView.textChanges(this.mEdtPwd), new BiFunction() { // from class: com.psychiatrygarden.activity.qd
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return LoginActivity.lambda$init$2((CharSequence) obj, (CharSequence) obj2);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.rd
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f13796c.lambda$init$3((Boolean) obj);
            }
        });
        this.logincheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.sd
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f13827c.lambda$init$4(compoundButton, z2);
            }
        });
        if (!TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.TEL, this.mContext))) {
            this.mEdtPhone.setText(SharePreferencesUtils.readStrConfig(CommonParameter.TEL, this.mContext));
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.password, this.mContext))) {
            return;
        }
        this.mEdtPwd.setText(SharePreferencesUtils.readStrConfig(CommonParameter.password, this.mContext));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == -1) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("refreshPersonal"));
            EventBus.getDefault().post("LoginSuccess");
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDisposable.dispose();
    }

    @Subscribe
    public void onEventMainThread(WXAuthEvent event) {
        if (TextUtils.isEmpty(event.getCode())) {
            return;
        }
        LogUtils.d("wx_auth_code", event.getCode());
        wxLogin(event.getCode());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == 3 && grantResults[0] == -1 && ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.READ_PHONE_STATE)) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.READ_PHONE_STATE}, 3);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle(R.string.login_yjy);
        setContentView(R.layout.activity_login);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mTvLogin.setOnClickListener(this.mOnclick);
        this.mBtRegister.setOnClickListener(this.mOnclick);
        this.mTvForgetPwd.setOnClickListener(this.mOnclick);
        this.mImgEyes.setOnClickListener(this.mOnclick);
    }
}
