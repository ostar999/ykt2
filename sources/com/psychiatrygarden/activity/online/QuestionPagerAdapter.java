package com.psychiatrygarden.activity.online;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> mFragments;

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.mFragments = fragments;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    @NonNull
    public Fragment createFragment(int position) {
        return this.mFragments.get(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mFragments.size();
    }
}
