package com.tencent.liteav.videoencoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.basic.opengl.l;
import com.tencent.liteav.basic.opengl.m;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.i;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes6.dex */
public class b extends e {
    private int J;
    private int K;
    private int L;
    private int M;
    private int N;
    private boolean O;
    private j Z;
    private ArrayList<Long> ac;

    /* renamed from: o, reason: collision with root package name */
    private boolean f20218o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f20219p;

    /* renamed from: u, reason: collision with root package name */
    private i f20224u;

    /* renamed from: z, reason: collision with root package name */
    private Object f20229z;

    /* renamed from: a, reason: collision with root package name */
    private int f20204a = 0;

    /* renamed from: b, reason: collision with root package name */
    private long f20205b = 0;

    /* renamed from: c, reason: collision with root package name */
    private double f20206c = 0.0d;

    /* renamed from: d, reason: collision with root package name */
    private long f20207d = 0;

    /* renamed from: e, reason: collision with root package name */
    private long f20208e = 0;

    /* renamed from: f, reason: collision with root package name */
    private int f20209f = 0;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20210g = false;

    /* renamed from: h, reason: collision with root package name */
    private boolean f20211h = true;

    /* renamed from: i, reason: collision with root package name */
    private long f20212i = 0;

    /* renamed from: j, reason: collision with root package name */
    private long f20213j = 0;

    /* renamed from: k, reason: collision with root package name */
    private long f20214k = 0;

    /* renamed from: l, reason: collision with root package name */
    private long f20215l = 0;

    /* renamed from: m, reason: collision with root package name */
    private long f20216m = 0;

    /* renamed from: n, reason: collision with root package name */
    private long f20217n = 0;

    /* renamed from: q, reason: collision with root package name */
    private long f20220q = 0;

    /* renamed from: r, reason: collision with root package name */
    private long f20221r = 0;

    /* renamed from: s, reason: collision with root package name */
    private MediaCodec f20222s = null;

    /* renamed from: t, reason: collision with root package name */
    private String f20223t = MimeTypes.VIDEO_H264;

    /* renamed from: v, reason: collision with root package name */
    private Runnable f20225v = new Runnable() { // from class: com.tencent.liteav.videoencoder.b.10
        @Override // java.lang.Runnable
        public void run() {
            b.this.e();
        }
    };

    /* renamed from: w, reason: collision with root package name */
    private Runnable f20226w = new Runnable() { // from class: com.tencent.liteav.videoencoder.b.11
        @Override // java.lang.Runnable
        public void run() {
            b.this.b(10);
        }
    };

    /* renamed from: x, reason: collision with root package name */
    private Runnable f20227x = new Runnable() { // from class: com.tencent.liteav.videoencoder.b.2
        @Override // java.lang.Runnable
        public void run() {
            b.this.b(1);
        }
    };

    /* renamed from: y, reason: collision with root package name */
    private ArrayDeque<Long> f20228y = new ArrayDeque<>(10);
    private Surface A = null;
    private boolean B = true;
    private AtomicBoolean C = new AtomicBoolean(true);
    private boolean D = false;
    private ByteBuffer[] E = null;
    private byte[] F = null;
    private volatile long G = 0;
    private long H = 0;
    private long I = 0;
    private boolean P = true;
    private boolean Q = false;
    private boolean R = false;
    private int S = 0;
    private int T = 0;
    private int U = 0;
    private long V = 0;
    private int W = 0;
    private int X = 0;
    private int Y = -1;
    private final Object aa = new Object();
    private boolean ab = false;
    private int ad = 0;
    private boolean ae = true;
    private long af = 0;
    private int ag = 3;
    private int ah = 0;
    private boolean ai = false;
    private boolean aj = true;
    private long ak = 0;
    private boolean al = false;
    private int am = 0;
    private Runnable an = new Runnable() { // from class: com.tencent.liteav.videoencoder.b.3
        @Override // java.lang.Runnable
        public void run() throws Exception {
            b.this.ak = System.currentTimeMillis();
            b.this.b();
            b.this.d();
            b.this.c();
        }
    };

