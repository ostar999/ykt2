package com.beizi.fusion.g;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.collection.LruCache;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static Context f5200a;

    /* renamed from: e, reason: collision with root package name */
    private static i f5201e;

    /* renamed from: b, reason: collision with root package name */
    private ExecutorService f5202b = Executors.newFixedThreadPool(4);

    /* renamed from: c, reason: collision with root package name */
    private LruCache<String, Bitmap> f5203c = new LruCache<>(4194304);

    /* renamed from: d, reason: collision with root package name */
    private Handler f5204d = new Handler();

    public interface a {
        void a();

        void a(Bitmap bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return a(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static i a(Context context) {
        if (context == null) {
            as.b("Illegal Argument: context is null");
        } else {
            f5200a = context;
        }
        return b();
    }

    private static i b() {
        if (f5201e == null) {
            synchronized (i.class) {
                if (f5201e == null) {
                    f5201e = new i();
                }
            }
        }
        return f5201e;
    }

    public b a(String str) {
        return new b(str);
    }

    public void a(final String str, final a aVar) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Bitmap bitmap = this.f5203c.get(str);
        if (bitmap != null) {
            aVar.a(bitmap);
            return;
        }
        File file = new File(j.b(f5200a), c(str.substring(str.lastIndexOf("/") + 1)));
        Bitmap bitmapDecodeFile = (!file.exists() || file.length() <= 0) ? null : BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmapDecodeFile != null) {
            this.f5203c.put(str, bitmapDecodeFile);
            aVar.a(bitmapDecodeFile);
        } else {
            this.f5202b.submit(new Runnable() { // from class: com.beizi.fusion.g.i.1
                @Override // java.lang.Runnable
                public void run() throws ProtocolException {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setReadTimeout(5000);
                        if (httpURLConnection.getResponseCode() == 200) {
                            final Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                            httpURLConnection.disconnect();
                            i.this.f5204d.post(new Runnable() { // from class: com.beizi.fusion.g.i.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    aVar.a(bitmapDecodeStream);
                                }
                            });
                            i.this.f5203c.put(str, bitmapDecodeStream);
                            String str2 = str;
                            bitmapDecodeStream.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File(j.b(i.f5200a), i.c(str2.substring(str2.lastIndexOf("/") + 1)))));
                        }
                    } catch (Exception unused) {
                        i.this.f5204d.post(new Runnable() { // from class: com.beizi.fusion.g.i.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                aVar.a();
                            }
                        });
                    }
                }
            });
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        String f5211a;

        /* renamed from: b, reason: collision with root package name */
        ImageView f5212b;

        public b(String str) {
            this.f5211a = str;
        }

        private void b() {
            i.this.f5204d.post(new Runnable() { // from class: com.beizi.fusion.g.i.b.2
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }

        public void a(ImageView imageView) {
            this.f5212b = imageView;
            if (TextUtils.isEmpty(this.f5211a)) {
                return;
            }
            Bitmap bitmap = (Bitmap) i.this.f5203c.get(this.f5211a);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                return;
            }
            Bitmap bitmapA = a();
            if (bitmapA == null) {
                i.this.f5202b.submit(this);
            } else {
                imageView.setImageBitmap(bitmapA);
                i.this.f5203c.put(this.f5211a, bitmapA);
            }
        }

        @Override // java.lang.Runnable
        public void run() throws ProtocolException {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.f5211a).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                if (httpURLConnection.getResponseCode() == 200) {
                    final Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    i.this.f5204d.post(new Runnable() { // from class: com.beizi.fusion.g.i.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.f5212b.setImageBitmap(bitmapDecodeStream);
                        }
                    });
                    i.this.f5203c.put(this.f5211a, bitmapDecodeStream);
                    String str = this.f5211a;
                    File file = new File(j.b(i.f5200a), i.c(str.substring(str.lastIndexOf("/") + 1)));
                    ac.a("BeiZis", "BeiZiImageUtils run file == " + file);
                    bitmapDecodeStream.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
                } else {
                    b();
                }
            } catch (FileNotFoundException unused) {
            } catch (Exception e2) {
                e2.printStackTrace();
                b();
            }
        }

        private Bitmap a() {
            String str = this.f5211a;
            File file = new File(j.b(i.f5200a), i.c(str.substring(str.lastIndexOf("/") + 1)));
            ac.a("BeiZis", "BeiZiImageUtils getBitmapFile file == " + file);
            if (!file.exists() || file.length() <= 0) {
                return null;
            }
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            int i2 = (b3 >>> 4) & 15;
            int i3 = 0;
            while (true) {
                sb.append((char) ((i2 < 0 || i2 > 9) ? (i2 - 10) + 97 : i2 + 48));
                i2 = b3 & 15;
                int i4 = i3 + 1;
                if (i3 >= 1) {
                    break;
                }
                i3 = i4;
            }
        }
        return sb.toString();
    }
}
