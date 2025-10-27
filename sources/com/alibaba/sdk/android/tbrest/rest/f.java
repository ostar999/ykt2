package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.request.BizRequest;
import com.alibaba.sdk.android.tbrest.request.UrlWrapper;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class f {
    public static boolean a(SendService sendService, String str, Context context, String str2, long j2, String str3, int i2, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        byte[] packRequest;
        try {
            LogUtil.i("RestAPI start send log!");
            String strA = e.a(sendService, str, j2, str3, i2, obj, obj2, obj3, map);
            if (!StringUtils.isNotBlank(strA)) {
                LogUtil.i("UTRestAPI build data failure!");
                return false;
            }
            LogUtil.i("RestAPI build data succ!");
            HashMap map2 = new HashMap(1);
            map2.put(String.valueOf(i2), strA);
            try {
                packRequest = BizRequest.getPackRequest(sendService, str, context, map2);
            } catch (Exception e2) {
                LogUtil.e(e2.toString());
                packRequest = null;
            }
            if (packRequest == null) {
                return false;
            }
            LogUtil.i("packRequest success!");
            return UrlWrapper.sendRequest(sendService, str2, packRequest).isSuccess();
        } catch (Throwable th) {
            LogUtil.e("system error!", th);
            return false;
        }
    }

    public static boolean b(SendService sendService, String str, Context context, String str2, long j2, String str3, int i2, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        byte[] packRequest;
        try {
            LogUtil.i("RestAPI start send log by url!");
            String strA = e.a(sendService, str, j2, str3, i2, obj, obj2, obj3, map);
            if (!StringUtils.isNotBlank(strA)) {
                LogUtil.i("UTRestAPI build data failure by url!");
                return false;
            }
            LogUtil.i("RestAPI build data succ by url!");
            HashMap map2 = new HashMap(1);
            map2.put(String.valueOf(i2), strA);
            try {
                packRequest = BizRequest.getPackRequest(sendService, str, context, map2);
            } catch (Exception e2) {
                LogUtil.e(e2.toString());
                packRequest = null;
            }
            if (packRequest == null) {
                return false;
            }
            LogUtil.i("packRequest success by url!");
            return UrlWrapper.sendRequest(sendService, str, str2, packRequest).isSuccess();
        } catch (Throwable th) {
            LogUtil.e("system error by url!", th);
            return false;
        }
    }

    @Deprecated
    public static String a(SendService sendService, String str, String str2, Context context, long j2, String str3, int i2, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        try {
            LogUtil.i("sendLogByUrl RestAPI start send log!");
            d dVarA = e.a(sendService, str2, str, context, j2, str3, i2, obj, obj2, obj3, map);
            if (dVarA != null) {
                LogUtil.i("sendLogByUrl RestAPI build data succ!");
                Map<String, Object> mapM63a = dVarA.m63a();
                if (mapM63a == null) {
                    LogUtil.i("sendLogByUrl postReqData is null!");
                    return null;
                }
                String strA = dVarA.a();
                if (StringUtils.isEmpty(strA)) {
                    LogUtil.i("sendLogByUrl reqUrl is null!");
                    return null;
                }
                byte[] bArrA = b.a(2, strA, mapM63a, true);
                if (bArrA != null) {
                    try {
                        String str4 = new String(bArrA, "UTF-8");
                        if (!StringUtils.isEmpty(str4)) {
                            return str4;
                        }
                    } catch (UnsupportedEncodingException e2) {
                        LogUtil.e("sendLogByUrl result encoding UTF-8 error!", e2);
                    }
                }
            } else {
                LogUtil.i("sendLogByUrl UTRestAPI build data failure!");
            }
        } catch (Throwable th) {
            LogUtil.e("sendLogByUrl system error!", th);
        }
        return null;
    }
}
