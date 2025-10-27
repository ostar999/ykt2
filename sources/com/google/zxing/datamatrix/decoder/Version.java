package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public final class Version {
    private static final Version[] VERSIONS = buildVersions();
    private final int dataRegionSizeColumns;
    private final int dataRegionSizeRows;
    private final ECBlocks ecBlocks;
    private final int symbolSizeColumns;
    private final int symbolSizeRows;
    private final int totalCodewords;
    private final int versionNumber;

    public static final class ECB {
        private final int count;
        private final int dataCodewords;

        public int getCount() {
            return this.count;
        }

        public int getDataCodewords() {
            return this.dataCodewords;
        }

        private ECB(int i2, int i3) {
            this.count = i2;
            this.dataCodewords = i3;
        }
    }

    private Version(int i2, int i3, int i4, int i5, int i6, ECBlocks eCBlocks) {
        this.versionNumber = i2;
        this.symbolSizeRows = i3;
        this.symbolSizeColumns = i4;
        this.dataRegionSizeRows = i5;
        this.dataRegionSizeColumns = i6;
        this.ecBlocks = eCBlocks;
        int eCCodewords = eCBlocks.getECCodewords();
        int count = 0;
        for (ECB ecb : eCBlocks.getECBlocks()) {
            count += ecb.getCount() * (ecb.getDataCodewords() + eCCodewords);
        }
        this.totalCodewords = count;
    }

    private static Version[] buildVersions() {
        int i2 = 8;
        int i3 = 1;
        int i4 = 5;
        Version version = new Version(3, 14, 14, 12, 12, new ECBlocks(10, new ECB(i3, i2)));
        int i5 = 2;
        int i6 = 12;
        int i7 = 18;
        Version version2 = new Version(7, 22, 22, 20, 20, new ECBlocks(20, new ECB(i3, 30)));
        int i8 = 6;
        int i9 = 36;
        int i10 = 62;
        int i11 = 56;
        int i12 = 68;
        ECB ecb = new ECB(i3, 5);
        ECB ecb2 = new ECB(i3, 10);
        ECB ecb3 = new ECB(i3, 16);
        return new Version[]{new Version(1, 10, 10, 8, 8, new ECBlocks(i4, new ECB(i3, 3))), new Version(2, 12, 12, 10, 10, new ECBlocks(7, new ECB(i3, i4))), version, new Version(4, 16, 16, 14, 14, new ECBlocks(i6, new ECB(i3, i6))), new Version(5, 18, 18, 16, 16, new ECBlocks(14, new ECB(i3, i7))), new Version(6, 20, 20, 18, 18, new ECBlocks(i7, new ECB(i3, 22))), version2, new Version(8, 24, 24, 22, 22, new ECBlocks(24, new ECB(i3, i9))), new Version(9, 26, 26, 24, 24, new ECBlocks(28, new ECB(i3, 44))), new Version(10, 32, 32, 14, 14, new ECBlocks(i9, new ECB(i3, i10))), new Version(11, 36, 36, 16, 16, new ECBlocks(42, new ECB(i3, 86))), new Version(12, 40, 40, 18, 18, new ECBlocks(48, new ECB(i3, 114))), new Version(13, 44, 44, 20, 20, new ECBlocks(i11, new ECB(i3, 144))), new Version(14, 48, 48, 22, 22, new ECBlocks(i12, new ECB(i3, R2.anim.window_ios_in))), new Version(15, 52, 52, 24, 24, new ECBlocks(42, new ECB(i5, 102))), new Version(16, 64, 64, 14, 14, new ECBlocks(i11, new ECB(i5, 140))), new Version(17, 72, 72, 16, 16, new ECBlocks(i9, new ECB(4, 92))), new Version(18, 80, 80, 18, 18, new ECBlocks(48, new ECB(4, 114))), new Version(19, 88, 88, 20, 20, new ECBlocks(i11, new ECB(4, 144))), new Version(20, 96, 96, 22, 22, new ECBlocks(i12, new ECB(4, R2.anim.window_ios_in))), new Version(21, 104, 104, 24, 24, new ECBlocks(i11, new ECB(i8, 136))), new Version(22, 120, 120, 18, 18, new ECBlocks(i12, new ECB(i8, R2.anim.window_ios_out))), new Version(23, 132, 132, 20, 20, new ECBlocks(i10, new ECB(i2, 163))), new Version(24, 144, 144, 22, 22, new ECBlocks(i10, new ECB(i2, 156), new ECB(i5, 155))), new Version(25, 8, 18, 6, 16, new ECBlocks(7, ecb)), new Version(26, 8, 32, 6, 14, new ECBlocks(11, ecb2)), new Version(27, 12, 26, 10, 24, new ECBlocks(14, ecb3)), new Version(28, 12, 36, 10, 16, new ECBlocks(i7, new ECB(i3, 22))), new Version(29, 16, 36, 14, 16, new ECBlocks(24, new ECB(i3, 32))), new Version(30, 16, 48, 14, 22, new ECBlocks(28, new ECB(i3, 49)))};
    }

    public static Version getVersionForDimensions(int i2, int i3) throws FormatException {
        if ((i2 & 1) != 0 || (i3 & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        for (Version version : VERSIONS) {
            if (version.symbolSizeRows == i2 && version.symbolSizeColumns == i3) {
                return version;
            }
        }
        throw FormatException.getFormatInstance();
    }

    public int getDataRegionSizeColumns() {
        return this.dataRegionSizeColumns;
    }

    public int getDataRegionSizeRows() {
        return this.dataRegionSizeRows;
    }

    public ECBlocks getECBlocks() {
        return this.ecBlocks;
    }

    public int getSymbolSizeColumns() {
        return this.symbolSizeColumns;
    }

    public int getSymbolSizeRows() {
        return this.symbolSizeRows;
    }

    public int getTotalCodewords() {
        return this.totalCodewords;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public String toString() {
        return String.valueOf(this.versionNumber);
    }

    public static final class ECBlocks {
        private final ECB[] ecBlocks;
        private final int ecCodewords;

        public ECB[] getECBlocks() {
            return this.ecBlocks;
        }

        public int getECCodewords() {
            return this.ecCodewords;
        }

        private ECBlocks(int i2, ECB ecb) {
            this.ecCodewords = i2;
            this.ecBlocks = new ECB[]{ecb};
        }

        private ECBlocks(int i2, ECB ecb, ECB ecb2) {
            this.ecCodewords = i2;
            this.ecBlocks = new ECB[]{ecb, ecb2};
        }
    }
}
