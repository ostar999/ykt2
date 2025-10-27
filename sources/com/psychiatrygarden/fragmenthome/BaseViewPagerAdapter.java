package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private Fragment mCurFragment;
    private List<PagerInfo> mInfoList;

    public static class PagerInfo {
        private Bundle args;
        private Class<?> clx;
        private String title;

        public PagerInfo(String title, Class<?> clx, Bundle args) {
            this.title = title;
            this.clx = clx;
            this.args = args;
        }
    }

    public BaseViewPagerAdapter(Context context, FragmentManager fm, List<PagerInfo> infoList) {
        super(fm);
        this.mContext = context;
        this.mInfoList = infoList;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        List<PagerInfo> list = this.mInfoList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Fragment getCurFragment() {
        return this.mCurFragment;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    @NonNull
    public Fragment getItem(int position) {
        PagerInfo pagerInfo = this.mInfoList.get(position);
        return Fragment.instantiate(this.mContext, pagerInfo.clx.getName(), pagerInfo.args);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object object) {
        return -2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int position) {
        return this.mInfoList.get(position).title;
    }

    public void setPageInfo(List<PagerInfo> infoList) {
        this.mInfoList.clear();
        this.mInfoList.addAll(infoList);
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        if (object instanceof Fragment) {
            this.mCurFragment = (Fragment) object;
        }
    }
}
