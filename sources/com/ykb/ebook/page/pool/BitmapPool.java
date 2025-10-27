package com.ykb.ebook.page.pool;

import android.graphics.Bitmap;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.ykb.ebook.page.pool.BitmapPool;
import com.ykb.ebook.util.ExecutorServiceKt;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0016\u0010\u0011\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nJ\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0005J\b\u0010\u0014\u001a\u00020\rH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/page/pool/BitmapPool;", "", "()V", "reusableBitmaps", "", "Landroid/graphics/Bitmap;", "canReconfigure", "", "candidate", "width", "", "height", PLVDocumentMarkToolType.CLEAR, "", "getBytesPerPixel", "config", "Landroid/graphics/Bitmap$Config;", "obtain", "recycle", "bitmap", "trimSize", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BitmapPool {

    @NotNull
    public static final BitmapPool INSTANCE = new BitmapPool();

    @NotNull
    private static final Set<Bitmap> reusableBitmaps;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            try {
                iArr[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Bitmap.Config.ALPHA_8.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet();
        Intrinsics.checkNotNullExpressionValue(keySetViewNewKeySet, "newKeySet()");
        reusableBitmaps = keySetViewNewKeySet;
    }

    private BitmapPool() {
    }

    private final boolean canReconfigure(Bitmap candidate, int width, int height) {
        int i2 = width * height;
        Bitmap.Config config = candidate.getConfig();
        Intrinsics.checkNotNullExpressionValue(config, "candidate.config");
        return i2 * getBytesPerPixel(config) <= candidate.getAllocationByteCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clear$lambda$0() {
        Iterator<Bitmap> it = reusableBitmaps.iterator();
        while (it.hasNext()) {
            Bitmap next = it.next();
            it.remove();
            next.recycle();
        }
    }

    private final int getBytesPerPixel(Bitmap.Config config) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[config.ordinal()];
        if (i2 != 1) {
            return (i2 == 2 || i2 == 3) ? 2 : 1;
        }
        return 4;
    }

    private final void trimSize() {
        ExecutorServiceKt.getGlobalExecutor().execute(new Runnable() { // from class: u1.b
            @Override // java.lang.Runnable
            public final void run() {
                BitmapPool.trimSize$lambda$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trimSize$lambda$1() {
        Iterator<Bitmap> it = reusableBitmaps.iterator();
        int byteCount = 0;
        while (it.hasNext()) {
            Bitmap next = it.next();
            if (byteCount > 67108864) {
                it.remove();
                next.recycle();
            } else {
                byteCount += next.getByteCount();
            }
        }
    }

    public final void clear() {
        if (reusableBitmaps.isEmpty()) {
            return;
        }
        ExecutorServiceKt.getGlobalExecutor().execute(new Runnable() { // from class: u1.a
            @Override // java.lang.Runnable
            public final void run() {
                BitmapPool.clear$lambda$0();
            }
        });
    }

    @NotNull
    public final Bitmap obtain(int width, int height) {
        Set<Bitmap> set = reusableBitmaps;
        if (set.isEmpty()) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(width, heig… Bitmap.Config.ARGB_8888)");
            return bitmapCreateBitmap;
        }
        Iterator<Bitmap> it = set.iterator();
        while (it.hasNext()) {
            Bitmap next = it.next();
            if (!next.isMutable()) {
                it.remove();
            } else if (canReconfigure(next, width, height)) {
                it.remove();
                next.reconfigure(width, height, Bitmap.Config.ARGB_8888);
                return next;
            }
        }
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap2, "createBitmap(width, heig… Bitmap.Config.ARGB_8888)");
        return bitmapCreateBitmap2;
    }

    public final void recycle(@NotNull Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        reusableBitmaps.add(bitmap);
        trimSize();
    }
}
