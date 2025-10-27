package com.luck.picture.lib.adapter;

import android.util.LruCache;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.adapter.holder.PreviewVideoHolder;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.List;

/* loaded from: classes4.dex */
public class PicturePreviewAdapter extends RecyclerView.Adapter<BasePreviewHolder> {
    private final List<LocalMedia> mData;
    private final LruCache<Integer, BasePreviewHolder> mHolderLruCache = new LruCache<>(3);
    private final BasePreviewHolder.OnPreviewEventListener onPreviewEventListener;

    public PicturePreviewAdapter(List<LocalMedia> list, BasePreviewHolder.OnPreviewEventListener onPreviewEventListener) {
        this.mData = list;
        this.onPreviewEventListener = onPreviewEventListener;
    }

    public void destroyVideo(int i2) {
        BasePreviewHolder basePreviewHolder = this.mHolderLruCache.get(Integer.valueOf(i2));
        if (basePreviewHolder instanceof PreviewVideoHolder) {
            ((PreviewVideoHolder) basePreviewHolder).releaseVideo();
        }
    }

    public BasePreviewHolder getCurrentHolder(int i2) {
        return this.mHolderLruCache.get(Integer.valueOf(i2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<LocalMedia> list = this.mData;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        return PictureMimeType.isHasVideo(this.mData.get(i2).getMimeType()) ? 2 : 1;
    }

    public void setVideoPlayButtonUI(int i2) {
        BasePreviewHolder currentHolder = getCurrentHolder(i2);
        if (currentHolder instanceof PreviewVideoHolder) {
            PreviewVideoHolder previewVideoHolder = (PreviewVideoHolder) currentHolder;
            if (previewVideoHolder.ivPlayButton.getVisibility() == 8) {
                previewVideoHolder.ivPlayButton.setVisibility(0);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull BasePreviewHolder basePreviewHolder, int i2) {
        basePreviewHolder.setOnPreviewEventListener(this.onPreviewEventListener);
        LocalMedia localMedia = this.mData.get(i2);
        this.mHolderLruCache.put(Integer.valueOf(i2), basePreviewHolder);
        basePreviewHolder.bindData(localMedia, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BasePreviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        if (i2 == 2) {
            int layoutResource = InjectResourceSource.getLayoutResource(viewGroup.getContext(), 8);
            if (layoutResource == 0) {
                layoutResource = R.layout.ps_preview_video;
            }
            return BasePreviewHolder.generate(viewGroup, i2, layoutResource);
        }
        int layoutResource2 = InjectResourceSource.getLayoutResource(viewGroup.getContext(), 7);
        if (layoutResource2 == 0) {
            layoutResource2 = R.layout.ps_preview_image;
        }
        return BasePreviewHolder.generate(viewGroup, i2, layoutResource2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(@NonNull BasePreviewHolder basePreviewHolder) {
        super.onViewDetachedFromWindow((PicturePreviewAdapter) basePreviewHolder);
        if (basePreviewHolder instanceof PreviewVideoHolder) {
            ((PreviewVideoHolder) basePreviewHolder).releaseVideo();
        }
    }
}
