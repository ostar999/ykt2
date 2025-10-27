package com.easefun.polyv.livecommon.module.modules.player.playback.di;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.PLVPlaybackCacheRepo;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheDatabaseDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheLocalStorageDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheMemoryDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheNetworkDataSource;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.PLVPlaybackCacheDatabase;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.config.PLVPlaybackCacheConfig;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheListViewModel;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.config.PLVPlaybackCacheVideoConfig;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.usecase.PLVPlaybackCacheListMergeUseCase;
import com.plv.foundationsdk.component.di.PLVDependModule;
import com.plv.livescenes.download.PLVDownloaderManager;
import com.plv.livescenes.download.api.PLVPlaybackDownloadApiManager;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheModule extends PLVDependModule {
    public static final PLVPlaybackCacheModule instance = new PLVPlaybackCacheModule();

    public PLVPlaybackCacheModule() {
        provide(new PLVDependModule.LazyProvider<PLVDownloaderManager>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVDownloaderManager onProvide() {
                return PLVDownloaderManager.getInstance();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackDownloadApiManager>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackDownloadApiManager onProvide() {
                return PLVPlaybackDownloadApiManager.getInstance();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheConfig>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheConfig onProvide() {
                return new PLVPlaybackCacheConfig();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheVideoConfig>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheVideoConfig onProvide() {
                return new PLVPlaybackCacheVideoConfig();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheDatabase>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheDatabase onProvide() {
                return PLVPlaybackCacheDatabase.getInstance((PLVPlaybackCacheConfig) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheConfig.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheDatabaseDataSource>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheDatabaseDataSource onProvide() {
                return PLVPlaybackCacheDatabaseDataSource.getInstance((PLVPlaybackCacheDatabase) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheDatabase.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheLocalStorageDataSource>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheLocalStorageDataSource onProvide() {
                return PLVPlaybackCacheLocalStorageDataSource.getInstance((PLVDownloaderManager) PLVPlaybackCacheModule.this.get(PLVDownloaderManager.class), (PLVPlaybackCacheConfig) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheConfig.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheMemoryDataSource>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheMemoryDataSource onProvide() {
                return new PLVPlaybackCacheMemoryDataSource();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheNetworkDataSource>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.9
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheNetworkDataSource onProvide() {
                return new PLVPlaybackCacheNetworkDataSource((PLVPlaybackDownloadApiManager) PLVPlaybackCacheModule.this.get(PLVPlaybackDownloadApiManager.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheRepo>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.10
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheRepo onProvide() {
                return PLVPlaybackCacheRepo.getInstance((PLVPlaybackCacheDatabaseDataSource) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheDatabaseDataSource.class), (PLVPlaybackCacheLocalStorageDataSource) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheLocalStorageDataSource.class), (PLVPlaybackCacheMemoryDataSource) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheMemoryDataSource.class), (PLVPlaybackCacheNetworkDataSource) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheNetworkDataSource.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheListMergeUseCase>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.11
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheListMergeUseCase onProvide() {
                return new PLVPlaybackCacheListMergeUseCase();
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheListViewModel>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheListViewModel onProvide() {
                return PLVPlaybackCacheListViewModel.getInstance((PLVPlaybackCacheRepo) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheRepo.class), (PLVPlaybackCacheListMergeUseCase) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheListMergeUseCase.class));
            }
        });
        provide(new PLVDependModule.LazyProvider<PLVPlaybackCacheVideoViewModel>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.di.PLVPlaybackCacheModule.13
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.component.di.PLVDependModule.LazyProvider
            @NonNull
            public PLVPlaybackCacheVideoViewModel onProvide() {
                return new PLVPlaybackCacheVideoViewModel((PLVPlaybackCacheRepo) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheRepo.class), (PLVPlaybackCacheVideoConfig) PLVPlaybackCacheModule.this.get(PLVPlaybackCacheVideoConfig.class));
            }
        });
    }
}
