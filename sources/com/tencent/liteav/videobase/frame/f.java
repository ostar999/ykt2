package com.tencent.liteav.videobase.frame;

import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.a.a;
import com.tencent.liteav.videobase.frame.c;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private final int f20095a;

    /* renamed from: b, reason: collision with root package name */
    private final int f20096b;

    /* renamed from: c, reason: collision with root package name */
    private final FloatBuffer f20097c;

    /* renamed from: d, reason: collision with root package name */
    private final FloatBuffer f20098d;

    /* renamed from: e, reason: collision with root package name */
    private final FloatBuffer f20099e;

    /* renamed from: f, reason: collision with root package name */
    private final FloatBuffer f20100f;

    /* renamed from: i, reason: collision with root package name */
    private a.EnumC0340a f20103i;

    /* renamed from: m, reason: collision with root package name */
    private c f20107m;

    /* renamed from: g, reason: collision with root package name */
    private final com.tencent.liteav.videobase.b.a[] f20101g = new com.tencent.liteav.videobase.b.a[a.c.values().length];

    /* renamed from: h, reason: collision with root package name */
    private final float[] f20102h = new float[16];

    /* renamed from: j, reason: collision with root package name */
    private PixelFrame f20104j = null;

    /* renamed from: k, reason: collision with root package name */
    private com.tencent.liteav.videobase.d.a f20105k = null;

    /* renamed from: l, reason: collision with root package name */
    private com.tencent.liteav.videobase.b.a f20106l = null;

    public f(int i2, int i3) {
        this.f20095a = i2;
        this.f20096b = i3;
        float[] fArr = com.tencent.liteav.videobase.a.a.f19933c;
        this.f20097c = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr);
        com.tencent.liteav.videobase.utils.d dVar = com.tencent.liteav.videobase.utils.d.NORMAL;
        this.f20098d = OpenGlUtils.createTextureCoordsBuffer(dVar, false, false);
        this.f20099e = OpenGlUtils.createNormalCubeVerticesBuffer();
        this.f20100f = OpenGlUtils.createTextureCoordsBuffer(dVar, false, false);
    }

    private float a(float f2, float f3) {
        return f2 == 0.0f ? f3 : 1.0f - f3;
    }

    private void b() {
        boolean z2 = this.f20104j.getRotation() == com.tencent.liteav.videobase.utils.d.ROTATION_90 || this.f20104j.getRotation() == com.tencent.liteav.videobase.utils.d.ROTATION_270;
        float fMax = Math.max((this.f20095a * 1.0f) / this.f20104j.getWidth(), (this.f20096b * 1.0f) / this.f20104j.getHeight());
        float fRound = (Math.round(r2 * fMax) * 1.0f) / this.f20095a;
        float fRound2 = (Math.round(r5 * fMax) * 1.0f) / this.f20096b;
        float[] fArr = com.tencent.liteav.videobase.a.a.f19933c;
        float[] fArr2 = new float[8];
        OpenGlUtils.initTextureCoordsBuffer(fArr2, this.f20104j.getRotation(), this.f20104j.isMirrorHorizontal(), this.f20104j.isMirrorVertical());
        a.EnumC0340a enumC0340a = this.f20103i;
        if (enumC0340a == a.EnumC0340a.CENTER_CROP) {
            float f2 = (1.0f - (z2 ? 1.0f / fRound2 : 1.0f / fRound)) / 2.0f;
            float f3 = (1.0f - (z2 ? 1.0f / fRound : 1.0f / fRound2)) / 2.0f;
            fArr2[0] = a(fArr2[0], f2);
            fArr2[1] = a(fArr2[1], f3);
            fArr2[2] = a(fArr2[2], f2);
            fArr2[3] = a(fArr2[3], f3);
            fArr2[4] = a(fArr2[4], f2);
            fArr2[5] = a(fArr2[5], f3);
            fArr2[6] = a(fArr2[6], f2);
            fArr2[7] = a(fArr2[7], f3);
        } else if (enumC0340a == a.EnumC0340a.FIT_CENTER) {
            fArr = new float[]{fArr[0] / fRound2, fArr[1] / fRound, fArr[2] / fRound2, fArr[3] / fRound, fArr[4] / fRound2, fArr[5] / fRound, fArr[6] / fRound2, fArr[7] / fRound};
        }
        this.f20097c.clear();
        this.f20097c.put(fArr).position(0);
        this.f20098d.clear();
        this.f20098d.put(fArr2).position(0);
    }

    private void c() {
        if (this.f20106l != null) {
            return;
        }
        com.tencent.liteav.videobase.b.a aVar = new com.tencent.liteav.videobase.b.a();
        this.f20106l = aVar;
        aVar.a((c) null);
        this.f20106l.a(this.f20095a, this.f20096b);
    }

    private void d() {
        com.tencent.liteav.videobase.d.a aVar = this.f20105k;
        if (aVar != null) {
            aVar.b();
            this.f20105k = null;
        }
        com.tencent.liteav.videobase.b.a aVar2 = this.f20106l;
        if (aVar2 != null) {
            aVar2.b();
            this.f20106l = null;
        }
        int i2 = 0;
        while (true) {
            com.tencent.liteav.videobase.b.a[] aVarArr = this.f20101g;
            if (i2 >= aVarArr.length) {
                break;
            }
            com.tencent.liteav.videobase.b.a aVar3 = aVarArr[i2];
            if (aVar3 != null) {
                aVar3.b();
                this.f20101g[i2] = null;
            }
            i2++;
        }
        c cVar = this.f20107m;
        if (cVar != null) {
            cVar.a();
        }
        TXCLog.i("PixelFrameRenderer", "uninitialize GL components");
    }

    public void a(PixelFrame pixelFrame, a.EnumC0340a enumC0340a, c.a aVar) {
        if (this.f20107m == null) {
            this.f20107m = new c();
        }
        if (this.f20104j == null || a(pixelFrame, enumC0340a)) {
            this.f20103i = enumC0340a;
            this.f20104j = new PixelFrame(pixelFrame);
            d();
            b();
        }
        if (this.f20104j.getPixelBufferType() == a.b.BYTE_BUFFER) {
            if (this.f20104j.getPixelFormatType() != a.c.RGBA) {
                a(this.f20104j.getPixelFormatType(), aVar, pixelFrame.getBuffer());
                return;
            } else {
                a(aVar, pixelFrame.getBuffer());
                return;
            }
        }
        if (this.f20104j.getPixelBufferType() == a.b.BYTE_ARRAY) {
            if (this.f20104j.getPixelFormatType() != a.c.RGBA) {
                a(this.f20104j.getPixelFormatType(), aVar, ByteBuffer.wrap(pixelFrame.getData()));
                return;
            } else {
                a(aVar, ByteBuffer.wrap(pixelFrame.getData()));
                return;
            }
        }
        if (this.f20104j.getPixelBufferType() == a.b.TEXTURE_OES) {
            a(aVar, pixelFrame.getTextureId(), pixelFrame.getMatrix());
        } else if (this.f20104j.getPixelBufferType() == a.b.TEXTURE_2D) {
            a(aVar, pixelFrame.getTextureId());
        }
    }

    public void a() {
        this.f20104j = null;
        d();
        c cVar = this.f20107m;
        if (cVar != null) {
            cVar.a();
            this.f20107m.b();
            this.f20107m = null;
        }
    }

    private boolean a(PixelFrame pixelFrame, a.EnumC0340a enumC0340a) {
        return (enumC0340a == this.f20103i && pixelFrame.getWidth() == this.f20104j.getWidth() && pixelFrame.getHeight() == this.f20104j.getHeight() && pixelFrame.getPixelBufferType() == this.f20104j.getPixelBufferType() && pixelFrame.getPixelFormatType() == this.f20104j.getPixelFormatType() && pixelFrame.isMirrorHorizontal() == this.f20104j.isMirrorHorizontal() && pixelFrame.isMirrorVertical() == this.f20104j.isMirrorVertical() && pixelFrame.getRotation() == this.f20104j.getRotation()) ? false : true;
    }

    private void a(c.a aVar, Buffer buffer) {
        int iOrdinal = a.c.RGBA.ordinal();
        com.tencent.liteav.videobase.b.a[] aVarArr = this.f20101g;
        if (aVarArr[iOrdinal] == null) {
            aVarArr[iOrdinal] = new com.tencent.liteav.videobase.d.b();
            this.f20101g[iOrdinal].a((c) null);
            this.f20101g[iOrdinal].a(this.f20095a, this.f20096b);
        }
        com.tencent.liteav.videobase.d.b bVar = (com.tencent.liteav.videobase.d.b) this.f20101g[iOrdinal];
        OpenGlUtils.glViewport(0, 0, this.f20095a, this.f20096b);
        if (this.f20104j.getRotation() != com.tencent.liteav.videobase.utils.d.ROTATION_90 && this.f20104j.getRotation() != com.tencent.liteav.videobase.utils.d.ROTATION_270) {
            bVar.a(buffer, this.f20104j.getWidth(), this.f20104j.getHeight());
        } else {
            bVar.a(buffer, this.f20104j.getHeight(), this.f20104j.getWidth());
        }
        bVar.a(-1, aVar, this.f20097c, this.f20098d);
    }

    private void a(a.c cVar, c.a aVar, ByteBuffer byteBuffer) {
        int iOrdinal = cVar.ordinal();
        com.tencent.liteav.videobase.b.a[] aVarArr = this.f20101g;
        if (aVarArr[iOrdinal] == null) {
            if (cVar == a.c.I420) {
                aVarArr[iOrdinal] = new com.tencent.liteav.videobase.g.a();
            } else if (cVar == a.c.NV21) {
                aVarArr[iOrdinal] = new com.tencent.liteav.videobase.g.d();
            } else {
                aVarArr[iOrdinal] = new com.tencent.liteav.videobase.g.c();
            }
            this.f20101g[iOrdinal].a((c) null);
            this.f20101g[iOrdinal].a(this.f20095a, this.f20096b);
        }
        com.tencent.liteav.videobase.g.e eVar = (com.tencent.liteav.videobase.g.e) this.f20101g[iOrdinal];
        OpenGlUtils.glViewport(0, 0, this.f20095a, this.f20096b);
        if (this.f20104j.getRotation() != com.tencent.liteav.videobase.utils.d.ROTATION_90 && this.f20104j.getRotation() != com.tencent.liteav.videobase.utils.d.ROTATION_270) {
            eVar.a(byteBuffer, this.f20104j.getWidth(), this.f20104j.getHeight());
        } else {
            eVar.a(byteBuffer, this.f20104j.getHeight(), this.f20104j.getWidth());
        }
        eVar.a(-1, aVar, this.f20097c, this.f20098d);
    }

    private void a(c.a aVar, int i2, float[] fArr) {
        if (this.f20105k == null) {
            com.tencent.liteav.videobase.d.a aVar2 = new com.tencent.liteav.videobase.d.a();
            this.f20105k = aVar2;
            aVar2.a((c) null);
            this.f20105k.a(this.f20095a, this.f20096b);
        }
        c();
        c cVar = this.f20107m;
        if (cVar == null) {
            return;
        }
        c.a aVarA = cVar.a(this.f20104j.getWidth(), this.f20104j.getHeight());
        OpenGlUtils.glViewport(0, 0, this.f20104j.getWidth(), this.f20104j.getHeight());
        Matrix.multiplyMM(this.f20102h, 0, fArr, 0, com.tencent.liteav.videobase.a.a.f19931a, 0);
        this.f20105k.a(this.f20102h);
        this.f20105k.a(i2, aVarA, this.f20099e, this.f20100f);
        OpenGlUtils.glViewport(0, 0, this.f20095a, this.f20096b);
        this.f20106l.a(aVarA.e(), aVar, this.f20097c, this.f20098d);
        this.f20107m.a(aVarA);
    }

    private void a(c.a aVar, int i2) {
        c();
        OpenGlUtils.glViewport(0, 0, this.f20095a, this.f20096b);
        this.f20106l.a(i2, aVar, this.f20097c, this.f20098d);
    }
}
