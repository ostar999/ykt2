package com.github.gzuliyujiang.oaid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.media.MediaDrm;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.github.gzuliyujiang.oaid.impl.OAIDFactory;
import com.hjq.permissions.Permission;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class DeviceID {
    private Application application;
    private String clientId;
    private String oaid;
    private boolean tryWidevine;

    public static class Holder {
        static final DeviceID INSTANCE = new DeviceID();

        private Holder() {
        }
    }

    public static String calculateHash(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            byte[] bArrDigest = MessageDigest.getInstance(str2).digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b3 : bArrDigest) {
                sb.append(String.format("%02x", Byte.valueOf(b3)));
            }
            return sb.toString();
        } catch (Exception e2) {
            OAIDLog.print(e2);
            return "";
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID(Context context) {
        String string;
        return (context == null || (string = Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID)) == null || "9774d56d682e549c".equals(string)) ? "" : string;
    }

    public static String getClientId() {
        String str = Holder.INSTANCE.clientId;
        return str == null ? "" : str;
    }

    public static String getClientIdMD5() {
        return calculateHash(getClientId(), "MD5");
    }

    public static String getClientIdSHA1() {
        return calculateHash(getClientId(), "SHA-1");
    }

    public static String getGUID(Context context) throws IOException {
        String uuidFromSystemSettings = getUuidFromSystemSettings(context);
        if (TextUtils.isEmpty(uuidFromSystemSettings)) {
            uuidFromSystemSettings = getUuidFromExternalStorage(context);
        }
        if (TextUtils.isEmpty(uuidFromSystemSettings)) {
            uuidFromSystemSettings = getUuidFromSharedPreferences(context);
        }
        if (!TextUtils.isEmpty(uuidFromSystemSettings)) {
            return uuidFromSystemSettings;
        }
        String string = UUID.randomUUID().toString();
        OAIDLog.print("Generate uuid by random: " + string);
        saveUuidToSharedPreferences(context, string);
        saveUuidToSystemSettings(context, string);
        saveUuidToExternalStorage(context, string);
        return string;
    }

    private static File getGuidFile(Context context) {
        boolean z2 = false;
        if (Build.VERSION.SDK_INT < 30 && context != null && context.checkSelfPermission(Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            z2 = true;
        }
        if (z2 && "mounted".equals(Environment.getExternalStorageState())) {
            return new File(Environment.getExternalStorageDirectory(), "Android/.GUID_uuid");
        }
        return null;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static String getIMEI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            String imei = telephonyManager.getImei();
            return TextUtils.isEmpty(imei) ? telephonyManager.getMeid() : imei;
        } catch (Error e2) {
            OAIDLog.print(e2);
            return "";
        } catch (Exception e3) {
            OAIDLog.print(e3);
            return "";
        }
    }

    public static String getOAID() {
        String str = Holder.INSTANCE.oaid;
        return str == null ? "" : str;
    }

    private static void getOAIDOrOtherId(final Application application, final boolean z2, final IRegisterCallback iRegisterCallback) {
        getOAID(application, new IGetter() { // from class: com.github.gzuliyujiang.oaid.DeviceID.1
            @Override // com.github.gzuliyujiang.oaid.IGetter
            public void onOAIDGetComplete(String str) throws IOException {
                if (TextUtils.isEmpty(str)) {
                    onOAIDGetError(new OAIDException("OAID is empty"));
                    return;
                }
                DeviceID deviceID = Holder.INSTANCE;
                deviceID.clientId = str;
                deviceID.oaid = str;
                OAIDLog.print("Client id is OAID/AAID: " + str);
                IRegisterCallback iRegisterCallback2 = iRegisterCallback;
                if (iRegisterCallback2 != null) {
                    iRegisterCallback2.onComplete(str, null);
                }
            }

            @Override // com.github.gzuliyujiang.oaid.IGetter
            public void onOAIDGetError(Exception exc) throws IOException {
                DeviceID.getOtherId(exc, application, z2, iRegisterCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void getOtherId(Exception exc, Application application, boolean z2, IRegisterCallback iRegisterCallback) throws IOException {
        if (z2) {
            String widevineID = getWidevineID();
            if (!TextUtils.isEmpty(widevineID)) {
                Holder.INSTANCE.clientId = widevineID;
                OAIDLog.print("Client id is WidevineID: " + widevineID);
                if (iRegisterCallback != null) {
                    iRegisterCallback.onComplete(widevineID, exc);
                    return;
                }
                return;
            }
        }
        String androidID = getAndroidID(application);
        if (!TextUtils.isEmpty(androidID)) {
            Holder.INSTANCE.clientId = androidID;
            OAIDLog.print("Client id is AndroidID: " + androidID);
            if (iRegisterCallback != null) {
                iRegisterCallback.onComplete(androidID, exc);
                return;
            }
            return;
        }
        String guid = getGUID(application);
        Holder.INSTANCE.clientId = guid;
        OAIDLog.print("Client id is GUID: " + guid);
        if (iRegisterCallback != null) {
            iRegisterCallback.onComplete(guid, exc);
        }
    }

    public static String getPseudoID() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.BOARD.length() % 10);
        sb.append(Arrays.deepToString(Build.SUPPORTED_ABIS).length() % 10);
        sb.append(Build.DEVICE.length() % 10);
        sb.append(Build.DISPLAY.length() % 10);
        sb.append(Build.HOST.length() % 10);
        sb.append(Build.ID.length() % 10);
        sb.append(Build.MANUFACTURER.length() % 10);
        sb.append(Build.BRAND.length() % 10);
        sb.append(Build.MODEL.length() % 10);
        sb.append(Build.PRODUCT.length() % 10);
        sb.append(Build.BOOTLOADER.length() % 10);
        sb.append(Build.HARDWARE.length() % 10);
        sb.append(Build.TAGS.length() % 10);
        sb.append(Build.TYPE.length() % 10);
        sb.append(Build.USER.length() % 10);
        return sb.toString();
    }

    public static String getUniqueID(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            OAIDLog.print("IMEI/MEID not allowed on Android 10+");
            return "";
        }
        if (context == null) {
            return "";
        }
        if (context.checkSelfPermission(Permission.READ_PHONE_STATE) == 0) {
            return getIMEI(context);
        }
        OAIDLog.print("android.permission.READ_PHONE_STATE not granted");
        return "";
    }

    private static String getUuidFromExternalStorage(Context context) throws IOException {
        String line = "";
        if (context == null) {
            return "";
        }
        File guidFile = getGuidFile(context);
        if (guidFile != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(guidFile));
                try {
                    line = bufferedReader.readLine();
                    bufferedReader.close();
                } finally {
                }
            } catch (Exception e2) {
                OAIDLog.print(e2);
            }
        }
        OAIDLog.print("Get uuid from external storage: " + line);
        return line;
    }

    private static String getUuidFromSharedPreferences(Context context) {
        if (context == null) {
            return "";
        }
        String string = context.getSharedPreferences("GUID", 0).getString(AliyunLogKey.KEY_UUID, "");
        OAIDLog.print("Get uuid from shared preferences: " + string);
        return string;
    }

    private static String getUuidFromSystemSettings(Context context) {
        if (context == null) {
            return "";
        }
        String string = Settings.System.getString(context.getContentResolver(), "GUID_uuid");
        OAIDLog.print("Get uuid from system settings: " + string);
        return string;
    }

    @Deprecated
    public static String getWidevineID() {
        try {
            byte[] propertyByteArray = new MediaDrm(new UUID(-1301668207276963122L, -6645017420763422227L)).getPropertyByteArray("deviceUniqueId");
            if (propertyByteArray == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b3 : propertyByteArray) {
                sb.append(String.format("%02x", Byte.valueOf(b3)));
            }
            return sb.toString();
        } catch (Throwable th) {
            OAIDLog.print(th);
            return "";
        }
    }

    public static void register(Application application) {
        register(application, false, null);
    }

    private static void saveUuidToExternalStorage(Context context, String str) throws IOException {
        if (context == null) {
            return;
        }
        File guidFile = getGuidFile(context);
        if (guidFile == null) {
            OAIDLog.print("UUID file in external storage is null");
            return;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(guidFile));
            try {
                if (!guidFile.exists()) {
                    guidFile.createNewFile();
                }
                bufferedWriter.write(str);
                bufferedWriter.flush();
                OAIDLog.print("Save uuid to external storage: " + str);
                bufferedWriter.close();
            } finally {
            }
        } catch (Exception e2) {
            OAIDLog.print(e2);
        }
    }

    private static void saveUuidToSharedPreferences(Context context, String str) {
        if (context == null) {
            return;
        }
        context.getSharedPreferences("GUID", 0).edit().putString(AliyunLogKey.KEY_UUID, str).apply();
        OAIDLog.print("Save uuid to shared preferences: " + str);
    }

    private static void saveUuidToSystemSettings(Context context, String str) {
        if (context == null) {
            return;
        }
        if (!Settings.System.canWrite(context)) {
            OAIDLog.print("android.permission.WRITE_SETTINGS not granted");
            return;
        }
        try {
            Settings.System.putString(context.getContentResolver(), "GUID_uuid", str);
            OAIDLog.print("Save uuid to system settings: " + str);
        } catch (Exception e2) {
            OAIDLog.print(e2);
        }
    }

    public static boolean supportedOAID(Context context) {
        return OAIDFactory.create(context).supported();
    }

    private DeviceID() {
    }

    public static void getOAID(Context context, IGetter iGetter) {
        IOAID ioaidCreate = OAIDFactory.create(context);
        OAIDLog.print("OAID implements class: " + ioaidCreate.getClass().getName());
        ioaidCreate.doGet(iGetter);
    }

    public static void register(Application application, boolean z2) {
        register(application, z2, null);
    }

    public static void register(Application application, IRegisterCallback iRegisterCallback) {
        register(application, false, iRegisterCallback);
    }

    public static void register(Application application, boolean z2, IRegisterCallback iRegisterCallback) {
        if (application == null) {
            if (iRegisterCallback != null) {
                iRegisterCallback.onComplete("", new RuntimeException("application is nulll"));
                return;
            }
            return;
        }
        DeviceID deviceID = Holder.INSTANCE;
        deviceID.application = application;
        deviceID.tryWidevine = z2;
        String uniqueID = getUniqueID(application);
        if (!TextUtils.isEmpty(uniqueID)) {
            deviceID.clientId = uniqueID;
            OAIDLog.print("Client id is IMEI/MEID: " + deviceID.clientId);
            if (iRegisterCallback != null) {
                iRegisterCallback.onComplete(uniqueID, null);
                return;
            }
            return;
        }
        getOAIDOrOtherId(application, z2, iRegisterCallback);
    }
}
