package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;

/* loaded from: classes3.dex */
final class zaaq extends zaau {
    private final /* synthetic */ zaak zafz;
    private final ArrayList<Api.Client> zags;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaaq(zaak zaakVar, ArrayList<Api.Client> arrayList) {
        super(zaakVar, null);
        this.zafz = zaakVar;
        this.zags = arrayList;
    }

    @Override // com.google.android.gms.common.api.internal.zaau
    @WorkerThread
    public final void zaal() {
        this.zafz.zafv.zaeh.zahe = this.zafz.zaar();
        ArrayList<Api.Client> arrayList = this.zags;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Api.Client client = arrayList.get(i2);
            i2++;
            client.getRemoteService(this.zafz.zagj, this.zafz.zafv.zaeh.zahe);
        }
    }
}
