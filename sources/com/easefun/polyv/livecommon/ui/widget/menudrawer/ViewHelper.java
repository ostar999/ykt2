package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.view.View;

/* loaded from: classes3.dex */
final class ViewHelper {
    private ViewHelper() {
    }

    public static int getBottom(View v2) {
        return PLVMenuDrawer.USE_TRANSLATIONS ? (int) (v2.getBottom() + v2.getTranslationY()) : v2.getBottom();
    }

    public static int getLayoutDirection(View v2) {
        return v2.getLayoutDirection();
    }

    public static int getLeft(View v2) {
        return PLVMenuDrawer.USE_TRANSLATIONS ? (int) (v2.getLeft() + v2.getTranslationX()) : v2.getLeft();
    }

    public static int getRight(View v2) {
        return PLVMenuDrawer.USE_TRANSLATIONS ? (int) (v2.getRight() + v2.getTranslationX()) : v2.getRight();
    }

    public static int getTop(View v2) {
        return PLVMenuDrawer.USE_TRANSLATIONS ? (int) (v2.getTop() + v2.getTranslationY()) : v2.getTop();
    }
}
