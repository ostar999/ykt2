package androidx.room;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.IMultiInstanceInvalidationService;
import java.util.HashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class MultiInstanceInvalidationService extends Service {
    int mMaxClientId = 0;
    final HashMap<Integer, String> mClientNames = new HashMap<>();
    final RemoteCallbackList<IMultiInstanceInvalidationCallback> mCallbackList = new RemoteCallbackList<IMultiInstanceInvalidationCallback>() { // from class: androidx.room.MultiInstanceInvalidationService.1
        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, Object obj) {
            MultiInstanceInvalidationService.this.mClientNames.remove(Integer.valueOf(((Integer) obj).intValue()));
        }
    };
    private final IMultiInstanceInvalidationService.Stub mBinder = new IMultiInstanceInvalidationService.Stub() { // from class: androidx.room.MultiInstanceInvalidationService.2
        @Override // androidx.room.IMultiInstanceInvalidationService
        public void broadcastInvalidation(int i2, String[] strArr) {
            synchronized (MultiInstanceInvalidationService.this.mCallbackList) {
                String str = MultiInstanceInvalidationService.this.mClientNames.get(Integer.valueOf(i2));
                if (str == null) {
                    Log.w("ROOM", "Remote invalidation client ID not registered");
                    return;
                }
                int iBeginBroadcast = MultiInstanceInvalidationService.this.mCallbackList.beginBroadcast();
                for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
                    try {
                        int iIntValue = ((Integer) MultiInstanceInvalidationService.this.mCallbackList.getBroadcastCookie(i3)).intValue();
                        String str2 = MultiInstanceInvalidationService.this.mClientNames.get(Integer.valueOf(iIntValue));
                        if (i2 != iIntValue && str.equals(str2)) {
                            try {
                                ((IMultiInstanceInvalidationCallback) MultiInstanceInvalidationService.this.mCallbackList.getBroadcastItem(i3)).onInvalidation(strArr);
                            } catch (RemoteException e2) {
                                Log.w("ROOM", "Error invoking a remote callback", e2);
                            }
                        }
                    } finally {
                        MultiInstanceInvalidationService.this.mCallbackList.finishBroadcast();
                    }
                }
            }
        }

        @Override // androidx.room.IMultiInstanceInvalidationService
        public int registerCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, String str) {
            if (str == null) {
                return 0;
            }
            synchronized (MultiInstanceInvalidationService.this.mCallbackList) {
                MultiInstanceInvalidationService multiInstanceInvalidationService = MultiInstanceInvalidationService.this;
                int i2 = multiInstanceInvalidationService.mMaxClientId + 1;
                multiInstanceInvalidationService.mMaxClientId = i2;
                if (multiInstanceInvalidationService.mCallbackList.register(iMultiInstanceInvalidationCallback, Integer.valueOf(i2))) {
                    MultiInstanceInvalidationService.this.mClientNames.put(Integer.valueOf(i2), str);
                    return i2;
                }
                MultiInstanceInvalidationService multiInstanceInvalidationService2 = MultiInstanceInvalidationService.this;
                multiInstanceInvalidationService2.mMaxClientId--;
                return 0;
            }
        }

        @Override // androidx.room.IMultiInstanceInvalidationService
        public void unregisterCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, int i2) {
            synchronized (MultiInstanceInvalidationService.this.mCallbackList) {
                MultiInstanceInvalidationService.this.mCallbackList.unregister(iMultiInstanceInvalidationCallback);
                MultiInstanceInvalidationService.this.mClientNames.remove(Integer.valueOf(i2));
            }
        }
    };

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
