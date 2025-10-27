package com.umeng.commonsdk.statistics.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes6.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    private static d f23324a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Context f23325b = null;

    /* renamed from: c, reason: collision with root package name */
    private static String f23326c = null;

    /* renamed from: e, reason: collision with root package name */
    private static final String f23327e = "mobclick_agent_user_";

    /* renamed from: f, reason: collision with root package name */
    private static final String f23328f = "mobclick_agent_header_";

    /* renamed from: g, reason: collision with root package name */
    private static final String f23329g = "mobclick_agent_cached_";

    /* renamed from: d, reason: collision with root package name */
    private a f23330d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private final int f23331a;

        /* renamed from: b, reason: collision with root package name */
        private File f23332b;

        /* renamed from: c, reason: collision with root package name */
        private FilenameFilter f23333c;

        public a(Context context) {
            this(context, ".um");
        }

        public boolean a() {
            File[] fileArrListFiles = this.f23332b.listFiles();
            return fileArrListFiles != null && fileArrListFiles.length > 0;
        }

        public void b() {
            File[] fileArrListFiles = this.f23332b.listFiles(this.f23333c);
            if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                return;
            }
            for (File file : fileArrListFiles) {
                file.delete();
            }
        }

        public int c() {
            File[] fileArrListFiles = this.f23332b.listFiles(this.f23333c);
            if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                return 0;
            }
            return fileArrListFiles.length;
        }

        public a(Context context, String str) {
            this.f23331a = 10;
            this.f23333c = new FilenameFilter() { // from class: com.umeng.commonsdk.statistics.common.d.a.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str2) {
                    return str2.startsWith("um");
                }
            };
            File file = new File(context.getFilesDir(), str);
            this.f23332b = file;
            if (file.exists() && this.f23332b.isDirectory()) {
                return;
            }
            this.f23332b.mkdir();
        }

        public void a(b bVar) {
            File file;
            File[] fileArrListFiles = this.f23332b.listFiles(this.f23333c);
            if (fileArrListFiles != null && fileArrListFiles.length >= 10) {
                Arrays.sort(fileArrListFiles);
                int length = fileArrListFiles.length - 10;
                for (int i2 = 0; i2 < length; i2++) {
                    fileArrListFiles[i2].delete();
                }
            }
            if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                return;
            }
            bVar.a(this.f23332b);
            int length2 = fileArrListFiles.length;
            for (int i3 = 0; i3 < length2; i3++) {
                try {
                } catch (Throwable unused) {
                    file = fileArrListFiles[i3];
                }
                if (bVar.b(fileArrListFiles[i3])) {
                    file = fileArrListFiles[i3];
                    file.delete();
                }
            }
            bVar.c(this.f23332b);
        }

        public void a(byte[] bArr) {
            if (bArr == null || bArr.length == 0) {
                return;
            }
            try {
                HelperUtils.writeFile(new File(this.f23332b, String.format(Locale.US, "um_cache_%d.env", Long.valueOf(System.currentTimeMillis()))), bArr);
            } catch (Exception unused) {
            }
        }
    }

    public interface b {
        void a(File file);

        boolean b(File file);

        void c(File file);
    }

    public d(Context context) {
        this.f23330d = new a(context);
    }

    public static synchronized d a(Context context) {
        f23325b = context.getApplicationContext();
        f23326c = context.getPackageName();
        if (f23324a == null) {
            f23324a = new d(context);
        }
        return f23324a;
    }

    private SharedPreferences f() {
        return f23325b.getSharedPreferences(f23327e + f23326c, 0);
    }

    public String b() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f23325b);
        if (sharedPreferences != null) {
            return sharedPreferences.getString("st", null);
        }
        return null;
    }

    public boolean c() {
        return UMFrUtils.envelopeFileNumber(f23325b) > 0;
    }

    public String[] d() {
        try {
            SharedPreferences sharedPreferencesF = f();
            String string = sharedPreferencesF.getString("au_p", null);
            String string2 = sharedPreferencesF.getString("au_u", null);
            if (string != null && string2 != null) {
                return new String[]{string, string2};
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public void e() {
        f().edit().remove("au_p").remove("au_u").commit();
    }

    public void a(int i2) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f23325b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt("vt", i2).commit();
        }
    }

    public int a() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f23325b);
        if (sharedPreferences != null) {
            return sharedPreferences.getInt("vt", 0);
        }
        return 0;
    }

    public void a(String str) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(f23325b);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("st", str).commit();
        }
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        SharedPreferences.Editor editorEdit = f().edit();
        editorEdit.putString("au_p", str);
        editorEdit.putString("au_u", str2);
        editorEdit.commit();
    }
}
