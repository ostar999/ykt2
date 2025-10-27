package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import org.json.JSONException;

/* loaded from: classes5.dex */
public class ExitLoginDialogActivity extends BaseActivity {
    public static String App_Id = "";
    public static String App_Name = "";
    public static String App_Type = "";
    public static String Student_Type = "";
    public boolean Comment_library_Red_Dot;
    public String app_title;
    private Button btn_re_login;
    public String user_id;

    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triggerReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        SharePreferencesUtils.clearAppointData(this);
        SharePreferencesUtils.removeConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, this);
        SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this);
        SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_CUT_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("refreshPersonal"));
        EMClient.getInstance().logout(true, new EMCallBack() { // from class: com.psychiatrygarden.activity.ExitLoginDialogActivity.1
            @Override // com.hyphenate.EMCallBack
            public void onError(int code, String error) {
            }

            @Override // com.hyphenate.EMCallBack
            public /* synthetic */ void onProgress(int i2, String str) {
                d1.a.a(this, i2, str);
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
            }
        });
        Intent intent = new Intent(this, (Class<?>) LoginActivity.class);
        intent.addFlags(268435456);
        startActivity(intent);
        overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
        ProjectApp.instance().closeAllActivityWithoutHome();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.btn_re_login = (Button) findViewById(R.id.btn_re_login);
        ((TextView) findViewById(R.id.tv_message)).setText(getIntent().getStringExtra("message"));
        EMClient.getInstance().logout(true, new EMCallBack() { // from class: com.psychiatrygarden.activity.ExitLoginDialogActivity.2
            @Override // com.hyphenate.EMCallBack
            public void onError(int code, String error) {
            }

            @Override // com.hyphenate.EMCallBack
            public /* synthetic */ void onProgress(int i2, String str) {
                d1.a.a(this, i2, str);
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundResource(R.color.transparent);
        super.onCreate(savedInstanceState);
        hideNav();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == 4;
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
    public void setContentView() throws JSONException {
        this.mActionBar.hide();
        setSwipeBackEnable(false);
        setContentView(R.layout.activity_exit_login_dialog);
        SharePreferencesUtils.saveInfo(this.mContext, CommonParameter.SUBMITANSWER, new ArrayList());
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("refreshPersonal"));
        String stringExtra = getIntent().getStringExtra("secret");
        if (UserConfig.isLogin() && stringExtra.equals(UserConfig.getInstance().getUser().getSecret())) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.btn_re_login.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ja
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12552c.lambda$setListenerForWidget$0(view);
            }
        });
    }
}
