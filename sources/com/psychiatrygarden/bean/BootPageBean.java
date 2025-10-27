package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/BootPageBean;", "", "target_params", "", "img_url", "jpush_type", "vip_include", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getImg_url", "()Ljava/lang/String;", "getJpush_type", "getTarget_params", "getVip_include", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class BootPageBean {

    @Nullable
    private final String img_url;

    @Nullable
    private final String jpush_type;

    @Nullable
    private final String target_params;

    @Nullable
    private final String vip_include;

    public BootPageBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.target_params = str;
        this.img_url = str2;
        this.jpush_type = str3;
        this.vip_include = str4;
    }

    public static /* synthetic */ BootPageBean copy$default(BootPageBean bootPageBean, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = bootPageBean.target_params;
        }
        if ((i2 & 2) != 0) {
            str2 = bootPageBean.img_url;
        }
        if ((i2 & 4) != 0) {
            str3 = bootPageBean.jpush_type;
        }
        if ((i2 & 8) != 0) {
            str4 = bootPageBean.vip_include;
        }
        return bootPageBean.copy(str, str2, str3, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTarget_params() {
        return this.target_params;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getImg_url() {
        return this.img_url;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getJpush_type() {
        return this.jpush_type;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getVip_include() {
        return this.vip_include;
    }

    @NotNull
    public final BootPageBean copy(@Nullable String target_params, @Nullable String img_url, @Nullable String jpush_type, @Nullable String vip_include) {
        return new BootPageBean(target_params, img_url, jpush_type, vip_include);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BootPageBean)) {
            return false;
        }
        BootPageBean bootPageBean = (BootPageBean) other;
        return Intrinsics.areEqual(this.target_params, bootPageBean.target_params) && Intrinsics.areEqual(this.img_url, bootPageBean.img_url) && Intrinsics.areEqual(this.jpush_type, bootPageBean.jpush_type) && Intrinsics.areEqual(this.vip_include, bootPageBean.vip_include);
    }

    @Nullable
    public final String getImg_url() {
        return this.img_url;
    }

    @Nullable
    public final String getJpush_type() {
        return this.jpush_type;
    }

    @Nullable
    public final String getTarget_params() {
        return this.target_params;
    }

    @Nullable
    public final String getVip_include() {
        return this.vip_include;
    }

    public int hashCode() {
        String str = this.target_params;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.img_url;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.jpush_type;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.vip_include;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "BootPageBean(target_params=" + this.target_params + ", img_url=" + this.img_url + ", jpush_type=" + this.jpush_type + ", vip_include=" + this.vip_include + ')';
    }
}
