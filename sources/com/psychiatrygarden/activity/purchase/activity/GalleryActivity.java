package com.psychiatrygarden.activity.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.util.Bimp;
import com.psychiatrygarden.activity.purchase.util.PublicWay;
import com.psychiatrygarden.activity.purchase.util.Res;
import com.psychiatrygarden.activity.purchase.zoom.PhotoView;
import com.psychiatrygarden.activity.purchase.zoom.ViewPagerFixed;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes5.dex */
public class GalleryActivity extends BaseActivity {
    private MyPageAdapter adapter;
    private Intent intent;
    ImageView iv_actionbar_right;
    private Context mContext;
    private ViewPagerFixed pager;
    private int position;
    private Button send_bt;
    private int location = 0;
    private ArrayList<View> listViews = null;
    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.purchase.activity.GalleryActivity.1
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int arg0) {
            GalleryActivity.this.location = arg0;
        }
    };

    public class DelListener implements View.OnClickListener {
        private DelListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (GalleryActivity.this.listViews.size() == 1) {
                Bimp.tempSelectBitmap.clear();
                Bimp.max = 0;
                GalleryActivity.this.send_bt.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
                GalleryActivity.this.sendBroadcast(new Intent("data.broadcast.action"));
                GalleryActivity.this.finish();
                return;
            }
            Bimp.tempSelectBitmap.remove(GalleryActivity.this.location);
            Bimp.max--;
            GalleryActivity.this.pager.removeAllViews();
            GalleryActivity.this.listViews.remove(GalleryActivity.this.location);
            GalleryActivity.this.adapter.setListViews(GalleryActivity.this.listViews);
            GalleryActivity.this.send_bt.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
            GalleryActivity.this.adapter.notifyDataSetChanged();
        }
    }

    public class GallerySendListener implements View.OnClickListener {
        private GallerySendListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            GalleryActivity.this.finish();
            GalleryActivity.this.intent.setClass(GalleryActivity.this.mContext, XiaoHongShuReplyActivity.class);
            GalleryActivity galleryActivity = GalleryActivity.this;
            galleryActivity.startActivity(galleryActivity.intent);
        }
    }

    public static class MyPageAdapter extends PagerAdapter {
        private ArrayList<View> listViews;
        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            this.size = listViews == null ? 0 : listViews.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(@NonNull View arg0, int arg1, @NonNull Object arg2) {
            ((ViewPagerFixed) arg0).removeView(this.listViews.get(arg1 % this.size));
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void finishUpdate(@NonNull View arg0) {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return this.size;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(@NonNull Object object) {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public Object instantiateItem(@NonNull View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(this.listViews.get(arg1 % this.size), 0);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return this.listViews.get(arg1 % this.size);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            this.size = listViews == null ? 0 : listViews.size();
        }
    }

    private void initListViews(Bitmap bm) {
        if (this.listViews == null) {
            this.listViews = new ArrayList<>();
        }
        PhotoView photoView = new PhotoView(this);
        photoView.setBackgroundColor(-16777216);
        photoView.setImageBitmap(bm);
        photoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.listViews.add(photoView);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() <= 0) {
            this.send_bt.setPressed(false);
            this.send_bt.setClickable(false);
            this.send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        } else {
            this.send_bt.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
            this.send_bt.setPressed(true);
            this.send_bt.setClickable(true);
            this.send_bt.setTextColor(-1);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_gallery"));
        setTitle("预览图片");
        this.mContext = this;
        Button button = (Button) findViewById(Res.getWidgetID("send_button"));
        this.send_bt = button;
        button.setOnClickListener(new GallerySendListener());
        this.iv_actionbar_right.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setBackgroundResource(R.drawable.plugin_camera_del_state);
        this.iv_actionbar_right.setOnClickListener(new DelListener());
        Intent intent = getIntent();
        this.intent = intent;
        this.position = Integer.parseInt(intent.getStringExtra("position"));
        isShowOkBt();
        ViewPagerFixed viewPagerFixed = (ViewPagerFixed) findViewById(Res.getWidgetID("gallery01"));
        this.pager = viewPagerFixed;
        viewPagerFixed.addOnPageChangeListener(this.pageChangeListener);
        for (int i2 = 0; i2 < Bimp.tempSelectBitmap.size(); i2++) {
            initListViews(Bimp.tempSelectBitmap.get(i2).getBitmap());
        }
        MyPageAdapter myPageAdapter = new MyPageAdapter(this.listViews);
        this.adapter = myPageAdapter;
        this.pager.setAdapter(myPageAdapter);
        this.pager.setPageMargin(getResources().getDimensionPixelOffset(Res.getDimenID("ui_10_dip")));
        this.pager.setCurrentItem(this.intent.getIntExtra("ID", 0));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        int i2 = this.position;
        if (i2 == 1) {
            finish();
            return true;
        }
        if (i2 != 2) {
            return false;
        }
        finish();
        this.intent.setClass(this, ShowAllPhoto.class);
        startActivity(this.intent);
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
