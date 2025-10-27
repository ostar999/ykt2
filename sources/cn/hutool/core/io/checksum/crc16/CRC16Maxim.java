package cn.hutool.core.io.checksum.crc16;

/* loaded from: classes.dex */
public class CRC16Maxim extends CRC16Checksum {
    private static final int WC_POLY = 40961;
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.io.checksum.crc16.CRC16Checksum, java.util.zip.Checksum
    public void update(byte[] bArr, int i2, int i3) {
        super.update(bArr, i2, i3);
        this.wCRCin ^= 65535;
    }

    @Override // java.util.zip.Checksum
    public void update(int i2) {
        this.wCRCin = (i2 & 255) ^ this.wCRCin;
        for (int i3 = 0; i3 < 8; i3++) {
            int i4 = this.wCRCin;
            if ((i4 & 1) != 0) {
                this.wCRCin = (i4 >> 1) ^ WC_POLY;
            } else {
                this.wCRCin = i4 >> 1;
            }
        }
    }
}
