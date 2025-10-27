package org.wrtca.api;

import android.content.Context;
import android.os.Build;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.wrtca.api.NetworkMonitorAutoDetect;
import org.wrtca.jni.CalledByNative;
import org.wrtca.log.Logging;
import org.wrtca.util.ContextUtils;
import org.wrtca.util.NativeClassQualifiedName;

/* loaded from: classes9.dex */
public class NetworkMonitor {
    private static final String TAG = "NetworkMonitor";
    private NetworkMonitorAutoDetect autoDetector;
    private final Object autoDetectorLock;
    private NetworkMonitorAutoDetect.ConnectionType currentConnectionType;
    private final ArrayList<Long> nativeNetworkObservers;
    private final ArrayList<NetworkObserver> networkObservers;
    private int numMonitors;

    public static class InstanceHolder {
        public static final NetworkMonitor instance = new NetworkMonitor();

        private InstanceHolder() {
        }
    }

    public interface NetworkObserver {
        void onConnectionTypeChanged(NetworkMonitorAutoDetect.ConnectionType connectionType);
    }

    public static void addNetworkObserver(NetworkObserver networkObserver) {
        getInstance().addNetworkObserverInternal(networkObserver);
    }

    private void addNetworkObserverInternal(NetworkObserver networkObserver) {
        this.networkObservers.add(networkObserver);
    }

    @CalledByNative
    private static int androidSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    private static void assertIsTrue(boolean z2) {
        if (!z2) {
            throw new AssertionError("Expected to be true");
        }
    }

    private NetworkMonitorAutoDetect createAutoDetector(Context context) {
        return new NetworkMonitorAutoDetect(new NetworkMonitorAutoDetect.Observer() { // from class: org.wrtca.api.NetworkMonitor.1
            @Override // org.wrtca.api.NetworkMonitorAutoDetect.Observer
            public void onConnectionTypeChanged(NetworkMonitorAutoDetect.ConnectionType connectionType) {
                NetworkMonitor.this.updateCurrentConnectionType(connectionType);
            }

            @Override // org.wrtca.api.NetworkMonitorAutoDetect.Observer
            public void onNetworkConnect(NetworkMonitorAutoDetect.NetworkInformation networkInformation) {
                NetworkMonitor.this.notifyObserversOfNetworkConnect(networkInformation);
            }

            @Override // org.wrtca.api.NetworkMonitorAutoDetect.Observer
            public void onNetworkDisconnect(long j2) {
                NetworkMonitor.this.notifyObserversOfNetworkDisconnect(j2);
            }
        }, context);
    }

    public static NetworkMonitorAutoDetect getAutoDetectorForTest(Context context) {
        NetworkMonitor networkMonitor = getInstance();
        NetworkMonitorAutoDetect networkMonitorAutoDetectCreateAutoDetector = networkMonitor.createAutoDetector(context);
        networkMonitor.autoDetector = networkMonitorAutoDetectCreateAutoDetector;
        return networkMonitorAutoDetectCreateAutoDetector;
    }

    private NetworkMonitorAutoDetect.ConnectionType getCurrentConnectionType() {
        return this.currentConnectionType;
    }

    private long getCurrentDefaultNetId() {
        long defaultNetId;
        synchronized (this.autoDetectorLock) {
            NetworkMonitorAutoDetect networkMonitorAutoDetect = this.autoDetector;
            defaultNetId = networkMonitorAutoDetect == null ? -1L : networkMonitorAutoDetect.getDefaultNetId();
        }
        return defaultNetId;
    }

    @CalledByNative
    public static NetworkMonitor getInstance() {
        return InstanceHolder.instance;
    }

    @Deprecated
    public static void init(Context context) {
    }

    public static boolean isOnline() {
        return getInstance().getCurrentConnectionType() != NetworkMonitorAutoDetect.ConnectionType.CONNECTION_NONE;
    }

    @NativeClassQualifiedName("webrtc::jni::AndroidNetworkMonitor")
    private native void nativeNotifyConnectionTypeChanged(long j2);

    @NativeClassQualifiedName("webrtc::jni::AndroidNetworkMonitor")
    private native void nativeNotifyOfActiveNetworkList(long j2, NetworkMonitorAutoDetect.NetworkInformation[] networkInformationArr);

    @NativeClassQualifiedName("webrtc::jni::AndroidNetworkMonitor")
    private native void nativeNotifyOfNetworkConnect(long j2, NetworkMonitorAutoDetect.NetworkInformation networkInformation);

