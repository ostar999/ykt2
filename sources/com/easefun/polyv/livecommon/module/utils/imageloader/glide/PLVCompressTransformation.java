package com.easefun.polyv.livecommon.module.utils.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import cn.hutool.core.img.ImgUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.ImageUtils;
import java.io.File;
import java.security.MessageDigest;

/* loaded from: classes3.dex */
public class PLVCompressTransformation implements Transformation<Bitmap> {
    private static final String ID = "PLVCompressTransformation.1";
    private static final byte[] ID_BYTES = ID.getBytes(Key.CHARSET);
    private static final String TAG = "PLVCompressTransformati";
    private static final int VERSION = 1;
    private BitmapPool mBitmapPool;
    private String mUrl;

    public PLVCompressTransformation(Context context, String url) {
        this(url, Glide.get(context).getBitmapPool());
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof PLVCompressTransformation;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return -2127851000;
    }

    @Override // com.bumptech.glide.load.Transformation
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap bitmapCompressImage;
        if (new File(this.mUrl).isFile()) {
            try {
                String imageType = ImageUtils.getImageType(this.mUrl);
                if ((imageType == null || !ImgUtil.IMAGE_TYPE_GIF.equals(imageType.toLowerCase())) && (bitmapCompressImage = PLVImageUtils.compressImage(this.mUrl)) != null) {
                    return BitmapResource.obtain(bitmapCompressImage, this.mBitmapPool);
                }
            } catch (Exception e2) {
                PLVCommonLog.d(TAG, "transform：" + e2.getMessage());
            }
        }
        return resource;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    private PLVCompressTransformation(String url, BitmapPool pool) {
        this.mBitmapPool = pool;
        this.mUrl = url;
    }
}
