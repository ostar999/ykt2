package org.wrtca.api;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.p2p.WifiP2pGroup;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.wrtca.jni.CalledByNative;
import org.wrtca.log.Logging;

/* loaded from: classes9.dex */
public class NetworkMonitorAutoDetect extends BroadcastReceiver {
    public static final long INVALID_NET_ID = -1;
    private static final String TAG = "NetworkMonitorAutoDetect";
    private final ConnectivityManager.NetworkCallback allNetworkCallback;
    private ConnectionType connectionType;
    private ConnectivityManagerDelegate connectivityManagerDelegate;
    private final Context context;
    private final IntentFilter intentFilter;
    private boolean isRegistered;
    private final ConnectivityManager.NetworkCallback mobileNetworkCallback;
    private final Observer observer;
    private WifiDirectManagerDelegate wifiDirectManagerDelegate;
    private WifiManagerDelegate wifiManagerDelegate;
    private String wifiSSID;

    public enum ConnectionType {
        CONNECTION_UNKNOWN,
        CONNECTION_ETHERNET,
        CONNECTION_WIFI,
        CONNECTION_4G,
        CONNECTION_3G,
        CONNECTION_2G,
        CONNECTION_UNKNOWN_CELLULAR,
        CONNECTION_BLUETOOTH,
        CONNECTION_NONE
    }

    public static class IPAddress {
        public final byte[] address;

        public IPAddress(byte[] bArr) {
            this.address = bArr;
        }

        @CalledByNative("IPAddress")
        private byte[] getAddress() {
            return this.address;
        }
    }

    public static class NetworkInformation {
        public final long handle;
        public final IPAddress[] ipAddresses;
        public final String name;
        public final ConnectionType type;

        public NetworkInformation(String str, ConnectionType connectionType, long j2, IPAddress[] iPAddressArr) {
            this.name = str;
            this.type = connectionType;
            this.handle = j2;
            this.ipAddresses = iPAddressArr;
        }

        @CalledByNative("NetworkInformation")
        private ConnectionType getConnectionType() {
            return this.type;
        }

        @CalledByNative("NetworkInformation")
        private long getHandle() {
            return this.handle;
        }

        @CalledByNative("NetworkInformation")
        private IPAddress[] getIpAddresses() {
            return this.ipAddresses;
        }

        @CalledByNative("NetworkInformation")
        private String getName() {
            return this.name;
        }
    }

    public static class NetworkState {
        private final boolean connected;
        private final int subtype;
        private final int type;

        public NetworkState(boolean z2, int i2, int i3) {
            this.connected = z2;
            this.type = i2;
            this.subtype = i3;
        }

        public int getNetworkSubType() {
            return this.subtype;
        }

        public int getNetworkType() {
            return this.type;
        }

        public boolean isConnected() {
            return this.connected;
        }
    }

    public interface Observer {
        void onConnectionTypeChanged(ConnectionType connectionType);

        void onNetworkConnect(NetworkInformation networkInformation);

        void onNetworkDisconnect(long j2);
    }

    @SuppressLint({"NewApi"})
    public class SimpleNetworkCallback extends ConnectivityManager.NetworkCallback {
        private SimpleNetworkCallback() {
        }

