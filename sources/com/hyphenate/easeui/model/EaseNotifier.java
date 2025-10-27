package com.hyphenate.easeui.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.C;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.provider.EaseSettingsProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseNotifier {
    protected static final String CHANNEL_ID = "hyphenate_chatuidemo_notification";
    protected static int NOTIFY_ID = 341;
    private static final String TAG = "EaseNotifier";
    protected static final long[] VIBRATION_PATTERN = {0, 180, 80, 120};
    protected Context appContext;
    protected AudioManager audioManager;
    protected long lastNotifyTime;
    protected String msg;
    protected EaseNotificationInfoProvider notificationInfoProvider;
    protected NotificationManager notificationManager;
    protected String packageName;
    protected Vibrator vibrator;
    protected HashSet<String> fromUsers = new HashSet<>();
    protected int notificationNum = 0;
    protected Ringtone ringtone = null;

    public interface EaseNotificationInfoProvider {
        String getDisplayedText(EMMessage eMMessage);

        String getLatestText(EMMessage eMMessage, int i2, int i3);

        Intent getLaunchIntent(EMMessage eMMessage);

        int getSmallIcon(EMMessage eMMessage);

        String getTitle(EMMessage eMMessage);
    }

    public EaseNotifier(Context context) {
        this.notificationManager = null;
        this.appContext = context.getApplicationContext();
        this.notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "hyphenate chatuidemo message default channel.", 3);
            notificationChannel.setVibrationPattern(VIBRATION_PATTERN);
            this.notificationManager.createNotificationChannel(notificationChannel);
        }
        this.packageName = this.appContext.getApplicationInfo().packageName;
        this.msg = context.getString(R.string.contact_send_message);
        this.audioManager = (AudioManager) this.appContext.getSystemService("audio");
        this.vibrator = (Vibrator) this.appContext.getSystemService("vibrator");
    }

    private NotificationCompat.Builder generateBaseBuilder(String str) {
        String string = this.appContext.getPackageManager().getApplicationLabel(this.appContext.getApplicationInfo()).toString();
        return new NotificationCompat.Builder(this.appContext, CHANNEL_ID).setSmallIcon(this.appContext.getApplicationInfo().icon).setContentTitle(string).setTicker(str).setContentText(str).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(PendingIntent.getActivity(this.appContext, NOTIFY_ID, this.appContext.getPackageManager().getLaunchIntentForPackage(this.packageName), 134217728));
    }

    private NotificationCompat.Builder generateBaseFullIntentBuilder(Intent intent, String str) {
        String string = this.appContext.getPackageManager().getApplicationLabel(this.appContext.getApplicationInfo()).toString();
        return new NotificationCompat.Builder(this.appContext, CHANNEL_ID).setSmallIcon(this.appContext.getApplicationInfo().icon).setContentTitle(string).setTicker(str).setContentText(str).setPriority(1).setCategory("call").setWhen(System.currentTimeMillis()).setAutoCancel(true).setFullScreenIntent(PendingIntent.getActivity(this.appContext, NOTIFY_ID, intent, 134217728), true);
    }

    public void cancelNotification() {
        NotificationManager notificationManager = this.notificationManager;
        if (notificationManager != null) {
            notificationManager.cancel(NOTIFY_ID);
        }
    }

    public void handleMessage(EMMessage eMMessage) {
        try {
            int size = this.fromUsers.size();
            NotificationCompat.Builder builderGenerateBaseBuilder = generateBaseBuilder(String.format(this.msg, Integer.valueOf(size), Integer.valueOf(this.notificationNum)));
            EaseNotificationInfoProvider easeNotificationInfoProvider = this.notificationInfoProvider;
            if (easeNotificationInfoProvider != null) {
                String title = easeNotificationInfoProvider.getTitle(eMMessage);
                if (title != null) {
                    builderGenerateBaseBuilder.setContentTitle(title);
                }
                String displayedText = this.notificationInfoProvider.getDisplayedText(eMMessage);
                if (displayedText != null) {
                    builderGenerateBaseBuilder.setTicker(displayedText);
                }
                Intent launchIntent = this.notificationInfoProvider.getLaunchIntent(eMMessage);
                if (launchIntent != null) {
                    builderGenerateBaseBuilder.setContentIntent(PendingIntent.getActivity(this.appContext, NOTIFY_ID, launchIntent, 134217728));
                }
                String latestText = this.notificationInfoProvider.getLatestText(eMMessage, size, this.notificationNum);
                if (latestText != null) {
                    builderGenerateBaseBuilder.setContentText(latestText);
                }
                int smallIcon = this.notificationInfoProvider.getSmallIcon(eMMessage);
                if (smallIcon != 0) {
                    builderGenerateBaseBuilder.setSmallIcon(smallIcon);
                }
            }
            this.notificationManager.notify(NOTIFY_ID, builderGenerateBaseBuilder.build());
            if (Build.VERSION.SDK_INT < 26) {
                vibrateAndPlayTone(eMMessage);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void notify(EMMessage eMMessage) {
        if (EaseCommonUtils.isSilentMessage(eMMessage)) {
            return;
        }
        if (EaseIM.getInstance().getSettingsProvider().isMsgNotifyAllowed(eMMessage)) {
            if (!EasyUtils.isAppRunningForeground(this.appContext)) {
                EMLog.d(TAG, "app is running in background");
                this.notificationNum++;
                this.fromUsers.add(eMMessage.getFrom());
                handleMessage(eMMessage);
            }
        }
    }

    public void reset() {
        resetNotificationCount();
        cancelNotification();
    }

    public void resetNotificationCount() {
        this.notificationNum = 0;
        this.fromUsers.clear();
    }

    public void setNotificationInfoProvider(EaseNotificationInfoProvider easeNotificationInfoProvider) {
        this.notificationInfoProvider = easeNotificationInfoProvider;
    }

    public void vibrateAndPlayTone(EMMessage eMMessage) {
        if (eMMessage == null || !EaseCommonUtils.isSilentMessage(eMMessage)) {
            EaseSettingsProvider settingsProvider = EaseIM.getInstance().getSettingsProvider();
            if (settingsProvider.isMsgNotifyAllowed(null) && System.currentTimeMillis() - this.lastNotifyTime >= 1000) {
                try {
                    this.lastNotifyTime = System.currentTimeMillis();
                    if (this.audioManager.getRingerMode() == 0) {
                        EMLog.e(TAG, "in slient mode now");
                        return;
                    }
                    if (settingsProvider.isMsgVibrateAllowed(eMMessage)) {
                        this.vibrator.vibrate(VIBRATION_PATTERN, -1);
                    }
                    if (settingsProvider.isMsgSoundAllowed(eMMessage)) {
                        if (this.ringtone == null) {
                            Uri defaultUri = RingtoneManager.getDefaultUri(2);
                            Ringtone ringtone = RingtoneManager.getRingtone(this.appContext, defaultUri);
                            this.ringtone = ringtone;
                            if (ringtone == null) {
                                EMLog.d(TAG, "cant find ringtone at:" + defaultUri.getPath());
                                return;
                            }
                        }
                        if (this.ringtone.isPlaying()) {
                            return;
                        }
                        String str = Build.MANUFACTURER;
                        this.ringtone.play();
                        if (str == null || !str.toLowerCase().contains("samsung")) {
                            return;
                        }
                        new Thread() { // from class: com.hyphenate.easeui.model.EaseNotifier.1
                            @Override // java.lang.Thread, java.lang.Runnable
                            public void run() throws InterruptedException {
                                try {
                                    Thread.sleep(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                                    if (EaseNotifier.this.ringtone.isPlaying()) {
                                        EaseNotifier.this.ringtone.stop();
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        }.run();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public synchronized void notify(List<EMMessage> list) {
        if (EaseCommonUtils.isSilentMessage(list.get(list.size() - 1))) {
            return;
        }
        if (EaseIM.getInstance().getSettingsProvider().isMsgNotifyAllowed(null)) {
            if (!EasyUtils.isAppRunningForeground(this.appContext)) {
                EMLog.d(TAG, "app is running in background");
                for (EMMessage eMMessage : list) {
                    this.notificationNum++;
                    this.fromUsers.add(eMMessage.getFrom());
                }
                handleMessage(list.get(list.size() - 1));
            }
        }
    }

    public synchronized void notify(String str) {
        if (!EasyUtils.isAppRunningForeground(this.appContext)) {
            try {
                this.notificationManager.notify(NOTIFY_ID, generateBaseBuilder(str).build());
                if (Build.VERSION.SDK_INT < 26) {
                    vibrateAndPlayTone(null);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public synchronized void notify(Intent intent, String str, String str2) {
        if (!EasyUtils.isAppRunningForeground(this.appContext)) {
            try {
                NotificationCompat.Builder builderGenerateBaseFullIntentBuilder = generateBaseFullIntentBuilder(intent, str2);
                if (!TextUtils.isEmpty(str)) {
                    builderGenerateBaseFullIntentBuilder.setContentTitle(str);
                }
                this.notificationManager.notify(NOTIFY_ID, builderGenerateBaseFullIntentBuilder.build());
                if (Build.VERSION.SDK_INT < 26) {
                    vibrateAndPlayTone(null);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
