package com.ykb.ebook.extensions;

import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import com.ykb.ebook.base.ViewBindingProperty;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aE\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0004\"\b\b\u0001\u0010\u0003*\u00020\u0005*\u00020\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007H\u0007¢\u0006\u0002\b\b\u001aa\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0004\"\b\b\u0001\u0010\u0003*\u00020\u0005*\u00020\u00042\u0014\b\u0004\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u00030\u00072\u0014\b\u0006\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\n0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\b\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\f"}, d2 = {"viewBinding", "Lcom/ykb/ebook/base/ViewBindingProperty;", "F", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/fragment/app/Fragment;", "Landroidx/viewbinding/ViewBinding;", "viewBinder", "Lkotlin/Function1;", "viewBindingFragment", "vbFactory", "Landroid/view/View;", "viewProvider", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class FragmentViewBindingsKt {
    @JvmName(name = "viewBindingFragment")
    @NotNull
    public static final <F extends Fragment, T extends ViewBinding> ViewBindingProperty<F, T> viewBindingFragment(@NotNull Fragment fragment, @NotNull Function1<? super F, ? extends T> viewBinder) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(viewBinder, "viewBinder");
        return new FragmentViewBindingProperty(viewBinder);
    }

    public static /* synthetic */ ViewBindingProperty viewBindingFragment$default(Fragment fragment, Function1 vbFactory, Function1 viewProvider, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            viewProvider = FragmentViewBindingsKt$viewBinding$1.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(vbFactory, "vbFactory");
        Intrinsics.checkNotNullParameter(viewProvider, "viewProvider");
        return viewBindingFragment(fragment, new FragmentViewBindingsKt$viewBinding$2(vbFactory, viewProvider));
    }

    @JvmName(name = "viewBindingFragment")
    @NotNull
    public static final <F extends Fragment, T extends ViewBinding> ViewBindingProperty<F, T> viewBindingFragment(@NotNull Fragment fragment, @NotNull Function1<? super View, ? extends T> vbFactory, @NotNull Function1<? super F, ? extends View> viewProvider) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(vbFactory, "vbFactory");
        Intrinsics.checkNotNullParameter(viewProvider, "viewProvider");
        return viewBindingFragment(fragment, new FragmentViewBindingsKt$viewBinding$2(vbFactory, viewProvider));
    }
}
