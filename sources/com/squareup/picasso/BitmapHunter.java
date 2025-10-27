package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
class BitmapHunter implements Runnable {
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    Picasso.LoadedFrom loadedFrom;
    final Picasso picasso;
    Picasso.Priority priority;
    final RequestHandler requestHandler;
    Bitmap result;
    int retryCount;
    final int sequence = SEQUENCE_GENERATOR.incrementAndGet();
    final boolean skipMemoryCache;
    final Stats stats;
    private static final Object DECODE_LOCK = new Object();
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal<StringBuilder>() { // from class: com.squareup.picasso.BitmapHunter.1
        @Override // java.lang.ThreadLocal
        public StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
    private static final RequestHandler ERRORING_HANDLER = new RequestHandler() { // from class: com.squareup.picasso.BitmapHunter.2
        @Override // com.squareup.picasso.RequestHandler
        public boolean canHandleRequest(Request request) {
            return true;
        }

        @Override // com.squareup.picasso.RequestHandler
        public RequestHandler.Result load(Request request) throws IOException {
            throw new IllegalStateException("Unrecognized type of request: " + request);
        }
    };

    public BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, RequestHandler requestHandler) {
        this.picasso = picasso;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.stats = stats;
        this.action = action;
        this.key = action.getKey();
        this.data = action.getRequest();
        this.priority = action.getPriority();
        this.skipMemoryCache = action.skipCache;
        this.requestHandler = requestHandler;
        this.retryCount = requestHandler.getRetryCount();
    }

