package com.beizi.ad.internal.animation;

/* loaded from: classes2.dex */
public enum TransitionDirection {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public static TransitionDirection getDirectionForInt(int i2) {
        for (TransitionDirection transitionDirection : values()) {
            if (transitionDirection.ordinal() == i2) {
                return transitionDirection;
            }
        }
        return UP;
    }
}
