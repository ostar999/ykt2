package org.apache.commons.compress.compressors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes9.dex */
public class FileNameUtil {
    private final Map<String, String> compressSuffix = new HashMap();
    private final String defaultExtension;
    private final int longestCompressedSuffix;
    private final int longestUncompressedSuffix;
    private final int shortestCompressedSuffix;
    private final int shortestUncompressedSuffix;
    private final Map<String, String> uncompressSuffix;

    public FileNameUtil(Map<String, String> map, String str) {
        this.uncompressSuffix = Collections.unmodifiableMap(map);
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MAX_VALUE;
        int i5 = Integer.MIN_VALUE;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            int length = entry.getKey().length();
            i2 = length > i2 ? length : i2;
            i3 = length < i3 ? length : i3;
            String value = entry.getValue();
            int length2 = value.length();
            if (length2 > 0) {
                if (!this.compressSuffix.containsKey(value)) {
                    this.compressSuffix.put(value, entry.getKey());
                }
                i5 = length2 > i5 ? length2 : i5;
                if (length2 < i4) {
                    i4 = length2;
                }
            }
        }
        this.longestCompressedSuffix = i2;
        this.longestUncompressedSuffix = i5;
        this.shortestCompressedSuffix = i3;
        this.shortestUncompressedSuffix = i4;
        this.defaultExtension = str;
    }

    public String getCompressedFilename(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int length = lowerCase.length();
        for (int i2 = this.shortestUncompressedSuffix; i2 <= this.longestUncompressedSuffix && i2 < length; i2++) {
            int i3 = length - i2;
            String str2 = this.compressSuffix.get(lowerCase.substring(i3));
            if (str2 != null) {
                return str.substring(0, i3) + str2;
            }
        }
        return str + this.defaultExtension;
    }

    public String getUncompressedFilename(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int length = lowerCase.length();
        for (int i2 = this.shortestCompressedSuffix; i2 <= this.longestCompressedSuffix && i2 < length; i2++) {
            int i3 = length - i2;
            String str2 = this.uncompressSuffix.get(lowerCase.substring(i3));
            if (str2 != null) {
                return str.substring(0, i3) + str2;
            }
        }
        return str;
    }

    public boolean isCompressedFilename(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int length = lowerCase.length();
        for (int i2 = this.shortestCompressedSuffix; i2 <= this.longestCompressedSuffix && i2 < length; i2++) {
            if (this.uncompressSuffix.containsKey(lowerCase.substring(length - i2))) {
                return true;
            }
        }
        return false;
    }
}
