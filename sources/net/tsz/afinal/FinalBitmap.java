package net.tsz.afinal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import cn.hutool.core.text.StrPool;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import net.tsz.afinal.bitmap.core.BitmapCache;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.core.BitmapProcess;
import net.tsz.afinal.bitmap.display.Displayer;
import net.tsz.afinal.bitmap.display.SimpleDisplayer;
import net.tsz.afinal.bitmap.download.Downloader;
import net.tsz.afinal.bitmap.download.SimpleDownloader;
import net.tsz.afinal.core.AsyncTask;
import net.tsz.afinal.utils.Utils;

/* loaded from: classes9.dex */
public class FinalBitmap {
    private static FinalBitmap mFinalBitmap;
    private ExecutorService bitmapLoadAndDisplayExecutor;
    private BitmapProcess mBitmapProcess;
    private FinalBitmapConfig mConfig;
    private Context mContext;
    private BitmapCache mImageCache;
    private boolean mExitTasksEarly = false;
    private boolean mPauseWork = false;
    private final Object mPauseWorkLock = new Object();
    private boolean mInit = false;
    private HashMap<String, BitmapDisplayConfig> configMap = new HashMap<>();

    public class BitmapCircleLoadAndDisplayTask extends AsyncTask<Object, Void, Bitmap> {
        private Object data;
        private final BitmapDisplayConfig displayConfig;
        private final WeakReference<View> imageViewReference;

        public BitmapCircleLoadAndDisplayTask(View view, BitmapDisplayConfig bitmapDisplayConfig) {
            this.imageViewReference = new WeakReference<>(view);
            this.displayConfig = bitmapDisplayConfig;
        }

