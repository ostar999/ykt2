package com.ykb.ebook.extensions;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J&\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000fH\u0007J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u000bH\u0002J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0001J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0001J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\bH\u0007J!\u0010\u0018\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\b¢\u0006\u0002\u0010\u001fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/ykb/ebook/extensions/ConvertUtils;", "", "()V", "GB", "", "KB", "MB", "formatFileSize", "", SessionDescription.ATTR_LENGTH, "toBitmap", "Landroid/graphics/Bitmap;", HttpHeaderValues.BYTES, "", "width", "", "height", "toDrawable", "Landroid/graphics/drawable/Drawable;", "bitmap", "toFloat", "", "obj", "toInt", "toString", "is", "Ljava/io/InputStream;", "charset", "objects", "", "tag", "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ConvertUtils {
    public static final long GB = 1073741824;

    @NotNull
    public static final ConvertUtils INSTANCE = new ConvertUtils();
    public static final long KB = 1024;
    public static final long MB = 1048576;

    private ConvertUtils() {
    }

    public static /* synthetic */ Bitmap toBitmap$default(ConvertUtils convertUtils, byte[] bArr, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = -1;
        }
        if ((i4 & 4) != 0) {
            i3 = -1;
        }
        return convertUtils.toBitmap(bArr, i2, i3);
    }

    private final Drawable toDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(Resources.getSystem(), bitmap);
    }

    public static /* synthetic */ String toString$default(ConvertUtils convertUtils, InputStream inputStream, String str, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str = "utf-8";
        }
        return convertUtils.toString(inputStream, str);
    }

    @NotNull
    public final String formatFileSize(long length) {
        if (length <= 0) {
            return "0";
        }
        String[] strArr = {"b", "kb", "M", "G", ExifInterface.GPS_DIRECTION_TRUE};
        double d3 = length;
        int iLog10 = (int) (Math.log10(d3) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.##").format(d3 / Math.pow(1024.0d, iLog10)) + ' ' + strArr[iLog10];
    }

    @JvmOverloads
    @Nullable
    public final Bitmap toBitmap(@NotNull byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return toBitmap$default(this, bytes, 0, 0, 6, null);
    }

    @JvmOverloads
    @Nullable
    public final Bitmap toBitmap(@NotNull byte[] bytes, int i2) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return toBitmap$default(this, bytes, i2, 0, 4, null);
    }

    @JvmOverloads
    @Nullable
    public final Bitmap toBitmap(@NotNull byte[] bytes, int width, int height) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Bitmap bitmapDecodeByteArray = null;
        if (!(!(bytes.length == 0))) {
            return null;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = null;
            if (width > 0 && height > 0) {
                options.outWidth = width;
                options.outHeight = height;
            }
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            Intrinsics.checkNotNull(bitmapDecodeByteArray);
            bitmapDecodeByteArray.setDensity(96);
            Result.m783constructorimpl(Unit.INSTANCE);
            return bitmapDecodeByteArray;
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m783constructorimpl(ResultKt.createFailure(th));
            return bitmapDecodeByteArray;
        }
    }

    public final float toFloat(@NotNull Object obj) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(obj, "obj");
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Float.valueOf(Float.parseFloat(obj.toString())));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        Float fValueOf = Float.valueOf(-1.0f);
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = fValueOf;
        }
        return ((Number) objM783constructorimpl).floatValue();
    }

    public final int toInt(@NotNull Object obj) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(obj, "obj");
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Integer.valueOf(Integer.parseInt(obj.toString())));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = -1;
        }
        return ((Number) objM783constructorimpl).intValue();
    }

    @JvmOverloads
    @NotNull
    public final String toString(@NotNull InputStream is) {
        Intrinsics.checkNotNullParameter(is, "is");
        return toString$default(this, is, null, 2, null);
    }

    @NotNull
    public final String toString(@NotNull Object[] objects, @NotNull String tag) {
        Intrinsics.checkNotNullParameter(objects, "objects");
        Intrinsics.checkNotNullParameter(tag, "tag");
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            sb.append(obj);
            sb.append(tag);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    @Nullable
    public final Drawable toDrawable(@NotNull byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return toDrawable(toBitmap$default(this, bytes, 0, 0, 6, null));
    }

    public final int toInt(@NotNull byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        int length = bytes.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i2 += (bytes[i3] & 255) << (i3 * 8);
        }
        return i2;
    }

    @JvmOverloads
    @NotNull
    public final String toString(@NotNull InputStream is, @NotNull String charset) {
        Intrinsics.checkNotNullParameter(is, "is");
        Intrinsics.checkNotNullParameter(charset, "charset");
        StringBuilder sb = new StringBuilder();
        try {
            Result.Companion companion = Result.INSTANCE;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, charset));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            }
            bufferedReader.close();
            is.close();
            Result.m783constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }
}
