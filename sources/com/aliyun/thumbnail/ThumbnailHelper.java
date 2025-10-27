package com.aliyun.thumbnail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.aliyun.utils.a;
import com.aliyun.utils.f;
import com.aliyun.utils.g;
import com.cicada.player.utils.Logger;
import com.just.agentweb.DefaultWebClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes2.dex */
public class ThumbnailHelper {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int MSG_KEY_BITMAP_FAIL = 2;
    private static final int MSG_KEY_BITMAP_SUCCESS = 3;
    private static final int MSG_KEY_PREPARE_FAIL = 0;
    private static final int MSG_KEY_PREPARE_SUCCESS = 1;
    private static final String TAG = "ThumbnailHelper";
    private ThumbnailInfo[] mThumbnailInfoArray;
    private String mUrl;
    private final Object lock = new Object();
    private Map<String, byte[]> mUrlDataMap = new HashMap();
    private volatile boolean hasPrepared = false;
    private OnPrepareListener mOnPrepareListener = null;
    private OnThumbnailGetListener mOnThumbnailGetListener = null;
    private ResultHandler mResultHandler = new ResultHandler(this);

    public class ByteHttp extends a {
        byte[] bytes;
        int len;

        private ByteHttp() {
            this.bytes = null;
            this.len = 0;
        }

        @Override // com.aliyun.utils.a
        public void handleErrorInputStream(InputStream inputStream) {
        }

        @Override // com.aliyun.utils.a
        public void handleOKInputStream(InputStream inputStream) throws IOException {
            byte[] stream = ThumbnailHelper.readStream(inputStream);
            this.bytes = stream;
            if (stream != null) {
                this.len = stream.length;
            }
        }
    }

    public interface OnImgDataResultListener {
        void onFail();

        void onSuccess(byte[] bArr);
    }

    public interface OnPrepareListener {
        void onPrepareFail();

        void onPrepareSuccess();
    }

    public interface OnThumbnailGetListener {
        void onThumbnailGetFail(long j2, String str);

        void onThumbnailGetSuccess(long j2, ThumbnailBitmapInfo thumbnailBitmapInfo);
    }

    public static class ResultHandler extends Handler {
        private WeakReference<ThumbnailHelper> thumbnailHelperWeakReference;

        public ResultHandler(ThumbnailHelper thumbnailHelper) {
            super(Looper.getMainLooper());
            this.thumbnailHelperWeakReference = new WeakReference<>(thumbnailHelper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ThumbnailHelper thumbnailHelper = this.thumbnailHelperWeakReference.get();
            if (thumbnailHelper != null) {
                thumbnailHelper.handleMessage(message);
            }
            super.handleMessage(message);
        }
    }

    static {
        f.b();
    }

