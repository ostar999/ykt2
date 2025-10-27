package com.psychiatrygarden.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B1\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/psychiatrygarden/adapter/NewViewPager2Adapter;", "Landroidx/viewpager2/adapter/FragmentStateAdapter;", "fragments", "", "Landroidx/fragment/app/Fragment;", "fragmentIds", "", "m", "Landroidx/fragment/app/FragmentManager;", NotifyType.LIGHTS, "Landroidx/lifecycle/Lifecycle;", "(Ljava/util/List;Ljava/util/List;Landroidx/fragment/app/FragmentManager;Landroidx/lifecycle/Lifecycle;)V", "containsItem", "", "itemId", "createFragment", "position", "", "getItemCount", "getItemId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NewViewPager2Adapter extends FragmentStateAdapter {

    @NotNull
    private final List<Long> fragmentIds;

    @NotNull
    private final List<Fragment> fragments;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NewViewPager2Adapter(@NotNull List<? extends Fragment> fragments, @NotNull List<Long> fragmentIds, @NotNull FragmentManager m2, @NotNull Lifecycle l2) {
        super(m2, l2);
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        Intrinsics.checkNotNullParameter(fragmentIds, "fragmentIds");
        Intrinsics.checkNotNullParameter(m2, "m");
        Intrinsics.checkNotNullParameter(l2, "l");
        this.fragments = fragments;
        this.fragmentIds = fragmentIds;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public boolean containsItem(long itemId) {
        return this.fragmentIds.contains(Long.valueOf(itemId));
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

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return this.fragmentIds.get(position).longValue();
    }
}
