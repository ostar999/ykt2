package com.tencent.rtmp.sharp.jni;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.alipay.sdk.util.h;
import com.google.android.exoplayer2.C;
import com.heytap.mcssdk.constant.a;
import com.hjq.permissions.Permission;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.tencent.liteav.basic.a.b;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.rtmp.sharp.jni.TraeMediaPlayer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@SuppressLint({"NewApi"})
/* loaded from: classes6.dex */
public class TraeAudioManager extends BroadcastReceiver {
    public static final String ACTION_TRAEAUDIOMANAGER_NOTIFY = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY";
    public static final String ACTION_TRAEAUDIOMANAGER_REQUEST = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST";
    public static final String ACTION_TRAEAUDIOMANAGER_RES = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES";
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP = 128;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO = 16;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    static final int AUDIO_DEVICE_OUT_EARPIECE = 1;
    static final int AUDIO_DEVICE_OUT_SPEAKER = 2;
    static final int AUDIO_DEVICE_OUT_WIRED_HEADPHONE = 8;
    static final int AUDIO_DEVICE_OUT_WIRED_HEADSET = 4;
    public static final int AUDIO_MANAGER_ACTIVE_NONE = 0;
    public static final int AUDIO_MANAGER_ACTIVE_RING = 2;
    public static final int AUDIO_MANAGER_ACTIVE_VOICECALL = 1;
    static final String AUDIO_PARAMETER_STREAM_ROUTING = "routing";
    public static final String CONNECTDEVICE_DEVICENAME = "CONNECTDEVICE_DEVICENAME";
    public static final String CONNECTDEVICE_RESULT_DEVICENAME = "CONNECTDEVICE_RESULT_DEVICENAME";
    public static final String DEVICE_BLUETOOTHHEADSET = "DEVICE_BLUETOOTHHEADSET";
    public static final String DEVICE_EARPHONE = "DEVICE_EARPHONE";
    public static final String DEVICE_NONE = "DEVICE_NONE";
    public static final String DEVICE_SPEAKERPHONE = "DEVICE_SPEAKERPHONE";
    public static final int DEVICE_STATUS_CONNECTED = 2;
    public static final int DEVICE_STATUS_CONNECTING = 1;
    public static final int DEVICE_STATUS_DISCONNECTED = 0;
    public static final int DEVICE_STATUS_DISCONNECTING = 3;
    public static final int DEVICE_STATUS_ERROR = -1;
    public static final int DEVICE_STATUS_UNCHANGEABLE = 4;
    public static final String DEVICE_WIREDHEADSET = "DEVICE_WIREDHEADSET";
    public static final int EARACTION_AWAY = 0;
    public static final int EARACTION_CLOSE = 1;
    public static final String EXTRA_DATA_AVAILABLEDEVICE_LIST = "EXTRA_DATA_AVAILABLEDEVICE_LIST";
    public static final String EXTRA_DATA_CONNECTEDDEVICE = "EXTRA_DATA_CONNECTEDDEVICE";
    public static final String EXTRA_DATA_DEVICECONFIG = "EXTRA_DATA_DEVICECONFIG";
    public static final String EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME = "EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME";
    public static final String EXTRA_DATA_PREV_CONNECTEDDEVICE = "EXTRA_DATA_PREV_CONNECTEDDEVICE";
    public static final String EXTRA_DATA_ROUTESWITCHEND_DEV = "EXTRA_DATA_ROUTESWITCHEND_DEV";
    public static final String EXTRA_DATA_ROUTESWITCHEND_TIME = "EXTRA_DATA_ROUTESWITCHEND_TIME";
    public static final String EXTRA_DATA_ROUTESWITCHSTART_FROM = "EXTRA_DATA_ROUTESWITCHSTART_FROM";
    public static final String EXTRA_DATA_ROUTESWITCHSTART_TO = "EXTRA_DATA_ROUTESWITCHSTART_TO";
    public static final String EXTRA_DATA_STREAMTYPE = "EXTRA_DATA_STREAMTYPE";
    public static final String EXTRA_EARACTION = "EXTRA_EARACTION";
    public static final int FORCE_ANALOG_DOCK = 8;
    public static final int FORCE_BT_A2DP = 4;
    public static final int FORCE_BT_CAR_DOCK = 6;
    public static final int FORCE_BT_DESK_DOCK = 7;
    public static final int FORCE_BT_SCO = 3;
    public static final int FORCE_DEFAULT = 0;
    public static final int FORCE_DIGITAL_DOCK = 9;
    public static final int FORCE_HEADPHONES = 2;
    public static final int FORCE_NONE = 0;
    public static final int FORCE_NO_BT_A2DP = 10;
    public static final int FORCE_SPEAKER = 1;
    public static final int FORCE_WIRED_ACCESSORY = 5;
    public static final int FOR_COMMUNICATION = 0;
    public static final int FOR_DOCK = 3;
    public static final int FOR_MEDIA = 1;
    public static final int FOR_RECORD = 2;
    public static final String GETCONNECTEDDEVICE_RESULT_LIST = "GETCONNECTEDDEVICE_REULT_LIST";
    public static final String GETCONNECTINGDEVICE_RESULT_LIST = "GETCONNECTINGDEVICE_REULT_LIST";
    public static final String ISDEVICECHANGABLED_RESULT_ISCHANGABLED = "ISDEVICECHANGABLED_REULT_ISCHANGABLED";
    public static boolean IsEarPhoneSupported = false;
    public static boolean IsMusicScene = false;
    public static boolean IsUpdateSceneFlag = false;
    public static final int MODE_MUSIC_PLAYBACK = 2;
    public static final int MODE_MUSIC_PLAY_RECORD = 1;
    public static final int MODE_MUSIC_PLAY_RECORD_HIGH_QUALITY = 3;
    public static final int MODE_MUSIC_PLAY_RECORD_LOW_QUALITY = 5;
    public static final int MODE_VOICE_CHAT = 0;
    public static final int MODE_VOICE_PLAYBACK = 4;
    public static final String MUSIC_CONFIG = "DEVICE_SPEAKERPHONE;DEVICE_WIREDHEADSET;DEVICE_BLUETOOTHHEADSET;";
    public static final String NOTIFY_DEVICECHANGABLE_UPDATE = "NOTIFY_DEVICECHANGABLE_UPDATE";
    public static final String NOTIFY_DEVICECHANGABLE_UPDATE_DATE = "NOTIFY_DEVICECHANGABLE_UPDATE_DATE";
    public static final String NOTIFY_DEVICELIST_UPDATE = "NOTIFY_DEVICELISTUPDATE";
    public static final String NOTIFY_RING_COMPLETION = "NOTIFY_RING_COMPLETION";
    public static final String NOTIFY_ROUTESWITCHEND = "NOTIFY_ROUTESWITCHEND";
    public static final String NOTIFY_ROUTESWITCHSTART = "NOTIFY_ROUTESWITCHSTART";
    public static final String NOTIFY_SERVICE_STATE = "NOTIFY_SERVICE_STATE";
    public static final String NOTIFY_SERVICE_STATE_DATE = "NOTIFY_SERVICE_STATE_DATE";
    public static final String NOTIFY_STREAMTYPE_UPDATE = "NOTIFY_STREAMTYPE_UPDATE";
    private static final int NUM_FORCE_CONFIG = 11;
    private static final int NUM_FORCE_USE = 4;
    public static final String OPERATION_CONNECTDEVICE = "OPERATION_CONNECTDEVICE";
    public static final String OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE = "OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE";
    public static final String OPERATION_EARACTION = "OPERATION_EARACTION";
    public static final String OPERATION_GETCONNECTEDDEVICE = "OPERATION_GETCONNECTEDDEVICE";
    public static final String OPERATION_GETCONNECTINGDEVICE = "OPERATION_GETCONNECTINGDEVICE";
    public static final String OPERATION_GETDEVICELIST = "OPERATION_GETDEVICELIST";
    public static final String OPERATION_GETSTREAMTYPE = "OPERATION_GETSTREAMTYPE";
    public static final String OPERATION_ISDEVICECHANGABLED = "OPERATION_ISDEVICECHANGABLED";
    public static final String OPERATION_RECOVER_AUDIO_FOCUS = "OPERATION_RECOVER_AUDIO_FOCUS";
    public static final String OPERATION_REGISTERAUDIOSESSION = "OPERATION_REGISTERAUDIOSESSION";
    public static final String OPERATION_REQUEST_RELEASE_AUDIO_FOCUS = "OPERATION_REQUEST_RELEASE_AUDIO_FOCUS";
    public static final String OPERATION_STARTRING = "OPERATION_STARTRING";
    public static final String OPERATION_STARTSERVICE = "OPERATION_STARTSERVICE";
    public static final String OPERATION_STOPRING = "OPERATION_STOPRING";
    public static final String OPERATION_STOPSERVICE = "OPERATION_STOPSERVICE";
    public static final String OPERATION_VOICECALL_AUDIOPARAM_CHANGED = "OPERATION_VOICECALL_AUDIOPARAM_CHANGED";
    public static final String OPERATION_VOICECALL_POSTPROCESS = "OPERATION_VOICECALL_POSTROCESS";
    public static final String OPERATION_VOICECALL_PREPROCESS = "OPERATION_VOICECALL_PREPROCESS";
    public static final String PARAM_DEVICE = "PARAM_DEVICE";
    public static final String PARAM_ERROR = "PARAM_ERROR";
    public static final String PARAM_ISHOSTSIDE = "PARAM_ISHOSTSIDE";
    public static final String PARAM_MODEPOLICY = "PARAM_MODEPOLICY";
    public static final String PARAM_OPERATION = "PARAM_OPERATION";
    public static final String PARAM_RES_ERRCODE = "PARAM_RES_ERRCODE";
    public static final String PARAM_RING_DATASOURCE = "PARAM_RING_DATASOURCE";
    public static final String PARAM_RING_FILEPATH = "PARAM_RING_FILEPATH";
    public static final String PARAM_RING_LOOP = "PARAM_RING_LOOP";
    public static final String PARAM_RING_LOOPCOUNT = "PARAM_RING_LOOPCOUNT";
    public static final String PARAM_RING_MODE = "PARAM_RING_MODE";
    public static final String PARAM_RING_RSID = "PARAM_RING_RSID";
    public static final String PARAM_RING_URI = "PARAM_RING_URI";
    public static final String PARAM_RING_USERDATA_STRING = "PARAM_RING_USERDATA_STRING";
    public static final String PARAM_SESSIONID = "PARAM_SESSIONID";
    public static final String PARAM_STATUS = "PARAM_STATUS";
    public static final String PARAM_STREAMTYPE = "PARAM_STREAMTYPE";
    public static final String REGISTERAUDIOSESSION_ISREGISTER = "REGISTERAUDIOSESSION_ISREGISTER";
    public static final int RES_ERRCODE_DEVICE_BTCONNCECTED_TIMEOUT = 10;
    public static final int RES_ERRCODE_DEVICE_NOT_VISIABLE = 8;
    public static final int RES_ERRCODE_DEVICE_UNCHANGEABLE = 9;
    public static final int RES_ERRCODE_DEVICE_UNKOWN = 7;
    public static final int RES_ERRCODE_NONE = 0;
    public static final int RES_ERRCODE_RING_NOT_EXIST = 5;
    public static final int RES_ERRCODE_SERVICE_OFF = 1;
    public static final int RES_ERRCODE_STOPRING_INTERRUPT = 4;
    public static final int RES_ERRCODE_VOICECALLPOST_INTERRUPT = 6;
    public static final int RES_ERRCODE_VOICECALL_EXIST = 2;
    public static final int RES_ERRCODE_VOICECALL_NOT_EXIST = 3;
    private static final String TAG = "TraeAudioManager";
    public static final String VIDEO_CONFIG = "DEVICE_EARPHONE;DEVICE_SPEAKERPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;";
    public static final String VOICECALL_CONFIG = "DEVICE_SPEAKERPHONE;DEVICE_EARPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;";
    public static boolean enableDeviceSwitchFlag = true;
    Context _context;
    TraeAudioManagerLooper mTraeAudioManagerLooper;
    static ReentrantLock _glock = new ReentrantLock();
    static TraeAudioManager _ginstance = null;
    static final String[] forceName = {"FORCE_NONE", "FORCE_SPEAKER", "FORCE_HEADPHONES", "FORCE_BT_SCO", "FORCE_BT_A2DP", "FORCE_WIRED_ACCESSORY", "FORCE_BT_CAR_DOCK", "FORCE_BT_DESK_DOCK", "FORCE_ANALOG_DOCK", "FORCE_NO_BT_A2DP", "FORCE_DIGITAL_DOCK"};
    AudioManager _am = null;
    int _activeMode = 0;
    int _prevMode = 0;
    int _streamType = 0;
    int _modePolicy = -1;
    private int bluetoothState = 4;
    final boolean[] _bluetooth_sco_connect = {false};
    boolean IsBluetoothA2dpExisted = true;
    boolean IsServiceReadytoStop = false;
    TraeAudioSessionHost _audioSessionHost = new TraeAudioSessionHost();
    DeviceConfigManager _deviceConfigManager = new DeviceConfigManager();
    BluetoohHeadsetCheckInterface _bluetoothCheck = null;
    String sessionConnectedDev = DEVICE_NONE;
    ReentrantLock _lock = new ReentrantLock();
    switchThread _switchThread = null;
    ReentrantLock _gSwitchTreadlock = new ReentrantLock();

    @TargetApi(11)
    public class BluetoohHeadsetCheck extends BluetoohHeadsetCheckInterface implements BluetoothProfile.ServiceListener {
        BluetoothAdapter _adapter;
        Context _ctx;
        DeviceConfigManager _devCfg;
        BluetoothProfile _profile;
        private final ReentrantLock _profileLock;

        public BluetoohHeadsetCheck() {
            super();
            this._ctx = null;
            this._devCfg = null;
            this._adapter = null;
            this._profile = null;
            this._profileLock = new ReentrantLock();
        }

