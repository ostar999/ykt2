package com.luck.picture.lib.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.luck.picture.lib.interfaces.OnCallbackListener;

/* loaded from: classes4.dex */
public interface ImageEngine {
    void loadAlbumCover(@NonNull Context context, @NonNull String str, @NonNull ImageView imageView);

    void loadGridImage(@NonNull Context context, @NonNull String str, @NonNull ImageView imageView);

    void loadImage(@NonNull Context context, @NonNull String str, @NonNull ImageView imageView);

    void loadImageBitmap(@NonNull Context context, @NonNull String str, int i2, int i3, OnCallbackListener<Bitmap> onCallbackListener);

    void pauseRequests(Context context);

    void resumeRequests(Context context);
}
