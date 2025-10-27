package com.umeng.analytics.vshelper;

/* loaded from: classes6.dex */
public class PageNameMonitor implements com.umeng.analytics.vshelper.a {
    private static String currentActivity;
    private static String currentCustomPage;
    private static Object lock = new Object();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final PageNameMonitor f23001a = new PageNameMonitor();

        private a() {
        }
    }

    public static PageNameMonitor getInstance() {
        return a.f23001a;
    }

    @Override // com.umeng.analytics.vshelper.a
    public void activityPause(String str) {
        synchronized (lock) {
            currentActivity = null;
        }
    }

    @Override // com.umeng.analytics.vshelper.a
    public void activityResume(String str) {
        synchronized (lock) {
            currentActivity = str;
        }
    }

    @Override // com.umeng.analytics.vshelper.a
    public void customPageBegin(String str) {
        synchronized (lock) {
            currentCustomPage = str;
        }
    }

    @Override // com.umeng.analytics.vshelper.a
    public void customPageEnd(String str) {
        synchronized (lock) {
            currentCustomPage = null;
        }
    }

    public String getCurrenPageName() {
        synchronized (lock) {
            String str = currentCustomPage;
            if (str != null) {
                return str;
            }
            String str2 = currentActivity;
            if (str2 != null) {
                return str2;
            }
            return null;
        }
    }

    private PageNameMonitor() {
    }
}
