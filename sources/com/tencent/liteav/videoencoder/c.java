package com.tencent.liteav.videoencoder;

import android.opengl.GLES20;
import android.os.Bundle;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.h;
import com.tencent.liteav.basic.util.i;
import com.tencent.liteav.beauty.b.k;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class c extends com.tencent.liteav.basic.module.a {

    /* renamed from: s, reason: collision with root package name */
    private static Integer f20247s = 1;

    /* renamed from: v, reason: collision with root package name */
    private static final String f20248v = c.class.getSimpleName();

    /* renamed from: w, reason: collision with root package name */
    private static int f20249w = 0;

    /* renamed from: f, reason: collision with root package name */
    private int f20255f;

    /* renamed from: k, reason: collision with root package name */
    private TXSVideoEncoderParam f20260k;

    /* renamed from: q, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.b f20266q;

    /* renamed from: r, reason: collision with root package name */
    private i f20267r;

    /* renamed from: t, reason: collision with root package name */
    private boolean f20268t;

    /* renamed from: u, reason: collision with root package name */
    private k f20269u;

    /* renamed from: a, reason: collision with root package name */
    private final com.tencent.liteav.basic.util.c f20250a = new com.tencent.liteav.basic.util.c("video-encoder", (int) TimeUnit.SECONDS.toMillis(5));

    /* renamed from: b, reason: collision with root package name */
    private e f20251b = null;

    /* renamed from: c, reason: collision with root package name */
    private f f20252c = null;

    /* renamed from: d, reason: collision with root package name */
    private WeakReference<com.tencent.liteav.basic.b.b> f20253d = null;

    /* renamed from: e, reason: collision with root package name */
    private int f20254e = 0;

    /* renamed from: g, reason: collision with root package name */
    private a f20256g = a.HW_ENCODER_H264;

    /* renamed from: h, reason: collision with root package name */
    private Timer f20257h = null;

    /* renamed from: i, reason: collision with root package name */
    private TimerTask f20258i = null;

    /* renamed from: j, reason: collision with root package name */
    private LinkedList<Runnable> f20259j = new LinkedList<>();

    /* renamed from: l, reason: collision with root package name */
    private float f20261l = 0.0f;

    /* renamed from: m, reason: collision with root package name */
    private float f20262m = 0.0f;

    /* renamed from: n, reason: collision with root package name */
    private float f20263n = 0.0f;

    /* renamed from: o, reason: collision with root package name */
    private int f20264o = 0;

    /* renamed from: p, reason: collision with root package name */
    private int f20265p = 0;

    public enum a {
        HW_ENCODER_H264(1),
        SW_ENCODER_H264(2),
        HW_ENCODER_H265(3),
        SW_ENCODER_H265(4);

        private final int value;

        a(int i2) {
            this.value = i2;
        }

        public int a() {
            return this.value;
        }
    }

    public static class b extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<c> f20292a;

        public b(c cVar) {
            this.f20292a = new WeakReference<>(cVar);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            c cVar;
            WeakReference<c> weakReference = this.f20292a;
            if (weakReference == null || (cVar = weakReference.get()) == null) {
                return;
            }
            if (cVar.f20264o < cVar.f20265p) {
                int[] iArrA = h.a();
                c.k(cVar);
                cVar.f20261l += iArrA[0] / 10;
                cVar.f20262m += iArrA[1] / 10;
                cVar.f20263n = (float) (cVar.f20263n + ((cVar.c() * 100.0d) / cVar.f20260k.fps));
                return;
            }
            if (com.tencent.liteav.basic.c.c.a().a(cVar.f20261l / cVar.f20265p, cVar.f20262m / cVar.f20265p, cVar.f20263n / cVar.f20265p) && com.tencent.liteav.basic.c.c.a().c() != 0) {
                Monitor.a(2, "VideoEncoder: Insufficient performance, switching software encoding to hardware encoding [appCPU:" + cVar.f20261l + "][sysCPU:" + cVar.f20262m + "][fps:" + cVar.f20263n + "][checkCount:" + cVar.f20265p + StrPool.BRACKET_END, "", 0);
                cVar.i();
            }
            cVar.h();
        }
    }

    public c(int i2) {
        this.f20255f = 2;
        this.f20255f = i2;
    }

    public static /* synthetic */ int k(c cVar) {
        int i2 = cVar.f20264o + 1;
        cVar.f20264o = i2;
        return i2;
    }

    @Override // com.tencent.liteav.basic.module.a
    public void setID(String str) {
        super.setID(str);
        e eVar = this.f20251b;
        if (eVar != null) {
            eVar.setID(str);
        }
        setStatusValue(4007, Long.valueOf(this.f20256g.a()));
    }

    private void g() {
        if (this.f20258i == null) {
            this.f20258i = new b(this);
        }
        Timer timer = new Timer();
        this.f20257h = timer;
        timer.schedule(this.f20258i, 1000L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        Timer timer = this.f20257h;
        if (timer != null) {
            timer.cancel();
            this.f20257h = null;
        }
        if (this.f20258i != null) {
            this.f20258i = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        a(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.7
            @Override // java.lang.Runnable
            public void run() {
                c.this.a(1107, "Switches from software encoding to hardware encoding");
                if (c.this.f20251b != null) {
                    c.this.f20251b.setListener(null);
                    c.this.f20251b.stop();
                }
                c.this.f20251b = new com.tencent.liteav.videoencoder.b();
                TXCLog.w("TXCVideoEncoder", "create hw encoder");
                c.this.f20256g = a.HW_ENCODER_H264;
                if (c.this.f20260k.isH265EncoderEnabled) {
                    TXCLog.w(c.f20248v, "can not switch from 265 sw to hw!");
                    c.this.f20260k.isH265EncoderEnabled = false;
                }
                c.this.setStatusValue(4007, Long.valueOf(r0.f20256g.a()));
                c.this.f20251b.start(c.this.f20260k);
                if (c.this.f20252c != null) {
                    c.this.f20251b.setListener(c.this.f20252c);
                }
                if (c.this.f20254e != 0) {
                    c.this.f20251b.setBitrate(c.this.f20254e);
                }
                c.this.f20251b.setID(c.this.getID());
            }
        });
        TXCLog.w("TXCVideoEncoder", "switchSWToHW");
    }

    public boolean d(int i2) {
        e eVar = this.f20251b;
        if (eVar == null) {
            return false;
        }
        eVar.setEncodeIdrFpsFromQos(i2);
        return true;
    }

    public void e(final int i2) {
        a(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.6
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.f20251b != null) {
                    c.this.f20251b.enableNearestRPS(i2);
                }
            }
        });
    }

    public void b(boolean z2) {
        e eVar = this.f20251b;
        if (eVar != null) {
            eVar.setGLFinishedTextureNeed(z2);
        }
    }

    public void c(int i2) {
        this.f20254e = i2;
        a(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.5
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.f20251b != null) {
                    c.this.f20251b.setBitrate(c.this.f20254e);
                }
            }
        });
    }

    public int e() {
        e eVar = this.f20251b;
        if (eVar != null) {
            return eVar.getEncodeCost();
        }
        return 0;
    }

    public long d() {
        e eVar = this.f20251b;
        if (eVar != null) {
            return eVar.getRealBitrate();
        }
        return 0L;
    }

    public boolean b(int i2) {
        e eVar = this.f20251b;
        if (eVar == null) {
            return false;
        }
        eVar.setFPS(i2);
        return true;
    }

    public double c() {
        e eVar = this.f20251b;
        if (eVar != null) {
            return eVar.getRealFPS();
        }
        return 0.0d;
    }

    public void b() {
        e eVar = this.f20251b;
        if (eVar != null) {
            eVar.restartIDR();
        }
    }

    public int a(TXSVideoEncoderParam tXSVideoEncoderParam) {
        int iStart;
        int iC = tXSVideoEncoderParam.enableBlackList ? com.tencent.liteav.basic.c.c.a().c() : 2;
        StringBuilder sb = new StringBuilder("Enables ");
        if (tXSVideoEncoderParam.isH265EncoderEnabled && d.a(tXSVideoEncoderParam.width, tXSVideoEncoderParam.height, tXSVideoEncoderParam.fps)) {
            sb.append("H265 ");
            this.f20255f = 1;
        } else {
            sb.append("H264 ");
            if (tXSVideoEncoderParam.isH265EncoderEnabled) {
                a(-2311, "encode not support hevc,change to 264");
                Monitor.a(2, String.format(Locale.getDefault(), "VideoEncoder: hevc hardware encoder not support, switch to 264 encoder. %s, %d", TXCCommonUtil.getDeviceInfo(), Integer.valueOf(d.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20) ? 1 : 0)), "", 0);
            }
            tXSVideoEncoderParam.isH265EncoderEnabled = false;
        }
        int i2 = this.f20255f;
        if (i2 == 1 && iC != 0) {
            this.f20251b = new com.tencent.liteav.videoencoder.b();
            if (tXSVideoEncoderParam.isH265EncoderEnabled) {
                this.f20256g = a.HW_ENCODER_H265;
            } else {
                this.f20256g = a.HW_ENCODER_H264;
            }
            sb.append("hardware encoding");
            a(1008, sb.toString(), this.f20256g.a(), tXSVideoEncoderParam.streamType);
        } else if (i2 == 3 && tXSVideoEncoderParam.width == 720 && tXSVideoEncoderParam.height == 1280 && iC != 0) {
            this.f20251b = new com.tencent.liteav.videoencoder.b();
            if (tXSVideoEncoderParam.isH265EncoderEnabled) {
                this.f20256g = a.HW_ENCODER_H265;
            } else {
                this.f20256g = a.HW_ENCODER_H264;
            }
            sb.append("hardware encoding");
            a(1008, sb.toString(), this.f20256g.a(), tXSVideoEncoderParam.streamType);
        } else {
            this.f20251b = new TXCSWVideoEncoder();
            if (tXSVideoEncoderParam.isH265EncoderEnabled) {
                this.f20256g = a.SW_ENCODER_H265;
            } else {
                this.f20256g = a.SW_ENCODER_H264;
            }
            sb.append("software encoding");
            TXSVideoEncoderParam tXSVideoEncoderParam2 = this.f20260k;
            if (tXSVideoEncoderParam2 != null && tXSVideoEncoderParam2.isH265EncoderEnabled) {
                tXSVideoEncoderParam2.isH265EncoderEnabled = false;
                tXSVideoEncoderParam.isH265EncoderEnabled = false;
            }
            a(1008, sb.toString(), this.f20256g.a(), tXSVideoEncoderParam.streamType);
        }
        setStatusValue(4007, Long.valueOf(this.f20256g.a()));
        this.f20260k = tXSVideoEncoderParam;
        e eVar = this.f20251b;
        if (eVar != null) {
            f fVar = this.f20252c;
            if (fVar != null) {
                eVar.setListener(fVar);
            }
            int i3 = this.f20254e;
            if (i3 != 0) {
                this.f20251b.setBitrate(i3);
            }
            this.f20251b.setID(getID());
            iStart = this.f20251b.start(tXSVideoEncoderParam);
            if (iStart != 0) {
                TXCLog.i(f20248v, "start video encode " + sb.toString());
                return iStart;
            }
        } else {
            iStart = 10000002;
        }
        if (this.f20255f == 3) {
            this.f20261l = 0.0f;
            this.f20262m = 0.0f;
            this.f20263n = 0.0f;
            this.f20264o = 0;
            this.f20265p = com.tencent.liteav.basic.c.c.a().f();
            g();
        }
        return iStart;
    }

    public boolean b(int i2, int i3) {
        e eVar = this.f20251b;
        if (eVar == null) {
            return false;
        }
        this.f20254e = i2;
        eVar.setBitrateFromQos(i2, i3);
        return true;
    }

    public EGLContext a(final int i2, final int i3) {
        i iVar;
        if (!this.f20268t) {
            this.f20268t = true;
            synchronized (f20247s) {
                StringBuilder sb = new StringBuilder();
                sb.append("CVGLThread");
                Integer num = f20247s;
                f20247s = Integer.valueOf(num.intValue() + 1);
                sb.append(num);
                iVar = new i(sb.toString());
                this.f20267r = iVar;
            }
            final boolean[] zArr = new boolean[1];
            iVar.a(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.this.f20266q = com.tencent.liteav.basic.opengl.b.a(null, null, null, i2, i3);
                    zArr[0] = c.this.f20266q != null;
                }
            });
            if (zArr[0]) {
                return this.f20266q.d();
            }
            return null;
        }
        com.tencent.liteav.basic.opengl.b bVar = this.f20266q;
        if (bVar != null) {
            return bVar.d();
        }
        return null;
    }

    public void a(Runnable runnable) {
        synchronized (this.f20259j) {
            this.f20259j.add(runnable);
        }
    }

    private boolean a(Queue<Runnable> queue) {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            Runnable runnablePoll = queue.poll();
            if (runnablePoll == null) {
                return false;
            }
            runnablePoll.run();
            return true;
        }
    }

    public long a(final byte[] bArr, final int i2, final int i3, final int i4, final long j2) {
        if (this.f20266q == null) {
            return -1L;
        }
        this.f20267r.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.2
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.f20269u == null || c.this.f20269u.o() != i3 || c.this.f20269u.p() != i4) {
                    if (c.this.f20269u != null) {
                        c.this.f20269u.d();
                        c.this.f20269u = null;
                    }
                    c.this.f20269u = new k(i2);
                    if (!c.this.f20269u.a()) {
                        if (c.this.f20266q != null) {
                            c.this.f20266q.c();
                            c.this.f20266q = null;
                        }
                        c.this.f20269u = null;
                        return;
                    }
                    c.this.f20269u.a(true);
                    c.this.f20269u.a(i3, i4);
                }
                c.this.f20269u.a(bArr);
                GLES20.glViewport(0, 0, i3, i4);
                int iR = c.this.f20269u.r();
                GLES20.glFlush();
                c.this.a(iR, i3, i4, j2);
            }
        });
        return 0L;
    }

    public void a() {
        i iVar = this.f20267r;
        if (iVar != null) {
            final com.tencent.liteav.basic.opengl.b bVar = this.f20266q;
            iVar.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.3
                @Override // java.lang.Runnable
                public void run() {
                    c.this.f20259j.clear();
                    if (c.this.f20251b != null) {
                        c.this.f20251b.stop();
                    }
                    if (c.this.f20269u != null) {
                        c.this.f20269u.d();
                        c.this.f20269u = null;
                    }
                    com.tencent.liteav.basic.opengl.b bVar2 = bVar;
                    if (bVar2 != null) {
                        bVar2.c();
                    }
                }
            });
            this.f20267r = null;
            this.f20266q = null;
        } else {
            this.f20259j.clear();
            e eVar = this.f20251b;
            if (eVar != null) {
                eVar.stop();
            }
        }
        if (this.f20255f == 3) {
            this.f20261l = 0.0f;
            this.f20262m = 0.0f;
            this.f20263n = 0.0f;
            this.f20264o = 0;
            h();
        }
        this.f20252c = null;
        this.f20254e = 0;
    }

    public void a(int i2) {
        e eVar = this.f20251b;
        if (eVar != null) {
            eVar.setRotation(i2);
        }
    }

    public void a(boolean z2) {
        e eVar = this.f20251b;
        if (eVar != null) {
            eVar.setXMirror(z2);
        }
    }

    public long a(int i2, int i3, int i4, long j2) {
        this.f20250a.a();
        while (a(this.f20259j)) {
        }
        if (this.f20251b == null) {
            return 10000002L;
        }
        setStatusValue(4002, Long.valueOf(d()));
        setStatusValue(4001, this.f20260k.streamType, Double.valueOf(c()));
        a aVar = this.f20256g;
        if (aVar == a.HW_ENCODER_H264 || aVar == a.HW_ENCODER_H265) {
            setStatusValue(8002, this.f20260k.streamType, Integer.valueOf(e()));
        }
        return this.f20251b.pushVideoFrame(i2, i3, i4, j2);
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.f20253d = new WeakReference<>(bVar);
    }

    public void a(f fVar) {
        this.f20252c = fVar;
        a(new Runnable() { // from class: com.tencent.liteav.videoencoder.c.4
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.f20251b != null) {
                    c.this.f20251b.setListener(c.this.f20252c);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, String str) {
        com.tencent.liteav.basic.b.b bVar;
        WeakReference<com.tencent.liteav.basic.b.b> weakReference = this.f20253d;
        if (weakReference == null || (bVar = weakReference.get()) == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        bVar.onNotifyEvent(i2, bundle);
    }

    private void a(int i2, String str, int i3, int i4) {
        com.tencent.liteav.basic.b.b bVar;
        WeakReference<com.tencent.liteav.basic.b.b> weakReference = this.f20253d;
        if (weakReference == null || (bVar = weakReference.get()) == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        bundle.putInt("EVT_PARAM1", i3);
        bundle.putInt("EVT_STREAM_TYPE", i4);
        bVar.onNotifyEvent(i2, bundle);
    }
}
