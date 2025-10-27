package com.psychiatrygarden.interfaceclass;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.WelcomeActivity;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class NotifiService extends Service {
    static Timer timer;

    public static void cleanAllNotification() {
        ((NotificationManager) ProjectApp.instance().getSystemService(RemoteMessageConst.NOTIFICATION)).cancelAll();
        Timer timer2 = timer;
        if (timer2 != null) {
            timer2.cancel();
            timer = null;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        LogUtils.e("是否执行服务", "onCreate");
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(final Intent intent, int flags, int startId) {
        LogUtils.e("是否执行服务", "onStartCommand");
        Timer timer2 = timer;
        if (timer2 != null) {
            timer2.cancel();
            timer = new Timer();
        } else {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() { // from class: com.psychiatrygarden.interfaceclass.NotifiService.1
            @Override // java.util.TimerTask, java.lang.Runnable
            @SuppressLint({"NewApi"})
            public void run() throws JSONException {
                String strConfig = SharePreferencesUtils.readStrConfig("liveRoom", ProjectApp.instance(), "");
                if (strConfig.equals("")) {
                    return;
                }
                try {
                    JSONArray jSONArray = new JSONArray();
                    JSONArray jSONArray2 = new JSONArray(strConfig);
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        JSONObject jSONObjectOptJSONObject = jSONArray2.optJSONObject(i2);
                        if (jSONObjectOptJSONObject.optString("is_order").equals("1") && jSONObjectOptJSONObject.optLong("started") > 0) {
                            if (jSONObjectOptJSONObject.optLong("started") < 300) {
                                NotificationManager notificationManager = (NotificationManager) NotifiService.this.getSystemService(RemoteMessageConst.NOTIFICATION);
                                Notification.Builder builder = new Notification.Builder(NotifiService.this);
                                builder.setContentIntent(PendingIntent.getActivity(NotifiService.this, 0, new Intent(NotifiService.this, (Class<?>) WelcomeActivity.class), 0));
                                builder.setSmallIcon(R.drawable.app_icon);
                                builder.setTicker(jSONObjectOptJSONObject.optString("name"));
                                builder.setContentText("你预约的直播马上开始啦");
                                builder.setContentTitle(jSONObjectOptJSONObject.optString("name"));
                                builder.setAutoCancel(true);
                                builder.setDefaults(-1);
                                notificationManager.notify((int) System.currentTimeMillis(), builder.build());
                                Timer timer3 = NotifiService.timer;
                                if (timer3 != null) {
                                    timer3.cancel();
                                    NotifiService.timer = null;
                                }
                            } else if (jSONObjectOptJSONObject.optLong("started") == 1800) {
                                NotificationManager notificationManager2 = (NotificationManager) NotifiService.this.getSystemService(RemoteMessageConst.NOTIFICATION);
                                Notification.Builder builder2 = new Notification.Builder(NotifiService.this);
                                builder2.setContentIntent(PendingIntent.getActivity(NotifiService.this, 0, new Intent(NotifiService.this, (Class<?>) WelcomeActivity.class), 0));
                                builder2.setSmallIcon(R.drawable.app_icon);
                                builder2.setTicker(jSONObjectOptJSONObject.optString("name"));
                                builder2.setContentText("你预约的直播还有30分钟就要开始啦");
                                builder2.setContentTitle(jSONObjectOptJSONObject.optString("name"));
                                builder2.setAutoCancel(true);
                                builder2.setDefaults(-1);
                                notificationManager2.notify((int) System.currentTimeMillis(), builder2.build());
                            } else if (jSONObjectOptJSONObject.optLong("started") == 3600) {
                                NotificationManager notificationManager3 = (NotificationManager) NotifiService.this.getSystemService(RemoteMessageConst.NOTIFICATION);
                                Notification.Builder builder3 = new Notification.Builder(NotifiService.this);
                                builder3.setContentIntent(PendingIntent.getActivity(NotifiService.this, 0, new Intent(NotifiService.this, (Class<?>) WelcomeActivity.class), 0));
                                builder3.setSmallIcon(R.drawable.app_icon);
                                builder3.setTicker(jSONObjectOptJSONObject.optString("name"));
                                builder3.setContentText("你预约的直播还有60分钟就要开始啦");
                                builder3.setContentTitle(jSONObjectOptJSONObject.optString("name"));
                                builder3.setAutoCancel(true);
                                builder3.setDefaults(-1);
                                notificationManager3.notify((int) System.currentTimeMillis(), builder3.build());
                            }
                        }
                        jSONObjectOptJSONObject.put("started", (jSONObjectOptJSONObject.optLong("started") - 5) + "");
                        jSONArray.put(jSONObjectOptJSONObject);
                    }
                    SharePreferencesUtils.writeStrConfig("liveRoom", jSONArray.toString(), ProjectApp.instance());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }, 0L, 5000L);
        return super.onStartCommand(intent, flags, startId);
    }
}
