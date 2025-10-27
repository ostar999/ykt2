package com.hjq.http.model;

import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.net.URLConnection;
import okhttp3.MediaType;

/* loaded from: classes4.dex */
public final class ContentType {
    public static final MediaType STREAM = MediaType.parse("application/octet-stream");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

    public static MediaType guessMimeType(String str) {
        if (TextUtils.isEmpty(str)) {
            return STREAM;
        }
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(str.replace(DictionaryFactory.SHARP, ""));
        if (contentTypeFor == null) {
            return STREAM;
        }
        MediaType mediaType = MediaType.parse(contentTypeFor);
        return mediaType == null ? STREAM : mediaType;
    }
}
