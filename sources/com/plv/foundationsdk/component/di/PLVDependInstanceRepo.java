package com.plv.foundationsdk.component.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.plv.foundationsdk.component.di.PLVDependModule;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
class PLVDependInstanceRepo extends ViewModel {
    private final Map<String, Object> instances = new HashMap(16);

    @NonNull
    public final synchronized <T> T getInstance(@NonNull String str) {
        if (this.instances.containsKey(str)) {
            return (T) PLVSugarUtil.requireNotNull(this.instances.get(str));
        }
        T t2 = (T) PLVSugarUtil.requireNotNull(((PLVDependModule.LazyProvider) PLVSugarUtil.requireNotNull(PLVDependManager.getInstance().dependGraph.getDependencies().get(str))).onProvide());
        this.instances.put(str, t2);
        return t2;
    }

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        for (Object obj : this.instances.values()) {
            if (obj instanceof IPLVLifecycleAwareDependComponent) {
                ((IPLVLifecycleAwareDependComponent) obj).onCleared();
            }
        }
        this.instances.clear();
    }
}
