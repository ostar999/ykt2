package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes4.dex */
final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private boolean mirror;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    public BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int height = bitMatrix.getHeight();
        if (height < 21 || (height & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.bitMatrix = bitMatrix;
    }

    private int copyBit(int i2, int i3, int i4) {
        return this.mirror ? this.bitMatrix.get(i3, i2) : this.bitMatrix.get(i2, i3) ? (i4 << 1) | 1 : i4 << 1;
    }

    public void mirror() {
        int i2 = 0;
        while (i2 < this.bitMatrix.getWidth()) {
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < this.bitMatrix.getHeight(); i4++) {
                if (this.bitMatrix.get(i2, i4) != this.bitMatrix.get(i4, i2)) {
                    this.bitMatrix.flip(i4, i2);
                    this.bitMatrix.flip(i2, i4);
                }
            }
            i2 = i3;
        }
    }

    public byte[] readCodewords() throws FormatException {
        FormatInformation formatInformation = readFormatInformation();
        Version version = readVersion();
        DataMask dataMaskForReference = DataMask.forReference(formatInformation.getDataMask());
        int height = this.bitMatrix.getHeight();
        dataMaskForReference.unmaskBitMatrix(this.bitMatrix, height);
        BitMatrix bitMatrixBuildFunctionPattern = version.buildFunctionPattern();
        byte[] bArr = new byte[version.getTotalCodewords()];
        int i2 = height - 1;
        boolean z2 = true;
        int i3 = i2;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i3 > 0) {
            if (i3 == 6) {
                i3--;
            }
            for (int i7 = 0; i7 < height; i7++) {
                int i8 = z2 ? i2 - i7 : i7;
                for (int i9 = 0; i9 < 2; i9++) {
                    int i10 = i3 - i9;
                    if (!bitMatrixBuildFunctionPattern.get(i10, i8)) {
                        i5++;
                        i6 <<= 1;
                        if (this.bitMatrix.get(i10, i8)) {
                            i6 |= 1;
                        }
                        if (i5 == 8) {
                            bArr[i4] = (byte) i6;
                            i4++;
                            i5 = 0;
                            i6 = 0;
                        }
                    }
                }
            }
            z2 = !z2;
            i3 -= 2;
        }
        if (i4 == version.getTotalCodewords()) {
            return bArr;
        }
        throw FormatException.getFormatInstance();
    }

    public FormatInformation readFormatInformation() throws FormatException {
        FormatInformation formatInformation = this.parsedFormatInfo;
        if (formatInformation != null) {
            return formatInformation;
        }
        int iCopyBit = 0;
        int iCopyBit2 = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            iCopyBit2 = copyBit(i2, 8, iCopyBit2);
        }
        int iCopyBit3 = copyBit(8, 7, copyBit(8, 8, copyBit(7, 8, iCopyBit2)));
        for (int i3 = 5; i3 >= 0; i3--) {
            iCopyBit3 = copyBit(8, i3, iCopyBit3);
        }
        int height = this.bitMatrix.getHeight();
        int i4 = height - 7;
        for (int i5 = height - 1; i5 >= i4; i5--) {
            iCopyBit = copyBit(8, i5, iCopyBit);
        }
        for (int i6 = height - 8; i6 < height; i6++) {
            iCopyBit = copyBit(i6, 8, iCopyBit);
        }
        FormatInformation formatInformationDecodeFormatInformation = FormatInformation.decodeFormatInformation(iCopyBit3, iCopyBit);
        this.parsedFormatInfo = formatInformationDecodeFormatInformation;
        if (formatInformationDecodeFormatInformation != null) {
            return formatInformationDecodeFormatInformation;
        }
        throw FormatException.getFormatInstance();
    }

    public Version readVersion() throws FormatException {
        Version version = this.parsedVersion;
        if (version != null) {
            return version;
        }
        int height = this.bitMatrix.getHeight();
        int i2 = (height - 17) >> 2;
        if (i2 <= 6) {
            return Version.getVersionForNumber(i2);
        }
        int i3 = height - 11;
        int iCopyBit = 0;
        int iCopyBit2 = 0;
        for (int i4 = 5; i4 >= 0; i4--) {
            for (int i5 = height - 9; i5 >= i3; i5--) {
                iCopyBit2 = copyBit(i5, i4, iCopyBit2);
            }
        }
        Version versionDecodeVersionInformation = Version.decodeVersionInformation(iCopyBit2);
        if (versionDecodeVersionInformation != null && versionDecodeVersionInformation.getDimensionForVersion() == height) {
            this.parsedVersion = versionDecodeVersionInformation;
            return versionDecodeVersionInformation;
        }
        for (int i6 = 5; i6 >= 0; i6--) {
            for (int i7 = height - 9; i7 >= i3; i7--) {
                iCopyBit = copyBit(i6, i7, iCopyBit);
            }
        }
        Version versionDecodeVersionInformation2 = Version.decodeVersionInformation(iCopyBit);
        if (versionDecodeVersionInformation2 == null || versionDecodeVersionInformation2.getDimensionForVersion() != height) {
            throw FormatException.getFormatInstance();
        }
        this.parsedVersion = versionDecodeVersionInformation2;
        return versionDecodeVersionInformation2;
    }

    public void remask() {
        FormatInformation formatInformation = this.parsedFormatInfo;
        if (formatInformation == null) {
            return;
        }
        DataMask.forReference(formatInformation.getDataMask()).unmaskBitMatrix(this.bitMatrix, this.bitMatrix.getHeight());
    }

    public void setMirror(boolean z2) {
        this.parsedVersion = null;
        this.parsedFormatInfo = null;
        this.mirror = z2;
    }
}
