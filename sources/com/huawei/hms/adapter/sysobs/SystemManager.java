package com.huawei.hms.adapter.sysobs;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class SystemManager {

    /* renamed from: a, reason: collision with root package name */
    public static SystemManager f7429a = new SystemManager();

    /* renamed from: b, reason: collision with root package name */
    public static final Object f7430b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public static SystemNotifier f7431c = new a();

    public static SystemManager getInstance() {
        return f7429a;
    }

    public static SystemNotifier getSystemNotifier() {
        return f7431c;
    }

    public void notifyNoticeResult(int i2) {
        f7431c.notifyNoticeObservers(i2);
    }

    public void notifyResolutionResult(Intent intent, String str) {
        f7431c.notifyObservers(intent, str);
    }

    public void notifyUpdateResult(int i2) {
        f7431c.notifyObservers(i2);
    }

    public static class a implements SystemNotifier {

        /* renamed from: a, reason: collision with root package name */
        public final List<SystemObserver> f7432a = new ArrayList();

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void notifyNoticeObservers(int i2) {
            synchronized (SystemManager.f7430b) {
                Iterator<SystemObserver> it = this.f7432a.iterator();
                while (it.hasNext()) {
                    if (it.next().onNoticeResult(i2)) {
                        it.remove();
                    }
                }
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void notifyObservers(Intent intent, String str) {
            synchronized (SystemManager.f7430b) {
                Iterator<SystemObserver> it = this.f7432a.iterator();
                while (it.hasNext()) {
                    if (it.next().onSolutionResult(intent, str)) {
                        it.remove();
                    }
                }
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void registerObserver(SystemObserver systemObserver) {
            if (systemObserver == null || this.f7432a.contains(systemObserver)) {
                return;
            }
            synchronized (SystemManager.f7430b) {
                this.f7432a.add(systemObserver);
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void unRegisterObserver(SystemObserver systemObserver) {
            synchronized (SystemManager.f7430b) {
                this.f7432a.remove(systemObserver);
            }
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemNotifier
        public void notifyObservers(int i2) {
            synchronized (SystemManager.f7430b) {
                Iterator<SystemObserver> it = this.f7432a.iterator();
                while (it.hasNext()) {
                    if (it.next().onUpdateResult(i2)) {
                        it.remove();
                    }
                }
            }
        }
    }
}
