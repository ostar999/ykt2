package com.tencent.liteav.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

@TargetApi(18)
/* loaded from: classes6.dex */
public class b implements com.tencent.liteav.muxer.a {

    /* renamed from: a, reason: collision with root package name */
    public static float f19407a = 0.5f;

    /* renamed from: b, reason: collision with root package name */
    public static float f19408b = 0.8f;

    /* renamed from: c, reason: collision with root package name */
    public static float f19409c = 1.25f;

    /* renamed from: d, reason: collision with root package name */
    public static float f19410d = 2.0f;

    /* renamed from: f, reason: collision with root package name */
    private MediaMuxer f19412f;

    /* renamed from: e, reason: collision with root package name */
    private int f19411e = 2;

    /* renamed from: g, reason: collision with root package name */
    private String f19413g = null;

    /* renamed from: h, reason: collision with root package name */
    private MediaFormat f19414h = null;

    /* renamed from: i, reason: collision with root package name */
    private MediaFormat f19415i = null;

    /* renamed from: j, reason: collision with root package name */
    private int f19416j = 0;

    /* renamed from: k, reason: collision with root package name */
    private int f19417k = 0;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19418l = false;

    /* renamed from: m, reason: collision with root package name */
    private boolean f19419m = false;

    /* renamed from: n, reason: collision with root package name */
    private ConcurrentLinkedQueue<a> f19420n = new ConcurrentLinkedQueue<>();

    /* renamed from: o, reason: collision with root package name */
    private ConcurrentLinkedQueue<a> f19421o = new ConcurrentLinkedQueue<>();

    /* renamed from: p, reason: collision with root package name */
    private long f19422p = -1;

    /* renamed from: q, reason: collision with root package name */
    private long f19423q = -1;

    /* renamed from: r, reason: collision with root package name */
    private long f19424r = -1;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        ByteBuffer f19425a;

        /* renamed from: b, reason: collision with root package name */
        MediaCodec.BufferInfo f19426b;

