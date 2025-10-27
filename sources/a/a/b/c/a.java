package a.a.b.c;

import a.a.a.a.f.e;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.plv.rtc.model.PLVARTCEncoderConfiguration;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.mediaio.IVideoFrameConsumer;
import io.agora.rtc.mediaio.IVideoSource;
import io.agora.rtc.mediaio.MediaIO;

/* loaded from: classes.dex */
public class a implements IVideoSource {

    /* renamed from: g, reason: collision with root package name */
    private static final String f1173g = "a";

    /* renamed from: h, reason: collision with root package name */
    private static final int f1174h = 3;

    /* renamed from: a, reason: collision with root package name */
    private b f1175a;

    /* renamed from: b, reason: collision with root package name */
    private volatile a.a.b.b f1176b;

    /* renamed from: c, reason: collision with root package name */
    private volatile a.a.b.b f1177c;

    /* renamed from: d, reason: collision with root package name */
    private volatile IVideoFrameConsumer f1178d;

    /* renamed from: e, reason: collision with root package name */
    private Context f1179e;

    /* renamed from: f, reason: collision with root package name */
    private RtcEngine f1180f;

    public class b extends Thread {

        /* renamed from: a, reason: collision with root package name */
        private final String f1181a;

        /* renamed from: b, reason: collision with root package name */
        private final int f1182b;

        /* renamed from: c, reason: collision with root package name */
        private a.a.a.a.f.b f1183c;

        /* renamed from: d, reason: collision with root package name */
        private EGLSurface f1184d;

        /* renamed from: e, reason: collision with root package name */
        private int f1185e;

        /* renamed from: f, reason: collision with root package name */
        private SurfaceTexture f1186f;

        /* renamed from: g, reason: collision with root package name */
        private Surface f1187g;

        /* renamed from: h, reason: collision with root package name */
        private float[] f1188h;

        /* renamed from: i, reason: collision with root package name */
        private a.a.b.a f1189i;

        /* renamed from: j, reason: collision with root package name */
        int f1190j;

        /* renamed from: k, reason: collision with root package name */
        int f1191k;

        /* renamed from: l, reason: collision with root package name */
        private volatile boolean f1192l;

        /* renamed from: m, reason: collision with root package name */
        private volatile boolean f1193m;

        private b() {
            this.f1181a = b.class.getSimpleName();
            this.f1182b = 1;
            this.f1188h = new float[16];
        }

        private void c() {
            a.a.a.a.f.b bVar = new a.a.a.a.f.b();
            this.f1183c = bVar;
            EGLSurface eGLSurfaceA = bVar.a(1, 1);
            this.f1184d = eGLSurfaceA;
            this.f1183c.b(eGLSurfaceA);
            this.f1185e = e.a(36197);
            this.f1186f = new SurfaceTexture(this.f1185e);
            this.f1187g = new Surface(this.f1186f);
            a.a.b.a aVar = new a.a.b.a();
            this.f1189i = aVar;
            a.a.a.a.f.b bVar2 = this.f1183c;
            aVar.f1170a = bVar2;
            aVar.f1171b = bVar2.b();
            this.f1189i.f1172c = new a.a.a.a.e();
            a.this.f1180f.setVideoSource(a.this);
        }

        private void d() {
            if (a.this.f1180f == null) {
                return;
            }
            this.f1187g.release();
            this.f1183c.d();
            this.f1183c.c(this.f1184d);
            this.f1186f.release();
            e.b(this.f1185e);
            this.f1185e = 0;
            this.f1183c.e();
        }

        private void e() throws InterruptedException {
            a(a.this.f1176b != null ? a.this.f1176b.c() : 1);
        }

        public void a() {
            this.f1193m = true;
        }

        public void b() {
            this.f1192l = true;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        @TargetApi(21)
        public void run() throws InterruptedException {
            c();
            while (!this.f1192l) {
                if (a.this.f1176b != a.this.f1177c) {
                    Log.i(this.f1181a, "New video input selected");
                    if (a.this.f1176b != null) {
                        a.this.f1176b.a(this.f1189i);
                        Log.i(this.f1181a, "recycle stopped input");
                    }
                    a aVar = a.this;
                    aVar.f1176b = aVar.f1177c;
                    if (a.this.f1176b != null) {
                        a.this.f1176b.a(this.f1187g);
                        Log.i(this.f1181a, "initialize new input");
                    }
                    if (a.this.f1176b != null) {
                        Size sizeA = a.this.f1176b.a();
                        this.f1190j = sizeA.getWidth();
                        int height = sizeA.getHeight();
                        this.f1191k = height;
                        this.f1186f.setDefaultBufferSize(this.f1190j, height);
                        if (this.f1193m) {
                            this.f1193m = false;
                        }
                    }
                } else if (a.this.f1176b != null && !a.this.f1176b.b()) {
                    Log.i(this.f1181a, "current video input is not running");
                    a.this.f1176b.a(this.f1189i);
                    a.this.f1176b = null;
                    a.this.f1177c = null;
                }
                if (this.f1193m || a.this.f1176b == null) {
                    a(1);
                } else {
                    try {
                        this.f1186f.updateTexImage();
                        this.f1186f.getTransformMatrix(this.f1188h);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (a.this.f1176b != null) {
                        a.this.f1176b.a(this.f1189i, this.f1185e, this.f1188h);
                    }
                    this.f1183c.b(this.f1184d);
                    GLES20.glViewport(0, 0, this.f1190j, this.f1191k);
                    if (a.this.f1178d != null) {
                        Log.e(this.f1181a, "publish stream with ->width:" + this.f1190j + ",height:" + this.f1191k);
                        a.this.f1178d.consumeTextureFrame(this.f1185e, MediaIO.PixelFormat.TEXTURE_OES.intValue(), this.f1190j, this.f1191k, 0, System.currentTimeMillis(), this.f1188h);
                    }
                    e();
                }
            }
            if (a.this.f1176b != null) {
                a.this.f1176b.a(this.f1189i);
            }
            d();
        }

        private void a(int i2) throws InterruptedException {
            try {
                Thread.sleep(i2);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    public a(Context context, RtcEngine rtcEngine) {
        this.f1179e = context;
        this.f1180f = rtcEngine;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public int getBufferType() {
        return MediaIO.BufferType.TEXTURE.intValue();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onDispose() {
        Log.e(f1173g, "SwitchExternalVideo-onDispose");
        this.f1178d = null;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onInitialize(IVideoFrameConsumer iVideoFrameConsumer) {
        this.f1178d = iVideoFrameConsumer;
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onStart() {
        return true;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onStop() {
    }

    public void a() {
        b bVar = new b();
        this.f1175a = bVar;
        bVar.start();
    }

    public void b() {
        b bVar = this.f1175a;
        if (bVar != null) {
            bVar.b();
        }
    }

    public boolean a(Intent intent, PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration) {
        if (this.f1176b != null && this.f1176b.b()) {
            return false;
        }
        PLVARTCEncoderConfiguration.VideoDimensions videoDimensions = pLVARTCEncoderConfiguration.dimensions;
        int i2 = videoDimensions.width;
        int i3 = videoDimensions.height;
        int i4 = pLVARTCEncoderConfiguration.frameRate;
        Log.i(f1173g, "ScreenShare:" + i2 + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + i3 + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + 3 + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + i4);
        a(new a.a.b.c.b(this.f1179e, i2, i3, 3, i4, intent));
        return true;
    }

    private void a(a.a.b.b bVar) {
        b bVar2 = this.f1175a;
        if (bVar2 != null && bVar2.isAlive()) {
            this.f1175a.a();
        }
        this.f1177c = bVar;
    }
}
