package com.google.zxing;

/* loaded from: classes4.dex */
public final class Dimension {
    private final int height;
    private final int width;

    public Dimension(int i2, int i3) {
        if (i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException();
        }
        this.width = i2;
        this.height = i3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Dimension)) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        return this.width == dimension.width && this.height == dimension.height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (this.width * 32713) + this.height;
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}
