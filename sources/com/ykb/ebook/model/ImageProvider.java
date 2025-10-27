package com.ykb.ebook.model;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Size;
import com.luck.picture.lib.config.PictureMimeType;
import com.ykb.ebook.R;
import com.ykb.ebook.util.MD5Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bJ\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000bJ\u000e\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000bJ\u001e\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000bR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018"}, d2 = {"Lcom/ykb/ebook/model/ImageProvider;", "", "()V", "errorBitmap", "Landroid/graphics/Bitmap;", "getErrorBitmap", "()Landroid/graphics/Bitmap;", "errorBitmap$delegate", "Lkotlin/Lazy;", "downloadImage", "urlString", "", "existBitmap", "", "src", "getBitmap", "getPath", "content", "Landroid/content/Context;", "getSize", "Landroid/util/Size;", "saveBitmap", "bitmap", "path", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ImageProvider {

    @NotNull
    public static final ImageProvider INSTANCE = new ImageProvider();

    /* renamed from: errorBitmap$delegate, reason: from kotlin metadata */
    @NotNull
    private static final Lazy errorBitmap = LazyKt__LazyJVMKt.lazy(new Function0<Bitmap>() { // from class: com.ykb.ebook.model.ImageProvider$errorBitmap$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Bitmap invoke() {
            return BitmapFactory.decodeResource(AppCtxKt.getAppCtx().getResources(), R.drawable.image_loading_error);
        }
    });

    private ImageProvider() {
    }

    private final Bitmap getErrorBitmap() {
        Object value = errorBitmap.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-errorBitmap>(...)");
        return (Bitmap) value;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0070  */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Bitmap downloadImage(@org.jetbrains.annotations.NotNull java.lang.String r8) throws java.lang.Throwable {
        /*
            r7 = this;
            java.lang.String r0 = "urlString"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "TEST"
            java.lang.String r1 = "downloadImage: -----"
            android.util.Log.d(r0, r1)
            boolean r2 = r7.existBitmap(r8)
            r3 = 0
            if (r2 == 0) goto L14
            return r3
        L14:
            java.net.URL r2 = new java.net.URL     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            r2.<init>(r8)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.net.URLConnection r2 = r2.openConnection()     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.lang.String r4 = "null cannot be cast to non-null type java.net.HttpURLConnection"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2, r4)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.lang.String r4 = "GET"
            r2.setRequestMethod(r4)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            r2.connect()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            int r4 = r2.getResponseCode()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 != r5) goto L5b
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            java.io.InputStream r5 = r2.getInputStream()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            r4.<init>(r5)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeStream(r4)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            java.lang.String r6 = "bitmap"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            android.content.Context r6 = android.content.AppCtxKt.getAppCtx()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            java.lang.String r6 = r7.getPath(r6)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            r7.saveBitmap(r5, r6, r8)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            android.util.Log.d(r0, r1)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            r4.close()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6c
            r2.disconnect()
            return r5
        L5b:
            r2.disconnect()
            goto L6b
        L5f:
            r8 = move-exception
            goto L65
        L61:
            r8 = move-exception
            goto L6e
        L63:
            r8 = move-exception
            r2 = r3
        L65:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L6c
            if (r2 == 0) goto L6b
            goto L5b
        L6b:
            return r3
        L6c:
            r8 = move-exception
            r3 = r2
        L6e:
            if (r3 == 0) goto L73
            r3.disconnect()
        L73:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.model.ImageProvider.downloadImage(java.lang.String):android.graphics.Bitmap");
    }

    public final boolean existBitmap(@NotNull String src) {
        Intrinsics.checkNotNullParameter(src, "src");
        return new File(getPath(AppCtxKt.getAppCtx()) + MD5Utils.INSTANCE.MD5Encode(src) + PictureMimeType.PNG).exists();
    }

    @NotNull
    public final Bitmap getBitmap(@NotNull String src) {
        Intrinsics.checkNotNullParameter(src, "src");
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(getPath(AppCtxKt.getAppCtx()) + MD5Utils.INSTANCE.MD5Encode(src) + PictureMimeType.PNG);
            Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
            return bitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return getErrorBitmap();
        }
    }

    @NotNull
    public final String getPath(@NotNull Context content) {
        Intrinsics.checkNotNullParameter(content, "content");
        StringBuilder sb = new StringBuilder();
        sb.append(content.getExternalFilesDir(""));
        String str = File.separator;
        sb.append(str);
        sb.append("imgCache");
        sb.append(str);
        return sb.toString();
    }

    @NotNull
    public final Size getSize(@NotNull String src) {
        Intrinsics.checkNotNullParameter(src, "src");
        File file = new File(getPath(AppCtxKt.getAppCtx()) + MD5Utils.INSTANCE.MD5Encode(src) + PictureMimeType.PNG);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new Size(options.outWidth, options.outHeight);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.graphics.Bitmap, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v9 */
    @NotNull
    public final String saveBitmap(@NotNull Bitmap bitmap, @NotNull String path, @NotNull String src) throws Throwable {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(src, "src");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(path, MD5Utils.INSTANCE.MD5Encode(src) + PictureMimeType.PNG);
        if (file2.exists()) {
            file2.delete();
        }
        ?? r5 = 0;
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream3 = new FileOutputStream(file2);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream3);
                        fileOutputStream3.flush();
                        fileOutputStream3.close();
                        fileOutputStream3.close();
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream3;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        String absolutePath = file2.getAbsolutePath();
                        r5 = "f.absolutePath";
                        Intrinsics.checkNotNullExpressionValue(absolutePath, "f.absolutePath");
                        return absolutePath;
                    } catch (IOException e3) {
                        e = e3;
                        fileOutputStream2 = fileOutputStream3;
                        e.printStackTrace();
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        String absolutePath2 = file2.getAbsolutePath();
                        r5 = "f.absolutePath";
                        Intrinsics.checkNotNullExpressionValue(absolutePath2, "f.absolutePath");
                        return absolutePath2;
                    } catch (Throwable th) {
                        th = th;
                        r5 = fileOutputStream3;
                        if (r5 != 0) {
                            try {
                                r5.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e5) {
                    e = e5;
                } catch (IOException e6) {
                    e = e6;
                }
            } catch (IOException e7) {
                e7.printStackTrace();
            }
            String absolutePath22 = file2.getAbsolutePath();
            r5 = "f.absolutePath";
            Intrinsics.checkNotNullExpressionValue(absolutePath22, "f.absolutePath");
            return absolutePath22;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
