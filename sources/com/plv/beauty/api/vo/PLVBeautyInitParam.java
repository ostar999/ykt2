package com.plv.beauty.api.vo;

import androidx.annotation.Nullable;
import com.plv.beauty.api.resource.BaseResource;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVBeautyInitParam {
    public boolean isOnlineLicense = true;
    public String licenseKey;
    public String licenseName;
    public String licenseSecret;

    @Nullable
    public List<? extends BaseResource> remoteResourceList;

    public PLVBeautyInitParam setLicenseKey(String str) {
        this.licenseKey = str;
        return this;
    }

    public PLVBeautyInitParam setLicenseName(String str) {
        this.licenseName = str;
        return this;
    }

    public PLVBeautyInitParam setLicenseSecret(String str) {
        this.licenseSecret = str;
        return this;
    }

    public PLVBeautyInitParam setOnlineLicense(boolean z2) {
        this.isOnlineLicense = z2;
        return this;
    }

    public PLVBeautyInitParam setRemoteResourceList(@Nullable List<? extends BaseResource> list) {
        this.remoteResourceList = list;
        return this;
    }
}
