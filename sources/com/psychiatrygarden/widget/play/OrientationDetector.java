package com.psychiatrygarden.widget.play;

import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;

/* loaded from: classes6.dex */
public class OrientationDetector {
    private static final int HOLDING_THRESHOLD = 1500;
    private static final String TAG = "OrientationDetector";
    private Context context;
    private OrientationChangeListener listener;
    private OrientationEventListener orientationEventListener;
    private int rotationThreshold = 20;
    private long holdingTime = 0;
    private long lastCalcTime = 0;
    private Direction lastDirection = Direction.PORTRAIT;
    private int currentOrientation = 1;

    public enum Direction {
        PORTRAIT,
        REVERSE_PORTRAIT,
        LANDSCAPE,
        REVERSE_LANDSCAPE
    }

    public interface OrientationChangeListener {
        void onOrientationChanged(int screenOrientation, Direction direction);
    }

    public OrientationDetector(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Direction calcDirection(int orientation) {
        int i2 = this.rotationThreshold;
        if (orientation <= i2 || orientation >= 360 - i2) {
            return Direction.PORTRAIT;
        }
        if (Math.abs(orientation - 180) <= this.rotationThreshold) {
            return Direction.REVERSE_PORTRAIT;
        }
        if (Math.abs(orientation - 90) <= this.rotationThreshold) {
            return Direction.REVERSE_LANDSCAPE;
        }
        if (Math.abs(orientation - 270) <= this.rotationThreshold) {
            return Direction.LANDSCAPE;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calcHoldingTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.lastCalcTime == 0) {
            this.lastCalcTime = jCurrentTimeMillis;
        }
        this.holdingTime += jCurrentTimeMillis - this.lastCalcTime;
        this.lastCalcTime = jCurrentTimeMillis;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetTime() {
        this.lastCalcTime = 0L;
        this.holdingTime = 0L;
    }

    public void disable() {
        OrientationEventListener orientationEventListener = this.orientationEventListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
    }

    public void enable() {
        if (this.orientationEventListener == null) {
            this.orientationEventListener = new OrientationEventListener(this.context, 2) { // from class: com.psychiatrygarden.widget.play.OrientationDetector.1
                @Override // android.view.OrientationEventListener
                public void onOrientationChanged(int orientation) {
                    Direction directionCalcDirection = OrientationDetector.this.calcDirection(orientation);
                    if (directionCalcDirection == null) {
                        return;
                    }
                    if (directionCalcDirection != OrientationDetector.this.lastDirection) {
                        OrientationDetector.this.resetTime();
                        OrientationDetector.this.lastDirection = directionCalcDirection;
                        return;
                    }
                    OrientationDetector.this.calcHoldingTime();
                    if (OrientationDetector.this.holdingTime > 1500) {
                        if (directionCalcDirection == Direction.LANDSCAPE) {
                            if (OrientationDetector.this.currentOrientation != 0) {
                                Log.d(OrientationDetector.TAG, "switch to SCREEN_ORIENTATION_LANDSCAPE");
                                OrientationDetector.this.currentOrientation = 0;
                                if (OrientationDetector.this.listener != null) {
                                    OrientationDetector.this.listener.onOrientationChanged(0, directionCalcDirection);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (directionCalcDirection == Direction.PORTRAIT) {
                            if (OrientationDetector.this.currentOrientation != 1) {
                                Log.d(OrientationDetector.TAG, "switch to SCREEN_ORIENTATION_PORTRAIT");
                                OrientationDetector.this.currentOrientation = 1;
                                if (OrientationDetector.this.listener != null) {
                                    OrientationDetector.this.listener.onOrientationChanged(1, directionCalcDirection);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (directionCalcDirection == Direction.REVERSE_PORTRAIT) {
                            if (OrientationDetector.this.currentOrientation != 9) {
                                Log.d(OrientationDetector.TAG, "switch to SCREEN_ORIENTATION_REVERSE_PORTRAIT");
                                OrientationDetector.this.currentOrientation = 9;
                                if (OrientationDetector.this.listener != null) {
                                    OrientationDetector.this.listener.onOrientationChanged(9, directionCalcDirection);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (directionCalcDirection != Direction.REVERSE_LANDSCAPE || OrientationDetector.this.currentOrientation == 8) {
                            return;
                        }
                        Log.d(OrientationDetector.TAG, "switch to SCREEN_ORIENTATION_REVERSE_LANDSCAPE");
                        OrientationDetector.this.currentOrientation = 8;
                        if (OrientationDetector.this.listener != null) {
                            OrientationDetector.this.listener.onOrientationChanged(8, directionCalcDirection);
                        }
                    }
                }
            };
        }
        this.orientationEventListener.enable();
    }

    public void setInitialDirection(Direction direction) {
        this.lastDirection = direction;
    }

    public void setOrientationChangeListener(OrientationChangeListener listener) {
        this.listener = listener;
    }

    public void setThresholdDegree(int degree) {
        this.rotationThreshold = degree;
    }
}