        private void onNetworkChanged(Network network) {
            NetworkInformation networkInformationNetworkToInfo = NetworkMonitorAutoDetect.this.connectivityManagerDelegate.networkToInfo(network);
            if (networkInformationNetworkToInfo != null) {
                NetworkMonitorAutoDetect.this.observer.onNetworkConnect(networkInformationNetworkToInfo);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            Logging.d(NetworkMonitorAutoDetect.TAG, "Network becomes available: " + network.toString());
            onNetworkChanged(network);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            Logging.d(NetworkMonitorAutoDetect.TAG, "capabilities changed: " + networkCapabilities.toString());
            onNetworkChanged(network);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            Logging.d(NetworkMonitorAutoDetect.TAG, "link properties changed: " + linkProperties.toString());
            onNetworkChanged(network);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLosing(Network network, int i2) {
            Logging.d(NetworkMonitorAutoDetect.TAG, "Network " + network.toString() + " is about to lose in " + i2 + "ms");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            Logging.d(NetworkMonitorAutoDetect.TAG, "Network " + network.toString() + " is disconnected");
            NetworkMonitorAutoDetect.this.observer.onNetworkDisconnect(NetworkMonitorAutoDetect.networkToNetId(network));
        }
    }

    public static class WifiDirectManagerDelegate extends BroadcastReceiver {
        private static final int WIFI_P2P_NETWORK_HANDLE = 0;
        private final Context context;
        private final Observer observer;
        private NetworkInformation wifiP2pNetworkInfo = null;

        public WifiDirectManagerDelegate(Observer observer, Context context) {
            this.context = context;
            this.observer = observer;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
            intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
            context.registerReceiver(this, intentFilter);
        }

        private void onWifiP2pGroupChange(WifiP2pGroup wifiP2pGroup) {
            if (wifiP2pGroup == null || wifiP2pGroup.getInterface() == null) {
                return;
            }
            try {
                ArrayList list = Collections.list(NetworkInterface.getByName(wifiP2pGroup.getInterface()).getInetAddresses());
                IPAddress[] iPAddressArr = new IPAddress[list.size()];
                for (int i2 = 0; i2 < list.size(); i2++) {
                    iPAddressArr[i2] = new IPAddress(((InetAddress) list.get(i2)).getAddress());
                }
                NetworkInformation networkInformation = new NetworkInformation(wifiP2pGroup.getInterface(), ConnectionType.CONNECTION_WIFI, 0L, iPAddressArr);
                this.wifiP2pNetworkInfo = networkInformation;
                this.observer.onNetworkConnect(networkInformation);
            } catch (SocketException e2) {
                Logging.e(NetworkMonitorAutoDetect.TAG, "Unable to get WifiP2p network interface", e2);
            }
        }

        private void onWifiP2pStateChange(int i2) {
            if (i2 == 1) {
                this.wifiP2pNetworkInfo = null;
                this.observer.onNetworkDisconnect(0L);
            }
        }

        public List<NetworkInformation> getActiveNetworkList() {
            NetworkInformation networkInformation = this.wifiP2pNetworkInfo;
            return networkInformation != null ? Collections.singletonList(networkInformation) : Collections.emptyList();
        }

        @Override // android.content.BroadcastReceiver
        @SuppressLint({"InlinedApi"})
        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(intent.getAction())) {
                onWifiP2pGroupChange((WifiP2pGroup) intent.getParcelableExtra("p2pGroupInfo"));
            } else if ("android.net.wifi.p2p.STATE_CHANGED".equals(intent.getAction())) {
                onWifiP2pStateChange(intent.getIntExtra("wifi_p2p_state", 0));
            }
        }

        public void release() {
            this.context.unregisterReceiver(this);
        }
    }

    @SuppressLint({"NewApi"})
    public NetworkMonitorAutoDetect(Observer observer, Context context) {
        this.observer = observer;
        this.context = context;
        this.connectivityManagerDelegate = new ConnectivityManagerDelegate(context);
        this.wifiManagerDelegate = new WifiManagerDelegate(context);
        NetworkState networkState = this.connectivityManagerDelegate.getNetworkState();
        this.connectionType = getConnectionType(networkState);
        this.wifiSSID = getWifiSSID(networkState);
        this.intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        if (PeerConnectionFactory.fieldTrialsFindFullName("IncludeWifiDirect").equals(PeerConnectionFactory.TRIAL_ENABLED)) {
            this.wifiDirectManagerDelegate = new WifiDirectManagerDelegate(observer, context);
        }
        registerReceiver();
        if (!this.connectivityManagerDelegate.supportNetworkCallback()) {
            this.mobileNetworkCallback = null;
            this.allNetworkCallback = null;
            return;
        }
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback();
        try {
            this.connectivityManagerDelegate.requestMobileNetwork(networkCallback);
        } catch (SecurityException unused) {
            Logging.w(TAG, "Unable to obtain permission to request a cellular network.");
            networkCallback = null;
        }
        this.mobileNetworkCallback = networkCallback;
        SimpleNetworkCallback simpleNetworkCallback = new SimpleNetworkCallback();
        this.allNetworkCallback = simpleNetworkCallback;
        this.connectivityManagerDelegate.registerNetworkCallback(simpleNetworkCallback);
    }

