package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class ArcCurveFit extends CurveFit {
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;
    Arc[] mArcs;
    private boolean mExtrapolate = true;
    private final double[] mTime;

    public static class Arc {
        private static final double EPSILON = 0.001d;
        private static final String TAG = "Arc";
        private static double[] ourPercent = new double[91];
        boolean linear;
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        public Arc(int i2, double d3, double d4, double d5, double d6, double d7, double d8) {
            this.linear = false;
            this.mVertical = i2 == 1;
            this.mTime1 = d3;
            this.mTime2 = d4;
            this.mOneOverDeltaTime = 1.0d / (d4 - d3);
            if (3 == i2) {
                this.linear = true;
            }
            double d9 = d7 - d5;
            double d10 = d8 - d6;
            if (!this.linear && Math.abs(d9) >= EPSILON && Math.abs(d10) >= EPSILON) {
                this.mLut = new double[101];
                boolean z2 = this.mVertical;
                this.mEllipseA = d9 * (z2 ? -1 : 1);
                this.mEllipseB = d10 * (z2 ? 1 : -1);
                this.mEllipseCenterX = z2 ? d7 : d5;
                this.mEllipseCenterY = z2 ? d6 : d8;
                buildTable(d5, d6, d7, d8);
                this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                return;
            }
            this.linear = true;
            this.mX1 = d5;
            this.mX2 = d7;
            this.mY1 = d6;
            this.mY2 = d8;
            double dHypot = Math.hypot(d10, d9);
            this.mArcDistance = dHypot;
            this.mArcVelocity = dHypot * this.mOneOverDeltaTime;
            double d11 = this.mTime2;
            double d12 = this.mTime1;
            this.mEllipseCenterX = d9 / (d11 - d12);
            this.mEllipseCenterY = d10 / (d11 - d12);
        }

        private void buildTable(double d3, double d4, double d5, double d6) {
            double dHypot;
            double d7 = d5 - d3;
            double d8 = d4 - d6;
            int i2 = 0;
            double d9 = 0.0d;
            double d10 = 0.0d;
            double d11 = 0.0d;
            while (true) {
                if (i2 >= ourPercent.length) {
                    break;
                }
                double d12 = d9;
                double radians = Math.toRadians((i2 * 90.0d) / (r15.length - 1));
                double dSin = Math.sin(radians) * d7;
                double dCos = Math.cos(radians) * d8;
                if (i2 > 0) {
                    dHypot = Math.hypot(dSin - d10, dCos - d11) + d12;
                    ourPercent[i2] = dHypot;
                } else {
                    dHypot = d12;
                }
                i2++;
                d11 = dCos;
                d9 = dHypot;
                d10 = dSin;
            }
            double d13 = d9;
            this.mArcDistance = d13;
            int i3 = 0;
            while (true) {
                double[] dArr = ourPercent;
                if (i3 >= dArr.length) {
                    break;
                }
                dArr[i3] = dArr[i3] / d13;
                i3++;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= this.mLut.length) {
                    return;
                }
                double length = i4 / (r1.length - 1);
                int iBinarySearch = Arrays.binarySearch(ourPercent, length);
                if (iBinarySearch >= 0) {
                    this.mLut[i4] = iBinarySearch / (ourPercent.length - 1);
                } else if (iBinarySearch == -1) {
                    this.mLut[i4] = 0.0d;
                } else {
                    int i5 = -iBinarySearch;
                    int i6 = i5 - 2;
                    double[] dArr2 = ourPercent;
                    double d14 = dArr2[i6];
                    this.mLut[i4] = (i6 + ((length - d14) / (dArr2[i5 - 1] - d14))) / (dArr2.length - 1);
                }
                i4++;
            }
        }

        public double getDX() {
            double d3 = this.mEllipseA * this.mTmpCosAngle;
            double dHypot = this.mArcVelocity / Math.hypot(d3, (-this.mEllipseB) * this.mTmpSinAngle);
            if (this.mVertical) {
                d3 = -d3;
            }
            return d3 * dHypot;
        }

        public double getDY() {
            double d3 = this.mEllipseA * this.mTmpCosAngle;
            double d4 = (-this.mEllipseB) * this.mTmpSinAngle;
            double dHypot = this.mArcVelocity / Math.hypot(d3, d4);
            return this.mVertical ? (-d4) * dHypot : d4 * dHypot;
        }

        public double getLinearDX(double d3) {
            return this.mEllipseCenterX;
        }

        public double getLinearDY(double d3) {
            return this.mEllipseCenterY;
        }

        public double getLinearX(double d3) {
            double d4 = (d3 - this.mTime1) * this.mOneOverDeltaTime;
            double d5 = this.mX1;
            return d5 + (d4 * (this.mX2 - d5));
        }

        public double getLinearY(double d3) {
            double d4 = (d3 - this.mTime1) * this.mOneOverDeltaTime;
            double d5 = this.mY1;
            return d5 + (d4 * (this.mY2 - d5));
        }

        public double getX() {
            return this.mEllipseCenterX + (this.mEllipseA * this.mTmpSinAngle);
        }

        public double getY() {
            return this.mEllipseCenterY + (this.mEllipseB * this.mTmpCosAngle);
        }

        public double lookup(double d3) {
            if (d3 <= 0.0d) {
                return 0.0d;
            }
            if (d3 >= 1.0d) {
                return 1.0d;
            }
            double[] dArr = this.mLut;
            double length = d3 * (dArr.length - 1);
            int i2 = (int) length;
            double d4 = length - i2;
            double d5 = dArr[i2];
            return d5 + (d4 * (dArr[i2 + 1] - d5));
        }

        public void setPoint(double d3) {
            double dLookup = lookup((this.mVertical ? this.mTime2 - d3 : d3 - this.mTime1) * this.mOneOverDeltaTime) * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(dLookup);
            this.mTmpCosAngle = Math.cos(dLookup);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ArcCurveFit(int[] r25, double[] r26, double[][] r27) {
        /*
            r24 = this;
            r0 = r24
            r1 = r26
            r24.<init>()
            r2 = 1
            r0.mExtrapolate = r2
            r0.mTime = r1
            int r3 = r1.length
            int r3 = r3 - r2
            androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc[] r3 = new androidx.constraintlayout.core.motion.utils.ArcCurveFit.Arc[r3]
            r0.mArcs = r3
            r3 = 0
            r5 = r2
            r6 = r5
            r4 = r3
        L16:
            androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc[] r7 = r0.mArcs
            int r8 = r7.length
            if (r4 >= r8) goto L4f
            r8 = r25[r4]
            r9 = 3
            if (r8 == 0) goto L2f
            if (r8 == r2) goto L2c
            r10 = 2
            if (r8 == r10) goto L2a
            if (r8 == r9) goto L28
            goto L30
        L28:
            if (r5 != r2) goto L2c
        L2a:
            r5 = r10
            goto L2d
        L2c:
            r5 = r2
        L2d:
            r6 = r5
            goto L30
        L2f:
            r6 = r9
        L30:
            androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc r22 = new androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc
            r10 = r1[r4]
            int r23 = r4 + 1
            r12 = r1[r23]
            r8 = r27[r4]
            r14 = r8[r3]
            r16 = r8[r2]
            r8 = r27[r23]
            r18 = r8[r3]
            r20 = r8[r2]
            r8 = r22
            r9 = r6
            r8.<init>(r9, r10, r12, r14, r16, r18, r20)
            r7[r4] = r22
            r4 = r23
            goto L16
        L4f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.ArcCurveFit.<init>(int[], double[], double[][]):void");
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double d3, double[] dArr) {
        if (this.mExtrapolate) {
            Arc[] arcArr = this.mArcs;
            Arc arc = arcArr[0];
            double d4 = arc.mTime1;
            if (d3 < d4) {
                double d5 = d3 - d4;
                if (arc.linear) {
                    dArr[0] = arc.getLinearX(d4) + (this.mArcs[0].getLinearDX(d4) * d5);
                    dArr[1] = this.mArcs[0].getLinearY(d4) + (d5 * this.mArcs[0].getLinearDY(d4));
                    return;
                } else {
                    arc.setPoint(d4);
                    dArr[0] = this.mArcs[0].getX() + (this.mArcs[0].getDX() * d5);
                    dArr[1] = this.mArcs[0].getY() + (d5 * this.mArcs[0].getDY());
                    return;
                }
            }
            if (d3 > arcArr[arcArr.length - 1].mTime2) {
                double d6 = arcArr[arcArr.length - 1].mTime2;
                double d7 = d3 - d6;
                int length = arcArr.length - 1;
                Arc arc2 = arcArr[length];
                if (arc2.linear) {
                    dArr[0] = arc2.getLinearX(d6) + (this.mArcs[length].getLinearDX(d6) * d7);
                    dArr[1] = this.mArcs[length].getLinearY(d6) + (d7 * this.mArcs[length].getLinearDY(d6));
                    return;
                } else {
                    arc2.setPoint(d3);
                    dArr[0] = this.mArcs[length].getX() + (this.mArcs[length].getDX() * d7);
                    dArr[1] = this.mArcs[length].getY() + (d7 * this.mArcs[length].getDY());
                    return;
                }
            }
        } else {
            Arc[] arcArr2 = this.mArcs;
            double d8 = arcArr2[0].mTime1;
            if (d3 < d8) {
                d3 = d8;
            }
            if (d3 > arcArr2[arcArr2.length - 1].mTime2) {
                d3 = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i2 >= arcArr3.length) {
                return;
            }
            Arc arc3 = arcArr3[i2];
            if (d3 <= arc3.mTime2) {
                if (arc3.linear) {
                    dArr[0] = arc3.getLinearX(d3);
                    dArr[1] = this.mArcs[i2].getLinearY(d3);
                    return;
                } else {
                    arc3.setPoint(d3);
                    dArr[0] = this.mArcs[i2].getX();
                    dArr[1] = this.mArcs[i2].getY();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getSlope(double d3, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        double d4 = arcArr[0].mTime1;
        if (d3 < d4) {
            d3 = d4;
        } else if (d3 > arcArr[arcArr.length - 1].mTime2) {
            d3 = arcArr[arcArr.length - 1].mTime2;
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i2 >= arcArr2.length) {
                return;
            }
            Arc arc = arcArr2[i2];
            if (d3 <= arc.mTime2) {
                if (arc.linear) {
                    dArr[0] = arc.getLinearDX(d3);
                    dArr[1] = this.mArcs[i2].getLinearDY(d3);
                    return;
                } else {
                    arc.setPoint(d3);
                    dArr[0] = this.mArcs[i2].getDX();
                    dArr[1] = this.mArcs[i2].getDY();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mTime;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getSlope(double d3, int i2) {
        Arc[] arcArr = this.mArcs;
        int i3 = 0;
        double d4 = arcArr[0].mTime1;
        if (d3 < d4) {
            d3 = d4;
        }
        if (d3 > arcArr[arcArr.length - 1].mTime2) {
            d3 = arcArr[arcArr.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i3 >= arcArr2.length) {
                return Double.NaN;
            }
            Arc arc = arcArr2[i3];
            if (d3 <= arc.mTime2) {
                if (arc.linear) {
                    if (i2 == 0) {
                        return arc.getLinearDX(d3);
                    }
                    return arc.getLinearDY(d3);
                }
                arc.setPoint(d3);
                if (i2 == 0) {
                    return this.mArcs[i3].getDX();
                }
                return this.mArcs[i3].getDY();
            }
            i3++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double d3, float[] fArr) {
        if (this.mExtrapolate) {
            Arc[] arcArr = this.mArcs;
            Arc arc = arcArr[0];
            double d4 = arc.mTime1;
            if (d3 < d4) {
                double d5 = d3 - d4;
                if (arc.linear) {
                    fArr[0] = (float) (arc.getLinearX(d4) + (this.mArcs[0].getLinearDX(d4) * d5));
                    fArr[1] = (float) (this.mArcs[0].getLinearY(d4) + (d5 * this.mArcs[0].getLinearDY(d4)));
                    return;
                } else {
                    arc.setPoint(d4);
                    fArr[0] = (float) (this.mArcs[0].getX() + (this.mArcs[0].getDX() * d5));
                    fArr[1] = (float) (this.mArcs[0].getY() + (d5 * this.mArcs[0].getDY()));
                    return;
                }
            }
            if (d3 > arcArr[arcArr.length - 1].mTime2) {
                double d6 = arcArr[arcArr.length - 1].mTime2;
                double d7 = d3 - d6;
                int length = arcArr.length - 1;
                Arc arc2 = arcArr[length];
                if (arc2.linear) {
                    fArr[0] = (float) (arc2.getLinearX(d6) + (this.mArcs[length].getLinearDX(d6) * d7));
                    fArr[1] = (float) (this.mArcs[length].getLinearY(d6) + (d7 * this.mArcs[length].getLinearDY(d6)));
                    return;
                } else {
                    arc2.setPoint(d3);
                    fArr[0] = (float) this.mArcs[length].getX();
                    fArr[1] = (float) this.mArcs[length].getY();
                    return;
                }
            }
        } else {
            Arc[] arcArr2 = this.mArcs;
            double d8 = arcArr2[0].mTime1;
            if (d3 < d8) {
                d3 = d8;
            } else if (d3 > arcArr2[arcArr2.length - 1].mTime2) {
                d3 = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i2 >= arcArr3.length) {
                return;
            }
            Arc arc3 = arcArr3[i2];
            if (d3 <= arc3.mTime2) {
                if (arc3.linear) {
                    fArr[0] = (float) arc3.getLinearX(d3);
                    fArr[1] = (float) this.mArcs[i2].getLinearY(d3);
                    return;
                } else {
                    arc3.setPoint(d3);
                    fArr[0] = (float) this.mArcs[i2].getX();
                    fArr[1] = (float) this.mArcs[i2].getY();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getPos(double d3, int i2) {
        double linearY;
        double linearDY;
        double y2;
        double dy;
        double linearY2;
        double linearDY2;
        int i3 = 0;
        if (this.mExtrapolate) {
            Arc[] arcArr = this.mArcs;
            Arc arc = arcArr[0];
            double d4 = arc.mTime1;
            if (d3 < d4) {
                double d5 = d3 - d4;
                if (arc.linear) {
                    if (i2 == 0) {
                        linearY2 = arc.getLinearX(d4);
                        linearDY2 = this.mArcs[0].getLinearDX(d4);
                    } else {
                        linearY2 = arc.getLinearY(d4);
                        linearDY2 = this.mArcs[0].getLinearDY(d4);
                    }
                    return linearY2 + (d5 * linearDY2);
                }
                arc.setPoint(d4);
                if (i2 == 0) {
                    y2 = this.mArcs[0].getX();
                    dy = this.mArcs[0].getDX();
                } else {
                    y2 = this.mArcs[0].getY();
                    dy = this.mArcs[0].getDY();
                }
                return y2 + (d5 * dy);
            }
            if (d3 > arcArr[arcArr.length - 1].mTime2) {
                double d6 = arcArr[arcArr.length - 1].mTime2;
                double d7 = d3 - d6;
                int length = arcArr.length - 1;
                if (i2 == 0) {
                    linearY = arcArr[length].getLinearX(d6);
                    linearDY = this.mArcs[length].getLinearDX(d6);
                } else {
                    linearY = arcArr[length].getLinearY(d6);
                    linearDY = this.mArcs[length].getLinearDY(d6);
                }
                return linearY + (d7 * linearDY);
            }
        } else {
            Arc[] arcArr2 = this.mArcs;
            double d8 = arcArr2[0].mTime1;
            if (d3 < d8) {
                d3 = d8;
            } else if (d3 > arcArr2[arcArr2.length - 1].mTime2) {
                d3 = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i3 >= arcArr3.length) {
                return Double.NaN;
            }
            Arc arc2 = arcArr3[i3];
            if (d3 <= arc2.mTime2) {
                if (arc2.linear) {
                    if (i2 == 0) {
                        return arc2.getLinearX(d3);
                    }
                    return arc2.getLinearY(d3);
                }
                arc2.setPoint(d3);
                if (i2 == 0) {
                    return this.mArcs[i3].getX();
                }
                return this.mArcs[i3].getY();
            }
            i3++;
        }
    }
}
