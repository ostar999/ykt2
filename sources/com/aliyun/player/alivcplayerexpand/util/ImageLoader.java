package com.aliyun.player.alivcplayerexpand.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes2.dex */
public class ImageLoader {
    private LoadImgHandler mLoadImgHandler;

    public static class LoadImgHandler extends Handler {
        private WeakReference<ImageView> imageViewWeakReference;

        public LoadImgHandler(ImageView imageView) {
            this.imageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ImageView imageView = this.imageViewWeakReference.get();
            if (imageView == null) {
                return;
            }
            imageView.setImageBitmap((Bitmap) message.obj);
            super.handleMessage(message);
        }
    }

    public ImageLoader(ImageView imageView) {
        this.mLoadImgHandler = new LoadImgHandler(imageView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getImageBitmap(String str) throws IOException {
        Bitmap bitmapDecodeStream = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmapDecodeStream;
        } catch (MalformedURLException | IOException unused) {
            return bitmapDecodeStream;
        }
    }

    public void loadAsync(final String str) {
        new Thread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.ImageLoader.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                Bitmap imageBitmap = ImageLoader.this.getImageBitmap(str);
                Message messageObtainMessage = ImageLoader.this.mLoadImgHandler.obtainMessage();
                messageObtainMessage.obj = imageBitmap;
                ImageLoader.this.mLoadImgHandler.sendMessage(messageObtainMessage);
            }
        }).start();
    }
}
