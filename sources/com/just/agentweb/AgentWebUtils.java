package com.just.agentweb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.loader.content.CursorLoader;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.text.StrPool;
import com.google.android.material.snackbar.Snackbar;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class AgentWebUtils {
    private static final String TAG = "AgentWebUtils";
    private static Handler mHandler;
    private static Toast mToast;
    private static WeakReference<Snackbar> snackbarWeakReference;

    private AgentWebUtils() {
        throw new UnsupportedOperationException("u can't init me");
    }

    public static boolean checkNetwork(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static int checkNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return 0;
        }
        int type = activeNetworkInfo.getType();
        if (type != 0) {
            return (type == 1 || type == 6 || type == 9) ? 1 : 0;
        }
        int subtype = activeNetworkInfo.getSubtype();
        switch (subtype) {
            case 1:
            case 2:
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            default:
                switch (subtype) {
                }
        }
        return 0;
    }

    public static boolean checkWifi(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }

    public static void clearAgentWebCache(Context context) {
        try {
            clearCacheFolder(new File(getAgentWebFilePath(context)), 0);
        } catch (Throwable th) {
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
        }
    }

    public static void clearCache(Context context, int i2) {
        Log.i("Info", String.format("Starting cache prune, deleting files older than %d days", Integer.valueOf(i2)));
        Log.i("Info", String.format("Cache pruning completed, %d files deleted", Integer.valueOf(clearCacheFolder(context.getCacheDir(), i2))));
    }

    public static int clearCacheFolder(File file, int i2) {
        int iClearCacheFolder;
        if (file != null) {
            Log.i("Info", "dir:" + file.getAbsolutePath());
        }
        if (file == null || !file.isDirectory()) {
            return 0;
        }
        try {
            iClearCacheFolder = 0;
            for (File file2 : file.listFiles()) {
                try {
                    if (file2.isDirectory()) {
                        iClearCacheFolder += clearCacheFolder(file2, i2);
                    }
                    if (file2.lastModified() < new Date().getTime() - (i2 * 86400000)) {
                        Log.i(TAG, "file name:" + file2.getName());
                        if (file2.delete()) {
                            iClearCacheFolder++;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.e("Info", String.format("Failed to clean the cache, result %s", e.getMessage()));
                    return iClearCacheFolder;
                }
            }
        } catch (Exception e3) {
            e = e3;
            iClearCacheFolder = 0;
        }
        return iClearCacheFolder;
    }

    public static final void clearWebView(WebView webView) {
        if (webView != null && Looper.myLooper() == Looper.getMainLooper()) {
            webView.loadUrl("about:blank");
            webView.stopLoading();
            if (webView.getHandler() != null) {
                webView.getHandler().removeCallbacksAndMessages(null);
            }
            webView.removeAllViews();
            ViewGroup viewGroup = (ViewGroup) webView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(webView);
            }
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.setTag(null);
            webView.clearHistory();
            webView.destroy();
        }
    }

    public static void clearWebViewAllCache(Context context) {
        try {
            clearWebViewAllCache(context, new LollipopFixedWebView(context.getApplicationContext()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void clearWebViewAllCache(Context context, WebView webView) {
        try {
            AgentWebConfig.removeAllCookies(null);
            webView.getSettings().setCacheMode(2);
            context.deleteDatabase("webviewCache.db");
            context.deleteDatabase("webview.db");
            webView.clearCache(true);
            webView.clearHistory();
            webView.clearFormData();
            clearCacheFolder(new File(AgentWebConfig.getCachePath(context)), 0);
        } catch (Exception e2) {
            if (AgentWebConfig.DEBUG) {
                e2.printStackTrace();
            }
        }
    }

    public static void closeIO(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static File createFileByName(Context context, String str, boolean z2) throws IOException {
        String agentWebFilePath = getAgentWebFilePath(context);
        if (TextUtils.isEmpty(agentWebFilePath)) {
            return null;
        }
        File file = new File(agentWebFilePath, str);
        if (!file.exists()) {
            file.createNewFile();
        } else if (z2) {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    public static File createImageFile(Context context) {
        try {
            return createFileByName(context, String.format("aw_%s.jpg", new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date())), true);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static File createVideoFile(Context context) {
        try {
            return createFileByName(context, String.format("aw_%s.mp4", new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date())), true);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static void dismiss() {
        WeakReference<Snackbar> weakReference = snackbarWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        snackbarWeakReference.get().dismiss();
        snackbarWeakReference = null;
    }

    public static int dp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String getAgentWebFilePath(Context context) {
        if (!TextUtils.isEmpty(AgentWebConfig.AGENTWEB_FILE_PATH)) {
            return AgentWebConfig.AGENTWEB_FILE_PATH;
        }
        File file = new File(getDiskExternalCacheDir(context), "agentweb-cache");
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Throwable unused) {
            LogUtils.i(TAG, "create dir exception");
        }
        LogUtils.i(TAG, "path:" + file.getAbsolutePath() + "  path:" + file.getPath());
        String absolutePath = file.getAbsolutePath();
        AgentWebConfig.AGENTWEB_FILE_PATH = absolutePath;
        return absolutePath;
    }

    public static AbsAgentWebUIController getAgentWebUIControllerByWebView(WebView webView) {
        return getWebParentLayoutByWebView(webView).provide();
    }

    public static String getApplicationName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            packageManager = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    public static long getAvailableStorage() {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().toString());
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } catch (RuntimeException unused) {
            return 0L;
        }
    }

    public static Intent getCommonFileIntentCompat(Context context, File file) {
        Intent action = new Intent().setAction("android.intent.action.VIEW");
        setIntentDataAndType(context, action, getMIMEType(file), file, false);
        return action;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) throws Throwable {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        cursorQuery.close();
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static List<String> getDeniedPermissions(Activity activity, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (!hasPermission(activity, strArr[i2])) {
                arrayList.add(strArr[i2]);
            }
        }
        return arrayList;
    }

    public static String getDiskExternalCacheDir(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if ("mounted".equals(EnvironmentCompat.getStorageState(externalCacheDir))) {
            return externalCacheDir.getAbsolutePath();
        }
        return null;
    }

    @TargetApi(19)
    public static String getFileAbsolutePath(Activity activity, Uri uri) {
        Uri uri2 = null;
        if (activity != null && uri != null) {
            if (!DocumentsContract.isDocumentUri(activity, uri)) {
                if (uri.getAuthority().equalsIgnoreCase(activity.getPackageName() + ".AgentWebFileProvider")) {
                    String path = uri.getPath();
                    return getAgentWebFilePath(activity) + File.separator + path.substring(path.lastIndexOf("/") + 1, path.length());
                }
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                    return isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(activity, uri, null, null);
                }
                if ("file".equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
            } else if (isExternalStorageDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    return getDataColumn(activity, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
                }
                if (isMediaDocument(uri)) {
                    String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = strArrSplit2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(activity, uri2, "_id=?", new String[]{strArrSplit2[1]});
                }
            }
        }
        return null;
    }

    public static Intent getInstallApkIntentCompat(Context context, File file) {
        Intent action = new Intent().setAction("android.intent.action.VIEW");
        setIntentDataAndType(context, action, "application/vnd.android.package-archive", file, false);
        return action;
    }

    public static Intent getIntentCaptureCompat(Context context, File file) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriFromFile = getUriFromFile(context, file);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("output", uriFromFile);
        return intent;
    }

    public static Intent getIntentVideoCompat(Context context, File file) {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        Uri uriFromFile = getUriFromFile(context, file);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("output", uriFromFile);
        return intent;
    }

    private static String getMIMEType(File file) {
        String name = file.getName();
        String lowerCase = name.substring(name.lastIndexOf(StrPool.DOT) + 1, name.length()).toLowerCase();
        return lowerCase.equals("pdf") ? MimeTypes.APP_PDF : (lowerCase.equals("m4a") || lowerCase.equals("mp3") || lowerCase.equals("mid") || lowerCase.equals("xmf") || lowerCase.equals("ogg") || lowerCase.equals("wav")) ? MimeTypes.AUDIO_ALL : (lowerCase.equals("3gp") || lowerCase.equals("mp4")) ? MimeTypes.VIDEO_ALL : (lowerCase.equals("jpg") || lowerCase.equals(ImgUtil.IMAGE_TYPE_GIF) || lowerCase.equals("png") || lowerCase.equals("jpeg") || lowerCase.equals(ImgUtil.IMAGE_TYPE_BMP)) ? MimeTypes.IMAGE_ALL : lowerCase.equals("apk") ? "application/vnd.android.package-archive" : (lowerCase.equals("pptx") || lowerCase.equals("ppt")) ? "application/vnd.ms-powerpoint" : (lowerCase.equals("docx") || lowerCase.equals("doc")) ? "application/vnd.ms-word" : (lowerCase.equals("xlsx") || lowerCase.equals("xls")) ? MimeTypes.APP_EXCEL : MimeTypes.ANY_TYPE;
    }

    private static String getRealPathBelowVersion(Context context, Uri uri) {
        String string;
        LogUtils.i(TAG, "method -> getRealPathBelowVersion " + uri + "   path:" + uri.getPath() + "    getAuthority:" + uri.getAuthority());
        String[] strArr = {"_data"};
        Cursor cursorLoadInBackground = new CursorLoader(context, uri, strArr, null, null, null).loadInBackground();
        if (cursorLoadInBackground != null) {
            cursorLoadInBackground.moveToFirst();
            string = cursorLoadInBackground.getString(cursorLoadInBackground.getColumnIndex(strArr[0]));
            cursorLoadInBackground.close();
        } else {
            string = null;
        }
        return string == null ? uri.getPath() : string;
    }

    @Deprecated
    public static void getUIControllerAndShowMessage(Activity activity, String str, String str2) {
        AbsAgentWebUIController absAgentWebUIControllerProvide;
        if (activity == null || activity.isFinishing() || (absAgentWebUIControllerProvide = ((WebParentLayout) activity.findViewById(R.id.web_parent_layout_id)).provide()) == null) {
            return;
        }
        absAgentWebUIControllerProvide.onShowMessage(str, str2);
    }

    public static Uri getUriFromFile(Context context, File file) {
        return Build.VERSION.SDK_INT >= 24 ? getUriFromFileForN(context, file) : Uri.fromFile(file);
    }

    public static Uri getUriFromFileForN(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".AgentWebFileProvider", file);
    }

    public static WebParentLayout getWebParentLayoutByWebView(WebView webView) {
        if (!(webView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("please check webcreator's create method was be called ?");
        }
        ViewParent parent = webView.getParent();
        while (true) {
            for (ViewGroup viewGroup = (ViewGroup) parent; viewGroup != null; viewGroup = null) {
                String str = TAG;
                LogUtils.i(str, "ViewGroup:" + viewGroup);
                if (viewGroup.getId() == R.id.web_parent_layout_id) {
                    WebParentLayout webParentLayout = (WebParentLayout) viewGroup;
                    LogUtils.i(str, "found WebParentLayout");
                    return webParentLayout;
                }
                parent = viewGroup.getParent();
                if (parent instanceof ViewGroup) {
                    break;
                }
            }
            throw new IllegalStateException("please check webcreator's create method was be called ?");
        }
    }

    public static void grantPermissions(Context context, Intent intent, Uri uri, boolean z2) {
        int i2 = z2 ? 3 : 1;
        intent.addFlags(i2);
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentActivities(intent, 65536).iterator();
        while (it.hasNext()) {
            context.grantUriPermission(it.next().activityInfo.packageName, uri, i2);
        }
    }

    public static boolean hasPermission(@NonNull Context context, @NonNull List<String> list) {
        for (String str : list) {
            if (ContextCompat.checkSelfPermission(context, str) == -1) {
                return false;
            }
            String strPermissionToOp = AppOpsManagerCompat.permissionToOp(str);
            if (!TextUtils.isEmpty(strPermissionToOp) && AppOpsManagerCompat.noteProxyOp(context, strPermissionToOp, context.getPackageName()) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPermission(@NonNull Context context, @NonNull String... strArr) {
        return hasPermission(context, (List<String>) Arrays.asList(strArr));
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isEmptyCollection(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmptyMap(Map map) {
        return map == null || map.isEmpty();
    }

    public static Method isExistMethod(Object obj, String str, Class... clsArr) {
        if (obj == null) {
            return null;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (str.startsWith(StrPool.BRACKET_START)) {
                new JSONArray(str);
            } else {
                new JSONObject(str);
            }
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isOverriedMethod(Object obj, String str, String str2, Class... clsArr) {
        LogUtils.i(TAG, "  methodName:" + str + "   method:" + str2);
        boolean z2 = false;
        if (obj == null) {
            return false;
        }
        try {
            z2 = !obj.getClass().getMethod(str, clsArr).toGenericString().contains(str2);
        } catch (Exception e2) {
            if (LogUtils.isDebug()) {
                e2.printStackTrace();
            }
        }
        LogUtils.i(TAG, "isOverriedMethod:" + z2);
        return z2;
    }

    public static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String md5(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (Exception e2) {
            if (!LogUtils.isDebug()) {
                return "";
            }
            e2.printStackTrace();
            return "";
        }
    }

    public static void runInUiThread(Runnable runnable) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(runnable);
    }

    public static void setIntentData(Context context, Intent intent, File file, boolean z2) {
        if (Build.VERSION.SDK_INT < 24) {
            intent.setData(Uri.fromFile(file));
            return;
        }
        intent.setData(getUriFromFile(context, file));
        intent.addFlags(1);
        if (z2) {
            intent.addFlags(2);
        }
    }

    public static void setIntentDataAndType(Context context, Intent intent, String str, File file, boolean z2) {
        if (Build.VERSION.SDK_INT < 24) {
            intent.setDataAndType(Uri.fromFile(file), str);
            return;
        }
        intent.setDataAndType(getUriFromFile(context, file), str);
        intent.addFlags(1);
        if (z2) {
            intent.addFlags(2);
        }
    }

    public static void show(View view, CharSequence charSequence, int i2, @ColorInt int i3, @ColorInt int i4, CharSequence charSequence2, @ColorInt int i5, View.OnClickListener onClickListener) {
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ForegroundColorSpan(i3), 0, spannableString.length(), 33);
        WeakReference<Snackbar> weakReference = new WeakReference<>(Snackbar.make(view, spannableString, i2));
        snackbarWeakReference = weakReference;
        Snackbar snackbar = weakReference.get();
        snackbar.getView().setBackgroundColor(i4);
        if (charSequence2 != null && charSequence2.length() > 0 && onClickListener != null) {
            snackbar.setActionTextColor(i5);
            snackbar.setAction(charSequence2, onClickListener);
        }
        snackbar.show();
    }

    public static boolean showFileChooserCompat(Activity activity, WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams, PermissionInterceptor permissionInterceptor, ValueCallback valueCallback2, String str, Handler.Callback callback) {
        try {
            Object objInvoke = Class.forName("com.just.agentweb.filechooser.FileChooser").getDeclaredMethod("newBuilder", Activity.class, WebView.class).invoke(null, activity, webView);
            Class<?> cls = objInvoke.getClass();
            if (valueCallback != null) {
                Method declaredMethod = cls.getDeclaredMethod("setUriValueCallbacks", ValueCallback.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(objInvoke, valueCallback);
            }
            if (fileChooserParams != null) {
                Method declaredMethod2 = cls.getDeclaredMethod("setFileChooserParams", WebChromeClient.FileChooserParams.class);
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(objInvoke, fileChooserParams);
            }
            if (valueCallback2 != null) {
                Method declaredMethod3 = cls.getDeclaredMethod("setUriValueCallback", ValueCallback.class);
                declaredMethod3.setAccessible(true);
                declaredMethod3.invoke(objInvoke, valueCallback2);
            }
            if (!TextUtils.isEmpty(str)) {
                Method declaredMethod4 = cls.getDeclaredMethod("setAcceptType", String.class);
                declaredMethod4.setAccessible(true);
                declaredMethod4.invoke(objInvoke, str);
            }
            if (callback != null) {
                Method declaredMethod5 = cls.getDeclaredMethod("setJsChannelCallback", Handler.Callback.class);
                declaredMethod5.setAccessible(true);
                declaredMethod5.invoke(objInvoke, callback);
            }
            Method declaredMethod6 = cls.getDeclaredMethod("setPermissionInterceptor", PermissionInterceptor.class);
            declaredMethod6.setAccessible(true);
            declaredMethod6.invoke(objInvoke, permissionInterceptor);
            Method declaredMethod7 = cls.getDeclaredMethod("build", new Class[0]);
            declaredMethod7.setAccessible(true);
            Object objInvoke2 = declaredMethod7.invoke(objInvoke, new Object[0]);
            Method declaredMethod8 = objInvoke2.getClass().getDeclaredMethod("openFileChooser", new Class[0]);
            declaredMethod8.setAccessible(true);
            declaredMethod8.invoke(objInvoke2, new Object[0]);
        } catch (Throwable th) {
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
            if (th instanceof ClassNotFoundException) {
                LogUtils.e(TAG, "Please check whether compile'com.just.agentweb:filechooser:x.x.x' dependency was added.");
            }
            if (valueCallback != null) {
                LogUtils.i(TAG, "onReceiveValue empty");
                return false;
            }
            if (valueCallback2 != null) {
                valueCallback2.onReceiveValue(null);
            }
        }
        return true;
    }

    public static void toastShowShort(Context context, String str) {
        Toast toast = mToast;
        if (toast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), str, 0);
        } else {
            toast.setText(str);
        }
        mToast.show();
    }

    public static String[] uriToPath(Activity activity, Uri[] uriArr) {
        if (activity != null && uriArr != null && uriArr.length != 0) {
            try {
                String[] strArr = new String[uriArr.length];
                int length = uriArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i2 < length) {
                    int i4 = i3 + 1;
                    strArr[i3] = getFileAbsolutePath(activity, uriArr[i2]);
                    i2++;
                    i3 = i4;
                }
                return strArr;
            } catch (Throwable th) {
                if (LogUtils.isDebug()) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }
}
