package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public final class zac extends zab {
    private WeakReference<ImageManager.OnImageLoadedListener> zang;

    public zac(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull(onImageLoadedListener);
        this.zang = new WeakReference<>(onImageLoadedListener);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zac)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zac zacVar = (zac) obj;
        ImageManager.OnImageLoadedListener onImageLoadedListener = this.zang.get();
        ImageManager.OnImageLoadedListener onImageLoadedListener2 = zacVar.zang.get();
        return onImageLoadedListener2 != null && onImageLoadedListener != null && Objects.equal(onImageLoadedListener2, onImageLoadedListener) && Objects.equal(zacVar.zamz, this.zamz);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zamz);
    }

    @Override // com.google.android.gms.common.images.zab
    public final void zaa(Drawable drawable, boolean z2, boolean z3, boolean z4) {
        ImageManager.OnImageLoadedListener onImageLoadedListener;
        if (z3 || (onImageLoadedListener = this.zang.get()) == null) {
            return;
        }
        onImageLoadedListener.onImageLoaded(this.zamz.uri, drawable, z4);
    }
}
