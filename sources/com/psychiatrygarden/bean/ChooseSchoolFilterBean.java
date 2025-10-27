package com.psychiatrygarden.bean;

import com.psychiatrygarden.db.SQLHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolFilterBean;", "", "id", "", "title", SQLHelper.SELECTED, "", "(Ljava/lang/String;Ljava/lang/String;Z)V", "getId", "()Ljava/lang/String;", "getSelected", "()Z", "setSelected", "(Z)V", "getTitle", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolFilterBean {

    @NotNull
    private final String id;
    private boolean selected;

    @NotNull
    private final String title;

    public ChooseSchoolFilterBean(@NotNull String id, @NotNull String title, boolean z2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        this.id = id;
        this.title = title;
        this.selected = z2;
    }

    public static /* synthetic */ ChooseSchoolFilterBean copy$default(ChooseSchoolFilterBean chooseSchoolFilterBean, String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chooseSchoolFilterBean.id;
        }
        if ((i2 & 2) != 0) {
            str2 = chooseSchoolFilterBean.title;
        }
        if ((i2 & 4) != 0) {
            z2 = chooseSchoolFilterBean.selected;
        }
        return chooseSchoolFilterBean.copy(str, str2, z2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getSelected() {
        return this.selected;
    }

    @NotNull
    public final ChooseSchoolFilterBean copy(@NotNull String id, @NotNull String title, boolean selected) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        return new ChooseSchoolFilterBean(id, title, selected);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolFilterBean)) {
            return false;
        }
        ChooseSchoolFilterBean chooseSchoolFilterBean = (ChooseSchoolFilterBean) other;
        return Intrinsics.areEqual(this.id, chooseSchoolFilterBean.id) && Intrinsics.areEqual(this.title, chooseSchoolFilterBean.title) && this.selected == chooseSchoolFilterBean.selected;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final boolean getSelected() {
        return this.selected;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((this.id.hashCode() * 31) + this.title.hashCode()) * 31;
        boolean z2 = this.selected;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    public final void setSelected(boolean z2) {
        this.selected = z2;
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolFilterBean(id=" + this.id + ", title=" + this.title + ", selected=" + this.selected + ')';
    }

    public /* synthetic */ ChooseSchoolFilterBean(String str, String str2, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? false : z2);
    }
}
