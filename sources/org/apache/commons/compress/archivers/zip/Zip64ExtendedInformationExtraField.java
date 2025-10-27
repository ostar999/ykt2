package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;

/* loaded from: classes9.dex */
public class Zip64ExtendedInformationExtraField implements ZipExtraField {
    private static final String LFH_MUST_HAVE_BOTH_SIZES_MSG = "Zip64 extended information must contain both size values in the local file header.";
    private ZipEightByteInteger compressedSize;
    private ZipLong diskStart;
    private byte[] rawCentralDirectoryData;
    private ZipEightByteInteger relativeHeaderOffset;
    private ZipEightByteInteger size;
    static final ZipShort HEADER_ID = new ZipShort(1);
    private static final byte[] EMPTY = new byte[0];

    public Zip64ExtendedInformationExtraField() {
    }

    private int addSizes(byte[] bArr) {
        int i2;
        ZipEightByteInteger zipEightByteInteger = this.size;
        if (zipEightByteInteger != null) {
            System.arraycopy(zipEightByteInteger.getBytes(), 0, bArr, 0, 8);
            i2 = 8;
        } else {
            i2 = 0;
        }
        ZipEightByteInteger zipEightByteInteger2 = this.compressedSize;
        if (zipEightByteInteger2 == null) {
            return i2;
        }
        System.arraycopy(zipEightByteInteger2.getBytes(), 0, bArr, i2, 8);
        return i2 + 8;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        byte[] bArr = new byte[getCentralDirectoryLength().getValue()];
        int iAddSizes = addSizes(bArr);
        ZipEightByteInteger zipEightByteInteger = this.relativeHeaderOffset;
        if (zipEightByteInteger != null) {
            System.arraycopy(zipEightByteInteger.getBytes(), 0, bArr, iAddSizes, 8);
            iAddSizes += 8;
        }
        ZipLong zipLong = this.diskStart;
        if (zipLong != null) {
            System.arraycopy(zipLong.getBytes(), 0, bArr, iAddSizes, 4);
        }
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        return new ZipShort((this.size != null ? 8 : 0) + (this.compressedSize != null ? 8 : 0) + (this.relativeHeaderOffset == null ? 0 : 8) + (this.diskStart != null ? 4 : 0));
    }

    public ZipEightByteInteger getCompressedSize() {
        return this.compressedSize;
    }

    public ZipLong getDiskStartNumber() {
        return this.diskStart;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        ZipEightByteInteger zipEightByteInteger = this.size;
        if (zipEightByteInteger == null && this.compressedSize == null) {
            return EMPTY;
        }
        if (zipEightByteInteger == null || this.compressedSize == null) {
            throw new IllegalArgumentException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
        }
        byte[] bArr = new byte[16];
        addSizes(bArr);
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        return new ZipShort(this.size != null ? 16 : 0);
    }

    public ZipEightByteInteger getRelativeHeaderOffset() {
        return this.relativeHeaderOffset;
    }

    public ZipEightByteInteger getSize() {
        return this.size;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) throws ZipException {
        byte[] bArr2 = new byte[i3];
        this.rawCentralDirectoryData = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        if (i3 >= 28) {
            parseFromLocalFileData(bArr, i2, i3);
            return;
        }
        if (i3 != 24) {
            if (i3 % 8 == 4) {
                this.diskStart = new ZipLong(bArr, (i2 + i3) - 4);
            }
        } else {
            this.size = new ZipEightByteInteger(bArr, i2);
            int i4 = i2 + 8;
            this.compressedSize = new ZipEightByteInteger(bArr, i4);
            this.relativeHeaderOffset = new ZipEightByteInteger(bArr, i4 + 8);
        }
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) throws ZipException {
        if (i3 == 0) {
            return;
        }
        if (i3 < 16) {
            throw new ZipException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
        }
        this.size = new ZipEightByteInteger(bArr, i2);
        int i4 = i2 + 8;
        this.compressedSize = new ZipEightByteInteger(bArr, i4);
        int i5 = i4 + 8;
        int i6 = i3 - 16;
        if (i6 >= 8) {
            this.relativeHeaderOffset = new ZipEightByteInteger(bArr, i5);
            i5 += 8;
            i6 -= 8;
        }
        if (i6 >= 4) {
            this.diskStart = new ZipLong(bArr, i5);
        }
    }

    public void reparseCentralDirectoryData(boolean z2, boolean z3, boolean z4, boolean z5) throws ZipException {
        byte[] bArr = this.rawCentralDirectoryData;
        if (bArr != null) {
            int i2 = 0;
            int i3 = (z2 ? 8 : 0) + (z3 ? 8 : 0) + (z4 ? 8 : 0) + (z5 ? 4 : 0);
            if (bArr.length < i3) {
                throw new ZipException("central directory zip64 extended information extra field's length doesn't match central directory data.  Expected length " + i3 + " but is " + this.rawCentralDirectoryData.length);
            }
            if (z2) {
                this.size = new ZipEightByteInteger(this.rawCentralDirectoryData, 0);
                i2 = 8;
            }
            if (z3) {
                this.compressedSize = new ZipEightByteInteger(this.rawCentralDirectoryData, i2);
                i2 += 8;
            }
            if (z4) {
                this.relativeHeaderOffset = new ZipEightByteInteger(this.rawCentralDirectoryData, i2);
                i2 += 8;
            }
            if (z5) {
                this.diskStart = new ZipLong(this.rawCentralDirectoryData, i2);
            }
        }
    }

    public void setCompressedSize(ZipEightByteInteger zipEightByteInteger) {
        this.compressedSize = zipEightByteInteger;
    }

    public void setDiskStartNumber(ZipLong zipLong) {
        this.diskStart = zipLong;
    }

    public void setRelativeHeaderOffset(ZipEightByteInteger zipEightByteInteger) {
        this.relativeHeaderOffset = zipEightByteInteger;
    }

    public void setSize(ZipEightByteInteger zipEightByteInteger) {
        this.size = zipEightByteInteger;
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger zipEightByteInteger, ZipEightByteInteger zipEightByteInteger2) {
        this(zipEightByteInteger, zipEightByteInteger2, null, null);
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger zipEightByteInteger, ZipEightByteInteger zipEightByteInteger2, ZipEightByteInteger zipEightByteInteger3, ZipLong zipLong) {
        this.size = zipEightByteInteger;
        this.compressedSize = zipEightByteInteger2;
        this.relativeHeaderOffset = zipEightByteInteger3;
        this.diskStart = zipLong;
    }
}
