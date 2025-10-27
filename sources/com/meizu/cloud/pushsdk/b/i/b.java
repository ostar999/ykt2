package com.meizu.cloud.pushsdk.b.i;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.NetworkOnMainThreadException;
import android.widget.ImageView;
import com.meizu.cloud.pushsdk.b.a.c;
import com.meizu.cloud.pushsdk.b.c.k;
import com.meizu.cloud.pushsdk.b.g.f;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/* loaded from: classes4.dex */
public class b {
    public static int a(int i2, int i3, int i4, int i5) {
        double dMin = Math.min(i2 / i4, i3 / i5);
        float f2 = 1.0f;
        while (true) {
            float f3 = 2.0f * f2;
            if (f3 > dMin) {
                return (int) f2;
            }
            f2 = f3;
        }
    }

    private static int a(int i2, int i3, int i4, int i5, ImageView.ScaleType scaleType) {
        if (i2 == 0 && i3 == 0) {
            return i4;
        }
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            return i2 == 0 ? i4 : i2;
        }
        if (i2 == 0) {
            return (int) (i4 * (i3 / i5));
        }
        if (i3 == 0) {
            return i2;
        }
        double d3 = i5 / i4;
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            double d4 = i3;
            return ((double) i2) * d3 < d4 ? (int) (d4 / d3) : i2;
        }
        double d5 = i3;
        return ((double) i2) * d3 > d5 ? (int) (d5 / d3) : i2;
    }

    public static c<Bitmap> a(k kVar, int i2, int i3, Bitmap.Config config, ImageView.ScaleType scaleType) {
        Bitmap bitmapCreateScaledBitmap;
        byte[] bArrI = new byte[0];
        try {
            bArrI = f.a(kVar.b().a()).i();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i2 == 0 && i3 == 0) {
            options.inPreferredConfig = config;
            bitmapCreateScaledBitmap = BitmapFactory.decodeByteArray(bArrI, 0, bArrI.length, options);
        } else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArrI, 0, bArrI.length, options);
            int i4 = options.outWidth;
            int i5 = options.outHeight;
            int iA = a(i2, i3, i4, i5, scaleType);
            int iA2 = a(i3, i2, i5, i4, scaleType);
            options.inJustDecodeBounds = false;
            options.inSampleSize = a(i4, i5, iA, iA2);
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrI, 0, bArrI.length, options);
            if (bitmapDecodeByteArray == null || (bitmapDecodeByteArray.getWidth() <= iA && bitmapDecodeByteArray.getHeight() <= iA2)) {
                bitmapCreateScaledBitmap = bitmapDecodeByteArray;
            } else {
                bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeByteArray, iA, iA2, true);
                bitmapDecodeByteArray.recycle();
            }
        }
        return bitmapCreateScaledBitmap == null ? c.a(b(new com.meizu.cloud.pushsdk.b.b.a(kVar))) : c.a(bitmapCreateScaledBitmap);
    }

    public static com.meizu.cloud.pushsdk.b.b.a a(com.meizu.cloud.pushsdk.b.b.a aVar) {
        aVar.a("connectionError");
        aVar.a(0);
        aVar.b(aVar.getMessage());
        return aVar;
    }

    public static com.meizu.cloud.pushsdk.b.b.a a(com.meizu.cloud.pushsdk.b.b.a aVar, com.meizu.cloud.pushsdk.b.a.b bVar, int i2) {
        com.meizu.cloud.pushsdk.b.b.a aVarA = bVar.a(aVar);
        aVarA.a(i2);
        aVarA.a("responseFromServerError");
        return aVarA;
    }

    public static com.meizu.cloud.pushsdk.b.b.a a(Exception exc) {
        com.meizu.cloud.pushsdk.b.b.a aVar = new com.meizu.cloud.pushsdk.b.b.a(exc);
        aVar.a(exc instanceof NetworkOnMainThreadException ? "networkOnMainThreadError" : "connectionError");
        aVar.a(0);
        return aVar;
    }

    public static String a(String str) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(str);
        return contentTypeFor == null ? "application/octet-stream" : contentTypeFor;
    }

    public static void a(k kVar, String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream;
        byte[] bArr = new byte[2048];
        InputStream inputStream = null;
        try {
            InputStream inputStreamB = kVar.b().b();
            try {
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                fileOutputStream = new FileOutputStream(new File(file, str2));
                while (true) {
                    try {
                        int i2 = inputStreamB.read(bArr);
                        if (i2 == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i2);
                        }
                    } catch (Throwable th) {
                        th = th;
                        inputStream = inputStreamB;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fileOutputStream == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            throw th;
                        }
                    }
                }
                fileOutputStream.flush();
                try {
                    inputStreamB.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                try {
                    fileOutputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }

    public static com.meizu.cloud.pushsdk.b.b.a b(com.meizu.cloud.pushsdk.b.b.a aVar) {
        aVar.a(0);
        aVar.a("parseError");
        aVar.b(aVar.getMessage());
        return aVar;
    }
}
