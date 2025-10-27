package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
abstract class Action<T> {
    boolean cancelled;
    final Drawable errorDrawable;
    final int errorResId;
    final String key;
    final boolean noFade;
    final Picasso picasso;
    final Request request;
    final boolean skipCache;
    final Object tag;
    final WeakReference<T> target;
    boolean willReplay;

    public static class RequestWeakReference<M> extends WeakReference<M> {
        final Action action;

        public RequestWeakReference(Action action, M m2, ReferenceQueue<? super M> referenceQueue) {
            super(m2, referenceQueue);
            this.action = action;
        }
    }

    public Action(Picasso picasso, T t2, Request request, boolean z2, boolean z3, int i2, Drawable drawable, String str, Object obj) {
        this.picasso = picasso;
        this.request = request;
        this.target = t2 == null ? null : new RequestWeakReference(this, t2, picasso.referenceQueue);
        this.skipCache = z2;
        this.noFade = z3;
        this.errorResId = i2;
        this.errorDrawable = drawable;
        this.key = str;
        this.tag = obj == null ? this : obj;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public abstract void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom);

    public abstract void error();

    public String getKey() {
        return this.key;
    }

    public Picasso getPicasso() {
        return this.picasso;
    }

    public Picasso.Priority getPriority() {
        return this.request.priority;
    }

    public Request getRequest() {
        return this.request;
    }

    public Object getTag() {
        return this.tag;
    }

    public T getTarget() {
        WeakReference<T> weakReference = this.target;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean willReplay() {
        return this.willReplay;
    }
}
