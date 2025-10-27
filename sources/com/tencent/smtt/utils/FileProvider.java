package com.tencent.smtt.utils;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.tencent.smtt.sdk.QbSdk;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class FileProvider extends ContentProvider {

    /* renamed from: a, reason: collision with root package name */
    private static final String[] f21416a = {"_display_name", "_size"};

    /* renamed from: b, reason: collision with root package name */
    private static final File f21417b = new File("/");

    /* renamed from: c, reason: collision with root package name */
    private static HashMap<String, a> f21418c = new HashMap<>();

    /* renamed from: d, reason: collision with root package name */
    private a f21419d;

    public interface a {
        Uri a(File file);

        File a(Uri uri);
    }

    public static class b implements a {

        /* renamed from: a, reason: collision with root package name */
        private final String f21420a;

        /* renamed from: b, reason: collision with root package name */
        private final HashMap<String, File> f21421b = new HashMap<>();

        public b(String str) {
            this.f21420a = str;
        }

        @Override // com.tencent.smtt.utils.FileProvider.a
        public Uri a(File file) throws IOException {
            try {
                String canonicalPath = file.getCanonicalPath();
                Map.Entry<String, File> entry = null;
                for (Map.Entry<String, File> entry2 : this.f21421b.entrySet()) {
                    String path = entry2.getValue().getPath();
                    if (canonicalPath.startsWith(path) && (entry == null || path.length() > entry.getValue().getPath().length())) {
                        entry = entry2;
                    }
                }
                if (entry == null) {
                    throw new IllegalArgumentException("Failed to find configured root that contains " + canonicalPath);
                }
                String path2 = entry.getValue().getPath();
                boolean zEndsWith = path2.endsWith("/");
                int length = path2.length();
                if (!zEndsWith) {
                    length++;
                }
                return new Uri.Builder().scheme("content").authority(this.f21420a).encodedPath(Uri.encode(entry.getKey()) + '/' + Uri.encode(canonicalPath.substring(length), "/")).build();
            } catch (IOException unused) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
            }
        }

        @Override // com.tencent.smtt.utils.FileProvider.a
        public File a(Uri uri) throws IOException {
            String encodedPath = uri.getEncodedPath();
            int iIndexOf = encodedPath.indexOf(47, 1);
            String strDecode = Uri.decode(encodedPath.substring(1, iIndexOf));
            String strDecode2 = Uri.decode(encodedPath.substring(iIndexOf + 1));
            File file = this.f21421b.get(strDecode);
            if (file == null) {
                throw new IllegalArgumentException("Unable to find configured root for " + uri);
            }
            File file2 = new File(file, strDecode2);
            try {
                File canonicalFile = file2.getCanonicalFile();
                if (canonicalFile.getPath().startsWith(file.getPath())) {
                    return canonicalFile;
                }
                throw new SecurityException("Resolved path jumped beyond configured root");
            } catch (IOException unused) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file2);
            }
        }

        public void a(String str, File file) throws IOException {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                this.f21421b.put(str, file.getCanonicalFile());
            } catch (IOException e2) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file, e2);
            }
        }
    }

    private static int a(String str) {
        if ("r".equals(str)) {
            return 268435456;
        }
        if ("w".equals(str) || "wt".equals(str)) {
            return 738197504;
        }
        if ("wa".equals(str)) {
            return 704643072;
        }
        if (InternalZipConstants.WRITE_MODE.equals(str)) {
            return 939524096;
        }
        if ("rwt".equals(str)) {
            return 1006632960;
        }
        throw new IllegalArgumentException("Invalid mode: " + str);
    }

    public static Uri a(Context context, File file) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String str;
        if (Build.VERSION.SDK_INT < 24) {
            return null;
        }
        try {
            str = context.getPackageManager().getProviderInfo(new ComponentName(context.getPackageName(), "androidx.core.content.FileProvider"), 0).authority;
        } catch (Exception e2) {
            e2.printStackTrace();
            str = "";
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Method declaredMethod = Class.forName("androidx.core.content.FileProvider").getDeclaredMethod("getUriForFile", Context.class, String.class, File.class);
            if (declaredMethod == null) {
                return null;
            }
            Object objInvoke = declaredMethod.invoke(null, context, str, file);
            if (objInvoke instanceof Uri) {
                return (Uri) objInvoke;
            }
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static Uri a(Context context, String str) {
        Uri uriA = null;
        if (context != null && context.getApplicationContext() != null && "com.tencent.mobileqq".equals(context.getApplicationContext().getApplicationInfo().packageName)) {
            try {
                return (Uri) j.a(Class.forName("com.tencent.mobileqq.utils.kapalaiadapter.FileProvider7Helper"), "getUriForFile", (Class<?>[]) new Class[]{Context.class, File.class}, context, new File(str));
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        if (context != null && context.getApplicationInfo().targetSdkVersion >= 24 && Build.VERSION.SDK_INT >= 24 && (uriA = a(context, new File(str))) == null && QbSdk.checkContentProviderPrivilage(context)) {
            uriA = a(context, context.getApplicationInfo().packageName + ".provider", new File(str));
        }
        if (uriA != null) {
            return uriA;
        }
        try {
            return Uri.fromFile(new File(str));
        } catch (Exception e3) {
            e3.printStackTrace();
            Log.e("FileProvider", "create uri failed,please check again");
            return uriA;
        }
    }

    public static Uri a(Context context, String str, File file) {
        return b(context, str).a(file);
    }

    private static File a(File file, String... strArr) {
        for (String str : strArr) {
            if (str != null) {
                file = new File(file, str);
            }
        }
        return file;
    }

    private static Object[] a(Object[] objArr, int i2) {
        Object[] objArr2 = new Object[i2];
        System.arraycopy(objArr, 0, objArr2, 0, i2);
        return objArr2;
    }

    private static String[] a(String[] strArr, int i2) {
        String[] strArr2 = new String[i2];
        System.arraycopy(strArr, 0, strArr2, 0, i2);
        return strArr2;
    }

    private static a b(Context context, String str) {
        a aVarC;
        synchronized (f21418c) {
            aVarC = f21418c.get(str);
            if (aVarC == null) {
                try {
                    aVarC = c(context, str);
                    f21418c.put(str, aVarC);
                } catch (IOException e2) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                } catch (XmlPullParserException e3) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e3);
                }
            }
        }
        return aVarC;
    }

    private static a c(Context context, String str) throws XmlPullParserException, IOException {
        b bVar = new b(str);
        ProviderInfo providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider(str, 128);
        if (providerInfoResolveContentProvider == null) {
            throw new RuntimeException("Must declare com.tencent.smtt.utils.FileProvider in AndroidManifest above Android 7.0,please view document in x5.tencent.com");
        }
        XmlResourceParser xmlResourceParserLoadXmlMetaData = providerInfoResolveContentProvider.loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
        if (xmlResourceParserLoadXmlMetaData == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        while (true) {
            int next = xmlResourceParserLoadXmlMetaData.next();
            if (next == 1) {
                return bVar;
            }
            if (next == 2) {
                String name = xmlResourceParserLoadXmlMetaData.getName();
                File fileA = null;
                String attributeValue = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "name");
                String attributeValue2 = xmlResourceParserLoadXmlMetaData.getAttributeValue(null, "path");
                if ("root-path".equals(name)) {
                    fileA = a(f21417b, attributeValue2);
                } else if ("files-path".equals(name)) {
                    fileA = a(context.getFilesDir(), attributeValue2);
                } else if ("cache-path".equals(name)) {
                    fileA = a(context.getCacheDir(), attributeValue2);
                } else if ("external-path".equals(name)) {
                    if (context.getApplicationInfo().packageName.contains("com.tencent.mobileqq")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(Environment.getExternalStorageDirectory());
                        String str2 = File.separator;
                        sb.append(str2);
                        sb.append("Android");
                        sb.append(str2);
                        sb.append("data");
                        sb.append(str2);
                        sb.append(context.getApplicationInfo().packageName);
                        fileA = a(new File(sb.toString()), attributeValue2);
                    } else {
                        fileA = a(Environment.getExternalStorageDirectory(), attributeValue2);
                    }
                }
                if (fileA != null) {
                    bVar.a(attributeValue, fileA);
                }
            }
        }
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        this.f21419d = b(context, providerInfo.authority);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return this.f21419d.a(uri).delete() ? 1 : 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        File fileA = this.f21419d.a(uri);
        int iLastIndexOf = fileA.getName().lastIndexOf(46);
        if (iLastIndexOf < 0) {
            return "application/octet-stream";
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileA.getName().substring(iLastIndexOf + 1));
        return mimeTypeFromExtension != null ? mimeTypeFromExtension : "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        return ParcelFileDescriptor.open(this.f21419d.a(uri), a(str));
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int i2;
        File fileA = this.f21419d.a(uri);
        if (strArr == null) {
            strArr = f21416a;
        }
        String[] strArr3 = new String[strArr.length];
        Object[] objArr = new Object[strArr.length];
        int i3 = 0;
        for (String str3 : strArr) {
            if ("_display_name".equals(str3)) {
                strArr3[i3] = "_display_name";
                i2 = i3 + 1;
                objArr[i3] = fileA.getName();
            } else if ("_size".equals(str3)) {
                strArr3[i3] = "_size";
                i2 = i3 + 1;
                objArr[i3] = Long.valueOf(fileA.length());
            }
            i3 = i2;
        }
        String[] strArrA = a(strArr3, i3);
        Object[] objArrA = a(objArr, i3);
        MatrixCursor matrixCursor = new MatrixCursor(strArrA, 1);
        matrixCursor.addRow(objArrA);
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("No external updates");
    }
}
