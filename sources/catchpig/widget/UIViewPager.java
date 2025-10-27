package catchpig.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;
import catchpig.widget.ImagePagerAdapter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class UIViewPager extends FrameLayout implements ViewPager.OnPageChangeListener, LifecycleObserver {
    private static final int VIEWPAGER_CHANGE = 100;
    private boolean mAutoPlay;
    private int mCount;
    private long mDelayTime;
    private ScheduledExecutorService mExecutorService;
    private PagerHandler mHandler;
    private ImagePagerAdapter mImagePagerAdapter;
    private int mIndicatorStyle;
    private boolean mInfiniteLoop;
    private RadioGroup mRadioGroup;
    private ViewPagerScroller mScroller;
    private RunnableScheduledFuture mTimer;
    private int mTranslationSpeed;
    private ViewPager mViewPager;

    public static class PagerHandler extends Handler {
        private UIViewPager mPager;

        public PagerHandler(UIViewPager pager) {
            this.mPager = pager;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
            UIViewPager uIViewPager;
            if (msg.what != 100 || (uIViewPager = this.mPager) == null) {
                return;
            }
            uIViewPager.playNext();
        }
    }

    public UIViewPager(@NonNull Context context) {
        this(context, null);
    }

    private int dpToPxInt(float dp) {
        return (int) ((dp * getResources().getDisplayMetrics().density) + 0.5d);
    }

    private void initRadioButton() {
        this.mRadioGroup.removeAllViews();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setPadding(dpToPxInt(5.0f), 0, 0, 0);
            if (i2 == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
            }
            imageView.setImageResource(this.mIndicatorStyle);
            this.mRadioGroup.addView(imageView, -2, -2);
        }
    }

    private synchronized void setCurrentRadio(int position) {
        int i2 = 0;
        while (i2 < this.mCount) {
            boolean zIsEnabled = this.mRadioGroup.getChildAt(i2).isEnabled();
            boolean z2 = position == i2;
            if (z2 || zIsEnabled) {
                ImageView imageView = new ImageView(getContext());
                if (z2) {
                    imageView.setEnabled(true);
                }
                if (zIsEnabled) {
                    imageView.setEnabled(false);
                }
                this.mRadioGroup.removeViewAt(i2);
                imageView.setImageResource(this.mIndicatorStyle);
                imageView.setPadding(dpToPxInt(5.0f), 0, 0, 0);
                this.mRadioGroup.addView(imageView, i2, new ViewGroup.LayoutParams(-2, -2));
            }
            i2++;
        }
    }

    private void startTimer() {
        if (this.mAutoPlay && this.mDelayTime > 0) {
            RunnableScheduledFuture runnableScheduledFuture = this.mTimer;
            if (runnableScheduledFuture == null || !runnableScheduledFuture.isPeriodic()) {
                ScheduledExecutorService scheduledExecutorService = this.mExecutorService;
                Runnable runnable = new Runnable() { // from class: catchpig.widget.UIViewPager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        UIViewPager.this.mHandler.sendEmptyMessage(100);
                    }
                };
                long j2 = this.mDelayTime;
                this.mTimer = (RunnableScheduledFuture) scheduledExecutorService.scheduleAtFixedRate(runnable, j2, j2, TimeUnit.SECONDS);
            }
        }
    }

    public void bindLifecycle(Fragment fragment) {
        fragment.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.mExecutorService.shutdown();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        setCurrentRadio(position % this.mCount);
    }

    public void playNext() throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        int currentItem = this.mViewPager.getCurrentItem() + 1;
        int i2 = currentItem <= this.mImagePagerAdapter.getSize() ? currentItem : 1;
        this.mScroller.setScrollDuration(this.mTranslationSpeed);
        this.mScroller.initViewPagerScroll(this.mViewPager);
        this.mViewPager.setCurrentItem(i2);
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setDelayTime(@IntRange(from = 1) long delayTime) {
        this.mDelayTime = delayTime;
        if (delayTime > 0) {
            setAutoPlay(true);
        }
    }

    public void setImageLoader(ImagePagerAdapter.ImageLoader imageLoader) {
        this.mImagePagerAdapter.setImageLoader(imageLoader);
    }

    public void setImages(List<String> images) {
        this.mImagePagerAdapter.setImages(images);
        this.mCount = images.size();
    }

    public void setIndicatorStyle(@DrawableRes int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        this.mInfiniteLoop = infiniteLoop;
    }

    public void setOffscreenPageLimit(int limit) throws Resources.NotFoundException {
        this.mViewPager.setOffscreenPageLimit(limit);
    }

    public void setOnItemClickListener(ImagePagerAdapter.OnItemClickListener listener) {
        this.mImagePagerAdapter.setOnItemClickListener(listener);
    }

    public void setTranslationSpeed(int translationSpeed) {
        this.mTranslationSpeed = translationSpeed;
    }

    public void start() throws Resources.NotFoundException {
        this.mImagePagerAdapter.setInfiniteLoop(this.mInfiniteLoop);
        this.mViewPager.setAdapter(this.mImagePagerAdapter);
        int i2 = this.mCount;
        if (i2 < 4) {
            this.mViewPager.setOffscreenPageLimit(i2);
        } else {
            this.mViewPager.setOffscreenPageLimit(4);
        }
        initRadioButton();
        startTimer();
    }

    public UIViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void bindLifecycle(Context context) {
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this);
        }
    }

    public UIViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.mTranslationSpeed = 1000;
        this.mAutoPlay = false;
        this.mDelayTime = 3L;
        this.mInfiniteLoop = false;
        this.mIndicatorStyle = R.drawable.carousel_bg;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.widget_uiviewpager, (ViewGroup) this, true);
        this.mScroller = new ViewPagerScroller(context);
        this.mViewPager = (ViewPager) viewInflate.findViewById(R.id.viewPager);
        this.mRadioGroup = (RadioGroup) viewInflate.findViewById(R.id.group);
        this.mHandler = new PagerHandler(this);
        this.mViewPager.addOnPageChangeListener(this);
        this.mImagePagerAdapter = new ImagePagerAdapter();
        bindLifecycle(context);
    }
}
