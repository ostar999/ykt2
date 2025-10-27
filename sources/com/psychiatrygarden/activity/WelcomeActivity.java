package com.psychiatrygarden.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.hjq.permissions.Permission;
import com.hyphenate.easeui.constants.EaseConstant;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.bean.AdBean;
import com.psychiatrygarden.bean.AdDataBean;
import com.psychiatrygarden.bean.BootPageBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.bean.NoticeBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ReminderPopwindow;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import com.ykb.common_share_lib.CommonConfig;
import de.greenrobot.event.EventBus;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes5.dex */
public class WelcomeActivity extends Activity {
    private Bundle mHXMsgBundle;
    private String mHxPushData;
    private ImageView mImageTopShow;
    private Uri mUri;
    private String mNoticeUrl = "";
    private int mNoticeTimes = 1;
    private Long mNoticeSecond = 0L;
    private String mScheme = "";
    private String fromWechatOpenUrl = "";

    private void getAdPic() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(DatabaseManager.SIZE, CommonUtil.getScreenWidth(this) + StrPool.UNDERLINE + CommonUtil.getScreenHeight(this));
        YJYHttpUtils.get(getApplicationContext(), NetworkRequestsURL.mAdUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.WelcomeActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                AdDataBean data;
                super.onSuccess((AnonymousClass1) t2);
                try {
                    AdBean adBean = (AdBean) new Gson().fromJson(t2, AdBean.class);
                    if (!"200".equals(adBean.getCode()) || (data = adBean.getData()) == null) {
                        return;
                    }
                    if (data.getAndroid_open() != null && !TextUtils.isEmpty(data.getAndroid_open()) && data.getAndroid_open().matches("^[0-9]*$")) {
                        ProjectApp.android_open = Integer.parseInt(data.getAndroid_open());
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.kaoshishijian, data.getExam_time(), WelcomeActivity.this.getApplicationContext());
                    BootPageBean boot_page = data.getBoot_page();
                    NoticeBean notice = data.getNotice();
                    if (boot_page != null) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.boot_page, boot_page.getTarget_params(), WelcomeActivity.this);
                        if (!TextUtils.isEmpty(boot_page.getImg_url())) {
                            WelcomeActivity welcomeActivity = WelcomeActivity.this;
                            String img_url = boot_page.getImg_url();
                            Objects.requireNonNull(img_url);
                            GlideUtils.loadImage(welcomeActivity, img_url, WelcomeActivity.this.mImageTopShow);
                        }
                    }
                    if (notice != null) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.notice, new Gson().toJson(notice), WelcomeActivity.this);
                        WelcomeActivity.this.mNoticeUrl = notice.getWeb_url();
                        if (notice.getTimes() != null && notice.getTimes().matches("^[0-9]*$")) {
                            WelcomeActivity.this.mNoticeTimes = Integer.parseInt(notice.getTimes());
                        }
                        if (notice.getSecond() == null || !notice.getSecond().matches("^[0-9]*$")) {
                            return;
                        }
                        WelcomeActivity.this.mNoticeSecond = Long.valueOf(Long.parseLong(notice.getSecond()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getNotifyPermission() {
        if (Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(this, Permission.POST_NOTIFICATIONS) == 0) {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Permission.POST_NOTIFICATIONS}, 111);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goMain() {
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.agree, false, this)) {
            showSelectPop();
            return;
        }
        Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
        intent.putExtra("url", "");
        intent.putExtra("isshow", false);
        Bundle bundle = this.mHXMsgBundle;
        if (bundle != null) {
            intent.putExtra(EventBusConstant.EVENT_HX_MESSAGE_DATA, bundle);
        }
        if (!TextUtils.isEmpty(this.mHxPushData) && !this.mHxPushData.equals(StrPool.EMPTY_JSON) && this.mHxPushData.contains(PushConstants.PUSH_TYPE)) {
            intent.putExtra(EventBusConstant.EVENT_HX_PUSH_DATA, this.mHxPushData);
        }
        if (!TextUtils.isEmpty(this.mHxPushData) && !this.mHxPushData.equals(StrPool.EMPTY_JSON) && this.mHxPushData.contains(PushConstants.PUSH_TYPE)) {
            intent.putExtra(EventBusConstant.EVENT_HX_PUSH_DATA, this.mHxPushData);
        }
        if (this.mUri != null) {
            intent.putExtra("scheme", this.mScheme);
            intent.setData(this.mUri);
        }
        intent.putExtra("fromWechatOpenUrl", this.fromWechatOpenUrl);
        intent.putExtra(CommonParameter.notice, this.mNoticeUrl);
        intent.putExtra("notice_times", this.mNoticeTimes);
        intent.putExtra("notice_second", this.mNoticeSecond);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToGuidePage() {
        startActivity(AppGuidePageActivity.newIntent(this));
    }

    private void gotoChat(Bundle bundle) {
        try {
            String string = bundle.getString("t");
            String string2 = bundle.getString("f");
            String string3 = bundle.getString("m");
            String string4 = bundle.getString("g");
            Log.e("mengdepeng", "welcomeonCreate:  t:" + string + " f:" + string2 + " m:" + string3 + " g:" + string4);
            if (TextUtils.isEmpty(string4)) {
                if (TextUtils.isEmpty(string2)) {
                    return;
                }
                Intent intent = new Intent(this, (Class<?>) ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
                intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, string2);
                intent.setFlags(268435456);
                startActivity(intent);
                return;
            }
            Intent intent2 = new Intent(this, (Class<?>) ChatActivity.class);
            intent2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
            GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, this, ""), GroupChatListBean.class);
            if (groupChatListBean.getCode().equals("200")) {
                int i2 = 0;
                while (true) {
                    if (i2 >= groupChatListBean.getData().size()) {
                        break;
                    }
                    if (groupChatListBean.getData().get(i2).getCommunity_id().equals(string4)) {
                        intent2.putExtra("group_img", groupChatListBean.getData().get(i2).getLogo());
                        intent2.putExtra("name", groupChatListBean.getData().get(i2).getName());
                        break;
                    }
                    i2++;
                }
            }
            intent2.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, string4);
            intent2.setFlags(268435456);
            startActivity(intent2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triggerReceiver);
    }

    private /* synthetic */ void lambda$onCreate$0(int i2, String str) {
        ProjectApp.instance().setApiUrl(i2);
        init();
    }

    private void showSelectPop() {
        ReminderPopwindow reminderPopwindow = new ReminderPopwindow(this, new ReminderPopwindow.onclickImL() { // from class: com.psychiatrygarden.activity.ir
            @Override // com.psychiatrygarden.widget.ReminderPopwindow.onclickImL
            public final void agree() {
                this.f12538a.goToGuidePage();
            }
        });
        XPopup.Builder builder = new XPopup.Builder(this);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).asCustom(reminderPopwindow).show();
    }

    public void init() {
        this.mImageTopShow = (ImageView) findViewById(R.id.image_top);
        getAdPic();
        try {
            if (this.mHXMsgBundle != null) {
                goMain();
            } else if (TextUtils.isEmpty(this.mHxPushData) || this.mHxPushData.equals(StrPool.EMPTY_JSON) || !this.mHxPushData.contains(PushConstants.PUSH_TYPE)) {
                this.mImageTopShow.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.hr
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f12488c.goMain();
                    }
                }, 1000L);
            } else {
                goMain();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            goMain();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws JsonIOException {
        getWindow().setBackgroundDrawable(null);
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.agree, false, this)) {
            SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, 0, this);
        }
        SkinManager.onActivityCreateSetSkin(this);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 0 ? R.color.trans_app_theme_gray : R.color.sysbg_night));
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ProjectApp.instance.addActivity(this);
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.fromWechatOpenUrl = getIntent().getStringExtra("wechatUrl");
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.agree, false, this)) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                LogUtils.e("mengdepeng", extras.toString());
                String string = extras.getString("f");
                try {
                    if (!TextUtils.isEmpty(extras.getString("g")) || !TextUtils.isEmpty(string)) {
                        this.mHXMsgBundle = extras;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                JsonObject jsonObject = new JsonObject();
                for (String str : extras.keySet()) {
                    try {
                        jsonObject.addProperty(str, extras.getString(str));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        Log.d("Convert Bund", e3.toString());
                    }
                }
                String json = new Gson().toJson((JsonElement) jsonObject);
                this.mHxPushData = json;
                LogUtils.e("mengdepeng", json);
            }
            try {
                if (!isTaskRoot()) {
                    Intent intent = getIntent();
                    String action = intent.getAction();
                    if (intent.hasCategory("android.intent.category.LAUNCHER") && action.equals("android.intent.action.MAIN")) {
                        if (!TextUtils.isEmpty(this.mHxPushData) && !this.mHxPushData.equals(StrPool.EMPTY_JSON) && this.mHxPushData.contains(PushConstants.PUSH_TYPE)) {
                            PublicMethodActivity.getInstance().mToActivity(this.mHxPushData);
                        }
                        Bundle bundle = this.mHXMsgBundle;
                        if (bundle != null) {
                            gotoChat(bundle);
                        }
                        finish();
                        return;
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            try {
                Intent intent2 = getIntent();
                this.mScheme = intent2.getScheme();
                LogUtils.e("scheme", "=====>" + this.mScheme);
                if ("yikaobang.app".equals(this.mScheme)) {
                    this.mUri = intent2.getData();
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        hideNav();
        setContentView();
        try {
            CommonConfig commonConfig = CommonConfig.INSTANCE;
            commonConfig.setUser_Agent(AndroidBaseUtils.getDeviceName() + "");
            commonConfig.setApp_version(AndroidBaseUtils.getAPPVersionCode(this) + "");
        } catch (PackageManager.NameNotFoundException e6) {
            e6.printStackTrace();
        }
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.FIRSTSTART, true, getApplicationContext())) {
            showSelectPop();
        } else if (SharePreferencesUtils.readBooleanConfig(CommonParameter.agree, false, this)) {
            init();
        } else {
            showSelectPop();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ProjectApp.instance.removeActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
        if (EventBusConstant.EVENT_HX_PUSH_DATA.equals(event.getKey())) {
            this.mHxPushData = (String) event.getValueObj();
        }
    }

    public void onEventMainThread(String mOnEventMsg) {
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.mHXMsgBundle = getIntent().getExtras();
        String stringExtra = getIntent().getStringExtra(EventBusConstant.EVENT_HX_PUSH_DATA);
        this.mHxPushData = stringExtra;
        try {
            if (this.mHXMsgBundle != null) {
                goMain();
            } else {
                if (TextUtils.isEmpty(stringExtra) || this.mHxPushData.equals(StrPool.EMPTY_JSON) || !this.mHxPushData.contains(PushConstants.PUSH_TYPE)) {
                    return;
                }
                goMain();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            goMain();
        }
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        ProjectApp.isForeground = false;
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        getNotifyPermission();
        ProjectApp.isForeground = true;
    }

    public void setContentView() {
        setContentView(R.layout.activity_welcome);
    }
}
