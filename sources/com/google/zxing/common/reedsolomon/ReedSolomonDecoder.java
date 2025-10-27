package com.google.zxing.common.reedsolomon;

/* loaded from: classes4.dex */
public final class ReedSolomonDecoder {
    private final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    private int[] findErrorLocations(GenericGFPoly genericGFPoly) throws ReedSolomonException {
        int degree = genericGFPoly.getDegree();
        int i2 = 0;
        if (degree == 1) {
            return new int[]{genericGFPoly.getCoefficient(1)};
        }
        int[] iArr = new int[degree];
        for (int i3 = 1; i3 < this.field.getSize() && i2 < degree; i3++) {
            if (genericGFPoly.evaluateAt(i3) == 0) {
                iArr[i2] = this.field.inverse(i3);
                i2++;
            }
        }
        if (i2 == degree) {
            return iArr;
        }
        throw new ReedSolomonException("Error locator degree does not match number of roots");
    }

    private int[] findErrorMagnitudes(GenericGFPoly genericGFPoly, int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            int iInverse = this.field.inverse(iArr[i2]);
            int iMultiply = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (i2 != i3) {
                    int iMultiply2 = this.field.multiply(iArr[i3], iInverse);
                    iMultiply = this.field.multiply(iMultiply, (iMultiply2 & 1) == 0 ? iMultiply2 | 1 : iMultiply2 & (-2));
                }
            }
            iArr2[i2] = this.field.multiply(genericGFPoly.evaluateAt(iInverse), this.field.inverse(iMultiply));
            if (this.field.getGeneratorBase() != 0) {
                iArr2[i2] = this.field.multiply(iArr2[i2], iInverse);
            }
        }
        return iArr2;
    }

    private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly genericGFPoly, GenericGFPoly genericGFPoly2, int i2) throws ReedSolomonException {
        if (genericGFPoly.getDegree() < genericGFPoly2.getDegree()) {
            genericGFPoly2 = genericGFPoly;
            genericGFPoly = genericGFPoly2;
        }
        GenericGFPoly zero = this.field.getZero();
        GenericGFPoly one = this.field.getOne();
        do {
            GenericGFPoly genericGFPoly3 = genericGFPoly2;
            genericGFPoly2 = genericGFPoly;
            genericGFPoly = genericGFPoly3;
            GenericGFPoly genericGFPoly4 = one;
            GenericGFPoly genericGFPoly5 = zero;
            zero = genericGFPoly4;
            if (genericGFPoly.getDegree() < i2 / 2) {
                int coefficient = zero.getCoefficient(0);
                if (coefficient == 0) {
                    throw new ReedSolomonException("sigmaTilde(0) was zero");
                }
                int iInverse = this.field.inverse(coefficient);
                return new GenericGFPoly[]{zero.multiply(iInverse), genericGFPoly.multiply(iInverse)};
            }
            if (genericGFPoly.isZero()) {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
            GenericGFPoly zero2 = this.field.getZero();
            int iInverse2 = this.field.inverse(genericGFPoly.getCoefficient(genericGFPoly.getDegree()));
            while (genericGFPoly2.getDegree() >= genericGFPoly.getDegree() && !genericGFPoly2.isZero()) {
                int degree = genericGFPoly2.getDegree() - genericGFPoly.getDegree();
                int iMultiply = this.field.multiply(genericGFPoly2.getCoefficient(genericGFPoly2.getDegree()), iInverse2);
                zero2 = zero2.addOrSubtract(this.field.buildMonomial(degree, iMultiply));
                genericGFPoly2 = genericGFPoly2.addOrSubtract(genericGFPoly.multiplyByMonomial(degree, iMultiply));
            }
            one = zero2.multiply(zero).addOrSubtract(genericGFPoly5);
        } while (genericGFPoly2.getDegree() < genericGFPoly.getDegree());
        throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
    }

    public void decode(int[] iArr, int i2) throws ReedSolomonException {
        GenericGFPoly genericGFPoly = new GenericGFPoly(this.field, iArr);
        int[] iArr2 = new int[i2];
        boolean z2 = true;
        for (int i3 = 0; i3 < i2; i3++) {
            GenericGF genericGF = this.field;
            int iEvaluateAt = genericGFPoly.evaluateAt(genericGF.exp(genericGF.getGeneratorBase() + i3));
            iArr2[(i2 - 1) - i3] = iEvaluateAt;
            if (iEvaluateAt != 0) {
                z2 = false;
            }
        }
        if (z2) {
            return;
        }
        GenericGFPoly[] genericGFPolyArrRunEuclideanAlgorithm = runEuclideanAlgorithm(this.field.buildMonomial(i2, 1), new GenericGFPoly(this.field, iArr2), i2);
        GenericGFPoly genericGFPoly2 = genericGFPolyArrRunEuclideanAlgorithm[0];
        GenericGFPoly genericGFPoly3 = genericGFPolyArrRunEuclideanAlgorithm[1];
        int[] iArrFindErrorLocations = findErrorLocations(genericGFPoly2);
        int[] iArrFindErrorMagnitudes = findErrorMagnitudes(genericGFPoly3, iArrFindErrorLocations);
        for (int i4 = 0; i4 < iArrFindErrorLocations.length; i4++) {
            int length = (iArr.length - 1) - this.field.log(iArrFindErrorLocations[i4]);
            if (length < 0) {
                throw new ReedSolomonException("Bad error location");
            }
            iArr[length] = GenericGF.addOrSubtract(iArr[length], iArrFindErrorMagnitudes[i4]);
        }
    }
}
