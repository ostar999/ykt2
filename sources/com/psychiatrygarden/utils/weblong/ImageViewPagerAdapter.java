package com.psychiatrygarden.utils.weblong;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;

/* loaded from: classes6.dex */
public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String IMAGE_URL = "image";
    List<String> mDatas;

    public ImageViewPagerAdapter(FragmentManager fm, List data) {
        super(fm);
        this.mDatas = data;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.mDatas.size();
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(this.mDatas.get(position));
    }
}
