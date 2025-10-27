package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.imagezoom.MutipleTouchViewPager;
import com.psychiatrygarden.widget.imagezoom.PhotoView;
import com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher;
import com.yikaobang.yixue.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ViewPagerActivity extends BaseActivity {
    private LinearLayout linearLayout2;
    private ArrayList<String> mList;
    private MutipleTouchViewPager mViewPager;
    private int position = 0;

    @SuppressLint({"HandlerLeak"})
    private final Handler mFinishHandler = new Handler() { // from class: com.psychiatrygarden.activity.ViewPagerActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ViewPagerActivity.this.finish();
            }
        }
    };

    public class SamplePagerAdapter extends PagerAdapter {
        Context context;
        Handler mFinishHandler;

        public SamplePagerAdapter(Context context, Handler mFinishHandler) {
            this.context = context;
            this.mFinishHandler = mFinishHandler;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$instantiateItem$0(View view, float f2, float f3) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 1;
            this.mFinishHandler.sendMessage(messageObtain);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            PhotoView photoView = (PhotoView) object;
            photoView.setImageBitmap(null);
            photoView.setBackgroundDrawable(null);
            releaseImageViewResouce(photoView);
            container.removeView(photoView);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return ViewPagerActivity.this.mList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public void releaseImageViewResouce(PhotoView imageView) {
            Bitmap bitmap;
            if (imageView == null) {
                return;
            }
            Drawable drawable = imageView.getDrawable();
            if ((drawable instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            System.gc();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public View instantiateItem(@NonNull ViewGroup container, final int position) {
            WeakReference weakReference = new WeakReference(null);
            PhotoView photoView = weakReference.get() == null ? new PhotoView(container.getContext()) : (PhotoView) weakReference.get();
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() { // from class: com.psychiatrygarden.activity.vq
                @Override // com.psychiatrygarden.widget.imagezoom.PhotoViewAttacher.OnViewTapListener
                public final void onViewTap(View view, float f2, float f3) {
                    this.f14131a.lambda$instantiateItem$0(view, f2, f3);
                }
            });
            Glide.with(photoView.getContext()).load((Object) GlideUtils.generateUrl((String) ViewPagerActivity.this.mList.get(position))).error(R.drawable.imgplacehodel_image).placeholder(new ColorDrawable(ContextCompat.getColor(photoView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(photoView);
            container.addView(photoView, -1, -1);
            return photoView;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        if (this.mList.size() == 1) {
            this.linearLayout2.setVisibility(8);
        }
        for (int i2 = 0; i2 < this.mList.size(); i2++) {
            ImageView imageView = new ImageView(ProjectApp.instance());
            if (this.position == i2) {
                imageView.setBackgroundResource(R.drawable.scroll_but_active);
            } else {
                imageView.setBackgroundResource(R.drawable.scroll_but);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(5, 0, 5, 0);
            this.linearLayout2.addView(imageView, layoutParams);
        }
        WeakReference weakReference = new WeakReference(this.mViewPager);
        if (weakReference.get() == null) {
            this.mViewPager = (MutipleTouchViewPager) findViewById(R.id.view_pager);
        } else {
            this.mViewPager = (MutipleTouchViewPager) weakReference.get();
        }
        this.mViewPager.setAdapter(new SamplePagerAdapter(ProjectApp.instance(), this.mFinishHandler));
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.ViewPagerActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg0) {
                ViewPagerActivity.this.position = arg0;
                ViewPagerActivity.this.linearLayout2.removeAllViews();
                int i3 = 0;
                while (i3 < ViewPagerActivity.this.mList.size()) {
                    ImageView imageView2 = new ImageView(ProjectApp.instance());
                    imageView2.setBackgroundResource(arg0 == i3 ? R.drawable.scroll_but_active : R.drawable.scroll_but);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.setMargins(5, 0, 5, 0);
                    ViewPagerActivity.this.linearLayout2.addView(imageView2, layoutParams2);
                    i3++;
                }
            }
        });
        this.mViewPager.setCurrentItem(this.position);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setSwipeBackEnable(false);
        this.mActionBar.hide();
        this.position = getIntent().getIntExtra("position", 0);
        this.mList = getIntent().getStringArrayListExtra("list");
        setContentView(R.layout.activity_view_pager);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
