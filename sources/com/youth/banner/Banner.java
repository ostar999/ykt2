package com.youth.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.exoplayer2.C;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.Indicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.MZScaleInTransformer;
import com.youth.banner.transformer.ScaleInTransformer;
import com.youth.banner.util.BannerLifecycleObserver;
import com.youth.banner.util.BannerLifecycleObserverAdapter;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.ScrollSpeedManger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes8.dex */
public class Banner<T, BA extends BannerAdapter<T, ? extends RecyclerView.ViewHolder>> extends FrameLayout implements BannerLifecycleObserver {
    public static final int HORIZONTAL = 0;
    public static final int INVALID_VALUE = -1;
    public static final int VERTICAL = 1;
    private int indicatorGravity;
    private int indicatorHeight;
    private int indicatorMargin;
    private int indicatorMarginBottom;
    private int indicatorMarginLeft;
    private int indicatorMarginRight;
    private int indicatorMarginTop;
    private int indicatorRadius;
    private int indicatorSpace;
    private boolean isIntercept;
    private BA mAdapter;
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver;
    private float mBannerRadius;
    private CompositePageTransformer mCompositePageTransformer;
    private Paint mImagePaint;
    private Indicator mIndicator;
    private boolean mIsAutoLoop;
    private boolean mIsInfiniteLoop;
    private boolean mIsViewPager2Drag;
    private AutoLoopTask mLoopTask;
    private long mLoopTime;
    private OnPageChangeListener mOnPageChangeListener;
    private int mOrientation;
    private Banner<T, BA>.BannerOnPageChangeCallback mPageChangeCallback;
    private boolean mRoundBottomLeft;
    private boolean mRoundBottomRight;
    private Paint mRoundPaint;
    private boolean mRoundTopLeft;
    private boolean mRoundTopRight;
    private int mScrollTime;
    private int mStartPosition;
    private float mStartX;
    private float mStartY;
    private int mTouchSlop;
    private ViewPager2 mViewPager2;
    private int normalColor;
    private int normalWidth;
    private int selectedColor;
    private int selectedWidth;

    public static class AutoLoopTask implements Runnable {
        private final WeakReference<Banner> reference;

        public AutoLoopTask(Banner banner) {
            this.reference = new WeakReference<>(banner);
        }

        @Override // java.lang.Runnable
        public void run() {
            int itemCount;
            Banner banner = this.reference.get();
            if (banner == null || !banner.mIsAutoLoop || (itemCount = banner.getItemCount()) == 0) {
                return;
            }
            banner.setCurrentItem((banner.getCurrentItem() + 1) % itemCount);
            banner.postDelayed(banner.mLoopTask, banner.mLoopTime);
        }
    }

    public class BannerOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private boolean isScrolled;
        private int mTempPosition = -1;

