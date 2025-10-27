package com.plv.foundationsdk.component.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.g;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.plv.foundationsdk.component.di.PLVDependModule;
import com.plv.foundationsdk.component.viewmodel.PLVViewModels;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class PLVDependManager {
    private static volatile PLVDependManager INSTANCE;
    final DependGraph dependGraph = new DependGraph();
    private PLVDependInstanceRepo instanceRepo;

    public static class DependGraph {
        private final Map<String, PLVDependModule.LazyProvider> dependencies = new HashMap(16);

        public final Map<String, PLVDependModule.LazyProvider> getDependencies() {
            return this.dependencies;
        }
    }

    private PLVDependManager() {
    }

    public static PLVDependManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVDependManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVDependManager();
                }
            }
        }
        return INSTANCE;
    }

    public PLVDependManager addModule(@NonNull PLVDependModule pLVDependModule) {
        return addModule(pLVDependModule, false);
    }

    @NonNull
    public synchronized <T> T get(@NonNull Class<T> cls) {
        return (T) get(cls.getName());
    }

    public synchronized PLVDependManager switchStore(@NonNull ViewModelStoreOwner viewModelStoreOwner) {
        return switchStore(viewModelStoreOwner.getViewModelStore());
    }

    public PLVDependManager addModule(@NonNull PLVDependModule pLVDependModule, boolean z2) {
        for (Map.Entry<String, PLVDependModule.LazyProvider> entry : pLVDependModule.dependencies.entrySet()) {
            if (!this.dependGraph.dependencies.containsKey(entry.getKey()) || z2) {
                this.dependGraph.dependencies.put(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    @NonNull
    public synchronized <T> T get(@NonNull String str) {
        return (T) ((PLVDependInstanceRepo) PLVSugarUtil.requireNotNull(this.instanceRepo)).getInstance(str);
    }

    public synchronized PLVDependManager switchStore(@NonNull ViewModelStore viewModelStore) {
        this.instanceRepo = (PLVDependInstanceRepo) PLVViewModels.on(viewModelStore).setFactory(new ViewModelProvider.Factory() { // from class: com.plv.foundationsdk.component.di.PLVDependManager.1
            private final ViewModelProvider.Factory defaultFactory = new ViewModelProvider.NewInstanceFactory();

            @Override // androidx.lifecycle.ViewModelProvider.Factory
            @NonNull
            public <T extends ViewModel> T create(@NonNull Class<T> cls) {
                return PLVDependInstanceRepo.class.isAssignableFrom(cls) ? new PLVDependInstanceRepo() : (T) this.defaultFactory.create(cls);
            }

            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public /* synthetic */ ViewModel create(Class cls, CreationExtras creationExtras) {
                return g.b(this, cls, creationExtras);
            }
        }).get(PLVDependInstanceRepo.class);
        return this;
    }
}
