package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.bean.CancelAccountBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AuthDialogView;
import com.psychiatrygarden.widget.PopLogoutHint;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PhoneChangeActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_phone;
    private EditText ed_verification_code;
    private MyRunnable mRunnable;
    private TextView tv_next;
    private TextView tv_no_verification_code;
    private TextView tv_send_verification_code;
    private int gotoType = 0;
    private int identityType = 0;
    private final Handler mHandler = new Handler();
    private int time = 60;
    private String verification_code = "";

    public class MyRunnable implements Runnable {
        private MyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PhoneChangeActivity.access$110(PhoneChangeActivity.this);
            if (PhoneChangeActivity.this.time > 0) {
                PhoneChangeActivity.this.tv_send_verification_code.setText(String.format(Locale.CHINA, "重新获取（%d）", Integer.valueOf(PhoneChangeActivity.this.time)));
                PhoneChangeActivity.this.mHandler.postDelayed(this, 1000L);
                return;
            }
            PhoneChangeActivity.this.mHandler.removeCallbacks(PhoneChangeActivity.this.mRunnable);
            PhoneChangeActivity.this.mRunnable = null;
            PhoneChangeActivity.this.time = 60;
            PhoneChangeActivity.this.tv_send_verification_code.setText("重新获取");
            PhoneChangeActivity.this.tv_send_verification_code.setBackgroundResource(R.drawable.ffe25d49_20);
            PhoneChangeActivity.this.tv_send_verification_code.setClickable(true);
        }
    }

    public static /* synthetic */ int access$110(PhoneChangeActivity phoneChangeActivity) {
        int i2 = phoneChangeActivity.time;
        phoneChangeActivity.time = i2 - 1;
        return i2;
    }

    private void getkefucs() {
        showProgressDialog();
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.kefucsApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.PhoneChangeActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PhoneChangeActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                PhoneChangeActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        CommonUtil.onlineService(PhoneChangeActivity.this, (OnlineServiceBean) new Gson().fromJson(jSONObject.optString("data"), OnlineServiceBean.class));
                    } else {
                        ToastUtil.shortToast(PhoneChangeActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$confirmVerifyCode$3(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                Intent intent = new Intent();
                int i2 = this.identityType;
                if (i2 == 0) {
                    intent.setClass(this, PhoneChangeConfirmActivity.class);
                    startActivity(intent);
                    finish();
                } else if (i2 == 1) {
                    intent.setClass(this, ForgetModifyPwdActivity.class);
                    int i3 = this.gotoType;
                    if (i3 == 0) {
                        intent.putExtra("mobile", this.ed_phone.getText().toString());
                    } else if (i3 == 1) {
                        intent.putExtra("email", this.ed_phone.getText().toString());
                    }
                    intent.putExtra("code", this.verification_code);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                    finish();
                } else if (i2 == 3) {
                    intent.setClass(this, EmailConfirmActivity.class);
                    intent.putExtra("emailType", 3);
                    startActivity(intent);
                    finish();
                }
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$confirmVerifyCode$4(VolleyError volleyError, String str) {
        hideProgressDialog();
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$1(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                try {
                    this.verification_code = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (this.mRunnable == null) {
                    this.tv_send_verification_code.setBackgroundResource(R.drawable.fff1cac5_20);
                    this.tv_send_verification_code.setClickable(false);
                    MyRunnable myRunnable = new MyRunnable();
                    this.mRunnable = myRunnable;
                    this.mHandler.postDelayed(myRunnable, 0L);
                }
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$2(VolleyError volleyError, String str) {
        hideProgressDialog();
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBtnState() {
        if (TextUtils.isEmpty(this.ed_phone.getText().toString()) || TextUtils.isEmpty(this.ed_verification_code.getText().toString())) {
            this.tv_next.setBackgroundResource(R.drawable.fff1cac5_20);
        } else {
            this.tv_next.setBackgroundResource(R.drawable.ffe25d49_20);
        }
    }

    public void confirmVerifyCode() {
        showProgressDialog();
        HashMap map = new HashMap();
        String str = NetworkRequestsURL.confirmVerifyCode;
        if (this.gotoType == 1) {
            map.put("email", this.ed_phone.getText().toString().trim());
        } else {
            map.put("mobile", this.ed_phone.getText().toString().trim());
        }
        map.put("type", "2");
        map.put("code", "" + this.verification_code);
        YJYHttpUtils.post(this, str, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.ef
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str2) {
                this.f12315c.lambda$confirmVerifyCode$3((String) obj, str2);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.ff
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str2) {
                this.f12352c.lambda$confirmVerifyCode$4(volleyError, str2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
    /* renamed from: getAuthCode, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$onClick$0(java.lang.String r6) {
        /*
            r5 = this;
            r5.showProgressDialog()
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            int r1 = r5.gotoType
            r2 = 1
            if (r1 != r2) goto L10
            java.lang.String r3 = com.psychiatrygarden.http.NetworkRequestsURL.authCodeEmail
            goto L12
        L10:
            java.lang.String r3 = com.psychiatrygarden.http.NetworkRequestsURL.mYAnzheng
        L12:
            if (r1 != r2) goto L28
            android.widget.EditText r6 = r5.ed_phone
            android.text.Editable r6 = r6.getText()
            java.lang.String r6 = r6.toString()
            java.lang.String r6 = r6.trim()
            java.lang.String r1 = "email"
            r0.put(r1, r6)
            goto L47
        L28:
            android.widget.EditText r1 = r5.ed_phone
            android.text.Editable r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = r1.trim()
            java.lang.String r4 = "mobile"
            r0.put(r4, r1)
            java.lang.String r1 = "captchaVerifyParam"
            r0.put(r1, r6)
            java.lang.String r6 = "is_verify"
            java.lang.String r1 = "1"
            r0.put(r6, r1)
        L47:
            int r6 = r5.identityType
            java.lang.String r1 = "type"
            if (r6 == 0) goto L5c
            if (r6 == r2) goto L5c
            r2 = 2
            if (r6 == r2) goto L56
            r2 = 3
            if (r6 == r2) goto L5c
            goto L61
        L56:
            java.lang.String r6 = "3"
            r0.put(r1, r6)
            goto L61
        L5c:
            java.lang.String r6 = "2"
            r0.put(r1, r6)
        L61:
            com.psychiatrygarden.activity.cf r6 = new com.psychiatrygarden.activity.cf
            r6.<init>()
            com.psychiatrygarden.activity.df r1 = new com.psychiatrygarden.activity.df
            r1.<init>()
            com.psychiatrygarden.http.YJYHttpUtils.post(r5, r3, r0, r6, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.PhoneChangeActivity.lambda$onClick$0(java.lang.String):void");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.gotoType = getIntent().getIntExtra("gotoType", 0);
        this.identityType = getIntent().getIntExtra("identityType", 0);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        TextView textView2 = (TextView) findViewById(R.id.tv_phone_email);
        this.ed_phone = (EditText) findViewById(R.id.ed_phone);
        this.ed_verification_code = (EditText) findViewById(R.id.ed_verification_code);
        this.tv_send_verification_code = (TextView) findViewById(R.id.tv_send_verification_code);
        this.tv_next = (TextView) findViewById(R.id.tv_next);
        this.tv_no_verification_code = (TextView) findViewById(R.id.tv_no_verification_code);
        int i2 = this.gotoType;
        if (i2 == 0) {
            setTitle("手机号验证");
            textView.setText("请输入手机号及验证码进行验证");
            textView2.setText("手机号");
            this.ed_phone.setHint("输入手机号");
            this.ed_verification_code.setHint("输入短信验证码");
        } else if (i2 == 1) {
            setTitle("验证邮箱");
            textView.setText("请输入当前邮箱，并接收邮箱验证码进行验证");
            textView2.setText("邮箱");
            this.ed_phone.setHint("输入当前邮箱");
            this.ed_verification_code.setHint("输入邮箱验证码");
        }
        if (2 != this.identityType) {
            this.tv_next.setText("下一步");
        } else {
            this.tv_next.setText("确定");
            setTitle("注销账号");
        }
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_next) {
            if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
                if (this.gotoType == 1) {
                    ToastUtil.shortToast(this, "请输入绑定邮箱");
                    return;
                } else {
                    ToastUtil.shortToast(this, "请输入手机号");
                    return;
                }
            }
            if (TextUtils.isEmpty(this.ed_verification_code.getText().toString())) {
                ToastUtil.shortToast(this, "请输入验证码");
                return;
            }
            if (!TextUtils.equals(this.ed_verification_code.getText().toString(), this.verification_code)) {
                ToastUtil.shortToast(this, "输入的验证码错误");
                return;
            }
            int i2 = this.identityType;
            if (i2 != 0 && i2 != 1) {
                if (i2 == 2) {
                    new XPopup.Builder(this).moveUpToKeyboard(Boolean.FALSE).asCustom(new PopLogoutHint(this, new PopLogoutHint.onDialogOkClickListener() { // from class: com.psychiatrygarden.activity.bf
                        @Override // com.psychiatrygarden.widget.PopLogoutHint.onDialogOkClickListener
                        public final void onclickOk() {
                            this.f11109a.putAccountData();
                        }
                    })).show();
                    return;
                } else if (i2 != 3) {
                    return;
                }
            }
            confirmVerifyCode();
            return;
        }
        if (id == R.id.tv_no_verification_code) {
            getkefucs();
            return;
        }
        if (id != R.id.tv_send_verification_code) {
            return;
        }
        if (this.gotoType == 1) {
            String email = UserConfig.getInstance().getUser().getEmail();
            if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
                ToastUtil.shortToast(this, "请输入绑定邮箱");
                return;
            } else if (!TextUtils.equals(this.ed_phone.getText().toString(), email)) {
                ToastUtil.shortToast(this, "邮箱输入错误");
                return;
            }
        } else {
            String str = SharePreferencesUtils.readStrConfig(CommonParameter.TEL, this) + "";
            if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
                ToastUtil.shortToast(this, "请输入手机号");
                return;
            } else if (!TextUtils.equals(this.ed_phone.getText().toString(), str)) {
                ToastUtil.shortToast(this, "手机号输入错误");
                return;
            }
        }
        if (this.gotoType == 1) {
            lambda$onClick$0("");
        } else {
            new XPopup.Builder(this).asCustom(new AuthDialogView(this, new AuthDialogView.OnAuthDataListener() { // from class: com.psychiatrygarden.activity.af
                @Override // com.psychiatrygarden.widget.AuthDialogView.OnAuthDataListener
                public final void onConfirm(String str2) {
                    this.f10991a.lambda$onClick$0(str2);
                }
            })).show();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS)) {
            finish();
        }
    }

    public void putAccountData() {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("app_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        int i2 = this.gotoType;
        if (i2 == 0) {
            ajaxParams.put("type", "mobile");
        } else if (i2 == 1) {
            ajaxParams.put("type", "email");
        }
        ajaxParams.put("code", "" + this.verification_code);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.cancellationApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.PhoneChangeActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PhoneChangeActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                PhoneChangeActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        UserConfig.getInstance().saveUser(null);
                        EMClient.getInstance().logout(true, new EMCallBack() { // from class: com.psychiatrygarden.activity.PhoneChangeActivity.3.1
                            @Override // com.hyphenate.EMCallBack
                            public void onError(int code, String error) {
                                Log.e("logout_huanxin", "退出失败");
                            }

                            @Override // com.hyphenate.EMCallBack
                            public /* synthetic */ void onProgress(int i3, String str) {
                                d1.a.a(this, i3, str);
                            }

                            @Override // com.hyphenate.EMCallBack
                            public void onSuccess() {
                                Log.e("logout_huanxin", "退出成功");
                            }
                        });
                        SharePreferencesUtils.clearAppointData(PhoneChangeActivity.this);
                        CancelAccountBean cancelAccountBean = (CancelAccountBean) new Gson().fromJson(jSONObject.optString("data"), CancelAccountBean.class);
                        Intent intent = new Intent(PhoneChangeActivity.this.mContext, (Class<?>) AccountLogoutConfirmActivity.class);
                        intent.putExtra("gotoType", 0);
                        intent.putExtra(AliyunLogCommon.LogLevel.INFO, cancelAccountBean);
                        PhoneChangeActivity.this.startActivity(intent);
                        PhoneChangeActivity.this.finish();
                    } else {
                        PhoneChangeActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_phone_change);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_send_verification_code.setOnClickListener(this);
        this.tv_next.setOnClickListener(this);
        this.tv_no_verification_code.setOnClickListener(this);
        this.ed_verification_code.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.PhoneChangeActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                PhoneChangeActivity.this.setBtnState();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.ed_phone.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.PhoneChangeActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                PhoneChangeActivity.this.setBtnState();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
    }
}
