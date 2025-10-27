package com.tencent.liteav.videoengine.decoder;

import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Looper;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.a.a;
import com.tencent.liteav.videobase.frame.PixelFrame;
import com.tencent.liteav.videobase.frame.c;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import com.tencent.liteav.videoengine.decoder.n;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class h implements SurfaceTexture.OnFrameAvailableListener, n {

    /* renamed from: a, reason: collision with root package name */
    @NonNull
    private final com.tencent.liteav.basic.util.e f20334a;

    /* renamed from: b, reason: collision with root package name */
    @NonNull
    private final PixelFrame f20335b;

    /* renamed from: c, reason: collision with root package name */
    @NonNull
    private final com.tencent.liteav.basic.util.f f20336c;

    /* renamed from: d, reason: collision with root package name */
    @NonNull
    private final com.tencent.liteav.videobase.f.a f20337d;

    /* renamed from: e, reason: collision with root package name */
    private final boolean f20338e;

    /* renamed from: f, reason: collision with root package name */
    private final JSONArray f20339f;

    /* renamed from: g, reason: collision with root package name */
    private MediaCodec f20340g;

    /* renamed from: h, reason: collision with root package name */
    private o f20341h;

    /* renamed from: i, reason: collision with root package name */
    private final MediaCodec.BufferInfo f20342i;

    /* renamed from: j, reason: collision with root package name */
    private final Deque<com.tencent.liteav.videobase.e.b> f20343j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f20344k;

    /* renamed from: l, reason: collision with root package name */
    private com.tencent.liteav.videobase.c.c f20345l;

    /* renamed from: m, reason: collision with root package name */
    private int f20346m;

    /* renamed from: n, reason: collision with root package name */
    private SurfaceTexture f20347n;

    /* renamed from: o, reason: collision with root package name */
    private Surface f20348o;

    /* renamed from: p, reason: collision with root package name */
    private com.tencent.liteav.videobase.frame.c f20349p;

    /* renamed from: q, reason: collision with root package name */
    private com.tencent.liteav.videobase.frame.f f20350q;

    public h(@NonNull Looper looper, @NonNull com.tencent.liteav.basic.util.e eVar, boolean z2, @Nullable JSONArray jSONArray, @NonNull com.tencent.liteav.videobase.f.a aVar) {
        com.tencent.liteav.basic.util.e eVar2 = new com.tencent.liteav.basic.util.e();
        this.f20334a = eVar2;
        this.f20335b = new PixelFrame();
        this.f20340g = null;
        this.f20342i = new MediaCodec.BufferInfo();
        this.f20343j = new LinkedList();
        this.f20344k = true;
        this.f20346m = -1;
        this.f20338e = z2;
        this.f20339f = jSONArray;
        eVar2.f18712a = eVar.f18712a;
        eVar2.f18713b = eVar.f18713b;
        this.f20336c = new com.tencent.liteav.basic.util.f(looper);
        this.f20337d = aVar;
        TXCLog.i("HardwareVideoDecoder", "create decoder %s, useHevc: %b, params: %s", eVar, Boolean.valueOf(z2), jSONArray);
    }

    private void b() {
        MediaFormat outputFormat = this.f20340g.getOutputFormat();
        TXCLog.i("HardwareVideoDecoder", "decode output format changed: " + outputFormat);
        TXCLog.i("HardwareVideoDecoder", "cropWidth: %d, cropHeight: %d, frameWidth: %d, frameHeight: %d", Integer.valueOf(Math.abs(outputFormat.getInteger("crop-right") - outputFormat.getInteger("crop-left")) + 1), Integer.valueOf(Math.abs(outputFormat.getInteger("crop-bottom") - outputFormat.getInteger("crop-top")) + 1), Integer.valueOf(outputFormat.getInteger("width")), Integer.valueOf(outputFormat.getInteger("height")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x0019 -> B:14:0x003e). Please report as a decompilation issue!!! */
    public void c() {
        String str = "release MediaCodec failed.";
        TXCLog.i("HardwareVideoDecoder", "stop internal");
        MediaCodec mediaCodec = this.f20340g;
        if (mediaCodec != null) {
            try {
                try {
                    try {
                        mediaCodec.stop();
                        this.f20340g.release();
                    } catch (Throwable th) {
                        try {
                            this.f20340g.release();
                        } catch (Exception e2) {
                            TXCLog.e("HardwareVideoDecoder", "release MediaCodec failed.", e2);
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    TXCLog.e("HardwareVideoDecoder", str, e3);
                }
            } catch (Exception e4) {
                TXCLog.e("HardwareVideoDecoder", "stop MediaCodec failed." + e4.getMessage());
                this.f20340g.release();
            }
            str = null;
            this.f20340g = null;
        }
        d();
    }

    private void d() {
        TXCLog.i("HardwareVideoDecoder", "uninitialize gl components");
        if (e()) {
            com.tencent.liteav.videobase.frame.c cVar = this.f20349p;
            if (cVar != null) {
                cVar.a();
                this.f20349p.b();
                this.f20349p = null;
            }
            com.tencent.liteav.videobase.frame.f fVar = this.f20350q;
            if (fVar != null) {
                fVar.a();
                this.f20350q = null;
            }
            Surface surface = this.f20348o;
            if (surface != null) {
                surface.release();
                this.f20348o = null;
            }
            SurfaceTexture surfaceTexture = this.f20347n;
            if (surfaceTexture != null) {
                surfaceTexture.release();
                this.f20347n = null;
            }
            OpenGlUtils.deleteTexture(this.f20346m);
            this.f20346m = -1;
            try {
                com.tencent.liteav.videobase.c.c cVar2 = this.f20345l;
                if (cVar2 != null) {
                    cVar2.b();
                    this.f20345l.d();
                }
            } catch (com.tencent.liteav.videobase.c.d e2) {
                TXCLog.e("HardwareVideoDecoder", "destroy EGLCore failed.", e2);
            }
            this.f20345l = null;
        }
    }

    private boolean e() {
        try {
            com.tencent.liteav.videobase.c.c cVar = this.f20345l;
            if (cVar == null) {
                return true;
            }
            cVar.a();
            return true;
        } catch (com.tencent.liteav.videobase.c.d e2) {
            TXCLog.e("HardwareVideoDecoder", "makeCurrent failed.", e2);
            return false;
        }
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public void decode(com.tencent.liteav.videobase.e.b bVar) {
        a(j.a(this, bVar));
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public n.a getDecoderType() {
        return n.a.HARDWARE;
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        a(l.a(this, surfaceTexture));
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public void start(Object obj, o oVar) {
        a(i.a(this, obj, oVar));
    }

    @Override // com.tencent.liteav.videoengine.decoder.n
    public void stop() {
        a(k.a(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, o oVar) throws JSONException, IOException {
        String str;
        TXCLog.i("HardwareVideoDecoder", "start internal");
        if (this.f20345l != null) {
            TXCLog.w("HardwareVideoDecoder", "Decoder already started.");
            return;
        }
        this.f20341h = oVar;
        if (a(obj)) {
            String str2 = this.f20338e ? MimeTypes.VIDEO_H265 : MimeTypes.VIDEO_H264;
            com.tencent.liteav.basic.util.e eVar = this.f20334a;
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(str2, eVar.f18712a, eVar.f18713b);
            a(mediaFormatCreateVideoFormat, this.f20339f);
            try {
                MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(str2);
                this.f20340g = mediaCodecCreateDecoderByType;
                mediaCodecCreateDecoderByType.configure(mediaFormatCreateVideoFormat, this.f20348o, (MediaCrypto) null, 0);
                this.f20340g.setVideoScalingMode(1);
                this.f20340g.start();
                TXCLog.i("HardwareVideoDecoder", "start MediaCodec success.");
                this.f20337d.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_START_SUCCESS, "VideoDecode: start decoder success", "", new Object[0]);
                this.f20335b.setPixelBufferType(a.b.TEXTURE_OES);
                this.f20335b.setPixelFormatType(a.c.RGBA);
                this.f20335b.setWidth(this.f20334a.f18712a);
                this.f20335b.setHeight(this.f20334a.f18713b);
                this.f20335b.setGLContext(this.f20345l.c());
                this.f20335b.setTextureId(this.f20346m);
                this.f20335b.setMatrix(new float[16]);
            } catch (Exception e2) {
                TXCLog.e("HardwareVideoDecoder", "start MediaCodec failed.", e2);
                com.tencent.liteav.videobase.f.b bVar = com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_START_FAILED;
                if (e2 instanceof IllegalArgumentException) {
                    bVar = com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_START_FAILED_ILLEGAL_ARGUMENT;
                    str = "VideoDecode: illegal argument, start decoder failed";
                } else if (e2 instanceof IllegalStateException) {
                    bVar = com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_START_FAILED_ILLEGAL_STATE;
                    str = "VideoDecode: illegal state, start decoder failed";
                } else {
                    str = "VideoDecode: start decoder failed";
                }
                a(bVar, str, "exception: %s", e2.getMessage());
            }
        }
    }

    private boolean b(com.tencent.liteav.videobase.e.b bVar) throws MediaCodec.CryptoException {
        if (bVar != null && bVar.f19989a.length != 0) {
            ByteBuffer[] inputBuffers = this.f20340g.getInputBuffers();
            if (com.tencent.liteav.videobase.utils.a.a(inputBuffers)) {
                TXCLog.e("HardwareVideoDecoder", "get invalid input buffers.");
                return false;
            }
            int iDequeueInputBuffer = this.f20340g.dequeueInputBuffer(com.heytap.mcssdk.constant.a.f7153q);
            if (iDequeueInputBuffer < 0) {
                return false;
            }
            inputBuffers[iDequeueInputBuffer].put(bVar.f19989a);
            this.f20340g.queueInputBuffer(iDequeueInputBuffer, 0, bVar.f19989a.length, bVar.f19994f, 0);
            return true;
        }
        TXCLog.w("HardwareVideoDecoder", "receive empty buffer.");
        return true;
    }

    private boolean a(Object obj) {
        com.tencent.liteav.videobase.c.c cVar = new com.tencent.liteav.videobase.c.c();
        this.f20345l = cVar;
        try {
            cVar.a(obj, null, 128, 128);
            this.f20345l.a();
            this.f20346m = OpenGlUtils.generateTextureOES();
            try {
                this.f20347n = new SurfaceTexture(this.f20346m);
                this.f20348o = new Surface(this.f20347n);
                this.f20347n.setOnFrameAvailableListener(this);
                this.f20349p = new com.tencent.liteav.videobase.frame.c();
                com.tencent.liteav.basic.util.e eVar = this.f20334a;
                this.f20350q = new com.tencent.liteav.videobase.frame.f(eVar.f18712a, eVar.f18713b);
                TXCLog.i("HardwareVideoDecoder", "initialize gl components");
                return true;
            } catch (Surface.OutOfResourcesException e2) {
                TXCLog.e("HardwareVideoDecoder", "create SurfaceTexture failed.", e2);
                a(com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_START_FAILED_INSUFFICIENT_RESOURCE, "VideoDecode: insufficient resource, start decoder failed", "error message: %s", e2.getMessage());
                return false;
            }
        } catch (com.tencent.liteav.videobase.c.d e3) {
            TXCLog.e("HardwareVideoDecoder", "create EGLCore failed.", e3);
            a(com.tencent.liteav.videobase.f.b.ERR_VIDEO_DECODE_EGL_CORE_CREATE_FAILED, "VideoDecode: create EGLCore failed", "errorCode: %d", Integer.valueOf(e3.a()));
            return false;
        }
    }

    private static void a(MediaFormat mediaFormat, JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return;
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                mediaFormat.setInteger(jSONObject.optString("key"), jSONObject.optInt("value"));
            } catch (JSONException e2) {
                TXCLog.e("HardwareVideoDecoder", "set MediaCodec device related params failed.", e2);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.tencent.liteav.videobase.e.b bVar) {
        if (this.f20340g == null) {
            TXCLog.w("HardwareVideoDecoder", "MediaCodec is stopped.");
            return;
        }
        if (bVar != null) {
            this.f20343j.addLast(bVar);
        }
        while (this.f20344k && a()) {
            try {
            } catch (Exception e2) {
                TXCLog.e("HardwareVideoDecoder", "decode failed.", e2);
                a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_RESTART_WHEN_DECODE_ERROR, "VideoDecode: decode error, restart decoder", "message: %s", e2.getMessage());
                return;
            }
        }
        while (!this.f20343j.isEmpty() && b(this.f20343j.peekFirst())) {
            this.f20343j.removeFirst();
        }
    }

    private boolean a() {
        int iDequeueOutputBuffer = this.f20340g.dequeueOutputBuffer(this.f20342i, TimeUnit.MILLISECONDS.toMicros(10L));
        if (iDequeueOutputBuffer == -1) {
            return false;
        }
        if (iDequeueOutputBuffer == -3) {
            TXCLog.i("HardwareVideoDecoder", "on output buffers changed");
            return false;
        }
        if (iDequeueOutputBuffer == -2) {
            b();
            return true;
        }
        if (iDequeueOutputBuffer < 0) {
            TXCLog.d("HardwareVideoDecoder", "dequeueOutputBuffer get invalid index: %d", Integer.valueOf(iDequeueOutputBuffer));
            return false;
        }
        this.f20344k = false;
        this.f20340g.releaseOutputBuffer(iDequeueOutputBuffer, true);
        if ((this.f20342i.flags & 4) != 0) {
            TXCLog.i("HardwareVideoDecoder", "meet end of stream.");
        }
        return true;
    }

    public static /* synthetic */ void a(h hVar, SurfaceTexture surfaceTexture) {
        if (hVar.f20347n == null) {
            return;
        }
        hVar.e();
        surfaceTexture.updateTexImage();
        surfaceTexture.getTransformMatrix(hVar.f20335b.getMatrix());
        com.tencent.liteav.videobase.frame.c cVar = hVar.f20349p;
        com.tencent.liteav.basic.util.e eVar = hVar.f20334a;
        c.a aVarA = cVar.a(eVar.f18712a, eVar.f18713b);
        hVar.f20350q.a(hVar.f20335b, a.EnumC0340a.CENTER_CROP, aVarA);
        GLES20.glFinish();
        hVar.f20344k = true;
        PixelFrame pixelFrameA = aVarA.c().a(hVar.f20345l.c());
        pixelFrameA.setTimestamp(surfaceTexture.getTimestamp() / 1000);
        hVar.f20341h.onDecodeFrame(pixelFrameA, surfaceTexture.getTimestamp() / 1000);
        pixelFrameA.release();
        hVar.f20349p.a(aVarA);
    }

    private void a(com.tencent.liteav.videobase.f.b bVar, String str, String str2, Object... objArr) {
        this.f20337d.b(bVar, str, str2, objArr);
        o oVar = this.f20341h;
        if (oVar != null) {
            oVar.onDecodeFailed(bVar);
        }
    }

    private void a(Runnable runnable) {
        if (Looper.myLooper() == this.f20336c.getLooper()) {
            runnable.run();
        } else {
            this.f20336c.post(runnable);
        }
    }
}
