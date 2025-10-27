package org.apache.commons.compress.archivers.zip;

import com.yikaobang.yixue.R2;

/* loaded from: classes9.dex */
public class UnicodePathExtraField extends AbstractUnicodeExtraField {
    public static final ZipShort UPATH_ID = new ZipShort(R2.styleable.NumberPicker_np_dividerDistance);

    public UnicodePathExtraField() {
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return UPATH_ID;
    }

    public UnicodePathExtraField(String str, byte[] bArr, int i2, int i3) {
        super(str, bArr, i2, i3);
    }

    public UnicodePathExtraField(String str, byte[] bArr) {
        super(str, bArr);
    }
}