    @NativeClassQualifiedName("webrtc::jni::AndroidNetworkMonitor")
    private native void nativeNotifyOfNetworkDisconnect(long j2, long j3);

    @CalledByNative
    private boolean networkBindingSupported() {
        boolean z2;
        synchronized (this.autoDetectorLock) {
            NetworkMonitorAutoDetect networkMonitorAutoDetect = this.autoDetector;
            z2 = networkMonitorAutoDetect != null && networkMonitorAutoDetect.supportNetworkCallback();
        }
        return z2;
    }

    private void notifyObserversOfConnectionTypeChange(NetworkMonitorAutoDetect.ConnectionType connectionType) {
        Iterator<Long> it = this.nativeNetworkObservers.iterator();
        while (it.hasNext()) {
            nativeNotifyConnectionTypeChanged(it.next().longValue());
        }
        Iterator<NetworkObserver> it2 = this.networkObservers.iterator();
        while (it2.hasNext()) {
            it2.next().onConnectionTypeChanged(connectionType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyObserversOfNetworkConnect(NetworkMonitorAutoDetect.NetworkInformation networkInformation) {
        Iterator<Long> it = this.nativeNetworkObservers.iterator();
        while (it.hasNext()) {
            nativeNotifyOfNetworkConnect(it.next().longValue(), networkInformation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyObserversOfNetworkDisconnect(long j2) {
        Iterator<Long> it = this.nativeNetworkObservers.iterator();
        while (it.hasNext()) {
            nativeNotifyOfNetworkDisconnect(it.next().longValue(), j2);
        }
    }

    public static void removeNetworkObserver(NetworkObserver networkObserver) {
        getInstance().removeNetworkObserverInternal(networkObserver);
    }

    private void removeNetworkObserverInternal(NetworkObserver networkObserver) {
        this.networkObservers.remove(networkObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentConnectionType(NetworkMonitorAutoDetect.ConnectionType connectionType) {
        this.currentConnectionType = connectionType;
        notifyObserversOfConnectionTypeChange(connectionType);
    }

    private void updateObserverActiveNetworkList(long j2) {
        List<NetworkMonitorAutoDetect.NetworkInformation> activeNetworkList;
        synchronized (this.autoDetectorLock) {
            NetworkMonitorAutoDetect networkMonitorAutoDetect = this.autoDetector;
            activeNetworkList = networkMonitorAutoDetect == null ? null : networkMonitorAutoDetect.getActiveNetworkList();
        }
        if (activeNetworkList == null || activeNetworkList.size() == 0) {
            return;
        }
        nativeNotifyOfActiveNetworkList(j2, (NetworkMonitorAutoDetect.NetworkInformation[]) activeNetworkList.toArray(new NetworkMonitorAutoDetect.NetworkInformation[activeNetworkList.size()]));
    }

    public void startMonitoring() {
        synchronized (this.autoDetectorLock) {
            this.numMonitors++;
            if (this.autoDetector == null) {
                this.autoDetector = createAutoDetector(ContextUtils.getApplicationContext());
            }
            this.currentConnectionType = NetworkMonitorAutoDetect.getConnectionType(this.autoDetector.getCurrentNetworkState());
        }
    }

    public void stopMonitoring() {
        synchronized (this.autoDetectorLock) {
            int i2 = this.numMonitors - 1;
            this.numMonitors = i2;
            if (i2 == 0) {
                this.autoDetector.destroy();
                this.autoDetector = null;
            }
        }
    }

    private NetworkMonitor() {
        this.autoDetectorLock = new Object();
        this.currentConnectionType = NetworkMonitorAutoDetect.ConnectionType.CONNECTION_UNKNOWN;
        this.nativeNetworkObservers = new ArrayList<>();
        this.networkObservers = new ArrayList<>();
        this.numMonitors = 0;
    }

    @CalledByNative
    private void stopMonitoring(long j2) {
        Logging.d(TAG, "Stop monitoring with native observer " + j2);
        stopMonitoring();
        this.nativeNetworkObservers.remove(Long.valueOf(j2));
    }

    @CalledByNative
    private void startMonitoring(long j2) {
        Logging.d(TAG, "Start monitoring with native observer " + j2);
        startMonitoring();
        this.nativeNetworkObservers.add(Long.valueOf(j2));
        updateObserverActiveNetworkList(j2);
        notifyObserversOfConnectionTypeChange(this.currentConnectionType);
    }
}
