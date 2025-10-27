package com.tencent.tbs.one.optional;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class TBSOneStandaloneService extends Service {
    public static final String IMPL_CLASS_NAME_KEY = "implClassName";

    /* renamed from: a, reason: collision with root package name */
    public ServiceImpl f22262a;

    public static abstract class ServiceImpl {

        /* renamed from: a, reason: collision with root package name */
        public Service f22263a;

        public IBinder onBind(Intent intent) {
            return null;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onCreate() {
        }

        public void onDestroy() {
        }

        public void onLowMemory() {
        }

        public void onRebind(Intent intent) {
        }

        public abstract int onStartCommand(Intent intent, int i2, int i3);

        public void onTaskRemoved(Intent intent) {
        }

        public void onTrimMemory(int i2) {
        }

        public boolean onUnbind(Intent intent) {
            return false;
        }

        public void setBaseService(Service service) {
            this.f22263a = service;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl == null) {
            return null;
        }
        return serviceImpl.onBind(intent);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl != null) {
            serviceImpl.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl != null) {
            serviceImpl.onDestroy();
        }
        super.onDestroy();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl != null) {
            serviceImpl.onLowMemory();
        }
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl != null) {
            serviceImpl.onRebind(intent);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        ServiceImpl serviceImpl;
        String stringExtra = intent.getStringExtra(IMPL_CLASS_NAME_KEY);
        if (!TextUtils.isEmpty(stringExtra) && ((serviceImpl = this.f22262a) == null || !serviceImpl.getClass().getName().equals(stringExtra))) {
            ServiceImpl serviceImpl2 = this.f22262a;
            if (serviceImpl2 != null) {
                serviceImpl2.onDestroy();
                this.f22262a = null;
            }
            try {
                ServiceImpl serviceImpl3 = (ServiceImpl) Class.forName(stringExtra).newInstance();
                this.f22262a = serviceImpl3;
                serviceImpl3.setBaseService(this);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            ServiceImpl serviceImpl4 = this.f22262a;
            if (serviceImpl4 != null) {
                serviceImpl4.onCreate();
            }
        }
        ServiceImpl serviceImpl5 = this.f22262a;
        return serviceImpl5 != null ? serviceImpl5.onStartCommand(intent, i2, i3) : super.onStartCommand(intent, i2, i3);
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl != null) {
            serviceImpl.onTaskRemoved(intent);
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int i2) {
        super.onTrimMemory(i2);
        ServiceImpl serviceImpl = this.f22262a;
        if (serviceImpl != null) {
            serviceImpl.onTrimMemory(i2);
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        ServiceImpl serviceImpl = this.f22262a;
        return serviceImpl == null ? super.onUnbind(intent) : serviceImpl.onUnbind(intent);
    }
}
