package com.github.mikephil.charting.matrix;

/* loaded from: classes3.dex */
public final class Vector3 {

    /* renamed from: x, reason: collision with root package name */
    public float f6561x;

    /* renamed from: y, reason: collision with root package name */
    public float f6562y;

    /* renamed from: z, reason: collision with root package name */
    public float f6563z;
    public static final Vector3 ZERO = new Vector3(0.0f, 0.0f, 0.0f);
    public static final Vector3 UNIT_X = new Vector3(1.0f, 0.0f, 0.0f);
    public static final Vector3 UNIT_Y = new Vector3(0.0f, 1.0f, 0.0f);
    public static final Vector3 UNIT_Z = new Vector3(0.0f, 0.0f, 1.0f);

    public Vector3() {
    }

    public final void add(Vector3 vector3) {
        this.f6561x += vector3.f6561x;
        this.f6562y += vector3.f6562y;
        this.f6563z += vector3.f6563z;
    }

    public final Vector3 cross(Vector3 vector3) {
        float f2 = this.f6562y;
        float f3 = vector3.f6563z;
        float f4 = this.f6563z;
        float f5 = vector3.f6562y;
        float f6 = (f2 * f3) - (f4 * f5);
        float f7 = vector3.f6561x;
        float f8 = this.f6561x;
        return new Vector3(f6, (f4 * f7) - (f3 * f8), (f8 * f5) - (f2 * f7));
    }

    public final float distance2(Vector3 vector3) {
        float f2 = this.f6561x - vector3.f6561x;
        float f3 = this.f6562y - vector3.f6562y;
        float f4 = this.f6563z - vector3.f6563z;
        return (f2 * f2) + (f3 * f3) + (f4 * f4);
    }

    public final void divide(float f2) {
        if (f2 != 0.0f) {
            this.f6561x /= f2;
            this.f6562y /= f2;
            this.f6563z /= f2;
        }
    }

    public final float dot(Vector3 vector3) {
        return (this.f6561x * vector3.f6561x) + (this.f6562y * vector3.f6562y) + (this.f6563z * vector3.f6563z);
    }

    public final float length() {
        return (float) Math.sqrt(length2());
    }

    public final float length2() {
        float f2 = this.f6561x;
        float f3 = this.f6562y;
        float f4 = (f2 * f2) + (f3 * f3);
        float f5 = this.f6563z;
        return f4 + (f5 * f5);
    }

    public final void multiply(float f2) {
        this.f6561x *= f2;
        this.f6562y *= f2;
        this.f6563z *= f2;
    }

    public final float normalize() {
        float length = length();
        if (length != 0.0f) {
            this.f6561x /= length;
            this.f6562y /= length;
            this.f6563z /= length;
        }
        return length;
    }

    public final boolean pointsInSameDirection(Vector3 vector3) {
        return dot(vector3) > 0.0f;
    }

    public final void set(Vector3 vector3) {
        this.f6561x = vector3.f6561x;
        this.f6562y = vector3.f6562y;
        this.f6563z = vector3.f6563z;
    }

    public final void subtract(Vector3 vector3) {
        this.f6561x -= vector3.f6561x;
        this.f6562y -= vector3.f6562y;
        this.f6563z -= vector3.f6563z;
    }

    public final void subtractMultiple(Vector3 vector3, float f2) {
        this.f6561x -= vector3.f6561x * f2;
        this.f6562y -= vector3.f6562y * f2;
        this.f6563z -= vector3.f6563z * f2;
    }

    public final void zero() {
        set(0.0f, 0.0f, 0.0f);
    }

    public Vector3(float[] fArr) {
        set(fArr[0], fArr[1], fArr[2]);
    }

    public Vector3(float f2, float f3, float f4) {
        set(f2, f3, f4);
    }

    public final void add(float f2, float f3, float f4) {
        this.f6561x += f2;
        this.f6562y += f3;
        this.f6563z += f4;
    }

    public final void multiply(Vector3 vector3) {
        this.f6561x *= vector3.f6561x;
        this.f6562y *= vector3.f6562y;
        this.f6563z *= vector3.f6563z;
    }

    public final void set(float f2, float f3, float f4) {
        this.f6561x = f2;
        this.f6562y = f3;
        this.f6563z = f4;
    }

    public Vector3(Vector3 vector3) {
        set(vector3);
    }
}
