package com.pizidea.imagepicker;

import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

/* loaded from: classes4.dex */
public class UilImgLoader implements ImgLoader {
    @Override // com.pizidea.imagepicker.ImgLoader
    public void onPresentImage(ImageView imageView, String str, int i2) {
        ImageDownloader.Scheme scheme = ImageDownloader.Scheme.FILE;
        ImageLoader imageLoader = ImageLoader.getInstance();
        String strWrap = scheme.wrap(str);
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        int i3 = R.drawable.default_img;
        imageLoader.displayImage(strWrap, imageView, builder.showImageOnLoading(i3).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).showImageOnFail(i3).showImageForEmptyUri(i3).showImageOnLoading(i3).build());
    }

    public void onPresentImage2(ImageView imageView, String str, int i2) {
        ImageDownloader.Scheme scheme = ImageDownloader.Scheme.FILE;
        ImageLoader imageLoader = ImageLoader.getInstance();
        String strWrap = scheme.wrap(str);
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        int i3 = R.drawable.default_img;
        imageLoader.displayImage(strWrap, imageView, builder.showImageOnLoading(i3).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).showImageOnFail(i3).showImageForEmptyUri(i3).showImageOnLoading(i3).build());
    }
}
