package com.psychiatrygarden.activity.courselist.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0015\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\nR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\n¨\u0006\u001c"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/bean/QRCode;", "", "wechat_qrcode", "", "wechat_tips", "wechat_number", "is_open_qrcode", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "()Ljava/lang/String;", "set_open_qrcode", "(Ljava/lang/String;)V", "getWechat_number", "setWechat_number", "getWechat_qrcode", "setWechat_qrcode", "getWechat_tips", "setWechat_tips", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class QRCode {

    @Nullable
    private String is_open_qrcode;

    @Nullable
    private String wechat_number;

    @Nullable
    private String wechat_qrcode;

    @Nullable
    private String wechat_tips;

    public QRCode(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.wechat_qrcode = str;
        this.wechat_tips = str2;
        this.wechat_number = str3;
        this.is_open_qrcode = str4;
    }

    public static /* synthetic */ QRCode copy$default(QRCode qRCode, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = qRCode.wechat_qrcode;
        }
        if ((i2 & 2) != 0) {
            str2 = qRCode.wechat_tips;
        }
        if ((i2 & 4) != 0) {
            str3 = qRCode.wechat_number;
        }
        if ((i2 & 8) != 0) {
            str4 = qRCode.is_open_qrcode;
        }
        return qRCode.copy(str, str2, str3, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getWechat_qrcode() {
        return this.wechat_qrcode;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getWechat_tips() {
        return this.wechat_tips;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getWechat_number() {
        return this.wechat_number;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getIs_open_qrcode() {
        return this.is_open_qrcode;
    }

    @NotNull
    public final QRCode copy(@Nullable String wechat_qrcode, @Nullable String wechat_tips, @Nullable String wechat_number, @Nullable String is_open_qrcode) {
        return new QRCode(wechat_qrcode, wechat_tips, wechat_number, is_open_qrcode);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof QRCode)) {
            return false;
        }
        QRCode qRCode = (QRCode) other;
        return Intrinsics.areEqual(this.wechat_qrcode, qRCode.wechat_qrcode) && Intrinsics.areEqual(this.wechat_tips, qRCode.wechat_tips) && Intrinsics.areEqual(this.wechat_number, qRCode.wechat_number) && Intrinsics.areEqual(this.is_open_qrcode, qRCode.is_open_qrcode);
    }

    @Nullable
    public final String getWechat_number() {
        return this.wechat_number;
    }

    @Nullable
    public final String getWechat_qrcode() {
        return this.wechat_qrcode;
    }

    @Nullable
    public final String getWechat_tips() {
        return this.wechat_tips;
    }

    public int hashCode() {
        String str = this.wechat_qrcode;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.wechat_tips;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.wechat_number;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.is_open_qrcode;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @Nullable
    public final String is_open_qrcode() {
        return this.is_open_qrcode;
    }

    public final void setWechat_number(@Nullable String str) {
        this.wechat_number = str;
    }

    public final void setWechat_qrcode(@Nullable String str) {
        this.wechat_qrcode = str;
    }

    public final void setWechat_tips(@Nullable String str) {
        this.wechat_tips = str;
    }

    public final void set_open_qrcode(@Nullable String str) {
        this.is_open_qrcode = str;
    }

    @NotNull
    public String toString() {
        return "QRCode(wechat_qrcode=" + this.wechat_qrcode + ", wechat_tips=" + this.wechat_tips + ", wechat_number=" + this.wechat_number + ", is_open_qrcode=" + this.is_open_qrcode + ')';
    }
}
