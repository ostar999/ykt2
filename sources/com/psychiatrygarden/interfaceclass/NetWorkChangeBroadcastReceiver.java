package com.psychiatrygarden.interfaceclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.NewToast;

/* loaded from: classes4.dex */
public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
                if (NetworkInfo.State.CONNECTED == networkInfo.getState()) {
                    return;
                }
            }
        }
        try {
            if (UserConfig.getUserId().equals("") || !ProjectApp.isForeground) {
                return;
            }
            long jCount = ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().count();
            if (jCount <= 30) {
                NewToast.showShort(context, "网络已经断开，答题记录可能会丢失", 0).show();
                return;
            }
            NewToast.showShort(context, "网络已经断开，您还有" + jCount + "题未提交", 0).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
