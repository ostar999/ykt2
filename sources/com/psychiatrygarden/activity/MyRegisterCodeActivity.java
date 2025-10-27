package com.psychiatrygarden.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.UrlsConfig;
import com.psychiatrygarden.widget.DialogShare;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyRegisterCodeActivity extends BaseActivity {
    Button mBtMyRegisterCode;
    TextView mTvGainJifen;
    TextView mTvMyCode;
    TextView mTvShowState;
    String mRegisterCode = "";
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ke
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f12587c.lambda$new$0(view);
        }
    };
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.MyRegisterCodeActivity.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            MyRegisterCodeActivity.this.AlertToast(arg1.getMessage());
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    private void getRegisterCode() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.getmd5(this.mContext, "", ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.MyRegisterCodeActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyRegisterCodeActivity.this.AlertToast("请求失败");
                MyRegisterCodeActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                MyRegisterCodeActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("1")) {
                        JSONObject jSONObject2 = new JSONObject(jSONObject.optString("data"));
                        MyRegisterCodeActivity.this.mRegisterCode = jSONObject2.optString("inv_code");
                        MyRegisterCodeActivity.this.mTvMyCode.setText(Html.fromHtml("我的邀请码：<font  color='#B2575C'>" + MyRegisterCodeActivity.this.mRegisterCode + "</font>"));
                        MyRegisterCodeActivity.this.mTvGainJifen.setText(jSONObject2.optString("inv_num") + "，" + jSONObject2.optString("inv_num"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                MyRegisterCodeActivity.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id != R.id.bt_my_register_code) {
            if (id != R.id.tv_show_state) {
                return;
            }
            goActivity(MyRegisterCodeStateActivity.class);
        } else if (this.mRegisterCode.equals("")) {
            AlertToast("邀请码为空");
        } else {
            new DialogShare(this, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.je
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i2) {
                    this.f12557a.shareAppControl(i2);
                }
            }).show();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mTvShowState = (TextView) findViewById(R.id.tv_show_state);
        this.mTvMyCode = (TextView) findViewById(R.id.tv_my_code);
        this.mBtMyRegisterCode = (Button) findViewById(R.id.bt_my_register_code);
        this.mTvGainJifen = (TextView) findViewById(R.id.tv_gain_jifen);
        getRegisterCode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("邀请注册");
        setContentView(R.layout.activity_my_register_code);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtMyRegisterCode.setOnClickListener(this.mOnclick);
        this.mTvShowState.setOnClickListener(this.mOnclick);
    }

    public void shareAppControl(int i2) {
        String str = "?code=" + this.mRegisterCode;
        LogUtils.e(this.TAG, "分享地址" + str);
        String str2 = getString(R.string.register_app_code) + this.mRegisterCode;
        String str3 = UrlsConfig.shareImageUrl;
        UMImage uMImage = new UMImage(this, str3);
        if (str3.equals("")) {
            uMImage = new UMImage(this, R.drawable.app_icon);
        }
        UMWeb uMWeb = new UMWeb(str);
        uMWeb.setTitle("邀请注册");
        uMWeb.setDescription(str2);
        uMWeb.setThumb(uMImage);
        new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
