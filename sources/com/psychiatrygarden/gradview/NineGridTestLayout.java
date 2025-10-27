package com.psychiatrygarden.gradview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class NineGridTestLayout extends NineGridLayout {
    protected static final int MAX_W_H_RATIO = 3;

    public NineGridTestLayout(Context context) {
        super(context);
    }

    @Override // com.psychiatrygarden.gradview.NineGridLayout
    public void displayImage(RatioImageView imageView, String url) {
        GlideUtils.loadImage(getContext(), url, imageView, R.drawable.imgplacehodel_image, R.drawable.imgplacehodel_image);
    }

    @Override // com.psychiatrygarden.gradview.NineGridLayout
    public boolean displayOneImage(final RatioImageView imageView, final String url, final int parentWidth) {
        Glide.with(ProjectApp.instance()).asBitmap().load((Object) GlideUtils.generateUrl(url)).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.gradview.NineGridTestLayout.1
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                float f2;
                float f3;
                float f4;
                if (resource != null) {
                    float width = resource.getWidth();
                    float height = resource.getHeight();
                    if (height >= width * 2.0f) {
                        f3 = parentWidth / 3;
                        f2 = 2.0f * f3;
                    } else if (height >= width) {
                        float f5 = (parentWidth / 2) - 50;
                        f2 = (height * f5) / width;
                        f3 = f5;
                    } else if (width >= height * 2.0f) {
                        f3 = (parentWidth * 2) / 3;
                        f2 = f3 / 2.0f;
                    } else {
                        if (width / height <= 1.1d) {
                            f3 = (parentWidth / 5) * 3;
                            f4 = 4.0f;
                        } else {
                            f3 = (parentWidth / 5) * 3;
                            f4 = 3.0f;
                        }
                        f2 = (f4 * f3) / 5.0f;
                    }
                    NineGridTestLayout.this.setOneImageLayoutParams(imageView, (int) f3, (int) f2);
                }
            }
        });
        if (url.toUpperCase().endsWith(".GIF")) {
            Glide.with(ProjectApp.instance()).asGif().load((Object) GlideUtils.generateUrl(url)).into(imageView);
            return false;
        }
        Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(url)).into(imageView);
        return false;
    }

    @Override // com.psychiatrygarden.gradview.NineGridLayout
    public void onClickImage(int i2, String url, List<String> urlList, String strTxt) {
        int i3 = this.type;
        Integer numValueOf = Integer.valueOf(R.drawable.imgplacehodel_image);
        if (i3 != 1) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, numValueOf).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.setImageUrls(new ArrayList(urlList)).setSrcView(null, i2).show();
        } else {
            if (strTxt.equals("video")) {
                this.mVideoClickIml.mVideoClickData();
                return;
            }
            ImageViewerPopupViewCustom xPopupImageLoader2 = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, numValueOf).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader2.popupInfo = new PopupInfo();
            xPopupImageLoader2.setImageUrls(new ArrayList(urlList)).setSrcView(null, i2).show();
        }
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // com.psychiatrygarden.gradview.NineGridLayout
    public void onClickImage(int position, String url, List<String> urlList, String strTxt, ImageView imageView) {
        if (this.type == 1 && strTxt.equals("video")) {
            this.mVideoClickIml.mVideoClickData();
            return;
        }
        if (urlList.size() > 1) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.setImageUrls(new ArrayList(urlList)).setSrcView(null, position).show();
        } else if (urlList.size() == 1) {
            ImageViewerPopupViewCustom xPopupImageLoader2 = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(imageView, urlList.get(0)).setXPopupImageLoader(new ImageLoaderUtilsCustom(true, R.drawable.imgplacehodel_image));
            xPopupImageLoader2.popupInfo = new PopupInfo();
            xPopupImageLoader2.show();
        }
    }
}
