package org.eclipse.jetty.io;

import java.text.DateFormatSymbols;
import java.util.Locale;
import org.eclipse.jetty.util.DateCache;

/* loaded from: classes9.dex */
public class BufferDateCache extends DateCache {
    Buffer _buffer;
    String _last;

    public BufferDateCache() {
    }

    public synchronized Buffer formatBuffer(long j2) {
        String str = super.format(j2);
        if (str == this._last) {
            return this._buffer;
        }
        this._last = str;
        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(str);
        this._buffer = byteArrayBuffer;
        return byteArrayBuffer;
    }

    public BufferDateCache(String str, DateFormatSymbols dateFormatSymbols) {
        super(str, dateFormatSymbols);
    }

    public BufferDateCache(String str, Locale locale) {
        super(str, locale);
    }

    public BufferDateCache(String str) {
        super(str);
    }
}
