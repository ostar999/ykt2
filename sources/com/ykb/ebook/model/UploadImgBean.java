package com.ykb.ebook.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/model/UploadImgBean;", "", "b_img", "", "s_img", "(Ljava/lang/String;Ljava/lang/String;)V", "getB_img", "()Ljava/lang/String;", "getS_img", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class UploadImgBean {

    @NotNull
    private final String b_img;

    @NotNull
    private final String s_img;

    public UploadImgBean(@NotNull String b_img, @NotNull String s_img) {
        Intrinsics.checkNotNullParameter(b_img, "b_img");
        Intrinsics.checkNotNullParameter(s_img, "s_img");
        this.b_img = b_img;
        this.s_img = s_img;
    }

    public static /* synthetic */ UploadImgBean copy$default(UploadImgBean uploadImgBean, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = uploadImgBean.b_img;
        }
        if ((i2 & 2) != 0) {
            str2 = uploadImgBean.s_img;
        }
        return uploadImgBean.copy(str, str2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getB_img() {
        return this.b_img;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getS_img() {
        return this.s_img;
    }

    @NotNull
    public final UploadImgBean copy(@NotNull String b_img, @NotNull String s_img) {
        Intrinsics.checkNotNullParameter(b_img, "b_img");
        Intrinsics.checkNotNullParameter(s_img, "s_img");
        return new UploadImgBean(b_img, s_img);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UploadImgBean)) {
            return false;
        }
        UploadImgBean uploadImgBean = (UploadImgBean) other;
        return Intrinsics.areEqual(this.b_img, uploadImgBean.b_img) && Intrinsics.areEqual(this.s_img, uploadImgBean.s_img);
    }

    @NotNull
    public final String getB_img() {
        return this.b_img;
    }

    @NotNull
    public final String getS_img() {
        return this.s_img;
    }

    public int hashCode() {
        return (this.b_img.hashCode() * 31) + this.s_img.hashCode();
    }

    @NotNull
    public String toString() {
        return "UploadImgBean(b_img=" + this.b_img + ", s_img=" + this.s_img + ')';
    }
}