    private void connectionTypeChanged(NetworkState networkState) {
        ConnectionType connectionType = getConnectionType(networkState);
        String wifiSSID = getWifiSSID(networkState);
        if (connectionType == this.connectionType && wifiSSID.equals(this.wifiSSID)) {
            return;
        }
        this.connectionType = connectionType;
        this.wifiSSID = wifiSSID;
        Logging.d(TAG, "Network connectivity changed, type is: " + this.connectionType);
        this.observer.onConnectionTypeChanged(connectionType);
    }

    public static ConnectionType getConnectionType(NetworkState networkState) {
        if (!networkState.isConnected()) {
            return ConnectionType.CONNECTION_NONE;
        }
        int networkType = networkState.getNetworkType();
        if (networkType != 0) {
            return networkType != 1 ? networkType != 6 ? networkType != 7 ? networkType != 9 ? ConnectionType.CONNECTION_UNKNOWN : ConnectionType.CONNECTION_ETHERNET : ConnectionType.CONNECTION_BLUETOOTH : ConnectionType.CONNECTION_4G : ConnectionType.CONNECTION_WIFI;
        }
        switch (networkState.getNetworkSubType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return ConnectionType.CONNECTION_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return ConnectionType.CONNECTION_3G;
            case 13:
                return ConnectionType.CONNECTION_4G;
            default:
                return ConnectionType.CONNECTION_UNKNOWN_CELLULAR;
        }
    }

