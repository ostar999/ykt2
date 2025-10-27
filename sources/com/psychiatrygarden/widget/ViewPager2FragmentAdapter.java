package com.psychiatrygarden.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/widget/ViewPager2FragmentAdapter;", "Landroidx/viewpager2/adapter/FragmentStateAdapter;", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "lf", "Landroidx/lifecycle/Lifecycle;", "fragments", "", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/FragmentManager;Landroidx/lifecycle/Lifecycle;Ljava/util/List;)V", "createFragment", "position", "", "getItemCount", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ViewPager2FragmentAdapter extends FragmentStateAdapter {

    @NotNull
    private final FragmentManager fragmentManager;

    @NotNull
    private final List<Fragment> fragments;

    @NotNull
    private final Lifecycle lf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ViewPager2FragmentAdapter(@NotNull FragmentManager fragmentManager, @NotNull Lifecycle lf, @NotNull List<? extends Fragment> fragments) {
        super(fragmentManager, lf);
        Intrinsics.checkNotNullParameter(fragmentManager, "fragmentManager");
        Intrinsics.checkNotNullParameter(lf, "lf");
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        this.fragmentManager = fragmentManager;
        this.lf = lf;
        this.fragments = fragments;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    @NotNull
    public Fragment createFragment(int position) {
        return this.fragments.get(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.fragments.size();
    }
}
