package com.psychiatrygarden.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ViewPager2Adapter extends FragmentStateAdapter {
    private final List<Fragment> mFragments;

    public ViewPager2Adapter(List<Fragment> fragments, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        ArrayList arrayList = new ArrayList();
        this.mFragments = arrayList;
        if (fragments != null) {
            arrayList.clear();
            arrayList.addAll(fragments);
        }
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
