package androidx.constraintlayout.core.widgets;

/* loaded from: classes.dex */
public class Rectangle {
    public int height;
    public int width;

    /* renamed from: x, reason: collision with root package name */
    public int f1780x;

    /* renamed from: y, reason: collision with root package name */
    public int f1781y;

    public boolean contains(int i2, int i3) {
        int i4;
        int i5 = this.f1780x;
        return i2 >= i5 && i2 < i5 + this.width && i3 >= (i4 = this.f1781y) && i3 < i4 + this.height;
    }

    public int getCenterX() {
        return (this.f1780x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.f1781y + this.height) / 2;
    }

    public void grow(int i2, int i3) {
        this.f1780x -= i2;
        this.f1781y -= i3;
        this.width += i2 * 2;
        this.height += i3 * 2;
    }

    public boolean intersects(Rectangle rectangle) {
        int i2;
        int i3;
        int i4 = this.f1780x;
        int i5 = rectangle.f1780x;
        return i4 >= i5 && i4 < i5 + rectangle.width && (i2 = this.f1781y) >= (i3 = rectangle.f1781y) && i2 < i3 + rectangle.height;
    }

    public void setBounds(int i2, int i3, int i4, int i5) {
        this.f1780x = i2;
        this.f1781y = i3;
        this.width = i4;
        this.height = i5;
    }
}
