package com.squareup.picasso;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
class AssetRequestHandler extends RequestHandler {
    protected static final String ANDROID_ASSET = "android_asset";
    private static final int ASSET_PREFIX_LENGTH = 22;
    private final AssetManager assetManager;

    public AssetRequestHandler(Context context) {
        this.assetManager = context.getAssets();
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "file".equals(uri.getScheme()) && !uri.getPathSegments().isEmpty() && ANDROID_ASSET.equals(uri.getPathSegments().get(0));
    }

    public Bitmap decodeAsset(Request request, String str) throws Throwable {
        BitmapFactory.Options optionsCreateBitmapOptions = RequestHandler.createBitmapOptions(request);
        InputStream inputStream = null;
        if (RequestHandler.requiresInSampleSize(optionsCreateBitmapOptions)) {
            try {
                InputStream inputStreamOpen = this.assetManager.open(str);
                try {
                    BitmapFactory.decodeStream(inputStreamOpen, null, optionsCreateBitmapOptions);
                    Utils.closeQuietly(inputStreamOpen);
                    RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions, request);
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpen;
                    Utils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        InputStream inputStreamOpen2 = this.assetManager.open(str);
        try {
            return BitmapFactory.decodeStream(inputStreamOpen2, null, optionsCreateBitmapOptions);
        } finally {
            Utils.closeQuietly(inputStreamOpen2);
        }
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request) throws IOException {
        return new RequestHandler.Result(decodeAsset(request, request.uri.toString().substring(ASSET_PREFIX_LENGTH)), Picasso.LoadedFrom.DISK);
    }
}
