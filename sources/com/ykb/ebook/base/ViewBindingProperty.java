package com.ykb.ebook.base;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u0019*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0005:\u0002\u0018\u0019B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0007J\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0013J\"\u0010\u0014\u001a\u00028\u00012\u0006\u0010\u000b\u001a\u00028\u00002\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0097\u0002¢\u0006\u0002\u0010\u0017R\u001e\u0010\t\u001a\u00120\nR\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u0004\u0018\u00018\u0000X\u0088\u000e¢\u0006\u0004\n\u0002\u0010\fR\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u0004\u0018\u00018\u0001X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006\u001a"}, d2 = {"Lcom/ykb/ebook/base/ViewBindingProperty;", "R", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/viewbinding/ViewBinding;", "Lkotlin/properties/ReadOnlyProperty;", "viewBinder", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;)V", "lifecycleObserver", "Lcom/ykb/ebook/base/ViewBindingProperty$ClearOnDestroyLifecycleObserver;", "thisRef", "Ljava/lang/Object;", "viewBinding", "Landroidx/viewbinding/ViewBinding;", PLVDocumentMarkToolType.CLEAR, "", "getLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "(Ljava/lang/Object;)Landroidx/lifecycle/LifecycleOwner;", "getValue", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Landroidx/viewbinding/ViewBinding;", "ClearOnDestroyLifecycleObserver", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nViewBindingProperty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ViewBindingProperty.kt\ncom/ykb/ebook/base/ViewBindingProperty\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,57:1\n1#2:58\n*E\n"})
/* loaded from: classes6.dex */
public abstract class ViewBindingProperty<R, T extends ViewBinding> implements ReadOnlyProperty<R, T> {

    @NotNull
    private static final Companion Companion = new Companion(null);

    @Deprecated
    @NotNull
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    @NotNull
    private final ViewBindingProperty<R, T>.ClearOnDestroyLifecycleObserver lifecycleObserver;

    @Nullable
    private R thisRef;

    @NotNull
    private final Function1<R, T> viewBinder;

    @Nullable
    private T viewBinding;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/base/ViewBindingProperty$ClearOnDestroyLifecycleObserver;", "Landroidx/lifecycle/DefaultLifecycleObserver;", "(Lcom/ykb/ebook/base/ViewBindingProperty;)V", "onDestroy", "", "owner", "Landroidx/lifecycle/LifecycleOwner;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class ClearOnDestroyLifecycleObserver implements DefaultLifecycleObserver {
        public ClearOnDestroyLifecycleObserver() {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
            androidx.lifecycle.b.a(this, lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        @MainThread
        public void onDestroy(@NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            ViewBindingProperty.this.clear();
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
            androidx.lifecycle.b.c(this, lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
            androidx.lifecycle.b.d(this, lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
            androidx.lifecycle.b.e(this, lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
            androidx.lifecycle.b.f(this, lifecycleOwner);
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/ykb/ebook/base/ViewBindingProperty$Companion;", "", "()V", "mainHandler", "Landroid/os/Handler;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ViewBindingProperty(@NotNull Function1<? super R, ? extends T> viewBinder) {
        Intrinsics.checkNotNullParameter(viewBinder, "viewBinder");
        this.viewBinder = viewBinder;
        this.lifecycleObserver = new ClearOnDestroyLifecycleObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clear$lambda$3(ViewBindingProperty this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewBinding = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getValue$lambda$1(ViewBindingProperty this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewBinding = null;
    }

    @MainThread
    public final void clear() {
        R r2 = this.thisRef;
        if (r2 == null) {
            return;
        }
        this.thisRef = null;
        getLifecycleOwner(r2).getLifecycle().removeObserver(this.lifecycleObserver);
        mainHandler.post(new Runnable() { // from class: com.ykb.ebook.base.d
            @Override // java.lang.Runnable
            public final void run() {
                ViewBindingProperty.clear$lambda$3(this.f26288c);
            }
        });
    }

    @NotNull
    public abstract LifecycleOwner getLifecycleOwner(@NotNull R thisRef);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.properties.ReadOnlyProperty
    public /* bridge */ /* synthetic */ Object getValue(Object obj, KProperty kProperty) {
        return getValue((ViewBindingProperty<R, T>) obj, (KProperty<?>) kProperty);
    }

    @Override // kotlin.properties.ReadOnlyProperty
    @MainThread
    @NotNull
    public T getValue(@NotNull R thisRef, @NotNull KProperty<?> property) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(property, "property");
        T t2 = this.viewBinding;
        if (t2 != null) {
            return t2;
        }
        this.thisRef = thisRef;
        Lifecycle lifecycle = getLifecycleOwner(thisRef).getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "getLifecycleOwner(thisRef).lifecycle");
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            mainHandler.post(new Runnable() { // from class: com.ykb.ebook.base.e
                @Override // java.lang.Runnable
                public final void run() {
                    ViewBindingProperty.getValue$lambda$1(this.f26289c);
                }
            });
        } else {
            lifecycle.addObserver(this.lifecycleObserver);
        }
        T tInvoke = this.viewBinder.invoke(thisRef);
        this.viewBinding = tInvoke;
        return tInvoke;
    }
}
