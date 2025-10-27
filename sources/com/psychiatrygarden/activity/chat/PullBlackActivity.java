package com.psychiatrygarden.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.EMSilentModeResult;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.yikaobang.yixue.R;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PullBlackActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.chat.PullBlackActivity.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
        }
    };
    public Switch switchs;
    public Switch switchs_msg;
    public String to_user_uuid;
    public TextView tousu;
    public String username;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(new Intent(this, (Class<?>) ChatReportActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$2(CompoundButton compoundButton, boolean z2) {
        if (z2) {
            ChatRequest.getIntance(this).addBlackList(this.to_user_uuid, this);
        } else {
            ChatRequest.getIntance(this).removeBlackList(this.to_user_uuid, this);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.username = getIntent().getExtras().getString("nickname");
        this.to_user_uuid = getIntent().getExtras().getString("to_user_uuid");
        this.switchs = (Switch) findViewById(R.id.switchs);
        TextView textView = (TextView) findViewById(R.id.tousu);
        this.tousu = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11158c.lambda$init$0(view);
            }
        });
        findViewById(R.id.jmui_return_btn).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11160c.lambda$init$1(view);
            }
        });
        ChatRequest.getIntance(this).onBlackList(this.to_user_uuid, this);
        EMClient.getInstance().pushManager().getSilentModeForConversation(this.to_user_uuid, EMConversation.EMConversationType.Chat, new EMValueCallBack<EMSilentModeResult>() { // from class: com.psychiatrygarden.activity.chat.PullBlackActivity.1
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int error, String errorMsg) {
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(EMSilentModeResult result) {
                if (result.isConversationRemindTypeEnabled() && result.getRemindType() == EMPushManager.EMPushRemindType.NONE) {
                    PullBlackActivity.this.mHandler.sendEmptyMessage(0);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String onEveStr) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_pull_black);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (!requstUrl.equals(NetworkRequestsURL.onBlackListApi)) {
            if (requstUrl.equals(NetworkRequestsURL.addBlackListApi)) {
                return;
            }
            requstUrl.equals(NetworkRequestsURL.removeBlackListApi);
            return;
        }
        try {
            if (new JSONObject(s2).optString("data").equals("0")) {
                this.switchs.setChecked(false);
            } else {
                this.switchs.setChecked(true);
            }
            this.switchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.chat.h0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    this.f11162c.lambda$onSuccess$2(compoundButton, z2);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
