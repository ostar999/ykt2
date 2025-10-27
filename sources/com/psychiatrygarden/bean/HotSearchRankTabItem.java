package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tR\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000bR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lcom/psychiatrygarden/bean/HotSearchRankTabItem;", "", "title", "", "select", "", "type", "", "code", "(Ljava/lang/String;ZILjava/lang/String;)V", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "getSelect", "()Z", "setSelect", "(Z)V", "getTitle", "getType", "()I", "setType", "(I)V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class HotSearchRankTabItem {

    @NotNull
    private String code;
    private boolean select;

    @NotNull
    private final String title;
    private int type;

    public HotSearchRankTabItem(@NotNull String title, boolean z2, int i2, @NotNull String code) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(code, "code");
        this.title = title;
        this.select = z2;
        this.type = i2;
        this.code = code;
    }

    @NotNull
    public final String getCode() {
        return this.code;
    }

    public final boolean getSelect() {
        return this.select;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getType() {
        return this.type;
    }

    public final void setCode(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.code = str;
    }

    public final void setSelect(boolean z2) {
        this.select = z2;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    public /* synthetic */ HotSearchRankTabItem(String str, boolean z2, int i2, String str2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? false : z2, (i3 & 4) != 0 ? 1 : i2, (i3 & 8) != 0 ? "bbs" : str2);
    }
}
