package com.ykb.ebook.extensions;

import android.view.LayoutInflater;
import androidx.core.app.ComponentActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.viewbinding.ViewBinding;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aE\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\u0014\b\u0004\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\bø\u0001\u0000¢\u0006\u0002\b\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000b"}, d2 = {"viewBinding", "Lkotlin/Lazy;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "Landroidx/core/app/ComponentActivity;", "bindingInflater", "Lkotlin/Function1;", "Landroid/view/LayoutInflater;", "setContentView", "", "viewBindingActivity", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ActivityViewBindingsKt {
    @JvmName(name = "viewBindingActivity")
    @NotNull
    public static final <T extends ViewBinding> Lazy<T> viewBindingActivity(@NotNull ComponentActivity componentActivity, @NotNull Function1<? super LayoutInflater, ? extends T> bindingInflater, boolean z2) {
        Intrinsics.checkNotNullParameter(componentActivity, "<this>");
        Intrinsics.checkNotNullParameter(bindingInflater, "bindingInflater");
        return LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new ActivityViewBindingsKt$viewBinding$1(bindingInflater, componentActivity, z2));
    }

    public static /* synthetic */ Lazy viewBindingActivity$default(ComponentActivity componentActivity, Function1 bindingInflater, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        Intrinsics.checkNotNullParameter(componentActivity, "<this>");
        Intrinsics.checkNotNullParameter(bindingInflater, "bindingInflater");
        return LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new ActivityViewBindingsKt$viewBinding$1(bindingInflater, componentActivity, z2));
    }
}
