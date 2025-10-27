package com.gyf.immersionbar;

import android.app.Application;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import com.gyf.immersionbar.GestureUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
final class NavigationBarObserver extends ContentObserver {
    private Application mApplication;
    private boolean mIsRegister;
    private ArrayList<OnNavigationBarListener> mListeners;

    public static class NavigationBarObserverInstance {
        private static final NavigationBarObserver INSTANCE = new NavigationBarObserver();

        private NavigationBarObserverInstance() {
        }
    }

    public static NavigationBarObserver getInstance() {
        return NavigationBarObserverInstance.INSTANCE;
    }

    public void addOnNavigationBarListener(OnNavigationBarListener onNavigationBarListener) {
        if (onNavigationBarListener == null) {
            return;
        }
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        if (this.mListeners.contains(onNavigationBarListener)) {
            return;
        }
        this.mListeners.add(onNavigationBarListener);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        super.onChange(z2);
        ArrayList<OnNavigationBarListener> arrayList = this.mListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        GestureUtils.GestureBean gestureBean = GestureUtils.getGestureBean(this.mApplication);
        boolean z3 = true;
        if (gestureBean.isGesture && (!gestureBean.checkNavigation || BarConfig.getNavigationBarHeightInternal(this.mApplication) <= 0)) {
            z3 = false;
        }
        Iterator<OnNavigationBarListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onNavigationBarChange(z3, gestureBean.type);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void register(android.app.Application r6) {
        /*
            r5 = this;
            r5.mApplication = r6
            if (r6 == 0) goto Lcc
            android.content.ContentResolver r6 = r6.getContentResolver()
            if (r6 == 0) goto Lcc
            boolean r6 = r5.mIsRegister
            if (r6 != 0) goto Lcc
            boolean r6 = com.gyf.immersionbar.OSUtils.isHuaWei()
            r0 = 0
            if (r6 != 0) goto L96
            boolean r6 = com.gyf.immersionbar.OSUtils.isEMUI()
            if (r6 == 0) goto L1d
            goto L96
        L1d:
            boolean r6 = com.gyf.immersionbar.OSUtils.isXiaoMi()
            if (r6 != 0) goto L86
            boolean r6 = com.gyf.immersionbar.OSUtils.isMIUI()
            if (r6 == 0) goto L2a
            goto L86
        L2a:
            boolean r6 = com.gyf.immersionbar.OSUtils.isVivo()
            if (r6 != 0) goto L7f
            boolean r6 = com.gyf.immersionbar.OSUtils.isFuntouchOrOriginOs()
            if (r6 == 0) goto L37
            goto L7f
        L37:
            boolean r6 = com.gyf.immersionbar.OSUtils.isOppo()
            if (r6 != 0) goto L78
            boolean r6 = com.gyf.immersionbar.OSUtils.isColorOs()
            if (r6 == 0) goto L44
            goto L78
        L44:
            boolean r6 = com.gyf.immersionbar.OSUtils.isSamsung()
            if (r6 == 0) goto L71
            android.app.Application r6 = r5.mApplication
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r1 = "navigationbar_hide_bar_enabled"
            r2 = -1
            int r6 = android.provider.Settings.Global.getInt(r6, r1, r2)
            if (r6 != r2) goto L6c
            java.lang.String r6 = "navigation_bar_gesture_while_hidden"
            android.net.Uri r6 = android.provider.Settings.Global.getUriFor(r6)
            java.lang.String r0 = "navigation_bar_gesture_detail_type"
            android.net.Uri r0 = android.provider.Settings.Global.getUriFor(r0)
            java.lang.String r1 = "navigation_bar_gesture_hint"
            android.net.Uri r1 = android.provider.Settings.Global.getUriFor(r1)
            goto La8
        L6c:
            android.net.Uri r6 = android.provider.Settings.Global.getUriFor(r1)
            goto La7
        L71:
            java.lang.String r6 = "navigation_mode"
            android.net.Uri r6 = android.provider.Settings.Secure.getUriFor(r6)
            goto La7
        L78:
            java.lang.String r6 = "hide_navigationbar_enable"
            android.net.Uri r6 = android.provider.Settings.Secure.getUriFor(r6)
            goto La7
        L7f:
            java.lang.String r6 = "navigation_gesture_on"
            android.net.Uri r6 = android.provider.Settings.Secure.getUriFor(r6)
            goto La7
        L86:
            java.lang.String r6 = "force_fsg_nav_bar"
            android.net.Uri r6 = android.provider.Settings.Global.getUriFor(r6)
            java.lang.String r1 = "hide_gesture_line"
            android.net.Uri r1 = android.provider.Settings.Global.getUriFor(r1)
            r4 = r1
            r1 = r0
            r0 = r4
            goto La8
        L96:
            boolean r6 = com.gyf.immersionbar.OSUtils.isEMUI3_x()
            java.lang.String r1 = "navigationbar_is_min"
            if (r6 != 0) goto La3
            android.net.Uri r6 = android.provider.Settings.Global.getUriFor(r1)
            goto La7
        La3:
            android.net.Uri r6 = android.provider.Settings.System.getUriFor(r1)
        La7:
            r1 = r0
        La8:
            r2 = 1
            if (r6 == 0) goto Lb6
            android.app.Application r3 = r5.mApplication
            android.content.ContentResolver r3 = r3.getContentResolver()
            r3.registerContentObserver(r6, r2, r5)
            r5.mIsRegister = r2
        Lb6:
            if (r0 == 0) goto Lc1
            android.app.Application r6 = r5.mApplication
            android.content.ContentResolver r6 = r6.getContentResolver()
            r6.registerContentObserver(r0, r2, r5)
        Lc1:
            if (r1 == 0) goto Lcc
            android.app.Application r6 = r5.mApplication
            android.content.ContentResolver r6 = r6.getContentResolver()
            r6.registerContentObserver(r1, r2, r5)
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gyf.immersionbar.NavigationBarObserver.register(android.app.Application):void");
    }

    public void removeOnNavigationBarListener(OnNavigationBarListener onNavigationBarListener) {
        ArrayList<OnNavigationBarListener> arrayList;
        if (onNavigationBarListener == null || (arrayList = this.mListeners) == null) {
            return;
        }
        arrayList.remove(onNavigationBarListener);
    }

    private NavigationBarObserver() {
        super(new Handler(Looper.getMainLooper()));
        this.mIsRegister = false;
    }
}
