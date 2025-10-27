package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.basic.b.b;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.h;
import com.tencent.liteav.j;
import com.tencent.liteav.n;
import com.tencent.liteav.o;
import com.tencent.liteav.p;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import java.io.UnsupportedEncodingException;
import javax.microedition.khronos.egl.EGLContext;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class a implements b {
    private long A;
    private String B;
    private long G;
    private String H;
    private int I;
    private TXLivePlayer.ITXAudioRawDataListener L;

    /* renamed from: a, reason: collision with root package name */
    private TXCloudVideoView f20701a;

    /* renamed from: b, reason: collision with root package name */
    private Surface f20702b;

    /* renamed from: c, reason: collision with root package name */
    private int f20703c;

    /* renamed from: d, reason: collision with root package name */
    private int f20704d;

    /* renamed from: f, reason: collision with root package name */
    private TXLivePlayConfig f20706f;

    /* renamed from: i, reason: collision with root package name */
    private int f20709i;

    /* renamed from: j, reason: collision with root package name */
    private int f20710j;

    /* renamed from: s, reason: collision with root package name */
    private Context f20719s;

    /* renamed from: t, reason: collision with root package name */
    private Handler f20720t;

    /* renamed from: u, reason: collision with root package name */
    private n f20721u;

    /* renamed from: y, reason: collision with root package name */
    private j f20725y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f20726z;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20707g = true;

    /* renamed from: h, reason: collision with root package name */
    private boolean f20708h = true;

    /* renamed from: k, reason: collision with root package name */
    private String f20711k = "";

    /* renamed from: l, reason: collision with root package name */
    private boolean f20712l = false;

    /* renamed from: m, reason: collision with root package name */
    private int f20713m = 100;

    /* renamed from: n, reason: collision with root package name */
    private int f20714n = 0;

    /* renamed from: o, reason: collision with root package name */
    private TXLivePlayer.ITXVideoRawDataListener f20715o = null;

    /* renamed from: p, reason: collision with root package name */
    private byte[] f20716p = null;

    /* renamed from: q, reason: collision with root package name */
    private Object f20717q = null;

    /* renamed from: r, reason: collision with root package name */
    private TXLivePlayer.ITXLivePlayVideoRenderListener f20718r = null;

    /* renamed from: v, reason: collision with root package name */
    private boolean f20722v = true;

    /* renamed from: w, reason: collision with root package name */
    private float f20723w = 1.0f;

    /* renamed from: x, reason: collision with root package name */
    private boolean f20724x = false;
    private int C = -1;
    private long D = 0;
    private TXLivePlayer.ITXAudioVolumeEvaluationListener E = null;
    private int F = 0;
    private int J = 0;
    private RunnableC0352a K = null;

    /* renamed from: e, reason: collision with root package name */
    private ITXLivePlayListener f20705e = null;

    /* renamed from: com.tencent.rtmp.a$a, reason: collision with other inner class name */
    public class RunnableC0352a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private int f20736b;

        private RunnableC0352a() {
            this.f20736b = 300;
        }

        public void a(int i2) {
            this.f20736b = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (a.this.f20721u != null && a.this.f20721u.c()) {
                int i2 = a.this.f20721u.i();
                if (a.this.E != null) {
                    a.this.E.onAudioVolumeEvaluationNotify(i2);
                }
            }
            if (a.this.f20720t == null || this.f20736b <= 0) {
                return;
            }
            a.this.f20720t.postDelayed(a.this.K, this.f20736b);
        }
    }

    public a(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f20719s = applicationContext;
        TXCCommonUtil.setAppContext(applicationContext);
        TXCLog.init();
        this.f20720t = new Handler(Looper.getMainLooper());
        TXCCommonUtil.setAppContext(this.f20719s);
        TXCLog.init();
    }

    private void h() {
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(false, 0);
        }
        Handler handler = this.f20720t;
        if (handler != null) {
            handler.removeCallbacks(this.K);
        }
        this.K = null;
        this.F = 0;
    }

    private void i() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.G > C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS) {
            this.G = jCurrentTimeMillis;
            c("[Statistics] logStatisticsStr statistics:" + this.H);
        }
    }

    private boolean j() {
        return TXCBuild.Manufacturer().equalsIgnoreCase("HUAWEI") && TXCBuild.Model().equalsIgnoreCase("Che2-TL00");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00af A[PHI: r2
      0x00af: PHI (r2v1 int) = (r2v0 int), (r2v2 int), (r2v2 int) binds: [B:18:0x0046, B:20:0x004a, B:41:0x0076] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00b9 A[PHI: r3
      0x00b9: PHI (r3v1 int) = (r3v0 int), (r3v2 int), (r3v6 int) binds: [B:22:0x004e, B:24:0x0052, B:32:0x0062] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.tencent.liteav.basic.b.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onNotifyEvent(int r6, android.os.Bundle r7) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.a.onNotifyEvent(int, android.os.Bundle):void");
    }

    private void c(String str) {
        if (str == null) {
            str = "";
        }
        TXCLog.i(TXLivePlayer.TAG, "[API] TXLivePlayer(" + hashCode() + ") " + str);
    }

    private void g() {
        n nVar = this.f20721u;
        if (nVar != null) {
            int i2 = this.F;
            nVar.a(i2 > 0, i2);
            if (this.F > 0) {
                if (this.K == null) {
                    this.K = new RunnableC0352a();
                }
                this.K.a(this.F);
                Handler handler = this.f20720t;
                if (handler != null) {
                    handler.removeCallbacks(this.K);
                    this.f20720t.postDelayed(this.K, this.F);
                }
            }
        }
    }

    public void b() {
        c("pause");
        if (this.f20721u != null) {
            TXCLog.w(TXLivePlayer.TAG, "pause play");
            this.f20721u.a();
        }
    }

    public void d(int i2) {
        c("setAudioRoute route:" + i2);
        TXCAudioEngine.setAudioRoute(i2);
    }

    public void e(int i2) {
        c("enableAudioVolumeEvaluation intervalMs:" + i2);
        if (i2 <= 0) {
            this.F = 0;
            h();
        } else {
            if (i2 < 100) {
                i2 = 100;
            }
            this.F = i2;
            g();
        }
    }

    public int f(int i2) {
        c("startRecord type:" + i2);
        if (TXCBuild.VersionInt() < 18) {
            TXCLog.e(TXLivePlayer.TAG, "API levl is too low (record need 18, current is" + TXCBuild.VersionInt() + ")");
            return -3;
        }
        if (!a()) {
            TXCLog.e(TXLivePlayer.TAG, "startRecord: there is no playing stream");
            return -1;
        }
        n nVar = this.f20721u;
        if (nVar != null) {
            return nVar.e(i2);
        }
        return -1;
    }

    public void c() {
        c("resume");
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.b();
            if (this.f20721u.e()) {
                j jVar = this.f20725y;
                long jA = jVar != null ? jVar.a() : 0L;
                this.A = jA;
                if (jA > 0) {
                    this.f20721u.g();
                }
            }
        }
    }

    public int d() {
        c("stopRecord");
        n nVar = this.f20721u;
        if (nVar != null) {
            return nVar.d();
        }
        return -1;
    }

    public void a(TXLivePlayConfig tXLivePlayConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("setConfig");
        sb.append(tXLivePlayConfig != null ? tXLivePlayConfig.toString() : null);
        c(sb.toString());
        this.f20706f = tXLivePlayConfig;
        if (tXLivePlayConfig == null) {
            this.f20706f = new TXLivePlayConfig();
        }
        n nVar = this.f20721u;
        if (nVar != null) {
            h hVarJ = nVar.j();
            if (hVarJ == null) {
                hVarJ = new h();
            }
            TXLivePlayConfig tXLivePlayConfig2 = this.f20706f;
            float f2 = tXLivePlayConfig2.mMinAutoAdjustCacheTime;
            this.I = (int) (1000.0f * f2);
            hVarJ.f19356a = tXLivePlayConfig2.mCacheTime;
            hVarJ.f19362g = tXLivePlayConfig2.mAutoAdjustCacheTime;
            hVarJ.f19358c = f2;
            hVarJ.f19357b = tXLivePlayConfig2.mMaxAutoAdjustCacheTime;
            hVarJ.f19359d = tXLivePlayConfig2.mVideoBlockThreshold;
            hVarJ.f19360e = tXLivePlayConfig2.mConnectRetryCount;
            hVarJ.f19361f = tXLivePlayConfig2.mConnectRetryInterval;
            hVarJ.f19364i = tXLivePlayConfig2.mEnableNearestIP;
            hVarJ.f19368m = tXLivePlayConfig2.mRtmpChannelType;
            hVarJ.f19363h = this.f20707g;
            hVarJ.f19369n = tXLivePlayConfig2.mCacheFolderPath;
            hVarJ.f19370o = tXLivePlayConfig2.mMaxCacheItems;
            hVarJ.f19365j = tXLivePlayConfig2.mEnableMessage;
            hVarJ.f19366k = tXLivePlayConfig2.mEnableMetaData;
            hVarJ.f19367l = tXLivePlayConfig2.mFlvSessionKey;
            hVarJ.f19371p = tXLivePlayConfig2.mHeaders;
            TXCLog.i(TXLivePlayer.TAG, "liteav_api setConfig [cacheTime:" + this.f20706f.mCacheTime + "][autoAdjustCacheTime:" + this.f20706f.mAutoAdjustCacheTime + "][minAutoAdjustCacheTime:" + this.f20706f.mMinAutoAdjustCacheTime + "][maxAutoAdjustCacheTime:" + this.f20706f.mMaxAutoAdjustCacheTime + "][videoBlockThreshold:" + this.f20706f.mVideoBlockThreshold + "][connectRetryCount:" + this.f20706f.mConnectRetryCount + "][connectRetryInterval:" + this.f20706f.mConnectRetryInterval + "][enableHWDec:" + this.f20707g + "][enableMessage:" + this.f20706f.mEnableMessage + "][enableMetaData:" + this.f20706f.mEnableMetaData + "][flvSessionKey:" + this.f20706f.mFlvSessionKey);
            this.f20721u.a(hVarJ);
        }
    }

    public void b(int i2) {
        c("setRenderRotation rotation:" + i2);
        this.f20710j = i2;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.b(i2);
        }
    }

    @Deprecated
    public void d(boolean z2) {
        TXCLog.i(TXLivePlayer.TAG, "setAutoPlay " + z2);
        this.f20722v = z2;
    }

    public int e() {
        c("resumeLive");
        if (!this.f20726z) {
            return -1;
        }
        this.f20726z = false;
        return a(this.B, 1);
    }

    public long f() {
        n nVar = this.f20721u;
        if (nVar != null) {
            return nVar.f();
        }
        return 0L;
    }

    public boolean b(boolean z2) {
        c("enableHardwareDecode enable:" + z2);
        if (z2) {
            if (TXCBuild.VersionInt() < 18) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, android system build.version = " + TXCBuild.VersionInt() + ", the minimum build.version should be 18(android 4.3 or later)");
                return false;
            }
            if (j()) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, MANUFACTURER = " + TXCBuild.Manufacturer() + ", MODEL" + TXCBuild.Model());
                return false;
            }
        }
        this.f20707g = z2;
        n nVar = this.f20721u;
        if (nVar == null) {
            return true;
        }
        h hVarJ = nVar.j();
        if (hVarJ == null) {
            hVarJ = new h();
        }
        hVarJ.f19363h = this.f20707g;
        this.f20721u.a(hVarJ);
        return true;
    }

    public void c(boolean z2) {
        c("setMute mute:" + z2);
        this.f20712l = z2;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.b(z2);
        }
    }

    public void g(int i2) {
        c("seek time:" + i2);
        n nVar = this.f20721u;
        if (nVar != null) {
            if (!nVar.e() && !this.f20726z) {
                this.f20721u.f(i2);
                return;
            }
            j jVar = this.f20725y;
            String strA = jVar != null ? jVar.a(i2) : "";
            if (!TextUtils.isEmpty(strA)) {
                boolean z2 = a(strA, 3) == 0;
                this.f20726z = z2;
                if (z2) {
                    this.A = i2 * 1000;
                    return;
                }
                return;
            }
            ITXLivePlayListener iTXLivePlayListener = this.f20705e;
            if (iTXLivePlayListener != null) {
                iTXLivePlayListener.onPlayEvent(-2301, new Bundle());
            }
        }
    }

    public void c(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > 100) {
            i2 = 100;
        }
        c("setVolume volume:" + i2);
        this.f20713m = i2;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.c(i2);
        }
    }

    private String c(String str, int i2) throws UnsupportedEncodingException {
        if (i2 != 6) {
            str = TXCCommonUtil.tryEncodeUrl(str);
        }
        return str.trim();
    }

    public void b(String str) throws JSONException {
        c("callExperimentalAPI json:" + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("api")) {
                TXCLog.e(TXLivePlayer.TAG, "callExperimentalAPI[lack api or illegal type]: " + str);
                return;
            }
            String string = jSONObject.getString("api");
            JSONObject jSONObject2 = jSONObject.has("params") ? jSONObject.getJSONObject("params") : null;
            if (string.equals("muteRemoteAudioInSpeaker")) {
                if (jSONObject2 == null) {
                    TXCLog.e(TXLivePlayer.TAG, "muteRemoteAudioInSpeaker[lack parameter]");
                    return;
                }
                if (!jSONObject2.has("enable")) {
                    TXCLog.e(TXLivePlayer.TAG, "muteRemoteAudioInSpeaker[lack parameter]: enable");
                    return;
                }
                int i2 = jSONObject2.getInt("enable");
                n nVar = this.f20721u;
                if (nVar != null) {
                    boolean z2 = true;
                    if (i2 != 1) {
                        z2 = false;
                    }
                    nVar.c(z2);
                }
            }
            if (string.equals("setInterfaceType")) {
                if (jSONObject2 == null) {
                    TXCLog.e(TXLivePlayer.TAG, "setInterfaceType[lack parameter]");
                    return;
                } else {
                    if (!jSONObject2.has("type")) {
                        TXCLog.e(TXLivePlayer.TAG, "setInterfaceType[lack parameter]: type");
                        return;
                    }
                    this.J = jSONObject2.optInt("type", 0);
                }
            }
            if (string.equals("setSEIPayloadType")) {
                if (jSONObject2 != null && jSONObject2.has("payloadType")) {
                    int i3 = jSONObject2.getInt("payloadType");
                    if (i3 != 5 && i3 != 242 && i3 != 100 && i3 != 243) {
                        TXCLog.e(TXLivePlayer.TAG, "callExperimentalAPI[invalid param]: payloadType[" + i3 + StrPool.BRACKET_END);
                        return;
                    }
                    this.f20714n = i3;
                    n nVar2 = this.f20721u;
                    if (nVar2 != null) {
                        nVar2.d(i3);
                        return;
                    }
                    return;
                }
                TXCLog.e(TXLivePlayer.TAG, "callExperimentalAPI[lack parameter or illegal type]: payloadType");
            }
        } catch (Exception unused) {
            TXCLog.e(TXLivePlayer.TAG, "callExperimentalAPI[failed]: " + str);
        }
    }

    public void a(ITXLivePlayListener iTXLivePlayListener) {
        c("setPlayListener listener:" + iTXLivePlayListener);
        this.f20705e = iTXLivePlayListener;
    }

    public void a(TXCloudVideoView tXCloudVideoView) {
        c("setPlayerView old:" + this.f20701a + " new:" + tXCloudVideoView);
        this.f20701a = tXCloudVideoView;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(tXCloudVideoView);
        }
    }

    public int a(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            TXCLog.e(TXLivePlayer.TAG, "start play error when url is empty " + this);
            return -1;
        }
        if (!TextUtils.isEmpty(this.f20711k)) {
            if (this.f20711k.equalsIgnoreCase(str) && a()) {
                TXCLog.e(TXLivePlayer.TAG, "start play error when new url is the same with old url  " + this);
                if (this.f20726z) {
                    onNotifyEvent(2004, new Bundle());
                }
                return this.f20726z ? 0 : -1;
            }
            TXCLog.w(TXLivePlayer.TAG, " stop old play when new url is not the same with old url  " + this);
            n nVar = this.f20721u;
            if (nVar != null) {
                nVar.a(false);
            }
            this.f20711k = "";
        }
        TXCDRApi.initCrashReport(this.f20719s);
        TXCLog.i(TXLivePlayer.TAG, "===========================================================================================================================================================");
        TXCLog.i(TXLivePlayer.TAG, "===========================================================================================================================================================");
        TXCLog.i(TXLivePlayer.TAG, "=====  StartPlay url = " + str + " playType = " + i2 + " DeviceName = " + TXCBuild.Model() + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.i(TXLivePlayer.TAG, "===========================================================================================================================================================");
        TXCLog.i(TXLivePlayer.TAG, "===========================================================================================================================================================");
        int i3 = this.C;
        if (i3 == -1 || i3 != i2) {
            this.f20721u = p.a(this.f20719s, i2);
        }
        this.C = i2;
        if (this.f20721u == null) {
            TXCLog.i(TXLivePlayer.TAG, "liteav_api startPlay create palyer failed" + this);
            return -2;
        }
        this.f20711k = c(str, i2);
        c("startPlay url:" + str + " type:" + i2);
        a(this.f20706f);
        TXCloudVideoView tXCloudVideoView = this.f20701a;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.clearLog();
            this.f20701a.setVisibility(0);
        }
        this.f20721u.a(this.f20701a);
        this.f20721u.a(this);
        this.f20721u.d(this.f20722v);
        Surface surface = this.f20702b;
        if (surface != null) {
            this.f20721u.a(surface);
            this.f20721u.a(this.f20703c, this.f20704d);
        }
        this.f20721u.a(this.f20711k, i2);
        this.f20721u.b(this.f20712l);
        this.f20721u.c(this.f20713m);
        this.f20721u.d(this.f20714n);
        this.f20721u.a(this.f20723w);
        this.f20721u.b(this.f20710j);
        this.f20721u.a(this.f20709i);
        this.f20721u.a(this.L);
        TXLivePlayer.ITXVideoRawDataListener iTXVideoRawDataListener = this.f20715o;
        if (iTXVideoRawDataListener != null) {
            a(iTXVideoRawDataListener);
        }
        TXLivePlayer.ITXLivePlayVideoRenderListener iTXLivePlayVideoRenderListener = this.f20718r;
        if (iTXLivePlayVideoRenderListener != null) {
            a(iTXLivePlayVideoRenderListener, this.f20717q);
        }
        if (this.f20721u.e()) {
            this.B = this.f20711k;
            j jVar = this.f20725y;
            long jA = jVar != null ? jVar.a() : 0L;
            this.A = jA;
            if (jA > 0) {
                this.f20721u.g();
            }
        }
        if (this.J == 0) {
            TXCDRApi.txReportDAU(this.f20719s.getApplicationContext(), com.tencent.liteav.basic.datareport.a.bu);
        } else {
            TXCDRApi.txReportDAU(this.f20719s.getApplicationContext(), com.tencent.liteav.basic.datareport.a.bH);
        }
        g();
        return 0;
    }

    public int b(String str, int i2) {
        c("prepareLiveSeek domain:" + str + " bizid:" + i2);
        if (this.f20725y == null) {
            this.f20725y = new j();
        }
        j jVar = this.f20725y;
        if (jVar != null) {
            return jVar.a(this.f20711k, str, i2, new j.a() { // from class: com.tencent.rtmp.a.4
                @Override // com.tencent.liteav.j.a
                public void a(long j2) {
                    a.this.A = j2;
                    if (a.this.f20721u != null) {
                        a.this.f20721u.g();
                    }
                }
            });
        }
        return -1;
    }

    public int a(boolean z2) {
        TXCloudVideoView tXCloudVideoView;
        c("stopPlay need clear:" + z2);
        if (z2 && (tXCloudVideoView = this.f20701a) != null) {
            tXCloudVideoView.setVisibility(8);
        }
        h();
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(z2);
        }
        this.f20711k = "";
        this.A = 0L;
        this.J = 0;
        this.f20725y = null;
        this.f20726z = false;
        return 0;
    }

    public boolean a() {
        n nVar = this.f20721u;
        if (nVar != null) {
            return nVar.c();
        }
        return false;
    }

    public void a(Surface surface) {
        c("setSurface old:" + this.f20702b + " new:" + surface);
        this.f20702b = surface;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(surface);
        }
    }

    public void a(int i2, int i3) {
        c("setSurfaceSize width:" + i2 + " height:" + i3);
        this.f20703c = i2;
        this.f20704d = i3;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(i2, i3);
        }
    }

    public void a(int i2) {
        c("setRenderMode mode:" + i2);
        this.f20709i = i2;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(i2);
        }
    }

    public int a(String str) {
        c("[SwitchStream]switchStream url:" + str);
        n nVar = this.f20721u;
        if (nVar != null) {
            return nVar.a(str);
        }
        return -1;
    }

    public void a(TXLivePlayer.ITXAudioVolumeEvaluationListener iTXAudioVolumeEvaluationListener) {
        this.E = iTXAudioVolumeEvaluationListener;
    }

    public void a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        c("setVideoRecordListener listener:" + iTXVideoRecordListener);
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(iTXVideoRecordListener);
        }
    }

    public void a(final TXLivePlayer.ITXSnapshotListener iTXSnapshotListener) {
        c("snapshot listener:" + iTXSnapshotListener);
        if (this.f20724x || iTXSnapshotListener == null) {
            return;
        }
        this.f20724x = true;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(new com.tencent.liteav.basic.opengl.p() { // from class: com.tencent.rtmp.a.1
                @Override // com.tencent.liteav.basic.opengl.p
                public void onTakePhotoComplete(Bitmap bitmap) {
                    a.this.a(iTXSnapshotListener, bitmap);
                }
            });
        } else {
            this.f20724x = false;
        }
    }

    public boolean a(byte[] bArr) {
        String str = this.f20711k;
        if (str == null || str.isEmpty()) {
            return false;
        }
        if (this.f20707g) {
            TXLog.e(TXLivePlayer.TAG, "can not addVideoRawData because of hw decode has set!");
            return false;
        }
        if (this.f20721u == null) {
            TXCLog.e(TXLivePlayer.TAG, "player hasn't created or not instanceof live player");
            return false;
        }
        this.f20716p = bArr;
        return true;
    }

    public int a(TXLivePlayer.ITXLivePlayVideoRenderListener iTXLivePlayVideoRenderListener, Object obj) {
        c("setVideoRenderListener listener:" + iTXLivePlayVideoRenderListener + " context:" + obj);
        if (obj != null) {
            if (TXCBuild.VersionInt() >= 17) {
                if (!(obj instanceof EGLContext) && !(obj instanceof android.opengl.EGLContext)) {
                    TXCLog.w(TXLivePlayer.TAG, "setVideoRenderListener error when glContext error " + obj);
                    return -1;
                }
            } else if (!(obj instanceof EGLContext)) {
                TXCLog.w(TXLivePlayer.TAG, "setVideoRenderListener error when glContext error " + obj);
                return -1;
            }
        }
        this.f20717q = obj;
        this.f20718r = iTXLivePlayVideoRenderListener;
        n nVar = this.f20721u;
        if (nVar == null) {
            return 0;
        }
        if (iTXLivePlayVideoRenderListener != null) {
            nVar.a(new o() { // from class: com.tencent.rtmp.a.2
                @Override // com.tencent.liteav.o
                public void onRenderVideoFrame(String str, int i2, TXSVideoFrame tXSVideoFrame) {
                    TXLivePlayer.ITXLivePlayVideoRenderListener iTXLivePlayVideoRenderListener2;
                    if (tXSVideoFrame == null || tXSVideoFrame.width <= 0 || tXSVideoFrame.height <= 0 || (iTXLivePlayVideoRenderListener2 = a.this.f20718r) == null) {
                        return;
                    }
                    TXLivePlayer.TXLiteAVTexture tXLiteAVTexture = new TXLivePlayer.TXLiteAVTexture();
                    tXLiteAVTexture.textureId = tXSVideoFrame.textureId;
                    tXLiteAVTexture.width = tXSVideoFrame.width;
                    tXLiteAVTexture.height = tXSVideoFrame.height;
                    tXLiteAVTexture.eglContext = tXSVideoFrame.eglContext;
                    iTXLivePlayVideoRenderListener2.onRenderVideoFrame(tXLiteAVTexture);
                }
            }, com.tencent.liteav.basic.enums.b.TEXTURE_2D, obj);
            return 0;
        }
        nVar.a(null, com.tencent.liteav.basic.enums.b.UNKNOWN, null);
        return 0;
    }

    public void a(TXLivePlayer.ITXVideoRawDataListener iTXVideoRawDataListener) {
        c("setVideoRawDataListener listener:" + iTXVideoRawDataListener);
        this.f20715o = iTXVideoRawDataListener;
        n nVar = this.f20721u;
        if (nVar == null) {
            return;
        }
        if (iTXVideoRawDataListener != null) {
            nVar.a(new o() { // from class: com.tencent.rtmp.a.3
                @Override // com.tencent.liteav.o
                public void onRenderVideoFrame(String str, int i2, TXSVideoFrame tXSVideoFrame) {
                    if (tXSVideoFrame == null || tXSVideoFrame.width <= 0 || tXSVideoFrame.height <= 0) {
                        return;
                    }
                    byte[] bArr = a.this.f20716p;
                    a.this.f20716p = null;
                    TXLivePlayer.ITXVideoRawDataListener iTXVideoRawDataListener2 = a.this.f20715o;
                    if (iTXVideoRawDataListener2 == null || bArr == null) {
                        return;
                    }
                    if (bArr.length < ((tXSVideoFrame.width * tXSVideoFrame.height) * 3) / 2) {
                        TXCLog.e(TXLivePlayer.TAG, "raw data buffer length is too large");
                        return;
                    }
                    tXSVideoFrame.loadYUVArray(bArr);
                    iTXVideoRawDataListener2.onVideoRawDataAvailable(bArr, tXSVideoFrame.width, tXSVideoFrame.height, (int) tXSVideoFrame.pts);
                    tXSVideoFrame.release();
                }
            }, com.tencent.liteav.basic.enums.b.I420, null);
        } else {
            nVar.a(null, com.tencent.liteav.basic.enums.b.UNKNOWN, null);
        }
    }

    public void a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
        c("setAudioRawDataListener listener:" + iTXAudioRawDataListener);
        this.L = iTXAudioRawDataListener;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(iTXAudioRawDataListener);
        }
    }

    @Deprecated
    public void a(float f2) {
        TXCLog.i(TXLivePlayer.TAG, "setRate " + f2);
        this.f20723w = f2;
        n nVar = this.f20721u;
        if (nVar != null) {
            nVar.a(f2);
        }
    }

    private void a(int i2, Bundle bundle) {
        if (i2 != 15001 || bundle == null) {
            if (i2 == 2007 || i2 == 2105) {
                TXCLog.i(TXLivePlayer.TAG, "[Event]code:" + i2 + " param:" + bundle);
                i();
                return;
            }
            return;
        }
        String str = this.f20711k;
        if (str == null) {
            return;
        }
        boolean zStartsWith = str.startsWith("room://");
        this.H = a(bundle);
        int i3 = bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE, 0);
        int i4 = bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED, 0);
        int i5 = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS, 0);
        int i6 = bundle.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL);
        int i7 = bundle.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL);
        if ((zStartsWith || this.I <= i3) && i4 >= 10 && ((i5 == 0 || i5 >= 5) && Math.abs(i6) <= 5000 && Math.abs(i7) <= 5000)) {
            return;
        }
        i();
    }

    private String a(Bundle bundle) {
        return " IP:" + bundle.getString(TXLiveConstants.NET_STATUS_SERVER_IP) + " RES:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) + " FPS:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) + " GOP:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s Speed:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps AudioSpeed:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps VideoSpeed:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps AudioCache:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE) + " VideoCache:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE) + " VideoCacheFrameCount:" + bundle.getInt(TXLiveConstants.NET_STATUS_V_SUM_CACHE_SIZE) + " VideoDecoderCacheFrameCount:" + bundle.getInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE) + " AVJitterSync:" + bundle.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL) + " AVPlaySync:" + bundle.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL) + " AudioParamsInfo:" + bundle.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final TXLivePlayer.ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.a.5
            @Override // java.lang.Runnable
            public void run() {
                TXLivePlayer.ITXSnapshotListener iTXSnapshotListener2 = iTXSnapshotListener;
                if (iTXSnapshotListener2 != null) {
                    iTXSnapshotListener2.onSnapshot(bitmap);
                }
                a.this.f20724x = false;
            }
        });
    }
}
