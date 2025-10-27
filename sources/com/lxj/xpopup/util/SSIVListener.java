package com.lxj.xpopup.util;

import android.widget.ProgressBar;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/* loaded from: classes4.dex */
public class SSIVListener implements SubsamplingScaleImageView.OnImageEventListener {
    private final int errorImage;
    private final boolean longImage;
    private final ProgressBar progressBar;
    private final SubsamplingScaleImageView ssiv;

    public SSIVListener(SubsamplingScaleImageView subsamplingScaleImageView, ProgressBar progressBar, int i2, boolean z2) {
        this.ssiv = subsamplingScaleImageView;
        this.progressBar = progressBar;
        this.errorImage = i2;
        this.longImage = z2;
    }

    @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
    public void onImageLoadError(Exception exc) {
        this.ssiv.setImage(ImageSource.resource(this.errorImage));
        this.progressBar.setVisibility(4);
    }

    @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
    public void onImageLoaded() {
        this.progressBar.setVisibility(4);
        if (this.longImage) {
            this.ssiv.setMinimumScaleType(4);
        } else {
            this.ssiv.setMinimumScaleType(1);
        }
    }

    @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
    public void onPreviewLoadError(Exception exc) {
    }

    @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
    public void onPreviewReleased() {
    }

    @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
    public void onReady() {
    }

    @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
    public void onTileLoadError(Exception exc) {
    }
}