    public static Bitmap applyCustomTransformations(List<Transformation> list, Bitmap bitmap) {
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            final Transformation transformation = list.get(i2);
            try {
                Bitmap bitmapTransform = transformation.transform(bitmap);
                if (bitmapTransform == null) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Transformation ");
                    sb.append(transformation.key());
                    sb.append(" returned null after ");
                    sb.append(i2);
                    sb.append(" previous transformation(s).\n\nTransformation list:\n");
                    Iterator<Transformation> it = list.iterator();
                    while (it.hasNext()) {
                        sb.append(it.next().key());
                        sb.append('\n');
                    }
                    Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.4
                        @Override // java.lang.Runnable
                        public void run() {
                            throw new NullPointerException(sb.toString());
                        }
                    });
                    return null;
                }
                if (bitmapTransform == bitmap && bitmap.isRecycled()) {
                    Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.5
                        @Override // java.lang.Runnable
                        public void run() {
                            throw new IllegalStateException("Transformation " + transformation.key() + " returned input Bitmap but recycled it.");
                        }
                    });
                    return null;
                }
                if (bitmapTransform != bitmap && !bitmap.isRecycled()) {
                    Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.6
                        @Override // java.lang.Runnable
                        public void run() {
                            throw new IllegalStateException("Transformation " + transformation.key() + " mutated input Bitmap but failed to recycle the original.");
                        }
                    });
                    return null;
                }
                i2++;
                bitmap = bitmapTransform;
            } catch (RuntimeException e2) {
                Picasso.HANDLER.post(new Runnable() { // from class: com.squareup.picasso.BitmapHunter.3
                    @Override // java.lang.Runnable
                    public void run() {
                        throw new RuntimeException("Transformation " + transformation.key() + " crashed with exception.", e2);
                    }
                });
                return null;
            }
        }
        return bitmap;
    }

    private Picasso.Priority computeNewPriority() {
        Picasso.Priority priority = Picasso.Priority.LOW;
        List<Action> list = this.actions;
        boolean z2 = true;
        boolean z3 = (list == null || list.isEmpty()) ? false : true;
        Action action = this.action;
        if (action == null && !z3) {
            z2 = false;
        }
        if (!z2) {
            return priority;
        }
        if (action != null) {
            priority = action.getPriority();
        }
        if (z3) {
            int size = this.actions.size();
            for (int i2 = 0; i2 < size; i2++) {
                Picasso.Priority priority2 = this.actions.get(i2).getPriority();
                if (priority2.ordinal() > priority.ordinal()) {
                    priority = priority2;
                }
            }
        }
        return priority;
    }

    public static BitmapHunter forRequest(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        Request request = action.getRequest();
        List<RequestHandler> requestHandlers = picasso.getRequestHandlers();
        int size = requestHandlers.size();
        for (int i2 = 0; i2 < size; i2++) {
            RequestHandler requestHandler = requestHandlers.get(i2);
            if (requestHandler.canHandleRequest(request)) {
                return new BitmapHunter(picasso, dispatcher, cache, stats, action, requestHandler);
            }
        }
        return new BitmapHunter(picasso, dispatcher, cache, stats, action, ERRORING_HANDLER);
    }

    public static Bitmap transformResult(Request request, Bitmap bitmap, int i2) {
        int i3;
        int i4;
        int i5;
        float f2;
        float f3;
        float f4;
        float f5;
        int i6;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        int i7 = 0;
        if (request.needsMatrixTransform()) {
            int i8 = request.targetWidth;
            int i9 = request.targetHeight;
            float f6 = request.rotationDegrees;
            if (f6 != 0.0f) {
                if (request.hasRotationPivot) {
                    matrix.setRotate(f6, request.rotationPivotX, request.rotationPivotY);
                } else {
                    matrix.setRotate(f6);
                }
            }
            if (request.centerCrop) {
                float f7 = i8 / width;
                float f8 = i9 / height;
                if (f7 > f8) {
                    int iCeil = (int) Math.ceil(r5 * (f8 / f7));
                    i6 = (height - iCeil) / 2;
                    height = iCeil;
                } else {
                    int iCeil2 = (int) Math.ceil(r2 * (f7 / f8));
                    i6 = 0;
                    i7 = (width - iCeil2) / 2;
                    width = iCeil2;
                    f7 = f8;
                }
                matrix.preScale(f7, f7);
                i3 = width;
                i4 = height;
                i5 = i6;
            } else {
                if (request.centerInside) {
                    float f9 = i8 / width;
                    float f10 = i9 / height;
                    if (f9 >= f10) {
                        f9 = f10;
                    }
                    matrix.preScale(f9, f9);
                } else if ((i8 != 0 || i9 != 0) && (i8 != width || i9 != height)) {
                    if (i8 != 0) {
                        f2 = i8;
                        f3 = width;
                    } else {
                        f2 = i9;
                        f3 = height;
                    }
                    float f11 = f2 / f3;
                    if (i9 != 0) {
                        f4 = i9;
                        f5 = height;
                    } else {
                        f4 = i8;
                        f5 = width;
                    }
                    matrix.preScale(f11, f4 / f5);
                }
                i3 = width;
                i4 = height;
                i5 = 0;
            }
        } else {
            i3 = width;
            i4 = height;
            i5 = 0;
        }
        if (i2 != 0) {
            matrix.preRotate(i2);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, i7, i5, i3, i4, matrix, true);
        if (bitmapCreateBitmap == bitmap) {
            return bitmap;
        }
        bitmap.recycle();
        return bitmapCreateBitmap;
    }

    public static void updateThreadName(Request request) {
        String name = request.getName();
        StringBuilder sb = NAME_BUILDER.get();
        sb.ensureCapacity(name.length() + 8);
        sb.replace(8, sb.length(), name);
        Thread.currentThread().setName(sb.toString());
    }

    public void attach(Action action) {
        boolean z2 = this.picasso.loggingEnabled;
        Request request = action.request;
        if (this.action == null) {
            this.action = action;
            if (z2) {
                List<Action> list = this.actions;
                if (list == null || list.isEmpty()) {
                    Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
                    return;
                } else {
                    Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                    return;
                }
            }
            return;
        }
        if (this.actions == null) {
            this.actions = new ArrayList(3);
        }
        this.actions.add(action);
        if (z2) {
            Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
        }
        Picasso.Priority priority = action.getPriority();
        if (priority.ordinal() > this.priority.ordinal()) {
            this.priority = priority;
        }
    }

    public boolean cancel() {
        Future<?> future;
        if (this.action != null) {
            return false;
        }
        List<Action> list = this.actions;
        return (list == null || list.isEmpty()) && (future = this.future) != null && future.cancel(false);
    }

    public void detach(Action action) {
        boolean zRemove;
        if (this.action == action) {
            this.action = null;
            zRemove = true;
        } else {
            List<Action> list = this.actions;
            zRemove = list != null ? list.remove(action) : false;
        }
        if (zRemove && action.getPriority() == this.priority) {
            this.priority = computeNewPriority();
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    public Action getAction() {
        return this.action;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public Request getData() {
        return this.data;
    }

    public Exception getException() {
        return this.exception;
    }

    public String getKey() {
        return this.key;
    }

    public Picasso.LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    public Picasso getPicasso() {
        return this.picasso;
    }

    public Picasso.Priority getPriority() {
        return this.priority;
    }

    public Bitmap getResult() {
        return this.result;
    }

    public Bitmap hunt() throws IOException {
        Bitmap bitmapTransformResult;
        if (this.skipMemoryCache) {
            bitmapTransformResult = null;
        } else {
            bitmapTransformResult = this.cache.get(this.key);
            if (bitmapTransformResult != null) {
                this.stats.dispatchCacheHit();
                this.loadedFrom = Picasso.LoadedFrom.MEMORY;
                if (this.picasso.loggingEnabled) {
                    Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
                }
                return bitmapTransformResult;
            }
        }
        Request request = this.data;
        request.loadFromLocalCacheOnly = this.retryCount == 0;
        RequestHandler.Result resultLoad = this.requestHandler.load(request);
        if (resultLoad != null) {
            bitmapTransformResult = resultLoad.getBitmap();
            this.loadedFrom = resultLoad.getLoadedFrom();
            this.exifRotation = resultLoad.getExifOrientation();
        }
        if (bitmapTransformResult != null) {
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId());
            }
            this.stats.dispatchBitmapDecoded(bitmapTransformResult);
            if (this.data.needsTransformation() || this.exifRotation != 0) {
                synchronized (DECODE_LOCK) {
                    if (this.data.needsMatrixTransform() || this.exifRotation != 0) {
                        bitmapTransformResult = transformResult(this.data, bitmapTransformResult, this.exifRotation);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId());
                        }
                    }
                    if (this.data.hasCustomTransformations()) {
                        bitmapTransformResult = applyCustomTransformations(this.data.transformations, bitmapTransformResult);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                        }
                    }
                }
                if (bitmapTransformResult != null) {
                    this.stats.dispatchBitmapTransformed(bitmapTransformResult);
                }
            }
        }
        return bitmapTransformResult;
    }

    public boolean isCancelled() {
        Future<?> future = this.future;
        return future != null && future.isCancelled();
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                try {
                    updateThreadName(this.data);
                    if (this.picasso.loggingEnabled) {
                        Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
                    }
                    Bitmap bitmapHunt = hunt();
                    this.result = bitmapHunt;
                    if (bitmapHunt == null) {
                        this.dispatcher.dispatchFailed(this);
                    } else {
                        this.dispatcher.dispatchComplete(this);
                    }
                } catch (IOException e2) {
                    this.exception = e2;
                    this.dispatcher.dispatchRetry(this);
                } catch (OutOfMemoryError e3) {
                    StringWriter stringWriter = new StringWriter();
                    this.stats.createSnapshot().dump(new PrintWriter(stringWriter));
                    this.exception = new RuntimeException(stringWriter.toString(), e3);
                    this.dispatcher.dispatchFailed(this);
                }
            } catch (Downloader.ResponseException e4) {
                this.exception = e4;
                this.dispatcher.dispatchFailed(this);
            } catch (Exception e5) {
                this.exception = e5;
                this.dispatcher.dispatchFailed(this);
            }
        } finally {
            Thread.currentThread().setName("Picasso-Idle");
        }
    }

    public boolean shouldRetry(boolean z2, NetworkInfo networkInfo) {
        int i2 = this.retryCount;
        if (!(i2 > 0)) {
            return false;
        }
        this.retryCount = i2 - 1;
        return this.requestHandler.shouldRetry(z2, networkInfo);
    }

    public boolean shouldSkipMemoryCache() {
        return this.skipMemoryCache;
    }

    public boolean supportsReplay() {
        return this.requestHandler.supportsReplay();
    }
}
