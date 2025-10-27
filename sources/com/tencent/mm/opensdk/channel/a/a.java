package com.tencent.mm.opensdk.channel.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: com.tencent.mm.opensdk.channel.a.a$a, reason: collision with other inner class name */
    public static class C0344a {

        /* renamed from: a, reason: collision with root package name */
        public String f20417a;

        /* renamed from: b, reason: collision with root package name */
        public String f20418b;

        /* renamed from: c, reason: collision with root package name */
        public String f20419c;

        /* renamed from: d, reason: collision with root package name */
        public long f20420d;

        /* renamed from: e, reason: collision with root package name */
        public Bundle f20421e;
    }

    public static int a(Bundle bundle, String str, int i2) {
        if (bundle == null) {
            return i2;
        }
        try {
            return bundle.getInt(str, i2);
        } catch (Exception e2) {
            Log.e("MicroMsg.IntentUtil", "getIntExtra exception:" + e2.getMessage());
            return i2;
        }
    }

    public static Object a(int i2, String str) {
        try {
            switch (i2) {
                case 1:
                    return Integer.valueOf(str);
                case 2:
                    return Long.valueOf(str);
                case 3:
                    return str;
                case 4:
                    return Boolean.valueOf(str);
                case 5:
                    return Float.valueOf(str);
                case 6:
                    return Double.valueOf(str);
                default:
                    Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unknown type");
                    return null;
            }
        } catch (Exception e2) {
            Log.e("MicroMsg.SDK.PluginProvider.Resolver", "resolveObj exception:" + e2.getMessage());
            return null;
        }
    }

    public static String a(Bundle bundle, String str) {
        if (bundle == null) {
            return null;
        }
        try {
            return bundle.getString(str);
        } catch (Exception e2) {
            Log.e("MicroMsg.IntentUtil", "getStringExtra exception:" + e2.getMessage());
            return null;
        }
    }

    public static boolean a(Context context, C0344a c0344a) {
        String str;
        String str2;
        if (context == null || c0344a == null) {
            str = "send fail, invalid argument";
        } else {
            if (!b.b(c0344a.f20418b)) {
                if (b.b(c0344a.f20417a)) {
                    str2 = null;
                } else {
                    str2 = c0344a.f20417a + ".permission.MM_MESSAGE";
                }
                Intent intent = new Intent(c0344a.f20418b);
                Bundle bundle = c0344a.f20421e;
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                String packageName = context.getPackageName();
                intent.putExtra(ConstantsAPI.SDK_VERSION, Build.SDK_INT);
                intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
                intent.putExtra(ConstantsAPI.CONTENT, c0344a.f20419c);
                intent.putExtra(ConstantsAPI.APP_SUPORT_CONTENT_TYPE, c0344a.f20420d);
                intent.putExtra(ConstantsAPI.CHECK_SUM, a(c0344a.f20419c, Build.SDK_INT, packageName));
                context.sendBroadcast(intent, str2);
                Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str2);
                return true;
            }
            str = "send fail, action is null";
        }
        Log.e("MicroMsg.SDK.MMessage", str);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x014f: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:104:0x014f */
    /* JADX WARN: Removed duplicated region for block: B:132:0x015e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x011f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x00ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x00f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0140 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0145 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x014a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0154 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0115 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0159 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:177:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r8v20, types: [java.net.HttpURLConnection, java.net.URLConnection] */
    /* JADX WARN: Type inference failed for: r8v32 */
    /* JADX WARN: Type inference failed for: r8v33 */
    /* JADX WARN: Type inference failed for: r8v34 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(java.lang.String r8, int r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.opensdk.channel.a.a.a(java.lang.String, int):byte[]");
    }

    public static byte[] a(String str, int i2, String str2) throws NoSuchAlgorithmException {
        String str3;
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append(str);
        }
        stringBuffer.append(i2);
        stringBuffer.append(str2);
        stringBuffer.append("mMcShCsTr");
        byte[] bytes = stringBuffer.toString().substring(1, 9).getBytes();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr2 = new char[bArrDigest.length * 2];
            int i3 = 0;
            for (byte b3 : bArrDigest) {
                int i4 = i3 + 1;
                cArr2[i3] = cArr[(b3 >>> 4) & 15];
                i3 = i4 + 1;
                cArr2[i4] = cArr[b3 & 15];
            }
            str3 = new String(cArr2);
        } catch (Exception unused) {
            str3 = null;
        }
        return str3.getBytes();
    }
}
