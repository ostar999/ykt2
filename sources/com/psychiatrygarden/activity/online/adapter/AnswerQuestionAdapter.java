package com.psychiatrygarden.activity.online.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class AnswerQuestionAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> mFragments;

    public AnswerQuestionAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.mFragments = fragments;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        fragment.getParentFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.mFragments.size();
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    @NonNull
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = this.mFragments.get(position);
        this.fragmentManager.beginTransaction().add(container.getId(), fragment, "fragment:" + position).commitNowAllowingStateLoss();
        return fragment;
    }
}
