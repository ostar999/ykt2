package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import androidx.collection.LruCache;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.internal.base.zaj;
import com.google.android.gms.internal.base.zan;
import com.google.android.gms.internal.base.zao;
import com.google.android.gms.internal.base.zar;
import com.heytap.mcssdk.constant.a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final class ImageManager {
    private static final Object zamj = new Object();
    private static HashSet<Uri> zamk = new HashSet<>();
    private static ImageManager zaml;
    private final Context mContext;
    private final Handler mHandler = new zar(Looper.getMainLooper());
    private final ExecutorService zamm = zan.zact().zaa(4, zao.zasg);
    private final zaa zamn = null;
    private final zaj zamo = new zaj();
    private final Map<com.google.android.gms.common.images.zab, ImageReceiver> zamp = new HashMap();
    private final Map<Uri, ImageReceiver> zamq = new HashMap();
    private final Map<Uri, Long> zamr = new HashMap();

    @KeepName
    public final class ImageReceiver extends ResultReceiver {
        private final Uri zamt;
        private final ArrayList<com.google.android.gms.common.images.zab> zamu;

        public ImageReceiver(Uri uri) {
            super(new zar(Looper.getMainLooper()));
            this.zamt = uri;
            this.zamu = new ArrayList<>();
        }

        @Override // android.os.ResultReceiver
        public final void onReceiveResult(int i2, Bundle bundle) {
            ImageManager.this.zamm.execute(ImageManager.this.new zac(this.zamt, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zab(com.google.android.gms.common.images.zab zabVar) {
            Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zamu.add(zabVar);
        }

        public final void zac(com.google.android.gms.common.images.zab zabVar) {
            Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zamu.remove(zabVar);
        }

        public final void zacc() {
            Intent intent = new Intent(Constants.ACTION_LOAD_IMAGE);
            intent.putExtra(Constants.EXTRA_URI, this.zamt);
            intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, this);
            intent.putExtra(Constants.EXTRA_PRIORITY, 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z2);
    }

    public static final class zaa extends LruCache<com.google.android.gms.common.images.zaa, Bitmap> {
        @Override // androidx.collection.LruCache
        public final /* synthetic */ void entryRemoved(boolean z2, com.google.android.gms.common.images.zaa zaaVar, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z2, zaaVar, bitmap, bitmap2);
        }

        @Override // androidx.collection.LruCache
        public final /* synthetic */ int sizeOf(com.google.android.gms.common.images.zaa zaaVar, Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            return bitmap2.getHeight() * bitmap2.getRowBytes();
        }
    }

    public final class zab implements Runnable {
        private final com.google.android.gms.common.images.zab zamw;

        public zab(com.google.android.gms.common.images.zab zabVar) {
            this.zamw = zabVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zamp.get(this.zamw);
            if (imageReceiver != null) {
                ImageManager.this.zamp.remove(this.zamw);
                imageReceiver.zac(this.zamw);
            }
            com.google.android.gms.common.images.zab zabVar = this.zamw;
            com.google.android.gms.common.images.zaa zaaVar = zabVar.zamz;
            if (zaaVar.uri == null) {
                zabVar.zaa(ImageManager.this.mContext, ImageManager.this.zamo, true);
                return;
            }
            Bitmap bitmapZaa = ImageManager.this.zaa(zaaVar);
            if (bitmapZaa != null) {
                this.zamw.zaa(ImageManager.this.mContext, bitmapZaa, true);
                return;
            }
            Long l2 = (Long) ImageManager.this.zamr.get(zaaVar.uri);
            if (l2 != null) {
                if (SystemClock.elapsedRealtime() - l2.longValue() < a.f7141e) {
                    this.zamw.zaa(ImageManager.this.mContext, ImageManager.this.zamo, true);
                    return;
                }
                ImageManager.this.zamr.remove(zaaVar.uri);
            }
            this.zamw.zaa(ImageManager.this.mContext, ImageManager.this.zamo);
            ImageReceiver imageReceiver2 = (ImageReceiver) ImageManager.this.zamq.get(zaaVar.uri);
            if (imageReceiver2 == null) {
                imageReceiver2 = ImageManager.this.new ImageReceiver(zaaVar.uri);
                ImageManager.this.zamq.put(zaaVar.uri, imageReceiver2);
            }
            imageReceiver2.zab(this.zamw);
            if (!(this.zamw instanceof com.google.android.gms.common.images.zac)) {
                ImageManager.this.zamp.put(this.zamw, imageReceiver2);
            }
            synchronized (ImageManager.zamj) {
                if (!ImageManager.zamk.contains(zaaVar.uri)) {
                    ImageManager.zamk.add(zaaVar.uri);
                    imageReceiver2.zacc();
                }
            }
        }
    }

    public final class zac implements Runnable {
        private final Uri zamt;
        private final ParcelFileDescriptor zamx;

        public zac(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zamt = uri;
            this.zamx = parcelFileDescriptor;
        }

        @Override // java.lang.Runnable
        public final void run() throws InterruptedException, IOException {
            Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            ParcelFileDescriptor parcelFileDescriptor = this.zamx;
            boolean z2 = false;
            Bitmap bitmapDecodeFileDescriptor = null;
            if (parcelFileDescriptor != null) {
                try {
                    bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
                } catch (OutOfMemoryError e2) {
                    String strValueOf = String.valueOf(this.zamt);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 34);
                    sb.append("OOM while loading bitmap for uri: ");
                    sb.append(strValueOf);
                    Log.e("ImageManager", sb.toString(), e2);
                    z2 = true;
                }
                try {
                    this.zamx.close();
                } catch (IOException e3) {
                    Log.e("ImageManager", "closed failed", e3);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post(ImageManager.this.new zad(this.zamt, bitmapDecodeFileDescriptor, z2, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
                String strValueOf2 = String.valueOf(this.zamt);
                StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 32);
                sb2.append("Latch interrupted while posting ");
                sb2.append(strValueOf2);
                Log.w("ImageManager", sb2.toString());
            }
        }
    }

    public final class zad implements Runnable {
        private final Bitmap mBitmap;
        private final CountDownLatch zads;
        private final Uri zamt;
        private boolean zamy;

        public zad(Uri uri, Bitmap bitmap, boolean z2, CountDownLatch countDownLatch) {
            this.zamt = uri;
            this.mBitmap = bitmap;
            this.zamy = z2;
            this.zads = countDownLatch;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Asserts.checkMainThread("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z2 = this.mBitmap != null;
            if (ImageManager.this.zamn != null) {
                if (this.zamy) {
                    ImageManager.this.zamn.evictAll();
                    System.gc();
                    this.zamy = false;
                    ImageManager.this.mHandler.post(this);
                    return;
                }
                if (z2) {
                    ImageManager.this.zamn.put(new com.google.android.gms.common.images.zaa(this.zamt), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zamq.remove(this.zamt);
            if (imageReceiver != null) {
                ArrayList arrayList = imageReceiver.zamu;
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.google.android.gms.common.images.zab zabVar = (com.google.android.gms.common.images.zab) arrayList.get(i2);
                    if (z2) {
                        zabVar.zaa(ImageManager.this.mContext, this.mBitmap, false);
                    } else {
                        ImageManager.this.zamr.put(this.zamt, Long.valueOf(SystemClock.elapsedRealtime()));
                        zabVar.zaa(ImageManager.this.mContext, ImageManager.this.zamo, false);
                    }
                    if (!(zabVar instanceof com.google.android.gms.common.images.zac)) {
                        ImageManager.this.zamp.remove(zabVar);
                    }
                }
            }
            this.zads.countDown();
            synchronized (ImageManager.zamj) {
                ImageManager.zamk.remove(this.zamt);
            }
        }
    }

    private ImageManager(Context context, boolean z2) {
        this.mContext = context.getApplicationContext();
    }

    public static ImageManager create(Context context) {
        if (zaml == null) {
            zaml = new ImageManager(context, false);
        }
        return zaml;
    }

    private final void zaa(com.google.android.gms.common.images.zab zabVar) {
        Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
        new zab(zabVar).run();
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zaa(new com.google.android.gms.common.images.zad(imageView, uri));
    }

    public final void loadImage(ImageView imageView, int i2) {
        zaa(new com.google.android.gms.common.images.zad(imageView, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bitmap zaa(com.google.android.gms.common.images.zaa zaaVar) {
        zaa zaaVar2 = this.zamn;
        if (zaaVar2 == null) {
            return null;
        }
        return zaaVar2.get(zaaVar);
    }

    public final void loadImage(ImageView imageView, Uri uri, int i2) {
        com.google.android.gms.common.images.zad zadVar = new com.google.android.gms.common.images.zad(imageView, uri);
        zadVar.zanb = i2;
        zaa(zadVar);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zaa(new com.google.android.gms.common.images.zac(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i2) {
        com.google.android.gms.common.images.zac zacVar = new com.google.android.gms.common.images.zac(onImageLoadedListener, uri);
        zacVar.zanb = i2;
        zaa(zacVar);
    }
}
