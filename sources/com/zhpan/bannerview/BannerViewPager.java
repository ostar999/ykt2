package com.zhpan.bannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.catchpig.mvvm.R;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.manager.BannerManager;
import com.zhpan.bannerview.manager.BannerOptions;
import com.zhpan.bannerview.provider.ReflectLayoutManager;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.option.IndicatorOptions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint({"NotifyDataSetChanged"})
/* loaded from: classes8.dex */
public class BannerViewPager<T> extends RelativeLayout implements LifecycleObserver {
    private static final String KEY_CURRENT_POSITION = "CURRENT_POSITION";
    private static final String KEY_IS_CUSTOM_INDICATOR = "IS_CUSTOM_INDICATOR";
    private static final String KEY_SUPER_STATE = "SUPER_STATE";
    private int currentPosition;
    private boolean isCustomIndicator;
    private boolean isLooping;
    private Lifecycle lifecycleRegistry;
    private BannerManager mBannerManager;
    private BaseBannerAdapter<T> mBannerPagerAdapter;
    private final Handler mHandler;
    private RelativeLayout mIndicatorLayout;
    private IIndicator mIndicatorView;
    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    private Path mRadiusPath;
    private RectF mRadiusRectF;
    private final Runnable mRunnable;
    private ViewPager2 mViewPager;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;
    private int startX;
    private int startY;

    public interface OnPageClickListener {
        void onPageClick(View view, int i2);
    }

    public BannerViewPager(Context context) {
        this(context, null);
    }

    private int getInterval() {
        return this.mBannerManager.getBannerOptions().getInterval();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePosition() {
        BaseBannerAdapter<T> baseBannerAdapter = this.mBannerPagerAdapter;
        if (baseBannerAdapter == null || baseBannerAdapter.getListSize() <= 1 || !isAutoPlay()) {
            return;
        }
        ViewPager2 viewPager2 = this.mViewPager;
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, this.mBannerManager.getBannerOptions().isAutoScrollSmoothly());
        this.mHandler.postDelayed(this.mRunnable, getInterval());
    }

    private void init(Context context, AttributeSet attributeSet) {
        BannerManager bannerManager = new BannerManager();
        this.mBannerManager = bannerManager;
        bannerManager.initAttrs(context, attributeSet);
        initView();
    }

    private void initBannerData() {
        List<? extends T> data = this.mBannerPagerAdapter.getData();
        if (data != null) {
            setIndicatorValues(data);
            setupViewPager(data);
            initRoundCorner();
        }
    }

    private void initIndicator(IndicatorOptions indicatorOptions, List<? extends T> list) {
        if (((View) this.mIndicatorView).getParent() == null) {
            this.mIndicatorLayout.removeAllViews();
            this.mIndicatorLayout.addView((View) this.mIndicatorView);
            initIndicatorSliderMargin();
            initIndicatorGravity();
        }
        this.mIndicatorView.setIndicatorOptions(indicatorOptions);
        indicatorOptions.setPageSize(list.size());
        this.mIndicatorView.notifyDataChanged();
    }

