package com.ykb.ebook.page;

import android.content.AppCtxKt;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.collection.LruCache;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.ykb.ebook.R;
import com.ykb.ebook.page.exception.NoStackTraceException;
import com.ykb.ebook.util.BitmapUtils;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.SvgUtils;
import java.io.File;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J/\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00042\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u001cJ\u0012\u0010\u001d\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001e\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001b\u0010\u000e\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0013\u001a\n \u0014*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/ykb/ebook/page/ImageProvider;", "", "()V", "M", "", "bitmapLruCache", "Landroidx/collection/LruCache;", "", "Landroid/graphics/Bitmap;", "getBitmapLruCache", "()Landroidx/collection/LruCache;", "cacheSize", "getCacheSize", "()I", "errorBitmap", "getErrorBitmap", "()Landroid/graphics/Bitmap;", "errorBitmap$delegate", "Lkotlin/Lazy;", "tag", "kotlin.jvm.PlatformType", PLVDocumentMarkToolType.CLEAR, "", "getImage", "bookName", "src", "width", "height", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;)Landroid/graphics/Bitmap;", "getNotRecycled", "key", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ImageProvider {

    @NotNull
    public static final ImageProvider INSTANCE;
    private static final int M = 1048576;

    @NotNull
    private static final LruCache<String, Bitmap> bitmapLruCache;

    /* renamed from: errorBitmap$delegate, reason: from kotlin metadata */
    @NotNull
    private static final Lazy errorBitmap;
    private static final String tag;

    static {
        ImageProvider imageProvider = new ImageProvider();
        INSTANCE = imageProvider;
        tag = imageProvider.getClass().getSimpleName();
        errorBitmap = LazyKt__LazyJVMKt.lazy(new Function0<Bitmap>() { // from class: com.ykb.ebook.page.ImageProvider$errorBitmap$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Bitmap invoke() {
                return BitmapFactory.decodeResource(AppCtxKt.getAppCtx().getResources(), R.drawable.image_loading_error);
            }
        });
        final int cacheSize = imageProvider.getCacheSize();
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) { // from class: com.ykb.ebook.page.ImageProvider$bitmapLruCache$1
            @Override // androidx.collection.LruCache
            public void entryRemoved(boolean evicted, @NotNull String filePath, @NotNull Bitmap oldBitmap, @Nullable Bitmap newBitmap) {
                Intrinsics.checkNotNullParameter(filePath, "filePath");
                Intrinsics.checkNotNullParameter(oldBitmap, "oldBitmap");
                if (Intrinsics.areEqual(oldBitmap, ImageProvider.INSTANCE.getErrorBitmap())) {
                    return;
                }
                oldBitmap.recycle();
            }

            @Override // androidx.collection.LruCache
            public int sizeOf(@NotNull String filePath, @NotNull Bitmap bitmap) {
                Intrinsics.checkNotNullParameter(filePath, "filePath");
                Intrinsics.checkNotNullParameter(bitmap, "bitmap");
                return bitmap.getByteCount();
            }
        };
    }

    private ImageProvider() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bitmap getErrorBitmap() {
        Object value = errorBitmap.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-errorBitmap>(...)");
        return (Bitmap) value;
    }

    public static /* synthetic */ Bitmap getImage$default(ImageProvider imageProvider, String str, String str2, int i2, Integer num, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            num = null;
        }
        return imageProvider.getImage(str, str2, i2, num);
    }

    private final Bitmap getNotRecycled(String key) {
        LruCache<String, Bitmap> lruCache = bitmapLruCache;
        Bitmap bitmap = lruCache.get(key);
        if (bitmap == null) {
            return null;
        }
        if (!bitmap.isRecycled()) {
            return bitmap;
        }
        lruCache.remove(key);
        return null;
    }

    public final void clear() {
        bitmapLruCache.evictAll();
    }

    @NotNull
    public final LruCache<String, Bitmap> getBitmapLruCache() {
        return bitmapLruCache;
    }

    public final int getCacheSize() {
        return 52428800;
    }

    @NotNull
    public final Bitmap getImage(@NotNull String bookName, @NotNull String src, int width, @Nullable Integer height) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(bookName, "bookName");
        Intrinsics.checkNotNullParameter(src, "src");
        if (StringsKt__StringsJVMKt.isBlank(src)) {
            Log log = Log.INSTANCE;
            String tag2 = tag;
            Intrinsics.checkNotNullExpressionValue(tag2, "tag");
            log.logE(tag2, "图片链接为空，请注意检查数据");
        }
        File image = BookHelp.INSTANCE.getImage(bookName, src);
        if (!image.exists()) {
            return getErrorBitmap();
        }
        String absolutePath = image.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "vFile.absolutePath");
        Bitmap notRecycled = getNotRecycled(absolutePath);
        if (notRecycled != null) {
            return notRecycled;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            BitmapUtils bitmapUtils = BitmapUtils.INSTANCE;
            String absolutePath2 = image.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath2, "vFile.absolutePath");
            Bitmap bitmapDecodeBitmap = bitmapUtils.decodeBitmap(absolutePath2, width, height);
            if (bitmapDecodeBitmap == null) {
                SvgUtils svgUtils = SvgUtils.INSTANCE;
                String absolutePath3 = image.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath3, "vFile.absolutePath");
                bitmapDecodeBitmap = svgUtils.createBitmap(absolutePath3, width, height);
                if (bitmapDecodeBitmap == null) {
                    String string = AppCtxKt.getAppCtx().getString(R.string.error_decode_bitmap);
                    Intrinsics.checkNotNullExpressionValue(string, "appCtx.getString(R.string.error_decode_bitmap)");
                    throw new NoStackTraceException(string);
                }
            }
            bitmapLruCache.put(image.getAbsolutePath(), bitmapDecodeBitmap);
            objM783constructorimpl = Result.m783constructorimpl(bitmapDecodeBitmap);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m786exceptionOrNullimpl(objM783constructorimpl) != null) {
            bitmapLruCache.put(image.getAbsolutePath(), INSTANCE.getErrorBitmap());
        }
        Bitmap errorBitmap2 = getErrorBitmap();
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = errorBitmap2;
        }
        return (Bitmap) objM783constructorimpl;
    }
}
