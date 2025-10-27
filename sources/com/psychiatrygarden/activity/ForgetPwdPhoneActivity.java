package com.psychiatrygarden.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.widget.AuthDialogView;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForgetPwdPhoneActivity extends BaseActivity {
    private Button bt_next;
    private EditText et_input_phone;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$2(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                Intent intent = new Intent(this.mContext, (Class<?>) ForgetAuthCodeActivity.class);
                intent.putExtra("mobile", this.et_input_phone.getText().toString().trim());
                try {
                    intent.putExtra("code", DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data")));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                intent.putExtra("type", getIntent().getIntExtra("type", 1));
                startActivity(intent);
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAuthCode$3(VolleyError volleyError, String str) {
        hideProgressDialog();
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.et_input_phone.getText().toString().trim().equals("")) {
            AlertToast(R.string.toast_phone_empty);
        } else if (this.et_input_phone.getText().toString().trim().length() != 11) {
            AlertToast(R.string.toast_check_phone);
        } else {
            new XPopup.Builder(this).asCustom(new AuthDialogView(this, new AuthDialogView.OnAuthDataListener() { // from class: com.psychiatrygarden.activity.jb
                @Override // com.psychiatrygarden.widget.AuthDialogView.OnAuthDataListener
                public final void onConfirm(String str) {
                    this.f12553a.lambda$setListenerForWidget$0(str);
                }
            })).show();
        }
    }

    /* renamed from: getAuthCode, reason: merged with bridge method [inline-methods] */
    public void lambda$setListenerForWidget$0(String captchaVerifyParam) {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("mobile", this.et_input_phone.getText().toString().trim());
        map.put("type", "2");
        map.put("captchaVerifyParam", captchaVerifyParam);
        map.put("is_verify", "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.mYAnzheng, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.hb
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12473c.lambda$getAuthCode$2((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.ib
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12505c.lambda$getAuthCode$3(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.et_input_phone = (EditText) findViewById(R.id.et_input_phone);
        this.bt_next = (Button) findViewById(R.id.bt_next);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("ForgetPwdPhoneActivity")) {
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        int intExtra = getIntent().getIntExtra("type", 1);
        if (intExtra == 1) {
            setTitle(R.string.reset_pwd);
        } else if (intExtra == 2) {
            setTitle(R.string.modify_pwd);
        }
        setContentView(R.layout.activity_forgetpwd_phone);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.bt_next.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.kb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12585c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
