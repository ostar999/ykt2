package com.psychiatrygarden.activity.purchase.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.psychiatrygarden.activity.purchase.beans.FormFile;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.Md5Util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes5.dex */
public class SocketHttpRequester {
    public static String post(String path, Map<String, String> params, FormFile[] files, Handler handler) throws Exception {
        String str;
        String str2;
        int length;
        FormFile[] formFileArr = files;
        String str3 = "";
        try {
            int length2 = 0;
            for (FormFile formFile : formFileArr) {
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append("---------------------------7da2137580612");
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"" + formFile.getParameterName() + "\";filename=\"" + formFile.getFilname() + "\"\r\n");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Content-Type: ");
                sb2.append(formFile.getContentType());
                sb2.append("\r\n\r\n");
                sb.append(sb2.toString());
                sb.append("\r\n");
                int length3 = length2 + sb.length();
                if (formFile.getInStream() == null) {
                    length = formFile.getData().length;
                } else if (formFile.getFile() != null) {
                    length2 = (int) (length3 + formFile.getFile().length());
                } else {
                    length = formFile.getFileSize();
                }
                length2 = length3 + length;
            }
            params.put("app_id", "xiyizonghe_1861");
            StringBuilder sb3 = new StringBuilder();
            String str4 = "\r\n\r\n";
            String str5 = "Content-Type: ";
            sb3.append(System.currentTimeMillis());
            sb3.append("");
            params.put("t", sb3.toString());
            TreeMap treeMap = new TreeMap(params);
            for (Map.Entry entry : treeMap.entrySet()) {
                str3 = str3 + ((String) entry.getKey()) + "=" + ((String) entry.getValue());
            }
            treeMap.put("sign", Md5Util.MD5Encode(str3 + "e44d68345002ba51b4859f218a90fcb5"));
            StringBuilder sb4 = new StringBuilder();
            for (Map.Entry entry2 : treeMap.entrySet()) {
                sb4.append("--");
                sb4.append("---------------------------7da2137580612");
                sb4.append("\r\n");
                sb4.append("Content-Disposition: form-data; name=\"" + ((String) entry2.getKey()) + "\"\r\n\r\n");
                sb4.append((String) entry2.getValue());
                sb4.append("\r\n");
            }
            LogUtils.d("---------------------", sb4.toString());
            int length4 = sb4.toString().getBytes().length + length2 + "-----------------------------7da2137580612--\r\n".getBytes().length;
            URL url = new URL(path);
            int port = url.getPort() == -1 ? 80 : url.getPort();
            Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
            Log.i("hbgz", "socket connected is " + socket.isConnected());
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(("POST " + url.getPath() + " HTTP/1.1\r\n").getBytes());
            outputStream.write("Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n".getBytes());
            outputStream.write("Accept-Language: zh-CN\r\n".getBytes());
            outputStream.write("Content-Type: multipart/form-data; boundary=---------------------------7da2137580612\r\n".getBytes());
            outputStream.write(("Content-Length: " + length4 + "\r\n").getBytes());
            outputStream.write("Connection: Keep-Alive\r\n".getBytes());
            outputStream.write(("Host: " + url.getHost() + ":" + port + "\r\n").getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(sb4.toString().getBytes());
            System.out.println("文件的个数" + formFileArr.length);
            int length5 = formFileArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length5) {
                FormFile formFile2 = formFileArr[i2];
                StringBuilder sb5 = new StringBuilder();
                sb5.append("--");
                sb5.append("---------------------------7da2137580612");
                sb5.append("\r\n");
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Content-Disposition: form-data;name=\"");
                int i4 = length5;
                sb6.append(formFile2.getParameterName());
                sb6.append("\";filename=\"");
                sb6.append(formFile2.getFilname());
                sb6.append("\"\r\n");
                sb5.append(sb6.toString());
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str5);
                sb7.append(formFile2.getContentType());
                String str6 = str4;
                sb7.append(str6);
                sb5.append(sb7.toString());
                outputStream.write(sb5.toString().getBytes());
                InputStream inStream = formFile2.getInStream();
                if (inStream != null) {
                    byte[] bArr = new byte[1024];
                    str = str5;
                    while (true) {
                        int i5 = inStream.read(bArr, 0, 1024);
                        str2 = str6;
                        if (i5 == -1) {
                            break;
                        }
                        outputStream.write(bArr, 0, i5);
                        i3 += i5;
                        Message message = new Message();
                        message.arg1 = 11;
                        message.obj = Integer.valueOf(i3);
                        handler.sendMessage(message);
                        str6 = str2;
                    }
                    inStream.close();
                } else {
                    str = str5;
                    str2 = str6;
                    outputStream.write(formFile2.getData(), 0, formFile2.getData().length);
                }
                outputStream.write("\r\n".getBytes());
                i2++;
                length5 = i4;
                formFileArr = files;
                str5 = str;
                str4 = str2;
            }
            outputStream.write("-----------------------------7da2137580612--\r\n".getBytes());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"), 8);
            StringBuilder sb8 = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    System.out.print("okokokokokokoko" + sb8.toString());
                    socket.getInputStream().close();
                    outputStream.close();
                    bufferedReader.close();
                    socket.close();
                    return sb8.toString();
                }
                sb8.append(line + "\n");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String post(String path, Map<String, String> params, FormFile file, Handler handler) throws Exception {
        return post(path, params, new FormFile[]{file}, handler);
    }
}
