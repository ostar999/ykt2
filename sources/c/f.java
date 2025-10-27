package c;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public static f f2247a;

    /* renamed from: b, reason: collision with root package name */
    public static final String f2248b = File.separator;

    public static f a() {
        if (f2247a == null) {
            f2247a = new f();
        }
        return f2247a;
    }

    public boolean b(String str, String str2) throws IOException {
        try {
            File file = new File(str2);
            if (!file.exists() && !file.mkdirs()) {
                Log.e("--Method--", "copyFolder: cannot create directory.");
                return false;
            }
            for (String str3 : new File(str).list()) {
                String str4 = File.separator;
                File file2 = str.endsWith(str4) ? new File(str + str3) : new File(str + str4 + str3);
                if (file2.isDirectory()) {
                    b(str + "/" + str3, str2 + "/" + str3);
                } else {
                    if (!file2.exists()) {
                        Log.e("--Method--", "copyFolder:  oldFile not exist.");
                        return false;
                    }
                    if (!file2.isFile()) {
                        Log.e("--Method--", "copyFolder:  oldFile not file.");
                        return false;
                    }
                    if (!file2.canRead()) {
                        Log.e("--Method--", "copyFolder:  oldFile cannot read.");
                        return false;
                    }
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + file2.getName());
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, i2);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void a(Context context, int i2, String str, String str2) throws Resources.NotFoundException, IOException {
        InputStream inputStreamOpenRawResource = context.getResources().openRawResource(i2);
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        a(str2 + f2248b + str, inputStreamOpenRawResource);
    }

    public static void a(String str, InputStream inputStream) throws IOException {
        File file = new File(str);
        try {
            if (file.exists()) {
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[inputStream.available()];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 != -1) {
                    fileOutputStream.write(bArr, 0, i2);
                } else {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();
                    return;
                }
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public boolean a(String str, String str2) throws IOException {
        try {
            File file = new File(str);
            if (!file.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            }
            if (!file.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            }
            if (!file.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = fileInputStream.read(bArr);
                if (i2 != -1) {
                    fileOutputStream.write(bArr, 0, i2);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
