package com.easefun.polyv.livecommon.ui.widget.autolineView;

import androidx.annotation.IdRes;

/* loaded from: classes3.dex */
public class PLVAutoLineStateModel {
    private int id;

    @IdRes
    private int imageSource;
    private boolean isActive = true;
    private boolean isShow = true;
    private String name;

    public PLVAutoLineStateModel() {
    }

    public int getId() {
        return this.id;
    }

    public int getImageSource() {
        return this.imageSource;
    }

    public String getName() {
        return this.name;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShow(boolean show) {
        this.isShow = show;
    }

    public PLVAutoLineStateModel(int imageSource, String name) {
        this.imageSource = imageSource;
        this.name = name;
    }
}
