package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.bean.CancelAccountBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
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
public class ForgetAuthCodeActivity extends BaseActivity {
    private Button bt_ok;
    private Button mBtGetAuthCode;
    private EditText mEtAuthCode;
    private TimeCount mTimecount;
    private TextView mTvPhone;
    private String mAutuCode = "";
    View.OnClickListener mOnclick = new AnonymousClass1();

    /* renamed from: com.psychiatrygarden.activity.ForgetAuthCodeActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(String str) {
            ForgetAuthCodeActivity.this.getAuthCode(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$1() {
            ForgetAuthCodeActivity.this.putAccountData();
        }

        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            int id = v2.getId();
            if (id == R.id.bt_get_auth_code) {
                new XPopup.Builder(ForgetAuthCodeActivity.this).asCustom(new AuthDialogView(ForgetAuthCodeActivity.this, new AuthDialogView.OnAuthDataListener() { // from class: com.psychiatrygarden.activity.cb
                    @Override // com.psychiatrygarden.widget.AuthDialogView.OnAuthDataListener
                    public final void onConfirm(String str) {
                        this.f11141a.lambda$onClick$0(str);
                    }
                })).show();
                return;
            }
            if (id != R.id.bt_ok) {
                return;
            }
            if (ForgetAuthCodeActivity.this.mEtAuthCode.getText().toString().trim().equals("")) {
                ForgetAuthCodeActivity.this.AlertToast(R.string.toast_input_auth_code);
                return;
            }
            if (!ForgetAuthCodeActivity.this.mEtAuthCode.getText().toString().trim().equals(ForgetAuthCodeActivity.this.mAutuCode)) {
                ForgetAuthCodeActivity.this.AlertToast(R.string.toast_auth_code_different);
                return;
            }
            if (3 == ForgetAuthCodeActivity.this.getIntent().getIntExtra("type", 1)) {
                new XPopup.Builder(ForgetAuthCodeActivity.this).moveUpToKeyboard(Boolean.FALSE).asCustom(new PopLogoutHint(ForgetAuthCodeActivity.this, new PopLogoutHint.onDialogOkClickListener() { // from class: com.psychiatrygarden.activity.db
                    @Override // com.psychiatrygarden.widget.PopLogoutHint.onDialogOkClickListener
                    public final void onclickOk() {
                        this.f12240a.lambda$onClick$1();
                    }
                })).show();
                return;
            }
            Intent intent = new Intent(ForgetAuthCodeActivity.this.mContext, (Class<?>) ForgetModifyPwdActivity.class);
            intent.putExtra("mobile", ForgetAuthCodeActivity.this.getIntent().getStringExtra("mobile"));
            intent.putExtra("code", ForgetAuthCodeActivity.this.mAutuCode);
            intent.putExtra("type", ForgetAuthCodeActivity.this.getIntent().getIntExtra("type", 1));
            ForgetAuthCodeActivity.this.startActivity(intent);
        }
    }

    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInfuture, long countDownInterval) {
            super(millisInfuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setBackgroundResource(R.drawable.ffe25d49_20);
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setText("重新发送");
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setClickable(true);
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setEnabled(true);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setBackgroundResource(R.drawable.fff1cac5_20);
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setClickable(false);
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setEnabled(false);
            ForgetAuthCodeActivity.this.mBtGetAuthCode.setText(String.format(Locale.CHINA, "%d 秒后重新发送", Long.valueOf(millisUntilFinished / 1000)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$0(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                try {
                    this.mAutuCode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.mTimecount.start();
                AlertToast("发送成功");
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

    public void getAuthCode(String captchaVerifyParam) {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("mobile", this.mTvPhone.getText().toString().trim());
        map.put("type", "2");
        map.put("captchaVerifyParam", captchaVerifyParam);
        map.put("is_verify", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mYAnzheng, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.ab
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f10988c.lambda$getAuthCode$0((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.bb
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f11106c.lambda$getAuthCode$1(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TextView textView = (TextView) findViewById(R.id.zhuxiaotext);
        this.mEtAuthCode = (EditText) findViewById(R.id.et_auth_code);
        this.mBtGetAuthCode = (Button) findViewById(R.id.bt_get_auth_code);
        this.bt_ok = (Button) findViewById(R.id.bt_ok);
        this.mTvPhone = (TextView) findViewById(R.id.tv_phone);
        this.mAutuCode = getIntent().getStringExtra("code");
        this.mTvPhone.setText(getIntent().getStringExtra("mobile"));
        this.mTimecount.start();
        if (3 == getIntent().getIntExtra("type", 1)) {
            textView.setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("ForgetAuthCodeActivity")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    public void putAccountData() {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("app_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this));
        ajaxParams.put("mobile", "" + getIntent().getStringExtra("mobile"));
        ajaxParams.put("code", "" + this.mAutuCode);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.cancellationApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ForgetAuthCodeActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForgetAuthCodeActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                ForgetAuthCodeActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        CancelAccountBean cancelAccountBean = (CancelAccountBean) new Gson().fromJson(jSONObject.optString("data"), CancelAccountBean.class);
                        Intent intent = new Intent(ForgetAuthCodeActivity.this, (Class<?>) AccountLogoutConfirmActivity.class);
                        intent.putExtra("gotoType", 0);
                        intent.putExtra(AliyunLogCommon.LogLevel.INFO, cancelAccountBean);
                        ForgetAuthCodeActivity.this.startActivity(intent);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        int intExtra = getIntent().getIntExtra("type", 1);
        if (intExtra == 1) {
            setTitle(R.string.reset_pwd);
        } else if (intExtra == 2) {
            setTitle(R.string.modify_pwd);
        } else if (intExtra == 3) {
            setTitle("注销账号");
        }
        this.mTimecount = new TimeCount(120000L, 1000L);
        setContentView(R.layout.activity_forget_auth_code);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtGetAuthCode.setOnClickListener(this.mOnclick);
        this.bt_ok.setOnClickListener(this.mOnclick);
    }
}
