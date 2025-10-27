package com.psychiatrygarden.activity.purchase;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;

/* loaded from: classes5.dex */
public class VideoFullActivity extends FragmentActivity {
    public boolean isDestroyed;
    public String vid;
    public CustomAliPlayerView videovidew;
    public String Mp4Url = "";
    public String videotitle = "";
    public long mCurrentPosition = 0;
    public String tag = "vip";

    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triggerReceiver);
    }

    public void changeDesTroy() {
        if (this.isDestroyed || this.videovidew == null) {
            return;
        }
        if (!TextUtils.isEmpty(this.Mp4Url)) {
            EventBus.getDefault().post(new EventBusMessage(this.tag, Long.valueOf(this.videovidew.mAliyunVodPlayerView.getmCurrentPosition())));
        }
        this.videovidew.onDestory();
        this.isDestroyed = true;
        this.videovidew = null;
    }

    public void init() {
        this.tag = getIntent().getExtras().getString("tag");
        this.videotitle = getIntent().getExtras().getString("videotitle");
        this.mCurrentPosition = getIntent().getLongExtra("mCurrentPosition", 0L) / 1000;
        this.vid = getIntent().getExtras().getString("vid");
        this.Mp4Url = getIntent().getExtras().getString("Mp4Url");
        CustomAliPlayerView customAliPlayerView = (CustomAliPlayerView) findViewById(R.id.videovidew);
        this.videovidew = customAliPlayerView;
        customAliPlayerView.setFullForbbtie(true);
        if (TextUtils.isEmpty(this.Mp4Url)) {
            this.videovidew.initView(this, this.vid, UserConfig.isCanPlayBy4g(this));
            this.videovidew.fullorori(false);
            CommonUtil.getCourseDownAk(this.vid, this.videovidew, true);
            return;
        }
        this.videovidew.setMp4Url(this.Mp4Url);
        this.videovidew.setFullAttrs(true);
        this.videovidew.setVideoTitle(this.videotitle + "");
        this.videovidew.setSeeDuration(this.mCurrentPosition + "");
        this.videovidew.initView(this, "", UserConfig.isCanPlayBy4g(this));
        this.videovidew.fullorori(false);
        this.videovidew.setMp4UrlSource();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_full_land_screen);
        hideNav();
        init();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        changeDesTroy();
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            changeDesTroy();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CustomAliPlayerView customAliPlayerView = this.videovidew;
        if (customAliPlayerView != null) {
            customAliPlayerView.onResume();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        CustomAliPlayerView customAliPlayerView = this.videovidew;
        if (customAliPlayerView != null) {
            customAliPlayerView.onPause();
        }
    }
}
