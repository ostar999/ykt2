package com.beizi.ad.internal.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.collection.LruCache;
import com.beizi.ad.a.a.g;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class ImageManager {
    private static ImageManager instance;
    private static Context mContext;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private LruCache<String, Bitmap> imageCache = new LruCache<>(4194304);
    private Handler handler = new Handler();

    public interface BitmapLoadedListener {
        void onBitmapLoadFailed();

        void onBitmapLoaded(Bitmap bitmap);
    }

    public class RequestCreatorRunnble implements Runnable {
        int errorResId;
        int holderResId;
        ImageView imageView;
        String url;

        public RequestCreatorRunnble(String str) {
            this.url = str;
        }

        private Bitmap getBitmapFile() {
            String str = this.url;
            File file = new File(g.c(ImageManager.mContext), HashingFunctions.md5(str.substring(str.lastIndexOf("/") + 1)));
            if (!file.exists() || file.length() <= 0) {
                return null;
            }
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        private void showError() {
            ImageManager.this.handler.post(new Runnable() { // from class: com.beizi.ad.internal.utilities.ImageManager.RequestCreatorRunnble.2
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }

        public RequestCreatorRunnble error(int i2) {
            this.errorResId = i2;
            return this;
        }

        public void into(ImageView imageView) {
            this.imageView = imageView;
            if (TextUtils.isEmpty(this.url)) {
                return;
            }
            Bitmap bitmap = (Bitmap) ImageManager.this.imageCache.get(this.url);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                return;
            }
            Bitmap bitmapFile = getBitmapFile();
            if (bitmapFile == null) {
                ImageManager.this.executorService.submit(this);
            } else {
                imageView.setImageBitmap(bitmapFile);
                ImageManager.this.imageCache.put(this.url, bitmapFile);
            }
        }

        public RequestCreatorRunnble placeholder(int i2) {
            this.holderResId = i2;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() throws ProtocolException {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(2000);
                if (httpURLConnection.getResponseCode() == 200) {
                    final Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    ImageManager.this.handler.post(new Runnable() { // from class: com.beizi.ad.internal.utilities.ImageManager.RequestCreatorRunnble.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RequestCreatorRunnble.this.imageView.setImageBitmap(bitmapDecodeStream);
                        }
                    });
                    ImageManager.this.imageCache.put(this.url, bitmapDecodeStream);
                    String str = this.url;
                    bitmapDecodeStream.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File(g.c(ImageManager.mContext), HashingFunctions.md5(str.substring(str.lastIndexOf("/") + 1)))));
                } else {
                    showError();
                }
            } catch (FileNotFoundException unused) {
            } catch (Exception e2) {
                e2.printStackTrace();
                showError();
            }
        }
    }

    private static ImageManager getInstance() {
        if (instance == null) {
            synchronized (ImageManager.class) {
                if (instance == null) {
                    instance = new ImageManager();
                }
            }
        }
        return instance;
    }

    public static ImageManager with(Context context) {
        if (com.beizi.ad.internal.g.a().f4184i != null) {
            mContext = com.beizi.ad.internal.g.a().f4184i;
        } else {
            mContext = context;
        }
        return getInstance();
    }

    public void getBitmap(final String str, final BitmapLoadedListener bitmapLoadedListener) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Bitmap bitmap = this.imageCache.get(str);
        if (bitmap != null) {
            bitmapLoadedListener.onBitmapLoaded(bitmap);
            return;
        }
        File file = new File(g.c(mContext), HashingFunctions.md5(str.substring(str.lastIndexOf("/") + 1)));
        Bitmap bitmapDecodeFile = (!file.exists() || file.length() <= 0) ? null : BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmapDecodeFile == null) {
            this.executorService.submit(new Runnable() { // from class: com.beizi.ad.internal.utilities.ImageManager.1
                @Override // java.lang.Runnable
                public void run() throws ProtocolException {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setConnectTimeout(2000);
                        if (httpURLConnection.getResponseCode() == 200) {
                            final Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                            httpURLConnection.disconnect();
                            ImageManager.this.handler.post(new Runnable() { // from class: com.beizi.ad.internal.utilities.ImageManager.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    bitmapLoadedListener.onBitmapLoaded(bitmapDecodeStream);
                                }
                            });
                            ImageManager.this.imageCache.put(str, bitmapDecodeStream);
                            String str2 = str;
                            bitmapDecodeStream.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File(g.c(ImageManager.mContext), HashingFunctions.md5(str2.substring(str2.lastIndexOf("/") + 1)))));
                        }
                    } catch (Exception unused) {
                        ImageManager.this.handler.post(new Runnable() { // from class: com.beizi.ad.internal.utilities.ImageManager.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                bitmapLoadedListener.onBitmapLoadFailed();
                            }
                        });
                    }
                }
            });
        } else {
            this.imageCache.put(str, bitmapDecodeFile);
            bitmapLoadedListener.onBitmapLoaded(bitmapDecodeFile);
        }
    }

    public RequestCreatorRunnble load(String str) {
        return new RequestCreatorRunnble(str);
    }
}
