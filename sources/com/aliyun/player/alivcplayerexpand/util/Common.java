package com.aliyun.player.alivcplayerexpand.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class Common {
    private static final int FAILED = 0;
    private static final int SUCCESS = 1;
    private static Common instance;
    private FileOperateCallback callback;
    private Context context;
    private String errorStr;
    private volatile boolean isSuccess;
    private String sdPath;
    private String srcPath;
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.aliyun.player.alivcplayerexpand.util.Common.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (Common.this.callback != null) {
                if (message.what == 1) {
                    Common.this.callback.onSuccess();
                }
                if (message.what == 0) {
                    Common.this.callback.onFailed(message.obj.toString());
                }
            }
            Common.this.threadPoolExecutor.remove(Common.this.runnable);
            Common.this.context = null;
            Common unused = Common.instance = null;
        }
    };
    private Runnable runnable = new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.Common.2
        @Override // java.lang.Runnable
        public void run() throws IOException {
            Common common = Common.this;
            common.copyAssetsToDst(common.context, Common.this.srcPath, Common.this.sdPath);
            if (Common.this.isSuccess) {
                Common.this.handler.obtainMessage(1).sendToTarget();
            } else {
                Common.this.handler.obtainMessage(0, Common.this.errorStr).sendToTarget();
            }
        }
    };

    public interface FileOperateCallback {
        void onFailed(String str);

        void onSuccess();
    }

    private Common(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyAssetsToDst(Context context, String str, String str2) throws IOException {
        try {
            String[] list = context.getAssets().list(str);
            if (list.length > 0) {
                File file = new File(str2);
                if (!file.exists()) {
                    file.mkdirs();
                }
                for (String str3 : list) {
                    if ("".equals(str)) {
                        copyAssetsToDst(context, str3, str2 + File.separator + str3);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        String str4 = File.separator;
                        sb.append(str4);
                        sb.append(str3);
                        copyAssetsToDst(context, sb.toString(), str2 + str4 + str3);
                    }
                }
            } else {
                File file2 = new File(str2);
                InputStream inputStreamOpen = context.getAssets().open(str);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = inputStreamOpen.read(bArr);
                    if (i2 == -1) {
                        break;
                    } else {
                        fileOutputStream.write(bArr, 0, i2);
                    }
                }
                fileOutputStream.flush();
                inputStreamOpen.close();
                fileOutputStream.close();
            }
            this.isSuccess = true;
        } catch (Exception e2) {
            e2.printStackTrace();
            this.errorStr = e2.getMessage();
            this.isSuccess = false;
        }
    }

    public static Common getInstance(Context context) {
        if (instance == null) {
            synchronized (Common.class) {
                if (instance == null) {
                    instance = new Common(context);
                }
            }
        }
        return instance;
    }

    public Common copyAssetsToSD(String str, String str2) {
        this.srcPath = str;
        this.sdPath = str2;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.threadPoolExecutor = threadPoolExecutor;
        threadPoolExecutor.execute(Executors.defaultThreadFactory().newThread(this.runnable));
        return this;
    }

    public void onDestroy() {
        Runnable runnable;
        ThreadPoolExecutor threadPoolExecutor = this.threadPoolExecutor;
        if (threadPoolExecutor != null && (runnable = this.runnable) != null) {
            threadPoolExecutor.remove(runnable);
        }
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeMessages(1);
            this.handler.removeMessages(0);
            this.handler = null;
        }
        instance = null;
        this.callback = null;
        this.context = null;
    }

    public void setFileOperateCallback(FileOperateCallback fileOperateCallback) {
        this.callback = fileOperateCallback;
    }
}
