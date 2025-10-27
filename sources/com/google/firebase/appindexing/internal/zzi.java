package com.google.firebase.appindexing.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.icing.zzhl;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.internal.Thing;

/* loaded from: classes4.dex */
public final class zzi extends FirebaseAppIndex {
    private static String[] zzez = {"com.google.android.googlequicksearchbox", "com.google.android.gms"};

    @NonNull
    private final Context zzcs;

    @NonNull
    private final GoogleApi<?> zzfa;

    @VisibleForTesting
    private final zzk zzfb;

    public zzi(@NonNull Context context) {
        this(context, new zzh(context));
    }

    private final Task<Void> zza(@NonNull zzy zzyVar) {
        return this.zzfb.zzb(zzyVar);
    }

    @Override // com.google.firebase.appindexing.FirebaseAppIndex
    public final Task<Void> remove(String... strArr) {
        return zza(new zzy(3, null, strArr, null, null, null, null));
    }

    @Override // com.google.firebase.appindexing.FirebaseAppIndex
    public final Task<Void> removeAll() {
        return zza(new zzy(4, null, null, null, null, null, null));
    }

    @Override // com.google.firebase.appindexing.FirebaseAppIndex
    public final Task<Void> update(Indexable... indexableArr) {
        Thing[] thingArr;
        Context context;
        if (indexableArr == null) {
            thingArr = null;
        } else {
            try {
                thingArr = new Thing[indexableArr.length];
                System.arraycopy(indexableArr, 0, thingArr, 0, indexableArr.length);
            } catch (ArrayStoreException unused) {
                return Tasks.forException(new FirebaseAppIndexingInvalidArgumentException("Custom Indexable-objects are not allowed. Please use the 'Indexables'-class for creating the objects."));
            }
        }
        if (zzhl.zzeb() && PlatformVersion.isAtLeastKitKat() && (context = this.zzcs) != null && thingArr != null && thingArr.length > 0) {
            zzaa zzabVar = Build.VERSION.SDK_INT >= 28 ? new zzab(context) : new zzz(context);
            for (Thing thing : thingArr) {
                if (thing != null) {
                    Thing.zza zzaVarZzac = thing.zzac();
                    String[] stringArray = (zzaVarZzac.zze() == null || !(zzaVarZzac.zze().get("sliceUri") instanceof String[])) ? null : zzaVarZzac.zze().getStringArray("sliceUri");
                    boolean z2 = stringArray != null && stringArray.length > 0;
                    Thing.zza zzaVarZzac2 = thing.zzac();
                    boolean[] booleanArray = (zzaVarZzac2.zze() == null || !(zzaVarZzac2.zze().get("grantSlicePermission") instanceof boolean[])) ? null : zzaVarZzac2.zze().getBooleanArray("grantSlicePermission");
                    boolean z3 = booleanArray != null && booleanArray.length > 0 && booleanArray[0];
                    if (z2 && z3) {
                        String str = stringArray[0];
                        for (String str2 : zzez) {
                            try {
                                zzabVar.grantSlicePermission(str2, Uri.parse(str));
                            } catch (Exception e2) {
                                String strValueOf = String.valueOf(e2);
                                StringBuilder sb = new StringBuilder(strValueOf.length() + 48);
                                sb.append("Error trying to grant permission to Slice Uris: ");
                                sb.append(strValueOf);
                                String string = sb.toString();
                                if (zzt.isLoggable(5)) {
                                    Log.w(FirebaseAppIndex.APP_INDEXING_API_TAG, string);
                                }
                            }
                        }
                    }
                }
            }
        }
        return zza(new zzy(1, thingArr));
    }

    @VisibleForTesting
    private zzi(@NonNull Context context, @NonNull GoogleApi<Api.ApiOptions.NoOptions> googleApi) {
        this.zzfa = googleApi;
        this.zzcs = context;
        this.zzfb = new zzk(googleApi);
    }
}
