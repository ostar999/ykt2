package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.BaseGmsClient;

/* loaded from: classes3.dex */
final class zabm implements BaseGmsClient.SignOutCallbacks {
    final /* synthetic */ GoogleApiManager.zaa zaiq;

    public zabm(GoogleApiManager.zaa zaaVar) {
        this.zaiq = zaaVar;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.SignOutCallbacks
    public final void onSignOutComplete() {
        GoogleApiManager.this.handler.post(new zabl(this));
    }
}
