package com.hyphenate.easeui.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.player.EasyVideoCallback;
import com.hyphenate.easeui.player.EasyVideoPlayer;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class EaseShowLocalVideoActivity extends EaseBaseActivity implements EasyVideoCallback {
    private EasyVideoPlayer evpPlayer;
    private Uri uri;

    public static void actionStart(Context context, String str) {
        Intent intent = new Intent(context, (Class<?>) EaseShowLocalVideoActivity.class);
        intent.putExtra("path", str);
        context.startActivity(intent);
    }

    public void initData() throws IllegalStateException, SecurityException, IllegalArgumentException {
        this.evpPlayer.setAutoPlay(true);
        Uri uri = this.uri;
        if (uri != null) {
            this.evpPlayer.setSource(uri);
        }
    }

    public void initIntent(Intent intent) {
        String stringExtra = intent.getStringExtra("path");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.uri = Uri.parse(stringExtra);
        }
        if (this.uri == null) {
            finish();
        }
    }

    public void initListener() {
        this.evpPlayer.setCallback(this);
    }

    public void initView() {
        this.evpPlayer = (EasyVideoPlayer) findViewById(R.id.evp_player);
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onBuffering(int i2) {
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onClickVideoFrame(EasyVideoPlayer easyVideoPlayer) {
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onCompletion(EasyVideoPlayer easyVideoPlayer) {
        finish();
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws IllegalStateException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_show_local_video);
        setFitSystemForTheme(false, R.color.transparent, false);
        initIntent(getIntent());
        initView();
        initListener();
        initData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EasyVideoPlayer easyVideoPlayer = this.evpPlayer;
        if (easyVideoPlayer != null) {
            easyVideoPlayer.release();
            this.evpPlayer = null;
        }
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onError(EasyVideoPlayer easyVideoPlayer, Exception exc) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(Intent intent) throws IllegalStateException, SecurityException, IllegalArgumentException {
        super.onNewIntent(intent);
        initIntent(intent);
        Uri uri = this.uri;
        if (uri != null) {
            this.evpPlayer.setSource(uri);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() throws IllegalStateException {
        super.onPause();
        EasyVideoPlayer easyVideoPlayer = this.evpPlayer;
        if (easyVideoPlayer != null) {
            easyVideoPlayer.pause();
        }
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onPaused(EasyVideoPlayer easyVideoPlayer) {
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onPrepared(EasyVideoPlayer easyVideoPlayer) {
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onPreparing(EasyVideoPlayer easyVideoPlayer) {
    }

    @Override // com.hyphenate.easeui.player.EasyVideoCallback
    public void onStarted(EasyVideoPlayer easyVideoPlayer) {
    }
}
