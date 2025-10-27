package com.meizu.cloud.pushsdk.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: b, reason: collision with root package name */
    private static a f8917b;

    /* renamed from: c, reason: collision with root package name */
    private static final Object f8918c = new Object();

    /* renamed from: d, reason: collision with root package name */
    private byte[] f8920d;

    /* renamed from: e, reason: collision with root package name */
    private byte[] f8921e;

    /* renamed from: f, reason: collision with root package name */
    private byte[] f8922f;

    /* renamed from: g, reason: collision with root package name */
    private byte[] f8923g;

    /* renamed from: h, reason: collision with root package name */
    private byte[] f8924h;

    /* renamed from: i, reason: collision with root package name */
    private PublicKey f8925i;

    /* renamed from: j, reason: collision with root package name */
    private SharedPreferences f8926j;

    /* renamed from: k, reason: collision with root package name */
    private SharedPreferences f8927k;

    /* renamed from: m, reason: collision with root package name */
    private Context f8929m;

    /* renamed from: l, reason: collision with root package name */
    private long f8928l = 0;

    /* renamed from: a, reason: collision with root package name */
    String f8919a = "88&*5a9*4&a122ek";

    private a(Context context) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NumberFormatException, InvalidKeyException {
        this.f8929m = context;
        this.f8926j = context.getSharedPreferences("com.x.y.1", 0);
        this.f8927k = context.getSharedPreferences("com.x.y.2", 0);
        Integer.parseInt(this.f8926j.getString("keyTimeout", "0"));
        this.f8926j.getLong("createDate", 0L);
        e();
        byte[] bArr = this.f8920d;
        if (bArr != null && (bArr == null || bArr.length != 0)) {
            byte[] bArr2 = this.f8921e;
            if (bArr2 == null || (bArr2 != null && bArr2.length == 0)) {
                PublicKey publicKeyB = b(this.f8929m);
                this.f8925i = publicKeyB;
                if (publicKeyB != null) {
                    h();
                    return;
                }
                return;
            }
            return;
        }
        PublicKey publicKeyB2 = b(this.f8929m);
        this.f8925i = publicKeyB2;
        if (publicKeyB2 != null) {
            f();
            return;
        }
        this.f8926j.edit().clear().apply();
        try {
            d();
            PublicKey publicKeyB3 = b(this.f8929m);
            this.f8925i = publicKeyB3;
            if (publicKeyB3 != null) {
                f();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static a a() {
        a aVar = f8917b;
        if (aVar != null) {
            return aVar;
        }
        throw new IllegalStateException("KeyMgr is not initialised - invoke at least once with parameterised init/get");
    }

    private String a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                try {
                    int i2 = inputStream.read();
                    if (i2 == -1) {
                        String string = byteArrayOutputStream.toString();
                        try {
                            byteArrayOutputStream.close();
                            return string;
                        } catch (IOException unused) {
                            return string;
                        }
                    }
                    byteArrayOutputStream.write(i2);
                } catch (IOException unused2) {
                    byteArrayOutputStream.close();
                    return null;
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException unused3) {
                    }
                    throw th;
                }
            } catch (IOException unused4) {
                return null;
            }
        }
    }

    public static void a(Context context) {
        if (f8917b == null) {
            synchronized (f8918c) {
                if (f8917b == null) {
                    f8917b = new a(context);
                }
            }
        }
    }

    private PublicKey b(Context context) {
        b("load publicKey from preference");
        String string = this.f8927k.getString("publicKey", "");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(string, 2)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void b(String str) {
        DebugLogger.d("HttpKeyMgr", str);
    }

    private void c(String str) {
        DebugLogger.e("HttpKeyMgr", str);
    }

    private void d() throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://norma-external-collect.meizu.com/android/exchange/getpublickey.do").openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            try {
                httpURLConnection.setRequestMethod("GET");
            } catch (ProtocolException e2) {
                e2.printStackTrace();
            }
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            InputStream inputStream = null;
            try {
                b("code = " + httpURLConnection.getResponseCode());
                inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    String strA = a(inputStream);
                    b("body = " + strA);
                    if (!TextUtils.isEmpty(strA)) {
                        try {
                            JSONObject jSONObject = new JSONObject(strA);
                            if (jSONObject.getInt("code") == 200) {
                                String string = jSONObject.getString("value");
                                SharedPreferences.Editor editorEdit = this.f8927k.edit();
                                editorEdit.putString("publicKey", string);
                                editorEdit.apply();
                            }
                        } catch (Exception e3) {
                            c("downloadPublicKey message error " + e3.getMessage());
                        }
                    }
                }
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                    }
                }
                httpURLConnection.disconnect();
            }
        } catch (MalformedURLException unused2) {
        }
    }

    private void e() {
        b("loadKeys");
        String string = this.f8926j.getString("sKey64", "");
        b("saved sKey64: " + string);
        if (!TextUtils.isEmpty(string)) {
            this.f8924h = string.getBytes();
        }
        String string2 = this.f8926j.getString("aKey64", "");
        b("saved aKey64: " + string2);
        if (!TextUtils.isEmpty(string2)) {
            byte[] bytes = string2.getBytes();
            this.f8923g = bytes;
            this.f8921e = Base64.decode(bytes, 2);
        }
        String string3 = this.f8926j.getString("rKey64", "");
        b("saved rKey64: " + string3);
        if (TextUtils.isEmpty(string3)) {
            return;
        }
        byte[] bytes2 = string3.getBytes();
        this.f8922f = bytes2;
        this.f8920d = Base64.decode(bytes2, 2);
        b("saved rKey: " + new String(this.f8920d));
    }

    private void f() throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        g();
        h();
    }

    private void g() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            byte[] encoded = keyGenerator.generateKey().getEncoded();
            this.f8920d = encoded;
            this.f8922f = Base64.encode(encoded, 2);
            b("***** rKey64: " + new String(this.f8922f));
            SharedPreferences.Editor editorEdit = this.f8926j.edit();
            editorEdit.putString("rKey64", new String(this.f8922f));
            editorEdit.apply();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void h() throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, this.f8925i);
            byte[] bArrDoFinal = cipher.doFinal(this.f8920d);
            this.f8921e = bArrDoFinal;
            this.f8923g = Base64.encode(bArrDoFinal, 2);
            b("***** aKey64: " + new String(this.f8923g));
            SharedPreferences.Editor editorEdit = this.f8926j.edit();
            editorEdit.putString("aKey64", new String(this.f8923g));
            editorEdit.apply();
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str) {
        this.f8924h = str.getBytes();
        SharedPreferences.Editor editorEdit = this.f8926j.edit();
        editorEdit.putString("sKey64", new String(this.f8924h));
        editorEdit.apply();
    }

    public byte[] a(byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        String str;
        byte[] bArr2 = this.f8920d;
        if (bArr2 == null || (bArr2 != null && bArr2.length == 0)) {
            str = "rKey null!";
        } else {
            if (bArr != null && bArr.length != 0) {
                b(">>>>>>>>>> encrypt input >>>>>>>>>>\n" + new String(Base64.encode(bArr, 2)));
                b("<<<<<<<<<< encrypt input <<<<<<<<<<");
                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(1, new SecretKeySpec(this.f8920d, "AES"), new IvParameterSpec(this.f8920d));
                    byte[] bArrDoFinal = cipher.doFinal(bArr);
                    b(">>>>>>>>>> encrypt output >>>>>>>>>>\n" + new String(Base64.encode(bArrDoFinal, 2)));
                    b("<<<<<<<<<< encrypt output <<<<<<<<<<");
                    return bArrDoFinal;
                } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
            str = "input null!";
        }
        c(str);
        return null;
    }

    public byte[] b() {
        return this.f8923g;
    }

    public byte[] c() {
        return this.f8924h;
    }
}
