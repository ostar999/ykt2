package com.easefun.polyv.livecommon.module.modules.beauty.di;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.beauty.model.PLVBeautyRepo;
import com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautyLocalDataSource;
import com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautySdkDataSource;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.PLVBeautyViewModel;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautyOptionListInitUseCase;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautyResetUseCase;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautySwitchUseCase;
import com.plv.foundationsdk.component.di.PLVDependModule;

/* loaded from: classes3.dex */
public class PLVBeautyModule extends PLVDependModule {
    public static final PLVBeautyModule instance = new PLVBeautyModule();

    public PLVBeautyModule() {
        provide(new PLVDependModule.LazyProvider<PLVBeautyLocalDataSource>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautyLocalDataSource onProvide() {
                return new PLVBeautyLocalDataSource();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVBeautySdkDataSource>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautySdkDataSource onProvide() {
                return new PLVBeautySdkDataSource();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVBeautyRepo>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautyRepo onProvide() {
                return new PLVBeautyRepo((PLVBeautyLocalDataSource) PLVBeautyModule.this.get(PLVBeautyLocalDataSource.class), (PLVBeautySdkDataSource) PLVBeautyModule.this.get(PLVBeautySdkDataSource.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVBeautyOptionListInitUseCase>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautyOptionListInitUseCase onProvide() {
                return new PLVBeautyOptionListInitUseCase((PLVBeautyRepo) PLVBeautyModule.this.get(PLVBeautyRepo.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVBeautySwitchUseCase>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautySwitchUseCase onProvide() {
                return new PLVBeautySwitchUseCase((PLVBeautyRepo) PLVBeautyModule.this.get(PLVBeautyRepo.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVBeautyResetUseCase>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautyResetUseCase onProvide() {
                return new PLVBeautyResetUseCase((PLVBeautyRepo) PLVBeautyModule.this.get(PLVBeautyRepo.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVBeautyViewModel>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.di.PLVBeautyModule.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVBeautyViewModel onProvide() {
                return new PLVBeautyViewModel((PLVBeautyRepo) PLVBeautyModule.this.get(PLVBeautyRepo.class), (PLVBeautyOptionListInitUseCase) PLVBeautyModule.this.get(PLVBeautyOptionListInitUseCase.class), (PLVBeautySwitchUseCase) PLVBeautyModule.this.get(PLVBeautySwitchUseCase.class), (PLVBeautyResetUseCase) PLVBeautyModule.this.get(PLVBeautyResetUseCase.class));
            }
        });
    }
}
