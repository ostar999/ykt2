package tv.cjump.jni;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import androidx.core.internal.view.SupportMenu;
import java.lang.reflect.Field;

/* loaded from: classes9.dex */
public class NativeBitmapFactory {
    static Field nativeIntField = null;
    static boolean nativeLibLoaded = false;
    static boolean notLoadAgain = false;

    private static native Bitmap createBitmap(int i2, int i3, int i4, boolean z2);

    public static Bitmap createBitmap(int i2, int i3, Bitmap.Config config) {
        return createBitmap(i2, i3, config, config.equals(Bitmap.Config.ARGB_4444) || config.equals(Bitmap.Config.ARGB_8888));
    }

    private static native Bitmap createBitmap19(int i2, int i3, int i4, boolean z2);

    private static Bitmap createNativeBitmap(int i2, int i3, Bitmap.Config config, boolean z2) {
        return createBitmap(i2, i3, getNativeConfig(config), z2);
    }

    public static int getNativeConfig(Bitmap.Config config) {
        try {
            Field field = nativeIntField;
            if (field == null) {
                return 0;
            }
            return field.getInt(config);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return 0;
        }
    }

    private static native boolean init();

    public static void initField() throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = Bitmap.Config.class.getDeclaredField("nativeInt");
            nativeIntField = declaredField;
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException e2) {
            nativeIntField = null;
            e2.printStackTrace();
        }
    }

    public static boolean isInNativeAlloc() {
        return nativeLibLoaded && nativeIntField != null;
    }

    public static void loadLibs() throws NoSuchFieldException, SecurityException {
        if (notLoadAgain) {
            return;
        }
        if (!DeviceUtils.isRealARMArch() && !DeviceUtils.isRealX86Arch()) {
            notLoadAgain = true;
            nativeLibLoaded = false;
            return;
        }
        if (nativeLibLoaded) {
            return;
        }
        try {
            notLoadAgain = true;
            nativeLibLoaded = false;
        } catch (Error e2) {
            e2.printStackTrace();
            notLoadAgain = true;
            nativeLibLoaded = false;
        } catch (Exception e3) {
            e3.printStackTrace();
            notLoadAgain = true;
            nativeLibLoaded = false;
        }
        if (nativeLibLoaded) {
            if (init()) {
                initField();
                if (!testLib()) {
                    release();
                    notLoadAgain = true;
                    nativeLibLoaded = false;
                }
            } else {
                release();
                notLoadAgain = true;
                nativeLibLoaded = false;
            }
        }
        Log.e("NativeBitmapFactory", "loaded" + nativeLibLoaded);
    }

    public static void recycle(Bitmap bitmap) {
        bitmap.recycle();
    }

    private static native boolean release();

    public static synchronized void releaseLibs() {
        boolean z2 = nativeLibLoaded;
        nativeIntField = null;
        nativeLibLoaded = false;
        if (z2) {
            release();
        }
    }

    @SuppressLint({"NewApi"})
    private static boolean testLib() {
        if (nativeIntField == null) {
            return false;
        }
        Bitmap bitmapCreateNativeBitmap = null;
        try {
            try {
                bitmapCreateNativeBitmap = createNativeBitmap(2, 2, Bitmap.Config.ARGB_8888, true);
                boolean zIsPremultiplied = bitmapCreateNativeBitmap != null && bitmapCreateNativeBitmap.getWidth() == 2 && bitmapCreateNativeBitmap.getHeight() == 2;
                if (zIsPremultiplied) {
                    if (!bitmapCreateNativeBitmap.isPremultiplied()) {
                        bitmapCreateNativeBitmap.setPremultiplied(true);
                    }
                    Canvas canvas = new Canvas(bitmapCreateNativeBitmap);
                    Paint paint = new Paint();
                    paint.setColor(SupportMenu.CATEGORY_MASK);
                    paint.setTextSize(20.0f);
                    canvas.drawRect(0.0f, 0.0f, bitmapCreateNativeBitmap.getWidth(), bitmapCreateNativeBitmap.getHeight(), paint);
                    canvas.drawText("TestLib", 0.0f, 0.0f, paint);
                    zIsPremultiplied = bitmapCreateNativeBitmap.isPremultiplied();
                }
                if (bitmapCreateNativeBitmap != null) {
                    bitmapCreateNativeBitmap.recycle();
                }
                return zIsPremultiplied;
            } catch (Error unused) {
                if (bitmapCreateNativeBitmap != null) {
                    bitmapCreateNativeBitmap.recycle();
                }
                return false;
            } catch (Exception e2) {
                Log.e("NativeBitmapFactory", "exception:" + e2.toString());
                if (bitmapCreateNativeBitmap != null) {
                    bitmapCreateNativeBitmap.recycle();
                }
                return false;
            }
        } catch (Throwable th) {
            if (bitmapCreateNativeBitmap != null) {
                bitmapCreateNativeBitmap.recycle();
            }
            throw th;
        }
    }

    public static synchronized Bitmap createBitmap(int i2, int i3, Bitmap.Config config, boolean z2) {
        if (nativeLibLoaded && nativeIntField != null) {
            return createNativeBitmap(i2, i3, config, z2);
        }
        return Bitmap.createBitmap(i2, i3, config);
    }
}