    private String getWifiSSID(NetworkState networkState) {
        return getConnectionType(networkState) != ConnectionType.CONNECTION_WIFI ? "" : this.wifiManagerDelegate.getWifiSSID();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public static long networkToNetId(Network network) {
        return network.getNetworkHandle();
    }

    private void registerReceiver() {
        if (this.isRegistered) {
            return;
        }
        this.isRegistered = true;
        this.context.registerReceiver(this, this.intentFilter);
    }

    private void unregisterReceiver() {
        if (this.isRegistered) {
            this.isRegistered = false;
            this.context.unregisterReceiver(this);
        }
    }

    public void destroy() {
        ConnectivityManager.NetworkCallback networkCallback = this.allNetworkCallback;
        if (networkCallback != null) {
            this.connectivityManagerDelegate.releaseCallback(networkCallback);
        }
        ConnectivityManager.NetworkCallback networkCallback2 = this.mobileNetworkCallback;
        if (networkCallback2 != null) {
            this.connectivityManagerDelegate.releaseCallback(networkCallback2);
        }
        WifiDirectManagerDelegate wifiDirectManagerDelegate = this.wifiDirectManagerDelegate;
        if (wifiDirectManagerDelegate != null) {
            wifiDirectManagerDelegate.release();
        }
        unregisterReceiver();
    }

    public List<NetworkInformation> getActiveNetworkList() {
        List<NetworkInformation> activeNetworkList = this.connectivityManagerDelegate.getActiveNetworkList();
        if (activeNetworkList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(activeNetworkList);
        WifiDirectManagerDelegate wifiDirectManagerDelegate = this.wifiDirectManagerDelegate;
        if (wifiDirectManagerDelegate != null) {
            arrayList.addAll(wifiDirectManagerDelegate.getActiveNetworkList());
        }
        return arrayList;
    }

    public NetworkState getCurrentNetworkState() {
        return this.connectivityManagerDelegate.getNetworkState();
    }

    public long getDefaultNetId() {
        return this.connectivityManagerDelegate.getDefaultNetId();
    }

    public boolean isReceiverRegisteredForTesting() {
        return this.isRegistered;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        NetworkState currentNetworkState = getCurrentNetworkState();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            connectionTypeChanged(currentNetworkState);
        }
    }

    public void setConnectivityManagerDelegateForTests(ConnectivityManagerDelegate connectivityManagerDelegate) {
        this.connectivityManagerDelegate = connectivityManagerDelegate;
    }

    public void setWifiManagerDelegateForTests(WifiManagerDelegate wifiManagerDelegate) {
        this.wifiManagerDelegate = wifiManagerDelegate;
    }

    public boolean supportNetworkCallback() {
        return this.connectivityManagerDelegate.supportNetworkCallback();
    }

    public static class ConnectivityManagerDelegate {
        private final ConnectivityManager connectivityManager;

        public ConnectivityManagerDelegate(Context context) {
            this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        }

        /* JADX INFO: Access modifiers changed from: private */
        @SuppressLint({"NewApi"})
        public NetworkInformation networkToInfo(Network network) {
            ConnectivityManager connectivityManager = this.connectivityManager;
            if (connectivityManager == null) {
                return null;
            }
            LinkProperties linkProperties = connectivityManager.getLinkProperties(network);
            if (linkProperties == null) {
                Logging.w(NetworkMonitorAutoDetect.TAG, "Detected unknown network: " + network.toString());
                return null;
            }
            if (linkProperties.getInterfaceName() == null) {
                Logging.w(NetworkMonitorAutoDetect.TAG, "Null interface name for network " + network.toString());
                return null;
            }
            NetworkState networkState = getNetworkState(network);
            if (networkState.connected && networkState.getNetworkType() == 17) {
                networkState = getNetworkState();
            }
            ConnectionType connectionType = NetworkMonitorAutoDetect.getConnectionType(networkState);
            if (connectionType == ConnectionType.CONNECTION_NONE) {
                Logging.d(NetworkMonitorAutoDetect.TAG, "Network " + network.toString() + " is disconnected");
                return null;
            }
            if (connectionType == ConnectionType.CONNECTION_UNKNOWN || connectionType == ConnectionType.CONNECTION_UNKNOWN_CELLULAR) {
                Logging.d(NetworkMonitorAutoDetect.TAG, "Network " + network.toString() + " connection type is " + connectionType + " because it has type " + networkState.getNetworkType() + " and subtype " + networkState.getNetworkSubType());
            }
            return new NetworkInformation(linkProperties.getInterfaceName(), connectionType, NetworkMonitorAutoDetect.networkToNetId(network), getIPAddresses(linkProperties));
        }

        public List<NetworkInformation> getActiveNetworkList() {
            if (!supportNetworkCallback()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Network network : getAllNetworks()) {
                NetworkInformation networkInformationNetworkToInfo = networkToInfo(network);
                if (networkInformationNetworkToInfo != null) {
                    arrayList.add(networkInformationNetworkToInfo);
                }
            }
            return arrayList;
        }

        @SuppressLint({"NewApi"})
        public Network[] getAllNetworks() {
            ConnectivityManager connectivityManager = this.connectivityManager;
            return connectivityManager == null ? new Network[0] : connectivityManager.getAllNetworks();
        }

        @SuppressLint({"NewApi"})
        public long getDefaultNetId() {
            NetworkInfo activeNetworkInfo;
            NetworkInfo networkInfo;
            if (!supportNetworkCallback() || (activeNetworkInfo = this.connectivityManager.getActiveNetworkInfo()) == null) {
                return -1L;
            }
            long jNetworkToNetId = -1;
            for (Network network : getAllNetworks()) {
                if (hasInternetCapability(network) && (networkInfo = this.connectivityManager.getNetworkInfo(network)) != null && networkInfo.getType() == activeNetworkInfo.getType()) {
                    if (jNetworkToNetId != -1) {
                        throw new RuntimeException("Multiple connected networks of same type are not supported.");
                    }
                    jNetworkToNetId = NetworkMonitorAutoDetect.networkToNetId(network);
                }
            }
            return jNetworkToNetId;
        }

        @SuppressLint({"NewApi"})
        public IPAddress[] getIPAddresses(LinkProperties linkProperties) {
            IPAddress[] iPAddressArr = new IPAddress[linkProperties.getLinkAddresses().size()];
            Iterator<LinkAddress> it = linkProperties.getLinkAddresses().iterator();
            int i2 = 0;
            while (it.hasNext()) {
                iPAddressArr[i2] = new IPAddress(it.next().getAddress().getAddress());
                i2++;
            }
            return iPAddressArr;
        }

        public NetworkState getNetworkState() {
            ConnectivityManager connectivityManager = this.connectivityManager;
            return connectivityManager == null ? new NetworkState(false, -1, -1) : getNetworkState(connectivityManager.getActiveNetworkInfo());
        }

        @SuppressLint({"NewApi"})
        public boolean hasInternetCapability(Network network) {
            NetworkCapabilities networkCapabilities;
            ConnectivityManager connectivityManager = this.connectivityManager;
            return (connectivityManager == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(network)) == null || !networkCapabilities.hasCapability(12)) ? false : true;
        }

        @SuppressLint({"NewApi"})
        public void registerNetworkCallback(ConnectivityManager.NetworkCallback networkCallback) {
            this.connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().addCapability(12).build(), networkCallback);
        }

