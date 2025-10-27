package core.monitor;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import c.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes8.dex */
public class MonitorService extends Service {
    public static final String TAG = "MonitorService";
    private c cpuCollector;
    private MonitorHandler mMonitorHandler;
    private HandlerThread mThread;
    private Messenger messenger;
    private boolean stop = true;
    public final Messenger mMessenger = new Messenger(new IncomingHandler());

    public class IncomingHandler extends Handler {
        public IncomingHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws RemoteException {
            int i2 = message.what;
            if (i2 != 0) {
                if (i2 != 1) {
                    super.handleMessage(message);
                    return;
                }
                Log.d(MonitorService.TAG, "IncomingHandler-STOP_SERVICE" + Thread.currentThread());
                MonitorService.this.stop = true;
                return;
            }
            Log.d(MonitorService.TAG, "IncomingHandler-START_SERVICE");
            if (message.replyTo == null) {
                return;
            }
            Message messageObtain = Message.obtain();
            messageObtain.what = 2;
            MonitorService.this.messenger = message.replyTo;
            try {
                MonitorService.this.messenger.send(messageObtain);
                Log.d(MonitorService.TAG, "IncomingHandler-send-MSG_REPLY" + Thread.currentThread());
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            MonitorService.this.mThread = new HandlerThread("MonitorServiceThread");
            MonitorService.this.mThread.start();
            MonitorService monitorService = MonitorService.this;
            monitorService.mMonitorHandler = monitorService.new MonitorHandler(monitorService.mThread.getLooper());
            MonitorService.this.mMonitorHandler.sendEmptyMessage(1);
            MonitorService.this.stop = false;
        }
    }

    public class MonitorHandler extends Handler {
        public MonitorHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws RemoteException {
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            String string = Runtime.getRuntime().availableProcessors() > 1 ? Integer.toString(Integer.parseInt(MonitorService.this.cpuCollector.b()) / Runtime.getRuntime().availableProcessors()) : "0";
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) MonitorService.this.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            activityManager.getMemoryInfo(memoryInfo);
            int iMyUid = Process.myUid();
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            ArrayList arrayList = new ArrayList();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.uid == iMyUid) {
                    arrayList.add(Integer.valueOf(runningAppProcessInfo.pid));
                }
            }
            int[] iArr = new int[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
            }
            long totalPss = 0;
            for (int i3 = 0; i3 < activityManager.getProcessMemoryInfo(iArr).length; i3++) {
                totalPss += r0[i3].getTotalPss();
            }
            Message messageObtain = Message.obtain();
            messageObtain.what = 4;
            Bundle bundle = new Bundle();
            bundle.putString("data", string + ":" + ((totalPss / 1024) + ""));
            messageObtain.setData(bundle);
            if (MonitorService.this.messenger != null) {
                try {
                    MonitorService.this.messenger.send(messageObtain);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (MonitorService.this.stop) {
                return;
            }
            MonitorService.this.mMonitorHandler.sendEmptyMessageDelayed(2, 1000L);
        }
    }

    private int getPPId() {
        Process process;
        try {
            try {
                process = (Process) Process.class.newInstance();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                process = null;
                return ((Integer) Process.class.getMethod("myPpid", new Class[0]).invoke(process, new Object[0])).intValue();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
                process = null;
                return ((Integer) Process.class.getMethod("myPpid", new Class[0]).invoke(process, new Object[0])).intValue();
            }
            return ((Integer) Process.class.getMethod("myPpid", new Class[0]).invoke(process, new Object[0])).intValue();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            return 0;
        } catch (NoSuchMethodException e5) {
            e5.printStackTrace();
            return 0;
        } catch (InvocationTargetException e6) {
            e6.printStackTrace();
            return 0;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind" + this);
        return this.mMessenger.getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: " + this);
        c cVarD = c.d();
        this.cpuCollector = cVarD;
        cVarD.b(getApplicationContext().getPackageName());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy " + this);
        this.mMonitorHandler.getLooper().quit();
        this.mMonitorHandler = null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, i2, i3);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind ");
        return super.onUnbind(intent);
    }
}
