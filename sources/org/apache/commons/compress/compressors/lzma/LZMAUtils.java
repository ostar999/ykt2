package org.apache.commons.compress.compressors.lzma;

import java.util.HashMap;
import org.apache.commons.compress.compressors.FileNameUtil;

/* loaded from: classes9.dex */
public class LZMAUtils {
    private static final byte[] HEADER_MAGIC = {93, 0, 0};
    private static volatile CachedAvailability cachedLZMAAvailability;
    private static final FileNameUtil fileNameUtil;

    public enum CachedAvailability {
        DONT_CACHE,
        CACHED_AVAILABLE,
        CACHED_UNAVAILABLE
    }

    static {
        HashMap map = new HashMap();
        map.put(".lzma", "");
        map.put("-lzma", "");
        fileNameUtil = new FileNameUtil(map, ".lzma");
        cachedLZMAAvailability = CachedAvailability.DONT_CACHE;
        try {
            Class.forName("org.osgi.framework.BundleEvent");
        } catch (Exception unused) {
            setCacheLZMAAvailablity(true);
        }
    }

    private LZMAUtils() {
    }

    public static CachedAvailability getCachedLZMAAvailability() {
        return cachedLZMAAvailability;
    }

    public static String getCompressedFilename(String str) {
        return fileNameUtil.getCompressedFilename(str);
    }

    public static String getUncompressedFilename(String str) {
        return fileNameUtil.getUncompressedFilename(str);
    }

    private static boolean internalIsLZMACompressionAvailable() {
        try {
            LZMACompressorInputStream.matches(null, 0);
            return true;
        } catch (NoClassDefFoundError unused) {
            return false;
        }
    }

    public static boolean isCompressedFilename(String str) {
        return fileNameUtil.isCompressedFilename(str);
    }

    public static boolean isLZMACompressionAvailable() {
        CachedAvailability cachedAvailability = cachedLZMAAvailability;
        return cachedAvailability != CachedAvailability.DONT_CACHE ? cachedAvailability == CachedAvailability.CACHED_AVAILABLE : internalIsLZMACompressionAvailable();
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 < HEADER_MAGIC.length) {
            return false;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr2 = HEADER_MAGIC;
            if (i3 >= bArr2.length) {
                return true;
            }
            if (bArr[i3] != bArr2[i3]) {
                return false;
            }
            i3++;
        }
    }

    public static void setCacheLZMAAvailablity(boolean z2) {
        if (!z2) {
            cachedLZMAAvailability = CachedAvailability.DONT_CACHE;
        } else if (cachedLZMAAvailability == CachedAvailability.DONT_CACHE) {
            cachedLZMAAvailability = internalIsLZMACompressionAvailable() ? CachedAvailability.CACHED_AVAILABLE : CachedAvailability.CACHED_UNAVAILABLE;
        }
    }
}
