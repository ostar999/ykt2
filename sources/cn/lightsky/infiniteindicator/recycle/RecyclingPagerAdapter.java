package cn.lightsky.infiniteindicator.recycle;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;

/* loaded from: classes.dex */
public abstract class RecyclingPagerAdapter extends PagerAdapter {
    static final int IGNORE_ITEM_VIEW_TYPE = -1;
    private DataChangeListener mDataChangeListener;
    private final RecycleBin recycleBin;

    public interface DataChangeListener {
        void notifyDataChange();
    }

    public RecyclingPagerAdapter() {
        this(new RecycleBin());
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewGroup viewGroup, int i2, Object obj) {
        View view = (View) obj;
        viewGroup.removeView(view);
        int itemViewType = getItemViewType(i2);
        if (itemViewType != -1) {
            this.recycleBin.addScrapView(view, i2, itemViewType);
        }
    }

    public DataChangeListener getDataChangeListener() {
        return this.mDataChangeListener;
    }

    public int getItemViewType(int i2) {
        return 0;
    }

    public abstract View getView(int i2, View view, ViewGroup viewGroup);

    public int getViewTypeCount() {
        return 1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewGroup viewGroup, int i2) {
        int itemViewType = getItemViewType(i2);
        View view = getView(i2, itemViewType != -1 ? this.recycleBin.getScrapView(i2, itemViewType) : null, viewGroup);
        viewGroup.addView(view);
        return view;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void notifyDataSetChanged() {
        this.recycleBin.scrapActiveViews();
        DataChangeListener dataChangeListener = this.mDataChangeListener;
        if (dataChangeListener != null) {
            dataChangeListener.notifyDataChange();
        }
        super.notifyDataSetChanged();
    }

    public void setDataChangeListener(DataChangeListener dataChangeListener) {
        this.mDataChangeListener = dataChangeListener;
    }

    public RecyclingPagerAdapter(RecycleBin recycleBin) {
        this.recycleBin = recycleBin;
        recycleBin.setViewTypeCount(getViewTypeCount());
    }
}
