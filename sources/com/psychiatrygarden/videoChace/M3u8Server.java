package com.psychiatrygarden.videoChace;

import android.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.psychiatrygarden.videoChace.NanoHTTPD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes6.dex */
public class M3u8Server extends NanoHTTPD {
    public static final int PORT = 30001;
    private static String TAG = "M3U8Server";
    private static NanoHTTPD server;

    public M3u8Server() {
        super(30001);
    }

    public static void execute() {
        try {
            if (server == null) {
                server = (NanoHTTPD) M3u8Server.class.newInstance();
            }
            server.start(5000, false);
        } catch (IOException e2) {
            Log.e(TAG, "启动服务失败：\n" + e2);
        } catch (Exception e3) {
            Log.e(TAG, "启动服务失败：\n" + e3);
        }
        Log.i(TAG, "服务启动成功\n");
        try {
            System.in.read();
        } catch (Throwable unused) {
        }
    }

    public static void finish() throws InterruptedException {
        NanoHTTPD nanoHTTPD = server;
        if (nanoHTTPD != null) {
            nanoHTTPD.stop();
            Log.i(TAG, "服务已经关闭");
            server = null;
        }
    }

    public String readFileByLines(String fileName) throws Throwable {
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    System.out.println("以行为单位读取文件内容，一次读一整行：");
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                    int i2 = 1;
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            System.out.println("line " + i2 + ": " + line);
                            sb.append(line);
                            i2++;
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return sb.toString();
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                    bufferedReader2.close();
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (IOException unused2) {
        }
        return sb.toString();
    }

    @Override // com.psychiatrygarden.videoChace.NanoHTTPD
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        String strValueOf = String.valueOf(session.getUri());
        Log.d(TAG, "请求URL：" + strValueOf);
        File file = new File(strValueOf);
        if (!file.exists()) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, "text/html", "文件不存在：" + strValueOf);
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            file.length();
            return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, strValueOf.contains(".m3u8") ? "video/x-mpegURL" : MimeTypes.VIDEO_MPEG, fileInputStream);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, "text/html", "文件不存在：" + strValueOf);
        }
    }
}