        private List<BluetoothDevice> getConnectedDevices() {
            if (this._profile == null) {
                return null;
            }
            try {
                boolean z2 = true;
                if (TraeAudioManager.this._context != null && TXCBuild.VersionInt() >= 31 && TraeAudioManager.this._context.checkPermission(Permission.BLUETOOTH_CONNECT, Process.myPid(), Process.myUid()) != 0) {
                    z2 = false;
                }
                if (z2) {
                    return this._profile.getConnectedDevices();
                }
                return null;
            } catch (Exception e2) {
                if (!QLog.isColorLevel()) {
                    return null;
                }
                QLog.e(TraeAudioManager.TAG, 2, "get connected devices failed." + e2.getMessage());
                return null;
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void _addAction(IntentFilter intentFilter, IntentFilter intentFilter2) {
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, " " + interfaceDesc() + " _addAction");
            }
            intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void _onReceive(Context context, Intent intent) {
            if ("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 10);
                if (intExtra != 12) {
                    if (intExtra == 10) {
                        TraeAudioManager.this.checkAutoDeviceListUpdate(true);
                        return;
                    }
                    return;
                } else {
                    if (TraeAudioManager.this.bluetoothState == 6) {
                        QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread ACTION_AUDIO_STATE_CHANGED +++ Bluetooth audio SCO is now connected, SCO_CONNECTED");
                        TraeAudioManager.this.bluetoothState = 7;
                        synchronized (TraeAudioManager.this._bluetooth_sco_connect) {
                            boolean[] zArr = TraeAudioManager.this._bluetooth_sco_connect;
                            zArr[0] = true;
                            zArr.notifyAll();
                        }
                        return;
                    }
                    return;
                }
            }
            if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                if (intExtra2 == 0) {
                    TraeAudioManager.this.bluetoothState = 3;
                    this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                    QLog.w(TraeAudioManager.TAG, 2, "BluetoothHeadset ACTION_CONNECTION_STATE_CHANGED BluetoothProfile.STATE_DISCONNECTED");
                    return;
                } else {
                    if (intExtra2 != 2) {
                        return;
                    }
                    TraeAudioManager.this.bluetoothState = 4;
                    this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                    QLog.w(TraeAudioManager.TAG, 2, "BluetoothHeadset ACTION_CONNECTION_STATE_CHANGED BluetoothProfile.STATE_CONNECTED");
                    TraeAudioManager.this.checkAutoDeviceListUpdate(false);
                    return;
                }
            }
            if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra3 = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                int intExtra4 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_CONNECTION_STATE", -1);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "BT ACTION_CONNECTION_STATE_CHANGED|   EXTRA_CONNECTION_STATE " + getBTAdapterConnectionState(intExtra3));
                }
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "    EXTRA_PREVIOUS_CONNECTION_STATE " + getBTAdapterConnectionState(intExtra4));
                }
                if (QLog.isColorLevel()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("    EXTRA_DEVICE ");
                    sb.append(bluetoothDevice);
                    sb.append(" ");
                    sb.append(bluetoothDevice != null ? bluetoothDevice.getName() : " ");
                    QLog.w(TraeAudioManager.TAG, 2, sb.toString());
                }
                if (intExtra3 != 2) {
                    if (intExtra3 == 0) {
                        this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                        return;
                    }
                    return;
                }
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "   dev:" + bluetoothDevice.getName() + " connected");
                }
                String name = bluetoothDevice != null ? bluetoothDevice.getName() : "unkown";
                if (!name.contains("FreeBuds")) {
                    this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                }
                this._devCfg.setBluetoothName(name);
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        @TargetApi(11)
        public boolean init(Context context, DeviceConfigManager deviceConfigManager) {
            AudioDeviceInterface.LogTraceEntry("");
            if (context == null || deviceConfigManager == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " err ctx==null||_devCfg==null");
                }
                return false;
            }
            this._ctx = context;
            this._devCfg = deviceConfigManager;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this._adapter = defaultAdapter;
            if (defaultAdapter == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " err getDefaultAdapter fail!");
                }
                return false;
            }
            this._profileLock.lock();
            try {
                if (this._adapter.isEnabled() && this._profile == null && !this._adapter.getProfileProxy(this._ctx, this, 1)) {
                    if (QLog.isColorLevel()) {
                        QLog.e(TraeAudioManager.TAG, 2, "BluetoohHeadsetCheck: getProfileProxy HEADSET fail!");
                    }
                    return false;
                }
                this._profileLock.unlock();
                AudioDeviceInterface.LogTraceExit();
                return true;
            } finally {
                this._profileLock.unlock();
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public String interfaceDesc() {
            return "BluetoohHeadsetCheck";
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public boolean isConnected() {
            this._profileLock.lock();
            try {
                boolean z2 = false;
                if (this._profile != null) {
                    List<BluetoothDevice> connectedDevices = getConnectedDevices();
                    if (connectedDevices == null) {
                        return false;
                    }
                    if (connectedDevices.size() > 0) {
                        z2 = true;
                    }
                }
                return z2;
            } finally {
                this._profileLock.unlock();
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        @TargetApi(11)
        public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
            BluetoothProfile bluetoothProfile2;
            AudioDeviceInterface.LogTraceEntry("_profile:" + this._profile + " profile:" + i2 + " proxy:" + bluetoothProfile);
            if (i2 == 1) {
                this._profileLock.lock();
                try {
                    BluetoothProfile bluetoothProfile3 = this._profile;
                    if (bluetoothProfile3 != null && bluetoothProfile3 != bluetoothProfile) {
                        if (QLog.isColorLevel()) {
                            QLog.w(TraeAudioManager.TAG, 2, "BluetoohHeadsetCheck: HEADSET Connected proxy:" + bluetoothProfile + " _profile:" + this._profile);
                        }
                        this._adapter.closeProfileProxy(1, this._profile);
                        this._profile = null;
                    }
                    this._profile = bluetoothProfile;
                    List<BluetoothDevice> connectedDevices = getConnectedDevices();
                    if (connectedDevices != null && this._profile != null) {
                        if (QLog.isColorLevel()) {
                            QLog.w(TraeAudioManager.TAG, 2, "TRAEBluetoohProxy: HEADSET Connected devs:" + connectedDevices.size() + " _profile:" + this._profile);
                        }
                        for (int i3 = 0; i3 < connectedDevices.size(); i3++) {
                            BluetoothDevice bluetoothDevice = connectedDevices.get(i3);
                            try {
                                bluetoothProfile2 = this._profile;
                            } catch (Exception e2) {
                                if (QLog.isColorLevel()) {
                                    QLog.e(TraeAudioManager.TAG, 2, "get bluetooth connection state failed." + e2.getMessage());
                                }
                            }
                            int connectionState = bluetoothProfile2 != null ? bluetoothProfile2.getConnectionState(bluetoothDevice) : 0;
                            if (connectionState == 2) {
                                this._devCfg.setBluetoothName(bluetoothDevice.getName());
                            }
                            if (QLog.isColorLevel()) {
                                QLog.w(TraeAudioManager.TAG, 2, "   " + i3 + " " + bluetoothDevice.getName() + " ConnectionState:" + connectionState);
                            }
                        }
                    }
                    this._profileLock.unlock();
                    if (this._devCfg != null) {
                        DeviceConfigManager deviceConfigManager = TraeAudioManager.this._deviceConfigManager;
                        if (!TextUtils.isEmpty(deviceConfigManager != null ? deviceConfigManager.getBluetoothName() : null) && isConnected()) {
                            this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                            TraeAudioManager.this.checkDevicePlug(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                        } else {
                            this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                        }
                    }
                } catch (Throwable th) {
                    this._profileLock.unlock();
                    throw th;
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        @TargetApi(11)
        public void onServiceDisconnected(int i2) {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this._profile + " profile:" + i2);
            if (i2 == 1) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "TRAEBluetoohProxy: HEADSET Disconnected");
                }
                if (isConnected()) {
                    TraeAudioManager.this.checkDevicePlug(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
                this._profileLock.lock();
                try {
                    BluetoothProfile bluetoothProfile = this._profile;
                    if (bluetoothProfile != null) {
                        this._adapter.closeProfileProxy(1, bluetoothProfile);
                        this._profile = null;
                    }
                } finally {
                    this._profileLock.unlock();
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void release() {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this._profile);
            this._profileLock.lock();
            try {
                try {
                    BluetoothAdapter bluetoothAdapter = this._adapter;
                    if (bluetoothAdapter != null) {
                        BluetoothProfile bluetoothProfile = this._profile;
                        if (bluetoothProfile != null) {
                            bluetoothAdapter.closeProfileProxy(1, bluetoothProfile);
                        }
                        this._profile = null;
                    }
                } catch (Exception e2) {
                    if (QLog.isColorLevel()) {
                        QLog.w(TraeAudioManager.TAG, 2, " closeProfileProxy:e:" + e2.getMessage());
                    }
                }
                AudioDeviceInterface.LogTraceExit();
            } finally {
                this._profileLock.unlock();
            }
        }
    }

    public class BluetoohHeadsetCheckFake extends BluetoohHeadsetCheckInterface {
        public BluetoohHeadsetCheckFake() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void _addAction(IntentFilter intentFilter, IntentFilter intentFilter2) {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void _onReceive(Context context, Intent intent) {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public boolean init(Context context, DeviceConfigManager deviceConfigManager) {
            return true;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public String interfaceDesc() {
            return "BluetoohHeadsetCheckFake";
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public boolean isConnected() {
            return false;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void release() {
        }
    }

    public class BluetoohHeadsetCheckFor2x extends BluetoohHeadsetCheckInterface {
        public static final String ACTION_BLUETOOTHHEADSET_AUDIO_STATE_CHANGED = "android.bluetooth.headset.action.AUDIO_STATE_CHANGED";
        public static final String ACTION_BLUETOOTHHEADSET_STATE_CHANGED = "android.bluetooth.headset.action.STATE_CHANGED";
        public static final int AUDIO_STATE_CONNECTED = 1;
        public static final int AUDIO_STATE_DISCONNECTED = 0;
        static final int STATE_CONNECTED = 2;
        static final int STATE_DISCONNECTED = 0;
        Class<?> BluetoothHeadsetClass;
        Object BluetoothHeadsetObj;
        Class<?> ListenerClass;
        Context _ctx;
        DeviceConfigManager _devCfg;
        Method getCurrentHeadsetMethod;

        public BluetoohHeadsetCheckFor2x() {
            super();
            this.BluetoothHeadsetClass = null;
            this.ListenerClass = null;
            this.BluetoothHeadsetObj = null;
            this.getCurrentHeadsetMethod = null;
            this._ctx = null;
            this._devCfg = null;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void _addAction(IntentFilter intentFilter, IntentFilter intentFilter2) {
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, " " + interfaceDesc() + " _addAction");
            }
            intentFilter.addAction(ACTION_BLUETOOTHHEADSET_AUDIO_STATE_CHANGED);
            intentFilter.addAction(ACTION_BLUETOOTHHEADSET_STATE_CHANGED);
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void _onReceive(Context context, Intent intent) {
            if (ACTION_BLUETOOTHHEADSET_AUDIO_STATE_CHANGED.equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.headset.extra.STATE", -2);
                int intExtra2 = intent.getIntExtra("android.bluetooth.headset.extra.PREVIOUS_STATE", -2);
                int intExtra3 = intent.getIntExtra("android.bluetooth.headset.extra.AUDIO_STATE", -2);
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "++ AUDIO_STATE_CHANGED|  STATE " + intExtra);
                }
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "       PREVIOUS_STATE " + intExtra2);
                }
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "       AUDIO_STATE " + intExtra3);
                }
                if (intExtra3 == 2) {
                    this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                    return;
                } else {
                    if (intExtra3 == 0) {
                        this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                        return;
                    }
                    return;
                }
            }
            if (ACTION_BLUETOOTHHEADSET_STATE_CHANGED.equals(intent.getAction())) {
                int intExtra4 = intent.getIntExtra("android.bluetooth.headset.extra.STATE", -2);
                int intExtra5 = intent.getIntExtra("android.bluetooth.headset.extra.PREVIOUS_STATE", -2);
                int intExtra6 = intent.getIntExtra("android.bluetooth.headset.extra.AUDIO_STATE", -2);
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "++ STATE_CHANGED|  STATE " + intExtra4);
                }
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "       PREVIOUS_STATE " + intExtra5);
                }
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "       AUDIO_STATE " + intExtra6);
                }
                if (intExtra6 == 2) {
                    this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                } else if (intExtra6 == 0) {
                    this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public boolean init(Context context, DeviceConfigManager deviceConfigManager) {
            AudioDeviceInterface.LogTraceEntry("");
            this._ctx = context;
            this._devCfg = deviceConfigManager;
            if (context == null || deviceConfigManager == null) {
                return false;
            }
            try {
                this.BluetoothHeadsetClass = Class.forName("android.bluetooth.BluetoothHeadset");
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset class not found");
                }
            }
            if (this.BluetoothHeadsetClass == null) {
                return false;
            }
            try {
                this.ListenerClass = Class.forName("android.bluetooth.BluetoothHeadset$ServiceListener");
            } catch (Exception e2) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset.ServiceListener class not found:" + e2);
                }
            }
            try {
                this.getCurrentHeadsetMethod = this.BluetoothHeadsetClass.getDeclaredMethod("getCurrentHeadset", new Class[0]);
            } catch (NoSuchMethodException unused2) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset NoSuchMethodException");
                }
            }
            if (this.getCurrentHeadsetMethod == null) {
                return false;
            }
            try {
                this.BluetoothHeadsetObj = this.BluetoothHeadsetClass.getConstructor(Context.class, this.ListenerClass).newInstance(context, null);
            } catch (IllegalAccessException unused3) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset getConstructor IllegalAccessException");
                }
            } catch (IllegalArgumentException unused4) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset getConstructor IllegalArgumentException");
                }
            } catch (InstantiationException unused5) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset getConstructor InstantiationException");
                }
            } catch (NoSuchMethodException unused6) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset getConstructor NoSuchMethodException");
                }
            } catch (InvocationTargetException unused7) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread BluetoothHeadset getConstructor InvocationTargetException");
                }
            }
            if (this.BluetoothHeadsetObj == null) {
                return false;
            }
            this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, isConnected());
            if (isConnected()) {
                this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                TraeAudioManager.this.checkDevicePlug(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
            } else {
                this._devCfg.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
            }
            AudioDeviceInterface.LogTraceExit();
            return true;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public String interfaceDesc() {
            return "BluetoohHeadsetCheckFor2x";
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x005b A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean isConnected() throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
            /*
                r6 = this;
                java.lang.String r0 = "TraeAudioManager"
                java.lang.reflect.Method r1 = r6.getCurrentHeadsetMethod
                r2 = 0
                if (r1 == 0) goto L5c
                if (r1 != 0) goto La
                goto L5c
            La:
                r3 = 2
                java.lang.Object r4 = r6.BluetoothHeadsetObj     // Catch: java.lang.reflect.InvocationTargetException -> L14 java.lang.IllegalAccessException -> L20 java.lang.IllegalArgumentException -> L2c
                java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch: java.lang.reflect.InvocationTargetException -> L14 java.lang.IllegalAccessException -> L20 java.lang.IllegalArgumentException -> L2c
                java.lang.Object r1 = r1.invoke(r4, r5)     // Catch: java.lang.reflect.InvocationTargetException -> L14 java.lang.IllegalAccessException -> L20 java.lang.IllegalArgumentException -> L2c
                goto L38
            L14:
                boolean r1 = com.tencent.rtmp.sharp.jni.QLog.isColorLevel()
                if (r1 == 0) goto L37
                java.lang.String r1 = "BTLooperThread BluetoothHeadset method getCurrentHeadset InvocationTargetException"
                com.tencent.rtmp.sharp.jni.QLog.w(r0, r3, r1)
                goto L37
            L20:
                boolean r1 = com.tencent.rtmp.sharp.jni.QLog.isColorLevel()
                if (r1 == 0) goto L37
                java.lang.String r1 = "BTLooperThread BluetoothHeadset method getCurrentHeadset IllegalAccessException"
                com.tencent.rtmp.sharp.jni.QLog.w(r0, r3, r1)
                goto L37
            L2c:
                boolean r1 = com.tencent.rtmp.sharp.jni.QLog.isColorLevel()
                if (r1 == 0) goto L37
                java.lang.String r1 = "BTLooperThread BluetoothHeadset method getCurrentHeadset IllegalArgumentException"
                com.tencent.rtmp.sharp.jni.QLog.w(r0, r3, r1)
            L37:
                r1 = 0
            L38:
                boolean r4 = com.tencent.rtmp.sharp.jni.QLog.isColorLevel()
                if (r4 == 0) goto L59
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "BTLooperThread BluetoothHeadset method getCurrentHeadset res:"
                r4.append(r5)
                if (r1 == 0) goto L4d
                java.lang.String r5 = " Y"
                goto L4f
            L4d:
                java.lang.String r5 = "N"
            L4f:
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.tencent.rtmp.sharp.jni.QLog.w(r0, r3, r4)
            L59:
                if (r1 == 0) goto L5c
                r2 = 1
            L5c:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckFor2x.isConnected():boolean");
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.BluetoohHeadsetCheckInterface
        public void release() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Method declaredMethod;
            AudioDeviceInterface.LogTraceEntry("");
            if (this.BluetoothHeadsetObj == null) {
                return;
            }
            try {
                declaredMethod = this.BluetoothHeadsetClass.getDeclaredMethod("close", new Class[0]);
            } catch (NoSuchMethodException unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "BTLooperThread _uninitHeadsetfor2x method close NoSuchMethodException");
                }
                declaredMethod = null;
            }
            if (declaredMethod == null) {
                return;
            }
            try {
                declaredMethod.invoke(this.BluetoothHeadsetObj, new Object[0]);
            } catch (Exception e2) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "close bluetooth headset failed." + e2.getMessage());
                }
            }
            this.BluetoothHeadsetClass = null;
            this.ListenerClass = null;
            this.BluetoothHeadsetObj = null;
            this.getCurrentHeadsetMethod = null;
            AudioDeviceInterface.LogTraceExit();
        }
    }

    public abstract class BluetoohHeadsetCheckInterface {
        public BluetoohHeadsetCheckInterface() {
        }

        public abstract void _addAction(IntentFilter intentFilter, IntentFilter intentFilter2);

        public abstract void _onReceive(Context context, Intent intent);

        public void addAction(IntentFilter intentFilter, IntentFilter intentFilter2) {
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            _addAction(intentFilter, intentFilter2);
        }

        public String getBTActionStateChangedExtraString(int i2) {
            String str;
            switch (i2) {
                case 10:
                    str = "STATE_OFF";
                    break;
                case 11:
                    str = "STATE_TURNING_ON";
                    break;
                case 12:
                    str = "STATE_ON";
                    break;
                case 13:
                    str = "STATE_TURNING_OFF";
                    break;
                default:
                    str = "unknow";
                    break;
            }
            return str + ":" + i2;
        }

        public String getBTAdapterConnectionState(int i2) {
            return (i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "unknow" : "STATE_DISCONNECTING" : "STATE_CONNECTED" : "STATE_CONNECTING" : "STATE_DISCONNECTED") + ":" + i2;
        }

        public String getBTHeadsetAudioState(int i2) {
            String str;
            if (i2 == 10) {
                str = "STATE_AUDIO_DISCONNECTED";
            } else if (i2 != 12) {
                str = "unknow:" + i2;
            } else {
                str = "STATE_AUDIO_CONNECTED";
            }
            return str + ":" + i2;
        }

        public String getBTHeadsetConnectionState(int i2) {
            return (i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "unknow" : "STATE_DISCONNECTING" : "STATE_CONNECTED" : "STATE_CONNECTING" : "STATE_DISCONNECTED") + ":" + i2;
        }

        public String getSCOAudioStateExtraString(int i2) {
            return (i2 != -1 ? i2 != 0 ? i2 != 1 ? i2 != 2 ? "unknow" : "SCO_AUDIO_STATE_CONNECTING" : "SCO_AUDIO_STATE_CONNECTED" : "SCO_AUDIO_STATE_DISCONNECTED" : "SCO_AUDIO_STATE_ERROR") + ":" + i2;
        }

        public abstract boolean init(Context context, DeviceConfigManager deviceConfigManager);

        public abstract String interfaceDesc();

        public abstract boolean isConnected();

        public void onReceive(Context context, Intent intent, DeviceConfigManager deviceConfigManager) {
            if (!"android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction()) || TXCBuild.VersionInt() >= 11) {
                    if (!"android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction()) || TXCBuild.VersionInt() >= 11) {
                        _onReceive(context, intent);
                        return;
                    }
                    return;
                }
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
            int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1);
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, "BT ACTION_STATE_CHANGED|   EXTRA_STATE " + getBTActionStateChangedExtraString(intExtra));
            }
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, "BT ACTION_STATE_CHANGED|   EXTRA_PREVIOUS_STATE " + getBTActionStateChangedExtraString(intExtra2));
            }
            if (intExtra == 10) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "    BT off");
                }
                deviceConfigManager.setVisible(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
            } else if (intExtra == 12 && QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, "BT OFF-->ON,Visiable it...");
            }
        }

        public abstract void release();
    }

    public interface Bluetooth_State {
        public static final int ERROR = 2;
        public static final int HEADSET_AVAILABLE = 4;
        public static final int HEADSET_UNAVAILABLE = 3;
        public static final int SCO_CONNECTED = 7;
        public static final int SCO_CONNECTING = 6;
        public static final int SCO_DISCONNECTED = 8;
        public static final int SCO_DISCONNECTING = 5;
        public static final int UNINITIALIZED = 1;
    }

    public class Parameters {
        public static final String BLUETOOTHPOLICY = "com.tencent.sharp.TraeAudioManager.Parameters.BLUETOOTHPOLICY";
        public static final String CONTEXT = "com.tencent.sharp.TraeAudioManager.Parameters.CONTEXT";
        public static final String DEVICECONFIG = "com.tencent.sharp.TraeAudioManager.Parameters.DEVICECONFIG";
        public static final String MODEPOLICY = "com.tencent.sharp.TraeAudioManager.Parameters.MODEPOLICY";

        public Parameters() {
        }
    }

    public class TraeAudioManagerLooper extends Thread {
        public static final int MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE = 32786;
        public static final int MESSAGE_AUTO_DEVICELIST_PLUGOUT_UPDATE = 32787;
        public static final int MESSAGE_AUTO_DEVICELIST_UPDATE = 32785;
        public static final int MESSAGE_BEGIN = 32768;
        public static final int MESSAGE_CONNECTDEVICE = 32775;
        public static final int MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE = 32789;
        public static final int MESSAGE_DISABLE = 32773;
        public static final int MESSAGE_EARACTION = 32776;
        public static final int MESSAGE_ENABLE = 32772;
        public static final int MESSAGE_GETCONNECTEDDEVICE = 32778;
        public static final int MESSAGE_GETCONNECTINGDEVICE = 32779;
        public static final int MESSAGE_GETDEVICELIST = 32774;
        public static final int MESSAGE_GETSTREAMTYPE = 32784;
        public static final int MESSAGE_ISDEVICECHANGABLED = 32777;
        public static final int MESSAGE_NOTIFY_DEVICELIST_UPDATE = 32793;
        public static final int MESSAGE_RECOVER_AUDIO_FOCUS = 32791;
        public static final int MESSAGE_REQUEST_RELEASE_AUDIO_FOCUS = 32790;
        public static final int MESSAGE_STARTRING = 32782;
        public static final int MESSAGE_STOPRING = 32783;
        public static final int MESSAGE_VOICECALLPOSTPROCESS = 32781;
        public static final int MESSAGE_VOICECALLPREPROCESS = 32780;
        public static final int MESSAGE_VOICECALL_AUIDOPARAM_CHANGED = 32788;
        TraeAudioManager _parent;
        final boolean[] _started;
        Handler mMsgHandler = null;
        TraeMediaPlayer _ringPlayer = null;
        long _ringSessionID = -1;
        String _ringOperation = "";
        String _ringUserdata = "";
        boolean _enabled = false;
        String _lastCfg = "";
        int _preServiceMode = 0;
        int _preRingMode = 0;
        long _voiceCallSessionID = -1;
        String _voiceCallOperation = "";
        AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;
        int _focusSteamType = 0;

        public TraeAudioManagerLooper(TraeAudioManager traeAudioManager) {
            boolean[] zArr = {false};
            this._started = zArr;
            this._parent = traeAudioManager;
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (QLog.isColorLevel()) {
                QLog.e(TraeAudioManager.TAG, 2, "TraeAudioManagerLooper start...");
            }
            start();
            synchronized (zArr) {
                if (!zArr[0]) {
                    try {
                        zArr.wait(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            if (QLog.isColorLevel()) {
                QLog.e(TraeAudioManager.TAG, 2, "  start used:" + (SystemClock.elapsedRealtime() - jElapsedRealtime) + "ms");
            }
        }

        public int InternalGetStreamType(HashMap<String, Object> map) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this._preRingMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (!QLog.isColorLevel()) {
                    return -1;
                }
                QLog.e(TraeAudioManager.TAG, 2, " InternalStopRing am==null!!");
                return -1;
            }
            int streamType = traeAudioManager._activeMode == 2 ? this._ringPlayer.getStreamType() : traeAudioManager._streamType;
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.EXTRA_DATA_STREAMTYPE, streamType);
            TraeAudioManager.this.sendResBroadcast(intent, map, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }

        public int InternalNotifyStreamTypeUpdate(final int i2) {
            if (TraeAudioManager.this._context == null) {
                return -1;
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.TraeAudioManagerLooper.3
                @Override // java.lang.Runnable
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
                    intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_STREAMTYPE_UPDATE);
                    intent.putExtra(TraeAudioManager.EXTRA_DATA_STREAMTYPE, i2);
                    Context context = TraeAudioManager.this._context;
                    if (context != null) {
                        b.a(context).a(intent);
                    }
                }
            });
            return 0;
        }

        public int InternalSessionGetDeviceList(HashMap<String, Object> map) {
            Intent intent = new Intent();
            HashMap<String, Object> snapParams = TraeAudioManager.this._deviceConfigManager.getSnapParams();
            ArrayList arrayList = (ArrayList) snapParams.get(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST);
            String str = (String) snapParams.get(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE);
            String str2 = (String) snapParams.get(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE);
            intent.putExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, (String[]) arrayList.toArray(new String[0]));
            intent.putExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, str);
            intent.putExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, str2);
            intent.putExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME, TraeAudioManager.this._deviceConfigManager.getBluetoothName());
            TraeAudioManager.this.sendResBroadcast(intent, map, 0);
            return 0;
        }

        public int InternalStartRing(HashMap<String, Object> map) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " InternalStartRing am==null!!");
                }
                return -1;
            }
            if (traeAudioManager._activeMode == 2) {
                interruptRing();
            }
            try {
                this._ringSessionID = ((Long) map.get(TraeAudioManager.PARAM_SESSIONID)).longValue();
                this._ringOperation = (String) map.get(TraeAudioManager.PARAM_OPERATION);
                this._ringUserdata = (String) map.get(TraeAudioManager.PARAM_RING_USERDATA_STRING);
                int iIntValue = ((Integer) map.get(TraeAudioManager.PARAM_RING_DATASOURCE)).intValue();
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "  dataSource:" + iIntValue);
                }
                int iIntValue2 = ((Integer) map.get(TraeAudioManager.PARAM_RING_RSID)).intValue();
                Uri uri = (Uri) map.get(TraeAudioManager.PARAM_RING_URI);
                String str = (String) map.get(TraeAudioManager.PARAM_RING_FILEPATH);
                boolean zBooleanValue = ((Boolean) map.get(TraeAudioManager.PARAM_RING_LOOP)).booleanValue();
                int iIntValue3 = ((Integer) map.get(TraeAudioManager.PARAM_RING_LOOPCOUNT)).intValue();
                boolean zBooleanValue2 = ((Boolean) map.get(TraeAudioManager.PARAM_RING_MODE)).booleanValue();
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                if (traeAudioManager2._activeMode != 1) {
                    traeAudioManager2._activeMode = 2;
                }
                Intent intent = new Intent();
                intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this._ringUserdata);
                TraeAudioManager.this.sendResBroadcast(intent, map, 0);
                this._preRingMode = TraeAudioManager.this._am.getMode();
                TraeMediaPlayer traeMediaPlayer = this._ringPlayer;
                TraeAudioManager traeAudioManager3 = TraeAudioManager.this;
                traeMediaPlayer.playRing(iIntValue, iIntValue2, uri, str, zBooleanValue, iIntValue3, zBooleanValue2, traeAudioManager3._activeMode == 1, traeAudioManager3._streamType);
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, " _ringUserdata:" + this._ringUserdata + " DurationMS:" + this._ringPlayer.getDuration());
                }
                InternalNotifyStreamTypeUpdate(this._ringPlayer.getStreamType());
                AudioDeviceInterface.LogTraceExit();
                return 0;
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " startRing err params");
                }
                return -1;
            }
        }

        public int InternalStopRing(HashMap<String, Object> map) {
            TraeMediaPlayer traeMediaPlayer;
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this._preRingMode);
            if (TraeAudioManager.this._am == null || (traeMediaPlayer = this._ringPlayer) == null) {
                if (!QLog.isColorLevel()) {
                    return -1;
                }
                QLog.e(TraeAudioManager.TAG, 2, " InternalStopRing am==null!!");
                return -1;
            }
            traeMediaPlayer.stopRing();
            if (!this._ringPlayer.hasCall() && TraeAudioManager.this._activeMode == 2) {
                abandonAudioFocus();
                TraeAudioManager.this._activeMode = 0;
            }
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, map, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }

        public int InternalVoicecallPostprocess(HashMap<String, Object> map) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " InternalVoicecallPostprocess am==null!!");
                }
                return -1;
            }
            if (traeAudioManager._activeMode == 1) {
                traeAudioManager._activeMode = 0;
                AudioDeviceInterface.LogTraceExit();
                return 0;
            }
            if (QLog.isColorLevel()) {
                QLog.e(TraeAudioManager.TAG, 2, " not ACTIVE_VOICECALL!!");
            }
            TraeAudioManager.this.sendResBroadcast(new Intent(), map, 3);
            return -1;
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x00fa  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int InternalVoicecallPreprocess(java.util.HashMap<java.lang.String, java.lang.Object> r8) {
            /*
                Method dump skipped, instructions count: 275
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.sharp.jni.TraeAudioManager.TraeAudioManagerLooper.InternalVoicecallPreprocess(java.util.HashMap):int");
        }

        public void _init() {
            AudioDeviceInterface.LogTraceEntry("");
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._deviceConfigManager == null) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "_deviceConfigManager is null");
                    return;
                }
                return;
            }
            try {
                traeAudioManager._am = (AudioManager) traeAudioManager._context.getSystemService("audio");
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                traeAudioManager2._bluetoothCheck = traeAudioManager2.CreateBluetoothCheck(traeAudioManager2._context, traeAudioManager2._deviceConfigManager);
                IntentFilter intentFilter = new IntentFilter();
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                TraeAudioManager.this._bluetoothCheck.addAction(intentFilter, intentFilter2);
                TraeAudioManager.this._context.registerReceiver(this._parent, intentFilter);
                intentFilter2.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
                b.a(TraeAudioManager.this._context).a(this._parent, intentFilter2);
                QLog.i(TraeAudioManager.TAG, 2, "register receiver in _init");
            } catch (Exception e2) {
                QLog.e(TraeAudioManager.TAG, 2, "init failed, " + e2.getMessage());
            }
            AudioDeviceInterface.LogTraceExit();
        }

        public void _post_stopService() {
            AudioDeviceInterface.LogTraceEntry("");
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._deviceConfigManager == null) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "_deviceConfigManager is null");
                    return;
                }
                return;
            }
            try {
                BluetoohHeadsetCheckInterface bluetoohHeadsetCheckInterface = traeAudioManager._bluetoothCheck;
                if (bluetoohHeadsetCheckInterface != null) {
                    bluetoohHeadsetCheckInterface.release();
                }
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                traeAudioManager2._bluetoothCheck = null;
                Context context = traeAudioManager2._context;
                if (context != null) {
                    try {
                        context.unregisterReceiver(this._parent);
                    } catch (Exception unused) {
                    }
                    QLog.i(TraeAudioManager.TAG, 2, "unregister receiver in _post_stopService");
                    b.a(TraeAudioManager.this._context).a(this._parent);
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
                    b.a(TraeAudioManager.this._context).a(this._parent, intentFilter);
                }
            } catch (Exception e2) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "stop service failed." + e2.getMessage());
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        public void _prev_startService() {
            AudioDeviceInterface.LogTraceEntry("");
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._deviceConfigManager == null) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "_deviceConfigManager is null");
                    return;
                }
                return;
            }
            try {
                traeAudioManager._am = (AudioManager) traeAudioManager._context.getSystemService("audio");
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                if (traeAudioManager2._bluetoothCheck == null) {
                    traeAudioManager2._bluetoothCheck = traeAudioManager2.CreateBluetoothCheck(traeAudioManager2._context, traeAudioManager2._deviceConfigManager);
                }
                try {
                    TraeAudioManager.this._context.unregisterReceiver(this._parent);
                } catch (Exception unused) {
                }
                QLog.i(TraeAudioManager.TAG, 2, "unregister receiver in _prev_startService");
                b.a(TraeAudioManager.this._context).a(this._parent);
                IntentFilter intentFilter = new IntentFilter();
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                TraeAudioManager.this._bluetoothCheck.addAction(intentFilter, intentFilter2);
                intentFilter2.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
                TraeAudioManager.this._context.registerReceiver(this._parent, intentFilter);
                QLog.i(TraeAudioManager.TAG, 2, "register receiver in _prev_startService");
                b.a(TraeAudioManager.this._context).a(this._parent, intentFilter2);
            } catch (Exception e2) {
                QLog.w(TraeAudioManager.TAG, 2, "prev start service failed." + e2.getMessage());
            }
            AudioDeviceInterface.LogTraceExit();
        }

        public void _uninit() {
            AudioDeviceInterface.LogTraceEntry("");
            try {
                stopService();
                BluetoohHeadsetCheckInterface bluetoohHeadsetCheckInterface = TraeAudioManager.this._bluetoothCheck;
                if (bluetoohHeadsetCheckInterface != null) {
                    bluetoohHeadsetCheckInterface.release();
                }
                TraeAudioManager traeAudioManager = TraeAudioManager.this;
                traeAudioManager._bluetoothCheck = null;
                Context context = traeAudioManager._context;
                if (context != null) {
                    try {
                        context.unregisterReceiver(this._parent);
                    } catch (Exception unused) {
                    }
                    QLog.i(TraeAudioManager.TAG, 2, "unregister receiver in _uninit");
                    b.a(TraeAudioManager.this._context).a(this._parent);
                    TraeAudioManager.this._context = null;
                }
                DeviceConfigManager deviceConfigManager = TraeAudioManager.this._deviceConfigManager;
                if (deviceConfigManager != null) {
                    deviceConfigManager.clearConfig();
                }
            } catch (Exception e2) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "uninit failed." + e2.getMessage());
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @TargetApi(8)
        public void abandonAudioFocus() {
        }

        public int interruptRing() {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this._preRingMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " interruptRing am==null!!");
                }
                return -1;
            }
            if (traeAudioManager._activeMode != 2) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " not ACTIVE_RING!!");
                }
                return -1;
            }
            this._ringPlayer.stopRing();
            abandonAudioFocus();
            TraeAudioManager.this._activeMode = 0;
            HashMap<String, Object> map = new HashMap<>();
            map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this._ringSessionID));
            map.put(TraeAudioManager.PARAM_OPERATION, this._ringOperation);
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, map, 4);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }

        public int interruptVoicecallPostprocess() {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " am==null!!");
                }
                return -1;
            }
            if (traeAudioManager._activeMode != 1) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " not ACTIVE_RING!!");
                }
                return -1;
            }
            traeAudioManager._activeMode = 0;
            int i2 = traeAudioManager._prevMode;
            if (i2 != -1) {
                traeAudioManager.InternalSetMode(i2);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this._voiceCallSessionID));
            map.put(TraeAudioManager.PARAM_OPERATION, this._voiceCallOperation);
            TraeAudioManager.this.sendResBroadcast(new Intent(), map, 6);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }

        public boolean isNeedForceVolumeType() {
            if (TXCBuild.Manufacturer().equals("Xiaomi")) {
                return TXCBuild.Model().equals("MI 5") || TXCBuild.Model().equals("MI 5s") || TXCBuild.Model().equals("MI 5s Plus");
            }
            return false;
        }

        public String msgToText(int i2) {
            if (i2 == 32768) {
                return "MESSAGE_BEGIN";
            }
            switch (i2) {
                case 32772:
                    return "MESSAGE_ENABLE";
                case 32773:
                    return "MESSAGE_DISABLE";
                case 32774:
                    return "MESSAGE_GETDEVICELIST";
                case 32775:
                    return "MESSAGE_CONNECTDEVICE";
                case 32776:
                    return "MESSAGE_EARACTION";
                case 32777:
                    return "MESSAGE_ISDEVICECHANGABLED";
                case MESSAGE_GETCONNECTEDDEVICE /* 32778 */:
                    return "MESSAGE_GETCONNECTEDDEVICE";
                case 32779:
                    return "MESSAGE_GETCONNECTINGDEVICE";
                case MESSAGE_VOICECALLPREPROCESS /* 32780 */:
                    return "MESSAGE_VOICECALLPREPROCESS";
                case 32781:
                    return "MESSAGE_VOICECALLPOSTPROCESS";
                case 32782:
                    return "MESSAGE_STARTRING";
                case 32783:
                    return "MESSAGE_STOPRING";
                case 32784:
                    return "MESSAGE_GETSTREAMTYPE";
                case 32785:
                    return "MESSAGE_AUTO_DEVICELIST_UPDATE";
                case 32786:
                    return "MESSAGE_AUTO_DEVICELIST_PLUGIN_UPDATE";
                case 32787:
                    return "MESSAGE_AUTO_DEVICELIST_PLUGOUT_UPDATE";
                case 32788:
                    return "MESSAGE_VOICECALL_AUIDOPARAM_CHANGED";
                case MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE /* 32789 */:
                    return "MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE";
                case 32790:
                    return "MESSAGE_REQUEST_RELEASE_AUDIO_FOCUS";
                case 32791:
                    return "MESSAGE_RECOVER_AUDIO_FOCUS";
                default:
                    return "MESSAGE_NONE";
            }
        }

        public void notifyRingCompletion() {
            HashMap<String, Object> map = new HashMap<>();
            map.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this._ringSessionID));
            map.put(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_RING_COMPLETION);
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this._ringUserdata);
            TraeAudioManager.this.sendResBroadcast(intent, map, 0);
        }

        public int notifyServiceState(boolean z2) {
            if (TraeAudioManager.this._context == null) {
                return -1;
            }
            Intent intent = new Intent();
            intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
            intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_SERVICE_STATE);
            intent.putExtra(TraeAudioManager.NOTIFY_SERVICE_STATE_DATE, z2);
            Context context = TraeAudioManager.this._context;
            if (context == null) {
                return 0;
            }
            b.a(context).a(intent);
            return 0;
        }

        public void quit() {
            AudioDeviceInterface.LogTraceEntry("");
            if (this.mMsgHandler == null) {
                return;
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.mMsgHandler.getLooper().quit();
            synchronized (this._started) {
                boolean[] zArr = this._started;
                if (zArr[0]) {
                    try {
                        zArr.wait(a.f7153q);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            if (QLog.isColorLevel()) {
                QLog.e(TraeAudioManager.TAG, 2, "  quit used:" + (SystemClock.elapsedRealtime() - jElapsedRealtime) + "ms");
            }
            this.mMsgHandler = null;
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            AudioDeviceInterface.LogTraceEntry("");
            Looper.prepare();
            this.mMsgHandler = new Handler() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.TraeAudioManagerLooper.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    HashMap<String, Object> map;
                    try {
                        map = (HashMap) message.obj;
                    } catch (Exception unused) {
                        map = null;
                    }
                    if (QLog.isColorLevel()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("TraeAudioManagerLooper msg ");
                        sb.append(message.what);
                        sb.append(":");
                        sb.append(TraeAudioManagerLooper.this.msgToText(message.what));
                        sb.append(" _enabled:");
                        sb.append(TraeAudioManagerLooper.this._enabled ? "Y" : "N");
                        QLog.w(TraeAudioManager.TAG, 2, sb.toString());
                    }
                    int i2 = message.what;
                    if (i2 == 32772) {
                        TraeAudioManagerLooper.this.startService(map);
                        return;
                    }
                    TraeAudioManagerLooper traeAudioManagerLooper = TraeAudioManagerLooper.this;
                    if (!traeAudioManagerLooper._enabled) {
                        if (QLog.isColorLevel()) {
                            QLog.w(TraeAudioManager.TAG, 2, "******* disabled ,skip msg******");
                        }
                        TraeAudioManager.this.sendResBroadcast(new Intent(), map, 1);
                        return;
                    }
                    switch (i2) {
                        case 32773:
                            traeAudioManagerLooper.stopService();
                            break;
                        case 32774:
                            traeAudioManagerLooper.InternalSessionGetDeviceList(map);
                            break;
                        case 32775:
                            TraeAudioManager.this.InternalSessionConnectDevice(map);
                            break;
                        case 32776:
                            TraeAudioManager.this.InternalSessionEarAction(map);
                            break;
                        case 32777:
                            TraeAudioManager.this.InternalSessionIsDeviceChangabled(map);
                            break;
                        case TraeAudioManagerLooper.MESSAGE_GETCONNECTEDDEVICE /* 32778 */:
                            TraeAudioManager.this.InternalSessionGetConnectedDevice(map);
                            break;
                        case 32779:
                            TraeAudioManager.this.InternalSessionGetConnectingDevice(map);
                            break;
                        case TraeAudioManagerLooper.MESSAGE_VOICECALLPREPROCESS /* 32780 */:
                            traeAudioManagerLooper.InternalVoicecallPreprocess(map);
                            break;
                        case 32781:
                            traeAudioManagerLooper.InternalVoicecallPostprocess(map);
                            break;
                        case 32782:
                            traeAudioManagerLooper.InternalStartRing(map);
                            break;
                        case 32783:
                            traeAudioManagerLooper.InternalStopRing(map);
                            break;
                        case 32784:
                            traeAudioManagerLooper.InternalGetStreamType(map);
                            break;
                        case 32785:
                        case TraeAudioManagerLooper.MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE /* 32789 */:
                            String availabledHighestPriorityDevice = TraeAudioManager.this._deviceConfigManager.getAvailabledHighestPriorityDevice();
                            String connectedDevice = TraeAudioManager.this._deviceConfigManager.getConnectedDevice();
                            if (QLog.isColorLevel()) {
                                QLog.w(TraeAudioManager.TAG, 2, "MESSAGE_AUTO_DEVICELIST_UPDATE  connectedDev:" + connectedDevice + " highestDev" + availabledHighestPriorityDevice);
                            }
                            if (!TraeAudioManager.IsUpdateSceneFlag) {
                                if (!TraeAudioManager.DEVICE_BLUETOOTHHEADSET.equals(connectedDevice) && availabledHighestPriorityDevice.equals(connectedDevice)) {
                                    TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                                    break;
                                } else {
                                    TraeAudioManager.this.InternalConnectDevice(availabledHighestPriorityDevice, null, false);
                                    break;
                                }
                            } else {
                                if (TraeAudioManager.IsMusicScene) {
                                    TraeAudioManager traeAudioManager = TraeAudioManager.this;
                                    if (!traeAudioManager.IsBluetoothA2dpExisted) {
                                        traeAudioManager.InternalConnectDevice(traeAudioManager._deviceConfigManager.getAvailabledHighestPriorityDevice(TraeAudioManager.DEVICE_BLUETOOTHHEADSET), null, true);
                                        break;
                                    }
                                }
                                TraeAudioManager.this.InternalConnectDevice(availabledHighestPriorityDevice, null, true);
                                break;
                            }
                            break;
                        case 32786:
                            String str = (String) map.get(TraeAudioManager.PARAM_DEVICE);
                            if (TraeAudioManager.this.InternalConnectDevice(str, null, false) != 0) {
                                if (QLog.isColorLevel()) {
                                    QLog.w(TraeAudioManager.TAG, 2, " plugin dev:" + str + " sessionConnectedDev:" + TraeAudioManager.this.sessionConnectedDev + " connected fail,auto switch!");
                                }
                                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                                traeAudioManager2.InternalConnectDevice(traeAudioManager2._deviceConfigManager.getAvailabledHighestPriorityDevice(), null, false);
                                break;
                            }
                            break;
                        case 32787:
                            TraeAudioManager traeAudioManager3 = TraeAudioManager.this;
                            if (traeAudioManager3.InternalConnectDevice(traeAudioManager3.sessionConnectedDev, null, false) != 0) {
                                QLog.w("TRAE", 2, " plugout dev:" + ((String) map.get(TraeAudioManager.PARAM_DEVICE)) + " sessionConnectedDev:" + TraeAudioManager.this.sessionConnectedDev + " connected fail,auto switch!");
                                TraeAudioManager traeAudioManager4 = TraeAudioManager.this;
                                traeAudioManager4.InternalConnectDevice(traeAudioManager4._deviceConfigManager.getAvailabledHighestPriorityDevice(), null, false);
                                break;
                            }
                            break;
                        case 32788:
                            Integer num = (Integer) map.get(TraeAudioManager.PARAM_STREAMTYPE);
                            if (num != null) {
                                TraeAudioManager.this._streamType = num.intValue();
                                TraeAudioManagerLooper.this.InternalNotifyStreamTypeUpdate(num.intValue());
                                break;
                            } else if (QLog.isColorLevel()) {
                                QLog.e(TraeAudioManager.TAG, 2, " MESSAGE_VOICECALL_AUIDOPARAM_CHANGED params.get(PARAM_STREAMTYPE)==null!!");
                                break;
                            }
                            break;
                        case 32790:
                            traeAudioManagerLooper.abandonAudioFocus();
                            break;
                        case 32793:
                            TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                            break;
                    }
                }
            };
            _init();
            synchronized (this._started) {
                boolean[] zArr = this._started;
                zArr[0] = true;
                zArr.notifyAll();
            }
            Looper.loop();
            _uninit();
            synchronized (this._started) {
                boolean[] zArr2 = this._started;
                zArr2[0] = false;
                zArr2.notifyAll();
            }
            AudioDeviceInterface.LogTraceExit();
        }

        public int sendMessage(int i2, HashMap<String, Object> map) {
            Handler handler = this.mMsgHandler;
            if (handler != null) {
                Message messageObtain = Message.obtain(handler, i2, map);
                if (i2 != 32786 || TraeAudioManager.this.InternalIsDeviceChangeable()) {
                    return this.mMsgHandler.sendMessage(messageObtain) ? 0 : -1;
                }
                QLog.w(TraeAudioManager.TAG, 2, "sendMessageDelayed, device is connecting, plugin need delay 1 second");
                return this.mMsgHandler.sendMessageDelayed(messageObtain, 1000L) ? 0 : -1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(" fail mMsgHandler==null _enabled:");
            sb.append(this._enabled ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(TraeAudioManager.this._activeMode);
            sb.append(" msg:");
            sb.append(i2);
            AudioDeviceInterface.LogTraceEntry(sb.toString());
            return -1;
        }

        public void startService(HashMap<String, Object> map) {
            String str = (String) map.get(TraeAudioManager.EXTRA_DATA_DEVICECONFIG);
            QLog.w(TraeAudioManager.TAG, 2, "startService cfg:" + str);
            StringBuilder sb = new StringBuilder();
            sb.append(" _enabled:");
            sb.append(this._enabled ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(TraeAudioManager.this._activeMode);
            sb.append(" cfg:");
            sb.append(str);
            AudioDeviceInterface.LogTraceEntry(sb.toString());
            if (TraeAudioManager.this._context == null) {
                return;
            }
            QLog.w(TraeAudioManager.TAG, 2, "   startService:" + str);
            if (!(this._enabled && this._lastCfg.equals(str)) && TraeAudioManager.this._activeMode == 0) {
                if (this._enabled) {
                    stopService();
                }
                _prev_startService();
                DeviceConfigManager deviceConfigManager = TraeAudioManager.this._deviceConfigManager;
                if (deviceConfigManager != null) {
                    deviceConfigManager.clearConfig();
                    TraeAudioManager.this._deviceConfigManager.init(str);
                }
                this._lastCfg = str;
                AudioManager audioManager = TraeAudioManager.this._am;
                if (audioManager != null) {
                    this._preServiceMode = audioManager.getMode();
                }
                this._enabled = true;
                TraeAudioManager traeAudioManager = TraeAudioManager.this;
                traeAudioManager.IsServiceReadytoStop = false;
                if (this._ringPlayer == null) {
                    this._ringPlayer = new TraeMediaPlayer(traeAudioManager._context, new TraeMediaPlayer.OnCompletionListener() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.TraeAudioManagerLooper.1
                        @Override // com.tencent.rtmp.sharp.jni.TraeMediaPlayer.OnCompletionListener
                        public void onCompletion() {
                            if (QLog.isColorLevel()) {
                                QLog.w(TraeAudioManager.TAG, 2, "_ringPlayer onCompletion _activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + TraeAudioManagerLooper.this._preRingMode);
                            }
                            HashMap<String, Object> map2 = new HashMap<>();
                            map2.put(TraeAudioManager.PARAM_ISHOSTSIDE, Boolean.TRUE);
                            TraeAudioManagerLooper.this.sendMessage(32783, map2);
                            TraeAudioManagerLooper.this.notifyRingCompletion();
                        }
                    });
                }
                TraeAudioManager.this.updateDeviceStatus();
                notifyServiceState(this._enabled);
                AudioDeviceInterface.LogTraceExit();
            }
        }

        public void stopService() {
            StringBuilder sb = new StringBuilder();
            sb.append(" _enabled:");
            sb.append(this._enabled ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(TraeAudioManager.this._activeMode);
            AudioDeviceInterface.LogTraceEntry(sb.toString());
            if (this._enabled) {
                TraeAudioManager traeAudioManager = TraeAudioManager.this;
                traeAudioManager.IsServiceReadytoStop = true;
                int i2 = traeAudioManager._activeMode;
                if (i2 == 1) {
                    interruptVoicecallPostprocess();
                } else if (i2 == 2) {
                    interruptRing();
                }
                TraeAudioManager.this._gSwitchTreadlock.lock();
                if (TraeAudioManager.this._switchThread != null) {
                    if (QLog.isColorLevel()) {
                        QLog.w(TraeAudioManager.TAG, 2, "_switchThread:" + TraeAudioManager.this._switchThread.getDeviceName());
                    }
                    TraeAudioManager.this._switchThread.quit();
                    TraeAudioManager.this._switchThread = null;
                }
                TraeAudioManager.this._gSwitchTreadlock.unlock();
                TraeMediaPlayer traeMediaPlayer = this._ringPlayer;
                if (traeMediaPlayer != null) {
                    traeMediaPlayer.stopRing();
                }
                this._ringPlayer = null;
                this._enabled = false;
                notifyServiceState(false);
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                if (traeAudioManager2._am != null && traeAudioManager2._context != null) {
                    try {
                        traeAudioManager2.InternalSetMode(0);
                        if (isNeedForceVolumeType()) {
                            QLog.w(TraeAudioManager.TAG, 2, "NeedForceVolumeType: AudioManager.STREAM_MUSIC");
                            TraeAudioManager.forceVolumeControlStream(TraeAudioManager.this._am, 3);
                        }
                    } catch (Exception e2) {
                        if (QLog.isColorLevel()) {
                            QLog.e(TraeAudioManager.TAG, 2, "set mode failed." + e2.getMessage());
                        }
                    }
                }
                _post_stopService();
                AudioDeviceInterface.LogTraceExit();
            }
        }
    }

    public class bluetoothHeadsetSwitchThread extends switchThread {
        public bluetoothHeadsetSwitchThread() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        @TargetApi(8)
        public void _quit() {
            if (TraeAudioManager.this._am == null) {
                return;
            }
            QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread _quit _stopBluetoothSco");
            _stopBluetoothSco();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _run() throws InterruptedException {
            boolean z2;
            if (TraeAudioManager.IsMusicScene || !TraeAudioManager.IsUpdateSceneFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "connect bluetoothHeadset: do nothing, IsMusicScene:" + TraeAudioManager.IsMusicScene + " ,IsUpdateSceneFlag:" + TraeAudioManager.IsUpdateSceneFlag);
                }
                updateStatus();
                return;
            }
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "connect bluetoothHeadset: disableDeviceSwitchFlag");
                    return;
                }
                return;
            }
            TraeAudioManager.this._deviceConfigManager.getBluetoothName();
            try {
                if (TraeAudioManager.this.bluetoothState == 8) {
                    QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread SCO_DISCONNECTED sleep 5000");
                    Thread.sleep(5000L);
                } else {
                    QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread sleep 1000");
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException unused) {
            }
            TraeAudioManager.this.bluetoothState = 4;
            int i2 = 1;
            if (this._running) {
                TraeAudioManager.this.bluetoothState = 6;
                synchronized (TraeAudioManager.this._bluetooth_sco_connect) {
                    TraeAudioManager.this._bluetooth_sco_connect[0] = false;
                }
                _startBluetoothSco();
                QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread _startBluetoothSco");
                z2 = true;
            } else {
                z2 = false;
            }
            while (true) {
                if (!this._running) {
                    break;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("bluetoothHeadsetSwitchThread i:");
                sb.append(i2);
                sb.append(" sco:");
                sb.append(TraeAudioManager.this._am.isBluetoothScoOn() ? "Y" : "N");
                sb.append(" :");
                sb.append(TraeAudioManager.this._deviceConfigManager.getBluetoothName());
                QLog.w(TraeAudioManager.TAG, 2, sb.toString());
                if (TraeAudioManager.this.bluetoothState == 7) {
                    QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread bluetoothState ==  Bluetooth_State.SCO_CONNECTED 1");
                    updateStatus();
                    break;
                }
                synchronized (TraeAudioManager.this._bluetooth_sco_connect) {
                    boolean[] zArr = TraeAudioManager.this._bluetooth_sco_connect;
                    if (!zArr[0]) {
                        try {
                            zArr.wait(4000L);
                        } catch (InterruptedException unused2) {
                        }
                    }
                }
                if (TraeAudioManager.this.bluetoothState == 7) {
                    QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread bluetoothState ==  Bluetooth_State.SCO_CONNECTED 2");
                    updateStatus();
                    break;
                }
                int i3 = i2 + 1;
                if (i2 > 3) {
                    break;
                }
                if (z2) {
                    _stopBluetoothSco();
                    try {
                        Thread.sleep(4000L);
                    } catch (InterruptedException unused3) {
                    }
                    TraeAudioManager.this.bluetoothState = 6;
                    synchronized (TraeAudioManager.this._bluetooth_sco_connect) {
                        TraeAudioManager.this._bluetooth_sco_connect[0] = false;
                    }
                    _startBluetoothSco();
                    QLog.w(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread retry start sco");
                }
                i2 = i3;
            }
            if (TraeAudioManager.this.bluetoothState != 7) {
                QLog.e(TraeAudioManager.TAG, 2, "bluetoothHeadsetSwitchThread sco fail,remove btheadset");
                TraeAudioManager.this._deviceConfigManager.setVisible(getDeviceName(), false);
                TraeAudioManager.this._deviceConfigManager.resetNullConnecting();
                processDeviceConnectRes(10);
                TraeAudioManager.this.checkAutoDeviceListUpdate(false);
            }
        }

        @TargetApi(8)
        public void _startBluetoothSco() {
            TraeAudioManager.this._am.setBluetoothScoOn(true);
            if (TXCBuild.VersionInt() > 8) {
                TraeAudioManager.this._am.startBluetoothSco();
            }
        }

        @TargetApi(8)
        public void _stopBluetoothSco() {
            try {
                TraeAudioManager.this._am.stopBluetoothSco();
                TraeAudioManager.this._am.setBluetoothScoOn(false);
            } catch (Exception unused) {
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public String getDeviceName() {
            return TraeAudioManager.DEVICE_BLUETOOTHHEADSET;
        }
    }

    public class earphoneSwitchThread extends switchThread {
        public earphoneSwitchThread() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _quit() {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _run() throws InterruptedException {
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag && TraeAudioManager.enableDeviceSwitchFlag) {
                TraeAudioManager traeAudioManager = TraeAudioManager.this;
                traeAudioManager.InternalSetSpeaker(traeAudioManager._context, false);
            }
            updateStatus();
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "connect earphone: disableDeviceSwitchFlag");
                    return;
                }
                return;
            }
            int i2 = 0;
            while (this._running) {
                if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag) {
                    try {
                        if (TraeAudioManager.this._am.isSpeakerphoneOn()) {
                            TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                            traeAudioManager2.InternalSetSpeaker(traeAudioManager2._context, false);
                        }
                    } catch (Exception unused) {
                    }
                } else if (QLog.isColorLevel()) {
                    QLog.d(TraeAudioManager.TAG, 2, "connect earphone: do nothing");
                }
                try {
                    Thread.sleep(i2 < 5 ? 1000L : 4000L);
                } catch (InterruptedException unused2) {
                }
                i2++;
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public String getDeviceName() {
            return TraeAudioManager.DEVICE_EARPHONE;
        }
    }

    public class headsetSwitchThread extends switchThread {
        public headsetSwitchThread() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _quit() {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _run() throws InterruptedException {
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag && TraeAudioManager.enableDeviceSwitchFlag) {
                TraeAudioManager traeAudioManager = TraeAudioManager.this;
                traeAudioManager.InternalSetSpeaker(traeAudioManager._context, false);
                TraeAudioManager.this._am.setWiredHeadsetOn(true);
            }
            updateStatus();
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "connect headset: disableDeviceSwitchFlag");
                    return;
                }
                return;
            }
            int i2 = 0;
            while (this._running) {
                if (TraeAudioManager.IsMusicScene || !TraeAudioManager.IsUpdateSceneFlag) {
                    if (QLog.isColorLevel()) {
                        QLog.d(TraeAudioManager.TAG, 2, "connect headset: do nothing");
                    }
                } else if (TraeAudioManager.this._am.isSpeakerphoneOn()) {
                    TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                    traeAudioManager2.InternalSetSpeaker(traeAudioManager2._context, false);
                }
                try {
                    Thread.sleep(i2 < 5 ? 1000L : 4000L);
                } catch (InterruptedException unused) {
                }
                i2++;
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public String getDeviceName() {
            return TraeAudioManager.DEVICE_WIREDHEADSET;
        }
    }

    public class speakerSwitchThread extends switchThread {
        public speakerSwitchThread() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _quit() {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public void _run() throws InterruptedException {
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag && TraeAudioManager.enableDeviceSwitchFlag) {
                TraeAudioManager traeAudioManager = TraeAudioManager.this;
                traeAudioManager.InternalSetSpeaker(traeAudioManager._context, true);
            }
            updateStatus();
            if (!TraeAudioManager.enableDeviceSwitchFlag) {
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "connect speakerPhone: disableDeviceSwitchFlag");
                    return;
                }
                return;
            }
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, " _run:" + getDeviceName() + " _running:" + this._running);
            }
            int i2 = 0;
            while (this._running) {
                if (TraeAudioManager.IsMusicScene || !TraeAudioManager.IsUpdateSceneFlag) {
                    if (QLog.isColorLevel()) {
                        QLog.d(TraeAudioManager.TAG, 2, "connect speakerPhone: do nothing");
                    }
                } else if (!TraeAudioManager.this._am.isSpeakerphoneOn()) {
                    TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                    traeAudioManager2.InternalSetSpeaker(traeAudioManager2._context, true);
                }
                try {
                    Thread.sleep(i2 < 5 ? 1000L : 4000L);
                } catch (InterruptedException unused) {
                }
                i2++;
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.switchThread
        public String getDeviceName() {
            return TraeAudioManager.DEVICE_SPEAKERPHONE;
        }
    }

    public abstract class switchThread extends Thread {
        boolean _running = true;
        boolean[] _exited = {false};
        HashMap<String, Object> _params = null;
        long _usingtime = 0;

        public switchThread() {
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, "construct switchThread:" + getDeviceName());
            }
        }

        public abstract void _quit();

        public abstract void _run();

        public abstract String getDeviceName();

        public void processDeviceConnectRes(int i2) {
            TraeAudioManager.this.InternalNotifyDeviceChangableUpdate();
            AudioDeviceInterface.LogTraceEntry(getDeviceName() + " err:" + i2);
            if (this._params == null) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                return;
            }
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            traeAudioManager.sessionConnectedDev = traeAudioManager._deviceConfigManager.getConnectedDevice();
            Long l2 = (Long) this._params.get(TraeAudioManager.PARAM_SESSIONID);
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, " sessonID:" + l2);
            }
            if (l2 == null || l2.longValue() == Long.MIN_VALUE) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                if (QLog.isColorLevel()) {
                    QLog.w(TraeAudioManager.TAG, 2, "processDeviceConnectRes sid null,don't send res");
                    return;
                }
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.CONNECTDEVICE_RESULT_DEVICENAME, (String) this._params.get(TraeAudioManager.PARAM_DEVICE));
            if (TraeAudioManager.this.sendResBroadcast(intent, this._params, i2) == 0) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
            }
            AudioDeviceInterface.LogTraceExit();
        }

        public void quit() {
            AudioDeviceInterface.LogTraceEntry(getDeviceName());
            this._running = false;
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, " quit:" + getDeviceName() + " _running:" + this._running);
            }
            interrupt();
            _quit();
            synchronized (this._exited) {
                boolean[] zArr = this._exited;
                if (!zArr[0]) {
                    try {
                        zArr.wait(a.f7153q);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            AudioDeviceInterface.LogTraceEntry(getDeviceName());
            TraeAudioManager.this._deviceConfigManager.setConnecting(getDeviceName());
            TraeAudioManager.this.InternalNotifyDeviceChangableUpdate();
            _run();
            synchronized (this._exited) {
                boolean[] zArr = this._exited;
                zArr[0] = true;
                zArr.notifyAll();
            }
            AudioDeviceInterface.LogTraceExit();
        }

        public void setDeviceConnectParam(HashMap<String, Object> map) {
            this._params = map;
        }

        public void updateStatus() {
            TraeAudioManager.this._deviceConfigManager.setConnected(getDeviceName());
            processDeviceConnectRes(0);
        }
    }

    public TraeAudioManager(Context context) {
        this._context = null;
        this.mTraeAudioManagerLooper = null;
        AudioDeviceInterface.LogTraceEntry(" context:" + context);
        if (context == null) {
            return;
        }
        this._context = context;
        this.mTraeAudioManagerLooper = new TraeAudioManagerLooper(this);
        AudioDeviceInterface.LogTraceExit();
    }

    public static boolean IsEabiLowVersion() {
        String str;
        String str2 = Build.CPU_ABI;
        if (TXCBuild.VersionInt() >= 8) {
            try {
                str = (String) Build.class.getDeclaredField("CPU_ABI2").get(null);
            } catch (Exception unused) {
                return IsEabiLowVersionByAbi(str2);
            }
        } else {
            str = "unknown";
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "IsEabiVersion CPU_ABI:" + str2 + " CPU_ABI2:" + str);
        }
        return IsEabiLowVersionByAbi(str2) && IsEabiLowVersionByAbi(str);
    }

    public static boolean IsEabiLowVersionByAbi(String str) {
        if (str == null) {
            return true;
        }
        if (str.contains("x86") || str.contains("mips")) {
            return false;
        }
        if (str.equalsIgnoreCase("armeabi")) {
            return true;
        }
        return (str.equalsIgnoreCase("armeabi-v7a") || str.equalsIgnoreCase("arm64-v8a")) ? false : true;
    }

    public static int SetSpeakerForTest(Context context, boolean z2) {
        int iInternalSetSpeaker;
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        if (traeAudioManager != null) {
            iInternalSetSpeaker = traeAudioManager.InternalSetSpeaker(context, z2);
        } else {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "TraeAudioManager|static SetSpeakerForTest|null == _ginstance");
            }
            iInternalSetSpeaker = -1;
        }
        _glock.unlock();
        return iInternalSetSpeaker;
    }

    public static boolean checkDevName(String str) {
        if (str == null) {
            return false;
        }
        return DEVICE_SPEAKERPHONE.equals(str) || DEVICE_EARPHONE.equals(str) || DEVICE_WIREDHEADSET.equals(str) || DEVICE_BLUETOOTHHEADSET.equals(str);
    }

    public static int connectDevice(String str, long j2, boolean z2, String str2) {
        if (str2 == null) {
            return -1;
        }
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        map.put(CONNECTDEVICE_DEVICENAME, str2);
        map.put(PARAM_DEVICE, str2);
        return sendMessage(32775, map);
    }

    public static int connectHighestPriorityDevice(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(TraeAudioManagerLooper.MESSAGE_CONNECT_HIGHEST_PRIORITY_DEVICE, map);
    }

    public static int disableDeviceSwitch() {
        enableDeviceSwitchFlag = false;
        return 0;
    }

    public static int earAction(String str, long j2, boolean z2, int i2) {
        if (i2 != 0 && i2 != 1) {
            return -1;
        }
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        map.put(EXTRA_EARACTION, Integer.valueOf(i2));
        return sendMessage(32776, map);
    }

    public static void forceVolumeControlStream(AudioManager audioManager, int i2) {
        if (TXCBuild.Manufacturer().equals("Google")) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "forceVolumeControlStream, Google phone nothing to do");
                return;
            }
            return;
        }
        Object objInvokeMethod = invokeMethod(audioManager, "forceVolumeControlStream", new Object[]{Integer.valueOf(i2)}, new Class[]{Integer.TYPE});
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "forceVolumeControlStream  streamType:" + i2 + " res:" + objInvokeMethod);
        }
    }

    public static int getAudioSource(int i2) {
        if (IsMusicScene) {
            return 0;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "[Config] armeabi low Version, getAudioSource _audioSourcePolicy:" + i2 + " source:0");
            }
            return 0;
        }
        int iVersionInt = TXCBuild.VersionInt();
        if (i2 >= 0) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "[Config] getAudioSource _audioSourcePolicy:" + i2 + " source:" + i2);
            }
            return i2;
        }
        int i3 = iVersionInt >= 11 ? 7 : 0;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "[Config] getAudioSource _audioSourcePolicy:" + i2 + " source:" + i3);
        }
        return i3;
    }

    public static int getAudioStreamType(int i2) {
        int i3 = 3;
        if (IsMusicScene) {
            return 3;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "[Config] armeabi low Version, getAudioStreamType audioStreamTypePolicy:" + i2 + " streamType:3");
            }
            return 3;
        }
        int iVersionInt = TXCBuild.VersionInt();
        if (i2 >= 0) {
            i3 = i2;
        } else if (iVersionInt >= 9) {
            i3 = 0;
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "[Config] getAudioStreamType audioStreamTypePolicy:" + i2 + " streamType:" + i3);
        }
        return i3;
    }

    public static int getCallAudioMode(int i2) {
        if (IsMusicScene) {
            return 0;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "[Config] armeabi low Version, getCallAudioMode modePolicy:" + i2 + " mode:0");
            }
            return 0;
        }
        int iVersionInt = TXCBuild.VersionInt();
        if (i2 >= 0) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "[Config] getCallAudioMode modePolicy:" + i2 + " mode:" + i2);
            }
            return i2;
        }
        int i3 = iVersionInt >= 11 ? 3 : 0;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "[Config] getCallAudioMode _modePolicy:" + i2 + " mode:" + i3 + "facturer:" + TXCBuild.Manufacturer() + " model:" + TXCBuild.Model());
        }
        return i3;
    }

    public static int getConnectedDevice(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(TraeAudioManagerLooper.MESSAGE_GETCONNECTEDDEVICE, map);
    }

    public static int getConnectingDevice(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32779, map);
    }

    public static int getDeviceList(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32774, map);
    }

    public static String getForceConfigName(int i2) {
        if (i2 < 0) {
            return "unknow";
        }
        String[] strArr = forceName;
        return i2 < strArr.length ? strArr[i2] : "unknow";
    }

    public static int getForceUse(int i2) {
        Object objInvokeStaticMethod = invokeStaticMethod("android.media.AudioSystem", "getForceUse", new Object[]{Integer.valueOf(i2)}, new Class[]{Integer.TYPE});
        Integer num = objInvokeStaticMethod != null ? (Integer) objInvokeStaticMethod : 0;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "getForceUse  usage:" + i2 + " config:" + num + " ->" + getForceConfigName(num.intValue()));
        }
        return num.intValue();
    }

    public static int getStreamType(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32784, map);
    }

    public static int init(Context context) {
        QLog.w(TAG, 2, "TraeAudioManager init _ginstance:" + _ginstance);
        AudioDeviceInterface.LogTraceEntry(" _ginstance:" + _ginstance);
        _glock.lock();
        if (_ginstance == null) {
            _ginstance = new TraeAudioManager(context);
        }
        _glock.unlock();
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class[] clsArr) {
        try {
            return obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "invokeMethod Exception:" + e2.getMessage());
            }
            return null;
        }
    }

    public static Object invokeStaticMethod(String str, String str2, Object[] objArr, Class[] clsArr) {
        try {
            return Class.forName(str).getMethod(str2, clsArr).invoke(null, objArr);
        } catch (ClassNotFoundException unused) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.w(TAG, 2, "ClassNotFound:" + str);
            return null;
        } catch (IllegalAccessException unused2) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.w(TAG, 2, "IllegalAccess:" + str2);
            return null;
        } catch (IllegalArgumentException unused3) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.w(TAG, 2, "IllegalArgument:" + str2);
            return null;
        } catch (NoSuchMethodException unused4) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.w(TAG, 2, "NoSuchMethod:" + str2);
            return null;
        } catch (InvocationTargetException unused5) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.w(TAG, 2, "InvocationTarget:" + str2);
            return null;
        } catch (Exception e2) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.w(TAG, 2, "invokeStaticMethod Exception:" + e2.getMessage());
            return null;
        }
    }

    public static boolean isCloseSystemAPM(int i2) {
        if (i2 != -1) {
            return false;
        }
        if (TXCBuild.Manufacturer().equals("Xiaomi")) {
            if (TXCBuild.Model().equals("MI 2") || TXCBuild.Model().equals("MI 2A") || TXCBuild.Model().equals("MI 2S") || TXCBuild.Model().equals("MI 2SC")) {
                return true;
            }
        } else if (TXCBuild.Manufacturer().equals("samsung") && TXCBuild.Model().equals("SCH-I959")) {
            return true;
        }
        return false;
    }

    public static int isDeviceChangabled(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32777, map);
    }

    public static boolean isHandfree(String str) {
        return checkDevName(str) && DEVICE_SPEAKERPHONE.equals(str);
    }

    public static int recoverAudioFocus(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32791, map);
    }

    public static int registerAudioSession(TraeAudioSession traeAudioSession, boolean z2, long j2, Context context) {
        int i2;
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        if (traeAudioManager != null) {
            if (z2) {
                TraeAudioSessionHost traeAudioSessionHost = traeAudioManager._audioSessionHost;
                if (traeAudioSessionHost != null) {
                    traeAudioSessionHost.add(traeAudioSession, j2, context);
                    if (QLog.isColorLevel()) {
                        QLog.d(TAG, 2, "[register] add AudioSession: " + j2);
                    }
                } else if (QLog.isColorLevel()) {
                    QLog.d(TAG, 2, "_ginstance._audioSessionHost is null");
                }
            } else {
                TraeAudioSessionHost traeAudioSessionHost2 = traeAudioManager._audioSessionHost;
                if (traeAudioSessionHost2 != null) {
                    traeAudioSessionHost2.remove(j2);
                    if (QLog.isColorLevel()) {
                        QLog.d(TAG, 2, "[register] remove AudioSession: " + j2);
                    }
                } else if (QLog.isColorLevel()) {
                    QLog.d(TAG, 2, "_ginstance._audioSessionHost is null");
                }
            }
            i2 = 0;
        } else {
            i2 = -1;
        }
        _glock.unlock();
        return i2;
    }

    public static int requestReleaseAudioFocus(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32790, map);
    }

    public static int sendMessage(int i2, HashMap<String, Object> map) {
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        int iInternalSendMessage = traeAudioManager != null ? traeAudioManager.internalSendMessage(i2, map) : -1;
        _glock.unlock();
        return iInternalSendMessage;
    }

    public static void setForceUse(int i2, int i3) {
        Object[] objArr = {Integer.valueOf(i2), Integer.valueOf(i3)};
        Class cls = Integer.TYPE;
        Object objInvokeStaticMethod = invokeStaticMethod("android.media.AudioSystem", "setForceUse", objArr, new Class[]{cls, cls});
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "setForceUse  usage:" + i2 + " config:" + i3 + " ->" + getForceConfigName(i3) + " res:" + objInvokeStaticMethod);
        }
    }

    public static void setParameters(String str) {
        Object[] objArr = {str};
        Class[] clsArr = {String.class};
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "setParameters  :" + str);
        }
        invokeStaticMethod("android.media.AudioSystem", "setParameters", objArr, clsArr);
    }

    public static void setPhoneState(int i2) {
        invokeStaticMethod("android.media.AudioSystem", "setPhoneState", new Object[]{Integer.valueOf(i2)}, new Class[]{Integer.TYPE});
    }

    public static int startRing(String str, long j2, boolean z2, int i2, int i3, Uri uri, String str2, boolean z3, int i4, String str3, boolean z4) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        map.put(PARAM_RING_DATASOURCE, Integer.valueOf(i2));
        map.put(PARAM_RING_RSID, Integer.valueOf(i3));
        map.put(PARAM_RING_URI, uri);
        map.put(PARAM_RING_FILEPATH, str2);
        map.put(PARAM_RING_LOOP, Boolean.valueOf(z3));
        map.put(PARAM_RING_LOOPCOUNT, Integer.valueOf(i4));
        map.put(PARAM_RING_MODE, Boolean.valueOf(z4));
        map.put(PARAM_RING_USERDATA_STRING, str3);
        return sendMessage(32782, map);
    }

    public static int startService(String str, long j2, boolean z2, String str2) {
        if (str2.length() <= 0) {
            return -1;
        }
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        map.put(EXTRA_DATA_DEVICECONFIG, str2);
        return sendMessage(32772, map);
    }

    public static int stopRing(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32783, map);
    }

    public static int stopService(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32773, map);
    }

    public static void uninit() {
        QLog.w(TAG, 2, "TraeAudioManager uninit _ginstance:" + _ginstance);
        AudioDeviceInterface.LogTraceEntry(" _ginstance:" + _ginstance);
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        if (traeAudioManager != null) {
            traeAudioManager.release();
            _ginstance = null;
        }
        _glock.unlock();
        AudioDeviceInterface.LogTraceExit();
    }

    public static int voiceCallAudioParamChanged(String str, long j2, boolean z2, int i2, int i3) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        map.put(PARAM_MODEPOLICY, Integer.valueOf(i2));
        map.put(PARAM_STREAMTYPE, Integer.valueOf(i3));
        return sendMessage(32788, map);
    }

    public static int voicecallPostprocess(String str, long j2, boolean z2) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        return sendMessage(32781, map);
    }

    public static int voicecallPreprocess(String str, long j2, boolean z2, int i2, int i3) {
        HashMap map = new HashMap();
        map.put(PARAM_SESSIONID, Long.valueOf(j2));
        map.put(PARAM_OPERATION, str);
        map.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z2));
        map.put(PARAM_MODEPOLICY, Integer.valueOf(i2));
        map.put(PARAM_STREAMTYPE, Integer.valueOf(i3));
        return sendMessage(TraeAudioManagerLooper.MESSAGE_VOICECALLPREPROCESS, map);
    }

    public BluetoohHeadsetCheckInterface CreateBluetoothCheck(Context context, DeviceConfigManager deviceConfigManager) {
        BluetoohHeadsetCheckInterface bluetoohHeadsetCheck = TXCBuild.VersionInt() >= 11 ? new BluetoohHeadsetCheck() : TXCBuild.VersionInt() != 18 ? new BluetoohHeadsetCheckFor2x() : new BluetoohHeadsetCheckFake();
        if (!bluetoohHeadsetCheck.init(context, deviceConfigManager)) {
            bluetoohHeadsetCheck = new BluetoohHeadsetCheckFake();
        }
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("CreateBluetoothCheck:");
            sb.append(bluetoohHeadsetCheck.interfaceDesc());
            sb.append(" skip android4.3:");
            sb.append(TXCBuild.VersionInt() == 18 ? "Y" : "N");
            QLog.w(TAG, 2, sb.toString());
        }
        return bluetoohHeadsetCheck;
    }

    public int InternalConnectDevice(String str, HashMap<String, Object> map, boolean z2) {
        AudioDeviceInterface.LogTraceEntry(" devName:" + str);
        if (str == null) {
            return -1;
        }
        if (!z2 && !DEVICE_BLUETOOTHHEADSET.equals(str) && !this._deviceConfigManager.getConnectedDevice().equals(DEVICE_NONE) && str.equals(this._deviceConfigManager.getConnectedDevice())) {
            return 0;
        }
        if (!checkDevName(str) || !this._deviceConfigManager.getVisible(str)) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, " checkDevName fail");
            }
            return -1;
        }
        if (!InternalIsDeviceChangeable()) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, " InternalIsDeviceChangeable fail");
            }
            return -1;
        }
        if (this.IsServiceReadytoStop) {
            QLog.e(TAG, 2, " InternalConnectDevice fail,ready to stopService");
            return -1;
        }
        this._gSwitchTreadlock.lock();
        if (!this.IsServiceReadytoStop) {
            this._deviceConfigManager.setConnecting(str);
            if (this._switchThread != null) {
                QLog.w(TAG, 2, "_switchThread:" + this._switchThread.getDeviceName());
                this._switchThread.quit();
                this._switchThread = null;
            }
            if (str.equals(DEVICE_EARPHONE)) {
                this._switchThread = new earphoneSwitchThread();
            } else if (str.equals(DEVICE_SPEAKERPHONE)) {
                this._switchThread = new speakerSwitchThread();
            } else if (str.equals(DEVICE_WIREDHEADSET)) {
                this._switchThread = new headsetSwitchThread();
            } else if (str.equals(DEVICE_BLUETOOTHHEADSET)) {
                this._switchThread = new bluetoothHeadsetSwitchThread();
            }
            switchThread switchthread = this._switchThread;
            if (switchthread != null) {
                switchthread.setDeviceConnectParam(map);
                this._switchThread.start();
            }
        }
        this._gSwitchTreadlock.unlock();
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }

    public boolean InternalIsDeviceChangeable() {
        String connectingDevice = this._deviceConfigManager.getConnectingDevice();
        return connectingDevice == null || connectingDevice.equals(DEVICE_NONE) || connectingDevice.equals("");
    }

    public int InternalNotifyDeviceChangableUpdate() {
        if (this._context == null) {
            return -1;
        }
        final boolean zInternalIsDeviceChangeable = InternalIsDeviceChangeable();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.3
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE);
                intent.putExtra(TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE_DATE, zInternalIsDeviceChangeable);
                Context context = TraeAudioManager.this._context;
                if (context != null) {
                    b.a(context).a(intent);
                }
            }
        });
        return 0;
    }

    public int InternalNotifyDeviceListUpdate() {
        AudioDeviceInterface.LogTraceEntry("");
        if (this._context == null) {
            return -1;
        }
        HashMap<String, Object> snapParams = this._deviceConfigManager.getSnapParams();
        final ArrayList arrayList = (ArrayList) snapParams.get(EXTRA_DATA_AVAILABLEDEVICE_LIST);
        final String str = (String) snapParams.get(EXTRA_DATA_CONNECTEDDEVICE);
        final String str2 = (String) snapParams.get(EXTRA_DATA_PREV_CONNECTEDDEVICE);
        final String bluetoothName = this._deviceConfigManager.getBluetoothName();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.2
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_DEVICELIST_UPDATE);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, (String[]) arrayList.toArray(new String[0]));
                intent.putExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, str);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, str2);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME, bluetoothName);
                Context context = TraeAudioManager.this._context;
                if (context != null) {
                    b.a(context).a(intent);
                }
            }
        });
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }

    public int InternalSessionConnectDevice(HashMap<String, Object> map) {
        AudioDeviceInterface.LogTraceEntry("");
        if (map == null || this._context == null) {
            return -1;
        }
        if (IsMusicScene) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "MusicScene: InternalSessionConnectDevice failed");
            }
            return -1;
        }
        String str = (String) map.get(PARAM_DEVICE);
        Log.w(TAG, "ConnectDevice: " + str);
        if (!IsEarPhoneSupported && str.equals(DEVICE_EARPHONE)) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "InternalSessionConnectDevice IsEarPhoneSupported = false, Connect device:" + str + " failed");
            }
            return -1;
        }
        boolean zInternalIsDeviceChangeable = InternalIsDeviceChangeable();
        int i2 = !checkDevName(str) ? 7 : !this._deviceConfigManager.getVisible(str) ? 8 : !zInternalIsDeviceChangeable ? 9 : 0;
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("sessonID:");
            sb.append((Long) map.get(PARAM_SESSIONID));
            sb.append(" devName:");
            sb.append(str);
            sb.append(" bChangabled:");
            sb.append(zInternalIsDeviceChangeable ? "Y" : "N");
            sb.append(" err:");
            sb.append(i2);
            QLog.w(TAG, 2, sb.toString());
        }
        if (i2 != 0) {
            Intent intent = new Intent();
            intent.putExtra(CONNECTDEVICE_RESULT_DEVICENAME, (String) map.get(PARAM_DEVICE));
            sendResBroadcast(intent, map, i2);
            return -1;
        }
        if (!str.equals(this._deviceConfigManager.getConnectedDevice())) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, " --connecting...");
            }
            InternalConnectDevice(str, map, false);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.e(TAG, 2, " --has connected!");
        }
        Intent intent2 = new Intent();
        intent2.putExtra(CONNECTDEVICE_RESULT_DEVICENAME, (String) map.get(PARAM_DEVICE));
        sendResBroadcast(intent2, map, i2);
        return 0;
    }

    public int InternalSessionEarAction(HashMap<String, Object> map) {
        return 0;
    }

    public int InternalSessionGetConnectedDevice(HashMap<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(GETCONNECTEDDEVICE_RESULT_LIST, this._deviceConfigManager.getConnectedDevice());
        sendResBroadcast(intent, map, 0);
        return 0;
    }

    public int InternalSessionGetConnectingDevice(HashMap<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(GETCONNECTINGDEVICE_RESULT_LIST, this._deviceConfigManager.getConnectingDevice());
        sendResBroadcast(intent, map, 0);
        return 0;
    }

    public int InternalSessionIsDeviceChangabled(HashMap<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(ISDEVICECHANGABLED_RESULT_ISCHANGABLED, InternalIsDeviceChangeable());
        sendResBroadcast(intent, map, 0);
        return 0;
    }

    public void InternalSetMode(int i2) {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "SetMode entry:" + i2);
        }
        AudioManager audioManager = this._am;
        if (audioManager == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "setMode:" + i2 + " fail am=null");
                return;
            }
            return;
        }
        audioManager.setMode(i2);
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("setMode:");
            sb.append(i2);
            sb.append(this._am.getMode() != i2 ? "fail" : "success");
            QLog.w(TAG, 2, sb.toString());
        }
    }

    public int InternalSetSpeaker(Context context, boolean z2) {
        if (context == null) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "Could not InternalSetSpeaker - no context");
            }
            return -1;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager == null) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "Could not InternalSetSpeaker - no audio manager");
            }
            return -1;
        }
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("InternalSetSpeaker entry:speaker:");
            sb.append(audioManager.isSpeakerphoneOn() ? "Y" : "N");
            sb.append("-->:");
            sb.append(z2 ? "Y" : "N");
            QLog.w(TAG, 2, sb.toString());
        }
        if (isCloseSystemAPM(this._modePolicy) && this._activeMode != 2) {
            return InternalSetSpeakerSpe(audioManager, z2);
        }
        try {
            if (audioManager.isSpeakerphoneOn() != z2) {
                audioManager.setSpeakerphoneOn(z2);
            }
        } catch (Exception e2) {
            QLog.e(TAG, 2, "setSpeakerphoneOn failed with " + e2.getMessage());
        }
        int i2 = audioManager.isSpeakerphoneOn() == z2 ? 0 : -1;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InternalSetSpeaker exit:" + z2 + " res:" + i2 + " mode:" + audioManager.getMode());
        }
        return i2;
    }

    public int InternalSetSpeakerSpe(AudioManager audioManager, boolean z2) {
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InternalSetSpeakerSpe fac:" + TXCBuild.Manufacturer() + " model:" + TXCBuild.Model() + " st:" + this._streamType + " media_force_use:" + getForceUse(1));
        }
        if (z2) {
            InternalSetMode(0);
            audioManager.setSpeakerphoneOn(true);
            setForceUse(1, 1);
        } else {
            InternalSetMode(3);
            audioManager.setSpeakerphoneOn(false);
            setForceUse(1, 0);
        }
        int i2 = audioManager.isSpeakerphoneOn() != z2 ? -1 : 0;
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "InternalSetSpeakerSpe exit:" + z2 + " res:" + i2 + " mode:" + audioManager.getMode());
        }
        return i2;
    }

    public void _updateEarphoneVisable() {
        DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
        if (deviceConfigManager == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "_deviceConfigManager is null");
            }
        } else if (deviceConfigManager.getVisible(DEVICE_WIREDHEADSET) || this._deviceConfigManager.getVisible(DEVICE_BLUETOOTHHEADSET)) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, " detected headset plugin,so disable earphone");
            }
            this._deviceConfigManager.setVisible(DEVICE_EARPHONE, false);
        } else {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, " detected headset plugout,so enable earphone");
            }
            this._deviceConfigManager.setVisible(DEVICE_EARPHONE, true);
        }
    }

    public void checkAutoDeviceListUpdate(boolean z2) {
        DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
        if (deviceConfigManager == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "_deviceConfigManager is null");
            }
        } else if (z2 || deviceConfigManager.getVisiableUpdateFlag()) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "checkAutoDeviceListUpdate got update!");
            }
            _updateEarphoneVisable();
            this._deviceConfigManager.resetVisiableUpdateFlag();
            internalSendMessage(32785, new HashMap<>());
        }
    }

    public void checkDevicePlug(String str, boolean z2) {
        DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
        if (deviceConfigManager == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "_deviceConfigManager is null");
                return;
            }
            return;
        }
        if (deviceConfigManager.getVisiableUpdateFlag()) {
            if (QLog.isColorLevel()) {
                StringBuilder sb = new StringBuilder();
                sb.append("checkDevicePlug got update dev:");
                sb.append(str);
                sb.append(z2 ? " piugin" : " plugout");
                sb.append(" connectedDev:");
                sb.append(this._deviceConfigManager.getConnectedDevice());
                QLog.w(TAG, 2, sb.toString());
            }
            _updateEarphoneVisable();
            this._deviceConfigManager.resetVisiableUpdateFlag();
            if (z2) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(PARAM_DEVICE, str);
                internalSendMessage(32786, map);
                return;
            }
            String connectedDevice = this._deviceConfigManager.getConnectedDevice();
            if (connectedDevice.equals(str) || connectedDevice.equals(DEVICE_NONE)) {
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put(PARAM_DEVICE, str);
                internalSendMessage(32787, map2);
                return;
            }
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, " ---No switch,plugout:" + str + " connectedDev:" + connectedDevice);
            }
            internalSendMessage(32793, new HashMap<>());
        }
    }

    public int internalSendMessage(int i2, HashMap<String, Object> map) {
        TraeAudioManagerLooper traeAudioManagerLooper = this.mTraeAudioManagerLooper;
        if (traeAudioManagerLooper != null) {
            return traeAudioManagerLooper.sendMessage(i2, map);
        }
        return -1;
    }

    public void onHeadsetPlug(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("name");
        if (stringExtra == null) {
            stringExtra = "unkonw";
        }
        String string = " [" + stringExtra + "] ";
        int intExtra = intent.getIntExtra("state", -1);
        if (intExtra != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(intExtra == 0 ? "unplugged" : "plugged");
            string = sb.toString();
        }
        String string2 = string + " mic:";
        int intExtra2 = intent.getIntExtra(PLVPPTAuthentic.PermissionType.MICROPHONE, -1);
        if (intExtra2 != -1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(string2);
            sb2.append(intExtra2 == 1 ? "Y" : "unkown");
            string2 = sb2.toString();
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "onHeadsetPlug:: " + string2);
        }
        DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
        if (deviceConfigManager != null) {
            deviceConfigManager.setVisible(DEVICE_WIREDHEADSET, 1 == intExtra);
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "onHeadsetPlug exit");
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            if (QLog.isColorLevel()) {
                QLog.d(TAG, 2, "onReceive intent or context is null!");
                return;
            }
            return;
        }
        try {
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra(PARAM_OPERATION);
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "TraeAudioManager|onReceive::Action:" + intent.getAction());
            }
            DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
            if (deviceConfigManager == null) {
                if (QLog.isColorLevel()) {
                    QLog.d(TAG, 2, "_deviceConfigManager null!");
                    return;
                }
                return;
            }
            boolean visible = deviceConfigManager.getVisible(DEVICE_WIREDHEADSET);
            boolean visible2 = this._deviceConfigManager.getVisible(DEVICE_BLUETOOTHHEADSET);
            if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                onHeadsetPlug(context, intent);
                if (!visible && this._deviceConfigManager.getVisible(DEVICE_WIREDHEADSET)) {
                    checkDevicePlug(DEVICE_WIREDHEADSET, true);
                }
                if (!visible || this._deviceConfigManager.getVisible(DEVICE_WIREDHEADSET)) {
                    return;
                }
                checkDevicePlug(DEVICE_WIREDHEADSET, false);
                return;
            }
            if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                return;
            }
            if (!ACTION_TRAEAUDIOMANAGER_REQUEST.equals(action)) {
                DeviceConfigManager deviceConfigManager2 = this._deviceConfigManager;
                if (deviceConfigManager2 != null) {
                    BluetoohHeadsetCheckInterface bluetoohHeadsetCheckInterface = this._bluetoothCheck;
                    if (bluetoohHeadsetCheckInterface != null) {
                        bluetoohHeadsetCheckInterface.onReceive(context, intent, deviceConfigManager2);
                    }
                    if (!visible2 && this._deviceConfigManager.getVisible(DEVICE_BLUETOOTHHEADSET)) {
                        checkDevicePlug(DEVICE_BLUETOOTHHEADSET, true);
                    }
                    if (!visible2 || this._deviceConfigManager.getVisible(DEVICE_BLUETOOTHHEADSET)) {
                        return;
                    }
                    checkDevicePlug(DEVICE_BLUETOOTHHEADSET, false);
                    return;
                }
                return;
            }
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "   OPERATION:" + stringExtra);
            }
            if (OPERATION_STARTSERVICE.equals(stringExtra)) {
                startService(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getStringExtra(EXTRA_DATA_DEVICECONFIG));
                return;
            }
            if (OPERATION_STOPSERVICE.equals(stringExtra)) {
                stopService(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_GETDEVICELIST.equals(stringExtra)) {
                getDeviceList(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_GETSTREAMTYPE.equals(stringExtra)) {
                getStreamType(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_CONNECTDEVICE.equals(stringExtra)) {
                connectDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getStringExtra(CONNECTDEVICE_DEVICENAME));
                return;
            }
            if (OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE.equals(stringExtra)) {
                connectHighestPriorityDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_EARACTION.equals(stringExtra)) {
                earAction(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(EXTRA_EARACTION, -1));
                return;
            }
            if (OPERATION_ISDEVICECHANGABLED.equals(stringExtra)) {
                isDeviceChangabled(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_GETCONNECTEDDEVICE.equals(stringExtra)) {
                getConnectedDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_GETCONNECTINGDEVICE.equals(stringExtra)) {
                getConnectingDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_VOICECALL_PREPROCESS.equals(stringExtra)) {
                voicecallPreprocess(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(PARAM_MODEPOLICY, -1), intent.getIntExtra(PARAM_STREAMTYPE, -1));
                return;
            }
            if (OPERATION_VOICECALL_POSTPROCESS.equals(stringExtra)) {
                voicecallPostprocess(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                return;
            }
            if (OPERATION_VOICECALL_AUDIOPARAM_CHANGED.equals(stringExtra)) {
                voiceCallAudioParamChanged(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(PARAM_MODEPOLICY, -1), intent.getIntExtra(PARAM_STREAMTYPE, -1));
            } else {
                if (!OPERATION_STARTRING.equals(stringExtra)) {
                    if (OPERATION_STOPRING.equals(stringExtra)) {
                        stopRing(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                        return;
                    }
                    return;
                }
                startRing(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(PARAM_RING_DATASOURCE, -1), intent.getIntExtra(PARAM_RING_RSID, -1), (Uri) intent.getParcelableExtra(PARAM_RING_URI), intent.getStringExtra(PARAM_RING_FILEPATH), intent.getBooleanExtra(PARAM_RING_LOOP, false), intent.getIntExtra(PARAM_RING_LOOPCOUNT, 1), intent.getStringExtra(PARAM_RING_USERDATA_STRING), intent.getBooleanExtra(PARAM_RING_MODE, false));
            }
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "deal with receiver failed." + e2.getMessage());
            }
        }
    }

    public void printDevices() {
        AudioDeviceInterface.LogTraceEntry("");
        DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
        if (deviceConfigManager == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "_deviceConfigManager is null");
                return;
            }
            return;
        }
        int deviceNumber = deviceConfigManager.getDeviceNumber();
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "   ConnectedDevice:" + this._deviceConfigManager.getConnectedDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "   ConnectingDevice:" + this._deviceConfigManager.getConnectingDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "   prevConnectedDevice:" + this._deviceConfigManager.getPrevConnectedDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "   AHPDevice:" + this._deviceConfigManager.getAvailabledHighestPriorityDevice());
        }
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "   deviceNamber:" + deviceNumber);
        }
        for (int i2 = 0; i2 < deviceNumber; i2++) {
            String deviceName = this._deviceConfigManager.getDeviceName(i2);
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "      " + i2 + " devName:" + deviceName + " Visible:" + this._deviceConfigManager.getVisible(deviceName) + " Priority:" + this._deviceConfigManager.getPriority(deviceName));
            }
        }
        String[] strArr = (String[]) this._deviceConfigManager.getAvailableDeviceList().toArray(new String[0]);
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, "   AvailableNamber:" + strArr.length);
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            String str = strArr[i3];
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "      " + i3 + " devName:" + str + " Visible:" + this._deviceConfigManager.getVisible(str) + " Priority:" + this._deviceConfigManager.getPriority(str));
            }
        }
        AudioDeviceInterface.LogTraceExit();
    }

    public void release() {
        AudioDeviceInterface.LogTraceEntry("");
        TraeAudioManagerLooper traeAudioManagerLooper = this.mTraeAudioManagerLooper;
        if (traeAudioManagerLooper != null) {
            traeAudioManagerLooper.quit();
            this.mTraeAudioManagerLooper = null;
        }
        AudioDeviceInterface.LogTraceExit();
    }

    public int sendResBroadcast(final Intent intent, HashMap<String, Object> map, final int i2) {
        if (this._context == null) {
            return -1;
        }
        Long l2 = (Long) map.get(PARAM_SESSIONID);
        if (QLog.isColorLevel()) {
            QLog.w(TAG, 2, " sessonID:" + l2 + " " + ((String) map.get(PARAM_OPERATION)));
        }
        if (l2 == null || l2.longValue() == Long.MIN_VALUE) {
            InternalNotifyDeviceListUpdate();
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "sendResBroadcast sid null,don't send res");
            }
            return -1;
        }
        final Long l3 = (Long) map.get(PARAM_SESSIONID);
        final String str = (String) map.get(PARAM_OPERATION);
        if (!OPERATION_VOICECALL_PREPROCESS.equals(str)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.1
                @Override // java.lang.Runnable
                public void run() {
                    intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_RES);
                    intent.putExtra(TraeAudioManager.PARAM_SESSIONID, l3);
                    intent.putExtra(TraeAudioManager.PARAM_OPERATION, str);
                    intent.putExtra(TraeAudioManager.PARAM_RES_ERRCODE, i2);
                    Context context = TraeAudioManager.this._context;
                    if (context != null) {
                        b.a(context).a(intent);
                    }
                }
            });
            return 0;
        }
        intent.setAction(ACTION_TRAEAUDIOMANAGER_RES);
        intent.putExtra(PARAM_SESSIONID, l3);
        intent.putExtra(PARAM_OPERATION, str);
        intent.putExtra(PARAM_RES_ERRCODE, i2);
        TraeAudioSessionHost traeAudioSessionHost = this._audioSessionHost;
        if (traeAudioSessionHost == null) {
            return 0;
        }
        traeAudioSessionHost.sendToAudioSessionMessage(intent);
        return 0;
    }

    public void updateDeviceStatus() {
        boolean visible;
        DeviceConfigManager deviceConfigManager = this._deviceConfigManager;
        if (deviceConfigManager == null) {
            if (QLog.isColorLevel()) {
                QLog.w(TAG, 2, "_deviceConfigManager is null");
                return;
            }
            return;
        }
        int deviceNumber = deviceConfigManager.getDeviceNumber();
        for (int i2 = 0; i2 < deviceNumber; i2++) {
            String deviceName = this._deviceConfigManager.getDeviceName(i2);
            if (deviceName == null) {
                visible = false;
            } else if (deviceName.equals(DEVICE_BLUETOOTHHEADSET)) {
                BluetoohHeadsetCheckInterface bluetoohHeadsetCheckInterface = this._bluetoothCheck;
                visible = bluetoohHeadsetCheckInterface == null ? this._deviceConfigManager.setVisible(deviceName, false) : this._deviceConfigManager.setVisible(deviceName, bluetoohHeadsetCheckInterface.isConnected());
            } else if (deviceName.equals(DEVICE_WIREDHEADSET)) {
                visible = this._deviceConfigManager.setVisible(deviceName, this._am.isWiredHeadsetOn());
            } else {
                if (deviceName.equals(DEVICE_SPEAKERPHONE)) {
                    this._deviceConfigManager.setVisible(deviceName, true);
                }
                visible = false;
            }
            if (visible && QLog.isColorLevel()) {
                QLog.w(TAG, 2, "pollUpdateDevice dev:" + deviceName + " Visible:" + this._deviceConfigManager.getVisible(deviceName));
            }
        }
        checkAutoDeviceListUpdate(false);
    }

    public class DeviceConfigManager {
        HashMap<String, DeviceConfig> deviceConfigs = new HashMap<>();
        String prevConnectedDevice = TraeAudioManager.DEVICE_NONE;
        String connectedDevice = TraeAudioManager.DEVICE_NONE;
        String connectingDevice = TraeAudioManager.DEVICE_NONE;
        ReentrantLock mLock = new ReentrantLock();
        boolean visiableUpdate = false;
        String _bluetoothDevName = "unknow";

        public class DeviceConfig {
            String deviceName = TraeAudioManager.DEVICE_NONE;
            boolean visible = false;
            int priority = 0;

            public DeviceConfig() {
            }

            public String getDeviceName() {
                return this.deviceName;
            }

            public int getPriority() {
                return this.priority;
            }

            public boolean getVisible() {
                return this.visible;
            }

            public boolean init(String str, int i2) {
                if (str == null || str.length() <= 0 || !TraeAudioManager.checkDevName(str)) {
                    return false;
                }
                this.deviceName = str;
                this.priority = i2;
                return true;
            }

            public void setVisible(boolean z2) {
                this.visible = z2;
            }
        }

        public DeviceConfigManager() {
        }

        public boolean _addConfig(String str, int i2) {
            AudioDeviceInterface.LogTraceEntry(" devName:" + str + " priority:" + i2);
            DeviceConfig deviceConfig = new DeviceConfig();
            if (!deviceConfig.init(str, i2)) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, " err dev init!");
                }
                return false;
            }
            if (this.deviceConfigs.containsKey(str)) {
                if (QLog.isColorLevel()) {
                    QLog.e(TraeAudioManager.TAG, 2, "err dev exist!");
                }
                return false;
            }
            this.deviceConfigs.put(str, deviceConfig);
            this.visiableUpdate = true;
            if (QLog.isColorLevel()) {
                QLog.w(TraeAudioManager.TAG, 2, " n" + getDeviceNumber() + " 0:" + getDeviceName(0));
            }
            AudioDeviceInterface.LogTraceExit();
            return true;
        }

        public ArrayList<String> _getAvailableDeviceList() {
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator<Map.Entry<String, DeviceConfig>> it = this.deviceConfigs.entrySet().iterator();
            while (it.hasNext()) {
                DeviceConfig value = it.next().getValue();
                if (value != null && value.getVisible()) {
                    arrayList.add(value.getDeviceName());
                }
            }
            return arrayList;
        }

        public String _getConnectedDevice() {
            DeviceConfig deviceConfig = this.deviceConfigs.get(this.connectedDevice);
            return (deviceConfig == null || !deviceConfig.getVisible()) ? TraeAudioManager.DEVICE_NONE : this.connectedDevice;
        }

        public String _getPrevConnectedDevice() {
            DeviceConfig deviceConfig = this.deviceConfigs.get(this.prevConnectedDevice);
            return (deviceConfig == null || !deviceConfig.getVisible()) ? TraeAudioManager.DEVICE_NONE : this.prevConnectedDevice;
        }

        public void clearConfig() {
            this.mLock.lock();
            this.deviceConfigs.clear();
            this.prevConnectedDevice = TraeAudioManager.DEVICE_NONE;
            this.connectedDevice = TraeAudioManager.DEVICE_NONE;
            this.connectingDevice = TraeAudioManager.DEVICE_NONE;
            this.mLock.unlock();
        }

        public ArrayList<String> getAvailableDeviceList() {
            new ArrayList();
            this.mLock.lock();
            ArrayList<String> arrayList_getAvailableDeviceList = _getAvailableDeviceList();
            this.mLock.unlock();
            return arrayList_getAvailableDeviceList;
        }

        public String getAvailabledHighestPriorityDevice(String str) {
            this.mLock.lock();
            DeviceConfig deviceConfig = null;
            for (Map.Entry<String, DeviceConfig> entry : this.deviceConfigs.entrySet()) {
                entry.getKey();
                entry.getValue();
                DeviceConfig value = entry.getValue();
                if (value != null && value.getVisible() && !value.getDeviceName().equals(str) && (deviceConfig == null || value.getPriority() >= deviceConfig.getPriority())) {
                    deviceConfig = value;
                }
            }
            this.mLock.unlock();
            return deviceConfig != null ? deviceConfig.getDeviceName() : TraeAudioManager.DEVICE_SPEAKERPHONE;
        }

        public String getBluetoothName() {
            return this._bluetoothDevName;
        }

        public String getConnectedDevice() {
            this.mLock.lock();
            String str_getConnectedDevice = _getConnectedDevice();
            this.mLock.unlock();
            return str_getConnectedDevice;
        }

        public String getConnectingDevice() {
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(this.connectingDevice);
            String str = (deviceConfig == null || !deviceConfig.getVisible()) ? null : this.connectingDevice;
            this.mLock.unlock();
            return str;
        }

        public String getDeviceName(int i2) {
            DeviceConfig value;
            this.mLock.lock();
            Iterator<Map.Entry<String, DeviceConfig>> it = this.deviceConfigs.entrySet().iterator();
            int i3 = 0;
            while (true) {
                if (!it.hasNext()) {
                    value = null;
                    break;
                }
                Map.Entry<String, DeviceConfig> next = it.next();
                if (i3 == i2) {
                    value = next.getValue();
                    break;
                }
                i3++;
            }
            String deviceName = value != null ? value.getDeviceName() : TraeAudioManager.DEVICE_NONE;
            this.mLock.unlock();
            return deviceName;
        }

        public int getDeviceNumber() {
            this.mLock.lock();
            int size = this.deviceConfigs.size();
            this.mLock.unlock();
            return size;
        }

        public String getPrevConnectedDevice() {
            this.mLock.lock();
            String str_getPrevConnectedDevice = _getPrevConnectedDevice();
            this.mLock.unlock();
            return str_getPrevConnectedDevice;
        }

        public int getPriority(String str) {
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(str);
            int priority = deviceConfig != null ? deviceConfig.getPriority() : -1;
            this.mLock.unlock();
            return priority;
        }

        public HashMap<String, Object> getSnapParams() {
            HashMap<String, Object> map = new HashMap<>();
            this.mLock.lock();
            map.put(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, _getAvailableDeviceList());
            map.put(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, _getConnectedDevice());
            map.put(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, _getPrevConnectedDevice());
            this.mLock.unlock();
            return map;
        }

        public boolean getVisiableUpdateFlag() {
            this.mLock.lock();
            boolean z2 = this.visiableUpdate;
            this.mLock.unlock();
            return z2;
        }

        public boolean getVisible(String str) {
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(str);
            boolean visible = deviceConfig != null ? deviceConfig.getVisible() : false;
            this.mLock.unlock();
            return visible;
        }

        public boolean init(String str) {
            String strReplace;
            AudioDeviceInterface.LogTraceEntry(" strConfigs:" + str);
            if (str != null && str.length() > 0 && (strReplace = str.replace("\n", "").replace(StrPool.CR, "")) != null && strReplace.length() > 0) {
                if (strReplace.indexOf(h.f3376b) < 0) {
                    strReplace = strReplace + h.f3376b;
                }
                String[] strArrSplit = strReplace.split(h.f3376b);
                if (strArrSplit != null && 1 <= strArrSplit.length) {
                    this.mLock.lock();
                    for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                        _addConfig(strArrSplit[i2], i2);
                    }
                    this.mLock.unlock();
                    TraeAudioManager.this.printDevices();
                    return true;
                }
            }
            return false;
        }

        public boolean isConnected(String str) {
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(str);
            boolean zEquals = (deviceConfig == null || !deviceConfig.getVisible()) ? false : this.connectedDevice.equals(str);
            this.mLock.unlock();
            return zEquals;
        }

        public boolean resetNullConnecting() {
            this.mLock.lock();
            this.connectingDevice = "";
            this.mLock.unlock();
            return true;
        }

        public void resetVisiableUpdateFlag() {
            this.mLock.lock();
            this.visiableUpdate = false;
            this.mLock.unlock();
        }

        public void setBluetoothName(String str) {
            if (str == null) {
                this._bluetoothDevName = "unknow";
            } else if (str.isEmpty()) {
                this._bluetoothDevName = "unknow";
            } else {
                this._bluetoothDevName = str;
            }
        }

        public boolean setConnected(String str) {
            boolean z2;
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(str);
            if (deviceConfig == null || !deviceConfig.getVisible()) {
                z2 = false;
            } else {
                String str2 = this.connectedDevice;
                if (str2 != null && !str2.equals(str)) {
                    this.prevConnectedDevice = this.connectedDevice;
                }
                this.connectedDevice = str;
                this.connectingDevice = "";
                z2 = true;
            }
            this.mLock.unlock();
            return z2;
        }

        public boolean setConnecting(String str) {
            boolean z2;
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(str);
            if (deviceConfig == null || !deviceConfig.getVisible()) {
                z2 = false;
            } else {
                this.connectingDevice = str;
                z2 = true;
            }
            this.mLock.unlock();
            return z2;
        }

        public boolean setVisible(String str, boolean z2) {
            boolean z3;
            this.mLock.lock();
            DeviceConfig deviceConfig = this.deviceConfigs.get(str);
            if (deviceConfig == null || deviceConfig.getVisible() == z2) {
                z3 = false;
            } else {
                deviceConfig.setVisible(z2);
                z3 = true;
                this.visiableUpdate = true;
                if (QLog.isColorLevel()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" ++setVisible:");
                    sb.append(str);
                    sb.append(z2 ? " Y" : " N");
                    QLog.w(TraeAudioManager.TAG, 2, sb.toString());
                }
            }
            this.mLock.unlock();
            return z3;
        }

        public String getAvailabledHighestPriorityDevice() {
            this.mLock.lock();
            DeviceConfig deviceConfig = null;
            for (Map.Entry<String, DeviceConfig> entry : this.deviceConfigs.entrySet()) {
                entry.getKey();
                entry.getValue();
                DeviceConfig value = entry.getValue();
                if (value != null && value.getVisible() && (deviceConfig == null || value.getPriority() >= deviceConfig.getPriority())) {
                    deviceConfig = value;
                }
            }
            this.mLock.unlock();
            return deviceConfig != null ? deviceConfig.getDeviceName() : TraeAudioManager.DEVICE_SPEAKERPHONE;
        }
    }
}
