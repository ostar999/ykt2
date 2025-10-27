package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class DataSpec {
    public static final int FLAG_ALLOW_CACHE_FRAGMENTATION = 4;
    public static final int FLAG_ALLOW_GZIP = 1;
    public static final int FLAG_DONT_CACHE_IF_LENGTH_UNKNOWN = 2;
    public static final int FLAG_MIGHT_NOT_USE_FULL_NETWORK_SPEED = 8;
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_HEAD = 3;
    public static final int HTTP_METHOD_POST = 2;

    @Deprecated
    public final long absoluteStreamPosition;

    @Nullable
    public final Object customData;
    public final int flags;

    @Nullable
    public final byte[] httpBody;
    public final int httpMethod;
    public final Map<String, String> httpRequestHeaders;

    @Nullable
    public final String key;
    public final long length;
    public final long position;
    public final Uri uri;
    public final long uriPositionOffset;

    public static final class Builder {

        @Nullable
        private Object customData;
        private int flags;

        @Nullable
        private byte[] httpBody;
        private int httpMethod;
        private Map<String, String> httpRequestHeaders;

        @Nullable
        private String key;
        private long length;
        private long position;

        @Nullable
        private Uri uri;
        private long uriPositionOffset;

        public DataSpec build() {
            Assertions.checkStateNotNull(this.uri, "The uri must be set.");
            return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
        }

        public Builder setCustomData(@Nullable Object obj) {
            this.customData = obj;
            return this;
        }

        public Builder setFlags(int i2) {
            this.flags = i2;
            return this;
        }

        public Builder setHttpBody(@Nullable byte[] bArr) {
            this.httpBody = bArr;
            return this;
        }

        public Builder setHttpMethod(int i2) {
            this.httpMethod = i2;
            return this;
        }

        public Builder setHttpRequestHeaders(Map<String, String> map) {
            this.httpRequestHeaders = map;
            return this;
        }

        public Builder setKey(@Nullable String str) {
            this.key = str;
            return this;
        }

        public Builder setLength(long j2) {
            this.length = j2;
            return this;
        }

        public Builder setPosition(long j2) {
            this.position = j2;
            return this;
        }

        public Builder setUri(String str) {
            this.uri = Uri.parse(str);
            return this;
        }

        public Builder setUriPositionOffset(long j2) {
            this.uriPositionOffset = j2;
            return this;
        }

        public Builder() {
            this.httpMethod = 1;
            this.httpRequestHeaders = Collections.emptyMap();
            this.length = -1L;
        }

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        private Builder(DataSpec dataSpec) {
            this.uri = dataSpec.uri;
            this.uriPositionOffset = dataSpec.uriPositionOffset;
            this.httpMethod = dataSpec.httpMethod;
            this.httpBody = dataSpec.httpBody;
            this.httpRequestHeaders = dataSpec.httpRequestHeaders;
            this.position = dataSpec.position;
            this.length = dataSpec.length;
            this.key = dataSpec.key;
            this.flags = dataSpec.flags;
            this.customData = dataSpec.customData;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.datasource");
    }

    public static String getStringForHttpMethod(int i2) {
        if (i2 == 1) {
            return "GET";
        }
        if (i2 == 2) {
            return "POST";
        }
        if (i2 == 3) {
            return "HEAD";
        }
        throw new IllegalStateException();
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public final String getHttpMethodString() {
        return getStringForHttpMethod(this.httpMethod);
    }

    public boolean isFlagSet(int i2) {
        return (this.flags & i2) == i2;
    }

    public DataSpec subrange(long j2) {
        long j3 = this.length;
        return subrange(j2, j3 != -1 ? j3 - j2 : -1L);
    }

    public String toString() {
        String httpMethodString = getHttpMethodString();
        String strValueOf = String.valueOf(this.uri);
        long j2 = this.position;
        long j3 = this.length;
        String str = this.key;
        int i2 = this.flags;
        StringBuilder sb = new StringBuilder(String.valueOf(httpMethodString).length() + 70 + strValueOf.length() + String.valueOf(str).length());
        sb.append("DataSpec[");
        sb.append(httpMethodString);
        sb.append(" ");
        sb.append(strValueOf);
        sb.append(", ");
        sb.append(j2);
        sb.append(", ");
        sb.append(j3);
        sb.append(", ");
        sb.append(str);
        sb.append(", ");
        sb.append(i2);
        sb.append(StrPool.BRACKET_END);
        return sb.toString();
    }

    public DataSpec withAdditionalHeaders(Map<String, String> map) {
        HashMap map2 = new HashMap(this.httpRequestHeaders);
        map2.putAll(map);
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, map2, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withRequestHeaders(Map<String, String> map) {
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, map, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withUri(Uri uri) {
        return new DataSpec(uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec(Uri uri) {
        this(uri, 0L, -1L);
    }

    public DataSpec subrange(long j2, long j3) {
        return (j2 == 0 && this.length == j3) ? this : new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position + j2, j3, this.key, this.flags, this.customData);
    }

    public DataSpec(Uri uri, long j2, long j3) {
        this(uri, 0L, 1, null, Collections.emptyMap(), j2, j3, null, 0, null);
    }

    @Deprecated
    public DataSpec(Uri uri, int i2) {
        this(uri, 0L, -1L, null, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str) {
        this(uri, j2, j2, j3, str, 0);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str, int i2) {
        this(uri, j2, j2, j3, str, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str, int i2, Map<String, String> map) {
        this(uri, 1, null, j2, j2, j3, str, i2, map);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, long j4, @Nullable String str, int i2) {
        this(uri, null, j2, j3, j4, str, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, @Nullable byte[] bArr, long j2, long j3, long j4, @Nullable String str, int i2) {
        this(uri, bArr != null ? 2 : 1, bArr, j2, j3, j4, str, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, int i2, @Nullable byte[] bArr, long j2, long j3, long j4, @Nullable String str, int i3) {
        this(uri, i2, bArr, j2, j3, j4, str, i3, Collections.emptyMap());
    }

    @Deprecated
    public DataSpec(Uri uri, int i2, @Nullable byte[] bArr, long j2, long j3, long j4, @Nullable String str, int i3, Map<String, String> map) {
        this(uri, j2 - j3, i2, bArr, map, j3, j4, str, i3, null);
    }

    private DataSpec(Uri uri, long j2, int i2, @Nullable byte[] bArr, Map<String, String> map, long j3, long j4, @Nullable String str, int i3, @Nullable Object obj) {
        byte[] bArr2 = bArr;
        long j5 = j2 + j3;
        boolean z2 = true;
        Assertions.checkArgument(j5 >= 0);
        Assertions.checkArgument(j3 >= 0);
        if (j4 <= 0 && j4 != -1) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        this.uri = uri;
        this.uriPositionOffset = j2;
        this.httpMethod = i2;
        this.httpBody = (bArr2 == null || bArr2.length == 0) ? null : bArr2;
        this.httpRequestHeaders = Collections.unmodifiableMap(new HashMap(map));
        this.position = j3;
        this.absoluteStreamPosition = j5;
        this.length = j4;
        this.key = str;
        this.flags = i3;
        this.customData = obj;
    }
}
