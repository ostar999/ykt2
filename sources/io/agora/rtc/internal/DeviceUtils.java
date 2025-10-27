package io.agora.rtc.internal;

import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import com.umeng.analytics.pro.am;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes8.dex */
public class DeviceUtils {
    public static final int DEVICE_INFO_UNKNOWN = -1;
    private static final String TAG = "DeviceUtils";
    private static final String[] H264_HW_BLACKLIST = {"SAMSUNG-SGH-I337", "Nexus 7", "Nexus 4", "P6-C00", "HM 2A", "XT105", "XT109", "XT1060"};
    private static final FileFilter CPU_FILTER = new FileFilter() { // from class: io.agora.rtc.internal.DeviceUtils.1
        @Override // java.io.FileFilter
        public boolean accept(File pathname) {
            String name = pathname.getName();
            if (!name.startsWith(am.f22460w)) {
                return false;
            }
            for (int i2 = 3; i2 < name.length(); i2++) {
                if (!Character.isDigit(name.charAt(i2))) {
                    return false;
                }
            }
            return true;
        }
    };

    private static int extractValue(byte[] buffer, int index) {
        byte b3;
        while (index < buffer.length && (b3 = buffer[index]) != 10) {
            if (Character.isDigit(b3)) {
                int i2 = index + 1;
                while (i2 < buffer.length && Character.isDigit(buffer[i2])) {
                    i2++;
                }
                return Integer.parseInt(new String(buffer, 0, index, i2 - index));
            }
            index++;
        }
        return -1;
    }

