package com.tencent.liteav.beauty.b;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.d;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

/* loaded from: classes6.dex */
public class x extends com.tencent.liteav.basic.opengl.j {
    private String B;

    /* renamed from: r, reason: collision with root package name */
    protected a[] f19002r;

    /* renamed from: s, reason: collision with root package name */
    protected List<d.f> f19003s;

    /* renamed from: t, reason: collision with root package name */
    protected boolean f19004t;

    /* renamed from: u, reason: collision with root package name */
    protected int f19005u;

    /* renamed from: w, reason: collision with root package name */
    protected ShortBuffer f19006w;

    /* renamed from: x, reason: collision with root package name */
    private a f19007x;

    /* renamed from: y, reason: collision with root package name */
    private int f19008y;

    /* renamed from: v, reason: collision with root package name */
    protected static final short[] f19000v = {1, 2, 0, 2, 0, 3};

    /* renamed from: z, reason: collision with root package name */
    private static final float[] f19001z = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private static final float[] A = {0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};

    public class a {

        /* renamed from: c, reason: collision with root package name */
        public Bitmap f19011c;

        /* renamed from: a, reason: collision with root package name */
        public FloatBuffer f19009a = null;

        /* renamed from: b, reason: collision with root package name */
        public FloatBuffer f19010b = null;

        /* renamed from: d, reason: collision with root package name */
        public int[] f19012d = null;

        public a() {
        }
    }

    public x(String str, String str2) {
        super(str, str2);
        this.f19002r = null;
        this.f19007x = null;
        this.f19003s = null;
        this.f19004t = false;
        this.f19005u = 1;
        this.f19008y = 1;
        this.f19006w = null;
        this.B = "GPUWatermark";
        short[] sArr = f19000v;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(sArr.length * 2);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        ShortBuffer shortBufferAsShortBuffer = byteBufferAllocateDirect.asShortBuffer();
        this.f19006w = shortBufferAsShortBuffer;
        shortBufferAsShortBuffer.put(sArr);
        this.f19006w.position(0);
        this.f18606o = true;
    }

    private void r() {
        if (this.f19002r != null) {
            int i2 = 0;
            while (true) {
                a[] aVarArr = this.f19002r;
                if (i2 >= aVarArr.length) {
                    break;
                }
                a aVar = aVarArr[i2];
                if (aVar != null) {
                    int[] iArr = aVar.f19012d;
                    if (iArr != null) {
                        GLES20.glDeleteTextures(1, iArr, 0);
                    }
                    a[] aVarArr2 = this.f19002r;
                    a aVar2 = aVarArr2[i2];
                    aVar2.f19012d = null;
                    aVar2.f19011c = null;
                    aVarArr2[i2] = null;
                }
                i2++;
            }
        }
        this.f19002r = null;
    }

