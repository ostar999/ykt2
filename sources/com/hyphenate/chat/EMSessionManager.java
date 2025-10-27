package com.hyphenate.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.hyphenate.chat.adapter.EMAConnectionListener;
import com.hyphenate.chat.adapter.EMAREncryptUtils;
import com.hyphenate.chat.adapter.EMASessionManager;
import com.hyphenate.util.DeviceUuidFactory;
import com.hyphenate.util.EMLog;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
class EMSessionManager {
    private static final String PREF_KEY_IS_LOGIN_WITH_AGORA_TOKEN = "easemob.chat.is_login_with_agora_token";
    private static final String PREF_KEY_LOGIN_PWD = "easemob.chat.loginpwd";
    private static final String PREF_KEY_LOGIN_PWD_GCM = "easemob.chat.loginpwd.gcm";
    private static final String PREF_KEY_LOGIN_TOKEN = "easemob.chat.login.token";
    private static final String PREF_KEY_LOGIN_TOKEN_GCM = "easemob.chat.login.token.gcm";
    private static final String PREF_KEY_LOGIN_USER = "easemob.chat.loginuser";
    private static final String PREF_KEY_LOGIN_WITH_TOKEN = "easemob.chat.login_with_token";
    private static final String PREF_KEY_TOKEN_AVAILABLE_PERIOD = "easemob.chat.token_available_period";
    private static final String PREF_KEY_TOKEN_TIMESTAMP = "easemob.chat.token_timestamp";
    private static final String TAG = "Session";
    private static EMSessionManager instance = new EMSessionManager();
    private EMAREncryptUtils encryptUtils;
    EMClient mClient;
    EMASessionManager mSessionManager;
    private Timer timer;
    private Context appContext = null;
    public EMContact currentUser = null;
    private String lastLoginUser = null;
    private String lastLoginPwd = null;
    private String lastLoginToken = null;
    private String tokenTimeStamp = null;
    private long tokenAvailablePeriod = 0;

    public static synchronized EMSessionManager getInstance() {
        EMSessionManager eMSessionManager = instance;
        if (eMSessionManager.appContext == null) {
            eMSessionManager.appContext = EMClient.getInstance().getContext();
        }
        return instance;
    }

    public synchronized void checkTokenAvailability(EMAConnectionListener eMAConnectionListener) {
        String tokenTimeStamp = getTokenTimeStamp();
        if (!TextUtils.isEmpty(tokenTimeStamp) && eMAConnectionListener != null) {
            long jLongValue = Long.valueOf(tokenTimeStamp).longValue() - System.currentTimeMillis();
            EMLog.d("Session", "checkTokenAvailability, delay=" + jLongValue);
            if (jLongValue <= 0) {
                eMAConnectionListener.onTokenNotification(108);
            } else if (jLongValue <= getTokenAvailablePeriod() / 2) {
                eMAConnectionListener.onTokenNotification(109);
            }
        }
    }

