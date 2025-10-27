package com.google.firebase.heartbeatinfo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.Dependency;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;

/* loaded from: classes4.dex */
public class DefaultHeartBeatInfo implements HeartBeatInfo {
    private HeartBeatInfoStorage storage;

    private DefaultHeartBeatInfo(Context context) {
        this.storage = HeartBeatInfoStorage.getInstance(context);
    }

    @NonNull
    public static Component<HeartBeatInfo> component() {
        return Component.builder(HeartBeatInfo.class).add(Dependency.required(Context.class)).factory(DefaultHeartBeatInfo$$Lambda$1.instance).build();
    }

    public static /* synthetic */ HeartBeatInfo lambda$component$0(ComponentContainer componentContainer) {
        return new DefaultHeartBeatInfo((Context) componentContainer.get(Context.class));
    }

    @Override // com.google.firebase.heartbeatinfo.HeartBeatInfo
    @NonNull
    public HeartBeatInfo.HeartBeat getHeartBeatCode(@NonNull String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean zShouldSendSdkHeartBeat = this.storage.shouldSendSdkHeartBeat(str, jCurrentTimeMillis);
        boolean zShouldSendGlobalHeartBeat = this.storage.shouldSendGlobalHeartBeat(jCurrentTimeMillis);
        return (zShouldSendSdkHeartBeat && zShouldSendGlobalHeartBeat) ? HeartBeatInfo.HeartBeat.COMBINED : zShouldSendGlobalHeartBeat ? HeartBeatInfo.HeartBeat.GLOBAL : zShouldSendSdkHeartBeat ? HeartBeatInfo.HeartBeat.SDK : HeartBeatInfo.HeartBeat.NONE;
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    @VisibleForTesting
    public DefaultHeartBeatInfo(HeartBeatInfoStorage heartBeatInfoStorage) {
        this.storage = heartBeatInfoStorage;
    }
}
