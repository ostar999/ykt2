package com.google.android.gms.common.images;

/* loaded from: classes3.dex */
public final class Size {
    private final int zanj;
    private final int zank;

    public Size(int i2, int i3) {
        this.zanj = i2;
        this.zank = i3;
    }

    public static Size parseSize(String str) throws NumberFormatException {
        if (str == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int iIndexOf = str.indexOf(42);
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf(120);
        }
        if (iIndexOf < 0) {
            throw zah(str);
        }
        try {
            return new Size(Integer.parseInt(str.substring(0, iIndexOf)), Integer.parseInt(str.substring(iIndexOf + 1)));
        } catch (NumberFormatException unused) {
            throw zah(str);
        }
    }

    private static NumberFormatException zah(String str) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 16);
        sb.append("Invalid Size: \"");
        sb.append(str);
        sb.append("\"");
        throw new NumberFormatException(sb.toString());
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size size = (Size) obj;
            if (this.zanj == size.zanj && this.zank == size.zank) {
                return true;
            }
        }
        return false;
    }

    public final int getHeight() {
        return this.zank;
    }

    public final int getWidth() {
        return this.zanj;
    }

    public final int hashCode() {
        int i2 = this.zank;
        int i3 = this.zanj;
        return i2 ^ ((i3 >>> 16) | (i3 << 16));
    }

    public final String toString() {
        int i2 = this.zanj;
        int i3 = this.zank;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i2);
        sb.append("x");
        sb.append(i3);
        return sb.toString();
    }
}
