package net.tsz.afinal.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import java.io.File;

/* loaded from: classes9.dex */
public class Utils {
    private static final long INITIALCRC = -1;
    private static final long POLY64REV = -7661587058870466123L;
    private static final String TAG = "BitmapCommonUtils";
    private static long[] sCrcTable = new long[256];

    static {
        for (int i2 = 0; i2 < 256; i2++) {
            long j2 = i2;
            for (int i3 = 0; i3 < 8; i3++) {
                j2 = (j2 >> 1) ^ ((((int) j2) & 1) != 0 ? POLY64REV : 0L);
            }
            sCrcTable[i2] = j2;
        }
    }

    public static byte[] copyOfRange(byte[] bArr, int i2, int i3) {
        int i4 = i3 - i2;
        if (i4 >= 0) {
            byte[] bArr2 = new byte[i4];
            System.arraycopy(bArr, i2, bArr2, 0, Math.min(bArr.length - i2, i4));
            return bArr2;
        }
        throw new IllegalArgumentException(i2 + " > " + i3);
    }

    public static final long crc64Long(String str) {
        if (str == null || str.length() == 0) {
            return 0L;
        }
        return crc64Long(getBytes(str));
    }

    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static byte[] getBytes(String str) {
        byte[] bArr = new byte[str.length() * 2];
        int i2 = 0;
        for (char c3 : str.toCharArray()) {
            int i3 = i2 + 1;
            bArr[i2] = (byte) (c3 & 255);
            i2 = i3 + 1;
            bArr[i3] = (byte) (c3 >> '\b');
        }
        return bArr;
    }

    public static File getDiskCacheDir(Context context, String str) {
        return new File(("mounted".equals(Environment.getExternalStorageState()) ? getExternalCacheDir(context) : context.getCacheDir()).getPath() + File.separator + str);
    }

    public static File getExternalCacheDir(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/cache/"));
    }

    public static long getUsableSpace(File file) {
        try {
            StatFs statFs = new StatFs(file.getPath());
            return statFs.getBlockSize() * statFs.getAvailableBlocks();
        } catch (Exception e2) {
            Log.e(TAG, "获取 sdcard 缓存大小 出错，请查看AndroidManifest.xml 是否添加了sdcard的访问权限");
            e2.printStackTrace();
            return -1L;
        }
    }

    public static boolean isSameKey(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (bArr2.length < length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] makeKey(String str) {
        return getBytes(str);
    }

    public static final long crc64Long(byte[] bArr) {
        long j2 = -1;
        for (byte b3 : bArr) {
            j2 = (j2 >> 8) ^ sCrcTable[(((int) j2) ^ b3) & 255];
        }
        return j2;
    }
}
