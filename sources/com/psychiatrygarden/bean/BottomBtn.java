package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019"}, d2 = {"Lcom/psychiatrygarden/bean/BottomBtn;", "Ljava/io/Serializable;", "type", "", "text", "btnTip", "disable", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBtnTip", "()Ljava/lang/String;", "getDisable", "getText", "getType", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class BottomBtn implements Serializable {

    @SerializedName("btn_tip")
    @NotNull
    private final String btnTip;

    @NotNull
    private final String disable;

    @NotNull
    private final String text;

    @NotNull
    private final String type;

    public BottomBtn(@NotNull String type, @NotNull String text, @NotNull String btnTip, @NotNull String disable) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(btnTip, "btnTip");
        Intrinsics.checkNotNullParameter(disable, "disable");
        this.type = type;
        this.text = text;
        this.btnTip = btnTip;
        this.disable = disable;
    }

    public static /* synthetic */ BottomBtn copy$default(BottomBtn bottomBtn, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = bottomBtn.type;
        }
        if ((i2 & 2) != 0) {
            str2 = bottomBtn.text;
        }
        if ((i2 & 4) != 0) {
            str3 = bottomBtn.btnTip;
        }
        if ((i2 & 8) != 0) {
            str4 = bottomBtn.disable;
        }
        return bottomBtn.copy(str, str2, str3, str4);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getText() {
        return this.text;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getBtnTip() {
        return this.btnTip;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getDisable() {
        return this.disable;
    }

    @NotNull
    public final BottomBtn copy(@NotNull String type, @NotNull String text, @NotNull String btnTip, @NotNull String disable) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(btnTip, "btnTip");
        Intrinsics.checkNotNullParameter(disable, "disable");
        return new BottomBtn(type, text, btnTip, disable);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BottomBtn)) {
            return false;
        }
        BottomBtn bottomBtn = (BottomBtn) other;
        return Intrinsics.areEqual(this.type, bottomBtn.type) && Intrinsics.areEqual(this.text, bottomBtn.text) && Intrinsics.areEqual(this.btnTip, bottomBtn.btnTip) && Intrinsics.areEqual(this.disable, bottomBtn.disable);
    }

    @NotNull
    public final String getBtnTip() {
        return this.btnTip;
    }

    @NotNull
    public final String getDisable() {
        return this.disable;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return (((((this.type.hashCode() * 31) + this.text.hashCode()) * 31) + this.btnTip.hashCode()) * 31) + this.disable.hashCode();
    }

    @NotNull
    public String toString() {
        return "BottomBtn(type=" + this.type + ", text=" + this.text + ", btnTip=" + this.btnTip + ", disable=" + this.disable + ')';
    }
}
