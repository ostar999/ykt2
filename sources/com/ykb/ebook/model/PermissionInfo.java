package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J7\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\u0006HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006#"}, d2 = {"Lcom/ykb/ebook/model/PermissionInfo;", "", "id", "", "name", "pass", "", "ways", "", "Lcom/ykb/ebook/model/Ways;", "(Ljava/lang/String;Ljava/lang/String;ILjava/util/List;)V", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "getName", "setName", "getPass", "()I", "setPass", "(I)V", "getWays", "()Ljava/util/List;", "setWays", "(Ljava/util/List;)V", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class PermissionInfo {

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("name")
    @NotNull
    private String name;

    @SerializedName("pass")
    private int pass;

    @SerializedName("ways")
    @NotNull
    private List<Ways> ways;

    public PermissionInfo(@NotNull String id, @NotNull String name, int i2, @NotNull List<Ways> ways) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(ways, "ways");
        this.id = id;
        this.name = name;
        this.pass = i2;
        this.ways = ways;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PermissionInfo copy$default(PermissionInfo permissionInfo, String str, String str2, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = permissionInfo.id;
        }
        if ((i3 & 2) != 0) {
            str2 = permissionInfo.name;
        }
        if ((i3 & 4) != 0) {
            i2 = permissionInfo.pass;
        }
        if ((i3 & 8) != 0) {
            list = permissionInfo.ways;
        }
        return permissionInfo.copy(str, str2, i2, list);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final int getPass() {
        return this.pass;
    }

    @NotNull
    public final List<Ways> component4() {
        return this.ways;
    }

    @NotNull
    public final PermissionInfo copy(@NotNull String id, @NotNull String name, int pass, @NotNull List<Ways> ways) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(ways, "ways");
        return new PermissionInfo(id, name, pass, ways);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PermissionInfo)) {
            return false;
        }
        PermissionInfo permissionInfo = (PermissionInfo) other;
        return Intrinsics.areEqual(this.id, permissionInfo.id) && Intrinsics.areEqual(this.name, permissionInfo.name) && this.pass == permissionInfo.pass && Intrinsics.areEqual(this.ways, permissionInfo.ways);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final int getPass() {
        return this.pass;
    }

    @NotNull
    public final List<Ways> getWays() {
        return this.ways;
    }

    public int hashCode() {
        return (((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.pass) * 31) + this.ways.hashCode();
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final void setPass(int i2) {
        this.pass = i2;
    }

    public final void setWays(@NotNull List<Ways> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.ways = list;
    }

    @NotNull
    public String toString() {
        return "PermissionInfo(id=" + this.id + ", name=" + this.name + ", pass=" + this.pass + ", ways=" + this.ways + ')';
    }
}
