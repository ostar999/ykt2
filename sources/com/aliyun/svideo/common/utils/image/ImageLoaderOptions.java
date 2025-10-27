package com.aliyun.svideo.common.utils.image;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;

/* loaded from: classes2.dex */
public class ImageLoaderOptions {
    private int mErrorDrawableId;
    private Drawable mHolderDrawable;
    private int mHolderDrawableId;
    private boolean mIsAsBitmap;
    private boolean mIsCenterCrop;
    private boolean mIsCircle;
    private boolean mIsCrossFade;
    private boolean mIsRoundCorner;
    private boolean mIsSkipDiskCacheCache;
    private boolean mIsSkipMemoryCache;
    private Point mOverridePoint;
    private float mThumbnail;

    public static final class Builder {
        private final ImageLoaderOptions mLoaderOptions = new ImageLoaderOptions();

        public Builder asBitmap() {
            this.mLoaderOptions.mIsAsBitmap = true;
            return this;
        }

        public ImageLoaderOptions build() {
            return this.mLoaderOptions;
        }

        public Builder centerCrop() {
            this.mLoaderOptions.mIsCenterCrop = true;
            return this;
        }

        public Builder circle() {
            this.mLoaderOptions.mIsCircle = true;
            return this;
        }

        public Builder crossFade() {
            this.mLoaderOptions.mIsCrossFade = true;
            return this;
        }

        public Builder error(@DrawableRes int i2) {
            this.mLoaderOptions.mErrorDrawableId = i2;
            return this;
        }

        public Builder override(int i2, int i3) {
            this.mLoaderOptions.mOverridePoint.x = i2;
            this.mLoaderOptions.mOverridePoint.y = i3;
            return this;
        }

        public Builder placeholder(@DrawableRes int i2) {
            this.mLoaderOptions.mHolderDrawableId = i2;
            return this;
        }

        public Builder roundCorner() {
            this.mLoaderOptions.mIsRoundCorner = true;
            return this;
        }

        public Builder skipDiskCacheCache() {
            this.mLoaderOptions.mIsSkipDiskCacheCache = true;
            return this;
        }

        public Builder skipMemoryCache() {
            this.mLoaderOptions.mIsSkipMemoryCache = true;
            return this;
        }

        public Builder thumbnail(float f2) {
            this.mLoaderOptions.mThumbnail = f2;
            return this;
        }

        public Builder placeholder(Drawable drawable) {
            this.mLoaderOptions.mHolderDrawable = drawable;
            return this;
        }
    }

    public int getErrorDrawableId() {
        return this.mErrorDrawableId;
    }

    public Drawable getHolderDrawable() {
        return this.mHolderDrawable;
    }

    public int getHolderDrawableId() {
        return this.mHolderDrawableId;
    }

    public Point getOverridePoint() {
        return this.mOverridePoint;
    }

    public float getThumbnail() {
        return this.mThumbnail;
    }

    public boolean isAsBitmap() {
        return this.mIsAsBitmap;
    }

    public boolean isCenterCrop() {
        return this.mIsCenterCrop;
    }

    public boolean isCircle() {
        return this.mIsCircle;
    }

    public boolean isCrossFade() {
        return this.mIsCrossFade;
    }

    public boolean isRoundCorner() {
        return this.mIsRoundCorner;
    }

    public boolean isSkipDiskCacheCache() {
        return this.mIsSkipDiskCacheCache;
    }

    public boolean isSkipMemoryCache() {
        return this.mIsSkipMemoryCache;
    }

    private ImageLoaderOptions() {
        this.mHolderDrawableId = -1;
        this.mErrorDrawableId = -1;
        this.mIsAsBitmap = false;
        this.mIsCrossFade = false;
        this.mThumbnail = 1.0f;
        this.mIsCenterCrop = false;
        this.mIsSkipMemoryCache = false;
        this.mIsSkipDiskCacheCache = false;
        this.mIsCircle = false;
        this.mOverridePoint = new Point();
        this.mIsRoundCorner = false;
    }
}