    public static int getCPUMaxFreqKHz() throws IOException {
        int iIntValue = -1;
        for (int i2 = 0; i2 < getNumberOfCPUCores(); i2++) {
            try {
                File file = new File("/sys/devices/system/cpu/cpu" + i2 + "/cpufreq/cpuinfo_max_freq");
                if (file.exists()) {
                    byte[] bArr = new byte[128];
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        fileInputStream.read(bArr);
                        int i3 = 0;
                        while (Character.isDigit(bArr[i3]) && i3 < 128) {
                            i3++;
                        }
                        Integer numValueOf = Integer.valueOf(Integer.parseInt(new String(bArr, 0, i3)));
                        if (numValueOf.intValue() > iIntValue) {
                            iIntValue = numValueOf.intValue();
                        }
                    } catch (NumberFormatException unused) {
                    } catch (Throwable th) {
                        fileInputStream.close();
                        throw th;
                    }
                    fileInputStream.close();
                }
            } catch (IOException unused2) {
                return -1;
            }
        }
        if (iIntValue == -1) {
            FileInputStream fileInputStream2 = new FileInputStream("/proc/cpuinfo");
            try {
                int fileForValue = parseFileForValue("cpu MHz", fileInputStream2) * 1000;
                if (fileForValue > iIntValue) {
                    iIntValue = fileForValue;
                }
                fileInputStream2.close();
            } catch (Throwable th2) {
                fileInputStream2.close();
                throw th2;
            }
        }
        return iIntValue;
    }

    private static int getCoresFromCPUFileList() {
        return new File("/sys/devices/system/cpu").listFiles(CPU_FILTER).length;
    }

    private static int getCoresFromFileInfo(String fileLocation) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(fileLocation);
            try {
                String line = new BufferedReader(new InputStreamReader(fileInputStream2)).readLine();
                fileInputStream2.close();
                int coresFromFileString = getCoresFromFileString(line);
                try {
                    fileInputStream2.close();
                } catch (IOException e2) {
                    Logging.e(TAG, "close file stream", e2);
                }
                return coresFromFileString;
            } catch (IOException unused) {
                fileInputStream = fileInputStream2;
                if (fileInputStream == null) {
                    return -1;
                }
                try {
                    fileInputStream.close();
                    return -1;
                } catch (IOException e3) {
                    Logging.e(TAG, "close file stream", e3);
                    return -1;
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        Logging.e(TAG, "close file stream", e4);
                    }
                }
                throw th;
            }
        } catch (IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static int getCoresFromFileString(String str) {
        if (str == null || !str.matches("0-[\\d]+$")) {
            return -1;
        }
        return Integer.valueOf(str.substring(2)).intValue() + 1;
    }

    public static String getCpuABI() {
        return Build.CPU_ABI;
    }

    public static String getCpuName() throws Throwable {
        FileReader fileReader;
        Reader reader = null;
        try {
            try {
                try {
                    fileReader = new FileReader("/proc/cpuinfo");
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileReader = null;
                } catch (IOException e3) {
                    e = e3;
                    fileReader = null;
                } catch (Throwable th) {
                    th = th;
                    if (0 != 0) {
                        try {
                            reader.close();
                        } catch (IOException e4) {
                            Logging.e(TAG, "failed to close proc file", e4);
                        }
                    }
                    throw th;
                }
                try {
                    String[] strArrSplit = new BufferedReader(fileReader).readLine().split(":\\s+", 2);
                    for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                    }
                    fileReader.close();
                    String str = strArrSplit[1];
                    try {
                        fileReader.close();
                    } catch (IOException e5) {
                        Logging.e(TAG, "failed to close proc file", e5);
                    }
                    return str;
                } catch (FileNotFoundException e6) {
                    e = e6;
                    Logging.e(TAG, "getCpuName failed, no /proc/cpuinfo found in system", e);
                    if (fileReader != null) {
                        fileReader.close();
                    }
                    return null;
                } catch (IOException e7) {
                    e = e7;
                    Logging.e(TAG, "getCpuName failed,", e);
                    if (fileReader != null) {
                        fileReader.close();
                    }
                    return null;
                }
            } catch (IOException e8) {
                Logging.e(TAG, "failed to close proc file", e8);
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getDeviceId() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append("/");
        sb.append(Build.MODEL);
        sb.append("/");
        sb.append(Build.PRODUCT);
        sb.append("/");
        String str = Build.DEVICE;
        sb.append(str);
        sb.append("/");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("/");
        sb.append(System.getProperty("os.version"));
        String string = sb.toString();
        if (string != null) {
            string = string.toLowerCase();
        }
        Matcher matcher = Pattern.compile(".*[A-Z][A-M][0-9]$").matcher(Build.ID);
        if (Build.BRAND.toLowerCase().equals("samsung") && str.toLowerCase().startsWith("cs02")) {
            matcher.find();
        }
        return string;
    }

    public static String getDeviceInfo() {
        String str = Build.MANUFACTURER + "/" + Build.MODEL;
        return str != null ? str.toLowerCase() : str;
    }

    public static int getNumberOfCPUCores() {
        try {
            int coresFromFileInfo = getCoresFromFileInfo("/sys/devices/system/cpu/possible");
            if (coresFromFileInfo == -1) {
                coresFromFileInfo = getCoresFromFileInfo("/sys/devices/system/cpu/present");
            }
            return coresFromFileInfo == -1 ? getCoresFromCPUFileList() : coresFromFileInfo;
        } catch (NullPointerException | SecurityException unused) {
            return -1;
        }
    }

    public static int getNumberOfCameras() {
        try {
            return Camera.getNumberOfCameras();
        } catch (Exception e2) {
            Log.e(TAG, e2.toString());
            return 0;
        }
    }

    public static int getRecommendedEncoderType() {
        List listAsList = Arrays.asList(H264_HW_BLACKLIST);
        String str = Build.MODEL;
        if (!listAsList.contains(str)) {
            return 0;
        }
        Logging.w(TAG, "Model: " + str + " has black listed H.264 encoder.");
        return 1;
    }

    public static String getSystemInfo() {
        return "Android/" + Build.VERSION.RELEASE;
    }

    private static int parseFileForValue(String textToMatch, FileInputStream stream) throws IOException {
        byte[] bArr = new byte[1024];
        try {
            int i2 = stream.read(bArr);
            int i3 = 0;
            while (i3 < i2) {
                byte b3 = bArr[i3];
                if (b3 == 10 || i3 == 0) {
                    if (b3 == 10) {
                        i3++;
                    }
                    for (int i4 = i3; i4 < i2; i4++) {
                        int i5 = i4 - i3;
                        if (bArr[i4] != textToMatch.charAt(i5)) {
                            break;
                        }
                        if (i5 == textToMatch.length() - 1) {
                            return extractValue(bArr, i4);
                        }
                    }
                }
                i3++;
            }
            return -1;
        } catch (IOException | NumberFormatException unused) {
            return -1;
        }
    }

    public static int selectFrontCamera() {
        try {
            return Camera.getNumberOfCameras() > 1 ? 1 : 0;
        } catch (Exception e2) {
            Log.e(TAG, e2.toString());
            return 0;
        }
    }
}
