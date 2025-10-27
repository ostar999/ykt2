package net.polyv.danmaku.danmaku.model;

import java.lang.reflect.Array;

/* loaded from: classes9.dex */
public class SpecialDanmaku extends BaseDanmaku {
    public long alphaDuration;
    public int beginAlpha;
    public float beginX;
    public float beginY;
    public int deltaAlpha;
    public float deltaX;
    public float deltaY;
    public int endAlpha;
    public float endX;
    public float endY;
    public LinePath[] linePaths;
    private ScaleFactor mScaleFactor;
    private int mScaleFactorChangedFlag;
    public float pivotX;
    public float pivotY;
    public float rotateX;
    public float rotateZ;
    public long translationDuration;
    public long translationStartDelay;
    private int mCurrentWidth = 0;
    private int mCurrentHeight = 0;
    public boolean isQuadraticEaseOut = false;
    private float[] currStateValues = new float[4];

    public class LinePath {
        public long beginTime;
        float delatX;
        float deltaY;
        public long duration;
        public long endTime;
        Point pBegin;
        Point pEnd;

        public LinePath() {
        }

        public float[] getBeginPoint() {
            Point point = this.pBegin;
            return new float[]{point.f27746x, point.f27747y};
        }

        public float getDistance() {
            return this.pEnd.getDistance(this.pBegin);
        }

        public float[] getEndPoint() {
            Point point = this.pEnd;
            return new float[]{point.f27746x, point.f27747y};
        }

        public void setPoints(Point point, Point point2) {
            this.pBegin = point;
            this.pEnd = point2;
            this.delatX = point2.f27746x - point.f27746x;
            this.deltaY = point2.f27747y - point.f27747y;
        }
    }

    public class Point {

        /* renamed from: x, reason: collision with root package name */
        float f27746x;

        /* renamed from: y, reason: collision with root package name */
        float f27747y;

        public Point(float f2, float f3) {
            this.f27746x = f2;
            this.f27747y = f3;
        }

        public float getDistance(Point point) {
            float fAbs = Math.abs(this.f27746x - point.f27746x);
            float fAbs2 = Math.abs(this.f27747y - point.f27747y);
            return (float) Math.sqrt((fAbs * fAbs) + (fAbs2 * fAbs2));
        }
    }

    public static class ScaleFactor {
        int flag = 0;
        int height;
        float scaleX;
        float scaleY;
        int width;

        public ScaleFactor(int i2, int i3, float f2, float f3) {
            update(i2, i3, f2, f3);
        }

        public boolean isUpdated(int i2, int i3, int i4) {
            return (this.flag == i2 || (this.width == i3 && this.height == i4)) ? false : true;
        }

        public void update(int i2, int i3, float f2, float f3) {
            if (Float.compare(this.scaleX, f2) != 0 || Float.compare(this.scaleY, f3) != 0) {
                this.flag++;
            }
            this.width = i2;
            this.height = i3;
            this.scaleX = f2;
            this.scaleY = f3;
        }
    }

