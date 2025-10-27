package org.apache.commons.compress.utils;

import java.io.InputStream;
import java.util.zip.CRC32;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes9.dex */
public class CRC32VerifyingInputStream extends ChecksumVerifyingInputStream {
    public CRC32VerifyingInputStream(InputStream inputStream, long j2, int i2) {
        this(inputStream, j2, i2 & InternalZipConstants.ZIP_64_LIMIT);
    }

    public CRC32VerifyingInputStream(InputStream inputStream, long j2, long j3) {
        super(new CRC32(), inputStream, j2, j3);
    }
}
