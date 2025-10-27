package com.tencent.liteav.basic.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.MimeTypes;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.a;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.UUID;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public static long f18732a = 0;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f18733b = true;

    /* renamed from: c, reason: collision with root package name */
    private static String f18734c = "";

    /* renamed from: d, reason: collision with root package name */
    private static int f18735d = 0;

    /* renamed from: e, reason: collision with root package name */
    private static long f18736e = 0;

    /* renamed from: f, reason: collision with root package name */
    private static boolean f18737f = false;

    /* renamed from: g, reason: collision with root package name */
    private static String f18738g = "";

    /* renamed from: h, reason: collision with root package name */
    private static String f18739h = null;

    /* renamed from: i, reason: collision with root package name */
    private static String f18740i = "";

    /* renamed from: j, reason: collision with root package name */
    private static a<b> f18741j = new a<>(new a.InterfaceC0331a<b>() { // from class: com.tencent.liteav.basic.util.h.1
        @Override // com.tencent.liteav.basic.util.a.InterfaceC0331a
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public b a() {
            return new b();
        }
    });

    /* renamed from: k, reason: collision with root package name */
    private static final Object f18742k = new Object();

    /* renamed from: l, reason: collision with root package name */
    private static boolean f18743l = false;

    /* renamed from: m, reason: collision with root package name */
    private static int[] f18744m = {96000, 88200, 64000, 48000, 44100, 32000, R2.string.load_cost, R2.layout.item_problem, 16000, R2.drawable.qrcodeimg, R2.drawable.jpush_ic_richpush_actionbar_divider, 8000, R2.dimen.dp_717};

    public static String c(Context context) {
        if (!f18738g.equals("")) {
            return f18738g;
        }
        if (context != null) {
            try {
                f18738g = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
            } catch (Exception unused) {
            }
        }
        return f18738g;
    }

    public static boolean d(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            System.currentTimeMillis();
            if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
                return false;
            }
            return activeNetworkInfo.isAvailable();
        } catch (Exception unused) {
            return false;
        }
    }

    public static int e(Context context) {
        NetworkInfo activeNetworkInfo;
        int i2 = 0;
        if (context == null) {
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        try {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e2) {
            TXCLog.e("TXCSystemUtil", "getNetworkType: error occurred.", e2);
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return 0;
        }
        if (activeNetworkInfo.getType() == 9) {
            return 5;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() == 0) {
            i2 = 2;
            try {
                int networkType = telephonyManager.getNetworkType();
                switch (networkType) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 4;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return 3;
                    case 13:
                        return 2;
                    default:
                        return (TXCBuild.VersionInt() < 29 || networkType != 20) ? 2 : 6;
                }
            } catch (Exception e3) {
                TXCLog.e("TXCSystemUtil", "getNetworkType: error occurred.", e3);
            }
            TXCLog.e("TXCSystemUtil", "getNetworkType: error occurred.", e3);
        }
        return i2;
    }

    public static String f(Context context) {
        return TXCDRApi.getDevUUID(context, TXCDRApi.getSimulateIDFA(context));
    }

    public static int g(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
        } catch (Exception unused) {
            return 0;
        }
    }

    public static int b() {
        if (f18737f || (f18736e != 0 && TXCTimeUtil.getTimeTick() - f18736e < C.DEFAULT_SEEK_FORWARD_INCREMENT_MS)) {
            return f18735d;
        }
        f18737f = true;
        try {
            AsyncTask.execute(new Runnable() { // from class: com.tencent.liteav.basic.util.h.2
                @Override // java.lang.Runnable
                public void run() {
                    System.currentTimeMillis();
                    boolean unused = h.f18737f = false;
                    try {
                        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                        Debug.getMemoryInfo(memoryInfo);
                        int totalPss = memoryInfo.getTotalPss();
                        long unused2 = h.f18736e = TXCTimeUtil.getTimeTick();
                        int unused3 = h.f18735d = totalPss / 1024;
                    } catch (Exception unused4) {
                    }
                }
            });
        } catch (Throwable th) {
            TXCLog.e("TXCSystemUtil", "getAppMemory error : " + th);
        }
        return f18735d;
    }

    public static String f() throws IOException {
        BufferedReader bufferedReader;
        String line;
        if (!TextUtils.isEmpty(f18739h)) {
            return f18739h;
        }
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
        } catch (Throwable unused) {
        }
        do {
            try {
                line = bufferedReader.readLine();
            } catch (Throwable unused2) {
                bufferedReader2 = bufferedReader;
                d.a(bufferedReader2);
                return "";
            }
            if (line == null) {
                d.a(bufferedReader);
                return "";
            }
        } while (!line.contains("Hardware"));
        String str = line.split(" ")[r1.length - 1];
        String upperCase = TextUtils.isEmpty(str) ? "" : str.toUpperCase();
        f18739h = upperCase;
        d.a(bufferedReader);
        return upperCase;
    }

    public static int[] a() {
        if (f18733b) {
            f18733b = false;
            f18741j.a().a();
            return new int[]{0, 0};
        }
        return f18741j.a().a();
    }

    public static boolean d() {
        boolean z2;
        synchronized (f18742k) {
            if (!f18743l) {
                Log.w("Native-LiteAV", "load library txffmpeg " + a("txffmpeg"));
                Log.w("Native-LiteAV", "load library soundtouch " + a("soundtouch"));
                Log.w("Native-LiteAV", "load library traeimp-rtmp " + a("traeimp-rtmp"));
                f18743l = a("liteavsdk");
                Log.w("Native-LiteAV", "load library liteavsdk " + f18743l);
            }
            z2 = f18743l;
        }
        return z2;
    }

    public static String c() {
        return UUID.randomUUID().toString();
    }

    public static boolean a(Context context) {
        ActivityManager activityManager;
        if (context == null) {
            return false;
        }
        try {
            activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        } catch (Exception unused) {
        }
        if (activityManager.getRunningTasks(1) == null) {
            TXCLog.e("TXCSystemUtil", "running task is null, ams is abnormal!!!");
            return false;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(1).get(0);
        if (runningTaskInfo != null && runningTaskInfo.topActivity != null) {
            return !runningTaskInfo.topActivity.getPackageName().equals(context.getPackageName());
        }
        TXCLog.e("TXCSystemUtil", "failed to get RunningTaskInfo");
        return false;
    }

    public static String b(Context context) {
        return TXCDRApi.getSimulateIDFA(context);
    }

    private static boolean b(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Log.w("Native-LiteAV", "load library " + str2 + " from path " + str);
            System.load(str + "/lib" + str2 + ".so");
            return true;
        } catch (Error e2) {
            Log.w("Native-LiteAV", "load library : " + e2.toString());
            return false;
        } catch (Exception e3) {
            Log.w("Native-LiteAV", "load library : " + e3.toString());
            return false;
        }
    }

    public static String e() {
        return f18734c;
    }

    public static void b(String str) {
        Log.w("Native-LiteAV", "setLibraryPath " + str);
        f18734c = str;
    }

    public static String a(Context context, String str) {
        if (!TextUtils.isEmpty(f18740i)) {
            return f18740i;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            f18740i = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 128)).toString();
        } catch (Exception unused) {
            f18740i = "";
        }
        return f18740i;
    }

    public static void a(WeakReference<com.tencent.liteav.basic.b.b> weakReference, String str, int i2, String str2, long j2) {
        Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", str);
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        bundle.putLong(TXLiveConstants.EVT_BLOCK_DURATION, j2);
        if (str2 != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str2);
        }
        a(weakReference, i2, bundle);
    }

    public static void a(WeakReference<com.tencent.liteav.basic.b.b> weakReference, int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        if (str != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        }
        a(weakReference, i2, bundle);
    }

    public static void a(WeakReference<com.tencent.liteav.basic.b.b> weakReference, int i2, Bundle bundle) {
        com.tencent.liteav.basic.b.b bVar;
        if (weakReference == null || (bVar = weakReference.get()) == null) {
            return;
        }
        bVar.onNotifyEvent(i2, bundle);
    }

    public static void a(WeakReference<com.tencent.liteav.basic.b.b> weakReference, String str, int i2, Bundle bundle) {
        com.tencent.liteav.basic.b.b bVar;
        if (weakReference == null || (bVar = weakReference.get()) == null) {
            return;
        }
        bundle.putString("EVT_USERID", str);
        bVar.onNotifyEvent(i2, bundle);
    }

    public static com.tencent.liteav.basic.opengl.a a(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8 = i2 * i5;
        int i9 = i3 * i4;
        if (i8 >= i9) {
            i7 = i9 / i5;
            i6 = i3;
        } else {
            i6 = i8 / i4;
            i7 = i2;
        }
        return new com.tencent.liteav.basic.opengl.a(i2 > i7 ? (i2 - i7) >> 1 : 0, i3 > i6 ? (i3 - i6) >> 1 : 0, i7, i6);
    }

    public static boolean a(String str) {
        try {
            Log.w("Native-LiteAV", "load library " + str + " from system path ");
            System.loadLibrary(str);
            return true;
        } catch (Error e2) {
            Log.w("Native-LiteAV", "load library : " + e2.toString());
            return b(f18734c, str);
        } catch (Exception e3) {
            Log.w("Native-LiteAV", "load library : " + e3.toString());
            return b(f18734c, str);
        }
    }

    public static int a(int i2) {
        int[] iArr;
        int i3 = 0;
        while (true) {
            iArr = f18744m;
            if (i3 >= iArr.length || iArr[i3] == i2) {
                break;
            }
            i3++;
        }
        if (i3 >= iArr.length) {
            return -1;
        }
        return i3;
    }

    @TargetApi(16)
    public static MediaFormat a(int i2, int i3, int i4) {
        int iA = a(i2);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        byteBufferAllocate.put(0, (byte) ((i4 << 3) | (iA >> 1)));
        byteBufferAllocate.put(1, (byte) (((iA & 1) << 7) | (i3 << 3)));
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, i2, i3);
        mediaFormatCreateAudioFormat.setInteger("channel-count", i3);
        mediaFormatCreateAudioFormat.setInteger("sample-rate", i2);
        mediaFormatCreateAudioFormat.setByteBuffer("csd-0", byteBufferAllocate);
        return mediaFormatCreateAudioFormat;
    }

    public static boolean a(String str, String str2) throws Throwable {
        MediaMetadataRetriever mediaMetadataRetriever;
        if (str == null || str2 == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                if (!new File(str).exists()) {
                    return false;
                }
                mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    mediaMetadataRetriever.setDataSource(str);
                    Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime();
                    File file = new File(str2);
                    if (file.exists()) {
                        file.delete();
                    }
                    File parentFile = file.getParentFile();
                    if (parentFile != null && !parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        frameAtTime.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                        fileOutputStream2.flush();
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused) {
                        }
                        mediaMetadataRetriever.release();
                        return true;
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        TXCLog.e("TXCSystemUtil", "get video thumb failed.", e);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        if (mediaMetadataRetriever != null) {
                            mediaMetadataRetriever.release();
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                        if (mediaMetadataRetriever != null) {
                            mediaMetadataRetriever.release();
                            throw th;
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                mediaMetadataRetriever = null;
            } catch (Throwable th2) {
                th = th2;
                mediaMetadataRetriever = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
