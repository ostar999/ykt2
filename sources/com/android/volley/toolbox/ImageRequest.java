package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

/* loaded from: classes2.dex */
public class ImageRequest extends Request<Bitmap> {
    private static final float IMAGE_BACKOFF_MULT = 2.0f;
    private static final int IMAGE_MAX_RETRIES = 2;
    private static final int IMAGE_TIMEOUT_MS = 1000;
    private static final Object sDecodeLock = new Object();
    private final Bitmap.Config mDecodeConfig;
    private final Response.Listener<Bitmap> mListener;
    private final int mMaxHeight;
    private final int mMaxWidth;

    public ImageRequest(String str, Response.Listener<Bitmap> listener, int i2, int i3, Bitmap.Config config, Response.ErrorListener errorListener) {
        super(0, str, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(1000, 2, 2.0f));
        this.mListener = listener;
        this.mDecodeConfig = config;
        this.mMaxWidth = i2;
        this.mMaxHeight = i3;
    }

    private Response<Bitmap> doParse(NetworkResponse networkResponse) {
        Bitmap bitmapDecodeByteArray;
        byte[] bArr = networkResponse.data;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (this.mMaxWidth == 0 && this.mMaxHeight == 0) {
            options.inPreferredConfig = this.mDecodeConfig;
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            int resizedDimension = getResizedDimension(this.mMaxWidth, this.mMaxHeight, i2, i3);
            int resizedDimension2 = getResizedDimension(this.mMaxHeight, this.mMaxWidth, i3, i2);
            options.inJustDecodeBounds = false;
            options.inSampleSize = findBestSampleSize(i2, i3, resizedDimension, resizedDimension2);
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (bitmapDecodeByteArray != null && (bitmapDecodeByteArray.getWidth() > resizedDimension || bitmapDecodeByteArray.getHeight() > resizedDimension2)) {
                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeByteArray, resizedDimension, resizedDimension2, true);
                bitmapDecodeByteArray.recycle();
                bitmapDecodeByteArray = bitmapCreateScaledBitmap;
            }
        }
        return bitmapDecodeByteArray == null ? Response.error(new ParseError(networkResponse)) : Response.success(bitmapDecodeByteArray, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    public static int findBestSampleSize(int i2, int i3, int i4, int i5) {
        double dMin = Math.min(i2 / i4, i3 / i5);
        float f2 = 1.0f;
        while (true) {
            float f3 = 2.0f * f2;
            if (f3 > dMin) {
                return (int) f2;
            }
            f2 = f3;
        }
    }

    private static int getResizedDimension(int i2, int i3, int i4, int i5) {
        if (i2 == 0 && i3 == 0) {
            return i4;
        }
        if (i2 == 0) {
            return (int) (i4 * (i3 / i5));
        }
        if (i3 == 0) {
            return i2;
        }
        double d3 = i5 / i4;
        double d4 = i3;
        return ((double) i2) * d3 > d4 ? (int) (d4 / d3) : i2;
    }

    @Override // com.android.volley.Request
    public Request.Priority getPriority() {
        return Request.Priority.LOW;
    }

    @Override // com.android.volley.Request
    public Response<Bitmap> parseNetworkResponse(NetworkResponse networkResponse) {
        Response<Bitmap> responseDoParse;
        synchronized (sDecodeLock) {
            try {
                try {
                    responseDoParse = doParse(networkResponse);
                } catch (OutOfMemoryError e2) {
                    VolleyLog.e("Caught OOM for %d byte image, url=%s", Integer.valueOf(networkResponse.data.length), getUrl());
                    return Response.error(new ParseError(e2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return responseDoParse;
    }

    @Override // com.android.volley.Request
    public void deliverResponse(Bitmap bitmap, String str) {
        this.mListener.onResponse(bitmap, str);
    }
}
