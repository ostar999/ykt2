package com.alibaba.sdk.android.beacon;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Beacon {

    /* renamed from: a, reason: collision with root package name */
    private int f2629a;

    /* renamed from: a, reason: collision with other field name */
    private final HandlerThread f0a;

    /* renamed from: a, reason: collision with other field name */
    private final b f1a;

    /* renamed from: a, reason: collision with other field name */
    private final List<OnUpdateListener> f2a;

    /* renamed from: b, reason: collision with root package name */
    private final List<OnServiceErrListener> f2630b;
    private boolean isStartPoll;
    private final String mAppKey;
    private final String mAppSecret;
    private final Map<String, String> mExtras;
    private Handler mHandler;
    private long mLoopInterval;

    public final class BeaconHandler extends Handler {
        static final int MSG_ADD_ERR_LISTENER = 6;
        static final int MSG_ADD_UPDATE_LISTENER = 4;
        static final int MSG_ERR_CALLBACK = 7;
        static final int MSG_REMOVE_UPDATE_LISTENER = 5;
        static final int MSG_START = 0;
        static final int MSG_START_POLLING = 2;
        static final int MSG_STOP_POLLING = 3;
        static final int MSG_UPDATE = 1;

        public BeaconHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                switch (message.what) {
                    case 0:
                        Beacon.this.c((Context) message.obj);
                        break;
                    case 1:
                        Beacon.this.d((Context) message.obj);
                        break;
                    case 2:
                        Beacon.this.e((Context) message.obj);
                        break;
                    case 3:
                        Beacon.this.b();
                        break;
                    case 4:
                        Beacon.this.a((OnUpdateListener) message.obj);
                        break;
                    case 5:
                        Beacon.this.b((OnUpdateListener) message.obj);
                        break;
                    case 6:
                        Beacon.this.a((OnServiceErrListener) message.obj);
                        break;
                    case 7:
                        Beacon.this.b((Error) message.obj);
                        break;
                }
            } catch (Exception e2) {
                Log.i("beacon", e2.getMessage(), e2);
            }
        }
    }

    public static final class Builder {
        String mAppKey;
        String mAppSecret;
        Map<String, String> mExtras = new HashMap();
        long mLoopInterval = 300000;
        boolean isStartPoll = false;

        public Builder appKey(String str) {
            this.mAppKey = str.trim();
            return this;
        }

        public Builder appSecret(String str) {
            this.mAppSecret = str.trim();
            return this;
        }

        public Beacon build() {
            return new Beacon(this);
        }

        public Builder defaultConfig() {
            this.mAppKey = "24657847";
            this.mAppSecret = "f30fc0937f2b1e9e50a1b7134f1ddb10";
            return this;
        }

        public Builder extras(Map<String, String> map) {
            this.mExtras.putAll(map);
            return this;
        }

        public Builder loopInterval(long j2) {
            if (j2 < 60000) {
                this.mLoopInterval = 60000L;
            } else {
                this.mLoopInterval = j2;
            }
            return this;
        }

        public Builder startPoll(boolean z2) {
            this.isStartPoll = z2;
            return this;
        }
    }

    public static final class Config {
        public final String key;
        public final String value;

        public Config(String str, String str2) {
            this.key = str;
            this.value = str2;
        }
    }

    public static final class Error {
        public final String errCode;
        public final String errMsg;

        public Error(String str, String str2) {
            this.errCode = str;
            this.errMsg = str2;
        }
    }

    public interface OnServiceErrListener {
        void onErr(Error error);
    }

    public interface OnUpdateListener {
        void onUpdate(List<Config> list);
    }

    private Beacon(Builder builder) {
        this.f2a = new ArrayList();
        this.f2630b = new ArrayList();
        this.f2629a = 255;
        this.mAppKey = builder.mAppKey;
        this.mAppSecret = builder.mAppSecret;
        this.mExtras = builder.mExtras;
        this.mLoopInterval = builder.mLoopInterval;
        this.isStartPoll = builder.isStartPoll;
        this.f1a = new b(this);
        HandlerThread handlerThread = new HandlerThread("Beacon Daemon");
        this.f0a = handlerThread;
        handlerThread.start();
        a();
    }

    private void a() {
        this.mHandler = new BeaconHandler(this.f0a.getLooper());
    }

    private void a(Context context) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 1;
        messageObtain.obj = context;
        this.mHandler.sendMessage(messageObtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(OnServiceErrListener onServiceErrListener) {
        this.f2630b.add(onServiceErrListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(OnUpdateListener onUpdateListener) {
        this.f2a.add(onUpdateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.mHandler.getLooper().quitSafely();
        a();
    }

    private void b(Context context) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 2;
        messageObtain.obj = context;
        this.mHandler.sendMessage(messageObtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Error error) {
        Iterator<OnServiceErrListener> it = this.f2630b.iterator();
        while (it.hasNext()) {
            it.next().onErr(error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(OnUpdateListener onUpdateListener) {
        this.f2a.remove(onUpdateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context) {
        if (this.isStartPoll) {
            b(context);
            this.f2629a = 1;
        } else {
            this.f2629a = 1;
            a(context);
            stop();
            this.f2629a = 255;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Context context) {
        this.f1a.m20a(context, this.mAppKey, this.mAppSecret, this.mExtras);
        List<Config> listA = this.f1a.a();
        Iterator<OnUpdateListener> it = this.f2a.iterator();
        while (it.hasNext()) {
            it.next().onUpdate(listA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(Context context) {
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        a(context);
        this.mHandler.sendEmptyMessageDelayed(2, this.mLoopInterval);
    }

    private boolean isStarted() {
        return this.f2629a == 1;
    }

    public static final void setPrepare(boolean z2) {
        a.f2631a = z2;
    }

    public void a(Error error) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 7;
        messageObtain.obj = error;
        this.mHandler.sendMessage(messageObtain);
    }

    public void addServiceErrListener(OnServiceErrListener onServiceErrListener) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 6;
        messageObtain.obj = onServiceErrListener;
        this.mHandler.sendMessage(messageObtain);
    }

    public void addUpdateListener(OnUpdateListener onUpdateListener) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 4;
        messageObtain.obj = onUpdateListener;
        this.mHandler.sendMessage(messageObtain);
    }

    public List<Config> getConfigs() {
        return this.f1a.a();
    }

    public void start(Context context) {
        if (isStarted()) {
            return;
        }
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = context;
        this.mHandler.sendMessage(messageObtain);
    }

    public void stop() {
        if (isStarted()) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 3;
            this.mHandler.sendMessage(messageObtain);
        }
    }
}
