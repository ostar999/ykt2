package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.BinderThread;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import cn.hutool.core.date.DatePattern;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.IGmsCallbacks;
import com.google.android.gms.common.internal.IGmsServiceBroker;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class BaseGmsClient<T extends IInterface> {

    @KeepForSdk
    public static final int CONNECT_STATE_CONNECTED = 4;

    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTED = 1;

    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTING = 5;

    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";

    @KeepForSdk
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private int zzbu;
    private long zzbv;
    private long zzbw;
    private int zzbx;
    private long zzby;

    @VisibleForTesting
    private zzh zzbz;
    private final Looper zzca;
    private final GmsClientSupervisor zzcb;
    private final GoogleApiAvailabilityLight zzcc;
    private final Object zzcd;

    @GuardedBy("mServiceBrokerLock")
    private IGmsServiceBroker zzce;

    @VisibleForTesting
    protected ConnectionProgressReportCallbacks zzcf;

    @GuardedBy("mLock")
    private T zzcg;
    private final ArrayList<zzc<?>> zzch;

    @GuardedBy("mLock")
    private zze zzci;

    @GuardedBy("mLock")
    private int zzcj;
    private final BaseConnectionCallbacks zzck;
    private final BaseOnConnectionFailedListener zzcl;
    private final int zzcm;
    private final String zzcn;
    private ConnectionResult zzco;
    private boolean zzcp;
    private volatile com.google.android.gms.common.internal.zzb zzcq;

    @VisibleForTesting
    protected AtomicInteger zzcr;
    private static final Feature[] zzbt = new Feature[0];

    @KeepForSdk
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", "service_googleme"};

    @KeepForSdk
    public interface BaseConnectionCallbacks {
        @KeepForSdk
        void onConnected(@Nullable Bundle bundle);

        @KeepForSdk
        void onConnectionSuspended(int i2);
    }

    @KeepForSdk
    public interface BaseOnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    @KeepForSdk
    public interface ConnectionProgressReportCallbacks {
        @KeepForSdk
        void onReportServiceBinding(@NonNull ConnectionResult connectionResult);
    }

    public class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        @KeepForSdk
        public LegacyClientCallbackAdapter() {
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzcl != null) {
                BaseGmsClient.this.zzcl.onConnectionFailed(connectionResult);
            }
        }
    }

    @KeepForSdk
    public interface SignOutCallbacks {
        @KeepForSdk
        void onSignOutComplete();
    }

    public abstract class zza extends zzc<Boolean> {
        private final int statusCode;
        private final Bundle zzcs;

        @BinderThread
        public zza(int i2, Bundle bundle) {
            super(Boolean.TRUE);
            this.statusCode = i2;
            this.zzcs = bundle;
        }

        public abstract void zza(ConnectionResult connectionResult);

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zzc
        public final /* synthetic */ void zza(Boolean bool) {
            if (bool == null) {
                BaseGmsClient.this.zza(1, (int) null);
                return;
            }
            int i2 = this.statusCode;
            if (i2 == 0) {
                if (zzm()) {
                    return;
                }
                BaseGmsClient.this.zza(1, (int) null);
                zza(new ConnectionResult(8, null));
                return;
            }
            if (i2 == 10) {
                BaseGmsClient.this.zza(1, (int) null);
                throw new IllegalStateException(String.format("A fatal developer error has occurred. Class name: %s. Start service action: %s. Service Descriptor: %s. ", getClass().getSimpleName(), BaseGmsClient.this.getStartServiceAction(), BaseGmsClient.this.getServiceDescriptor()));
            }
            BaseGmsClient.this.zza(1, (int) null);
            Bundle bundle = this.zzcs;
            zza(new ConnectionResult(this.statusCode, bundle != null ? (PendingIntent) bundle.getParcelable(BaseGmsClient.KEY_PENDING_INTENT) : null));
        }

        public abstract boolean zzm();

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zzc
        public final void zzn() {
        }
    }

    public final class zzb extends com.google.android.gms.internal.common.zze {
        public zzb(Looper looper) {
            super(looper);
        }

        private static void zza(Message message) {
            zzc zzcVar = (zzc) message.obj;
            zzcVar.zzn();
            zzcVar.unregister();
        }

        private static boolean zzb(Message message) {
            int i2 = message.what;
            return i2 == 2 || i2 == 1 || i2 == 7;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (BaseGmsClient.this.zzcr.get() != message.arg1) {
                if (zzb(message)) {
                    zza(message);
                    return;
                }
                return;
            }
            int i2 = message.what;
            if ((i2 == 1 || i2 == 7 || ((i2 == 4 && !BaseGmsClient.this.enableLocalFallback()) || message.what == 5)) && !BaseGmsClient.this.isConnecting()) {
                zza(message);
                return;
            }
            int i3 = message.what;
            if (i3 == 4) {
                BaseGmsClient.this.zzco = new ConnectionResult(message.arg2);
                if (BaseGmsClient.this.zzl() && !BaseGmsClient.this.zzcp) {
                    BaseGmsClient.this.zza(3, (int) null);
                    return;
                }
                ConnectionResult connectionResult = BaseGmsClient.this.zzco != null ? BaseGmsClient.this.zzco : new ConnectionResult(8);
                BaseGmsClient.this.zzcf.onReportServiceBinding(connectionResult);
                BaseGmsClient.this.onConnectionFailed(connectionResult);
                return;
            }
            if (i3 == 5) {
                ConnectionResult connectionResult2 = BaseGmsClient.this.zzco != null ? BaseGmsClient.this.zzco : new ConnectionResult(8);
                BaseGmsClient.this.zzcf.onReportServiceBinding(connectionResult2);
                BaseGmsClient.this.onConnectionFailed(connectionResult2);
                return;
            }
            if (i3 == 3) {
                Object obj = message.obj;
                ConnectionResult connectionResult3 = new ConnectionResult(message.arg2, obj instanceof PendingIntent ? (PendingIntent) obj : null);
                BaseGmsClient.this.zzcf.onReportServiceBinding(connectionResult3);
                BaseGmsClient.this.onConnectionFailed(connectionResult3);
                return;
            }
            if (i3 == 6) {
                BaseGmsClient.this.zza(5, (int) null);
                if (BaseGmsClient.this.zzck != null) {
                    BaseGmsClient.this.zzck.onConnectionSuspended(message.arg2);
                }
                BaseGmsClient.this.onConnectionSuspended(message.arg2);
                BaseGmsClient.this.zza(5, 1, (int) null);
                return;
            }
            if (i3 == 2 && !BaseGmsClient.this.isConnected()) {
                zza(message);
                return;
            }
            if (zzb(message)) {
                ((zzc) message.obj).zzo();
                return;
            }
            int i4 = message.what;
            StringBuilder sb = new StringBuilder(45);
            sb.append("Don't know how to handle message: ");
            sb.append(i4);
            Log.wtf("GmsClient", sb.toString(), new Exception());
        }
    }

    public abstract class zzc<TListener> {
        private TListener zzcu;
        private boolean zzcv = false;

        public zzc(TListener tlistener) {
            this.zzcu = tlistener;
        }

        public final void removeListener() {
            synchronized (this) {
                this.zzcu = null;
            }
        }

        public final void unregister() {
            removeListener();
            synchronized (BaseGmsClient.this.zzch) {
                BaseGmsClient.this.zzch.remove(this);
            }
        }

        public abstract void zza(TListener tlistener);

        public abstract void zzn();

        public final void zzo() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.zzcu;
                if (this.zzcv) {
                    String strValueOf = String.valueOf(this);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 47);
                    sb.append("Callback proxy ");
                    sb.append(strValueOf);
                    sb.append(" being reused. This is not safe.");
                    Log.w("GmsClient", sb.toString());
                }
            }
            if (tlistener != null) {
                try {
                    zza(tlistener);
                } catch (RuntimeException e2) {
                    zzn();
                    throw e2;
                }
            } else {
                zzn();
            }
            synchronized (this) {
                this.zzcv = true;
            }
            unregister();
        }
    }

    @VisibleForTesting
    public static final class zzd extends IGmsCallbacks.zza {
        private BaseGmsClient zzcw;
        private final int zzcx;

        public zzd(@NonNull BaseGmsClient baseGmsClient, int i2) {
            this.zzcw = baseGmsClient;
            this.zzcx = i2;
        }

        @Override // com.google.android.gms.common.internal.IGmsCallbacks
        @BinderThread
        public final void onPostInitComplete(int i2, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            Preconditions.checkNotNull(this.zzcw, "onPostInitComplete can be called only once per call to getRemoteService");
            this.zzcw.onPostInitHandler(i2, iBinder, bundle, this.zzcx);
            this.zzcw = null;
        }

        @Override // com.google.android.gms.common.internal.IGmsCallbacks
        @BinderThread
        public final void zza(int i2, @Nullable Bundle bundle) {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }

        @Override // com.google.android.gms.common.internal.IGmsCallbacks
        @BinderThread
        public final void zza(int i2, @NonNull IBinder iBinder, @NonNull com.google.android.gms.common.internal.zzb zzbVar) {
            Preconditions.checkNotNull(this.zzcw, "onPostInitCompleteWithConnectionInfo can be called only once per call togetRemoteService");
            Preconditions.checkNotNull(zzbVar);
            this.zzcw.zza(zzbVar);
            onPostInitComplete(i2, iBinder, zzbVar.zzda);
        }
    }

    @VisibleForTesting
    public final class zze implements ServiceConnection {
        private final int zzcx;

        public zze(int i2) {
            this.zzcx = i2;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder == null) {
                BaseGmsClient.this.zzb(16);
                return;
            }
            synchronized (BaseGmsClient.this.zzcd) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                baseGmsClient.zzce = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGmsServiceBroker)) ? new IGmsServiceBroker.Stub.zza(iBinder) : (IGmsServiceBroker) iInterfaceQueryLocalInterface;
            }
            BaseGmsClient.this.zza(0, (Bundle) null, this.zzcx);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (BaseGmsClient.this.zzcd) {
                BaseGmsClient.this.zzce = null;
            }
            Handler handler = BaseGmsClient.this.mHandler;
            handler.sendMessage(handler.obtainMessage(6, this.zzcx, 1));
        }
    }

    public final class zzf extends zza {
        private final IBinder zzcy;

        @BinderThread
        public zzf(int i2, IBinder iBinder, Bundle bundle) {
            super(i2, bundle);
            this.zzcy = iBinder;
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        public final void zza(ConnectionResult connectionResult) {
            if (BaseGmsClient.this.zzcl != null) {
                BaseGmsClient.this.zzcl.onConnectionFailed(connectionResult);
            }
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        public final boolean zzm() throws RemoteException {
            try {
                String interfaceDescriptor = this.zzcy.getInterfaceDescriptor();
                if (!BaseGmsClient.this.getServiceDescriptor().equals(interfaceDescriptor)) {
                    String serviceDescriptor = BaseGmsClient.this.getServiceDescriptor();
                    StringBuilder sb = new StringBuilder(String.valueOf(serviceDescriptor).length() + 34 + String.valueOf(interfaceDescriptor).length());
                    sb.append("service descriptor mismatch: ");
                    sb.append(serviceDescriptor);
                    sb.append(" vs. ");
                    sb.append(interfaceDescriptor);
                    Log.e("GmsClient", sb.toString());
                    return false;
                }
                IInterface iInterfaceCreateServiceInterface = BaseGmsClient.this.createServiceInterface(this.zzcy);
                if (iInterfaceCreateServiceInterface == null || !(BaseGmsClient.this.zza(2, 4, (int) iInterfaceCreateServiceInterface) || BaseGmsClient.this.zza(3, 4, (int) iInterfaceCreateServiceInterface))) {
                    return false;
                }
                BaseGmsClient.this.zzco = null;
                Bundle connectionHint = BaseGmsClient.this.getConnectionHint();
                if (BaseGmsClient.this.zzck == null) {
                    return true;
                }
                BaseGmsClient.this.zzck.onConnected(connectionHint);
                return true;
            } catch (RemoteException unused) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }
    }

    public final class zzg extends zza {
        @BinderThread
        public zzg(int i2, @Nullable Bundle bundle) {
            super(i2, null);
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        public final void zza(ConnectionResult connectionResult) {
            if (BaseGmsClient.this.enableLocalFallback() && BaseGmsClient.this.zzl()) {
                BaseGmsClient.this.zzb(16);
            } else {
                BaseGmsClient.this.zzcf.onReportServiceBinding(connectionResult);
                BaseGmsClient.this.onConnectionFailed(connectionResult);
            }
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.zza
        public final boolean zzm() {
            BaseGmsClient.this.zzcf.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
            return true;
        }
    }

    @KeepForSdk
    public BaseGmsClient(Context context, Looper looper, int i2, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailabilityLight.getInstance(), i2, (BaseConnectionCallbacks) Preconditions.checkNotNull(baseConnectionCallbacks), (BaseOnConnectionFailedListener) Preconditions.checkNotNull(baseOnConnectionFailedListener), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(com.google.android.gms.common.internal.zzb zzbVar) {
        this.zzcq = zzbVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(int i2) {
        int i3;
        if (zzk()) {
            this.zzcp = true;
            i3 = 5;
        } else {
            i3 = 4;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(i3, this.zzcr.get(), 16));
    }

    @Nullable
    private final String zzj() {
        String str = this.zzcn;
        return str == null ? this.mContext.getClass().getName() : str;
    }

    private final boolean zzk() {
        boolean z2;
        synchronized (this.mLock) {
            z2 = this.zzcj == 3;
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzl() throws ClassNotFoundException {
        if (this.zzcp || TextUtils.isEmpty(getServiceDescriptor()) || TextUtils.isEmpty(getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    @KeepForSdk
    public void checkAvailabilityAndConnect() {
        int iIsGooglePlayServicesAvailable = this.zzcc.isGooglePlayServicesAvailable(this.mContext, getMinApkVersion());
        if (iIsGooglePlayServicesAvailable == 0) {
            connect(new LegacyClientCallbackAdapter());
        } else {
            zza(1, (int) null);
            triggerNotAvailable(new LegacyClientCallbackAdapter(), iIsGooglePlayServicesAvailable, null);
        }
    }

    @KeepForSdk
    public final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @KeepForSdk
    public void connect(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzcf = (ConnectionProgressReportCallbacks) Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        zza(2, (int) null);
    }

    @Nullable
    @KeepForSdk
    public abstract T createServiceInterface(IBinder iBinder);

    @KeepForSdk
    public void disconnect() {
        this.zzcr.incrementAndGet();
        synchronized (this.zzch) {
            int size = this.zzch.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.zzch.get(i2).removeListener();
            }
            this.zzch.clear();
        }
        synchronized (this.zzcd) {
            this.zzce = null;
        }
        zza(1, (int) null);
    }

    @KeepForSdk
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i2;
        T t2;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.mLock) {
            i2 = this.zzcj;
            t2 = this.zzcg;
        }
        synchronized (this.zzcd) {
            iGmsServiceBroker = this.zzce;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        if (i2 == 1) {
            printWriter.print("DISCONNECTED");
        } else if (i2 == 2) {
            printWriter.print("REMOTE_CONNECTING");
        } else if (i2 == 3) {
            printWriter.print("LOCAL_CONNECTING");
        } else if (i2 == 4) {
            printWriter.print("CONNECTED");
        } else if (i2 != 5) {
            printWriter.print("UNKNOWN");
        } else {
            printWriter.print("DISCONNECTING");
        }
        printWriter.append(" mService=");
        if (t2 == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(t2.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN, Locale.US);
        if (this.zzbw > 0) {
            PrintWriter printWriterAppend = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j2 = this.zzbw;
            String str2 = simpleDateFormat.format(new Date(this.zzbw));
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 21);
            sb.append(j2);
            sb.append(" ");
            sb.append(str2);
            printWriterAppend.println(sb.toString());
        }
        if (this.zzbv > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            int i3 = this.zzbu;
            if (i3 == 1) {
                printWriter.append("CAUSE_SERVICE_DISCONNECTED");
            } else if (i3 != 2) {
                printWriter.append((CharSequence) String.valueOf(i3));
            } else {
                printWriter.append("CAUSE_NETWORK_LOST");
            }
            PrintWriter printWriterAppend2 = printWriter.append(" lastSuspendedTime=");
            long j3 = this.zzbv;
            String str3 = simpleDateFormat.format(new Date(this.zzbv));
            StringBuilder sb2 = new StringBuilder(String.valueOf(str3).length() + 21);
            sb2.append(j3);
            sb2.append(" ");
            sb2.append(str3);
            printWriterAppend2.println(sb2.toString());
        }
        if (this.zzby > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzbx));
            PrintWriter printWriterAppend3 = printWriter.append(" lastFailedTime=");
            long j4 = this.zzby;
            String str4 = simpleDateFormat.format(new Date(this.zzby));
            StringBuilder sb3 = new StringBuilder(String.valueOf(str4).length() + 21);
            sb3.append(j4);
            sb3.append(" ");
            sb3.append(str4);
            printWriterAppend3.println(sb3.toString());
        }
    }

    @KeepForSdk
    public boolean enableLocalFallback() {
        return false;
    }

    @KeepForSdk
    public Account getAccount() {
        return null;
    }

    @KeepForSdk
    public Feature[] getApiFeatures() {
        return zzbt;
    }

    @Nullable
    @KeepForSdk
    public final Feature[] getAvailableFeatures() {
        com.google.android.gms.common.internal.zzb zzbVar = this.zzcq;
        if (zzbVar == null) {
            return null;
        }
        return zzbVar.zzdb;
    }

    @KeepForSdk
    public Bundle getConnectionHint() {
        return null;
    }

    @KeepForSdk
    public final Context getContext() {
        return this.mContext;
    }

    @KeepForSdk
    public String getEndpointPackageName() {
        zzh zzhVar;
        if (!isConnected() || (zzhVar = this.zzbz) == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return zzhVar.getPackageName();
    }

    @KeepForSdk
    public Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    @Nullable
    @KeepForSdk
    public String getLocalStartServiceAction() {
        return null;
    }

    @KeepForSdk
    public final Looper getLooper() {
        return this.zzca;
    }

    @KeepForSdk
    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    @KeepForSdk
    @WorkerThread
    public void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzcm);
        getServiceRequest.zzy = this.mContext.getPackageName();
        getServiceRequest.zzdk = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzdj = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (requiresSignIn()) {
            getServiceRequest.zzdl = getAccount() != null ? getAccount() : new Account("<<default account>>", AccountType.GOOGLE);
            if (iAccountAccessor != null) {
                getServiceRequest.zzdi = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest.zzdl = getAccount();
        }
        getServiceRequest.zzdm = zzbt;
        getServiceRequest.zzdn = getApiFeatures();
        try {
            synchronized (this.zzcd) {
                IGmsServiceBroker iGmsServiceBroker = this.zzce;
                if (iGmsServiceBroker != null) {
                    iGmsServiceBroker.getService(new zzd(this, this.zzcr.get()), getServiceRequest);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e2) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            triggerConnectionSuspended(1);
        } catch (RemoteException e3) {
            e = e3;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzcr.get());
        } catch (SecurityException e4) {
            throw e4;
        } catch (RuntimeException e5) {
            e = e5;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzcr.get());
        }
    }

    @KeepForSdk
    public Set<Scope> getScopes() {
        return Collections.EMPTY_SET;
    }

    @KeepForSdk
    public final T getService() throws DeadObjectException {
        T t2;
        synchronized (this.mLock) {
            if (this.zzcj == 5) {
                throw new DeadObjectException();
            }
            checkConnected();
            Preconditions.checkState(this.zzcg != null, "Client is connected but service is null");
            t2 = this.zzcg;
        }
        return t2;
    }

    @Nullable
    @KeepForSdk
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzcd) {
            IGmsServiceBroker iGmsServiceBroker = this.zzce;
            if (iGmsServiceBroker == null) {
                return null;
            }
            return iGmsServiceBroker.asBinder();
        }
    }

    @NonNull
    @KeepForSdk
    public abstract String getServiceDescriptor();

    @KeepForSdk
    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    @NonNull
    @KeepForSdk
    public abstract String getStartServiceAction();

    @KeepForSdk
    public String getStartServicePackage() {
        return "com.google.android.gms";
    }

    @KeepForSdk
    public boolean isConnected() {
        boolean z2;
        synchronized (this.mLock) {
            z2 = this.zzcj == 4;
        }
        return z2;
    }

    @KeepForSdk
    public boolean isConnecting() {
        boolean z2;
        synchronized (this.mLock) {
            int i2 = this.zzcj;
            z2 = i2 == 2 || i2 == 3;
        }
        return z2;
    }

    @KeepForSdk
    @CallSuper
    public void onConnectedLocked(@NonNull T t2) {
        this.zzbw = System.currentTimeMillis();
    }

    @KeepForSdk
    @CallSuper
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzbx = connectionResult.getErrorCode();
        this.zzby = System.currentTimeMillis();
    }

    @KeepForSdk
    @CallSuper
    public void onConnectionSuspended(int i2) {
        this.zzbu = i2;
        this.zzbv = System.currentTimeMillis();
    }

    @KeepForSdk
    public void onPostInitHandler(int i2, IBinder iBinder, Bundle bundle, int i3) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1, i3, -1, new zzf(i2, iBinder, bundle)));
    }

    @KeepForSdk
    public void onSetConnectState(int i2, T t2) {
    }

    @KeepForSdk
    public void onUserSignOut(@NonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    @KeepForSdk
    public boolean providesSignIn() {
        return false;
    }

    @KeepForSdk
    public boolean requiresAccount() {
        return false;
    }

    @KeepForSdk
    public boolean requiresGooglePlayServices() {
        return true;
    }

    @KeepForSdk
    public boolean requiresSignIn() {
        return false;
    }

    @KeepForSdk
    public void triggerConnectionSuspended(int i2) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(6, this.zzcr.get(), i2));
    }

    @VisibleForTesting
    @KeepForSdk
    public void triggerNotAvailable(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int i2, @Nullable PendingIntent pendingIntent) {
        this.zzcf = (ConnectionProgressReportCallbacks) Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3, this.zzcr.get(), i2, pendingIntent));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(int i2, T t2) {
        zzh zzhVar;
        Preconditions.checkArgument((i2 == 4) == (t2 != null));
        synchronized (this.mLock) {
            this.zzcj = i2;
            this.zzcg = t2;
            onSetConnectState(i2, t2);
            if (i2 != 1) {
                if (i2 == 2 || i2 == 3) {
                    if (this.zzci != null && (zzhVar = this.zzbz) != null) {
                        String strZzt = zzhVar.zzt();
                        String packageName = this.zzbz.getPackageName();
                        StringBuilder sb = new StringBuilder(String.valueOf(strZzt).length() + 70 + String.valueOf(packageName).length());
                        sb.append("Calling connect() while still connected, missing disconnect() for ");
                        sb.append(strZzt);
                        sb.append(" on ");
                        sb.append(packageName);
                        Log.e("GmsClient", sb.toString());
                        this.zzcb.zza(this.zzbz.zzt(), this.zzbz.getPackageName(), this.zzbz.zzq(), this.zzci, zzj());
                        this.zzcr.incrementAndGet();
                    }
                    this.zzci = new zze(this.zzcr.get());
                    zzh zzhVar2 = (this.zzcj != 3 || getLocalStartServiceAction() == null) ? new zzh(getStartServicePackage(), getStartServiceAction(), false, 129) : new zzh(getContext().getPackageName(), getLocalStartServiceAction(), true, 129);
                    this.zzbz = zzhVar2;
                    if (!this.zzcb.zza(new GmsClientSupervisor.zza(zzhVar2.zzt(), this.zzbz.getPackageName(), this.zzbz.zzq()), this.zzci, zzj())) {
                        String strZzt2 = this.zzbz.zzt();
                        String packageName2 = this.zzbz.getPackageName();
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strZzt2).length() + 34 + String.valueOf(packageName2).length());
                        sb2.append("unable to connect to service: ");
                        sb2.append(strZzt2);
                        sb2.append(" on ");
                        sb2.append(packageName2);
                        Log.e("GmsClient", sb2.toString());
                        zza(16, (Bundle) null, this.zzcr.get());
                    }
                } else if (i2 == 4) {
                    onConnectedLocked(t2);
                }
            } else if (this.zzci != null) {
                this.zzcb.zza(this.zzbz.zzt(), this.zzbz.getPackageName(), this.zzbz.zzq(), this.zzci, zzj());
                this.zzci = null;
            }
        }
    }

    @VisibleForTesting
    @KeepForSdk
    public BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i2, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this.mLock = new Object();
        this.zzcd = new Object();
        this.zzch = new ArrayList<>();
        this.zzcj = 1;
        this.zzco = null;
        this.zzcp = false;
        this.zzcq = null;
        this.zzcr = new AtomicInteger(0);
        this.mContext = (Context) Preconditions.checkNotNull(context, "Context must not be null");
        this.zzca = (Looper) Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzcb = (GmsClientSupervisor) Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzcc = (GoogleApiAvailabilityLight) Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.mHandler = new zzb(looper);
        this.zzcm = i2;
        this.zzck = baseConnectionCallbacks;
        this.zzcl = baseOnConnectionFailedListener;
        this.zzcn = str;
    }

    @VisibleForTesting
    @KeepForSdk
    public BaseGmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i2, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        this.mLock = new Object();
        this.zzcd = new Object();
        this.zzch = new ArrayList<>();
        this.zzcj = 1;
        this.zzco = null;
        this.zzcp = false;
        this.zzcq = null;
        this.zzcr = new AtomicInteger(0);
        this.mContext = (Context) Preconditions.checkNotNull(context, "Context must not be null");
        this.mHandler = (Handler) Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzca = handler.getLooper();
        this.zzcb = (GmsClientSupervisor) Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzcc = (GoogleApiAvailabilityLight) Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzcm = i2;
        this.zzck = baseConnectionCallbacks;
        this.zzcl = baseOnConnectionFailedListener;
        this.zzcn = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zza(int i2, int i3, T t2) {
        synchronized (this.mLock) {
            if (this.zzcj != i2) {
                return false;
            }
            zza(i3, (int) t2);
            return true;
        }
    }

    public final void zza(int i2, @Nullable Bundle bundle, int i3) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(7, i3, -1, new zzg(i2, null)));
    }
}