        @SuppressLint({"NewApi"})
        public void releaseCallback(ConnectivityManager.NetworkCallback networkCallback) {
            if (supportNetworkCallback()) {
                Logging.d(NetworkMonitorAutoDetect.TAG, "Unregister network callback");
                this.connectivityManager.unregisterNetworkCallback(networkCallback);
            }
        }

        @SuppressLint({"NewApi"})
        public void requestMobileNetwork(ConnectivityManager.NetworkCallback networkCallback) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12).addTransportType(0);
            this.connectivityManager.requestNetwork(builder.build(), networkCallback);
        }

        public boolean supportNetworkCallback() {
            return this.connectivityManager != null;
        }

        public ConnectivityManagerDelegate() {
            this.connectivityManager = null;
        }

        @SuppressLint({"NewApi"})
        public NetworkState getNetworkState(Network network) {
            ConnectivityManager connectivityManager = this.connectivityManager;
            if (connectivityManager == null) {
                return new NetworkState(false, -1, -1);
            }
            return getNetworkState(connectivityManager.getNetworkInfo(network));
        }

        public NetworkState getNetworkState(NetworkInfo networkInfo) {
            if (networkInfo != null && networkInfo.isConnected()) {
                return new NetworkState(true, networkInfo.getType(), networkInfo.getSubtype());
            }
            return new NetworkState(false, -1, -1);
        }
    }

    public static class WifiManagerDelegate {
        private final Context context;

        public WifiManagerDelegate(Context context) {
            this.context = context;
        }

        public String getWifiSSID() {
            WifiInfo wifiInfo;
            String ssid;
            Intent intentRegisterReceiver = this.context.registerReceiver(null, new IntentFilter("android.net.wifi.STATE_CHANGE"));
            return (intentRegisterReceiver == null || (wifiInfo = (WifiInfo) intentRegisterReceiver.getParcelableExtra("wifiInfo")) == null || (ssid = wifiInfo.getSSID()) == null) ? "" : ssid;
        }

        public WifiManagerDelegate() {
            this.context = null;
        }
    }
}
