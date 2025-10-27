package com.ykb.ebook.extensions;

import android.view.LayoutInflater;
import androidx.core.app.ComponentActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.viewbinding.ViewBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Add missing generic type declarations: [T] */
@Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "invoke", "()Landroidx/viewbinding/ViewBinding;"}, k = 3, mv = {1, 8, 0}, xi = 176)
@SourceDebugExtension({"SMAP\nActivityViewBindings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt$viewBinding$1\n*L\n1#1,23:1\n*E\n"})
/* loaded from: classes7.dex */
public final class ActivityViewBindingsKt$viewBinding$1<T> extends Lambda implements Function0<T> {
    final /* synthetic */ Function1<LayoutInflater, T> $bindingInflater;
    final /* synthetic */ boolean $setContentView;
    final /* synthetic */ ComponentActivity $this_viewBinding;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ActivityViewBindingsKt$viewBinding$1(Function1<? super LayoutInflater, ? extends T> function1, ComponentActivity componentActivity, boolean z2) {
        super(0);
        this.$bindingInflater = function1;
        this.$this_viewBinding = componentActivity;
        this.$setContentView = z2;
    }

    /* JADX WARN: Incorrect return type in method signature: ()TT; */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final ViewBinding invoke() {
        Function1<LayoutInflater, T> function1 = this.$bindingInflater;
        LayoutInflater layoutInflater = this.$this_viewBinding.getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
        ViewBinding viewBinding = (ViewBinding) function1.invoke(layoutInflater);
        if (this.$setContentView) {
            this.$this_viewBinding.setContentView(viewBinding.getRoot());
        }
        return viewBinding;
    }
}
