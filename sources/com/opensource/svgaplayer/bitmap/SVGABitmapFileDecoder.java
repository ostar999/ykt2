package com.opensource.svgaplayer.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/opensource/svgaplayer/bitmap/SVGABitmapFileDecoder;", "Lcom/opensource/svgaplayer/bitmap/SVGABitmapDecoder;", "", "()V", "onDecode", "Landroid/graphics/Bitmap;", "data", "ops", "Landroid/graphics/BitmapFactory$Options;", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGABitmapFileDecoder extends SVGABitmapDecoder<String> {
    public static final SVGABitmapFileDecoder INSTANCE = new SVGABitmapFileDecoder();

    private SVGABitmapFileDecoder() {
    }

    @Override // com.opensource.svgaplayer.bitmap.SVGABitmapDecoder
    @Nullable
    public Bitmap onDecode(@NotNull String data, @NotNull BitmapFactory.Options ops) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        Intrinsics.checkParameterIsNotNull(ops, "ops");
        return BitmapFactory.decodeFile(data, ops);
    }
}
