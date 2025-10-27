package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes2.dex */
class EngineResource<Z> implements Resource<Z> {
    private int acquired;
    private final boolean isMemoryCacheable;
    private final boolean isRecyclable;
    private boolean isRecycled;
    private final Key key;
    private final ResourceListener listener;
    private final Resource<Z> resource;

    public interface ResourceListener {
        void onResourceReleased(Key key, EngineResource<?> engineResource);
    }

    public EngineResource(Resource<Z> resource, boolean z2, boolean z3, Key key, ResourceListener resourceListener) {
        this.resource = (Resource) Preconditions.checkNotNull(resource);
        this.isMemoryCacheable = z2;
        this.isRecyclable = z3;
        this.key = key;
        this.listener = (ResourceListener) Preconditions.checkNotNull(resourceListener);
    }

    public synchronized void acquire() {
        if (this.isRecycled) {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        }
        this.acquired++;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Z get() {
        return this.resource.get();
    }

    public Resource<Z> getResource() {
        return this.resource;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<Z> getResourceClass() {
        return this.resource.getResourceClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return this.resource.getSize();
    }

    public boolean isMemoryCacheable() {
        return this.isMemoryCacheable;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public synchronized void recycle() {
        if (this.acquired > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        }
        if (this.isRecycled) {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        }
        this.isRecycled = true;
        if (this.isRecyclable) {
            this.resource.recycle();
        }
    }

    public void release() {
        boolean z2;
        synchronized (this) {
            int i2 = this.acquired;
            if (i2 <= 0) {
                throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
            }
            z2 = true;
            int i3 = i2 - 1;
            this.acquired = i3;
            if (i3 != 0) {
                z2 = false;
            }
        }
        if (z2) {
            this.listener.onResourceReleased(this.key, this);
        }
    }

    public synchronized String toString() {
        return "EngineResource{isMemoryCacheable=" + this.isMemoryCacheable + ", listener=" + this.listener + ", key=" + this.key + ", acquired=" + this.acquired + ", isRecycled=" + this.isRecycled + ", resource=" + this.resource + '}';
    }
}
