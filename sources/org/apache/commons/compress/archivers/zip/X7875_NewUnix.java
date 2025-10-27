package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.zip.ZipException;

/* loaded from: classes9.dex */
public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private BigInteger gid;
    private BigInteger uid;
    private int version = 1;
    private static final ZipShort HEADER_ID = new ZipShort(30837);
    private static final ZipShort ZERO = new ZipShort(0);
    private static final BigInteger ONE_THOUSAND = BigInteger.valueOf(1000);

    public X7875_NewUnix() {
        reset();
    }

    private void reset() {
        BigInteger bigInteger = ONE_THOUSAND;
        this.uid = bigInteger;
        this.gid = bigInteger;
    }

    public static byte[] trimLeadingZeroesForceMinLength(byte[] bArr) {
        if (bArr == null) {
            return bArr;
        }
        int length = bArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length && bArr[i3] == 0; i3++) {
            i2++;
        }
        int iMax = Math.max(1, bArr.length - i2);
        byte[] bArr2 = new byte[iMax];
        int length2 = iMax - (bArr.length - i2);
        System.arraycopy(bArr, i2, bArr2, length2, iMax - length2);
        return bArr2;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof X7875_NewUnix)) {
            return false;
        }
        X7875_NewUnix x7875_NewUnix = (X7875_NewUnix) obj;
        return this.version == x7875_NewUnix.version && this.uid.equals(x7875_NewUnix.uid) && this.gid.equals(x7875_NewUnix.gid);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        return new byte[0];
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        return ZERO;
    }

    public long getGID() {
        return ZipUtil.bigToLong(this.gid);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        byte[] byteArray = this.uid.toByteArray();
        byte[] byteArray2 = this.gid.toByteArray();
        byte[] bArrTrimLeadingZeroesForceMinLength = trimLeadingZeroesForceMinLength(byteArray);
        byte[] bArrTrimLeadingZeroesForceMinLength2 = trimLeadingZeroesForceMinLength(byteArray2);
        byte[] bArr = new byte[bArrTrimLeadingZeroesForceMinLength.length + 3 + bArrTrimLeadingZeroesForceMinLength2.length];
        ZipUtil.reverse(bArrTrimLeadingZeroesForceMinLength);
        ZipUtil.reverse(bArrTrimLeadingZeroesForceMinLength2);
        bArr[0] = ZipUtil.unsignedIntToSignedByte(this.version);
        bArr[1] = ZipUtil.unsignedIntToSignedByte(bArrTrimLeadingZeroesForceMinLength.length);
        System.arraycopy(bArrTrimLeadingZeroesForceMinLength, 0, bArr, 2, bArrTrimLeadingZeroesForceMinLength.length);
        int length = 2 + bArrTrimLeadingZeroesForceMinLength.length;
        bArr[length] = ZipUtil.unsignedIntToSignedByte(bArrTrimLeadingZeroesForceMinLength2.length);
        System.arraycopy(bArrTrimLeadingZeroesForceMinLength2, 0, bArr, length + 1, bArrTrimLeadingZeroesForceMinLength2.length);
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        return new ZipShort(trimLeadingZeroesForceMinLength(this.uid.toByteArray()).length + 3 + trimLeadingZeroesForceMinLength(this.gid.toByteArray()).length);
    }

    public long getUID() {
        return ZipUtil.bigToLong(this.uid);
    }

    public int hashCode() {
        return (Integer.rotateLeft(this.uid.hashCode(), 16) ^ (this.version * (-1234567))) ^ this.gid.hashCode();
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) throws ZipException {
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) throws ZipException {
        reset();
        int i4 = i2 + 1;
        this.version = ZipUtil.signedByteToUnsignedInt(bArr[i2]);
        int i5 = i4 + 1;
        int iSignedByteToUnsignedInt = ZipUtil.signedByteToUnsignedInt(bArr[i4]);
        byte[] bArr2 = new byte[iSignedByteToUnsignedInt];
        System.arraycopy(bArr, i5, bArr2, 0, iSignedByteToUnsignedInt);
        int i6 = i5 + iSignedByteToUnsignedInt;
        this.uid = new BigInteger(1, ZipUtil.reverse(bArr2));
        int i7 = i6 + 1;
        int iSignedByteToUnsignedInt2 = ZipUtil.signedByteToUnsignedInt(bArr[i6]);
        byte[] bArr3 = new byte[iSignedByteToUnsignedInt2];
        System.arraycopy(bArr, i7, bArr3, 0, iSignedByteToUnsignedInt2);
        this.gid = new BigInteger(1, ZipUtil.reverse(bArr3));
    }

    public void setGID(long j2) {
        this.gid = ZipUtil.longToBig(j2);
    }

    public void setUID(long j2) {
        this.uid = ZipUtil.longToBig(j2);
    }

    public String toString() {
        return "0x7875 Zip Extra Field: UID=" + this.uid + " GID=" + this.gid;
    }
}
