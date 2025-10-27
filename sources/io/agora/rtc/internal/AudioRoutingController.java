package io.agora.rtc.internal;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes8.dex */
public class AudioRoutingController {
    private static final int BLUETOOTH_SCO_TIMEOUT_MS = 4000;
    private static final int BT_SCO_STATE_CONNECTED = 1;
    private static final int BT_SCO_STATE_CONNECTING = 0;
    private static final int BT_SCO_STATE_DISCONNECTED = 3;
    private static final int BT_SCO_STATE_DISCONNECTING = 2;
    public static final int CMD_FORCE_BT_SCO_OFF = 16;
    public static final int CMD_FORCE_TO_SPEAKER = 11;
    public static final int CMD_MUTE_VIDEO_ALL = 14;
    public static final int CMD_MUTE_VIDEO_LOCAL = 12;
    public static final int CMD_MUTE_VIDEO_REMOTES = 13;
    public static final int CMD_SET_DEFAULT_ROUTING = 10;
    public static final int CMD_START_BT_SCO = 15;
    private static final int ERROR = 4;
    private static final int EVT_BT_HEADSET = 2;
    private static final int EVT_BT_SCO = 3;
    public static final int EVT_CHANNEL_PROFILE = 20;
    public static final int EVT_ENGINE_ROLE_CHANGED = 21;
    private static final int EVT_HEADSET = 1;
    public static final int EVT_PHONE_STATE_CHANGED = 22;
    public static final int EVT_USING_COMM_PARAMETERS = 112;
    public static final int EVT_USING_NORM_PARAMETERS = 113;
    private static final int MAX_SCO_CONNECT_ATTEMPS = 5;
    public static final int OFF = 0;
    public static final int ON = 1;
    private static final int START = 1;
    private static final int STOP = 2;
    private static final String TAG = "AudioRoute";
    private static final int UNINIT = 0;
    public static final int UNSET = -1;
    private BluetoothAdapter mBTAdapter;
    private BluetoothHeadset mBTHeadset;
    private BluetoothProfile.ServiceListener mBTHeadsetListener;
    private BTHeadsetBroadcastReceiver mBTHeadsetReceiver;
    private WeakReference<Context> mContext;
    private EventHandler mEventHandler;
    private HeadsetBroadcastReceiver mHeadsetReceiver;
    private WeakReference<AudioRoutingListener> mListener;
    private int mScoConnectionAttemps;
    private ControllerState mState;
    private boolean mIsWiredHeadsetPlugged = false;
    private int mHeadsetType = -1;
    private boolean mIsBTHeadsetPlugged = false;
    private int mForceSpeakerphone = -1;
    private int mCurrentRouting = -1;
    private int mDefaultRouting = -1;
    private int mChannelProfile = 1;
    private boolean mVideoDisabled = true;
    private boolean mMuteLocal = false;
    private boolean mMuteRemotes = false;
    private int mEngineRole = -1;
    private boolean mPhoneInCall = false;
    private int mSpeakerCommVolume = -1;
    private boolean mBTHeadSetProperlySeted = false;
    private int mBtScoState = 3;
    private boolean mIsBTScoStarted = false;
    private int dynamic_timeout = 4000;
    private ControllerStopState mStopState = null;
    private ControllerStartState mStartState = null;
    private ControllerErrorState mErrorState = null;
    private boolean mUsingCommParameters = false;
    private final Runnable bluetoothTimeoutRunnable = new Runnable() { // from class: io.agora.rtc.internal.AudioRoutingController.1
        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            AudioRoutingController.this.bluetoothTimeout();
        }
    };

    public class BTHeadsetBroadcastReceiver extends BroadcastReceiver {
        private boolean isRegistered;

        private BTHeadsetBroadcastReceiver() {
            this.isRegistered = false;
        }

        public boolean getRegistered() {
            return this.isRegistered;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            try {
                if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                    int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -99);
                    Logging.d(AudioRoutingController.TAG, "BT ACTION_CONNECTION_STATE_CHANGED prev " + intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -99) + ", " + intExtra);
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (intExtra == 0) {
                        Logging.i(AudioRoutingController.TAG, "Bluetooth device " + bluetoothDevice + " disconnected");
                        AudioRoutingController.this.sendEvent(2, 0);
                    } else if (intExtra == 1) {
                        Logging.i(AudioRoutingController.TAG, "Bluetooth device " + bluetoothDevice + " connecting");
                    } else if (intExtra != 2) {
                        if (intExtra != 3) {
                            Logging.i(AudioRoutingController.TAG, "Bluetooth device " + bluetoothDevice + " unknown event, state=" + intExtra);
                        } else {
                            Logging.i(AudioRoutingController.TAG, "Bluetooth device " + bluetoothDevice + " disconnecting");
                            AudioRoutingController.this.mIsBTHeadsetPlugged = false;
                        }
                    } else if (bluetoothDevice != null && (bluetoothDevice.getBluetoothClass().hasService(2097152) || bluetoothDevice.getBluetoothClass().hasService(4194304))) {
                        Logging.i(AudioRoutingController.TAG, "Bluetooth device " + bluetoothDevice + " connected");
                        AudioRoutingController.this.mScoConnectionAttemps = 0;
                        AudioRoutingController.this.sendEvent(2, 1);
                    }
                } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                    int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -99);
                    Logging.d(AudioRoutingController.TAG, "BT ACTION_AUDIO_STATE_CHANGED prev " + intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -99) + ", " + intExtra2);
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    switch (intExtra2) {
                        case 10:
                            Logging.i(AudioRoutingController.TAG, "Bluetooth audio device " + bluetoothDevice2 + " disconnected");
                            break;
                        case 11:
                            Logging.i(AudioRoutingController.TAG, "Bluetooth audio device " + bluetoothDevice2 + " connecting");
                            break;
                        case 12:
                            Logging.i(AudioRoutingController.TAG, "Bluetooth audio device " + bluetoothDevice2 + " connected");
                            break;
                        default:
                            Logging.i(AudioRoutingController.TAG, "Bluetooth audio device " + bluetoothDevice2 + " event, state=" + intExtra2);
                            break;
                    }
                } else if (action.equals("android.media.ACTION_SCO_AUDIO_STATE_UPDATED")) {
                    int intExtra3 = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -99);
                    Logging.d(AudioRoutingController.TAG, "BT ACTION_SCO_AUDIO_STATE_UPDATED prev " + intent.getIntExtra("android.media.extra.SCO_AUDIO_PREVIOUS_STATE", -99) + ", " + intExtra3);
                    if (intExtra3 == -1) {
                        Logging.i(AudioRoutingController.TAG, "Bluetooth SCO device error");
                    } else if (intExtra3 == 0) {
                        Logging.i(AudioRoutingController.TAG, "Bluetooth SCO device disconnected");
                        AudioRoutingController.this.sendEvent(3, 0);
                    } else if (intExtra3 != 1) {
                        if (intExtra3 != 2) {
                            Logging.i(AudioRoutingController.TAG, "Bluetooth SCO device unknown event, state=" + intExtra3);
                        } else {
                            Logging.i(AudioRoutingController.TAG, "Bluetooth SCO device connecting");
                        }
                    } else if (AudioRoutingController.this.mBTAdapter.getProfileConnectionState(1) == 2) {
                        Logging.i(AudioRoutingController.TAG, "Bluetooth SCO device connected");
                        AudioRoutingController.this.cancelTimer();
                        AudioRoutingController.this.sendEvent(3, 1);
                    }
                } else if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    int intExtra4 = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -99);
                    Logging.d(AudioRoutingController.TAG, "BluetoothAdapter.ACTION_STATE_CHANGED prev " + intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -99) + ", " + intExtra4);
                    if (intExtra4 == 10) {
                        AudioRoutingController.this.sendEvent(2, 0);
                    } else if (intExtra4 == 12 && (AudioRoutingController.this.mBTAdapter.getProfileConnectionState(2) == 2 || AudioRoutingController.this.mBTAdapter.getProfileConnectionState(1) == 2)) {
                        AudioRoutingController.this.sendEvent(2, 1);
                    }
                }
            } catch (Exception e2) {
                Logging.e(AudioRoutingController.TAG, "BT broadcast receiver onReceive fail ", e2);
            }
        }

        public void setRegistered(boolean isReg) {
            this.isRegistered = isReg;
        }
    }

    public abstract class ControllerBaseState implements ControllerState {
        private ControllerBaseState() {
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerState
        public int getState() {
            return 0;
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void onEvent(int event, int info) {
            if (event == 1) {
                AudioRoutingController.this.mHeadsetType = info;
                AudioRoutingController.this.mIsWiredHeadsetPlugged = info >= 0;
                return;
            }
            if (event == 2) {
                AudioRoutingController.this.mIsBTHeadsetPlugged = info == 1;
                return;
            }
            if (event == 21) {
                AudioRoutingController.this.mEngineRole = info;
                return;
            }
            if (event == 22) {
                AudioRoutingController.this.mPhoneInCall = info > 0;
                return;
            }
            if (event == 112) {
                AudioRoutingController.this.mUsingCommParameters = true;
            } else if (event != 113) {
                switch (event) {
                    case 12:
                        AudioRoutingController.this.mMuteLocal = info > 0;
                        break;
                    case 13:
                        AudioRoutingController.this.mMuteRemotes = info > 0;
                        break;
                    case 14:
                        AudioRoutingController.this.mVideoDisabled = info > 0;
                        break;
                }
                return;
            }
            AudioRoutingController.this.mUsingCommParameters = false;
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void reset() {
            AudioRoutingController.this.resetAudioRouting();
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void setState(int state) {
            if (state == getState()) {
                Logging.i(AudioRoutingController.TAG, "setState: state not changed!");
            } else {
                AudioRoutingController audioRoutingController = AudioRoutingController.this;
                audioRoutingController.mState = audioRoutingController.changeState(state);
            }
        }
    }

    public class ControllerErrorState extends ControllerBaseState {
        private ControllerErrorState() {
            super();
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public int getState() {
            return 4;
        }
    }

    public class ControllerStartState extends ControllerBaseState {
        public ControllerStartState() {
            super();
            if (AudioRoutingController.this.mDefaultRouting == -1) {
                if (AudioRoutingController.this.mChannelProfile == 0 && AudioRoutingController.this.isAudioOnly()) {
                    AudioRoutingController.this.mDefaultRouting = 1;
                } else {
                    AudioRoutingController.this.mDefaultRouting = 3;
                }
            }
            AudioRoutingController.this.resetAudioRouting();
            Logging.i(AudioRoutingController.TAG, "Monitor start: default routing: " + AudioRoutingController.this.getAudioRouteDesc(AudioRoutingController.this.mDefaultRouting) + ", current routing: " + AudioRoutingController.this.getAudioRouteDesc(AudioRoutingController.this.mCurrentRouting));
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public int getState() {
            return 1;
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void onEvent(int event, int info) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Logging.d(AudioRoutingController.TAG, "StartState: onEvent: " + event + ", info: " + info);
            AudioManager audioManager = AudioRoutingController.this.getAudioManager();
            if (event == 1) {
                AudioRoutingController.this.mHeadsetType = info;
                AudioRoutingController.this.mIsWiredHeadsetPlugged = info >= 0;
                if (AudioRoutingController.this.mPhoneInCall || AudioRoutingController.this.mIsBTHeadsetPlugged) {
                    return;
                }
                if (!AudioRoutingController.this.mIsWiredHeadsetPlugged || AudioRoutingController.this.mCurrentRouting == info) {
                    AudioRoutingController.this.resetAudioRouting();
                    return;
                } else {
                    AudioRoutingController.this.doSetAudioOutputRouting(info);
                    return;
                }
            }
            if (event == 2) {
                if (info == 0) {
                    boolean unused = AudioRoutingController.this.mIsBTHeadsetPlugged;
                }
                AudioRoutingController.this.mBTHeadSetProperlySeted = false;
                AudioRoutingController.this.mIsBTHeadsetPlugged = info == 1;
                if (AudioRoutingController.this.mPhoneInCall) {
                    return;
                }
                Logging.d(AudioRoutingController.TAG, "BT HEADSET EVENT  " + info + " mIsBTHeadsetPlugged " + AudioRoutingController.this.mIsBTHeadsetPlugged);
                if (AudioRoutingController.this.mIsBTHeadsetPlugged) {
                    AudioRoutingController.this.doSetAudioOutputRouting(5);
                    return;
                }
                if (AudioRoutingController.this.mForceSpeakerphone == 1) {
                    AudioRoutingController.this.doSetAudioOutputRouting(3);
                    return;
                }
                if (AudioRoutingController.this.mForceSpeakerphone == 0) {
                    if (AudioRoutingController.this.mIsWiredHeadsetPlugged) {
                        AudioRoutingController.this.doSetAudioOutputRouting(0);
                        return;
                    } else {
                        AudioRoutingController.this.doSetAudioOutputRouting(1);
                        return;
                    }
                }
                if (AudioRoutingController.this.mIsWiredHeadsetPlugged) {
                    AudioRoutingController.this.doSetAudioOutputRouting(0);
                    return;
                } else {
                    AudioRoutingController audioRoutingController = AudioRoutingController.this;
                    audioRoutingController.doSetAudioOutputRouting(audioRoutingController.mDefaultRouting);
                    return;
                }
            }
            if (event == 3) {
                AudioRoutingController.this.mBtScoState = info == 1 ? 1 : 2;
                if (AudioRoutingController.this.mPhoneInCall) {
                    return;
                }
                AudioRoutingController.this.checkBtScoState(info == 1);
                if (info == 0) {
                    AudioRoutingController.this.resetAudioRouting();
                    return;
                }
                return;
            }
            if (event == 11) {
                if (AudioRoutingController.this.mChannelProfile == 1 && AudioRoutingController.this.mIsBTHeadsetPlugged) {
                    return;
                }
                AudioRoutingController.this.mForceSpeakerphone = info;
                if (AudioRoutingController.this.mPhoneInCall) {
                    return;
                }
                AudioRoutingController.this.resetAudioRouting();
                return;
            }
            if (event == 16) {
                AudioManager audioManager2 = AudioRoutingController.this.getAudioManager();
                if (info == 0) {
                    if (audioManager2.isBluetoothScoOn()) {
                        AudioRoutingController.this.doStopBTSco(audioManager2);
                        return;
                    }
                    return;
                } else {
                    if (audioManager2.isBluetoothA2dpOn()) {
                        AudioRoutingController.this.doStartBTSco(audioManager2);
                        return;
                    }
                    return;
                }
            }
            if (event == 21) {
                AudioRoutingController.this.mEngineRole = info;
                if (AudioRoutingController.this.mPhoneInCall) {
                    return;
                }
                AudioRoutingController audioRoutingController2 = AudioRoutingController.this;
                audioRoutingController2.updateBluetoothSco(audioRoutingController2.mCurrentRouting);
                return;
            }
            if (event == 22) {
                Logging.i(AudioRoutingController.TAG, "phone state changed: " + info);
                AudioRoutingController.this.mPhoneInCall = info > 0;
                if (info == 0) {
                    AudioRoutingController.this.resetAudioRouting();
                    return;
                } else {
                    AudioRoutingController.this.mCurrentRouting = -1;
                    return;
                }
            }
            if (event != 112) {
                if (event != 113) {
                    super.onEvent(event, info);
                    return;
                }
                if (!AudioRoutingController.this.mUsingCommParameters) {
                    boolean unused2 = AudioRoutingController.this.mBTHeadSetProperlySeted;
                }
                AudioRoutingController.this.mBTHeadSetProperlySeted = true;
                AudioRoutingController.this.mUsingCommParameters = false;
                if (!audioManager.isBluetoothScoOn()) {
                    AudioRoutingController.this.resetAudioRouting();
                    return;
                } else {
                    AudioRoutingController.this.cancelTimer();
                    AudioRoutingController.this.doStopBTSco(audioManager);
                    return;
                }
            }
            if (AudioRoutingController.this.mUsingCommParameters && AudioRoutingController.this.mBTHeadSetProperlySeted) {
                return;
            }
            AudioRoutingController.this.mUsingCommParameters = true;
            Logging.d(AudioRoutingController.TAG, "BT HEADSET EVENT  " + info + " mIsBTHeadsetPlugged " + AudioRoutingController.this.mIsBTHeadsetPlugged);
            AudioRoutingController.this.mBTHeadSetProperlySeted = true;
            if (!AudioRoutingController.this.mIsBTHeadsetPlugged) {
                AudioRoutingController.this.mIsBTScoStarted = false;
                AudioRoutingController.this.resetAudioRouting();
            } else {
                AudioRoutingController.this.startTimer();
                AudioRoutingController.this.mIsBTScoStarted = true;
                AudioRoutingController.this.doStartBTSco(audioManager);
            }
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void reset() {
            if (AudioRoutingController.this.mDefaultRouting == -1) {
                if (AudioRoutingController.this.mChannelProfile == 0 && AudioRoutingController.this.isAudioOnly()) {
                    AudioRoutingController.this.mDefaultRouting = 1;
                } else {
                    AudioRoutingController.this.mDefaultRouting = 3;
                }
            }
            AudioRoutingController.this.resetAudioRouting();
            StringBuilder sb = new StringBuilder();
            sb.append("Monitor reset: default routing: ");
            AudioRoutingController audioRoutingController = AudioRoutingController.this;
            sb.append(audioRoutingController.getAudioRouteDesc(audioRoutingController.mDefaultRouting));
            sb.append(", current routing: ");
            AudioRoutingController audioRoutingController2 = AudioRoutingController.this;
            sb.append(audioRoutingController2.getAudioRouteDesc(audioRoutingController2.mCurrentRouting));
            Logging.i(AudioRoutingController.TAG, sb.toString());
        }
    }

    public interface ControllerState {
        int getState();

        void onEvent(int event, int info);

        void reset();

        void setState(int state);
    }

    public class ControllerStopState extends ControllerBaseState {
        public ControllerStopState() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super();
            AudioRoutingController.this.cancelTimer();
            if (AudioRoutingController.this.mIsBTScoStarted) {
                AudioRoutingController.this.mIsBTScoStarted = false;
                AudioRoutingController.this.stopBtSco();
            }
            AudioRoutingController.this.mForceSpeakerphone = -1;
            AudioRoutingController.this.mCurrentRouting = -1;
            AudioRoutingController.this.mDefaultRouting = -1;
            AudioRoutingController.this.mScoConnectionAttemps = 0;
            Logging.i(AudioRoutingController.TAG, "Monitor stopped");
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public int getState() {
            return 2;
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void onEvent(int evt, int info) {
            Logging.d(AudioRoutingController.TAG, "StopState: onEvent: " + evt + ", info: " + info);
            try {
                AudioManager audioManager = AudioRoutingController.this.getAudioManager();
                if (evt != 11) {
                    super.onEvent(evt, info);
                } else {
                    audioManager.setSpeakerphoneOn(info == 1);
                    AudioRoutingController.this.mCurrentRouting = info == 1 ? 3 : -1;
                    AudioRoutingController.this.mForceSpeakerphone = info;
                    AudioRoutingController audioRoutingController = AudioRoutingController.this;
                    audioRoutingController.notifyAudioRoutingChanged(audioRoutingController.queryCurrentAudioRouting());
                }
            } catch (Exception e2) {
                Logging.e(AudioRoutingController.TAG, "onEvent: Exception ", e2);
            }
        }

        @Override // io.agora.rtc.internal.AudioRoutingController.ControllerBaseState, io.agora.rtc.internal.AudioRoutingController.ControllerState
        public void reset() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Logging.i(AudioRoutingController.TAG, "Monitor stop state, reset");
            AudioRoutingController.this.mBTHeadSetProperlySeted = false;
            AudioRoutingController.this.cancelTimer();
            if (AudioRoutingController.this.mIsBTScoStarted || AudioRoutingController.this.getAudioManager().isBluetoothScoOn()) {
                AudioRoutingController.this.mIsBTScoStarted = false;
                AudioRoutingController.this.stopBtSco();
            }
            AudioRoutingController.this.mForceSpeakerphone = -1;
            AudioRoutingController.this.mCurrentRouting = -1;
            AudioRoutingController.this.mDefaultRouting = -1;
            AudioRoutingController.this.mScoConnectionAttemps = 0;
            Logging.i(AudioRoutingController.TAG, "Monitor stopped");
        }
    }

    public class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            AudioRoutingController.this.mState.onEvent(msg.what, msg.arg1);
        }
    }

    public class HeadsetBroadcastReceiver extends BroadcastReceiver {
        private boolean isRegistered;

        private HeadsetBroadcastReceiver() {
            this.isRegistered = false;
        }

        public boolean getRegistered() {
            return this.isRegistered;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("android.intent.action.HEADSET_PLUG") && intent.hasExtra("state")) {
                int intExtra = intent.getIntExtra("state", -1);
                if (intExtra == 1) {
                    if (intent.getIntExtra(PLVPPTAuthentic.PermissionType.MICROPHONE, -1) == 1) {
                        Logging.i(AudioRoutingController.TAG, "Headset w/ mic connected");
                        AudioRoutingController.this.sendEvent(1, 0);
                        return;
                    } else {
                        Logging.i(AudioRoutingController.TAG, "Headset w/o mic connected");
                        AudioRoutingController.this.sendEvent(1, 2);
                        return;
                    }
                }
                if (intExtra == 0) {
                    Logging.i(AudioRoutingController.TAG, "Headset disconnected");
                    AudioRoutingController.this.sendEvent(1, -1);
                } else {
                    Logging.i(AudioRoutingController.TAG, "Headset unknown event detected, state=" + intExtra);
                }
            }
        }

        public void setRegistered(boolean isReg) {
            this.isRegistered = isReg;
        }
    }

    public AudioRoutingController(Context context, AudioRoutingListener listener) {
        this.mContext = new WeakReference<>(context);
        this.mListener = new WeakReference<>(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void bluetoothTimeout() throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.internal.AudioRoutingController.bluetoothTimeout():void");
    }

    private String btStateAsString(int state) {
        if (state == 0) {
            return "SCO_CONNECTING";
        }
        if (state == 1) {
            return "SCO_CONNECTED";
        }
        if (state == 2) {
            return "SCO_DISCONNECTING";
        }
        if (state == 3) {
            return "SCO_DISCONNECTED";
        }
        return "Unknown " + state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelTimer() {
        Logging.d(TAG, "cancel bluetooth timer");
        this.mEventHandler.removeCallbacks(this.bluetoothTimeoutRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ControllerState changeState(int state) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (state == 2) {
            if (this.mStopState == null) {
                this.mStopState = new ControllerStopState();
            }
            ControllerStopState controllerStopState = this.mStopState;
            if (controllerStopState != null) {
                controllerStopState.reset();
            }
            return this.mStopState;
        }
        if (state == 1) {
            if (this.mStartState == null) {
                this.mStartState = new ControllerStartState();
            }
            ControllerStartState controllerStartState = this.mStartState;
            if (controllerStartState != null) {
                controllerStartState.reset();
            }
            return this.mStartState;
        }
        if (this.mErrorState == null) {
            this.mErrorState = new ControllerErrorState();
        }
        ControllerErrorState controllerErrorState = this.mErrorState;
        if (controllerErrorState != null) {
            controllerErrorState.reset();
        }
        return this.mErrorState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBtScoState(boolean isBtScoOn) {
        this.mScoConnectionAttemps = 0;
    }

    private void clearBTResource() {
        BluetoothAdapter bluetoothAdapter = this.mBTAdapter;
        if (bluetoothAdapter != null) {
            bluetoothAdapter.closeProfileProxy(1, this.mBTHeadset);
            this.mBTAdapter = null;
        }
        if (this.mBTHeadsetListener != null) {
            this.mBTHeadsetListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int doSetAudioOutputRouting(int routing) {
        Logging.i(TAG, "set audio output routing from " + getAudioRouteDesc(this.mCurrentRouting) + " to " + getAudioRouteDesc(routing));
        try {
            AudioManager audioManager = getAudioManager();
            if (routing != 5) {
                cancelTimer();
                stopBtSco();
                audioManager.setSpeakerphoneOn(false);
                audioManager.setSpeakerphoneOn(routing == 3);
            }
            if (queryCurrentAudioRouting() != routing) {
                if (routing == 3) {
                    new Handler().postDelayed(new Runnable() { // from class: io.agora.rtc.internal.AudioRoutingController.3
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                AudioRoutingController.this.getAudioManager().setSpeakerphoneOn(true);
                            } catch (Exception e2) {
                                Logging.e(AudioRoutingController.TAG, "setSpeakerphoneOn() fail !", e2);
                            }
                        }
                    }, 200L);
                }
                int iQueryCurrentAudioRouting = queryCurrentAudioRouting();
                Logging.i(TAG, "different audio routing from target " + routing + ", actual routing: " + iQueryCurrentAudioRouting + StrPool.BRACKET_START + getAudioRouteDesc(iQueryCurrentAudioRouting) + StrPool.BRACKET_END);
            }
            updateBluetoothSco(routing);
            this.mCurrentRouting = routing;
            notifyAudioRoutingChanged(routing);
            Logging.i(TAG, "audio routing changed to " + getAudioRouteDesc(this.mCurrentRouting));
        } catch (Exception e2) {
            Logging.e(TAG, "set audio output routing failed:", e2);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doStartBTSco(AudioManager am) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            int mode = am.getMode();
            Logging.i(TAG, "doStartBTSco " + Build.VERSION.SDK_INT + " sco on: " + am.isBluetoothScoOn() + " " + mode + StrPool.BRACKET_START + modeAsString(mode) + StrPool.BRACKET_END);
            am.setSpeakerphoneOn(false);
            am.setBluetoothScoOn(false);
            am.stopBluetoothSco();
            am.startBluetoothSco();
            am.setBluetoothScoOn(true);
            BluetoothHeadset bluetoothHeadset = this.mBTHeadset;
            if (bluetoothHeadset != null) {
                try {
                    try {
                        bluetoothHeadset.getClass().getMethod("connectAudio", new Class[0]).invoke(this.mBTHeadset, new Object[0]);
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                    } catch (NoSuchMethodException e3) {
                        e3.printStackTrace();
                    }
                } catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                }
            }
        } catch (Exception e5) {
            Logging.e(TAG, "doStartBTSco fail ", e5);
        }
        Logging.d(TAG, "doStartBTSco done sco on: " + am.isBluetoothScoOn() + " " + am.getMode() + StrPool.BRACKET_START + modeAsString(am.getMode()) + StrPool.BRACKET_END);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doStopBTSco(AudioManager am) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Logging.i(TAG, "doStopBTSco " + Build.VERSION.SDK_INT + " sco on: " + am.isBluetoothScoOn());
        am.setBluetoothScoOn(false);
        am.stopBluetoothSco();
        BluetoothHeadset bluetoothHeadset = this.mBTHeadset;
        if (bluetoothHeadset != null) {
            try {
                bluetoothHeadset.getClass().getMethod("disconnectAudio", new Class[0]).invoke(this.mBTHeadset, new Object[0]);
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (NoSuchMethodException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AudioManager getAudioManager() {
        Context context = this.mContext.get();
        if (context == null) {
            return null;
        }
        return (AudioManager) context.getSystemService("audio");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAudioRouteDesc(int route) {
        switch (route) {
            case -1:
                return "Default";
            case 0:
                return "Headset";
            case 1:
                return "Earpiece";
            case 2:
                return "HeadsetOnly";
            case 3:
                return "Speakerphone";
            case 4:
                return "Loudspeaker";
            case 5:
                return "HeadsetBluetooth";
            default:
                return "Unknown";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAudioOnly() {
        return this.mVideoDisabled || (this.mMuteLocal && this.mMuteRemotes);
    }

    private String modeAsString(int mode) {
        if (mode == 0) {
            return "MODE_NORMAL";
        }
        if (mode == 1) {
            return "MODE_RINGTONE";
        }
        if (mode == 2) {
            return "MODE_IN_CALL";
        }
        if (mode == 3) {
            return "MODE_IN_COMMUNICATION";
        }
        return "Unknown " + mode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAudioRoutingChanged(int routing) {
        AudioRoutingListener audioRoutingListener = this.mListener.get();
        if (audioRoutingListener != null) {
            audioRoutingListener.onAudioRoutingChanged(routing);
        } else {
            Logging.w(TAG, "failed to get audio routing listener");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int queryCurrentAudioRouting() {
        AudioManager audioManager = getAudioManager();
        if (this.mBTAdapter == null) {
            this.mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        try {
            if (audioManager.isSpeakerphoneOn()) {
                return 3;
            }
            if ((audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) && this.mBTAdapter.isEnabled()) {
                return 5;
            }
            return audioManager.isWiredHeadsetOn() ? 0 : 1;
        } catch (Exception e2) {
            Logging.e(TAG, "fatal error @queryCurrentAudioRouting", e2);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAudioRouting() {
        int i2;
        Logging.e(TAG, "default routing: " + getAudioRouteDesc(this.mDefaultRouting) + " bluetooth " + this.mIsBTHeadsetPlugged + ", current routing: " + getAudioRouteDesc(this.mCurrentRouting) + ", actual system routing: " + getAudioRouteDesc(queryCurrentAudioRouting()));
        if (this.mForceSpeakerphone == 1) {
            Logging.i(TAG, "reset(force) audio routing, default routing: " + getAudioRouteDesc(this.mDefaultRouting) + ", current routing: " + getAudioRouteDesc(this.mCurrentRouting) + ", target routing: " + getAudioRouteDesc(3) + ", actual system routing:" + getAudioRouteDesc(queryCurrentAudioRouting()));
            if (this.mCurrentRouting == 3 && queryCurrentAudioRouting() == 3) {
                return;
            }
            doSetAudioOutputRouting(3);
            return;
        }
        int iQueryCurrentAudioRouting = queryCurrentAudioRouting();
        if (iQueryCurrentAudioRouting == 0 || iQueryCurrentAudioRouting == 2) {
            this.mIsWiredHeadsetPlugged = true;
        }
        if (this.mIsBTHeadsetPlugged) {
            i2 = 5;
        } else if (this.mIsWiredHeadsetPlugged) {
            i2 = this.mHeadsetType;
        } else {
            i2 = this.mForceSpeakerphone != 0 ? this.mDefaultRouting : 1;
        }
        Logging.i(TAG, "reset audio routing, default routing: " + getAudioRouteDesc(this.mDefaultRouting) + ", current routing: " + getAudioRouteDesc(this.mCurrentRouting) + ", target routing: " + getAudioRouteDesc(i2) + ", actual system routing: " + getAudioRouteDesc(queryCurrentAudioRouting()));
        if (this.mCurrentRouting == i2 && queryCurrentAudioRouting() == this.mCurrentRouting) {
            return;
        }
        doSetAudioOutputRouting(i2);
    }

    private void startBtSco() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AudioManager audioManager = getAudioManager();
        int mode = audioManager.getMode();
        Logging.i(TAG, "try to opening bt sco " + this.mScoConnectionAttemps + " " + mode + StrPool.BRACKET_START + modeAsString(mode) + "] " + this.mBtScoState + StrPool.BRACKET_START + btStateAsString(this.mBtScoState) + "] sco on: " + audioManager.isBluetoothScoOn());
        StringBuilder sb = new StringBuilder();
        sb.append("Off call sco support = ");
        sb.append(audioManager.isBluetoothScoAvailableOffCall());
        Logging.d(TAG, sb.toString());
        this.mBtScoState = 0;
        this.mScoConnectionAttemps = this.mScoConnectionAttemps + 1;
        doStartBTSco(audioManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimer() {
        this.dynamic_timeout += this.mScoConnectionAttemps * 4000;
        Logging.d(TAG, "start bluetooth timer " + this.dynamic_timeout);
        this.mEventHandler.postDelayed(this.bluetoothTimeoutRunnable, 4000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopBtSco() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AudioManager audioManager = getAudioManager();
        int mode = audioManager.getMode();
        Logging.i(TAG, "try to stopping bt sco " + mode + StrPool.BRACKET_START + modeAsString(mode) + "] " + this.mBtScoState + StrPool.BRACKET_START + btStateAsString(this.mBtScoState) + "] sco on: " + audioManager.isBluetoothScoOn());
        if (audioManager.isBluetoothScoOn()) {
            this.mBtScoState = 2;
        } else {
            this.mBtScoState = 3;
        }
        doStopBTSco(audioManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int updateBluetoothSco(int targetRouting) {
        Logging.d(TAG, "updateBluetoothSco sco started: " + this.mIsBTScoStarted + ", audio route target: " + targetRouting + StrPool.BRACKET_START + getAudioRouteDesc(targetRouting) + "] current: " + this.mCurrentRouting + StrPool.BRACKET_START + getAudioRouteDesc(this.mCurrentRouting) + "], engine role: " + this.mEngineRole + "mUsing  " + this.mUsingCommParameters + " mBTHeadSetProperlySeted " + this.mBTHeadSetProperlySeted);
        if (targetRouting == 5) {
            this.mBTHeadSetProperlySeted = false;
        } else {
            this.mBTHeadSetProperlySeted = true;
        }
        notifyAudioRoutingChanged(targetRouting);
        return 0;
    }

    public void clearListenerNativeHandle() {
        Logging.d(TAG, "clearListenerNativeHandle");
        AudioRoutingListener audioRoutingListener = this.mListener.get();
        if (audioRoutingListener != null) {
            audioRoutingListener.onAudioRoutingDestroyed();
        } else {
            Logging.w(TAG, "failed to get audio routing listener");
        }
    }

    public boolean hasPermission(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission) == 0;
    }

    public int initialize() {
        BluetoothAdapter defaultAdapter;
        BluetoothProfile.ServiceListener serviceListener;
        Logging.i(TAG, "initialize +");
        Context context = this.mContext.get();
        if (context == null) {
            Logging.e(TAG, "context has been GCed");
            return -1;
        }
        AudioManager audioManager = getAudioManager();
        if (audioManager == null) {
            Logging.e(TAG, "invalid context: can't get AudioManager");
            return -1;
        }
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new EventHandler(looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        if (this.mHeadsetReceiver == null) {
            this.mHeadsetReceiver = new HeadsetBroadcastReceiver();
        }
        this.mIsWiredHeadsetPlugged = audioManager.isWiredHeadsetOn();
        this.mState = changeState(2);
        Logging.i(TAG, "Headset setup: Plugged = " + this.mIsWiredHeadsetPlugged);
        if (!this.mHeadsetReceiver.getRegistered()) {
            context.registerReceiver(this.mHeadsetReceiver, new IntentFilter("android.intent.action.HEADSET_PLUG"));
            this.mHeadsetReceiver.setRegistered(true);
        }
        if (this.mBTHeadsetListener != null) {
            Logging.w(TAG, "Bluetooth service Listener already been initialized");
        } else {
            try {
                this.mBTHeadsetListener = new BluetoothProfile.ServiceListener() { // from class: io.agora.rtc.internal.AudioRoutingController.2
                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public void onServiceConnected(int profile, BluetoothProfile proxy) {
                        Logging.i(AudioRoutingController.TAG, "onServiceConnected " + profile + " =? headset(1)");
                        if (profile == 1) {
                            Logging.i(AudioRoutingController.TAG, "on BT service connected: " + profile + " " + proxy);
                            AudioRoutingController.this.mBTHeadset = (BluetoothHeadset) proxy;
                        }
                    }

                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public void onServiceDisconnected(int profile) {
                        Logging.i(AudioRoutingController.TAG, "onServiceDisconnected " + profile + " =? headset(1)");
                        if (profile == 1) {
                            Logging.i(AudioRoutingController.TAG, "on BT service disconnected: " + profile);
                            AudioRoutingController.this.cancelTimer();
                            AudioRoutingController.this.mBTHeadset = null;
                        }
                    }
                };
            } catch (Exception e2) {
                Logging.e(TAG, "initialize failed: unable to create BluetoothProfile.ServiceListener, err=" + e2.getMessage());
            }
        }
        if (!hasPermission(context, "android.permission.BLUETOOTH")) {
            Logging.w(TAG, "lacks BLUETOOTH permission");
            return 0;
        }
        try {
            if (this.mBTHeadsetReceiver == null) {
                this.mBTHeadsetReceiver = new BTHeadsetBroadcastReceiver();
            }
            defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.mBTAdapter = defaultAdapter;
        } catch (Exception e3) {
            Logging.e(TAG, "unable to create BluetoothHeadsetBroadcastReceiver, err:" + e3.getMessage());
        }
        if (defaultAdapter == null || (serviceListener = this.mBTHeadsetListener) == null) {
            Logging.e(TAG, "initialize: failed to get bluetooth adapter!!");
            return 0;
        }
        defaultAdapter.getProfileProxy(context, serviceListener, 1);
        if (2 == this.mBTAdapter.getProfileConnectionState(1)) {
            this.mIsBTHeadsetPlugged = true;
        }
        Logging.i(TAG, "BT headset setup: BTHeadsetPlugged = " + this.mIsBTHeadsetPlugged + " " + this.mBTHeadset);
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        if (!this.mBTHeadsetReceiver.getRegistered()) {
            Intent intentRegisterReceiver = context.registerReceiver(this.mBTHeadsetReceiver, intentFilter);
            this.mBTHeadsetReceiver.setRegistered(true);
            if (intentRegisterReceiver != null && TextUtils.equals(intentRegisterReceiver.getAction(), "android.media.ACTION_SCO_AUDIO_STATE_UPDATED")) {
                if (intentRegisterReceiver.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0) != 1) {
                    Logging.i(TAG, "initial Bluetooth SCO device unconnected");
                    this.mBtScoState = 3;
                } else {
                    Logging.i(TAG, "initial Bluetooth SCO device connected");
                    this.mBtScoState = 1;
                }
            }
        }
        Logging.i(TAG, "initialize -");
        return 0;
    }

    public void sendEvent(int event, int arg) {
        Logging.d(TAG, "sendEvent: [" + event + "], extra arg: " + arg + "... " + this.mEventHandler);
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler != null) {
            this.mEventHandler.sendMessage(eventHandler.obtainMessage(event, arg, 0));
        }
    }

    public void startMonitoring(int defaultRouting, int channelProfile) {
        if (1 == this.mState.getState()) {
            Logging.i(TAG, "state not changed!");
            return;
        }
        this.mDefaultRouting = defaultRouting;
        this.mChannelProfile = channelProfile;
        this.mState.setState(1);
    }

    public void stopMonitoring() {
        this.mState.setState(2);
    }

    public void uninitialize() {
        Logging.d(TAG, "uninitialize");
        try {
            clearBTResource();
            Context context = this.mContext.get();
            if (context != null) {
                HeadsetBroadcastReceiver headsetBroadcastReceiver = this.mHeadsetReceiver;
                if (headsetBroadcastReceiver != null && headsetBroadcastReceiver.getRegistered()) {
                    context.unregisterReceiver(this.mHeadsetReceiver);
                    this.mHeadsetReceiver.setRegistered(false);
                }
                BTHeadsetBroadcastReceiver bTHeadsetBroadcastReceiver = this.mBTHeadsetReceiver;
                if (bTHeadsetBroadcastReceiver != null && bTHeadsetBroadcastReceiver.getRegistered()) {
                    context.unregisterReceiver(this.mBTHeadsetReceiver);
                    this.mBTHeadsetReceiver.setRegistered(false);
                }
            }
            this.mHeadsetReceiver = null;
            this.mBTHeadsetReceiver = null;
        } catch (Exception e2) {
            Logging.e(TAG, "AudioRoutingController uninitialize fail: ", e2);
        }
    }
}
