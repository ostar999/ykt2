package com.catchpig.mvvm.base.viewmodel;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.tencent.open.SocialConstants;
import io.reactivex.rxjava3.disposables.Disposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u00002\u00020\u00012\u00020\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001c\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\u0010\u001a\u00020\u0004H\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u0004H\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0015"}, d2 = {"Lcom/catchpig/mvvm/base/viewmodel/IBaseViewModel;", "Landroidx/lifecycle/DefaultLifecycleObserver;", "Landroidx/lifecycle/LifecycleEventObserver;", "add", "", "disposable", "Lio/reactivex/rxjava3/disposables/Disposable;", "onAny", "owner", "Landroidx/lifecycle/LifecycleOwner;", NotificationCompat.CATEGORY_EVENT, "Landroidx/lifecycle/Lifecycle$Event;", "onCreate", "onDestroy", "onPause", "onResume", "onStart", "onStateChanged", SocialConstants.PARAM_SOURCE, "onStop", PLVRemoveMicSiteEvent.EVENT_NAME, "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IBaseViewModel extends DefaultLifecycleObserver, LifecycleEventObserver {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void onAny(@NotNull IBaseViewModel iBaseViewModel, @Nullable LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        }

        public static void onCreate(@NotNull IBaseViewModel iBaseViewModel) {
        }

        public static void onCreate(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            iBaseViewModel.onCreate();
        }

        public static void onDestroy(@NotNull IBaseViewModel iBaseViewModel) {
        }

        public static void onDestroy(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            iBaseViewModel.onDestroy();
        }

        public static void onPause(@NotNull IBaseViewModel iBaseViewModel) {
        }

        public static void onPause(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            iBaseViewModel.onPause();
        }

        public static void onResume(@NotNull IBaseViewModel iBaseViewModel) {
        }

        public static void onResume(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            iBaseViewModel.onResume();
        }

        public static void onStart(@NotNull IBaseViewModel iBaseViewModel) {
        }

        public static void onStart(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            iBaseViewModel.onStart();
        }

        public static void onStateChanged(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner source, @NotNull Lifecycle.Event event) {
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(event, "event");
            iBaseViewModel.onAny(source, event);
        }

        public static void onStop(@NotNull IBaseViewModel iBaseViewModel) {
        }

        public static void onStop(@NotNull IBaseViewModel iBaseViewModel, @NotNull LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            iBaseViewModel.onStop();
        }
    }

    void add(@NotNull Disposable disposable);

    void onAny(@Nullable LifecycleOwner owner, @Nullable Lifecycle.Event event);

    void onCreate();

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    void onCreate(@NotNull LifecycleOwner owner);

    void onDestroy();

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    void onDestroy(@NotNull LifecycleOwner owner);

    void onPause();

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    void onPause(@NotNull LifecycleOwner owner);

    void onResume();

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    void onResume(@NotNull LifecycleOwner owner);

    void onStart();

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    void onStart(@NotNull LifecycleOwner owner);

    void onStateChanged(@NotNull LifecycleOwner source, @NotNull Lifecycle.Event event);

    void onStop();

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    void onStop(@NotNull LifecycleOwner owner);

    void remove(@NotNull Disposable disposable);
}
