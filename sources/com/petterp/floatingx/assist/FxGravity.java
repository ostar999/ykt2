package com.petterp.floatingx.assist;

import com.google.android.material.badge.BadgeDrawable;
import com.tencent.connect.common.Constants;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0015"}, d2 = {"Lcom/petterp/floatingx/assist/FxGravity;", "", "value", "", Constants.PARAM_SCOPE, "(Ljava/lang/String;III)V", "getScope", "()I", "getValue", "isDefault", "", "DEFAULT", "LEFT_OR_TOP", "LEFT_OR_CENTER", "LEFT_OR_BOTTOM", "RIGHT_OR_TOP", "RIGHT_OR_CENTER", "RIGHT_OR_BOTTOM", "CENTER", "TOP_OR_CENTER", "BOTTOM_OR_CENTER", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public enum FxGravity {
    DEFAULT(BadgeDrawable.TOP_START, 1),
    LEFT_OR_TOP(BadgeDrawable.TOP_START, 1),
    LEFT_OR_CENTER(8388627, 2),
    LEFT_OR_BOTTOM(BadgeDrawable.BOTTOM_START, 3),
    RIGHT_OR_TOP(BadgeDrawable.TOP_END, 1),
    RIGHT_OR_CENTER(8388629, 2),
    RIGHT_OR_BOTTOM(BadgeDrawable.BOTTOM_END, 3),
    CENTER(17, 2),
    TOP_OR_CENTER(49, 1),
    BOTTOM_OR_CENTER(81, 3);

    private final int scope;
    private final int value;

    FxGravity(int i2, int i3) {
        this.value = i2;
        this.scope = i3;
    }

    public final int getScope() {
        return this.scope;
    }

    public final int getValue() {
        return this.value;
    }

    public final boolean isDefault() {
        return this == DEFAULT;
    }
}
