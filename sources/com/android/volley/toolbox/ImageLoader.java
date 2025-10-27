package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public class ImageLoader {
    private final ImageCache mCache;
    private final RequestQueue mRequestQueue;
    private Runnable mRunnable;
    private int mBatchResponseDelayMs = 100;
    private final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap<>();
    private final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public class BatchedImageRequest {
        private final LinkedList<ImageContainer> mContainers;
        private VolleyError mError;
        private final Request<?> mRequest;
        private Bitmap mResponseBitmap;

        public BatchedImageRequest(Request<?> request, ImageContainer imageContainer) {
            LinkedList<ImageContainer> linkedList = new LinkedList<>();
            this.mContainers = linkedList;
            this.mRequest = request;
            linkedList.add(imageContainer);
        }

        public void addContainer(ImageContainer imageContainer) {
            this.mContainers.add(imageContainer);
        }

        public VolleyError getError() {
            return this.mError;
        }

        public String getRequestType() {
            return this.mRequest.getRequestType();
        }

        public boolean removeContainerAndCancelIfNecessary(ImageContainer imageContainer) {
            this.mContainers.remove(imageContainer);
            if (this.mContainers.size() != 0) {
                return false;
            }
            this.mRequest.cancel();
            return true;
        }

        public void setError(VolleyError volleyError) {
            this.mError = volleyError;
        }
    }

    public interface ImageCache {
        Bitmap getBitmap(String str);

        void putBitmap(String str, Bitmap bitmap);
    }

    public class ImageContainer {
        private Bitmap mBitmap;
        private final String mCacheKey;
        private final ImageListener mListener;
        private final String mRequestUrl;

        public ImageContainer(Bitmap bitmap, String str, String str2, ImageListener imageListener) {
            this.mBitmap = bitmap;
            this.mRequestUrl = str;
            this.mCacheKey = str2;
            this.mListener = imageListener;
        }

        public void cancelRequest() {
            if (this.mListener == null) {
                return;
            }
            BatchedImageRequest batchedImageRequest = (BatchedImageRequest) ImageLoader.this.mInFlightRequests.get(this.mCacheKey);
            if (batchedImageRequest != null) {
                if (batchedImageRequest.removeContainerAndCancelIfNecessary(this)) {
                    ImageLoader.this.mInFlightRequests.remove(this.mCacheKey);
                    return;
                }
                return;
            }
            BatchedImageRequest batchedImageRequest2 = (BatchedImageRequest) ImageLoader.this.mBatchedResponses.get(this.mCacheKey);
            if (batchedImageRequest2 != null) {
                batchedImageRequest2.removeContainerAndCancelIfNecessary(this);
                if (batchedImageRequest2.mContainers.size() == 0) {
                    ImageLoader.this.mBatchedResponses.remove(this.mCacheKey);
                }
            }
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public String getRequestUrl() {
            return this.mRequestUrl;
        }
    }

    public interface ImageListener extends Response.ErrorListener {
        void onResponse(ImageContainer imageContainer, boolean z2);
    }

    public ImageLoader(RequestQueue requestQueue, ImageCache imageCache) {
        this.mRequestQueue = requestQueue;
        this.mCache = imageCache;
    }

    private void batchResponse(String str, final BatchedImageRequest batchedImageRequest) {
        this.mBatchedResponses.put(str, batchedImageRequest);
        if (this.mRunnable == null) {
            Runnable runnable = new Runnable() { // from class: com.android.volley.toolbox.ImageLoader.4
                @Override // java.lang.Runnable
                public void run() {
                    for (BatchedImageRequest batchedImageRequest2 : ImageLoader.this.mBatchedResponses.values()) {
                        Iterator it = batchedImageRequest2.mContainers.iterator();
                        while (it.hasNext()) {
                            ImageContainer imageContainer = (ImageContainer) it.next();
                            if (imageContainer.mListener != null) {
                                if (batchedImageRequest2.getError() == null) {
                                    imageContainer.mBitmap = batchedImageRequest2.mResponseBitmap;
                                    imageContainer.mListener.onResponse(imageContainer, false);
                                } else {
                                    imageContainer.mListener.onErrorResponse(batchedImageRequest2.getError(), batchedImageRequest.getRequestType());
                                }
                            }
                        }
                    }
                    ImageLoader.this.mBatchedResponses.clear();
                    ImageLoader.this.mRunnable = null;
                }
            };
            this.mRunnable = runnable;
            this.mHandler.postDelayed(runnable, this.mBatchResponseDelayMs);
        }
    }

    private static String getCacheKey(String str, int i2, int i3) {
        StringBuilder sb = new StringBuilder(str.length() + 12);
        sb.append("#W");
        sb.append(i2);
        sb.append("#H");
        sb.append(i3);
        sb.append(str);
        return sb.toString();
    }

    public static ImageListener getImageListener(final ImageView imageView, final int i2, final int i3) {
        return new ImageListener() { // from class: com.android.volley.toolbox.ImageLoader.1
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError, String str) {
                int i4 = i3;
                if (i4 != 0) {
                    imageView.setImageResource(i4);
                }
            }

            @Override // com.android.volley.toolbox.ImageLoader.ImageListener
            public void onResponse(ImageContainer imageContainer, boolean z2) {
                if (imageContainer.getBitmap() != null) {
                    imageView.setImageBitmap(imageContainer.getBitmap());
                    return;
                }
                int i4 = i2;
                if (i4 != 0) {
                    imageView.setImageResource(i4);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetImageError(String str, VolleyError volleyError) {
        BatchedImageRequest batchedImageRequestRemove = this.mInFlightRequests.remove(str);
        batchedImageRequestRemove.setError(volleyError);
        batchResponse(str, batchedImageRequestRemove);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetImageSuccess(String str, Bitmap bitmap) {
        this.mCache.putBitmap(str, bitmap);
        BatchedImageRequest batchedImageRequestRemove = this.mInFlightRequests.remove(str);
        if (batchedImageRequestRemove != null) {
            batchedImageRequestRemove.mResponseBitmap = bitmap;
            batchResponse(str, batchedImageRequestRemove);
        }
    }

    private void throwIfNotOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
        }
    }

    public ImageContainer get(String str, ImageListener imageListener) {
        return get(str, imageListener, 0, 0);
    }

    public boolean isCached(String str, int i2, int i3) {
        throwIfNotOnMainThread();
        return this.mCache.getBitmap(getCacheKey(str, i2, i3)) != null;
    }

    public void setBatchedResponseDelay(int i2) {
        this.mBatchResponseDelayMs = i2;
    }

    public ImageContainer get(String str, ImageListener imageListener, int i2, int i3) {
        throwIfNotOnMainThread();
        final String cacheKey = getCacheKey(str, i2, i3);
        Bitmap bitmap = this.mCache.getBitmap(cacheKey);
        if (bitmap != null) {
            ImageContainer imageContainer = new ImageContainer(bitmap, str, null, null);
            imageListener.onResponse(imageContainer, true);
            return imageContainer;
        }
        ImageContainer imageContainer2 = new ImageContainer(null, str, cacheKey, imageListener);
        imageListener.onResponse(imageContainer2, true);
        BatchedImageRequest batchedImageRequest = this.mInFlightRequests.get(cacheKey);
        if (batchedImageRequest != null) {
            batchedImageRequest.addContainer(imageContainer2);
            return imageContainer2;
        }
        ImageRequest imageRequest = new ImageRequest(str, new Response.Listener<Bitmap>() { // from class: com.android.volley.toolbox.ImageLoader.2
            @Override // com.android.volley.Response.Listener
            public void onResponse(Bitmap bitmap2, String str2) {
                ImageLoader.this.onGetImageSuccess(cacheKey, bitmap2);
            }
        }, i2, i3, Bitmap.Config.RGB_565, new Response.ErrorListener() { // from class: com.android.volley.toolbox.ImageLoader.3
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError, String str2) {
                ImageLoader.this.onGetImageError(cacheKey, volleyError);
            }
        });
        this.mRequestQueue.add(imageRequest);
        this.mInFlightRequests.put(cacheKey, new BatchedImageRequest(imageRequest, imageContainer2));
        return imageContainer2;
    }
}
