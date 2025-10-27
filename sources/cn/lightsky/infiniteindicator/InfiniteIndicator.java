package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.lightsky.infiniteindicator.indicator.PageIndicator;
import cn.lightsky.infiniteindicator.recycle.RecyclingPagerAdapter;
import cn.lightsky.infiniteindicator.recycle.RecyleAdapter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;

/* loaded from: classes.dex */
public class InfiniteIndicator extends RelativeLayout implements RecyclingPagerAdapter.DataChangeListener, ViewPager.OnPageChangeListener {
    public static final int MSG_SCROLL = 1000;
    public static final int PAGE_COUNT_FACTOR = 100;
    private IndicatorConfiguration configuration;
    private float downX;
    private final ScrollHandler handler;
    private boolean isScrolling;
    private boolean isStopByTouch;
    private Context mContext;
    private PageIndicator mIndicator;
    private RecyleAdapter mRecyleAdapter;
    private ViewPager mViewPager;
    private DurationScroller scroller;
    private int theme;
    private float touchX;

    public static class ScrollHandler extends Handler {
        public WeakReference<InfiniteIndicator> mWeakReference;

        public ScrollHandler(InfiniteIndicator infiniteIndicator) {
            this.mWeakReference = new WeakReference<>(infiniteIndicator);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Resources.NotFoundException {
            super.handleMessage(message);
            InfiniteIndicator infiniteIndicator = this.mWeakReference.get();
            if (infiniteIndicator == null || message.what != 1000) {
                return;
            }
            infiniteIndicator.scrollOnce();
            infiniteIndicator.sendScrollMessage();
        }
    }

    public InfiniteIndicator(Context context) {
        this(context, null);
    }

    private int getIndex(int i2) {
        return (((getRealCount() * 100) / 2) - (((getRealCount() * 100) / 2) % getRealCount())) + i2;
    }

    private int getRealCount() {
        return this.mRecyleAdapter.getRealCount();
    }

    private int getRealPosition(int i2) {
        return this.mRecyleAdapter.getRealPosition(i2);
    }

