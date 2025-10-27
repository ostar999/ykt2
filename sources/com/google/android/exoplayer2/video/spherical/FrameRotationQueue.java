package com.google.android.exoplayer2.video.spherical;

import android.opengl.Matrix;
import com.google.android.exoplayer2.util.TimedValueQueue;

/* loaded from: classes3.dex */
final class FrameRotationQueue {
    private boolean recenterMatrixComputed;
    private final float[] recenterMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];
    private final TimedValueQueue<float[]> rotations = new TimedValueQueue<>();

    public static void computeRecenterMatrix(float[] fArr, float[] fArr2) {
        Matrix.setIdentityM(fArr, 0);
        float f2 = fArr2[10];
        float f3 = fArr2[8];
        float fSqrt = (float) Math.sqrt((f2 * f2) + (f3 * f3));
        float f4 = fArr2[10];
        fArr[0] = f4 / fSqrt;
        float f5 = fArr2[8];
        fArr[2] = f5 / fSqrt;
        fArr[8] = (-f5) / fSqrt;
        fArr[10] = f4 / fSqrt;
    }

    private static void getRotationMatrixFromAngleAxis(float[] fArr, float[] fArr2) {
        float f2 = fArr2[0];
        float f3 = -fArr2[1];
        float f4 = -fArr2[2];
        float length = Matrix.length(f2, f3, f4);
        if (length != 0.0f) {
            Matrix.setRotateM(fArr, 0, (float) Math.toDegrees(length), f2 / length, f3 / length, f4 / length);
        } else {
            Matrix.setIdentityM(fArr, 0);
        }
    }

    public boolean pollRotationMatrix(float[] fArr, long j2) {
        float[] fArrPollFloor = this.rotations.pollFloor(j2);
        if (fArrPollFloor == null) {
            return false;
        }
        getRotationMatrixFromAngleAxis(this.rotationMatrix, fArrPollFloor);
        if (!this.recenterMatrixComputed) {
            computeRecenterMatrix(this.recenterMatrix, this.rotationMatrix);
            this.recenterMatrixComputed = true;
        }
        Matrix.multiplyMM(fArr, 0, this.recenterMatrix, 0, this.rotationMatrix, 0);
        return true;
    }

    public void reset() {
        this.rotations.clear();
        this.recenterMatrixComputed = false;
    }

    public void setRotation(long j2, float[] fArr) {
        this.rotations.add(j2, fArr);
    }
}
