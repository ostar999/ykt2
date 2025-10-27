package cn.hutool.core.io.checksum.crc16;

/* loaded from: classes.dex */
public class CRC16XModem extends CRC16Checksum {
    private static final int WC_POLY = 4129;
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.io.checksum.crc16.CRC16Checksum, java.util.zip.Checksum
    public void update(byte[] bArr, int i2, int i3) {
        super.update(bArr, i2, i3);
        this.wCRCin &= 65535;
    }

    @Override // java.util.zip.Checksum
    public void update(int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            boolean z2 = ((i2 >> (7 - i3)) & 1) == 1;
            int i4 = this.wCRCin;
            boolean z3 = ((i4 >> 15) & 1) == 1;
            int i5 = i4 << 1;
            this.wCRCin = i5;
            if (z2 ^ z3) {
                this.wCRCin = i5 ^ 4129;
            }
        }
    }
}
