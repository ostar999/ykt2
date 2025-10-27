package com.tencent.liteav.videodecoder;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class g extends f {

    /* renamed from: a, reason: collision with root package name */
    private final Object f20167a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private Handler f20168b;

    /* renamed from: c, reason: collision with root package name */
    private h f20169c;

    /* renamed from: d, reason: collision with root package name */
    private WeakReference<com.tencent.liteav.basic.b.b> f20170d;

    /* renamed from: e, reason: collision with root package name */
    private Surface f20171e;

    /* renamed from: f, reason: collision with root package name */
    private JSONArray f20172f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20173g;

    /* renamed from: h, reason: collision with root package name */
    private int f20174h;

    /* renamed from: i, reason: collision with root package name */
    private int f20175i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f20176j;

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public int GetDecodeCost() {
        return super.GetDecodeCost();
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public int config(final Surface surface) {
        this.f20171e = surface;
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.5
            @Override // java.lang.Runnable
            public void run() {
                g.super.config(surface);
            }
        });
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public void decode(final TXSNALPacket tXSNALPacket) {
        this.f20176j = a(tXSNALPacket);
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.2
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                g.super.decode(tXSNALPacket);
            }
        });
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public void enableLimitDecCache(final boolean z2) {
        this.f20173g = z2;
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.7
            @Override // java.lang.Runnable
            public void run() {
                g.super.enableLimitDecCache(z2);
            }
        });
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public void setListener(final h hVar) {
        this.f20169c = hVar;
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.3
            @Override // java.lang.Runnable
            public void run() {
                g.super.setListener(hVar);
            }
        });
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public void setNotifyListener(final WeakReference<com.tencent.liteav.basic.b.b> weakReference) {
        this.f20170d = weakReference;
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.4
            @Override // java.lang.Runnable
            public void run() {
                g.super.setNotifyListener(weakReference);
            }
        });
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public int start(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final boolean z2, final boolean z3) {
        this.f20176j = z3;
        a();
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.9
            @Override // java.lang.Runnable
            public void run() {
                g.this.a("start decoder.");
                g gVar = g.this;
                g.super.setListener(gVar.f20169c);
                g gVar2 = g.this;
                g.super.setNotifyListener(gVar2.f20170d);
                g gVar3 = g.this;
                g.super.config(gVar3.f20171e);
                g gVar4 = g.this;
                g.super.a(gVar4.f20172f);
                g gVar5 = g.this;
                g.super.enableLimitDecCache(gVar5.f20173g);
                g gVar6 = g.this;
                g.super.a(gVar6.f20174h, g.this.f20175i);
                g.super.start(byteBuffer, byteBuffer2, z2, z3);
                g.this.a("start decoder finish.");
            }
        });
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.f, com.tencent.liteav.videodecoder.b
    public void stop() {
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.10
            @Override // java.lang.Runnable
            public void run() {
                g.this.a("stop decoder.");
                g.super.stop();
                g.this.a("stop decoder finish.");
            }
        });
        b();
    }

    private void b() {
        synchronized (this.f20167a) {
            final Handler handler = this.f20168b;
            if (handler == null) {
                a("stop decoder thread failed. already stop.");
                return;
            }
            this.f20168b = null;
            handler.post(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.1
                @Override // java.lang.Runnable
                public void run() {
                    handler.getLooper().quit();
                }
            });
            a("stop decoder thread success.");
        }
    }

    private void a() {
        synchronized (this.f20167a) {
            if (this.f20168b != null) {
                a("start decoder thread failed. already start.");
                return;
            }
            HandlerThread handlerThread = new HandlerThread("video_hw_decoder");
            handlerThread.start();
            this.f20168b = new Handler(handlerThread.getLooper());
            a("start decoder thread success. id:" + handlerThread.getId());
        }
    }

    private void a(Runnable runnable) {
        synchronized (this.f20167a) {
            Handler handler = this.f20168b;
            if (handler == null) {
                return;
            }
            handler.post(runnable);
        }
    }

    @Override // com.tencent.liteav.videodecoder.f
    public void a(final JSONArray jSONArray) {
        this.f20172f = jSONArray;
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.6
            @Override // java.lang.Runnable
            public void run() {
                g.super.a(jSONArray);
            }
        });
    }

    @Override // com.tencent.liteav.videodecoder.f
    public void a(final int i2, final int i3) {
        this.f20174h = i2;
        this.f20175i = i3;
        a(new Runnable() { // from class: com.tencent.liteav.videodecoder.g.8
            @Override // java.lang.Runnable
            public void run() {
                g.super.a(i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        TXCLog.i("TXCVideoMediaCodecWrapper", "decoder(" + hashCode() + ") " + str);
    }
}
