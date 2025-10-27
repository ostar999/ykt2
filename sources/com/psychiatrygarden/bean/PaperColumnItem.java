package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J-\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/PaperColumnItem;", "", "identity_id", "", "parent_id", "title", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getIdentity_id", "()Ljava/lang/String;", "getParent_id", "getTitle", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class PaperColumnItem {

    @Nullable
    private final String identity_id;

    @Nullable
    private final String parent_id;

    @Nullable
    private final String title;

    public PaperColumnItem(@Nullable String str, @Nullable String str2, @Nullable String str3) {
        this.identity_id = str;
        this.parent_id = str2;
        this.title = str3;
    }

    public static /* synthetic */ PaperColumnItem copy$default(PaperColumnItem paperColumnItem, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = paperColumnItem.identity_id;
        }
        if ((i2 & 2) != 0) {
            str2 = paperColumnItem.parent_id;
        }
        if ((i2 & 4) != 0) {
            str3 = paperColumnItem.title;
        }
        return paperColumnItem.copy(str, str2, str3);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getIdentity_id() {
        return this.identity_id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getParent_id() {
        return this.parent_id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final PaperColumnItem copy(@Nullable String identity_id, @Nullable String parent_id, @Nullable String title) {
        return new PaperColumnItem(identity_id, parent_id, title);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PaperColumnItem)) {
            return false;
        }
        PaperColumnItem paperColumnItem = (PaperColumnItem) other;
        return Intrinsics.areEqual(this.identity_id, paperColumnItem.identity_id) && Intrinsics.areEqual(this.parent_id, paperColumnItem.parent_id) && Intrinsics.areEqual(this.title, paperColumnItem.title);
    }

    @Nullable
    public final String getIdentity_id() {
        return this.identity_id;
    }

    @Nullable
    public final String getParent_id() {
        return this.parent_id;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.identity_id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.parent_id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.title;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "PaperColumnItem(identity_id=" + this.identity_id + ", parent_id=" + this.parent_id + ", title=" + this.title + ')';
    }
}
