package com.plv.foundationsdk.component.viewmodel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/* loaded from: classes4.dex */
public class PLVViewModels {
    private ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
    private final ViewModelStore store;

    private PLVViewModels(ViewModelStore viewModelStore) {
        this.store = viewModelStore;
    }

    @NonNull
    public static PLVViewModels on(@NonNull ViewModelStoreOwner viewModelStoreOwner) {
        return new PLVViewModels(viewModelStoreOwner.getViewModelStore());
    }

    public <T extends ViewModel> T get(Class<T> cls) {
        return (T) new ViewModelProvider(this.store, this.factory).get(cls);
    }

    public PLVViewModels setFactory(ViewModelProvider.Factory factory) {
        this.factory = factory;
        return this;
    }

    @NonNull
    public static PLVViewModels on(@NonNull FragmentActivity fragmentActivity) {
        return new PLVViewModels(fragmentActivity.getViewModelStore());
    }

    @NonNull
    public static PLVViewModels on(@NonNull ViewModelStore viewModelStore) {
        return new PLVViewModels(viewModelStore);
    }
}