        public BannerOnPageChangeCallback() {
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(int i2) {
            if (i2 == 1 || i2 == 2) {
                this.isScrolled = true;
            } else if (i2 == 0) {
                this.isScrolled = false;
                if (this.mTempPosition != -1 && Banner.this.mIsInfiniteLoop) {
                    int i3 = this.mTempPosition;
                    if (i3 == 0) {
                        Banner banner = Banner.this;
                        banner.setCurrentItem(banner.getRealCount(), false);
                    } else if (i3 == Banner.this.getItemCount() - 1) {
                        Banner.this.setCurrentItem(1, false);
                    }
                }
            }
            if (Banner.this.mOnPageChangeListener != null) {
                Banner.this.mOnPageChangeListener.onPageScrollStateChanged(i2);
            }
            if (Banner.this.getIndicator() != null) {
                Banner.this.getIndicator().onPageScrollStateChanged(i2);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int i2, float f2, int i3) {
            int realPosition = BannerUtils.getRealPosition(Banner.this.isInfiniteLoop(), i2, Banner.this.getRealCount());
            if (Banner.this.mOnPageChangeListener != null && realPosition == Banner.this.getCurrentItem() - 1) {
                Banner.this.mOnPageChangeListener.onPageScrolled(realPosition, f2, i3);
            }
            if (Banner.this.getIndicator() == null || realPosition != Banner.this.getCurrentItem() - 1) {
                return;
            }
            Banner.this.getIndicator().onPageScrolled(realPosition, f2, i3);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int i2) {
            if (this.isScrolled) {
                this.mTempPosition = i2;
                int realPosition = BannerUtils.getRealPosition(Banner.this.isInfiniteLoop(), i2, Banner.this.getRealCount());
                if (Banner.this.mOnPageChangeListener != null) {
                    Banner.this.mOnPageChangeListener.onPageSelected(realPosition);
                }
                if (Banner.this.getIndicator() != null) {
                    Banner.this.getIndicator().onPageSelected(realPosition);
                }
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public Banner(Context context) {
        this(context, null);
    }

    private void drawBottomLeft(Canvas canvas) {
        int height = getHeight();
        Path path = new Path();
        float f2 = height;
        path.moveTo(0.0f, f2 - this.mBannerRadius);
        path.lineTo(0.0f, f2);
        path.lineTo(this.mBannerRadius, f2);
        float f3 = this.mBannerRadius;
        path.arcTo(new RectF(0.0f, f2 - (f3 * 2.0f), f3 * 2.0f, f2), 90.0f, 90.0f);
        path.close();
        canvas.drawPath(path, this.mRoundPaint);
    }

    private void drawBottomRight(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        Path path = new Path();
        float f2 = width;
        float f3 = height;
        path.moveTo(f2 - this.mBannerRadius, f3);
        path.lineTo(f2, f3);
        path.lineTo(f2, f3 - this.mBannerRadius);
        float f4 = this.mBannerRadius;
        path.arcTo(new RectF(f2 - (f4 * 2.0f), f3 - (f4 * 2.0f), f2, f3), 0.0f, 90.0f);
        path.close();
        canvas.drawPath(path, this.mRoundPaint);
    }

    private void drawTopLeft(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0.0f, this.mBannerRadius);
        path.lineTo(0.0f, 0.0f);
        path.lineTo(this.mBannerRadius, 0.0f);
        float f2 = this.mBannerRadius;
        path.arcTo(new RectF(0.0f, 0.0f, f2 * 2.0f, f2 * 2.0f), -90.0f, -90.0f);
        path.close();
        canvas.drawPath(path, this.mRoundPaint);
    }

    private void drawTopRight(Canvas canvas) {
        int width = getWidth();
        Path path = new Path();
        float f2 = width;
        path.moveTo(f2 - this.mBannerRadius, 0.0f);
        path.lineTo(f2, 0.0f);
        path.lineTo(f2, this.mBannerRadius);
        float f3 = this.mBannerRadius;
        path.arcTo(new RectF(f2 - (f3 * 2.0f), 0.0f, f2, f3 * 2.0f), 0.0f, -90.0f);
        path.close();
        canvas.drawPath(path, this.mRoundPaint);
    }

    private void init(Context context) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
        this.mCompositePageTransformer = new CompositePageTransformer();
        this.mPageChangeCallback = new BannerOnPageChangeCallback();
        this.mLoopTask = new AutoLoopTask(this);
        ViewPager2 viewPager2 = new ViewPager2(context);
        this.mViewPager2 = viewPager2;
        viewPager2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.mViewPager2.setOffscreenPageLimit(2);
        this.mViewPager2.registerOnPageChangeCallback(this.mPageChangeCallback);
        this.mViewPager2.setPageTransformer(this.mCompositePageTransformer);
        ScrollSpeedManger.reflectLayoutManager(this);
        addView(this.mViewPager2);
        Paint paint = new Paint();
        this.mRoundPaint = paint;
        paint.setColor(-1);
        this.mRoundPaint.setAntiAlias(true);
        this.mRoundPaint.setStyle(Paint.Style.FILL);
        this.mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Paint paint2 = new Paint();
        this.mImagePaint = paint2;
        paint2.setXfermode(null);
    }

    private void initIndicator() {
        if (getIndicator() == null || getAdapter() == null) {
            return;
        }
        if (getIndicator().getIndicatorConfig().isAttachToBanner()) {
            removeIndicator();
            addView(getIndicator().getIndicatorView());
        }
        initIndicatorAttr();
        setIndicatorPageChange();
    }

    private void initIndicatorAttr() {
        int i2 = this.indicatorMargin;
        if (i2 != 0) {
            setIndicatorMargins(new IndicatorConfig.Margins(i2));
        } else {
            int i3 = this.indicatorMarginLeft;
            if (i3 != 0 || this.indicatorMarginTop != 0 || this.indicatorMarginRight != 0 || this.indicatorMarginBottom != 0) {
                setIndicatorMargins(new IndicatorConfig.Margins(i3, this.indicatorMarginTop, this.indicatorMarginRight, this.indicatorMarginBottom));
            }
        }
        int i4 = this.indicatorSpace;
        if (i4 > 0) {
            setIndicatorSpace(i4);
        }
        int i5 = this.indicatorGravity;
        if (i5 != 1) {
            setIndicatorGravity(i5);
        }
        int i6 = this.normalWidth;
        if (i6 > 0) {
            setIndicatorNormalWidth(i6);
        }
        int i7 = this.selectedWidth;
        if (i7 > 0) {
            setIndicatorSelectedWidth(i7);
        }
        int i8 = this.indicatorHeight;
        if (i8 > 0) {
            setIndicatorHeight(i8);
        }
        int i9 = this.indicatorRadius;
        if (i9 > 0) {
            setIndicatorRadius(i9);
        }
        setIndicatorNormalColor(this.normalColor);
        setIndicatorSelectedColor(this.selectedColor);
    }

    private void initTypedArray(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Banner);
            this.mBannerRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_radius, 0);
            this.mLoopTime = typedArrayObtainStyledAttributes.getInt(R.styleable.Banner_banner_loop_time, 3000);
            this.mIsAutoLoop = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Banner_banner_auto_loop, true);
            this.mIsInfiniteLoop = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Banner_banner_infinite_loop, true);
            this.normalWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_normal_width, BannerConfig.INDICATOR_NORMAL_WIDTH);
            this.selectedWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_selected_width, BannerConfig.INDICATOR_SELECTED_WIDTH);
            this.normalColor = typedArrayObtainStyledAttributes.getColor(R.styleable.Banner_banner_indicator_normal_color, BannerConfig.INDICATOR_NORMAL_COLOR);
            this.selectedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.Banner_banner_indicator_selected_color, BannerConfig.INDICATOR_SELECTED_COLOR);
            this.indicatorGravity = typedArrayObtainStyledAttributes.getInt(R.styleable.Banner_banner_indicator_gravity, 1);
            this.indicatorSpace = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_space, 0);
            this.indicatorMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_margin, 0);
            this.indicatorMarginLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginLeft, 0);
            this.indicatorMarginTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginTop, 0);
            this.indicatorMarginRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginRight, 0);
            this.indicatorMarginBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginBottom, 0);
            this.indicatorHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_height, BannerConfig.INDICATOR_HEIGHT);
            this.indicatorRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Banner_banner_indicator_radius, BannerConfig.INDICATOR_RADIUS);
            this.mOrientation = typedArrayObtainStyledAttributes.getInt(R.styleable.Banner_banner_orientation, 0);
            this.mRoundTopLeft = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Banner_banner_round_top_left, false);
            this.mRoundTopRight = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Banner_banner_round_top_right, false);
            this.mRoundBottomLeft = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Banner_banner_round_bottom_left, false);
            this.mRoundBottomRight = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Banner_banner_round_bottom_right, false);
            typedArrayObtainStyledAttributes.recycle();
        }
        setOrientation(this.mOrientation);
        setInfiniteLoop();
    }

    private void setInfiniteLoop() {
        if (!isInfiniteLoop()) {
            isAutoLoop(false);
        }
        setStartPosition(isInfiniteLoop() ? this.mStartPosition : 0);
    }

    private void setRecyclerViewPadding(int i2) {
        setRecyclerViewPadding(i2, i2);
    }

    public Banner addBannerLifecycleObserver(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().addObserver(new BannerLifecycleObserverAdapter(lifecycleOwner, this));
        }
        return this;
    }

    public Banner addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        getViewPager2().addItemDecoration(itemDecoration);
        return this;
    }

    public Banner addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
        return this;
    }

    public Banner addPageTransformer(@Nullable ViewPager2.PageTransformer pageTransformer) {
        this.mCompositePageTransformer.addTransformer(pageTransformer);
        return this;
    }

    public void destroy() {
        if (getViewPager2() != null && this.mPageChangeCallback != null) {
            getViewPager2().unregisterOnPageChangeCallback(this.mPageChangeCallback);
            this.mPageChangeCallback = null;
        }
        stop();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (this.mBannerRadius <= 0.0f) {
            super.dispatchDraw(canvas);
            return;
        }
        canvas.saveLayer(new RectF(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight()), this.mImagePaint, 31);
        super.dispatchDraw(canvas);
        if (!this.mRoundTopRight && !this.mRoundTopLeft && !this.mRoundBottomRight && !this.mRoundBottomLeft) {
            drawTopLeft(canvas);
            drawTopRight(canvas);
            drawBottomLeft(canvas);
            drawBottomRight(canvas);
            canvas.restore();
            return;
        }
        if (this.mRoundTopLeft) {
            drawTopLeft(canvas);
        }
        if (this.mRoundTopRight) {
            drawTopRight(canvas);
        }
        if (this.mRoundBottomLeft) {
            drawBottomLeft(canvas);
        }
        if (this.mRoundBottomRight) {
            drawBottomRight(canvas);
        }
        canvas.restore();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!getViewPager2().isUserInputEnabled()) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3 || actionMasked == 4) {
            start();
        } else if (actionMasked == 0) {
            stop();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public BannerAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getCurrentItem() {
        return getViewPager2().getCurrentItem();
    }

    public Indicator getIndicator() {
        return this.mIndicator;
    }

    public IndicatorConfig getIndicatorConfig() {
        if (getIndicator() != null) {
            return getIndicator().getIndicatorConfig();
        }
        return null;
    }

    public int getItemCount() {
        if (getAdapter() != null) {
            return getAdapter().getItemCount();
        }
        return 0;
    }

    public int getRealCount() {
        if (getAdapter() != null) {
            return getAdapter().getRealCount();
        }
        return 0;
    }

    public int getScrollTime() {
        return this.mScrollTime;
    }

    public int getStartPosition() {
        return this.mStartPosition;
    }

    public ViewPager2 getViewPager2() {
        return this.mViewPager2;
    }

    public Banner isAutoLoop(boolean z2) {
        this.mIsAutoLoop = z2;
        return this;
    }

    public boolean isInfiniteLoop() {
        return this.mIsInfiniteLoop;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override // com.youth.banner.util.BannerLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        destroy();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006a  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            androidx.viewpager2.widget.ViewPager2 r0 = r5.getViewPager2()
            boolean r0 = r0.isUserInputEnabled()
            if (r0 == 0) goto L8a
            boolean r0 = r5.isIntercept
            if (r0 != 0) goto L10
            goto L8a
        L10:
            int r0 = r6.getAction()
            r1 = 1
            if (r0 == 0) goto L72
            r2 = 0
            if (r0 == r1) goto L6a
            r3 = 2
            if (r0 == r3) goto L21
            r1 = 3
            if (r0 == r1) goto L6a
            goto L85
        L21:
            float r0 = r6.getX()
            float r3 = r6.getY()
            float r4 = r5.mStartX
            float r0 = r0 - r4
            float r0 = java.lang.Math.abs(r0)
            float r4 = r5.mStartY
            float r3 = r3 - r4
            float r3 = java.lang.Math.abs(r3)
            androidx.viewpager2.widget.ViewPager2 r4 = r5.getViewPager2()
            int r4 = r4.getOrientation()
            if (r4 != 0) goto L51
            int r4 = r5.mTouchSlop
            float r4 = (float) r4
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L4d
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 <= 0) goto L4d
            goto L4e
        L4d:
            r1 = r2
        L4e:
            r5.mIsViewPager2Drag = r1
            goto L60
        L51:
            int r4 = r5.mTouchSlop
            float r4 = (float) r4
            int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r4 <= 0) goto L5d
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L5d
            goto L5e
        L5d:
            r1 = r2
        L5e:
            r5.mIsViewPager2Drag = r1
        L60:
            android.view.ViewParent r0 = r5.getParent()
            boolean r1 = r5.mIsViewPager2Drag
            r0.requestDisallowInterceptTouchEvent(r1)
            goto L85
        L6a:
            android.view.ViewParent r0 = r5.getParent()
            r0.requestDisallowInterceptTouchEvent(r2)
            goto L85
        L72:
            float r0 = r6.getX()
            r5.mStartX = r0
            float r0 = r6.getY()
            r5.mStartY = r0
            android.view.ViewParent r0 = r5.getParent()
            r0.requestDisallowInterceptTouchEvent(r1)
        L85:
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        L8a:
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.youth.banner.Banner.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.youth.banner.util.BannerLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        start();
    }

    @Override // com.youth.banner.util.BannerLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        stop();
    }

    public Banner removeIndicator() {
        if (getIndicator() != null) {
            removeView(getIndicator().getIndicatorView());
        }
        return this;
    }

    public Banner removeTransformer(ViewPager2.PageTransformer pageTransformer) {
        this.mCompositePageTransformer.removeTransformer(pageTransformer);
        return this;
    }

    public Banner setAdapter(BA ba) {
        if (ba == null) {
            throw new NullPointerException(getContext().getString(R.string.banner_adapter_null_error));
        }
        this.mAdapter = ba;
        if (!isInfiniteLoop()) {
            getAdapter().setIncreaseCount(0);
        }
        getAdapter().registerAdapterDataObserver(this.mAdapterDataObserver);
        this.mViewPager2.setAdapter(ba);
        setCurrentItem(this.mStartPosition, false);
        initIndicator();
        return this;
    }

    public Banner setBannerGalleryEffect(int i2, int i3) {
        return setBannerGalleryEffect(i2, i3, 0.85f);
    }

    public Banner setBannerGalleryMZ(int i2) {
        return setBannerGalleryMZ(i2, 0.88f);
    }

    public Banner setBannerRound(float f2) {
        this.mBannerRadius = f2;
        return this;
    }

    @RequiresApi(api = 21)
    public Banner setBannerRound2(float f2) {
        BannerUtils.setBannerRound(this, f2);
        return this;
    }

    public Banner setCurrentItem(int i2) {
        return setCurrentItem(i2, true);
    }

    public Banner setDatas(List<T> list) {
        if (getAdapter() != null) {
            getAdapter().setDatas(list);
            setCurrentItem(this.mStartPosition, false);
            setIndicatorPageChange();
            start();
        }
        return this;
    }

    public Banner setIndicator(Indicator indicator) {
        return setIndicator(indicator, true);
    }

    public Banner setIndicatorGravity(int i2) {
        if (getIndicatorConfig() != null && getIndicatorConfig().isAttachToBanner()) {
            getIndicatorConfig().setGravity(i2);
            getIndicator().getIndicatorView().postInvalidate();
        }
        return this;
    }

    public Banner setIndicatorHeight(int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setHeight(i2);
        }
        return this;
    }

    public Banner setIndicatorMargins(IndicatorConfig.Margins margins) {
        if (getIndicatorConfig() != null && getIndicatorConfig().isAttachToBanner()) {
            getIndicatorConfig().setMargins(margins);
            getIndicator().getIndicatorView().requestLayout();
        }
        return this;
    }

    public Banner setIndicatorNormalColor(@ColorInt int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setNormalColor(i2);
        }
        return this;
    }

    public Banner setIndicatorNormalColorRes(@ColorRes int i2) {
        setIndicatorNormalColor(ContextCompat.getColor(getContext(), i2));
        return this;
    }

    public Banner setIndicatorNormalWidth(int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setNormalWidth(i2);
        }
        return this;
    }

    public Banner setIndicatorPageChange() {
        if (getIndicator() != null) {
            getIndicator().onPageChanged(getRealCount(), BannerUtils.getRealPosition(isInfiniteLoop(), getCurrentItem(), getRealCount()));
        }
        return this;
    }

    public Banner setIndicatorRadius(int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setRadius(i2);
        }
        return this;
    }

    public Banner setIndicatorSelectedColor(@ColorInt int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setSelectedColor(i2);
        }
        return this;
    }

    public Banner setIndicatorSelectedColorRes(@ColorRes int i2) {
        setIndicatorSelectedColor(ContextCompat.getColor(getContext(), i2));
        return this;
    }

    public Banner setIndicatorSelectedWidth(int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setSelectedWidth(i2);
        }
        return this;
    }

    public Banner setIndicatorSpace(int i2) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setIndicatorSpace(i2);
        }
        return this;
    }

    public Banner setIndicatorWidth(int i2, int i3) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setNormalWidth(i2);
            getIndicatorConfig().setSelectedWidth(i3);
        }
        return this;
    }

    public Banner setIntercept(boolean z2) {
        this.isIntercept = z2;
        return this;
    }

    public Banner setLoopTime(long j2) {
        this.mLoopTime = j2;
        return this;
    }

    public Banner setOnBannerListener(OnBannerListener<T> onBannerListener) {
        if (getAdapter() != null) {
            getAdapter().setOnBannerListener(onBannerListener);
        }
        return this;
    }

    public Banner setOrientation(int i2) {
        getViewPager2().setOrientation(i2);
        return this;
    }

    public Banner setPageTransformer(@Nullable ViewPager2.PageTransformer pageTransformer) {
        getViewPager2().setPageTransformer(pageTransformer);
        return this;
    }

    public Banner setScrollTime(int i2) {
        this.mScrollTime = i2;
        return this;
    }

    public Banner setStartPosition(int i2) {
        this.mStartPosition = i2;
        return this;
    }

    public Banner setTouchSlop(int i2) {
        this.mTouchSlop = i2;
        return this;
    }

    public Banner setUserInputEnabled(boolean z2) {
        getViewPager2().setUserInputEnabled(z2);
        return this;
    }

    public Banner start() {
        if (this.mIsAutoLoop) {
            stop();
            postDelayed(this.mLoopTask, this.mLoopTime);
        }
        return this;
    }

    public Banner stop() {
        if (this.mIsAutoLoop) {
            removeCallbacks(this.mLoopTask);
        }
        return this;
    }

    public Banner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void setRecyclerViewPadding(int i2, int i3) {
        RecyclerView recyclerView = (RecyclerView) getViewPager2().getChildAt(0);
        if (getViewPager2().getOrientation() == 1) {
            recyclerView.setPadding(this.mViewPager2.getPaddingLeft(), i2, this.mViewPager2.getPaddingRight(), i3);
        } else {
            recyclerView.setPadding(i2, this.mViewPager2.getPaddingTop(), i3, this.mViewPager2.getPaddingBottom());
        }
        recyclerView.setClipToPadding(false);
    }

    public Banner addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int i2) {
        getViewPager2().addItemDecoration(itemDecoration, i2);
        return this;
    }

    public Banner setBannerGalleryEffect(int i2, int i3, int i4) {
        return setBannerGalleryEffect(i2, i3, i4, 0.85f);
    }

    public Banner setBannerGalleryMZ(int i2, float f2) {
        if (f2 < 1.0f && f2 > 0.0f) {
            addPageTransformer(new MZScaleInTransformer(f2));
        }
        setRecyclerViewPadding(BannerUtils.dp2px(i2));
        return this;
    }

    public Banner setCurrentItem(int i2, boolean z2) {
        getViewPager2().setCurrentItem(i2, z2);
        return this;
    }

    public Banner setIndicator(Indicator indicator, boolean z2) {
        removeIndicator();
        indicator.getIndicatorConfig().setAttachToBanner(z2);
        this.mIndicator = indicator;
        initIndicator();
        return this;
    }

    public Banner(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mIsInfiniteLoop = true;
        this.mIsAutoLoop = true;
        this.mLoopTime = C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
        this.mScrollTime = 600;
        this.mStartPosition = 1;
        this.mBannerRadius = 0.0f;
        this.normalWidth = BannerConfig.INDICATOR_NORMAL_WIDTH;
        this.selectedWidth = BannerConfig.INDICATOR_SELECTED_WIDTH;
        this.normalColor = BannerConfig.INDICATOR_NORMAL_COLOR;
        this.selectedColor = BannerConfig.INDICATOR_SELECTED_COLOR;
        this.indicatorGravity = 1;
        this.indicatorHeight = BannerConfig.INDICATOR_HEIGHT;
        this.indicatorRadius = BannerConfig.INDICATOR_RADIUS;
        this.mOrientation = 0;
        this.isIntercept = true;
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.youth.banner.Banner.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                if (Banner.this.getItemCount() <= 1) {
                    Banner.this.stop();
                } else {
                    Banner.this.start();
                }
                Banner.this.setIndicatorPageChange();
            }
        };
        init(context);
        initTypedArray(context, attributeSet);
    }

    public Banner setBannerGalleryEffect(int i2, int i3, float f2) {
        return setBannerGalleryEffect(i2, i2, i3, f2);
    }

    public Banner setBannerGalleryEffect(int i2, int i3, int i4, float f2) {
        if (i4 > 0) {
            addPageTransformer(new MarginPageTransformer(BannerUtils.dp2px(i4)));
        }
        if (f2 < 1.0f && f2 > 0.0f) {
            addPageTransformer(new ScaleInTransformer(f2));
        }
        setRecyclerViewPadding(i2 > 0 ? BannerUtils.dp2px(i2 + i4) : 0, i3 > 0 ? BannerUtils.dp2px(i3 + i4) : 0);
        return this;
    }

    public Banner setAdapter(BA ba, boolean z2) {
        this.mIsInfiniteLoop = z2;
        setInfiniteLoop();
        setAdapter(ba);
        return this;
    }
}
