package com.plv.foundationsdk.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVFastPermission {

    public static class SingletonHolder {
        private static final PLVFastPermission INSTANCE = new PLVFastPermission();

        private SingletonHolder() {
        }
    }

    public static PLVFastPermission getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static boolean hasPermission(Context context, String... strArr) {
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public void jump2Settings(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + context.getApplicationInfo().packageName));
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public void start(Activity activity, List<String> list, PLVOnPermissionCallback pLVOnPermissionCallback) {
        if (activity == null) {
            PLVCommonLog.exception(new Throwable("activity=null"));
            return;
        }
        if (list == null) {
            PLVCommonLog.exception(new Throwable("permissions=null"));
            return;
        }
        if (pLVOnPermissionCallback == null) {
            PLVCommonLog.exception(new Throwable("callback=null"));
            return;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2);
            if (ContextCompat.checkSelfPermission(activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            pLVOnPermissionCallback.onAllGranted();
            return;
        }
        PLVVoidFragment pLVVoidFragmentNewInstance = PLVVoidFragment.newInstance(arrayList);
        pLVVoidFragmentNewInstance.setCallback(pLVOnPermissionCallback);
        activity.getFragmentManager().beginTransaction().add(pLVVoidFragmentNewInstance, activity.getClass().getSimpleName()).commit();
    }

    private PLVFastPermission() {
    }

    public static boolean hasPermission(Context context, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (ContextCompat.checkSelfPermission(context, it.next()) != 0) {
                return false;
            }
        }
        return true;
    }
}
