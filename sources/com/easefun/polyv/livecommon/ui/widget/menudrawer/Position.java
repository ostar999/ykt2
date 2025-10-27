package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.util.SparseArray;

/* loaded from: classes3.dex */
public enum Position {
    LEFT(0),
    TOP(1),
    RIGHT(2),
    BOTTOM(3),
    START(4),
    END(5);

    private static final SparseArray<Position> STRING_MAPPING = new SparseArray<>();
    final int mValue;

    static {
        for (Position position : values()) {
            STRING_MAPPING.put(position.mValue, position);
        }
    }

    Position(int value) {
        this.mValue = value;
    }

    public static Position fromValue(int value) {
        return STRING_MAPPING.get(value);
    }
}
