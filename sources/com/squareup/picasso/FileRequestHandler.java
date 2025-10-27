package com.squareup.picasso;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;

/* loaded from: classes6.dex */
class FileRequestHandler extends ContentStreamRequestHandler {
    public FileRequestHandler(Context context) {
        super(context);
    }

    public static int getFileExifRotation(Uri uri) throws IOException {
        int attributeInt = new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return 180;
        }
        if (attributeInt != 6) {
            return attributeInt != 8 ? 0 : 270;
        }
        return 90;
    }

    @Override // com.squareup.picasso.ContentStreamRequestHandler, com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        return "file".equals(request.uri.getScheme());
    }

    @Override // com.squareup.picasso.ContentStreamRequestHandler, com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request) throws IOException {
        return new RequestHandler.Result(decodeContentStream(request), Picasso.LoadedFrom.DISK, getFileExifRotation(request.uri));
    }
}
