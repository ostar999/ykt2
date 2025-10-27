package com.ykb.ebook.extensions;

import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;
import com.ykb.ebook.base.ViewBindingProperty;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0005B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/ykb/ebook/extensions/FragmentViewBindingProperty;", "F", "Landroidx/fragment/app/Fragment;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "Lcom/ykb/ebook/base/ViewBindingProperty;", "viewBinder", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;)V", "getLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "thisRef", "(Landroidx/fragment/app/Fragment;)Landroidx/lifecycle/LifecycleOwner;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
final class FragmentViewBindingProperty<F extends Fragment, T extends ViewBinding> extends ViewBindingProperty<F, T> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FragmentViewBindingProperty(@NotNull Function1<? super F, ? extends T> viewBinder) {
        super(viewBinder);
        Intrinsics.checkNotNullParameter(viewBinder, "viewBinder");
    }

    @Override // com.ykb.ebook.base.ViewBindingProperty
    @NotNull
    public LifecycleOwner getLifecycleOwner(@NotNull F thisRef) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        LifecycleOwner viewLifecycleOwner = thisRef.getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "thisRef.viewLifecycleOwner");
        return viewLifecycleOwner;
    }
}
