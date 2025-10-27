package com.lwkandroid.widget.ngv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.core.content.res.ResourcesCompat;
import com.lwkandroid.widget.ngv.AbsNgvAdapter;
import java.util.List;

/* loaded from: classes4.dex */
public class NineGridView extends ViewGroup implements AbsNgvAdapter.OnDataChangedListener {
    public static final ImageView.ScaleType[] SCALE_TYPE_ARRAY = {ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    private AbsNgvAdapter mAdapter;
    private NgvAttrOptions mAttrOptions;
    private int mMeasuredWidth;
    private View mPlusImageView;

    public static class SavedViewState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedViewState> CREATOR = new Parcelable.Creator<SavedViewState>() { // from class: com.lwkandroid.widget.ngv.NineGridView.SavedViewState.1
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
        private List mDataList;
        private int mDividerSize;
        private boolean mEnableEditMode;
        private int mHorizontalChildCount;
        private byte[] mIconDeleteDrawable;
        private float mIconDeleteSizeRatio;
        private byte[] mIconPlusDrawable;
        private int mImageScaleType;
        private int mSingleImageHeight;
        private int mSingleImageWidth;

        public SavedViewState(Parcelable superState) {
            super(superState);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.mDividerSize);
            parcel.writeInt(this.mSingleImageWidth);
            parcel.writeInt(this.mSingleImageHeight);
            parcel.writeByteArray(this.mIconPlusDrawable);
            parcel.writeByteArray(this.mIconDeleteDrawable);
            parcel.writeFloat(this.mIconDeleteSizeRatio);
            if (Build.VERSION.SDK_INT >= 29) {
                parcel.writeBoolean(this.mEnableEditMode);
            } else {
                parcel.writeByte(this.mEnableEditMode ? (byte) 1 : (byte) 0);
            }
            parcel.writeInt(this.mHorizontalChildCount);
            parcel.writeInt(this.mImageScaleType);
            parcel.writeList(this.mDataList);
        }

        public SavedViewState(Parcel source) {
            super(source);
            this.mDividerSize = source.readInt();
            this.mSingleImageWidth = source.readInt();
            this.mSingleImageHeight = source.readInt();
            source.readByteArray(this.mIconPlusDrawable);
            source.readByteArray(this.mIconDeleteDrawable);
            this.mIconDeleteSizeRatio = source.readFloat();
            if (Build.VERSION.SDK_INT >= 29) {
                this.mEnableEditMode = source.readBoolean();
            } else {
                this.mEnableEditMode = source.readByte() == 1;
            }
            this.mHorizontalChildCount = source.readInt();
            this.mImageScaleType = source.readInt();
            source.readList(this.mDataList, Object.class.getClassLoader());
        }
    }

    public NineGridView(Context context) {
        this(context, null);
    }

    private void autoAdjustPlusImageView() {
        if (canShowPlusImageView()) {
            showPlusImageView();
        } else {
            removePlusImageView();
        }
    }

    private View buildChildView(int position) {
        View viewCreateContentView = this.mAdapter.createContentView(getContext());
        AbsNgvAdapter absNgvAdapter = this.mAdapter;
        absNgvAdapter.bindContentView(viewCreateContentView, absNgvAdapter.getDataList().get(position), position, this.mAttrOptions);
        return viewCreateContentView;
    }

    private boolean canShowPlusImageView() {
        AbsNgvAdapter absNgvAdapter;
        return this.mAttrOptions.isEnableEditMode() && ((absNgvAdapter = this.mAdapter) == null || !absNgvAdapter.isDataToLimited());
    }

    public static int getImageScaleTypeIndex(ImageView.ScaleType scaleType) {
        if (ImageView.ScaleType.MATRIX == scaleType) {
            return 0;
        }
        if (ImageView.ScaleType.FIT_XY == scaleType) {
            return 1;
        }
        if (ImageView.ScaleType.FIT_START == scaleType) {
            return 2;
        }
        if (ImageView.ScaleType.FIT_CENTER == scaleType) {
            return 3;
        }
        if (ImageView.ScaleType.FIT_END == scaleType) {
            return 4;
        }
        if (ImageView.ScaleType.CENTER == scaleType) {
            return 5;
        }
        if (ImageView.ScaleType.CENTER_CROP == scaleType) {
            return 6;
        }
        return ImageView.ScaleType.CENTER_INSIDE == scaleType ? 7 : -1;
    }

    private void removePlusImageView() {
        View view = this.mPlusImageView;
        if (view != null) {
            removeView(view);
        }
        this.mPlusImageView = null;
    }

