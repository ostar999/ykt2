package com.tencent.liteav.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.muxer.jni.TXSWMuxerJNI;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

@TargetApi(18)
/* loaded from: classes6.dex */
public class d implements com.tencent.liteav.muxer.a {

    /* renamed from: a, reason: collision with root package name */
    public static float f19429a = 0.5f;

    /* renamed from: b, reason: collision with root package name */
    public static float f19430b = 0.8f;

    /* renamed from: c, reason: collision with root package name */
    public static float f19431c = 1.25f;

    /* renamed from: d, reason: collision with root package name */
    public static float f19432d = 2.0f;

    /* renamed from: f, reason: collision with root package name */
    private TXSWMuxerJNI f19434f;

    /* renamed from: e, reason: collision with root package name */
    private int f19433e = 2;

    /* renamed from: g, reason: collision with root package name */
    private String f19435g = null;

    /* renamed from: h, reason: collision with root package name */
    private MediaFormat f19436h = null;

    /* renamed from: i, reason: collision with root package name */
    private MediaFormat f19437i = null;

    /* renamed from: j, reason: collision with root package name */
    private int f19438j = 0;

    /* renamed from: k, reason: collision with root package name */
    private int f19439k = 0;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19440l = false;

    /* renamed from: m, reason: collision with root package name */
    private boolean f19441m = false;

    /* renamed from: n, reason: collision with root package name */
    private ConcurrentLinkedQueue<a> f19442n = new ConcurrentLinkedQueue<>();

    /* renamed from: o, reason: collision with root package name */
    private ConcurrentLinkedQueue<a> f19443o = new ConcurrentLinkedQueue<>();

    /* renamed from: p, reason: collision with root package name */
    private long f19444p = -1;

    /* renamed from: q, reason: collision with root package name */
    private long f19445q = -1;

    /* renamed from: r, reason: collision with root package name */
    private long f19446r = -1;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        ByteBuffer f19447a;

        /* renamed from: b, reason: collision with root package name */
        MediaCodec.BufferInfo f19448b;

