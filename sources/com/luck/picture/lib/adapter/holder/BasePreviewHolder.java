package com.luck.picture.lib.adapter.holder;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.utils.BitmapUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.MediaUtils;

/* loaded from: classes4.dex */
public class BasePreviewHolder extends RecyclerView.ViewHolder {
    public static final int ADAPTER_TYPE_IMAGE = 1;
    public static final int ADAPTER_TYPE_VIDEO = 2;
    protected final PictureSelectionConfig config;
    public PhotoView coverImageView;
    protected OnPreviewEventListener mPreviewEventListener;
    protected final int screenAppInHeight;
    protected final int screenHeight;
    protected final int screenWidth;

    public interface OnPreviewEventListener {
        void onBackPressed();

        void onLoadCompleteBeginScale(BasePreviewHolder basePreviewHolder, int i2, int i3);

        void onLoadCompleteError(BasePreviewHolder basePreviewHolder);

        void onLongPressDownload(LocalMedia localMedia);

        void onPreviewVideoTitle(String str);
    }

    public BasePreviewHolder(@NonNull View view) {
        super(view);
        this.config = PictureSelectionConfig.getInstance();
        this.screenWidth = DensityUtil.getRealScreenWidth(view.getContext());
        this.screenHeight = DensityUtil.getScreenHeight(view.getContext());
        this.screenAppInHeight = DensityUtil.getRealScreenHeight(view.getContext());
        this.coverImageView = (PhotoView) view.findViewById(R.id.preview_image);
    }

    public static BasePreviewHolder generate(ViewGroup viewGroup, int i2, int i3) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(i3, viewGroup, false);
        return i2 == 2 ? new PreviewVideoHolder(viewInflate) : new PreviewImageHolder(viewInflate);
    }

    public void bindData(final LocalMedia localMedia, int i2) {
        final String availablePath = localMedia.getAvailablePath();
        int[] maxImageSize = BitmapUtils.getMaxImageSize(this.itemView.getContext(), localMedia.getWidth(), localMedia.getHeight(), this.screenWidth, this.screenHeight);
        PictureSelectionConfig.imageEngine.loadImageBitmap(this.itemView.getContext(), availablePath, maxImageSize[0], maxImageSize[1], new OnCallbackListener<Bitmap>() { // from class: com.luck.picture.lib.adapter.holder.BasePreviewHolder.1
            @Override // com.luck.picture.lib.interfaces.OnCallbackListener
            public void onCall(Bitmap bitmap) {
                if (bitmap == null) {
                    BasePreviewHolder basePreviewHolder = BasePreviewHolder.this;
                    basePreviewHolder.mPreviewEventListener.onLoadCompleteError(basePreviewHolder);
                    return;
                }
                if (PictureMimeType.isHasWebp(localMedia.getMimeType()) || PictureMimeType.isUrlHasWebp(availablePath) || PictureMimeType.isUrlHasGif(availablePath) || PictureMimeType.isHasGif(localMedia.getMimeType())) {
                    PictureSelectionConfig.imageEngine.loadImage(BasePreviewHolder.this.itemView.getContext(), availablePath, BasePreviewHolder.this.coverImageView);
                } else {
                    BasePreviewHolder.this.coverImageView.setImageBitmap(bitmap);
                }
                if (MediaUtils.isLongImage(bitmap.getWidth(), bitmap.getHeight())) {
                    BasePreviewHolder.this.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    BasePreviewHolder.this.coverImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                BasePreviewHolder basePreviewHolder2 = BasePreviewHolder.this;
                basePreviewHolder2.mPreviewEventListener.onLoadCompleteBeginScale(basePreviewHolder2, bitmap.getWidth(), bitmap.getHeight());
            }
        });
        setScaleDisplaySize(localMedia);
        this.coverImageView.setOnViewTapListener(new OnViewTapListener() { // from class: com.luck.picture.lib.adapter.holder.BasePreviewHolder.2
            @Override // com.luck.picture.lib.photoview.OnViewTapListener
            public void onViewTap(View view, float f2, float f3) {
                OnPreviewEventListener onPreviewEventListener = BasePreviewHolder.this.mPreviewEventListener;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
        this.coverImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.BasePreviewHolder.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                OnPreviewEventListener onPreviewEventListener = BasePreviewHolder.this.mPreviewEventListener;
                if (onPreviewEventListener == null) {
                    return false;
                }
                onPreviewEventListener.onLongPressDownload(localMedia);
                return false;
            }
        });
    }

    public void setOnPreviewEventListener(OnPreviewEventListener onPreviewEventListener) {
        this.mPreviewEventListener = onPreviewEventListener;
    }

    public void setScaleDisplaySize(LocalMedia localMedia) {
        if (this.config.isPreviewZoomEffect || this.screenWidth >= this.screenHeight) {
            return;
        }
        int width = (int) (this.screenWidth / (localMedia.getWidth() / localMedia.getHeight()));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.coverImageView.getLayoutParams();
        layoutParams.width = this.screenWidth;
        int i2 = this.screenHeight;
        if (width > i2) {
            i2 = this.screenAppInHeight;
        }
        layoutParams.height = i2;
        layoutParams.gravity = 17;
    }
}
