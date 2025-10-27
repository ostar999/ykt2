package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J'\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00062\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\t\"\u0004\b\u0010\u0010\u000b¨\u0006\u001a"}, d2 = {"Lcom/ykb/ebook/model/CorrectType;", "", "id", "", "title", "isCheck", "", "(Ljava/lang/String;Ljava/lang/String;Z)V", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "()Z", "setCheck", "(Z)V", "getTitle", "setTitle", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class CorrectType {

    @SerializedName("id")
    @NotNull
    private String id;
    private boolean isCheck;

    @SerializedName("title")
    @NotNull
    private String title;

    public CorrectType(@NotNull String id, @NotNull String title, boolean z2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        this.id = id;
        this.title = title;
        this.isCheck = z2;
    }

    public static /* synthetic */ CorrectType copy$default(CorrectType correctType, String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = correctType.id;
        }
        if ((i2 & 2) != 0) {
            str2 = correctType.title;
        }
        if ((i2 & 4) != 0) {
            z2 = correctType.isCheck;
        }
        return correctType.copy(str, str2, z2);
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
    public final boolean getIsCheck() {
        return this.isCheck;
    }

    @NotNull
    public final CorrectType copy(@NotNull String id, @NotNull String title, boolean isCheck) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        return new CorrectType(id, title, isCheck);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CorrectType)) {
            return false;
        }
        CorrectType correctType = (CorrectType) other;
        return Intrinsics.areEqual(this.id, correctType.id) && Intrinsics.areEqual(this.title, correctType.title) && this.isCheck == correctType.isCheck;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((this.id.hashCode() * 31) + this.title.hashCode()) * 31;
        boolean z2 = this.isCheck;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    public final boolean isCheck() {
        return this.isCheck;
    }

    public final void setCheck(boolean z2) {
        this.isCheck = z2;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "CorrectType(id=" + this.id + ", title=" + this.title + ", isCheck=" + this.isCheck + ')';
    }

    public /* synthetic */ CorrectType(String str, String str2, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? false : z2);
    }
}
