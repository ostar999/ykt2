package com.lwkandroid.widget.ngv;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/* loaded from: classes4.dex */
public class NgvChildImageView extends ViewGroup {
    private int mContentImageHeight;
    private ImageView mContentImageView;
    private int mContentImageWidth;
    private int mDeleteImageSize;
    private float mDeleteImageSizeRatio;
    private ImageView mDeleteImageView;
    private boolean mShowDeleteImageView;

    public static class SavedViewState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedViewState> CREATOR = new Parcelable.Creator<SavedViewState>() { // from class: com.lwkandroid.widget.ngv.NgvChildImageView.SavedViewState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedViewState createFromParcel(Parcel source) {
                return new SavedViewState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedViewState[] newArray(int size) {
                return new SavedViewState[size];
            }
        };
        private float mDeleteImageSizeRatio;
        private boolean mShowDeleteImageView;

        public SavedViewState(Parcelable superState) {
            super(superState);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            if (Build.VERSION.SDK_INT >= 29) {
                parcel.writeBoolean(this.mShowDeleteImageView);
            } else {
                parcel.writeByte(this.mShowDeleteImageView ? (byte) 1 : (byte) 0);
            }
            parcel.writeFloat(this.mDeleteImageSizeRatio);
        }

        public SavedViewState(Parcel source) {
            super(source);
            if (Build.VERSION.SDK_INT >= 29) {
                this.mShowDeleteImageView = source.readBoolean();
            } else {
                this.mShowDeleteImageView = source.readByte() == 1;
            }
            this.mDeleteImageSizeRatio = source.readFloat();
        }
    }

    public NgvChildImageView(Context context) {
        this(context, null);
    }

    private void initChildView(Context context) {
        this.mContentImageView = new ImageView(context);
        this.mDeleteImageView = new ImageView(context);
        addView(this.mContentImageView);
        addView(this.mDeleteImageView);
    }

    public int getContentImageHeight() {
        return this.mContentImageHeight;
    }

    public int getContentImageWidth() {
        return this.mContentImageWidth;
    }

    public float getDeleteImageSizeRatio() {
        return this.mDeleteImageSizeRatio;
    }

    public ImageView getImageContent() {
        return this.mContentImageView;
    }

    public ImageView getImageDelete() {
        return this.mDeleteImageView;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int measuredWidth = getMeasuredWidth();
        ImageView imageView = this.mDeleteImageView;
        if (imageView != null) {
            int i2 = this.mDeleteImageSize;
            imageView.layout(measuredWidth - i2, 0, measuredWidth, i2);
        }
        ImageView imageView2 = this.mContentImageView;
        if (imageView2 != null) {
            int i3 = this.mDeleteImageSize;
            imageView2.layout(i3 / 2, i3 / 2, (i3 / 2) + this.mContentImageWidth, (i3 / 2) + this.mContentImageHeight);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int iMin = this.mShowDeleteImageView ? (int) (Math.min(size, size2) * this.mDeleteImageSizeRatio) : 0;
        this.mDeleteImageSize = iMin;
        if (this.mDeleteImageView != null) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, 1073741824);
            this.mDeleteImageView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        }
        if (this.mContentImageView != null) {
            int i2 = this.mDeleteImageSize;
            int i3 = size - i2;
            this.mContentImageWidth = i3;
            this.mContentImageHeight = size2 - i2;
            this.mContentImageView.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mContentImageHeight, 1073741824));
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedViewState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedViewState savedViewState = (SavedViewState) state;
        super.onRestoreInstanceState(savedViewState);
        this.mShowDeleteImageView = savedViewState.mShowDeleteImageView;
        this.mDeleteImageSizeRatio = savedViewState.mDeleteImageSizeRatio;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedViewState savedViewState = new SavedViewState(super.onSaveInstanceState());
        savedViewState.mShowDeleteImageView = this.mShowDeleteImageView;
        savedViewState.mDeleteImageSizeRatio = this.mDeleteImageSizeRatio;
        return savedViewState;
    }

    public void setContentImageResource(int resId) {
        ImageView imageView = this.mContentImageView;
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
    }

    public void setContentImageScaleType(ImageView.ScaleType scaleType) {
        ImageView imageView = this.mContentImageView;
        if (imageView != null) {
            imageView.setScaleType(scaleType);
        }
    }

    public void setDeleteImageDrawable(Drawable deleteDrawable) {
        ImageView imageView = this.mDeleteImageView;
        if (imageView != null) {
            imageView.setImageDrawable(deleteDrawable);
        }
    }

    public void setDeleteImageResource(int resId) {
        ImageView imageView = this.mDeleteImageView;
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
    }

    public void setDeleteImageSizeRatio(float sizeRatio) {
        this.mDeleteImageSizeRatio = sizeRatio;
    }

    public void showDeleteImageView(boolean show) {
        this.mShowDeleteImageView = show;
        requestLayout();
    }

    public NgvChildImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChildView(context);
    }
}
