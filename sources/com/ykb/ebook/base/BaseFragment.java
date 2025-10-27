package com.ykb.ebook.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import com.ykb.ebook.weight.EbookLoadingPop;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u00020\bH$J\u001a\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH$J\u001a\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/ykb/ebook/base/BaseFragment;", "Landroidx/fragment/app/Fragment;", "layoutId", "", "(I)V", "loadingPop", "Lcom/ykb/ebook/weight/EbookLoadingPop;", "hideLoading", "", "observeViewModel", "onFragmentCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "showLoading", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class BaseFragment extends Fragment {
    private EbookLoadingPop loadingPop;

    public BaseFragment(@LayoutRes int i2) {
        super(i2);
    }

    public final void hideLoading() {
        EbookLoadingPop ebookLoadingPop = this.loadingPop;
        if (ebookLoadingPop == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingPop");
            ebookLoadingPop = null;
        }
        ebookLoadingPop.dismiss();
    }

    public abstract void observeViewModel();

    public abstract void onFragmentCreated(@NotNull View view, @Nullable Bundle savedInstanceState);

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        this.loadingPop = new EbookLoadingPop(contextRequireContext, null, 2, 0 == true ? 1 : 0);
        onFragmentCreated(view, savedInstanceState);
    }

    public final void showLoading() {
        EbookLoadingPop ebookLoadingPop = this.loadingPop;
        if (ebookLoadingPop == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingPop");
            ebookLoadingPop = null;
        }
        ebookLoadingPop.show();
    }
}
