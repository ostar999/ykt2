package com.plv.beauty.api.resource;

import com.plv.beauty.api.resource.BaseResource;

/* loaded from: classes4.dex */
public class LocalResource extends BaseResource {
    private final String path;

    public LocalResource(String str, String str2) {
        super(str);
        this.path = str2;
    }

    @Override // com.plv.beauty.api.resource.BaseResource
    public void asyncGetResource() {
        this.resourceListener.onResourceSuccess(this, new BaseResource.ResourceResult(this.path));
    }

    @Override // com.plv.beauty.api.resource.BaseResource
    public void cancel() {
        throw new IllegalStateException("no cancel for local resource");
    }

    @Override // com.plv.beauty.api.resource.BaseResource
    public BaseResource.ResourceResult syncGetResource() {
        return new BaseResource.ResourceResult(this.path);
    }
}
