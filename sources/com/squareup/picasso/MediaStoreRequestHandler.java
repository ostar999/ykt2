package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import com.yikaobang.yixue.R2;
import java.io.IOException;

/* loaded from: classes6.dex */
class MediaStoreRequestHandler extends ContentStreamRequestHandler {
    private static final String[] CONTENT_ORIENTATION = {"orientation"};

    public enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, R2.attr.arrow_right),
        FULL(2, -1, -1);

        final int androidKind;
        final int height;
        final int width;

        PicassoKind(int i2, int i3, int i4) {
            this.androidKind = i2;
            this.width = i3;
            this.height = i4;
        }
    }

    public MediaStoreRequestHandler(Context context) {
        super(context);
    }

    public static int getExifOrientation(ContentResolver contentResolver, Uri uri) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = contentResolver.query(uri, CONTENT_ORIENTATION, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                int i2 = cursorQuery.getInt(0);
                cursorQuery.close();
                return i2;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return 0;
        } catch (RuntimeException unused) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return 0;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static PicassoKind getPicassoKind(int i2, int i3) {
        PicassoKind picassoKind = PicassoKind.MICRO;
        if (i2 <= picassoKind.width && i3 <= picassoKind.height) {
            return picassoKind;
        }
        PicassoKind picassoKind2 = PicassoKind.MINI;
        return (i2 > picassoKind2.width || i3 > picassoKind2.height) ? PicassoKind.FULL : picassoKind2;
    }

    @Override // com.squareup.picasso.ContentStreamRequestHandler, com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "content".equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    @Override // com.squareup.picasso.ContentStreamRequestHandler, com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request) throws IOException {
        Bitmap thumbnail;
        ContentResolver contentResolver = this.context.getContentResolver();
        int exifOrientation = getExifOrientation(contentResolver, request.uri);
        String type = contentResolver.getType(request.uri);
        boolean z2 = type != null && type.startsWith("video/");
        if (request.hasSize()) {
            PicassoKind picassoKind = getPicassoKind(request.targetWidth, request.targetHeight);
            if (!z2 && picassoKind == PicassoKind.FULL) {
                return new RequestHandler.Result(decodeContentStream(request), Picasso.LoadedFrom.DISK, exifOrientation);
            }
            long id = ContentUris.parseId(request.uri);
            BitmapFactory.Options optionsCreateBitmapOptions = RequestHandler.createBitmapOptions(request);
            optionsCreateBitmapOptions.inJustDecodeBounds = true;
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, picassoKind.width, picassoKind.height, optionsCreateBitmapOptions, request);
            if (z2) {
                thumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, picassoKind != PicassoKind.FULL ? picassoKind.androidKind : 1, optionsCreateBitmapOptions);
            } else {
                thumbnail = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, picassoKind.androidKind, optionsCreateBitmapOptions);
            }
            if (thumbnail != null) {
                return new RequestHandler.Result(thumbnail, Picasso.LoadedFrom.DISK, exifOrientation);
            }
        }
        return new RequestHandler.Result(decodeContentStream(request), Picasso.LoadedFrom.DISK, exifOrientation);
    }
}
