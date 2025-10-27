package com.plv.beauty.api.resource;

/* loaded from: classes4.dex */
public abstract class BaseResource {
    protected String name;
    protected ResourceListener resourceListener;

    public interface ResourceListener {
        void onResourceFail(BaseResource baseResource, int i2, String str);

        void onResourceProgressChanged(BaseResource baseResource, float f2);

        void onResourceStart(BaseResource baseResource);

        void onResourceSuccess(BaseResource baseResource, ResourceResult resourceResult);
    }

    public static class ResourceResult {
        public String path;

        public ResourceResult() {
        }

        public ResourceResult(String str) {
            this.path = str;
        }
    }

    public BaseResource() {
    }

    public abstract void asyncGetResource();

    public abstract void cancel();

    public String getName() {
        return this.name;
    }

    public ResourceListener getResourceListener() {
        return this.resourceListener;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setResourceListener(ResourceListener resourceListener) {
        this.resourceListener = resourceListener;
    }

    public abstract ResourceResult syncGetResource();

    public BaseResource(String str) {
        this.name = str;
    }
}
