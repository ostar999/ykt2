package com.tencent.liteav;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18312a = "b";

    /* renamed from: d, reason: collision with root package name */
    private a f18315d;

    /* renamed from: e, reason: collision with root package name */
    private HandlerThread f18316e;

    /* renamed from: k, reason: collision with root package name */
    private WeakReference<InterfaceC0327b> f18322k;

    /* renamed from: b, reason: collision with root package name */
    private int f18313b = 300;

    /* renamed from: c, reason: collision with root package name */
    private long f18314c = 0;

    /* renamed from: f, reason: collision with root package name */
    private boolean f18317f = false;

    /* renamed from: g, reason: collision with root package name */
    private ByteBuffer f18318g = null;

    /* renamed from: h, reason: collision with root package name */
    private Bitmap f18319h = null;

    /* renamed from: i, reason: collision with root package name */
    private int f18320i = 0;

    /* renamed from: j, reason: collision with root package name */
    private int f18321j = 0;

    public class a extends Handler {

        /* renamed from: b, reason: collision with root package name */
        private int f18324b;

        /* renamed from: c, reason: collision with root package name */
        private long f18325c;

        public a(Looper looper, int i2, long j2) {
            super(looper);
            this.f18324b = i2;
            this.f18325c = j2;
            TXCLog.w(b.f18312a, "bkgpush:init publish time delay:" + this.f18324b + ", end:" + this.f18325c);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1001) {
                try {
                    b.this.f();
                    if (this.f18325c >= 0 && System.currentTimeMillis() >= this.f18325c) {
                        TXCLog.w(b.f18312a, "bkgpush:stop background publish when timeout");
                        if (b.this.f18322k == null || !b.this.f18317f) {
                            return;
                        }
                        InterfaceC0327b interfaceC0327b = (InterfaceC0327b) b.this.f18322k.get();
                        if (interfaceC0327b != null) {
                            interfaceC0327b.a();
                        }
                        b.this.f18317f = false;
                        return;
                    }
                    sendEmptyMessageDelayed(1001, this.f18324b);
                } catch (Exception e2) {
                    TXCLog.e(b.f18312a, "publish image failed." + e2.getMessage());
                }
            }
        }
    }

    /* renamed from: com.tencent.liteav.b$b, reason: collision with other inner class name */
    public interface InterfaceC0327b {
        void a();

        void a(Bitmap bitmap, ByteBuffer byteBuffer, int i2, int i3);
    }

    public b(InterfaceC0327b interfaceC0327b) {
        this.f18322k = null;
        this.f18322k = new WeakReference<>(interfaceC0327b);
    }

    private void d() {
        e();
        HandlerThread handlerThread = new HandlerThread("TXImageCapturer");
        this.f18316e = handlerThread;
        handlerThread.start();
        this.f18315d = new a(this.f18316e.getLooper(), this.f18313b, this.f18314c);
    }

    private void e() {
        a aVar = this.f18315d;
        if (aVar != null) {
            aVar.removeCallbacksAndMessages(null);
            this.f18315d = null;
        }
        HandlerThread handlerThread = this.f18316e;
        if (handlerThread != null) {
            handlerThread.quit();
            this.f18316e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        int i2;
        InterfaceC0327b interfaceC0327b;
        ByteBuffer byteBufferAllocateDirect;
        int height = 0;
        try {
            WeakReference<InterfaceC0327b> weakReference = this.f18322k;
            if (weakReference == null || !this.f18317f || (interfaceC0327b = weakReference.get()) == null) {
                return;
            }
            Bitmap bitmap = this.f18319h;
            ByteBuffer byteBuffer = this.f18318g;
            if (byteBuffer != null || bitmap == null) {
                byteBufferAllocateDirect = byteBuffer;
                i2 = 0;
            } else {
                int width = bitmap.getWidth();
                try {
                    height = bitmap.getHeight();
                    byteBufferAllocateDirect = ByteBuffer.allocateDirect(width * height * 4);
                    bitmap.copyPixelsToBuffer(byteBufferAllocateDirect);
                    byteBufferAllocateDirect.rewind();
                    this.f18318g = byteBufferAllocateDirect;
                    i2 = height;
                    height = width;
                } catch (Error unused) {
                    i2 = height;
                    height = width;
                    TXCLog.w(f18312a, "bkgpush: generate bitmap pixel error " + height + "*" + i2);
                } catch (Exception unused2) {
                    i2 = height;
                    height = width;
                    TXCLog.w(f18312a, "bkgpush: generate bitmap pixel exception " + height + "*" + i2);
                }
            }
            if (bitmap == null || byteBufferAllocateDirect == null) {
                return;
            }
            try {
                interfaceC0327b.a(bitmap, byteBufferAllocateDirect, this.f18320i, this.f18321j);
            } catch (Error unused3) {
                TXCLog.w(f18312a, "bkgpush: generate bitmap pixel error " + height + "*" + i2);
            } catch (Exception unused4) {
                TXCLog.w(f18312a, "bkgpush: generate bitmap pixel exception " + height + "*" + i2);
            }
        } catch (Error unused5) {
            i2 = 0;
        } catch (Exception unused6) {
            i2 = 0;
        }
    }

    public void b() {
        this.f18317f = false;
        this.f18318g = null;
        this.f18319h = null;
        TXCLog.w(f18312a, "bkgpush: stop background publish");
        e();
    }

    public void a(int i2, int i3) {
        if (this.f18317f) {
            TXCLog.w(f18312a, "bkgpush: start background publish return when started");
            return;
        }
        this.f18317f = true;
        b(i2, i3);
        d();
        a aVar = this.f18315d;
        if (aVar != null) {
            aVar.sendEmptyMessageDelayed(1001, this.f18313b);
        }
        TXCLog.w(f18312a, "bkgpush: start background publish with time:" + ((this.f18314c - System.currentTimeMillis()) / 1000) + ", interval:" + this.f18313b);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0006 A[PHI: r0
      0x0006: PHI (r0v7 int) = (r0v3 int), (r0v4 int) binds: [B:4:0x0004, B:7:0x0009] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r5, int r6) {
        /*
            r4 = this;
            if (r5 <= 0) goto L12
            r0 = 20
            if (r5 < r0) goto L8
        L6:
            r5 = r0
            goto Lc
        L8:
            r0 = 5
            if (r5 > r0) goto Lc
            goto L6
        Lc:
            r0 = 1000(0x3e8, float:1.401E-42)
            int r0 = r0 / r5
            r4.f18313b = r0
            goto L16
        L12:
            r5 = 200(0xc8, float:2.8E-43)
            r4.f18313b = r5
        L16:
            long r0 = (long) r6
            if (r6 <= 0) goto L24
            long r5 = java.lang.System.currentTimeMillis()
            r2 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 * r2
            long r5 = r5 + r0
            r4.f18314c = r5
            goto L35
        L24:
            if (r6 != 0) goto L31
            long r5 = java.lang.System.currentTimeMillis()
            r0 = 300000(0x493e0, double:1.482197E-318)
            long r5 = r5 + r0
            r4.f18314c = r5
            goto L35
        L31:
            r5 = -1
            r4.f18314c = r5
        L35:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.b.b(int, int):void");
    }

    public void a(int i2, int i3, Bitmap bitmap, int i4, int i5) {
        if (this.f18317f) {
            TXCLog.w(f18312a, "bkgpush: start background publish return when started");
            return;
        }
        if (bitmap == null) {
            try {
                TXCLog.w(f18312a, "bkgpush: background publish img is empty, add default img " + i4 + "*" + i5);
                ColorDrawable colorDrawable = new ColorDrawable(-16777216);
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
                colorDrawable.draw(new Canvas(bitmapCreateBitmap));
                bitmap = bitmapCreateBitmap;
            } catch (Error e2) {
                TXCLog.e(f18312a, "save bitmap failed.", e2);
            } catch (Exception e3) {
                TXCLog.e(f18312a, "save bitmap failed.", e3);
            }
        }
        TXCLog.w(f18312a, "bkgpush: generate bitmap " + i4 + "*" + i5);
        this.f18319h = bitmap;
        this.f18320i = i4;
        this.f18321j = i5;
        a(i2, i3);
    }

    public boolean a() {
        return this.f18317f;
    }
}
