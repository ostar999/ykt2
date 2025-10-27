package cn.lightsky.infiniteindicator;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import cn.lightsky.infiniteindicator.recycle.BaseViewBinder;
import cn.lightsky.infiniteindicator.recycle.ViewBinder;

/* loaded from: classes.dex */
public class IndicatorConfiguration {
    public static final int DEFAULT_INTERVAL = 50000;
    public static final double DEFAULT_SCROLL_FACTOR = 1.0d;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private int direction;
    private final ImageLoader imageLoader;
    private View indicator;
    private long interval;
    private boolean isAutoScroll;
    private boolean isDrawIndicator;
    private boolean isLoop;
    private boolean isStopScrollWhenTouch;
    private final OnPageClickListener mOnPageClickListener;
    private int mThemeSkin;
    private final ViewPager.OnPageChangeListener onPageChangeListener;
    private final IndicatorPosition presentIndicator;
    private double scrollFactor;
    private final ViewBinder viewBinder;

    public static class Builder {
        private ImageLoader imageLoader;
        private View indicator;
        private ViewPager.OnPageChangeListener onPageChangeListener;
        private OnPageClickListener onPageClickListener;
        private boolean isLoop = true;
        private boolean isAutoScroll = true;
        private boolean isStopWhileTouch = true;
        private boolean isDrawIndicator = true;
        private ViewBinder viewBinder = new BaseViewBinder();
        private int direction = 1;
        private long interval = 50000;
        private double scrollFactor = 1.0d;
        private IndicatorPosition indicatorPosition = IndicatorPosition.Center_Bottom;
        private int mThemeSkin = 0;

        private Builder indicator(View view) {
            this.indicator = view;
            return this;
        }

        public IndicatorConfiguration build() {
            return new IndicatorConfiguration(this);
        }

        public Builder direction(int i2) {
            this.direction = i2;
            return this;
        }

        public Builder imageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
            return this;
        }

        public Builder internal(long j2) {
            this.interval = j2;
            return this;
        }

        public Builder isAutoScroll(boolean z2) {
            this.isAutoScroll = z2;
            return this;
        }

        public Builder isDrawIndicator(boolean z2) {
            this.isDrawIndicator = z2;
            return this;
        }

        public Builder isLoop(boolean z2) {
            this.isLoop = z2;
            return this;
        }

        public Builder isStopWhileTouch(boolean z2) {
            this.isStopWhileTouch = z2;
            return this;
        }

        public Builder mThemeSkin(int i2) {
            this.mThemeSkin = i2;
            return this;
        }

        public Builder onPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
            this.onPageChangeListener = onPageChangeListener;
            return this;
        }

        public Builder onPageClickListener(OnPageClickListener onPageClickListener) {
            this.onPageClickListener = onPageClickListener;
            return this;
        }

        public Builder position(IndicatorPosition indicatorPosition) {
            this.indicatorPosition = indicatorPosition;
            return this;
        }

        public Builder scrollDurationFactor(double d3) {
            this.scrollFactor = d3;
            return this;
        }

        public Builder viewBinder(ViewBinder viewBinder) {
            this.viewBinder = viewBinder;
            return this;
        }
    }

    public enum IndicatorPosition {
        Center("Center_Bottom", R.id.default_center_indicator),
        Center_Bottom("Center_Bottom", R.id.default_center_bottom_indicator),
        Right_Bottom("Right_Bottom", R.id.default_bottom_right_indicator),
        Left_Bottom("Left_Bottom", R.id.default_bottom_left_indicator),
        Center_Top("Center_Top", R.id.default_center_top_indicator),
        Right_Top("Right_Top", R.id.default_center_top_right_indicator),
        Left_Top("Left_Top", R.id.default_center_top_left_indicator);

        private final int id;
        private final String name;

        IndicatorPosition(String str, int i2) {
            this.name = str;
            this.id = i2;
        }

        public int getResourceId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }
    }

    public int getDirection() {
        return this.direction;
    }

    public ImageLoader getImageLoader() {
        ImageLoader imageLoader = this.imageLoader;
        if (imageLoader != null) {
            return imageLoader;
        }
        throw new RuntimeException("You should set ImageLoader first");
    }

    public long getInterval() {
        return this.interval;
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return this.onPageChangeListener;
    }

    public OnPageClickListener getOnPageClickListener() {
        return this.mOnPageClickListener;
    }

    public IndicatorPosition getPageIndicator() {
        return this.presentIndicator;
    }

    public double getScrollFactor() {
        return this.scrollFactor;
    }

    public ViewBinder getViewBinder() {
        return this.viewBinder;
    }

    public int getmThemeSkin() {
        return this.mThemeSkin;
    }

    public boolean isAutoScroll() {
        return this.isAutoScroll;
    }

    public boolean isDrawIndicator() {
        return this.isDrawIndicator;
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    public boolean isStopWhenTouch() {
        return this.isStopScrollWhenTouch;
    }

    private IndicatorConfiguration(Builder builder) {
        this.mThemeSkin = 0;
        this.imageLoader = builder.imageLoader;
        this.isLoop = builder.isLoop;
        this.interval = builder.interval;
        this.direction = builder.direction;
        this.mThemeSkin = builder.mThemeSkin;
        this.isDrawIndicator = builder.isDrawIndicator;
        this.isAutoScroll = builder.isAutoScroll;
        this.scrollFactor = builder.scrollFactor;
        this.presentIndicator = builder.indicatorPosition;
        this.indicator = builder.indicator;
        this.viewBinder = builder.viewBinder;
        this.mOnPageClickListener = builder.onPageClickListener;
        this.isStopScrollWhenTouch = builder.isStopWhileTouch;
        this.onPageChangeListener = builder.onPageChangeListener;
    }
}
