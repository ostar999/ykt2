package com.yddmi.doctor.entity.result;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/yddmi/doctor/entity/result/HeartDetailIndicator;", "", "name", "", "action", "", "(Ljava/lang/String;I)V", "getAction", "()I", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class HeartDetailIndicator {
    private final int action;

    @NotNull
    private final String name;

    public HeartDetailIndicator(@NotNull String name, int i2) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.action = i2;
    }

    public static /* synthetic */ HeartDetailIndicator copy$default(HeartDetailIndicator heartDetailIndicator, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = heartDetailIndicator.name;
        }
        if ((i3 & 2) != 0) {
            i2 = heartDetailIndicator.action;
        }
        return heartDetailIndicator.copy(str, i2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final int getAction() {
        return this.action;
    }

    @NotNull
    public final HeartDetailIndicator copy(@NotNull String name, int action) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new HeartDetailIndicator(name, action);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HeartDetailIndicator)) {
            return false;
        }
        HeartDetailIndicator heartDetailIndicator = (HeartDetailIndicator) other;
        return Intrinsics.areEqual(this.name, heartDetailIndicator.name) && this.action == heartDetailIndicator.action;
    }

    public final int getAction() {
        return this.action;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.action;
    }

    @NotNull
    public String toString() {
        return "HeartDetailIndicator(name=" + this.name + ", action=" + this.action + ")";
    }
}