        private View getAttachedImageView() {
            View view = this.imageViewReference.get();
            if (this == FinalBitmap.getBitmapCircleTaskFromImageView(view)) {
                return view;
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.tsz.afinal.core.AsyncTask
        public Bitmap doInBackground(Object... objArr) {
            Object obj = objArr[0];
            this.data = obj;
            String strValueOf = String.valueOf(obj);
            synchronized (FinalBitmap.this.mPauseWorkLock) {
                while (FinalBitmap.this.mPauseWork && !isCancelled()) {
                    try {
                        FinalBitmap.this.mPauseWorkLock.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            Bitmap bitmapProcessBitmap = (isCancelled() || getAttachedImageView() == null || FinalBitmap.this.mExitTasksEarly) ? null : FinalBitmap.this.processBitmap(strValueOf, this.displayConfig);
            if (bitmapProcessBitmap != null) {
                FinalBitmap.this.mImageCache.addToMemoryCache(strValueOf, bitmapProcessBitmap);
                FinalBitmap.this.mImageCache.addToDiskCache(strValueOf, FinalBitmap.this.Bitmap2Bytes(bitmapProcessBitmap));
            }
            return bitmapProcessBitmap;
        }

        @Override // net.tsz.afinal.core.AsyncTask
        public void onCancelled(Bitmap bitmap) {
            super.onCancelled((BitmapCircleLoadAndDisplayTask) bitmap);
            synchronized (FinalBitmap.this.mPauseWorkLock) {
                FinalBitmap.this.mPauseWorkLock.notifyAll();
            }
        }

        @Override // net.tsz.afinal.core.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            if (isCancelled() || FinalBitmap.this.mExitTasksEarly) {
                bitmap = null;
            }
            if (bitmap != null) {
                bitmap = FinalBitmap.getCircleBitmap(bitmap);
            }
            View attachedImageView = getAttachedImageView();
            if (bitmap != null && attachedImageView != null) {
                FinalBitmap.this.mConfig.displayer.loadCompletedisplay(attachedImageView, bitmap, this.displayConfig);
            } else {
                if (bitmap != null || attachedImageView == null) {
                    return;
                }
                FinalBitmap.this.mConfig.displayer.loadFailDisplay(attachedImageView, this.displayConfig.getLoadfailBitmap());
            }
        }
    }

    public class BitmapLoadAndDisplayTask extends AsyncTask<Object, Void, Bitmap> {
        private Object data;
        private final BitmapDisplayConfig displayConfig;
        private final WeakReference<View> imageViewReference;

        public BitmapLoadAndDisplayTask(View view, BitmapDisplayConfig bitmapDisplayConfig) {
            this.imageViewReference = new WeakReference<>(view);
            this.displayConfig = bitmapDisplayConfig;
        }

        private View getAttachedImageView() {
            View view = this.imageViewReference.get();
            if (this == FinalBitmap.getBitmapTaskFromImageView(view)) {
                return view;
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.tsz.afinal.core.AsyncTask
        public Bitmap doInBackground(Object... objArr) {
            Object obj = objArr[0];
            this.data = obj;
            String strValueOf = String.valueOf(obj);
            synchronized (FinalBitmap.this.mPauseWorkLock) {
                while (FinalBitmap.this.mPauseWork && !isCancelled()) {
                    try {
                        FinalBitmap.this.mPauseWorkLock.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            Bitmap bitmapProcessBitmap = (isCancelled() || getAttachedImageView() == null || FinalBitmap.this.mExitTasksEarly) ? null : FinalBitmap.this.processBitmap(strValueOf, this.displayConfig);
            if (bitmapProcessBitmap != null) {
                FinalBitmap.this.mImageCache.addToMemoryCache(strValueOf, bitmapProcessBitmap);
            }
            return bitmapProcessBitmap;
        }

        @Override // net.tsz.afinal.core.AsyncTask
        public void onCancelled(Bitmap bitmap) {
            super.onCancelled((BitmapLoadAndDisplayTask) bitmap);
            synchronized (FinalBitmap.this.mPauseWorkLock) {
                FinalBitmap.this.mPauseWorkLock.notifyAll();
            }
        }

        @Override // net.tsz.afinal.core.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            if (isCancelled() || FinalBitmap.this.mExitTasksEarly) {
                bitmap = null;
            }
            View attachedImageView = getAttachedImageView();
            if (bitmap != null && attachedImageView != null) {
                FinalBitmap.this.mConfig.displayer.loadCompletedisplay(attachedImageView, bitmap, this.displayConfig);
            } else {
                if (bitmap != null || attachedImageView == null) {
                    return;
                }
                FinalBitmap.this.mConfig.displayer.loadFailDisplay(attachedImageView, this.displayConfig.getLoadfailBitmap());
            }
        }
    }

    public class CacheExecutecTask extends AsyncTask<Object, Void, Void> {
        public static final int MESSAGE_CLEAR = 1;
        public static final int MESSAGE_CLEAR_DISK = 3;
        public static final int MESSAGE_CLEAR_KEY = 4;
        public static final int MESSAGE_CLEAR_KEY_IN_DISK = 5;
        public static final int MESSAGE_CLOSE = 2;

        private CacheExecutecTask() {
        }

        @Override // net.tsz.afinal.core.AsyncTask
        public Void doInBackground(Object... objArr) {
            int iIntValue = ((Integer) objArr[0]).intValue();
            if (iIntValue == 1) {
                FinalBitmap.this.clearCacheInternalInBackgroud();
                return null;
            }
            if (iIntValue == 2) {
                FinalBitmap.this.closeCacheInternalInBackgroud();
                return null;
            }
            if (iIntValue == 3) {
                FinalBitmap.this.clearDiskCacheInBackgroud();
                return null;
            }
            if (iIntValue == 4) {
                FinalBitmap.this.clearCacheInBackgroud(String.valueOf(objArr[1]));
                return null;
            }
            if (iIntValue != 5) {
                return null;
            }
            FinalBitmap.this.clearDiskCacheInBackgroud(String.valueOf(objArr[1]));
            return null;
        }
    }

    public class FinalBitmapConfig {
        public String cachePath;
        public BitmapDisplayConfig defaultDisplayConfig;
        public int diskCacheSize;
        public Displayer displayer;
        public Downloader downloader;
        public int memCacheSize;
        public float memCacheSizePercent;
        public int poolSize = 3;
        public boolean recycleImmediately = true;

        public FinalBitmapConfig(Context context) {
            BitmapDisplayConfig bitmapDisplayConfig = new BitmapDisplayConfig();
            this.defaultDisplayConfig = bitmapDisplayConfig;
            bitmapDisplayConfig.setAnimation(null);
            this.defaultDisplayConfig.setAnimationType(1);
            int iFloor = (int) Math.floor(context.getResources().getDisplayMetrics().widthPixels / 2);
            this.defaultDisplayConfig.setBitmapHeight(iFloor);
            this.defaultDisplayConfig.setBitmapWidth(iFloor);
        }
    }

    private FinalBitmap(Context context) {
        this.mContext = context;
        this.mConfig = new FinalBitmapConfig(context);
        configDiskCachePath(Utils.getDiskCacheDir(context, "imgCache").getAbsolutePath());
        configDisplayer(new SimpleDisplayer());
        configDownlader(new SimpleDownloader());
    }

    public static boolean checkImageCircleTask(Object obj, View view) {
        BitmapCircleLoadAndDisplayTask bitmapCircleTaskFromImageView = getBitmapCircleTaskFromImageView(view);
        if (bitmapCircleTaskFromImageView != null) {
            Object obj2 = bitmapCircleTaskFromImageView.data;
            if (obj2 != null && obj2.equals(obj)) {
                return false;
            }
            bitmapCircleTaskFromImageView.cancel(true);
        }
        return true;
    }

    public static boolean checkImageTask(Object obj, View view) {
        BitmapLoadAndDisplayTask bitmapTaskFromImageView = getBitmapTaskFromImageView(view);
        if (bitmapTaskFromImageView != null) {
            Object obj2 = bitmapTaskFromImageView.data;
            if (obj2 != null && obj2.equals(obj)) {
                return false;
            }
            bitmapTaskFromImageView.cancel(true);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCacheInBackgroud(String str) {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.clearCache(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCacheInternalInBackgroud() {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.clearCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDiskCacheInBackgroud() {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.clearDiskCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCacheInternalInBackgroud() {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.close();
            this.mImageCache = null;
            mFinalBitmap = null;
        }
    }

    public static synchronized FinalBitmap create(Context context) {
        if (mFinalBitmap == null) {
            mFinalBitmap = new FinalBitmap(context.getApplicationContext());
        }
        return mFinalBitmap;
    }

    private void doDisplay(View view, String str, BitmapDisplayConfig bitmapDisplayConfig) {
        if (!this.mInit) {
            init();
        }
        if (TextUtils.isEmpty(str) || view == null) {
            return;
        }
        if (bitmapDisplayConfig == null) {
            bitmapDisplayConfig = this.mConfig.defaultDisplayConfig;
        }
        BitmapCache bitmapCache = this.mImageCache;
        Bitmap bitmapFromMemoryCache = bitmapCache != null ? bitmapCache.getBitmapFromMemoryCache(str) : null;
        if (bitmapFromMemoryCache != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmapFromMemoryCache);
                return;
            } else {
                view.setBackgroundDrawable(new BitmapDrawable(bitmapFromMemoryCache));
                return;
            }
        }
        if (checkImageTask(str, view)) {
            BitmapLoadAndDisplayTask bitmapLoadAndDisplayTask = new BitmapLoadAndDisplayTask(view, bitmapDisplayConfig);
            AsyncDrawable asyncDrawable = new AsyncDrawable(this.mContext.getResources(), bitmapDisplayConfig.getLoadingBitmap(), bitmapLoadAndDisplayTask);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(asyncDrawable);
            } else {
                view.setBackgroundDrawable(asyncDrawable);
            }
            bitmapLoadAndDisplayTask.executeOnExecutor(this.bitmapLoadAndDisplayExecutor, str);
        }
    }

    private void doDisplayCircle(View view, String str, BitmapDisplayConfig bitmapDisplayConfig) {
        if (!this.mInit) {
            init();
        }
        if (TextUtils.isEmpty(str) || view == null) {
            return;
        }
        if (bitmapDisplayConfig == null) {
            bitmapDisplayConfig = this.mConfig.defaultDisplayConfig;
        }
        Bitmap bitmapFromCache = this.mImageCache != null ? getBitmapFromCache(str) : null;
        if (bitmapFromCache != null) {
            Bitmap circleBitmap = getCircleBitmap(bitmapFromCache);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(circleBitmap);
                return;
            } else {
                view.setBackgroundDrawable(new BitmapDrawable(circleBitmap));
                return;
            }
        }
        if (checkImageTask(str, view)) {
            this.mImageCache.clearCache(str);
            BitmapCircleLoadAndDisplayTask bitmapCircleLoadAndDisplayTask = new BitmapCircleLoadAndDisplayTask(view, bitmapDisplayConfig);
            AsyncDrawable asyncDrawable = new AsyncDrawable(this.mContext.getResources(), bitmapDisplayConfig.getLoadingBitmap(), bitmapCircleLoadAndDisplayTask);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(asyncDrawable);
            } else {
                view.setBackgroundDrawable(asyncDrawable);
            }
            bitmapCircleLoadAndDisplayTask.executeOnExecutor(this.bitmapLoadAndDisplayExecutor, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BitmapCircleLoadAndDisplayTask getBitmapCircleTaskFromImageView(View view) {
        if (view == null) {
            return null;
        }
        Drawable drawable = view instanceof ImageView ? ((ImageView) view).getDrawable() : view.getBackground();
        if (drawable instanceof AsyncDrawable) {
            return ((AsyncDrawable) drawable).getBitmapCircleWorkerTask();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BitmapLoadAndDisplayTask getBitmapTaskFromImageView(View view) {
        if (view == null) {
            return null;
        }
        Drawable drawable = view instanceof ImageView ? ((ImageView) view).getDrawable() : view.getBackground();
        if (drawable instanceof AsyncDrawable) {
            return ((AsyncDrawable) drawable).getBitmapWorkerTask();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setColor(-12434878);
        float f2 = width / 2;
        canvas.drawCircle(f2, f2, f2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapCreateBitmap;
    }

    private BitmapDisplayConfig getDisplayConfig() {
        BitmapDisplayConfig bitmapDisplayConfig = new BitmapDisplayConfig();
        bitmapDisplayConfig.setAnimation(this.mConfig.defaultDisplayConfig.getAnimation());
        bitmapDisplayConfig.setAnimationType(this.mConfig.defaultDisplayConfig.getAnimationType());
        bitmapDisplayConfig.setBitmapHeight(this.mConfig.defaultDisplayConfig.getBitmapHeight());
        bitmapDisplayConfig.setBitmapWidth(this.mConfig.defaultDisplayConfig.getBitmapWidth());
        bitmapDisplayConfig.setLoadfailBitmap(this.mConfig.defaultDisplayConfig.getLoadfailBitmap());
        bitmapDisplayConfig.setLoadingBitmap(this.mConfig.defaultDisplayConfig.getLoadingBitmap());
        return bitmapDisplayConfig;
    }

    private FinalBitmap init() {
        if (!this.mInit) {
            BitmapCache.ImageCacheParams imageCacheParams = new BitmapCache.ImageCacheParams(this.mConfig.cachePath);
            FinalBitmapConfig finalBitmapConfig = this.mConfig;
            float f2 = finalBitmapConfig.memCacheSizePercent;
            if (f2 <= 0.05d || f2 >= 0.8d) {
                int i2 = finalBitmapConfig.memCacheSize;
                if (i2 > 2097152) {
                    imageCacheParams.setMemCacheSize(i2);
                } else {
                    imageCacheParams.setMemCacheSizePercent(this.mContext, 0.3f);
                }
            } else {
                imageCacheParams.setMemCacheSizePercent(this.mContext, f2);
            }
            int i3 = this.mConfig.diskCacheSize;
            if (i3 > 5242880) {
                imageCacheParams.setDiskCacheSize(i3);
            }
            imageCacheParams.setRecycleImmediately(this.mConfig.recycleImmediately);
            this.mImageCache = new BitmapCache(imageCacheParams);
            this.bitmapLoadAndDisplayExecutor = Executors.newFixedThreadPool(this.mConfig.poolSize, new ThreadFactory() { // from class: net.tsz.afinal.FinalBitmap.1
                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setPriority(4);
                    return thread;
                }
            });
            this.mBitmapProcess = new BitmapProcess(this.mConfig.downloader, this.mImageCache);
            this.mInit = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap processBitmap(String str, BitmapDisplayConfig bitmapDisplayConfig) {
        BitmapProcess bitmapProcess = this.mBitmapProcess;
        if (bitmapProcess != null) {
            return bitmapProcess.getBitmap(str, bitmapDisplayConfig);
        }
        return null;
    }

    public byte[] Bitmap2Bytes(Bitmap bitmap) {
        return new ByteArrayOutputStream().toByteArray();
    }

    public void clearCache() {
        new CacheExecutecTask().execute(1);
    }

    public void clearDiskCache() {
        new CacheExecutecTask().execute(3);
    }

    public void clearMemoryCache() {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.clearMemoryCache();
        }
    }

    public void closeCache() {
        new CacheExecutecTask().execute(2);
    }

    public FinalBitmap configBitmapLoadThreadSize(int i2) {
        if (i2 >= 1) {
            this.mConfig.poolSize = i2;
        }
        return this;
    }

    public FinalBitmap configBitmapMaxHeight(int i2) {
        this.mConfig.defaultDisplayConfig.setBitmapHeight(i2);
        return this;
    }

    public FinalBitmap configBitmapMaxWidth(int i2) {
        this.mConfig.defaultDisplayConfig.setBitmapWidth(i2);
        return this;
    }

    public FinalBitmap configDiskCachePath(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mConfig.cachePath = str;
        }
        return this;
    }

    public FinalBitmap configDiskCacheSize(int i2) {
        this.mConfig.diskCacheSize = i2;
        return this;
    }

    public FinalBitmap configDisplayer(Displayer displayer) {
        this.mConfig.displayer = displayer;
        return this;
    }

    public FinalBitmap configDownlader(Downloader downloader) {
        this.mConfig.downloader = downloader;
        return this;
    }

    public FinalBitmap configLoadfailImage(Bitmap bitmap) {
        this.mConfig.defaultDisplayConfig.setLoadfailBitmap(bitmap);
        return this;
    }

    public FinalBitmap configLoadingImage(Bitmap bitmap) {
        this.mConfig.defaultDisplayConfig.setLoadingBitmap(bitmap);
        return this;
    }

    public FinalBitmap configMemoryCachePercent(float f2) {
        this.mConfig.memCacheSizePercent = f2;
        return this;
    }

    public FinalBitmap configMemoryCacheSize(int i2) {
        this.mConfig.memCacheSize = i2;
        return this;
    }

    public FinalBitmap configRecycleImmediately(boolean z2) {
        this.mConfig.recycleImmediately = z2;
        return this;
    }

    public void display(View view, String str) {
        doDisplay(view, str, null);
    }

    public void displayCircle(View view, String str) {
        doDisplayCircle(view, str, null);
    }

    public void exitTasksEarly(boolean z2) {
        this.mExitTasksEarly = z2;
        if (z2) {
            pauseWork(false);
        }
    }

    public Bitmap getBitmapFromCache(String str) {
        Bitmap bitmapFromMemoryCache = getBitmapFromMemoryCache(str);
        return bitmapFromMemoryCache == null ? getBitmapFromDiskCache(str) : bitmapFromMemoryCache;
    }

    public Bitmap getBitmapFromDiskCache(String str) {
        return getBitmapFromDiskCache(str, null);
    }

    public Bitmap getBitmapFromMemoryCache(String str) {
        return this.mImageCache.getBitmapFromMemoryCache(str);
    }

    public void onDestroy() {
        closeCache();
    }

    public void onPause() {
        setExitTasksEarly(true);
    }

    public void onResume() {
        setExitTasksEarly(false);
    }

    public void pauseWork(boolean z2) {
        synchronized (this.mPauseWorkLock) {
            this.mPauseWork = z2;
            if (!z2) {
                this.mPauseWorkLock.notifyAll();
            }
        }
    }

    public void setExitTasksEarly(boolean z2) {
        this.mExitTasksEarly = z2;
    }

    public void clearCache(String str) {
        new CacheExecutecTask().execute(4, str);
    }

    public void clearDiskCache(String str) {
        new CacheExecutecTask().execute(5, str);
    }

    public FinalBitmap configLoadfailImage(int i2) {
        this.mConfig.defaultDisplayConfig.setLoadfailBitmap(BitmapFactory.decodeResource(this.mContext.getResources(), i2));
        return this;
    }

    public FinalBitmap configLoadingImage(int i2) {
        this.mConfig.defaultDisplayConfig.setLoadingBitmap(BitmapFactory.decodeResource(this.mContext.getResources(), i2));
        return this;
    }

    public void display(View view, String str, int i2, int i3) {
        BitmapDisplayConfig displayConfig = this.configMap.get(i2 + StrPool.UNDERLINE + i3);
        if (displayConfig == null) {
            displayConfig = getDisplayConfig();
            displayConfig.setBitmapHeight(i3);
            displayConfig.setBitmapWidth(i2);
            this.configMap.put(i2 + StrPool.UNDERLINE + i3, displayConfig);
        }
        doDisplay(view, str, displayConfig);
    }

    public Bitmap getBitmapFromDiskCache(String str, BitmapDisplayConfig bitmapDisplayConfig) {
        return this.mBitmapProcess.getFromDisk(str, bitmapDisplayConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDiskCacheInBackgroud(String str) {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.clearDiskCache(str);
        }
    }

    public void clearMemoryCache(String str) {
        BitmapCache bitmapCache = this.mImageCache;
        if (bitmapCache != null) {
            bitmapCache.clearMemoryCache(str);
        }
    }

    public static class AsyncDrawable extends BitmapDrawable {
        private WeakReference<BitmapCircleLoadAndDisplayTask> bitmapCircleWorkerTaskReference;
        private WeakReference<BitmapLoadAndDisplayTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources resources, Bitmap bitmap, BitmapLoadAndDisplayTask bitmapLoadAndDisplayTask) {
            super(resources, bitmap);
            this.bitmapWorkerTaskReference = null;
            this.bitmapCircleWorkerTaskReference = null;
            this.bitmapWorkerTaskReference = new WeakReference<>(bitmapLoadAndDisplayTask);
        }

        public BitmapCircleLoadAndDisplayTask getBitmapCircleWorkerTask() {
            return this.bitmapCircleWorkerTaskReference.get();
        }

        public BitmapLoadAndDisplayTask getBitmapWorkerTask() {
            return this.bitmapWorkerTaskReference.get();
        }

        public AsyncDrawable(Resources resources, Bitmap bitmap, BitmapCircleLoadAndDisplayTask bitmapCircleLoadAndDisplayTask) {
            super(resources, bitmap);
            this.bitmapWorkerTaskReference = null;
            this.bitmapCircleWorkerTaskReference = null;
            this.bitmapCircleWorkerTaskReference = new WeakReference<>(bitmapCircleLoadAndDisplayTask);
        }
    }

    public void display(View view, String str, Bitmap bitmap) {
        BitmapDisplayConfig displayConfig = this.configMap.get(String.valueOf(bitmap));
        if (displayConfig == null) {
            displayConfig = getDisplayConfig();
            displayConfig.setLoadingBitmap(bitmap);
            this.configMap.put(String.valueOf(bitmap), displayConfig);
        }
        doDisplay(view, str, displayConfig);
    }

    public void display(View view, String str, Bitmap bitmap, Bitmap bitmap2) {
        BitmapDisplayConfig displayConfig = this.configMap.get(String.valueOf(bitmap) + StrPool.UNDERLINE + String.valueOf(bitmap2));
        if (displayConfig == null) {
            displayConfig = getDisplayConfig();
            displayConfig.setLoadingBitmap(bitmap);
            displayConfig.setLoadfailBitmap(bitmap2);
            this.configMap.put(String.valueOf(bitmap) + StrPool.UNDERLINE + String.valueOf(bitmap2), displayConfig);
        }
        doDisplay(view, str, displayConfig);
    }

    public void display(View view, String str, int i2, int i3, Bitmap bitmap, Bitmap bitmap2) {
        BitmapDisplayConfig displayConfig = this.configMap.get(i2 + StrPool.UNDERLINE + i3 + StrPool.UNDERLINE + String.valueOf(bitmap) + StrPool.UNDERLINE + String.valueOf(bitmap2));
        if (displayConfig == null) {
            displayConfig = getDisplayConfig();
            displayConfig.setBitmapHeight(i3);
            displayConfig.setBitmapWidth(i2);
            displayConfig.setLoadingBitmap(bitmap);
            displayConfig.setLoadfailBitmap(bitmap2);
            this.configMap.put(i2 + StrPool.UNDERLINE + i3 + StrPool.UNDERLINE + String.valueOf(bitmap) + StrPool.UNDERLINE + String.valueOf(bitmap2), displayConfig);
        }
        doDisplay(view, str, displayConfig);
    }

    public void display(View view, String str, BitmapDisplayConfig bitmapDisplayConfig) {
        doDisplay(view, str, bitmapDisplayConfig);
    }
}
