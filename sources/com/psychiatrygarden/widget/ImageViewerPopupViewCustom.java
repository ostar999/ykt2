package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnDragChangeListener;
import com.lxj.xpopup.interfaces.OnImageViewerLongPressListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.photoview.PhotoView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BlankView;
import com.lxj.xpopup.widget.HackyViewPager;
import com.lxj.xpopup.widget.PhotoViewContainer;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* loaded from: classes6.dex */
public class ImageViewerPopupViewCustom extends BasePopupView implements OnDragChangeListener, View.OnClickListener {
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
            int iDp2px = XPopupUtils.dp2px(ImageViewerPopupViewCustom.this.container.getContext(), 40.0f);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iDp2px, iDp2px);
            layoutParams.gravity = 17;
            progressBar.setLayoutParams(layoutParams);
            progressBar.setVisibility(8);
            return progressBar;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            ImageViewerPopupViewCustom imageViewerPopupViewCustom = ImageViewerPopupViewCustom.this;
            if (imageViewerPopupViewCustom.isInfinite) {
                return 100000;
            }
            return imageViewerPopupViewCustom.urls.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageViewerPopupViewCustom imageViewerPopupViewCustom = ImageViewerPopupViewCustom.this;
            if (imageViewerPopupViewCustom.isInfinite) {
                position %= imageViewerPopupViewCustom.urls.size();
            }
            int i2 = position;
            FrameLayout frameLayoutBuildContainer = buildContainer(container.getContext());
            ProgressBar progressBarBuildProgressBar = buildProgressBar(container.getContext());
            ImageViewerPopupViewCustom imageViewerPopupViewCustom2 = ImageViewerPopupViewCustom.this;
            XPopupImageLoader xPopupImageLoader = imageViewerPopupViewCustom2.imageLoader;
            Object obj = imageViewerPopupViewCustom2.urls.get(i2);
            ImageViewerPopupViewCustom imageViewerPopupViewCustom3 = ImageViewerPopupViewCustom.this;
            frameLayoutBuildContainer.addView(xPopupImageLoader.loadImage(i2, obj, imageViewerPopupViewCustom3, imageViewerPopupViewCustom3.snapshotView, progressBarBuildProgressBar), new FrameLayout.LayoutParams(-1, -1));
            frameLayoutBuildContainer.addView(progressBarBuildProgressBar);
            container.addView(frameLayoutBuildContainer);
            return frameLayoutBuildContainer;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o2) {
            return o2 == view;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i2) {
            ImageViewerPopupViewCustom imageViewerPopupViewCustom = ImageViewerPopupViewCustom.this;
            imageViewerPopupViewCustom.position = i2;
            imageViewerPopupViewCustom.showPagerIndicator();
            OnSrcViewUpdateListener onSrcViewUpdateListener = ImageViewerPopupViewCustom.this.srcViewUpdateListener;
        }
    }

    public ImageViewerPopupViewCustom(@NonNull Context context) {
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
    public void animateShadowBg(final int endColor) {
        final int color = ((ColorDrawable) this.photoViewContainer.getBackground()).getColor();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ImageViewerPopupViewCustom imageViewerPopupViewCustom = ImageViewerPopupViewCustom.this;
                imageViewerPopupViewCustom.photoViewContainer.setBackgroundColor(((Integer) imageViewerPopupViewCustom.argbEvaluator.evaluate(animation.getAnimatedFraction(), Integer.valueOf(color), Integer.valueOf(endColor))).intValue());
            }
        });
        valueAnimatorOfFloat.setDuration(getAnimationDuration()).setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.start();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getMimeType(java.lang.String r3) {
        /*
            r0 = 46
            int r0 = r3.lastIndexOf(r0)
            r1 = 1
            int r0 = r0 + r1
            java.lang.String r3 = r3.substring(r0)
            java.util.Locale r0 = java.util.Locale.ROOT
            java.lang.String r3 = r3.toLowerCase(r0)
            r3.hashCode()
            int r0 = r3.hashCode()
            r2 = -1
            switch(r0) {
                case 105441: goto L3f;
                case 111145: goto L36;
                case 3268712: goto L2b;
                case 3645340: goto L1f;
                default: goto L1d;
            }
        L1d:
            r1 = r2
            goto L49
        L1f:
            java.lang.String r0 = "webp"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L29
            goto L1d
        L29:
            r1 = 3
            goto L49
        L2b:
            java.lang.String r0 = "jpeg"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L34
            goto L1d
        L34:
            r1 = 2
            goto L49
        L36:
            java.lang.String r0 = "png"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L49
            goto L1d
        L3f:
            java.lang.String r0 = "jpg"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L48
            goto L1d
        L48:
            r1 = 0
        L49:
            switch(r1) {
                case 0: goto L55;
                case 1: goto L52;
                case 2: goto L55;
                case 3: goto L4f;
                default: goto L4c;
            }
        L4c:
            java.lang.String r3 = "image/*"
            return r3
        L4f:
            java.lang.String r3 = "image/webp"
            return r3
        L52:
            java.lang.String r3 = "image/png"
            return r3
        L55:
            java.lang.String r3 = "image/jpeg"
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.getMimeType(java.lang.String):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0() {
        ActivityCompat.requestPermissions((Activity) getContext(), Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.READ_MEDIA_IMAGES, Permission.WRITE_EXTERNAL_STORAGE} : new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(boolean z2) {
        Toast.makeText(getContext(), z2 ? "保存成功" : "保存失败", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(Object obj) throws ExecutionException, InterruptedException {
        try {
            GlideUrl glideUrlGenerateUrl = obj instanceof String ? GlideUtils.generateUrl(String.valueOf(obj)) : null;
            RequestBuilder<File> requestBuilderDownloadOnly = Glide.with(getContext()).downloadOnly();
            if (glideUrlGenerateUrl != null) {
                obj = glideUrlGenerateUrl;
            }
            File file = requestBuilderDownloadOnly.load(obj).submit().get();
            final boolean zSaveImageToGallery = saveImageToGallery(getContext(), file, System.currentTimeMillis() + ".jpg");
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.psychiatrygarden.widget.ca
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16366c.lambda$save$1(zSaveImageToGallery);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean saveImageToGallery(Context context, File imageFile, String fileName) throws IOException {
        if (!imageFile.exists()) {
            return false;
        }
        String mimeType = getMimeType(imageFile.getName());
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = Build.VERSION.SDK_INT;
        Uri contentUri = i2 >= 29 ? MediaStore.Images.Media.getContentUri("external_primary") : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", fileName);
        contentValues.put("mime_type", mimeType);
        contentValues.put("date_added", Long.valueOf(System.currentTimeMillis() / 1000));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        if (i2 >= 29) {
            contentValues.put("relative_path", Environment.DIRECTORY_PICTURES);
            contentValues.put("is_pending", (Integer) 1);
        }
        Uri uriInsert = contentResolver.insert(contentUri, contentValues);
        if (uriInsert == null) {
            return false;
        }
        try {
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            byte[] bArr = new byte[4096];
            while (true) {
                int i3 = fileInputStream.read(bArr);
                if (i3 == -1) {
                    break;
                }
                outputStreamOpenOutputStream.write(bArr, 0, i3);
            }
            fileInputStream.close();
            outputStreamOpenOutputStream.close();
            if (Build.VERSION.SDK_INT >= 29) {
                contentValues.clear();
                contentValues.put("is_pending", (Integer) 0);
                contentResolver.update(uriInsert, contentValues, null, null);
            }
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            contentResolver.delete(uriInsert, null, null);
            return false;
        }
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
        hackyViewPager.removeOnPageChangeListener((ImageViewerPopupView.PhotoViewAdapter) hackyViewPager.getAdapter());
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
        this.snapshotView.post(new Runnable() { // from class: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.3
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupViewCustom.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupViewCustom.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.3.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(@NonNull Transition transition) {
                        ImageViewerPopupViewCustom.this.pager.setVisibility(4);
                        ImageViewerPopupViewCustom.this.snapshotView.setVisibility(0);
                        ImageViewerPopupViewCustom.this.pager.setScaleX(1.0f);
                        ImageViewerPopupViewCustom.this.pager.setScaleY(1.0f);
                        ImageViewerPopupViewCustom.this.snapshotView.setScaleX(1.0f);
                        ImageViewerPopupViewCustom.this.snapshotView.setScaleY(1.0f);
                        ImageViewerPopupViewCustom.this.placeholderView.setVisibility(4);
                    }
                }));
                ImageViewerPopupViewCustom.this.snapshotView.setScaleX(1.0f);
                ImageViewerPopupViewCustom.this.snapshotView.setScaleY(1.0f);
                ImageViewerPopupViewCustom.this.snapshotView.setTranslationY(r0.rect.top);
                ImageViewerPopupViewCustom.this.snapshotView.setTranslationX(r0.rect.left);
                ImageViewerPopupViewCustom imageViewerPopupViewCustom = ImageViewerPopupViewCustom.this;
                imageViewerPopupViewCustom.snapshotView.setScaleType(imageViewerPopupViewCustom.srcView.getScaleType());
                ImageViewerPopupViewCustom imageViewerPopupViewCustom2 = ImageViewerPopupViewCustom.this;
                XPopupUtils.setWidthHeight(imageViewerPopupViewCustom2.snapshotView, imageViewerPopupViewCustom2.rect.width(), ImageViewerPopupViewCustom.this.rect.height());
                ImageViewerPopupViewCustom.this.animateShadowBg(0);
                View view = ImageViewerPopupViewCustom.this.customView;
                if (view != null) {
                    view.animate().alpha(0.0f).setDuration(ImageViewerPopupViewCustom.this.getAnimationDuration()).setListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.3.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            View view2 = ImageViewerPopupViewCustom.this.customView;
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
        this.snapshotView.post(new Runnable() { // from class: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.1
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupViewCustom.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupViewCustom.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.psychiatrygarden.widget.ImageViewerPopupViewCustom.1.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(@NonNull Transition transition) {
                        ImageViewerPopupViewCustom.this.pager.setVisibility(0);
                        ImageViewerPopupViewCustom.this.snapshotView.setVisibility(4);
                        ImageViewerPopupViewCustom.this.showPagerIndicator();
                        ImageViewerPopupViewCustom.this.photoViewContainer.isReleasing = false;
                    }
                }));
                ImageViewerPopupViewCustom.this.snapshotView.setTranslationY(0.0f);
                ImageViewerPopupViewCustom.this.snapshotView.setTranslationX(0.0f);
                ImageViewerPopupViewCustom.this.snapshotView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageViewerPopupViewCustom imageViewerPopupViewCustom = ImageViewerPopupViewCustom.this;
                XPopupUtils.setWidthHeight(imageViewerPopupViewCustom.snapshotView, imageViewerPopupViewCustom.photoViewContainer.getWidth(), ImageViewerPopupViewCustom.this.photoViewContainer.getHeight());
                ImageViewerPopupViewCustom imageViewerPopupViewCustom2 = ImageViewerPopupViewCustom.this;
                imageViewerPopupViewCustom2.animateShadowBg(imageViewerPopupViewCustom2.bgColor);
                View view2 = ImageViewerPopupViewCustom.this.customView;
                if (view2 != null) {
                    view2.animate().alpha(1.0f).setDuration(ImageViewerPopupViewCustom.this.getAnimationDuration()).start();
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

    public ImageViewerPopupViewCustom isInfinite(boolean isInfinite) {
        this.isInfinite = isInfinite;
        return this;
    }

    public ImageViewerPopupViewCustom isShowIndicator(boolean isShow) {
        this.isShowIndicator = isShow;
        return this;
    }

    public ImageViewerPopupViewCustom isShowPlaceholder(boolean isShow) {
        this.isShowPlaceholder = isShow;
        return this;
    }

    public ImageViewerPopupViewCustom isShowSaveButton(boolean isShowSaveBtn) {
        this.isShowSaveBtn = isShowSaveBtn;
        return this;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2 == this.tv_save) {
            if (CommonUtil.hasRequiredPermissionsNoCamera(getContext())) {
                save();
            } else {
                new XPopup.Builder(getContext()).asCustom(new RequestMediaPermissionPop(getContext(), new OnConfirmListener() { // from class: com.psychiatrygarden.widget.ba
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f16340a.lambda$onClick$0();
                    }
                })).show();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        this.srcView = null;
        this.srcViewUpdateListener = null;
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onDragChange(int dy, float scale, float fraction) {
        float f2 = 1.0f - fraction;
        this.tv_pager_indicator.setAlpha(f2);
        View view = this.customView;
        if (view != null) {
            view.setAlpha(f2);
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setAlpha(f2);
        }
        this.photoViewContainer.setBackgroundColor(((Integer) this.argbEvaluator.evaluate(fraction * 0.8f, Integer.valueOf(this.bgColor), 0)).intValue());
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onRelease() {
        dismiss();
    }

    public void save() {
        final Object obj = this.urls.get(getRealPosition());
        try {
            new Thread(new Runnable() { // from class: com.psychiatrygarden.widget.aa
                @Override // java.lang.Runnable
                public final void run() throws ExecutionException, InterruptedException {
                    this.f16312c.lambda$save$2(obj);
                }
            }).start();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public ImageViewerPopupViewCustom setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public ImageViewerPopupViewCustom setImageUrls(List<Object> urls) {
        this.urls = urls;
        return this;
    }

    public ImageViewerPopupViewCustom setLongPressListener(OnImageViewerLongPressListener longPressListener) {
        this.longPressListener = longPressListener;
        return this;
    }

    public ImageViewerPopupViewCustom setPlaceholderColor(int color) {
        this.placeholderColor = color;
        return this;
    }

    public ImageViewerPopupViewCustom setPlaceholderRadius(int radius) {
        this.placeholderRadius = radius;
        return this;
    }

    public ImageViewerPopupViewCustom setPlaceholderStrokeColor(int strokeColor) {
        this.placeholderStrokeColor = strokeColor;
        return this;
    }

    public ImageViewerPopupViewCustom setSingleSrcView(ImageView srcView, Object url) {
        if (this.urls == null) {
            this.urls = new ArrayList();
        }
        this.urls.clear();
        this.urls.add(url);
        setSrcView(srcView, 0);
        return this;
    }

    public ImageViewerPopupViewCustom setSrcView(ImageView srcView, int position) {
        this.srcView = srcView;
        this.position = position;
        if (srcView != null) {
            int[] iArr = new int[2];
            srcView.getLocationInWindow(iArr);
            if (XPopupUtils.isLayoutRtl(getContext())) {
                int i2 = -((XPopupUtils.getWindowWidth(getContext()) - iArr[0]) - srcView.getWidth());
                this.rect = new Rect(i2, iArr[1], srcView.getWidth() + i2, iArr[1] + srcView.getHeight());
            } else {
                int i3 = iArr[0];
                this.rect = new Rect(i3, iArr[1], srcView.getWidth() + i3, iArr[1] + srcView.getHeight());
            }
        }
        return this;
    }

    public ImageViewerPopupViewCustom setSrcViewUpdateListener(OnSrcViewUpdateListener srcViewUpdateListener) {
        this.srcViewUpdateListener = srcViewUpdateListener;
        return this;
    }

    public ImageViewerPopupViewCustom setXPopupImageLoader(XPopupImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public void updateSrcView(ImageView srcView) {
        setSrcView(srcView, this.position);
        addOrUpdateSnapshot();
    }
}
