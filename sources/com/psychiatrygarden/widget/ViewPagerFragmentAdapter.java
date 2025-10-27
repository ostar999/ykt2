package com.psychiatrygarden.widget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragments;

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragments = new ArrayList();
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    public void clearFragments() {
        this.fragments.clear();
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    @NonNull
    public Fragment createFragment(int position) {
        return this.fragments.get(position);
    }

    public Fragment getFragment(int position) {
        if (position < 0 || position >= this.fragments.size()) {
            return null;
        }
        return this.fragments.get(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.fragments.size();
    }

    public ViewPagerFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
        this.fragments = new ArrayList();
    }

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.fragments = new ArrayList();
    }
}
