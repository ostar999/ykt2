package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.AuthDialogView;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RegisterActivity extends BaseActivity {
    private Button bt_auth_code;
    private Button bt_next;
    private EditText et_auth_code;
    private EditText et_input_pwd;
    private EditText et_phone;
    private EditText et_register_code;
    private ImageView iv_look_pwd;
    private TimeCount timeCount;
    private String autu_code = "";
    private boolean isOpenEyes = true;
    View.OnClickListener mOnclick = new AnonymousClass1();

    /* renamed from: com.psychiatrygarden.activity.RegisterActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(String str) {
            RegisterActivity.this.getAuthCode(str);
        }

        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            int id = v2.getId();
            if (id == R.id.bt_auth_code) {
                if (RegisterActivity.this.et_phone.getText().toString().trim().equals("")) {
                    RegisterActivity.this.AlertToast(R.string.toast_phone_empty);
                    return;
                } else if (RegisterActivity.this.et_phone.getText().toString().trim().length() != 11) {
                    RegisterActivity.this.AlertToast(R.string.toast_check_phone);
                    return;
                } else {
                    new XPopup.Builder(RegisterActivity.this).asCustom(new AuthDialogView(RegisterActivity.this, new AuthDialogView.OnAuthDataListener() { // from class: com.psychiatrygarden.activity.ph
                        @Override // com.psychiatrygarden.widget.AuthDialogView.OnAuthDataListener
                        public final void onConfirm(String str) {
                            this.f13544a.lambda$onClick$0(str);
                        }
                    })).show();
                    return;
                }
            }
            if (id != R.id.bt_next) {
                if (id != R.id.iv_look_pwd) {
                    return;
                }
                int length = TextUtils.isEmpty(RegisterActivity.this.et_input_pwd.getText()) ? 0 : RegisterActivity.this.et_input_pwd.getText().toString().length();
                if (RegisterActivity.this.isOpenEyes) {
                    RegisterActivity.this.et_input_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    RegisterActivity.this.et_input_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                RegisterActivity.this.et_input_pwd.setSelection(length);
                RegisterActivity registerActivity = RegisterActivity.this;
                registerActivity.setImageResource(registerActivity.isOpenEyes);
                RegisterActivity.this.isOpenEyes = !r6.isOpenEyes;
                return;
            }
            if (RegisterActivity.this.et_phone.getText().toString().trim().equals("")) {
                RegisterActivity.this.AlertToast(R.string.toast_phone_empty);
                return;
            }
            if (RegisterActivity.this.et_phone.getText().toString().trim().length() != 11) {
                RegisterActivity.this.AlertToast(R.string.toast_check_phone);
                return;
            }
            if (RegisterActivity.this.et_auth_code.getText().toString().trim().equals("")) {
                RegisterActivity.this.AlertToast(R.string.toast_input_auth_code);
                return;
            }
            if (RegisterActivity.this.et_input_pwd.getText().toString().trim().equals("")) {
                RegisterActivity.this.AlertToast(R.string.toast_input_pwd);
                return;
            }
            if (RegisterActivity.this.et_input_pwd.getText().toString().trim().length() < 6 || RegisterActivity.this.et_input_pwd.getText().toString().trim().length() > 16) {
                RegisterActivity.this.AlertToast(R.string.toast_pwd_format);
                return;
            }
            if (!RegisterActivity.this.et_auth_code.getText().toString().trim().equals(RegisterActivity.this.autu_code)) {
                RegisterActivity.this.AlertToast(R.string.toast_auth_code_different);
                return;
            }
            Intent intent = new Intent();
            intent.setClass(RegisterActivity.this.mContext, RegisterInfoActivity.class);
            intent.putExtra("mobile", RegisterActivity.this.et_phone.getText().toString().trim());
            intent.putExtra(CommonParameter.password, RegisterActivity.this.et_input_pwd.getText().toString().trim());
            intent.putExtra("code", RegisterActivity.this.et_auth_code.getText().toString().trim());
            intent.putExtra("et_register_code", RegisterActivity.this.et_register_code.getText().toString().trim());
            RegisterActivity.this.startActivity(intent);
        }
    }

    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInfuture, long countDownInterval) {
            super(millisInfuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (SkinManager.getCurrentSkinType(RegisterActivity.this.mContext) == 0) {
                RegisterActivity.this.bt_auth_code.setTextColor(ContextCompat.getColor(RegisterActivity.this.mContext, R.color.app_theme_red));
            } else {
                RegisterActivity.this.bt_auth_code.setTextColor(ContextCompat.getColor(RegisterActivity.this.mContext, R.color.red_theme_night));
            }
            RegisterActivity.this.bt_auth_code.setText("重新发送");
            RegisterActivity.this.bt_auth_code.setClickable(true);
            RegisterActivity.this.bt_auth_code.setEnabled(true);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            if (SkinManager.getCurrentSkinType(RegisterActivity.this.mContext) == 0) {
                RegisterActivity.this.bt_auth_code.setTextColor(ContextCompat.getColor(RegisterActivity.this.mContext, R.color.gray_font));
            } else {
                RegisterActivity.this.bt_auth_code.setTextColor(ContextCompat.getColor(RegisterActivity.this.mContext, R.color.jiucuo_night));
            }
            RegisterActivity.this.bt_auth_code.setClickable(false);
            RegisterActivity.this.bt_auth_code.setEnabled(false);
            RegisterActivity.this.bt_auth_code.setText(String.format(Locale.CHINA, "%d 秒后重新发送", Long.valueOf(millisUntilFinished / 1000)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$0(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                try {
                    this.autu_code = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.timeCount.start();
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
    public void setImageResource(boolean isOpenEyes) {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            if (isOpenEyes) {
                this.iv_look_pwd.setImageResource(R.drawable.ic_eyes_open);
                return;
            } else {
                this.iv_look_pwd.setImageResource(R.drawable.ic_eyes_close);
                return;
            }
        }
        if (isOpenEyes) {
            this.iv_look_pwd.setImageResource(R.drawable.ic_eyes_open_night);
        } else {
            this.iv_look_pwd.setImageResource(R.drawable.ic_eyes_close_night);
        }
    }

    public void getAuthCode(String captchaVerifyParam) {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("mobile", this.et_phone.getText().toString().trim());
        map.put("type", "1");
        map.put("captchaVerifyParam", captchaVerifyParam);
        map.put("is_verify", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mYAnzheng, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.nh
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f13050c.lambda$getAuthCode$0((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.oh
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f13081c.lambda$getAuthCode$1(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.et_phone = (EditText) findViewById(R.id.et_phone);
        this.et_auth_code = (EditText) findViewById(R.id.et_auth_code);
        this.et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
        this.bt_auth_code = (Button) findViewById(R.id.bt_auth_code);
        this.bt_next = (Button) findViewById(R.id.bt_next);
        this.et_register_code = (EditText) findViewById(R.id.et_register_code);
        this.iv_look_pwd = (ImageView) findViewById(R.id.iv_look_pwd);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.iv_look_pwd.setImageResource(R.drawable.ic_eyes_close);
        } else {
            this.iv_look_pwd.setImageResource(R.drawable.ic_eyes_close_night);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("RegisterActivity")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle(R.string.user_register);
        this.timeCount = new TimeCount(120000L, 1000L);
        setContentView(R.layout.activity_register);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.tv_yijiaoyuan).setOnClickListener(this.mOnclick);
        this.bt_auth_code.setOnClickListener(this.mOnclick);
        this.bt_next.setOnClickListener(this.mOnclick);
        this.iv_look_pwd.setOnClickListener(this.mOnclick);
        this.iv_look_pwd.setTag(Boolean.TRUE);
    }
}
