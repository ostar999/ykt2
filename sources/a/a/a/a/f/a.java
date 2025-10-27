package a.a.a.a.f;

import java.nio.FloatBuffer;

/* loaded from: classes.dex */
public class a {

    /* renamed from: d, reason: collision with root package name */
    public static final int f1142d = 4;

    /* renamed from: e, reason: collision with root package name */
    public static final int f1143e = 2;

    /* renamed from: f, reason: collision with root package name */
    public static final int f1144f = 8;

    /* renamed from: g, reason: collision with root package name */
    public static final int f1145g = 8;

    /* renamed from: a, reason: collision with root package name */
    private FloatBuffer f1146a;

    /* renamed from: b, reason: collision with root package name */
    private FloatBuffer f1147b;

    /* renamed from: c, reason: collision with root package name */
    private int f1148c;

    public a() {
    }

    public void a(float[] fArr) {
        this.f1146a = e.a(fArr);
    }

    public void b(float[] fArr) {
        this.f1147b = e.a(fArr);
        this.f1148c = fArr.length / 2;
    }

    public int c() {
        return this.f1148c;
    }

    public a(float[] fArr, float[] fArr2) {
        b(fArr);
        a(fArr2);
    }

    public FloatBuffer a() {
        return this.f1146a;
    }

    public FloatBuffer b() {
        return this.f1147b;
    }
}
