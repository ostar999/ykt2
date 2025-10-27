package com.catchpig.mvvm.utils.screenshot;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ScreenShotListenManager {
    private static final String TAG = "ScreenShotListenManager";
    private static Point sScreenRealSize;
    private Context mContext;
    private MediaContentObserver mExternalObserver;
    private MediaContentObserver mInternalObserver;
    private OnScreenShotListener mListener;
    private long mStartListenTime;
    private final Handler mUiHandler = new Handler(Looper.getMainLooper());
    private static final String[] MEDIA_PROJECTIONS = {"_data", "datetaken"};
    private static final String[] MEDIA_PROJECTIONS_API_16 = {"_data", "datetaken", "width", "height"};
    private static final String[] KEYWORDS = {"screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap"};
    private static final List<String> sHasCallbackPaths = new ArrayList();

    public class MediaContentObserver extends ContentObserver {
        private Uri mContentUri;

        public MediaContentObserver(Uri uri, Handler handler) {
            super(handler);
            this.mContentUri = uri;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z2) {
            super.onChange(z2);
            Log.d(ScreenShotListenManager.TAG, "截图 onChange() ScreenShot" + this.mContentUri.toString());
            ScreenShotListenManager.this.handleMediaContentChange(this.mContentUri);
        }
    }

    public interface OnScreenShotListener {
        void onShot(String str);
    }

    private ScreenShotListenManager(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The context must not be null.");
        }
        this.mContext = context;
        if (sScreenRealSize == null) {
            Point realScreenSize = getRealScreenSize();
            sScreenRealSize = realScreenSize;
            if (realScreenSize == null) {
                Log.w(TAG, "Get screen real size failed.");
                return;
            }
            Log.d(TAG, "Screen Real Size: " + sScreenRealSize.x + " * " + sScreenRealSize.y);
        }
    }

    private static void assertInMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            throw new IllegalStateException("Call the method must be in main thread: " + ((stackTrace == null || stackTrace.length < 4) ? null : stackTrace[3].toString()));
        }
    }

    private boolean checkCallback(String str) {
        List<String> list = sHasCallbackPaths;
        if (list.contains(str)) {
            Log.d(TAG, "ScreenShot: imgPath has done; imagePath = " + str);
            return true;
        }
        if (list.size() >= 20) {
            for (int i2 = 0; i2 < 5; i2++) {
                sHasCallbackPaths.remove(0);
            }
        }
        sHasCallbackPaths.add(str);
        return false;
    }

    private boolean checkScreenShot(String str, long j2, int i2, int i3) {
        Point point;
        int i4;
        if (j2 < this.mStartListenTime || System.currentTimeMillis() - j2 > com.heytap.mcssdk.constant.a.f7153q || (((point = sScreenRealSize) != null && ((i2 > (i4 = point.x) || i3 > point.y) && (i3 > i4 || i2 > point.y))) || TextUtils.isEmpty(str))) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        for (String str2 : KEYWORDS) {
            if (lowerCase.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    private int dp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private Point getImageSize(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new Point(options.outWidth, options.outHeight);
    }

    private Point getRealScreenSize() {
        Point point;
        Exception e2;
        try {
            point = new Point();
            try {
                ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRealSize(point);
            } catch (Exception e3) {
                e2 = e3;
                e2.printStackTrace();
                return point;
            }
        } catch (Exception e4) {
            point = null;
            e2 = e4;
        }
        return point;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMediaContentChange(Uri uri) {
        Cursor cursorQuery;
        int i2;
        int i3;
        Cursor cursor = null;
        try {
            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("android:query-arg-sort-columns", new String[]{"date_added"});
                    bundle.putInt("android:query-arg-sort-direction", 1);
                    bundle.putInt("android:query-arg-limit", 1);
                    cursorQuery = this.mContext.getContentResolver().query(uri, MEDIA_PROJECTIONS_API_16, bundle, null);
                } else {
                    cursorQuery = this.mContext.getContentResolver().query(uri, MEDIA_PROJECTIONS_API_16, null, null, "date_added desc limit 1");
                }
                cursor = cursorQuery;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (0 == 0 || cursor.isClosed()) {
                    return;
                }
            }
            if (cursor == null) {
                Log.e(TAG, "截图 无数据 Deviant logic.");
                if (cursor == null || cursor.isClosed()) {
                    return;
                }
                cursor.close();
                return;
            }
            if (!cursor.moveToFirst()) {
                Log.d(TAG, "截图 无数据 Cursor no data.");
                if (cursor.isClosed()) {
                    return;
                }
                cursor.close();
                return;
            }
            int columnIndex = cursor.getColumnIndex("_data");
            int columnIndex2 = cursor.getColumnIndex("datetaken");
            int columnIndex3 = cursor.getColumnIndex("width");
            int columnIndex4 = cursor.getColumnIndex("height");
            String string = cursor.getString(columnIndex);
            long j2 = cursor.getLong(columnIndex2);
            if (columnIndex3 < 0 || columnIndex4 < 0) {
                Point imageSize = getImageSize(string);
                int i4 = imageSize.x;
                i2 = imageSize.y;
                i3 = i4;
            } else {
                i3 = cursor.getInt(columnIndex3);
                i2 = cursor.getInt(columnIndex4);
            }
            handleMediaRowData(string, j2, i3, i2);
            if (cursor.isClosed()) {
                return;
            }
            cursor.close();
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
    }

    private void handleMediaRowData(String str, long j2, int i2, int i3) {
        if (!checkScreenShot(str, j2, i2, i3)) {
            Log.w(TAG, "Media content changed, but not screenshot: path = " + str + "; size = " + i2 + " * " + i3 + "; date = " + j2);
            return;
        }
        Log.d(TAG, "ScreenShot: path = " + str + "; size = " + i2 + " * " + i3 + "; date = " + j2);
        if (this.mListener == null || checkCallback(str)) {
            return;
        }
        this.mListener.onShot(str);
    }

    public static ScreenShotListenManager newInstance(Context context) {
        assertInMainThread();
        return new ScreenShotListenManager(context);
    }

    public void setListener(OnScreenShotListener onScreenShotListener) {
        this.mListener = onScreenShotListener;
    }

    public void startListen() {
        assertInMainThread();
        this.mStartListenTime = System.currentTimeMillis();
        this.mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, this.mUiHandler);
        this.mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.mUiHandler);
        if (Build.VERSION.SDK_INT < 29) {
            this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, false, this.mInternalObserver);
            this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, this.mExternalObserver);
        } else {
            this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, true, this.mInternalObserver);
            this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, this.mExternalObserver);
        }
    }

    public void stopListen() {
        assertInMainThread();
        if (this.mInternalObserver != null) {
            try {
                this.mContext.getContentResolver().unregisterContentObserver(this.mInternalObserver);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.mInternalObserver = null;
        }
        if (this.mExternalObserver != null) {
            try {
                this.mContext.getContentResolver().unregisterContentObserver(this.mExternalObserver);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            this.mExternalObserver = null;
        }
        this.mStartListenTime = 0L;
        this.mListener = null;
    }
}
