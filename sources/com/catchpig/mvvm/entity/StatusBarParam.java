package com.catchpig.mvvm.entity;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/catchpig/mvvm/entity/StatusBarParam;", "", "hide", "", "enabled", "transparent", "(ZZZ)V", "getEnabled", "()Z", "getHide", "getTransparent", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class StatusBarParam {
    private final boolean enabled;
    private final boolean hide;
    private final boolean transparent;

    public StatusBarParam() {
        this(false, false, false, 7, null);
    }

    public StatusBarParam(boolean z2, boolean z3, boolean z4) {
        this.hide = z2;
        this.enabled = z3;
        this.transparent = z4;
    }

    public static /* synthetic */ StatusBarParam copy$default(StatusBarParam statusBarParam, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = statusBarParam.hide;
        }
        if ((i2 & 2) != 0) {
            z3 = statusBarParam.enabled;
        }
        if ((i2 & 4) != 0) {
            z4 = statusBarParam.transparent;
        }
        return statusBarParam.copy(z2, z3, z4);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getHide() {
        return this.hide;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getEnabled() {
        return this.enabled;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getTransparent() {
        return this.transparent;
    }

    @NotNull
    public final StatusBarParam copy(boolean hide, boolean enabled, boolean transparent) {
        return new StatusBarParam(hide, enabled, transparent);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StatusBarParam)) {
            return false;
        }
        StatusBarParam statusBarParam = (StatusBarParam) other;
        return this.hide == statusBarParam.hide && this.enabled == statusBarParam.enabled && this.transparent == statusBarParam.transparent;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final boolean getHide() {
        return this.hide;
    }

    public final boolean getTransparent() {
        return this.transparent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    public int hashCode() {
        boolean z2 = this.hide;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        int i2 = r02 * 31;
        ?? r2 = this.enabled;
        int i3 = r2;
        if (r2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.transparent;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "StatusBarParam(hide=" + this.hide + ", enabled=" + this.enabled + ", transparent=" + this.transparent + ')';
    }

    public /* synthetic */ StatusBarParam(boolean z2, boolean z3, boolean z4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) != 0 ? false : z3, (i2 & 4) != 0 ? false : z4);
    }
}
