package com.psychiatrygarden.widget;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment;
import java.util.List;

/* loaded from: classes6.dex */
public class AnswerQuestionAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> mFragments;

    public AnswerQuestionAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.mFragments = fragments;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment instanceof ShareStemQuestionFragment) {
            return;
        }
        fragment.getParentFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.mFragments.size();
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    @NonNull
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = this.mFragments.get(position);
        if (!fragment.isAdded()) {
            this.fragmentManager.beginTransaction().add(container.getId(), fragment, "fragment:" + position).commitNowAllowingStateLoss();
        }
        return fragment;
    }
}
