package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
class ContactsPhotoRequestHandler extends RequestHandler {
    private static final int ID_CONTACT = 3;
    private static final int ID_DISPLAY_PHOTO = 4;
    private static final int ID_LOOKUP = 1;
    private static final int ID_THUMBNAIL = 2;
    private static final UriMatcher matcher;
    final Context context;

    @TargetApi(14)
    public static class ContactPhotoStreamIcs {
        private ContactPhotoStreamIcs() {
        }

        public static InputStream get(ContentResolver contentResolver, Uri uri) {
            return ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri, true);
        }
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        matcher = uriMatcher;
        uriMatcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        uriMatcher.addURI("com.android.contacts", "contacts/lookup/*", 1);
        uriMatcher.addURI("com.android.contacts", "contacts/#/photo", 2);
        uriMatcher.addURI("com.android.contacts", "contacts/#", 3);
        uriMatcher.addURI("com.android.contacts", "display_photo/#", 4);
    }

    public ContactsPhotoRequestHandler(Context context) {
        this.context = context;
    }

    private Bitmap decodeStream(InputStream inputStream, Request request) throws IOException {
        if (inputStream == null) {
            return null;
        }
        BitmapFactory.Options optionsCreateBitmapOptions = RequestHandler.createBitmapOptions(request);
        if (RequestHandler.requiresInSampleSize(optionsCreateBitmapOptions)) {
            InputStream inputStream2 = getInputStream(request);
            try {
                BitmapFactory.decodeStream(inputStream2, null, optionsCreateBitmapOptions);
                Utils.closeQuietly(inputStream2);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, optionsCreateBitmapOptions, request);
            } catch (Throwable th) {
                Utils.closeQuietly(inputStream2);
                throw th;
            }
        }
        return BitmapFactory.decodeStream(inputStream, null, optionsCreateBitmapOptions);
    }

    private InputStream getInputStream(Request request) throws IOException {
        ContentResolver contentResolver = this.context.getContentResolver();
        Uri uriLookupContact = request.uri;
        int iMatch = matcher.match(uriLookupContact);
        if (iMatch != 1) {
            if (iMatch != 2) {
                if (iMatch != 3) {
                    if (iMatch != 4) {
                        throw new IllegalStateException("Invalid uri: " + uriLookupContact);
                    }
                }
            }
            return contentResolver.openInputStream(uriLookupContact);
        }
        uriLookupContact = ContactsContract.Contacts.lookupContact(contentResolver, uriLookupContact);
        if (uriLookupContact == null) {
            return null;
        }
        return ContactPhotoStreamIcs.get(contentResolver, uriLookupContact);
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "content".equals(uri.getScheme()) && ContactsContract.Contacts.CONTENT_URI.getHost().equals(uri.getHost()) && !uri.getPathSegments().contains("photo");
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request) throws Throwable {
        InputStream inputStream;
        try {
            inputStream = getInputStream(request);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            RequestHandler.Result result = new RequestHandler.Result(decodeStream(inputStream, request), Picasso.LoadedFrom.DISK);
            Utils.closeQuietly(inputStream);
            return result;
        } catch (Throwable th2) {
            th = th2;
            Utils.closeQuietly(inputStream);
            throw th;
        }
    }
}
