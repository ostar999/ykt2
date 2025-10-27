package cn.hutool.core.io.checksum.crc16;

/* loaded from: classes.dex */
public class CRC16CCITT extends CRC16Checksum {
    private static final int WC_POLY = 33800;
    private static final long serialVersionUID = 1;

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
