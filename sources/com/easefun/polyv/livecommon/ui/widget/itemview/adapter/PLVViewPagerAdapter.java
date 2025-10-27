package com.easefun.polyv.livecommon.ui.widget.itemview.adapter;

import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<? extends Fragment> fragments;

    public PLVViewPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.fragments.size();
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object object) {
        int iIndexOf = this.fragments.indexOf(object);
        if (iIndexOf < 0) {
            return -2;
        }
        return iIndexOf;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable state, ClassLoader loader) {
        try {
            super.restoreState(state, loader);
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            e2.printStackTrace();
        }
    }
}
