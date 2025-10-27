package com.psychiatrygarden.utils;

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
import com.bumptech.glide.load.model.GlideUrl;
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
import com.lxj.xpopup.util.ImageDownloadTarget;
import com.lxj.xpopup.util.SSIVListener;
import com.lxj.xpopup.util.XPopupUtils;
import java.io.File;

/* loaded from: classes6.dex */
public class ImageLoader implements XPopupImageLoader {
    private int errImg;
    private boolean mBigImage;

    public ImageLoader() {
    }

    private SubsamplingScaleImageView buildBigImageView(final ImageViewerPopupView popupView, ProgressBar progressBar, final int realPosition) {
        SubsamplingScaleImageView subsamplingScaleImageView = new SubsamplingScaleImageView(popupView.getContext());
        subsamplingScaleImageView.setOnStateChangedListener(new SubsamplingScaleImageView.DefaultOnStateChangedListener() { // from class: com.psychiatrygarden.utils.ImageLoader.2
            @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.DefaultOnStateChangedListener, com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnStateChangedListener
            public void onCenterChanged(PointF newCenter, int origin) {
                super.onCenterChanged(newCenter, origin);
            }
        });
        subsamplingScaleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.ImageLoader.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                popupView.dismiss();
            }
        });
        if (popupView.longPressListener != null) {
            subsamplingScaleImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.utils.ImageLoader.4
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View v2) {
                    ImageViewerPopupView imageViewerPopupView = popupView;
                    imageViewerPopupView.longPressListener.onLongPressed(imageViewerPopupView, realPosition);
                    return false;
                }
            });
        }
        return subsamplingScaleImageView;
    }

    private PhotoView buildPhotoView(final ImageViewerPopupView popupView, final PhotoView snapshotView, final int realPosition) {
        final PhotoView photoView = new PhotoView(popupView.getContext());
        photoView.setZoomable(false);
        photoView.setOnMatrixChangeListener(new OnMatrixChangedListener() { // from class: com.psychiatrygarden.utils.ImageLoader.5
            @Override // com.lxj.xpopup.photoview.OnMatrixChangedListener
            public void onMatrixChanged(RectF rect) {
                if (snapshotView != null) {
                    Matrix matrix = new Matrix();
                    photoView.getSuppMatrix(matrix);
                    snapshotView.setSuppMatrix(matrix);
                }
            }
        });
        photoView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.ImageLoader.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                popupView.dismiss();
            }
        });
        if (popupView.longPressListener != null) {
            photoView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.utils.ImageLoader.7
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View v2) {
                    ImageViewerPopupView imageViewerPopupView = popupView;
                    imageViewerPopupView.longPressListener.onLongPressed(imageViewerPopupView, realPosition);
                    return false;
                }
            });
        }
        return photoView;
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public File getImageFile(@NonNull Context context, @NonNull Object uri) {
        try {
            GlideUrl glideUrlGenerateUrl = uri instanceof String ? GlideUtils.generateUrl(String.valueOf(uri)) : null;
            RequestBuilder<File> requestBuilderDownloadOnly = Glide.with(context).downloadOnly();
            if (glideUrlGenerateUrl != null) {
                uri = glideUrlGenerateUrl;
            }
            return requestBuilderDownloadOnly.load(uri).submit().get();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public View loadImage(final int position, @NonNull final Object url, @NonNull ImageViewerPopupView popupView, @Nullable final PhotoView snapshot, @NonNull final ProgressBar progressBar) {
        progressBar.setVisibility(0);
        final View viewBuildBigImageView = this.mBigImage ? buildBigImageView(popupView, progressBar, position) : buildPhotoView(popupView, snapshot, position);
        final Context context = viewBuildBigImageView.getContext();
        if (snapshot != null && snapshot.getDrawable() != null && ((Integer) snapshot.getTag()).intValue() == position) {
            if (viewBuildBigImageView instanceof PhotoView) {
                try {
                    ((PhotoView) viewBuildBigImageView).setImageDrawable(snapshot.getDrawable().getConstantState().newDrawable());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                ((SubsamplingScaleImageView) viewBuildBigImageView).setImage(ImageSource.bitmap(XPopupUtils.view2Bitmap(snapshot)));
            }
        }
        GlideUrl glideUrlGenerateUrl = url instanceof String ? GlideUtils.generateUrl(String.valueOf(url)) : null;
        RequestBuilder<File> requestBuilderDownloadOnly = Glide.with(viewBuildBigImageView).downloadOnly();
        if (glideUrlGenerateUrl != null) {
            url = glideUrlGenerateUrl;
        }
        requestBuilderDownloadOnly.load(url).into((RequestBuilder<File>) new ImageDownloadTarget() { // from class: com.psychiatrygarden.utils.ImageLoader.1
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                progressBar.setVisibility(8);
                View view = viewBuildBigImageView;
                if (!(view instanceof PhotoView)) {
                    ((SubsamplingScaleImageView) view).setImage(ImageSource.resource(ImageLoader.this.errImg));
                } else {
                    ((PhotoView) view).setImageResource(ImageLoader.this.errImg);
                    ((PhotoView) viewBuildBigImageView).setZoomable(false);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(@NonNull File resource, Transition<? super File> transition) {
                boolean z2;
                super.onResourceReady(resource, transition);
                int windowWidth = XPopupUtils.getWindowWidth(context) * 2;
                int screenHeight = XPopupUtils.getScreenHeight(context) * 2;
                int[] imageSize = XPopupUtils.getImageSize(resource);
                int rotateDegree = XPopupUtils.getRotateDegree(resource.getAbsolutePath());
                View view = viewBuildBigImageView;
                if (view instanceof PhotoView) {
                    progressBar.setVisibility(8);
                    ((PhotoView) viewBuildBigImageView).setZoomable(true);
                    if (imageSize[0] <= windowWidth && imageSize[1] <= screenHeight) {
                        Glide.with(viewBuildBigImageView).load(resource).transform(new Rotate(rotateDegree)).apply((BaseRequestOptions<?>) new RequestOptions().error(ImageLoader.this.errImg).override(imageSize[0], imageSize[1])).into((PhotoView) viewBuildBigImageView);
                        return;
                    } else {
                        ((PhotoView) viewBuildBigImageView).setImageBitmap(XPopupUtils.rotate(XPopupUtils.getBitmap(resource, windowWidth, screenHeight), rotateDegree, imageSize[0] / 2.0f, imageSize[1] / 2.0f));
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
                subsamplingScaleImageView.setOnImageEventListener(new SSIVListener(subsamplingScaleImageView, progressBar, ImageLoader.this.errImg, z2));
                subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)).dimensions(imageSize[0], imageSize[1]), ImageSource.cachedBitmap(XPopupUtils.getBitmap(resource, XPopupUtils.getWindowWidth(context), XPopupUtils.getScreenHeight(context))));
            }
        });
        return viewBuildBigImageView;
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public void loadSnapshot(@NonNull Object uri, @NonNull final PhotoView snapshot) {
        GlideUrl glideUrlGenerateUrl = uri instanceof String ? GlideUtils.generateUrl(String.valueOf(uri)) : null;
        RequestBuilder<File> requestBuilderDownloadOnly = Glide.with(snapshot).downloadOnly();
        if (glideUrlGenerateUrl != null) {
            uri = glideUrlGenerateUrl;
        }
        requestBuilderDownloadOnly.load(uri).into((RequestBuilder<File>) new ImageDownloadTarget() { // from class: com.psychiatrygarden.utils.ImageLoader.8
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(@NonNull File resource, Transition<? super File> transition) {
                super.onResourceReady(resource, transition);
                int rotateDegree = XPopupUtils.getRotateDegree(resource.getAbsolutePath());
                int windowWidth = XPopupUtils.getWindowWidth(snapshot.getContext());
                int screenHeight = XPopupUtils.getScreenHeight(snapshot.getContext());
                int[] imageSize = XPopupUtils.getImageSize(resource);
                if (imageSize[0] <= windowWidth && imageSize[1] <= screenHeight) {
                    Glide.with(snapshot).load(resource).apply((BaseRequestOptions<?>) new RequestOptions().override(imageSize[0], imageSize[1])).into(snapshot);
                } else {
                    snapshot.setImageBitmap(XPopupUtils.rotate(XPopupUtils.getBitmap(resource, windowWidth, screenHeight), rotateDegree, imageSize[0] / 2.0f, imageSize[1] / 2.0f));
                }
            }
        });
    }

    public ImageLoader(int errImgRes) {
        this.errImg = errImgRes;
    }

    public ImageLoader(boolean bigImage, int errImgRes) {
        this(errImgRes);
        this.mBigImage = bigImage;
    }
}
