package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class MoreContentCache {
    private CharSequence collapseText;
    private CharSequence expandText;

    public MoreContentCache(CharSequence collapseText, CharSequence expandText) {
        this.collapseText = collapseText;
        this.expandText = expandText;
    }

    public CharSequence getCollapseText() {
        return this.collapseText;
    }

    public CharSequence getExpandText() {
        return this.expandText;
    }

    public void setCollapseText(CharSequence collapseText) {
        this.collapseText = collapseText;
    }

    public void setExpandText(CharSequence expandText) {
        this.expandText = expandText;
    }
}
