package com.umeng.socialize.shareboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
class SocializeMenuPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ShareBoardMenuHelper mMenuHelper;
    private List<SnsPlatform[][]> mPageData;

    public SocializeMenuPagerAdapter(Context context, ShareBoardConfig shareBoardConfig) {
        this(context, shareBoardConfig, null);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i2, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<SnsPlatform[][]> list = this.mPageData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i2) {
        View viewCreatePageLayout = this.mMenuHelper.createPageLayout(this.mContext, this.mPageData.get(i2));
        viewGroup.addView(viewCreatePageLayout);
        return viewCreatePageLayout;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void setData(List<SnsPlatform> list) {
        this.mPageData.clear();
        if (list != null) {
            this.mPageData.addAll(this.mMenuHelper.formatPageData(list));
        }
        notifyDataSetChanged();
    }

    public SocializeMenuPagerAdapter(Context context, ShareBoardConfig shareBoardConfig, List<SnsPlatform> list) {
        this.mPageData = new ArrayList();
        this.mContext = context;
        this.mMenuHelper = new ShareBoardMenuHelper(shareBoardConfig);
        setData(list);
    }
}
