package com.luck.picture.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.luck.picture.lib.adapter.PicturePreviewAdapter;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter;
import com.luck.picture.lib.adapter.holder.PreviewVideoHolder;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.basic.PictureMediaScannerConnection;
import com.luck.picture.lib.config.Crop;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.decoration.HorizontalItemDecoration;
import com.luck.picture.lib.decoration.WrapContentLinearLayoutManager;
import com.luck.picture.lib.dialog.PictureCommonDialog;
import com.luck.picture.lib.engine.ExtendLoaderEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.loader.LocalMediaLoader;
import com.luck.picture.lib.loader.LocalMediaPageLoader;
import com.luck.picture.lib.magical.BuildRecycleItemViewParams;
import com.luck.picture.lib.magical.MagicalView;
import com.luck.picture.lib.magical.OnMagicalViewCallback;
import com.luck.picture.lib.magical.ViewParams;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.BitmapUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.DownloadFileUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ToastUtils;
import com.luck.picture.lib.utils.ValueOf;
import com.luck.picture.lib.widget.BottomNavBar;
import com.luck.picture.lib.widget.CompleteSelectView;
import com.luck.picture.lib.widget.PreviewBottomNavBar;
import com.luck.picture.lib.widget.PreviewTitleBar;
import com.luck.picture.lib.widget.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectorPreviewFragment extends PictureCommonFragment {
    public static final String TAG = "PictureSelectorPreviewFragment";
    private PreviewBottomNavBar bottomNarBar;
    private CompleteSelectView completeSelectView;
    private int curPosition;
    private String currentAlbum;
    private boolean isAnimationStart;
    private boolean isDisplayDelete;
    private boolean isExternalPreview;
    private boolean isFirstLoaded;
    private boolean isInternalBottomPreview;
    private boolean isSaveInstanceState;
    private boolean isShowCamera;
    private List<View> mAnimViews;
    private PreviewGalleryAdapter mGalleryAdapter;
    private RecyclerView mGalleryRecycle;
    private MagicalView magicalView;
    private int screenHeight;
    private int screenWidth;
    private View selectClickArea;
    private PreviewTitleBar titleBar;
    private int totalNum;
    private TextView tvSelected;
    private TextView tvSelectedWord;
    private PicturePreviewAdapter viewPageAdapter;
    private ViewPager2 viewPager;
    private ArrayList<LocalMedia> mData = new ArrayList<>();
    protected boolean isHasMore = true;
    private long mBucketId = -1;
    private boolean needScaleBig = true;
    private boolean needScaleSmall = false;
    private final ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.20
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int i2, float f2, int i3) {
            ArrayList arrayList;
            if (PictureSelectorPreviewFragment.this.mData.size() > i2) {
                if (i3 < PictureSelectorPreviewFragment.this.screenWidth / 2) {
                    arrayList = PictureSelectorPreviewFragment.this.mData;
                } else {
                    arrayList = PictureSelectorPreviewFragment.this.mData;
                    i2++;
                }
                LocalMedia localMedia = (LocalMedia) arrayList.get(i2);
                PictureSelectorPreviewFragment.this.tvSelected.setSelected(PictureSelectorPreviewFragment.this.isSelected(localMedia));
                PictureSelectorPreviewFragment.this.notifyGallerySelectMedia(localMedia);
                PictureSelectorPreviewFragment.this.notifySelectNumberStyle(localMedia);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int i2) {
            PictureSelectorPreviewFragment.this.curPosition = i2;
            PictureSelectorPreviewFragment.this.titleBar.setTitle((PictureSelectorPreviewFragment.this.curPosition + 1) + "/" + PictureSelectorPreviewFragment.this.totalNum);
            if (PictureSelectorPreviewFragment.this.mData.size() > i2) {
                LocalMedia localMedia = (LocalMedia) PictureSelectorPreviewFragment.this.mData.get(i2);
                PictureSelectorPreviewFragment.this.notifySelectNumberStyle(localMedia);
                if (!PictureSelectorPreviewFragment.this.isExternalPreview && !PictureSelectorPreviewFragment.this.isInternalBottomPreview && ((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                    PictureSelectorPreviewFragment.this.changeMagicalViewParams(i2);
                }
                if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                    PictureSelectorPreviewFragment.this.viewPageAdapter.setVideoPlayButtonUI(i2);
                }
                PictureSelectorPreviewFragment.this.notifyGallerySelectMedia(localMedia);
                PictureSelectorPreviewFragment.this.bottomNarBar.isDisplayEditor(PictureMimeType.isHasVideo(localMedia.getMimeType()));
                if (PictureSelectorPreviewFragment.this.isExternalPreview || PictureSelectorPreviewFragment.this.isInternalBottomPreview || ((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isOnlySandboxDir || !((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPageStrategy) {
                    return;
                }
                if (PictureSelectorPreviewFragment.this.isHasMore) {
                    if (i2 == (r0.viewPageAdapter.getItemCount() - 1) - 10 || i2 == PictureSelectorPreviewFragment.this.viewPageAdapter.getItemCount() - 1) {
                        PictureSelectorPreviewFragment.this.loadMoreData();
                    }
                }
            }
        }
    };

    public class MyOnPreviewEventListener implements BasePreviewHolder.OnPreviewEventListener {
        private MyOnPreviewEventListener() {
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onBackPressed() {
            if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewFullScreenMode) {
                PictureSelectorPreviewFragment.this.previewFullScreenMode();
                return;
            }
            if (PictureSelectorPreviewFragment.this.isExternalPreview) {
                PictureSelectorPreviewFragment.this.handleExternalPreviewBack();
            } else if (PictureSelectorPreviewFragment.this.isInternalBottomPreview || !((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                PictureSelectorPreviewFragment.this.onBackOffFragment();
            } else {
                PictureSelectorPreviewFragment.this.magicalView.backToMin();
            }
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onLoadCompleteBeginScale(BasePreviewHolder basePreviewHolder, int i2, int i3) {
            if (PictureSelectorPreviewFragment.this.isSaveInstanceState || PictureSelectorPreviewFragment.this.isFirstLoaded || PictureSelectorPreviewFragment.this.isInternalBottomPreview || !((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                return;
            }
            PictureSelectorPreviewFragment.this.isFirstLoaded = true;
            basePreviewHolder.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int[] realSizeFromMedia = MediaUtils.isLongImage(i2, i3) ? new int[]{PictureSelectorPreviewFragment.this.screenWidth, PictureSelectorPreviewFragment.this.screenHeight} : (i2 <= 0 || i3 <= 0) ? PictureSelectorPreviewFragment.this.getRealSizeFromMedia((LocalMedia) PictureSelectorPreviewFragment.this.mData.get(PictureSelectorPreviewFragment.this.curPosition)) : new int[]{i2, i3};
            PictureSelectorPreviewFragment.this.magicalView.changeRealScreenHeight(realSizeFromMedia[0], realSizeFromMedia[1], false);
            ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(PictureSelectorPreviewFragment.this.isShowCamera ? PictureSelectorPreviewFragment.this.curPosition + 1 : PictureSelectorPreviewFragment.this.curPosition);
            if (itemViewParams == null || realSizeFromMedia[0] == 0 || realSizeFromMedia[1] == 0) {
                PictureSelectorPreviewFragment.this.magicalView.startNormal(realSizeFromMedia[0], realSizeFromMedia[1], false);
                PictureSelectorPreviewFragment.this.magicalView.setBackgroundAlpha(1.0f);
                for (int i4 = 0; i4 < PictureSelectorPreviewFragment.this.mAnimViews.size(); i4++) {
                    ((View) PictureSelectorPreviewFragment.this.mAnimViews.get(i4)).setAlpha(1.0f);
                }
            } else {
                PictureSelectorPreviewFragment.this.magicalView.setViewParams(itemViewParams.left, itemViewParams.f8889top, itemViewParams.width, itemViewParams.height, realSizeFromMedia[0], realSizeFromMedia[1]);
                PictureSelectorPreviewFragment.this.magicalView.start(false);
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(PictureSelectorPreviewFragment.this.viewPager, "alpha", 0.0f, 1.0f);
            objectAnimatorOfFloat.setDuration(50L);
            objectAnimatorOfFloat.start();
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onLoadCompleteError(BasePreviewHolder basePreviewHolder) {
            if (PictureSelectorPreviewFragment.this.isFirstLoaded || PictureSelectorPreviewFragment.this.isInternalBottomPreview || !((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                return;
            }
            PictureSelectorPreviewFragment.this.isFirstLoaded = true;
            PictureSelectorPreviewFragment.this.viewPager.setAlpha(1.0f);
            PictureSelectorPreviewFragment.this.magicalView.startNormal(0, 0, false);
            PictureSelectorPreviewFragment.this.magicalView.setBackgroundAlpha(1.0f);
            for (int i2 = 0; i2 < PictureSelectorPreviewFragment.this.mAnimViews.size(); i2++) {
                ((View) PictureSelectorPreviewFragment.this.mAnimViews.get(i2)).setAlpha(1.0f);
            }
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onLongPressDownload(LocalMedia localMedia) {
            if (PictureSelectorPreviewFragment.this.isExternalPreview) {
                PictureSelectorPreviewFragment.this.onExternalLongPressDownload(localMedia);
            }
        }

        @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder.OnPreviewEventListener
        public void onPreviewVideoTitle(String str) {
            if (!TextUtils.isEmpty(str)) {
                PictureSelectorPreviewFragment.this.titleBar.setTitle(str);
                return;
            }
            PictureSelectorPreviewFragment.this.titleBar.setTitle((PictureSelectorPreviewFragment.this.curPosition + 1) + "/" + PictureSelectorPreviewFragment.this.totalNum);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeMagicalViewParams(final int i2) {
        final LocalMedia localMedia = this.mData.get(i2);
        final int[] realSizeFromMedia = getRealSizeFromMedia(localMedia);
        int[] maxImageSize = BitmapUtils.getMaxImageSize(getContext(), realSizeFromMedia[0], realSizeFromMedia[1], this.screenWidth, this.screenHeight);
        int i3 = realSizeFromMedia[0];
        if (i3 == 0 && realSizeFromMedia[1] == 0) {
            PictureSelectionConfig.imageEngine.loadImageBitmap(getActivity(), localMedia.getPath(), maxImageSize[0], maxImageSize[1], new OnCallbackListener<Bitmap>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.21
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(Bitmap bitmap) {
                    if (ActivityCompatHelper.isDestroy(PictureSelectorPreviewFragment.this.getActivity())) {
                        return;
                    }
                    localMedia.setWidth(bitmap.getWidth());
                    localMedia.setHeight(bitmap.getHeight());
                    if (MediaUtils.isLongImage(bitmap.getWidth(), bitmap.getHeight())) {
                        realSizeFromMedia[0] = PictureSelectorPreviewFragment.this.screenWidth;
                        realSizeFromMedia[1] = PictureSelectorPreviewFragment.this.screenHeight;
                    } else {
                        realSizeFromMedia[0] = bitmap.getWidth();
                        realSizeFromMedia[1] = bitmap.getHeight();
                    }
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                    int[] iArr = realSizeFromMedia;
                    pictureSelectorPreviewFragment.setMagicalViewViewParams(iArr[0], iArr[1], i2);
                }
            });
        } else {
            setMagicalViewViewParams(i3, realSizeFromMedia[1], i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NotifyDataSetChanged"})
    public void deletePreview() {
        OnExternalPreviewEventListener onExternalPreviewEventListener;
        if (!this.isDisplayDelete || (onExternalPreviewEventListener = PictureSelectionConfig.onExternalPreviewEventListener) == null) {
            return;
        }
        onExternalPreviewEventListener.onPreviewDelete(this.viewPager.getCurrentItem());
        int currentItem = this.viewPager.getCurrentItem();
        this.mData.remove(currentItem);
        if (this.mData.size() == 0) {
            handleExternalPreviewBack();
            return;
        }
        this.titleBar.setTitle(getString(R.string.ps_preview_image_num, Integer.valueOf(this.curPosition + 1), Integer.valueOf(this.mData.size())));
        this.totalNum = this.mData.size();
        this.curPosition = currentItem;
        if (this.viewPager.getAdapter() != null) {
            this.viewPager.setAdapter(null);
            this.viewPager.setAdapter(this.viewPageAdapter);
        }
        this.viewPager.setCurrentItem(this.curPosition, false);
    }

    private void externalPreviewStyle() {
        this.titleBar.getImageDelete().setVisibility(this.isDisplayDelete ? 0 : 8);
        this.tvSelected.setVisibility(8);
        this.bottomNarBar.setVisibility(8);
        this.completeSelectView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getRealSizeFromMedia(LocalMedia localMedia) {
        int height;
        int i2;
        if (MediaUtils.isLongImage(localMedia.getWidth(), localMedia.getHeight())) {
            i2 = this.screenWidth;
            height = this.screenHeight;
        } else {
            int width = localMedia.getWidth();
            height = localMedia.getHeight();
            i2 = width;
        }
        return new int[]{i2, height};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExternalPreviewBack() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (this.config.isPreviewFullScreenMode) {
            hideFullScreenStatusBar();
        }
        onExitPictureSelector();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLoadData(ArrayList<LocalMedia> arrayList) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mData = arrayList;
        if (arrayList.size() == 0) {
            onBackOffFragment();
            return;
        }
        int i2 = this.isShowCamera ? 0 : -1;
        for (int i3 = 0; i3 < this.mData.size(); i3++) {
            i2++;
            this.mData.get(i3).setPosition(i2);
        }
        initViewPagerData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMoreData(List<LocalMedia> list, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.isHasMore = z2;
        if (z2) {
            if (list.size() <= 0) {
                loadMoreData();
                return;
            }
            int size = this.mData.size();
            this.mData.addAll(list);
            this.viewPageAdapter.notifyItemRangeChanged(size, this.mData.size());
        }
    }

    private void hideFullScreenStatusBar() {
        for (int i2 = 0; i2 < this.mAnimViews.size(); i2++) {
            this.mAnimViews.get(i2).setEnabled(true);
        }
        this.bottomNarBar.getEditor().setEnabled(true);
    }

    private void iniMagicalView() {
        if (this.isInternalBottomPreview) {
            this.magicalView.setBackgroundAlpha(1.0f);
            return;
        }
        if (!this.config.isPreviewZoomEffect) {
            this.magicalView.setBackgroundAlpha(1.0f);
            return;
        }
        int i2 = 0;
        if (this.isSaveInstanceState) {
            this.magicalView.setBackgroundAlpha(1.0f);
            while (i2 < this.mAnimViews.size()) {
                if (!(this.mAnimViews.get(i2) instanceof TitleBar)) {
                    this.mAnimViews.get(i2).setAlpha(1.0f);
                }
                i2++;
            }
        } else {
            this.magicalView.setBackgroundAlpha(0.0f);
            while (i2 < this.mAnimViews.size()) {
                if (!(this.mAnimViews.get(i2) instanceof TitleBar)) {
                    this.mAnimViews.get(i2).setAlpha(0.0f);
                }
                i2++;
            }
        }
        setMagicalViewAction();
    }

    private void initBottomNavBar() {
        this.bottomNarBar.setBottomNavBarStyle();
        this.bottomNarBar.setSelectedChange();
        this.bottomNarBar.setOnBottomNavBarListener(new BottomNavBar.OnBottomNavBarListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.17
            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onCheckOriginalChange() {
                PictureSelectorPreviewFragment.this.sendSelectedOriginalChangeEvent();
            }

            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onEditImage() {
                if (PictureSelectionConfig.onEditMediaEventListener != null) {
                    PictureSelectionConfig.onEditMediaEventListener.onStartMediaEdit(PictureSelectorPreviewFragment.this, (LocalMedia) PictureSelectorPreviewFragment.this.mData.get(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem()), 696);
                }
            }

            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onFirstCheckOriginalSelectedChange() {
                int currentItem = PictureSelectorPreviewFragment.this.viewPager.getCurrentItem();
                if (PictureSelectorPreviewFragment.this.mData.size() > currentItem) {
                    PictureSelectorPreviewFragment.this.confirmSelect((LocalMedia) PictureSelectorPreviewFragment.this.mData.get(currentItem), false);
                }
            }
        });
    }

    private void initComplete() {
        final SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        if (StyleUtils.checkStyleValidity(selectMainStyle.getPreviewSelectBackground())) {
            this.tvSelected.setBackgroundResource(selectMainStyle.getPreviewSelectBackground());
        } else if (StyleUtils.checkStyleValidity(selectMainStyle.getSelectBackground())) {
            this.tvSelected.setBackgroundResource(selectMainStyle.getSelectBackground());
        }
        if (StyleUtils.checkTextValidity(selectMainStyle.getPreviewSelectText())) {
            this.tvSelectedWord.setText(selectMainStyle.getPreviewSelectText());
        } else {
            this.tvSelectedWord.setText("");
        }
        if (StyleUtils.checkSizeValidity(selectMainStyle.getPreviewSelectTextSize())) {
            this.tvSelectedWord.setTextSize(selectMainStyle.getPreviewSelectTextSize());
        }
        if (StyleUtils.checkStyleValidity(selectMainStyle.getPreviewSelectTextColor())) {
            this.tvSelectedWord.setTextColor(selectMainStyle.getPreviewSelectTextColor());
        }
        if (StyleUtils.checkSizeValidity(selectMainStyle.getPreviewSelectMarginRight()) && (this.tvSelected.getLayoutParams() instanceof ConstraintLayout.LayoutParams)) {
            if (this.tvSelected.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.tvSelected.getLayoutParams())).rightMargin = selectMainStyle.getPreviewSelectMarginRight();
            } else if (this.tvSelected.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.tvSelected.getLayoutParams()).rightMargin = selectMainStyle.getPreviewSelectMarginRight();
            }
        }
        this.completeSelectView.setCompleteSelectViewStyle();
        if (selectMainStyle.isCompleteSelectRelativeTop()) {
            if (this.completeSelectView.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams();
                int i2 = R.id.title_bar;
                layoutParams.topToTop = i2;
                ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams()).bottomToBottom = i2;
                if (this.config.isPreviewFullScreenMode) {
                    ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams())).topMargin = DensityUtil.getStatusBarHeight(getContext());
                }
            } else if ((this.completeSelectView.getLayoutParams() instanceof RelativeLayout.LayoutParams) && this.config.isPreviewFullScreenMode) {
                ((RelativeLayout.LayoutParams) this.completeSelectView.getLayoutParams()).topMargin = DensityUtil.getStatusBarHeight(getContext());
            }
        }
        if (selectMainStyle.isPreviewSelectRelativeBottom()) {
            if (this.tvSelected.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.tvSelected.getLayoutParams();
                int i3 = R.id.bottom_nar_bar;
                layoutParams2.topToTop = i3;
                ((ConstraintLayout.LayoutParams) this.tvSelected.getLayoutParams()).bottomToBottom = i3;
                ((ConstraintLayout.LayoutParams) this.tvSelectedWord.getLayoutParams()).topToTop = i3;
                ((ConstraintLayout.LayoutParams) this.tvSelectedWord.getLayoutParams()).bottomToBottom = i3;
                ((ConstraintLayout.LayoutParams) this.selectClickArea.getLayoutParams()).topToTop = i3;
                ((ConstraintLayout.LayoutParams) this.selectClickArea.getLayoutParams()).bottomToBottom = i3;
            }
        } else if (this.config.isPreviewFullScreenMode) {
            if (this.tvSelectedWord.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.tvSelectedWord.getLayoutParams())).topMargin = DensityUtil.getStatusBarHeight(getContext());
            } else if (this.tvSelectedWord.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.tvSelectedWord.getLayoutParams()).topMargin = DensityUtil.getStatusBarHeight(getContext());
            }
        }
        this.completeSelectView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean z2 = true;
                if (selectMainStyle.isCompleteSelectRelativeTop() && SelectedManager.getCount() == 0) {
                    PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                    if (pictureSelectorPreviewFragment.confirmSelect((LocalMedia) pictureSelectorPreviewFragment.mData.get(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem()), false) != 0) {
                        z2 = false;
                    }
                }
                if (z2) {
                    PictureSelectorPreviewFragment.this.dispatchTransformResult();
                }
            }
        });
    }

    private void initPreviewSelectGallery(ViewGroup viewGroup) {
        SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        if (selectMainStyle.isPreviewDisplaySelectGallery()) {
            this.mGalleryRecycle = new RecyclerView(getContext());
            if (StyleUtils.checkStyleValidity(selectMainStyle.getAdapterPreviewGalleryBackgroundResource())) {
                this.mGalleryRecycle.setBackgroundResource(selectMainStyle.getAdapterPreviewGalleryBackgroundResource());
            } else {
                this.mGalleryRecycle.setBackgroundResource(R.drawable.ps_preview_gallery_bg);
            }
            viewGroup.addView(this.mGalleryRecycle);
            ViewGroup.LayoutParams layoutParams = this.mGalleryRecycle.getLayoutParams();
            if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
                ((ViewGroup.MarginLayoutParams) layoutParams2).width = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams2).height = -2;
                layoutParams2.bottomToTop = R.id.bottom_nar_bar;
                layoutParams2.startToStart = 0;
                layoutParams2.endToEnd = 0;
            }
            WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getContext()) { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.13
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i2) {
                    super.smoothScrollToPosition(recyclerView, state, i2);
                    LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.13.1
                        @Override // androidx.recyclerview.widget.LinearSmoothScroller
                        public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 300.0f / displayMetrics.densityDpi;
                        }
                    };
                    linearSmoothScroller.setTargetPosition(i2);
                    startSmoothScroll(linearSmoothScroller);
                }
            };
            RecyclerView.ItemAnimator itemAnimator = this.mGalleryRecycle.getItemAnimator();
            if (itemAnimator != null) {
                ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
            }
            if (this.mGalleryRecycle.getItemDecorationCount() == 0) {
                this.mGalleryRecycle.addItemDecoration(new HorizontalItemDecoration(Integer.MAX_VALUE, DensityUtil.dip2px(getContext(), 6.0f)));
            }
            wrapContentLinearLayoutManager.setOrientation(0);
            this.mGalleryRecycle.setLayoutManager(wrapContentLinearLayoutManager);
            this.mGalleryRecycle.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.ps_layout_animation_fall_down));
            this.mGalleryAdapter = new PreviewGalleryAdapter(this.isInternalBottomPreview, SelectedManager.getSelectedResult());
            notifyGallerySelectMedia(this.mData.get(this.curPosition));
            this.mGalleryRecycle.setAdapter(this.mGalleryAdapter);
            this.mGalleryAdapter.setItemClickListener(new PreviewGalleryAdapter.OnItemClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.14
                @Override // com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter.OnItemClickListener
                public void onItemClick(final int i2, LocalMedia localMedia, View view) {
                    if (PictureSelectorPreviewFragment.this.isInternalBottomPreview || TextUtils.equals(PictureSelectorPreviewFragment.this.currentAlbum, PictureSelectorPreviewFragment.this.getString(R.string.ps_camera_roll)) || TextUtils.equals(localMedia.getParentFolderName(), PictureSelectorPreviewFragment.this.currentAlbum)) {
                        if (!PictureSelectorPreviewFragment.this.isInternalBottomPreview) {
                            i2 = PictureSelectorPreviewFragment.this.isShowCamera ? localMedia.position - 1 : localMedia.position;
                        }
                        if (i2 == PictureSelectorPreviewFragment.this.viewPager.getCurrentItem() && localMedia.isChecked()) {
                            return;
                        }
                        if (PictureSelectorPreviewFragment.this.viewPager.getAdapter() != null) {
                            PictureSelectorPreviewFragment.this.viewPager.setAdapter(null);
                            PictureSelectorPreviewFragment.this.viewPager.setAdapter(PictureSelectorPreviewFragment.this.viewPageAdapter);
                        }
                        PictureSelectorPreviewFragment.this.viewPager.setCurrentItem(i2, false);
                        PictureSelectorPreviewFragment.this.notifyGallerySelectMedia(localMedia);
                        PictureSelectorPreviewFragment.this.viewPager.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.14.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                                    PictureSelectorPreviewFragment.this.viewPageAdapter.setVideoPlayButtonUI(i2);
                                }
                            }
                        });
                    }
                }
            });
            if (SelectedManager.getCount() > 0) {
                this.mGalleryRecycle.setVisibility(0);
            } else {
                this.mGalleryRecycle.setVisibility(4);
            }
            this.mAnimViews.add(this.mGalleryRecycle);
            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.15
                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    int lastCheckPosition;
                    viewHolder.itemView.setAlpha(1.0f);
                    if (PictureSelectorPreviewFragment.this.needScaleSmall) {
                        PictureSelectorPreviewFragment.this.needScaleSmall = false;
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.1f, 1.0f), ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.1f, 1.0f));
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.setDuration(50L);
                        animatorSet.start();
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.15.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                PictureSelectorPreviewFragment.this.needScaleBig = true;
                            }
                        });
                    }
                    super.clearView(recyclerView, viewHolder);
                    PictureSelectorPreviewFragment.this.mGalleryAdapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                    if (PictureSelectorPreviewFragment.this.isInternalBottomPreview && PictureSelectorPreviewFragment.this.viewPager.getCurrentItem() != (lastCheckPosition = PictureSelectorPreviewFragment.this.mGalleryAdapter.getLastCheckPosition()) && lastCheckPosition != -1) {
                        PictureSelectorPreviewFragment.this.viewPager.setCurrentItem(lastCheckPosition, false);
                    }
                    if (!PictureSelectionConfig.selectorStyle.getSelectMainStyle().isSelectNumberStyle() || ActivityCompatHelper.isDestroy(PictureSelectorPreviewFragment.this.getActivity())) {
                        return;
                    }
                    List<Fragment> fragments = PictureSelectorPreviewFragment.this.getActivity().getSupportFragmentManager().getFragments();
                    for (int i2 = 0; i2 < fragments.size(); i2++) {
                        Fragment fragment = fragments.get(i2);
                        if (fragment instanceof PictureCommonFragment) {
                            ((PictureCommonFragment) fragment).sendChangeSubSelectPositionEvent(true);
                        }
                    }
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public long getAnimationDuration(@NonNull RecyclerView recyclerView, int i2, float f2, float f3) {
                    return super.getAnimationDuration(recyclerView, i2, f2, f3);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    viewHolder.itemView.setAlpha(0.7f);
                    return ItemTouchHelper.Callback.makeMovementFlags(12, 0);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public boolean isLongPressDragEnabled() {
                    return true;
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float f2, float f3, int i2, boolean z2) {
                    if (PictureSelectorPreviewFragment.this.needScaleBig) {
                        PictureSelectorPreviewFragment.this.needScaleBig = false;
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.0f, 1.1f), ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.0f, 1.1f));
                        animatorSet.setDuration(50L);
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.start();
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.15.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                PictureSelectorPreviewFragment.this.needScaleSmall = true;
                            }
                        });
                    }
                    super.onChildDraw(canvas, recyclerView, viewHolder, f2, f3, i2, z2);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
                    try {
                        int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
                        int absoluteAdapterPosition2 = viewHolder2.getAbsoluteAdapterPosition();
                        if (absoluteAdapterPosition < absoluteAdapterPosition2) {
                            int i2 = absoluteAdapterPosition;
                            while (i2 < absoluteAdapterPosition2) {
                                int i3 = i2 + 1;
                                Collections.swap(PictureSelectorPreviewFragment.this.mGalleryAdapter.getData(), i2, i3);
                                Collections.swap(SelectedManager.getSelectedResult(), i2, i3);
                                if (PictureSelectorPreviewFragment.this.isInternalBottomPreview) {
                                    Collections.swap(PictureSelectorPreviewFragment.this.mData, i2, i3);
                                }
                                i2 = i3;
                            }
                        } else {
                            for (int i4 = absoluteAdapterPosition; i4 > absoluteAdapterPosition2; i4--) {
                                int i5 = i4 - 1;
                                Collections.swap(PictureSelectorPreviewFragment.this.mGalleryAdapter.getData(), i4, i5);
                                Collections.swap(SelectedManager.getSelectedResult(), i4, i5);
                                if (PictureSelectorPreviewFragment.this.isInternalBottomPreview) {
                                    Collections.swap(PictureSelectorPreviewFragment.this.mData, i4, i5);
                                }
                            }
                        }
                        PictureSelectorPreviewFragment.this.mGalleryAdapter.notifyItemMoved(absoluteAdapterPosition, absoluteAdapterPosition2);
                        return true;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return true;
                    }
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i2) {
                    super.onSelectedChanged(viewHolder, i2);
                }

                @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i2) {
                }
            });
            itemTouchHelper.attachToRecyclerView(this.mGalleryRecycle);
            this.mGalleryAdapter.setItemLongClickListener(new PreviewGalleryAdapter.OnItemLongClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.16
                @Override // com.luck.picture.lib.adapter.holder.PreviewGalleryAdapter.OnItemLongClickListener
                public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i2, View view) {
                    ((Vibrator) PictureSelectorPreviewFragment.this.getActivity().getSystemService("vibrator")).vibrate(50L);
                    if (PictureSelectorPreviewFragment.this.mGalleryAdapter.getItemCount() != ((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.maxSelectNum) {
                        itemTouchHelper.startDrag(viewHolder);
                    } else if (viewHolder.getLayoutPosition() != PictureSelectorPreviewFragment.this.mGalleryAdapter.getItemCount() - 1) {
                        itemTouchHelper.startDrag(viewHolder);
                    }
                }
            });
        }
    }

    private void initTitleBar() {
        if (PictureSelectionConfig.selectorStyle.getTitleBarStyle().isHideTitleBar()) {
            this.titleBar.setVisibility(8);
        }
        this.titleBar.setTitleBarStyle();
        this.titleBar.setOnTitleBarListener(new TitleBar.OnTitleBarListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.9
            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onBackPressed() {
                if (PictureSelectorPreviewFragment.this.isExternalPreview) {
                    PictureSelectorPreviewFragment.this.handleExternalPreviewBack();
                } else if (PictureSelectorPreviewFragment.this.isInternalBottomPreview || !((PictureCommonFragment) PictureSelectorPreviewFragment.this).config.isPreviewZoomEffect) {
                    PictureSelectorPreviewFragment.this.onBackOffFragment();
                } else {
                    PictureSelectorPreviewFragment.this.magicalView.backToMin();
                }
            }
        });
        this.titleBar.setTitle((this.curPosition + 1) + "/" + this.totalNum);
        this.titleBar.getImageDelete().setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureSelectorPreviewFragment.this.deletePreview();
            }
        });
        this.selectClickArea.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PictureSelectorPreviewFragment.this.isExternalPreview) {
                    PictureSelectorPreviewFragment.this.deletePreview();
                    return;
                }
                LocalMedia localMedia = (LocalMedia) PictureSelectorPreviewFragment.this.mData.get(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem());
                PictureSelectorPreviewFragment pictureSelectorPreviewFragment = PictureSelectorPreviewFragment.this;
                if (pictureSelectorPreviewFragment.confirmSelect(localMedia, pictureSelectorPreviewFragment.tvSelected.isSelected()) == 0) {
                    PictureSelectorPreviewFragment.this.tvSelected.startAnimation(AnimationUtils.loadAnimation(PictureSelectorPreviewFragment.this.getContext(), R.anim.ps_anim_modal_in));
                }
            }
        });
        this.tvSelected.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureSelectorPreviewFragment.this.selectClickArea.performClick();
            }
        });
    }

    private void initViewPagerData() {
        this.viewPageAdapter = new PicturePreviewAdapter(this.mData, new MyOnPreviewEventListener());
        this.viewPager.setOrientation(0);
        this.viewPager.setAdapter(this.viewPageAdapter);
        this.viewPager.setCurrentItem(this.curPosition, false);
        if (this.mData.size() > 0) {
            this.bottomNarBar.isDisplayEditor(PictureMimeType.isHasVideo(this.mData.get(this.curPosition).getMimeType()));
        }
        this.tvSelected.setSelected(SelectedManager.getSelectedResult().contains(this.mData.get(this.viewPager.getCurrentItem())));
        this.completeSelectView.setSelectedChange(true);
        this.viewPager.registerOnPageChangeCallback(this.pageChangeCallback);
        this.viewPager.setPageTransformer(new MarginPageTransformer(DensityUtil.dip2px(getContext(), 3.0f)));
        sendChangeSubSelectPositionEvent(false);
        notifySelectNumberStyle(this.mData.get(this.curPosition));
    }

    private void loadData(int i2) {
        if (this.config.isOnlySandboxDir) {
            ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
            if (extendLoaderEngine != null) {
                extendLoaderEngine.loadOnlyInAppDirAllMediaData(getContext(), new OnQueryAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.2
                    @Override // com.luck.picture.lib.interfaces.OnQueryAlbumListener
                    public void onComplete(LocalMediaFolder localMediaFolder) {
                        PictureSelectorPreviewFragment.this.handleLoadData(localMediaFolder.getData());
                    }
                });
                return;
            } else {
                this.mLoader.loadOnlyInAppDirAllMedia(new OnQueryAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.3
                    @Override // com.luck.picture.lib.interfaces.OnQueryAlbumListener
                    public void onComplete(LocalMediaFolder localMediaFolder) {
                        PictureSelectorPreviewFragment.this.handleLoadData(localMediaFolder.getData());
                    }
                });
                return;
            }
        }
        ExtendLoaderEngine extendLoaderEngine2 = PictureSelectionConfig.loaderDataEngine;
        if (extendLoaderEngine2 != null) {
            extendLoaderEngine2.loadFirstPageMediaData(getContext(), this.mBucketId, 1, i2, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.4
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorPreviewFragment.this.handleLoadData(arrayList);
                }
            });
        } else {
            this.mLoader.loadFirstPageMedia(this.mBucketId, i2, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.5
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorPreviewFragment.this.handleLoadData(arrayList);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMoreData() {
        int i2 = this.mPage + 1;
        this.mPage = i2;
        ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
        if (extendLoaderEngine == null) {
            this.mLoader.loadPageMediaData(this.mBucketId, i2, this.config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.7
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorPreviewFragment.this.handleMoreData(arrayList, z2);
                }
            });
            return;
        }
        Context context = getContext();
        long j2 = this.mBucketId;
        int i3 = this.mPage;
        int i4 = this.config.pageSize;
        extendLoaderEngine.loadMoreMediaData(context, j2, i3, i4, i4, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.6
            @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
            public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                PictureSelectorPreviewFragment.this.handleMoreData(arrayList, z2);
            }
        });
    }

    public static PictureSelectorPreviewFragment newInstance() {
        PictureSelectorPreviewFragment pictureSelectorPreviewFragment = new PictureSelectorPreviewFragment();
        pictureSelectorPreviewFragment.setArguments(new Bundle());
        return pictureSelectorPreviewFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyGallerySelectMedia(LocalMedia localMedia) {
        if (this.mGalleryAdapter == null || !PictureSelectionConfig.selectorStyle.getSelectMainStyle().isPreviewDisplaySelectGallery()) {
            return;
        }
        this.mGalleryAdapter.isSelectMedia(localMedia);
    }

    private void notifyPreviewGalleryData(boolean z2, LocalMedia localMedia) {
        if (this.mGalleryAdapter == null || !PictureSelectionConfig.selectorStyle.getSelectMainStyle().isPreviewDisplaySelectGallery()) {
            return;
        }
        if (this.mGalleryRecycle.getVisibility() == 4) {
            this.mGalleryRecycle.setVisibility(0);
        }
        if (z2) {
            if (this.config.selectionMode == 1) {
                this.mGalleryAdapter.clear();
            }
            this.mGalleryAdapter.addGalleryData(localMedia);
            this.mGalleryRecycle.smoothScrollToPosition(this.mGalleryAdapter.getItemCount() - 1);
            return;
        }
        this.mGalleryAdapter.removeGalleryData(localMedia);
        if (SelectedManager.getCount() == 0) {
            this.mGalleryRecycle.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExternalLongPressDownload(final LocalMedia localMedia) {
        OnExternalPreviewEventListener onExternalPreviewEventListener = PictureSelectionConfig.onExternalPreviewEventListener;
        if (onExternalPreviewEventListener == null || onExternalPreviewEventListener.onLongPressDownload(localMedia)) {
            return;
        }
        PictureCommonDialog.showDialog(getContext(), getContext().getString(R.string.ps_prompt), (PictureMimeType.isHasVideo(localMedia.getMimeType()) || PictureMimeType.isUrlHasVideo(localMedia.getAvailablePath())) ? getContext().getString(R.string.ps_prompt_video_content) : getContext().getString(R.string.ps_prompt_content)).setOnDialogEventListener(new PictureCommonDialog.OnDialogEventListener() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.19
            @Override // com.luck.picture.lib.dialog.PictureCommonDialog.OnDialogEventListener
            public void onConfirm() {
                String path = TextUtils.isEmpty(localMedia.getSandboxPath()) ? localMedia.getPath() : localMedia.getSandboxPath();
                if (PictureMimeType.isHasHttp(path)) {
                    PictureSelectorPreviewFragment.this.showLoading();
                }
                DownloadFileUtils.saveLocalFile(PictureSelectorPreviewFragment.this.getContext(), path, localMedia.getMimeType(), new OnCallbackListener<String>() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.19.1
                    @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                    public void onCall(String str) {
                        PictureSelectorPreviewFragment.this.dismissLoading();
                        if (TextUtils.isEmpty(str)) {
                            ToastUtils.showToast(PictureSelectorPreviewFragment.this.getContext(), PictureMimeType.isHasVideo(localMedia.getMimeType()) ? PictureSelectorPreviewFragment.this.getString(R.string.ps_save_video_error) : PictureSelectorPreviewFragment.this.getString(R.string.ps_save_image_error));
                            return;
                        }
                        new PictureMediaScannerConnection(PictureSelectorPreviewFragment.this.getActivity(), str);
                        ToastUtils.showToast(PictureSelectorPreviewFragment.this.getContext(), PictureSelectorPreviewFragment.this.getString(R.string.ps_save_success) + "\n" + str);
                    }
                });
            }
        });
    }

    private void onKeyDownBackToMin() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (this.isExternalPreview) {
            onExitPictureSelector();
            return;
        }
        if (this.isInternalBottomPreview) {
            onBackOffFragment();
        } else if (this.config.isPreviewZoomEffect) {
            this.magicalView.backToMin();
        } else {
            onBackOffFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void previewFullScreenMode() {
        if (this.isAnimationStart) {
            return;
        }
        boolean z2 = this.titleBar.getTranslationY() == 0.0f;
        AnimatorSet animatorSet = new AnimatorSet();
        float f2 = z2 ? 0.0f : -this.titleBar.getHeight();
        float f3 = z2 ? -this.titleBar.getHeight() : 0.0f;
        float f4 = z2 ? 1.0f : 0.0f;
        float f5 = z2 ? 0.0f : 1.0f;
        for (int i2 = 0; i2 < this.mAnimViews.size(); i2++) {
            View view = this.mAnimViews.get(i2);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", f4, f5));
            if (view instanceof TitleBar) {
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationY", f2, f3));
            }
        }
        animatorSet.setDuration(350L);
        animatorSet.start();
        this.isAnimationStart = true;
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.18
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PictureSelectorPreviewFragment.this.isAnimationStart = false;
            }
        });
        if (z2) {
            showFullScreenStatusBar();
        } else {
            hideFullScreenStatusBar();
        }
    }

    private void setMagicalViewAction() {
        this.magicalView.setOnMojitoViewCallback(new OnMagicalViewCallback() { // from class: com.luck.picture.lib.PictureSelectorPreviewFragment.1
            @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
            public void onBackgroundAlpha(float f2) {
                for (int i2 = 0; i2 < PictureSelectorPreviewFragment.this.mAnimViews.size(); i2++) {
                    if (!(PictureSelectorPreviewFragment.this.mAnimViews.get(i2) instanceof TitleBar)) {
                        ((View) PictureSelectorPreviewFragment.this.mAnimViews.get(i2)).setAlpha(f2);
                    }
                }
            }

            @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
            public void onBeginBackMinAnim() {
                BasePreviewHolder currentHolder = PictureSelectorPreviewFragment.this.viewPageAdapter.getCurrentHolder(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem());
                if (currentHolder == null) {
                    return;
                }
                if (currentHolder.coverImageView.getVisibility() == 8) {
                    currentHolder.coverImageView.setVisibility(0);
                }
                if (currentHolder instanceof PreviewVideoHolder) {
                    PreviewVideoHolder previewVideoHolder = (PreviewVideoHolder) currentHolder;
                    if (previewVideoHolder.ivPlayButton.getVisibility() == 0) {
                        previewVideoHolder.ivPlayButton.setVisibility(8);
                    }
                }
            }

            @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
            public void onBeginBackMinMagicalFinish(boolean z2) {
                BasePreviewHolder currentHolder;
                ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(PictureSelectorPreviewFragment.this.isShowCamera ? PictureSelectorPreviewFragment.this.curPosition + 1 : PictureSelectorPreviewFragment.this.curPosition);
                if (itemViewParams == null || (currentHolder = PictureSelectorPreviewFragment.this.viewPageAdapter.getCurrentHolder(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem())) == null) {
                    return;
                }
                currentHolder.coverImageView.getLayoutParams().width = itemViewParams.width;
                currentHolder.coverImageView.getLayoutParams().height = itemViewParams.height;
                currentHolder.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
            public void onBeginMagicalAnimComplete(MagicalView magicalView, boolean z2) {
                BasePreviewHolder currentHolder = PictureSelectorPreviewFragment.this.viewPageAdapter.getCurrentHolder(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem());
                if (currentHolder == null) {
                    return;
                }
                LocalMedia localMedia = (LocalMedia) PictureSelectorPreviewFragment.this.mData.get(PictureSelectorPreviewFragment.this.viewPager.getCurrentItem());
                if (MediaUtils.isLongImage(localMedia.getWidth(), localMedia.getHeight())) {
                    currentHolder.coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    currentHolder.coverImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                if (currentHolder instanceof PreviewVideoHolder) {
                    PreviewVideoHolder previewVideoHolder = (PreviewVideoHolder) currentHolder;
                    if (previewVideoHolder.ivPlayButton.getVisibility() == 8) {
                        previewVideoHolder.ivPlayButton.setVisibility(0);
                    }
                }
            }

            @Override // com.luck.picture.lib.magical.OnMagicalViewCallback
            public void onMagicalViewFinish() {
                PictureSelectorPreviewFragment.this.onBackOffFragment();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMagicalViewViewParams(int i2, int i3, int i4) {
        this.magicalView.changeRealScreenHeight(i2, i3, true);
        if (this.isShowCamera) {
            i4++;
        }
        ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(i4);
        if (itemViewParams == null || i2 == 0 || i3 == 0) {
            this.magicalView.setViewParams(0, 0, 0, 0, i2, i3);
        } else {
            this.magicalView.setViewParams(itemViewParams.left, itemViewParams.f8889top, itemViewParams.width, itemViewParams.height, i2, i3);
        }
    }

    private void showFullScreenStatusBar() {
        for (int i2 = 0; i2 < this.mAnimViews.size(); i2++) {
            this.mAnimViews.get(i2).setEnabled(false);
        }
        this.bottomNarBar.getEditor().setEnabled(false);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        int layoutResource = InjectResourceSource.getLayoutResource(getContext(), 2);
        return layoutResource != 0 ? layoutResource : R.layout.ps_fragment_preview;
    }

    public void initLoader() {
        if (this.config.isPageStrategy) {
            this.mLoader = new LocalMediaPageLoader(getContext(), this.config);
        } else {
            this.mLoader = new LocalMediaLoader(getContext(), this.config);
        }
    }

    public boolean isSelected(LocalMedia localMedia) {
        return SelectedManager.getSelectedResult().contains(localMedia);
    }

    public void notifySelectNumberStyle(LocalMedia localMedia) {
        if (PictureSelectionConfig.selectorStyle.getSelectMainStyle().isPreviewSelectNumberStyle() && PictureSelectionConfig.selectorStyle.getSelectMainStyle().isSelectNumberStyle()) {
            this.tvSelected.setText("");
            for (int i2 = 0; i2 < SelectedManager.getCount(); i2++) {
                LocalMedia localMedia2 = SelectedManager.getSelectedResult().get(i2);
                if (TextUtils.equals(localMedia2.getPath(), localMedia.getPath()) || localMedia2.getId() == localMedia.getId()) {
                    localMedia.setNum(localMedia2.getNum());
                    localMedia2.setPosition(localMedia.getPosition());
                    this.tvSelected.setText(ValueOf.toString(Integer.valueOf(localMedia.getNum())));
                }
            }
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCheckOriginalChange() {
        this.bottomNarBar.setOriginalCheck();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        int i2;
        int i3;
        super.onConfigurationChanged(configuration);
        if (this.isInternalBottomPreview || this.isExternalPreview || !this.config.isPreviewZoomEffect) {
            return;
        }
        int size = this.mData.size();
        int i4 = this.curPosition;
        if (size > i4) {
            int[] realSizeFromMedia = getRealSizeFromMedia(this.mData.get(i4));
            ViewParams itemViewParams = BuildRecycleItemViewParams.getItemViewParams(this.isShowCamera ? this.curPosition + 1 : this.curPosition);
            if (itemViewParams == null || (i2 = realSizeFromMedia[0]) == 0 || (i3 = realSizeFromMedia[1]) == 0) {
                this.magicalView.setViewParams(0, 0, 0, 0, realSizeFromMedia[0], realSizeFromMedia[1]);
                this.magicalView.resetStartNormal(realSizeFromMedia[0], realSizeFromMedia[1], false);
            } else {
                this.magicalView.setViewParams(itemViewParams.left, itemViewParams.f8889top, itemViewParams.width, itemViewParams.height, i2, i3);
                this.magicalView.resetStart();
            }
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    @Nullable
    public Animation onCreateAnimation(int i2, boolean z2, int i3) throws Resources.NotFoundException {
        if (!this.isInternalBottomPreview && !this.isExternalPreview && this.config.isPreviewZoomEffect) {
            return null;
        }
        PictureWindowAnimationStyle windowAnimationStyle = PictureSelectionConfig.selectorStyle.getWindowAnimationStyle();
        if (windowAnimationStyle.activityPreviewEnterAnimation == 0 || windowAnimationStyle.activityPreviewExitAnimation == 0) {
            return super.onCreateAnimation(i2, z2, i3);
        }
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getActivity(), z2 ? windowAnimationStyle.activityPreviewEnterAnimation : windowAnimationStyle.activityPreviewExitAnimation);
        if (z2) {
            onEnterFragment();
        } else {
            onExitFragment();
        }
        return animationLoadAnimation;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        this.viewPageAdapter.destroyVideo(this.viewPager.getCurrentItem());
        this.viewPager.unregisterOnPageChangeCallback(this.pageChangeCallback);
        if (this.isExternalPreview) {
            PictureSelectionConfig.destroy();
        }
        super.onDestroy();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onEditMedia(Intent intent) {
        if (this.mData.size() > this.viewPager.getCurrentItem()) {
            LocalMedia localMedia = this.mData.get(this.viewPager.getCurrentItem());
            Uri output = Crop.getOutput(intent);
            localMedia.setCutPath(output != null ? output.getPath() : "");
            localMedia.setCropImageWidth(Crop.getOutputImageWidth(intent));
            localMedia.setCropImageHeight(Crop.getOutputImageHeight(intent));
            localMedia.setCropOffsetX(Crop.getOutputImageOffsetX(intent));
            localMedia.setCropOffsetY(Crop.getOutputImageOffsetY(intent));
            localMedia.setCropResultAspectRatio(Crop.getOutputCropAspectRatio(intent));
            localMedia.setCut(!TextUtils.isEmpty(localMedia.getCutPath()));
            localMedia.setCustomData(Crop.getOutputCustomExtraData(intent));
            localMedia.setEditorImage(localMedia.isCut());
            localMedia.setSandboxPath(localMedia.getCutPath());
            if (SelectedManager.getSelectedResult().contains(localMedia)) {
                sendFixedSelectedChangeEvent(localMedia);
            } else {
                confirmSelect(localMedia, false);
            }
            this.viewPageAdapter.notifyItemChanged(this.viewPager.getCurrentItem());
            notifyGallerySelectMedia(localMedia);
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onExitFragment() {
        if (this.config.isPreviewFullScreenMode) {
            hideFullScreenStatusBar();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onKeyBackFragment() {
        onKeyDownBackToMin();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(PictureConfig.EXTRA_CURRENT_PAGE, this.mPage);
        bundle.putLong(PictureConfig.EXTRA_CURRENT_BUCKET_ID, this.mBucketId);
        bundle.putInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.curPosition);
        bundle.putInt(PictureConfig.EXTRA_PREVIEW_CURRENT_ALBUM_TOTAL, this.totalNum);
        bundle.putBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW, this.isExternalPreview);
        bundle.putBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, this.isDisplayDelete);
        bundle.putBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.isShowCamera);
        bundle.putBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, this.isInternalBottomPreview);
        bundle.putString(PictureConfig.EXTRA_CURRENT_ALBUM_NAME, this.currentAlbum);
        if (this.isExternalPreview) {
            SelectedManager.addSelectedPreviewResult(this.mData);
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onSelectedChange(boolean z2, LocalMedia localMedia) {
        this.tvSelected.setSelected(SelectedManager.getSelectedResult().contains(localMedia));
        this.bottomNarBar.setSelectedChange();
        this.completeSelectView.setSelectedChange(true);
        notifySelectNumberStyle(localMedia);
        notifyPreviewGalleryData(z2, localMedia);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        reStartSavedInstance(bundle);
        this.isSaveInstanceState = bundle != null;
        this.screenWidth = DensityUtil.getRealScreenWidth(getContext());
        this.screenHeight = DensityUtil.getScreenHeight(getContext());
        this.titleBar = (PreviewTitleBar) view.findViewById(R.id.title_bar);
        this.tvSelected = (TextView) view.findViewById(R.id.ps_tv_selected);
        this.tvSelectedWord = (TextView) view.findViewById(R.id.ps_tv_selected_word);
        this.selectClickArea = view.findViewById(R.id.select_click_area);
        this.completeSelectView = (CompleteSelectView) view.findViewById(R.id.ps_complete_select);
        this.magicalView = (MagicalView) view.findViewById(R.id.magical);
        this.viewPager = new ViewPager2(getContext());
        this.bottomNarBar = (PreviewBottomNavBar) view.findViewById(R.id.bottom_nar_bar);
        this.magicalView.setMagicalContent(this.viewPager);
        ArrayList arrayList = new ArrayList();
        this.mAnimViews = arrayList;
        arrayList.add(this.titleBar);
        this.mAnimViews.add(this.tvSelected);
        this.mAnimViews.add(this.tvSelectedWord);
        this.mAnimViews.add(this.selectClickArea);
        this.mAnimViews.add(this.completeSelectView);
        this.mAnimViews.add(this.bottomNarBar);
        initTitleBar();
        if (this.isExternalPreview) {
            if (bundle != null || this.mData.size() == 0) {
                this.mData = new ArrayList<>(SelectedManager.getSelectedPreviewResult());
            }
            this.magicalView.setBackgroundAlpha(1.0f);
            SelectedManager.clearExternalPreviewData();
            externalPreviewStyle();
            initViewPagerData();
            return;
        }
        initLoader();
        initBottomNavBar();
        initPreviewSelectGallery((ViewGroup) view);
        initComplete();
        iniMagicalView();
        if (bundle == null || this.mData.size() != 0) {
            initViewPagerData();
            return;
        }
        if (this.isInternalBottomPreview) {
            this.mData = new ArrayList<>(SelectedManager.getSelectedResult());
            initViewPagerData();
            return;
        }
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig.isPageStrategy) {
            loadData(this.mPage * pictureSelectionConfig.pageSize);
        } else {
            this.mLoader = new LocalMediaPageLoader(getContext(), this.config);
            loadData(this.totalNum);
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void reStartSavedInstance(Bundle bundle) {
        super.reStartSavedInstance(bundle);
        if (bundle != null) {
            this.mPage = bundle.getInt(PictureConfig.EXTRA_CURRENT_PAGE, 1);
            this.mBucketId = bundle.getLong(PictureConfig.EXTRA_CURRENT_BUCKET_ID, -1L);
            this.curPosition = bundle.getInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.curPosition);
            this.isShowCamera = bundle.getBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.isShowCamera);
            this.totalNum = bundle.getInt(PictureConfig.EXTRA_PREVIEW_CURRENT_ALBUM_TOTAL, this.totalNum);
            this.isExternalPreview = bundle.getBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW, this.isExternalPreview);
            this.isDisplayDelete = bundle.getBoolean(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, this.isDisplayDelete);
            this.isInternalBottomPreview = bundle.getBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, this.isInternalBottomPreview);
            this.currentAlbum = bundle.getString(PictureConfig.EXTRA_CURRENT_ALBUM_NAME, "");
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendChangeSubSelectPositionEvent(boolean z2) {
        if (PictureSelectionConfig.selectorStyle.getSelectMainStyle().isPreviewSelectNumberStyle() && PictureSelectionConfig.selectorStyle.getSelectMainStyle().isSelectNumberStyle()) {
            int i2 = 0;
            while (i2 < SelectedManager.getCount()) {
                LocalMedia localMedia = SelectedManager.getSelectedResult().get(i2);
                i2++;
                localMedia.setNum(i2);
            }
        }
    }

    public void setExternalPreviewData(int i2, int i3, ArrayList<LocalMedia> arrayList, boolean z2) {
        this.mData = arrayList;
        this.totalNum = i3;
        this.curPosition = i2;
        this.isDisplayDelete = z2;
        this.isExternalPreview = true;
        PictureSelectionConfig.getInstance().isPreviewZoomEffect = false;
    }

    public void setInternalPreviewData(boolean z2, String str, boolean z3, int i2, int i3, int i4, long j2, ArrayList<LocalMedia> arrayList) {
        this.mPage = i4;
        this.mBucketId = j2;
        this.mData = arrayList;
        this.totalNum = i3;
        this.curPosition = i2;
        this.currentAlbum = str;
        this.isShowCamera = z3;
        this.isInternalBottomPreview = z2;
    }
}
