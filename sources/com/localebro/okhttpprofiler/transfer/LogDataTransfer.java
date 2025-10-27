package com.localebro.okhttpprofiler.transfer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/* loaded from: classes4.dex */
public class LogDataTransfer implements DataTransfer {
    private static final int BODY_BUFFER_SIZE = 10485760;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String DELIMITER = "_";
    private static final String KEY_PARTS_COUNT = "PARTS_COUNT";
    private static final String KEY_TAG = "TAG";
    private static final String KEY_VALUE = "VALUE";
    private static final int LOG_LENGTH = 4000;
    private static final String LOG_PREFIX = "OKPRFL";
    private static final int SLOW_DOWN_PARTS_AFTER = 20;
    private final Handler mHandler;
    private static final Character HEADER_DELIMITER = ':';
    private static final Character SPACE = ' ';

    public static class LogBodyHandler extends Handler {
        @Override // android.os.Handler
        public void handleMessage(Message message) throws InterruptedException {
            Bundle data = message.getData();
            if (data != null) {
                if (data.getInt(LogDataTransfer.KEY_PARTS_COUNT, 0) > 20) {
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                String string = data.getString(LogDataTransfer.KEY_VALUE);
                String string2 = data.getString(LogDataTransfer.KEY_TAG);
                if (string == null || string2 == null) {
                    return;
                }
                Log.v(string2, string);
            }
        }

        private LogBodyHandler(Looper looper) {
            super(looper);
        }
    }

    public LogDataTransfer() {
        HandlerThread handlerThread = new HandlerThread("OkHttpProfiler", 10);
        handlerThread.start();
        this.mHandler = new LogBodyHandler(handlerThread.getLooper());
    }

    @SuppressLint({"LogNotTimber"})
    private void fastLog(String str, MessageType messageType, String str2) {
        String str3 = "OKPRFL_" + str + "_" + messageType.name;
        if (str2 != null) {
            Log.v(str3, str2);
        }
    }

    private void largeLog(String str, MessageType messageType, String str2) {
        int length = str2.length();
        if (str2.length() <= 4000) {
            logWithHandler(str, messageType, str2, 0);
            return;
        }
        int i2 = length / 4000;
        for (int i3 = 0; i3 <= i2; i3++) {
            int i4 = i3 * 4000;
            int i5 = i4 + 4000;
            if (i5 > length) {
                i5 = length;
            }
            logWithHandler(str, messageType, str2.substring(i4, i5), i2);
        }
    }

    private void logWithHandler(String str, MessageType messageType, String str2, int i2) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        String str3 = "OKPRFL_" + str + "_" + messageType.name;
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TAG, str3);
        bundle.putString(KEY_VALUE, str2);
        bundle.putInt(KEY_PARTS_COUNT, i2);
        messageObtainMessage.setData(bundle);
        this.mHandler.sendMessage(messageObtainMessage);
    }

    @Override // com.localebro.okhttpprofiler.transfer.DataTransfer
    public void sendDuration(String str, long j2) {
        logWithHandler(str, MessageType.RESPONSE_TIME, String.valueOf(j2), 0);
        logWithHandler(str, MessageType.RESPONSE_END, "-->", 0);
    }

    @Override // com.localebro.okhttpprofiler.transfer.DataTransfer
    public void sendException(String str, Exception exc) {
        logWithHandler(str, MessageType.RESPONSE_ERROR, exc.getLocalizedMessage(), 0);
    }

    @Override // com.localebro.okhttpprofiler.transfer.DataTransfer
    public void sendRequest(String str, Request request) throws IOException {
        fastLog(str, MessageType.REQUEST_METHOD, request.method());
        fastLog(str, MessageType.REQUEST_URL, request.url().getUrl());
        fastLog(str, MessageType.REQUEST_TIME, String.valueOf(System.currentTimeMillis()));
        Request requestBuild = request.newBuilder().build();
        Buffer buffer = new Buffer();
        RequestBody requestBodyBody = requestBuild.body();
        if (requestBodyBody != null) {
            MediaType contentType = requestBodyBody.getContentType();
            if (contentType != null) {
                fastLog(str, MessageType.REQUEST_HEADER, "Content-Type" + HEADER_DELIMITER + SPACE + contentType.getMediaType());
            }
            long jContentLength = requestBodyBody.contentLength();
            if (jContentLength != -1) {
                fastLog(str, MessageType.REQUEST_HEADER, "Content-Length" + HEADER_DELIMITER + SPACE + jContentLength);
            }
        }
        Headers headers = request.headers();
        for (String str2 : headers.names()) {
            if (!"Content-Type".equalsIgnoreCase(str2) && !"Content-Length".equalsIgnoreCase(str2)) {
                fastLog(str, MessageType.REQUEST_HEADER, str2 + HEADER_DELIMITER + SPACE + headers.get(str2));
            }
        }
        if (requestBodyBody != null) {
            requestBodyBody.writeTo(buffer);
            largeLog(str, MessageType.REQUEST_BODY, buffer.readString(Charset.defaultCharset()));
        }
    }

    @Override // com.localebro.okhttpprofiler.transfer.DataTransfer
    public void sendResponse(String str, Response response) throws IOException {
        largeLog(str, MessageType.RESPONSE_BODY, response.peekBody(10485760L).string());
        Headers headers = response.headers();
        logWithHandler(str, MessageType.RESPONSE_STATUS, String.valueOf(response.code()), 0);
        for (String str2 : headers.names()) {
            logWithHandler(str, MessageType.RESPONSE_HEADER, str2 + HEADER_DELIMITER + headers.get(str2), 0);
        }
    }
}
