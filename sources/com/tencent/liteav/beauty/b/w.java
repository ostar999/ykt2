package com.tencent.liteav.beauty.b;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.IOException;

/* loaded from: classes6.dex */
public class w {

    /* renamed from: b, reason: collision with root package name */
    private static final String f18982b = "w";

    /* renamed from: a, reason: collision with root package name */
    SurfaceTexture.OnFrameAvailableListener f18983a;

    /* renamed from: c, reason: collision with root package name */
    private SurfaceTexture f18984c;

    /* renamed from: f, reason: collision with root package name */
    private MediaExtractor f18987f;

    /* renamed from: g, reason: collision with root package name */
    private AssetFileDescriptor f18988g;

    /* renamed from: l, reason: collision with root package name */
    private long f18993l;

    /* renamed from: m, reason: collision with root package name */
    private MediaCodec f18994m;

    /* renamed from: o, reason: collision with root package name */
    private boolean f18996o;

    /* renamed from: p, reason: collision with root package name */
    private Handler f18997p;

    /* renamed from: d, reason: collision with root package name */
    private int f18985d = -1;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18986e = false;

    /* renamed from: h, reason: collision with root package name */
    private int f18989h = -1;

    /* renamed from: i, reason: collision with root package name */
    private int f18990i = -1;

    /* renamed from: j, reason: collision with root package name */
    private int f18991j = -1;

    /* renamed from: k, reason: collision with root package name */
    private int f18992k = -1;

    /* renamed from: n, reason: collision with root package name */
    private boolean f18995n = false;

    /* renamed from: q, reason: collision with root package name */
    private Object f18998q = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    public void c() throws IOException {
        b();
        this.f18983a = null;
        this.f18993l = 0L;
        this.f18996o = false;
        SurfaceTexture surfaceTexture = this.f18984c;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.f18984c = null;
        }
        synchronized (this.f18998q) {
            Handler handler = this.f18997p;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                this.f18997p.getLooper().quit();
                this.f18997p = null;
                this.f18998q.notify();
            }
        }
        AssetFileDescriptor assetFileDescriptor = this.f18988g;
        if (assetFileDescriptor != null) {
            try {
                assetFileDescriptor.close();
            } catch (Exception unused) {
            }
            this.f18988g = null;
        }
    }

    private void b() {
        if (this.f18986e) {
            this.f18986e = false;
            MediaExtractor mediaExtractor = this.f18987f;
            if (mediaExtractor != null) {
                mediaExtractor.release();
                this.f18987f = null;
            }
            try {
                try {
                    this.f18994m.stop();
                } catch (Exception e2) {
                    try {
                        TXCLog.e(f18982b, "stop decoder Exception: " + e2.toString());
                        try {
                            this.f18994m.release();
                        } catch (Exception e3) {
                            TXCLog.e(f18982b, "release decoder exception: " + e3.toString());
                        }
                    } finally {
                    }
                }
                try {
                    try {
                        this.f18994m.release();
                    } catch (Exception e4) {
                        TXCLog.e(f18982b, "release decoder exception: " + e4.toString());
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    try {
                        this.f18994m.release();
                    } finally {
                    }
                } catch (Exception e5) {
                    TXCLog.e(f18982b, "release decoder exception: " + e5.toString());
                }
                throw th;
            }
        }
    }

    public synchronized void a() {
        synchronized (this.f18998q) {
            if (this.f18997p != null) {
                if (Looper.myLooper() == this.f18997p.getLooper()) {
                    c();
                } else {
                    Runnable runnable = new Runnable() { // from class: com.tencent.liteav.beauty.b.w.1
                        @Override // java.lang.Runnable
                        public void run() {
                            synchronized (w.this.f18998q) {
                                w.this.c();
                                w.this.f18998q.notify();
                            }
                        }
                    };
                    this.f18997p.removeCallbacksAndMessages(null);
                    this.f18997p.post(runnable);
                    this.f18997p.getLooper().quitSafely();
                    while (true) {
                        try {
                            this.f18998q.wait();
                            break;
                        } catch (InterruptedException unused) {
                        }
                    }
                }
            }
        }
    }
}
