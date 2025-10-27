package com.tencent.liteav.screencapture;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.f;
import com.tencent.liteav.basic.util.h;
import com.tencent.liteav.basic.util.j;
import com.tencent.rtmp.video.TXScreenCapture;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static volatile c f19913a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f19914b;

    /* renamed from: f, reason: collision with root package name */
    private MediaProjection f19918f;

    /* renamed from: g, reason: collision with root package name */
    private j f19919g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f19920h;

    /* renamed from: d, reason: collision with root package name */
    private final Map<Surface, a> f19916d = new HashMap();

    /* renamed from: e, reason: collision with root package name */
    private boolean f19917e = false;

    /* renamed from: i, reason: collision with root package name */
    private MediaProjection.Callback f19921i = new MediaProjection.Callback() { // from class: com.tencent.liteav.screencapture.c.1
        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            TXCLog.e("VirtualDisplayManager", "MediaProjection session is no longer valid");
            HashMap map = new HashMap(c.this.f19916d);
            c.this.f19916d.clear();
            for (a aVar : map.values()) {
                b bVar = aVar.f19929d;
                if (bVar != null) {
                    if (aVar.f19930e != null) {
                        bVar.a();
                    } else {
                        bVar.a(false, false);
                    }
                }
            }
            c.this.a(false);
        }
    };

    /* renamed from: j, reason: collision with root package name */
    private j.a f19922j = new j.a() { // from class: com.tencent.liteav.screencapture.c.2
        @Override // com.tencent.liteav.basic.util.j.a
        public void onTimeout() {
            c cVar = c.this;
            boolean zB = cVar.b(cVar.f19914b);
            if (c.this.f19920h == zB) {
                return;
            }
            c.this.f19920h = zB;
            Iterator it = c.this.f19916d.values().iterator();
            while (it.hasNext()) {
                b bVar = ((a) it.next()).f19929d;
                if (bVar != null) {
                    bVar.a(zB);
                }
            }
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private final Handler f19915c = new f(Looper.getMainLooper());

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public Surface f19926a;

        /* renamed from: b, reason: collision with root package name */
        public int f19927b;

        /* renamed from: c, reason: collision with root package name */
        public int f19928c;

        /* renamed from: d, reason: collision with root package name */
        public b f19929d;

        /* renamed from: e, reason: collision with root package name */
        public VirtualDisplay f19930e;

        private a() {
        }
    }

    public interface b {
        void a();

        void a(boolean z2);

        void a(boolean z2, boolean z3);
    }

    public c(Context context) {
        this.f19914b = context.getApplicationContext();
        this.f19920h = b(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(Context context) {
        int iG = h.g(context);
        return iG == 0 || iG == 2;
    }

    public static c a(Context context) {
        if (f19913a == null) {
            synchronized (c.class) {
                if (f19913a == null) {
                    f19913a = new c(context);
                }
            }
        }
        return f19913a;
    }

    public void a(Surface surface, int i2, int i3, b bVar) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Must call this method in main thread!");
        }
        if (surface == null) {
            TXCLog.e("VirtualDisplayManager", "surface is null!");
            bVar.a(false, false);
            return;
        }
        a aVar = new a();
        aVar.f19926a = surface;
        aVar.f19927b = i2;
        aVar.f19928c = i3;
        aVar.f19929d = bVar;
        aVar.f19930e = null;
        this.f19916d.put(surface, aVar);
        if (this.f19918f == null) {
            if (this.f19917e) {
                return;
            }
            this.f19917e = true;
            Intent intent = new Intent(this.f19914b, (Class<?>) TXScreenCapture.TXScreenCaptureAssistantActivity.class);
            intent.addFlags(268435456);
            this.f19914b.startActivity(intent);
            return;
        }
        a();
    }

    public void a(Surface surface) {
        VirtualDisplay virtualDisplay;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Must call this at main thread!");
        }
        if (surface == null) {
            return;
        }
        a aVarRemove = this.f19916d.remove(surface);
        if (aVarRemove != null && (virtualDisplay = aVarRemove.f19930e) != null) {
            virtualDisplay.release();
            TXCLog.i("VirtualDisplayManager", "VirtualDisplay released, " + aVarRemove.f19930e);
        }
        a(true);
    }

    private void a() {
        for (a aVar : this.f19916d.values()) {
            if (aVar.f19930e == null) {
                aVar.f19930e = this.f19918f.createVirtualDisplay("TXCScreenCapture", aVar.f19927b, aVar.f19928c, 1, 1, aVar.f19926a, null, null);
                TXCLog.i("VirtualDisplayManager", "create VirtualDisplay " + aVar.f19930e);
                b bVar = aVar.f19929d;
                if (bVar != null) {
                    bVar.a(true, false);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        if (this.f19916d.isEmpty()) {
            if (z2) {
                this.f19915c.postDelayed(new Runnable() { // from class: com.tencent.liteav.screencapture.c.3
                    @Override // java.lang.Runnable
                    public void run() {
                        c.this.a(false);
                    }
                }, TimeUnit.SECONDS.toMillis(1L));
                return;
            }
            TXCLog.i("VirtualDisplayManager", "stop media projection session " + this.f19918f);
            MediaProjection mediaProjection = this.f19918f;
            if (mediaProjection != null) {
                mediaProjection.unregisterCallback(this.f19921i);
                this.f19918f.stop();
                this.f19918f = null;
            }
            j jVar = this.f19919g;
            if (jVar != null) {
                jVar.a();
                this.f19919g = null;
            }
        }
    }

    public void a(MediaProjection mediaProjection) {
        this.f19917e = false;
        if (mediaProjection == null) {
            HashMap map = new HashMap(this.f19916d);
            this.f19916d.clear();
            Iterator it = map.values().iterator();
            while (it.hasNext()) {
                b bVar = ((a) it.next()).f19929d;
                if (bVar != null) {
                    bVar.a(false, true);
                }
            }
            return;
        }
        TXCLog.i("VirtualDisplayManager", "Got session " + mediaProjection);
        this.f19918f = mediaProjection;
        mediaProjection.registerCallback(this.f19921i, this.f19915c);
        a();
        j jVar = new j(Looper.getMainLooper(), this.f19922j);
        this.f19919g = jVar;
        jVar.a(50, 50);
        a(true);
    }
}
