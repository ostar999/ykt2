package com.psychiatrygarden.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\u0003HÖ\u0001J\t\u0010\r\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/bean/SelectErrorWrongFragmentEvent;", "", "position", "", "(I)V", "getPosition", "()I", "component1", "copy", "equals", "", "other", "hashCode", "toString", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class SelectErrorWrongFragmentEvent {
    private final int position;

    public SelectErrorWrongFragmentEvent(int i2) {
        this.position = i2;
    }

    public static /* synthetic */ SelectErrorWrongFragmentEvent copy$default(SelectErrorWrongFragmentEvent selectErrorWrongFragmentEvent, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = selectErrorWrongFragmentEvent.position;
        }
        return selectErrorWrongFragmentEvent.copy(i2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getPosition() {
        return this.position;
    }

    @NotNull
    public final SelectErrorWrongFragmentEvent copy(int position) {
        return new SelectErrorWrongFragmentEvent(position);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SelectErrorWrongFragmentEvent) && this.position == ((SelectErrorWrongFragmentEvent) other).position;
    }

    public final int getPosition() {
        return this.position;
    }

    public int hashCode() {
        return this.position;
    }

    @NotNull
    public String toString() {
        return "SelectErrorWrongFragmentEvent(position=" + this.position + ')';
    }
}
