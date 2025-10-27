package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class SpringStopEngine implements StopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;
    private float mV;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private int mBoundaryMode = 0;

    private void compute(double d3) {
        double d4 = this.mStiffness;
        double d5 = this.mDamping;
        int iSqrt = (int) ((9.0d / ((Math.sqrt(d4 / this.mMass) * d3) * 4.0d)) + 1.0d);
        double d6 = d3 / iSqrt;
        int i2 = 0;
        while (i2 < iSqrt) {
            float f2 = this.mPos;
            double d7 = this.mTargetPos;
            float f3 = this.mV;
            double d8 = d4;
            double d9 = ((-d4) * (f2 - d7)) - (f3 * d5);
            float f4 = this.mMass;
            double d10 = d5;
            double d11 = f3 + (((d9 / f4) * d6) / 2.0d);
            double d12 = ((((-((f2 + ((d6 * d11) / 2.0d)) - d7)) * d8) - (d11 * d10)) / f4) * d6;
            float f5 = (float) (f3 + d12);
            this.mV = f5;
            float f6 = (float) (f2 + ((f3 + (d12 / 2.0d)) * d6));
            this.mPos = f6;
            int i3 = this.mBoundaryMode;
            if (i3 > 0) {
                if (f6 < 0.0f && (i3 & 1) == 1) {
                    this.mPos = -f6;
                    this.mV = -f5;
                }
                float f7 = this.mPos;
                if (f7 > 1.0f && (i3 & 2) == 2) {
                    this.mPos = 2.0f - f7;
                    this.mV = -this.mV;
                }
            }
            i2++;
            d4 = d8;
            d5 = d10;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public String debug(String str, float f2) {
        return null;
    }

    public float getAcceleration() {
        return ((float) (((-this.mStiffness) * (this.mPos - this.mTargetPos)) - (this.mDamping * this.mV))) / this.mMass;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getInterpolation(float f2) {
        compute(f2 - this.mLastTime);
        this.mLastTime = f2;
        return this.mPos;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity() {
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity(float f2) {
        return this.mV;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public boolean isStopped() {
        double d3 = this.mPos - this.mTargetPos;
        double d4 = this.mStiffness;
        double d5 = this.mV;
        return Math.sqrt((((d5 * d5) * ((double) this.mMass)) + ((d4 * d3) * d3)) / d4) <= ((double) this.mStopThreshold);
    }

    public void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str2 = ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName() + "() ";
        System.out.println(str2 + str);
    }

    public void springConfig(float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i2) {
        this.mTargetPos = f3;
        this.mDamping = f7;
        this.mInitialized = false;
        this.mPos = f2;
        this.mLastVelocity = f4;
        this.mStiffness = f6;
        this.mMass = f5;
        this.mStopThreshold = f8;
        this.mBoundaryMode = i2;
        this.mLastTime = 0.0f;
    }
}
