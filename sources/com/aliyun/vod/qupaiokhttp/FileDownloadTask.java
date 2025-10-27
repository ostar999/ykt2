package com.aliyun.vod.qupaiokhttp;

import android.os.AsyncTask;
import com.aliyun.vod.common.utils.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
class FileDownloadTask extends AsyncTask<Void, Long, Boolean> {
    private FileDownloadCallback callback;
    private OkHttpClient okHttpClient = OkHttpFinal.getInstance().getOkHttpClientBuilder().build();
    private long previousTime;
    private File target;
    private String url;

    public FileDownloadTask(String str, File file, FileDownloadCallback fileDownloadCallback) {
        this.url = str;
        this.callback = fileDownloadCallback;
        this.target = file;
        FileUtils.mkdirs(file.getParentFile());
        if (file.exists()) {
            file.delete();
        }
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        super.onPreExecute();
        this.previousTime = System.currentTimeMillis();
        FileDownloadCallback fileDownloadCallback = this.callback;
        if (fileDownloadCallback != null) {
            fileDownloadCallback.onStart();
        }
    }

    public String saveFile(Response response) throws Throwable {
        FileOutputStream fileOutputStream;
        byte[] bArr = new byte[2048];
        InputStream inputStream = null;
        try {
            InputStream inputStreamByteStream = response.body().byteStream();
            try {
                long j2 = response.body().getContentLength();
                FileUtils.mkdirs(this.target.getParentFile());
                fileOutputStream = new FileOutputStream(this.target);
                long j3 = 0;
                while (true) {
                    try {
                        int i2 = inputStreamByteStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        j3 += i2;
                        fileOutputStream.write(bArr, 0, i2);
                        if (this.callback != null) {
                            publishProgress(Long.valueOf(j3), Long.valueOf(j2));
                        }
                    } catch (Throwable th) {
                        th = th;
                        inputStream = inputStreamByteStream;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        if (fileOutputStream == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (IOException unused2) {
                            throw th;
                        }
                    }
                }
                fileOutputStream.flush();
                String absolutePath = this.target.getAbsolutePath();
                try {
                    inputStreamByteStream.close();
                } catch (IOException unused3) {
                }
                try {
                    fileOutputStream.close();
                } catch (IOException unused4) {
                }
                return absolutePath;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }

    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... voidArr) throws Throwable {
        boolean z2 = false;
        try {
            Response responseExecute = this.okHttpClient.newCall(new Request.Builder().url(this.url).build()).execute();
            long j2 = responseExecute.body().getContentLength();
            saveFile(responseExecute);
            if (j2 == this.target.length()) {
                z2 = true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return Boolean.valueOf(z2);
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean bool) {
        super.onPostExecute((FileDownloadTask) bool);
        if (bool.booleanValue()) {
            FileDownloadCallback fileDownloadCallback = this.callback;
            if (fileDownloadCallback != null) {
                fileDownloadCallback.onDone();
                return;
            }
            return;
        }
        FileDownloadCallback fileDownloadCallback2 = this.callback;
        if (fileDownloadCallback2 != null) {
            fileDownloadCallback2.onFailure();
        }
    }

    @Override // android.os.AsyncTask
    public void onProgressUpdate(Long... lArr) {
        super.onProgressUpdate((Object[]) lArr);
        if (this.callback == null || lArr == null || lArr.length < 2) {
            return;
        }
        long jLongValue = lArr[0].longValue();
        int iLongValue = (int) ((jLongValue * 100.0f) / lArr[1].longValue());
        long jCurrentTimeMillis = (System.currentTimeMillis() - this.previousTime) / 1000;
        if (jCurrentTimeMillis == 0) {
            jCurrentTimeMillis++;
        }
        this.callback.onProgress(iLongValue, jLongValue / jCurrentTimeMillis);
    }
}
