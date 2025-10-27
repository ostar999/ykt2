package cn.hutool.core.io.checksum;

import cn.hutool.core.io.checksum.crc16.CRC16Checksum;
import cn.hutool.core.io.checksum.crc16.CRC16IBM;
import java.io.Serializable;
import java.util.zip.Checksum;

/* loaded from: classes.dex */
public class CRC16 implements Checksum, Serializable {
    private static final long serialVersionUID = 1;
    private final CRC16Checksum crc16;

    public CRC16() {
        this(new CRC16IBM());
    }

    public String getHexValue() {
        return this.crc16.getHexValue();
    }

    @Override // java.util.zip.Checksum
    public long getValue() {
        return this.crc16.getValue();
    }

    @Override // java.util.zip.Checksum
    public void reset() {
        this.crc16.reset();
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr, int i2, int i3) {
        this.crc16.update(bArr, i2, i3);
    }

    public CRC16(CRC16Checksum cRC16Checksum) {
        this.crc16 = cRC16Checksum;
    }

    public String getHexValue(boolean z2) {
        return this.crc16.getHexValue(z2);
    }

    @Override // java.util.zip.Checksum
    public void update(int i2) {
        this.crc16.update(i2);
    }
}
