package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;

/* loaded from: classes4.dex */
public final class ErrorCorrection {
    private final ModulusGF field = ModulusGF.PDF417_GF;

    private int[] findErrorLocations(ModulusPoly modulusPoly) throws ChecksumException {
        int degree = modulusPoly.getDegree();
        int[] iArr = new int[degree];
        int i2 = 0;
        for (int i3 = 1; i3 < this.field.getSize() && i2 < degree; i3++) {
            if (modulusPoly.evaluateAt(i3) == 0) {
                iArr[i2] = this.field.inverse(i3);
                i2++;
            }
        }
        if (i2 == degree) {
            return iArr;
        }
        throw ChecksumException.getChecksumInstance();
    }

    private int[] findErrorMagnitudes(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int[] iArr) {
        int degree = modulusPoly2.getDegree();
        int[] iArr2 = new int[degree];
        for (int i2 = 1; i2 <= degree; i2++) {
            iArr2[degree - i2] = this.field.multiply(i2, modulusPoly2.getCoefficient(i2));
        }
        ModulusPoly modulusPoly3 = new ModulusPoly(this.field, iArr2);
        int length = iArr.length;
        int[] iArr3 = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            int iInverse = this.field.inverse(iArr[i3]);
            iArr3[i3] = this.field.multiply(this.field.subtract(0, modulusPoly.evaluateAt(iInverse)), this.field.inverse(modulusPoly3.evaluateAt(iInverse)));
        }
        return iArr3;
    }

    private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int i2) throws ChecksumException {
        if (modulusPoly.getDegree() < modulusPoly2.getDegree()) {
            modulusPoly2 = modulusPoly;
            modulusPoly = modulusPoly2;
        }
        ModulusPoly zero = this.field.getZero();
        ModulusPoly one = this.field.getOne();
        while (true) {
            ModulusPoly modulusPoly3 = modulusPoly2;
            modulusPoly2 = modulusPoly;
            modulusPoly = modulusPoly3;
            ModulusPoly modulusPoly4 = one;
            ModulusPoly modulusPoly5 = zero;
            zero = modulusPoly4;
            if (modulusPoly.getDegree() < i2 / 2) {
                int coefficient = zero.getCoefficient(0);
                if (coefficient == 0) {
                    throw ChecksumException.getChecksumInstance();
                }
                int iInverse = this.field.inverse(coefficient);
                return new ModulusPoly[]{zero.multiply(iInverse), modulusPoly.multiply(iInverse)};
            }
            if (modulusPoly.isZero()) {
                throw ChecksumException.getChecksumInstance();
            }
            ModulusPoly zero2 = this.field.getZero();
            int iInverse2 = this.field.inverse(modulusPoly.getCoefficient(modulusPoly.getDegree()));
            while (modulusPoly2.getDegree() >= modulusPoly.getDegree() && !modulusPoly2.isZero()) {
                int degree = modulusPoly2.getDegree() - modulusPoly.getDegree();
                int iMultiply = this.field.multiply(modulusPoly2.getCoefficient(modulusPoly2.getDegree()), iInverse2);
                zero2 = zero2.add(this.field.buildMonomial(degree, iMultiply));
                modulusPoly2 = modulusPoly2.subtract(modulusPoly.multiplyByMonomial(degree, iMultiply));
            }
            one = zero2.multiply(zero).subtract(modulusPoly5).negative();
        }
    }

    public int decode(int[] iArr, int i2, int[] iArr2) throws ChecksumException {
        ModulusPoly modulusPoly = new ModulusPoly(this.field, iArr);
        int[] iArr3 = new int[i2];
        boolean z2 = false;
        for (int i3 = i2; i3 > 0; i3--) {
            int iEvaluateAt = modulusPoly.evaluateAt(this.field.exp(i3));
            iArr3[i2 - i3] = iEvaluateAt;
            if (iEvaluateAt != 0) {
                z2 = true;
            }
        }
        if (!z2) {
            return 0;
        }
        ModulusPoly one = this.field.getOne();
        for (int i4 : iArr2) {
            int iExp = this.field.exp((iArr.length - 1) - i4);
            ModulusGF modulusGF = this.field;
            one = one.multiply(new ModulusPoly(modulusGF, new int[]{modulusGF.subtract(0, iExp), 1}));
        }
        ModulusPoly[] modulusPolyArrRunEuclideanAlgorithm = runEuclideanAlgorithm(this.field.buildMonomial(i2, 1), new ModulusPoly(this.field, iArr3), i2);
        ModulusPoly modulusPoly2 = modulusPolyArrRunEuclideanAlgorithm[0];
        ModulusPoly modulusPoly3 = modulusPolyArrRunEuclideanAlgorithm[1];
        int[] iArrFindErrorLocations = findErrorLocations(modulusPoly2);
        int[] iArrFindErrorMagnitudes = findErrorMagnitudes(modulusPoly3, modulusPoly2, iArrFindErrorLocations);
        for (int i5 = 0; i5 < iArrFindErrorLocations.length; i5++) {
            int length = (iArr.length - 1) - this.field.log(iArrFindErrorLocations[i5]);
            if (length < 0) {
                throw ChecksumException.getChecksumInstance();
            }
            iArr[length] = this.field.subtract(iArr[length], iArrFindErrorMagnitudes[i5]);
        }
        return iArrFindErrorLocations.length;
    }
}
