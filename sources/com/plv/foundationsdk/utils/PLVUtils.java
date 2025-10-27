package com.plv.foundationsdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.luck.picture.lib.config.PictureMimeType;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import retrofit2.HttpException;

/* loaded from: classes4.dex */
public class PLVUtils {
    private static final String TAG = "PLVUtils";
    private static String imei;

    public static String MD5(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b3 : bArrDigest) {
                sb.append(Integer.toHexString((b3 & 255) | 256).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            PLVCommonLog.exception(e2);
            return "";
        }
    }

    private static String getAES(String str, String str2, String str3) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArrDoFinal;
        boolean z2;
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(str3.getBytes());
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            bArrDoFinal = cipher.doFinal(Base64.decode(str, 0));
            z2 = false;
        } catch (Exception e2) {
            e2.printStackTrace();
            bArrDoFinal = null;
            z2 = true;
        }
        if (z2) {
            try {
                Cipher cipher2 = Cipher.getInstance("AES/CBC/NoPadding");
                cipher2.init(2, secretKeySpec, ivParameterSpec);
                bArrDoFinal = cipher2.doFinal(Base64.decode(str, 0));
            } catch (Exception e3) {
                e3.printStackTrace();
                return str;
            }
        }
        if (bArrDoFinal == null) {
            Log.e(TAG, "aes decrypted is null");
            return str;
        }
        if (bArrDoFinal.length == 0) {
            Log.e(TAG, "aes decrypted length 0");
            return str;
        }
        try {
            return new String(bArrDoFinal, "UTF-8");
        } catch (Exception e4) {
            e4.printStackTrace();
            return str;
        }
    }

    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
    }

    public static String getErrorMessage(Throwable th) {
        String message = th.getMessage();
        if (!(th instanceof HttpException)) {
            return message;
        }
        try {
            return ((HttpException) th).response().errorBody().string();
        } catch (Exception unused) {
            return message;
        }
    }

    public static String getExceptionFullMessage(Throwable th) {
        return getExceptionFullMessage(th, 500);
    }

    public static String getExternalFilePath(String str) {
        if (PLVAppUtils.getApp() == null) {
            return "";
        }
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return PLVAppUtils.getApp().getFilesDir().getAbsolutePath();
        }
        File externalFilesDir = PLVAppUtils.getApp().getExternalFilesDir(str);
        return externalFilesDir == null ? PLVAppUtils.getApp().getApplicationContext().getFilesDir().getAbsolutePath() : externalFilesDir.getAbsolutePath();
    }

    public static String getMacAddress() throws IOException {
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address").getInputStream()));
            String line = "";
            while (line != null) {
                line = lineNumberReader.readLine();
                if (line != null) {
                    return line.trim();
                }
            }
            return null;
        } catch (IOException e2) {
            PLVCommonLog.e(TAG, "getMacAddress:" + e2.getMessage());
            return null;
        }
    }

    public static String getMacFromHardware() throws SocketException {
        try {
            Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (it.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface) it.next();
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b3 : hardwareAddress) {
                        sb.append(String.format("%02X:", Byte.valueOf(b3)));
                    }
                    if (!TextUtils.isEmpty(sb)) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, "getMacFromHardware:" + e2.getMessage());
        }
        return "";
    }

    public static String getPhoneIMEI(Context context) {
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        if (Build.VERSION.SDK_INT >= 29) {
            String string = Settings.System.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            PLVCommonLog.e("getPhoneIMEI", "getPhoneIMEI :" + string);
            return string;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.READ_PHONE_STATE) != 0) {
            return TextUtils.isEmpty(imei) ? getWifiMacAddress(context) : imei;
        }
        imei = telephonyManager.getDeviceId();
        PLVCommonLog.d(TAG, "imei :" + imei);
        return imei;
    }

    public static String getPid() throws NoSuchAlgorithmException {
        Random random;
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                random = SecureRandom.getInstanceStrong();
            } catch (NoSuchAlgorithmException unused) {
                random = new Random();
            }
        } else {
            random = new Random();
        }
        return System.currentTimeMillis() + "X" + (random.nextInt(1000000) + 1000000);
    }

    public static String getUrlHost(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new URL(str).getHost();
        } catch (MalformedURLException e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            return str;
        }
    }

    public static String getVerName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            e2.printStackTrace();
            return "";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            e2.printStackTrace();
            return 0;
        }
    }

    public static String getWifiMacAddress(Context context) {
        int i2 = Build.VERSION.SDK_INT;
        String macAddress = i2 < 24 ? getMacAddress() : i2 >= 24 ? getMacFromHardware() : "";
        PLVCommonLog.e("getWifiMacAddress", "getWifiMacAddress:" + macAddress);
        return macAddress;
    }

    public static boolean isImageUrl(String str) {
        return str.endsWith(".jpg") || str.endsWith(PictureMimeType.PNG) || str.endsWith(PictureMimeType.GIF) || str.endsWith(".JPG") || str.endsWith(".PNG") || str.endsWith(".GIF");
    }

    public static boolean isVideoUrl(String str) {
        return str.endsWith(".flv") || str.endsWith(".mp4") || str.endsWith(".FLV") || str.endsWith(".MP4");
    }

    public static String parseEncryptData(String str) {
        return getAES(str, "PolyvApiResponse", "PolyvLiveEncrypt");
    }

    public static boolean validateVideoId(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("^[[a-z]|[0-9]]{32}_[[a-z]|[0-9]]$").matcher(str).matches();
    }

    public static String getExceptionFullMessage(Throwable th, int i2) throws Throwable {
        StringWriter stringWriter;
        if (th == null) {
            return "";
        }
        PrintWriter printWriter = null;
        try {
            stringWriter = new StringWriter();
            try {
                try {
                    PrintWriter printWriter2 = new PrintWriter(stringWriter);
                    try {
                        th.printStackTrace(printWriter2);
                        String string = stringWriter.toString();
                        if (TextUtils.isEmpty(string)) {
                            printWriter2.close();
                            try {
                                stringWriter.close();
                            } catch (IOException e2) {
                                Log.e(TAG, getExceptionFullMessage(e2, -1));
                            }
                            return "";
                        }
                        String strReplaceAll = string.replaceAll("\n", "");
                        if (i2 == -1 || strReplaceAll.length() < i2) {
                            i2 = strReplaceAll.length();
                        }
                        String strSubstring = strReplaceAll.substring(0, i2);
                        printWriter2.close();
                        try {
                            stringWriter.close();
                        } catch (IOException e3) {
                            Log.e(TAG, getExceptionFullMessage(e3, -1));
                        }
                        return strSubstring;
                    } catch (Exception e4) {
                        e = e4;
                        printWriter = printWriter2;
                        PLVCommonLog.e(TAG, e.getMessage());
                        if (printWriter != null) {
                            printWriter.close();
                        }
                        if (stringWriter != null) {
                            try {
                                stringWriter.close();
                            } catch (IOException e5) {
                                Log.e(TAG, getExceptionFullMessage(e5, -1));
                            }
                        }
                        return "";
                    } catch (Throwable th2) {
                        th = th2;
                        printWriter = printWriter2;
                        if (printWriter != null) {
                            printWriter.close();
                        }
                        if (stringWriter != null) {
                            try {
                                stringWriter.close();
                            } catch (IOException e6) {
                                Log.e(TAG, getExceptionFullMessage(e6, -1));
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e7) {
                e = e7;
            }
        } catch (Exception e8) {
            e = e8;
            stringWriter = null;
        } catch (Throwable th4) {
            th = th4;
            stringWriter = null;
        }
    }
}
