package com.psychiatrygarden.activity.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.AccountLogoutConfirmActivity;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.IdentityConfirmActivity;
import com.psychiatrygarden.activity.ModifyMailboxActivity;
import com.psychiatrygarden.activity.mine.AccountLogoutActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.WXAuthEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.UnbindWxPopWidow;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AccountWithPasswordActivity extends BaseActivity {
    private RelativeLayout changePasswordNum;
    private boolean hasCheckBind;
    private boolean isBind;
    private RelativeLayout mLyCancelAccount;
    private TextView mTvWechat;
    private RelativeLayout rl_email;
    private RelativeLayout rl_phone;
    private TextView tv_email;
    private TextView tv_phone;
    private String userName;

    /* renamed from: com.psychiatrygarden.activity.setting.AccountWithPasswordActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(final View v2) {
            if (AccountWithPasswordActivity.this.isBind) {
                new XPopup.Builder(v2.getContext()).asCustom(new UnbindWxPopWidow(v2.getContext(), new UnbindWxPopWidow.ClickIml() { // from class: com.psychiatrygarden.activity.setting.AccountWithPasswordActivity.1.1
                    @Override // com.psychiatrygarden.widget.UnbindWxPopWidow.ClickIml
                    public void mClickIml() {
                        AjaxParams ajaxParams = new AjaxParams();
                        ajaxParams.put("type", "wechat");
                        YJYHttpUtils.post(v2.getContext(), NetworkRequestsURL.unbindWx, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.AccountWithPasswordActivity.1.1.1
                            @Override // net.tsz.afinal.http.AjaxCallBack
                            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                                super.onFailure(t2, errorNo, strMsg);
                            }

                            @Override // net.tsz.afinal.http.AjaxCallBack
                            public void onSuccess(String s2) {
                                super.onSuccess((C02781) s2);
                                try {
                                    if (new JSONObject(s2).optString("code", "").equals("200")) {
                                        AccountWithPasswordActivity.this.AlertToast("已解除绑定");
                                        AccountWithPasswordActivity.this.mTvWechat.setText("尚未绑定");
                                        AccountWithPasswordActivity.this.isBind = false;
                                        UserConfig.getInstance().unbindWx();
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        });
                    }
                })).show();
                return;
            }
            IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(v2.getContext(), SdkConstant.getWxAppId());
            if (!iwxapiCreateWXAPI.isWXAppInstalled()) {
                ToastUtil.shortToast(v2.getContext(), "您的设备未安装微信客户端");
                return;
            }
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "com.yikaobang.yixueykb";
            iwxapiCreateWXAPI.sendReq(req);
        }
    }

    private void bindWx(String code) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "wechat");
        ajaxParams.put("code", code);
        YJYHttpUtils.post(this, NetworkRequestsURL.bindWx, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.AccountWithPasswordActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code", "").equals("200")) {
                        String strOptString = jSONObject.optString("message", "");
                        if (TextUtils.isEmpty(strOptString)) {
                            return;
                        }
                        AccountWithPasswordActivity.this.AlertToast(strOptString);
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (jSONObjectOptJSONObject != null) {
                        AccountWithPasswordActivity.this.mTvWechat.setText(jSONObjectOptJSONObject.optString("username", ""));
                    }
                    AccountWithPasswordActivity.this.AlertToast("绑定成功");
                    AccountWithPasswordActivity.this.isBind = true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void checkBind() {
        YJYHttpUtils.get(this, NetworkRequestsURL.checkSocialLoginBind, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.AccountWithPasswordActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        AccountWithPasswordActivity.this.hasCheckBind = true;
                        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
                            return;
                        }
                        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                            JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                            if ("wechat".equals(jSONObject2.optString("type", "")) && "1".equals(jSONObject2.optString("is_bind"))) {
                                AccountWithPasswordActivity.this.mTvWechat.setText(jSONObject2.optString("username", ""));
                                AccountWithPasswordActivity.this.isBind = true;
                            }
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        Intent intent = new Intent();
        intent.setClass(this.mContext, IdentityConfirmActivity.class);
        intent.putExtra("identityType", 1);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        Intent intent = new Intent();
        intent.setClass(this.mContext, IdentityConfirmActivity.class);
        intent.putExtra("identityType", 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (!TextUtils.isEmpty(UserConfig.getInstance().getUser().getEmail())) {
            startActivity(new Intent(this, (Class<?>) ModifyMailboxActivity.class));
            return;
        }
        Intent intent = new Intent(this, (Class<?>) AccountLogoutConfirmActivity.class);
        intent.putExtra("gotoType", 3);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        startActivity(new Intent(this.mContext, (Class<?>) AccountLogoutActivity.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.changePasswordNum = (RelativeLayout) findViewById(R.id.changePasswordNum);
        this.rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        this.rl_email = (RelativeLayout) findViewById(R.id.rl_email);
        this.tv_email = (TextView) findViewById(R.id.tv_email);
        this.tv_phone = (TextView) findViewById(R.id.tv_phone);
        this.mLyCancelAccount = (RelativeLayout) findViewById(R.id.rl_accountCancellation);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_wechat_num);
        this.mTvWechat = (TextView) findViewById(R.id.tv_wechat);
        relativeLayout.setOnClickListener(new AnonymousClass1());
    }

    @Subscribe
    public void onEventMainThread(WXAuthEvent event) {
        if (TextUtils.isEmpty(event.getCode())) {
            return;
        }
        bindWx(event.getCode());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (UserConfig.isLogin()) {
            String str = SharePreferencesUtils.readStrConfig(CommonParameter.TEL, this) + "";
            try {
                if (str.length() > 7) {
                    this.tv_phone.setText(String.format("%s****%s", str.substring(0, 3), str.substring(7)));
                } else {
                    this.tv_phone.setText(str);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                this.tv_phone.setText(str);
            }
            String email = UserConfig.getInstance().getUser().getEmail();
            try {
                if (TextUtils.isEmpty(email)) {
                    this.tv_email.setText("待完善");
                } else {
                    String[] strArrSplit = email.split("@");
                    this.tv_email.setText(String.format("%s*********%s", strArrSplit[0].substring(0, 2), strArrSplit[1]));
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                this.tv_email.setText(email);
            }
            checkBind();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_account_password);
        setTitle("账号与安全");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.changePasswordNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13829c.lambda$setListenerForWidget$0(view);
            }
        });
        this.rl_phone.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13834c.lambda$setListenerForWidget$1(view);
            }
        });
        this.rl_email.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13839c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mLyCancelAccount.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13844c.lambda$setListenerForWidget$3(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS)) {
            finish();
        }
    }
}
