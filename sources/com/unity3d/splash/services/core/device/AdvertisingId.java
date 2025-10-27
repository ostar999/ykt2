package com.unity3d.splash.services.core.device;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@TargetApi(9)
/* loaded from: classes6.dex */
public class AdvertisingId {
    private static final String ADVERTISING_ID_SERVICE_NAME = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
    private static AdvertisingId instance;
    private String advertisingIdentifier = null;
    private boolean limitedAdvertisingTracking = false;

    public interface GoogleAdvertisingInfo extends IInterface {

        public static abstract class GoogleAdvertisingInfoBinder extends Binder implements GoogleAdvertisingInfo {

            public static class GoogleAdvertisingInfoImplementation implements GoogleAdvertisingInfo {
                private final IBinder _binder;

                public GoogleAdvertisingInfoImplementation(IBinder iBinder) {
                    this._binder = iBinder;
                }

                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return this._binder;
                }

                @Override // com.unity3d.splash.services.core.device.AdvertisingId.GoogleAdvertisingInfo
                public boolean getEnabled(boolean z2) {
                    Parcel parcelObtain = Parcel.obtain();
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(AdvertisingId.ADVERTISING_ID_SERVICE_NAME);
                        parcelObtain.writeInt(z2 ? 1 : 0);
                        this._binder.transact(2, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        return parcelObtain2.readInt() != 0;
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }

                @Override // com.unity3d.splash.services.core.device.AdvertisingId.GoogleAdvertisingInfo
                public String getId() {
                    Parcel parcelObtain = Parcel.obtain();
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(AdvertisingId.ADVERTISING_ID_SERVICE_NAME);
                        this._binder.transact(1, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        return parcelObtain2.readString();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
            }

            public static GoogleAdvertisingInfo create(IBinder iBinder) {
                if (iBinder == null) {
                    return null;
                }
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(AdvertisingId.ADVERTISING_ID_SERVICE_NAME);
                return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof GoogleAdvertisingInfo)) ? new GoogleAdvertisingInfoImplementation(iBinder) : (GoogleAdvertisingInfo) iInterfaceQueryLocalInterface;
            }

            @Override // android.os.Binder
            public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
                if (i2 == 1) {
                    parcel.enforceInterface(AdvertisingId.ADVERTISING_ID_SERVICE_NAME);
                    String id = getId();
                    parcel2.writeNoException();
                    parcel2.writeString(id);
                    return true;
                }
                if (i2 != 2) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel.enforceInterface(AdvertisingId.ADVERTISING_ID_SERVICE_NAME);
                boolean enabled = getEnabled(parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeInt(enabled ? 1 : 0);
                return true;
            }
        }

        boolean getEnabled(boolean z2);

        String getId();
    }

    public class GoogleAdvertisingServiceConnection implements ServiceConnection {
        boolean _consumed = false;
        private final BlockingQueue _binderQueue = new LinkedBlockingQueue();

        private GoogleAdvertisingServiceConnection() {
        }

        public IBinder getBinder() {
            if (this._consumed) {
                throw new IllegalStateException();
            }
            this._consumed = true;
            return (IBinder) this._binderQueue.take();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws IllegalAccessException, InterruptedException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            try {
                this._binderQueue.put(iBinder);
            } catch (InterruptedException unused) {
                DeviceLog.debug("Couldn't put service to binder que");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    private void fetchAdvertisingId(Context context) {
    }

    public static String getAdvertisingTrackingId() {
        return getInstance().advertisingIdentifier;
    }

    private static AdvertisingId getInstance() {
        if (instance == null) {
            instance = new AdvertisingId();
        }
        return instance;
    }

    public static boolean getLimitedAdTracking() {
        return getInstance().limitedAdvertisingTracking;
    }

    public static void init(Context context) {
        getInstance().fetchAdvertisingId(context);
    }
}
