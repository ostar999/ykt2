package com.google.android.gms.internal.icing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzaj implements AppIndexApi {
    private static final String TAG = "zzaj";

    public static abstract class zza<T extends Result> extends BaseImplementation.ApiMethodImpl<T, zzah> {
        public zza(GoogleApiClient googleApiClient) {
            super(zzf.zzg, googleApiClient);
        }

        @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
        public /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
            zza((zzaa) ((zzah) anyClient).getService());
        }

        public abstract void zza(zzaa zzaaVar) throws RemoteException;
    }

    @Deprecated
    public static final class zzb implements AppIndexApi.ActionResult {
        private zzaj zzau;
        private PendingResult<Status> zzav;
        private Action zzaw;

        public zzb(zzaj zzajVar, PendingResult<Status> pendingResult, Action action) {
            this.zzau = zzajVar;
            this.zzav = pendingResult;
            this.zzaw = action;
        }

        @Override // com.google.android.gms.appindexing.AppIndexApi.ActionResult
        public final PendingResult<Status> end(GoogleApiClient googleApiClient) {
            return this.zzau.zza(googleApiClient, zzag.zza(this.zzaw, System.currentTimeMillis(), googleApiClient.getContext().getPackageName(), 2));
        }

        @Override // com.google.android.gms.appindexing.AppIndexApi.ActionResult
        public final PendingResult<Status> getPendingResult() {
            return this.zzav;
        }
    }

    public static final class zzc extends zzae<Status> {
        public zzc(BaseImplementation.ResultHolder<Status> resultHolder) {
            super(resultHolder);
        }

        @Override // com.google.android.gms.internal.icing.zzae, com.google.android.gms.internal.icing.zzac
        public final void zza(Status status) {
            this.zzas.setResult(status);
        }
    }

    public static abstract class zzd<T extends Result> extends zza<Status> {
        public zzd(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override // com.google.android.gms.common.api.internal.BasePendingResult
        public /* synthetic */ Result createFailedResult(Status status) {
            return status;
        }
    }

    private static void zzb(String str, Uri uri) {
        if (zza(uri)) {
            if (uri.getHost().isEmpty()) {
                String strValueOf = String.valueOf(uri);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 98);
                sb.append("AppIndex: The web URL must have a host (follow the format http(s)://<host>/<path>). Provided URI: ");
                sb.append(strValueOf);
                throw new IllegalArgumentException(sb.toString());
            }
            return;
        }
        if (!zzb(uri)) {
            String strValueOf2 = String.valueOf(uri);
            StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 176);
            sb2.append("AppIndex: The URI scheme must either be 'http(s)' or 'android-app'. If the latter, it must follow the format 'android-app://<package_name>/<scheme>/<host_path>'. Provided URI: ");
            sb2.append(strValueOf2);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (str != null && !str.equals(uri.getHost())) {
            String strValueOf3 = String.valueOf(uri);
            StringBuilder sb3 = new StringBuilder(strValueOf3.length() + 150);
            sb3.append("AppIndex: The android-app URI host must match the package name and follow the format android-app://<package_name>/<scheme>/<host_path>. Provided URI: ");
            sb3.append(strValueOf3);
            throw new IllegalArgumentException(sb3.toString());
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.isEmpty() || pathSegments.get(0).isEmpty()) {
            String strValueOf4 = String.valueOf(uri);
            StringBuilder sb4 = new StringBuilder(strValueOf4.length() + 128);
            sb4.append("AppIndex: The app URI scheme must exist and follow the format android-app://<package_name>/<scheme>/<host_path>). Provided URI: ");
            sb4.append(strValueOf4);
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final AppIndexApi.ActionResult action(GoogleApiClient googleApiClient, Action action) {
        return new zzb(this, zza(googleApiClient, action, 1), action);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final PendingResult<Status> end(GoogleApiClient googleApiClient, Action action) {
        return zza(googleApiClient, action, 2);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final PendingResult<Status> start(GoogleApiClient googleApiClient, Action action) {
        return zza(googleApiClient, action, 1);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final PendingResult<Status> view(GoogleApiClient googleApiClient, Activity activity, Intent intent, String str, Uri uri, List<AppIndexApi.AppIndexingLink> list) {
        String packageName = googleApiClient.getContext().getPackageName();
        if (list != null) {
            Iterator<AppIndexApi.AppIndexingLink> it = list.iterator();
            while (it.hasNext()) {
                zzb(null, it.next().appIndexingUrl);
            }
        }
        return zza(googleApiClient, new zzw(packageName, intent, str, uri, null, list, 1));
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final PendingResult<Status> viewEnd(GoogleApiClient googleApiClient, Activity activity, Intent intent) {
        return zza(googleApiClient, new zzz().zza(zzw.zza(googleApiClient.getContext().getPackageName(), intent)).zza(System.currentTimeMillis()).zzb(0).zzc(2).zzd());
    }

    public final PendingResult<Status> zza(GoogleApiClient googleApiClient, zzw... zzwVarArr) {
        return googleApiClient.enqueue(new zzai(this, googleApiClient, zzwVarArr));
    }

    public static Intent zza(String str, Uri uri) {
        zzb(str, uri);
        if (zza(uri)) {
            return new Intent("android.intent.action.VIEW", uri);
        }
        if (!zzb(uri)) {
            String strValueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 70);
            sb.append("appIndexingUri is neither an HTTP(S) URL nor an \"android-app://\" URL: ");
            sb.append(strValueOf);
            throw new RuntimeException(sb.toString());
        }
        List<String> pathSegments = uri.getPathSegments();
        String str2 = pathSegments.get(0);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(str2);
        if (pathSegments.size() > 1) {
            builder.authority(pathSegments.get(1));
            for (int i2 = 2; i2 < pathSegments.size(); i2++) {
                builder.appendPath(pathSegments.get(i2));
            }
        } else {
            String str3 = TAG;
            String strValueOf2 = String.valueOf(uri);
            StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 88);
            sb2.append("The app URI must have the format: android-app://<package_name>/<scheme>/<path>. But got ");
            sb2.append(strValueOf2);
            Log.e(str3, sb2.toString());
        }
        builder.encodedQuery(uri.getEncodedQuery());
        builder.encodedFragment(uri.getEncodedFragment());
        return new Intent("android.intent.action.VIEW", builder.build());
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final PendingResult<Status> view(GoogleApiClient googleApiClient, Activity activity, Uri uri, String str, Uri uri2, List<AppIndexApi.AppIndexingLink> list) {
        String packageName = googleApiClient.getContext().getPackageName();
        zzb(packageName, uri);
        return view(googleApiClient, activity, zza(packageName, uri), str, uri2, list);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public final PendingResult<Status> viewEnd(GoogleApiClient googleApiClient, Activity activity, Uri uri) {
        return viewEnd(googleApiClient, activity, zza(googleApiClient.getContext().getPackageName(), uri));
    }

    private static boolean zzb(Uri uri) {
        return "android-app".equals(uri.getScheme());
    }

    private static boolean zza(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, Action action, int i2) {
        return zza(googleApiClient, zzag.zza(action, System.currentTimeMillis(), googleApiClient.getContext().getPackageName(), i2));
    }
}
