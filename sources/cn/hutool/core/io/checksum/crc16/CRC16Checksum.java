package cn.hutool.core.io.checksum.crc16;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.HexUtil;
import java.io.Serializable;
import java.util.zip.Checksum;

/* loaded from: classes.dex */
public abstract class CRC16Checksum implements Checksum, Serializable {
    private static final long serialVersionUID = 1;
    protected int wCRCin;

    public CRC16Checksum() {
        reset();
    }

    public String getHexValue() {
        return getHexValue(false);
    }

    @Override // java.util.zip.Checksum
    public long getValue() {
        return this.wCRCin;
    }

    @Override // java.util.zip.Checksum
    public void reset() {
        this.wCRCin = 0;
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public String getHexValue(boolean z2) {
        String hex = HexUtil.toHex(getValue());
        return z2 ? CharSequenceUtil.padPre((CharSequence) hex, 4, '0') : hex;
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            update(bArr[i4]);
        }
    }
}
