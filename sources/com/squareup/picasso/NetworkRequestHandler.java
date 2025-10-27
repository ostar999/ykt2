package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
class NetworkRequestHandler extends RequestHandler {
    private static final int MARKER = 65536;
    static final int RETRY_COUNT = 2;
    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";
    private final Downloader downloader;
    private final Stats stats;

    public NetworkRequestHandler(Downloader downloader, Stats stats) {
        this.downloader = downloader;
        this.stats = stats;
    }

    private Bitmap decodeStream(InputStream inputStream, Request request) throws IOException {
        MarkableInputStream markableInputStream = new MarkableInputStream(inputStream);
        long jSavePosition = markableInputStream.savePosition(65536);
        BitmapFactory.Options optionsCreateBitmapOptions = RequestHandler.createBitmapOptions(request);
        boolean zRequiresInSampleSize = RequestHandler.requiresInSampleSize(optionsCreateBitmapOptions);
        boolean zIsWebPFile = Utils.isWebPFile(markableInputStream);
        markableInputStream.reset(jSavePosition);
        if (zIsWebPFile) {
            byte[] byteArray = Utils.toByteArray(markableInputStream);
            if (zRequiresInSampleSize) {
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, optionsCreateBitmapOptions);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions, request);
            }
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, optionsCreateBitmapOptions);
        }
        if (zRequiresInSampleSize) {
            BitmapFactory.decodeStream(markableInputStream, null, optionsCreateBitmapOptions);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions, request);
            markableInputStream.reset(jSavePosition);
        }
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(markableInputStream, null, optionsCreateBitmapOptions);
        if (bitmapDecodeStream != null) {
            return bitmapDecodeStream;
        }
        throw new IOException("Failed to decode stream.");
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        String scheme = request.uri.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    @Override // com.squareup.picasso.RequestHandler
    public int getRetryCount() {
        return 2;
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request) throws IOException {
        Downloader.Response responseLoad = this.downloader.load(request.uri, request.loadFromLocalCacheOnly);
        if (responseLoad == null) {
            return null;
        }
        Picasso.LoadedFrom loadedFrom = responseLoad.cached ? Picasso.LoadedFrom.DISK : Picasso.LoadedFrom.NETWORK;
        Bitmap bitmap = responseLoad.getBitmap();
        if (bitmap != null) {
            return new RequestHandler.Result(bitmap, loadedFrom);
        }
        InputStream inputStream = responseLoad.getInputStream();
        if (inputStream == null) {
            return null;
        }
        if (responseLoad.getContentLength() == 0) {
            Utils.closeQuietly(inputStream);
            throw new IOException("Received response with 0 content-length header.");
        }
        if (loadedFrom == Picasso.LoadedFrom.NETWORK && responseLoad.getContentLength() > 0) {
            this.stats.dispatchDownloadFinished(responseLoad.getContentLength());
        }
        try {
            return new RequestHandler.Result(decodeStream(inputStream, request), loadedFrom);
        } finally {
            Utils.closeQuietly(inputStream);
        }
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean shouldRetry(boolean z2, NetworkInfo networkInfo) {
        return networkInfo == null || networkInfo.isConnected();
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean supportsReplay() {
        return true;
    }
}
