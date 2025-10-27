package com.google.zxing.qrcode.decoder;

import com.google.zxing.common.BitMatrix;

/* loaded from: classes4.dex */
abstract class DataMask {
    private static final DataMask[] DATA_MASKS;

    public static final class DataMask000 extends DataMask {
        private DataMask000() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            return ((i2 + i3) & 1) == 0;
        }
    }

    public static final class DataMask001 extends DataMask {
        private DataMask001() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            return (i2 & 1) == 0;
        }
    }

    public static final class DataMask010 extends DataMask {
        private DataMask010() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            return i3 % 3 == 0;
        }
    }

    public static final class DataMask011 extends DataMask {
        private DataMask011() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            return (i2 + i3) % 3 == 0;
        }
    }

    public static final class DataMask100 extends DataMask {
        private DataMask100() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            return (((i2 >>> 1) + (i3 / 3)) & 1) == 0;
        }
    }

    public static final class DataMask101 extends DataMask {
        private DataMask101() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            int i4 = i2 * i3;
            return (i4 & 1) + (i4 % 3) == 0;
        }
    }

    public static final class DataMask110 extends DataMask {
        private DataMask110() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            int i4 = i2 * i3;
            return (((i4 & 1) + (i4 % 3)) & 1) == 0;
        }
    }

    public static final class DataMask111 extends DataMask {
        private DataMask111() {
            super();
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        public boolean isMasked(int i2, int i3) {
            return ((((i2 + i3) & 1) + ((i2 * i3) % 3)) & 1) == 0;
        }
    }

    static {
        DATA_MASKS = new DataMask[]{new DataMask000(), new DataMask001(), new DataMask010(), new DataMask011(), new DataMask100(), new DataMask101(), new DataMask110(), new DataMask111()};
    }

    public static DataMask forReference(int i2) {
        if (i2 < 0 || i2 > 7) {
            throw new IllegalArgumentException();
        }
        return DATA_MASKS[i2];
    }

    public abstract boolean isMasked(int i2, int i3);

    public final void unmaskBitMatrix(BitMatrix bitMatrix, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                if (isMasked(i3, i4)) {
                    bitMatrix.flip(i4, i3);
                }
            }
        }
    }

    private DataMask() {
    }
}
