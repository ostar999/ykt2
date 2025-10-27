package com.arialyy.aria.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.FtpUrlEntity;
import com.azhon.appupdate.config.Constant;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import dalvik.system.DexFile;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class CommonUtil {
    public static final String SERVER_CHARSET = "ISO-8859-1";
    private static final String TAG = "CommonUtil";
    private static long lastClickTime;
    private static List<String> mFragmentClassName = new ArrayList();
    private static List<String> mDialogFragmentClassName = new ArrayList();

    static {
        mFragmentClassName.add("androidx.fragment.app.Fragment");
        mFragmentClassName.add("androidx.fragment.app.DialogFragment");
        mFragmentClassName.add("android.app.Fragment");
        mFragmentClassName.add("android.app.DialogFragment");
        mFragmentClassName.add("androidx.fragment.app.Fragment");
        mFragmentClassName.add("androidx.fragment.app.DialogFragment");
        mDialogFragmentClassName.add("androidx.fragment.app.DialogFragment");
        mDialogFragmentClassName.add("android.app.DialogFragment");
        mDialogFragmentClassName.add("androidx.fragment.app.DialogFragment");
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("0x");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        char[] cArr = new char[2];
        for (byte b3 : bArr) {
            cArr[0] = Character.forDigit((b3 >>> 4) & 15, 16);
            cArr[1] = Character.forDigit(b3 & 15, 16);
            sb.append(cArr);
        }
        return sb.toString();
    }

    public static boolean checkMD5(String str, File file) {
        if (TextUtils.isEmpty(str) || file == null) {
            ALog.e(TAG, "MD5 string empty or updateFile null");
            return false;
        }
        String fileMD5 = getFileMD5(file);
        if (fileMD5 != null) {
            return fileMD5.equalsIgnoreCase(str);
        }
        ALog.e(TAG, "calculatedDigest null");
        return false;
    }

    public static boolean checkSqlExpression(String... strArr) {
        if (strArr.length == 0) {
            ALog.e(TAG, "sql语句表达式不能为null");
            return false;
        }
        int i2 = 0;
        while (Pattern.compile("\\?").matcher(strArr[0]).find()) {
            i2++;
        }
        if (i2 < strArr.length - 1) {
            ALog.e(TAG, String.format("条件语句的?个数不能小于参数个数，参数信息：%s", Arrays.toString(strArr)));
            return false;
        }
        if (i2 <= strArr.length - 1) {
            return true;
        }
        ALog.e(TAG, String.format("条件语句的?个数不能大于参数个数， 参数信息：%s", Arrays.toString(strArr)));
        return false;
    }

    public static String convertFtpChar(String str, String str2) throws UnsupportedEncodingException {
        return new String(str2.getBytes(str), "ISO-8859-1");
    }

    public static String convertSFtpChar(String str, String str2) throws UnsupportedEncodingException {
        return new String(str2.getBytes(), str);
    }

    public static String convertUrl(String str) {
        String string = Uri.parse(str).toString();
        if (hasDoubleCharacter(string)) {
            string = string.replaceAll(" ", "%20");
            Matcher matcher = Pattern.compile(Regular.REG_DOUBLE_CHAR_AND_SPACE).matcher(string);
            HashSet<String> hashSet = new HashSet();
            while (matcher.find()) {
                hashSet.add(matcher.group());
            }
            try {
                for (String str2 : hashSet) {
                    string = string.replaceAll(str2, URLEncoder.encode(str2, "UTF-8"));
                }
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return string;
    }

    public static Intent createIntent(String str, String str2) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(str);
        Uri uriBuild = builder.build();
        Intent intent = new Intent(str2);
        intent.setData(uriBuild);
        return intent;
    }

    public static String decryptBASE64(String str) {
        return new String(Base64.decode(str.getBytes(), 0));
    }

    public static String encryptBASE64(String str) {
        return Base64.encodeToString(str.getBytes(), 0);
    }

    public static String formatFileSize(double d3) {
        if (d3 < 0.0d) {
            return "0kb";
        }
        double d4 = d3 / 1024.0d;
        if (d4 < 1.0d) {
            return d3 + "b";
        }
        double d5 = d4 / 1024.0d;
        if (d5 < 1.0d) {
            return new BigDecimal(Double.toString(d4)).setScale(2, 4).toPlainString() + "kb";
        }
        double d6 = d5 / 1024.0d;
        if (d6 < 1.0d) {
            return new BigDecimal(Double.toString(d5)).setScale(2, 4).toPlainString() + "mb";
        }
        double d7 = d6 / 1024.0d;
        if (d7 < 1.0d) {
            return new BigDecimal(Double.toString(d6)).setScale(2, 4).toPlainString() + "gb";
        }
        return new BigDecimal(d7).setScale(2, 4).toPlainString() + TtmlNode.VERTICAL;
    }

    public static String formatTime(int i2) {
        return i2 <= 0 ? "00:00" : i2 < 60 ? String.format(Locale.getDefault(), "00:%02d", Integer.valueOf(i2 % 60)) : i2 < 3600 ? String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)) : i2 < 86400 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(i2 / 3600), Integer.valueOf((i2 % 3600) / 60), Integer.valueOf(i2 % 60)) : i2 < 604800 ? String.format(Locale.getDefault(), "%dd %02d:%02d", Integer.valueOf(i2 / 86400), Integer.valueOf((i2 % 86400) / 3600), Integer.valueOf(i2 % 3600)) : "∞";
    }

    public static List<Field> getAllFields(Class cls) {
        ArrayList<Field> arrayList = new ArrayList();
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            Class superclass2 = superclass.getSuperclass();
            if (superclass2 != null) {
                Collections.addAll(arrayList, superclass2.getDeclaredFields());
            }
            Collections.addAll(arrayList, superclass.getDeclaredFields());
        }
        Collections.addAll(arrayList, cls.getDeclaredFields());
        ArrayList arrayList2 = new ArrayList();
        for (Field field : arrayList) {
            if (field.getName().equals(AriaConfig.IGNORE_CLASS_KLASS) || field.getName().equals(AriaConfig.IGNORE_CLASS_MONITOR)) {
                arrayList2.add(field);
            }
        }
        if (!arrayList2.isEmpty()) {
            arrayList.removeAll(arrayList2);
        }
        return arrayList;
    }

    public static String getAppPath(Context context) {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return null;
        }
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            return externalFilesDir.getPath() + "/";
        }
        return Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName() + "/files/";
    }

    public static String getClassName(Object obj) {
        return obj.getClass().getName().split("\\.")[r1.length - 1];
    }

    public static int getCoresNum() {
        try {
            File[] fileArrListFiles = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() { // from class: com.arialyy.aria.util.CommonUtil.1CpuFilter
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]", file.getName());
                }
            });
            ALog.d(TAG, "CPU Count: " + fileArrListFiles.length);
            return fileArrListFiles.length;
        } catch (Exception e2) {
            ALog.d(TAG, "CPU Count: Failed.");
            e2.printStackTrace();
            return 1;
        }
    }

    public static Field getField(Class cls, String str) throws NoSuchFieldException, SecurityException {
        Field field;
        try {
            try {
                field = cls.getDeclaredField(str);
            } catch (NoSuchFieldException unused) {
                field = cls.getField(str);
            }
        } catch (NoSuchFieldException unused2) {
            if (cls.getSuperclass() == null) {
                return null;
            }
            field = getField(cls.getSuperclass(), str);
        }
        if (field != null) {
            field.setAccessible(true);
        }
        return field;
    }

    public static Field[] getFields(Class cls) {
        Class superclass;
        Field[] declaredFields = cls.getDeclaredFields();
        return (declaredFields.length != 0 || (superclass = cls.getSuperclass()) == null) ? declaredFields : getFields(superclass);
    }

    public static String getFileConfigPath(boolean z2, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(AriaConfig.getInstance().getAPP().getFilesDir().getPath());
        sb.append(z2 ? AriaConfig.DOWNLOAD_TEMP_DIR : AriaConfig.UPLOAD_TEMP_DIR);
        sb.append(str);
        sb.append(".properties");
        return sb.toString();
    }

    public static String getFileMD5(File file) {
        try {
            return getFileMD5(new FileInputStream(file));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Activity getFragmentActivity(Object obj) {
        try {
            return (Activity) obj.getClass().getMethod("getActivity", new Class[0]).invoke(obj, new Object[0]);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static FtpUrlEntity getFtpUrlInfo(String str) {
        Uri uri = Uri.parse(str);
        String userInfo = uri.getUserInfo();
        String path = uri.getPath();
        ALog.d(TAG, String.format("scheme = %s, user = %s, host = %s, port = %s, path = %s", uri.getScheme(), userInfo, uri.getHost(), Integer.valueOf(uri.getPort()), path));
        FtpUrlEntity ftpUrlEntity = new FtpUrlEntity();
        ftpUrlEntity.url = str;
        ftpUrlEntity.hostName = uri.getHost();
        ftpUrlEntity.port = uri.getPort() == -1 ? "21" : String.valueOf(uri.getPort());
        if (!TextUtils.isEmpty(userInfo)) {
            String[] strArrSplit = userInfo.split(":");
            if (strArrSplit.length == 2) {
                ftpUrlEntity.user = strArrSplit[0];
                ftpUrlEntity.password = strArrSplit[1];
            } else {
                ftpUrlEntity.user = userInfo;
            }
        }
        ftpUrlEntity.scheme = uri.getScheme();
        if (TextUtils.isEmpty(path)) {
            path = "/";
        }
        ftpUrlEntity.remotePath = path;
        return ftpUrlEntity;
    }

    public static Class getListParamType(Field field) {
        if (!field.getType().isAssignableFrom(List.class)) {
            ALog.d(TAG, "字段类型不是List");
            return null;
        }
        Type genericType = field.getGenericType();
        if (genericType == null) {
            ALog.d(TAG, "该字段没有泛型参数");
            return null;
        }
        if (genericType instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
        }
        return null;
    }

    public static Class[] getMapParamType(Field field) {
        if (!field.getType().isAssignableFrom(Map.class)) {
            ALog.d(TAG, "字段类型不是Map");
            return null;
        }
        Type genericType = field.getGenericType();
        if (genericType == null) {
            ALog.d(TAG, "该字段没有泛型参数");
            return null;
        }
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        return new Class[]{(Class) actualTypeArguments[0], (Class) actualTypeArguments[1]};
    }

    public static String getMd5Code(List<String> list) throws NoSuchAlgorithmException {
        if (list == null || list.size() < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(sb.toString().getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e2) {
            ALog.e(TAG, e2.getMessage());
            return "";
        }
    }

    public static Method getMethod(Class cls, String str, Class<?>... clsArr) throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            try {
                method = cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException unused) {
                method = cls.getMethod(str, clsArr);
            }
        } catch (NoSuchMethodException unused2) {
            if (cls.getSuperclass() == null) {
                return null;
            }
            method = getMethod(cls.getSuperclass(), str, clsArr);
        }
        if (method != null) {
            method.setAccessible(true);
        }
        return method;
    }

    public static List<String> getPkgClassName(String str, String str2) throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (!new File(str).exists()) {
            ALog.w(TAG, String.format("路径【%s】下的Dex文件不存在", str));
            return arrayList;
        }
        DexFile dexFile = new DexFile(str);
        Enumeration<String> enumerationEntries = dexFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            String strNextElement = enumerationEntries.nextElement();
            if (strNextElement.contains(str2) && strNextElement.contains(str2)) {
                arrayList.add(strNextElement);
            }
        }
        dexFile.close();
        return arrayList;
    }

    public static List<String> getPkgClassNames(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        String packageCodePath = context.getPackageCodePath();
        File parentFile = new File(packageCodePath).getParentFile();
        if (parentFile.list() == null) {
            arrayList.addAll(getPkgClassName(packageCodePath, str));
        } else {
            String path = parentFile.getPath();
            for (String str2 : parentFile.list()) {
                String str3 = path + "/" + str2;
                if (str3.endsWith(Constant.APK_SUFFIX)) {
                    arrayList.addAll(getPkgClassName(str3, str));
                }
            }
        }
        return arrayList;
    }

    public static String getStrMd5(String str) throws NoSuchAlgorithmException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String getString(String str, Context context, String str2) {
        return context.getSharedPreferences(str, 0).getString(str2, "");
    }

    public static String getTargetName(Object obj) {
        if (!isLocalOrAnonymousClass(obj.getClass())) {
            return obj.getClass().getName();
        }
        Log.w(TAG, String.format("%s 是匿名内部类或局部类，将使用其主类的对象", obj.getClass().getName()));
        String name = obj.getClass().getName();
        return name.substring(0, name.lastIndexOf("$"));
    }

    public static String getThreadName(String str, int i2) {
        return getStrMd5(str.concat(String.valueOf(i2)));
    }

    public static String getWindowReplaceUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, "拦截数据为null");
            return null;
        }
        Matcher matcher = Pattern.compile(Regular.REG_WINLOD_REPLACE).matcher(str);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group().substring(9, r2.length() - 2);
    }

    public static boolean hasDoubleCharacter(String str) {
        for (char c3 : str.toCharArray()) {
            if ((c3 >= 913 && c3 <= 65509) || c3 == '\r' || c3 == '\n' || c3 == ' ') {
                return true;
            }
        }
        return false;
    }

    public static boolean isDialogFragment(Class cls) {
        Class superclass = cls.getSuperclass();
        if (superclass == null) {
            return false;
        }
        if (mDialogFragmentClassName.contains(superclass.getName())) {
            return true;
        }
        return isDialogFragment(superclass);
    }

    public static boolean isFastDoubleClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = jCurrentTimeMillis - lastClickTime;
        if (0 >= j2 || j2 >= 500) {
            lastClickTime = jCurrentTimeMillis;
            return false;
        }
        ALog.i(TAG, "操作太频繁了，缓一下吧～");
        return true;
    }

    public static boolean isFragment(Class cls) {
        Class superclass = cls.getSuperclass();
        if (superclass == null) {
            return false;
        }
        if (mFragmentClassName.contains(superclass.getName())) {
            return true;
        }
        return isFragment(superclass);
    }

    public static boolean isLocalOrAnonymousClass(Class cls) {
        return cls.isLocalClass() || cls.isAnonymousClass();
    }

    public static int keyToHashCode(String str) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '-') {
                cCharAt = 28;
            }
            if (cCharAt == '\'') {
                cCharAt = 29;
            }
            i2 = (i2 * 33) + (cCharAt & 31);
        }
        return i2;
    }

    public static String keyToHashKey(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return String.valueOf(str.hashCode());
        }
    }

    public static Boolean putString(String str, Context context, String str2, String str3) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
        editorEdit.putString(str2, str3);
        return Boolean.valueOf(editorEdit.commit());
    }

    public static String strCharSetConvert(String str, String str2) {
        try {
            return new String(str.getBytes(), str2);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getClassName(Class cls) {
        return cls.getName().split("\\.")[r1.length - 1];
    }

    public static String getFileMD5(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bArr = new byte[8192];
            while (true) {
                try {
                    try {
                        int i2 = inputStream.read(bArr);
                        if (i2 <= 0) {
                            break;
                        }
                        messageDigest.update(bArr, 0, i2);
                    } catch (IOException e2) {
                        throw new RuntimeException("Unable to process file for MD5", e2);
                    }
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            }
            return String.format("%32s", new BigInteger(1, messageDigest.digest()).toString(16)).replace(' ', '0');
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static boolean checkMD5(String str, InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        if (!TextUtils.isEmpty(str) && inputStream != null) {
            String fileMD5 = getFileMD5(inputStream);
            if (fileMD5 == null) {
                ALog.e(TAG, "calculatedDigest null");
                return false;
            }
            return fileMD5.equalsIgnoreCase(str);
        }
        ALog.e(TAG, "MD5 string empty or updateFile null");
        return false;
    }
}