        public a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this.f19425a = byteBuffer;
            this.f19426b = bufferInfo;
        }

        public ByteBuffer a() {
            return this.f19425a;
        }

        public MediaCodec.BufferInfo b() {
            return this.f19426b;
        }
    }

    private void d(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f2;
        float f3;
        long j2 = bufferInfo.presentationTimeUs;
        long j3 = this.f19422p;
        long j4 = j2 - j3;
        if (j3 < 0 || j4 < 0) {
            TXCLog.w("TXCMP4HWMuxer", "drop sample. first frame offset timeus = " + this.f19422p + ", current sample timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (j4 < this.f19424r) {
            TXCLog.e("TXCMP4HWMuxer", "audio is not in chronological order. current audio's pts pts(" + j4 + ") must larger than pre audio's pts(" + this.f19424r + ")");
            j4 = this.f19424r + 1;
        } else {
            this.f19424r = j4;
        }
        int i2 = this.f19411e;
        if (i2 != 2) {
            if (i2 == 3) {
                f2 = j4;
                f3 = f19408b;
            } else if (i2 == 4) {
                f2 = j4;
                f3 = f19407a;
            } else if (i2 == 1) {
                f2 = j4;
                f3 = f19409c;
            } else if (i2 == 0) {
                f2 = j4;
                f3 = f19410d;
            }
            j4 = (long) (f2 * f3);
        }
        bufferInfo.presentationTimeUs = j4;
        try {
            this.f19412f.writeSampleData(this.f19416j, byteBuffer, bufferInfo);
        } catch (IllegalArgumentException e2) {
            TXCLog.e("TXCMP4HWMuxer", "write sample IllegalArgumentException: " + e2);
        } catch (IllegalStateException e3) {
            TXCLog.e("TXCMP4HWMuxer", "write sample IllegalStateException: " + e3);
        }
    }

    private void e() {
        while (this.f19420n.size() > 0) {
            a aVarPoll = this.f19420n.poll();
            c(aVarPoll.a(), aVarPoll.b());
        }
        while (this.f19421o.size() > 0) {
            a aVarPoll2 = this.f19421o.poll();
            d(aVarPoll2.a(), aVarPoll2.b());
        }
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void a(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4HWMuxer", "addVideoTrack:" + mediaFormat);
        this.f19414h = mediaFormat;
        this.f19420n.clear();
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void b(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4HWMuxer", "addAudioTrack:" + mediaFormat);
        this.f19415i = mediaFormat;
        this.f19421o.clear();
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized boolean c() {
        return this.f19414h != null;
    }

    private void c(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f2;
        float f3;
        long j2 = bufferInfo.presentationTimeUs - this.f19422p;
        if (j2 < 0) {
            TXCLog.e("TXCMP4HWMuxer", "pts error! first frame offset timeus = " + this.f19422p + ", current timeus = " + bufferInfo.presentationTimeUs);
            j2 = this.f19423q;
            if (j2 <= 0) {
                j2 = 0;
            }
        }
        if (j2 < this.f19423q) {
            TXCLog.w("TXCMP4HWMuxer", "video is not in chronological order. current frame's pts(" + j2 + ") smaller than pre frame's pts(" + this.f19423q + ")");
        } else {
            this.f19423q = j2;
        }
        int i2 = this.f19411e;
        if (i2 != 2) {
            if (i2 == 3) {
                f2 = j2;
                f3 = f19408b;
            } else if (i2 == 4) {
                f2 = j2;
                f3 = f19407a;
            } else if (i2 == 1) {
                f2 = j2;
                f3 = f19409c;
            } else if (i2 == 0) {
                f2 = j2;
                f3 = f19410d;
            }
            j2 = (long) (f2 * f3);
        }
        bufferInfo.presentationTimeUs = j2;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f19412f.writeSampleData(this.f19417k, byteBuffer, bufferInfo);
            if ((bufferInfo.flags & 1) != 0) {
                this.f19419m = true;
            }
        } catch (IllegalArgumentException e2) {
            TXCLog.e("TXCMP4HWMuxer", "write frame info.presentationTimeUs: " + bufferInfo.presentationTimeUs + ", IllegalArgumentException: " + e2);
        } catch (IllegalStateException e3) {
            TXCLog.e("TXCMP4HWMuxer", "write frame info.presentationTimeUs: " + bufferInfo.presentationTimeUs + ", IllegalStateException: " + e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.tencent.liteav.muxer.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized int a() {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.muxer.b.a():int");
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized int b() {
        if (this.f19412f != null) {
            TXCLog.d("TXCMP4HWMuxer", "stop. start flag = " + this.f19418l + ", video key frame set = " + this.f19419m);
            try {
                try {
                    if (this.f19418l && this.f19419m) {
                        this.f19412f.stop();
                    }
                    this.f19412f.release();
                } finally {
                    this.f19418l = false;
                    this.f19412f = null;
                    this.f19419m = false;
                    this.f19420n.clear();
                    this.f19421o.clear();
                    this.f19414h = null;
                    this.f19415i = null;
                    this.f19423q = -1L;
                    this.f19424r = -1L;
                }
            } catch (Exception e2) {
                TXCLog.e("TXCMP4HWMuxer", "muxer stop/release exception: " + e2);
                return -1;
            }
        }
        return 0;
    }

    private long d() {
        a aVarPeek;
        long j2 = this.f19420n.size() > 0 ? this.f19420n.peek().b().presentationTimeUs : 0L;
        if (this.f19421o.size() <= 0 || (aVarPeek = this.f19421o.peek()) == null || aVarPeek.b() == null) {
            return j2;
        }
        long j3 = this.f19421o.peek().b().presentationTimeUs;
        return j2 > j3 ? j3 : j2;
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void a(String str) {
        this.f19413g = str;
        if (!TextUtils.isEmpty(str)) {
            File file = new File(this.f19413g);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e2) {
                TXCLog.e("TXCMP4HWMuxer", "create new file failed.", e2);
            }
        }
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void b(byte[] bArr, int i2, int i3, long j2, int i4) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i3);
        byteBufferAllocateDirect.put(bArr, i2, i3);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = j2;
        bufferInfo.offset = 0;
        bufferInfo.size = i3;
        bufferInfo.flags = i4;
        a(byteBufferAllocateDirect, bufferInfo);
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void a(byte[] bArr, int i2, int i3, long j2, int i4) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i3);
        byteBufferAllocateDirect.put(bArr, i2, i3);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = j2;
        bufferInfo.offset = 0;
        bufferInfo.size = i3;
        bufferInfo.flags = i4;
        b(byteBufferAllocateDirect, bufferInfo);
    }

    public synchronized void b(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f19412f != null && this.f19422p >= 0) {
            d(byteBuffer, bufferInfo);
            return;
        }
        TXCLog.d("TXCMP4HWMuxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
        a(false, byteBuffer, bufferInfo);
    }

    public synchronized void a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f19412f == null) {
            a(true, byteBuffer, bufferInfo);
            TXCLog.w("TXCMP4HWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.f19422p < 0) {
            a(true, byteBuffer, bufferInfo);
            this.f19422p = d();
            TXCLog.i("TXCMP4HWMuxer", "first frame offset = " + this.f19422p);
            e();
        } else {
            c(byteBuffer, bufferInfo);
        }
    }

    private void a(boolean z2, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (byteBuffer == null || bufferInfo == null) {
            return;
        }
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(byteBuffer.capacity());
        byteBuffer.rewind();
        if (bufferInfo.size > 0) {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.size);
        }
        byteBufferAllocateDirect.rewind();
        byteBufferAllocateDirect.put(byteBuffer);
        MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
        bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        a aVar = new a(byteBufferAllocateDirect, bufferInfo2);
        if (z2) {
            if (this.f19420n.size() < 200) {
                this.f19420n.add(aVar);
                return;
            } else {
                TXCLog.e("TXCMP4HWMuxer", "drop video frame. video cache size is larger than 200");
                return;
            }
        }
        if (this.f19421o.size() < 600) {
            this.f19421o.add(aVar);
        } else {
            TXCLog.e("TXCMP4HWMuxer", "drop audio frame. audio cache size is larger than 600");
        }
    }
}
