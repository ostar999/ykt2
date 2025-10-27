package com.umeng.socialize.b.b;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.DefaultClass;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static b f23640b = new b();

    /* renamed from: a, reason: collision with root package name */
    private String f23641a;

    private b() {
        this.f23641a = "";
        try {
            this.f23641a = ContextUtil.getContext().getCacheDir().getCanonicalPath();
        } catch (IOException e2) {
            SLog.error(e2);
        }
    }

    public static b a() {
        b bVar = f23640b;
        return bVar == null ? new b() : bVar;
    }

    public File b() throws IOException {
        File file = new File(c(), d());
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    public File c() throws IOException {
        String string;
        if (Build.VERSION.SDK_INT >= 29) {
            string = ContextUtil.getContext().getExternalFilesDir(null).getPath();
        } else if (Environment.getExternalStorageDirectory() != null && !TextUtils.isEmpty(Environment.getExternalStorageDirectory().getCanonicalPath())) {
            string = Environment.getExternalStorageDirectory().getCanonicalPath();
        } else if (TextUtils.isEmpty(this.f23641a)) {
            string = DefaultClass.getString();
            SLog.E(UmengText.CACHE.SD_NOT_FOUNT);
        } else {
            string = this.f23641a;
            SLog.E(UmengText.CACHE.SD_NOT_FOUNT);
        }
        File file = new File(string + c.f23647f);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public String d() {
        return SocializeUtils.hexdigest(String.valueOf(System.currentTimeMillis())) + ".jpg";
    }

    public byte[] a(File file) {
        FileInputStream fileInputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
            }
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException e2) {
                    SLog.error(UmengText.IMAGE.CLOSE, e2);
                }
                return byteArray;
            } catch (Throwable th3) {
                th = th3;
                try {
                    SLog.error(UmengText.IMAGE.READ_IMAGE_ERROR, th);
                    if (fileInputStream != null) {
                        try {
                        } catch (IOException e3) {
                            return DefaultClass.getBytes();
                        }
                    }
                    return DefaultClass.getBytes();
                } finally {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e32) {
                            SLog.error(UmengText.IMAGE.CLOSE, e32);
                        }
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }
            }
        } catch (Throwable th4) {
            fileInputStream = null;
            th = th4;
            byteArrayOutputStream = null;
        }
    }
}
