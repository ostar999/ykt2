package com.psychiatrygarden.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Md5Util;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForgetModifyPwdActivity extends BaseActivity {
    private Button bt_ok;
    private EditText et_input_pwd;
    private EditText et_input_pwd_re;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$modifyPwd$1(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                EventBus.getDefault().post("ForgetPwdPhoneActivity");
                EventBus.getDefault().post("ForgetAuthCodeActivity");
                AlertToast("修改成功");
                finish();
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$modifyPwd$2(VolleyError volleyError, String str) {
        hideProgressDialog();
        AlertToast(volleyError.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.et_input_pwd.getText().toString().trim().equals("")) {
            AlertToast(R.string.toast_input_pwd);
            return;
        }
        if (this.et_input_pwd.getText().toString().trim().length() < 6 || this.et_input_pwd.getText().toString().trim().length() > 16) {
            AlertToast(R.string.toast_pwd_format);
        } else if (this.et_input_pwd.getText().toString().trim().equals(this.et_input_pwd_re.getText().toString().trim())) {
            modifyPwd();
        } else {
            AlertToast("两次输入的密码不相同");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
        this.et_input_pwd_re = (EditText) findViewById(R.id.et_input_pwd_re);
        this.bt_ok = (Button) findViewById(R.id.bt_ok);
    }

    public void modifyPwd() {
        showProgressDialog();
        HashMap map = new HashMap();
        map.put("code", getIntent().getStringExtra("code"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("email"))) {
            map.put("email", getIntent().getStringExtra("email"));
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("mobile"))) {
            map.put("mobile", getIntent().getStringExtra("mobile"));
        }
        map.put(CommonParameter.password, Md5Util.MD5Encode(this.et_input_pwd.getText().toString().trim()));
        YJYHttpUtils.post(this, NetworkRequestsURL.mUpdatePwdUrl, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.fb
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12348c.lambda$modifyPwd$1((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.gb
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12438c.lambda$modifyPwd$2(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
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
            setTitle("设置新密码");
        }
        setContentView(R.layout.activity_forget_modify_pwd);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.bt_ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.eb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12280c.lambda$setListenerForWidget$0(view);
            }
        });
    }
}
