package com.gyf.immersionbar;

import android.app.Application;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
final class EMUI3NavigationBarObserver extends ContentObserver {
    private Application mApplication;
    private ArrayList<ImmersionCallback> mCallbacks;
    private Boolean mIsRegister;

    public static class NavigationBarObserverInstance {
        private static final EMUI3NavigationBarObserver INSTANCE = new EMUI3NavigationBarObserver();

        private NavigationBarObserverInstance() {
        }
    }

    public static EMUI3NavigationBarObserver getInstance() {
        return NavigationBarObserverInstance.INSTANCE;
    }

    public void addOnNavigationBarListener(ImmersionCallback immersionCallback) {
        if (immersionCallback == null) {
            return;
        }
        if (this.mCallbacks == null) {
            this.mCallbacks = new ArrayList<>();
        }
        if (this.mCallbacks.contains(immersionCallback)) {
            return;
        }
        this.mCallbacks.add(immersionCallback);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        ArrayList<ImmersionCallback> arrayList;
        super.onChange(z2);
        Application application = this.mApplication;
        if (application == null || application.getContentResolver() == null || (arrayList = this.mCallbacks) == null || arrayList.isEmpty()) {
            return;
        }
        int i2 = Settings.System.getInt(this.mApplication.getContentResolver(), "navigationbar_is_min", 0);
        NavigationBarType navigationBarType = NavigationBarType.CLASSIC;
        if (i2 == 1) {
            navigationBarType = NavigationBarType.GESTURES;
        }
        Iterator<ImmersionCallback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onNavigationBarChange(i2 == 0, navigationBarType);
        }
    }

    public void register(Application application) {
        Uri uriFor;
        this.mApplication = application;
        if (application == null || application.getContentResolver() == null || this.mIsRegister.booleanValue() || (uriFor = Settings.System.getUriFor("navigationbar_is_min")) == null) {
            return;
        }
        this.mApplication.getContentResolver().registerContentObserver(uriFor, true, this);
        this.mIsRegister = Boolean.TRUE;
    }

    public void removeOnNavigationBarListener(ImmersionCallback immersionCallback) {
        ArrayList<ImmersionCallback> arrayList;
        if (immersionCallback == null || (arrayList = this.mCallbacks) == null) {
            return;
        }
        arrayList.remove(immersionCallback);
    }

    private EMUI3NavigationBarObserver() {
        super(new Handler(Looper.getMainLooper()));
        this.mIsRegister = Boolean.FALSE;
    }
}
