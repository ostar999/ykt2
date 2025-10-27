package com.tencent.liteav.videodecoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class f implements b {

    /* renamed from: r, reason: collision with root package name */
    private h f20163r;

    /* renamed from: u, reason: collision with root package name */
    private WeakReference<com.tencent.liteav.basic.b.b> f20166u;

    /* renamed from: a, reason: collision with root package name */
    private MediaCodec.BufferInfo f20146a = new MediaCodec.BufferInfo();

    /* renamed from: b, reason: collision with root package name */
    private MediaCodec f20147b = null;

    /* renamed from: c, reason: collision with root package name */
    private String f20148c = MimeTypes.VIDEO_H264;

    /* renamed from: d, reason: collision with root package name */
    private int f20149d = R2.attr.bl_checked_gradient_type;

    /* renamed from: e, reason: collision with root package name */
    private int f20150e = 960;

    /* renamed from: f, reason: collision with root package name */
    private long f20151f = 0;

    /* renamed from: g, reason: collision with root package name */
    private long f20152g = 0;

    /* renamed from: h, reason: collision with root package name */
    private boolean f20153h = true;

    /* renamed from: i, reason: collision with root package name */
    private boolean f20154i = false;

    /* renamed from: j, reason: collision with root package name */
    private boolean f20155j = false;

    /* renamed from: k, reason: collision with root package name */
    private Surface f20156k = null;

    /* renamed from: l, reason: collision with root package name */
    private int f20157l = 0;

    /* renamed from: m, reason: collision with root package name */
    private ArrayList<TXSNALPacket> f20158m = new ArrayList<>();

    /* renamed from: n, reason: collision with root package name */
    private ArrayList<Long> f20159n = new ArrayList<>();

    /* renamed from: o, reason: collision with root package name */
    private long f20160o = 0;

    /* renamed from: p, reason: collision with root package name */
    private int f20161p = 0;

    /* renamed from: q, reason: collision with root package name */
    private JSONArray f20162q = null;

    /* renamed from: s, reason: collision with root package name */
    private d f20164s = new d();

    /* renamed from: t, reason: collision with root package name */
    private boolean f20165t = false;

    @TargetApi(16)
    private void b() throws MediaCodec.CryptoException {
        ByteBuffer[] inputBuffers;
        int iDequeueInputBuffer;
        int iDequeueOutputBuffer;
        if (this.f20147b == null) {
            TXCLog.e("MediaCodecDecoder", "null decoder");
            return;
        }
        TXSNALPacket tXSNALPacket = this.f20158m.get(0);
        if (tXSNALPacket == null || tXSNALPacket.nalData.length == 0) {
            TXCLog.e("MediaCodecDecoder", "decode: empty buffer");
            this.f20158m.remove(0);
            return;
        }
        long timeTick = TXCTimeUtil.getTimeTick();
        long jLongValue = 0;
        if (this.f20160o == 0) {
            this.f20160o = timeTick;
        }
        try {
            inputBuffers = this.f20147b.getInputBuffers();
        } catch (Exception e2) {
            TXCLog.e("MediaCodecDecoder", "decode: getInputBuffers Exception!! " + e2.toString());
            inputBuffers = null;
        }
        ByteBuffer[] byteBufferArr = inputBuffers;
        if (byteBufferArr == null || byteBufferArr.length == 0) {
            TXCLog.e("MediaCodecDecoder", "decode: getInputBuffers failed");
            return;
        }
        try {
            iDequeueInputBuffer = this.f20147b.dequeueInputBuffer(com.heytap.mcssdk.constant.a.f7153q);
        } catch (Exception e3) {
            TXCLog.e("MediaCodecDecoder", "decode: dequeueInputBuffer Exception!! " + e3.toString());
            iDequeueInputBuffer = -10000;
        }
        if (iDequeueInputBuffer >= 0) {
            byteBufferArr[iDequeueInputBuffer].put(tXSNALPacket.nalData);
            try {
                this.f20147b.queueInputBuffer(iDequeueInputBuffer, 0, tXSNALPacket.nalData.length, TimeUnit.MILLISECONDS.toMicros(tXSNALPacket.pts), 0);
                this.f20158m.remove(0);
            } catch (Exception unused) {
                f();
            }
            if (this.f20151f == 0) {
                TXCLog.w("MediaCodecDecoder", "decode: input buffer available, dequeueInputBuffer index: " + iDequeueInputBuffer);
            }
        } else {
            TXCLog.w("MediaCodecDecoder", "decode: input buffer not available, dequeueInputBuffer failed");
        }
        try {
            iDequeueOutputBuffer = this.f20147b.dequeueOutputBuffer(this.f20146a, this.f20151f == 0 ? C.DEFAULT_SEEK_FORWARD_INCREMENT_MS : com.heytap.mcssdk.constant.a.f7153q);
        } catch (Exception e4) {
            f();
            TXCLog.e("MediaCodecDecoder", "decode: dequeueOutputBuffer exception!!" + e4);
            iDequeueOutputBuffer = -10000;
        }
        if (iDequeueOutputBuffer >= 0) {
            long millis = TimeUnit.MICROSECONDS.toMillis(this.f20146a.presentationTimeUs);
            a(iDequeueOutputBuffer, millis, millis, tXSNALPacket.rotation);
            this.f20157l = 0;
        } else if (iDequeueOutputBuffer == -1) {
            TXCLog.i("MediaCodecDecoder", "decode: no output from decoder available when timeout fail count " + this.f20157l);
            f();
        } else if (iDequeueOutputBuffer == -3) {
            TXCLog.i("MediaCodecDecoder", "decode: output buffers changed");
        } else if (iDequeueOutputBuffer == -2) {
            c();
        } else {
            TXCLog.e("MediaCodecDecoder", "decode: unexpected result from decoder.dequeueOutputBuffer: " + iDequeueOutputBuffer);
        }
        long timeTick2 = TXCTimeUtil.getTimeTick();
        this.f20159n.add(Long.valueOf(timeTick2 - timeTick));
        if (timeTick2 > this.f20160o + 1000) {
            Iterator<Long> it = this.f20159n.iterator();
            while (it.hasNext()) {
                Long next = it.next();
                if (next.longValue() > jLongValue) {
                    jLongValue = next.longValue();
                }
            }
            this.f20159n.clear();
            this.f20160o = timeTick2;
            this.f20161p = (int) (jLongValue * 3);
        }
    }

    private void c() {
        int i2;
        MediaFormat outputFormat = this.f20147b.getOutputFormat();
        TXCLog.i("MediaCodecDecoder", "decode output format changed: " + outputFormat);
        int iAbs = Math.abs(outputFormat.getInteger("crop-right") - outputFormat.getInteger("crop-left")) + 1;
        int iAbs2 = Math.abs(outputFormat.getInteger("crop-bottom") - outputFormat.getInteger("crop-top")) + 1;
        int integer = outputFormat.getInteger("width");
        int integer2 = outputFormat.getInteger("height");
        int iMin = Math.min(iAbs, integer);
        int iMin2 = Math.min(iAbs2, integer2);
        int i3 = this.f20149d;
        if (iMin == i3 && iMin2 == (i2 = this.f20150e)) {
            if (this.f20153h) {
                this.f20153h = false;
                h hVar = this.f20163r;
                if (hVar != null) {
                    hVar.onVideoSizeChange(i3, i2);
                    return;
                }
                return;
            }
            return;
        }
        if (this.f20155j && !e.b(iMin, iMin2, 20)) {
            e();
            Monitor.a(2, String.format(Locale.getDefault(), "outputFormatChange: dynamic change resolution but change to a not support resolution: %s, oldwidth = %d,oldheight = %d, newwidth = %d, newheight=", TXCCommonUtil.getDeviceInfo(), Integer.valueOf(this.f20149d), Integer.valueOf(this.f20150e), Integer.valueOf(iMin), Integer.valueOf(iMin2)), "", 0);
        }
        this.f20149d = iMin;
        this.f20150e = iMin2;
        try {
            h hVar2 = this.f20163r;
            if (hVar2 != null) {
                hVar2.onVideoSizeChange(iMin, iMin2);
            }
        } catch (Exception e2) {
            TXCLog.e("MediaCodecDecoder", "onVideoSizeChange failed.", e2);
        }
        TXCLog.i("MediaCodecDecoder", "decode: video size change to w:" + iMin + ",h:" + iMin2);
    }

    private void d() {
        if (this.f20151f == 0) {
            TXCLog.w("MediaCodecDecoder", "decode first frame sucess");
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = this.f20151f;
        if (j2 > 0 && jCurrentTimeMillis > j2 + 1000) {
            long j3 = this.f20152g;
            if (jCurrentTimeMillis > ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS + j3 && j3 != 0) {
                TXCLog.e("MediaCodecDecoder", "frame interval[" + (jCurrentTimeMillis - this.f20151f) + "] > 1000");
                this.f20152g = jCurrentTimeMillis;
            }
        }
        if (this.f20152g == 0) {
            this.f20152g = jCurrentTimeMillis;
        }
        this.f20151f = jCurrentTimeMillis;
    }

    private void e() {
        if (this.f20154i) {
            return;
        }
        this.f20154i = true;
        TXCLog.e("MediaCodecDecoder", "[Video][Decoder] notify HWDecoder error, isH265:" + this.f20155j);
        if (this.f20155j) {
            com.tencent.liteav.basic.util.h.a(this.f20166u, -2304, "h265 Decoding failed");
            Monitor.a(2, String.format(Locale.getDefault(), "VideoDecoder: hevc hardware decoder error. %s, %d, %d", TXCCommonUtil.getDeviceInfo(), Integer.valueOf(e.b(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20) ? 1 : 0), Integer.valueOf(e.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20) ? 1 : 0)), "", 0);
        } else {
            com.tencent.liteav.basic.util.h.a(this.f20166u, 2106, "Failed to enable hardware decoding，use software decoding.");
        }
        h hVar = this.f20163r;
        if (hVar != null) {
            hVar.onDecodeFailed(-1);
        }
    }

    private void f() {
        int i2 = this.f20157l;
        if (i2 < 40) {
            this.f20157l = i2 + 1;
        } else {
            e();
            this.f20157l = 0;
        }
    }

    @Override // com.tencent.liteav.videodecoder.b
    public int GetDecodeCost() {
        return this.f20161p;
    }

    public void a(JSONArray jSONArray) {
        this.f20162q = jSONArray;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public int config(Surface surface) {
        if (surface == null) {
            return -1;
        }
        this.f20156k = surface;
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void decode(TXSNALPacket tXSNALPacket) throws JSONException {
        a(a(tXSNALPacket));
        if (tXSNALPacket.codecId == 0) {
            b(tXSNALPacket);
        }
        this.f20158m.add(tXSNALPacket);
        while (!this.f20158m.isEmpty()) {
            int size = this.f20158m.size();
            try {
                b();
            } catch (Exception e2) {
                TXCLog.e("MediaCodecDecoder", "decode: doDecode Exception!! " + e2.toString());
            }
            if (size == this.f20158m.size()) {
                return;
            }
        }
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void enableLimitDecCache(boolean z2) {
        this.f20165t = z2;
        TXCLog.i("MediaCodecDecoder", "decode: enable limit dec cache: " + z2);
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void setListener(h hVar) {
        this.f20163r = hVar;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void setNotifyListener(WeakReference<com.tencent.liteav.basic.b.b> weakReference) {
        this.f20166u = weakReference;
    }

    @Override // com.tencent.liteav.videodecoder.b
    public int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z2, boolean z3) {
        return a(byteBuffer, byteBuffer2, z3);
    }

    @Override // com.tencent.liteav.videodecoder.b
    public void stop() {
        a();
    }

    public void a(int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        this.f20149d = i2;
        this.f20150e = i3;
        TXCLog.w("MediaCodecDecoder", "decode: init with video size: " + this.f20149d + ", " + this.f20150e);
    }

    private void a(MediaFormat mediaFormat) {
        if (TXCBuild.Hardware().toLowerCase().contains("qcom") && TXCBuild.VersionInt() >= 28) {
            mediaFormat.setInteger("vendor.qti-ext-dec-low-latency.enable", 1);
            mediaFormat.setInteger("vendor.qti-ext-dec-picture-order.enable", 1);
        } else if (TXCBuild.Hardware().toLowerCase().contains("kirin") && TXCBuild.VersionInt() >= 29) {
            mediaFormat.setInteger("vendor.hisi-ext-low-latency-video-dec.video-scene-for-low-latency-req", 1);
            mediaFormat.setInteger("vendor.hisi-ext-low-latency-video-dec.video-scene-for-low-latency-rdy", -1);
        }
        if (TXCBuild.VersionInt() >= 30) {
            mediaFormat.setInteger("low-latency", 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x0146 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(java.nio.ByteBuffer r8, java.nio.ByteBuffer r9, boolean r10) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videodecoder.f.a(java.nio.ByteBuffer, java.nio.ByteBuffer, boolean):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(com.tencent.liteav.basic.structs.TXSNALPacket r9) {
        /*
            Method dump skipped, instructions count: 183
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videodecoder.f.b(com.tencent.liteav.basic.structs.TXSNALPacket):void");
    }

    private void a() {
        MediaCodec mediaCodec = this.f20147b;
        if (mediaCodec != null) {
            try {
                try {
                    mediaCodec.stop();
                    TXCLog.w("MediaCodecDecoder", "decode: stop decoder sucess");
                    try {
                        try {
                            this.f20147b.release();
                            TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                        } catch (Exception e2) {
                            TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + e2.toString());
                        }
                    } finally {
                    }
                } catch (Throwable th) {
                    try {
                        try {
                            this.f20147b.release();
                            TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                        } catch (Exception e3) {
                            TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + e3.toString());
                        }
                        throw th;
                    } finally {
                    }
                }
            } catch (Exception e4) {
                TXCLog.e("MediaCodecDecoder", "decode: stop decoder Exception: " + e4.toString());
                try {
                    try {
                        this.f20147b.release();
                        TXCLog.w("MediaCodecDecoder", "decode: release decoder sucess");
                    } catch (Exception e5) {
                        TXCLog.e("MediaCodecDecoder", "decode: release decoder exception: " + e5.toString());
                    }
                } finally {
                }
            }
        }
        this.f20158m.clear();
        this.f20151f = 0L;
        this.f20153h = true;
        this.f20154i = false;
    }

    private void a(int i2, long j2, long j3, int i3) {
        this.f20147b.releaseOutputBuffer(i2, true);
        if ((this.f20146a.flags & 4) != 0) {
            TXCLog.i("MediaCodecDecoder", "output EOS");
        }
        try {
            h hVar = this.f20163r;
            if (hVar != null) {
                hVar.onDecodeFrame(null, this.f20149d, this.f20150e, j2, j3, i3);
            }
        } catch (Exception e2) {
            TXCLog.e("MediaCodecDecoder", "onDecodeFrame failed.", e2);
        }
        d();
    }

    public boolean a(TXSNALPacket tXSNALPacket) {
        return tXSNALPacket != null && tXSNALPacket.codecId == 1;
    }

    private void a(boolean z2) throws JSONException {
        if (this.f20155j != z2) {
            StringBuilder sb = new StringBuilder();
            sb.append("[Video][Decoder] nal data format changed, from:");
            sb.append(this.f20155j ? "h265" : "h264");
            sb.append(" to:");
            sb.append(z2 ? "h265" : "h264");
            TXCLog.i("MediaCodecDecoder", sb.toString());
            this.f20155j = z2;
            if (z2 && !e.b(this.f20149d, this.f20150e, 20)) {
                a();
                e();
                return;
            }
            a();
            a(null, null, this.f20155j);
            h hVar = this.f20163r;
            if (hVar != null) {
                hVar.onDecoderChange(this.f20148c, this.f20155j);
            }
        }
    }
}
