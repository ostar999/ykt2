package a.a.a.a;

import a.a.a.a.f.g;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.util.Arrays;

/* loaded from: classes.dex */
public class c extends g {

    /* renamed from: n, reason: collision with root package name */
    private static final String f1117n = "uniform mat4 uMVPMatrix;attribute vec4 vPosition;uniform float uPointSize;void main() {  gl_Position = uMVPMatrix * vPosition;  gl_PointSize = uPointSize;}";

    /* renamed from: o, reason: collision with root package name */
    private static final String f1118o = "precision mediump float;uniform vec4 vColor;void main() {  gl_FragColor = vColor;}";

    /* renamed from: p, reason: collision with root package name */
    private static final float[] f1119p = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    /* renamed from: d, reason: collision with root package name */
    private int f1120d;

    /* renamed from: e, reason: collision with root package name */
    private int f1121e;

    /* renamed from: f, reason: collision with root package name */
    private int f1122f;

    /* renamed from: g, reason: collision with root package name */
    private int f1123g;

    /* renamed from: h, reason: collision with root package name */
    private float f1124h;

    /* renamed from: i, reason: collision with root package name */
    private final float[] f1125i;

    /* renamed from: j, reason: collision with root package name */
    private int f1126j;

    /* renamed from: k, reason: collision with root package name */
    private int f1127k;

    /* renamed from: l, reason: collision with root package name */
    private int f1128l;

    /* renamed from: m, reason: collision with root package name */
    private int f1129m;

    public c() {
        super(f1117n, f1118o);
        this.f1124h = 6.0f;
        this.f1125i = new float[16];
    }

    @Override // a.a.a.a.f.g
    public a.a.a.a.f.a a() {
        return new b();
    }

    @Override // a.a.a.a.f.g
    public void b() {
        this.f1120d = GLES20.glGetAttribLocation(this.f1166a, "vPosition");
        a.a.a.a.f.e.a("vPosition");
        this.f1121e = GLES20.glGetUniformLocation(this.f1166a, "vColor");
        a.a.a.a.f.e.a("vColor");
        this.f1122f = GLES20.glGetUniformLocation(this.f1166a, "uMVPMatrix");
        a.a.a.a.f.e.a("glGetUniformLocation");
        this.f1123g = GLES20.glGetUniformLocation(this.f1166a, "uPointSize");
        a.a.a.a.f.e.a("uPointSize");
    }

    @Override // a.a.a.a.f.g
    public void a(int i2, float[] fArr, float[] fArr2) {
        GLES20.glUseProgram(this.f1166a);
        GLES20.glEnableVertexAttribArray(this.f1120d);
        GLES20.glVertexAttribPointer(this.f1120d, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 8, (Buffer) this.f1167b.b());
        GLES20.glUniform4fv(this.f1121e, 1, f1119p, 0);
        GLES20.glUniformMatrix4fv(this.f1122f, 1, false, this.f1125i, 0);
        GLES20.glUniform1f(this.f1123g, this.f1124h);
        GLES20.glDrawArrays(0, 0, this.f1167b.c());
        GLES20.glDisableVertexAttribArray(this.f1120d);
    }

    public void a(int i2, int i3, int i4, int i5) {
        a(0, null, null, i2, i3, i4, i5);
    }

    public void a(float[] fArr, int i2, int i3, int i4, int i5) {
        if (this.f1128l != i2 || this.f1129m != i3 || this.f1127k != i4 || this.f1126j != i5) {
            float[] fArr2 = new float[16];
            float[] fArr3 = new float[16];
            Matrix.orthoM(fArr2, 0, 0.0f, i2, 0.0f, i3, -1.0f, 1.0f);
            Matrix.setRotateM(fArr3, 0, 360 - i4, 0.0f, 0.0f, 1.0f);
            if (i5 == 0) {
                Matrix.rotateM(fArr3, 0, 180.0f, 1.0f, 0.0f, 0.0f);
            }
            Matrix.multiplyMM(this.f1125i, 0, fArr3, 0, fArr2, 0);
            this.f1128l = i2;
            this.f1129m = i3;
            this.f1127k = i4;
            this.f1126j = i5;
        }
        b(Arrays.copyOf(fArr, fArr.length));
    }
}