    public ThumbnailHelper(String str) {
        this.mUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getBitmap(ThumbnailInfo thumbnailInfo, byte[] bArr) throws IOException {
        try {
            BitmapRegionDecoder bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance((InputStream) new ByteArrayInputStream(bArr), true);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            int i2 = thumbnailInfo.mLeft;
            int i3 = thumbnailInfo.mTop;
            return bitmapRegionDecoderNewInstance.decodeRegion(new Rect(i2, i3, thumbnailInfo.mWidth + i2, thumbnailInfo.mHeight + i3), options);
        } catch (IOException e2) {
            e2.printStackTrace();
            Logger.d(TAG, "获取缩略图异常。。" + e2.getMessage());
            return null;
        }
    }

    private URLConnection getHttpUrlConnection(String str) throws IOException {
        URLConnection uRLConnectionOpenConnection;
        URLConnection uRLConnection = null;
        try {
            uRLConnectionOpenConnection = new URL(str).openConnection();
        } catch (Exception unused) {
        }
        try {
            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                return null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            uRLConnection = uRLConnectionOpenConnection;
            return uRLConnection;
        }
    }

    private URLConnection getHttpsUrlConnection(String str) throws IOException {
        URLConnection uRLConnectionOpenConnection;
        URLConnection uRLConnection = null;
        try {
            uRLConnectionOpenConnection = new URL(str).openConnection();
        } catch (Exception unused) {
        }
        try {
            if (!(uRLConnectionOpenConnection instanceof HttpsURLConnection)) {
                return null;
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(5000);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            uRLConnection = uRLConnectionOpenConnection;
            return uRLConnection;
        }
    }

    private ThumbnailInfo getInfoByPosition(long j2) {
        String str = TAG;
        Logger.d(str, "getInfoByPosition position = " + j2);
        ThumbnailInfo[] thumbnailInfoArr = this.mThumbnailInfoArray;
        ThumbnailInfo thumbnailInfo = null;
        if (thumbnailInfoArr == null) {
            Logger.e(str, "mThumbnailInfoArray == null");
            return null;
        }
        int length = thumbnailInfoArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            ThumbnailInfo thumbnailInfo2 = this.mThumbnailInfoArray[i2];
            if (thumbnailInfo2.mStart <= j2 && thumbnailInfo2.mUntil >= j2) {
                thumbnailInfo = thumbnailInfo2;
                break;
            }
            i2++;
        }
        Logger.d(TAG, "mThumbnailInfoArray targetInfo = " + thumbnailInfo);
        return thumbnailInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getResponseCode(URLConnection uRLConnection) throws IOException {
        HttpURLConnection httpURLConnection;
        if (uRLConnection instanceof HttpsURLConnection) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
        } else {
            if (!(uRLConnection instanceof HttpURLConnection)) {
                return 0;
            }
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        return httpURLConnection.getResponseCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native Object[] getThumbnailInfos(String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public URLConnection getUrlConnection(String str) {
        if (str.startsWith(DefaultWebClient.HTTPS_SCHEME)) {
            return getHttpsUrlConnection(str);
        }
        if (str.startsWith(DefaultWebClient.HTTP_SCHEME)) {
            return getHttpUrlConnection(str);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            OnPrepareListener onPrepareListener = this.mOnPrepareListener;
            if (onPrepareListener != null) {
                onPrepareListener.onPrepareSuccess();
                return;
            }
            return;
        }
        if (i2 == 0) {
            OnPrepareListener onPrepareListener2 = this.mOnPrepareListener;
            if (onPrepareListener2 != null) {
                onPrepareListener2.onPrepareFail();
                return;
            }
            return;
        }
        if (i2 == 2) {
            if (this.mOnThumbnailGetListener != null) {
                this.mOnThumbnailGetListener.onThumbnailGetFail(message.getData().getLong("pos"), (String) message.obj);
            }
        } else {
            if (i2 != 3 || this.mOnThumbnailGetListener == null) {
                return;
            }
            long j2 = message.getData().getLong("pos");
            long j3 = message.getData().getLong("start");
            long j4 = message.getData().getLong("until");
            Bitmap bitmap = (Bitmap) message.obj;
            ThumbnailBitmapInfo thumbnailBitmapInfo = new ThumbnailBitmapInfo();
            thumbnailBitmapInfo.setPositionRange(new long[]{j3, j4});
            thumbnailBitmapInfo.setThumbnailBitmap(bitmap);
            this.mOnThumbnailGetListener.onThumbnailGetSuccess(j2, thumbnailBitmapInfo);
        }
    }

    public static void loadClass() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                try {
                    int i2 = inputStream.read(bArr);
                    if (i2 != -1) {
                        byteArrayOutputStream.write(bArr, 0, i2);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                try {
                    break;
                } catch (IOException e3) {
                }
            } finally {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void requestImgData(final String str, final OnImgDataResultListener onImgDataResultListener) {
        g.f3601a.submit(new Runnable() { // from class: com.aliyun.thumbnail.ThumbnailHelper.3
            /* JADX WARN: Code restructure failed: missing block: B:29:0x00a6, code lost:
            
                if (r2 == null) goto L32;
             */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0068 -> B:52:0x00ac). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00a9 -> B:52:0x00ac). Please report as a decompilation issue!!! */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws java.io.IOException {
                /*
                    r6 = this;
                    com.aliyun.thumbnail.ThumbnailHelper r0 = com.aliyun.thumbnail.ThumbnailHelper.this
                    java.lang.Object r0 = com.aliyun.thumbnail.ThumbnailHelper.access$1000(r0)
                    monitor-enter(r0)
                    com.aliyun.thumbnail.ThumbnailHelper r1 = com.aliyun.thumbnail.ThumbnailHelper.this     // Catch: java.lang.Throwable -> Ldb
                    java.util.Map r1 = com.aliyun.thumbnail.ThumbnailHelper.access$1100(r1)     // Catch: java.lang.Throwable -> Ldb
                    java.lang.String r2 = r2     // Catch: java.lang.Throwable -> Ldb
                    boolean r1 = r1.containsKey(r2)     // Catch: java.lang.Throwable -> Ldb
                    r2 = 0
                    if (r1 == 0) goto L25
                    com.aliyun.thumbnail.ThumbnailHelper r1 = com.aliyun.thumbnail.ThumbnailHelper.this     // Catch: java.lang.Throwable -> Ldb
                    java.util.Map r1 = com.aliyun.thumbnail.ThumbnailHelper.access$1100(r1)     // Catch: java.lang.Throwable -> Ldb
                    java.lang.String r3 = r2     // Catch: java.lang.Throwable -> Ldb
                    java.lang.Object r1 = r1.get(r3)     // Catch: java.lang.Throwable -> Ldb
                    byte[] r1 = (byte[]) r1     // Catch: java.lang.Throwable -> Ldb
                    goto L26
                L25:
                    r1 = r2
                L26:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> Ldb
                    if (r1 == 0) goto L2f
                    com.aliyun.thumbnail.ThumbnailHelper$OnImgDataResultListener r0 = r3
                    r0.onSuccess(r1)
                    return
                L2f:
                    com.aliyun.thumbnail.ThumbnailHelper r0 = com.aliyun.thumbnail.ThumbnailHelper.this
                    java.lang.String r3 = r2
                    java.net.URLConnection r0 = com.aliyun.thumbnail.ThumbnailHelper.access$1200(r0, r3)
                    if (r0 != 0) goto L54
                    java.lang.String r0 = com.aliyun.thumbnail.ThumbnailHelper.access$1300()
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    java.lang.String r3 = "can not open url"
                    r2.append(r3)
                    java.lang.String r3 = r2
                    r2.append(r3)
                    java.lang.String r2 = r2.toString()
                    com.cicada.player.utils.Logger.e(r0, r2)
                    goto Lac
                L54:
                    com.aliyun.thumbnail.ThumbnailHelper r3 = com.aliyun.thumbnail.ThumbnailHelper.this     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    int r3 = com.aliyun.thumbnail.ThumbnailHelper.access$1400(r3, r0)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    r4 = 200(0xc8, float:2.8E-43)
                    if (r3 != r4) goto L6e
                    java.io.InputStream r2 = r0.getInputStream()     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    byte[] r1 = com.aliyun.thumbnail.ThumbnailHelper.access$1500(r2)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    if (r2 == 0) goto Lac
                L68:
                    r2.close()     // Catch: java.io.IOException -> L6c
                    goto Lac
                L6c:
                    r0 = move-exception
                    goto La9
                L6e:
                    java.lang.String r0 = com.aliyun.thumbnail.ThumbnailHelper.access$1300()     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    r4.<init>()     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    java.lang.String r5 = "open url responseCode = "
                    r4.append(r5)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    r4.append(r3)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    com.cicada.player.utils.Logger.e(r0, r3)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
                    goto Lac
                L87:
                    r0 = move-exception
                    goto Ld0
                L89:
                    r0 = move-exception
                    java.lang.String r3 = com.aliyun.thumbnail.ThumbnailHelper.access$1300()     // Catch: java.lang.Throwable -> L87
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L87
                    r4.<init>()     // Catch: java.lang.Throwable -> L87
                    java.lang.String r5 = "open url exception = "
                    r4.append(r5)     // Catch: java.lang.Throwable -> L87
                    java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L87
                    r4.append(r0)     // Catch: java.lang.Throwable -> L87
                    java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L87
                    com.cicada.player.utils.Logger.e(r3, r0)     // Catch: java.lang.Throwable -> L87
                    if (r2 == 0) goto Lac
                    goto L68
                La9:
                    r0.printStackTrace()
                Lac:
                    if (r1 != 0) goto Lb4
                    com.aliyun.thumbnail.ThumbnailHelper$OnImgDataResultListener r0 = r3
                    r0.onFail()
                    goto Lcc
                Lb4:
                    com.aliyun.thumbnail.ThumbnailHelper r0 = com.aliyun.thumbnail.ThumbnailHelper.this
                    java.lang.Object r0 = com.aliyun.thumbnail.ThumbnailHelper.access$1000(r0)
                    monitor-enter(r0)
                    com.aliyun.thumbnail.ThumbnailHelper r2 = com.aliyun.thumbnail.ThumbnailHelper.this     // Catch: java.lang.Throwable -> Lcd
                    java.util.Map r2 = com.aliyun.thumbnail.ThumbnailHelper.access$1100(r2)     // Catch: java.lang.Throwable -> Lcd
                    java.lang.String r3 = r2     // Catch: java.lang.Throwable -> Lcd
                    r2.put(r3, r1)     // Catch: java.lang.Throwable -> Lcd
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> Lcd
                    com.aliyun.thumbnail.ThumbnailHelper$OnImgDataResultListener r0 = r3
                    r0.onSuccess(r1)
                Lcc:
                    return
                Lcd:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> Lcd
                    throw r1
                Ld0:
                    if (r2 == 0) goto Lda
                    r2.close()     // Catch: java.io.IOException -> Ld6
                    goto Lda
                Ld6:
                    r1 = move-exception
                    r1.printStackTrace()
                Lda:
                    throw r0
                Ldb:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> Ldb
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.aliyun.thumbnail.ThumbnailHelper.AnonymousClass3.run():void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPrepareFailMsg() {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 0;
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPrepareSuccessMsg() {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 1;
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequestBitmapFailMsg(String str, long j2) {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 2;
        messageObtainMessage.obj = str;
        Bundle bundle = new Bundle();
        bundle.putLong("pos", j2);
        messageObtainMessage.setData(bundle);
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequestBitmapSuccMsg(ThumbnailInfo thumbnailInfo, long j2, Bitmap bitmap) {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 3;
        messageObtainMessage.obj = bitmap;
        Bundle bundle = new Bundle();
        bundle.putLong("pos", j2);
        bundle.putLong("start", thumbnailInfo.mStart);
        bundle.putLong("until", thumbnailInfo.mUntil);
        messageObtainMessage.setData(bundle);
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    public void prepare() {
        synchronized (this.lock) {
            if (this.hasPrepared) {
                Logger.e(TAG, "prepare again?");
            } else {
                this.hasPrepared = true;
                g.f3601a.submit(new Runnable() { // from class: com.aliyun.thumbnail.ThumbnailHelper.1
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        ByteHttp byteHttp = new ByteHttp();
                        byteHttp.doGet(ThumbnailHelper.this.mUrl);
                        Matcher matcher = Pattern.compile("([a-zA-Z]+://[^/]+).*[/]").matcher(ThumbnailHelper.this.mUrl);
                        if (matcher.find() && byteHttp.bytes != null) {
                            Object[] thumbnailInfos = ThumbnailHelper.this.getThumbnailInfos(matcher.group(0), new String(byteHttp.bytes));
                            if (thumbnailInfos == null) {
                                ThumbnailHelper.this.mThumbnailInfoArray = null;
                            } else {
                                ThumbnailHelper.this.mThumbnailInfoArray = (ThumbnailInfo[]) thumbnailInfos;
                            }
                        }
                        if (ThumbnailHelper.this.mThumbnailInfoArray != null) {
                            ThumbnailHelper.this.sendPrepareSuccessMsg();
                        } else {
                            ThumbnailHelper.this.sendPrepareFailMsg();
                        }
                    }
                });
            }
        }
    }

    public void requestBitmapAtPosition(final long j2) {
        final ThumbnailInfo infoByPosition = getInfoByPosition(j2);
        if (infoByPosition != null) {
            requestImgData(infoByPosition.mPath, new OnImgDataResultListener() { // from class: com.aliyun.thumbnail.ThumbnailHelper.2
                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnImgDataResultListener
                public void onFail() {
                    ThumbnailHelper.this.sendRequestBitmapFailMsg("can not get thumbnail at position:" + j2, j2);
                }

                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnImgDataResultListener
                public void onSuccess(byte[] bArr) throws IOException {
                    Bitmap bitmap = ThumbnailHelper.this.getBitmap(infoByPosition, bArr);
                    if (bitmap != null) {
                        ThumbnailHelper.this.sendRequestBitmapSuccMsg(infoByPosition, j2, bitmap);
                        return;
                    }
                    ThumbnailHelper.this.sendRequestBitmapFailMsg("can not get thumbnail at position:" + j2, j2);
                }
            });
            return;
        }
        sendRequestBitmapFailMsg("no match thumbnail at position:" + j2, j2);
    }

    public void setOnPrepareListener(OnPrepareListener onPrepareListener) {
        this.mOnPrepareListener = onPrepareListener;
    }

    public void setOnThumbnailGetListener(OnThumbnailGetListener onThumbnailGetListener) {
        this.mOnThumbnailGetListener = onThumbnailGetListener;
    }
}
