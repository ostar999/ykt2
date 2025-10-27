package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import android.os.Build;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.utils.DeviceUtils;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.StringUtils;
import com.umeng.analytics.pro.am;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: b, reason: collision with root package name */
    private static long f2911b = System.currentTimeMillis();

    private static String a(String str) {
        if (StringUtils.isBlank(str)) {
            return "-";
        }
        if (str == null || "".equals(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (char c3 : str.toCharArray()) {
            if (c3 != '\n' && c3 != '\r' && c3 != '\t' && c3 != '|') {
                sb.append(c3);
            }
        }
        return sb.toString();
    }

    public static String a(SendService sendService, String str, long j2, String str2, int i2, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        String str3;
        String str4 = null;
        if (i2 == 0) {
            return null;
        }
        try {
            String utdid = DeviceUtils.getUtdid(sendService.context);
            if (utdid == null) {
                LogUtil.e("get utdid failure, so build report failure, now return");
                return null;
            }
            String[] networkType = DeviceUtils.getNetworkType(sendService.context);
            String str5 = networkType[0];
            if (networkType.length > 1 && str5 != null && !"Wi-Fi".equals(str5)) {
                str4 = networkType[1];
            }
            String str6 = "" + (j2 > 0 ? j2 : System.currentTimeMillis());
            String strA = a(str2);
            String strA2 = a(String.valueOf(i2));
            String strA3 = a(StringUtils.convertObjectToString(obj));
            String strA4 = a(StringUtils.convertObjectToString(obj2));
            String strA5 = a(StringUtils.convertObjectToString(obj3));
            String strA6 = a(StringUtils.convertMapToString(map));
            String strA7 = a(DeviceUtils.getImei(sendService.context));
            String strA8 = a(DeviceUtils.getImsi(sendService.context));
            String strA9 = a(Build.BRAND);
            a(DeviceUtils.getCpuName());
            a(strA7);
            String strA10 = a(Build.MODEL);
            String strA11 = a(DeviceUtils.getResolution(sendService.context));
            String strA12 = a(DeviceUtils.getCarrier(sendService.context));
            String strA13 = a(str5);
            String strA14 = a(str4);
            String strA15 = a(str);
            String strA16 = a(sendService.appVersion);
            String strA17 = a(sendService.channel);
            String strA18 = a(sendService.userNick);
            String strA19 = a(sendService.userNick);
            a(DeviceUtils.getCountry());
            String strA20 = a(DeviceUtils.getLanguage());
            String str7 = sendService.appId;
            String str8 = am.av;
            String strA21 = a(Build.VERSION.RELEASE);
            String strA22 = a(utdid);
            String strA23 = a(sendService.country);
            StringUtils.isBlank("");
            if (str7 != null) {
                str3 = strA23;
                if (str7.contains("aliyunos")) {
                    str8 = "y";
                }
            } else {
                str3 = strA23;
            }
            HashMap map2 = new HashMap();
            map2.put(a.IMEI.toString(), strA7);
            map2.put(a.IMSI.toString(), strA8);
            map2.put(a.BRAND.toString(), strA9);
            map2.put(a.DEVICE_MODEL.toString(), strA10);
            map2.put(a.RESOLUTION.toString(), strA11);
            map2.put(a.CARRIER.toString(), strA12);
            map2.put(a.ACCESS.toString(), strA13);
            map2.put(a.ACCESS_SUBTYPE.toString(), strA14);
            map2.put(a.CHANNEL.toString(), strA17);
            map2.put(a.APPKEY.toString(), strA15);
            map2.put(a.APPVERSION.toString(), strA16);
            map2.put(a.LL_USERNICK.toString(), strA18);
            map2.put(a.USERNICK.toString(), strA19);
            map2.put(a.LL_USERID.toString(), "-");
            map2.put(a.USERID.toString(), "-");
            map2.put(a.LANGUAGE.toString(), strA20);
            map2.put(a.OS.toString(), str8);
            map2.put(a.OSVERSION.toString(), strA21);
            map2.put(a.SDKVERSION.toString(), "1.0");
            map2.put(a.START_SESSION_TIMESTAMP.toString(), "" + f2911b);
            map2.put(a.UTDID.toString(), strA22);
            map2.put(a.SDKTYPE.toString(), "mini");
            map2.put(a.RESERVE2.toString(), strA22);
            map2.put(a.RESERVE3.toString(), "-");
            map2.put(a.RESERVE4.toString(), "-");
            map2.put(a.RESERVE5.toString(), "-");
            map2.put(a.RESERVES.toString(), str3);
            map2.put(a.RECORD_TIMESTAMP.toString(), str6);
            map2.put(a.PAGE.toString(), strA);
            map2.put(a.EVENTID.toString(), strA2);
            map2.put(a.ARG1.toString(), strA3);
            map2.put(a.ARG2.toString(), strA4);
            map2.put(a.ARG3.toString(), strA5);
            map2.put(a.ARGS.toString(), strA6);
            return a(map2);
        } catch (Exception e2) {
            LogUtil.e("UTRestAPI buildTracePostReqDataObj catch!", e2);
            return "";
        }
    }

    public static String a(Map<String, String> map) {
        boolean z2;
        a aVar;
        StringBuffer stringBuffer = new StringBuffer();
        a[] aVarArrValues = a.values();
        int length = aVarArrValues.length;
        int i2 = 0;
        while (true) {
            String strConvertObjectToString = null;
            if (i2 >= length || (aVar = aVarArrValues[i2]) == a.ARGS) {
                break;
            }
            if (map.containsKey(aVar.toString())) {
                strConvertObjectToString = StringUtils.convertObjectToString(map.get(aVar.toString()));
                map.remove(aVar.toString());
            }
            stringBuffer.append(a(strConvertObjectToString));
            stringBuffer.append("||");
            i2++;
        }
        a aVar2 = a.ARGS;
        if (map.containsKey(aVar2.toString())) {
            stringBuffer.append(a(StringUtils.convertObjectToString(map.get(aVar2.toString()))));
            map.remove(aVar2.toString());
            z2 = false;
        } else {
            z2 = true;
        }
        for (String str : map.keySet()) {
            String strConvertObjectToString2 = map.containsKey(str) ? StringUtils.convertObjectToString(map.get(str)) : null;
            if (z2) {
                if ("StackTrace".equals(str)) {
                    stringBuffer.append("StackTrace=====>");
                    stringBuffer.append(strConvertObjectToString2);
                } else {
                    stringBuffer.append(a(str));
                    stringBuffer.append("=");
                    stringBuffer.append(strConvertObjectToString2);
                }
                z2 = false;
            } else if ("StackTrace".equals(str)) {
                stringBuffer.append(",");
                stringBuffer.append("StackTrace=====>");
                stringBuffer.append(strConvertObjectToString2);
            } else {
                stringBuffer.append(",");
                stringBuffer.append(a(str));
                stringBuffer.append("=");
                stringBuffer.append(strConvertObjectToString2);
            }
        }
        String string = stringBuffer.toString();
        if (StringUtils.isEmpty(string) || !string.endsWith("||")) {
            return string;
        }
        return string + "-";
    }

    public static d a(SendService sendService, String str, String str2, Context context, long j2, String str3, int i2, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        String str4 = "aliyunos";
        if (i2 == 0) {
            return null;
        }
        try {
            String utdid = DeviceUtils.getUtdid(sendService.context);
            if (utdid == null) {
                LogUtil.e("get utdid failure, so build report failure, now return");
                return null;
            }
            String[] networkType = DeviceUtils.getNetworkType(sendService.context);
            String str5 = networkType[0];
            String str6 = (networkType.length <= 1 || str5 == null || "Wi-Fi".equals(str5)) ? null : networkType[1];
            long jCurrentTimeMillis = j2 > 0 ? j2 : System.currentTimeMillis();
            String str7 = "" + jCurrentTimeMillis;
            String str8 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(jCurrentTimeMillis));
            String strA = a(str3);
            String strA2 = a(String.valueOf(i2));
            String strA3 = a(StringUtils.convertObjectToString(obj));
            String strA4 = a(StringUtils.convertObjectToString(obj2));
            String strA5 = a(StringUtils.convertObjectToString(obj3));
            String strA6 = a(StringUtils.convertMapToString(map));
            String strA7 = a(DeviceUtils.getImei(sendService.context));
            String strA8 = a(DeviceUtils.getImsi(sendService.context));
            String strA9 = a(Build.BRAND);
            String strA10 = a(DeviceUtils.getCpuName());
            String strA11 = a(strA7);
            String strA12 = a(Build.MODEL);
            String strA13 = a(DeviceUtils.getResolution(sendService.context));
            String strA14 = a(DeviceUtils.getCarrier(sendService.context));
            String strA15 = a(str5);
            String strA16 = a(str6);
            String strA17 = a(str);
            String strA18 = a(sendService.appVersion);
            String strA19 = a(sendService.channel);
            String strA20 = a(sendService.userNick);
            String strA21 = a(sendService.userNick);
            String strA22 = a(DeviceUtils.getCountry());
            String strA23 = a(DeviceUtils.getLanguage());
            String str9 = sendService.appId;
            if (str9 == null || !str9.contains("aliyunos")) {
                str4 = "Android";
            }
            String strA24 = a(Build.VERSION.RELEASE);
            String str10 = "" + f2911b;
            String strA25 = a(utdid);
            StringUtils.isBlank("");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("5.0.1");
            stringBuffer.append("||");
            stringBuffer.append(strA7);
            stringBuffer.append("||");
            stringBuffer.append(strA8);
            stringBuffer.append("||");
            stringBuffer.append(strA9);
            stringBuffer.append("||");
            stringBuffer.append(strA10);
            stringBuffer.append("||");
            stringBuffer.append(strA11);
            stringBuffer.append("||");
            stringBuffer.append(strA12);
            stringBuffer.append("||");
            stringBuffer.append(strA13);
            stringBuffer.append("||");
            stringBuffer.append(strA14);
            stringBuffer.append("||");
            stringBuffer.append(strA15);
            stringBuffer.append("||");
            stringBuffer.append(strA16);
            stringBuffer.append("||");
            stringBuffer.append(strA19);
            stringBuffer.append("||");
            stringBuffer.append(strA17);
            stringBuffer.append("||");
            stringBuffer.append(strA18);
            stringBuffer.append("||");
            stringBuffer.append(strA20);
            stringBuffer.append("||");
            stringBuffer.append(strA21);
            stringBuffer.append("||");
            stringBuffer.append("-");
            stringBuffer.append("||");
            stringBuffer.append(strA22);
            stringBuffer.append("||");
            stringBuffer.append(strA23);
            stringBuffer.append("||");
            stringBuffer.append(str4);
            stringBuffer.append("||");
            stringBuffer.append(strA24);
            stringBuffer.append("||");
            stringBuffer.append("mini");
            stringBuffer.append("||");
            stringBuffer.append("1.0");
            stringBuffer.append("||");
            stringBuffer.append(str10);
            stringBuffer.append("||");
            stringBuffer.append(strA25);
            stringBuffer.append("||");
            stringBuffer.append("-");
            stringBuffer.append("||");
            stringBuffer.append("-");
            stringBuffer.append("||");
            stringBuffer.append("-");
            stringBuffer.append("||");
            stringBuffer.append("-");
            stringBuffer.append("||");
            stringBuffer.append(str8);
            stringBuffer.append("||");
            stringBuffer.append(str7);
            stringBuffer.append("||");
            stringBuffer.append(strA);
            stringBuffer.append("||");
            stringBuffer.append(strA2);
            stringBuffer.append("||");
            stringBuffer.append(strA3);
            stringBuffer.append("||");
            stringBuffer.append(strA4);
            stringBuffer.append("||");
            stringBuffer.append(strA5);
            stringBuffer.append("||");
            stringBuffer.append(strA6);
            String string = stringBuffer.toString();
            HashMap map2 = new HashMap();
            map2.put("stm_x", string.getBytes());
            d dVar = new d();
            dVar.b(RestUrlWrapper.getSignedTransferUrl(str2, null, map2, context, strA17, strA19, strA18, str4, "", strA25));
            dVar.a(map2);
            return dVar;
        } catch (Exception e2) {
            LogUtil.e("UTRestAPI buildTracePostReqDataObj catch!", e2);
            return null;
        }
    }
}
