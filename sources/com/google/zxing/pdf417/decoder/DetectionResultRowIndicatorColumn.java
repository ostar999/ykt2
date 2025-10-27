package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.ResultPoint;

/* loaded from: classes4.dex */
final class DetectionResultRowIndicatorColumn extends DetectionResultColumn {
    private final boolean isLeft;

    public DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean z2) {
        super(boundingBox);
        this.isLeft = z2;
    }

    private void removeIncorrectCodewords(Codeword[] codewordArr, BarcodeMetadata barcodeMetadata) {
        for (int i2 = 0; i2 < codewordArr.length; i2++) {
            Codeword codeword = codewordArr[i2];
            if (codeword != null) {
                int value = codeword.getValue() % 30;
                int rowNumber = codeword.getRowNumber();
                if (rowNumber > barcodeMetadata.getRowCount()) {
                    codewordArr[i2] = null;
                } else {
                    if (!this.isLeft) {
                        rowNumber += 2;
                    }
                    int i3 = rowNumber % 3;
                    if (i3 != 0) {
                        if (i3 != 1) {
                            if (i3 == 2 && value + 1 != barcodeMetadata.getColumnCount()) {
                                codewordArr[i2] = null;
                            }
                        } else if (value / 3 != barcodeMetadata.getErrorCorrectionLevel() || value % 3 != barcodeMetadata.getRowCountLowerPart()) {
                            codewordArr[i2] = null;
                        }
                    } else if ((value * 3) + 1 != barcodeMetadata.getRowCountUpperPart()) {
                        codewordArr[i2] = null;
                    }
                }
            }
        }
    }

    public int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        Codeword[] codewords = getCodewords();
        setRowNumbers();
        removeIncorrectCodewords(codewords, barcodeMetadata);
        BoundingBox boundingBox = getBoundingBox();
        ResultPoint topLeft = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
        ResultPoint bottomLeft = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
        int iImageRowToCodewordIndex = imageRowToCodewordIndex((int) topLeft.getY());
        int iImageRowToCodewordIndex2 = imageRowToCodewordIndex((int) bottomLeft.getY());
        float rowCount = (iImageRowToCodewordIndex2 - iImageRowToCodewordIndex) / barcodeMetadata.getRowCount();
        int rowNumber = -1;
        int i2 = 0;
        int iMax = 1;
        while (iImageRowToCodewordIndex < iImageRowToCodewordIndex2) {
            Codeword codeword = codewords[iImageRowToCodewordIndex];
            if (codeword != null) {
                int rowNumber2 = codeword.getRowNumber() - rowNumber;
                if (rowNumber2 == 0) {
                    i2++;
                } else {
                    if (rowNumber2 == 1) {
                        iMax = Math.max(iMax, i2);
                        rowNumber = codeword.getRowNumber();
                    } else if (rowNumber2 < 0 || codeword.getRowNumber() >= barcodeMetadata.getRowCount() || rowNumber2 > iImageRowToCodewordIndex) {
                        codewords[iImageRowToCodewordIndex] = null;
                    } else {
                        if (iMax > 2) {
                            rowNumber2 *= iMax - 2;
                        }
                        boolean z2 = rowNumber2 >= iImageRowToCodewordIndex;
                        for (int i3 = 1; i3 <= rowNumber2 && !z2; i3++) {
                            z2 = codewords[iImageRowToCodewordIndex - i3] != null;
                        }
                        if (z2) {
                            codewords[iImageRowToCodewordIndex] = null;
                        } else {
                            rowNumber = codeword.getRowNumber();
                        }
                    }
                    i2 = 1;
                }
            }
            iImageRowToCodewordIndex++;
        }
        return (int) (rowCount + 0.5d);
    }

    public int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        BoundingBox boundingBox = getBoundingBox();
        ResultPoint topLeft = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
        ResultPoint bottomLeft = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
        int iImageRowToCodewordIndex = imageRowToCodewordIndex((int) topLeft.getY());
        int iImageRowToCodewordIndex2 = imageRowToCodewordIndex((int) bottomLeft.getY());
        float rowCount = (iImageRowToCodewordIndex2 - iImageRowToCodewordIndex) / barcodeMetadata.getRowCount();
        Codeword[] codewords = getCodewords();
        int rowNumber = -1;
        int i2 = 0;
        int iMax = 1;
        while (iImageRowToCodewordIndex < iImageRowToCodewordIndex2) {
            Codeword codeword = codewords[iImageRowToCodewordIndex];
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
                int rowNumber2 = codeword.getRowNumber() - rowNumber;
                if (rowNumber2 == 0) {
                    i2++;
                } else {
                    if (rowNumber2 == 1) {
                        iMax = Math.max(iMax, i2);
                        rowNumber = codeword.getRowNumber();
                    } else if (codeword.getRowNumber() >= barcodeMetadata.getRowCount()) {
                        codewords[iImageRowToCodewordIndex] = null;
                    } else {
                        rowNumber = codeword.getRowNumber();
                    }
                    i2 = 1;
                }
            }
            iImageRowToCodewordIndex++;
        }
        return (int) (rowCount + 0.5d);
    }

    public BarcodeMetadata getBarcodeMetadata() {
        Codeword[] codewords = getCodewords();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        BarcodeValue barcodeValue4 = new BarcodeValue();
        for (Codeword codeword : codewords) {
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
                int value = codeword.getValue() % 30;
                int rowNumber = codeword.getRowNumber();
                if (!this.isLeft) {
                    rowNumber += 2;
                }
                int i2 = rowNumber % 3;
                if (i2 == 0) {
                    barcodeValue2.setValue((value * 3) + 1);
                } else if (i2 == 1) {
                    barcodeValue4.setValue(value / 3);
                    barcodeValue3.setValue(value % 3);
                } else if (i2 == 2) {
                    barcodeValue.setValue(value + 1);
                }
            }
        }
        if (barcodeValue.getValue().length == 0 || barcodeValue2.getValue().length == 0 || barcodeValue3.getValue().length == 0 || barcodeValue4.getValue().length == 0 || barcodeValue.getValue()[0] < 1 || barcodeValue2.getValue()[0] + barcodeValue3.getValue()[0] < 3 || barcodeValue2.getValue()[0] + barcodeValue3.getValue()[0] > 90) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = new BarcodeMetadata(barcodeValue.getValue()[0], barcodeValue2.getValue()[0], barcodeValue3.getValue()[0], barcodeValue4.getValue()[0]);
        removeIncorrectCodewords(codewords, barcodeMetadata);
        return barcodeMetadata;
    }

    public int[] getRowHeights() throws FormatException {
        BarcodeMetadata barcodeMetadata = getBarcodeMetadata();
        if (barcodeMetadata == null) {
            return null;
        }
        adjustIncompleteIndicatorColumnRowNumbers(barcodeMetadata);
        int rowCount = barcodeMetadata.getRowCount();
        int[] iArr = new int[rowCount];
        for (Codeword codeword : getCodewords()) {
            if (codeword != null) {
                int rowNumber = codeword.getRowNumber();
                if (rowNumber >= rowCount) {
                    throw FormatException.getFormatInstance();
                }
                iArr[rowNumber] = iArr[rowNumber] + 1;
            }
        }
        return iArr;
    }

    public boolean isLeft() {
        return this.isLeft;
    }

    public void setRowNumbers() {
        for (Codeword codeword : getCodewords()) {
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
            }
        }
    }

    @Override // com.google.zxing.pdf417.decoder.DetectionResultColumn
    public String toString() {
        return "IsLeft: " + this.isLeft + '\n' + super.toString();
    }
}
