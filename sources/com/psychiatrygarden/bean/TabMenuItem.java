package com.psychiatrygarden.bean;

import android.graphics.drawable.Drawable;
import com.huawei.hms.push.constant.RemoteMessageConst;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\b\u0018\u0000  2\u00020\u0001:\u0001 B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001a\u001a\u00020\tHÆ\u0003J1\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001c\u001a\u00020\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/bean/TabMenuItem;", "", RemoteMessageConst.Notification.ICON, "Landroid/graphics/drawable/Drawable;", "tabTitle", "", "type", "", "select", "", "(Landroid/graphics/drawable/Drawable;Ljava/lang/String;IZ)V", "getIcon", "()Landroid/graphics/drawable/Drawable;", "getSelect", "()Z", "setSelect", "(Z)V", "getTabTitle", "()Ljava/lang/String;", "setTabTitle", "(Ljava/lang/String;)V", "getType", "()I", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class TabMenuItem {
    public static final int TYPE_CIRCLE = 3;
    public static final int TYPE_PERSONAL_CENTER = 5;
    public static final int TYPE_QUESTION_COURSE = 2;
    public static final int TYPE_QUESTION_DATABASE = 1;
    public static final int TYPE_QUESTION_HOME_INDEX = 0;
    public static final int TYPE_SHOP = 4;

    @NotNull
    private final Drawable icon;
    private boolean select;

    @NotNull
    private String tabTitle;
    private final int type;

    public TabMenuItem(@NotNull Drawable icon, @NotNull String tabTitle, int i2, boolean z2) {
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(tabTitle, "tabTitle");
        this.icon = icon;
        this.tabTitle = tabTitle;
        this.type = i2;
        this.select = z2;
    }

    public static /* synthetic */ TabMenuItem copy$default(TabMenuItem tabMenuItem, Drawable drawable, String str, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            drawable = tabMenuItem.icon;
        }
        if ((i3 & 2) != 0) {
            str = tabMenuItem.tabTitle;
        }
        if ((i3 & 4) != 0) {
            i2 = tabMenuItem.type;
        }
        if ((i3 & 8) != 0) {
            z2 = tabMenuItem.select;
        }
        return tabMenuItem.copy(drawable, str, i2, z2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final Drawable getIcon() {
        return this.icon;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTabTitle() {
        return this.tabTitle;
    }

    /* renamed from: component3, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getSelect() {
        return this.select;
    }

    @NotNull
    public final TabMenuItem copy(@NotNull Drawable icon, @NotNull String tabTitle, int type, boolean select) {
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(tabTitle, "tabTitle");
        return new TabMenuItem(icon, tabTitle, type, select);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TabMenuItem)) {
            return false;
        }
        TabMenuItem tabMenuItem = (TabMenuItem) other;
        return Intrinsics.areEqual(this.icon, tabMenuItem.icon) && Intrinsics.areEqual(this.tabTitle, tabMenuItem.tabTitle) && this.type == tabMenuItem.type && this.select == tabMenuItem.select;
    }

    @NotNull
    public final Drawable getIcon() {
        return this.icon;
    }

    public final boolean getSelect() {
        return this.select;
    }

    @NotNull
    public final String getTabTitle() {
        return this.tabTitle;
    }

    public final int getType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((this.icon.hashCode() * 31) + this.tabTitle.hashCode()) * 31) + this.type) * 31;
        boolean z2 = this.select;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    public final void setSelect(boolean z2) {
        this.select = z2;
    }

    public final void setTabTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.tabTitle = str;
    }

    @NotNull
    public String toString() {
        return "TabMenuItem(icon=" + this.icon + ", tabTitle=" + this.tabTitle + ", type=" + this.type + ", select=" + this.select + ')';
    }

    public /* synthetic */ TabMenuItem(Drawable drawable, String str, int i2, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable, str, i2, (i3 & 8) != 0 ? false : z2);
    }
}
