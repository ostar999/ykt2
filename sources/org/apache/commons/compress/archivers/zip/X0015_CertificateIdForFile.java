package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.PKWareExtraHeader;

/* loaded from: classes9.dex */
public class X0015_CertificateIdForFile extends PKWareExtraHeader {
    private PKWareExtraHeader.HashAlgorithm hashAlg;
    private int rcount;

    public X0015_CertificateIdForFile() {
        super(new ZipShort(21));
    }

    public PKWareExtraHeader.HashAlgorithm getHashAlgorithm() {
        return this.hashAlg;
    }

    public int getRecordCount() {
        return this.rcount;
    }

    @Override // org.apache.commons.compress.archivers.zip.PKWareExtraHeader, org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) {
        super.parseFromCentralDirectoryData(bArr, i2, i3);
        this.rcount = ZipShort.getValue(bArr, i2);
        this.hashAlg = PKWareExtraHeader.HashAlgorithm.getAlgorithmByCode(ZipShort.getValue(bArr, i2 + 2));
    }
}
