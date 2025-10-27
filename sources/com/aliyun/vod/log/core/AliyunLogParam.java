package com.aliyun.vod.log.core;

import cn.hutool.core.date.DatePattern;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.UUID;
import k.a;

/* loaded from: classes2.dex */
public class AliyunLogParam {
    public static String generatePushParams(Map<String, String> map, String str, String str2, String str3, String str4, int i2, String str5, String str6, String str7) {
        return "&t=" + String.valueOf(System.currentTimeMillis()) + "&" + AliyunLogKey.KEY_LOG_LEVEL + "=" + str2 + "&" + AliyunLogKey.KEY_LOG_VERSION + "=1&" + AliyunLogKey.KEY_PRODUCT + "=" + str + "&" + AliyunLogKey.KEY_MODULE + "=" + str3 + "&sm=" + str4 + "&hn=" + getHostIp() + "&bi=&ri=" + str5 + "&" + AliyunLogKey.KEY_EVENT + "=" + String.valueOf(i2) + "&" + AliyunLogKey.KEY_ARGS + "=" + transcodeArgs(map) + "&tt=" + AliyunLogCommon.TERMINAL_TYPE + "&" + AliyunLogKey.KEY_DEVICE_MODEL + "=" + AliyunLogCommon.DEVICE_MODEL + "&os=android&" + AliyunLogKey.KEY_OSVERSION + "=" + AliyunLogCommon.OS_VERSION + "&av=" + str7 + "&" + AliyunLogKey.KEY_UUID + "=" + AliyunLogCommon.UUID + "&" + AliyunLogKey.KEY_DEFINITION + "=&" + AliyunLogKey.KEY_CONNECTION + "=" + str6 + "&" + AliyunLogKey.KEY_USER_AGENT + "=&ui=" + a.f27524v + "&app_id=" + AliyunLogCommon.APPLICATION_ID + "&" + AliyunLogKey.KEY_CDN_IP + "=&r=&" + AliyunLogKey.KEY_APP_NAME + "=" + AliyunLogCommon.APPLICATION_NAME;
    }

    public static String generateRandom() {
        return UUID.randomUUID().toString();
    }

    public static String generateTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_PATTERN);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return simpleDateFormat.format(date);
    }

    public static String getHostIp() throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress()) {
                        return inetAddressNextElement.getHostAddress();
                    }
                }
            }
            return null;
        } catch (SocketException | Exception unused) {
            return null;
        }
    }

    public static String transcodeArgs(Map<String, String> map) {
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.lastIndexOf("&"));
            try {
                return URLEncoder.encode(sb.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }

    public static String generateTimestamp(long j2) {
        Date date = new Date(j2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.UTC_PATTERN);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return simpleDateFormat.format(date);
    }
}
