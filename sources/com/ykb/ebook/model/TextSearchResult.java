package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003JA\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020\u0007HÖ\u0001J\t\u0010%\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000e¨\u0006&"}, d2 = {"Lcom/ykb/ebook/model/TextSearchResult;", "", "id", "", "paragraphList", "", "sort", "", "title", "pass", "(Ljava/lang/String;Ljava/util/List;ILjava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "getParagraphList", "()Ljava/util/List;", "setParagraphList", "(Ljava/util/List;)V", "getPass", "setPass", "getSort", "()I", "setSort", "(I)V", "getTitle", "setTitle", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class TextSearchResult {

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("paragraph_list")
    @NotNull
    private List<String> paragraphList;

    @SerializedName("pass")
    @NotNull
    private String pass;

    @SerializedName("sort")
    private int sort;

    @SerializedName("title")
    @NotNull
    private String title;

    public TextSearchResult(@NotNull String id, @NotNull List<String> paragraphList, int i2, @NotNull String title, @NotNull String pass) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(paragraphList, "paragraphList");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pass, "pass");
        this.id = id;
        this.paragraphList = paragraphList;
        this.sort = i2;
        this.title = title;
        this.pass = pass;
    }

    public static /* synthetic */ TextSearchResult copy$default(TextSearchResult textSearchResult, String str, List list, int i2, String str2, String str3, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = textSearchResult.id;
        }
        if ((i3 & 2) != 0) {
            list = textSearchResult.paragraphList;
        }
        List list2 = list;
        if ((i3 & 4) != 0) {
            i2 = textSearchResult.sort;
        }
        int i4 = i2;
        if ((i3 & 8) != 0) {
            str2 = textSearchResult.title;
        }
        String str4 = str2;
        if ((i3 & 16) != 0) {
            str3 = textSearchResult.pass;
        }
        return textSearchResult.copy(str, list2, i4, str4, str3);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final List<String> component2() {
        return this.paragraphList;
    }

    /* renamed from: component3, reason: from getter */
    public final int getSort() {
        return this.sort;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getPass() {
        return this.pass;
    }

    @NotNull
    public final TextSearchResult copy(@NotNull String id, @NotNull List<String> paragraphList, int sort, @NotNull String title, @NotNull String pass) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(paragraphList, "paragraphList");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pass, "pass");
        return new TextSearchResult(id, paragraphList, sort, title, pass);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextSearchResult)) {
            return false;
        }
        TextSearchResult textSearchResult = (TextSearchResult) other;
        return Intrinsics.areEqual(this.id, textSearchResult.id) && Intrinsics.areEqual(this.paragraphList, textSearchResult.paragraphList) && this.sort == textSearchResult.sort && Intrinsics.areEqual(this.title, textSearchResult.title) && Intrinsics.areEqual(this.pass, textSearchResult.pass);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final List<String> getParagraphList() {
        return this.paragraphList;
    }

    @NotNull
    public final String getPass() {
        return this.pass;
    }

    public final int getSort() {
        return this.sort;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return (((((((this.id.hashCode() * 31) + this.paragraphList.hashCode()) * 31) + this.sort) * 31) + this.title.hashCode()) * 31) + this.pass.hashCode();
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setParagraphList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.paragraphList = list;
    }

    public final void setPass(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.pass = str;
    }

    public final void setSort(int i2) {
        this.sort = i2;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "TextSearchResult(id=" + this.id + ", paragraphList=" + this.paragraphList + ", sort=" + this.sort + ", title=" + this.title + ", pass=" + this.pass + ')';
    }
}
