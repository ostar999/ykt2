package org.apache.commons.compress.archivers.zip;

import com.yikaobang.yixue.R2;

/* loaded from: classes9.dex */
public class UnicodeCommentExtraField extends AbstractUnicodeExtraField {
    public static final ZipShort UCOM_ID = new ZipShort(R2.style.ExoStyledControls_Button_Center_FfwdWithAmount);

    public UnicodeCommentExtraField() {
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return UCOM_ID;
    }

    public UnicodeCommentExtraField(String str, byte[] bArr, int i2, int i3) {
        super(str, bArr, i2, i3);
    }

    public UnicodeCommentExtraField(String str, byte[] bArr) {
        super(str, bArr);
    }
}
