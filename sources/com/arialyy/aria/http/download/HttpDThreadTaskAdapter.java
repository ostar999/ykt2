package com.arialyy.aria.http.download;

import com.arialyy.aria.core.common.RequestEnum;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.exception.AriaHTTPException;
import com.arialyy.aria.http.BaseHttpThreadTaskAdapter;
import com.arialyy.aria.http.ConnectionHelp;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.BandwidthLimiter;
import com.arialyy.aria.util.BufferedRandomAccessFile;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
final class HttpDThreadTaskAdapter extends BaseHttpThreadTaskAdapter {
    private final String TAG;
    private DTaskWrapper mTaskWrapper;

    public HttpDThreadTaskAdapter(SubThreadConfig subThreadConfig) {
        super(subThreadConfig);
        this.TAG = "HttpDThreadTaskAdapter";
    }

    private void handleComplete() {
        if (!getThreadTask().isBreak() && getThreadTask().checkBlock()) {
            complete();
        }
    }

    private void readChunked(InputStream inputStream) throws Throwable {
        int i2;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(getThreadConfig().tempFile, true);
                    try {
                        byte[] bArr = new byte[getTaskConfig().getBuffSize()];
                        while (getThreadTask().isLive() && (i2 = inputStream.read(bArr)) != -1 && !getThreadTask().isBreak()) {
                            BandwidthLimiter bandwidthLimiter = this.mSpeedBandUtil;
                            if (bandwidthLimiter != null) {
                                bandwidthLimiter.limitNextBytes(i2);
                            }
                            fileOutputStream2.write(bArr, 0, i2);
                            progress(i2);
                        }
                        handleComplete();
                        fileOutputStream2.close();
                    } catch (IOException e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        fail(new AriaHTTPException(String.format("文件下载失败，savePath: %s, url: %s", getEntity().getFilePath(), getThreadConfig().url), e), true);
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x00fc A[Catch: IOException -> 0x00f8, TryCatch #1 {IOException -> 0x00f8, blocks: (B:53:0x00f1, B:57:0x00fc, B:59:0x0101), top: B:63:0x00f1 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0101 A[Catch: IOException -> 0x00f8, TRY_LEAVE, TryCatch #1 {IOException -> 0x00f8, blocks: (B:53:0x00f1, B:57:0x00fc, B:59:0x0101), top: B:63:0x00f1 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readDynamicFile(java.io.InputStream r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.http.download.HttpDThreadTaskAdapter.readDynamicFile(java.io.InputStream):void");
    }

    private void readNormal(InputStream inputStream, BufferedRandomAccessFile bufferedRandomAccessFile) throws IOException {
        int i2;
        byte[] bArr = new byte[getTaskConfig().getBuffSize()];
        while (getThreadTask().isLive() && (i2 = inputStream.read(bArr)) != -1 && !getThreadTask().isBreak()) {
            BandwidthLimiter bandwidthLimiter = this.mSpeedBandUtil;
            if (bandwidthLimiter != null) {
                bandwidthLimiter.limitNextBytes(i2);
            }
            bufferedRandomAccessFile.write(bArr, 0, i2);
            progress(i2);
        }
    }

    @Override // com.arialyy.aria.core.task.AbsThreadTaskAdapter
    public void handlerThreadTask() throws Throwable {
        HttpURLConnection httpURLConnectionHandleConnection;
        BufferedInputStream bufferedInputStream;
        Exception e2;
        MalformedURLException e3;
        ArrayIndexOutOfBoundsException e4;
        IOException e5;
        Map<String, String> params;
        this.mTaskWrapper = (DTaskWrapper) getTaskWrapper();
        if (getThreadRecord().isComplete) {
            handleComplete();
            return;
        }
        BufferedRandomAccessFile bufferedRandomAccessFile = null;
        try {
            try {
                try {
                    httpURLConnectionHandleConnection = ConnectionHelp.handleConnection(ConnectionHelp.handleUrl(getThreadConfig().url, this.mTaskOption), this.mTaskOption);
                    try {
                        if (this.mTaskWrapper.isSupportBP()) {
                            ALog.d("HttpDThreadTaskAdapter", String.format("任务【%s】线程__%s__开始下载【开始位置 : %s，结束位置：%s】", getFileName(), Integer.valueOf(getThreadRecord().threadId), Long.valueOf(getThreadRecord().startLocation), Long.valueOf(getThreadRecord().endLocation)));
                            httpURLConnectionHandleConnection.setRequestProperty("Range", String.format("bytes=%s-%s", Long.valueOf(getThreadRecord().startLocation), Long.valueOf(getThreadRecord().endLocation - 1)));
                        } else {
                            ALog.w("HttpDThreadTaskAdapter", "该下载不支持断点");
                        }
                        ConnectionHelp.setConnectParam(this.mTaskOption, httpURLConnectionHandleConnection);
                        httpURLConnectionHandleConnection.setConnectTimeout(getTaskConfig().getConnectTimeOut());
                        httpURLConnectionHandleConnection.setReadTimeout(getTaskConfig().getIOTimeOut());
                        if (this.mTaskOption.isChunked()) {
                            httpURLConnectionHandleConnection.setDoInput(true);
                            httpURLConnectionHandleConnection.setChunkedStreamingMode(0);
                        }
                        httpURLConnectionHandleConnection.connect();
                        if (this.mTaskOption.getRequestEnum() == RequestEnum.POST && (params = this.mTaskOption.getParams()) != null) {
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnectionHandleConnection.getOutputStream());
                            Set<String> setKeySet = params.keySet();
                            StringBuilder sb = new StringBuilder();
                            for (String str : setKeySet) {
                                sb.append(str);
                                sb.append("=");
                                sb.append(URLEncoder.encode(params.get(str)));
                                sb.append("&");
                            }
                            String string = sb.toString();
                            outputStreamWriter.write(string.substring(0, string.length() - 1));
                            outputStreamWriter.flush();
                            outputStreamWriter.close();
                        }
                        bufferedInputStream = new BufferedInputStream(ConnectionHelp.convertInputStream(httpURLConnectionHandleConnection));
                        try {
                            if (this.mTaskOption.isChunked()) {
                                readChunked(bufferedInputStream);
                            } else if (getThreadConfig().isBlock) {
                                readDynamicFile(bufferedInputStream);
                            } else {
                                BufferedRandomAccessFile bufferedRandomAccessFile2 = new BufferedRandomAccessFile(getThreadConfig().tempFile, "rwd", getTaskConfig().getBuffSize());
                                try {
                                    if (getThreadRecord().startLocation > 0) {
                                        bufferedRandomAccessFile2.seek(getThreadRecord().startLocation);
                                    }
                                    readNormal(bufferedInputStream, bufferedRandomAccessFile2);
                                    handleComplete();
                                    bufferedRandomAccessFile = bufferedRandomAccessFile2;
                                } catch (IOException e6) {
                                    e5 = e6;
                                    bufferedRandomAccessFile = bufferedRandomAccessFile2;
                                    fail(new AriaHTTPException(String.format("任务【%s】下载失败，filePath: %s, url: %s", getFileName(), getEntity().getFilePath(), getEntity().getUrl()), e5), true);
                                    if (bufferedRandomAccessFile != null) {
                                        bufferedRandomAccessFile.close();
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (httpURLConnectionHandleConnection != null) {
                                        httpURLConnectionHandleConnection.getInputStream().close();
                                        httpURLConnectionHandleConnection.disconnect();
                                        return;
                                    }
                                    return;
                                } catch (ArrayIndexOutOfBoundsException e7) {
                                    e4 = e7;
                                    bufferedRandomAccessFile = bufferedRandomAccessFile2;
                                    fail(new AriaHTTPException(String.format("任务【%s】下载失败，filePath: %s, url: %s", getFileName(), getEntity().getFilePath(), getEntity().getUrl()), e4), false);
                                    if (bufferedRandomAccessFile != null) {
                                        bufferedRandomAccessFile.close();
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (httpURLConnectionHandleConnection != null) {
                                        httpURLConnectionHandleConnection.getInputStream().close();
                                        httpURLConnectionHandleConnection.disconnect();
                                        return;
                                    }
                                    return;
                                } catch (MalformedURLException e8) {
                                    e3 = e8;
                                    bufferedRandomAccessFile = bufferedRandomAccessFile2;
                                    fail(new AriaHTTPException(String.format("任务【%s】下载失败，filePath: %s, url: %s", getFileName(), getEntity().getFilePath(), getEntity().getUrl()), e3), false);
                                    if (bufferedRandomAccessFile != null) {
                                        bufferedRandomAccessFile.close();
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (httpURLConnectionHandleConnection != null) {
                                        httpURLConnectionHandleConnection.getInputStream().close();
                                        httpURLConnectionHandleConnection.disconnect();
                                        return;
                                    }
                                    return;
                                } catch (Exception e9) {
                                    e2 = e9;
                                    bufferedRandomAccessFile = bufferedRandomAccessFile2;
                                    fail(new AriaHTTPException(String.format("任务【%s】下载失败，filePath: %s, url: %s", getFileName(), getEntity().getFilePath(), getEntity().getUrl()), e2), false);
                                    if (bufferedRandomAccessFile != null) {
                                        bufferedRandomAccessFile.close();
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (httpURLConnectionHandleConnection != null) {
                                        httpURLConnectionHandleConnection.getInputStream().close();
                                        httpURLConnectionHandleConnection.disconnect();
                                        return;
                                    }
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedRandomAccessFile = bufferedRandomAccessFile2;
                                    if (bufferedRandomAccessFile != null) {
                                        try {
                                            bufferedRandomAccessFile.close();
                                        } catch (IOException e10) {
                                            e10.printStackTrace();
                                            throw th;
                                        }
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (httpURLConnectionHandleConnection != null) {
                                        httpURLConnectionHandleConnection.getInputStream().close();
                                        httpURLConnectionHandleConnection.disconnect();
                                    }
                                    throw th;
                                }
                            }
                            if (bufferedRandomAccessFile != null) {
                                bufferedRandomAccessFile.close();
                            }
                            bufferedInputStream.close();
                            httpURLConnectionHandleConnection.getInputStream().close();
                            httpURLConnectionHandleConnection.disconnect();
                        } catch (IOException e11) {
                            e5 = e11;
                        } catch (ArrayIndexOutOfBoundsException e12) {
                            e4 = e12;
                        } catch (MalformedURLException e13) {
                            e3 = e13;
                        } catch (Exception e14) {
                            e2 = e14;
                        }
                    } catch (MalformedURLException e15) {
                        e3 = e15;
                        bufferedInputStream = null;
                    } catch (IOException e16) {
                        e5 = e16;
                        bufferedInputStream = null;
                    } catch (ArrayIndexOutOfBoundsException e17) {
                        e4 = e17;
                        bufferedInputStream = null;
                    } catch (Exception e18) {
                        e2 = e18;
                        bufferedInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedInputStream = null;
                    }
                } catch (IOException e19) {
                    httpURLConnectionHandleConnection = null;
                    e5 = e19;
                    bufferedInputStream = null;
                } catch (ArrayIndexOutOfBoundsException e20) {
                    httpURLConnectionHandleConnection = null;
                    e4 = e20;
                    bufferedInputStream = null;
                } catch (MalformedURLException e21) {
                    httpURLConnectionHandleConnection = null;
                    e3 = e21;
                    bufferedInputStream = null;
                } catch (Exception e22) {
                    httpURLConnectionHandleConnection = null;
                    e2 = e22;
                    bufferedInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    httpURLConnectionHandleConnection = null;
                    bufferedInputStream = null;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (IOException e23) {
            e23.printStackTrace();
        }
    }
}