        public a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            this.f19447a = byteBuffer;
            this.f19448b = bufferInfo;
        }

        public ByteBuffer a() {
            return this.f19447a;
        }

        public MediaCodec.BufferInfo b() {
            return this.f19448b;
        }
    }

    private ByteBuffer d() {
        ByteBuffer byteBuffer = this.f19437i.getByteBuffer("csd-0");
        if (byteBuffer != null) {
            byteBuffer.position(0);
        }
        return byteBuffer;
    }

    private ByteBuffer e() {
        return this.f19436h.getByteBuffer("csd-0");
    }

    private ByteBuffer f() {
        return this.f19436h.getByteBuffer("csd-1");
    }

    private long g() {
        a aVarPeek;
        long j2 = this.f19442n.size() > 0 ? this.f19442n.peek().b().presentationTimeUs : 0L;
        if (this.f19443o.size() <= 0 || (aVarPeek = this.f19443o.peek()) == null || aVarPeek.b() == null) {
            return j2;
        }
        long j3 = this.f19443o.peek().b().presentationTimeUs;
        return j2 > j3 ? j3 : j2;
    }

    private void h() {
        while (this.f19442n.size() > 0) {
            a aVarPoll = this.f19442n.poll();
            c(aVarPoll.a(), aVarPoll.b());
        }
        while (this.f19443o.size() > 0) {
            a aVarPoll2 = this.f19443o.poll();
            d(aVarPoll2.a(), aVarPoll2.b());
        }
    }

    private void i() {
        while (this.f19442n.size() > 0) {
            a aVarPoll = this.f19442n.poll();
            a(aVarPoll.b().presentationTimeUs);
            c(aVarPoll.a(), aVarPoll.b());
        }
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void a(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4SWMuxer", "addVideoTrack:" + mediaFormat);
        this.f19436h = mediaFormat;
        this.f19442n.clear();
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized void b(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4SWMuxer", "addAudioTrack:" + mediaFormat);
        this.f19437i = mediaFormat;
        this.f19443o.clear();
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized boolean c() {
        return this.f19436h != null;
    }

    private void d(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f2;
        float f3;
        long j2 = bufferInfo.presentationTimeUs;
        long j3 = this.f19444p;
        long j4 = j2 - j3;
        if (j3 >= 0 && j4 >= 0) {
            if (j4 < this.f19446r) {
                TXCLog.e("TXCMP4SWMuxer", "audio is not in chronological order. current audio's pts pts(" + j4 + ") must larger than pre audio's pts(" + this.f19446r + ")");
                j4 = this.f19446r + 1;
            } else {
                this.f19446r = j4;
            }
            int i2 = this.f19433e;
            if (i2 != 2) {
                if (i2 == 3) {
                    f2 = j4;
                    f3 = f19430b;
                } else if (i2 == 4) {
                    f2 = j4;
                    f3 = f19429a;
                } else if (i2 == 1) {
                    f2 = j4;
                    f3 = f19431c;
                } else if (i2 == 0) {
                    f2 = j4;
                    f3 = f19432d;
                }
                j4 = (long) (f2 * f3);
            }
            bufferInfo.presentationTimeUs = j4;
            try {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                this.f19434f.a(byteBuffer, 0, bufferInfo.offset, bufferInfo.size, bufferInfo.flags, bufferInfo.presentationTimeUs);
                return;
            } catch (IllegalArgumentException e2) {
                TXCLog.e("TXCMP4SWMuxer", "write sample IllegalArgumentException: " + e2);
                return;
            } catch (IllegalStateException e3) {
                TXCLog.e("TXCMP4SWMuxer", "write sample IllegalStateException: " + e3);
                return;
            }
        }
        TXCLog.w("TXCMP4SWMuxer", "drop sample. first frame offset timeus = " + this.f19444p + ", current sample timeus = " + bufferInfo.presentationTimeUs);
    }

    private void c(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        float f2;
        float f3;
        long j2 = bufferInfo.presentationTimeUs - this.f19444p;
        if (j2 < 0) {
            TXCLog.e("TXCMP4SWMuxer", "pts error! first frame offset timeus = " + this.f19444p + ", current timeus = " + bufferInfo.presentationTimeUs);
            j2 = this.f19445q;
            if (j2 <= 0) {
                j2 = 0;
            }
        }
        if (j2 < this.f19445q) {
            TXCLog.w("TXCMP4SWMuxer", "video is not in chronological order. current frame's pts(" + j2 + ") smaller than pre frame's pts(" + this.f19445q + ")");
        } else {
            this.f19445q = j2;
        }
        int i2 = this.f19433e;
        if (i2 != 2) {
            if (i2 == 3) {
                f2 = j2;
                f3 = f19430b;
            } else if (i2 == 4) {
                f2 = j2;
                f3 = f19429a;
            } else if (i2 == 1) {
                f2 = j2;
                f3 = f19431c;
            } else if (i2 == 0) {
                f2 = j2;
                f3 = f19432d;
            }
            j2 = (long) (f2 * f3);
        }
        bufferInfo.presentationTimeUs = j2;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f19434f.a(byteBuffer, 1, bufferInfo.offset, bufferInfo.size, bufferInfo.flags == 1 ? 1 : 0, bufferInfo.presentationTimeUs);
            if ((bufferInfo.flags & 1) != 0) {
                this.f19441m = true;
            }
        } catch (IllegalArgumentException e2) {
            TXCLog.e("TXCMP4SWMuxer", "write frame IllegalArgumentException: " + e2);
        } catch (IllegalStateException e3) {
            TXCLog.e("TXCMP4SWMuxer", "write frame IllegalStateException: " + e3);
        }
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized int a() {
        String str = this.f19435g;
        if (str != null && !str.isEmpty()) {
            if (!c()) {
                TXCLog.e("TXCMP4SWMuxer", "video track not set yet!");
                return -2;
            }
            if (this.f19434f != null) {
                TXCLog.w("TXCMP4SWMuxer", "start has been called. stop must be called before start");
                return 0;
            }
            TXCLog.d("TXCMP4SWMuxer", "start");
            this.f19434f = new TXSWMuxerJNI();
            TXSWMuxerJNI.AVOptions aVOptions = new TXSWMuxerJNI.AVOptions();
            MediaFormat mediaFormat = this.f19436h;
            if (mediaFormat != null) {
                int integer = mediaFormat.getInteger("width");
                aVOptions.videoHeight = this.f19436h.getInteger("height");
                aVOptions.videoWidth = integer;
                aVOptions.videoGOP = this.f19436h.containsKey("i-frame-interval") ? this.f19436h.getInteger("i-frame-interval") : 3;
            }
            MediaFormat mediaFormat2 = this.f19437i;
            if (mediaFormat2 != null) {
                int integer2 = mediaFormat2.getInteger("channel-count");
                int integer3 = this.f19437i.getInteger("sample-rate");
                aVOptions.audioChannels = integer2;
                aVOptions.audioSampleRate = integer3;
            }
            ByteBuffer byteBufferE = e();
            ByteBuffer byteBufferF = f();
            ByteBuffer byteBufferD = this.f19437i != null ? d() : null;
            if (byteBufferE != null && byteBufferF != null) {
                if (this.f19437i != null && byteBufferD == null) {
                    TXCLog.e("TXCMP4SWMuxer", "audio format contains error csd!");
                    return -3;
                }
                this.f19434f.a(byteBufferE, byteBufferE.capacity(), byteBufferF, byteBufferF.capacity());
                if (this.f19437i != null) {
                    this.f19434f.a(byteBufferD, byteBufferD.capacity());
                }
                this.f19434f.a(aVOptions);
                this.f19434f.a(this.f19435g);
                this.f19434f.a();
                this.f19444p = -1L;
                this.f19440l = true;
                this.f19441m = false;
                this.f19445q = -1L;
                this.f19446r = -1L;
                return 0;
            }
            TXCLog.e("TXCMP4SWMuxer", "video format contains error csd!");
            return -3;
        }
        TXCLog.e("TXCMP4SWMuxer", "target path not set yet!");
        return -1;
    }

    @Override // com.tencent.liteav.muxer.a
    public synchronized int b() {
        if (this.f19434f != null) {
            h();
            TXCLog.d("TXCMP4SWMuxer", "stop. start flag = " + this.f19440l + ", video key frame set = " + this.f19441m);
            try {
                try {
                    if (this.f19440l && this.f19441m) {
                        this.f19434f.b();
                    }
                    this.f19434f.c();
                } finally {
                    this.f19440l = false;
                    this.f19434f = null;
                    this.f19441m = false;
                    this.f19442n.clear();
                    this.f19443o.clear();
                    this.f19436h = null;
                    this.f19437i = null;
                    this.f19445q = -1L;
                    this.f19446r = -1L;
                }
            } catch (Exception e2) {
                TXCLog.e("TXCMP4SWMuxer", "muxer stop/release exception: " + e2);
                return -1;
            }
        }
        return 0;
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
    public synchronized void a(String str) {
        this.f19435g = str;
        if (!TextUtils.isEmpty(str)) {
            File file = new File(this.f19435g);
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
                TXCLog.e("TXCMP4SWMuxer", "create new file failed.", e2);
            }
        }
    }

    public synchronized void b(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        a(false, byteBuffer, bufferInfo);
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

    public synchronized void a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (this.f19434f == null) {
            a(true, byteBuffer, bufferInfo);
            TXCLog.w("TXCMP4SWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.f19444p < 0) {
            a(true, byteBuffer, bufferInfo);
            this.f19444p = g();
            TXCLog.i("TXCMP4SWMuxer", "first frame offset = " + this.f19444p);
            i();
        } else {
            a(bufferInfo.presentationTimeUs);
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
            if (this.f19442n.size() < 200) {
                this.f19442n.add(aVar);
                return;
            } else {
                TXCLog.e("TXCMP4SWMuxer", "drop video frame. video cache size is larger than 200");
                return;
            }
        }
        this.f19443o.add(aVar);
    }

    private void a(long j2) {
        while (this.f19443o.size() > 0) {
            if (this.f19443o.peek().b() == null) {
                TXCLog.e("TXCMP4SWMuxer", "flushAudioCache, bufferInfo is null");
                this.f19443o.remove();
            } else {
                if (this.f19443o.peek().b().presentationTimeUs >= j2) {
                    return;
                }
                a aVarPoll = this.f19443o.poll();
                d(aVarPoll.a(), aVarPoll.b());
            }
        }
    }
}
