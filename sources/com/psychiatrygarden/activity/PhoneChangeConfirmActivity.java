package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AuthDialogView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PhoneChangeConfirmActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_phone;
    private EditText ed_verification_code;
    private MyRunnable mRunnable;
    private TextView tv_confirm;
    private TextView tv_send_verification_code;
    private final Handler mHandler = new Handler();
    private int time = 60;
    private String verification_code = "";

    public class MyRunnable implements Runnable {
        private MyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PhoneChangeConfirmActivity.access$110(PhoneChangeConfirmActivity.this);
            if (PhoneChangeConfirmActivity.this.time > 0) {
                PhoneChangeConfirmActivity.this.tv_send_verification_code.setText(String.format(Locale.CHINA, "重新获取（%d）", Integer.valueOf(PhoneChangeConfirmActivity.this.time)));
                PhoneChangeConfirmActivity.this.mHandler.postDelayed(this, 1000L);
                return;
            }
            PhoneChangeConfirmActivity.this.mHandler.removeCallbacks(PhoneChangeConfirmActivity.this.mRunnable);
            PhoneChangeConfirmActivity.this.mRunnable = null;
            PhoneChangeConfirmActivity.this.time = 60;
            PhoneChangeConfirmActivity.this.tv_send_verification_code.setText("重新获取");
            PhoneChangeConfirmActivity.this.tv_send_verification_code.setBackgroundResource(R.drawable.ffe25d49_20);
            PhoneChangeConfirmActivity.this.tv_send_verification_code.setClickable(true);
        }
    }

    public static /* synthetic */ int access$110(PhoneChangeConfirmActivity phoneChangeConfirmActivity) {
        int i2 = phoneChangeConfirmActivity.time;
        phoneChangeConfirmActivity.time = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeMobile$3(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                AlertToast("手机号换绑成功");
                SharePreferencesUtils.clearAppointData(this);
                goActivity(LoginActivity.class);
                EMClient.getInstance().logout(true, new EMCallBack() { // from class: com.psychiatrygarden.activity.PhoneChangeConfirmActivity.3
                    @Override // com.hyphenate.EMCallBack
                    public void onError(int code, String error) {
                    }

                    @Override // com.hyphenate.EMCallBack
                    public /* synthetic */ void onProgress(int i2, String str3) {
                        d1.a.a(this, i2, str3);
                    }

                    @Override // com.hyphenate.EMCallBack
                    public void onSuccess() {
                    }
                });
                EventBus.getDefault().post(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS);
                finish();
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeMobile$4(VolleyError volleyError, String str) {
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
            this.tv_confirm.setBackgroundResource(R.drawable.fff1cac5_20);
        } else {
            this.tv_confirm.setBackgroundResource(R.drawable.ffe25d49_20);
        }
    }

    public void changeMobile() {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("mobile", this.ed_phone.getText().toString().trim());
        map.put("code", this.verification_code);
        YJYHttpUtils.post(this, NetworkRequestsURL.changeMobile, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.jf
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12558c.lambda$changeMobile$3((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.kf
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12588c.lambda$changeMobile$4(volleyError, str);
            }
        });
    }

    /* renamed from: getAuthCode, reason: merged with bridge method [inline-methods] */
    public void lambda$onClick$0(String captchaVerifyParam) {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("mobile", this.ed_phone.getText().toString().trim());
        map.put("type", "1");
        map.put("captchaVerifyParam", captchaVerifyParam);
        map.put("is_verify", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mYAnzheng, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.hf
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12476c.lambda$getAuthCode$1((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.if
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12509c.lambda$getAuthCode$2(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("变更手机号");
        this.ed_phone = (EditText) findViewById(R.id.ed_phone);
        this.ed_verification_code = (EditText) findViewById(R.id.ed_verification_code);
        this.tv_send_verification_code = (TextView) findViewById(R.id.tv_send_verification_code);
        this.tv_confirm = (TextView) findViewById(R.id.tv_confirm);
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        int id = v2.getId();
        if (id != R.id.tv_confirm) {
            if (id != R.id.tv_send_verification_code) {
                return;
            }
            if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
                ToastUtil.shortToast(this, "请输入手机号");
                return;
            } else {
                new XPopup.Builder(this).asCustom(new AuthDialogView(this, new AuthDialogView.OnAuthDataListener() { // from class: com.psychiatrygarden.activity.gf
                    @Override // com.psychiatrygarden.widget.AuthDialogView.OnAuthDataListener
                    public final void onConfirm(String str) {
                        this.f12441a.lambda$onClick$0(str);
                    }
                })).show();
                return;
            }
        }
        if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
            ToastUtil.shortToast(this, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(this.ed_verification_code.getText().toString())) {
            ToastUtil.shortToast(this, "请输入验证码");
        } else if (TextUtils.equals(this.ed_verification_code.getText().toString(), this.verification_code)) {
            changeMobile();
        } else {
            ToastUtil.shortToast(this, "输入的验证码错误");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_phone_change_confirm);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_send_verification_code.setOnClickListener(this);
        this.tv_confirm.setOnClickListener(this);
        this.ed_phone.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.PhoneChangeConfirmActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                PhoneChangeConfirmActivity.this.setBtnState();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.ed_verification_code.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.PhoneChangeConfirmActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                PhoneChangeConfirmActivity.this.setBtnState();
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
