package com.psychiatrygarden.utils;

/* loaded from: classes6.dex */
public interface AnimateHelper {
    public static final int STATE_HIDE = 0;
    public static final int STATE_SHOW = 1;

    int getState();

    void hide();

    void setMode(int modeBottom);

    void setStartY(float y2);

    void show();
}
