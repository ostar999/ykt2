package com.catchpig.mvvm.base.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import com.catchpig.mvvm.base.viewmodel.IBaseViewModel;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0014J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "Landroidx/lifecycle/ViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/IBaseViewModel;", "()V", "compositeDisposable", "Lio/reactivex/rxjava3/disposables/CompositeDisposable;", "add", "", "disposable", "Lio/reactivex/rxjava3/disposables/Disposable;", "onCleared", PLVRemoveMicSiteEvent.EVENT_NAME, "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public class BaseViewModel extends ViewModel implements IBaseViewModel {

    @NotNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void add(@NotNull Disposable disposable) {
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        this.compositeDisposable.add(disposable);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onAny(@Nullable LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        IBaseViewModel.DefaultImpls.onAny(this, lifecycleOwner, event);
    }

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        this.compositeDisposable.clear();
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onCreate() {
        IBaseViewModel.DefaultImpls.onCreate(this);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onDestroy() {
        IBaseViewModel.DefaultImpls.onDestroy(this);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onPause() {
        IBaseViewModel.DefaultImpls.onPause(this);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onResume() {
        IBaseViewModel.DefaultImpls.onResume(this);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onStart() {
        IBaseViewModel.DefaultImpls.onStart(this);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NotNull LifecycleOwner lifecycleOwner, @NotNull Lifecycle.Event event) {
        IBaseViewModel.DefaultImpls.onStateChanged(this, lifecycleOwner, event);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void onStop() {
        IBaseViewModel.DefaultImpls.onStop(this);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel
    public void remove(@NotNull Disposable disposable) {
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        this.compositeDisposable.remove(disposable);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onCreate(@NotNull LifecycleOwner lifecycleOwner) {
        IBaseViewModel.DefaultImpls.onCreate(this, lifecycleOwner);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onDestroy(@NotNull LifecycleOwner lifecycleOwner) {
        IBaseViewModel.DefaultImpls.onDestroy(this, lifecycleOwner);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onPause(@NotNull LifecycleOwner lifecycleOwner) {
        IBaseViewModel.DefaultImpls.onPause(this, lifecycleOwner);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onResume(@NotNull LifecycleOwner lifecycleOwner) {
        IBaseViewModel.DefaultImpls.onResume(this, lifecycleOwner);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStart(@NotNull LifecycleOwner lifecycleOwner) {
        IBaseViewModel.DefaultImpls.onStart(this, lifecycleOwner);
    }

    @Override // com.catchpig.mvvm.base.viewmodel.IBaseViewModel, androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStop(@NotNull LifecycleOwner lifecycleOwner) {
        IBaseViewModel.DefaultImpls.onStop(this, lifecycleOwner);
    }
}
