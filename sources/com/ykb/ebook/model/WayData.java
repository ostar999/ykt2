package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020'HÖ\u0001J\t\u0010(\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\f\"\u0004\b\u0012\u0010\u000eR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\f\"\u0004\b\u0016\u0010\u000eR\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u000eR\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000e¨\u0006)"}, d2 = {"Lcom/ykb/ebook/model/WayData;", "", "posterHtml", "", "popupImg", "joinUsType", "ykbCommunityId", "communityId", "price", "bookId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBookId", "()Ljava/lang/String;", "setBookId", "(Ljava/lang/String;)V", "getCommunityId", "setCommunityId", "getJoinUsType", "setJoinUsType", "getPopupImg", "setPopupImg", "getPosterHtml", "setPosterHtml", "getPrice", "setPrice", "getYkbCommunityId", "setYkbCommunityId", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class WayData {

    @SerializedName("book_id")
    @NotNull
    private String bookId;

    @SerializedName("community_id")
    @NotNull
    private String communityId;

    @SerializedName("join_us_type")
    @NotNull
    private String joinUsType;

    @SerializedName("popup_img")
    @NotNull
    private String popupImg;

    @SerializedName("poster_html")
    @NotNull
    private String posterHtml;

    @SerializedName("price")
    @NotNull
    private String price;

    @SerializedName("ykb_community_id")
    @NotNull
    private String ykbCommunityId;

    public WayData(@NotNull String posterHtml, @NotNull String popupImg, @NotNull String joinUsType, @NotNull String ykbCommunityId, @NotNull String communityId, @NotNull String price, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(posterHtml, "posterHtml");
        Intrinsics.checkNotNullParameter(popupImg, "popupImg");
        Intrinsics.checkNotNullParameter(joinUsType, "joinUsType");
        Intrinsics.checkNotNullParameter(ykbCommunityId, "ykbCommunityId");
        Intrinsics.checkNotNullParameter(communityId, "communityId");
        Intrinsics.checkNotNullParameter(price, "price");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        this.posterHtml = posterHtml;
        this.popupImg = popupImg;
        this.joinUsType = joinUsType;
        this.ykbCommunityId = ykbCommunityId;
        this.communityId = communityId;
        this.price = price;
        this.bookId = bookId;
    }

    public static /* synthetic */ WayData copy$default(WayData wayData, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = wayData.posterHtml;
        }
        if ((i2 & 2) != 0) {
            str2 = wayData.popupImg;
        }
        String str8 = str2;
        if ((i2 & 4) != 0) {
            str3 = wayData.joinUsType;
        }
        String str9 = str3;
        if ((i2 & 8) != 0) {
            str4 = wayData.ykbCommunityId;
        }
        String str10 = str4;
        if ((i2 & 16) != 0) {
            str5 = wayData.communityId;
        }
        String str11 = str5;
        if ((i2 & 32) != 0) {
            str6 = wayData.price;
        }
        String str12 = str6;
        if ((i2 & 64) != 0) {
            str7 = wayData.bookId;
        }
        return wayData.copy(str, str8, str9, str10, str11, str12, str7);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getPosterHtml() {
        return this.posterHtml;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getPopupImg() {
        return this.popupImg;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getJoinUsType() {
        return this.joinUsType;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getYkbCommunityId() {
        return this.ykbCommunityId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getCommunityId() {
        return this.communityId;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getPrice() {
        return this.price;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getBookId() {
        return this.bookId;
    }

    @NotNull
    public final WayData copy(@NotNull String posterHtml, @NotNull String popupImg, @NotNull String joinUsType, @NotNull String ykbCommunityId, @NotNull String communityId, @NotNull String price, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(posterHtml, "posterHtml");
        Intrinsics.checkNotNullParameter(popupImg, "popupImg");
        Intrinsics.checkNotNullParameter(joinUsType, "joinUsType");
        Intrinsics.checkNotNullParameter(ykbCommunityId, "ykbCommunityId");
        Intrinsics.checkNotNullParameter(communityId, "communityId");
        Intrinsics.checkNotNullParameter(price, "price");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        return new WayData(posterHtml, popupImg, joinUsType, ykbCommunityId, communityId, price, bookId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WayData)) {
            return false;
        }
        WayData wayData = (WayData) other;
        return Intrinsics.areEqual(this.posterHtml, wayData.posterHtml) && Intrinsics.areEqual(this.popupImg, wayData.popupImg) && Intrinsics.areEqual(this.joinUsType, wayData.joinUsType) && Intrinsics.areEqual(this.ykbCommunityId, wayData.ykbCommunityId) && Intrinsics.areEqual(this.communityId, wayData.communityId) && Intrinsics.areEqual(this.price, wayData.price) && Intrinsics.areEqual(this.bookId, wayData.bookId);
    }

    @NotNull
    public final String getBookId() {
        return this.bookId;
    }

    @NotNull
    public final String getCommunityId() {
        return this.communityId;
    }

    @NotNull
    public final String getJoinUsType() {
        return this.joinUsType;
    }

    @NotNull
    public final String getPopupImg() {
        return this.popupImg;
    }

    @NotNull
    public final String getPosterHtml() {
        return this.posterHtml;
    }

    @NotNull
    public final String getPrice() {
        return this.price;
    }

    @NotNull
    public final String getYkbCommunityId() {
        return this.ykbCommunityId;
    }

    public int hashCode() {
        return (((((((((((this.posterHtml.hashCode() * 31) + this.popupImg.hashCode()) * 31) + this.joinUsType.hashCode()) * 31) + this.ykbCommunityId.hashCode()) * 31) + this.communityId.hashCode()) * 31) + this.price.hashCode()) * 31) + this.bookId.hashCode();
    }

    public final void setBookId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bookId = str;
    }

    public final void setCommunityId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.communityId = str;
    }

    public final void setJoinUsType(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.joinUsType = str;
    }

    public final void setPopupImg(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.popupImg = str;
    }

    public final void setPosterHtml(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.posterHtml = str;
    }

    public final void setPrice(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.price = str;
    }

    public final void setYkbCommunityId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ykbCommunityId = str;
    }

    @NotNull
    public String toString() {
        return "WayData(posterHtml=" + this.posterHtml + ", popupImg=" + this.popupImg + ", joinUsType=" + this.joinUsType + ", ykbCommunityId=" + this.ykbCommunityId + ", communityId=" + this.communityId + ", price=" + this.price + ", bookId=" + this.bookId + ')';
    }
}
