package com.plv.beauty.api.resource;

import android.content.Context;
import android.util.Log;
import com.plv.beauty.api.resource.BaseResource;
import com.plv.beauty.api.resource.RemoteResource;
import com.plv.beauty.api.resource.database.DatabaseManager;
import com.plv.beauty.api.resource.network.NetworkManager;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class ResourceManager implements BaseResource.ResourceListener {
    public static final int MAX_LOADING_RESOURCE = 5;
    public static final String TAG = "ResourceManager";
    private static volatile ResourceManager sInstance;
    private final RemoteResource.DownloadContext mDownloadContext;
    private final Vector<BaseResource> mLoadingResource = new Vector<>();
    private final ConcurrentHashMap<BaseResource, WeakReference<BaseResource.ResourceListener>> mLoadingResourceListener = new ConcurrentHashMap<>();

    private ResourceManager(Context context) {
        this.mDownloadContext = new RemoteResource.DownloadContext(context.getApplicationContext(), new NetworkManager());
        DatabaseManager.init(context);
    }

    private synchronized boolean addLoadingResource(BaseResource baseResource, BaseResource.ResourceListener resourceListener) {
        if (this.mLoadingResource.contains(baseResource)) {
            return false;
        }
        this.mLoadingResource.add(baseResource);
        this.mLoadingResourceListener.put(baseResource, new WeakReference<>(resourceListener));
        while (this.mLoadingResource.size() > 5) {
            BaseResource baseResourceFirstElement = this.mLoadingResource.firstElement();
            this.mLoadingResource.remove(baseResourceFirstElement);
            baseResourceFirstElement.cancel();
        }
        return true;
    }

    public static ResourceManager getInstance(Context context) {
        synchronized (ResourceManager.class) {
            if (sInstance == null) {
                synchronized (ResourceManager.class) {
                    if (sInstance == null) {
                        sInstance = new ResourceManager(context);
                    }
                }
            }
        }
        return sInstance;
    }

    private synchronized BaseResource.ResourceListener listenerForResource(BaseResource baseResource) {
        WeakReference<BaseResource.ResourceListener> weakReference = this.mLoadingResourceListener.get(baseResource);
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private void prepareResource(BaseResource baseResource) {
        if (baseResource instanceof RemoteResource) {
            ((RemoteResource) baseResource).setContext(this.mDownloadContext);
        }
        baseResource.setResourceListener(this);
    }

    private synchronized void removeLoadingResource(BaseResource baseResource) {
        this.mLoadingResource.remove(baseResource);
        this.mLoadingResourceListener.remove(baseResource);
    }

    public void asyncGetResource(BaseResource baseResource, BaseResource.ResourceListener resourceListener) {
        if (addLoadingResource(baseResource, resourceListener)) {
            prepareResource(baseResource);
            baseResource.asyncGetResource();
        }
    }

    public synchronized void clearLoadingResource() {
        synchronized (this) {
            Iterator<BaseResource> it = this.mLoadingResource.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.mLoadingResource.clear();
            this.mLoadingResourceListener.clear();
        }
    }

    @Override // com.plv.beauty.api.resource.BaseResource.ResourceListener
    public void onResourceFail(BaseResource baseResource, int i2, String str) {
        BaseResource.ResourceListener resourceListenerListenerForResource = listenerForResource(baseResource);
        if (resourceListenerListenerForResource == null) {
            return;
        }
        removeLoadingResource(baseResource);
        resourceListenerListenerForResource.onResourceFail(baseResource, i2, str);
    }

    @Override // com.plv.beauty.api.resource.BaseResource.ResourceListener
    public void onResourceProgressChanged(BaseResource baseResource, float f2) {
        BaseResource.ResourceListener resourceListenerListenerForResource = listenerForResource(baseResource);
        if (resourceListenerListenerForResource != null) {
            resourceListenerListenerForResource.onResourceProgressChanged(baseResource, f2);
            return;
        }
        Log.e(TAG, "listenerForResource name = " + baseResource.name + " is null");
    }

    @Override // com.plv.beauty.api.resource.BaseResource.ResourceListener
    public void onResourceStart(BaseResource baseResource) {
        BaseResource.ResourceListener resourceListenerListenerForResource = listenerForResource(baseResource);
        if (resourceListenerListenerForResource == null) {
            return;
        }
        resourceListenerListenerForResource.onResourceStart(baseResource);
    }

    @Override // com.plv.beauty.api.resource.BaseResource.ResourceListener
    public void onResourceSuccess(BaseResource baseResource, BaseResource.ResourceResult resourceResult) {
        BaseResource.ResourceListener resourceListenerListenerForResource = listenerForResource(baseResource);
        if (resourceListenerListenerForResource == null) {
            return;
        }
        removeLoadingResource(baseResource);
        resourceListenerListenerForResource.onResourceSuccess(baseResource, resourceResult);
    }

    public BaseResource.ResourceResult syncGetResource(BaseResource baseResource) {
        return baseResource.syncGetResource();
    }
}