    private void scrollToIndex(int i2) throws Resources.NotFoundException {
        if (!this.configuration.isLoop() || getRealCount() <= 1) {
            this.mViewPager.setCurrentItem(i2);
        } else {
            this.mViewPager.setCurrentItem(getIndex(i2));
        }
        PageIndicator pageIndicator = this.mIndicator;
        if (pageIndicator != null) {
            pageIndicator.setCurrentItem(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendScrollMessage() {
        sendScrollMessage(this.configuration.getInterval());
    }

    private void setScroller() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            Field declaredField2 = ViewPager.class.getDeclaredField("sInterpolator");
            declaredField2.setAccessible(true);
            DurationScroller durationScroller = new DurationScroller(getContext(), (Interpolator) declaredField2.get(null));
            this.scroller = durationScroller;
            declaredField.set(this.mViewPager, durationScroller);
            this.scroller.setScrollDurationFactor(this.configuration.getScrollFactor());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.configuration == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (this.configuration.isStopWhenTouch()) {
            if (actionMasked == 0 && this.isScrolling) {
                this.isStopByTouch = true;
                stop();
            } else if (motionEvent.getAction() == 1 && this.isStopByTouch) {
                start();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public PageIndicator getPagerIndicator() {
        return this.mIndicator;
    }

    public void init(IndicatorConfiguration indicatorConfiguration) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        this.configuration = indicatorConfiguration;
        this.mRecyleAdapter = new RecyleAdapter(this.mContext, indicatorConfiguration.getViewBinder(), indicatorConfiguration.getOnPageClickListener());
        this.theme = indicatorConfiguration.getmThemeSkin();
        this.mRecyleAdapter.setmTheme(indicatorConfiguration.getmThemeSkin());
        this.mRecyleAdapter.setDataChangeListener(this);
        this.mRecyleAdapter.setImageLoader(indicatorConfiguration.getImageLoader());
        this.mViewPager.setAdapter(this.mRecyleAdapter);
        this.mViewPager.addOnPageChangeListener(this);
        this.mRecyleAdapter.setIsLoop(indicatorConfiguration.isLoop());
        setScroller();
        initIndicator();
    }

    public void initIndicator() {
        if (this.configuration.isDrawIndicator()) {
            PageIndicator pageIndicator = (PageIndicator) findViewById(this.configuration.getPageIndicator().getResourceId());
            this.mIndicator = pageIndicator;
            pageIndicator.setViewPager(this.mViewPager, this.theme);
        }
    }

    public void notifyDataChange(List<Page> list) throws Resources.NotFoundException {
        if (list != null && !list.isEmpty()) {
            this.mRecyleAdapter.setPages(list);
        }
        scrollToIndex(0);
        if (this.configuration.isAutoScroll()) {
            start();
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i2) {
        PageIndicator pageIndicator = this.mIndicator;
        if (pageIndicator != null) {
            pageIndicator.onPageScrollStateChanged(i2);
        }
        if (this.configuration.getOnPageChangeListener() != null) {
            this.configuration.getOnPageChangeListener().onPageScrollStateChanged(i2);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i2, float f2, int i3) {
        PageIndicator pageIndicator = this.mIndicator;
        if (pageIndicator != null) {
            pageIndicator.onPageScrolled(getRealPosition(i2), f2, i3);
        }
        if (this.configuration.getOnPageChangeListener() != null) {
            this.configuration.getOnPageChangeListener().onPageScrolled(getRealPosition(i2), f2, i3);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i2) {
        PageIndicator pageIndicator = this.mIndicator;
        if (pageIndicator != null) {
            pageIndicator.onPageSelected(getRealPosition(i2));
        }
        if (this.configuration.getOnPageChangeListener() != null) {
            this.configuration.getOnPageChangeListener().onPageSelected(getRealPosition(i2));
        }
    }

    public void scrollOnce() throws Resources.NotFoundException {
        int size;
        PagerAdapter adapter = this.mViewPager.getAdapter();
        int currentItem = this.mViewPager.getCurrentItem();
        if (adapter == null || (size = adapter.getSize()) <= 1) {
            return;
        }
        int i2 = this.configuration.getDirection() == 0 ? currentItem - 1 : currentItem + 1;
        if (i2 < 0) {
            if (this.configuration.isLoop()) {
                this.mViewPager.setCurrentItem(size - 1);
            }
        } else if (i2 != size) {
            this.mViewPager.setCurrentItem(i2, true);
        } else if (this.configuration.isLoop()) {
            this.mViewPager.setCurrentItem(0);
        }
    }

    public void setCurrentItem(int i2) throws Resources.NotFoundException {
        if (i2 <= getRealCount() - 1) {
            scrollToIndex(i2);
            return;
        }
        throw new IndexOutOfBoundsException("index is " + i2 + "current list size is " + getRealCount());
    }

    public void start() {
        start(this.configuration.getInterval());
    }

    public void stop() {
        this.isScrolling = false;
        this.handler.removeMessages(1000);
    }

    public InfiniteIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void sendScrollMessage(long j2) {
        this.handler.removeMessages(1000);
        this.handler.sendEmptyMessageDelayed(1000, j2);
    }

    public void start(long j2) {
        if (this.configuration == null) {
            throw new RuntimeException("You should init a configuration first");
        }
        if (getRealCount() <= 1 || this.isScrolling || !this.configuration.isLoop()) {
            return;
        }
        this.isScrolling = true;
        sendScrollMessage(j2);
    }

    public InfiniteIndicator(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.downX = 0.0f;
        this.touchX = 0.0f;
        this.theme = 0;
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.InfiniteIndicator, 0, 0);
        int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.InfiniteIndicator_indicator_type, 0);
        if (i3 == 0) {
            LayoutInflater.from(context).inflate(R.layout.layout_default_indicator, (ViewGroup) this, true);
        } else if (i3 == 1) {
            LayoutInflater.from(context).inflate(R.layout.layout_anim_circle_indicator, (ViewGroup) this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.layout_anim_line_indicator, (ViewGroup) this, true);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);
        this.handler = new ScrollHandler(this);
    }

    @Override // cn.lightsky.infiniteindicator.recycle.RecyclingPagerAdapter.DataChangeListener
    public void notifyDataChange() {
        PageIndicator pageIndicator = this.mIndicator;
        if (pageIndicator != null) {
            pageIndicator.notifyDataSetChanged();
        }
    }
}