    public void clearLastLoginPwd() {
        try {
            this.lastLoginPwd = "";
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
            editorEdit.putString(PREF_KEY_LOGIN_PWD, this.lastLoginPwd);
            editorEdit.putString(PREF_KEY_LOGIN_PWD_GCM, this.lastLoginPwd);
            editorEdit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void clearLastLoginToken() {
        try {
            this.lastLoginToken = "";
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
            editorEdit.putString(PREF_KEY_LOGIN_TOKEN, this.lastLoginToken);
            editorEdit.putString(PREF_KEY_LOGIN_TOKEN_GCM, this.lastLoginToken);
            editorEdit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void clearLastLoginUser() {
        try {
            this.lastLoginUser = "";
            EMContact eMContact = this.currentUser;
            eMContact.username = "";
            eMContact.nick = "";
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
            editorEdit.putString(PREF_KEY_LOGIN_USER, this.lastLoginUser);
            editorEdit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void clearLoginWithAgoraTokenData() {
        try {
            this.tokenAvailablePeriod = 0L;
            this.tokenTimeStamp = "";
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
            editorEdit.putLong(PREF_KEY_TOKEN_AVAILABLE_PERIOD, this.tokenAvailablePeriod);
            editorEdit.putString(PREF_KEY_TOKEN_TIMESTAMP, this.tokenTimeStamp);
            editorEdit.putBoolean(PREF_KEY_IS_LOGIN_WITH_AGORA_TOKEN, false);
            editorEdit.commit();
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public String decryptData(byte[] bArr, String str) {
        return this.encryptUtils.aesGcmDecrypt(str, bArr, 1);
    }

    public String encryptData(byte[] bArr, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        this.encryptUtils.initAESgcm(bArr);
        return this.encryptUtils.aesGcmEncrypt(str, 1);
    }

    public String getDeviceId(boolean z2) {
        String string = new DeviceUuidFactory(this.appContext).getDeviceUuid().toString();
        if (z2) {
            saveDeviceId(string);
        } else {
            String bakDeviceId = DeviceUuidFactory.getBakDeviceId(this.appContext);
            if (!TextUtils.isEmpty(bakDeviceId) && !TextUtils.equals(string, bakDeviceId)) {
                return bakDeviceId;
            }
        }
        return string;
    }

    public boolean getIsLoginWithAgoraToken() {
        return PreferenceManager.getDefaultSharedPreferences(this.appContext).getBoolean(PREF_KEY_IS_LOGIN_WITH_AGORA_TOKEN, false);
    }

    public String getLastLoginPwd() {
        if (this.lastLoginPwd == null) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.appContext);
            String string = defaultSharedPreferences.getString(PREF_KEY_LOGIN_PWD_GCM, "");
            if (TextUtils.isEmpty(string)) {
                String string2 = defaultSharedPreferences.getString(PREF_KEY_LOGIN_PWD, "");
                if (TextUtils.isEmpty(string2)) {
                    this.lastLoginPwd = "";
                    return "";
                }
                try {
                    this.lastLoginPwd = this.mSessionManager.decrypt(string2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return this.lastLoginPwd;
            }
            try {
                this.lastLoginPwd = decryptData(getSecretKey(false), string);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return this.lastLoginPwd;
    }

    public String getLastLoginToken() {
        if (this.lastLoginToken == null) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.appContext);
            String string = defaultSharedPreferences.getString(PREF_KEY_LOGIN_TOKEN_GCM, "");
            if (TextUtils.isEmpty(string)) {
                String string2 = defaultSharedPreferences.getString(PREF_KEY_LOGIN_TOKEN, "");
                if (TextUtils.isEmpty(string2)) {
                    this.lastLoginToken = "";
                    return "";
                }
                try {
                    this.lastLoginToken = this.mSessionManager.decrypt(string2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return this.lastLoginToken;
            }
            try {
                this.lastLoginToken = decryptData(getSecretKey(false), string);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return this.lastLoginToken;
    }

    public String getLastLoginUser() {
        if (this.lastLoginUser == null) {
            this.lastLoginUser = PreferenceManager.getDefaultSharedPreferences(this.appContext).getString(PREF_KEY_LOGIN_USER, "");
            this.currentUser = new EMContact(this.lastLoginUser);
        }
        return this.lastLoginUser;
    }

    public String getLoginUserName() {
        return this.currentUser.username;
    }

    public byte[] getSecretKey(boolean z2) {
        String deviceId = getDeviceId(z2);
        return this.mSessionManager.getEncryptionKey(getLastLoginUser(), deviceId);
    }

    public long getTokenAvailablePeriod() {
        if (this.tokenAvailablePeriod == 0) {
            this.tokenAvailablePeriod = PreferenceManager.getDefaultSharedPreferences(this.appContext).getLong(PREF_KEY_TOKEN_AVAILABLE_PERIOD, 0L);
        }
        return this.tokenAvailablePeriod;
    }

    public String getTokenTimeStamp() {
        if (this.tokenTimeStamp == null) {
            this.tokenTimeStamp = PreferenceManager.getDefaultSharedPreferences(this.appContext).getString(PREF_KEY_TOKEN_TIMESTAMP, "");
        }
        return this.tokenTimeStamp;
    }

    public void init(EMClient eMClient, EMASessionManager eMASessionManager) {
        this.mClient = eMClient;
        this.mSessionManager = eMASessionManager;
        this.encryptUtils = new EMAREncryptUtils();
    }

    public boolean isLastLoginWithToken() {
        return PreferenceManager.getDefaultSharedPreferences(this.appContext).getBoolean(PREF_KEY_LOGIN_WITH_TOKEN, false);
    }

    public void saveDeviceId(String str) {
        DeviceUuidFactory.saveBakDeviceId(this.appContext, str);
    }

    public void setLastLoginPwd(String str) {
        if (str == null) {
            return;
        }
        this.lastLoginPwd = str;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
        try {
            editorEdit.putString(PREF_KEY_LOGIN_PWD_GCM, encryptData(getSecretKey(true), str));
            editorEdit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setLastLoginToken(String str) {
        if (str == null) {
            return;
        }
        this.lastLoginToken = str;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
        try {
            editorEdit.putString(PREF_KEY_LOGIN_TOKEN_GCM, encryptData(getSecretKey(true), str));
            editorEdit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setLastLoginUser(String str) {
        if (str == null) {
            return;
        }
        this.currentUser = new EMContact(str);
        this.lastLoginUser = str;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
        editorEdit.putString(PREF_KEY_LOGIN_USER, str);
        editorEdit.commit();
    }

    public void setLastLoginWithToken(boolean z2) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
        editorEdit.putBoolean(PREF_KEY_LOGIN_WITH_TOKEN, z2);
        editorEdit.commit();
    }

    public void setLoginWithAgoraData(boolean z2, String str, long j2) {
        if (str == null || j2 < 0) {
            return;
        }
        this.tokenTimeStamp = str;
        this.tokenAvailablePeriod = j2;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
        try {
            editorEdit.putBoolean(PREF_KEY_IS_LOGIN_WITH_AGORA_TOKEN, z2);
            editorEdit.putString(PREF_KEY_TOKEN_TIMESTAMP, str);
            editorEdit.putLong(PREF_KEY_TOKEN_AVAILABLE_PERIOD, this.tokenAvailablePeriod);
            editorEdit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void startCountDownTokenAvailableTime(final EMAConnectionListener eMAConnectionListener) {
        String tokenTimeStamp = getTokenTimeStamp();
        if (!TextUtils.isEmpty(tokenTimeStamp) && eMAConnectionListener != null) {
            long tokenAvailablePeriod = getTokenAvailablePeriod();
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
            }
            this.timer = new Timer();
            TimerTask timerTask = new TimerTask() { // from class: com.hyphenate.chat.EMSessionManager.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    EMSessionManager.this.checkTokenAvailability(eMAConnectionListener);
                }
            };
            long jLongValue = Long.valueOf(tokenTimeStamp).longValue() - System.currentTimeMillis();
            if (jLongValue <= 0) {
                eMAConnectionListener.onTokenNotification(108);
            } else if (jLongValue <= tokenAvailablePeriod / 2) {
                EMLog.d("Session", "timer.schedule task1, delay=" + jLongValue);
                this.timer.schedule(timerTask, jLongValue);
                eMAConnectionListener.onTokenNotification(109);
            } else {
                EMLog.d("Session", "timer.schedule task1、task2, delay= " + jLongValue);
                TimerTask timerTask2 = new TimerTask() { // from class: com.hyphenate.chat.EMSessionManager.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        EMSessionManager.this.checkTokenAvailability(eMAConnectionListener);
                    }
                };
                this.timer.schedule(timerTask, jLongValue);
                this.timer.schedule(timerTask2, jLongValue - (tokenAvailablePeriod / 2));
            }
        }
    }
}
