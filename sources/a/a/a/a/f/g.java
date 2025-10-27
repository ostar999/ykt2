package a.a.a.a.f;

import android.content.Context;
import android.opengl.GLES20;
import com.yikaobang.yixue.R2;

/* loaded from: classes.dex */
public abstract class g {

    /* renamed from: c, reason: collision with root package name */
    private static final String f1165c = "mqi";

    /* renamed from: a, reason: collision with root package name */
    protected int f1166a;

    /* renamed from: b, reason: collision with root package name */
    protected a f1167b;

    public g(String str, String str2) {
        this.f1166a = e.a(str, str2);
        this.f1167b = a();
        b();
    }

    public abstract a a();

    public abstract void a(int i2, float[] fArr, float[] fArr2);

    public void a(float[] fArr) {
        this.f1167b.a(fArr);
    }

    public abstract void b();

    public void b(float[] fArr) {
        this.f1167b.b(fArr);
    }

    public void c() {
        GLES20.glDeleteProgram(this.f1166a);
        this.f1166a = -1;
    }

    public void a(int i2, float[] fArr) {
        a(i2, fArr, e.f1163b);
    }

    public void a(int i2, float[] fArr, float[] fArr2, int i3, int i4, int i5, int i6) {
        int[] iArr = new int[4];
        GLES20.glGetIntegerv(R2.attr.reactiveGuide_applyToAllConstraintSets, iArr, 0);
        GLES20.glViewport(i3, i4, i5, i6);
        a(i2, fArr, fArr2);
        GLES20.glViewport(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public g(Context context, int i2, int i3) {
        this(d.a(context, i2), d.a(context, i3));
    }
}
