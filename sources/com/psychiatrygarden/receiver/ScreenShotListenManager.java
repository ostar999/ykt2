package com.psychiatrygarden.receiver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import com.psychiatrygarden.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ScreenShotListenManager {
    private static Point sScreenRealSize;
    private Context mContext;
    private MediaContentObserver mExternalObserver;
    private MediaContentObserver mInternalObserver;
    private OnScreenShotListener mListener;
    private long mStartListenTime;
    private final Handler mUiHandler = new Handler(Looper.getMainLooper());
    private static final String[] MEDIA_PROJECTIONS = {"_data", "datetaken"};
    private static final String[] MEDIA_PROJECTIONS_API_16 = {"_data", "datetaken", "width", "height"};
    private static final String TAG = "screen_shot";
    private static final String[] KEYWORDS = {"screenshot", TAG, "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap"};
    private static final List<String> sHasCallbackPaths = new ArrayList();

    public class MediaContentObserver extends ContentObserver {
        private Uri mContentUri;

        public MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            this.mContentUri = contentUri;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            LogUtils.e(ScreenShotListenManager.TAG, "ScreenShotListenManager  MediaContentObserver  onChange");
            super.onChange(selfChange);
            ScreenShotListenManager.this.handleMediaContentChange(this.mContentUri);
        }
    }

    public interface OnScreenShotListener {
        void onShot(String imagePath, int height);
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
                Log.e(TAG, "Get screen real size failed.");
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

    private boolean checkCallback(String imagePath) {
        List<String> list = sHasCallbackPaths;
        if (list.contains(imagePath)) {
            Log.d(TAG, "ScreenShot: imgPath has done; imagePath = " + imagePath);
            return true;
        }
        if (list.size() >= 20) {
            for (int i2 = 0; i2 < 5; i2++) {
                sHasCallbackPaths.remove(0);
            }
        }
        sHasCallbackPaths.add(imagePath);
        return false;
    }

    private boolean checkScreenShot(String data, long dateTaken, int width, int height) {
        Point point;
        int i2;
        if (dateTaken < this.mStartListenTime || System.currentTimeMillis() - dateTaken > com.heytap.mcssdk.constant.a.f7153q || (((point = sScreenRealSize) != null && ((width > (i2 = point.x) || height > point.y) && (height > i2 || width > point.y))) || TextUtils.isEmpty(data))) {
            return false;
        }
        String lowerCase = data.toLowerCase();
        for (String str : KEYWORDS) {
            if (lowerCase.contains(str)) {
                return true;
            }
        }
        return false;
    }

    private Point getImageSize(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return new Point(options.outWidth, options.outHeight);
    }

    private Point getRealScreenSize() {
        Point point;
        Exception e2;
        try {
            point = new Point();
            try {
                WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
                if (windowManager != null) {
                    windowManager.getDefaultDisplay().getRealSize(point);
                }
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
    public void handleMediaContentChange(Uri contentUri) {
        int i2;
        int i3;
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.mContext.getContentResolver().query(contentUri, MEDIA_PROJECTIONS_API_16, null, null, "date_added desc");
            } catch (Exception e2) {
                e2.printStackTrace();
                if (0 == 0 || cursorQuery.isClosed()) {
                    return;
                }
            }
            if (cursorQuery == null) {
                Log.e(TAG, "Deviant logic.");
                if (cursorQuery == null || cursorQuery.isClosed()) {
                    return;
                }
                cursorQuery.close();
                return;
            }
            if (!cursorQuery.moveToFirst()) {
                Log.d(TAG, "Cursor no data.");
                if (cursorQuery.isClosed()) {
                    return;
                }
                cursorQuery.close();
                return;
            }
            int columnIndex = cursorQuery.getColumnIndex("_data");
            int columnIndex2 = cursorQuery.getColumnIndex("datetaken");
            int columnIndex3 = cursorQuery.getColumnIndex("width");
            int columnIndex4 = cursorQuery.getColumnIndex("height");
            String string = cursorQuery.getString(columnIndex);
            long j2 = cursorQuery.getLong(columnIndex2);
            if (columnIndex3 < 0 || columnIndex4 < 0) {
                Point imageSize = getImageSize(string);
                int i4 = imageSize.x;
                i2 = imageSize.y;
                i3 = i4;
            } else {
                i3 = cursorQuery.getInt(columnIndex3);
                i2 = cursorQuery.getInt(columnIndex4);
            }
            handleMediaRowData(string, j2, i3, i2);
            if (cursorQuery.isClosed()) {
                return;
            }
            cursorQuery.close();
        } catch (Throwable th) {
            if (0 != 0 && !cursorQuery.isClosed()) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    private void handleMediaRowData(String data, long dateTaken, int width, int height) {
        if (!checkScreenShot(data, dateTaken, width, height)) {
            Log.w(TAG, "Media content changed, but not screenshot: path = " + data + "; size = " + width + " * " + height + "; date = " + dateTaken);
            return;
        }
        Log.e(TAG, "path = " + data + "; size = " + width + " * " + height + "; date = " + dateTaken);
        if (this.mListener == null || checkCallback(data)) {
            return;
        }
        this.mListener.onShot(data, height);
    }

    public static ScreenShotListenManager newInstance(Context context) {
        assertInMainThread();
        return new ScreenShotListenManager(context);
    }

    public void setListener(OnScreenShotListener listener) {
        this.mListener = listener;
    }

    public void startListen() {
        assertInMainThread();
        this.mStartListenTime = System.currentTimeMillis();
        this.mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, this.mUiHandler);
        this.mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.mUiHandler);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        int i2 = Build.VERSION.SDK_INT;
        contentResolver.registerContentObserver(uri, i2 >= 29, this.mInternalObserver);
        this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, i2 >= 29, this.mExternalObserver);
        Log.e(TAG, "ScreenShotListenManager  startListen");
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
