package com.umeng.commonsdk;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.aa;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.aq;
import com.umeng.analytics.pro.l;
import com.umeng.common.b;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.listener.OnGetOaidListener;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.TagHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UMConfigure {
    public static final int DEVICE_TYPE_BOX = 2;
    public static final int DEVICE_TYPE_PHONE = 1;
    private static final String KEY_FILE_NAME_APPKEY = "APPKEY";
    private static final String KEY_FILE_NAME_LOG = "LOG";
    private static final String KEY_METHOD_NAME_PUSH_SETCHANNEL = "setMessageChannel";
    private static final String KEY_METHOD_NAME_PUSH_SET_SECRET = "setSecret";
    private static final String KEY_METHOD_NAME_SETAPPKEY = "setAppkey";
    private static final String KEY_METHOD_NAME_SETCHANNEL = "setChannel";
    private static final String KEY_METHOD_NAME_SETDEBUGMODE = "setDebugMode";
    private static final String TAG = "UMConfigure";
    private static final String WRAPER_TYPE_COCOS2DX_X = "Cocos2d-x";
    private static final String WRAPER_TYPE_COCOS2DX_XLUA = "Cocos2d-x_lua";
    private static final String WRAPER_TYPE_FLUTTER = "flutter";
    private static final String WRAPER_TYPE_HYBRID = "hybrid";
    private static final String WRAPER_TYPE_NATIVE = "native";
    private static final String WRAPER_TYPE_PHONEGAP = "phonegap";
    private static final String WRAPER_TYPE_REACTNATIVE = "react-native";
    private static final String WRAPER_TYPE_UNITY = "Unity";
    private static final String WRAPER_TYPE_WEEX = "weex";
    private static boolean debugLog = false;
    private static OnGetOaidListener mOnGetOaidListener;
    public static UMLog umDebugLog = new UMLog();
    private static boolean preInitComplete = false;
    private static Object PreInitLock = new Object();
    public static String sAppkey = "";
    public static String sChannel = "";
    public static boolean isInit = false;
    private static Object lock = new Object();
    private static int preInitInvokedFlag = 0;
    private static int policyGrantInvokedFlag = 0;
    private static int policyGrantResult = 0;
    private static boolean shouldCheckShareSdk = true;
    public static MobclickAgent.PageMode AUTO_ACTIVITY_PAGE_COLLECTION = MobclickAgent.PageMode.AUTO;
    private static final String[] BUSINESS_TYPE = {"apm", "push", "share", "ulink", "uverify", "usms", "urec", "abtest", "game", am.al};
    private static boolean isFinish = false;
    private static Object lockObject = new Object();

    public enum BS_TYPE {
        APM,
        PUSH,
        SHARE,
        ULINK,
        UVERIFY,
        USMS,
        UREC,
        ABTEST,
        GAME,
        ZID
    }

    private static boolean checkShareSdk(Class<?> cls) {
        try {
            return cls.getDeclaredField("isZyb") != null;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static void deleteModuleTag(BS_TYPE bs_type, String str) {
        if (bs_type == null) {
            Log.e("UMLog", UMLogCommon.SC_10025);
            return;
        }
        if (TextUtils.isEmpty(str)) {
            Log.e("UMLog", UMLogCommon.SC_10020);
            return;
        }
        try {
            if (str.trim().getBytes().length > 64) {
                Log.e("UMLog", UMLogCommon.SC_10021);
                return;
            }
            int iOrdinal = bs_type.ordinal();
            String[] strArr = BUSINESS_TYPE;
            String str2 = iOrdinal < strArr.length ? strArr[iOrdinal] : "";
            if (TextUtils.isEmpty(str2)) {
                Log.e("UMLog", UMLogCommon.SC_10022);
                return;
            }
            TagHelper.deleteModuleTag(str2 + StrPool.UNDERLINE + str);
        } catch (Throwable unused) {
        }
    }

    private static void enterRTDModeIfNeeded(Context context) {
        long jLongValue;
        long jLongValue2;
        if (context == null) {
            return;
        }
        String strA = b.a(context, AnalyticsConfig.RTD_SP_FILE, AnalyticsConfig.DEBUG_KEY);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        String strA2 = b.a(context, AnalyticsConfig.RTD_SP_FILE, "startTime");
        String strA3 = b.a(context, AnalyticsConfig.RTD_SP_FILE, "period");
        if (TextUtils.isEmpty(strA2)) {
            jLongValue = 0;
        } else {
            try {
                jLongValue = Long.valueOf(strA2).longValue();
            } catch (Throwable unused) {
            }
        }
        if (TextUtils.isEmpty(strA3)) {
            jLongValue2 = 0;
        } else {
            try {
                jLongValue2 = Long.valueOf(strA3).longValue();
            } catch (Throwable unused2) {
            }
        }
        if (jLongValue == 0 || jLongValue2 == 0) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> [RTD]本地缓存startTime或者duration值无效，清除缓存数据");
            b.a(context, AnalyticsConfig.RTD_SP_FILE);
            return;
        }
        if (System.currentTimeMillis() - jLongValue > jLongValue2 * 60 * 1000) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> [RTD]本地缓存dk值已经超时，清除缓存数据。");
            b.a(context, AnalyticsConfig.RTD_SP_FILE);
            if (AnalyticsConfig.isRealTimeDebugMode()) {
                AnalyticsConfig.turnOffRealTimeDebug();
                return;
            }
            return;
        }
        HashMap map = new HashMap();
        map.put(AnalyticsConfig.DEBUG_KEY, strA);
        if (AnalyticsConfig.isRealTimeDebugMode()) {
            return;
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> [RTD]本地缓存dk值在有效期内，切换到埋点验证模式。");
        AnalyticsConfig.turnOnRealTimeDebug(map);
    }

    private static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private static Object getDecInstanceObject(Class<?> cls) throws NoSuchMethodException, SecurityException {
        Constructor<?> declaredConstructor;
        if (cls == null) {
            return null;
        }
        try {
            declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
        } catch (NoSuchMethodException unused) {
            declaredConstructor = null;
        }
        if (declaredConstructor == null) {
            return null;
        }
        declaredConstructor.setAccessible(true);
        try {
            return declaredConstructor.newInstance(new Object[0]);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException unused2) {
            return null;
        }
    }

    private static Method getDecMethod(Class<?> cls, String str, Class<?>[] clsArr) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = null;
        if (cls != null) {
            try {
                declaredMethod = cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException unused) {
            }
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
            }
        }
        return declaredMethod;
    }

    public static boolean getInitStatus() {
        boolean z2;
        synchronized (lockObject) {
            z2 = isFinish;
        }
        return z2;
    }

    private static Map<String, String> getModuleTags() {
        try {
            return TagHelper.getModuleTags();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void getOaid(Context context, OnGetOaidListener onGetOaidListener) {
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "context is null !!!");
            }
        } else {
            final Context applicationContext = context.getApplicationContext();
            mOnGetOaidListener = onGetOaidListener;
            new Thread(new Runnable() { // from class: com.umeng.commonsdk.UMConfigure.4
                @Override // java.lang.Runnable
                public void run() {
                    String strA = aa.a(applicationContext);
                    if (UMConfigure.mOnGetOaidListener != null) {
                        UMConfigure.mOnGetOaidListener.onGetOaid(strA);
                    }
                }
            }).start();
        }
    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] strArr = new String[2];
        if (context != null) {
            try {
                strArr[0] = DeviceConfig.getDeviceIdForGeneral(context);
                strArr[1] = DeviceConfig.getMac(context);
            } catch (Exception unused) {
            }
        }
        return strArr;
    }

    public static String getUMIDString(Context context) {
        if (context != null) {
            return UMUtils.getUMId(context.getApplicationContext());
        }
        return null;
    }

    public static String getUmengZID(Context context) {
        if (context != null) {
            return UMUtils.getZid(context.getApplicationContext());
        }
        return null;
    }

    private static void ignoreSdkCheck(Context context) {
        shouldCheckShareSdk = false;
    }

    public static void init(Context context, int i2, String str) {
        init(context, null, null, i2, str);
    }

    private static void invoke(Method method, Object obj, Object[] objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method == null || obj == null) {
            return;
        }
        try {
            method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
        }
    }

    public static boolean isDebugLog() {
        return debugLog;
    }

    private static boolean isPreInit() {
        boolean z2;
        synchronized (PreInitLock) {
            z2 = preInitComplete;
        }
        return z2;
    }

    private static boolean needCheckPolicyResult(Context context) {
        File filesDir = context.getFilesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(filesDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append(aq.f22528m);
        return !new File(sb.toString()).exists();
    }

    public static boolean needSendZcfgEnv(Context context) {
        File filesDir = context.getFilesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(filesDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append(aq.f22527l);
        return !new File(sb.toString()).exists();
    }

    public static void preInit(Context context, String str, String str2) {
        synchronized (lock) {
            preInitInvokedFlag = 1;
        }
        preInitInternal(context, str, str2);
    }

    private static void preInitInternal(Context context, String str, String str2) {
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "preInit: context is null, pls check!");
                return;
            }
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str)) {
            str = UMUtils.getAppkeyByXML(applicationContext);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = UMUtils.getChannelByXML(applicationContext);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "Unknown";
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        sAppkey = str;
        sChannel = str2;
        UMGlobalContext.getInstance(applicationContext);
        l.a(applicationContext);
        if (!needSendZcfgEnv(applicationContext)) {
            FieldManager.a().a(applicationContext);
        }
        synchronized (PreInitLock) {
            preInitComplete = true;
        }
    }

    private static void setCheckDevice(boolean z2) {
        AnalyticsConstants.CHECK_DEVICE = z2;
    }

    public static void setEncryptEnabled(boolean z2) {
        com.umeng.commonsdk.statistics.b.a(z2);
    }

    private static void setFile(Class<?> cls, String str, String str2) throws IllegalAccessException, IllegalArgumentException {
        if (cls != null) {
            try {
                cls.getField(str).set(str, str2);
            } catch (Exception unused) {
            }
        }
    }

    private static void setLatencyWindow(long j2) {
        com.umeng.commonsdk.statistics.a.f23286c = ((int) j2) * 1000;
    }

    public static void setLogEnabled(boolean z2) {
        try {
            debugLog = z2;
            MLog.DEBUG = z2;
            Class<?> cls = getClass("com.umeng.message.PushAgent");
            Object decInstanceObject = getDecInstanceObject(cls);
            Class cls2 = Boolean.TYPE;
            invoke(getDecMethod(cls, KEY_METHOD_NAME_SETDEBUGMODE, new Class[]{cls2}), decInstanceObject, new Object[]{Boolean.valueOf(z2)});
            setFile(getClass("com.umeng.socialize.Config"), "DEBUG", z2);
            invoke(getDecMethod(getClass("com.umeng.umcrash.UMCrash"), "setDebug", new Class[]{cls2}), new Object[]{Boolean.valueOf(z2)});
        } catch (Exception e2) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + e2);
            }
        } catch (Throwable th) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + th);
            }
        }
    }

    private static void setModuleTag(BS_TYPE bs_type, String str, String str2) {
        try {
            if (bs_type == null) {
                Log.e("UMLog", UMLogCommon.SC_10024);
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (str.trim().getBytes().length > 64) {
                    Log.e("UMLog", UMLogCommon.SC_10017);
                    return;
                }
                if (str2.trim().getBytes().length > 128) {
                    Log.e("UMLog", UMLogCommon.SC_10018);
                    return;
                }
                int iOrdinal = bs_type.ordinal();
                String[] strArr = BUSINESS_TYPE;
                String str3 = iOrdinal < strArr.length ? strArr[iOrdinal] : "";
                if (TextUtils.isEmpty(str3)) {
                    Log.e("UMLog", UMLogCommon.SC_10019);
                    return;
                }
                TagHelper.setModuleTag(str3 + StrPool.UNDERLINE + str, str2);
                return;
            }
            Log.e("UMLog", UMLogCommon.SC_10016);
        } catch (Throwable unused) {
        }
    }

    public static void setProcessEvent(boolean z2) {
        AnalyticsConstants.SUB_PROCESS_EVENT = z2;
    }

    private static void setWraperType(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(WRAPER_TYPE_NATIVE)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_NATIVE;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_NATIVE;
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_X)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_COCOS2DX_X;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_COCOS2DX_X;
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_XLUA)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_COCOS2DX_XLUA;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_COCOS2DX_XLUA;
            } else if (str.equals(WRAPER_TYPE_UNITY)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_UNITY;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_UNITY;
            } else if (str.equals(WRAPER_TYPE_REACTNATIVE)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_REACTNATIVE;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_REACTNATIVE;
            } else if (str.equals(WRAPER_TYPE_PHONEGAP)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_PHONEGAP;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_PHONEGAP;
            } else if (str.equals(WRAPER_TYPE_WEEX)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_WEEX;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_WEEX;
            } else if (str.equals(WRAPER_TYPE_HYBRID)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_HYBRID;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_HYBRID;
            } else if (str.equals(WRAPER_TYPE_FLUTTER)) {
                com.umeng.commonsdk.stateless.a.f23253a = WRAPER_TYPE_FLUTTER;
                com.umeng.commonsdk.statistics.a.f23284a = WRAPER_TYPE_FLUTTER;
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        com.umeng.commonsdk.stateless.a.f23254b = str2;
        com.umeng.commonsdk.statistics.a.f23285b = str2;
    }

    public static void submitPolicyGrantResult(Context context, boolean z2) {
        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.B, com.umeng.commonsdk.internal.b.a(context).a(), null);
        synchronized (lock) {
            policyGrantInvokedFlag = 1;
            if (z2) {
                policyGrantResult = 1;
            } else {
                policyGrantResult = 2;
            }
        }
        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.f23192z, com.umeng.commonsdk.internal.b.a(context).a(), Integer.valueOf(policyGrantResult));
    }

    public static void init(Context context, String str, String str2, int i2, String str3) {
        Object objInvoke;
        Method declaredMethod;
        Method declaredMethod2;
        Method declaredMethod3;
        Object objInvoke2;
        Method declaredMethod4;
        try {
            try {
                if (debugLog) {
                    Log.i(TAG, "common version is 9.4.7");
                    Log.i(TAG, "common type is " + SdkVersion.SDK_TYPE);
                }
            } catch (Throwable th) {
                if (debugLog) {
                    Log.e(TAG, "init e is " + th);
                }
            }
        } catch (Exception e2) {
            if (debugLog) {
                Log.e(TAG, "init e is " + e2);
            }
        }
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "context is null !!!");
                return;
            }
            return;
        }
        if (isInit) {
            if (debugLog) {
                Log.e(TAG, "has inited !!!");
                return;
            }
            return;
        }
        final Context applicationContext = context.getApplicationContext();
        try {
            if (getClass("com.umeng.umzid.ZIDManager") == null) {
                Log.e(TAG, "--->>> SDK 初始化失败，请检查是否集成umeng-asms-1.2.x.aar库。<<<--- ");
                new Thread() { // from class: com.umeng.commonsdk.UMConfigure.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            Looper.prepare();
                            Toast.makeText(applicationContext, "SDK 初始化失败，请检查是否集成umeng-asms-1.2.X.aar库。", 1).show();
                            Looper.loop();
                        } catch (Throwable unused) {
                        }
                    }
                }.start();
                return;
            }
        } catch (Throwable unused) {
        }
        try {
            Class<?> cls = getClass("com.umeng.message.PushAgent");
            if (cls != null && !checkShareSdk(cls)) {
                Log.e("UMLog", UMLogCommon.SC_10015);
                if (shouldCheckShareSdk) {
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.2
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, UMLogCommon.SC_10015, 1).show();
                                Looper.loop();
                            } catch (Throwable unused2) {
                            }
                        }
                    }.start();
                }
            }
        } catch (Throwable unused2) {
        }
        try {
            Class<?> cls2 = getClass("com.umeng.socialize.UMShareAPI");
            if (cls2 != null && !checkShareSdk(cls2)) {
                Log.e("UMLog", UMLogCommon.SC_10015);
                if (shouldCheckShareSdk) {
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.3
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, UMLogCommon.SC_10015, 1).show();
                                Looper.loop();
                            } catch (Throwable unused3) {
                            }
                        }
                    }.start();
                }
            }
        } catch (Throwable unused3) {
        }
        if (!isPreInit()) {
            preInitInternal(applicationContext, str, str2);
            if (!isPreInit()) {
                return;
            }
        }
        UMUtils.setAppkey(applicationContext, sAppkey);
        String lastAppkey = UMUtils.getLastAppkey(applicationContext);
        if (!TextUtils.isEmpty(sAppkey) && !sAppkey.equals(lastAppkey)) {
            if (!TextUtils.isEmpty(lastAppkey) && debugLog) {
                UMLog.mutlInfo(UMLogCommon.SC_10008, 2, "");
            }
            UMUtils.setLastAppkey(applicationContext, sAppkey);
        }
        if (debugLog) {
            Log.i(TAG, "current appkey is " + sAppkey + ", last appkey is " + lastAppkey);
        }
        if (debugLog) {
            String appkeyByXML = UMUtils.getAppkeyByXML(applicationContext);
            if (!TextUtils.isEmpty(sAppkey) && !TextUtils.isEmpty(appkeyByXML) && !sAppkey.equals(appkeyByXML)) {
                UMLog.mutlInfo(UMLogCommon.SC_10011, 3, "", new String[]{"@", DictionaryFactory.SHARP}, new String[]{sAppkey, appkeyByXML});
            }
        }
        UMUtils.setChannel(applicationContext, sChannel);
        if (debugLog) {
            Log.i(TAG, "channel is " + sChannel);
        }
        try {
            Method declaredMethod5 = MobclickAgent.class.getDeclaredMethod("init", Context.class);
            if (declaredMethod5 != null) {
                declaredMethod5.setAccessible(true);
                declaredMethod5.invoke(MobclickAgent.class, applicationContext);
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> FirstResumeTrigger enabled.");
                    l.a(applicationContext).b(applicationContext);
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> FirstResumeTrigger disabled.");
                }
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10003, 2, "");
                }
            }
            Class.forName("com.umeng.analytics.game.UMGameAgent");
            Method declaredMethod6 = MobclickAgent.class.getDeclaredMethod("setGameScenarioType", Context.class);
            if (declaredMethod6 != null) {
                declaredMethod6.setAccessible(true);
                declaredMethod6.invoke(MobclickAgent.class, applicationContext);
            }
            if (com.umeng.commonsdk.statistics.b.f23287a.indexOf(AliyunLogKey.KEY_EVENT) >= 0 && (declaredMethod4 = MobclickAgent.class.getDeclaredMethod("disableExceptionCatch", new Class[0])) != null) {
                declaredMethod4.setAccessible(true);
                declaredMethod4.invoke(MobclickAgent.class, new Object[0]);
            }
        } catch (Throwable unused4) {
        }
        try {
            Class<?> cls3 = Class.forName("com.umeng.message.MessageSharedPrefs");
            Method declaredMethod7 = cls3.getDeclaredMethod("getInstance", Context.class);
            if (declaredMethod7 != null && (objInvoke2 = declaredMethod7.invoke(cls3, applicationContext)) != null) {
                Method declaredMethod8 = cls3.getDeclaredMethod("setMessageAppKey", String.class);
                if (declaredMethod8 != null) {
                    declaredMethod8.setAccessible(true);
                    declaredMethod8.invoke(objInvoke2, sAppkey);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10004, 2, "");
                    }
                }
                Method declaredMethod9 = cls3.getDeclaredMethod(KEY_METHOD_NAME_PUSH_SETCHANNEL, String.class);
                if (declaredMethod9 != null) {
                    declaredMethod9.setAccessible(true);
                    declaredMethod9.invoke(objInvoke2, sChannel);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10005, 2, "");
                    }
                }
                if (!TextUtils.isEmpty(str3)) {
                    if (debugLog) {
                        Log.i(TAG, "push secret is " + str3);
                    }
                    Method declaredMethod10 = cls3.getDeclaredMethod("setMessageAppSecret", String.class);
                    if (declaredMethod10 != null) {
                        declaredMethod10.setAccessible(true);
                        declaredMethod10.invoke(objInvoke2, str3);
                        if (debugLog) {
                            UMLog.mutlInfo(UMLogCommon.SC_10009, 2, "");
                        }
                    }
                }
            }
        } catch (Exception unused5) {
        }
        try {
            Class<?> cls4 = getClass("com.umeng.socialize.UMShareAPI");
            setFile(cls4, KEY_FILE_NAME_APPKEY, sAppkey);
            if (cls4 != null && (declaredMethod3 = cls4.getDeclaredMethod("init", Context.class, String.class)) != null) {
                declaredMethod3.setAccessible(true);
                declaredMethod3.invoke(cls4, applicationContext, sAppkey);
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10006, 2, "");
                }
            }
        } catch (Throwable unused6) {
        }
        AnalyticsConstants.setDeviceType(i2);
        try {
            Class<?> cls5 = Class.forName("com.umeng.error.UMError");
            Method declaredMethod11 = cls5.getDeclaredMethod("init", Context.class);
            if (declaredMethod11 != null) {
                declaredMethod11.setAccessible(true);
                declaredMethod11.invoke(cls5, applicationContext);
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10010, 2, "");
                }
            }
        } catch (Throwable unused7) {
        }
        try {
            Class<?> cls6 = Class.forName("com.umeng.umcrash.UMCrash");
            if (SdkVersion.SDK_TYPE == 1 && (declaredMethod2 = cls6.getDeclaredMethod("useIntlServices", Boolean.TYPE)) != null) {
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(cls6, Boolean.TRUE);
            }
            Method declaredMethod12 = cls6.getDeclaredMethod("init", Context.class, String.class, String.class);
            if (declaredMethod12 != null) {
                declaredMethod12.setAccessible(true);
                declaredMethod12.invoke(cls6, applicationContext, sAppkey, sChannel);
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10014, 2, "");
                }
            }
        } catch (Throwable unused8) {
        }
        try {
            Method declaredMethod13 = Class.forName("com.umeng.vt.facade.EventFacade").getDeclaredMethod("init", Application.class, String.class, String.class, Integer.TYPE, String.class);
            if (declaredMethod13 != null) {
                declaredMethod13.invoke(null, applicationContext, sAppkey, sChannel, Integer.valueOf(i2), str3);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>初始化 EventFacade 成功.");
            }
        } catch (Throwable unused9) {
        }
        try {
            Method declaredMethod14 = Class.forName("com.umeng.vt.common.VTTracker").getDeclaredMethod("init", Application.class, String.class);
            if (declaredMethod14 != null) {
                declaredMethod14.invoke(null, applicationContext, sAppkey);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>初始化 VTTracker 成功.");
            }
        } catch (Throwable unused10) {
        }
        synchronized (lockObject) {
            isFinish = true;
        }
        if (needCheckPolicyResult(applicationContext)) {
            synchronized (lock) {
                if (preInitInvokedFlag == 0) {
                    Log.e(TAG, UMLogCommon.SC_10026);
                }
                if (policyGrantResult == 0) {
                    Log.e(TAG, UMLogCommon.SC_10028);
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(com.umeng.commonsdk.internal.a.G, preInitInvokedFlag);
                    jSONObject.put(com.umeng.commonsdk.internal.a.H, policyGrantInvokedFlag);
                    jSONObject.put("policyGrantResult", policyGrantResult);
                    UMWorkDispatch.sendEvent(applicationContext, 32793, com.umeng.commonsdk.internal.b.a(applicationContext).a(), jSONObject);
                } catch (Throwable unused11) {
                }
            }
        }
        if (needSendZcfgEnv(applicationContext)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 走零号报文发送逻辑");
            UMWorkDispatch.sendEvent(applicationContext, 32781, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
        } else {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 走正常逻辑.");
            if (FieldManager.b()) {
                UMWorkDispatch.sendEvent(applicationContext, 32791, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
            }
        }
        if (isDebugLog()) {
            UMConfigureInternation.doSelfCheck(applicationContext);
        }
        try {
            Context applicationContext2 = context.getApplicationContext();
            Class<?> cls7 = Class.forName("com.umeng.cconfig.UMRemoteConfig");
            Method declaredMethod15 = cls7.getDeclaredMethod("getInstance", new Class[0]);
            if (declaredMethod15 != null && (objInvoke = declaredMethod15.invoke(cls7, new Object[0])) != null && (declaredMethod = cls7.getDeclaredMethod("init", Context.class)) != null) {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(objInvoke, applicationContext2);
            }
        } catch (Exception unused12) {
        }
        enterRTDModeIfNeeded(context.getApplicationContext());
        if (isInit) {
            return;
        }
        isInit = true;
    }

    private static void invoke(Method method, Object[] objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method != null) {
            try {
                method.invoke(null, objArr);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            }
        }
    }

    private static void setFile(Class<?> cls, String str, boolean z2) throws IllegalAccessException, IllegalArgumentException {
        if (cls != null) {
            try {
                cls.getField(str).set(str, Boolean.valueOf(z2));
            } catch (Exception unused) {
            }
        }
    }
}
