package com.lwkandroid.widget.ngv;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import java.util.List;

/* loaded from: classes4.dex */
public class DefaultNgvAdapter<D> extends AbsNgvAdapter<ImageView, NgvChildImageView, D> {
    private INgvImageLoader<D> mImageLoader;
    private OnChildClickedListener<D> mListener;

    public interface OnChildClickedListener<D> {
        void onContentImageClicked(int position, D data, NgvChildImageView childImageView);

        void onImageDeleted(int position, D data);

        void onPlusImageClicked(ImageView plusImageView, int dValueToLimited);
    }

    public DefaultNgvAdapter(int maxDataSize, INgvImageLoader<D> mImageLoader) {
        super(maxDataSize);
        this.mImageLoader = mImageLoader;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindContentView$1(final int position, final Object data, final NgvChildImageView childView, View v2) {
        OnChildClickedListener<D> onChildClickedListener = this.mListener;
        if (onChildClickedListener != null) {
            onChildClickedListener.onContentImageClicked(position, data, childView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$bindContentView$2(final Object data, final int position, View v2) {
        removeData((DefaultNgvAdapter<D>) data);
        OnChildClickedListener<D> onChildClickedListener = this.mListener;
        if (onChildClickedListener != null) {
            onChildClickedListener.onImageDeleted(position, data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindPlusView$0(final ImageView plusView, View v2) {
        OnChildClickedListener<D> onChildClickedListener = this.mListener;
        if (onChildClickedListener != null) {
            onChildClickedListener.onPlusImageClicked(plusView, getDValueToLimited());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter
    public /* bridge */ /* synthetic */ void bindContentView(View childView, Object data, int position, NgvAttrOptions attrOptions) {
        bindContentView((NgvChildImageView) childView, (NgvChildImageView) data, position, attrOptions);
    }

    public OnChildClickedListener<D> getOnChildClickedListener() {
        return this.mListener;
    }

    public void setOnChildClickListener(OnChildClickedListener<D> listener) {
        this.mListener = listener;
    }

    public void bindContentView(final NgvChildImageView childView, final D data, final int position, NgvAttrOptions attrOptions) {
        childView.getImageContent().setScaleType(attrOptions.getImageScaleType());
        childView.setDeleteImageSizeRatio(attrOptions.getIconDeleteSizeRatio());
        childView.setDeleteImageDrawable(attrOptions.getIconDeleteDrawable());
        childView.showDeleteImageView(attrOptions.isEnableEditMode());
        INgvImageLoader<D> iNgvImageLoader = this.mImageLoader;
        if (iNgvImageLoader != null) {
            iNgvImageLoader.load(data, childView.getImageContent(), childView.getContentImageWidth(), childView.getContentImageHeight());
        }
        childView.getImageContent().setOnClickListener(new View.OnClickListener() { // from class: com.lwkandroid.widget.ngv.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f8890c.lambda$bindContentView$1(position, data, childView, view);
            }
        });
        childView.getImageDelete().setOnClickListener(new View.OnClickListener() { // from class: com.lwkandroid.widget.ngv.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f8894c.lambda$bindContentView$2(data, position, view);
            }
        });
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter
    public void bindPlusView(final ImageView plusView, NgvAttrOptions attrOptions) {
        plusView.setImageDrawable(attrOptions.getIconPlusDrawable());
        plusView.setScaleType(ImageView.ScaleType.FIT_XY);
        plusView.setOnClickListener(new View.OnClickListener() { // from class: com.lwkandroid.widget.ngv.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f8897c.lambda$bindPlusView$0(plusView, view);
            }
        });
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter
    public NgvChildImageView createContentView(Context context) {
        return new NgvChildImageView(context);
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter
    public ImageView createPlusView(Context context) {
        return new ImageView(context);
    }

    public DefaultNgvAdapter(int maxDataSize, List<D> dataList, INgvImageLoader<D> mImageLoader) {
        super(maxDataSize, dataList);
        this.mImageLoader = mImageLoader;
    }
}
