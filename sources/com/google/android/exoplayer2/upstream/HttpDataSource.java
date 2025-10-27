package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface HttpDataSource extends DataSource {
    public static final Predicate<String> REJECT_PAYWALL_TYPES = new Predicate() { // from class: com.google.android.exoplayer2.upstream.k
        @Override // com.google.common.base.Predicate
        public final boolean apply(Object obj) {
            return l.a((String) obj);
        }
    };

    public static abstract class BaseFactory implements Factory {
        private final RequestProperties defaultRequestProperties = new RequestProperties();

        public abstract HttpDataSource createDataSourceInternal(RequestProperties requestProperties);

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.defaultRequestProperties.clearAndSet(map);
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory, com.google.android.exoplayer2.upstream.DataSource.Factory
        public final HttpDataSource createDataSource() {
            return createDataSourceInternal(this.defaultRequestProperties);
        }
    }

    public static final class CleartextNotPermittedException extends HttpDataSourceException {
        public CleartextNotPermittedException(IOException iOException, DataSpec dataSpec) {
            super("Cleartext HTTP traffic not permitted. See https://exoplayer.dev/issues/cleartext-not-permitted", iOException, dataSpec, 2007, 1);
        }
    }

    public interface Factory extends DataSource.Factory {
        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        /* bridge */ /* synthetic */ DataSource createDataSource();

        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        HttpDataSource createDataSource();

        Factory setDefaultRequestProperties(Map<String, String> map);
    }

    public static class HttpDataSourceException extends DataSourceException {
        public static final int TYPE_CLOSE = 3;
        public static final int TYPE_OPEN = 1;
        public static final int TYPE_READ = 2;
        public final DataSpec dataSpec;
        public final int type;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        @Deprecated
        public HttpDataSourceException(DataSpec dataSpec, int i2) {
            this(dataSpec, 2000, i2);
        }

        private static int assignErrorCode(int i2, int i3) {
            if (i2 == 2000 && i3 == 1) {
                return 2001;
            }
            return i2;
        }

        public static HttpDataSourceException createForIOException(IOException iOException, DataSpec dataSpec, int i2) {
            String message = iOException.getMessage();
            int i3 = iOException instanceof SocketTimeoutException ? 2002 : iOException instanceof InterruptedIOException ? 1004 : (message == null || !Ascii.toLowerCase(message).matches("cleartext.*not permitted.*")) ? 2001 : 2007;
            return i3 == 2007 ? new CleartextNotPermittedException(iOException, dataSpec) : new HttpDataSourceException(iOException, dataSpec, i3, i2);
        }

        public HttpDataSourceException(DataSpec dataSpec, int i2, int i3) {
            super(assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }

        @Deprecated
        public HttpDataSourceException(String str, DataSpec dataSpec, int i2) {
            this(str, dataSpec, 2000, i2);
        }

        public HttpDataSourceException(String str, DataSpec dataSpec, int i2, int i3) {
            super(str, assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }

        @Deprecated
        public HttpDataSourceException(IOException iOException, DataSpec dataSpec, int i2) {
            this(iOException, dataSpec, 2000, i2);
        }

        public HttpDataSourceException(IOException iOException, DataSpec dataSpec, int i2, int i3) {
            super(iOException, assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }

        @Deprecated
        public HttpDataSourceException(String str, IOException iOException, DataSpec dataSpec, int i2) {
            this(str, iOException, dataSpec, 2000, i2);
        }

        public HttpDataSourceException(String str, @Nullable IOException iOException, DataSpec dataSpec, int i2, int i3) {
            super(str, iOException, assignErrorCode(i2, i3));
            this.dataSpec = dataSpec;
            this.type = i3;
        }
    }

    public static final class InvalidContentTypeException extends HttpDataSourceException {
        public final String contentType;

        /* JADX WARN: Illegal instructions before constructor call */
        public InvalidContentTypeException(String str, DataSpec dataSpec) {
            String strValueOf = String.valueOf(str);
            super(strValueOf.length() != 0 ? "Invalid content type: ".concat(strValueOf) : new String("Invalid content type: "), dataSpec, 2003, 1);
            this.contentType = str;
        }
    }

    public static final class InvalidResponseCodeException extends HttpDataSourceException {
        public final Map<String, List<String>> headerFields;
        public final byte[] responseBody;
        public final int responseCode;

        @Nullable
        public final String responseMessage;

        @Deprecated
        public InvalidResponseCodeException(int i2, Map<String, List<String>> map, DataSpec dataSpec) {
            this(i2, null, null, map, dataSpec, Util.EMPTY_BYTE_ARRAY);
        }

        @Deprecated
        public InvalidResponseCodeException(int i2, @Nullable String str, Map<String, List<String>> map, DataSpec dataSpec) {
            this(i2, str, null, map, dataSpec, Util.EMPTY_BYTE_ARRAY);
        }

        public InvalidResponseCodeException(int i2, @Nullable String str, @Nullable IOException iOException, Map<String, List<String>> map, DataSpec dataSpec, byte[] bArr) {
            StringBuilder sb = new StringBuilder(26);
            sb.append("Response code: ");
            sb.append(i2);
            super(sb.toString(), iOException, dataSpec, 2004, 1);
            this.responseCode = i2;
            this.responseMessage = str;
            this.headerFields = map;
            this.responseBody = bArr;
        }
    }

    void clearAllRequestProperties();

    void clearRequestProperty(String str);

    @Override // com.google.android.exoplayer2.upstream.DataSource
    void close() throws HttpDataSourceException;

    int getResponseCode();

    @Override // com.google.android.exoplayer2.upstream.DataSource
    Map<String, List<String>> getResponseHeaders();

    @Override // com.google.android.exoplayer2.upstream.DataSource
    long open(DataSpec dataSpec) throws HttpDataSourceException;

    @Override // com.google.android.exoplayer2.upstream.DataReader
    int read(byte[] bArr, int i2, int i3) throws HttpDataSourceException;

    void setRequestProperty(String str, String str2);

    public static final class RequestProperties {
        private final Map<String, String> requestProperties = new HashMap();

        @Nullable
        private Map<String, String> requestPropertiesSnapshot;

        public synchronized void clear() {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
        }

        public synchronized void clearAndSet(Map<String, String> map) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.clear();
            this.requestProperties.putAll(map);
        }

        public synchronized Map<String, String> getSnapshot() {
            if (this.requestPropertiesSnapshot == null) {
                this.requestPropertiesSnapshot = Collections.unmodifiableMap(new HashMap(this.requestProperties));
            }
            return this.requestPropertiesSnapshot;
        }

        public synchronized void remove(String str) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.remove(str);
        }

        public synchronized void set(String str, String str2) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.put(str, str2);
        }

        public synchronized void set(Map<String, String> map) {
            this.requestPropertiesSnapshot = null;
            this.requestProperties.putAll(map);
        }
    }
}
