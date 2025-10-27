package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;

/* loaded from: classes9.dex */
public final class EntityUtils {
    private EntityUtils() {
    }

    public static void consume(HttpEntity httpEntity) throws IOException {
        InputStream content;
        if (httpEntity == null || !httpEntity.isStreaming() || (content = httpEntity.getContent()) == null) {
            return;
        }
        content.close();
    }

    public static String getContentCharSet(HttpEntity httpEntity) throws ParseException {
        NameValuePair parameterByName;
        if (httpEntity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        if (httpEntity.getContentType() != null) {
            HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length > 0 && (parameterByName = elements[0].getParameterByName("charset")) != null) {
                return parameterByName.getValue();
            }
        }
        return null;
    }

    public static String getContentMimeType(HttpEntity httpEntity) throws ParseException {
        if (httpEntity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        if (httpEntity.getContentType() != null) {
            HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length > 0) {
                return elements[0].getName();
            }
        }
        return null;
    }

    public static byte[] toByteArray(HttpEntity httpEntity) throws IllegalStateException, IOException {
        if (httpEntity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream content = httpEntity.getContent();
        if (content == null) {
            return null;
        }
        try {
            if (httpEntity.getContentLength() > 2147483647L) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int contentLength = (int) httpEntity.getContentLength();
            if (contentLength < 0) {
                contentLength = 4096;
            }
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(contentLength);
            byte[] bArr = new byte[4096];
            while (true) {
                int i2 = content.read(bArr);
                if (i2 == -1) {
                    return byteArrayBuffer.toByteArray();
                }
                byteArrayBuffer.append(bArr, 0, i2);
            }
        } finally {
            content.close();
        }
    }

    public static String toString(HttpEntity httpEntity, String str) throws IllegalStateException, ParseException, IOException {
        if (httpEntity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream content = httpEntity.getContent();
        if (content == null) {
            return null;
        }
        try {
            if (httpEntity.getContentLength() > 2147483647L) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int contentLength = (int) httpEntity.getContentLength();
            if (contentLength < 0) {
                contentLength = 4096;
            }
            String contentCharSet = getContentCharSet(httpEntity);
            if (contentCharSet != null) {
                str = contentCharSet;
            }
            if (str == null) {
                str = "ISO-8859-1";
            }
            InputStreamReader inputStreamReader = new InputStreamReader(content, str);
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(contentLength);
            char[] cArr = new char[1024];
            while (true) {
                int i2 = inputStreamReader.read(cArr);
                if (i2 == -1) {
                    return charArrayBuffer.toString();
                }
                charArrayBuffer.append(cArr, 0, i2);
            }
        } finally {
            content.close();
        }
    }

    public static String toString(HttpEntity httpEntity) throws ParseException, IOException {
        return toString(httpEntity, null);
    }
}
