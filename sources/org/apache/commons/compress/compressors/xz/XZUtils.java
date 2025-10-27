package org.apache.commons.compress.compressors.xz;

import java.util.HashMap;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.compressors.FileNameUtil;

/* loaded from: classes9.dex */
public class XZUtils {
    private static final byte[] HEADER_MAGIC = {-3, TarConstants.LF_CONTIG, 122, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 90, 0};
    private static volatile CachedAvailability cachedXZAvailability;
    private static final FileNameUtil fileNameUtil;

    public enum CachedAvailability {
        DONT_CACHE,
        CACHED_AVAILABLE,
        CACHED_UNAVAILABLE
    }

    static {
        HashMap map = new HashMap();
        map.put(".txz", ".tar");
        map.put(".xz", "");
        map.put("-xz", "");
        fileNameUtil = new FileNameUtil(map, ".xz");
        cachedXZAvailability = CachedAvailability.DONT_CACHE;
        try {
            Class.forName("org.osgi.framework.BundleEvent");
        } catch (Exception unused) {
            setCacheXZAvailablity(true);
        }
    }

    private XZUtils() {
    }

    public static CachedAvailability getCachedXZAvailability() {
        return cachedXZAvailability;
    }

    public static String getCompressedFilename(String str) {
        return fileNameUtil.getCompressedFilename(str);
    }

    public static String getUncompressedFilename(String str) {
        return fileNameUtil.getUncompressedFilename(str);
    }

    private static boolean internalIsXZCompressionAvailable() {
        try {
            XZCompressorInputStream.matches(null, 0);
            return true;
        } catch (NoClassDefFoundError unused) {
            return false;
        }
    }

    public static boolean isCompressedFilename(String str) {
        return fileNameUtil.isCompressedFilename(str);
    }

    public static boolean isXZCompressionAvailable() {
        CachedAvailability cachedAvailability = cachedXZAvailability;
        return cachedAvailability != CachedAvailability.DONT_CACHE ? cachedAvailability == CachedAvailability.CACHED_AVAILABLE : internalIsXZCompressionAvailable();
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

    public static void setCacheXZAvailablity(boolean z2) {
        if (!z2) {
            cachedXZAvailability = CachedAvailability.DONT_CACHE;
        } else if (cachedXZAvailability == CachedAvailability.DONT_CACHE) {
            cachedXZAvailability = internalIsXZCompressionAvailable() ? CachedAvailability.CACHED_AVAILABLE : CachedAvailability.CACHED_UNAVAILABLE;
        }
    }
}
