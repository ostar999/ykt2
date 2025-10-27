package com.tencent.smtt.sdk;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.sdk.TbsMediaPlayer;

/* loaded from: classes6.dex */
class p {

    /* renamed from: a, reason: collision with root package name */
    private DexLoader f21260a;

    /* renamed from: b, reason: collision with root package name */
    private Object f21261b;

    public p(DexLoader dexLoader, Context context) {
        this.f21261b = null;
        this.f21260a = dexLoader;
        this.f21261b = dexLoader.newInstance("com.tencent.tbs.player.TbsMediaPlayerProxy", new Class[]{Context.class}, context);
    }

    public void a(float f2) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "setVolume", new Class[]{Float.TYPE}, Float.valueOf(f2));
    }

    public void a(int i2) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "subtitle", new Class[]{Integer.TYPE}, Integer.valueOf(i2));
    }

    public void a(long j2) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "seek", new Class[]{Long.TYPE}, Long.valueOf(j2));
    }

    public void a(SurfaceTexture surfaceTexture) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "setSurfaceTexture", new Class[]{SurfaceTexture.class}, surfaceTexture);
    }

    public void a(TbsMediaPlayer.TbsMediaPlayerListener tbsMediaPlayerListener) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "setPlayerListener", new Class[]{Object.class}, tbsMediaPlayerListener);
    }

    public void a(String str, Bundle bundle) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "startPlay", new Class[]{String.class, Bundle.class}, str, bundle);
    }

    public boolean a() {
        return this.f21261b != null;
    }

    public float b() {
        Float f2 = (Float) this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "getVolume", new Class[0], new Object[0]);
        if (f2 != null) {
            return f2.floatValue();
        }
        return 0.0f;
    }

    public void b(int i2) {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "audio", new Class[]{Integer.TYPE}, Integer.valueOf(i2));
    }

    public void c() {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "pause", new Class[0], new Object[0]);
    }

    public void d() {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "play", new Class[0], new Object[0]);
    }

    public void e() {
        this.f21260a.invokeMethod(this.f21261b, "com.tencent.tbs.player.TbsMediaPlayerProxy", "close", new Class[0], new Object[0]);
    }
}
