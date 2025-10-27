package org.bouncycastle.bcpg;

/* loaded from: classes9.dex */
public class CRC24 {
    private static final int CRC24_INIT = 11994318;
    private static final int CRC24_POLY = 25578747;
    private int crc = CRC24_INIT;

    public int getValue() {
        return this.crc;
    }

    public void reset() {
        this.crc = CRC24_INIT;
    }

    public void update(int i2) {
        this.crc = (i2 << 16) ^ this.crc;
        for (int i3 = 0; i3 < 8; i3++) {
            int i4 = this.crc << 1;
            this.crc = i4;
            if ((16777216 & i4) != 0) {
                this.crc = i4 ^ CRC24_POLY;
            }
        }
    }
}
