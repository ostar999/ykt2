package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;

/* loaded from: classes3.dex */
public class GoogleApiAvailabilityCache {
    private final SparseIntArray zapd;
    private GoogleApiAvailabilityLight zape;

    public GoogleApiAvailabilityCache() {
        this(GoogleApiAvailability.getInstance());
    }

    public void flush() {
        this.zapd.clear();
    }

    public int getClientAvailability(@NonNull Context context, @NonNull Api.Client client) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(client);
        int iIsGooglePlayServicesAvailable = 0;
        if (!client.requiresGooglePlayServices()) {
            return 0;
        }
        int minApkVersion = client.getMinApkVersion();
        int i2 = this.zapd.get(minApkVersion, -1);
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= this.zapd.size()) {
                iIsGooglePlayServicesAvailable = i2;
                break;
            }
            int iKeyAt = this.zapd.keyAt(i3);
            if (iKeyAt > minApkVersion && this.zapd.get(iKeyAt) == 0) {
                break;
            }
            i3++;
        }
        if (iIsGooglePlayServicesAvailable == -1) {
            iIsGooglePlayServicesAvailable = this.zape.isGooglePlayServicesAvailable(context, minApkVersion);
        }
        this.zapd.put(minApkVersion, iIsGooglePlayServicesAvailable);
        return iIsGooglePlayServicesAvailable;
    }

    public GoogleApiAvailabilityCache(@NonNull GoogleApiAvailabilityLight googleApiAvailabilityLight) {
        this.zapd = new SparseIntArray();
        Preconditions.checkNotNull(googleApiAvailabilityLight);
        this.zape = googleApiAvailabilityLight;
    }
}
