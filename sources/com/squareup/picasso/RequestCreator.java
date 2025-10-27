package com.squareup.picasso;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RemoteViewsAction;
import com.squareup.picasso.Request;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class RequestCreator {
    private static int nextId;
    private final Request.Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private boolean noFade;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean setPlaceholder;
    private boolean skipMemoryCache;
    private Object tag;

    public RequestCreator(Picasso picasso, Uri uri, int i2) {
        this.setPlaceholder = true;
        if (picasso.shutdown) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.picasso = picasso;
        this.data = new Request.Builder(uri, i2);
    }

    private Request createRequest(long j2) throws Throwable {
        int requestId = getRequestId();
        Request requestBuild = this.data.build();
        requestBuild.id = requestId;
        requestBuild.started = j2;
        boolean z2 = this.picasso.loggingEnabled;
        if (z2) {
            Utils.log("Main", "created", requestBuild.plainId(), requestBuild.toString());
        }
        Request requestTransformRequest = this.picasso.transformRequest(requestBuild);
        if (requestTransformRequest != requestBuild) {
            requestTransformRequest.id = requestId;
            requestTransformRequest.started = j2;
            if (z2) {
                Utils.log("Main", "changed", requestTransformRequest.logId(), "into " + requestTransformRequest);
            }
        }
        return requestTransformRequest;
    }

    private Drawable getPlaceholderDrawable() {
        return this.placeholderResId != 0 ? this.picasso.context.getResources().getDrawable(this.placeholderResId) : this.placeholderDrawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getRequestId() throws Throwable {
        if (Utils.isMain()) {
            int i2 = nextId;
            nextId = i2 + 1;
            return i2;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicInteger atomicInteger = new AtomicInteger();
        Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.RequestCreator.1
            @Override // java.lang.Runnable
            public void run() {
                atomicInteger.set(RequestCreator.getRequestId());
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e2) {
            Utils.sneakyRethrow(e2);
        }
        return atomicInteger.get();
    }

    private void performRemoteViewInto(RemoteViewsAction remoteViewsAction) {
        Bitmap bitmapQuickMemoryCacheCheck;
        if (!this.skipMemoryCache && (bitmapQuickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(remoteViewsAction.getKey())) != null) {
            remoteViewsAction.complete(bitmapQuickMemoryCacheCheck, Picasso.LoadedFrom.MEMORY);
            return;
        }
        int i2 = this.placeholderResId;
        if (i2 != 0) {
            remoteViewsAction.setImageResource(i2);
        }
        this.picasso.enqueueAndSubmit(remoteViewsAction);
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop();
        return this;
    }

    public RequestCreator centerInside() {
        this.data.centerInside();
        return this;
    }

    public RequestCreator config(Bitmap.Config config) {
        this.data.config(config);
        return this;
    }

    public RequestCreator error(int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        }
        if (this.errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorResId = i2;
        return this;
    }

    public void fetch() throws Throwable {
        long jNanoTime = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        }
        if (this.data.hasImage()) {
            if (!this.data.hasPriority()) {
                this.data.priority(Picasso.Priority.LOW);
            }
            Request requestCreateRequest = createRequest(jNanoTime);
            this.picasso.submit(new FetchAction(this.picasso, requestCreateRequest, this.skipMemoryCache, Utils.createKey(requestCreateRequest, new StringBuilder()), this.tag));
        }
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    public Bitmap get() throws Throwable {
        long jNanoTime = System.nanoTime();
        Utils.checkNotMain();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with get.");
        }
        if (!this.data.hasImage()) {
            return null;
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        GetAction getAction = new GetAction(this.picasso, requestCreateRequest, this.skipMemoryCache, Utils.createKey(requestCreateRequest, new StringBuilder()), this.tag);
        Picasso picasso = this.picasso;
        return BitmapHunter.forRequest(picasso, picasso.dispatcher, picasso.cache, picasso.stats, getAction).hunt();
    }

    public void into(Target target) throws Throwable {
        Bitmap bitmapQuickMemoryCacheCheck;
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null.");
        }
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        }
        if (!this.data.hasImage()) {
            this.picasso.cancelRequest(target);
            target.onPrepareLoad(this.setPlaceholder ? getPlaceholderDrawable() : null);
            return;
        }
        Request requestCreateRequest = createRequest(jNanoTime);
        String strCreateKey = Utils.createKey(requestCreateRequest);
        if (this.skipMemoryCache || (bitmapQuickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(strCreateKey)) == null) {
            target.onPrepareLoad(this.setPlaceholder ? getPlaceholderDrawable() : null);
            this.picasso.enqueueAndSubmit(new TargetAction(this.picasso, target, requestCreateRequest, this.skipMemoryCache, this.errorResId, this.errorDrawable, strCreateKey, this.tag));
        } else {
            this.picasso.cancelRequest(target);
            target.onBitmapLoaded(bitmapQuickMemoryCacheCheck, Picasso.LoadedFrom.MEMORY);
        }
    }

    public RequestCreator noFade() {
        this.noFade = true;
        return this;
    }

    public RequestCreator noPlaceholder() {
        if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder resource already set.");
        }
        if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.setPlaceholder = false;
        return this;
    }

    public RequestCreator placeholder(int i2) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        }
        if (i2 == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        }
        if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.placeholderResId = i2;
        return this;
    }

    public RequestCreator priority(Picasso.Priority priority) {
        this.data.priority(priority);
        return this;
    }

    public RequestCreator resize(int i2, int i3) {
        this.data.resize(i2, i3);
        return this;
    }

    public RequestCreator resizeDimen(int i2, int i3) {
        Resources resources = this.picasso.context.getResources();
        return resize(resources.getDimensionPixelSize(i2), resources.getDimensionPixelSize(i3));
    }

    public RequestCreator rotate(float f2) {
        this.data.rotate(f2);
        return this;
    }

    public RequestCreator skipMemoryCache() {
        this.skipMemoryCache = true;
        return this;
    }

    public RequestCreator stableKey(String str) {
        this.data.stableKey(str);
        return this;
    }

    public RequestCreator tag(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Tag invalid.");
        }
        if (this.tag != null) {
            throw new IllegalStateException("Tag already set.");
        }
        this.tag = obj;
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    public RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

    public RequestCreator rotate(float f2, float f3, float f4) {
        this.data.rotate(f2, f3, f4);
        return this;
    }

    public RequestCreator error(Drawable drawable) {
        if (drawable != null) {
            if (this.errorResId == 0) {
                this.errorDrawable = drawable;
                return this;
            }
            throw new IllegalStateException("Error image already set.");
        }
        throw new IllegalArgumentException("Error image may not be null.");
    }

    public RequestCreator() {
        this.setPlaceholder = true;
        this.picasso = null;
        this.data = new Request.Builder((Uri) null, 0);
    }

    public RequestCreator placeholder(Drawable drawable) {
        if (this.setPlaceholder) {
            if (this.placeholderResId == 0) {
                this.placeholderDrawable = drawable;
                return this;
            }
            throw new IllegalStateException("Placeholder image already set.");
        }
        throw new IllegalStateException("Already explicitly declared as no placeholder.");
    }

    public void into(RemoteViews remoteViews, int i2, int i3, Notification notification) throws Throwable {
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        }
        if (notification != null) {
            if (!this.deferred) {
                if (this.placeholderDrawable == null && this.placeholderResId == 0 && this.errorDrawable == null) {
                    Request requestCreateRequest = createRequest(jNanoTime);
                    performRemoteViewInto(new RemoteViewsAction.NotificationAction(this.picasso, requestCreateRequest, remoteViews, i2, i3, notification, this.skipMemoryCache, this.errorResId, Utils.createKey(requestCreateRequest), this.tag));
                    return;
                }
                throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
            }
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        }
        throw new IllegalArgumentException("Notification must not be null.");
    }

    public void into(RemoteViews remoteViews, int i2, int[] iArr) throws Throwable {
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        }
        if (iArr != null) {
            if (!this.deferred) {
                if (this.placeholderDrawable == null && this.placeholderResId == 0 && this.errorDrawable == null) {
                    Request requestCreateRequest = createRequest(jNanoTime);
                    performRemoteViewInto(new RemoteViewsAction.AppWidgetAction(this.picasso, requestCreateRequest, remoteViews, i2, iArr, this.skipMemoryCache, this.errorResId, Utils.createKey(requestCreateRequest), this.tag));
                    return;
                }
                throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
            }
            throw new IllegalStateException("Fit cannot be used with remote views.");
        }
        throw new IllegalArgumentException("appWidgetIds must not be null.");
    }

    public void into(ImageView imageView) {
        into(imageView, null);
    }

    public void into(ImageView imageView, Callback callback) {
        Bitmap bitmapQuickMemoryCacheCheck;
        long jNanoTime = System.nanoTime();
        Utils.checkMain();
        if (imageView != null) {
            if (!this.data.hasImage()) {
                this.picasso.cancelRequest(imageView);
                if (this.setPlaceholder) {
                    PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
                    return;
                }
                return;
            }
            if (this.deferred) {
                if (!this.data.hasSize()) {
                    int width = imageView.getWidth();
                    int height = imageView.getHeight();
                    if (width != 0 && height != 0) {
                        this.data.resize(width, height);
                    } else {
                        if (this.setPlaceholder) {
                            PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
                        }
                        this.picasso.defer(imageView, new DeferredRequestCreator(this, imageView, callback));
                        return;
                    }
                } else {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
            }
            Request requestCreateRequest = createRequest(jNanoTime);
            String strCreateKey = Utils.createKey(requestCreateRequest);
            if (!this.skipMemoryCache && (bitmapQuickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(strCreateKey)) != null) {
                this.picasso.cancelRequest(imageView);
                Picasso picasso = this.picasso;
                Context context = picasso.context;
                Picasso.LoadedFrom loadedFrom = Picasso.LoadedFrom.MEMORY;
                PicassoDrawable.setBitmap(imageView, context, bitmapQuickMemoryCacheCheck, loadedFrom, this.noFade, picasso.indicatorsEnabled);
                if (this.picasso.loggingEnabled) {
                    Utils.log("Main", "completed", requestCreateRequest.plainId(), "from " + loadedFrom);
                }
                if (callback != null) {
                    callback.onSuccess();
                    return;
                }
                return;
            }
            if (this.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
            }
            this.picasso.enqueueAndSubmit(new ImageViewAction(this.picasso, imageView, requestCreateRequest, this.skipMemoryCache, this.noFade, this.errorResId, this.errorDrawable, strCreateKey, this.tag, callback));
            return;
        }
        throw new IllegalArgumentException("Target must not be null.");
    }
}
