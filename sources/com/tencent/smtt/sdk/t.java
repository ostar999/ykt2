package com.tencent.smtt.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import com.psychiatrygarden.utils.MimeTypes;
import com.tencent.smtt.export.external.DexLoader;

/* loaded from: classes6.dex */
class t extends FrameLayout implements MediaPlayer.OnErrorListener {

    /* renamed from: a, reason: collision with root package name */
    private Object f21293a;

    /* renamed from: b, reason: collision with root package name */
    private v f21294b;

    /* renamed from: c, reason: collision with root package name */
    private VideoView f21295c;

    /* renamed from: d, reason: collision with root package name */
    private Context f21296d;

    /* renamed from: e, reason: collision with root package name */
    private String f21297e;

    public t(Context context) {
        super(context.getApplicationContext());
        this.f21296d = context;
    }

    private void b(Bundle bundle, Object obj) {
        boolean zA;
        a();
        if (b()) {
            bundle.putInt("callMode", bundle.getInt("callMode"));
            zA = this.f21294b.a(this.f21293a, bundle, this, obj);
        } else {
            zA = false;
        }
        if (zA) {
            return;
        }
        VideoView videoView = this.f21295c;
        if (videoView != null) {
            videoView.stopPlayback();
        }
        if (this.f21295c == null) {
            this.f21295c = new VideoView(getContext());
        }
        String string = bundle.getString("videoUrl");
        this.f21297e = string;
        this.f21295c.setVideoURI(Uri.parse(string));
        this.f21295c.setOnErrorListener(this);
        Intent intent = new Intent("com.tencent.smtt.tbs.video.PLAY");
        intent.addFlags(268435456);
        Context applicationContext = getContext().getApplicationContext();
        intent.setPackage(applicationContext.getPackageName());
        applicationContext.startActivity(intent);
    }

    public void a() {
        setBackgroundColor(-16777216);
        if (this.f21294b == null) {
            g.a(true).a(getContext().getApplicationContext(), false, false);
            u uVarA = g.a(true).a();
            DexLoader dexLoaderC = uVarA != null ? uVarA.c() : null;
            if (dexLoaderC != null && QbSdk.canLoadVideo(getContext())) {
                this.f21294b = new v(dexLoaderC);
            }
        }
        v vVar = this.f21294b;
        if (vVar == null || this.f21293a != null) {
            return;
        }
        this.f21293a = vVar.a(getContext().getApplicationContext());
    }

    public void a(Activity activity) {
        VideoView videoView;
        if (b() || (videoView = this.f21295c) == null) {
            return;
        }
        if (videoView.getParent() == null) {
            Window window = activity.getWindow();
            FrameLayout frameLayout = (FrameLayout) window.getDecorView();
            window.addFlags(1024);
            window.addFlags(128);
            frameLayout.setBackgroundColor(-16777216);
            MediaController mediaController = new MediaController(activity);
            mediaController.setMediaPlayer(this.f21295c);
            this.f21295c.setMediaController(mediaController);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            layoutParams.gravity = 17;
            frameLayout.addView(this.f21295c, layoutParams);
        }
        this.f21295c.start();
    }

    public void a(Activity activity, int i2) {
        VideoView videoView;
        VideoView videoView2;
        if (i2 == 3 && !b() && (videoView2 = this.f21295c) != null) {
            videoView2.pause();
        }
        if (i2 == 4) {
            this.f21296d = null;
            if (!b() && (videoView = this.f21295c) != null) {
                videoView.stopPlayback();
                this.f21295c = null;
            }
        }
        if (i2 == 2 && !b()) {
            this.f21296d = activity;
            a(activity);
        }
        if (b()) {
            this.f21294b.a(this.f21293a, activity, i2);
        }
    }

    public void a(Bundle bundle, Object obj) {
        b(bundle, obj);
    }

    public boolean b() {
        return (this.f21294b == null || this.f21293a == null) ? false : true;
    }

    public void c() {
        if (b()) {
            this.f21294b.a(this.f21293a);
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
        try {
            Context context = this.f21296d;
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            Context context2 = getContext();
            if (context2 != null) {
                Toast.makeText(context2, "播放失败，请选择其它播放器播放", 1).show();
                Context applicationContext = context2.getApplicationContext();
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                intent.setDataAndType(Uri.parse(this.f21297e), MimeTypes.VIDEO_ALL);
                applicationContext.startActivity(intent);
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
