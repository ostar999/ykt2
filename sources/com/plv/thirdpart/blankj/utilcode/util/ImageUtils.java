package com.plv.thirdpart.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes5.dex */
public final class ImageUtils {
    private ImageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Bitmap addBorder(Bitmap bitmap, @IntRange(from = 1) int i2, @ColorInt int i3, boolean z2, float f2, boolean z3) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        if (!z3) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(1);
        paint.setColor(i3);
        paint.setStyle(Paint.Style.STROKE);
        float f3 = i2;
        paint.setStrokeWidth(f3);
        if (z2) {
            canvas.drawCircle(width / 2.0f, height / 2.0f, (Math.min(width, height) / 2.0f) - (f3 / 2.0f), paint);
        } else {
            float f4 = i2 >> 1;
            canvas.drawRoundRect(new RectF(f4, f4, width - r5, height - r5), f2, f2, paint);
        }
        return bitmap;
    }

    public static Bitmap addCircleBorder(Bitmap bitmap, @IntRange(from = 1) int i2, @ColorInt int i3) {
        return addBorder(bitmap, i2, i3, true, 0.0f, false);
    }

    public static Bitmap addCornerBorder(Bitmap bitmap, @IntRange(from = 1) int i2, @ColorInt int i3, @FloatRange(from = 0.0d) float f2) {
        return addBorder(bitmap, i2, i3, false, f2, false);
    }

    public static Bitmap addImageWatermark(Bitmap bitmap, Bitmap bitmap2, int i2, int i3, int i4) {
        return addImageWatermark(bitmap, bitmap2, i2, i3, i4, false);
    }

    public static Bitmap addReflection(Bitmap bitmap, int i2) {
        return addReflection(bitmap, i2, false);
    }

    public static Bitmap addTextWatermark(Bitmap bitmap, String str, int i2, @ColorInt int i3, float f2, float f3) {
        return addTextWatermark(bitmap, str, i2, i3, f2, f3, false);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(Utils.getApp().getResources(), bitmap);
    }

    public static Bitmap bytes2Bitmap(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }

    public static Drawable bytes2Drawable(byte[] bArr) {
        return bitmap2Drawable(bytes2Bitmap(bArr));
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int i2, int i3) {
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        int i6 = 1;
        while (true) {
            i5 >>= 1;
            if (i5 < i2 || (i4 = i4 >> 1) < i3) {
                break;
            }
            i6 <<= 1;
        }
        return i6;
    }

    public static Bitmap clip(Bitmap bitmap, int i2, int i3, int i4, int i5) {
        return clip(bitmap, i2, i3, i4, i5, false);
    }

    public static Bitmap compressByQuality(Bitmap bitmap, @IntRange(from = 0, to = 100) int i2) {
        return compressByQuality(bitmap, i2, false);
    }

    public static Bitmap compressBySampleSize(Bitmap bitmap, int i2) {
        return compressBySampleSize(bitmap, i2, false);
    }

    public static Bitmap compressByScale(Bitmap bitmap, int i2, int i3) {
        return scale(bitmap, i2, i3, false);
    }

    private static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmapCreateBitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat compressFormat) {
        if (drawable == null) {
            return null;
        }
        return bitmap2Bytes(drawable2Bitmap(drawable), compressFormat);
    }

    public static Bitmap fastBlur(Bitmap bitmap, @FloatRange(from = 0.0d, fromInclusive = false, to = 1.0d) float f2, @FloatRange(from = 0.0d, fromInclusive = false, to = 25.0d) float f3) {
        return fastBlur(bitmap, f2, f3, false);
    }

    public static String fixImageUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.startsWith("//")) {
            return URIUtil.HTTP_COLON + str;
        }
        if (!str.startsWith("/")) {
            return str;
        }
        return "http://livestatic.videocc.net" + str;
    }

    public static Bitmap getBitmap(File file) {
        if (file == null) {
            return null;
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    private static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    public static String getImageType(String str) {
        return getImageType(getFileByPath(str));
    }

    public static int getRotateDegree(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt != 3) {
                return attributeInt != 8 ? 90 : 270;
            }
            return 180;
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    private static byte[] input2Byte(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = inputStream.read(bArr, 0, 1024);
                    if (i2 == -1) {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        CloseUtils.closeIO(inputStream);
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                CloseUtils.closeIO(inputStream);
                return null;
            }
        } catch (Throwable th) {
            CloseUtils.closeIO(inputStream);
            throw th;
        }
    }

    private static boolean isBMP(byte[] bArr) {
        return bArr.length >= 2 && bArr[0] == 66 && bArr[1] == 77;
    }

    private static boolean isEmptyBitmap(Bitmap bitmap) {
        return bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0;
    }

    private static boolean isGIF(byte[] bArr) {
        if (bArr.length < 6 || bArr[0] != 71 || bArr[1] != 73 || bArr[2] != 70 || bArr[3] != 56) {
            return false;
        }
        byte b3 = bArr[4];
        return (b3 == 55 || b3 == 57) && bArr[5] == 97;
    }

    public static boolean isImage(File file) {
        return file != null && isImage(file.getPath());
    }

    private static boolean isJPEG(byte[] bArr) {
        return bArr.length >= 2 && bArr[0] == -1 && bArr[1] == -40;
    }

    private static boolean isPNG(byte[] bArr) {
        return bArr.length >= 8 && bArr[0] == -119 && bArr[1] == 80 && bArr[2] == 78 && bArr[3] == 71 && bArr[4] == 13 && bArr[5] == 10 && bArr[6] == 26 && bArr[7] == 10;
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(17)
    public static Bitmap renderScriptBlur(Bitmap bitmap, @FloatRange(from = 0.0d, fromInclusive = false, to = 25.0d) float f2) {
        return renderScriptBlur(bitmap, f2, false);
    }

    public static Bitmap rotate(Bitmap bitmap, int i2, float f2, float f3) {
        return rotate(bitmap, i2, f2, f3, false);
    }

    public static boolean save(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat) {
        return save(bitmap, getFileByPath(str), compressFormat, false);
    }

    public static Bitmap scale(Bitmap bitmap, int i2, int i3) {
        return scale(bitmap, i2, i3, false);
    }

    public static Bitmap skew(Bitmap bitmap, float f2, float f3) {
        return skew(bitmap, f2, f3, 0.0f, 0.0f, false);
    }

    public static Bitmap stackBlur(Bitmap bitmap, int i2) {
        return stackBlur(bitmap, i2, false);
    }

    public static Bitmap toAlpha(Bitmap bitmap) {
        return toAlpha(bitmap, Boolean.FALSE);
    }

    public static Bitmap toGray(Bitmap bitmap) {
        return toGray(bitmap, false);
    }

    public static Bitmap toRound(Bitmap bitmap) {
        return toRound(bitmap, 0, 0, false);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, float f2) {
        return toRoundCorner(bitmap, f2, 0, 0, false);
    }

    public static Bitmap view2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Bitmap addCircleBorder(Bitmap bitmap, @IntRange(from = 1) int i2, @ColorInt int i3, boolean z2) {
        return addBorder(bitmap, i2, i3, true, 0.0f, z2);
    }

    public static Bitmap addCornerBorder(Bitmap bitmap, @IntRange(from = 1) int i2, @ColorInt int i3, @FloatRange(from = 0.0d) float f2, boolean z2) {
        return addBorder(bitmap, i2, i3, false, f2, z2);
    }

    public static Bitmap addImageWatermark(Bitmap bitmap, Bitmap bitmap2, int i2, int i3, int i4, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        if (!isEmptyBitmap(bitmap2)) {
            Paint paint = new Paint(1);
            Canvas canvas = new Canvas(bitmapCopy);
            paint.setAlpha(i4);
            canvas.drawBitmap(bitmap2, i2, i3, paint);
        }
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCopy;
    }

    public static Bitmap addReflection(Bitmap bitmap, int i2, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, height - i2, width, i2, matrix, false);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(width, height + i2, bitmap.getConfig());
        Canvas canvas = new Canvas(bitmapCreateBitmap2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        float f2 = height + 0;
        canvas.drawBitmap(bitmapCreateBitmap, 0.0f, f2, (Paint) null);
        Paint paint = new Paint(1);
        paint.setShader(new LinearGradient(0.0f, height, 0.0f, bitmapCreateBitmap2.getHeight() + 0, 1895825407, 16777215, Shader.TileMode.MIRROR));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0.0f, f2, width, bitmapCreateBitmap2.getHeight(), paint);
        if (!bitmapCreateBitmap.isRecycled()) {
            bitmapCreateBitmap.recycle();
        }
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap2;
    }

    public static Bitmap addTextWatermark(Bitmap bitmap, String str, float f2, @ColorInt int i2, float f3, float f4, boolean z2) {
        if (isEmptyBitmap(bitmap) || str == null) {
            return null;
        }
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        Paint paint = new Paint(1);
        Canvas canvas = new Canvas(bitmapCopy);
        paint.setColor(i2);
        paint.setTextSize(f2);
        paint.getTextBounds(str, 0, str.length(), new Rect());
        canvas.drawText(str, f3, f4 + f2, paint);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCopy;
    }

    public static Bitmap clip(Bitmap bitmap, int i2, int i3, int i4, int i5, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, i2, i3, i4, i5);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap compressByQuality(Bitmap bitmap, @IntRange(from = 0, to = 100) int i2, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static Bitmap compressBySampleSize(Bitmap bitmap, int i2, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }

    public static Bitmap compressByScale(Bitmap bitmap, int i2, int i3, boolean z2) {
        return scale(bitmap, i2, i3, z2);
    }

    public static Bitmap fastBlur(Bitmap bitmap, @FloatRange(from = 0.0d, fromInclusive = false, to = 1.0d) float f2, @FloatRange(from = 0.0d, fromInclusive = false, to = 25.0d) float f3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Paint paint = new Paint(3);
        Canvas canvas = new Canvas();
        paint.setColorFilter(new PorterDuffColorFilter(0, PorterDuff.Mode.SRC_ATOP));
        canvas.scale(f2, f2);
        canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
        Bitmap bitmapRenderScriptBlur = renderScriptBlur(bitmapCreateBitmap, f3, z2);
        if (f2 == 1.0f) {
            return bitmapRenderScriptBlur;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapRenderScriptBlur, width, height, true);
        if (bitmapRenderScriptBlur != null && !bitmapRenderScriptBlur.isRecycled()) {
            bitmapRenderScriptBlur.recycle();
        }
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateScaledBitmap;
    }

    public static Bitmap getBitmap(File file, int i2, int i3) {
        if (file == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static String getImageType(File file) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        if (file == null) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
        } catch (IOException e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th) {
            th = th;
            CloseUtils.closeIO(fileInputStream2);
            throw th;
        }
        try {
            try {
                String imageType = getImageType(fileInputStream);
                CloseUtils.closeIO(fileInputStream);
                return imageType;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                CloseUtils.closeIO(fileInputStream2);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            CloseUtils.closeIO(fileInputStream);
            return null;
        }
    }

    public static boolean isImage(String str) {
        String upperCase = str.toUpperCase();
        return upperCase.endsWith(".PNG") || upperCase.endsWith(".JPG") || upperCase.endsWith(".JPEG") || upperCase.endsWith(".BMP") || upperCase.endsWith(".GIF");
    }

    @TargetApi(17)
    public static Bitmap renderScriptBlur(Bitmap bitmap, @FloatRange(from = 0.0d, fromInclusive = false, to = 25.0d) float f2, boolean z2) {
        RenderScript renderScriptCreate = null;
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        if (!z2) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
        }
        try {
            renderScriptCreate = RenderScript.create(Utils.getApp());
            renderScriptCreate.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            Allocation allocationCreateTyped = Allocation.createTyped(renderScriptCreate, allocationCreateFromBitmap.getType());
            ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
            scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
            scriptIntrinsicBlurCreate.setRadius(f2);
            scriptIntrinsicBlurCreate.forEach(allocationCreateTyped);
            allocationCreateTyped.copyTo(bitmap);
            renderScriptCreate.destroy();
            return bitmap;
        } catch (Throwable th) {
            if (renderScriptCreate != null) {
                renderScriptCreate.destroy();
            }
            throw th;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int i2, float f2, float f3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        if (i2 == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i2, f2, f3);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static boolean save(Bitmap bitmap, File file, Bitmap.CompressFormat compressFormat) {
        return save(bitmap, file, compressFormat, false);
    }

    public static Bitmap scale(Bitmap bitmap, int i2, int i3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, i2, i3, true);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateScaledBitmap;
    }

    public static Bitmap skew(Bitmap bitmap, float f2, float f3, boolean z2) {
        return skew(bitmap, f2, f3, 0.0f, 0.0f, z2);
    }

    public static Bitmap stackBlur(Bitmap bitmap, int i2, boolean z2) {
        int[] iArr;
        int i3 = i2;
        Bitmap bitmapCopy = z2 ? bitmap : bitmap.copy(bitmap.getConfig(), true);
        if (i3 < 1) {
            return null;
        }
        int width = bitmapCopy.getWidth();
        int height = bitmapCopy.getHeight();
        int i4 = width * height;
        int[] iArr2 = new int[i4];
        bitmapCopy.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i5 = width - 1;
        int i6 = height - 1;
        int i7 = i3 + i3 + 1;
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int[] iArr5 = new int[i4];
        int[] iArr6 = new int[Math.max(width, height)];
        int i8 = (i7 + 1) >> 1;
        int i9 = i8 * i8;
        int i10 = i9 * 256;
        int[] iArr7 = new int[i10];
        for (int i11 = 0; i11 < i10; i11++) {
            iArr7[i11] = i11 / i9;
        }
        int[][] iArr8 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i7, 3);
        int i12 = i3 + 1;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        while (i13 < height) {
            Bitmap bitmap2 = bitmapCopy;
            int i16 = height;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            int i25 = -i3;
            int i26 = 0;
            while (i25 <= i3) {
                int i27 = i6;
                int[] iArr9 = iArr6;
                int i28 = iArr2[i14 + Math.min(i5, Math.max(i25, 0))];
                int[] iArr10 = iArr8[i25 + i3];
                iArr10[0] = (i28 & 16711680) >> 16;
                iArr10[1] = (i28 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr10[2] = i28 & 255;
                int iAbs = i12 - Math.abs(i25);
                int i29 = iArr10[0];
                i26 += i29 * iAbs;
                int i30 = iArr10[1];
                i17 += i30 * iAbs;
                int i31 = iArr10[2];
                i18 += iAbs * i31;
                if (i25 > 0) {
                    i22 += i29;
                    i23 += i30;
                    i24 += i31;
                } else {
                    i19 += i29;
                    i20 += i30;
                    i21 += i31;
                }
                i25++;
                i6 = i27;
                iArr6 = iArr9;
            }
            int i32 = i6;
            int[] iArr11 = iArr6;
            int i33 = i3;
            int i34 = i26;
            int i35 = 0;
            while (i35 < width) {
                iArr3[i14] = iArr7[i34];
                iArr4[i14] = iArr7[i17];
                iArr5[i14] = iArr7[i18];
                int i36 = i34 - i19;
                int i37 = i17 - i20;
                int i38 = i18 - i21;
                int[] iArr12 = iArr8[((i33 - i3) + i7) % i7];
                int i39 = i19 - iArr12[0];
                int i40 = i20 - iArr12[1];
                int i41 = i21 - iArr12[2];
                if (i13 == 0) {
                    iArr = iArr7;
                    iArr11[i35] = Math.min(i35 + i3 + 1, i5);
                } else {
                    iArr = iArr7;
                }
                int i42 = iArr2[i15 + iArr11[i35]];
                int i43 = (i42 & 16711680) >> 16;
                iArr12[0] = i43;
                int i44 = (i42 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr12[1] = i44;
                int i45 = i42 & 255;
                iArr12[2] = i45;
                int i46 = i22 + i43;
                int i47 = i23 + i44;
                int i48 = i24 + i45;
                i34 = i36 + i46;
                i17 = i37 + i47;
                i18 = i38 + i48;
                i33 = (i33 + 1) % i7;
                int[] iArr13 = iArr8[i33 % i7];
                int i49 = iArr13[0];
                i19 = i39 + i49;
                int i50 = iArr13[1];
                i20 = i40 + i50;
                int i51 = iArr13[2];
                i21 = i41 + i51;
                i22 = i46 - i49;
                i23 = i47 - i50;
                i24 = i48 - i51;
                i14++;
                i35++;
                iArr7 = iArr;
            }
            i15 += width;
            i13++;
            bitmapCopy = bitmap2;
            height = i16;
            i6 = i32;
            iArr6 = iArr11;
        }
        Bitmap bitmap3 = bitmapCopy;
        int i52 = i6;
        int[] iArr14 = iArr6;
        int i53 = height;
        int[] iArr15 = iArr7;
        int i54 = 0;
        while (i54 < width) {
            int i55 = -i3;
            int i56 = i7;
            int[] iArr16 = iArr2;
            int i57 = 0;
            int i58 = 0;
            int i59 = 0;
            int i60 = 0;
            int i61 = 0;
            int i62 = 0;
            int i63 = 0;
            int i64 = i55;
            int i65 = i55 * width;
            int i66 = 0;
            int i67 = 0;
            while (i64 <= i3) {
                int i68 = width;
                int iMax = Math.max(0, i65) + i54;
                int[] iArr17 = iArr8[i64 + i3];
                iArr17[0] = iArr3[iMax];
                iArr17[1] = iArr4[iMax];
                iArr17[2] = iArr5[iMax];
                int iAbs2 = i12 - Math.abs(i64);
                i66 += iArr3[iMax] * iAbs2;
                i67 += iArr4[iMax] * iAbs2;
                i57 += iArr5[iMax] * iAbs2;
                if (i64 > 0) {
                    i61 += iArr17[0];
                    i62 += iArr17[1];
                    i63 += iArr17[2];
                } else {
                    i58 += iArr17[0];
                    i59 += iArr17[1];
                    i60 += iArr17[2];
                }
                int i69 = i52;
                if (i64 < i69) {
                    i65 += i68;
                }
                i64++;
                i52 = i69;
                width = i68;
            }
            int i70 = width;
            int i71 = i52;
            int i72 = i3;
            int i73 = i54;
            int i74 = i53;
            int i75 = 0;
            while (i75 < i74) {
                iArr16[i73] = (iArr16[i73] & (-16777216)) | (iArr15[i66] << 16) | (iArr15[i67] << 8) | iArr15[i57];
                int i76 = i66 - i58;
                int i77 = i67 - i59;
                int i78 = i57 - i60;
                int[] iArr18 = iArr8[((i72 - i3) + i56) % i56];
                int i79 = i58 - iArr18[0];
                int i80 = i59 - iArr18[1];
                int i81 = i60 - iArr18[2];
                if (i54 == 0) {
                    iArr14[i75] = Math.min(i75 + i12, i71) * i70;
                }
                int i82 = iArr14[i75] + i54;
                int i83 = iArr3[i82];
                iArr18[0] = i83;
                int i84 = iArr4[i82];
                iArr18[1] = i84;
                int i85 = iArr5[i82];
                iArr18[2] = i85;
                int i86 = i61 + i83;
                int i87 = i62 + i84;
                int i88 = i63 + i85;
                i66 = i76 + i86;
                i67 = i77 + i87;
                i57 = i78 + i88;
                i72 = (i72 + 1) % i56;
                int[] iArr19 = iArr8[i72];
                int i89 = iArr19[0];
                i58 = i79 + i89;
                int i90 = iArr19[1];
                i59 = i80 + i90;
                int i91 = iArr19[2];
                i60 = i81 + i91;
                i61 = i86 - i89;
                i62 = i87 - i90;
                i63 = i88 - i91;
                i73 += i70;
                i75++;
                i3 = i2;
            }
            i54++;
            i3 = i2;
            i52 = i71;
            i53 = i74;
            i7 = i56;
            iArr2 = iArr16;
            width = i70;
        }
        int i92 = width;
        bitmap3.setPixels(iArr2, 0, i92, 0, 0, i92, i53);
        return bitmap3;
    }

    public static Bitmap toAlpha(Bitmap bitmap, Boolean bool) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Bitmap bitmapExtractAlpha = bitmap.extractAlpha();
        if (bool.booleanValue() && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapExtractAlpha;
    }

    public static Bitmap toGray(Bitmap bitmap, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap toRound(Bitmap bitmap, boolean z2) {
        return toRound(bitmap, 0, 0, z2);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, float f2, boolean z2) {
        return toRoundCorner(bitmap, f2, 0, 0, z2);
    }

    public static Bitmap compressByScale(Bitmap bitmap, float f2, float f3) {
        return scale(bitmap, f2, f3, false);
    }

    public static boolean save(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat, boolean z2) {
        return save(bitmap, getFileByPath(str), compressFormat, z2);
    }

    public static Bitmap skew(Bitmap bitmap, float f2, float f3, float f4, float f5) {
        return skew(bitmap, f2, f3, f4, f5, false);
    }

    public static Bitmap toRound(Bitmap bitmap, @IntRange(from = 0) int i2, @ColorInt int i3) {
        return toRound(bitmap, i2, i3, false);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, float f2, @IntRange(from = 0) int i2, @ColorInt int i3) {
        return toRoundCorner(bitmap, f2, i2, i3, false);
    }

    public static Bitmap compressByScale(Bitmap bitmap, float f2, float f3, boolean z2) {
        return scale(bitmap, f2, f3, z2);
    }

    public static boolean save(Bitmap bitmap, File file, Bitmap.CompressFormat compressFormat, boolean z2) throws Throwable {
        boolean zCompress;
        if (isEmptyBitmap(bitmap) || !createFileByDeleteOldFile(file)) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
                try {
                    try {
                        zCompress = bitmap.compress(compressFormat, 100, bufferedOutputStream2);
                        if (z2) {
                            try {
                                if (!bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                            } catch (IOException e2) {
                                e = e2;
                                bufferedOutputStream = bufferedOutputStream2;
                                e.printStackTrace();
                                CloseUtils.closeIO(bufferedOutputStream);
                                return zCompress;
                            }
                        }
                        CloseUtils.closeIO(bufferedOutputStream2);
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        CloseUtils.closeIO(bufferedOutputStream);
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    zCompress = false;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e = e4;
            zCompress = false;
        }
        return zCompress;
    }

    public static Bitmap skew(Bitmap bitmap, float f2, float f3, float f4, float f5, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setSkew(f2, f3, f4, f5);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap toRound(Bitmap bitmap, @IntRange(from = 0) int i2, @ColorInt int i3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int iMin = Math.min(width, height);
        Paint paint = new Paint(1);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        float f2 = iMin;
        float f3 = f2 / 2.0f;
        float f4 = width;
        float f5 = height;
        RectF rectF = new RectF(0.0f, 0.0f, f4, f5);
        rectF.inset((width - iMin) / 2.0f, (height - iMin) / 2.0f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        matrix.preScale(f2 / f4, f2 / f5);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawRoundRect(rectF, f3, f3, paint);
        if (i2 > 0) {
            paint.setShader(null);
            paint.setColor(i3);
            paint.setStyle(Paint.Style.STROKE);
            float f6 = i2;
            paint.setStrokeWidth(f6);
            canvas.drawCircle(f4 / 2.0f, f5 / 2.0f, f3 - (f6 / 2.0f), paint);
        }
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, float f2, @IntRange(from = 0) int i2, @ColorInt int i3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Paint paint = new Paint(1);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        RectF rectF = new RectF(0.0f, 0.0f, width, height);
        float f3 = i2;
        float f4 = f3 / 2.0f;
        rectF.inset(f4, f4);
        canvas.drawRoundRect(rectF, f2, f2, paint);
        if (i2 > 0) {
            paint.setShader(null);
            paint.setColor(i3);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(f3);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRoundRect(rectF, f2, f2, paint);
        }
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap scale(Bitmap bitmap, float f2, float f3) {
        return scale(bitmap, f2, f3, false);
    }

    public static Bitmap scale(Bitmap bitmap, float f2, float f3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f3);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap compressByQuality(Bitmap bitmap, long j2) {
        return compressByQuality(bitmap, j2, false);
    }

    public static Bitmap getBitmap(String str) {
        if (isSpace(str)) {
            return null;
        }
        return BitmapFactory.decodeFile(str);
    }

    public static String getImageType(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            byte[] bArr = new byte[8];
            if (inputStream.read(bArr, 0, 8) != -1) {
                return getImageType(bArr);
            }
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap compressByQuality(Bitmap bitmap, long j2, boolean z2) {
        byte[] byteArray;
        if (isEmptyBitmap(bitmap) || j2 <= 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        if (byteArrayOutputStream.size() <= j2) {
            byteArray = byteArrayOutputStream.toByteArray();
        } else {
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
            if (byteArrayOutputStream.size() >= j2) {
                byteArray = byteArrayOutputStream.toByteArray();
            } else {
                int i3 = 0;
                int i4 = 0;
                while (i3 < i2) {
                    i4 = (i3 + i2) / 2;
                    byteArrayOutputStream.reset();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, i4, byteArrayOutputStream);
                    long size = byteArrayOutputStream.size();
                    if (size == j2) {
                        break;
                    }
                    if (size > j2) {
                        i2 = i4 - 1;
                    } else {
                        i3 = i4 + 1;
                    }
                }
                if (i2 == i4 - 1) {
                    byteArrayOutputStream.reset();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, i3, byteArrayOutputStream);
                }
                byteArray = byteArrayOutputStream.toByteArray();
            }
        }
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static Bitmap compressBySampleSize(Bitmap bitmap, int i2, int i3) {
        return compressBySampleSize(bitmap, i2, i3, false);
    }

    public static Bitmap getBitmap(String str, int i2, int i3) {
        if (isSpace(str)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static String getImageType(byte[] bArr) {
        if (isJPEG(bArr)) {
            return "JPEG";
        }
        if (isGIF(bArr)) {
            return "GIF";
        }
        if (isPNG(bArr)) {
            return "PNG";
        }
        if (isBMP(bArr)) {
            return "BMP";
        }
        return null;
    }

    public static Bitmap compressBySampleSize(Bitmap bitmap, int i2, int i3, boolean z2) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        if (z2 && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }

    public static Bitmap getBitmap(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    public static Bitmap getBitmap(InputStream inputStream, int i2, int i3) {
        if (inputStream == null) {
            return null;
        }
        return getBitmap(input2Byte(inputStream), 0, i2, i3);
    }

    public static Bitmap getBitmap(byte[] bArr, int i2) {
        if (bArr.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bArr, i2, bArr.length);
    }

    public static Bitmap getBitmap(byte[] bArr, int i2, int i3, int i4) {
        if (bArr.length == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr, i2, bArr.length, options);
        options.inSampleSize = calculateInSampleSize(options, i3, i4);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bArr, i2, bArr.length, options);
    }

    public static Bitmap getBitmap(@DrawableRes int i2) {
        Drawable drawable = ContextCompat.getDrawable(Utils.getApp(), i2);
        Canvas canvas = new Canvas();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmapCreateBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Bitmap getBitmap(@DrawableRes int i2, int i3, int i4) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Resources resources = Utils.getApp().getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i2, options);
        options.inSampleSize = calculateInSampleSize(options, i3, i4);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i2, options);
    }

    public static Bitmap getBitmap(FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            return null;
        }
        return BitmapFactory.decodeFileDescriptor(fileDescriptor);
    }

    public static Bitmap getBitmap(FileDescriptor fileDescriptor, int i2, int i3) {
        if (fileDescriptor == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }
}
