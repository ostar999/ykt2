package com.actionbarsherlock.widget.wheelview;

/* loaded from: classes.dex */
public class ItemsRange {
    private int count;
    private int first;

    public ItemsRange() {
        this(0, 0);
    }

    public boolean contains(int i2) {
        return i2 >= getFirst() && i2 <= getLast();
    }

    public int getCount() {
        return this.count;
    }

    public int getFirst() {
        return this.first;
    }

    public int getLast() {
        return (getFirst() + getCount()) - 1;
    }

    public ItemsRange(int i2, int i3) {
        this.first = i2;
        this.count = i3;
    }
}
