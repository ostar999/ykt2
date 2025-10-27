package com.luck.picture.lib.interfaces;

import com.luck.picture.lib.entity.LocalMedia;

/* loaded from: classes4.dex */
public interface OnExternalPreviewEventListener {
    boolean onLongPressDownload(LocalMedia localMedia);

    void onPreviewDelete(int i2);
}
