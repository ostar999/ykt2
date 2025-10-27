package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class DataSourceUtil {
    private DataSourceUtil() {
    }

    public static void closeQuietly(@Nullable DataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (IOException unused) {
            }
        }
    }

    public static byte[] readExactly(DataSource dataSource, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = dataSource.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                StringBuilder sb = new StringBuilder(56);
                sb.append("Not enough data could be read: ");
                sb.append(i3);
                sb.append(" < ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
            }
            i3 += i4;
        }
        return bArr;
    }

    public static byte[] readToEnd(DataSource dataSource) throws IOException {
        byte[] bArrCopyOf = new byte[1024];
        int i2 = 0;
        int i3 = 0;
        while (i2 != -1) {
            if (i3 == bArrCopyOf.length) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, bArrCopyOf.length * 2);
            }
            i2 = dataSource.read(bArrCopyOf, i3, bArrCopyOf.length - i3);
            if (i2 != -1) {
                i3 += i2;
            }
        }
        return Arrays.copyOf(bArrCopyOf, i3);
    }
}
