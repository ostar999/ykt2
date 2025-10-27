package com.google.zxing.common.reedsolomon;

import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public final class GenericGF {
    public static final GenericGF AZTEC_DATA_6;
    public static final GenericGF AZTEC_DATA_8;
    public static final GenericGF AZTEC_PARAM;
    public static final GenericGF DATA_MATRIX_FIELD_256;
    private static final int INITIALIZATION_THRESHOLD = 0;
    public static final GenericGF MAXICODE_FIELD_64;
    public static final GenericGF QR_CODE_FIELD_256;
    private int[] expTable;
    private final int generatorBase;
    private boolean initialized = false;
    private int[] logTable;
    private GenericGFPoly one;
    private final int primitive;
    private final int size;
    private GenericGFPoly zero;
    public static final GenericGF AZTEC_DATA_12 = new GenericGF(R2.color.alivc_player_bg_progress_rear, 4096, 1);
    public static final GenericGF AZTEC_DATA_10 = new GenericGF(1033, 1024, 1);

    static {
        GenericGF genericGF = new GenericGF(67, 64, 1);
        AZTEC_DATA_6 = genericGF;
        AZTEC_PARAM = new GenericGF(19, 16, 1);
        QR_CODE_FIELD_256 = new GenericGF(R2.attr.alertDialogStyle, 256, 0);
        GenericGF genericGF2 = new GenericGF(301, 256, 1);
        DATA_MATRIX_FIELD_256 = genericGF2;
        AZTEC_DATA_8 = genericGF2;
        MAXICODE_FIELD_64 = genericGF;
    }

    public GenericGF(int i2, int i3, int i4) {
        this.primitive = i2;
        this.size = i3;
        this.generatorBase = i4;
        if (i3 <= 0) {
            initialize();
        }
    }

    public static int addOrSubtract(int i2, int i3) {
        return i2 ^ i3;
    }

    private void checkInit() {
        if (this.initialized) {
            return;
        }
        initialize();
    }

    private void initialize() {
        int i2 = this.size;
        this.expTable = new int[i2];
        this.logTable = new int[i2];
        int i3 = 1;
        int i4 = 0;
        while (true) {
            int i5 = this.size;
            if (i4 >= i5) {
                break;
            }
            this.expTable[i4] = i3;
            i3 <<= 1;
            if (i3 >= i5) {
                i3 = (i3 ^ this.primitive) & (i5 - 1);
            }
            i4++;
        }
        for (int i6 = 0; i6 < this.size - 1; i6++) {
            this.logTable[this.expTable[i6]] = i6;
        }
        this.zero = new GenericGFPoly(this, new int[]{0});
        this.one = new GenericGFPoly(this, new int[]{1});
        this.initialized = true;
    }

    public GenericGFPoly buildMonomial(int i2, int i3) {
        checkInit();
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.zero;
        }
        int[] iArr = new int[i2 + 1];
        iArr[0] = i3;
        return new GenericGFPoly(this, iArr);
    }

    public int exp(int i2) {
        checkInit();
        return this.expTable[i2];
    }

    public int getGeneratorBase() {
        return this.generatorBase;
    }

    public GenericGFPoly getOne() {
        checkInit();
        return this.one;
    }

    public int getSize() {
        return this.size;
    }

    public GenericGFPoly getZero() {
        checkInit();
        return this.zero;
    }

    public int inverse(int i2) {
        checkInit();
        if (i2 != 0) {
            return this.expTable[(this.size - this.logTable[i2]) - 1];
        }
        throw new ArithmeticException();
    }

    public int log(int i2) {
        checkInit();
        if (i2 != 0) {
            return this.logTable[i2];
        }
        throw new IllegalArgumentException();
    }

    public int multiply(int i2, int i3) {
        checkInit();
        if (i2 == 0 || i3 == 0) {
            return 0;
        }
        int[] iArr = this.expTable;
        int[] iArr2 = this.logTable;
        return iArr[(iArr2[i2] + iArr2[i3]) % (this.size - 1)];
    }

    public String toString() {
        return "GF(0x" + Integer.toHexString(this.primitive) + ',' + this.size + ')';
    }
}
