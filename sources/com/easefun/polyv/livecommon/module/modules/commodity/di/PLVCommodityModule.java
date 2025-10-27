package com.easefun.polyv.livecommon.module.modules.commodity.di;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel;
import com.plv.foundationsdk.component.di.PLVDependModule;

/* loaded from: classes3.dex */
public class PLVCommodityModule extends PLVDependModule {
    public static final PLVCommodityModule instance = new PLVCommodityModule();

    public PLVCommodityModule() {
        provide(new PLVDependModule.LazyProvider<PLVCommodityRepo>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.di.PLVCommodityModule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVCommodityRepo onProvide() {
                return new PLVCommodityRepo();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVCommodityViewModel>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.di.PLVCommodityModule.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVCommodityViewModel onProvide() {
                return new PLVCommodityViewModel((PLVCommodityRepo) PLVCommodityModule.this.get(PLVCommodityRepo.class));
            }
        });
    }
}
