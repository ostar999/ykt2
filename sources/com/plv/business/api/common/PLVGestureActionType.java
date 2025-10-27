package com.plv.business.api.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVGestureActionType {
    public static final int GESTURE_CLICK = 6;
    public static final int GESTURE_DOUBLE = 7;
    public static final int GESTURE_LEFT_DOWN = 1;
    public static final int GESTURE_LEFT_UP = 0;
    public static final int GESTURE_RIGHT_DOWN = 3;
    public static final int GESTURE_RIGHT_UP = 2;
    public static final int GESTURE_SWIPE_LEFT = 4;
    public static final int GESTURE_SWIPE_RIGHT = 5;
    public static final int NONE = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Gesture_Action {
    }
}