    private void showPlusImageView() {
        AbsNgvAdapter absNgvAdapter;
        if (this.mPlusImageView != null || (absNgvAdapter = this.mAdapter) == null) {
            return;
        }
        View viewCreatePlusView = absNgvAdapter.createPlusView(getContext());
        this.mPlusImageView = viewCreatePlusView;
        this.mAdapter.bindPlusView(viewCreatePlusView, this.mAttrOptions);
        addView(this.mPlusImageView);
    }

    public AbsNgvAdapter getAdapter() {
        return this.mAdapter;
    }

    public boolean isEditModeEnabled() {
        return this.mAttrOptions.isEnableEditMode();
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter.OnDataChangedListener
    public void onAllDataChanged(List dataList, boolean reachLimitedSize) {
        removePlusImageView();
        removeAllViews();
        int size = this.mAdapter.getDataList().size();
        for (int i2 = 0; i2 < size; i2++) {
            addView(buildChildView(i2));
        }
        autoAdjustPlusImageView();
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter.OnDataChangedListener
    public void onDataAdded(Object data, int position, boolean reachLimitedSize) {
        addView(buildChildView(position), position);
        autoAdjustPlusImageView();
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter.OnDataChangedListener
    public void onDataChanged(Object data, int position, boolean reachLimitedSize) {
        this.mAdapter.bindContentView(getChildAt(position), data, position, this.mAttrOptions);
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter.OnDataChangedListener
    public void onDataListAdded(List dataList, int startPosition, boolean reachLimitedSize) {
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            onDataAdded(dataList.get(i2), startPosition + i2, reachLimitedSize);
        }
        autoAdjustPlusImageView();
    }

    @Override // com.lwkandroid.widget.ngv.AbsNgvAdapter.OnDataChangedListener
    public void onDataRemoved(Object data, int position, boolean reachLimitedSize) {
        removeViewAt(position);
        autoAdjustPlusImageView();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        autoAdjustPlusImageView();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int horizontalChildCount = i2 / this.mAttrOptions.getHorizontalChildCount();
            int measuredWidth = ((childAt.getMeasuredWidth() + this.mAttrOptions.getDividerSize()) * (i2 % this.mAttrOptions.getHorizontalChildCount())) + getPaddingLeft();
            int measuredHeight = ((childAt.getMeasuredHeight() + this.mAttrOptions.getDividerSize()) * horizontalChildCount) + getPaddingTop();
            childAt.layout(measuredWidth, measuredHeight, childAt.getMeasuredWidth() + measuredWidth, childAt.getMeasuredHeight() + measuredHeight);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x015b A[LOOP:0: B:30:0x0159->B:31:0x015b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r11, int r12) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lwkandroid.widget.ngv.NineGridView.onMeasure(int, int):void");
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedViewState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedViewState savedViewState = (SavedViewState) state;
        super.onRestoreInstanceState(savedViewState);
        NgvAttrOptions ngvAttrOptions = this.mAttrOptions;
        if (ngvAttrOptions != null) {
            ngvAttrOptions.setDividerSize(savedViewState.mDividerSize);
            this.mAttrOptions.setSingleImageWidth(savedViewState.mSingleImageWidth);
            this.mAttrOptions.setSingleImageHeight(savedViewState.mSingleImageHeight);
            this.mAttrOptions.setIconPlusDrawable(Utils.bytes2Drawable(getContext(), savedViewState.mIconPlusDrawable));
            this.mAttrOptions.setIconDeleteDrawable(Utils.bytes2Drawable(getContext(), savedViewState.mIconDeleteDrawable));
            this.mAttrOptions.setIconDeleteSizeRatio(savedViewState.mIconDeleteSizeRatio);
            this.mAttrOptions.setEnableEditMode(savedViewState.mEnableEditMode);
            this.mAttrOptions.setHorizontalChildCount(savedViewState.mHorizontalChildCount);
            this.mAttrOptions.setImageScaleType(SCALE_TYPE_ARRAY[savedViewState.mImageScaleType]);
        }
        AbsNgvAdapter absNgvAdapter = this.mAdapter;
        if (absNgvAdapter != null) {
            absNgvAdapter.setDataList(savedViewState.mDataList);
        }
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedViewState savedViewState = new SavedViewState(super.onSaveInstanceState());
        savedViewState.mDividerSize = this.mAttrOptions.getDividerSize();
        savedViewState.mSingleImageWidth = this.mAttrOptions.getSingleImageWidth();
        savedViewState.mSingleImageHeight = this.mAttrOptions.getSingleImageHeight();
        savedViewState.mIconPlusDrawable = Utils.drawable2Bytes(this.mAttrOptions.getIconPlusDrawable());
        savedViewState.mIconDeleteDrawable = Utils.drawable2Bytes(this.mAttrOptions.getIconDeleteDrawable());
        savedViewState.mIconDeleteSizeRatio = this.mAttrOptions.getIconDeleteSizeRatio();
        savedViewState.mEnableEditMode = this.mAttrOptions.isEnableEditMode();
        savedViewState.mHorizontalChildCount = this.mAttrOptions.getHorizontalChildCount();
        savedViewState.mImageScaleType = getImageScaleTypeIndex(this.mAttrOptions.getImageScaleType());
        savedViewState.mDataList = this.mAdapter.getDataList();
        return savedViewState;
    }

    public void setAdapter(AbsNgvAdapter adapter) {
        this.mAdapter = adapter;
        if (adapter != null) {
            onAllDataChanged(adapter.getDataList(), this.mAdapter.isDataToLimited());
            this.mAdapter.addDataChangedListener(this);
        }
    }

    public void setDividerLineSize(int unit, int size) {
        this.mAttrOptions.setDividerSize((int) TypedValue.applyDimension(unit, size, getResources().getDisplayMetrics()));
        requestLayout();
    }

    public void setEnableEditMode(boolean enable) {
        this.mAttrOptions.setEnableEditMode(enable);
        AbsNgvAdapter absNgvAdapter = this.mAdapter;
        if (absNgvAdapter != null) {
            List dataList = absNgvAdapter.getDataList();
            for (int i2 = 0; i2 < dataList.size(); i2++) {
                this.mAdapter.bindContentView(getChildAt(i2), dataList.get(i2), i2, this.mAttrOptions);
            }
        }
        autoAdjustPlusImageView();
        requestLayout();
    }

    public void setHorizontalChildCount(int count) {
        this.mAttrOptions.setHorizontalChildCount(count);
        requestLayout();
    }

    public void setIconDeleteDrawable(@DrawableRes int resId) {
        setIconDeleteDrawable(ResourcesCompat.getDrawable(getResources(), resId, getContext().getTheme()));
    }

    public void setIconDeleteSizeRatio(@FloatRange(from = 0.0d, to = 1.0d) float ratio) {
        this.mAttrOptions.setIconDeleteSizeRatio(ratio);
        requestLayout();
    }

    public void setIconPlusDrawable(@DrawableRes int resId) {
        setIconPlusDrawable(ResourcesCompat.getDrawable(getResources(), resId, getContext().getTheme()));
    }

    public void setImageScaleType(ImageView.ScaleType scaleType) {
        this.mAttrOptions.setImageScaleType(scaleType);
        requestLayout();
    }

    public void setSingleImageSize(int unit, int width, int height) {
        this.mAttrOptions.setSingleImageWidth((int) TypedValue.applyDimension(unit, width, getResources().getDisplayMetrics()));
        this.mAttrOptions.setSingleImageHeight((int) TypedValue.applyDimension(unit, height, getResources().getDisplayMetrics()));
        requestLayout();
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        NgvAttrOptions ngvAttrOptions = new NgvAttrOptions();
        this.mAttrOptions = ngvAttrOptions;
        ngvAttrOptions.setDividerSize(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NineGridView_divider_line_size, context.getResources().getDimensionPixelOffset(R.dimen.ngv_divider_line_size_default)));
        this.mAttrOptions.setSingleImageWidth(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NineGridView_single_image_width, 0));
        this.mAttrOptions.setSingleImageHeight(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NineGridView_single_image_height, 0));
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NineGridView_icon_plus_drawable);
        this.mAttrOptions.setIconPlusDrawable(drawable == null ? ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_ngv_plus, context.getTheme()) : drawable);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NineGridView_icon_delete_drawable);
        this.mAttrOptions.setIconDeleteDrawable(drawable2 == null ? ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_ngv_delete, context.getTheme()) : drawable2);
        this.mAttrOptions.setIconDeleteSizeRatio((float) Math.min(1.0d, Math.max(0.0d, typedArrayObtainStyledAttributes.getFloat(R.styleable.NineGridView_icon_delete_size_ratio, 0.2f))));
        this.mAttrOptions.setEnableEditMode(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NineGridView_enable_edit_mode, false));
        this.mAttrOptions.setHorizontalChildCount(typedArrayObtainStyledAttributes.getInt(R.styleable.NineGridView_horizontal_child_count, 3));
        this.mAttrOptions.setImageScaleType(SCALE_TYPE_ARRAY[typedArrayObtainStyledAttributes.getInt(R.styleable.NineGridView_android_scaleType, 6)]);
    }

    public void setIconDeleteDrawable(Drawable drawable) {
        this.mAttrOptions.setIconDeleteDrawable(drawable);
        requestLayout();
    }

    public void setIconPlusDrawable(Drawable drawable) {
        this.mAttrOptions.setIconPlusDrawable(drawable);
        requestLayout();
    }
}
