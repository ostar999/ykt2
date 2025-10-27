package com.tencent.liteav.basic.license;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.open.SocialOperation;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class LicenceCheck {

    /* renamed from: d, reason: collision with root package name */
    private static LicenceCheck f18439d;

    /* renamed from: a, reason: collision with root package name */
    private Context f18440a;

    /* renamed from: e, reason: collision with root package name */
    private a f18443e;

    /* renamed from: b, reason: collision with root package name */
    private String f18441b = "YTFaceSDK.licence";

    /* renamed from: c, reason: collision with root package name */
    private String f18442c = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4teqkW/TUruU89ElNVd\nKrpSL+HCITruyb6BS9mW6M4mqmxDhazDmQgMKNfsA0d2kxFucCsXTyesFNajaisk\nrAzVJpNGO75bQFap4jYzJYskIuas6fgIS7zSmGXgRcp6i0ZBH3pkVCXcgfLfsVCO\n+sN01jFhFgOC0LY2f1pJ+3jqktAlMIxy8Q9t7XwwL5/n8/Sledp7TwuRdnl2OPl3\nycCTRkXtOIoRNB9vgd9XooTKiEdCXC7W9ryvtwCiAB82vEfHWXXgzhsPC13URuFy\n1JqbWJtTCCcfsCVxuBplhVJAQ7JsF5SMntdJDkp7rJLhprgsaim2CRjcVseNmw97\nbwIDAQAB";

    /* renamed from: f, reason: collision with root package name */
    private int f18444f = -1;

    /* renamed from: g, reason: collision with root package name */
    private b f18445g = new b("TXUgcSDK.licence");

    /* renamed from: h, reason: collision with root package name */
    private b f18446h = new b("TXLiveSDK.licence");

    public interface a {
        void a(int i2, String str);
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        String f18449a;

        /* renamed from: b, reason: collision with root package name */
        String f18450b;

        /* renamed from: c, reason: collision with root package name */
        String f18451c = "";

        /* renamed from: d, reason: collision with root package name */
        String f18452d = "";

        /* renamed from: e, reason: collision with root package name */
        String f18453e = "";

        /* renamed from: f, reason: collision with root package name */
        boolean f18454f = false;

        /* renamed from: g, reason: collision with root package name */
        boolean f18455g = false;

        /* renamed from: h, reason: collision with root package name */
        int f18456h = -1;

        /* renamed from: i, reason: collision with root package name */
        String f18457i = "";

        public b(String str) {
            this.f18449a = str;
            this.f18450b = str + ".tmp";
        }
    }

    private LicenceCheck() {
    }

    private int c(b bVar) throws Throwable {
        String strB;
        String str = c() + File.separator + bVar.f18449a;
        if (!com.tencent.liteav.basic.util.d.a(str)) {
            return -7;
        }
        try {
            strB = com.tencent.liteav.basic.util.d.b(str);
        } catch (Exception e2) {
            TXCLog.e("LicenceCheck", "read licence file error: ", e2);
            strB = "";
        }
        if (!TextUtils.isEmpty(strB)) {
            return a(bVar, strB);
        }
        TXCLog.e("LicenceCheck", "checkLocalLicence, licenceStr is empty");
        return -8;
    }

    private int d(b bVar) throws IOException {
        if (!e(bVar)) {
            return -6;
        }
        String strB = com.tencent.liteav.basic.util.d.b(this.f18440a, bVar.f18449a);
        if (!TextUtils.isEmpty(strB)) {
            return a(bVar, strB);
        }
        TXCLog.e("LicenceCheck", "checkAssetLicence, licenceAssetStr is empty");
        return -8;
    }

    private boolean e(b bVar) {
        return com.tencent.liteav.basic.util.d.a(this.f18440a, bVar.f18449a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(b bVar) {
        StringBuilder sb = new StringBuilder();
        sb.append(c());
        String str = File.separator;
        sb.append(str);
        sb.append(bVar.f18449a);
        File file = new File(sb.toString());
        if (file.exists()) {
            TXCLog.i("LicenceCheck", "delete dst file:" + file.delete());
        }
        File file2 = new File(bVar.f18451c + str + bVar.f18450b);
        if (file2.exists()) {
            TXCLog.i("LicenceCheck", "rename file:" + file2.renameTo(file));
        }
        bVar.f18455g = true;
    }

    private int g(b bVar) {
        return bVar.f18456h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String h(b bVar) {
        return com.tencent.liteav.basic.util.d.b(new File(bVar.f18451c + File.separator + bVar.f18450b).getAbsolutePath());
    }

    private native byte[] nativeIvParameterSpec(byte[] bArr);

    private native String nativeRSAKey();

    private int e(b bVar, String str) {
        String strC = c(str);
        if (TextUtils.isEmpty(strC)) {
            TXCLog.e("LicenceCheck", "verifyOldLicence, decryptStr is empty");
            a(-3);
            a aVar = this.f18443e;
            if (aVar != null) {
                aVar.a(-3, "licence check failed!! decryption failed");
            }
            return -3;
        }
        bVar.f18457i = strC;
        try {
            JSONObject jSONObject = new JSONObject(strC);
            if (jSONObject.getString("packagename").equals(a(this.f18440a))) {
                if (!a(jSONObject.getString("startdate"), jSONObject.getString("enddate"))) {
                    return -11;
                }
                bVar.f18456h = 5;
                TXCDRApi.txReportDAU(this.f18440a, com.tencent.liteav.basic.datareport.a.aJ);
                return 0;
            }
            TXCLog.e("LicenceCheck", "packagename not match!");
            a(-4);
            a aVar2 = this.f18443e;
            if (aVar2 != null) {
                aVar2.a(-4, "licence check failed!! packagename error");
            }
            return -4;
        } catch (JSONException e2) {
            e2.printStackTrace();
            TXCLog.e("LicenceCheck", "verifyOldLicence, json format error !");
            a(-1);
            a aVar3 = this.f18443e;
            if (aVar3 != null) {
                aVar3.a(-1, "licence check failed!! json error");
            }
            return -1;
        }
    }

    public static LicenceCheck a() {
        if (f18439d == null) {
            f18439d = new LicenceCheck();
        }
        return f18439d;
    }

    private String b(b bVar) {
        Context context = this.f18440a;
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences("LicenceCheck.lastModified", 0).getString(bVar.f18449a + ".lastModified", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(b bVar, String str) {
        Context context = this.f18440a;
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("LicenceCheck.lastModified", 0).edit();
        editorEdit.putString(bVar.f18449a + ".lastModified", str);
        editorEdit.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int d(b bVar, String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("appId");
            String string = jSONObject.getString("encryptedLicense");
            String string2 = jSONObject.getString(SocialOperation.GAME_SIGNATURE);
            TXCLog.i("LicenceCheck", "appid:" + iOptInt);
            TXCLog.i("LicenceCheck", "encryptedLicense:" + string);
            TXCLog.i("LicenceCheck", "signature:" + string2);
            return a(bVar, string, string2);
        } catch (JSONException e2) {
            e2.printStackTrace();
            a(-1);
            a aVar = this.f18443e;
            if (aVar != null) {
                aVar.a(-1, "licence check failed!! json error");
            }
            return -1;
        }
    }

    public void a(a aVar) {
        this.f18443e = aVar;
    }

    public void a(Context context, String str, String str2) {
        a(this.f18446h, context, str, str2);
    }

    private void a(b bVar, Context context, String str, String str2) {
        if (context != null) {
            this.f18440a = context.getApplicationContext();
            TXCCommonUtil.setAppContext(context);
        }
        bVar.f18452d = str2;
        bVar.f18453e = str;
        if (this.f18440a != null) {
            bVar.f18451c = c();
            if (!com.tencent.liteav.basic.util.d.a(bVar.f18451c + File.separator + bVar.f18449a)) {
                TXCLog.i("LicenceCheck", "setLicense, file not exist, to download");
                b(bVar, "");
            }
            a(bVar);
        }
    }

    private String c(b bVar, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (TextUtils.isEmpty(bVar.f18452d)) {
            TXCLog.e("LicenceCheck", "decodeLicence, mKey is empty!!!");
            return "";
        }
        byte[] bytes = bVar.f18452d.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(nativeIvParameterSpec(bytes));
        byte[] bArrDecode = Base64.decode(str, 0);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            String str2 = new String(cipher.doFinal(bArrDecode), "UTF-8");
            TXCLog.i("LicenceCheck", "decodeLicence : " + str2);
            return str2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static long b(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str).getTime();
        } catch (Exception e2) {
            TXCLog.e("LicenceCheck", "time str to millsecond failed.", e2);
            return -1L;
        }
    }

    public int b() {
        return g(this.f18446h);
    }

    public void a(final b bVar) {
        if (TextUtils.isEmpty(bVar.f18453e)) {
            TXCLog.e("LicenceCheck", "downloadLicense, mUrl is empty, ignore!");
            return;
        }
        if (bVar.f18454f) {
            TXCLog.i("LicenceCheck", "downloadLicense, in downloading, ignore");
            return;
        }
        com.tencent.liteav.basic.license.b bVar2 = new com.tencent.liteav.basic.license.b() { // from class: com.tencent.liteav.basic.license.LicenceCheck.1
            @Override // com.tencent.liteav.basic.license.b
            public void a(File file, String str) {
                if (file == null) {
                    TXCLog.i("LicenceCheck", "downloadLicense, license not modified");
                    return;
                }
                LicenceCheck.this.b(bVar, str);
                TXCLog.i("LicenceCheck", "downloadLicense, onSaveSuccess");
                String strH = LicenceCheck.this.h(bVar);
                if (TextUtils.isEmpty(strH)) {
                    TXCLog.e("LicenceCheck", "downloadLicense, readDownloadTempLicence is empty!");
                    bVar.f18454f = false;
                } else if (LicenceCheck.this.d(bVar, strH) == 0) {
                    LicenceCheck.this.f(bVar);
                }
            }

            @Override // com.tencent.liteav.basic.license.b
            public void a(File file, Exception exc) {
                TXCLog.i("LicenceCheck", "downloadLicense, onSaveFailed");
            }

            @Override // com.tencent.liteav.basic.license.b
            public void a(int i2) {
                TXCLog.i("LicenceCheck", "downloadLicense, onProgressUpdate");
            }

            @Override // com.tencent.liteav.basic.license.b
            public void a() {
                TXCLog.i("LicenceCheck", "downloadLicense, onProcessEnd");
                bVar.f18454f = false;
            }
        };
        if (this.f18440a == null) {
            TXCLog.e("LicenceCheck", "context is NULL !!! Please set context in method:setLicense(Context context, String url, String key)");
            return;
        }
        bVar.f18451c = c();
        new Thread(new c(this.f18440a, bVar.f18453e, bVar.f18451c, bVar.f18450b, bVar2, false, b(bVar))).start();
        bVar.f18454f = true;
    }

    private String c(String str) {
        try {
            return new String(h.b(Base64.decode(str, 0), Base64.decode(nativeRSAKey(), 0)));
        } catch (Exception e2) {
            e2.printStackTrace();
            TXCLog.e("LicenceCheck", "decryptLicenceStr, exception is : " + e2);
            return null;
        }
    }

    public String c() {
        File file = new File(this.f18440a.getFilesDir() + File.separator + "liteav/licence");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public int a(f fVar, Context context) {
        return a(this.f18446h, fVar, context);
    }

    private int a(b bVar, f fVar, Context context) throws Throwable {
        int iA = a(bVar, context);
        if (iA != 0) {
            a(bVar);
        }
        if (fVar != null) {
            fVar.f18469a = bVar.f18457i;
        }
        return iA;
    }

    private int a(b bVar, Context context) throws Throwable {
        if (bVar.f18455g) {
            return 0;
        }
        if (this.f18440a == null) {
            this.f18440a = context;
        }
        if (d(bVar) == 0) {
            bVar.f18455g = true;
            return 0;
        }
        int iC = c(bVar);
        if (iC != 0) {
            return iC;
        }
        bVar.f18455g = true;
        return 0;
    }

    public int a(b bVar, String str) {
        try {
            new JSONObject(str);
            return d(bVar, str);
        } catch (JSONException unused) {
            if (bVar == this.f18446h) {
                return -1;
            }
            return e(bVar, str);
        }
    }

    public PublicKey a(String str) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
    }

    private int a(b bVar, String str, String str2) throws Throwable {
        boolean zA;
        try {
            zA = a(Base64.decode(str, 0), Base64.decode(str2, 0), a(this.f18442c));
        } catch (Exception e2) {
            e2.printStackTrace();
            TXCLog.e("LicenceCheck", "verifyLicence, exception is : " + e2);
            zA = false;
        }
        if (!zA) {
            a(-2);
            a aVar = this.f18443e;
            if (aVar != null) {
                aVar.a(-2, "licence check failed!! verify signature failed");
            }
            TXCLog.e("LicenceCheck", "verifyLicence, signature not pass!");
            return -2;
        }
        String strC = c(bVar, str);
        if (TextUtils.isEmpty(strC)) {
            a(-3);
            a aVar2 = this.f18443e;
            if (aVar2 != null) {
                aVar2.a(-3, "licence check failed!! decryption failed");
            }
            TXCLog.e("LicenceCheck", "verifyLicence, decodeValue is empty!");
            return -3;
        }
        bVar.f18457i = strC;
        try {
            JSONObject jSONObject = new JSONObject(strC);
            TXCLog.i("LicenceCheck", "verifyLicence, object " + jSONObject.toString());
            String string = jSONObject.getString("pituLicense");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("appData");
            if (jSONArrayOptJSONArray == null) {
                TXCLog.e("LicenceCheck", "verifyLicence, appDataArray is null!");
                a(-1);
                a aVar3 = this.f18443e;
                if (aVar3 != null) {
                    aVar3.a(-1, "licence check failed!! json error");
                }
                return -1;
            }
            this.f18444f = -1;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                String strOptString = jSONObject2.optString("packageName");
                if (!strOptString.equals(this.f18440a.getPackageName())) {
                    TXCLog.e("LicenceCheck", "verifyLicence, checkPackageName not match! license packageName: " + strOptString + ", current app packageName: " + this.f18440a.getPackageName());
                } else if (!a(jSONObject2.optString(com.heytap.mcssdk.constant.b.f7194s), jSONObject2.optString(com.heytap.mcssdk.constant.b.f7195t))) {
                    TXCLog.e("LicenceCheck", "verifyLicence, checkDateExpire! appDataJsonObject " + jSONObject2.toString());
                    z2 = true;
                } else if (a(bVar, jSONObject2, string)) {
                    z2 = true;
                    z3 = true;
                    z4 = true;
                } else {
                    TXCLog.e("LicenceCheck", "verifyLicence, checkFeature inValid! appDataJsonObject " + jSONObject2.toString());
                    z2 = true;
                    z3 = true;
                }
            }
            if (!z2) {
                a(-4);
                a aVar4 = this.f18443e;
                if (aVar4 != null) {
                    aVar4.a(-4, "licence check failed!! packagename error");
                }
                return -4;
            }
            if (!z3) {
                a(-11);
                a aVar5 = this.f18443e;
                if (aVar5 != null) {
                    aVar5.a(-11, "licence check failed!! licence expired");
                }
                return -11;
            }
            if (!z4) {
                a(-5);
                a aVar6 = this.f18443e;
                if (aVar6 != null) {
                    aVar6.a(-5, "licence check failed!! feature verification failed");
                }
                return -5;
            }
            if (!TextUtils.isEmpty(string)) {
                try {
                    byte[] bArrDecode = Base64.decode(string, 0);
                    File file = new File(c() + File.separator + this.f18441b);
                    com.tencent.liteav.basic.util.d.a(file.getAbsolutePath(), bArrDecode);
                    TXCCommonUtil.setPituLicencePath(file.getAbsolutePath());
                } catch (Exception e3) {
                    TXCLog.e("LicenceCheck", "decode pitu license error:" + e3);
                }
            }
            TXCDRApi.txReportDAU(this.f18440a, com.tencent.liteav.basic.datareport.a.aJ);
            a aVar7 = this.f18443e;
            if (aVar7 != null) {
                aVar7.a(0, "licence check success!!");
            }
            return 0;
        } catch (JSONException e4) {
            e4.printStackTrace();
            TXCLog.e("LicenceCheck", "verifyLicence, json format error ! exception = " + e4);
            a(-1);
            a aVar8 = this.f18443e;
            if (aVar8 != null) {
                aVar8.a(-1, "licence check failed!! json error");
            }
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(com.tencent.liteav.basic.license.LicenceCheck.b r8, org.json.JSONObject r9, java.lang.String r10) {
        /*
            r7 = this;
            java.lang.String r0 = "feature"
            int r9 = r9.optInt(r0)
            com.tencent.liteav.basic.license.LicenceCheck$b r0 = r7.f18446h
            r1 = 2
            java.lang.String r2 = ", isValid: "
            java.lang.String r3 = "LicenceCheck"
            r4 = 1
            r5 = 0
            if (r8 != r0) goto L37
            int r9 = r9 >> 4
            r9 = r9 & 15
            if (r9 < r4) goto L1a
            if (r9 > r1) goto L1a
            goto L1b
        L1a:
            r4 = r5
        L1b:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "live parseVersionType, featureCode = "
            r10.append(r0)
            r10.append(r9)
            r10.append(r2)
            r10.append(r4)
            java.lang.String r10 = r10.toString()
            com.tencent.liteav.basic.log.TXCLog.i(r3, r10)
        L35:
            r5 = r4
            goto L72
        L37:
            com.tencent.liteav.basic.license.LicenceCheck$b r0 = r7.f18445g
            if (r8 != r0) goto L72
            r0 = r9 & 15
            r6 = 5
            if (r0 > r4) goto L4e
            if (r0 == r4) goto L44
            if (r9 != 0) goto L54
        L44:
            boolean r9 = android.text.TextUtils.isEmpty(r10)
            if (r9 != 0) goto L4b
            goto L4c
        L4b:
            r6 = 3
        L4c:
            r9 = r6
            goto L56
        L4e:
            if (r0 < r1) goto L54
            if (r0 > r6) goto L54
            r9 = r0
            goto L56
        L54:
            r9 = r0
            r4 = r5
        L56:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "ugc parseVersionType, featureCode = "
            r10.append(r0)
            r10.append(r9)
            r10.append(r2)
            r10.append(r4)
            java.lang.String r10 = r10.toString()
            com.tencent.liteav.basic.log.TXCLog.i(r3, r10)
            goto L35
        L72:
            if (r5 == 0) goto L7e
            int r10 = r7.f18444f
            if (r9 <= r10) goto L7a
            r7.f18444f = r9
        L7a:
            int r9 = r7.f18444f
            r8.f18456h = r9
        L7e:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "parseVersionType, finalFeatureCode = "
            r9.append(r10)
            int r8 = r8.f18456h
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            com.tencent.liteav.basic.log.TXCLog.i(r3, r8)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.license.LicenceCheck.a(com.tencent.liteav.basic.license.LicenceCheck$b, org.json.JSONObject, java.lang.String):boolean");
    }

    private void a(int i2) {
        TXCLog.e("LicenceCheck", "onFail ret " + i2);
        TXCDRApi.txReportDAU(this.f18440a, com.tencent.liteav.basic.datareport.a.aK, i2, "");
    }

    private boolean a(String str, String str2) {
        long jB = b(str);
        long jB2 = b(str2);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jB < 0 || jB2 < 0) {
            TXCLog.e("LicenceCheck", "check date millis < 0! stateDate: " + jB + ", endDate: " + jB2);
            return false;
        }
        if (jB2 >= jCurrentTimeMillis && jB <= jCurrentTimeMillis) {
            return true;
        }
        TXCLog.e("LicenceCheck", "check date expire! currentDate: " + jCurrentTimeMillis + ", startDate: " + jB + ", endDate: " + jB2);
        return false;
    }

    private static String a(Context context) {
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    public static boolean a(byte[] bArr, byte[] bArr2, PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initVerify(publicKey);
        signature.update(bArr);
        return signature.verify(bArr2);
    }
}
