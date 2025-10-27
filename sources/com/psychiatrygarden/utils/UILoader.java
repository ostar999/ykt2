package com.psychiatrygarden.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/* loaded from: classes6.dex */
public class UILoader implements cn.lightsky.infiniteindicator.ImageLoader {
    private boolean isInited;
    private DisplayImageOptions options;

    public UILoader getImageLoader(Context context) {
        UILoader uILoader = new UILoader();
        initLoader(context);
        return uILoader;
    }

    public void initLoader(Context context) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        this.options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        this.isInited = true;
    }

    @Override // cn.lightsky.infiniteindicator.ImageLoader
    public void load(Context context, ImageView targetView, Object res) {
        if (!this.isInited) {
            initLoader(context);
        }
        if (res == null) {
            return;
        }
        if (res instanceof String) {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) res, targetView, this.options);
            return;
        }
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("drawable://" + res, targetView, this.options);
    }
}
