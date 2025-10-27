package com.umeng.commonsdk.framework;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.ao;
import com.umeng.analytics.pro.aq;
import com.umeng.analytics.pro.i;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.b;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UMEnvelopeBuild {
    public static boolean transmissionSendFlag = false;

    private static JSONObject add2CacheTable(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str, String str2, String str3) throws JSONException {
        if (jSONObject == null || jSONObject2 == null) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]构建信封传入 header 或 body 字段为空，直接返回");
            return null;
        }
        i iVarA = i.a(context);
        long jCurrentTimeMillis = System.currentTimeMillis();
        UUID uuidRandomUUID = UUID.randomUUID();
        ContentValues contentValues = new ContentValues();
        contentValues.put(aq.f22520e, str2);
        contentValues.put(aq.f22521f, iVarA.c(jSONObject.toString()));
        contentValues.put(aq.f22522g, iVarA.c(jSONObject2.toString()));
        contentValues.put(aq.f22523h, String.valueOf(jCurrentTimeMillis));
        contentValues.put(aq.f22524i, uuidRandomUUID.toString());
        contentValues.put(aq.f22525j, str);
        contentValues.put(aq.f22526k, str3);
        ao.a(context).a(aq.f22518c, contentValues);
        if (am.aC.equalsIgnoreCase(str2)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]inner业务，返回空 JSONObject。");
        } else if ("s".equalsIgnoreCase(str2)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]分享业务 返回body。");
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put("header", new JSONObject());
                jSONObject3.put("share", jSONObject2.getJSONObject("share"));
                return jSONObject3;
            } catch (JSONException unused) {
            }
        } else if (!"p".equalsIgnoreCase(str2)) {
            try {
                if ("st".equalsIgnoreCase(str2)) {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]统计业务 半开报文，返回body。");
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("header", new JSONObject());
                    jSONObject4.put("analytics", jSONObject2.getJSONObject("analytics"));
                    return jSONObject4;
                }
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]统计业务 闭合报文，返回body。");
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("header", new JSONObject());
                jSONObject5.put("analytics", jSONObject2.getJSONObject("analytics"));
                return jSONObject5;
            } catch (JSONException unused2) {
                return jSONObject2;
            }
        }
        return new JSONObject();
    }

    public static JSONObject buildEnvelopeWithExtHeader(Context context, JSONObject jSONObject, JSONObject jSONObject2) {
        return buildEnvelopeWithExtHeader(context, jSONObject, jSONObject2, UMServerURL.PATH_ANALYTICS, jSONObject.has("st") ? "t" : jSONObject2.has(am.au) ? am.aC : am.av, "9.4.7");
    }

    public static JSONObject buildZeroEnvelopeWithExtHeader(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) throws JSONException {
        JSONObject jSONObject3;
        if (UMUtils.isMainProgress(context)) {
            return new b().a(context.getApplicationContext(), jSONObject, jSONObject2, str);
        }
        JSONObject jSONObject4 = null;
        try {
            jSONObject3 = new JSONObject();
        } catch (JSONException unused) {
        }
        try {
            jSONObject3.put("exception", 120);
            return jSONObject3;
        } catch (JSONException unused2) {
            jSONObject4 = jSONObject3;
            return jSONObject4;
        }
    }

    public static long getLastInstantBuildTime(Context context) {
        if (context == null) {
            return 0L;
        }
        return UMFrUtils.getLastInstantBuildTime(context.getApplicationContext());
    }

    public static long getLastSuccessfulBuildTime(Context context) {
        if (context == null) {
            return 0L;
        }
        return UMFrUtils.getLastSuccessfulBuildTime(context.getApplicationContext());
    }

    public static synchronized boolean getTransmissionSendFlag() {
        return transmissionSendFlag;
    }

    public static String imprintProperty(Context context, String str, String str2) {
        return context == null ? str2 : ImprintHandler.getImprintService(context.getApplicationContext()).c().a(str, str2);
    }

    public static boolean isOnline(Context context) {
        return UMFrUtils.isOnline(context) && (UMConfigure.needSendZcfgEnv(context) ^ true);
    }

    public static boolean isReadyBuild(Context context, UMLogDataProtocol.UMBusinessType uMBusinessType) {
        return isRet(context, uMBusinessType, false);
    }

    public static boolean isReadyBuildNew(Context context, UMLogDataProtocol.UMBusinessType uMBusinessType) {
        if (getTransmissionSendFlag()) {
            return isRet(context, uMBusinessType, false);
        }
        return false;
    }

    public static boolean isReadyBuildStateless() {
        return getTransmissionSendFlag();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean isRet(android.content.Context r5, com.umeng.commonsdk.framework.UMLogDataProtocol.UMBusinessType r6, boolean r7) {
        /*
            if (r5 == 0) goto L43
            android.content.Context r0 = r5.getApplicationContext()
            boolean r1 = com.umeng.commonsdk.framework.UMFrUtils.isOnline(r0)
            int r2 = com.umeng.commonsdk.framework.UMFrUtils.envelopeFileNumber(r0)
            if (r1 == 0) goto L3c
            com.umeng.commonsdk.framework.UMLogDataProtocol$UMBusinessType r7 = com.umeng.commonsdk.framework.UMLogDataProtocol.UMBusinessType.U_INTERNAL
            r3 = 1
            r4 = 0
            if (r6 != r7) goto L1f
            boolean r5 = com.umeng.commonsdk.framework.UMFrUtils.hasEnvelopeFile(r0, r6)
            if (r5 == 0) goto L1d
            goto L2d
        L1d:
            r7 = r3
            goto L3c
        L1f:
            boolean r7 = com.umeng.commonsdk.framework.a.a()
            if (r7 == 0) goto L2f
            int r5 = com.umeng.commonsdk.framework.a.b()
            long r5 = (long) r5
            com.umeng.commonsdk.framework.UMWorkDispatch.sendDelayProcessMsg(r5)
        L2d:
            r7 = r4
            goto L3c
        L2f:
            boolean r6 = com.umeng.commonsdk.framework.UMFrUtils.hasEnvelopeFile(r0, r6)
            if (r6 != 0) goto L2d
            boolean r5 = com.umeng.commonsdk.UMConfigure.needSendZcfgEnv(r5)
            if (r5 == 0) goto L1d
            goto L2d
        L3c:
            if (r1 == 0) goto L43
            if (r2 <= 0) goto L43
            com.umeng.commonsdk.framework.a.d()
        L43:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.framework.UMEnvelopeBuild.isRet(android.content.Context, com.umeng.commonsdk.framework.UMLogDataProtocol$UMBusinessType, boolean):boolean");
    }

    public static long maxDataSpace(Context context) {
        if (context == null) {
            return 0L;
        }
        return b.a(context.getApplicationContext());
    }

    public static synchronized void setTransmissionSendFlag(boolean z2) {
        transmissionSendFlag = z2;
    }

    public static JSONObject buildEnvelopeWithExtHeader(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject3;
        JSONObject jSONObject4;
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]业务发起构建普通有状态信封请求。");
        JSONObject jSONObject5 = null;
        if (TextUtils.isEmpty(str)) {
            try {
                jSONObject3 = new JSONObject();
            } catch (JSONException unused) {
            }
            try {
                jSONObject3.put("exception", 121);
                return jSONObject3;
            } catch (JSONException unused2) {
                jSONObject5 = jSONObject3;
                return jSONObject5;
            }
        }
        if (!UMUtils.isMainProgress(context)) {
            try {
                jSONObject4 = new JSONObject();
            } catch (JSONException unused3) {
            }
            try {
                jSONObject4.put("exception", 120);
                return jSONObject4;
            } catch (JSONException unused4) {
                jSONObject5 = jSONObject4;
                return jSONObject5;
            }
        }
        if (UMConfigure.needSendZcfgEnv(context)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]零号报文应答数据 未获取到，写入二级缓存");
            return add2CacheTable(context, jSONObject, jSONObject2, str, str2, str3);
        }
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]零号报文应答数据 已获取到，判断二级缓存是否为空");
        if (ao.a(context).c()) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存为空，直接打信封");
            return new b().a(context.getApplicationContext(), jSONObject, jSONObject2, str, str2, str3);
        }
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]二级缓存不为空，写入二级缓存");
        JSONObject jSONObjectAdd2CacheTable = add2CacheTable(context, jSONObject, jSONObject2, str, str2, str3);
        if (!UMWorkDispatch.eventHasExist(32785)) {
            UMWorkDispatch.sendEvent(context, 32785, com.umeng.commonsdk.internal.b.a(context).a(), null);
        }
        return jSONObjectAdd2CacheTable;
    }
}
