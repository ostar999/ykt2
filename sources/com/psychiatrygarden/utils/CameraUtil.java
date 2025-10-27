package com.psychiatrygarden.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.psychiatrygarden.ProjectApp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class CameraUtil {
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int i2 = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {
            byteArrayOutputStream.reset();
            image.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
            i2 -= 10;
        }
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap getBitmapFormUri(android.app.Activity r7, android.net.Uri r8) throws java.io.IOException {
        /*
            android.content.ContentResolver r0 = r7.getContentResolver()
            java.io.InputStream r0 = r0.openInputStream(r8)
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options
            r1.<init>()
            r2 = 1
            r1.inJustDecodeBounds = r2
            r1.inDither = r2
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888
            r1.inPreferredConfig = r3
            r3 = 0
            android.graphics.BitmapFactory.decodeStream(r0, r3, r1)
            r0.close()
            int r0 = r1.outWidth
            int r1 = r1.outHeight
            r4 = -1
            if (r0 == r4) goto L64
            if (r1 != r4) goto L27
            goto L64
        L27:
            if (r0 <= r1) goto L33
            float r4 = (float) r0
            r5 = 1144258560(0x44340000, float:720.0)
            int r6 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r6 <= 0) goto L33
            float r4 = r4 / r5
            int r0 = (int) r4
            goto L40
        L33:
            if (r0 >= r1) goto L3f
            float r0 = (float) r1
            r1 = 1149698048(0x44870000, float:1080.0)
            int r4 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r4 <= 0) goto L3f
            float r0 = r0 / r1
            int r0 = (int) r0
            goto L40
        L3f:
            r0 = r2
        L40:
            if (r0 > 0) goto L43
            r0 = r2
        L43:
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options
            r1.<init>()
            r1.inSampleSize = r0
            r1.inDither = r2
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888
            r1.inPreferredConfig = r0
            android.content.ContentResolver r7 = r7.getContentResolver()
            java.io.InputStream r7 = r7.openInputStream(r8)
            android.graphics.Bitmap r8 = android.graphics.BitmapFactory.decodeStream(r7, r3, r1)
            r7.close()
            android.graphics.Bitmap r7 = compressImage(r8)
            return r7
        L64:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.CameraUtil.getBitmapFormUri(android.app.Activity, android.net.Uri):android.graphics.Bitmap");
    }

    public static File saveHeadimage2(Bitmap bmp, String dirname) throws IOException {
        File file = new File(ProjectApp.instance().getFilesDir().getPath());
        try {
            if (!file.exists()) {
                file.mkdir();
            }
            File file2 = new File(file, dirname);
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            byte[] bArr = new byte[byteArrayInputStream.available()];
            byteArrayInputStream.read(bArr);
            byteArrayInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            return file2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
