package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
class ContentStreamRequestHandler extends RequestHandler {
    final Context context;

    public ContentStreamRequestHandler(Context context) {
        this.context = context;
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        return "content".equals(request.uri.getScheme());
    }

    public Bitmap decodeContentStream(Request request) throws Throwable {
        InputStream inputStreamOpenInputStream;
        ContentResolver contentResolver = this.context.getContentResolver();
        BitmapFactory.Options optionsCreateBitmapOptions = RequestHandler.createBitmapOptions(request);
        InputStream inputStream = null;
        if (RequestHandler.requiresInSampleSize(optionsCreateBitmapOptions)) {
            try {
                inputStreamOpenInputStream = contentResolver.openInputStream(request.uri);
            } catch (Throwable th) {
                th = th;
            }
            try {
                BitmapFactory.decodeStream(inputStreamOpenInputStream, null, optionsCreateBitmapOptions);
                Utils.closeQuietly(inputStreamOpenInputStream);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions, request);
            } catch (Throwable th2) {
                th = th2;
                inputStream = inputStreamOpenInputStream;
                Utils.closeQuietly(inputStream);
                throw th;
            }
        }
        InputStream inputStreamOpenInputStream2 = contentResolver.openInputStream(request.uri);
        try {
            return BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, optionsCreateBitmapOptions);
        } finally {
            Utils.closeQuietly(inputStreamOpenInputStream2);
        }
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request) throws IOException {
        return new RequestHandler.Result(decodeContentStream(request), Picasso.LoadedFrom.DISK);
    }
}
