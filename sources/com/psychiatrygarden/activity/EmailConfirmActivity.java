package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EmailConfirmActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_phone;
    private EditText ed_verification_code;
    private MyRunnable mRunnable;
    private TextView tv_next;
    private TextView tv_send_verification_code;
    private int emailType = 1;
    private final Handler mHandler = new Handler();
    private int time = 60;
    private String verification_code = "";

    public class MyRunnable implements Runnable {
        private MyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            EmailConfirmActivity.access$110(EmailConfirmActivity.this);
            if (EmailConfirmActivity.this.time > 0) {
                EmailConfirmActivity.this.tv_send_verification_code.setText(String.format(Locale.CHINA, "重新获取（%d）", Integer.valueOf(EmailConfirmActivity.this.time)));
                EmailConfirmActivity.this.mHandler.postDelayed(this, 1000L);
                return;
            }
            EmailConfirmActivity.this.mHandler.removeCallbacks(EmailConfirmActivity.this.mRunnable);
            EmailConfirmActivity.this.mRunnable = null;
            EmailConfirmActivity.this.time = 60;
            EmailConfirmActivity.this.tv_send_verification_code.setText("重新获取");
            EmailConfirmActivity.this.tv_send_verification_code.setBackgroundResource(R.drawable.ffe25d49_20);
            EmailConfirmActivity.this.tv_send_verification_code.setClickable(true);
        }
    }

    public static /* synthetic */ int access$110(EmailConfirmActivity emailConfirmActivity) {
        int i2 = emailConfirmActivity.time;
        emailConfirmActivity.time = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeEmail$4(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                AlertToast(jSONObject.optString("message"));
                UserConfig.getInstance().getUser().setEmail(this.ed_phone.getText().toString().trim());
                UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                EventBus.getDefault().post(EventBusConstant.EVENT_EMAIL_CHANGE_SUCCESS);
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
    public /* synthetic */ void lambda$changeEmail$5(VolleyError volleyError, String str) {
        hideProgressDialog();
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$0(String str, String str2) {
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
    public /* synthetic */ void lambda$getAuthCode$1(VolleyError volleyError, String str) {
        hideProgressDialog();
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unboundEmail$2(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                AlertToast(jSONObject.optString("message"));
                UserConfig.getInstance().getUser().setEmail("");
                UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                EventBus.getDefault().post(EventBusConstant.EVENT_EMAIL_CHANGE_SUCCESS);
                Intent intent = new Intent(this.mContext, (Class<?>) AccountLogoutConfirmActivity.class);
                intent.putExtra("gotoType", 2);
                startActivity(intent);
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
    public /* synthetic */ void lambda$unboundEmail$3(VolleyError volleyError, String str) {
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

    public void changeEmail() {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("email", this.ed_phone.getText().toString().trim());
        map.put("code", this.verification_code);
        if (this.emailType == 1) {
            map.put("type", "bound");
        } else {
            map.put("type", "change");
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.changeEmail, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.g9
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12433c.lambda$changeEmail$4((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.h9
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12471c.lambda$changeEmail$5(volleyError, str);
            }
        });
    }

    public void getAuthCode() {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("email", this.ed_phone.getText().toString().trim());
        if (this.emailType == 2) {
            map.put("type", "4");
        } else {
            map.put("type", "5");
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.authCodeEmail, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.e9
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12276c.lambda$getAuthCode$0((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.f9
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12347c.lambda$getAuthCode$1(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.ed_phone = (EditText) findViewById(R.id.ed_phone);
        this.ed_verification_code = (EditText) findViewById(R.id.ed_verification_code);
        this.tv_send_verification_code = (TextView) findViewById(R.id.tv_send_verification_code);
        this.tv_next = (TextView) findViewById(R.id.tv_next);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        TextView textView2 = (TextView) findViewById(R.id.tv_phone_email);
        int intExtra = getIntent().getIntExtra("emailType", 1);
        this.emailType = intExtra;
        if (intExtra == 1) {
            setTitle("绑定邮箱");
            textView.setText("绑定邮箱，提升账号安全等级");
            this.tv_next.setText("绑定");
            this.ed_phone.setHint("输入邮箱");
        } else if (intExtra == 2) {
            setTitle("解除绑定");
            textView.setText("解绑后，将会降低您的账号安全等级");
            this.tv_next.setText("解除绑定");
            this.ed_phone.setHint("输入绑定邮箱");
        } else {
            setTitle("绑定新邮箱");
            textView.setText("请输入您的邮箱，并接收邮箱验证码进行验证");
            this.tv_next.setText("绑定");
            this.ed_phone.setHint("输入新邮箱");
        }
        textView2.setText("邮箱");
        this.ed_verification_code.setHint("请输入邮箱验证码");
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        int id = v2.getId();
        if (id != R.id.tv_next) {
            if (id != R.id.tv_send_verification_code) {
                return;
            }
            if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
                ToastUtil.shortToast(this, "请输入邮箱");
                return;
            } else if (this.ed_phone.getText().toString().contains("@")) {
                getAuthCode();
                return;
            } else {
                ToastUtil.shortToast(this, "请输入正确的邮箱格式");
                return;
            }
        }
        if (TextUtils.isEmpty(this.ed_phone.getText().toString())) {
            ToastUtil.shortToast(this, "请输入邮箱");
            return;
        }
        if (!this.ed_phone.getText().toString().contains("@")) {
            ToastUtil.shortToast(this, "请输入正确的邮箱格式");
            return;
        }
        if (TextUtils.isEmpty(this.ed_verification_code.getText().toString())) {
            ToastUtil.shortToast(this, "请输入验证码");
            return;
        }
        if (!TextUtils.equals(this.ed_verification_code.getText().toString(), this.verification_code)) {
            ToastUtil.shortToast(this, "输入的验证码错误");
        } else if (this.emailType == 2) {
            unboundEmail();
        } else {
            changeEmail();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_phone_change);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_send_verification_code.setOnClickListener(this);
        this.tv_next.setOnClickListener(this);
        this.ed_phone.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.EmailConfirmActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                EmailConfirmActivity.this.setBtnState();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.ed_verification_code.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.EmailConfirmActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                EmailConfirmActivity.this.setBtnState();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
    }

    public void unboundEmail() {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("email", this.ed_phone.getText().toString().trim());
        map.put("code", this.verification_code);
        YJYHttpUtils.post(this, NetworkRequestsURL.unboundEmail, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.c9
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f11139c.lambda$unboundEmail$2((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.d9
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12238c.lambda$unboundEmail$3(volleyError, str);
            }
        });
    }
}
