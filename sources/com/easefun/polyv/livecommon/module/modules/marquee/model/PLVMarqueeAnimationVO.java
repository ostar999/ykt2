package com.easefun.polyv.livecommon.module.modules.marquee.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class PLVMarqueeAnimationVO {
    public static final int FLICK = 2;
    public static final int FLICK_15PERCENT = 5;
    public static final int FLICK_ADVANCE = 7;
    public static final int MERGE_ROLL_FLICK = 3;
    public static final int ROLL = 1;
    public static final int ROLL_15PERCENT = 4;
    public static final int ROLL_ADVANCE = 6;
    private boolean isHiddenWhenPause = true;
    private boolean isAlwaysShowWhenRun = false;
    private int animationType = 1;
    private int speed = 200;
    private int interval = 5;
    private int lifeTime = 3;
    private int tweenTime = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public int getAnimationType() {
        return this.animationType;
    }

    public int getInterval() {
        return this.interval;
    }

    public int getLifeTime() {
        return this.lifeTime;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getTweenTime() {
        return this.tweenTime;
    }

    public boolean isAlwaysShowWhenRun() {
        return this.isAlwaysShowWhenRun;
    }

    public boolean isHiddenWhenPause() {
        return this.isHiddenWhenPause;
    }

    public PLVMarqueeAnimationVO setAlwaysShowWhenRun(boolean isAlwaysShowWhenRun) {
        this.isAlwaysShowWhenRun = isAlwaysShowWhenRun;
        return this;
    }

    public PLVMarqueeAnimationVO setAnimationType(int animationType) {
        this.animationType = animationType;
        return this;
    }

    public PLVMarqueeAnimationVO setHiddenWhenPause(boolean hiddenWhenPause) {
        this.isHiddenWhenPause = hiddenWhenPause;
        return this;
    }

    public PLVMarqueeAnimationVO setInterval(int interval) {
        if (interval >= 0) {
            this.interval = interval;
        }
        return this;
    }

    public PLVMarqueeAnimationVO setLifeTime(int lifeTime) {
        if (lifeTime >= 0) {
            this.lifeTime = lifeTime;
        }
        return this;
    }

    public PLVMarqueeAnimationVO setSpeed(int speed) {
        if (speed > 0) {
            this.speed = speed;
        }
        return this;
    }

    public PLVMarqueeAnimationVO setTweenTime(int tweenTime) {
        if (tweenTime >= 0) {
            this.tweenTime = tweenTime;
        }
        return this;
    }
}
