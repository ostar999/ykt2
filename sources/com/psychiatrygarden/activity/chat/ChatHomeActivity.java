package com.psychiatrygarden.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.FollowActivity;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.chat.MyEaseConversationListFragment;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ChatHomeActivity extends BaseActivity implements View.OnClickListener {
    private void getGroupChatList() {
        ChatRequest.getIntance(this).chatGroupList("-1", "", 1, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.activity.chat.ChatHomeActivity.1
            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onStart(String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onSuccess(String s2, String requstUrl) {
                try {
                    if (((GroupChatListBean) new Gson().fromJson(s2, GroupChatListBean.class)).getCode().equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, s2, ChatHomeActivity.this);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    LogUtils.e("mengdepeng", "Exception:" + e2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(new Intent(this, (Class<?>) GroupChatHomeActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        Intent intent = new Intent(this, (Class<?>) FollowActivity.class);
        intent.putExtra("user_id", UserConfig.getUserId());
        intent.putExtra("position", 1);
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mBtnActionbarRight.setText("全部粉丝");
        this.mTvActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setVisibility(8);
        this.iv_actionbar_right.setImageResource(R.mipmap.chat_group_home_add);
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11164c.lambda$init$0(view);
            }
        });
        replaceFragment(MyEaseConversationListFragment.newInstance());
        getGroupChatList();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.frame_chat, fragment);
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("聊天");
        setContentView(R.layout.fragment_chat_home);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11165c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
