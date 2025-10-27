package com.psychiatrygarden.activity.purchase.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.psychiatrygarden.activity.purchase.activity.XiaoHongShuReplyActivity;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class BitmapCache extends Activity {

    /* renamed from: h, reason: collision with root package name */
    public Handler f13716h = new Handler();
    public final String TAG = getClass().getSimpleName();
    private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<>();

    /* renamed from: com.psychiatrygarden.activity.purchase.util.BitmapCache$1, reason: invalid class name */
    public class AnonymousClass1 extends Thread {
        Bitmap thumb;
        final /* synthetic */ ImageCallback val$callback;
        final /* synthetic */ boolean val$isThumbPath;
        final /* synthetic */ ImageView val$iv;
        final /* synthetic */ String val$path;
        final /* synthetic */ String val$sourcePath;
        final /* synthetic */ String val$thumbPath;

        public AnonymousClass1(final boolean val$isThumbPath, final String val$thumbPath, final String val$sourcePath, final String val$path, final ImageCallback val$callback, final ImageView val$iv) {
            this.val$isThumbPath = val$isThumbPath;
            this.val$thumbPath = val$thumbPath;
            this.val$sourcePath = val$sourcePath;
            this.val$path = val$path;
            this.val$callback = val$callback;
            this.val$iv = val$iv;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (this.val$isThumbPath) {
                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(this.val$thumbPath);
                    this.thumb = bitmapDecodeFile;
                    if (bitmapDecodeFile == null) {
                        this.thumb = BitmapCache.this.revitionImageSize(this.val$sourcePath);
                    }
                } else {
                    this.thumb = BitmapCache.this.revitionImageSize(this.val$sourcePath);
                }
            } catch (Exception unused) {
            }
            if (this.thumb == null) {
                this.thumb = XiaoHongShuReplyActivity.bimap;
            }
            Log.e(BitmapCache.this.TAG, "-------thumb------" + this.thumb);
            BitmapCache.this.put(this.val$path, this.thumb);
            if (this.val$callback != null) {
                BitmapCache.this.f13716h.post(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.util.BitmapCache.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        anonymousClass1.val$callback.imageLoad(anonymousClass1.val$iv, anonymousClass1.thumb, anonymousClass1.val$sourcePath);
                    }
                });
            }
        }
    }

    public interface ImageCallback {
        void imageLoad(ImageView imageView, Bitmap bitmap, Object... params);
    }

    public void displayBmp(final ImageView iv, final String thumbPath, final String sourcePath, final ImageCallback callback) {
        String str;
        boolean z2;
        Bitmap bitmap;
        if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
            Log.e(this.TAG, "no paths pass in");
            return;
        }
        if (!TextUtils.isEmpty(thumbPath)) {
            str = thumbPath;
            z2 = true;
        } else {
            if (TextUtils.isEmpty(sourcePath)) {
                return;
            }
            str = sourcePath;
            z2 = false;
        }
        if (!this.imageCache.containsKey(str) || (bitmap = this.imageCache.get(str).get()) == null) {
            iv.setImageBitmap(null);
            new AnonymousClass1(z2, thumbPath, sourcePath, str, callback, iv).start();
        } else {
            if (callback != null) {
                callback.imageLoad(iv, bitmap, sourcePath);
            }
            iv.setImageBitmap(bitmap);
            Log.d(this.TAG, "hit cache");
        }
    }

    public void put(String path, Bitmap bmp) {
        if (TextUtils.isEmpty(path) || bmp == null) {
            return;
        }
        this.imageCache.put(path, new SoftReference<>(bmp));
    }

    public Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bufferedInputStream, null, options);
        bufferedInputStream.close();
        int i2 = 0;
        while (true) {
            if ((options.outWidth >> i2) <= 256 && (options.outHeight >> i2) <= 256) {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0d, i2);
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeStream(bufferedInputStream2, null, options);
            }
            i2++;
        }
    }
}
