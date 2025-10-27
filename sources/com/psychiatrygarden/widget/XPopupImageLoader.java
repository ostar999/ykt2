package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.lxj.xpopup.photoview.PhotoView;
import java.io.File;

/* loaded from: classes6.dex */
public interface XPopupImageLoader {
    File getImageFile(@NonNull Context context, @NonNull Object uri);

    View loadImage(int position, @NonNull Object uri, @NonNull ImageViewerPopupViewCustom popupView, @NonNull PhotoView snapshot, @NonNull ProgressBar progressBar);

    void loadSnapshot(@NonNull Object uri, @NonNull PhotoView snapshot);
}
