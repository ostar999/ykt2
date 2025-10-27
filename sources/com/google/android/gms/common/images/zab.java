package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zaj;

/* loaded from: classes3.dex */
public abstract class zab {
    final zaa zamz;
    protected int zanb;
    private int zana = 0;
    private boolean zanc = false;
    private boolean zand = true;
    private boolean zane = false;
    private boolean zanf = true;

    public zab(Uri uri, int i2) {
        this.zanb = 0;
        this.zamz = new zaa(uri);
        this.zanb = i2;
    }

    public final void zaa(Context context, Bitmap bitmap, boolean z2) {
        Asserts.checkNotNull(bitmap);
        zaa(new BitmapDrawable(context.getResources(), bitmap), z2, false, true);
    }

    public abstract void zaa(Drawable drawable, boolean z2, boolean z3, boolean z4);

    public final void zaa(Context context, zaj zajVar) {
        if (this.zanf) {
            zaa(null, false, true, false);
        }
    }

    public final void zaa(Context context, zaj zajVar, boolean z2) {
        int i2 = this.zanb;
        zaa(i2 != 0 ? context.getResources().getDrawable(i2) : null, z2, false, false);
    }

    public final boolean zaa(boolean z2, boolean z3) {
        return (!this.zand || z3 || z2) ? false : true;
    }
}
