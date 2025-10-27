package com.lxj.xpopup.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.photoview.PhotoView;
import java.io.File;

/* loaded from: classes4.dex */
public interface XPopupImageLoader {
    File getImageFile(@NonNull Context context, @NonNull Object obj);

    View loadImage(int i2, @NonNull Object obj, @NonNull ImageViewerPopupView imageViewerPopupView, @NonNull PhotoView photoView, @NonNull ProgressBar progressBar);

    void loadSnapshot(@NonNull Object obj, @NonNull PhotoView photoView);
}
