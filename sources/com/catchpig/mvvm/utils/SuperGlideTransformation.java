package com.catchpig.mvvm.utils;

import cn.hutool.core.text.CharPool;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BM\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\b\u0010%\u001a\u00020\bH\u0016J(\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020'2\u0006\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\bH\u0014J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\t\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\n\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0015\"\u0004\b\"\u0010\u0017R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0011\"\u0004\b$\u0010\u0013¨\u00061"}, d2 = {"Lcom/catchpig/mvvm/utils/SuperGlideTransformation;", "Lcom/bumptech/glide/load/resource/bitmap/BitmapTransformation;", "isCenterCrop", "", "scale", "", "blurRadius", "roundRadius", "", "borderColor", "borderSize", "roundArray", "", "(ZFFIII[F)V", "ID", "", "getBlurRadius", "()F", "setBlurRadius", "(F)V", "getBorderColor", "()I", "setBorderColor", "(I)V", "getBorderSize", "setBorderSize", "()Z", "setCenterCrop", "(Z)V", "getRoundArray", "()[F", "setRoundArray", "([F)V", "getRoundRadius", "setRoundRadius", "getScale", "setScale", "hashCode", "transform", "Landroid/graphics/Bitmap;", "pool", "Lcom/bumptech/glide/load/engine/bitmap_recycle/BitmapPool;", "toTransform", "outWidth", "outHeight", "updateDiskCacheKey", "", "messageDigest", "Ljava/security/MessageDigest;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SuperGlideTransformation extends BitmapTransformation {

    @NotNull
    private final String ID;
    private float blurRadius;
    private int borderColor;
    private int borderSize;
    private boolean isCenterCrop;

    @Nullable
    private float[] roundArray;
    private int roundRadius;
    private float scale;

    public SuperGlideTransformation() {
        this(false, 0.0f, 0.0f, 0, 0, 0, null, 127, null);
    }

    public /* synthetic */ SuperGlideTransformation(boolean z2, float f2, float f3, int i2, int i3, int i4, float[] fArr, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? false : z2, (i5 & 2) != 0 ? 0.0f : f2, (i5 & 4) != 0 ? 20.0f : f3, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) != 0 ? 0 : i3, (i5 & 32) == 0 ? i4 : 0, (i5 & 64) != 0 ? null : fArr);
    }

    public final float getBlurRadius() {
        return this.blurRadius;
    }

    public final int getBorderColor() {
        return this.borderColor;
    }

    public final int getBorderSize() {
        return this.borderSize;
    }

    @Nullable
    public final float[] getRoundArray() {
        return this.roundArray;
    }

    public final int getRoundRadius() {
        return this.roundRadius;
    }

    public final float getScale() {
        return this.scale;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return (this.ID + CharPool.DASHED + this.isCenterCrop + CharPool.DASHED + this.scale + CharPool.DASHED + this.blurRadius + CharPool.DASHED + this.roundRadius + CharPool.DASHED + this.borderColor + CharPool.DASHED + this.borderSize).hashCode();
    }

    /* renamed from: isCenterCrop, reason: from getter */
    public final boolean getIsCenterCrop() {
        return this.isCenterCrop;
    }

    public final void setBlurRadius(float f2) {
        this.blurRadius = f2;
    }

    public final void setBorderColor(int i2) {
        this.borderColor = i2;
    }

    public final void setBorderSize(int i2) {
        this.borderSize = i2;
    }

    public final void setCenterCrop(boolean z2) {
        this.isCenterCrop = z2;
    }

    public final void setRoundArray(@Nullable float[] fArr) {
        this.roundArray = fArr;
    }

    public final void setRoundRadius(int i2) {
        this.roundRadius = i2;
    }

    public final void setScale(float f2) {
        this.scale = f2;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap transform(@org.jetbrains.annotations.NotNull com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool r10, @org.jetbrains.annotations.NotNull android.graphics.Bitmap r11, int r12, int r13) {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.SuperGlideTransformation.transform(com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool, android.graphics.Bitmap, int, int):android.graphics.Bitmap");
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NotNull MessageDigest messageDigest) {
        Intrinsics.checkNotNullParameter(messageDigest, "messageDigest");
        String str = this.ID + CharPool.DASHED + this.isCenterCrop + CharPool.DASHED + this.scale + CharPool.DASHED + this.blurRadius + CharPool.DASHED + this.roundRadius + CharPool.DASHED + this.borderColor + CharPool.DASHED + this.borderSize;
        Charset CHARSET = Key.CHARSET;
        Intrinsics.checkNotNullExpressionValue(CHARSET, "CHARSET");
        byte[] bytes = str.getBytes(CHARSET);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        messageDigest.update(bytes);
    }

    public SuperGlideTransformation(boolean z2, float f2, float f3, int i2, int i3, int i4, @Nullable float[] fArr) {
        this.isCenterCrop = z2;
        this.scale = f2;
        this.blurRadius = f3;
        this.roundRadius = i2;
        this.borderColor = i3;
        this.borderSize = i4;
        this.roundArray = fArr;
        this.ID = "com.catchpig.mvvm.utils.SuperGlideTransformation";
    }
}
