package com.hjq.http.body;

import com.hjq.http.EasyUtils;
import com.hjq.http.model.ContentType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/* loaded from: classes4.dex */
public class UpdateBody extends RequestBody {
    private final String mKeyName;
    private final long mLength;
    private final MediaType mMediaType;
    private final Source mSource;

    public UpdateBody(File file) throws FileNotFoundException {
        this(Okio.source(file), ContentType.guessMimeType(file.getName()), file.getName(), file.length());
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        long j2 = this.mLength;
        if (j2 == 0) {
            return -1L;
        }
        return j2;
    }

    @Override // okhttp3.RequestBody
    /* renamed from: contentType */
    public MediaType getContentType() {
        return this.mMediaType;
    }

    public String getKeyName() {
        return this.mKeyName;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        try {
            bufferedSink.writeAll(this.mSource);
        } finally {
            EasyUtils.closeStream(this.mSource);
        }
    }

    public UpdateBody(InputStream inputStream, String str) throws IOException {
        this(Okio.source(inputStream), ContentType.STREAM, str, inputStream.available());
    }

    public UpdateBody(Source source, MediaType mediaType, String str, long j2) {
        this.mSource = source;
        this.mMediaType = mediaType;
        this.mKeyName = str;
        this.mLength = j2;
    }
}
