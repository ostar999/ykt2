package com.hjq.http.body;

import androidx.annotation.NonNull;
import com.hjq.http.model.ContentType;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/* loaded from: classes4.dex */
public class StringBody extends RequestBody {
    private final byte[] mBytes;
    private final String mText;

    public StringBody() {
        this("");
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        return this.mBytes.length;
    }

    @Override // okhttp3.RequestBody
    /* renamed from: contentType */
    public MediaType get$contentType() {
        return ContentType.TEXT;
    }

    @NonNull
    public String toString() {
        return this.mText;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        byte[] bArr = this.mBytes;
        bufferedSink.write(bArr, 0, bArr.length);
    }

    public StringBody(String str) {
        this.mText = str;
        this.mBytes = str.getBytes();
    }
}
