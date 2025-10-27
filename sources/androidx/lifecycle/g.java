package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes.dex */
public final /* synthetic */ class g {
    static {
        ViewModelProvider.Factory.Companion companion = ViewModelProvider.Factory.INSTANCE;
    }

    @NotNull
    public static ViewModel a(ViewModelProvider.Factory factory, @NotNull Class modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
    }

    @NotNull
    public static ViewModel b(ViewModelProvider.Factory factory, @NotNull Class modelClass, @NotNull CreationExtras extras) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        Intrinsics.checkNotNullParameter(extras, "extras");
        return factory.create(modelClass);
    }

    @JvmStatic
    @NotNull
    public static ViewModelProvider.Factory c(@NotNull ViewModelInitializer<?>... viewModelInitializerArr) {
        return ViewModelProvider.Factory.INSTANCE.from(viewModelInitializerArr);
    }
}
