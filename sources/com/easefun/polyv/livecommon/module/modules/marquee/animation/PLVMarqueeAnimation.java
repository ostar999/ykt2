package com.easefun.polyv.livecommon.module.modules.marquee.animation;

import android.view.View;
import java.util.HashMap;

/* loaded from: classes3.dex */
public abstract class PLVMarqueeAnimation {
    protected static final int IDLE = 0;
    public static final int PARAM_DURATION = 4;
    public static final int PARAM_HIDE_WHEN_PAUSE = 11;
    public static final int PARAM_INTERVAL = 7;
    public static final int PARAM_ISALWAYS_SHOW_WHEN_RUN = 10;
    public static final int PARAM_LIFE_TIME = 5;
    public static final int PARAM_SCREEN_HEIGHT = 3;
    public static final int PARAM_SCREEN_WIDTH = 2;
    public static final int PARAM_SECOND_VIEW_HEIGHT = 9;
    public static final int PARAM_SECOND_VIEW_WIDTH = 8;
    public static final int PARAM_TWEEN_TIME = 6;
    public static final int PARAM_VIEW_HEIGHT = 1;
    public static final int PARAM_VIEW_WIDTH = 0;
    protected static final int PAUSE = 2;
    protected static final int STARTED = 1;
    protected static final int STOP = 3;
    public static final int VIEW_MAIN = 20;
    public static final int VIEW_SECOND = 21;
    protected int animationStatus = 0;
    protected boolean isAlwaysShowWhenRun = false;
    protected boolean isHiddenWhenPause = true;
    protected int screenHeight;
    protected int screenWidth;
    protected int viewHeight;
    protected int viewWidth;

    public abstract void destroy();

    public abstract void onParentSizeChanged(View parentView);

    public abstract void pause();

    public abstract void setParams(HashMap<Integer, Integer> paramMap);

    public abstract void setViews(HashMap<Integer, View> viewMap);

    public abstract void start();

    public abstract void stop();
}
