package org.apache.commons.compress.archivers.zip;

/* loaded from: classes9.dex */
public final class UnparseableExtraFieldData implements ZipExtraField {
    private static final ZipShort HEADER_ID = new ZipShort(44225);
    private byte[] centralDirectoryData;
    private byte[] localFileData;

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        byte[] bArr = this.centralDirectoryData;
        return bArr == null ? getLocalFileDataData() : ZipUtil.copy(bArr);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        byte[] bArr = this.centralDirectoryData;
        return bArr == null ? getLocalFileDataLength() : new ZipShort(bArr.length);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localFileData);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        byte[] bArr = this.localFileData;
        return new ZipShort(bArr == null ? 0 : bArr.length);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.centralDirectoryData = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        if (this.localFileData == null) {
            parseFromLocalFileData(bArr, i2, i3);
        }
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.localFileData = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
    }
}
