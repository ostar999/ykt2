package com.tencent.liteav.videoengine.decoder;

import android.os.Build;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoengine.decoder.VideoDecodeController;
import com.tencent.liteav.videoengine.decoder.n;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    @NonNull
    private final d f20293a;

    /* renamed from: b, reason: collision with root package name */
    @NonNull
    private final com.tencent.liteav.videobase.f.a f20294b;

    /* renamed from: d, reason: collision with root package name */
    private final boolean f20296d;

    /* renamed from: e, reason: collision with root package name */
    private final boolean f20297e;

    /* renamed from: g, reason: collision with root package name */
    private n.a f20299g;

    /* renamed from: i, reason: collision with root package name */
    private e f20301i;

    /* renamed from: j, reason: collision with root package name */
    private long f20302j;

    /* renamed from: k, reason: collision with root package name */
    private long f20303k;

    /* renamed from: n, reason: collision with root package name */
    private boolean f20306n;

    /* renamed from: o, reason: collision with root package name */
    private int f20307o;

    /* renamed from: p, reason: collision with root package name */
    private int f20308p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f20309q;

    /* renamed from: r, reason: collision with root package name */
    private int f20310r;

    /* renamed from: c, reason: collision with root package name */
    @NonNull
    private final com.tencent.liteav.basic.util.e f20295c = new com.tencent.liteav.basic.util.e();

    /* renamed from: f, reason: collision with root package name */
    private VideoDecodeController.DecodeStrategy f20298f = VideoDecodeController.DecodeStrategy.AUTO_SWITCH;

    /* renamed from: h, reason: collision with root package name */
    private boolean f20300h = false;

    /* renamed from: l, reason: collision with root package name */
    private int f20304l = 8;

    /* renamed from: m, reason: collision with root package name */
    private int f20305m = 6;

    /* renamed from: com.tencent.liteav.videoengine.decoder.a$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f20311a;

        static {
            int[] iArr = new int[c.values().length];
            f20311a = iArr;
            try {
                iArr[c.SWITCH_TO_SOFTWARE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f20311a[c.SWITCH_TO_HARDWARE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f20311a[c.RESTART_DECODER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f20311a[c.CONTINUE_DECODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* renamed from: com.tencent.liteav.videoengine.decoder.a$a, reason: collision with other inner class name */
    public interface InterfaceC0342a {
        b a(com.tencent.liteav.videobase.e.b bVar);
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public final c f20312a;

        /* renamed from: b, reason: collision with root package name */
        public final e f20313b;

        public b(c cVar, e eVar) {
            this.f20312a = cVar;
            this.f20313b = eVar;
        }
    }

    public enum c {
        CONTINUE_DECODE(0),
        DROP_FRAME(1),
        RESTART_DECODER(2),
        SWITCH_TO_HARDWARE(3),
        SWITCH_TO_SOFTWARE(3),
        REQUEST_KEY_FRAME(4),
        REPORT_DECODE_ERROR(5);

        private final int mPriority;

        c(int i2) {
            this.mPriority = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int a() {
            return this.mPriority;
        }
    }

    public interface d {
        com.tencent.liteav.basic.util.e decodeResolutionFromSps(boolean z2, byte[] bArr);
    }

    public enum e {
        NONE(0),
        RPS_MODE_UPDATED(1),
        HARDWARE_DECODER_ABNORMAL(2),
        DECODE_ERROR(3),
        OTHERS_DO_NOT_SUPPORT_H265(4);

        private final int mPriority;

        e(int i2) {
            this.mPriority = i2;
        }

        public int a() {
            return this.mPriority;
        }
    }

    public a(@NonNull d dVar, @NonNull com.tencent.liteav.videobase.f.a aVar, boolean z2, boolean z3) {
        this.f20293a = dVar;
        this.f20294b = aVar;
        this.f20296d = z2;
        this.f20297e = z3;
        b();
    }

    private c b(com.tencent.liteav.videobase.e.b bVar) {
        if (!this.f20306n) {
            this.f20306n = true;
        }
        b bVarC = c(bVar);
        c cVar = bVarC.f20312a;
        if (cVar == c.SWITCH_TO_HARDWARE) {
            n.a aVar = this.f20299g;
            n.a aVar2 = n.a.HARDWARE;
            if (aVar == aVar2 || bVarC.f20313b.a() < this.f20301i.a()) {
                return c.CONTINUE_DECODE;
            }
            this.f20301i = bVarC.f20313b;
            this.f20299g = aVar2;
        } else if (cVar == c.SWITCH_TO_SOFTWARE) {
            n.a aVar3 = this.f20299g;
            n.a aVar4 = n.a.SOFTWARE;
            if (aVar3 == aVar4 || bVarC.f20313b.a() < this.f20301i.a()) {
                return c.CONTINUE_DECODE;
            }
            this.f20301i = bVarC.f20313b;
            this.f20299g = aVar4;
        }
        return bVarC.f20312a;
    }

    private c c() {
        if (this.f20306n) {
            return c.CONTINUE_DECODE;
        }
        int i2 = this.f20307o + 1;
        this.f20307o = i2;
        if (i2 <= 40) {
            return c.DROP_FRAME;
        }
        TXCLog.w("DecoderSupervisor", "decoding too many frame(>40) without output! request key frame now.");
        this.f20307o = 0;
        return c.REQUEST_KEY_FRAME;
    }

    private boolean d() {
        VideoDecodeController.DecodeStrategy decodeStrategy = this.f20298f;
        return decodeStrategy == VideoDecodeController.DecodeStrategy.USE_HARDWARE_ONLY || decodeStrategy == VideoDecodeController.DecodeStrategy.AUTO_SWITCH;
    }

    private boolean e() {
        VideoDecodeController.DecodeStrategy decodeStrategy = this.f20298f;
        return decodeStrategy == VideoDecodeController.DecodeStrategy.USE_SOFTWARE_ONLY || decodeStrategy == VideoDecodeController.DecodeStrategy.AUTO_SWITCH;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b f(com.tencent.liteav.videobase.e.b bVar) {
        boolean zC = bVar.c();
        if (zC && !this.f20297e && !this.f20296d) {
            return new b(c.REPORT_DECODE_ERROR, e.NONE);
        }
        if (zC && !this.f20296d && this.f20299g != n.a.HARDWARE && d()) {
            return new b(c.SWITCH_TO_HARDWARE, e.OTHERS_DO_NOT_SUPPORT_H265);
        }
        if (zC && !this.f20297e && this.f20299g != n.a.SOFTWARE && e()) {
            return new b(c.SWITCH_TO_SOFTWARE, e.OTHERS_DO_NOT_SUPPORT_H265);
        }
        if (zC != this.f20300h) {
            return new b(c.RESTART_DECODER, e.NONE);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b g(com.tencent.liteav.videobase.e.b bVar) {
        VideoDecodeController.DecodeStrategy decodeStrategy = this.f20298f;
        if (decodeStrategy == VideoDecodeController.DecodeStrategy.USE_HARDWARE_ONLY && this.f20299g != n.a.HARDWARE) {
            return new b(c.SWITCH_TO_HARDWARE, e.NONE);
        }
        if (decodeStrategy != VideoDecodeController.DecodeStrategy.USE_SOFTWARE_ONLY || this.f20299g == n.a.SOFTWARE) {
            return null;
        }
        return new b(c.SWITCH_TO_SOFTWARE, e.NONE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b h(com.tencent.liteav.videobase.e.b bVar) {
        boolean zB = bVar.b();
        if (!zB && this.f20299g == n.a.SOFTWARE && d()) {
            com.tencent.liteav.videobase.f.a aVar = this.f20294b;
            aVar.a("Remote-VideoDecoder[%s]: 远端停用RPS，软解切硬解 [tinyID:%s]", this, aVar.a());
            this.f20294b.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_SW_TO_HW_REMOTE_VIDEO_DISABLE_RPS, "VideoDecode: remote video disable RPS, switch SW to HW", "", new Object[0]);
            return new b(c.SWITCH_TO_HARDWARE, e.RPS_MODE_UPDATED);
        }
        if (!zB || this.f20299g == n.a.SOFTWARE || !e()) {
            return null;
        }
        com.tencent.liteav.videobase.f.a aVar2 = this.f20294b;
        aVar2.a("Remote-VideoDecoder[%s]: 远端启用RPS，硬解切软解 [tinyID:%d]", this, aVar2.a());
        this.f20294b.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_HW_TO_SW_REMOTE_VIDEO_ENABLE_RPS, "VideoDecode: remote video enable RPS, switch HW to SW", "", new Object[0]);
        return new b(c.SWITCH_TO_SOFTWARE, e.RPS_MODE_UPDATED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b i(com.tencent.liteav.videobase.e.b bVar) {
        if (this.f20299g == n.a.HARDWARE && this.f20295c.a() > 0) {
            int i2 = this.f20295c.a() >= 480000 ? this.f20304l : this.f20305m;
            int i3 = this.f20308p;
            boolean z2 = i3 >= i2;
            long j2 = this.f20303k;
            if ((z2 || ((j2 > 0L ? 1 : (j2 == 0L ? 0 : -1)) != 0 && ((this.f20302j - j2) > ((long) (i2 * 66)) ? 1 : ((this.f20302j - j2) == ((long) (i2 * 66)) ? 0 : -1)) >= 0 && i3 >= i2 + (-2))) && e()) {
                this.f20294b.a("Remote-VideoDecoder[" + this + "]: " + (z2 ? "硬解缓存过多，转为软解" : "解码耗时过长，切换为软解") + "[videoSize: " + this.f20295c + "][decCacheNum:" + this.f20308p + "][decPts:" + this.f20302j + "][renderPts:" + this.f20303k + "][cacheHigh:" + this.f20304l + "][cacheLow:" + this.f20305m + "][tinyID:" + this.f20294b.a() + StrPool.BRACKET_END, new Object[0]);
                if (z2) {
                    this.f20294b.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_HW_TO_SW_TOO_MANY_CACHE_FRAME, "VideoDecode: too many cache frames, switch HW to SW", "deviceName: %s", Build.MODEL);
                } else {
                    this.f20294b.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_HW_TO_SW_DECODE_COST_TOO_HIGH, "VideoDecode: decode cost too high, switch HW to SW", "deviceName: %s", Build.MODEL);
                }
                return new b(c.SWITCH_TO_SOFTWARE, e.HARDWARE_DECODER_ABNORMAL);
            }
        }
        return null;
    }

    public c a(com.tencent.liteav.videobase.e.b bVar) {
        this.f20302j = bVar.f19994f;
        c cVarB = bVar.a() ? b(bVar) : c();
        int i2 = AnonymousClass1.f20311a[cVarB.ordinal()];
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            this.f20308p = 1;
            this.f20300h = bVar.c();
        } else if (i2 == 4) {
            this.f20308p++;
        }
        return cVarB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b d(com.tencent.liteav.videobase.e.b bVar) {
        boolean z2;
        com.tencent.liteav.basic.util.e eVarDecodeResolutionFromSps = this.f20293a.decodeResolutionFromSps(bVar.c(), bVar.f19989a);
        if (this.f20295c.equals(eVarDecodeResolutionFromSps)) {
            z2 = false;
        } else {
            com.tencent.liteav.basic.util.e eVar = this.f20295c;
            eVar.f18712a = eVarDecodeResolutionFromSps.f18712a;
            eVar.f18713b = eVarDecodeResolutionFromSps.f18713b;
            z2 = true;
        }
        if (this.f20299g == n.a.HARDWARE && z2) {
            return new b(c.RESTART_DECODER, e.NONE);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b e(com.tencent.liteav.videobase.e.b bVar) {
        if (!this.f20309q) {
            return null;
        }
        this.f20309q = false;
        if (this.f20299g == n.a.HARDWARE && e()) {
            this.f20294b.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_HW_TO_SW_MEDIACODEC_NOT_WORK, "VideoDecode: MediaCodec doesn't work, switch HW to SW.", "", new Object[0]);
            return new b(c.SWITCH_TO_SOFTWARE, e.DECODE_ERROR);
        }
        int i2 = this.f20310r + 1;
        this.f20310r = i2;
        return i2 >= 3 ? new b(c.REPORT_DECODE_ERROR, e.DECODE_ERROR) : new b(c.RESTART_DECODER, e.DECODE_ERROR);
    }

    private b c(com.tencent.liteav.videobase.e.b bVar) {
        Iterator it = Arrays.asList(com.tencent.liteav.videoengine.decoder.b.a(this), com.tencent.liteav.videoengine.decoder.c.a(this), com.tencent.liteav.videoengine.decoder.d.a(this), com.tencent.liteav.videoengine.decoder.e.a(this), f.a(this), g.a(this)).iterator();
        b bVar2 = null;
        while (it.hasNext()) {
            b bVarA = ((InterfaceC0342a) it.next()).a(bVar);
            if (bVarA != null && (bVar2 == null || bVarA.f20312a.a() > bVar2.f20312a.a())) {
                bVar2 = bVarA;
            }
        }
        if (bVar2 != null) {
            return bVar2;
        }
        if (this.f20299g == null) {
            return new b(c.SWITCH_TO_HARDWARE, e.NONE);
        }
        return new b(c.CONTINUE_DECODE, e.NONE);
    }

    public void a(long j2) {
        int i2 = this.f20308p;
        if (i2 > 0) {
            this.f20308p = i2 - 1;
        }
        if (this.f20303k == 0) {
            TXCLog.i("DecoderSupervisor", "decode first frame success");
        }
        this.f20303k = j2;
        this.f20310r = 0;
    }

    public void a(VideoDecodeController.DecodeStrategy decodeStrategy) {
        if (this.f20298f == decodeStrategy) {
            return;
        }
        this.f20298f = decodeStrategy;
        this.f20299g = null;
        TXCLog.i("DecoderSupervisor", "set decode strategy to %s", decodeStrategy);
    }

    public void b() {
        this.f20310r = 0;
        this.f20306n = false;
        this.f20308p = 0;
        this.f20309q = false;
        com.tencent.liteav.basic.util.e eVar = this.f20295c;
        eVar.f18713b = 0;
        eVar.f18712a = 0;
        this.f20303k = 0L;
        this.f20302j = 0L;
        this.f20307o = 0;
        this.f20299g = null;
        this.f20301i = e.NONE;
    }

    public void a() {
        this.f20309q = true;
    }

    public void a(int i2, int i3) {
        this.f20304l = i2;
        this.f20305m = i3;
        TXCLog.i("DecoderSupervisor", "set hardware decoder max cache to highResolution: %d, lowResolution: %d", Integer.valueOf(i2), Integer.valueOf(this.f20305m));
    }
}
