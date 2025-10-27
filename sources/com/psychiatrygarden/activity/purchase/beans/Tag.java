package com.psychiatrygarden.activity.purchase.beans;

import java.util.Map;

/* loaded from: classes5.dex */
public class Tag {
    private Map<?, ?> attrs;
    private int id;
    public boolean is_click;
    private String text;

    public Tag(int id, String text, boolean is_click) {
        this.id = id;
        this.text = text;
        this.is_click = is_click;
    }

    public Map<?, ?> getAttrs() {
        return this.attrs;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public boolean isIs_click() {
        return this.is_click;
    }

    public void setAttrs(Map<?, ?> attrs) {
        this.attrs = attrs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIs_click(boolean is_click) {
        this.is_click = is_click;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Tag(int id, String text) {
        this.is_click = false;
        this.id = id;
        this.text = text;
    }
}
