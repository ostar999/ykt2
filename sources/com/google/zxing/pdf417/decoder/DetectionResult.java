package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

/* loaded from: classes4.dex */
final class DetectionResult {
    private static final int ADJUST_ROW_NUMBER_SKIP = 2;
    private final int barcodeColumnCount;
    private final BarcodeMetadata barcodeMetadata;
    private BoundingBox boundingBox;
    private final DetectionResultColumn[] detectionResultColumns;

    public DetectionResult(BarcodeMetadata barcodeMetadata, BoundingBox boundingBox) {
        this.barcodeMetadata = barcodeMetadata;
        int columnCount = barcodeMetadata.getColumnCount();
        this.barcodeColumnCount = columnCount;
        this.boundingBox = boundingBox;
        this.detectionResultColumns = new DetectionResultColumn[columnCount + 2];
    }

    private void adjustIndicatorColumnRowNumbers(DetectionResultColumn detectionResultColumn) {
        if (detectionResultColumn != null) {
            ((DetectionResultRowIndicatorColumn) detectionResultColumn).adjustCompleteIndicatorColumnRowNumbers(this.barcodeMetadata);
        }
    }

    private static boolean adjustRowNumber(Codeword codeword, Codeword codeword2) {
        if (codeword2 == null || !codeword2.hasValidRowNumber() || codeword2.getBucket() != codeword.getBucket()) {
            return false;
        }
        codeword.setRowNumber(codeword2.getRowNumber());
        return true;
    }

    private static int adjustRowNumberIfValid(int i2, int i3, Codeword codeword) {
        if (codeword == null || codeword.hasValidRowNumber()) {
            return i3;
        }
        if (!codeword.isValidRowNumber(i2)) {
            return i3 + 1;
        }
        codeword.setRowNumber(i2);
        return 0;
    }

    private int adjustRowNumbers() {
        int iAdjustRowNumbersByRow = adjustRowNumbersByRow();
        if (iAdjustRowNumbersByRow == 0) {
            return 0;
        }
        for (int i2 = 1; i2 < this.barcodeColumnCount + 1; i2++) {
            Codeword[] codewords = this.detectionResultColumns[i2].getCodewords();
            for (int i3 = 0; i3 < codewords.length; i3++) {
                Codeword codeword = codewords[i3];
                if (codeword != null && !codeword.hasValidRowNumber()) {
                    adjustRowNumbers(i2, i3, codewords);
                }
            }
        }
        return iAdjustRowNumbersByRow;
    }

    private int adjustRowNumbersByRow() {
        adjustRowNumbersFromBothRI();
        return adjustRowNumbersFromLRI() + adjustRowNumbersFromRRI();
    }

