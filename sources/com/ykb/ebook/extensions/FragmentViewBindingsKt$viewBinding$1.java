package com.ykb.ebook.extensions;

import android.view.View;
import androidx.fragment.app.Fragment;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 176)
@SourceDebugExtension({"SMAP\nFragmentViewBindings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FragmentViewBindings.kt\ncom/ykb/ebook/extensions/FragmentViewBindingsKt$viewBinding$1\n*L\n1#1,40:1\n*E\n"})
/* loaded from: classes7.dex */
public /* synthetic */ class FragmentViewBindingsKt$viewBinding$1 extends FunctionReferenceImpl implements Function1<Fragment, View> {
    public static final FragmentViewBindingsKt$viewBinding$1 INSTANCE = new FragmentViewBindingsKt$viewBinding$1();

    public FragmentViewBindingsKt$viewBinding$1() {
        super(1, Fragment.class, "requireView", "requireView()Landroid/view/View;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    @NotNull
    public final View invoke(@NotNull Fragment p02) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        return p02.requireView();
    }
}
