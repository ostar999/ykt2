package com.ykb.ebook.extensions;

import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Add missing generic type declarations: [T, F] */
@Metadata(d1 = {"\u0000\u0010\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0001*\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", ExifInterface.GPS_DIRECTION_TRUE, "F", "Landroidx/fragment/app/Fragment;", "Landroidx/viewbinding/ViewBinding;", "fragment", "invoke", "(Landroidx/fragment/app/Fragment;)Landroidx/viewbinding/ViewBinding;"}, k = 3, mv = {1, 8, 0}, xi = 176)
@SourceDebugExtension({"SMAP\nFragmentViewBindings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FragmentViewBindings.kt\ncom/ykb/ebook/extensions/FragmentViewBindingsKt$viewBinding$2\n*L\n1#1,40:1\n*E\n"})
/* loaded from: classes7.dex */
public final class FragmentViewBindingsKt$viewBinding$2<F, T> extends Lambda implements Function1<F, T> {
    final /* synthetic */ Function1<View, T> $vbFactory;
    final /* synthetic */ Function1<F, View> $viewProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FragmentViewBindingsKt$viewBinding$2(Function1<? super View, ? extends T> function1, Function1<? super F, ? extends View> function12) {
        super(1);
        this.$vbFactory = function1;
        this.$viewProvider = function12;
    }

    /* JADX WARN: Incorrect return type in method signature: (TF;)TT; */
    @Override // kotlin.jvm.functions.Function1
    @NotNull
    public final ViewBinding invoke(@NotNull Fragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        return (ViewBinding) this.$vbFactory.invoke(this.$viewProvider.invoke(fragment));
    }
}
