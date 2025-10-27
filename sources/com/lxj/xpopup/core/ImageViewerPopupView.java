package com.lxj.xpopup.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.R;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.OnDragChangeListener;
import com.lxj.xpopup.interfaces.OnImageViewerLongPressListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.photoview.PhotoView;
import com.lxj.xpopup.util.XPermission;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BlankView;
import com.lxj.xpopup.widget.HackyViewPager;
import com.lxj.xpopup.widget.PhotoViewContainer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ImageViewerPopupView extends BasePopupView implements OnDragChangeListener, View.OnClickListener {
    protected ArgbEvaluator argbEvaluator;
    protected int bgColor;
    protected FrameLayout container;
    protected View customView;
    protected XPopupImageLoader imageLoader;
    protected boolean isInfinite;
    protected boolean isShowIndicator;
    protected boolean isShowPlaceholder;
    protected boolean isShowSaveBtn;
    public OnImageViewerLongPressListener longPressListener;
    protected HackyViewPager pager;
    protected PhotoViewContainer photoViewContainer;
    protected int placeholderColor;
    protected int placeholderRadius;
    protected int placeholderStrokeColor;
    protected BlankView placeholderView;
    protected int position;
    protected Rect rect;
    protected PhotoView snapshotView;
    protected ImageView srcView;
    protected OnSrcViewUpdateListener srcViewUpdateListener;
    protected TextView tv_pager_indicator;
    protected TextView tv_save;
    protected List<Object> urls;

    public class PhotoViewAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        public PhotoViewAdapter() {
        }

        private FrameLayout buildContainer(Context context) {
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            return frameLayout;
        }

        private ProgressBar buildProgressBar(Context context) {
            ProgressBar progressBar = new ProgressBar(context);
            progressBar.setIndeterminate(true);
            int iDp2px = XPopupUtils.dp2px(ImageViewerPopupView.this.container.getContext(), 40.0f);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iDp2px, iDp2px);
            layoutParams.gravity = 17;
            progressBar.setLayoutParams(layoutParams);
            progressBar.setVisibility(8);
            return progressBar;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(@NonNull ViewGroup viewGroup, int i2, @NonNull Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            if (imageViewerPopupView.isInfinite) {
                return 100000;
            }
            return imageViewerPopupView.urls.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int i2) {
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            if (imageViewerPopupView.isInfinite) {
                i2 %= imageViewerPopupView.urls.size();
            }
            int i3 = i2;
            FrameLayout frameLayoutBuildContainer = buildContainer(viewGroup.getContext());
            ProgressBar progressBarBuildProgressBar = buildProgressBar(viewGroup.getContext());
            ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
            XPopupImageLoader xPopupImageLoader = imageViewerPopupView2.imageLoader;
            Object obj = imageViewerPopupView2.urls.get(i3);
            ImageViewerPopupView imageViewerPopupView3 = ImageViewerPopupView.this;
            frameLayoutBuildContainer.addView(xPopupImageLoader.loadImage(i3, obj, imageViewerPopupView3, imageViewerPopupView3.snapshotView, progressBarBuildProgressBar), new FrameLayout.LayoutParams(-1, -1));
            frameLayoutBuildContainer.addView(progressBarBuildProgressBar);
            viewGroup.addView(frameLayoutBuildContainer);
            return frameLayoutBuildContainer;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return obj == view;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i2, float f2, int i3) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i2) {
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            imageViewerPopupView.position = i2;
            imageViewerPopupView.showPagerIndicator();
            ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
            OnSrcViewUpdateListener onSrcViewUpdateListener = imageViewerPopupView2.srcViewUpdateListener;
            if (onSrcViewUpdateListener != null) {
                onSrcViewUpdateListener.onSrcViewUpdate(imageViewerPopupView2, imageViewerPopupView2.getRealPosition());
            }
        }
    }

    public ImageViewerPopupView(@NonNull Context context) {
        super(context);
        this.argbEvaluator = new ArgbEvaluator();
        this.urls = new ArrayList();
        this.rect = null;
        this.isShowPlaceholder = true;
        this.placeholderColor = Color.parseColor("#f1f1f1");
        this.placeholderStrokeColor = -1;
        this.placeholderRadius = -1;
        this.isShowSaveBtn = true;
        this.isShowIndicator = true;
        this.isInfinite = false;
        this.bgColor = Color.rgb(32, 36, 46);
        this.container = (FrameLayout) findViewById(R.id.container);
        if (getImplLayoutId() > 0) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.container, false);
            this.customView = viewInflate;
            viewInflate.setVisibility(4);
            this.customView.setAlpha(0.0f);
            this.container.addView(this.customView);
        }
    }

    private void addOrUpdateSnapshot() {
        if (this.srcView == null) {
            return;
        }
        if (this.snapshotView == null) {
            PhotoView photoView = new PhotoView(getContext());
            this.snapshotView = photoView;
            photoView.setEnabled(false);
            this.photoViewContainer.addView(this.snapshotView);
            this.snapshotView.setScaleType(this.srcView.getScaleType());
            this.snapshotView.setTranslationX(this.rect.left);
            this.snapshotView.setTranslationY(this.rect.top);
            XPopupUtils.setWidthHeight(this.snapshotView, this.rect.width(), this.rect.height());
        }
        int realPosition = getRealPosition();
        this.snapshotView.setTag(Integer.valueOf(realPosition));
        ImageView imageView = this.srcView;
        if (imageView != null && imageView.getDrawable() != null) {
            try {
                this.snapshotView.setImageDrawable(this.srcView.getDrawable().getConstantState().newDrawable());
            } catch (Exception unused) {
            }
        }
        setupPlaceholder();
        XPopupImageLoader xPopupImageLoader = this.imageLoader;
        if (xPopupImageLoader != null) {
            xPopupImageLoader.loadSnapshot(this.urls.get(realPosition), this.snapshotView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateShadowBg(final int i2) {
        final int color = ((ColorDrawable) this.photoViewContainer.getBackground()).getColor();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                imageViewerPopupView.photoViewContainer.setBackgroundColor(((Integer) imageViewerPopupView.argbEvaluator.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(color), Integer.valueOf(i2))).intValue());
            }
        });
        valueAnimatorOfFloat.setDuration(getAnimationDuration()).setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.start();
    }

    private void setupPlaceholder() {
        this.placeholderView.setVisibility(this.isShowPlaceholder ? 0 : 4);
        if (this.isShowPlaceholder) {
            int i2 = this.placeholderColor;
            if (i2 != -1) {
                this.placeholderView.color = i2;
            }
            int i3 = this.placeholderRadius;
            if (i3 != -1) {
                this.placeholderView.radius = i3;
            }
            int i4 = this.placeholderStrokeColor;
            if (i4 != -1) {
                this.placeholderView.strokeColor = i4;
            }
            XPopupUtils.setWidthHeight(this.placeholderView, this.rect.width(), this.rect.height());
            this.placeholderView.setTranslationX(this.rect.left);
            this.placeholderView.setTranslationY(this.rect.top);
            this.placeholderView.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPagerIndicator() {
        if (this.urls.size() > 1) {
            int realPosition = getRealPosition();
            this.tv_pager_indicator.setText((realPosition + 1) + "/" + this.urls.size());
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setVisibility(0);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void destroy() {
        super.destroy();
        HackyViewPager hackyViewPager = this.pager;
        hackyViewPager.removeOnPageChangeListener((PhotoViewAdapter) hackyViewPager.getAdapter());
        this.imageLoader = null;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        if (this.popupStatus != PopupStatus.Show) {
            return;
        }
        this.popupStatus = PopupStatus.Dismissing;
        doDismissAnimation();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
        if (this.srcView == null) {
            this.photoViewContainer.setBackgroundColor(0);
            doAfterDismiss();
            this.pager.setVisibility(4);
            this.placeholderView.setVisibility(4);
            return;
        }
        this.tv_pager_indicator.setVisibility(4);
        this.tv_save.setVisibility(4);
        this.pager.setVisibility(4);
        this.photoViewContainer.isReleasing = true;
        this.snapshotView.setVisibility(0);
        doAfterDismiss();
        this.snapshotView.post(new Runnable() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupView.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupView.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(@NonNull Transition transition) {
                        ImageViewerPopupView.this.pager.setVisibility(4);
                        ImageViewerPopupView.this.snapshotView.setVisibility(0);
                        ImageViewerPopupView.this.pager.setScaleX(1.0f);
                        ImageViewerPopupView.this.pager.setScaleY(1.0f);
                        ImageViewerPopupView.this.snapshotView.setScaleX(1.0f);
                        ImageViewerPopupView.this.snapshotView.setScaleY(1.0f);
                        ImageViewerPopupView.this.placeholderView.setVisibility(4);
                    }
                }));
                ImageViewerPopupView.this.snapshotView.setScaleX(1.0f);
                ImageViewerPopupView.this.snapshotView.setScaleY(1.0f);
                ImageViewerPopupView.this.snapshotView.setTranslationY(r0.rect.top);
                ImageViewerPopupView.this.snapshotView.setTranslationX(r0.rect.left);
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                imageViewerPopupView.snapshotView.setScaleType(imageViewerPopupView.srcView.getScaleType());
                ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
                XPopupUtils.setWidthHeight(imageViewerPopupView2.snapshotView, imageViewerPopupView2.rect.width(), ImageViewerPopupView.this.rect.height());
                ImageViewerPopupView.this.animateShadowBg(0);
                View view = ImageViewerPopupView.this.customView;
                if (view != null) {
                    view.animate().alpha(0.0f).setDuration(ImageViewerPopupView.this.getAnimationDuration()).setListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            View view2 = ImageViewerPopupView.this.customView;
                            if (view2 != null) {
                                view2.setVisibility(4);
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        if (this.srcView == null) {
            this.photoViewContainer.setBackgroundColor(this.bgColor);
            this.pager.setVisibility(0);
            showPagerIndicator();
            this.photoViewContainer.isReleasing = false;
            doAfterShow();
            return;
        }
        this.photoViewContainer.isReleasing = true;
        View view = this.customView;
        if (view != null) {
            view.setVisibility(0);
        }
        this.snapshotView.setVisibility(0);
        doAfterShow();
        this.snapshotView.post(new Runnable() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupView.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupView.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.1.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(@NonNull Transition transition) {
                        ImageViewerPopupView.this.pager.setVisibility(0);
                        ImageViewerPopupView.this.snapshotView.setVisibility(4);
                        ImageViewerPopupView.this.showPagerIndicator();
                        ImageViewerPopupView.this.photoViewContainer.isReleasing = false;
                    }
                }));
                ImageViewerPopupView.this.snapshotView.setTranslationY(0.0f);
                ImageViewerPopupView.this.snapshotView.setTranslationX(0.0f);
                ImageViewerPopupView.this.snapshotView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                XPopupUtils.setWidthHeight(imageViewerPopupView.snapshotView, imageViewerPopupView.photoViewContainer.getWidth(), ImageViewerPopupView.this.photoViewContainer.getHeight());
                ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
                imageViewerPopupView2.animateShadowBg(imageViewerPopupView2.bgColor);
                View view2 = ImageViewerPopupView.this.customView;
                if (view2 != null) {
                    view2.animate().alpha(1.0f).setDuration(ImageViewerPopupView.this.getAnimationDuration()).start();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_image_viewer_popup_view;
    }

    public int getRealPosition() {
        return this.isInfinite ? this.position % this.urls.size() : this.position;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() throws Resources.NotFoundException {
        super.initPopupContent();
        this.tv_pager_indicator = (TextView) findViewById(R.id.tv_pager_indicator);
        this.tv_save = (TextView) findViewById(R.id.tv_save);
        this.placeholderView = (BlankView) findViewById(R.id.placeholderView);
        PhotoViewContainer photoViewContainer = (PhotoViewContainer) findViewById(R.id.photoViewContainer);
        this.photoViewContainer = photoViewContainer;
        photoViewContainer.setOnDragChangeListener(this);
        this.pager = (HackyViewPager) findViewById(R.id.pager);
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter();
        this.pager.setAdapter(photoViewAdapter);
        this.pager.setCurrentItem(this.position);
        this.pager.setVisibility(4);
        addOrUpdateSnapshot();
        this.pager.setOffscreenPageLimit(2);
        this.pager.addOnPageChangeListener(photoViewAdapter);
        if (!this.isShowIndicator) {
            this.tv_pager_indicator.setVisibility(8);
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setOnClickListener(this);
        } else {
            this.tv_save.setVisibility(8);
        }
    }

    public ImageViewerPopupView isInfinite(boolean z2) {
        this.isInfinite = z2;
        return this;
    }

    public ImageViewerPopupView isShowIndicator(boolean z2) {
        this.isShowIndicator = z2;
        return this;
    }

    public ImageViewerPopupView isShowPlaceholder(boolean z2) {
        this.isShowPlaceholder = z2;
        return this;
    }

    public ImageViewerPopupView isShowSaveButton(boolean z2) {
        this.isShowSaveBtn = z2;
        return this;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.tv_save) {
            save();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        this.srcView = null;
        this.srcViewUpdateListener = null;
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onDragChange(int i2, float f2, float f3) {
        float f4 = 1.0f - f3;
        this.tv_pager_indicator.setAlpha(f4);
        View view = this.customView;
        if (view != null) {
            view.setAlpha(f4);
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setAlpha(f4);
        }
        this.photoViewContainer.setBackgroundColor(((Integer) this.argbEvaluator.evaluate(f3 * 0.8f, Integer.valueOf(this.bgColor), 0)).intValue());
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onRelease() {
        dismiss();
    }

    public void save() {
        XPermission.create(getContext(), "STORAGE").callback(new XPermission.SimpleCallback() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.4
            @Override // com.lxj.xpopup.util.XPermission.SimpleCallback
            public void onDenied() {
            }

            @Override // com.lxj.xpopup.util.XPermission.SimpleCallback
            public void onGranted() {
                Context context = ImageViewerPopupView.this.getContext();
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                XPopupUtils.saveBmpToAlbum(context, imageViewerPopupView.imageLoader, imageViewerPopupView.urls.get(imageViewerPopupView.getRealPosition()));
            }
        }).request();
    }

    public ImageViewerPopupView setBgColor(int i2) {
        this.bgColor = i2;
        return this;
    }

    public ImageViewerPopupView setImageUrls(List<Object> list) {
        this.urls = list;
        return this;
    }

    public ImageViewerPopupView setLongPressListener(OnImageViewerLongPressListener onImageViewerLongPressListener) {
        this.longPressListener = onImageViewerLongPressListener;
        return this;
    }

    public ImageViewerPopupView setPlaceholderColor(int i2) {
        this.placeholderColor = i2;
        return this;
    }

    public ImageViewerPopupView setPlaceholderRadius(int i2) {
        this.placeholderRadius = i2;
        return this;
    }

    public ImageViewerPopupView setPlaceholderStrokeColor(int i2) {
        this.placeholderStrokeColor = i2;
        return this;
    }

    public ImageViewerPopupView setSingleSrcView(ImageView imageView, Object obj) {
        if (this.urls == null) {
            this.urls = new ArrayList();
        }
        this.urls.clear();
        this.urls.add(obj);
        setSrcView(imageView, 0);
        return this;
    }

    public ImageViewerPopupView setSrcView(ImageView imageView, int i2) {
        this.srcView = imageView;
        this.position = i2;
        if (imageView != null) {
            int[] iArr = new int[2];
            imageView.getLocationInWindow(iArr);
            if (XPopupUtils.isLayoutRtl(getContext())) {
                int i3 = -((XPopupUtils.getWindowWidth(getContext()) - iArr[0]) - imageView.getWidth());
                this.rect = new Rect(i3, iArr[1], imageView.getWidth() + i3, iArr[1] + imageView.getHeight());
            } else {
                int i4 = iArr[0];
                this.rect = new Rect(i4, iArr[1], imageView.getWidth() + i4, iArr[1] + imageView.getHeight());
            }
        }
        return this;
    }

    public ImageViewerPopupView setSrcViewUpdateListener(OnSrcViewUpdateListener onSrcViewUpdateListener) {
        this.srcViewUpdateListener = onSrcViewUpdateListener;
        return this;
    }

    public ImageViewerPopupView setXPopupImageLoader(XPopupImageLoader xPopupImageLoader) {
        this.imageLoader = xPopupImageLoader;
        return this;
    }

    public void updateSrcView(ImageView imageView) {
        setSrcView(imageView, this.position);
        addOrUpdateSnapshot();
    }
}
