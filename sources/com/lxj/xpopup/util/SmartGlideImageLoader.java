package com.lxj.xpopup.util;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.photoview.OnMatrixChangedListener;
import com.lxj.xpopup.photoview.PhotoView;
import java.io.File;

/* loaded from: classes4.dex */
public class SmartGlideImageLoader implements XPopupImageLoader {
    private int errImg;
    private boolean mBigImage;

    public SmartGlideImageLoader() {
    }

    private SubsamplingScaleImageView buildBigImageView(final ImageViewerPopupView imageViewerPopupView, ProgressBar progressBar, final int i2) {
        SubsamplingScaleImageView subsamplingScaleImageView = new SubsamplingScaleImageView(imageViewerPopupView.getContext());
        subsamplingScaleImageView.setOnStateChangedListener(new SubsamplingScaleImageView.DefaultOnStateChangedListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.2
            @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.DefaultOnStateChangedListener, com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnStateChangedListener
            public void onCenterChanged(PointF pointF, int i3) {
                super.onCenterChanged(pointF, i3);
            }
        });
        subsamplingScaleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                imageViewerPopupView.dismiss();
            }
        });
        if (imageViewerPopupView.longPressListener != null) {
            subsamplingScaleImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.4
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    ImageViewerPopupView imageViewerPopupView2 = imageViewerPopupView;
                    imageViewerPopupView2.longPressListener.onLongPressed(imageViewerPopupView2, i2);
                    return false;
                }
            });
        }
        return subsamplingScaleImageView;
    }

    private PhotoView buildPhotoView(final ImageViewerPopupView imageViewerPopupView, final PhotoView photoView, final int i2) {
        final PhotoView photoView2 = new PhotoView(imageViewerPopupView.getContext());
        photoView2.setZoomable(false);
        photoView2.setOnMatrixChangeListener(new OnMatrixChangedListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.5
            @Override // com.lxj.xpopup.photoview.OnMatrixChangedListener
            public void onMatrixChanged(RectF rectF) {
                if (photoView != null) {
                    Matrix matrix = new Matrix();
                    photoView2.getSuppMatrix(matrix);
                    photoView.setSuppMatrix(matrix);
                }
            }
        });
        photoView2.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                imageViewerPopupView.dismiss();
            }
        });
        if (imageViewerPopupView.longPressListener != null) {
            photoView2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.7
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    ImageViewerPopupView imageViewerPopupView2 = imageViewerPopupView;
                    imageViewerPopupView2.longPressListener.onLongPressed(imageViewerPopupView2, i2);
                    return false;
                }
            });
        }
        return photoView2;
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public File getImageFile(@NonNull Context context, @NonNull Object obj) {
        try {
            return Glide.with(context).downloadOnly().load(obj).submit().get();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public View loadImage(int i2, @NonNull Object obj, @NonNull ImageViewerPopupView imageViewerPopupView, @Nullable PhotoView photoView, @NonNull final ProgressBar progressBar) {
        progressBar.setVisibility(0);
        final View viewBuildBigImageView = this.mBigImage ? buildBigImageView(imageViewerPopupView, progressBar, i2) : buildPhotoView(imageViewerPopupView, photoView, i2);
        final Context context = viewBuildBigImageView.getContext();
        if (photoView != null && photoView.getDrawable() != null && ((Integer) photoView.getTag()).intValue() == i2) {
            if (viewBuildBigImageView instanceof PhotoView) {
                try {
                    ((PhotoView) viewBuildBigImageView).setImageDrawable(photoView.getDrawable().getConstantState().newDrawable());
                } catch (Exception unused) {
                }
            } else {
                ((SubsamplingScaleImageView) viewBuildBigImageView).setImage(ImageSource.bitmap(XPopupUtils.view2Bitmap(photoView)));
            }
        }
        Glide.with(viewBuildBigImageView).downloadOnly().load(obj).into((RequestBuilder<File>) new ImageDownloadTarget() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.1
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable drawable) {
                super.onLoadFailed(drawable);
                progressBar.setVisibility(8);
                View view = viewBuildBigImageView;
                if (!(view instanceof PhotoView)) {
                    ((SubsamplingScaleImageView) view).setImage(ImageSource.resource(SmartGlideImageLoader.this.errImg));
                } else {
                    ((PhotoView) view).setImageResource(SmartGlideImageLoader.this.errImg);
                    ((PhotoView) viewBuildBigImageView).setZoomable(false);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(@NonNull File file, Transition<? super File> transition) {
                boolean z2;
                super.onResourceReady(file, transition);
                int windowWidth = XPopupUtils.getWindowWidth(context) * 2;
                int screenHeight = XPopupUtils.getScreenHeight(context) * 2;
                int[] imageSize = XPopupUtils.getImageSize(file);
                int rotateDegree = XPopupUtils.getRotateDegree(file.getAbsolutePath());
                View view = viewBuildBigImageView;
                if (view instanceof PhotoView) {
                    progressBar.setVisibility(8);
                    ((PhotoView) viewBuildBigImageView).setZoomable(true);
                    if (imageSize[0] <= windowWidth && imageSize[1] <= screenHeight) {
                        Glide.with(viewBuildBigImageView).load(file).transform(new Rotate(rotateDegree)).apply((BaseRequestOptions<?>) new RequestOptions().error(SmartGlideImageLoader.this.errImg).override(imageSize[0], imageSize[1])).into((PhotoView) viewBuildBigImageView);
                        return;
                    } else {
                        ((PhotoView) viewBuildBigImageView).setImageBitmap(XPopupUtils.rotate(XPopupUtils.getBitmap(file, windowWidth, screenHeight), rotateDegree, imageSize[0] / 2.0f, imageSize[1] / 2.0f));
                        return;
                    }
                }
                SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) view;
                if ((imageSize[1] * 1.0f) / imageSize[0] > (XPopupUtils.getScreenHeight(context) * 1.0f) / XPopupUtils.getWindowWidth(context)) {
                    subsamplingScaleImageView.setMinimumScaleType(4);
                    z2 = true;
                } else {
                    subsamplingScaleImageView.setMinimumScaleType(1);
                    z2 = false;
                }
                subsamplingScaleImageView.setOrientation(rotateDegree);
                subsamplingScaleImageView.setOnImageEventListener(new SSIVListener(subsamplingScaleImageView, progressBar, SmartGlideImageLoader.this.errImg, z2));
                subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)).dimensions(imageSize[0], imageSize[1]), ImageSource.cachedBitmap(XPopupUtils.getBitmap(file, XPopupUtils.getWindowWidth(context), XPopupUtils.getScreenHeight(context))));
            }
        });
        return viewBuildBigImageView;
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public void loadSnapshot(@NonNull Object obj, @NonNull final PhotoView photoView) {
        Glide.with(photoView).downloadOnly().load(obj).into((RequestBuilder<File>) new ImageDownloadTarget() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.8
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable drawable) {
                super.onLoadFailed(drawable);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(@NonNull File file, Transition<? super File> transition) {
                super.onResourceReady(file, transition);
                int rotateDegree = XPopupUtils.getRotateDegree(file.getAbsolutePath());
                int windowWidth = XPopupUtils.getWindowWidth(photoView.getContext());
                int screenHeight = XPopupUtils.getScreenHeight(photoView.getContext());
                int[] imageSize = XPopupUtils.getImageSize(file);
                if (imageSize[0] <= windowWidth && imageSize[1] <= screenHeight) {
                    Glide.with(photoView).load(file).apply((BaseRequestOptions<?>) new RequestOptions().override(imageSize[0], imageSize[1])).into(photoView);
                } else {
                    photoView.setImageBitmap(XPopupUtils.rotate(XPopupUtils.getBitmap(file, windowWidth, screenHeight), rotateDegree, imageSize[0] / 2.0f, imageSize[1] / 2.0f));
                }
            }
        });
    }

    public SmartGlideImageLoader(int i2) {
        this.errImg = i2;
    }

    public SmartGlideImageLoader(boolean z2, int i2) {
        this(i2);
        this.mBigImage = z2;
    }
}