    public b() {
        this.f20224u = null;
        this.f20224u = new i("HWVideoEncoder");
    }

    public static /* synthetic */ int d(b bVar) {
        int i2 = bVar.am;
        bVar.am = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int h(b bVar) {
        int i2 = bVar.S;
        bVar.S = i2 + 1;
        return i2;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public int getEncodeCost() {
        return this.ad;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long getRealBitrate() {
        return this.f20205b;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public double getRealFPS() {
        return this.f20206c;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public boolean isH265Encoder() {
        return this.R;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long pushVideoFrame(int i2, int i3, int i4, long j2) {
        int iB;
        if (this.C.get()) {
            return 10000004L;
        }
        synchronized (this.aa) {
            if (this.Z == null) {
                a(i3, i4);
            }
            this.Z.a(i3, i4);
            GLES20.glViewport(0, 0, i3, i4);
            iB = this.Z.b(i2);
            if (this.ae) {
                GLES20.glFinish();
            } else {
                GLES20.glFlush();
            }
        }
        this.U++;
        this.G = j2;
        this.Y = iB;
        this.mInputWidth = i3;
        this.mInputHeight = i4;
        if (this.O) {
            f();
        }
        if (!this.P || this.ab) {
            this.X++;
            this.f20224u.b(this.f20226w);
            this.ab = false;
        }
        int i5 = this.S;
        if (i5 > this.T + 30) {
            TXCLog.e("TXCHWVideoEncoder", String.format("hw encoder error when render[%d] pop[%d]", Integer.valueOf(i5), Integer.valueOf(this.T)));
            f fVar = this.mListener;
            if (fVar != null) {
                fVar.l(this.mStreamType);
                if (this.R) {
                    Monitor.a(2, String.format(Locale.getDefault(), "VideoEncoder: hevc hardware encoder error: mRendIdx= %d,mPopIdx= %d , switch to 264 hardware encoder. %s", Integer.valueOf(this.S), Integer.valueOf(this.T), TXCCommonUtil.getDeviceInfo()), "", 0);
                }
            }
        }
        if (this.V + 5000 >= System.currentTimeMillis()) {
            return 0L;
        }
        this.V = System.currentTimeMillis();
        int i6 = this.W;
        if (i6 != 0 && i6 == this.S) {
            TXCLog.i("TXCHWVideoEncoder", String.format("hw encoder error when push[%d] render task[%d] render[%d] pop[%d]", Integer.valueOf(this.U), Integer.valueOf(this.X), Integer.valueOf(this.S), Integer.valueOf(this.T)));
            f fVar2 = this.mListener;
            if (fVar2 != null) {
                fVar2.l(this.mStreamType);
                if (this.R) {
                    Monitor.a(2, String.format(Locale.getDefault(), "VideoEncoder: hevc hardware encoder error: timecheck , switch to 264 hardware encoder. %s", TXCCommonUtil.getDeviceInfo()), "", 0);
                }
            }
        }
        this.W = this.S;
        return 0L;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long pushVideoFrameAsync(final int i2, int i3, int i4, final long j2) {
        if (this.C.get()) {
            return 10000004L;
        }
        if (this.ae) {
            GLES20.glFinish();
        } else {
            GLES20.glFlush();
        }
        if (this.O) {
            f();
        }
        this.f20224u.a().post(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.8
            @Override // java.lang.Runnable
            public void run() {
                boolean z2;
                b bVar;
                f fVar;
                b bVar2;
                f fVar2;
                if (b.this.B || b.this.f20229z == null) {
                    return;
                }
                int i5 = i2;
                long j3 = j2;
                b.this.a(j3);
                b bVar3 = b.this;
                int i6 = (720 - bVar3.mRotation) % 360;
                bVar3.mEncodeFilter.a(bVar3.mInputWidth, bVar3.mInputHeight, i6, null, ((i6 == 90 || i6 == 270) ? bVar3.mOutputHeight : bVar3.mOutputWidth) / ((i6 == 90 || i6 == 270) ? bVar3.mOutputWidth : bVar3.mOutputHeight), bVar3.mEnableXMirror, true);
                b.this.mEncodeFilter.a(i5);
                if (b.this.f20229z instanceof com.tencent.liteav.basic.opengl.c) {
                    ((com.tencent.liteav.basic.opengl.c) b.this.f20229z).a(j3 * 1000000);
                    ((com.tencent.liteav.basic.opengl.c) b.this.f20229z).e();
                }
                if (b.this.f20229z instanceof com.tencent.liteav.basic.opengl.b) {
                    ((com.tencent.liteav.basic.opengl.b) b.this.f20229z).a();
                }
                b.d(b.this);
                b.this.i();
                int i7 = 0;
                if (b.this.am > 2) {
                    TXCLog.i("TXCHWVideoEncoder", "needWait：mEncodingCount :" + b.this.am);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2 && (fVar2 = (bVar2 = b.this).mListener) != null) {
                    fVar2.m(bVar2.mStreamType);
                }
                int iA = -1;
                while (!b.this.C.get()) {
                    iA = b.this.a(10);
                    if (iA == 0) {
                        i7++;
                    }
                    if (iA <= 0 && (b.this.am <= 0 || !z2 || iA != 0 || i7 >= 5)) {
                        break;
                    }
                }
                if (z2 && (fVar = (bVar = b.this).mListener) != null) {
                    fVar.m(bVar.mStreamType);
                }
                if (iA != -1 && iA != -2) {
                    b.h(b.this);
                    return;
                }
                TXCLog.e("TXCHWVideoEncoder", "[Encoder] dequeEncoder ret = " + iA);
                if (iA == -1) {
                    b.this.callDelegate(10000005);
                }
                b.this.B = true;
                b.this.e();
            }
        });
        return 0L;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public long pushVideoFrameSync(int i2, int i3, int i4, long j2) {
        if (this.C.get()) {
            return 10000004L;
        }
        if (this.ae) {
            GLES20.glFinish();
        } else {
            GLES20.glFlush();
        }
        this.G = j2;
        this.Y = i2;
        if (this.O) {
            f();
        }
        this.f20224u.a(this.f20227x);
        return 0L;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setBitrate(final int i2) {
        this.f20204a = i2;
        this.f20224u.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.6
            @Override // java.lang.Runnable
            public void run() {
                b.this.c(i2);
            }
        });
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setBitrateFromQos(final int i2, int i3) {
        this.f20204a = i2;
        this.f20224u.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.7
            @Override // java.lang.Runnable
            public void run() {
                b.this.c(i2);
            }
        });
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setEncodeIdrFpsFromQos(int i2) {
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setFPS(final int i2) {
        this.f20224u.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.5
            @Override // java.lang.Runnable
            public void run() throws Exception {
                b.this.d(i2);
            }
        });
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void setGLFinishedTextureNeed(boolean z2) {
        this.ae = z2;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void signalEOSAndFlush() {
        if (this.C.get()) {
            return;
        }
        this.f20224u.a(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.9
            @Override // java.lang.Runnable
            public void run() {
                if (b.this.f20222s == null) {
                    return;
                }
                try {
                    b.this.f20222s.signalEndOfInputStream();
                } catch (Exception e2) {
                    TXCLog.e("TXCHWVideoEncoder", "signalEndOfInputStream failed.", e2);
                }
                while (b.this.a(10) >= 0) {
                }
                b.this.e();
            }
        });
    }

    @Override // com.tencent.liteav.videoencoder.e
    public int start(final TXSVideoEncoderParam tXSVideoEncoderParam) {
        boolean z2;
        super.start(tXSVideoEncoderParam);
        if (TXCBuild.VersionInt() < 18) {
            z2 = false;
        } else {
            this.f20224u.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.1
                @Override // java.lang.Runnable
                public void run() {
                    TXSVideoEncoderParam tXSVideoEncoderParam2 = tXSVideoEncoderParam;
                    int i2 = tXSVideoEncoderParam2.encoderMode;
                    String str = "unknown";
                    String str2 = i2 != 1 ? i2 != 2 ? i2 != 3 ? "unknown" : "CQ" : "VBR" : "CBR";
                    int i3 = tXSVideoEncoderParam2.encoderProfile;
                    if (i3 == 1) {
                        str = "Baseline";
                    } else if (i3 == 2) {
                        str = "Main";
                    } else if (i3 == 3) {
                        str = "High";
                    }
                    Object[] objArr = new Object[11];
                    objArr[0] = Integer.valueOf(b.this.hashCode());
                    objArr[1] = Integer.valueOf(tXSVideoEncoderParam.width);
                    objArr[2] = Integer.valueOf(tXSVideoEncoderParam.height);
                    objArr[3] = Integer.valueOf(tXSVideoEncoderParam.fps);
                    objArr[4] = Integer.valueOf(tXSVideoEncoderParam.bitrate);
                    objArr[5] = Integer.valueOf(tXSVideoEncoderParam.gop);
                    objArr[6] = str2;
                    objArr[7] = str;
                    TXSVideoEncoderParam tXSVideoEncoderParam3 = tXSVideoEncoderParam;
                    boolean z3 = tXSVideoEncoderParam3.bMultiRef;
                    String str3 = k.a.f27523u;
                    objArr[8] = z3 ? k.a.f27523u : k.a.f27524v;
                    objArr[9] = Integer.valueOf(tXSVideoEncoderParam3.streamType);
                    if (!tXSVideoEncoderParam.isH265EncoderEnabled) {
                        str3 = k.a.f27524v;
                    }
                    objArr[10] = str3;
                    String str4 = String.format("VideoEncoder[%d]: Start [type:hardware][resolution:%d*%d][fps:%d][bitrate:%dkbps][gop:%d][rateControl:%s][profile:%s][rps:%s][streamType:%d][enable hevc:%s]", objArr);
                    Monitor.a(2, str4, "", 0);
                    TXCLog.i("TXCHWVideoEncoder", "start:" + str4);
                    b bVar = b.this;
                    if (bVar.mInit) {
                        bVar.e();
                    }
                    if (b.this.a(tXSVideoEncoderParam)) {
                        Monitor.a(2, String.format("VideoEncoder[%d]: Start successfully, streamType:%d", Integer.valueOf(b.this.hashCode()), Integer.valueOf(tXSVideoEncoderParam.streamType)), "streamType: 2-big, 3-small, 7-sub", 0);
                    } else {
                        b.this.callDelegate(10000004);
                    }
                }
            });
            z2 = true;
        }
        return z2 ? 0 : 10000004;
    }

    @Override // com.tencent.liteav.videoencoder.e
    public void stop() {
        this.C.set(true);
        this.f20224u.b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.4
            @Override // java.lang.Runnable
            public void run() {
                b bVar = b.this;
                if (bVar.mInit) {
                    Monitor.a(2, String.format("VideoEncoder[%d]: Stop, streamType:%d", Integer.valueOf(bVar.hashCode()), Integer.valueOf(b.this.mStreamType)), "streamType: 2-big, 3-small, 7-sub", 0);
                    b.this.e();
                }
            }
        });
        h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (this.mInit) {
            this.B = true;
            this.C.set(true);
            b();
            d();
            this.Y = -1;
            this.f20205b = 0L;
            this.f20206c = 0.0d;
            this.f20207d = 0L;
            this.f20208e = 0L;
            this.f20209f = 0;
            this.f20212i = 0L;
            this.f20213j = 0L;
            this.f20214k = 0L;
            this.f20215l = 0L;
            this.f20216m = 0L;
            this.f20220q = 0L;
            this.f20221r = 0L;
            this.mGLContextExternal = null;
            this.E = null;
            this.F = null;
            this.G = 0L;
            this.mOutputWidth = 0;
            this.mOutputHeight = 0;
            this.mInit = false;
            this.mListener = null;
            this.f20228y.clear();
            this.ac.clear();
            this.ad = 0;
        }
    }

    private void f() {
        if (TXCBuild.VersionInt() < 19 || this.f20222s == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("request-sync", 0);
        this.f20222s.setParameters(bundle);
    }

    private void g() {
        if (this.af > 0) {
            int i2 = this.L;
            int i3 = i2 - ((int) this.f20206c);
            int i4 = i2 / 2;
            if (i4 < 5) {
                i4 = 5;
            }
            if (i3 <= i4) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.af;
                int i5 = this.ag;
                if (jCurrentTimeMillis > ((3 - i5) + 1) * 2000) {
                    long j2 = this.ah - this.f20205b;
                    long j3 = this.f20212i / 2;
                    if (j3 < 100) {
                        j3 = 100;
                    }
                    if (j2 <= j3) {
                        int i6 = i5 - 1;
                        this.ag = i6;
                        if (i6 <= 0) {
                            this.af = 0L;
                            return;
                        }
                        return;
                    }
                    this.ai = true;
                    String str = "real bitrate is too much lower than target bitrate![current profile:" + this.N + "][targetBr:" + this.ah + "] [realBr:" + this.f20205b + "]. restart encoder. [module:" + TXCBuild.Model() + "] [Hardware:" + TXCBuild.Hardware() + "] [osVersion:" + TXCBuild.Version() + StrPool.BRACKET_END;
                    TXCLog.e("TXCHWVideoEncoder", str);
                    Monitor.a(3, str, "", 0);
                    if (this.R) {
                        this.N = 1;
                        TXCLog.w("TXCHWVideoEncoder", "[Encoder] force reset hevc profile to HEVCProfileMain when restart encoder. device:" + TXCCommonUtil.getDeviceInfo());
                    } else if (this.N != 1) {
                        this.N = 1;
                        TXCLog.e("TXCHWVideoEncoder", "[Encoder] force reset profile to baseline when restart encoder. device:" + TXCCommonUtil.getDeviceInfo());
                    }
                    i iVar = this.f20224u;
                    if (iVar != null) {
                        iVar.b(this.an);
                    }
                    this.af = 0L;
                }
            }
        }
    }

    private void h() {
        TXCLog.i("TXCHWVideoEncoder", "destroyCopyTexture");
        synchronized (this.aa) {
            j jVar = this.Z;
            if (jVar != null) {
                jVar.d();
                this.Z = null;
            }
            this.Y = -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        int i2 = 0;
        while (!this.al && i2 < 20 && !this.C.get()) {
            a(10);
            i2++;
        }
        TXCLog.i("TXCHWVideoEncoder", "run: wait for encoderReady try:" + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01ad A[Catch: Exception -> 0x01b2, TryCatch #6 {Exception -> 0x01b2, blocks: (B:71:0x01a0, B:73:0x01a4, B:74:0x01a7, B:76:0x01ad, B:77:0x01b0), top: B:104:0x01a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01b6  */
    /* JADX WARN: Type inference failed for: r15v2, types: [android.media.MediaCodec, android.view.Surface] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean c() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 466
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoencoder.b.c():boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        MediaCodec mediaCodec = this.f20222s;
        if (mediaCodec == null) {
            return;
        }
        try {
        } catch (Exception e2) {
            TXCLog.e("TXCHWVideoEncoder", "release encoder failed.", e2);
        }
        try {
            try {
                mediaCodec.stop();
                this.f20222s.release();
                Surface surface = this.A;
                if (surface != null) {
                    surface.release();
                }
                this.A = null;
            } catch (IllegalStateException e3) {
                TXCLog.e("TXCHWVideoEncoder", "stop encoder failed.", e3);
                this.f20222s.release();
                Surface surface2 = this.A;
                if (surface2 != null) {
                    surface2.release();
                }
                this.A = null;
            }
            this.f20222s = null;
        } catch (Throwable th) {
            try {
                this.f20222s.release();
                Surface surface3 = this.A;
                if (surface3 != null) {
                    surface3.release();
                }
                this.A = null;
            } catch (Exception e4) {
                TXCLog.e("TXCHWVideoEncoder", "release encoder failed.", e4);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        TXCLog.i("TXCHWVideoEncoder", "HWVideoEncode destroyGL");
        j jVar = this.mEncodeFilter;
        if (jVar != null) {
            jVar.d();
            this.mEncodeFilter = null;
        }
        Object obj = this.f20229z;
        if (obj instanceof com.tencent.liteav.basic.opengl.b) {
            ((com.tencent.liteav.basic.opengl.b) obj).c();
            this.f20229z = null;
        }
        Object obj2 = this.f20229z;
        if (obj2 instanceof com.tencent.liteav.basic.opengl.c) {
            ((com.tencent.liteav.basic.opengl.c) obj2).d();
            this.f20229z = null;
        }
    }

    private MediaFormat a(MediaCodec mediaCodec, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        MediaFormat mediaFormatA = a.a(this.f20223t, i2, i3, i4, i5, i6);
        if (mediaFormatA == null) {
            return null;
        }
        a.a(mediaCodec, mediaFormatA, this.f20223t, i7, i8, this.O);
        this.mOutputWidth = mediaFormatA.getInteger("width");
        this.mOutputHeight = mediaFormatA.getInteger("height");
        this.f20204a = (int) (mediaFormatA.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE) / 1024.0d);
        TXCLog.i("TXCHWVideoEncoder", "createEffectiveFormat fix:w:  " + this.mOutputWidth + "  " + this.mOutputHeight + "  " + this.f20204a);
        return mediaFormatA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j2) {
        this.f20228y.add(Long.valueOf(j2));
    }

    private long a() {
        Long lPoll = this.f20228y.poll();
        if (lPoll == null) {
            return 0L;
        }
        return lPoll.longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(18)
    public void b(int i2) {
        int iA;
        if (this.B || this.f20229z == null) {
            return;
        }
        synchronized (this.aa) {
            int i3 = this.Y;
            if (this.P) {
                this.Y = -1;
                if (i3 == -1) {
                    this.ab = true;
                    return;
                } else {
                    this.X++;
                    this.f20224u.a(this.f20226w, 1000 / this.L);
                }
            }
            if (i3 == -1) {
                return;
            }
            a(this.G);
            int i4 = (720 - this.mRotation) % 360;
            this.mEncodeFilter.a(this.mInputWidth, this.mInputHeight, i4, null, ((i4 == 90 || i4 == 270) ? this.mOutputHeight : this.mOutputWidth) / ((i4 == 90 || i4 == 270) ? this.mOutputWidth : this.mOutputHeight), this.mEnableXMirror, true);
            this.mEncodeFilter.a(i3);
            Object obj = this.f20229z;
            if (obj instanceof com.tencent.liteav.basic.opengl.c) {
                ((com.tencent.liteav.basic.opengl.c) obj).a(this.G * 1000000);
                ((com.tencent.liteav.basic.opengl.c) this.f20229z).e();
            }
            Object obj2 = this.f20229z;
            if (obj2 instanceof com.tencent.liteav.basic.opengl.b) {
                ((com.tencent.liteav.basic.opengl.b) obj2).a();
            }
            do {
                iA = a(i2);
            } while (iA > 0);
            if (iA != -1 && iA != -2) {
                this.S++;
                return;
            }
            TXCLog.e("TXCHWVideoEncoder", "[Encoder] dequeEncoder ret = " + iA);
            if (iA == -1) {
                callDelegate(10000005);
            }
            this.B = true;
            e();
        }
    }

    private boolean a(Surface surface, int i2, int i3) {
        if (surface == null) {
            return false;
        }
        TXCLog.i("TXCHWVideoEncoder", "HWVideoEncode createGL: " + this.mGLContextExternal);
        Object obj = this.mGLContextExternal;
        if (obj != null && (obj instanceof EGLContext)) {
            this.f20229z = com.tencent.liteav.basic.opengl.c.a(null, (EGLContext) obj, surface, i2, i3);
        } else {
            this.f20229z = com.tencent.liteav.basic.opengl.b.a(null, (javax.microedition.khronos.egl.EGLContext) obj, surface, i2, i3);
        }
        if (this.f20229z == null) {
            return false;
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        j jVar = new j();
        this.mEncodeFilter = jVar;
        jVar.a(m.f18642e, m.a(l.NORMAL, false, false));
        if (!this.mEncodeFilter.a()) {
            this.mEncodeFilter = null;
            return false;
        }
        GLES20.glViewport(0, 0, i2, i3);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(int i2) throws Exception {
        if (this.mInit && i2 != 0 && this.L != i2 && TXCBuild.VersionInt() >= 18) {
            TXCLog.i("TXCHWVideoEncoder", "set fps " + i2 + ", restart encoder.");
            b();
            d();
            this.L = i2;
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0172  */
    @android.annotation.TargetApi(18)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(com.tencent.liteav.videoencoder.TXSVideoEncoderParam r11) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoencoder.b.a(com.tencent.liteav.videoencoder.TXSVideoEncoderParam):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2) {
        if (this.mInit) {
            long j2 = this.f20212i;
            int i3 = this.f20204a;
            if (j2 == i3) {
                return;
            }
            boolean z2 = false;
            if (i3 < j2 && this.aj) {
                if (this.ai) {
                    Monitor.a(4, "restart video hw encoder when down bps。[module:" + TXCBuild.Model() + "] [Hardware:" + TXCBuild.Hardware() + "] [osVersion:" + TXCBuild.Version() + StrPool.BRACKET_END, "", 0);
                    z2 = true;
                } else {
                    this.ag = 3;
                    this.af = System.currentTimeMillis();
                    this.ah = this.f20204a;
                }
            }
            this.f20212i = this.f20204a;
            if (TXCBuild.VersionInt() < 19 || this.f20222s == null) {
                return;
            }
            if (z2) {
                this.f20224u.a().removeCallbacks(this.an);
                long jCurrentTimeMillis = System.currentTimeMillis();
                long j3 = this.ak;
                if (jCurrentTimeMillis - j3 >= ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                    this.an.run();
                    return;
                } else {
                    this.f20224u.a(this.an, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS - (jCurrentTimeMillis - j3));
                    return;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putInt("video-bitrate", this.f20204a * 1024);
            this.f20222s.setParameters(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int a(int r34) {
        /*
            Method dump skipped, instructions count: 945
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoencoder.b.a(int):int");
    }

    private byte[] a(byte[] bArr) {
        int i2;
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 20];
        int i3 = 0;
        int iA = 0;
        int i4 = 0;
        while (i4 < length) {
            byte b3 = bArr[i4];
            if (b3 == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 1) {
                iA = a(i4, i3, bArr2, bArr, iA);
                i3 = i4 + 3;
            } else {
                if (b3 == 0 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 0 && bArr[i4 + 3] == 1) {
                    iA = a(i4, i3, bArr2, bArr, iA);
                    i3 = i4 + 4;
                }
                if (i4 != length - 4 && (bArr[i4 + 1] != 0 || bArr[i4 + 2] != 0 || bArr[i4 + 3] != 1)) {
                    i2 = length;
                    break;
                }
                i4++;
            }
            i4 = i3;
            if (i4 != length - 4) {
            }
            i4++;
        }
        i2 = i4;
        int iA2 = a(i2, i3, bArr2, bArr, iA);
        byte[] bArr3 = new byte[iA2];
        System.arraycopy(bArr2, 0, bArr3, 0, iA2);
        return bArr3;
    }

    private int a(int i2, int i3, byte[] bArr, byte[] bArr2, int i4) {
        if (i3 <= 0 || i2 <= i3) {
            return i4;
        }
        int i5 = i2 - i3;
        try {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[4]);
            byteBufferWrap.asIntBuffer().put(i5);
            byteBufferWrap.order(ByteOrder.BIG_ENDIAN);
            System.arraycopy(byteBufferWrap.array(), 0, bArr, i4, 4);
            System.arraycopy(bArr2, i3, bArr, i4 + 4, i5);
            return i4 + i5 + 4;
        } catch (Exception unused) {
            TXCLog.e("TXCHWVideoEncoder", "setNalData exception");
            return i4;
        }
    }

    private void a(int i2, int i3) {
        TXCLog.i("TXCHWVideoEncoder", "createCopyTexture");
        synchronized (this.aa) {
            j jVar = new j();
            this.Z = jVar;
            jVar.a(true);
            this.Z.a();
            this.Z.a(i2, i3);
        }
    }
}
