package org.apache.commons.compress.archivers.zip;

/* loaded from: classes9.dex */
public class UnrecognizedExtraField implements ZipExtraField {
    private byte[] centralData;
    private ZipShort headerId;
    private byte[] localData;

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        byte[] bArr = this.centralData;
        return bArr != null ? ZipUtil.copy(bArr) : getLocalFileDataData();
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        byte[] bArr = this.centralData;
        return bArr != null ? new ZipShort(bArr.length) : getLocalFileDataLength();
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return this.headerId;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localData);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        byte[] bArr = this.localData;
        return new ZipShort(bArr != null ? bArr.length : 0);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        setCentralDirectoryData(bArr2);
        if (this.localData == null) {
            setLocalFileDataData(bArr2);
        }
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        setLocalFileDataData(bArr2);
    }

    public void setCentralDirectoryData(byte[] bArr) {
        this.centralData = ZipUtil.copy(bArr);
    }

    public void setHeaderId(ZipShort zipShort) {
        this.headerId = zipShort;
    }

    public void setLocalFileDataData(byte[] bArr) {
        this.localData = ZipUtil.copy(bArr);
    }
}