    private void initIndicatorGravity() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((View) this.mIndicatorView).getLayoutParams();
        int indicatorGravity = this.mBannerManager.getBannerOptions().getIndicatorGravity();
        if (indicatorGravity == 0) {
            layoutParams.addRule(14);
        } else if (indicatorGravity == 2) {
            layoutParams.addRule(9);
        } else {
            if (indicatorGravity != 4) {
                return;
            }
            layoutParams.addRule(11);
        }
    }

    private void initIndicatorSliderMargin() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((View) this.mIndicatorView).getLayoutParams();
        BannerOptions.IndicatorMargin indicatorMargin = this.mBannerManager.getBannerOptions().getIndicatorMargin();
        if (indicatorMargin != null) {
            marginLayoutParams.setMargins(indicatorMargin.getLeft(), indicatorMargin.getTop(), indicatorMargin.getRight(), indicatorMargin.getBottom());
        } else {
            int iDp2px = BannerUtils.dp2px(10.0f);
            marginLayoutParams.setMargins(iDp2px, iDp2px, iDp2px, iDp2px);
        }
    }

    private void initPageStyle(int i2) {
        float pageScale = this.mBannerManager.getBannerOptions().getPageScale();
        if (i2 == 4) {
            this.mBannerManager.setMultiPageStyle(true, pageScale);
        } else if (i2 == 8) {
            this.mBannerManager.setMultiPageStyle(false, pageScale);
        }
    }

    private void initRevealWidth(BannerOptions bannerOptions) {
        int rightRevealWidth = bannerOptions.getRightRevealWidth();
        int leftRevealWidth = bannerOptions.getLeftRevealWidth();
        if (leftRevealWidth != -1000 || rightRevealWidth != -1000) {
            RecyclerView recyclerView = (RecyclerView) this.mViewPager.getChildAt(0);
            int orientation = bannerOptions.getOrientation();
            int pageMargin = bannerOptions.getPageMargin() + rightRevealWidth;
            int pageMargin2 = bannerOptions.getPageMargin() + leftRevealWidth;
            if (pageMargin2 < 0) {
                pageMargin2 = 0;
            }
            if (pageMargin < 0) {
                pageMargin = 0;
            }
            if (orientation == 0) {
                recyclerView.setPadding(pageMargin2, 0, pageMargin, 0);
            } else if (orientation == 1) {
                recyclerView.setPadding(0, pageMargin2, 0, pageMargin);
            }
            recyclerView.setClipToPadding(false);
        }
        this.mBannerManager.createMarginTransformer();
    }

    private void initRoundCorner() {
        int roundRectRadius = this.mBannerManager.getBannerOptions().getRoundRectRadius();
        if (roundRectRadius > 0) {
            ViewStyleSetter.applyRoundCorner(this, roundRectRadius);
        }
    }

    private void initView() {
        View.inflate(getContext(), R.layout.bvp_layout, this);
        this.mViewPager = (ViewPager2) findViewById(R.id.vp_main);
        this.mIndicatorLayout = (RelativeLayout) findViewById(R.id.bvp_layout_indicator);
        this.mViewPager.setPageTransformer(this.mBannerManager.getCompositePageTransformer());
    }

    private boolean isAutoPlay() {
        return this.mBannerManager.getBannerOptions().isAutoPlay();
    }

    private boolean isCanLoopSafely() {
        BaseBannerAdapter<T> baseBannerAdapter;
        BannerManager bannerManager = this.mBannerManager;
        return (bannerManager == null || bannerManager.getBannerOptions() == null || !this.mBannerManager.getBannerOptions().isCanLoop() || (baseBannerAdapter = this.mBannerPagerAdapter) == null || baseBannerAdapter.getListSize() <= 1) ? false : true;
    }

    private boolean isStopLoopWhenDetachedFromWindow() {
        return this.mBannerManager.getBannerOptions().isStopLoopWhenDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshData$1(List list) {
        if (!isAttachedToWindow() || list == null || this.mBannerPagerAdapter == null) {
            return;
        }
        stopLoop();
        this.mBannerPagerAdapter.setData(list);
        this.mBannerPagerAdapter.notifyDataSetChanged();
        resetCurrentItem(getCurrentItem());
        refreshIndicator(list);
        startLoop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnPageClickListener$0(OnPageClickListener onPageClickListener, boolean z2, View view, int i2, int i3) {
        onPageClickListener.onPageClick(view, i2);
        if (z2) {
            this.mViewPager.setCurrentItem(i3);
        }
    }

    private void onHorizontalActionMove(int i2, int i3, int i4) {
        if (i3 <= i4) {
            if (i4 > i3) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        } else if (this.mBannerManager.getBannerOptions().isCanLoop()) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (this.currentPosition != 0 || i2 - this.startX <= 0) {
            getParent().requestDisallowInterceptTouchEvent(this.currentPosition != getData().size() - 1 || i2 - this.startX >= 0);
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private void onVerticalActionMove(int i2, int i3, int i4) {
        if (i4 <= i3) {
            if (i3 > i4) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        } else if (this.mBannerManager.getBannerOptions().isCanLoop()) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (this.currentPosition != 0 || i2 - this.startY <= 0) {
            getParent().requestDisallowInterceptTouchEvent(this.currentPosition != getData().size() - 1 || i2 - this.startY >= 0);
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pageScrollStateChanged(int i2) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.onPageChangeCallback;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageScrollStateChanged(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pageScrolled(int i2, float f2, int i3) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback;
        int listSize = this.mBannerPagerAdapter.getListSize();
        this.mBannerManager.getBannerOptions().isCanLoop();
        int realPosition = BannerUtils.getRealPosition(i2, listSize);
        if (listSize <= 0 || (onPageChangeCallback = this.onPageChangeCallback) == null) {
            return;
        }
        onPageChangeCallback.onPageScrolled(realPosition, f2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pageSelected(int i2) {
        int listSize = this.mBannerPagerAdapter.getListSize();
        boolean zIsCanLoop = this.mBannerManager.getBannerOptions().isCanLoop();
        int realPosition = BannerUtils.getRealPosition(i2, listSize);
        this.currentPosition = realPosition;
        if (listSize > 0 && zIsCanLoop && (i2 == 0 || i2 == 999)) {
            resetCurrentItem(realPosition);
        }
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.onPageChangeCallback;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageSelected(this.currentPosition);
        }
    }

    private void refreshIndicator(List<? extends T> list) {
        setIndicatorValues(list);
        this.mBannerManager.getBannerOptions().getIndicatorOptions().setCurrentPosition(BannerUtils.getRealPosition(this.mViewPager.getCurrentItem(), list.size()));
        this.mIndicatorView.notifyDataChanged();
    }

    private void resetCurrentItem(int i2) {
        if (isCanLoopSafely()) {
            this.mViewPager.setCurrentItem(BannerUtils.getOriginalPosition(this.mBannerPagerAdapter.getListSize()) + i2, false);
        } else {
            this.mViewPager.setCurrentItem(i2, false);
        }
    }

    private void setIndicatorValues(List<? extends T> list) {
        BannerOptions bannerOptions = this.mBannerManager.getBannerOptions();
        this.mIndicatorLayout.setVisibility(bannerOptions.getIndicatorVisibility());
        bannerOptions.resetIndicatorOptions();
        if (this.isCustomIndicator) {
            this.mIndicatorLayout.removeAllViews();
        } else if (this.mIndicatorView == null) {
            this.mIndicatorView = new IndicatorView(getContext());
        }
        initIndicator(bannerOptions.getIndicatorOptions(), list);
    }

    private void setupViewPager(List<T> list) {
        if (this.mBannerPagerAdapter == null) {
            throw new NullPointerException("You must set adapter for BannerViewPager");
        }
        BannerOptions bannerOptions = this.mBannerManager.getBannerOptions();
        if (bannerOptions.getScrollDuration() != 0) {
            ReflectLayoutManager.reflectLayoutManager(this.mViewPager, bannerOptions.getScrollDuration());
        }
        this.currentPosition = 0;
        this.mBannerPagerAdapter.setCanLoop(bannerOptions.isCanLoop());
        this.mViewPager.setAdapter(this.mBannerPagerAdapter);
        if (isCanLoopSafely()) {
            this.mViewPager.setCurrentItem(BannerUtils.getOriginalPosition(list.size()), false);
        }
        this.mViewPager.unregisterOnPageChangeCallback(this.mOnPageChangeCallback);
        this.mViewPager.registerOnPageChangeCallback(this.mOnPageChangeCallback);
        this.mViewPager.setOrientation(bannerOptions.getOrientation());
        this.mViewPager.setOffscreenPageLimit(bannerOptions.getOffScreenPageLimit());
        initRevealWidth(bannerOptions);
        initPageStyle(bannerOptions.getPageStyle());
        startLoop();
    }

    public void addData(List<? extends T> list) {
        BaseBannerAdapter<T> baseBannerAdapter;
        if (!isAttachedToWindow() || list == null || (baseBannerAdapter = this.mBannerPagerAdapter) == null) {
            return;
        }
        List<? extends T> data = baseBannerAdapter.getData();
        data.addAll(list);
        this.mBannerPagerAdapter.notifyDataSetChanged();
        resetCurrentItem(getCurrentItem());
        refreshIndicator(data);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration, int i2) {
        if (!isCanLoopSafely()) {
            this.mViewPager.addItemDecoration(itemDecoration, i2);
            return;
        }
        int listSize = this.mBannerPagerAdapter.getListSize();
        int currentItem = this.mViewPager.getCurrentItem();
        this.mBannerManager.getBannerOptions().isCanLoop();
        int realPosition = BannerUtils.getRealPosition(currentItem, listSize);
        if (currentItem != i2) {
            if (i2 == 0 && realPosition == listSize - 1) {
                this.mViewPager.addItemDecoration(itemDecoration, currentItem + 1);
            } else if (realPosition == 0 && i2 == listSize - 1) {
                this.mViewPager.addItemDecoration(itemDecoration, currentItem - 1);
            } else {
                this.mViewPager.addItemDecoration(itemDecoration, currentItem + (i2 - realPosition));
            }
        }
    }

    public BannerViewPager<T> addPageTransformer(@Nullable ViewPager2.PageTransformer pageTransformer) {
        if (pageTransformer != null) {
            this.mBannerManager.addTransformer(pageTransformer);
        }
        return this;
    }

    public void create(List<T> list) {
        BaseBannerAdapter<T> baseBannerAdapter = this.mBannerPagerAdapter;
        if (baseBannerAdapter == null) {
            throw new NullPointerException("You must set adapter for BannerViewPager");
        }
        baseBannerAdapter.setData(list);
        initBannerData();
    }

    @Deprecated
    public BannerViewPager<T> disallowInterceptTouchEvent(boolean z2) {
        this.mBannerManager.getBannerOptions().setDisallowParentInterceptDownEvent(z2);
        return this;
    }

    public BannerViewPager<T> disallowParentInterceptDownEvent(boolean z2) {
        this.mBannerManager.getBannerOptions().setDisallowParentInterceptDownEvent(z2);
        return this;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        float[] roundRectRadiusArray = this.mBannerManager.getBannerOptions().getRoundRectRadiusArray();
        RectF rectF = this.mRadiusRectF;
        if (rectF != null && this.mRadiusPath != null && roundRectRadiusArray != null) {
            rectF.right = getWidth();
            this.mRadiusRectF.bottom = getHeight();
            this.mRadiusPath.addRoundRect(this.mRadiusRectF, roundRectRadiusArray, Path.Direction.CW);
            canvas.clipPath(this.mRadiusPath);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isLooping = true;
            stopLoop();
        } else if (action == 1 || action == 3 || action == 4) {
            this.isLooping = false;
            startLoop();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public BaseBannerAdapter<T> getAdapter() {
        return this.mBannerPagerAdapter;
    }

    public int getCurrentItem() {
        return this.currentPosition;
    }

    public List<T> getData() {
        BaseBannerAdapter<T> baseBannerAdapter = this.mBannerPagerAdapter;
        return baseBannerAdapter != null ? baseBannerAdapter.getData() : Collections.emptyList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void insertItem(int i2, T t2) {
        List<? extends T> data = this.mBannerPagerAdapter.getData();
        if (!isAttachedToWindow() || i2 < 0 || i2 > data.size()) {
            return;
        }
        data.add(i2, t2);
        this.mBannerPagerAdapter.notifyDataSetChanged();
        resetCurrentItem(getCurrentItem());
        refreshIndicator(data);
    }

    public void nextPage() {
        setCurrentItem(getCurrentItem() + 1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mBannerManager == null || !isStopLoopWhenDetachedFromWindow()) {
            return;
        }
        startLoop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        stopLoop();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        if (this.mBannerManager != null && isStopLoopWhenDetachedFromWindow()) {
            stopLoop();
        }
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            androidx.viewpager2.widget.ViewPager2 r0 = r6.mViewPager
            boolean r0 = r0.isUserInputEnabled()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L1b
            com.zhpan.bannerview.BaseBannerAdapter<T> r0 = r6.mBannerPagerAdapter
            if (r0 == 0) goto L19
            java.util.List r0 = r0.getData()
            int r0 = r0.size()
            if (r0 > r2) goto L19
            goto L1b
        L19:
            r0 = r1
            goto L1c
        L1b:
            r0 = r2
        L1c:
            if (r0 == 0) goto L23
            boolean r7 = super.onInterceptTouchEvent(r7)
            return r7
        L23:
            int r0 = r7.getAction()
            if (r0 == 0) goto L6a
            if (r0 == r2) goto L62
            r3 = 2
            if (r0 == r3) goto L32
            r2 = 3
            if (r0 == r2) goto L62
            goto L8a
        L32:
            float r0 = r7.getX()
            int r0 = (int) r0
            float r1 = r7.getY()
            int r1 = (int) r1
            int r3 = r6.startX
            int r3 = r0 - r3
            int r3 = java.lang.Math.abs(r3)
            int r4 = r6.startY
            int r4 = r1 - r4
            int r4 = java.lang.Math.abs(r4)
            com.zhpan.bannerview.manager.BannerManager r5 = r6.mBannerManager
            com.zhpan.bannerview.manager.BannerOptions r5 = r5.getBannerOptions()
            int r5 = r5.getOrientation()
            if (r5 != r2) goto L5c
            r6.onVerticalActionMove(r1, r3, r4)
            goto L8a
        L5c:
            if (r5 != 0) goto L8a
            r6.onHorizontalActionMove(r0, r3, r4)
            goto L8a
        L62:
            android.view.ViewParent r0 = r6.getParent()
            r0.requestDisallowInterceptTouchEvent(r1)
            goto L8a
        L6a:
            float r0 = r7.getX()
            int r0 = (int) r0
            r6.startX = r0
            float r0 = r7.getY()
            int r0 = (int) r0
            r6.startY = r0
            android.view.ViewParent r0 = r6.getParent()
            com.zhpan.bannerview.manager.BannerManager r1 = r6.mBannerManager
            com.zhpan.bannerview.manager.BannerOptions r1 = r1.getBannerOptions()
            boolean r1 = r1.isDisallowParentInterceptDownEvent()
            r1 = r1 ^ r2
            r0.requestDisallowInterceptTouchEvent(r1)
        L8a:
            boolean r7 = super.onInterceptTouchEvent(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhpan.bannerview.BannerViewPager.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        stopLoop();
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
        this.currentPosition = bundle.getInt(KEY_CURRENT_POSITION);
        this.isCustomIndicator = bundle.getBoolean(KEY_IS_CUSTOM_INDICATOR);
        setCurrentItem(this.currentPosition, false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        startLoop();
    }

    @Override // android.view.View
    @Nullable
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, parcelableOnSaveInstanceState);
        bundle.putInt(KEY_CURRENT_POSITION, this.currentPosition);
        bundle.putBoolean(KEY_IS_CUSTOM_INDICATOR, this.isCustomIndicator);
        return bundle;
    }

    public void previousPage() {
        setCurrentItem(getCurrentItem() - 1);
    }

    public void refreshData(final List<? extends T> list) {
        post(new Runnable() { // from class: com.zhpan.bannerview.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f26557c.lambda$refreshData$1(list);
            }
        });
    }

    public BannerViewPager<T> registerLifecycleObserver(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
        this.lifecycleRegistry = lifecycle;
        return this;
    }

    public BannerViewPager<T> registerOnPageChangeCallback(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.onPageChangeCallback = onPageChangeCallback;
        return this;
    }

    public void removeDefaultPageTransformer() {
        this.mBannerManager.removeDefaultPageTransformer();
    }

    public void removeItem(int i2) {
        List<? extends T> data = this.mBannerPagerAdapter.getData();
        if (!isAttachedToWindow() || i2 < 0 || i2 >= data.size()) {
            return;
        }
        data.remove(i2);
        this.mBannerPagerAdapter.notifyDataSetChanged();
        resetCurrentItem(getCurrentItem());
        refreshIndicator(data);
    }

    public BannerViewPager<T> removeLifecycleObserver(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
        return this;
    }

    public void removeMarginPageTransformer() {
        this.mBannerManager.removeMarginPageTransformer();
    }

    public void removeTransformer(@Nullable ViewPager2.PageTransformer pageTransformer) {
        if (pageTransformer != null) {
            this.mBannerManager.removeTransformer(pageTransformer);
        }
    }

    public BannerViewPager<T> setAdapter(BaseBannerAdapter<T> baseBannerAdapter) {
        this.mBannerPagerAdapter = baseBannerAdapter;
        return this;
    }

    public BannerViewPager<T> setAutoPlay(boolean z2) {
        this.mBannerManager.getBannerOptions().setAutoPlay(z2);
        if (isAutoPlay()) {
            this.mBannerManager.getBannerOptions().setCanLoop(true);
        }
        return this;
    }

    public BannerViewPager<T> setAutoPlaySmoothly(boolean z2) {
        this.mBannerManager.getBannerOptions().setAutoScrollSmoothly(z2);
        return this;
    }

    public BannerViewPager<T> setCanLoop(boolean z2) {
        this.mBannerManager.getBannerOptions().setCanLoop(z2);
        if (!z2) {
            this.mBannerManager.getBannerOptions().setAutoPlay(false);
        }
        return this;
    }

    public void setCurrentItem(int i2) {
        setCurrentItem(i2, true);
    }

    public BannerViewPager<T> setIndicatorGravity(int i2) {
        this.mBannerManager.getBannerOptions().setIndicatorGravity(i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorHeight(@Px int i2) {
        this.mBannerManager.getBannerOptions().setIndicatorHeight(i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorMargin(@Px int i2, @Px int i3, @Px int i4, @Px int i5) {
        this.mBannerManager.getBannerOptions().setIndicatorMargin(i2, i3, i4, i5);
        return this;
    }

    public BannerViewPager<T> setIndicatorSlideMode(int i2) {
        this.mBannerManager.getBannerOptions().setIndicatorSlideMode(i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorSliderColor(@ColorInt int i2, @ColorInt int i3) {
        this.mBannerManager.getBannerOptions().setIndicatorSliderColor(i2, i3);
        return this;
    }

    public BannerViewPager<T> setIndicatorSliderGap(@Px int i2) {
        this.mBannerManager.getBannerOptions().setIndicatorGap(i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorSliderRadius(@Px int i2) {
        setIndicatorSliderRadius(i2, i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorSliderWidth(@Px int i2) {
        setIndicatorSliderWidth(i2, i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorStyle(int i2) {
        this.mBannerManager.getBannerOptions().setIndicatorStyle(i2);
        return this;
    }

    public BannerViewPager<T> setIndicatorView(IIndicator iIndicator) {
        if (iIndicator instanceof View) {
            this.isCustomIndicator = true;
            this.mIndicatorView = iIndicator;
        }
        return this;
    }

    public BannerViewPager<T> setIndicatorVisibility(int i2) {
        this.mBannerManager.getBannerOptions().setIndicatorVisibility(i2);
        return this;
    }

    public BannerViewPager<T> setInterval(int i2) {
        this.mBannerManager.getBannerOptions().setInterval(i2);
        return this;
    }

    @Deprecated
    public BannerViewPager<T> setLifecycleRegistry(Lifecycle lifecycle) {
        registerLifecycleObserver(lifecycle);
        return this;
    }

    public BannerViewPager<T> setOffScreenPageLimit(int i2) {
        this.mBannerManager.getBannerOptions().setOffScreenPageLimit(i2);
        return this;
    }

    public BannerViewPager<T> setOnPageClickListener(OnPageClickListener onPageClickListener) {
        setOnPageClickListener(onPageClickListener, false);
        return this;
    }

    public BannerViewPager<T> setOrientation(int i2) {
        this.mBannerManager.getBannerOptions().setOrientation(i2);
        return this;
    }

    public BannerViewPager<T> setPageMargin(@Px int i2) {
        this.mBannerManager.setPageMargin(i2);
        return this;
    }

    public BannerViewPager<T> setPageStyle(int i2) {
        return setPageStyle(i2, 0.85f);
    }

    public BannerViewPager<T> setPageTransformer(@Nullable ViewPager2.PageTransformer pageTransformer) {
        if (pageTransformer != null) {
            this.mViewPager.setPageTransformer(pageTransformer);
        }
        return this;
    }

    public BannerViewPager<T> setRTLMode(boolean z2) {
        this.mViewPager.setLayoutDirection(z2 ? 1 : 0);
        this.mBannerManager.getBannerOptions().setRtl(z2);
        return this;
    }

    public BannerViewPager<T> setRevealWidth(@Px int i2) {
        setRevealWidth(i2, i2);
        return this;
    }

    public BannerViewPager<T> setRoundCorner(@Px int i2) {
        this.mBannerManager.getBannerOptions().setRoundRectRadius(i2);
        return this;
    }

    @Deprecated
    public BannerViewPager<T> setRoundRect(@Px int i2) {
        return setRoundCorner(i2);
    }

    public BannerViewPager<T> setScrollDuration(int i2) {
        this.mBannerManager.getBannerOptions().setScrollDuration(i2);
        return this;
    }

    public BannerViewPager<T> setUserInputEnabled(boolean z2) {
        this.mBannerManager.getBannerOptions().setUserInputEnabled(z2);
        this.mViewPager.setUserInputEnabled(z2);
        return this;
    }

    public BannerViewPager<T> showIndicatorWhenOneItem(boolean z2) {
        this.mBannerManager.getBannerOptions().showIndicatorWhenOneItem(z2);
        return this;
    }

    public void startLoop() {
        BaseBannerAdapter<T> baseBannerAdapter;
        if (this.isLooping || !isAutoPlay() || (baseBannerAdapter = this.mBannerPagerAdapter) == null || baseBannerAdapter.getListSize() <= 1 || !isAttachedToWindow()) {
            return;
        }
        Lifecycle lifecycle = this.lifecycleRegistry;
        if (lifecycle == null || lifecycle.getCurrentState() == Lifecycle.State.RESUMED || this.lifecycleRegistry.getCurrentState() == Lifecycle.State.CREATED) {
            this.mHandler.postDelayed(this.mRunnable, getInterval());
            this.isLooping = true;
        }
    }

    public void startLoopNow() {
        BaseBannerAdapter<T> baseBannerAdapter;
        if (this.isLooping || !isAutoPlay() || (baseBannerAdapter = this.mBannerPagerAdapter) == null || baseBannerAdapter.getListSize() <= 1) {
            return;
        }
        this.mHandler.post(this.mRunnable);
        this.isLooping = true;
    }

    public void stopLoop() {
        if (this.isLooping) {
            this.mHandler.removeCallbacks(this.mRunnable);
            this.isLooping = false;
        }
    }

    public BannerViewPager<T> stopLoopWhenDetachedFromWindow(boolean z2) {
        this.mBannerManager.getBannerOptions().setStopLoopWhenDetachedFromWindow(z2);
        return this;
    }

    public BannerViewPager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setCurrentItem(int i2, boolean z2) {
        if (!isCanLoopSafely()) {
            this.mViewPager.setCurrentItem(i2, z2);
            return;
        }
        stopLoop();
        int currentItem = this.mViewPager.getCurrentItem();
        this.mViewPager.setCurrentItem(currentItem + (i2 - BannerUtils.getRealPosition(currentItem, this.mBannerPagerAdapter.getListSize())), z2);
        startLoop();
    }

    public BannerViewPager<T> setIndicatorSliderRadius(@Px int i2, @Px int i3) {
        this.mBannerManager.getBannerOptions().setIndicatorSliderWidth(i2 * 2, i3 * 2);
        return this;
    }

    public BannerViewPager<T> setIndicatorSliderWidth(@Px int i2, @Px int i3) {
        this.mBannerManager.getBannerOptions().setIndicatorSliderWidth(i2, i3);
        return this;
    }

    public BannerViewPager<T> setOnPageClickListener(final OnPageClickListener onPageClickListener, final boolean z2) {
        BaseBannerAdapter<T> baseBannerAdapter = this.mBannerPagerAdapter;
        if (baseBannerAdapter != null) {
            baseBannerAdapter.setPageClickListener(new BaseBannerAdapter.PageClickListener() { // from class: com.zhpan.bannerview.c
                @Override // com.zhpan.bannerview.BaseBannerAdapter.PageClickListener
                public final void onPageClick(View view, int i2, int i3) {
                    this.f26559a.lambda$setOnPageClickListener$0(onPageClickListener, z2, view, i2, i3);
                }
            });
        }
        return this;
    }

    public BannerViewPager<T> setPageStyle(int i2, float f2) {
        this.mBannerManager.getBannerOptions().setPageStyle(i2);
        this.mBannerManager.getBannerOptions().setPageScale(f2);
        return this;
    }

    public BannerViewPager<T> setRevealWidth(@Px int i2, @Px int i3) {
        this.mBannerManager.getBannerOptions().setRightRevealWidth(i3);
        this.mBannerManager.getBannerOptions().setLeftRevealWidth(i2);
        return this;
    }

    public BannerViewPager<T> setRoundCorner(@Px int i2, @Px int i3, int i4, int i5) {
        this.mRadiusRectF = new RectF();
        this.mRadiusPath = new Path();
        this.mBannerManager.getBannerOptions().setRoundRectRadius(i2, i3, i4, i5);
        return this;
    }

    @Deprecated
    public BannerViewPager<T> setRoundRect(@Px int i2, @Px int i3, int i4, int i5) {
        return setRoundCorner(i2, i3, i4, i5);
    }

    public BannerViewPager(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mRunnable = new Runnable() { // from class: com.zhpan.bannerview.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f26556c.handlePosition();
            }
        };
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.zhpan.bannerview.BannerViewPager.1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i3) {
                super.onPageScrollStateChanged(i3);
                BannerViewPager.this.pageScrollStateChanged(i3);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int i3, float f2, int i4) {
                super.onPageScrolled(i3, f2, i4);
                BannerViewPager.this.pageScrolled(i3, f2, i4);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i3) {
                super.onPageSelected(i3);
                BannerViewPager.this.pageSelected(i3);
            }
        };
        init(context, attributeSet);
    }

    public void create() {
        create(new ArrayList());
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.mViewPager.addItemDecoration(itemDecoration);
    }
}
