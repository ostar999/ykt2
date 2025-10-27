package com.tencent.liteav.renderer;

import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private TextureView f19774a;

    /* renamed from: b, reason: collision with root package name */
    private Handler f19775b;

    /* renamed from: c, reason: collision with root package name */
    private int f19776c;

    /* renamed from: d, reason: collision with root package name */
    private int f19777d;

    /* renamed from: e, reason: collision with root package name */
    private int f19778e = 640;

    /* renamed from: f, reason: collision with root package name */
    private int f19779f = 480;

    /* renamed from: g, reason: collision with root package name */
    private int f19780g = 0;

    /* renamed from: h, reason: collision with root package name */
    private int f19781h = 0;

    /* renamed from: i, reason: collision with root package name */
    private int f19782i = 1;

    /* renamed from: j, reason: collision with root package name */
    private int f19783j = 0;

    /* renamed from: k, reason: collision with root package name */
    private float f19784k = 1.0f;

    /* renamed from: l, reason: collision with root package name */
    private int f19785l = 0;

    public d(TextureView textureView) {
        this.f19776c = 0;
        this.f19777d = 0;
        this.f19774a = textureView;
        this.f19776c = textureView.getWidth();
        this.f19777d = textureView.getHeight();
        this.f19775b = new Handler(textureView.getContext().getMainLooper());
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0033 A[PHI: r8
      0x0033: PHI (r8v11 float) = (r8v7 float), (r8v17 float) binds: [B:35:0x005a, B:18:0x0031] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0035 A[PHI: r2
      0x0035: PHI (r2v7 float) = (r2v3 float), (r2v6 float), (r2v10 float) binds: [B:38:0x0069, B:35:0x005a, B:18:0x0031] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(int r8) {
        /*
            r7 = this;
            r7.f19782i = r8
            android.view.TextureView r0 = r7.f19774a
            if (r0 == 0) goto L84
            r1 = 1
            r2 = 90
            r3 = 270(0x10e, float:3.78E-43)
            r4 = 180(0xb4, float:2.52E-43)
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r8 != r1) goto L38
            int r8 = r7.f19783j
            if (r8 == 0) goto L6e
            if (r8 != r4) goto L18
            goto L6e
        L18:
            if (r8 == r3) goto L1c
            if (r8 != r2) goto L6e
        L1c:
            int r8 = r7.f19780g
            if (r8 == 0) goto L37
            int r1 = r7.f19781h
            if (r1 != 0) goto L25
            goto L37
        L25:
            int r2 = r7.f19777d
            float r2 = (float) r2
            float r8 = (float) r8
            float r2 = r2 / r8
            int r8 = r7.f19776c
            float r8 = (float) r8
            float r1 = (float) r1
            float r8 = r8 / r1
            int r1 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r1 <= 0) goto L35
        L33:
            r5 = r8
            goto L6e
        L35:
            r5 = r2
            goto L6e
        L37:
            return
        L38:
            if (r8 != 0) goto L6e
            int r8 = r7.f19780g
            if (r8 == 0) goto L6d
            int r1 = r7.f19781h
            if (r1 != 0) goto L43
            goto L6d
        L43:
            int r6 = r7.f19783j
            if (r6 == 0) goto L5d
            if (r6 != r4) goto L4a
            goto L5d
        L4a:
            if (r6 == r3) goto L4e
            if (r6 != r2) goto L6e
        L4e:
            int r2 = r7.f19777d
            float r2 = (float) r2
            float r8 = (float) r8
            float r2 = r2 / r8
            int r8 = r7.f19776c
            float r8 = (float) r8
            float r1 = (float) r1
            float r8 = r8 / r1
            int r1 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r1 >= 0) goto L35
            goto L33
        L5d:
            int r2 = r7.f19777d
            float r2 = (float) r2
            float r1 = (float) r1
            float r2 = r2 / r1
            int r1 = r7.f19776c
            float r1 = (float) r1
            float r8 = (float) r8
            float r1 = r1 / r8
            int r8 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r8 >= 0) goto L35
            r5 = r1
            goto L6e
        L6d:
            return
        L6e:
            float r8 = r7.f19784k
            r1 = 0
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 >= 0) goto L76
            float r5 = -r5
        L76:
            r0.setScaleX(r5)
            android.view.TextureView r8 = r7.f19774a
            float r0 = java.lang.Math.abs(r5)
            r8.setScaleY(r0)
            r7.f19784k = r5
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.d.b(int):void");
    }

    public void c(final int i2) {
        try {
            this.f19775b.post(new Runnable() { // from class: com.tencent.liteav.renderer.d.2
                @Override // java.lang.Runnable
                public void run() {
                    d.this.d(i2);
                }
            });
        } catch (Exception e2) {
            TXCLog.e("TXCTextureViewWrapper", "set render rotation failed.", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003f A[PHI: r0
      0x003f: PHI (r0v6 float) = (r0v5 float), (r0v13 float), (r0v13 float) binds: [B:39:0x006d, B:26:0x0047, B:21:0x003d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d(int r5) {
        /*
            r4 = this;
            int r5 = r5 % 360
            r4.f19783j = r5
            android.view.TextureView r0 = r4.f19774a
            if (r0 == 0) goto L8b
            r1 = 1
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r5 == 0) goto L4b
            r3 = 180(0xb4, float:2.52E-43)
            if (r5 != r3) goto L12
            goto L4b
        L12:
            r3 = 270(0x10e, float:3.78E-43)
            if (r5 == r3) goto L1a
            r3 = 90
            if (r5 != r3) goto L73
        L1a:
            int r3 = r4.f19780g
            if (r3 == 0) goto L4a
            int r3 = r4.f19781h
            if (r3 != 0) goto L23
            goto L4a
        L23:
            int r5 = 360 - r5
            float r5 = (float) r5
            r0.setRotation(r5)
            int r5 = r4.f19777d
            float r5 = (float) r5
            int r0 = r4.f19780g
            float r0 = (float) r0
            float r5 = r5 / r0
            int r0 = r4.f19776c
            float r0 = (float) r0
            int r3 = r4.f19781h
            float r3 = (float) r3
            float r0 = r0 / r3
            int r3 = r4.f19782i
            if (r3 != r1) goto L43
            int r1 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r1 <= 0) goto L41
        L3f:
            r2 = r0
            goto L73
        L41:
            r2 = r5
            goto L73
        L43:
            if (r3 != 0) goto L73
            int r1 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r1 >= 0) goto L41
            goto L3f
        L4a:
            return
        L4b:
            int r5 = 360 - r5
            float r5 = (float) r5
            r0.setRotation(r5)
            int r5 = r4.f19782i
            if (r5 != r1) goto L56
            goto L73
        L56:
            if (r5 != 0) goto L73
            int r5 = r4.f19780g
            if (r5 == 0) goto L72
            int r0 = r4.f19781h
            if (r0 != 0) goto L61
            goto L72
        L61:
            int r1 = r4.f19777d
            float r1 = (float) r1
            float r0 = (float) r0
            float r1 = r1 / r0
            int r0 = r4.f19776c
            float r0 = (float) r0
            float r5 = (float) r5
            float r0 = r0 / r5
            int r5 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r5 >= 0) goto L70
            goto L3f
        L70:
            r2 = r1
            goto L73
        L72:
            return
        L73:
            float r5 = r4.f19784k
            r0 = 0
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 >= 0) goto L7b
            float r2 = -r2
        L7b:
            android.view.TextureView r5 = r4.f19774a
            r5.setScaleX(r2)
            android.view.TextureView r5 = r4.f19774a
            float r0 = java.lang.Math.abs(r2)
            r5.setScaleY(r0)
            r4.f19784k = r2
        L8b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.d.d(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2, int i3) {
        int i4;
        int i5;
        if (this.f19774a == null || i2 == 0 || i3 == 0 || (i4 = this.f19776c) == 0 || (i5 = this.f19777d) == 0) {
            return;
        }
        double d3 = i3 / i2;
        if (i5 > ((int) (i4 * d3))) {
            this.f19780g = i4;
            this.f19781h = (int) (i4 * d3);
        } else {
            this.f19780g = (int) (i5 / d3);
            this.f19781h = i5;
        }
        int i6 = this.f19780g;
        float f2 = (i4 - i6) / 2.0f;
        float f3 = (i5 - r3) / 2.0f;
        float f4 = i6 / i4;
        float f5 = this.f19781h / i5;
        Matrix matrix = new Matrix();
        this.f19774a.getTransform(matrix);
        matrix.setScale(f4, f5);
        matrix.postTranslate(f2, f3);
        this.f19774a.setTransform(matrix);
        this.f19774a.requestLayout();
    }

    public void a(final int i2) {
        try {
            this.f19775b.post(new Runnable() { // from class: com.tencent.liteav.renderer.d.1
                @Override // java.lang.Runnable
                public void run() {
                    d.this.b(i2);
                }
            });
        } catch (Exception e2) {
            TXCLog.e("TXCTextureViewWrapper", "set render mode failed", e2);
        }
    }

    private void a() {
        try {
            a(new Runnable() { // from class: com.tencent.liteav.renderer.d.3
                @Override // java.lang.Runnable
                public void run() {
                    d dVar = d.this;
                    dVar.c(dVar.f19778e, d.this.f19779f);
                    d dVar2 = d.this;
                    dVar2.b(dVar2.f19782i);
                    d dVar3 = d.this;
                    dVar3.d(dVar3.f19783j);
                }
            });
        } catch (Exception e2) {
            TXCLog.e("TXCTextureViewWrapper", "adjust video size failed.", e2);
        }
    }

    public void a(final boolean z2) {
        try {
            this.f19775b.post(new Runnable() { // from class: com.tencent.liteav.renderer.d.4
                @Override // java.lang.Runnable
                public void run() {
                    if (d.this.f19774a != null) {
                        d dVar = d.this;
                        dVar.f19784k = Math.abs(dVar.f19784k);
                        if (z2) {
                            d dVar2 = d.this;
                            dVar2.f19784k = -dVar2.f19784k;
                        }
                        d.this.f19774a.setScaleX(d.this.f19784k);
                        d.this.f19774a.setScaleY(Math.abs(d.this.f19784k));
                    }
                }
            });
        } catch (Exception e2) {
            TXCLog.e("TXCTextureViewWrapper", "set mirror failed.", e2);
        }
    }

    public void a(int i2, int i3) {
        TXCLog.w("TXCTextureViewWrapper", "vrender: set view size:" + i2 + "," + i3);
        this.f19776c = i2;
        this.f19777d = i3;
        a();
    }

    private void a(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            this.f19775b.post(runnable);
        }
    }

    public void b(int i2, int i3) {
        TXCLog.w("TXCTextureViewWrapper", "vrender: set video size:" + i2 + "," + i3);
        this.f19778e = i2;
        this.f19779f = i3;
        a();
    }
}
