package com.easefun.polyv.livecommon.ui.widget.imageScan;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVUrlTag;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVMyProgressManager;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVCircleProgressView;

/* loaded from: classes3.dex */
public class PLVChatImageContainerWidget extends FrameLayout {
    public static final String LOADIMG_MOUDLE_TAG = "PLVChatImageContainerWidget";
    private PLVCircleProgressView cpvImgLoading;
    private PLVUrlTag imgUrlTag;
    private PLVScaleImageView ivChatImg;
    private View.OnClickListener onClickListener;
    private int position;

    public PLVChatImageContainerWidget(@NonNull Context context) {
        this(context, null);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_image_container_layout, this);
        this.cpvImgLoading = (PLVCircleProgressView) findViewById(R.id.cpv_img_loading);
        PLVScaleImageView pLVScaleImageView = (PLVScaleImageView) findViewById(R.id.iv_chat_img);
        this.ivChatImg = pLVScaleImageView;
        pLVScaleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageContainerWidget.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (PLVChatImageContainerWidget.this.onClickListener != null) {
                    PLVChatImageContainerWidget.this.onClickListener.onClick(v2);
                }
            }
        });
        setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageContainerWidget.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (PLVChatImageContainerWidget.this.onClickListener != null) {
                    PLVChatImageContainerWidget.this.onClickListener.onClick(v2);
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.imgUrlTag != null) {
            PLVMyProgressManager.removeListener(LOADIMG_MOUDLE_TAG, LOADIMG_MOUDLE_TAG + this.imgUrlTag);
        }
        if (this.ivChatImg.getDrawable() != null) {
            this.ivChatImg.setImageDrawable(null);
        }
        PLVCircleProgressView pLVCircleProgressView = this.cpvImgLoading;
        if (pLVCircleProgressView != null) {
            pLVCircleProgressView.setTag(null);
            this.cpvImgLoading.setVisibility(8);
            this.cpvImgLoading.setProgress(0);
        }
    }

    public void setData(final PLVUrlTag imgUrlTag, final int position) {
        this.imgUrlTag = imgUrlTag;
        this.position = position;
        if (imgUrlTag != null) {
            this.cpvImgLoading.setTag(imgUrlTag);
            PLVImageLoader.getInstance().loadImage(getContext(), LOADIMG_MOUDLE_TAG, LOADIMG_MOUDLE_TAG + imgUrlTag, R.drawable.plv_icon_image_load_err, new PLVAbsProgressStatusListener(imgUrlTag.getUrl()) { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageContainerWidget.3
                @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
                public void onFailedStatus(@Nullable Exception e2, Object model) {
                    if (PLVChatImageContainerWidget.this.cpvImgLoading.getTag() != imgUrlTag) {
                        return;
                    }
                    PLVChatImageContainerWidget.this.cpvImgLoading.setVisibility(8);
                    PLVChatImageContainerWidget.this.cpvImgLoading.setProgress(0);
                    PLVChatImageContainerWidget.this.ivChatImg.setImageResource(R.drawable.plv_icon_image_load_err);
                }

                @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
                public void onProgressStatus(String url, boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                    if (PLVChatImageContainerWidget.this.cpvImgLoading.getTag() != imgUrlTag) {
                        return;
                    }
                    if (isComplete) {
                        PLVChatImageContainerWidget.this.cpvImgLoading.setVisibility(8);
                        PLVChatImageContainerWidget.this.cpvImgLoading.setProgress(100);
                    } else {
                        if (PLVChatImageContainerWidget.this.ivChatImg.getDrawable() != null) {
                            PLVChatImageContainerWidget.this.ivChatImg.setImageDrawable(null);
                        }
                        PLVChatImageContainerWidget.this.cpvImgLoading.setVisibility(0);
                        PLVChatImageContainerWidget.this.cpvImgLoading.setProgress(percentage);
                    }
                }

                @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
                public void onResourceReadyStatus(Drawable drawable) {
                    if (PLVChatImageContainerWidget.this.cpvImgLoading.getTag() != imgUrlTag) {
                        return;
                    }
                    PLVChatImageContainerWidget.this.ivChatImg.drawablePrepared(drawable);
                }

                @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressStatusListener
                public void onStartStatus(String url) {
                    if (PLVChatImageContainerWidget.this.cpvImgLoading.getTag() == imgUrlTag && PLVChatImageContainerWidget.this.cpvImgLoading.getProgress() == 0 && PLVChatImageContainerWidget.this.cpvImgLoading.getVisibility() != 0) {
                        PLVChatImageContainerWidget.this.cpvImgLoading.setVisibility(0);
                        PLVChatImageContainerWidget.this.ivChatImg.setImageDrawable(null);
                    }
                }
            }, this.ivChatImg);
        }
    }

    public PLVChatImageContainerWidget setOnImgClickListener(View.OnClickListener l2) {
        this.onClickListener = l2;
        return this;
    }

    public PLVChatImageContainerWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVChatImageContainerWidget(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
