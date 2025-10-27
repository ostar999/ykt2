package net.tsz.afinal.bitmap.display;

import android.graphics.Bitmap;
import android.view.View;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;

/* loaded from: classes9.dex */
public interface Displayer {
    void loadCompletedisplay(View view, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig);

    void loadFailDisplay(View view, Bitmap bitmap);
}
