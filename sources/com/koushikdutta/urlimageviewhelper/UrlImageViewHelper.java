package com.koushikdutta.urlimageviewhelper;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import com.koushikdutta.urlimageviewhelper.UrlDownloader;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.http.NameValuePair;

/* loaded from: classes4.dex */
public final class UrlImageViewHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CACHE_DURATION_FIVE_DAYS = 432000000;
    public static final int CACHE_DURATION_FOUR_DAYS = 345600000;
    public static final int CACHE_DURATION_INFINITE = Integer.MAX_VALUE;
    public static final int CACHE_DURATION_ONE_DAY = 86400000;
    public static final int CACHE_DURATION_ONE_WEEK = 604800000;
    public static final int CACHE_DURATION_SIX_DAYS = 518400000;
    public static final int CACHE_DURATION_THREE_DAYS = 259200000;
    public static final int CACHE_DURATION_TWO_DAYS = 172800000;
    private static HashSet<Bitmap> mAllCache = null;
    private static LruBitmapCache mDeadCache = null;
    private static ArrayList<UrlDownloader> mDownloaders = null;
    private static boolean mHasCleaned = false;
    private static DrawableCache mLiveCache = null;
    static DisplayMetrics mMetrics = null;
    private static Hashtable<String, ArrayList<ImageView>> mPendingDownloads = null;
    private static Hashtable<ImageView, String> mPendingViews = null;
    private static RequestPropertiesCallback mRequestPropertiesCallback = null;
    static Resources mResources = null;
    private static boolean mUseBitmapScaling = true;
    private static HttpUrlDownloader mHttpDownloader = new HttpUrlDownloader();
    private static ContentUrlDownloader mContentDownloader = new ContentUrlDownloader();
    private static ContactContentUrlDownloader mContactDownloader = new ContactContentUrlDownloader();
    private static AssetUrlDownloader mAssetDownloader = new AssetUrlDownloader();
    private static FileUrlDownloader mFileDownloader = new FileUrlDownloader();

    public static abstract class Loader implements UrlDownloader.UrlDownloaderCallback {
        Bitmap result;

        private Loader() {
        }
    }

    public interface RequestPropertiesCallback {
        ArrayList<NameValuePair> getHeadersForRequest(Context context, String str);
    }

    public static class ZombieDrawable extends BitmapDrawable {
        Brains mBrains;
        String mUrl;

        public static class Brains {
            boolean mHeadshot;
            int mRefCounter;

            private Brains() {
            }
        }

        public ZombieDrawable(String str, Resources resources, Bitmap bitmap) {
            this(str, resources, bitmap, new Brains());
        }

        public ZombieDrawable clone(Resources resources) {
            return new ZombieDrawable(this.mUrl, resources, getBitmap(), this.mBrains);
        }

        public void finalize() throws Throwable {
            super.finalize();
            Brains brains = this.mBrains;
            int i2 = brains.mRefCounter - 1;
            brains.mRefCounter = i2;
            if (i2 == 0) {
                if (!brains.mHeadshot) {
                    UrlImageViewHelper.mDeadCache.put(this.mUrl, getBitmap());
                }
                UrlImageViewHelper.mAllCache.remove(getBitmap());
                UrlImageViewHelper.mLiveCache.remove(this.mUrl);
                UrlImageViewHelper.clog("Zombie GC event " + this.mUrl, new Object[0]);
            }
        }

        public void headshot() {
            UrlImageViewHelper.clog("BOOM! Headshot: " + this.mUrl, new Object[0]);
            this.mBrains.mHeadshot = true;
            UrlImageViewHelper.mLiveCache.remove(this.mUrl);
            UrlImageViewHelper.mAllCache.remove(getBitmap());
        }

        private ZombieDrawable(String str, Resources resources, Bitmap bitmap, Brains brains) {
            super(resources, bitmap);
            this.mUrl = str;
            this.mBrains = brains;
            UrlImageViewHelper.mAllCache.add(bitmap);
            UrlImageViewHelper.mDeadCache.remove(str);
            UrlImageViewHelper.mLiveCache.put(str, this);
            this.mBrains.mRefCounter++;
        }
    }

    static {
        ArrayList<UrlDownloader> arrayList = new ArrayList<>();
        mDownloaders = arrayList;
        arrayList.add(mHttpDownloader);
        mDownloaders.add(mContactDownloader);
        mDownloaders.add(mContentDownloader);
        mDownloaders.add(mAssetDownloader);
        mDownloaders.add(mFileDownloader);
        mLiveCache = DrawableCache.getInstance();
        mAllCache = new HashSet<>();
        mPendingViews = new Hashtable<>();
        mPendingDownloads = new Hashtable<>();
    }

    private static boolean checkCacheDuration(File file, long j2) {
        return j2 == 2147483647L || System.currentTimeMillis() < file.lastModified() + j2;
    }

    public static void cleanup(Context context, long j2) {
        if (mHasCleaned) {
            return;
        }
        mHasCleaned = true;
        try {
            String[] list = context.getFilesDir().list();
            if (list == null) {
                return;
            }
            for (String str : list) {
                if (str.endsWith(".urlimage")) {
                    File file = new File(context.getFilesDir().getAbsolutePath() + '/' + str);
                    if (System.currentTimeMillis() > file.lastModified() + j2) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void clog(String str, Object... objArr) {
        if (objArr.length == 0) {
            return;
        }
        String.format(str, objArr);
    }

    public static int copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        int i2 = 0;
        while (true) {
            int i3 = inputStream.read(bArr);
            if (i3 == -1) {
                return i2;
            }
            outputStream.write(bArr, 0, i3);
            i2 += i3;
        }
    }

    public static void executeTask(AsyncTask<Void, Void, Void> asyncTask) {
        executeTaskHoneycomb(asyncTask);
    }

    @TargetApi(11)
    private static void executeTaskHoneycomb(AsyncTask<Void, Void, Void> asyncTask) {
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public static Bitmap getCachedBitmap(String str) {
        if (str == null) {
            return null;
        }
        LruBitmapCache lruBitmapCache = mDeadCache;
        Bitmap bitmap = lruBitmapCache != null ? lruBitmapCache.get(str) : null;
        if (bitmap != null) {
            return bitmap;
        }
        DrawableCache drawableCache = mLiveCache;
        if (drawableCache != null) {
            Drawable drawable = drawableCache.get(str);
            if (drawable instanceof ZombieDrawable) {
                return ((ZombieDrawable) drawable).getBitmap();
            }
        }
        return null;
    }

    public static ArrayList<UrlDownloader> getDownloaders() {
        return mDownloaders;
    }

    public static String getFilenameForUrl(String str) {
        return str.hashCode() + ".urlimage";
    }

    private static int getHeapSize(Context context) {
        return ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getMemoryClass() * 1024 * 1024;
    }

    public static int getPendingDownloads() {
        return mPendingDownloads.size();
    }

    public static RequestPropertiesCallback getRequestPropertiesCallback() {
        return mRequestPropertiesCallback;
    }

    public static boolean getUseBitmapScaling() {
        return mUseBitmapScaling;
    }

    private static boolean isNullOrEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.equals("") || charSequence.equals("null") || charSequence.equals(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap loadBitmapFromStream(android.content.Context r8, java.lang.String r9, java.lang.String r10, int r11, int r12) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Failed to close FileInputStream"
            java.lang.String r1 = "UrlImageViewHelper"
            prepareResources(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r2 = "Decoding: "
            r8.append(r2)
            r8.append(r9)
            java.lang.String r9 = " "
            r8.append(r9)
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            r9 = 0
            java.lang.Object[] r2 = new java.lang.Object[r9]
            clog(r8, r2)
            r8 = 0
            boolean r2 = com.koushikdutta.urlimageviewhelper.UrlImageViewHelper.mUseBitmapScaling     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            r3 = 8192(0x2000, float:1.148E-41)
            r4 = 1
            if (r2 == 0) goto L5e
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            r2.<init>()     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            r2.inJustDecodeBounds = r4     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            r6.<init>(r10)     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            r5.<init>(r6, r3)     // Catch: java.lang.Throwable -> La0 java.io.IOException -> Lae
            android.graphics.BitmapFactory.decodeStream(r5, r8, r2)     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            r5.close()     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            r6 = r9
        L46:
            int r7 = r2.outWidth     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            int r7 = r7 >> r6
            if (r7 > r11) goto L5b
            int r7 = r2.outHeight     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            int r7 = r7 >> r6
            if (r7 <= r12) goto L51
            goto L5b
        L51:
            android.graphics.BitmapFactory$Options r11 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            r11.<init>()     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            int r12 = r4 << r6
            r11.inSampleSize = r12     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            goto L60
        L5b:
            int r6 = r6 + 1
            goto L46
        L5e:
            r11 = r8
            r5 = r11
        L60:
            java.io.BufferedInputStream r12 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            r2.<init>(r10)     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            r12.<init>(r2, r3)     // Catch: java.lang.Throwable -> L9d java.io.IOException -> Laf
            android.graphics.Bitmap r10 = android.graphics.BitmapFactory.decodeStream(r12, r8, r11)     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            java.lang.String r11 = "Loaded bitmap (%dx%d)."
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            int r3 = r10.getWidth()     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            r2[r9] = r3     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            int r3 = r10.getHeight()     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            r2[r4] = r3     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            java.lang.String r11 = java.lang.String.format(r11, r2)     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            clog(r11, r9)     // Catch: java.lang.Throwable -> L99 java.io.IOException -> L9b
            r12.close()     // Catch: java.io.IOException -> L94
            goto L98
        L94:
            r8 = move-exception
            android.util.Log.w(r1, r0, r8)
        L98:
            return r10
        L99:
            r8 = move-exception
            goto La3
        L9b:
            r5 = r12
            goto Laf
        L9d:
            r8 = move-exception
            r12 = r5
            goto La3
        La0:
            r9 = move-exception
            r12 = r8
            r8 = r9
        La3:
            if (r12 == 0) goto Lad
            r12.close()     // Catch: java.io.IOException -> La9
            goto Lad
        La9:
            r9 = move-exception
            android.util.Log.w(r1, r0, r9)
        Lad:
            throw r8
        Lae:
            r5 = r8
        Laf:
            if (r5 == 0) goto Lb9
            r5.close()     // Catch: java.io.IOException -> Lb5
            goto Lb9
        Lb5:
            r9 = move-exception
            android.util.Log.w(r1, r0, r9)
        Lb9:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.urlimageviewhelper.UrlImageViewHelper.loadBitmapFromStream(android.content.Context, java.lang.String, java.lang.String, int, int):android.graphics.Bitmap");
    }

    public static void loadUrlDrawable(Context context, String str) {
        setUrlDrawable(context, (ImageView) null, str, (Drawable) null, 259200000L, (UrlImageViewCallback) null);
    }

    private static void prepareResources(Context context) {
        if (mMetrics != null) {
            return;
        }
        mMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(mMetrics);
        mResources = new Resources(context.getAssets(), mMetrics, context.getResources().getConfiguration());
    }

    public static Bitmap remove(String str) {
        new File(getFilenameForUrl(str)).delete();
        Drawable drawableRemove = mLiveCache.remove(str);
        if (!(drawableRemove instanceof ZombieDrawable)) {
            return null;
        }
        ZombieDrawable zombieDrawable = (ZombieDrawable) drawableRemove;
        Bitmap bitmap = zombieDrawable.getBitmap();
        zombieDrawable.headshot();
        return bitmap;
    }

    public static void setRequestPropertiesCallback(RequestPropertiesCallback requestPropertiesCallback) {
        mRequestPropertiesCallback = requestPropertiesCallback;
    }

    public static void setUrlDrawable(ImageView imageView, String str, int i2) {
        setUrlDrawable(imageView.getContext(), imageView, str, i2, 259200000L);
    }

    public static void setUseBitmapScaling(boolean z2) {
        mUseBitmapScaling = z2;
    }

    public static void loadUrlDrawable(Context context, String str, long j2) {
        setUrlDrawable(context, (ImageView) null, str, (Drawable) null, j2, (UrlImageViewCallback) null);
    }

    public static void setUrlDrawable(ImageView imageView, String str) {
        setUrlDrawable(imageView.getContext(), imageView, str, (Drawable) null, 259200000L, (UrlImageViewCallback) null);
    }

    public static void loadUrlDrawable(Context context, String str, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(context, (ImageView) null, str, (Drawable) null, 259200000L, urlImageViewCallback);
    }

    public static void setUrlDrawable(ImageView imageView, String str, Drawable drawable) {
        setUrlDrawable(imageView.getContext(), imageView, str, drawable, 259200000L, (UrlImageViewCallback) null);
    }

    public static void loadUrlDrawable(Context context, String str, long j2, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(context, (ImageView) null, str, (Drawable) null, j2, urlImageViewCallback);
    }

    public static void setUrlDrawable(ImageView imageView, String str, int i2, long j2) {
        setUrlDrawable(imageView.getContext(), imageView, str, i2, j2);
    }

    public static void setUrlDrawable(ImageView imageView, String str, Drawable drawable, long j2) {
        setUrlDrawable(imageView.getContext(), imageView, str, drawable, j2, (UrlImageViewCallback) null);
    }

    private static void setUrlDrawable(Context context, ImageView imageView, String str, int i2, long j2) {
        setUrlDrawable(context, imageView, str, i2 != 0 ? imageView.getResources().getDrawable(i2) : null, j2, (UrlImageViewCallback) null);
    }

    public static void setUrlDrawable(ImageView imageView, String str, int i2, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(imageView.getContext(), imageView, str, i2, 259200000L, urlImageViewCallback);
    }

    public static void setUrlDrawable(ImageView imageView, String str, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(imageView.getContext(), imageView, str, (Drawable) null, 259200000L, urlImageViewCallback);
    }

    public static void cleanup(Context context) {
        cleanup(context, 604800000L);
    }

    public static void setUrlDrawable(ImageView imageView, String str, Drawable drawable, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(imageView.getContext(), imageView, str, drawable, 259200000L, urlImageViewCallback);
    }

    public static void setUrlDrawable(ImageView imageView, String str, int i2, long j2, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(imageView.getContext(), imageView, str, i2, j2, urlImageViewCallback);
    }

    public static void setUrlDrawable(ImageView imageView, String str, Drawable drawable, long j2, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(imageView.getContext(), imageView, str, drawable, j2, urlImageViewCallback);
    }

    private static void setUrlDrawable(Context context, ImageView imageView, String str, int i2, long j2, UrlImageViewCallback urlImageViewCallback) {
        setUrlDrawable(context, imageView, str, i2 != 0 ? imageView.getResources().getDrawable(i2) : null, j2, urlImageViewCallback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.koushikdutta.urlimageviewhelper.UrlDownloader] */
    /* JADX WARN: Type inference failed for: r4v10, types: [com.koushikdutta.urlimageviewhelper.UrlDownloader$UrlDownloaderCallback] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v7, types: [com.koushikdutta.urlimageviewhelper.UrlImageViewCallback] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v7, types: [android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r5v8 */
    private static void setUrlDrawable(final Context context, ImageView imageView, final String str, final Drawable drawable, long j2, UrlImageViewCallback urlImageViewCallback) {
        Drawable drawable2;
        Drawable drawable3;
        ZombieDrawable zombieDrawable;
        cleanup(context);
        if (isNullOrEmpty(str)) {
            if (imageView != null) {
                mPendingViews.remove(imageView);
                imageView.setImageDrawable(drawable);
                return;
            }
            return;
        }
        if (mMetrics == null) {
            prepareResources(context);
        }
        DisplayMetrics displayMetrics = mMetrics;
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        final String absolutePath = context.getFileStreamPath(getFilenameForUrl(str)).getAbsolutePath();
        File file = new File(absolutePath);
        if (mDeadCache == null) {
            mDeadCache = new LruBitmapCache(getHeapSize(context) / 8);
        }
        Bitmap bitmapRemove = mDeadCache.remove(str);
        if (bitmapRemove != null) {
            clog("zombie load: " + str, new Object[0]);
            drawable2 = null;
        } else {
            drawable2 = mLiveCache.get(str);
        }
        if (drawable2 == null && bitmapRemove == null) {
            drawable3 = drawable2;
        } else {
            clog("Cache hit on: " + str, new Object[0]);
            if (file.exists() && !checkCacheDuration(file, j2)) {
                clog("Cache hit, but file is stale. Forcing reload: " + str, new Object[0]);
                if (drawable2 != null && (drawable2 instanceof ZombieDrawable)) {
                    ((ZombieDrawable) drawable2).headshot();
                }
                bitmapRemove = null;
                drawable3 = null;
            } else {
                clog("Using cached: " + str, new Object[0]);
                drawable3 = drawable2;
            }
        }
        if (drawable3 != null || bitmapRemove != null) {
            if (imageView != null) {
                mPendingViews.remove(imageView);
                if (drawable3 instanceof ZombieDrawable) {
                    zombieDrawable = ((ZombieDrawable) drawable3).clone(mResources);
                } else {
                    if (bitmapRemove != null) {
                        zombieDrawable = new ZombieDrawable(str, mResources, bitmapRemove);
                    }
                    imageView.setImageDrawable(drawable3);
                }
                drawable3 = zombieDrawable;
                imageView.setImageDrawable(drawable3);
            }
            if (urlImageViewCallback != null) {
                if (bitmapRemove == null && (drawable3 instanceof ZombieDrawable)) {
                    bitmapRemove = ((ZombieDrawable) drawable3).getBitmap();
                }
                urlImageViewCallback.onLoaded(imageView, bitmapRemove, str, true);
                return;
            }
            return;
        }
        clog("Waiting for " + str + " " + imageView, new Object[0]);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
            mPendingViews.put(imageView, str);
        }
        ArrayList<ImageView> arrayList = mPendingDownloads.get(str);
        if (arrayList != null) {
            if (imageView != null) {
                arrayList.add(imageView);
                return;
            }
            return;
        }
        final ArrayList<ImageView> arrayList2 = new ArrayList<>();
        if (imageView != null) {
            arrayList2.add(imageView);
        }
        mPendingDownloads.put(str, arrayList2);
        final int i4 = i2 <= 0 ? Integer.MAX_VALUE : i2;
        final int i5 = i3 <= 0 ? Integer.MAX_VALUE : i3;
        final Loader loader = new Loader() { // from class: com.koushikdutta.urlimageviewhelper.UrlImageViewHelper.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader.UrlDownloaderCallback
            public void onDownloadComplete(UrlDownloader urlDownloader, InputStream inputStream, String str2) {
                File file2;
                boolean zAllowCache;
                if (inputStream == null && str2 == null) {
                    if (urlDownloader != null) {
                        if (zAllowCache) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                try {
                    try {
                        String str3 = absolutePath;
                        if (inputStream != null) {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(absolutePath), 8192);
                            UrlImageViewHelper.copyStream(bufferedInputStream, bufferedOutputStream);
                            bufferedOutputStream.close();
                            str2 = str3;
                        }
                        this.result = UrlImageViewHelper.loadBitmapFromStream(context, str, str2, i4, i5);
                    } catch (Exception unused) {
                        new File(absolutePath).delete();
                        if (urlDownloader == null || urlDownloader.allowCache()) {
                            return;
                        } else {
                            file2 = new File(absolutePath);
                        }
                    }
                    if (urlDownloader == null || urlDownloader.allowCache()) {
                        return;
                    }
                    file2 = new File(absolutePath);
                    file2.delete();
                } finally {
                    if (urlDownloader != null && !urlDownloader.allowCache()) {
                        new File(absolutePath).delete();
                    }
                }
            }
        };
        final ?? r4 = urlImageViewCallback;
        final ?? r5 = imageView;
        final Runnable runnable = new Runnable() { // from class: com.koushikdutta.urlimageviewhelper.UrlImageViewHelper.2
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // java.lang.Runnable
            public void run() {
                Bitmap bitmap = loader.result;
                Drawable zombieDrawable2 = bitmap != null ? new ZombieDrawable(str, UrlImageViewHelper.mResources, bitmap) : null;
                if (zombieDrawable2 == null) {
                    UrlImageViewHelper.clog("No usable result, defaulting " + str, new Object[0]);
                    zombieDrawable2 = drawable;
                    UrlImageViewHelper.mLiveCache.put(str, zombieDrawable2);
                }
                UrlImageViewHelper.mPendingDownloads.remove(str);
                UrlImageViewCallback urlImageViewCallback2 = r4;
                if (urlImageViewCallback2 != null && r5 == null) {
                    urlImageViewCallback2.onLoaded(null, loader.result, str, false);
                }
                Iterator it = arrayList2.iterator();
                int i6 = 0;
                while (it.hasNext()) {
                    ImageView imageView2 = (ImageView) it.next();
                    String str2 = (String) UrlImageViewHelper.mPendingViews.get(imageView2);
                    if (str.equals(str2)) {
                        i6++;
                        UrlImageViewHelper.mPendingViews.remove(imageView2);
                        if (zombieDrawable2 != null) {
                            imageView2.setImageDrawable(zombieDrawable2);
                        }
                        UrlImageViewCallback urlImageViewCallback3 = r4;
                        if (urlImageViewCallback3 != null && imageView2 == r5) {
                            urlImageViewCallback3.onLoaded(imageView2, loader.result, str, false);
                        }
                    } else {
                        UrlImageViewHelper.clog("Ignoring out of date request to update view for " + str + " " + str2 + " " + imageView2, new Object[0]);
                    }
                }
                UrlImageViewHelper.clog("Populated: " + i6, new Object[0]);
            }
        };
        if (file.exists()) {
            try {
                try {
                    if (checkCacheDuration(file, j2)) {
                        clog("File Cache hit on: " + str + ". " + (System.currentTimeMillis() - file.lastModified()) + "ms old.", new Object[0]);
                        executeTask(new AsyncTask<Void, Void, Void>() { // from class: com.koushikdutta.urlimageviewhelper.UrlImageViewHelper.3
                            @Override // android.os.AsyncTask
                            public Void doInBackground(Void... voidArr) {
                                loader.onDownloadComplete(null, null, absolutePath);
                                return null;
                            }

                            @Override // android.os.AsyncTask
                            public void onPostExecute(Void r12) {
                                runnable.run();
                            }
                        });
                        return;
                    }
                    r4 = loader;
                    r5 = runnable;
                    clog("File cache has expired. Refreshing.", new Object[0]);
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
            }
        } else {
            r4 = loader;
            r5 = runnable;
        }
        Iterator<UrlDownloader> it = mDownloaders.iterator();
        while (it.hasNext()) {
            UrlDownloader next = it.next();
            if (next.canDownloadUrl(str)) {
                next.download(context, str, absolutePath, r4, r5);
                return;
            }
        }
        imageView.setImageDrawable(drawable);
    }
}
