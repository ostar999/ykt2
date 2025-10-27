package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class RecyleAdapter extends RecyclingPagerAdapter {
    private boolean isLoop;
    private Context mContext;
    private ImageLoader mImageLoader;
    private OnPageClickListener mOnPageClickListener;
    private List<Page> mPageList;
    private int mTheme;
    private ViewBinder mViewBinder;

    public RecyleAdapter(Context context, ViewBinder viewBinder) {
        this(context, viewBinder, null);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.isLoop ? getRealCount() * 100 : getRealCount();
    }

    public int getRealCount() {
        return this.mPageList.size();
    }

    public int getRealPosition(int i2) {
        return this.isLoop ? i2 % getRealCount() : i2;
    }

    @Override // cn.lightsky.infiniteindicator.recycle.RecyclingPagerAdapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        return this.mViewBinder.bindView(this.mContext, getRealPosition(i2), this.mPageList.get(getRealPosition(i2)), this.mImageLoader, this.mOnPageClickListener, view, viewGroup, this.mTheme);
    }

    public int getmTheme() {
        return this.mTheme;
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.mImageLoader = imageLoader;
    }

    public void setIsLoop(boolean z2) {
        this.isLoop = z2;
    }

    public void setPages(List<Page> list) {
        this.mPageList = list;
        notifyDataSetChanged();
    }

    public void setmTheme(int i2) {
        this.mTheme = i2;
    }

    public RecyleAdapter(Context context, ViewBinder viewBinder, OnPageClickListener onPageClickListener) {
        this.mPageList = new ArrayList();
        this.isLoop = true;
        this.mTheme = 0;
        this.mContext = context;
        this.mOnPageClickListener = onPageClickListener;
        this.mViewBinder = viewBinder;
    }
}
