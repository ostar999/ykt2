package com.hyphenate.chat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMAChatClient;
import com.hyphenate.chat.adapter.EMAHeartBeatCustomizedParams;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.hyphenate.util.Utils;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
class EMSmartHeartBeat {
    static boolean Debug = false;
    private static final int GCM_DISCONNECT_CHECK_INTERVAL = 180000;
    private static final int PING_PONG_TIMEOUT = 8000;
    private static final String TAG = "smart ping";
    private AlarmManager alarmManager;
    private int currentInterval;
    private Context mContext;
    IParams params;
    private int succeededInterval;
    ExecutorService threadPool;
    private PowerManager.WakeLock wakeLock;
    private int fixedInterval = -1;
    boolean useCustomizedParams = false;
    EMAHeartBeatCustomizedParams mCustomizedWifiParams = null;
    EMAHeartBeatCustomizedParams mCustomizedMobileParams = null;
    private boolean dataReceivedDuringInterval = false;
    private EMHeartBeatReceiver alarmIntentReceiver = null;
    private PendingIntent alarmIntent = null;
    private EMConnectionListener cnnListener = null;
    private EMMessageListener messageListener = null;
    private Object stateLock = new Object();
    private boolean inited = false;
    private boolean prevWifi = false;
    private Timer disconnectTimer = null;
    private TimerTask disconnectTask = null;
    private boolean isInBackground = false;
    private EMSmartPingState pingState = EMSmartPingState.EMReady;
    private Runnable heartBeatRunnable = new Runnable() { // from class: com.hyphenate.chat.EMSmartHeartBeat.5
        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            EMLog.d(EMSmartHeartBeat.TAG, "has network connection:" + NetUtils.hasNetwork(EMSmartHeartBeat.this.mContext) + " has data conn:" + NetUtils.hasDataConnection(EMSmartHeartBeat.this.mContext) + " isConnected to hyphenate server : " + EMClient.getInstance().isConnected());
            if (EMSmartHeartBeat.this.hasDataConnection()) {
                try {
                    EMSmartHeartBeat.this.wakeLock.acquire();
                } catch (Exception e2) {
                    EMLog.e(EMSmartHeartBeat.TAG, e2.getMessage());
                }
                EMLog.d(EMSmartHeartBeat.TAG, "acquire wake lock");
                EMSmartHeartBeat.this.checkPingPong();
                EMSmartHeartBeat.this.releaseWakelock();
            } else {
                EMLog.d(EMSmartHeartBeat.TAG, "....no connection to server");
                if (!NetUtils.hasDataConnection(EMSmartHeartBeat.this.mContext) && EMClient.getInstance().isConnected()) {
                    if (Utils.isSdk14()) {
                        EMLog.d(EMSmartHeartBeat.TAG, "no data connection but im connection is connected, reconnect");
                        EMClient.getInstance().onNetworkChanged(EMAChatClient.EMANetwork.NETWORK_NONE);
                    } else {
                        EMClient.getInstance().forceReconnect();
                    }
                }
            }
            com.hyphenate.a.a.d();
            EMSmartHeartBeat.this.scheduleNextAlarm();
        }
    };

    public class EMParams extends IParams {
        static final int MAX_INTERVAL = 270000;
        static final int MAX_MIN_INTERVAL_COUNTER = 3;
        static final int MIN_INTERVAL = 30000;
        static final int MOBILE_DEFAULT_INTERVAL = 180000;
        static final int PING_PONG_CHECK_INTERVAL = 900000;
        static final int PING_PONG_TIMEOUT = 8000;
        static final int WIFI_DEFAULT_INTERVAL = 120000;

        public EMParams() {
            super();
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getDefaultInterval() {
            return EMSmartHeartBeat.this.fixedInterval != -1 ? EMSmartHeartBeat.this.fixedInterval : NetUtils.isWifiConnected(EMSmartHeartBeat.this.mContext) ? WIFI_DEFAULT_INTERVAL : MOBILE_DEFAULT_INTERVAL;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getMaxInterval() {
            return MAX_INTERVAL;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getMinInterval() {
            return 30000;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getPingPongCheckInterval() {
            return PING_PONG_CHECK_INTERVAL;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getPingPongTimeout() {
            return 8000;
        }
    }

    public class EMParamsCustomized extends IParams {
        static final int PING_PONG_CHECK_INTERVAL = 900000;
        static final int PING_PONG_TIMEOUT = 8000;
        private final int defaultInterval;
        private final int maxInterval;
        private final int minInterval;

        public EMParamsCustomized(EMAHeartBeatCustomizedParams eMAHeartBeatCustomizedParams) {
            super();
            this.defaultInterval = eMAHeartBeatCustomizedParams.defaultInterval;
            this.minInterval = eMAHeartBeatCustomizedParams.minInterval;
            this.maxInterval = eMAHeartBeatCustomizedParams.maxInterval;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getDefaultInterval() {
            if (EMSmartHeartBeat.this.fixedInterval != -1) {
                return EMSmartHeartBeat.this.fixedInterval;
            }
            EMLog.d(EMSmartHeartBeat.TAG, "get customized default: " + this.defaultInterval);
            return this.defaultInterval;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getMaxInterval() {
            return this.maxInterval;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getMinInterval() {
            return this.minInterval;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getPingPongCheckInterval() {
            return PING_PONG_CHECK_INTERVAL;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getPingPongTimeout() {
            return 8000;
        }
    }

    public class EMParamsQuickTest extends IParams {
        static final int MAX_INTERVAL = 30000;
        static final int MAX_MIN_INTERVAL_COUNTER = 3;
        static final int MIN_INTERVAL = 10000;
        static final int MOBILE_DEFAULT_INTERVAL = 20000;
        static final int PING_PONG_CHECK_INTERVAL = 900000;
        static final int PING_PONG_TIMEOUT = 8000;
        static final int WIFI_DEFAULT_INTERVAL = 20000;

        public EMParamsQuickTest() {
            super();
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getDefaultInterval() {
            NetUtils.isWifiConnected(EMSmartHeartBeat.this.mContext);
            return 20000;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getMaxInterval() {
            return 30000;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getMinInterval() {
            return 10000;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getNextInterval(int i2, boolean z2) {
            int maxInterval = i2 + ((z2 ? 1 : -1) * 5 * 1000);
            if (maxInterval > getMaxInterval()) {
                maxInterval = getMaxInterval();
            }
            return maxInterval < getMinInterval() ? getMinInterval() : maxInterval;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getPingPongCheckInterval() {
            return PING_PONG_CHECK_INTERVAL;
        }

        @Override // com.hyphenate.chat.EMSmartHeartBeat.IParams
        public int getPingPongTimeout() {
            return 8000;
        }
    }

    public enum EMSmartPingState {
        EMReady,
        EMEvaluating,
        EMReevaluating,
        EMHitted,
        EMStopped
    }

    public abstract class IParams {
        public IParams() {
        }

        public abstract int getDefaultInterval();

        public abstract int getMaxInterval();

        public abstract int getMinInterval();

        /* JADX WARN: Removed duplicated region for block: B:18:0x0047  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int getNextInterval(int r3, boolean r4) {
            /*
                r2 = this;
                com.hyphenate.chat.EMSmartHeartBeat r0 = com.hyphenate.chat.EMSmartHeartBeat.this
                int r0 = com.hyphenate.chat.EMSmartHeartBeat.access$000(r0)
                r1 = -1
                if (r0 == r1) goto L2c
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "use fixed interval: "
                r3.append(r4)
                com.hyphenate.chat.EMSmartHeartBeat r4 = com.hyphenate.chat.EMSmartHeartBeat.this
                int r4 = com.hyphenate.chat.EMSmartHeartBeat.access$000(r4)
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                java.lang.String r4 = "smart ping"
                com.hyphenate.util.EMLog.d(r4, r3)
                com.hyphenate.chat.EMSmartHeartBeat r3 = com.hyphenate.chat.EMSmartHeartBeat.this
                int r3 = com.hyphenate.chat.EMSmartHeartBeat.access$000(r3)
                return r3
            L2c:
                if (r4 == 0) goto L2f
                r1 = 1
            L2f:
                r4 = 120000(0x1d4c0, float:1.68156E-40)
                r0 = 60000(0xea60, float:8.4078E-41)
                if (r1 >= 0) goto L3d
                if (r3 > r0) goto L3a
                goto L3f
            L3a:
                if (r3 > r4) goto L4a
                goto L47
            L3d:
                if (r3 >= r0) goto L45
            L3f:
                int r1 = r1 * 10
            L41:
                int r1 = r1 * 1000
                int r3 = r3 + r1
                goto L4d
            L45:
                if (r3 >= r4) goto L4a
            L47:
                int r1 = r1 * 30
                goto L41
            L4a:
                int r1 = r1 * 45
                goto L41
            L4d:
                int r4 = r2.getMaxInterval()
                if (r3 <= r4) goto L57
                int r3 = r2.getMaxInterval()
            L57:
                int r4 = r2.getMinInterval()
                if (r3 >= r4) goto L61
                int r3 = r2.getMinInterval()
            L61:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMSmartHeartBeat.IParams.getNextInterval(int, boolean):int");
        }

        public abstract int getPingPongCheckInterval();

        public abstract int getPingPongTimeout();
    }

    private EMSmartHeartBeat(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calcDisconnectedInterval() {
        IParams eMParams;
        int i2;
        EMLog.d(TAG, "reset interval...");
        boolean zIsWifiConnected = NetUtils.isWifiConnected(this.mContext);
        boolean zIsEthernetConnected = NetUtils.isEthernetConnected(this.mContext);
        if (Debug) {
            eMParams = new EMParamsQuickTest();
        } else if (!this.useCustomizedParams || zIsEthernetConnected) {
            eMParams = new EMParams();
        } else {
            eMParams = new EMParamsCustomized(zIsWifiConnected ? this.mCustomizedWifiParams : this.mCustomizedMobileParams);
        }
        this.params = eMParams;
        boolean zIsSameNet = isSameNet(zIsWifiConnected);
        this.prevWifi = zIsWifiConnected;
        if (!zIsSameNet || (i2 = this.currentInterval) == 0) {
            this.currentInterval = this.params.getDefaultInterval();
            this.succeededInterval = 0;
            changeState(EMSmartPingState.EMEvaluating);
        } else {
            this.currentInterval = this.params.getNextInterval(i2, false);
            changeState(this.pingState == EMSmartPingState.EMHitted ? EMSmartPingState.EMEvaluating : EMSmartPingState.EMReevaluating);
            this.succeededInterval = 0;
        }
        this.dataReceivedDuringInterval = false;
        EMLog.d(TAG, "reset currentInterval:" + EMCollector.timeToString(this.currentInterval));
    }

    private void changeState(EMSmartPingState eMSmartPingState) {
        EMLog.d(TAG, "change smart ping state from : " + this.pingState + " to : " + eMSmartPingState);
        synchronized (this.stateLock) {
            this.pingState = eMSmartPingState;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPingPong() throws InterruptedException {
        EMLog.d(TAG, "check pingpong ...");
        int i2 = 0;
        boolean zSendPingPong = false;
        while (true) {
            if (i2 >= 3) {
                break;
            }
            try {
                Thread.sleep(1000L);
                try {
                    if (this.dataReceivedDuringInterval) {
                        return;
                    }
                    zSendPingPong = sendPingPong();
                    if (zSendPingPong) {
                        EMLog.d(TAG, "success to send ping pong ... with current heartbeat interval : " + EMCollector.timeToString(this.currentInterval));
                        this.succeededInterval = this.currentInterval;
                        EMLog.d(TAG, "send ping-pong successes");
                        EMSmartPingState eMSmartPingState = this.pingState;
                        EMSmartPingState eMSmartPingState2 = EMSmartPingState.EMHitted;
                        if (eMSmartPingState == eMSmartPingState2) {
                            EMLog.d(TAG, "that's already in the EMHitted state, just return...");
                            return;
                        }
                        if (this.succeededInterval != this.params.getMaxInterval() && this.pingState != EMSmartPingState.EMReevaluating) {
                            this.currentInterval = this.params.getNextInterval(this.currentInterval, true);
                        }
                        if (this.succeededInterval == this.params.getMaxInterval()) {
                            EMLog.d(TAG, "Find the best interval, interval is the max interval");
                        }
                        if (this.pingState == EMSmartPingState.EMReevaluating) {
                            EMLog.d(TAG, "success to pingping and current state is EMSmartPingState.EMReevaluating, so use current interval as final interval");
                        }
                        EMLog.d(TAG, "enter the ping state : " + this.pingState);
                        changeState(eMSmartPingState2);
                        return;
                    }
                    i2++;
                } catch (Exception unused) {
                    return;
                }
            } catch (InterruptedException unused2) {
                EMLog.e(TAG, "heartbeat thread be interrupt");
                Thread.currentThread().interrupt();
                return;
            }
        }
        if (zSendPingPong) {
            return;
        }
        EMLog.d(TAG, "failed to send ping pong ... with current heartbeat interval : " + EMCollector.timeToString(this.currentInterval));
        if (hasDataConnection()) {
            EMLog.d(TAG, "failed to send ping pong ... with current heartbeat interval : " + EMCollector.timeToString(this.currentInterval));
            EMSmartPingState eMSmartPingState3 = this.pingState;
            if (eMSmartPingState3 == EMSmartPingState.EMEvaluating || eMSmartPingState3 == EMSmartPingState.EMHitted) {
                EMLog.d(TAG, "send ping-pong failed, but has success interval candidate with ping state : " + this.pingState + " enter EMSmartPingState.EMReevaluating");
                changeState(EMSmartPingState.EMReevaluating);
            }
            this.succeededInterval = 0;
            EMClient.getInstance().forceReconnect();
        }
    }

    public static EMSmartHeartBeat create(Context context) {
        return new EMSmartHeartBeat(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TimerTask getTask() {
        return new TimerTask() { // from class: com.hyphenate.chat.EMSmartHeartBeat.4
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                EMLog.d(EMSmartHeartBeat.TAG, "enter the disconnect task");
                if (EMClient.getInstance().isConnected()) {
                    EMClient.getInstance().disconnect();
                }
                try {
                    EMSmartHeartBeat.this.alarmManager.cancel(EMSmartHeartBeat.this.alarmIntent);
                    EMSmartHeartBeat.this.mContext.unregisterReceiver(EMSmartHeartBeat.this.alarmIntentReceiver);
                    EMSmartHeartBeat.this.alarmIntentReceiver = null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasDataConnection() {
        return NetUtils.hasDataConnection(this.mContext) && EMClient.getInstance().isConnected();
    }

    private boolean isSameNet(boolean z2) {
        EMLog.d(TAG, "prevWifi:" + this.prevWifi + " isWifi:" + z2);
        return this.prevWifi == z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWakelock() {
        synchronized (this) {
            PowerManager.WakeLock wakeLock = this.wakeLock;
            if (wakeLock != null && wakeLock.isHeld()) {
                try {
                    this.wakeLock.release();
                } catch (Exception e2) {
                    EMLog.e(TAG, e2.getMessage());
                }
                EMLog.d(TAG, "released the wake lock");
            }
        }
    }

    private void reset() {
        EMLog.d(TAG, "reset interval...");
        this.currentInterval = 0;
        this.succeededInterval = 0;
        this.dataReceivedDuringInterval = false;
        changeState(EMSmartPingState.EMEvaluating);
    }

    private boolean sendPingPong() {
        EMLog.d(TAG, "send ping-pong type heartbeat");
        if (!EMClient.getInstance().isConnected()) {
            return false;
        }
        EMClient.getInstance().checkTokenAvailability();
        return EMClient.getInstance().sendPing(true, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    public void onInit() {
        this.threadPool = Executors.newSingleThreadExecutor();
        changeState(EMSmartPingState.EMEvaluating);
        reset();
        this.disconnectTimer = new Timer();
        this.alarmManager = (AlarmManager) this.mContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (this.cnnListener == null) {
            this.cnnListener = new EMConnectionListener() { // from class: com.hyphenate.chat.EMSmartHeartBeat.1
                @Override // com.hyphenate.EMConnectionListener
                public void onConnected() {
                    EMLog.d(EMSmartHeartBeat.TAG, " onConnectred ...");
                    if (EMPushHelper.getInstance().getPushType() == EMPushType.FCM && EMSmartHeartBeat.this.isInBackground) {
                        EMClient.getInstance().disconnect();
                    } else {
                        EMSmartHeartBeat.this.calcDisconnectedInterval();
                        EMSmartHeartBeat.this.scheduleNextAlarm();
                    }
                }

                @Override // com.hyphenate.EMConnectionListener
                public void onDisconnected(int i2) {
                    EMLog.d(EMSmartHeartBeat.TAG, " onDisconnected ..." + i2);
                }

                @Override // com.hyphenate.EMConnectionListener
                public /* synthetic */ void onLogout(int i2) {
                    d1.c.a(this, i2);
                }

                @Override // com.hyphenate.EMConnectionListener
                public /* synthetic */ void onTokenExpired() {
                    d1.c.b(this);
                }

                @Override // com.hyphenate.EMConnectionListener
                public /* synthetic */ void onTokenWillExpire() {
                    d1.c.c(this);
                }
            };
        }
        if (this.messageListener == null) {
            this.messageListener = new EMMessageListener() { // from class: com.hyphenate.chat.EMSmartHeartBeat.2
                @Override // com.hyphenate.EMMessageListener
                public void onCmdMessageReceived(List<EMMessage> list) {
                    EMSmartHeartBeat.this.dataReceivedDuringInterval = true;
                    EMSmartHeartBeat.this.scheduleNextAlarm();
                }

                @Override // com.hyphenate.EMMessageListener
                public void onGroupMessageRead(List<EMGroupReadAck> list) {
                }

                @Override // com.hyphenate.EMMessageListener
                public void onMessageChanged(EMMessage eMMessage, Object obj) {
                }

                @Override // com.hyphenate.EMMessageListener
                public void onMessageDelivered(List<EMMessage> list) {
                    EMSmartHeartBeat.this.dataReceivedDuringInterval = true;
                    EMSmartHeartBeat.this.scheduleNextAlarm();
                }

                @Override // com.hyphenate.EMMessageListener
                public void onMessageRead(List<EMMessage> list) {
                    EMSmartHeartBeat.this.dataReceivedDuringInterval = true;
                    EMSmartHeartBeat.this.scheduleNextAlarm();
                }

                @Override // com.hyphenate.EMMessageListener
                public void onMessageRecalled(List<EMMessage> list) {
                    EMSmartHeartBeat.this.dataReceivedDuringInterval = true;
                    EMSmartHeartBeat.this.scheduleNextAlarm();
                }

                @Override // com.hyphenate.EMMessageListener
                public void onMessageReceived(List<EMMessage> list) {
                    EMSmartHeartBeat.this.dataReceivedDuringInterval = true;
                    EMSmartHeartBeat.this.scheduleNextAlarm();
                }

                @Override // com.hyphenate.EMMessageListener
                public /* synthetic */ void onReactionChanged(List list) {
                    d1.e.g(this, list);
                }

                @Override // com.hyphenate.EMMessageListener
                public void onReadAckForGroupMessageUpdated() {
                }
            };
        }
        EMClient.getInstance().addConnectionListener(this.cnnListener);
        EMClient.getInstance().chatManager().addMessageListener(this.messageListener);
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        if (this.wakeLock == null) {
            this.wakeLock = powerManager.newWakeLock(1, "heartbeatlock");
        }
        if (Utils.isSdk14()) {
            EMClient.getInstance().setAppStateListener(new EMClient.AppStateListener() { // from class: com.hyphenate.chat.EMSmartHeartBeat.3
                @Override // com.hyphenate.chat.EMClient.AppStateListener
                public void onBackground() {
                    EMSmartHeartBeat.this.isInBackground = true;
                    EMLog.d(EMSmartHeartBeat.TAG, "app onBackground");
                    new Thread(new Runnable() { // from class: com.hyphenate.chat.EMSmartHeartBeat.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (EMPushHelper.getInstance().getPushType() == EMPushType.FCM) {
                                EMSmartHeartBeat eMSmartHeartBeat = EMSmartHeartBeat.this;
                                eMSmartHeartBeat.disconnectTask = eMSmartHeartBeat.getTask();
                                try {
                                    EMSmartHeartBeat.this.disconnectTimer.schedule(EMSmartHeartBeat.this.disconnectTask, 180000L);
                                    EMLog.d(EMSmartHeartBeat.TAG, "schedule disconnect task");
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }).start();
                }

                @Override // com.hyphenate.chat.EMClient.AppStateListener
                public void onForeground() {
                    EMLog.d(EMSmartHeartBeat.TAG, "app onForeground");
                    EMSmartHeartBeat.this.isInBackground = false;
                    new Thread(new Runnable() { // from class: com.hyphenate.chat.EMSmartHeartBeat.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (EMPushHelper.getInstance().getPushType() == EMPushType.FCM && EMSmartHeartBeat.this.disconnectTask != null) {
                                EMSmartHeartBeat.this.disconnectTask.cancel();
                            }
                            if (NetUtils.hasDataConnection(EMSmartHeartBeat.this.mContext)) {
                                if (EMClient.getInstance().isConnected()) {
                                    EMSmartHeartBeat.this.sendPingCheckConnection();
                                } else {
                                    EMClient.getInstance().onNetworkChanged();
                                }
                            }
                        }
                    }).start();
                }
            });
        }
        this.inited = true;
    }

    public void scheduleNextAlarm() {
        long jCurrentTimeMillis;
        try {
            EMLog.d(TAG, "schedule next alarm");
            EMLog.d(TAG, "current heartbeat interval : " + EMCollector.timeToString(this.currentInterval) + " smart ping state : " + this.pingState);
            this.dataReceivedDuringInterval = false;
            if (this.alarmIntent == null) {
                Intent intent = new Intent("hyphenate.chat.heatbeat." + EMClient.getInstance().getChatConfigPrivate().k());
                intent.setPackage(this.mContext.getPackageName());
                this.alarmIntent = PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
            }
            if (this.alarmIntentReceiver == null) {
                this.alarmIntentReceiver = new EMHeartBeatReceiver(this);
                this.mContext.registerReceiver(this.alarmIntentReceiver, new IntentFilter("hyphenate.chat.heatbeat." + EMClient.getInstance().getChatConfigPrivate().k()));
            }
            System.currentTimeMillis();
            if (hasDataConnection()) {
                if (this.currentInterval <= 0) {
                    this.currentInterval = this.params.getDefaultInterval();
                    EMLog.d(TAG, "current heartbeat interval is not set, use default interval : " + EMCollector.timeToString(this.currentInterval));
                }
                jCurrentTimeMillis = System.currentTimeMillis() + this.currentInterval;
            } else {
                jCurrentTimeMillis = System.currentTimeMillis() + 180000;
                EMLog.d(TAG, "is not connected to server, so use idle interval : 3 mins");
            }
            this.alarmManager.setExactAndAllowWhileIdle(0, jCurrentTimeMillis, this.alarmIntent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void sendPingCheckConnection() {
        boolean zSendPing = EMClient.getInstance().sendPing(true, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        EMClient.getInstance().checkTokenAvailability();
        StringBuilder sb = new StringBuilder();
        sb.append("send check Ping ");
        sb.append(zSendPing ? "success" : "timeout");
        EMLog.d(TAG, sb.toString());
        if (!zSendPing) {
            EMClient.getInstance().forceReconnect();
        } else {
            this.dataReceivedDuringInterval = true;
            scheduleNextAlarm();
        }
    }

    public void setCustomizedParams(EMAHeartBeatCustomizedParams eMAHeartBeatCustomizedParams, EMAHeartBeatCustomizedParams eMAHeartBeatCustomizedParams2) {
        if (eMAHeartBeatCustomizedParams == null || eMAHeartBeatCustomizedParams2 == null) {
            return;
        }
        this.useCustomizedParams = true;
        this.mCustomizedWifiParams = eMAHeartBeatCustomizedParams;
        this.mCustomizedMobileParams = eMAHeartBeatCustomizedParams2;
    }

    public void setFixedInterval(int i2) {
        this.fixedInterval = i2 * 1000;
    }

    public void start() {
        if (this.pingState == EMSmartPingState.EMStopped) {
            return;
        }
        if (!EMClient.getInstance().isConnected() && NetUtils.hasDataConnection(this.mContext)) {
            EMClient.getInstance().onNetworkChanged();
        }
        if (!EMClient.getInstance().isConnected() || !NetUtils.hasNetwork(this.mContext)) {
            if (this.dataReceivedDuringInterval) {
                this.dataReceivedDuringInterval = false;
            }
            scheduleNextAlarm();
        } else {
            if (this.dataReceivedDuringInterval) {
                this.dataReceivedDuringInterval = false;
                EMLog.d(TAG, "receiving packet...");
                return;
            }
            EMLog.d(TAG, "post heartbeat runnable");
            synchronized (this) {
                ExecutorService executorService = this.threadPool;
                if (executorService != null && !executorService.isShutdown()) {
                    this.threadPool.execute(this.heartBeatRunnable);
                }
            }
        }
    }

    public void stop() {
        EMLog.d(TAG, "stop heart beat timer");
        if (!this.inited) {
            EMLog.w(TAG, "smart heartbeat is not inited!");
            return;
        }
        changeState(EMSmartPingState.EMStopped);
        synchronized (this) {
            this.threadPool.shutdownNow();
        }
        reset();
        releaseWakelock();
        this.disconnectTimer.cancel();
        if (this.cnnListener != null) {
            EMClient.getInstance().removeConnectionListener(this.cnnListener);
        }
        if (this.messageListener != null) {
            EMClient.getInstance().chatManager().removeMessageListener(this.messageListener);
            this.messageListener = null;
        }
        try {
            this.alarmManager.cancel(this.alarmIntent);
            this.mContext.unregisterReceiver(this.alarmIntentReceiver);
            this.alarmIntentReceiver = null;
        } catch (Exception e2) {
            if (e2.getMessage().contains("Receiver not registered")) {
                return;
            }
            e2.printStackTrace();
        }
    }
}