    public void a(Bitmap bitmap, float f2, float f3, float f4, int i2) {
        if (bitmap == null) {
            a[] aVarArr = this.f19002r;
            if (aVarArr == null || aVarArr[i2] == null) {
                return;
            }
            Log.i(this.B, "release " + i2 + " water mark!");
            int[] iArr = this.f19002r[i2].f19012d;
            if (iArr != null) {
                GLES20.glDeleteTextures(1, iArr, 0);
            }
            a[] aVarArr2 = this.f19002r;
            a aVar = aVarArr2[i2];
            aVar.f19012d = null;
            aVar.f19011c = null;
            aVarArr2[i2] = null;
            return;
        }
        a[] aVarArr3 = this.f19002r;
        if (aVarArr3[i2] == null || i2 >= aVarArr3.length) {
            Log.e(this.B, "index is too large for mSzWaterMark!");
            return;
        }
        a(bitmap.getWidth(), bitmap.getHeight(), f2, f3, f4, i2);
        a aVar2 = this.f19002r[i2];
        if (aVar2.f19012d == null) {
            int[] iArr2 = new int[1];
            aVar2.f19012d = iArr2;
            GLES20.glGenTextures(1, iArr2, 0);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f19002r[i2].f19012d[0]);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
        }
        Bitmap bitmap2 = this.f19002r[i2].f19011c;
        if (bitmap2 == null || !bitmap2.equals(bitmap)) {
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f19002r[i2].f19012d[0]);
            if (bitmap.isRecycled()) {
                TXCLog.e(this.B, "SetWaterMark when bitmap is recycled");
            } else {
                GLUtils.texImage2D(R2.attr.tab_indicator_height, 0, bitmap, 0);
            }
        }
        this.f19002r[i2].f19011c = bitmap;
    }

    public void c(boolean z2) {
        this.f19004t = z2;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void d() {
        super.d();
        this.f19004t = false;
        r();
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void j() {
        super.j();
        if (!this.f19004t) {
            return;
        }
        GLES20.glEnable(R2.attr.roundWidth);
        GLES20.glBlendFunc(this.f19005u, R2.attr.bl_unSelected_textColor);
        GLES20.glActiveTexture(33984);
        int i2 = 0;
        while (true) {
            a[] aVarArr = this.f19002r;
            if (i2 >= aVarArr.length) {
                GLES20.glDisable(R2.attr.roundWidth);
                return;
            }
            a aVar = aVarArr[i2];
            if (aVar != null) {
                GLES20.glBindTexture(R2.attr.tab_indicator_height, aVar.f19012d[0]);
                GLES20.glUniform1i(this.f18594c, 0);
                GLES20.glVertexAttribPointer(this.f18593b, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 8, (Buffer) this.f19002r[i2].f19009a);
                GLES20.glEnableVertexAttribArray(this.f18593b);
                GLES20.glVertexAttribPointer(this.f18595d, 4, R2.color.m3_ref_palette_dynamic_tertiary60, false, 16, (Buffer) this.f19002r[i2].f19010b);
                GLES20.glEnableVertexAttribArray(this.f18595d);
                GLES20.glDrawElements(4, f19000v.length, R2.color.m3_ref_palette_dynamic_tertiary30, this.f19006w);
                GLES20.glDisableVertexAttribArray(this.f18593b);
                GLES20.glDisableVertexAttribArray(this.f18595d);
            }
            i2++;
        }
    }

    public x() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public void a(int i2, int i3, float f2, float f3, float f4, int i4) {
        float[] fArr = f19001z;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        this.f19002r[i4].f19009a = byteBufferAllocateDirect.asFloatBuffer();
        float[] fArr2 = new float[fArr.length];
        float f5 = (f2 * 2.0f) - 1.0f;
        fArr2[0] = f5;
        float f6 = 1.0f - (f3 * 2.0f);
        fArr2[1] = f6;
        fArr2[2] = f5;
        float f7 = f6 - (((((i3 / i2) * f4) * this.f18596e) / this.f18597f) * 2.0f);
        fArr2[3] = f7;
        float f8 = f5 + (f4 * 2.0f);
        fArr2[4] = f8;
        fArr2[5] = f7;
        fArr2[6] = f8;
        fArr2[7] = f6;
        for (int i5 = 1; i5 <= 7; i5 += 2) {
            fArr2[i5] = fArr2[i5] * (-1.0f);
        }
        this.f19002r[i4].f19009a.put(fArr2);
        this.f19002r[i4].f19009a.position(0);
        float[] fArr3 = A;
        ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(fArr3.length * 4);
        byteBufferAllocateDirect2.order(ByteOrder.nativeOrder());
        this.f19002r[i4].f19010b = byteBufferAllocateDirect2.asFloatBuffer();
        this.f19002r[i4].f19010b.put(fArr3);
        this.f19002r[i4].f19010b.position(0);
    }

    public void a(Bitmap bitmap, float f2, float f3, float f4) {
        if (this.f19002r == null) {
            this.f19002r = new a[1];
        }
        a[] aVarArr = this.f19002r;
        if (aVarArr[0] == null) {
            aVarArr[0] = new a();
        }
        a(bitmap, f2, f3, f4, 0);
        this.f19007x = this.f19002r[0];
    }
}
