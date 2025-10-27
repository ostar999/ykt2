package cn.hutool.core.io.checksum.crc16;

/* loaded from: classes.dex */
public class CRC16Ansi extends CRC16Checksum {
    private static final int WC_POLY = 40961;
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.io.checksum.crc16.CRC16Checksum, java.util.zip.Checksum
    public void reset() {
        this.wCRCin = 65535;
    }

    @Override // java.util.zip.Checksum
    public void update(int i2) {
        this.wCRCin = i2 ^ (this.wCRCin >> 8);
        for (int i3 = 0; i3 < 8; i3++) {
            int i4 = this.wCRCin;
            int i5 = i4 & 1;
            int i6 = i4 >> 1;
            this.wCRCin = i6;
            if (i5 == 1) {
                this.wCRCin = i6 ^ WC_POLY;
            }
        }
    }
}
