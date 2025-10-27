package com.tencent.liteav;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.google.android.exoplayer2.ExoPlayer;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.a.a;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.liteav.renderer.a;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class f extends n implements TXCRenderAndDec.a, TXCRenderAndDec.b, com.tencent.liteav.audio.d, com.tencent.liteav.audio.f, com.tencent.liteav.basic.b.b, com.tencent.liteav.network.g, a.InterfaceC0336a, com.tencent.liteav.renderer.g {
    private e A;
    private int B;
    private int C;
    private com.tencent.liteav.renderer.h D;
    private com.tencent.liteav.renderer.h E;
    private float[] F;
    private float[] G;
    private String H;
    private int I;
    private boolean J;
    private com.tencent.liteav.basic.enums.b K;
    private Object L;
    private com.tencent.liteav.basic.b.a M;
    private TXLivePlayer.ITXAudioRawDataListener N;
    private String O;
    private boolean P;
    private long Q;
    private long R;
    private a S;

    /* renamed from: e, reason: collision with root package name */
    private TXCRenderAndDec f19294e;

    /* renamed from: f, reason: collision with root package name */
    private com.tencent.liteav.renderer.a f19295f;

    /* renamed from: g, reason: collision with root package name */
    private TXCStreamDownloader f19296g;

    /* renamed from: h, reason: collision with root package name */
    private int f19297h;

    /* renamed from: i, reason: collision with root package name */
    private Handler f19298i;

    /* renamed from: j, reason: collision with root package name */
    private TextureView f19299j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f19300k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19301l;

    /* renamed from: m, reason: collision with root package name */
    private int f19302m;

    /* renamed from: n, reason: collision with root package name */
    private int f19303n;

    /* renamed from: o, reason: collision with root package name */
    private int f19304o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f19305p;

    /* renamed from: q, reason: collision with root package name */
    private Surface f19306q;

    /* renamed from: r, reason: collision with root package name */
    private int f19307r;

    /* renamed from: s, reason: collision with root package name */
    private int f19308s;

    /* renamed from: t, reason: collision with root package name */
    private int f19309t;

    /* renamed from: u, reason: collision with root package name */
    private boolean f19310u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f19311v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f19312w;

    /* renamed from: x, reason: collision with root package name */
    private int f19313x;

    /* renamed from: y, reason: collision with root package name */
    private com.tencent.liteav.a.a f19314y;

    /* renamed from: z, reason: collision with root package name */
    private TXRecordCommon.ITXVideoRecordListener f19315z;

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<f> f19326a;

        public a(f fVar) {
            this.f19326a = new WeakReference<>(fVar);
        }

        @Override // java.lang.Runnable
        public void run() {
            f fVar = this.f19326a.get();
            if (fVar == null || !fVar.c()) {
                return;
            }
            fVar.h();
            fVar.v();
        }
    }

    public f(Context context) {
        super(context);
        this.f19294e = null;
        this.f19295f = null;
        this.f19296g = null;
        this.f19297h = 0;
        this.f19300k = false;
        this.f19301l = false;
        this.f19302m = 100;
        this.f19303n = 0;
        this.f19304o = 0;
        this.f19305p = false;
        this.f19307r = 2;
        this.f19308s = 48000;
        this.f19309t = 16;
        this.f19310u = false;
        this.f19311v = false;
        this.f19312w = false;
        this.f19313x = 0;
        this.B = 0;
        this.C = 0;
        this.D = null;
        this.E = null;
        this.F = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
        this.G = new float[16];
        this.H = "";
        this.J = false;
        this.K = com.tencent.liteav.basic.enums.b.UNKNOWN;
        this.L = null;
        this.M = new com.tencent.liteav.basic.b.a() { // from class: com.tencent.liteav.f.1
            public void a(String str, int i2, String str2, String str3) {
                WeakReference<com.tencent.liteav.basic.b.b> weakReference = f.this.f19455d;
                if ((weakReference == null ? null : weakReference.get()) != null) {
                    if (i2 == 10048 || i2 == 10049 || i2 == 10053) {
                        i2 = 1205;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("EVT_ID", i2);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
                    if (str2 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        if (str3 == null) {
                            str3 = "";
                        }
                        sb.append(str3);
                        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, sb.toString());
                    }
                    f.this.onNotifyEvent(i2, bundle);
                }
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onError(String str, int i2, String str2, String str3) {
                TXCLog.e("TXCLivePlayer", "onError => id:" + str + " code:" + i2 + " msg:" + str2 + " params:" + str3);
                a(str, i2, str2, str3);
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onEvent(String str, int i2, String str2, String str3) {
                TXCLog.i("TXCLivePlayer", "onEvent => id:" + str + " code:" + i2 + " msg:" + str2 + " params:" + str3);
                a(str, i2, str2, str3);
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onWarning(String str, int i2, String str2, String str3) {
                TXCLog.i("TXCLivePlayer", "onWarning => id:" + str + " code:" + i2 + " msg:" + str2 + " params:" + str3);
                a(str, i2, str2, str3);
            }
        };
        this.O = "";
        this.P = false;
        this.Q = 0L;
        this.R = 0L;
        this.S = null;
        com.tencent.liteav.basic.c.c.a().a(context);
        TXCAudioEngine.CreateInstance(context, com.tencent.liteav.basic.c.c.a().b(), AudioServerConfig.loadFromSharedPreferences(context).isAudioDeviceDSPEnabled());
        TXCAudioEngine.getInstance().addEventCallback(new WeakReference<>(this.M));
        long jA = com.tencent.liteav.basic.c.c.a().a("Audio", "EnableAutoRestartDevice");
        TXCAudioEngine.getInstance().enableAutoRestartDevice(jA == 1 || jA == -1);
        this.f19298i = new Handler(Looper.getMainLooper());
        com.tencent.liteav.renderer.a aVar = new com.tencent.liteav.renderer.a();
        this.f19295f = aVar;
        aVar.a((com.tencent.liteav.basic.b.b) this);
        this.S = new a(this);
        TXCLog.i("TXCLivePlayer", "[FirstFramePath] TXCLivePlayer: create player success. instance:" + hashCode());
    }

    private void g(int i2) {
        TextureView textureView = this.f19299j;
        if (textureView != null) {
            textureView.setVisibility(0);
        }
        TXCRenderAndDec tXCRenderAndDec = new TXCRenderAndDec(this.f19453b);
        this.f19294e = tXCRenderAndDec;
        tXCRenderAndDec.setNotifyListener(this);
        this.f19294e.setVideoRender(this.f19295f);
        this.f19294e.setDecListener(this);
        this.f19294e.setRenderAndDecDelegate(this);
        this.f19294e.setConfig(this.f19452a);
        this.f19294e.setID(this.O);
        this.f19294e.start(i2 == 5);
        this.f19294e.setRenderMode(this.f19304o);
        this.f19294e.setRenderRotation(this.f19303n);
    }

    private void k() {
        if (this.f19314y == null) {
            this.B = this.f19295f.h();
            this.C = this.f19295f.i();
            a.C0322a c0322aM = m();
            com.tencent.liteav.a.a aVar = new com.tencent.liteav.a.a(this.f19453b);
            this.f19314y = aVar;
            aVar.a(c0322aM);
            this.f19314y.a(new a.b() { // from class: com.tencent.liteav.f.2
                @Override // com.tencent.liteav.a.a.b
                public void a(int i2, String str, String str2, String str3) {
                    if (f.this.f19315z != null) {
                        TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
                        if (i2 == 0) {
                            tXRecordResult.retCode = 0;
                        } else {
                            tXRecordResult.retCode = -1;
                        }
                        tXRecordResult.descMsg = str;
                        tXRecordResult.videoPath = str2;
                        tXRecordResult.coverPath = str3;
                        f.this.f19315z.onRecordComplete(tXRecordResult);
                    }
                    f.this.f19295f.a((com.tencent.liteav.renderer.g) null);
                    f.this.f19295f.a((a.InterfaceC0336a) null);
                }

                @Override // com.tencent.liteav.a.a.b
                public void a(long j2) {
                    if (f.this.f19315z != null) {
                        f.this.f19315z.onRecordProgress(j2);
                    }
                }
            });
        }
        if (this.D == null) {
            com.tencent.liteav.renderer.h hVar = new com.tencent.liteav.renderer.h(Boolean.TRUE);
            this.D = hVar;
            hVar.b();
            this.D.b(this.B, this.C);
            this.D.a(this.B, this.C);
        }
        if (this.E == null) {
            com.tencent.liteav.renderer.h hVar2 = new com.tencent.liteav.renderer.h(Boolean.FALSE);
            this.E = hVar2;
            hVar2.b();
            this.E.b(this.f19295f.f(), this.f19295f.g());
            this.E.a(this.f19295f.f(), this.f19295f.g());
            Matrix.setIdentityM(this.G, 0);
        }
    }

    private void l() {
        com.tencent.liteav.renderer.h hVar = this.D;
        if (hVar != null) {
            hVar.c();
            this.D = null;
        }
        com.tencent.liteav.renderer.h hVar2 = this.E;
        if (hVar2 != null) {
            hVar2.c();
            this.E = null;
        }
    }

    private a.C0322a m() {
        int i2;
        int i3 = this.B;
        if (i3 <= 0 || (i2 = this.C) <= 0) {
            i3 = 480;
            i2 = 640;
        }
        a.C0322a c0322a = new a.C0322a();
        c0322a.f18142a = i3;
        c0322a.f18143b = i2;
        c0322a.f18144c = 20;
        c0322a.f18145d = (int) (Math.sqrt((i3 * i3 * 1.0d) + (i2 * i2)) * 1.2d);
        c0322a.f18149h = this.f19307r;
        c0322a.f18150i = this.f19308s;
        c0322a.f18151j = this.f19309t;
        c0322a.f18147f = com.tencent.liteav.a.a.a(this.f19453b, ".mp4");
        c0322a.f18148g = com.tencent.liteav.a.a.a(this.f19453b, ".jpg");
        c0322a.f18146e = this.f19295f.b();
        TXCLog.d("TXCLivePlayer", "record config: " + c0322a);
        return c0322a;
    }

    private void n() {
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.stop();
            this.f19294e.setVideoRender(null);
            this.f19294e.setDecListener(null);
            this.f19294e.setNotifyListener(null);
            this.f19294e = null;
        }
    }

    private void o() {
        boolean z2 = this.I == 5;
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(this.J, 300);
        a(this.N);
        if (this.I == 5) {
            TXCAudioEngine tXCAudioEngine = TXCAudioEngine.getInstance();
            String str = this.O;
            boolean z3 = !this.f19452a.f19362g;
            float f2 = com.tencent.liteav.basic.enums.a.f18402b;
            tXCAudioEngine.setRemoteAudioCacheParams(str, z3, (int) (f2 * 1000.0f), (int) (f2 * 1000.0f), (int) (com.tencent.liteav.basic.enums.a.f18403c * 1000.0f));
        } else {
            TXCAudioEngine tXCAudioEngine2 = TXCAudioEngine.getInstance();
            String str2 = this.O;
            h hVar = this.f19452a;
            tXCAudioEngine2.setRemoteAudioCacheParams(str2, !hVar.f19362g, (int) (hVar.f19356a * 1000.0f), (int) (hVar.f19358c * 1000.0f), (int) (hVar.f19357b * 1000.0f));
        }
        TXCAudioEngine.getInstance().muteRemoteAudio(this.O, this.f19300k);
        TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(this.O, this.f19301l);
        TXCAudioEngine.getInstance().setRemotePlayoutVolume(this.O, this.f19302m);
        TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(this.O, this);
        y();
        TXCAudioEngine.getInstance().startRemoteAudio(this.O, z2);
    }

    private void p() {
        TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(this.O, null);
        TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(this.O, null);
        TXCAudioEngine.getInstance().stopRemoteAudio(this.O);
    }

    private void q() {
        TXCStreamDownloader tXCStreamDownloader = this.f19296g;
        if (tXCStreamDownloader != null) {
            tXCStreamDownloader.setListener(null);
            this.f19296g.setNotifyListener(null);
            this.f19296g.stop();
            this.f19296g = null;
        }
    }

    private void r() {
        e eVar = new e(this.f19453b);
        this.A = eVar;
        eVar.a(this.H);
        this.A.a(this.I == 5);
        this.A.d(this.O);
        this.A.e(this.f19296g.getRTMPProxyUserId());
        this.A.a();
    }

    private void s() {
        e eVar = this.A;
        if (eVar != null) {
            eVar.b();
            this.A = null;
        }
    }

    private void t() {
        this.P = false;
        y();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void u() {
        if (this.Q > 0) {
            Bundle bundle = new Bundle();
            bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS, (int) (this.Q / 1000));
            bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS, (int) this.Q);
            onNotifyEvent(2005, bundle);
        }
        Handler handler = this.f19298i;
        if (handler == null || !this.P) {
            return;
        }
        handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.f.4
            @Override // java.lang.Runnable
            public void run() {
                if (f.this.P) {
                    f.this.u();
                }
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void v() {
        Handler handler = this.f19298i;
        if (handler != null) {
            handler.postDelayed(this.S, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }

    private void w() {
        Handler handler = this.f19298i;
        if (handler != null) {
            handler.removeCallbacks(this.S);
        }
    }

    private void x() {
        ArrayList arrayList = new ArrayList();
        String str = this.O;
        if (str != null) {
            arrayList.add(str);
        }
        com.tencent.liteav.a.a("18446744073709551615", arrayList);
    }

    private void y() {
        if (this.f19310u || this.N != null || this.P) {
            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(this.O, this);
        }
        if (this.f19310u || this.N != null || this.P) {
            return;
        }
        TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(this.O, null);
    }

    @Override // com.tencent.liteav.n
    public boolean e() {
        return true;
    }

    public void h() {
        x();
        int[] iArrA = com.tencent.liteav.basic.util.h.a();
        String str = (iArrA[0] / 10) + "/" + (iArrA[1] / 10) + "%";
        int iC = TXCStatus.c(this.O, R2.dimen.dp_499);
        int iC2 = TXCStatus.c(this.O, R2.dimen.dp_498);
        String strB = TXCStatus.b(this.O, R2.dimen.dp_503);
        int iD = (int) TXCStatus.d(this.O, 6002);
        Bundle bundle = new Bundle();
        com.tencent.liteav.renderer.a aVar = this.f19295f;
        if (aVar != null) {
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH, aVar.h());
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT, this.f19295f.i());
        }
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE, (int) tXCRenderAndDec.getVideoCacheDuration());
            bundle.putInt(TXLiveConstants.NET_STATUS_V_SUM_CACHE_SIZE, (int) this.f19294e.getVideoCacheFrameCount());
            bundle.putInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE, this.f19294e.getVideoDecCacheFrameCount());
            bundle.putInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL, (int) this.f19294e.getAVPlayInterval());
            bundle.putInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL, (int) this.f19294e.getAVNetRecvInterval());
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, (int) ((((TXCStatus.c(this.O, R2.dimen.dp_511) * 10) / (iD == 0 ? 15 : iD)) / 10.0f) + 0.5d));
        }
        bundle.putString(TXLiveConstants.NET_STATUS_AUDIO_INFO, TXCAudioEngine.getInstance().getPlayAECType() + " | " + TXCStatus.c(this.O, 2019) + "," + TXCStatus.c(this.O, 2020) + " | " + TXCAudioEngine.getInstance().getPlaySampleRate() + "," + TXCAudioEngine.getInstance().getPlayChannels());
        bundle.putInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE, TXCStatus.c(this.O, 2007));
        bundle.putInt(TXLiveConstants.NET_STATUS_NET_JITTER, TXCStatus.c(this.O, 2018));
        bundle.putFloat(TXLiveConstants.NET_STATUS_AUDIO_CACHE_THRESHOLD, ((float) TXCStatus.c(this.O, 2021)) / 1000.0f);
        bundle.putInt(TXLiveConstants.NET_STATUS_AUDIO_BLOCK_TIME, TXCStatus.c(this.O, R2.attr.indeterminateProgressStyle));
        bundle.putInt(TXLiveConstants.NET_STATUS_NET_SPEED, iC2 + iC);
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_FPS, iD);
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE, iC2);
        bundle.putInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE, iC);
        bundle.putCharSequence(TXLiveConstants.NET_STATUS_SERVER_IP, strB);
        bundle.putCharSequence(TXLiveConstants.NET_STATUS_CPU_USAGE, str);
        int i2 = this.f19313x + 1;
        this.f19313x = i2;
        if (i2 == 5) {
            if (this.f19312w) {
                TXCStatus.a(this.O, R2.dimen.abc_action_bar_stacked_tab_max_width, (Object) 0L);
            } else {
                TXCStatus.a(this.O, R2.dimen.abc_action_bar_stacked_tab_max_width, (Object) 1L);
            }
        }
        com.tencent.liteav.basic.util.h.a(this.f19455d, R2.id.headertxt, bundle);
        TXCRenderAndDec tXCRenderAndDec2 = this.f19294e;
        if (tXCRenderAndDec2 != null) {
            tXCRenderAndDec2.updateLoadInfo();
        }
        e eVar = this.A;
        if (eVar != null) {
            eVar.d();
        }
    }

    @Override // com.tencent.liteav.n
    public int i() {
        return TXCAudioEngine.getInstance().getRemotePlayoutVolumeLevel(this.O);
    }

    @Override // com.tencent.liteav.audio.d
    public void onAudioJitterBufferNotify(String str, int i2, String str2) {
        onNotifyEvent(i2, null);
    }

    @Override // com.tencent.liteav.audio.f
    public void onAudioPlayPcmData(String str, byte[] bArr, long j2, int i2, int i3, byte[] bArr2) {
        TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener;
        this.f19308s = i2;
        this.f19307r = i3;
        if (this.f19314y != null) {
            if (j2 <= 0) {
                j2 = TXCTimeUtil.getTimeTick();
            }
            this.f19314y.a(bArr, j2);
        }
        if (this.R <= 0 && (iTXAudioRawDataListener = this.N) != null) {
            iTXAudioRawDataListener.onAudioInfoChanged(i2, i3, 16);
        }
        TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener2 = this.N;
        if (iTXAudioRawDataListener2 != null) {
            iTXAudioRawDataListener2.onPcmDataAvailable(bArr, j2);
        }
        long j3 = this.R;
        if (j3 <= 0) {
            this.R = j2;
        } else {
            this.Q = j2 - j3;
        }
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(final int i2, final Bundle bundle) {
        if (2003 == i2 && !this.f19312w) {
            this.f19312w = true;
        }
        if (2003 == i2 || 2026 == i2) {
            if (this.f19311v) {
                a(2004, "Video play started");
                this.f19311v = false;
            }
            if (2026 == i2) {
                a(2026, "Audio play started");
                TXCStatus.a(this.O, R2.attr.implementationMode, Long.valueOf(TXCTimeUtil.getTimeTick()));
                return;
            }
        }
        if (2025 == i2) {
            a(2004, "Video play started");
            return;
        }
        if (2023 == i2 || 2024 == i2) {
            a(2007, "Video play loading");
            return;
        }
        Handler handler = this.f19298i;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.f.6
                @Override // java.lang.Runnable
                public void run() {
                    com.tencent.liteav.basic.util.h.a(f.this.f19455d, i2, bundle);
                    if (i2 != 2103 || f.this.f19294e == null) {
                        return;
                    }
                    f.this.f19294e.restartDecoder();
                }
            });
        }
    }

    @Override // com.tencent.liteav.network.g
    public void onPullAudio(com.tencent.liteav.basic.structs.a aVar) {
    }

    @Override // com.tencent.liteav.network.g
    public void onPullNAL(TXSNALPacket tXSNALPacket) {
        if (this.f19305p) {
            try {
                TXCRenderAndDec tXCRenderAndDec = this.f19294e;
                if (tXCRenderAndDec != null) {
                    tXCRenderAndDec.decVideo(tXSNALPacket);
                }
            } catch (Exception e2) {
                TXCLog.e("TXCLivePlayer", "decode video failed." + e2.getMessage());
            }
        }
    }

    @Override // com.tencent.liteav.TXCRenderAndDec.b
    public void onRequestKeyFrame(String str, int i2) {
        TXCStreamDownloader tXCStreamDownloader;
        if (!this.f19305p || (tXCStreamDownloader = this.f19296g) == null) {
            return;
        }
        tXCStreamDownloader.requestKeyFrame(this.H);
    }

    @Override // com.tencent.liteav.renderer.a.InterfaceC0336a
    public void onTextureProcess(int i2, int i3, int i4, int i5) {
        com.tencent.liteav.renderer.h hVar;
        com.tencent.liteav.a.a aVar = this.f19314y;
        if (this.f19310u && aVar != null && (hVar = this.E) != null) {
            hVar.a(this.F);
            aVar.a(this.E.d(i2), TXCTimeUtil.getTimeTick());
            this.E.a(this.G);
            this.E.c(i2);
        }
        if (this.f19310u) {
            k();
        } else {
            l();
        }
    }

    @Override // com.tencent.liteav.n
    public void a(TXCloudVideoView tXCloudVideoView) {
        TextureView videoView;
        TXCloudVideoView tXCloudVideoView2 = this.f19454c;
        if (tXCloudVideoView2 != null && tXCloudVideoView2 != tXCloudVideoView && (videoView = tXCloudVideoView2.getVideoView()) != null) {
            this.f19454c.removeView(videoView);
        }
        super.a(tXCloudVideoView);
        TXCloudVideoView tXCloudVideoView3 = this.f19454c;
        if (tXCloudVideoView3 != null) {
            TextureView videoView2 = tXCloudVideoView3.getVideoView();
            this.f19299j = videoView2;
            if (videoView2 == null) {
                this.f19299j = new TextureView(this.f19454c.getContext());
            }
            this.f19454c.addVideoView(this.f19299j);
        }
        com.tencent.liteav.renderer.a aVar = this.f19295f;
        if (aVar != null) {
            aVar.a(this.f19299j);
        }
    }

    @Override // com.tencent.liteav.n
    public void b() throws ClassNotFoundException {
        a(this.H, this.I);
    }

    @Override // com.tencent.liteav.n
    public boolean c() {
        return this.f19305p;
    }

    @Override // com.tencent.liteav.n
    public void d(int i2) {
        this.f19297h = i2;
        TXCStreamDownloader tXCStreamDownloader = this.f19296g;
        if (tXCStreamDownloader != null) {
            tXCStreamDownloader.setPayloadType(i2);
        }
    }

    @Override // com.tencent.liteav.n
    public int e(int i2) {
        if (this.f19310u) {
            TXCLog.e("TXCLivePlayer", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        this.f19310u = true;
        this.f19295f.a((com.tencent.liteav.renderer.g) this);
        this.f19295f.a((a.InterfaceC0336a) this);
        y();
        TXCDRApi.txReportDAU(this.f19453b, com.tencent.liteav.basic.datareport.a.aw);
        return 0;
    }

    @Override // com.tencent.liteav.n
    public long f() {
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            return tXCRenderAndDec.getCurrentRenderPts();
        }
        return 0L;
    }

    @Override // com.tencent.liteav.n
    public void b(int i2) {
        this.f19303n = i2;
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setRenderRotation(i2);
        }
    }

    @Override // com.tencent.liteav.n
    public void c(boolean z2) {
        this.f19301l = z2;
        TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(this.O, z2);
    }

    @Override // com.tencent.liteav.n
    public void c(int i2) {
        this.f19302m = i2;
        TXCAudioEngine.getInstance().setRemotePlayoutVolume(this.O, this.f19302m);
    }

    @Override // com.tencent.liteav.n
    public int d() {
        if (!this.f19310u) {
            TXCLog.w("TXCLivePlayer", "stopRecord: no recording task exist");
            return -1;
        }
        this.f19310u = false;
        y();
        com.tencent.liteav.a.a aVar = this.f19314y;
        if (aVar != null) {
            aVar.a();
            this.f19314y = null;
        }
        return 0;
    }

    @Override // com.tencent.liteav.n
    public void b(boolean z2) {
        this.f19300k = z2;
        TXCAudioEngine.getInstance().muteRemoteAudio(this.O, this.f19300k);
    }

    private int b(String str, int i2) {
        if (i2 == 0) {
            this.f19296g = new TXCStreamDownloader(this.f19453b, 1);
        } else if (i2 == 5) {
            this.f19296g = new TXCStreamDownloader(this.f19453b, 4);
        } else {
            this.f19296g = new TXCStreamDownloader(this.f19453b, 0);
            if (!TextUtils.isEmpty(this.f19452a.f19367l)) {
                this.f19296g.setFlvSessionKey(this.f19452a.f19367l);
            }
        }
        this.f19296g.setID(this.O);
        this.f19296g.setListener(this);
        this.f19296g.setNotifyListener(this);
        this.f19296g.setHeaders(this.f19452a.f19371p);
        this.f19296g.setPayloadType(this.f19297h);
        if (i2 == 5) {
            this.f19296g.setRetryTimes(5);
            this.f19296g.setRetryInterval(1);
        } else {
            this.f19296g.setRetryTimes(this.f19452a.f19360e);
            this.f19296g.setRetryInterval(this.f19452a.f19361f);
        }
        TXCStreamDownloader tXCStreamDownloader = this.f19296g;
        h hVar = this.f19452a;
        return tXCStreamDownloader.start(str, hVar.f19364i, hVar.f19368m, hVar.f19365j, hVar.f19366k);
    }

    @Override // com.tencent.liteav.n
    public void a(Surface surface) {
        this.f19306q = surface;
        com.tencent.liteav.renderer.a aVar = this.f19295f;
        if (aVar != null) {
            aVar.a(surface);
        }
    }

    @Override // com.tencent.liteav.n
    public void g() {
        this.R = 0L;
        if (this.P) {
            return;
        }
        this.P = true;
        y();
        Handler handler = this.f19298i;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.f.3
                @Override // java.lang.Runnable
                public void run() {
                    if (f.this.P) {
                        f.this.u();
                    }
                }
            }, 1000L);
        }
    }

    @Override // com.tencent.liteav.n
    public void a(int i2, int i3) {
        com.tencent.liteav.renderer.a aVar = this.f19295f;
        if (aVar != null) {
            aVar.d(i2, i3);
        }
    }

    @Override // com.tencent.liteav.n
    public void a(h hVar) {
        super.a(hVar);
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setConfig(hVar);
        }
    }

    @Override // com.tencent.liteav.n
    public int a(String str, int i2) throws ClassNotFoundException {
        com.tencent.liteav.renderer.a aVar;
        if (c()) {
            TXCLog.w("TXCLivePlayer", "play: ignore start play when is playing");
            return -2;
        }
        h hVar = this.f19452a;
        if (hVar != null && hVar.f19358c > hVar.f19357b) {
            TXCLog.e("TXCLivePlayer", "play: can not start play while invalid cache config [minAutoAdjustCacheTime(" + this.f19452a.f19358c + ") > maxAutoAdjustCacheTime(" + this.f19452a.f19357b + ")]!!!!!!");
            return -1;
        }
        float f2 = hVar.f19356a;
        if (f2 > hVar.f19357b || f2 < hVar.f19358c) {
            TXCLog.w("TXCLivePlayer", "play: invalid cacheTime " + this.f19452a.f19356a + ", need between minAutoAdjustCacheTime " + this.f19452a.f19358c + " and maxAutoAdjustCacheTime " + this.f19452a.f19357b + " , fix to maxAutoAdjustCacheTime");
            h hVar2 = this.f19452a;
            hVar2.f19356a = hVar2.f19357b;
        }
        TXCLog.i("TXCLivePlayer", "[FirstFramePath] TXCLivePlayer: start play. instance: " + hashCode());
        this.H = str;
        this.I = i2;
        b(str);
        this.f19305p = true;
        this.f19313x = 0;
        this.f19311v = true;
        g(i2);
        o();
        int iB = b(str, i2);
        if (iB != 0) {
            this.f19305p = false;
            q();
            n();
            p();
            TextureView textureView = this.f19299j;
            if (textureView != null) {
                textureView.setVisibility(8);
            }
        } else {
            a(this.f19306q);
            r();
            v();
            if (this.K == com.tencent.liteav.basic.enums.b.TEXTURE_2D && this.f19299j == null && (aVar = this.f19295f) != null) {
                aVar.c(this.L);
            }
            com.tencent.liteav.renderer.a aVar2 = this.f19295f;
            if (aVar2 != null) {
                aVar2.b(true);
            }
            try {
                Class.forName("com.tencent.liteav.demo.play.SuperPlayerView");
                TXCDRApi.txReportDAU(this.f19453b, com.tencent.liteav.basic.datareport.a.bF);
            } catch (Exception unused) {
            }
        }
        return iB;
    }

    private void b(String str) {
        String str2 = String.format("%s-%d", str, Long.valueOf(TXCTimeUtil.getTimeTick() % com.heytap.mcssdk.constant.a.f7153q));
        this.O = str2;
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setID(str2);
        }
        com.tencent.liteav.renderer.a aVar = this.f19295f;
        if (aVar != null) {
            aVar.setID(this.O);
        }
        TXCStreamDownloader tXCStreamDownloader = this.f19296g;
        if (tXCStreamDownloader != null) {
            tXCStreamDownloader.setID(this.O);
        }
        e eVar = this.A;
        if (eVar != null) {
            eVar.d(this.O);
        }
    }

    @Override // com.tencent.liteav.n
    public int a(boolean z2) {
        com.tencent.liteav.renderer.a aVar;
        if (!c()) {
            TXCLog.w("TXCLivePlayer", "play: ignore stop play when not started");
            return -2;
        }
        TXCLog.v("TXCLivePlayer", "play: stop");
        this.f19305p = false;
        q();
        com.tencent.liteav.renderer.a aVar2 = this.f19295f;
        if (aVar2 != null) {
            aVar2.b(!z2);
        }
        n();
        TextureView textureView = this.f19299j;
        if (textureView != null && z2) {
            textureView.setVisibility(8);
        }
        com.tencent.liteav.renderer.a aVar3 = this.f19295f;
        if (aVar3 != null) {
            aVar3.a((Surface) null);
        }
        if (this.f19299j == null && (aVar = this.f19295f) != null) {
            aVar.d();
        }
        p();
        s();
        w();
        t();
        return 0;
    }

    @Override // com.tencent.liteav.n
    public int a(String str) {
        TXCStreamDownloader tXCStreamDownloader;
        if (!c() || (tXCStreamDownloader = this.f19296g) == null) {
            return -1;
        }
        if (this.I == 5) {
            tXCStreamDownloader.setRetryTimes(5);
            this.f19296g.setRetryInterval(1);
        } else {
            tXCStreamDownloader.setRetryTimes(this.f19452a.f19360e);
            this.f19296g.setRetryInterval(this.f19452a.f19361f);
        }
        boolean zSwitchStream = this.f19296g.switchStream(str);
        long jC = TXCStatus.c(this.O, 2007);
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        TXCLog.i("TXCLivePlayer", "[SwitchStream] current jitter size when start switch stream. video:" + (tXCRenderAndDec != null ? tXCRenderAndDec.getVideoCacheDuration() : 0L) + " audio:" + jC);
        if (!zSwitchStream) {
            return -2;
        }
        this.H = str;
        return 0;
    }

    @Override // com.tencent.liteav.n
    public void a() {
        a(false);
    }

    @Override // com.tencent.liteav.n
    public void a(int i2) {
        this.f19304o = i2;
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setRenderMode(i2);
        }
    }

    @Override // com.tencent.liteav.n
    public void a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        this.f19315z = iTXVideoRecordListener;
    }

    @Override // com.tencent.liteav.n
    public void a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
        this.N = iTXAudioRawDataListener;
        y();
    }

    @Override // com.tencent.liteav.n
    public void a(o oVar, com.tencent.liteav.basic.enums.b bVar, Object obj) {
        com.tencent.liteav.renderer.a aVar;
        this.K = bVar;
        this.L = obj;
        if (c() && this.K == com.tencent.liteav.basic.enums.b.TEXTURE_2D && this.f19299j == null && oVar != null && (aVar = this.f19295f) != null) {
            aVar.c(this.L);
        }
        TXCRenderAndDec tXCRenderAndDec = this.f19294e;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setVideoFrameListener(oVar, bVar);
        } else {
            TXCLog.w("TXCLivePlayer", "setVideoFrameListener->enter with renderAndDec is empty");
        }
    }

    @Override // com.tencent.liteav.n
    public void a(com.tencent.liteav.basic.opengl.p pVar) {
        com.tencent.liteav.renderer.a aVar = this.f19295f;
        if (aVar != null) {
            aVar.a(pVar);
        }
    }

    private void a(final int i2, String str) {
        if (this.f19455d != null) {
            final Bundle bundle = new Bundle();
            bundle.putInt("EVT_ID", i2);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
            if (str != null) {
                bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
            }
            Handler handler = this.f19298i;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.tencent.liteav.f.5
                    @Override // java.lang.Runnable
                    public void run() {
                        com.tencent.liteav.basic.util.h.a(f.this.f19455d, i2, bundle);
                    }
                });
            }
        }
    }

    @Override // com.tencent.liteav.renderer.g
    public int a(int i2, float[] fArr) {
        com.tencent.liteav.renderer.h hVar;
        com.tencent.liteav.a.a aVar = this.f19314y;
        if (this.f19310u && aVar != null && (hVar = this.D) != null) {
            int iD = hVar.d(i2);
            aVar.a(iD, TXCTimeUtil.getTimeTick());
            this.f19295f.a(iD, this.B, this.C, false, 0);
        }
        if (this.f19310u) {
            k();
        } else {
            l();
        }
        return i2;
    }

    @Override // com.tencent.liteav.TXCRenderAndDec.a
    public void a(SurfaceTexture surfaceTexture) {
        l();
        d();
    }

    @Override // com.tencent.liteav.n
    public void a(boolean z2, int i2) {
        this.J = z2;
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(z2, i2);
    }
}
