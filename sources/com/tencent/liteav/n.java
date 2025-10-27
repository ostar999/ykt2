package com.tencent.liteav;

import android.content.Context;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public abstract class n {

    /* renamed from: b, reason: collision with root package name */
    protected Context f19453b;

    /* renamed from: d, reason: collision with root package name */
    protected WeakReference<com.tencent.liteav.basic.b.b> f19455d;

    /* renamed from: a, reason: collision with root package name */
    protected h f19452a = null;

    /* renamed from: c, reason: collision with root package name */
    protected TXCloudVideoView f19454c = null;

    public n(Context context) {
        this.f19453b = null;
        if (context != null) {
            this.f19453b = context.getApplicationContext();
        }
    }

    public int a(String str) {
        return -1;
    }

    public abstract int a(String str, int i2);

    public abstract int a(boolean z2);

    public abstract void a(int i2);

    public void a(int i2, int i3) {
    }

    public void a(Surface surface) {
    }

    public abstract void a(com.tencent.liteav.basic.opengl.p pVar);

    public void a(h hVar) {
        this.f19452a = hVar;
        if (hVar == null) {
            this.f19452a = new h();
        }
    }

    public void a(o oVar, com.tencent.liteav.basic.enums.b bVar, Object obj) {
    }

    public void a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
    }

    public void a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
    }

    public abstract void a(boolean z2, int i2);

    public void b() {
        TXCLog.w("TXIPlayer", "resume not support");
    }

    public abstract void b(int i2);

    public abstract void b(boolean z2);

    public abstract void c(int i2);

    public abstract void c(boolean z2);

    public abstract boolean c();

    public abstract int d();

    public abstract void d(int i2);

    public void d(boolean z2) {
        TXCLog.w("TXIPlayer", "autoPlay not implement");
    }

    public abstract int e(int i2);

    public boolean e() {
        return false;
    }

    public long f() {
        return 0L;
    }

    public void f(int i2) {
        TXCLog.w("TXIPlayer", "seek not support");
    }

    public void g() {
    }

    public abstract int i();

    public h j() {
        return this.f19452a;
    }

    public void a() {
        TXCLog.w("TXIPlayer", "pause not support");
    }

    public void a(TXCloudVideoView tXCloudVideoView) {
        this.f19454c = tXCloudVideoView;
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.f19455d = new WeakReference<>(bVar);
    }

    public void a(float f2) {
        TXCLog.w("TXIPlayer", "rate not implement");
    }
}
