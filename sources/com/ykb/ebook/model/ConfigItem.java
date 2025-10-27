package com.ykb.ebook.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÆ\u0003J'\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/model/ConfigItem;", "", "value", "", "title", "", "checked", "", "(ILjava/lang/String;Z)V", "getChecked", "()Z", "setChecked", "(Z)V", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "getValue", "()I", "setValue", "(I)V", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ConfigItem {
    private boolean checked;

    @NotNull
    private String title;
    private int value;

    public ConfigItem(int i2, @NotNull String title, boolean z2) {
        Intrinsics.checkNotNullParameter(title, "title");
        this.value = i2;
        this.title = title;
        this.checked = z2;
    }

    public static /* synthetic */ ConfigItem copy$default(ConfigItem configItem, int i2, String str, boolean z2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = configItem.value;
        }
        if ((i3 & 2) != 0) {
            str = configItem.title;
        }
        if ((i3 & 4) != 0) {
            z2 = configItem.checked;
        }
        return configItem.copy(i2, str, z2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getValue() {
        return this.value;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getChecked() {
        return this.checked;
    }

    @NotNull
    public final ConfigItem copy(int value, @NotNull String title, boolean checked) {
        Intrinsics.checkNotNullParameter(title, "title");
        return new ConfigItem(value, title, checked);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConfigItem)) {
            return false;
        }
        ConfigItem configItem = (ConfigItem) other;
        return this.value == configItem.value && Intrinsics.areEqual(this.title, configItem.title) && this.checked == configItem.checked;
    }

    public final boolean getChecked() {
        return this.checked;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getValue() {
        return this.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((this.value * 31) + this.title.hashCode()) * 31;
        boolean z2 = this.checked;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    public final void setChecked(boolean z2) {
        this.checked = z2;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setValue(int i2) {
        this.value = i2;
    }

    @NotNull
    public String toString() {
        return "ConfigItem(value=" + this.value + ", title=" + this.title + ", checked=" + this.checked + ')';
    }

    public /* synthetic */ ConfigItem(int i2, String str, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, str, (i3 & 4) != 0 ? false : z2);
    }
}
