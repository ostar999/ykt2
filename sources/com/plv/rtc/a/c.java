package com.plv.rtc.a;

import android.content.Context;
import com.plv.beauty.api.IPLVBeautyManager;
import com.plv.beauty.api.PLVBeautyManager;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.mediaio.IVideoFrameConsumer;
import io.agora.rtc.mediaio.IVideoSource;
import io.agora.rtc.mediaio.MediaIO;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
public class c extends com.plv.rtc.a.b implements IVideoSource {

    /* renamed from: u, reason: collision with root package name */
    private static final int f10863u = 1280;

    /* renamed from: v, reason: collision with root package name */
    private static final int f10864v = 720;

    /* renamed from: m, reason: collision with root package name */
    private IVideoFrameConsumer f10865m;

    /* renamed from: n, reason: collision with root package name */
    private IPLVBeautyManager.InitCallback f10866n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f10867o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f10868p;

    /* renamed from: q, reason: collision with root package name */
    private volatile boolean f10869q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f10870r;

    /* renamed from: s, reason: collision with root package name */
    private int f10871s;

    /* renamed from: t, reason: collision with root package name */
    private f f10872t;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.r();
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.s();
        }
    }

    /* renamed from: com.plv.rtc.a.c$c, reason: collision with other inner class name */
    public class C0227c implements IVideoFrameConsumer {

        /* renamed from: a, reason: collision with root package name */
        private final WeakReference<IVideoFrameConsumer> f10875a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ IVideoFrameConsumer f10876b;

        public C0227c(IVideoFrameConsumer iVideoFrameConsumer) {
            this.f10876b = iVideoFrameConsumer;
            this.f10875a = new WeakReference<>(iVideoFrameConsumer);
        }

        @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
        public void consumeByteArrayFrame(byte[] bArr, int i2, int i3, int i4, int i5, long j2) {
            if (this.f10875a.get() != null) {
                this.f10875a.get().consumeByteArrayFrame(bArr, i2, i3, i4, i5, j2);
            }
        }

        @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
        public void consumeByteBufferFrame(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5, long j2) {
            if (this.f10875a.get() != null) {
                this.f10875a.get().consumeByteBufferFrame(byteBuffer, i2, i3, i4, i5, j2);
            }
        }

        @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
        public void consumeTextureFrame(int i2, int i3, int i4, int i5, int i6, long j2, float[] fArr) {
            if (this.f10875a.get() != null) {
                this.f10875a.get().consumeTextureFrame(i2, i3, i4, i5, i6, j2, fArr);
            }
        }
    }

    public class d implements IPLVBeautyManager.InitCallback {
        public d() {
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
        public void onFinishInit(Integer num) {
            c.this.f10867o = num != null && num.intValue() == 0;
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
        public void onStartInit() {
        }
    }

    public class e implements IPLVBeautyManager.SetupCallback {
        public e() {
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.SetupCallback
        public void onSetup(boolean z2) {
        }
    }

    public interface f {
        void a(MediaIO.PixelFormat pixelFormat);
    }

    public c(Context context) {
        this(context, 1280, 720);
    }

    private void c(boolean z2) {
        this.f10868p = z2;
        f fVar = this.f10872t;
        if (fVar != null) {
            fVar.a(o());
        }
    }

    private void p() {
        if (!this.f10868p && PLVBeautyManager.getInstance().isBeautySupport() && this.f10867o) {
            PLVBeautyManager.getInstance().release();
            PLVBeautyManager.getInstance().setup(new e());
            c(true);
        }
    }

    private void q() {
        if (PLVBeautyManager.getInstance().isBeautySupport()) {
            this.f10866n = new d();
            PLVBeautyManager.getInstance().addInitCallback(new WeakReference<>(this.f10866n));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void r() {
        p();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void s() {
        c(false);
        PLVBeautyManager.getInstance().release();
    }

    @Override // com.plv.rtc.a.b, com.plv.rtc.a.a
    public void d() throws IOException {
        this.f10870r = false;
        this.f10851a.getHandler().post(new b());
        super.d();
    }

    @Override // com.plv.rtc.a.b, com.plv.rtc.a.a
    public boolean e() throws IOException {
        boolean zE = super.e();
        this.f10851a.getHandler().post(new a());
        PLVBeautyManager.getInstance().setCameraFacing(k() == 1);
        this.f10870r = zE;
        this.f10871s = 0;
        return zE;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public int getBufferType() {
        return MediaIO.BufferType.TEXTURE.intValue();
    }

    public EglBase.Context n() {
        return this.f10851a.getEglContext();
    }

    public MediaIO.PixelFormat o() {
        return (this.f10868p && this.f10869q) ? MediaIO.PixelFormat.TEXTURE_2D : MediaIO.PixelFormat.TEXTURE_OES;
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onDispose() {
        this.f10865m = null;
        a();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onInitialize(IVideoFrameConsumer iVideoFrameConsumer) {
        this.f10865m = a(iVideoFrameConsumer);
        return c();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onStart() {
        return i();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onStop() {
        j();
    }

    @Override // com.plv.rtc.a.b, com.plv.rtc.a.a, io.agora.rtc.mediaio.SurfaceTextureHelper.OnTextureFrameAvailableListener
    public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
        super.onTextureFrameAvailable(i2, fArr, j2);
        if (this.f10870r) {
            int iL = l();
            if (PLVBeautyManager.getInstance().isBeautySupport()) {
                p();
            }
            if (!this.f10868p || !this.f10869q) {
                IVideoFrameConsumer iVideoFrameConsumer = this.f10865m;
                if (iVideoFrameConsumer != null) {
                    iVideoFrameConsumer.consumeTextureFrame(i2, MediaIO.PixelFormat.TEXTURE_OES.intValue(), this.f10854d, this.f10855e, iL, System.currentTimeMillis(), fArr);
                    return;
                }
                return;
            }
            int iProcessTextureOesTo2d = PLVBeautyManager.getInstance().processTextureOesTo2d(i2, this.f10854d, this.f10855e, iL, System.nanoTime());
            int i3 = this.f10871s;
            if (i3 < 2) {
                this.f10871s = i3 + 1;
                return;
            }
            IVideoFrameConsumer iVideoFrameConsumer2 = this.f10865m;
            if (iVideoFrameConsumer2 != null) {
                iVideoFrameConsumer2.consumeTextureFrame(iProcessTextureOesTo2d, MediaIO.PixelFormat.TEXTURE_2D.intValue(), this.f10854d, this.f10855e, iL, System.currentTimeMillis(), fArr);
            }
        }
    }

    public c(Context context, int i2, int i3) {
        super(context, i2, i3);
        this.f10869q = true;
        this.f10871s = Integer.MAX_VALUE;
        q();
    }

    public void b(boolean z2) {
        this.f10869q = z2;
        f fVar = this.f10872t;
        if (fVar != null) {
            fVar.a(o());
        }
    }

    private static IVideoFrameConsumer a(IVideoFrameConsumer iVideoFrameConsumer) {
        return new C0227c(iVideoFrameConsumer);
    }

    public c a(f fVar) {
        this.f10872t = fVar;
        return this;
    }
}
