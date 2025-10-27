package com.google.zxing.pdf417.decoder.ec;

/* loaded from: classes4.dex */
final class ModulusPoly {
    private final int[] coefficients;
    private final ModulusGF field;

    public ModulusPoly(ModulusGF modulusGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = modulusGF;
        int length = iArr.length;
        int i2 = 1;
        if (length <= 1 || iArr[0] != 0) {
            this.coefficients = iArr;
            return;
        }
        while (i2 < length && iArr[i2] == 0) {
            i2++;
        }
        if (i2 == length) {
            this.coefficients = modulusGF.getZero().coefficients;
            return;
        }
        int[] iArr2 = new int[length - i2];
        this.coefficients = iArr2;
        System.arraycopy(iArr, i2, iArr2, 0, iArr2.length);
    }

    public ModulusPoly add(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (isZero()) {
            return modulusPoly;
        }
        if (modulusPoly.isZero()) {
            return this;
        }
        int[] iArr = this.coefficients;
        int[] iArr2 = modulusPoly.coefficients;
        if (iArr.length <= iArr2.length) {
            iArr = iArr2;
            iArr2 = iArr;
        }
        int[] iArr3 = new int[iArr.length];
        int length = iArr.length - iArr2.length;
        System.arraycopy(iArr, 0, iArr3, 0, length);
        for (int i2 = length; i2 < iArr.length; i2++) {
            iArr3[i2] = this.field.add(iArr2[i2 - length], iArr[i2]);
        }
        return new ModulusPoly(this.field, iArr3);
    }

    public ModulusPoly[] divide(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (modulusPoly.isZero()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        ModulusPoly zero = this.field.getZero();
        int iInverse = this.field.inverse(modulusPoly.getCoefficient(modulusPoly.getDegree()));
        ModulusPoly modulusPolySubtract = this;
        while (modulusPolySubtract.getDegree() >= modulusPoly.getDegree() && !modulusPolySubtract.isZero()) {
            int degree = modulusPolySubtract.getDegree() - modulusPoly.getDegree();
            int iMultiply = this.field.multiply(modulusPolySubtract.getCoefficient(modulusPolySubtract.getDegree()), iInverse);
            ModulusPoly modulusPolyMultiplyByMonomial = modulusPoly.multiplyByMonomial(degree, iMultiply);
            zero = zero.add(this.field.buildMonomial(degree, iMultiply));
            modulusPolySubtract = modulusPolySubtract.subtract(modulusPolyMultiplyByMonomial);
        }
        return new ModulusPoly[]{zero, modulusPolySubtract};
    }

    public int evaluateAt(int i2) {
        if (i2 == 0) {
            return getCoefficient(0);
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        if (i2 != 1) {
            int iAdd = iArr[0];
            for (int i3 = 1; i3 < length; i3++) {
                ModulusGF modulusGF = this.field;
                iAdd = modulusGF.add(modulusGF.multiply(i2, iAdd), this.coefficients[i3]);
            }
            return iAdd;
        }
        int iAdd2 = 0;
        for (int i4 : iArr) {
            iAdd2 = this.field.add(iAdd2, i4);
        }
        return iAdd2;
    }

    public int getCoefficient(int i2) {
        return this.coefficients[(r0.length - 1) - i2];
    }

    public int[] getCoefficients() {
        return this.coefficients;
    }

    public int getDegree() {
        return this.coefficients.length - 1;
    }

    public boolean isZero() {
        return this.coefficients[0] == 0;
    }

    public ModulusPoly multiply(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (isZero() || modulusPoly.isZero()) {
            return this.field.getZero();
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = modulusPoly.coefficients;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            for (int i4 = 0; i4 < length2; i4++) {
                int i5 = i2 + i4;
                ModulusGF modulusGF = this.field;
                iArr3[i5] = modulusGF.add(iArr3[i5], modulusGF.multiply(i3, iArr2[i4]));
            }
        }
        return new ModulusPoly(this.field, iArr3);
    }

    public ModulusPoly multiplyByMonomial(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (i3 == 0) {
            return this.field.getZero();
        }
        int length = this.coefficients.length;
        int[] iArr = new int[i2 + length];
        for (int i4 = 0; i4 < length; i4++) {
            iArr[i4] = this.field.multiply(this.coefficients[i4], i3);
        }
        return new ModulusPoly(this.field, iArr);
    }

    public ModulusPoly negative() {
        int length = this.coefficients.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = this.field.subtract(0, this.coefficients[i2]);
        }
        return new ModulusPoly(this.field, iArr);
    }

    public ModulusPoly subtract(ModulusPoly modulusPoly) {
        if (this.field.equals(modulusPoly.field)) {
            return modulusPoly.isZero() ? this : add(modulusPoly.negative());
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getDegree() * 8);
        for (int degree = getDegree(); degree >= 0; degree--) {
            int coefficient = getCoefficient(degree);
            if (coefficient != 0) {
                if (coefficient < 0) {
                    sb.append(" - ");
                    coefficient = -coefficient;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (degree == 0 || coefficient != 1) {
                    sb.append(coefficient);
                }
                if (degree != 0) {
                    if (degree == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(degree);
                    }
                }
            }
        }
        return sb.toString();
    }

    public ModulusPoly multiply(int i2) {
        if (i2 == 0) {
            return this.field.getZero();
        }
        if (i2 == 1) {
            return this;
        }
        int length = this.coefficients.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.field.multiply(this.coefficients[i3], i2);
        }
        return new ModulusPoly(this.field, iArr);
    }
}
