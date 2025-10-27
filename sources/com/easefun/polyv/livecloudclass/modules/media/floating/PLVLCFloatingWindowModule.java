package com.easefun.polyv.livecloudclass.modules.media.floating;

import androidx.annotation.NonNull;
import com.plv.foundationsdk.component.di.PLVDependModule;

/* loaded from: classes3.dex */
public class PLVLCFloatingWindowModule extends PLVDependModule {
    public static final PLVLCFloatingWindowModule instance = new PLVLCFloatingWindowModule();

    public PLVLCFloatingWindowModule() {
        provide(new PLVDependModule.LazyProvider<PLVLCFloatingWindow>() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindowModule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVLCFloatingWindow onProvide() {
                return new PLVLCFloatingWindow();
            }
        });
    }
}