    private static final float getQuadEaseOutProgress(long j2, long j3) {
        float f2 = j2 / j3;
        return (-1.0f) * f2 * (f2 - 2.0f);
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.currStateValues[3];
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.currStateValues[0];
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j2) {
        LinePath linePath;
        int i2;
        if (!isMeasured()) {
            return null;
        }
        if (this.mScaleFactor.isUpdated(this.mScaleFactorChangedFlag, this.mCurrentWidth, this.mCurrentHeight)) {
            ScaleFactor scaleFactor = this.mScaleFactor;
            float f2 = scaleFactor.scaleX;
            float f3 = scaleFactor.scaleY;
            setTranslationData(this.beginX * f2, this.beginY * f3, this.endX * f2, this.endY * f3, this.translationDuration, this.translationStartDelay);
            LinePath[] linePathArr = this.linePaths;
            if (linePathArr != null && linePathArr.length > 0) {
                int length = linePathArr.length;
                float[][] fArr = (float[][]) Array.newInstance((Class<?>) Float.TYPE, length + 1, 2);
                int i3 = 0;
                while (i3 < length) {
                    fArr[i3] = this.linePaths[i3].getBeginPoint();
                    int i4 = i3 + 1;
                    fArr[i4] = this.linePaths[i3].getEndPoint();
                    i3 = i4;
                }
                for (float[] fArr2 : fArr) {
                    fArr2[0] = fArr2[0] * f2;
                    fArr2[1] = fArr2[1] * f3;
                }
                setLinePathData(fArr);
            }
            ScaleFactor scaleFactor2 = this.mScaleFactor;
            this.mScaleFactorChangedFlag = scaleFactor2.flag;
            this.mCurrentWidth = scaleFactor2.width;
            this.mCurrentHeight = scaleFactor2.height;
        }
        long actualTime = j2 - getActualTime();
        long j3 = this.alphaDuration;
        if (j3 > 0 && (i2 = this.deltaAlpha) != 0) {
            if (actualTime >= j3) {
                this.alpha = this.endAlpha;
            } else {
                this.alpha = this.beginAlpha + ((int) (i2 * (actualTime / j3)));
            }
        }
        float f4 = this.beginX;
        float f5 = this.beginY;
        long j4 = actualTime - this.translationStartDelay;
        long j5 = this.translationDuration;
        if (j5 > 0 && j4 >= 0 && j4 <= j5) {
            LinePath[] linePathArr2 = this.linePaths;
            if (linePathArr2 != null) {
                int length2 = linePathArr2.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length2) {
                        linePath = null;
                        break;
                    }
                    LinePath linePath2 = linePathArr2[i5];
                    if (j4 >= linePath2.beginTime && j4 < linePath2.endTime) {
                        linePath = linePath2;
                        break;
                    }
                    Point point = linePath2.pEnd;
                    float f6 = point.f27746x;
                    i5++;
                    f5 = point.f27747y;
                    f4 = f6;
                }
                if (linePath != null) {
                    float f7 = linePath.delatX;
                    float f8 = linePath.deltaY;
                    float f9 = (actualTime - linePath.beginTime) / linePath.duration;
                    Point point2 = linePath.pBegin;
                    float f10 = point2.f27746x;
                    float f11 = point2.f27747y;
                    if (f7 != 0.0f) {
                        f4 = f10 + (f7 * f9);
                    }
                    if (f8 != 0.0f) {
                        f5 = f11 + (f8 * f9);
                    }
                }
            } else {
                float quadEaseOutProgress = this.isQuadraticEaseOut ? getQuadEaseOutProgress(j4, j5) : j4 / j5;
                float f12 = this.deltaX;
                if (f12 != 0.0f) {
                    f4 = this.beginX + (f12 * quadEaseOutProgress);
                }
                float f13 = this.deltaY;
                if (f13 != 0.0f) {
                    f5 = this.beginY + (f13 * quadEaseOutProgress);
                }
            }
        } else if (j4 > j5) {
            f4 = this.endX;
            f5 = this.endY;
        }
        float[] fArr3 = this.currStateValues;
        fArr3[0] = f4;
        fArr3[1] = f5;
        fArr3[2] = f4 + this.paintWidth;
        fArr3[3] = f5 + this.paintHeight;
        setVisibility(!isOutside());
        return this.currStateValues;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.currStateValues[2];
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.currStateValues[1];
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 7;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f2, float f3) {
        getRectAtTime(iDisplayer, this.mTimer.currMillisecond);
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public void measure(IDisplayer iDisplayer, boolean z2) {
        super.measure(iDisplayer, z2);
        if (this.mCurrentWidth == 0 || this.mCurrentHeight == 0) {
            this.mCurrentWidth = iDisplayer.getWidth();
            this.mCurrentHeight = iDisplayer.getHeight();
        }
    }

    public void setAlphaData(int i2, int i3, long j2) {
        this.beginAlpha = i2;
        this.endAlpha = i3;
        this.deltaAlpha = i3 - i2;
        this.alphaDuration = j2;
        if (i2 != AlphaValue.MAX) {
            this.alpha = i2;
        }
    }

    public void setLinePathData(float[][] fArr) {
        LinePath[] linePathArr;
        if (fArr != null) {
            int length = fArr.length;
            int i2 = 0;
            float[] fArr2 = fArr[0];
            this.beginX = fArr2[0];
            this.beginY = fArr2[1];
            float[] fArr3 = fArr[length - 1];
            this.endX = fArr3[0];
            this.endY = fArr3[1];
            if (fArr.length > 1) {
                this.linePaths = new LinePath[fArr.length - 1];
                int i3 = 0;
                while (true) {
                    linePathArr = this.linePaths;
                    if (i3 >= linePathArr.length) {
                        break;
                    }
                    linePathArr[i3] = new LinePath();
                    LinePath linePath = this.linePaths[i3];
                    float[] fArr4 = fArr[i3];
                    Point point = new Point(fArr4[0], fArr4[1]);
                    i3++;
                    float[] fArr5 = fArr[i3];
                    linePath.setPoints(point, new Point(fArr5[0], fArr5[1]));
                }
                float distance = 0.0f;
                for (LinePath linePath2 : linePathArr) {
                    distance += linePath2.getDistance();
                }
                LinePath[] linePathArr2 = this.linePaths;
                int length2 = linePathArr2.length;
                LinePath linePath3 = null;
                while (i2 < length2) {
                    LinePath linePath4 = linePathArr2[i2];
                    long distance2 = (long) ((linePath4.getDistance() / distance) * this.translationDuration);
                    linePath4.duration = distance2;
                    long j2 = linePath3 == null ? 0L : linePath3.endTime;
                    linePath4.beginTime = j2;
                    linePath4.endTime = j2 + distance2;
                    i2++;
                    linePath3 = linePath4;
                }
            }
        }
    }

    public void setScaleFactor(ScaleFactor scaleFactor) {
        this.mScaleFactor = scaleFactor;
        this.mScaleFactorChangedFlag = scaleFactor.flag;
    }

    public void setTranslationData(float f2, float f3, float f4, float f5, long j2, long j3) {
        this.beginX = f2;
        this.beginY = f3;
        this.endX = f4;
        this.endY = f5;
        this.deltaX = f4 - f2;
        this.deltaY = f5 - f3;
        this.translationDuration = j2;
        this.translationStartDelay = j3;
    }
}
