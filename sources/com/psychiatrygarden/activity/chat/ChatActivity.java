package com.psychiatrygarden.activity.chat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.fragmenthome.chat.MyEaseChatFragment;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ChatActivity extends BaseActivity implements View.OnClickListener, QuestionDataCallBack<String> {
    private String announcement_content;
    private String announcement_image;
    private MyEaseChatFragment chatFragment;
    int chatType;
    private LinearLayout ll_chat_announcement;
    Handler mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.activity.chat.ChatActivity.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what != 1) {
                return false;
            }
            ChatActivity.this.title_bar_message.setTitle((String) msg.obj);
            return false;
        }
    });
    private EaseTitleBar title_bar_message;
    private TextView tv_announcement;

    private EaseAvatarOptions getAvatarOptions() {
        EaseAvatarOptions easeAvatarOptions = new EaseAvatarOptions();
        easeAvatarOptions.setAvatarShape(2);
        easeAvatarOptions.setAvatarRadius(5);
        return easeAvatarOptions;
    }

    private void initChatHead() {
        EaseIM.getInstance().setAvatarOptions(getAvatarOptions());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        int i2 = this.chatType;
        if (i2 == 2) {
            Intent intent = new Intent(this, (Class<?>) GroupChatSetActivity.class);
            intent.putExtra("id", getIntent().getExtras().getString("id"));
            intent.putExtra("community_id", getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID));
            startActivity(intent);
            return;
        }
        if (i2 == 1) {
            Intent intent2 = new Intent(this, (Class<?>) PullBlackActivity.class);
            intent2.putExtra("nickname", this.title_bar_message.getTitle().toString());
            intent2.putExtra("to_user_uuid", getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID));
            startActivity(intent2);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
        initChatHead();
        this.mActionBar.hide();
        this.ll_chat_announcement = (LinearLayout) findViewById(R.id.ll_chat_announcement);
        this.tv_announcement = (TextView) findViewById(R.id.tv_announcement);
        this.ll_chat_announcement.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11148c.onClick(view);
            }
        });
        EaseTitleBar easeTitleBar = (EaseTitleBar) findViewById(R.id.title_bar_message);
        this.title_bar_message = easeTitleBar;
        easeTitleBar.getRightLayout().setVisibility(0);
        this.title_bar_message.getLeftLayout().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11150c.lambda$init$0(view);
            }
        });
        ImageView leftImage = this.title_bar_message.getLeftImage();
        if (SkinManager.getCurrentSkinType(this) == 1) {
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_black_back);
            drawable.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
            leftImage.setImageDrawable(drawable);
        } else {
            Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.ic_black_back);
            drawable2.setColorFilter(Color.parseColor("#141516"), PorterDuff.Mode.SRC_IN);
            leftImage.setImageDrawable(drawable2);
        }
        ImageView rightImage = this.title_bar_message.getRightImage();
        if (SkinManager.getCurrentSkinType(this) == 1) {
            Drawable drawable3 = ContextCompat.getDrawable(this, R.drawable.jmui_chat_detail);
            drawable3.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
            rightImage.setImageDrawable(drawable3);
        } else {
            Drawable drawable4 = ContextCompat.getDrawable(this, R.drawable.jmui_chat_detail);
            drawable4.setColorFilter(Color.parseColor("#141516"), PorterDuff.Mode.SRC_IN);
            rightImage.setImageDrawable(drawable4);
        }
        this.title_bar_message.getRightLayout().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11152c.lambda$init$1(view);
            }
        });
        if (TextUtils.isEmpty(getIntent().getExtras().getString("name"))) {
            int i2 = this.chatType;
            if (i2 == 2) {
                Log.e(this.TAG, "init: " + getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID));
                EMClient.getInstance().groupManager().asyncGetGroupFromServer(getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID), new EMValueCallBack<EMGroup>() { // from class: com.psychiatrygarden.activity.chat.ChatActivity.1
                    @Override // com.hyphenate.EMValueCallBack
                    public void onError(int error, String errorMsg) {
                        Log.e(ChatActivity.this.TAG, error + "onError: " + errorMsg);
                    }

                    @Override // com.hyphenate.EMValueCallBack
                    public void onSuccess(EMGroup value) {
                        try {
                            LogUtils.e("mengdepeng", "init: " + value.getGroupName());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = value.getGroupName();
                            ChatActivity.this.mHandler.sendMessage(message);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            } else if (i2 == 1) {
                final String[] strArr = {getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID)};
                EMClient.getInstance().userInfoManager().fetchUserInfoByAttribute(strArr, new EMUserInfo.EMUserInfoType[]{EMUserInfo.EMUserInfoType.NICKNAME, EMUserInfo.EMUserInfoType.AVATAR_URL}, new EMValueCallBack<Map<String, EMUserInfo>>() { // from class: com.psychiatrygarden.activity.chat.ChatActivity.2
                    @Override // com.hyphenate.EMValueCallBack
                    public void onError(int error, String errorMsg) {
                    }

                    @Override // com.hyphenate.EMValueCallBack
                    public void onSuccess(Map<String, EMUserInfo> value) {
                        ProjectApp.hxUser.putAll(value);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = value.get(strArr[0]).getNickname();
                        ChatActivity.this.mHandler.sendMessage(message);
                    }
                });
            }
        } else {
            this.title_bar_message.setTitle(getIntent().getExtras().getString("name"));
        }
        this.chatFragment = new MyEaseChatFragment();
        getIntent().getExtras().putString("community_id", getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID));
        this.chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, this.chatFragment).commit();
        if (this.chatType == 2) {
            ChatRequest.getIntance(this).getAnnouncement(getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID), this);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.ll_chat_announcement && this.chatType == 2) {
            ChatRequest.getIntance(this).communitySee(getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID), this);
            this.ll_chat_announcement.setVisibility(8);
            Intent intent = new Intent(this, (Class<?>) ChatEditInfoActivity.class);
            intent.putExtra("id", getIntent().getExtras().getString("id"));
            intent.putExtra("community_id", getIntent().getExtras().getString(EaseConstant.EXTRA_CONVERSATION_ID));
            intent.putExtra("content", this.announcement_content);
            intent.putExtra("user_identity", "0");
            intent.putExtra("announcement_image", this.announcement_image);
            intent.putExtra("type", CommonParameter.tv_group_chat_notice);
            startActivity(intent);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String onEveStr) {
        if (onEveStr.equals(EventBusConstant.EVENT_CHAT_GROUP_EXIT)) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            if (grantResults[0] != -1 || ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
                return;
            }
            ToastUtil.shortToast(this, "无法下载，请检查app存储权限是否打开！");
            return;
        }
        if (2 == requestCode) {
            if (grantResults[0] != -1 || ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.ACCESS_COARSE_LOCATION)) {
                return;
            }
            ToastUtil.shortToast(this, "无法使用该功能，请检查app定位权限是否打开！");
            return;
        }
        if (3 == requestCode) {
            if (grantResults[0] != -1 || ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA)) {
                return;
            }
            ToastUtil.shortToast(this, "无法使用该功能，请检查app相机权限是否打开！");
            return;
        }
        if (4 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.RECORD_AUDIO)) {
            ToastUtil.shortToast(this, "无法使用该功能，请检查app录音权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CommonUtil.copyEncryptedFile(this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.demo_activity_chat);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.getAnnouncementApi)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (jSONObject.optString("code").equals("200")) {
                    if (jSONObject.optJSONObject("data").optString("see", "1").equals("0")) {
                        this.announcement_image = jSONObject.optJSONObject("data").optString("announcement_image");
                        String strOptString = jSONObject.optJSONObject("data").optString("announcement");
                        this.announcement_content = strOptString;
                        if (TextUtils.isEmpty(strOptString)) {
                            this.ll_chat_announcement.setVisibility(8);
                        } else {
                            this.ll_chat_announcement.setVisibility(0);
                            this.tv_announcement.setText(this.announcement_content);
                        }
                    } else {
                        this.ll_chat_announcement.setVisibility(8);
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