    private void adjustRowNumbersFromBothRI() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        DetectionResultColumn detectionResultColumn = detectionResultColumnArr[0];
        if (detectionResultColumn == null || detectionResultColumnArr[this.barcodeColumnCount + 1] == null) {
            return;
        }
        Codeword[] codewords = detectionResultColumn.getCodewords();
        Codeword[] codewords2 = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords();
        for (int i2 = 0; i2 < codewords.length; i2++) {
            Codeword codeword = codewords[i2];
            if (codeword != null && codewords2[i2] != null && codeword.getRowNumber() == codewords2[i2].getRowNumber()) {
                for (int i3 = 1; i3 <= this.barcodeColumnCount; i3++) {
                    Codeword codeword2 = this.detectionResultColumns[i3].getCodewords()[i2];
                    if (codeword2 != null) {
                        codeword2.setRowNumber(codewords[i2].getRowNumber());
                        if (!codeword2.hasValidRowNumber()) {
                            this.detectionResultColumns[i3].getCodewords()[i2] = null;
                        }
                    }
                }
            }
        }
    }

    private int adjustRowNumbersFromLRI() {
        DetectionResultColumn detectionResultColumn = this.detectionResultColumns[0];
        if (detectionResultColumn == null) {
            return 0;
        }
        Codeword[] codewords = detectionResultColumn.getCodewords();
        int i2 = 0;
        for (int i3 = 0; i3 < codewords.length; i3++) {
            Codeword codeword = codewords[i3];
            if (codeword != null) {
                int rowNumber = codeword.getRowNumber();
                int iAdjustRowNumberIfValid = 0;
                for (int i4 = 1; i4 < this.barcodeColumnCount + 1 && iAdjustRowNumberIfValid < 2; i4++) {
                    Codeword codeword2 = this.detectionResultColumns[i4].getCodewords()[i3];
                    if (codeword2 != null) {
                        iAdjustRowNumberIfValid = adjustRowNumberIfValid(rowNumber, iAdjustRowNumberIfValid, codeword2);
                        if (!codeword2.hasValidRowNumber()) {
                            i2++;
                        }
                    }
                }
            }
        }
        return i2;
    }

    private int adjustRowNumbersFromRRI() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        int i2 = this.barcodeColumnCount;
        if (detectionResultColumnArr[i2 + 1] == null) {
            return 0;
        }
        Codeword[] codewords = detectionResultColumnArr[i2 + 1].getCodewords();
        int i3 = 0;
        for (int i4 = 0; i4 < codewords.length; i4++) {
            Codeword codeword = codewords[i4];
            if (codeword != null) {
                int rowNumber = codeword.getRowNumber();
                int iAdjustRowNumberIfValid = 0;
                for (int i5 = this.barcodeColumnCount + 1; i5 > 0 && iAdjustRowNumberIfValid < 2; i5--) {
                    Codeword codeword2 = this.detectionResultColumns[i5].getCodewords()[i4];
                    if (codeword2 != null) {
                        iAdjustRowNumberIfValid = adjustRowNumberIfValid(rowNumber, iAdjustRowNumberIfValid, codeword2);
                        if (!codeword2.hasValidRowNumber()) {
                            i3++;
                        }
                    }
                }
            }
        }
        return i3;
    }

    public int getBarcodeColumnCount() {
        return this.barcodeColumnCount;
    }

    public int getBarcodeECLevel() {
        return this.barcodeMetadata.getErrorCorrectionLevel();
    }

    public int getBarcodeRowCount() {
        return this.barcodeMetadata.getRowCount();
    }

    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public DetectionResultColumn getDetectionResultColumn(int i2) {
        return this.detectionResultColumns[i2];
    }

    public DetectionResultColumn[] getDetectionResultColumns() {
        adjustIndicatorColumnRowNumbers(this.detectionResultColumns[0]);
        adjustIndicatorColumnRowNumbers(this.detectionResultColumns[this.barcodeColumnCount + 1]);
        int i2 = 928;
        while (true) {
            int iAdjustRowNumbers = adjustRowNumbers();
            if (iAdjustRowNumbers <= 0 || iAdjustRowNumbers >= i2) {
                break;
            }
            i2 = iAdjustRowNumbers;
        }
        return this.detectionResultColumns;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public void setDetectionResultColumn(int i2, DetectionResultColumn detectionResultColumn) {
        this.detectionResultColumns[i2] = detectionResultColumn;
    }

    public String toString() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        DetectionResultColumn detectionResultColumn = detectionResultColumnArr[0];
        if (detectionResultColumn == null) {
            detectionResultColumn = detectionResultColumnArr[this.barcodeColumnCount + 1];
        }
        Formatter formatter = new Formatter();
        for (int i2 = 0; i2 < detectionResultColumn.getCodewords().length; i2++) {
            formatter.format("CW %3d:", Integer.valueOf(i2));
            for (int i3 = 0; i3 < this.barcodeColumnCount + 2; i3++) {
                DetectionResultColumn detectionResultColumn2 = this.detectionResultColumns[i3];
                if (detectionResultColumn2 == null) {
                    formatter.format("    |   ", new Object[0]);
                } else {
                    Codeword codeword = detectionResultColumn2.getCodewords()[i2];
                    if (codeword == null) {
                        formatter.format("    |   ", new Object[0]);
                    } else {
                        formatter.format(" %3d|%3d", Integer.valueOf(codeword.getRowNumber()), Integer.valueOf(codeword.getValue()));
                    }
                }
            }
            formatter.format("%n", new Object[0]);
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    private void adjustRowNumbers(int i2, int i3, Codeword[] codewordArr) {
        Codeword codeword = codewordArr[i3];
        Codeword[] codewords = this.detectionResultColumns[i2 - 1].getCodewords();
        DetectionResultColumn detectionResultColumn = this.detectionResultColumns[i2 + 1];
        Codeword[] codewords2 = detectionResultColumn != null ? detectionResultColumn.getCodewords() : codewords;
        Codeword[] codewordArr2 = new Codeword[14];
        codewordArr2[2] = codewords[i3];
        codewordArr2[3] = codewords2[i3];
        if (i3 > 0) {
            int i4 = i3 - 1;
            codewordArr2[0] = codewordArr[i4];
            codewordArr2[4] = codewords[i4];
            codewordArr2[5] = codewords2[i4];
        }
        if (i3 > 1) {
            int i5 = i3 - 2;
            codewordArr2[8] = codewordArr[i5];
            codewordArr2[10] = codewords[i5];
            codewordArr2[11] = codewords2[i5];
        }
        if (i3 < codewordArr.length - 1) {
            int i6 = i3 + 1;
            codewordArr2[1] = codewordArr[i6];
            codewordArr2[6] = codewords[i6];
            codewordArr2[7] = codewords2[i6];
        }
        if (i3 < codewordArr.length - 2) {
            int i7 = i3 + 2;
            codewordArr2[9] = codewordArr[i7];
            codewordArr2[12] = codewords[i7];
            codewordArr2[13] = codewords2[i7];
        }
        for (int i8 = 0; i8 < 14 && !adjustRowNumber(codeword, codewordArr2[i8]); i8++) {
        }
    }
}
