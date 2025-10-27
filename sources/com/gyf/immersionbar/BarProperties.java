package com.gyf.immersionbar;

/* loaded from: classes4.dex */
public class BarProperties {
    private int actionBarHeight;
    private boolean hasNavigationBar;
    private boolean landscapeLeft;
    private boolean landscapeRight;
    private int navigationBarHeight;
    private int navigationBarWidth;
    private int notchHeight;
    private boolean notchScreen;
    private boolean portrait;
    private int statusBarHeight;

    public int getActionBarHeight() {
        return this.actionBarHeight;
    }

    public int getNavigationBarHeight() {
        return this.navigationBarHeight;
    }

    public int getNavigationBarWidth() {
        return this.navigationBarWidth;
    }

    public int getNotchHeight() {
        return this.notchHeight;
    }

    public int getStatusBarHeight() {
        return this.statusBarHeight;
    }

    public boolean hasNavigationBar() {
        return this.hasNavigationBar;
    }

    public boolean isLandscapeLeft() {
        return this.landscapeLeft;
    }

    public boolean isLandscapeRight() {
        return this.landscapeRight;
    }

    public boolean isNotchScreen() {
        return this.notchScreen;
    }

    public boolean isPortrait() {
        return this.portrait;
    }

    public void setActionBarHeight(int i2) {
        this.actionBarHeight = i2;
    }

    public void setLandscapeLeft(boolean z2) {
        this.landscapeLeft = z2;
    }

    public void setLandscapeRight(boolean z2) {
        this.landscapeRight = z2;
    }

    public void setNavigationBar(boolean z2) {
        this.hasNavigationBar = z2;
    }

    public void setNavigationBarHeight(int i2) {
        this.navigationBarHeight = i2;
    }

    public void setNavigationBarWidth(int i2) {
        this.navigationBarWidth = i2;
    }

    public void setNotchHeight(int i2) {
        this.notchHeight = i2;
    }

    public void setNotchScreen(boolean z2) {
        this.notchScreen = z2;
    }

    public void setPortrait(boolean z2) {
        this.portrait = z2;
    }

    public void setStatusBarHeight(int i2) {
        this.statusBarHeight = i2;
    }
}
