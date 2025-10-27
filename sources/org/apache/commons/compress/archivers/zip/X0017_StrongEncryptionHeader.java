package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.PKWareExtraHeader;

/* loaded from: classes9.dex */
public class X0017_StrongEncryptionHeader extends PKWareExtraHeader {
    private PKWareExtraHeader.EncryptionAlgorithm algId;
    private int bitlen;
    private byte[] erdData;
    private int flags;
    private int format;
    private PKWareExtraHeader.HashAlgorithm hashAlg;
    private int hashSize;
    private byte[] ivData;
    private byte[] keyBlob;
    private long rcount;
    private byte[] recipientKeyHash;
    private byte[] vCRC32;
    private byte[] vData;

    public X0017_StrongEncryptionHeader() {
        super(new ZipShort(23));
    }

    public PKWareExtraHeader.EncryptionAlgorithm getEncryptionAlgorithm() {
        return this.algId;
    }

    public PKWareExtraHeader.HashAlgorithm getHashAlgorithm() {
        return this.hashAlg;
    }

    public long getRecordCount() {
        return this.rcount;
    }

    public void parseCentralDirectoryFormat(byte[] bArr, int i2, int i3) {
        this.format = ZipShort.getValue(bArr, i2);
        this.algId = PKWareExtraHeader.EncryptionAlgorithm.getAlgorithmByCode(ZipShort.getValue(bArr, i2 + 2));
        this.bitlen = ZipShort.getValue(bArr, i2 + 4);
        this.flags = ZipShort.getValue(bArr, i2 + 6);
        long value = ZipLong.getValue(bArr, i2 + 8);
        this.rcount = value;
        if (value > 0) {
            this.hashAlg = PKWareExtraHeader.HashAlgorithm.getAlgorithmByCode(ZipShort.getValue(bArr, i2 + 12));
            this.hashSize = ZipShort.getValue(bArr, i2 + 14);
            for (int i4 = 0; i4 < this.rcount; i4++) {
                for (int i5 = 0; i5 < this.hashSize; i5++) {
                }
            }
        }
    }

    public void parseFileFormat(byte[] bArr, int i2, int i3) {
        int value = ZipShort.getValue(bArr, i2);
        byte[] bArr2 = new byte[value];
        this.ivData = bArr2;
        System.arraycopy(bArr, i2 + 4, bArr2, 0, value);
        int i4 = i2 + value;
        this.format = ZipShort.getValue(bArr, i4 + 6);
        this.algId = PKWareExtraHeader.EncryptionAlgorithm.getAlgorithmByCode(ZipShort.getValue(bArr, i4 + 8));
        this.bitlen = ZipShort.getValue(bArr, i4 + 10);
        this.flags = ZipShort.getValue(bArr, i4 + 12);
        int value2 = ZipShort.getValue(bArr, i4 + 14);
        byte[] bArr3 = new byte[value2];
        this.erdData = bArr3;
        int i5 = i4 + 16;
        System.arraycopy(bArr, i5, bArr3, 0, value2);
        this.rcount = ZipLong.getValue(bArr, i5 + value2);
        System.out.println("rcount: " + this.rcount);
        if (this.rcount == 0) {
            int value3 = ZipShort.getValue(bArr, i4 + 20 + value2);
            int i6 = value3 - 4;
            byte[] bArr4 = new byte[i6];
            this.vData = bArr4;
            this.vCRC32 = new byte[4];
            int i7 = i4 + 22 + value2;
            System.arraycopy(bArr, i7, bArr4, 0, i6);
            System.arraycopy(bArr, (i7 + value3) - 4, this.vCRC32, 0, 4);
            return;
        }
        this.hashAlg = PKWareExtraHeader.HashAlgorithm.getAlgorithmByCode(ZipShort.getValue(bArr, i4 + 20 + value2));
        int i8 = i4 + 22 + value2;
        this.hashSize = ZipShort.getValue(bArr, i8);
        int i9 = i4 + 24 + value2;
        int value4 = ZipShort.getValue(bArr, i9);
        int i10 = this.hashSize;
        byte[] bArr5 = new byte[i10];
        this.recipientKeyHash = bArr5;
        this.keyBlob = new byte[value4 - i10];
        System.arraycopy(bArr, i9, bArr5, 0, i10);
        int i11 = this.hashSize;
        System.arraycopy(bArr, i9 + i11, this.keyBlob, 0, value4 - i11);
        int value5 = ZipShort.getValue(bArr, i4 + 26 + value2 + value4);
        int i12 = value5 - 4;
        byte[] bArr6 = new byte[i12];
        this.vData = bArr6;
        this.vCRC32 = new byte[4];
        int i13 = i8 + value4;
        System.arraycopy(bArr, i13, bArr6, 0, i12);
        System.arraycopy(bArr, (i13 + value5) - 4, this.vCRC32, 0, 4);
    }

    @Override // org.apache.commons.compress.archivers.zip.PKWareExtraHeader, org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) {
        super.parseFromCentralDirectoryData(bArr, i2, i3);
        parseCentralDirectoryFormat(bArr, i2, i3);
    }

    @Override // org.apache.commons.compress.archivers.zip.PKWareExtraHeader, org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) {
        super.parseFromLocalFileData(bArr, i2, i3);
        parseFileFormat(bArr, i2, i3);
    }
}
