package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static final Pattern CONTENT_RANGE_WITH_START_AND_END = Pattern.compile("bytes (\\d+)-(\\d+)/(?:\\d+|\\*)");
    private static final Pattern CONTENT_RANGE_WITH_SIZE = Pattern.compile("bytes (?:(?:\\d+-\\d+)|\\*)/(\\d+)");

    private HttpUtil() {
    }

    @Nullable
    public static String buildRangeRequestHeader(long j2, long j3) {
        if (j2 == 0 && j3 == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("bytes=");
        sb.append(j2);
        sb.append("-");
        if (j3 != -1) {
            sb.append((j2 + j3) - 1);
        }
        return sb.toString();
    }

    public static long getContentLength(@Nullable String str, @Nullable String str2) throws NumberFormatException {
        long j2;
        if (TextUtils.isEmpty(str)) {
            j2 = -1;
        } else {
            try {
                j2 = Long.parseLong(str);
            } catch (NumberFormatException unused) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 28);
                sb.append("Unexpected Content-Length [");
                sb.append(str);
                sb.append(StrPool.BRACKET_END);
                Log.e(TAG, sb.toString());
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return j2;
        }
        Matcher matcher = CONTENT_RANGE_WITH_START_AND_END.matcher(str2);
        if (!matcher.matches()) {
            return j2;
        }
        try {
            long j3 = (Long.parseLong((String) Assertions.checkNotNull(matcher.group(2))) - Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)))) + 1;
            if (j2 < 0) {
                return j3;
            }
            if (j2 == j3) {
                return j2;
            }
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 26 + String.valueOf(str2).length());
            sb2.append("Inconsistent headers [");
            sb2.append(str);
            sb2.append("] [");
            sb2.append(str2);
            sb2.append(StrPool.BRACKET_END);
            Log.w(TAG, sb2.toString());
            return Math.max(j2, j3);
        } catch (NumberFormatException unused2) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str2).length() + 27);
            sb3.append("Unexpected Content-Range [");
            sb3.append(str2);
            sb3.append(StrPool.BRACKET_END);
            Log.e(TAG, sb3.toString());
            return j2;
        }
    }

    public static long getDocumentSize(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        Matcher matcher = CONTENT_RANGE_WITH_SIZE.matcher(str);
        if (matcher.matches()) {
            return Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)));
        }
        return -1L;
    }
}
