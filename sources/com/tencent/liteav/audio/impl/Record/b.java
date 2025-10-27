package com.tencent.liteav.audio.impl.Record;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.util.MimeTypes;
import com.tencent.liteav.audio.g;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.h;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Vector;

/* loaded from: classes6.dex */
public class b extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private MediaCodec.BufferInfo f18181a;

    /* renamed from: b, reason: collision with root package name */
    private MediaCodecInfo f18182b;

    /* renamed from: c, reason: collision with root package name */
    private MediaFormat f18183c;

    /* renamed from: d, reason: collision with root package name */
    private MediaCodec f18184d;

    /* renamed from: e, reason: collision with root package name */
    private Vector<byte[]> f18185e;

    /* renamed from: f, reason: collision with root package name */
    private WeakReference<g> f18186f;

    /* renamed from: g, reason: collision with root package name */
    private volatile boolean f18187g;

    /* renamed from: h, reason: collision with root package name */
    private volatile boolean f18188h;

    /* renamed from: i, reason: collision with root package name */
    private final Object f18189i;

    /* renamed from: j, reason: collision with root package name */
    private long f18190j;

    /* renamed from: k, reason: collision with root package name */
    private int f18191k;

    /* renamed from: l, reason: collision with root package name */
    private int f18192l;

    /* renamed from: m, reason: collision with root package name */
    private int f18193m;

    /* renamed from: n, reason: collision with root package name */
    private byte[] f18194n;

    static {
        h.d();
    }

    @TargetApi(16)
    public b() {
        super("TXAudioRecordThread");
        this.f18187g = false;
        this.f18188h = false;
        this.f18189i = new Object();
        this.f18190j = 0L;
        this.f18191k = 48000;
        this.f18192l = 1;
        this.f18193m = 16;
    }

    private void b() {
        MediaCodecInfo mediaCodecInfoA = a(MimeTypes.AUDIO_AAC);
        this.f18182b = mediaCodecInfoA;
        if (mediaCodecInfoA == null) {
            TXCLog.e("AudioCenter:TXCAudioHWEncoder", "Unable to find an appropriate codec for audio/mp4a-latm");
            return;
        }
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "selected codec: " + this.f18182b.getName());
        int i2 = this.f18191k;
        int i3 = i2 >= 32000 ? 64000 : 32000;
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, i2, this.f18192l);
        this.f18183c = mediaFormatCreateAudioFormat;
        mediaFormatCreateAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, i3);
        this.f18183c.setInteger("channel-count", this.f18192l);
        this.f18183c.setInteger("sample-rate", this.f18191k);
        this.f18183c.setInteger("aac-profile", 2);
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "format: " + this.f18183c);
        try {
            d();
        } catch (Exception e2) {
            TXCLog.e("AudioCenter:TXCAudioHWEncoder", "start media codec failed.", e2);
        }
        start();
    }

    private void c() {
        this.f18188h = true;
    }

    @TargetApi(16)
    private void d() throws IOException {
        if (this.f18184d != null) {
            return;
        }
        MediaCodec mediaCodecCreateEncoderByType = MediaCodec.createEncoderByType(MimeTypes.AUDIO_AAC);
        this.f18184d = mediaCodecCreateEncoderByType;
        mediaCodecCreateEncoderByType.configure(this.f18183c, (Surface) null, (MediaCrypto) null, 1);
        this.f18184d.start();
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "prepare finishing");
        this.f18187g = true;
    }

    private void e() {
        MediaCodec mediaCodec = this.f18184d;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.f18184d.release();
            this.f18184d = null;
        }
        this.f18187g = false;
    }

    private long f() {
        long timeTick = TXCTimeUtil.getTimeTick();
        long j2 = this.f18190j;
        return timeTick < j2 ? timeTick + (j2 - timeTick) : timeTick;
    }

    public void a(int i2, int i3, int i4, int i5, WeakReference<g> weakReference) {
        this.f18186f = weakReference;
        this.f18181a = new MediaCodec.BufferInfo();
        this.f18185e = new Vector<>();
        this.f18191k = i3;
        this.f18192l = i4;
        this.f18193m = i5;
        b();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException {
        boolean zIsEmpty;
        byte[] bArrRemove;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(1024);
        while (!this.f18188h) {
            if (this.f18187g) {
                synchronized (this.f18185e) {
                    zIsEmpty = this.f18185e.isEmpty();
                }
                if (zIsEmpty) {
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException unused) {
                    }
                } else {
                    synchronized (this.f18185e) {
                        bArrRemove = this.f18185e.remove(0);
                    }
                    if (bArrRemove != null) {
                        try {
                            byteBufferAllocateDirect.clear();
                            if (bArrRemove.length > byteBufferAllocateDirect.capacity()) {
                                byteBufferAllocateDirect = ByteBuffer.allocateDirect(bArrRemove.length);
                            }
                            byteBufferAllocateDirect.clear();
                            byteBufferAllocateDirect.put(bArrRemove);
                            byteBufferAllocateDirect.flip();
                            a(byteBufferAllocateDirect, bArrRemove.length, f());
                        } catch (Exception e2) {
                            TXCLog.e("AudioCenter:TXCAudioHWEncoder", "encode frame failed.", e2);
                        }
                    }
                }
            } else {
                synchronized (this.f18189i) {
                    try {
                        this.f18189i.wait();
                    } catch (InterruptedException unused2) {
                    }
                }
            }
        }
        e();
    }

    public void a(byte[] bArr, long j2) {
        Vector<byte[]> vector = this.f18185e;
        if (vector != null && bArr != null) {
            synchronized (vector) {
                Vector<byte[]> vector2 = this.f18185e;
                if (vector2 == null) {
                    return;
                } else {
                    vector2.add(bArr);
                }
            }
        }
        synchronized (this.f18189i) {
            this.f18189i.notify();
        }
    }

    private void b(byte[] bArr, long j2) {
        g gVar;
        WeakReference<g> weakReference = this.f18186f;
        if (weakReference == null || (gVar = weakReference.get()) == null) {
            return;
        }
        gVar.onRecordEncData(bArr, j2, this.f18191k, this.f18192l, this.f18193m);
    }

    public void a() {
        c();
    }

    private void a(ByteBuffer byteBuffer, int i2, long j2) throws MediaCodec.CryptoException {
        int iDequeueOutputBuffer;
        if (this.f18188h) {
            return;
        }
        ByteBuffer[] inputBuffers = this.f18184d.getInputBuffers();
        int iDequeueInputBuffer = this.f18184d.dequeueInputBuffer(com.heytap.mcssdk.constant.a.f7153q);
        if (iDequeueInputBuffer >= 0) {
            ByteBuffer byteBuffer2 = inputBuffers[iDequeueInputBuffer];
            byteBuffer2.clear();
            if (byteBuffer != null) {
                byteBuffer2.put(byteBuffer);
            }
            if (i2 <= 0) {
                TXCLog.i("AudioCenter:TXCAudioHWEncoder", "send BUFFER_FLAG_END_OF_STREAM");
                this.f18184d.queueInputBuffer(iDequeueInputBuffer, 0, 0, j2, 4);
            } else {
                this.f18184d.queueInputBuffer(iDequeueInputBuffer, 0, i2, j2, 0);
            }
        }
        ByteBuffer[] outputBuffers = this.f18184d.getOutputBuffers();
        do {
            iDequeueOutputBuffer = this.f18184d.dequeueOutputBuffer(this.f18181a, com.heytap.mcssdk.constant.a.f7153q);
            if (iDequeueOutputBuffer != -1) {
                if (iDequeueOutputBuffer == -3) {
                    outputBuffers = this.f18184d.getOutputBuffers();
                } else if (iDequeueOutputBuffer == -2) {
                    this.f18184d.getOutputFormat();
                } else if (iDequeueOutputBuffer >= 0) {
                    ByteBuffer byteBuffer3 = outputBuffers[iDequeueOutputBuffer];
                    if ((this.f18181a.flags & 2) != 0) {
                        TXCLog.d("AudioCenter:TXCAudioHWEncoder", "drain:BUFFER_FLAG_CODEC_CONFIG");
                        this.f18181a.size = 0;
                    }
                    MediaCodec.BufferInfo bufferInfo = this.f18181a;
                    if (bufferInfo.size != 0) {
                        bufferInfo.presentationTimeUs = f();
                        byte[] bArr = new byte[byteBuffer3.limit()];
                        this.f18194n = bArr;
                        byteBuffer3.get(bArr);
                        b(this.f18194n, this.f18181a.presentationTimeUs);
                        this.f18190j = this.f18181a.presentationTimeUs;
                    }
                    this.f18184d.releaseOutputBuffer(iDequeueOutputBuffer, false);
                }
            }
        } while (iDequeueOutputBuffer >= 0);
    }

    private static final MediaCodecInfo a(String str) {
        TXCLog.v("AudioCenter:TXCAudioHWEncoder", "selectAudioCodec:");
        int codecCount = MediaCodecList.getCodecCount();
        for (int i2 = 0; i2 < codecCount; i2++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (int i3 = 0; i3 < supportedTypes.length; i3++) {
                    TXCLog.i("AudioCenter:TXCAudioHWEncoder", "supportedType:" + codecInfoAt.getName() + ",MIME=" + supportedTypes[i3]);
                    if (supportedTypes[i3].equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
            }
        }
        return null;
    }
}
