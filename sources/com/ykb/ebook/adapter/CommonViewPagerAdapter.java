package com.ykb.ebook.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.socket.user.PLVSocketUserConstant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\u0014\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/adapter/CommonViewPagerAdapter;", "Landroidx/viewpager2/adapter/FragmentStateAdapter;", "fragments", "", "Landroidx/fragment/app/Fragment;", PLVSocketUserConstant.USERTYPE_MANAGER, "Landroidx/fragment/app/FragmentManager;", RequestParameters.SUBRESOURCE_LIFECYCLE, "Landroidx/lifecycle/Lifecycle;", "(Ljava/util/List;Landroidx/fragment/app/FragmentManager;Landroidx/lifecycle/Lifecycle;)V", "createFragment", "position", "", "getItemCount", PLVRxEncryptDataFunction.SET_DATA_METHOD, "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CommonViewPagerAdapter extends FragmentStateAdapter {

    @NotNull
    private List<? extends Fragment> fragments;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommonViewPagerAdapter(@NotNull List<? extends Fragment> fragments, @NotNull FragmentManager manager, @NotNull Lifecycle lifecycle) {
        super(manager, lifecycle);
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
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

    public final void setData(@NotNull List<? extends Fragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        this.fragments = fragments;
        notifyItemRangeChanged(0, fragments.size());
    }
}
