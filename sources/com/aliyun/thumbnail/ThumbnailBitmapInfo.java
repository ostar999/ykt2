package com.aliyun.thumbnail;

import android.graphics.Bitmap;

/* loaded from: classes2.dex */
public class ThumbnailBitmapInfo {
    private long[] mPositionRange;
    private Bitmap mThumbnailBitmap;

    public long[] getPositionRange() {
        return this.mPositionRange;
    }

    public Bitmap getThumbnailBitmap() {
        return this.mThumbnailBitmap;
    }

    public void setPositionRange(long[] jArr) {
        this.mPositionRange = jArr;
    }

    public void setThumbnailBitmap(Bitmap bitmap) {
        this.mThumbnailBitmap = bitmap;
    }
}
