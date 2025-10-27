package com.tencent.liteav;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRExtInfo;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.yikaobang.yixue.R2;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
public class e {
    private static HashMap<String, a> A = new HashMap<>();

    /* renamed from: a, reason: collision with root package name */
    private static String f19262a = "TXCDataReport";

    /* renamed from: c, reason: collision with root package name */
    private String f19264c;

    /* renamed from: d, reason: collision with root package name */
    private Context f19265d;

    /* renamed from: f, reason: collision with root package name */
    private long f19267f;

    /* renamed from: g, reason: collision with root package name */
    private long f19268g;

    /* renamed from: h, reason: collision with root package name */
    private long f19269h;

    /* renamed from: i, reason: collision with root package name */
    private long f19270i;

    /* renamed from: j, reason: collision with root package name */
    private long f19271j;

    /* renamed from: k, reason: collision with root package name */
    private long f19272k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19273l;

    /* renamed from: m, reason: collision with root package name */
    private long f19274m;

    /* renamed from: v, reason: collision with root package name */
    private long f19283v;

    /* renamed from: z, reason: collision with root package name */
    private String f19287z;

    /* renamed from: o, reason: collision with root package name */
    private long f19276o = 0;

    /* renamed from: p, reason: collision with root package name */
    private long f19277p = 0;

    /* renamed from: q, reason: collision with root package name */
    private boolean f19278q = false;

    /* renamed from: r, reason: collision with root package name */
    private long f19279r = 0;

    /* renamed from: s, reason: collision with root package name */
    private long f19280s = 0;

    /* renamed from: t, reason: collision with root package name */
    private long f19281t = 0;

    /* renamed from: u, reason: collision with root package name */
    private long f19282u = 0;

    /* renamed from: w, reason: collision with root package name */
    private long f19284w = 0;

    /* renamed from: x, reason: collision with root package name */
    private int f19285x = 0;

    /* renamed from: y, reason: collision with root package name */
    private long f19286y = 0;
    private String B = "";
    private String C = "";

    /* renamed from: b, reason: collision with root package name */
    private HashMap f19263b = new HashMap(100);

    /* renamed from: e, reason: collision with root package name */
    private String f19266e = TXCCommonUtil.getAppVersion();

    /* renamed from: n, reason: collision with root package name */
    private int f19275n = 5000;

    public enum a {
        PENDING,
        CONFIRM,
        NEGATIVE
    }

    public e(Context context) {
        this.f19283v = 0L;
        this.f19265d = context.getApplicationContext();
        this.f19283v = 0L;
    }

    private void g() {
        a(6002, 6007, 6008);
        TXCStatus.a(this.B, 9001, Integer.valueOf(com.tencent.liteav.basic.util.h.a()[0]));
        a(9001, 9002, 9003);
        TXCStatus.a(this.B, 9004, Integer.valueOf(com.tencent.liteav.basic.util.h.b()));
        a(9004, 9005, R2.drawable.circle_biaoqing);
    }

    private int h() {
        long timeTick = TXCTimeUtil.getTimeTick();
        long jC = TXCStatus.c(this.B, R2.dimen.alivc_common_height_group_12);
        long j2 = this.f19276o;
        int i2 = 0;
        if (j2 != 0) {
            long j3 = timeTick - j2;
            long j4 = jC - this.f19277p;
            if (j3 > 0) {
                i2 = (int) ((j4 * 1000) / j3);
            }
        } else {
            long j5 = timeTick - this.f19284w;
            if (j5 > 0) {
                i2 = (int) ((1000 * jC) / j5);
            }
        }
        this.f19276o = timeTick;
        this.f19277p = jC;
        return i2;
    }

    private void i() {
        HashMap map = new HashMap();
        String strB = TXCStatus.b(this.B, R2.dimen.dp_509);
        String strB2 = TXCStatus.b(this.B, R2.dimen.dp_50_);
        String strB3 = TXCStatus.b(this.B, R2.dimen.dp_51);
        int iC = TXCStatus.c(this.B, R2.dimen.dp_5);
        String strB4 = TXCStatus.b(this.B, R2.dimen.dp_50);
        int iC2 = TXCStatus.c(this.B, R2.dimen.dp_504);
        map.put("stream_url", strB);
        map.put("stream_id", strB2);
        map.put("bizid", strB3);
        map.put("err_code", String.valueOf(iC));
        map.put("err_info", strB4);
        map.put("channel_type", String.valueOf(iC2));
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = jCurrentTimeMillis - this.f19279r;
        map.put(com.umeng.analytics.pro.d.f22694p, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(this.f19279r)));
        map.put(com.umeng.analytics.pro.d.f22695q, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(jCurrentTimeMillis)));
        map.put("total_time", String.valueOf(j2));
        long jA = TXCStatus.a(this.B, 6003);
        long jA2 = TXCStatus.a(this.B, 6006);
        long jA3 = TXCStatus.a(this.B, 6005);
        long j3 = jA != 0 ? jA2 / jA : 0L;
        map.put("block_count", String.valueOf(jA));
        map.put("block_duration_max", String.valueOf(jA3));
        map.put("block_duration_avg", String.valueOf(j3));
        long j4 = this.f19282u;
        long j5 = j4 != 0 ? this.f19281t / j4 : 0L;
        map.put("jitter_cache_max", String.valueOf(this.f19280s));
        map.put("jitter_cache_avg", String.valueOf(j5));
        String strTxCreateToken = TXCDRApi.txCreateToken();
        int i2 = com.tencent.liteav.basic.datareport.a.ag;
        int i3 = com.tencent.liteav.basic.datareport.a.am;
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.command_id_comment = "LINKMIC";
        TXCDRApi.InitEvent(this.f19265d, strTxCreateToken, i2, i3, tXCDRExtInfo);
        TXCLog.d(f19262a, "report evt 40402: token=" + strTxCreateToken);
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            TXCLog.e(f19262a, "RealTimePlayStatisticInfo: " + str + " = " + str2);
            if (str != null && str.length() > 0 && str2 != null) {
                TXCDRApi.txSetEventValue(strTxCreateToken, i2, str, str2);
            }
        }
        TXCDRApi.nativeReportEvent(strTxCreateToken, i2);
        this.f19278q = false;
        this.f19279r = 0L;
        this.f19282u = 0L;
        this.f19281t = 0L;
        this.f19280s = 0L;
    }

    private void j() {
        this.f19273l = false;
        this.f19274m = 0L;
        this.f19285x = 0;
        String strB = this.C;
        if (TextUtils.isEmpty(strB)) {
            strB = TXCCommonUtil.getUserId();
        }
        if (TextUtils.isEmpty(strB)) {
            strB = com.tencent.liteav.basic.util.h.b(this.f19265d);
        }
        this.f19263b.put("str_user_id", strB);
        this.f19263b.put("str_device_type", TXCBuild.Model());
        this.f19263b.put("str_device_type", TXCBuild.Model());
        this.f19263b.put("u32_network_type", Integer.valueOf(com.tencent.liteav.basic.util.h.e(this.f19265d)));
        this.f19263b.put("token", com.tencent.liteav.basic.util.h.c());
        this.f19263b.put("str_package_name", com.tencent.liteav.basic.util.h.c(this.f19265d));
        this.f19263b.put("dev_uuid", com.tencent.liteav.basic.util.h.f(this.f19265d));
        this.f19263b.put("str_os_info", TXCBuild.Version());
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        this.f19283v = utcTimeTick;
        this.f19263b.put("u64_timestamp", Long.valueOf(utcTimeTick));
        this.f19284w = TXCTimeUtil.getTimeTick();
    }

    public void a() {
        j();
        this.f19268g = -1L;
        this.f19272k = -1L;
        this.f19269h = 0L;
        this.f19270i = 0L;
        this.f19271j = 0L;
        this.f19279r = System.currentTimeMillis();
        this.f19286y = TXCTimeUtil.getTimeTick();
    }

    public void b() {
        if (this.f19273l) {
            if (this.f19278q) {
                c(com.tencent.liteav.basic.datareport.a.Y);
            } else if (c() != a.NEGATIVE) {
                c(com.tencent.liteav.basic.datareport.a.V);
            }
            if (this.f19278q) {
                b(com.tencent.liteav.basic.datareport.a.Z);
            } else {
                b(com.tencent.liteav.basic.datareport.a.W);
            }
        } else {
            TXCLog.e(f19262a, "play " + this.f19264c + " failed");
            if (this.f19278q) {
                a(com.tencent.liteav.basic.datareport.a.X);
            } else {
                a(com.tencent.liteav.basic.datareport.a.U);
            }
        }
        if (this.f19278q) {
            i();
        }
        TXCStatus.a(this.B, R2.dimen.dp_500, (Object) 0L);
        TXCStatus.a(this.B, R2.attr.implementationMode, (Object) 0L);
        TXCStatus.a(this.B, 6001, (Object) 0L);
        TXCStatus.a(this.B, R2.dimen.dp_4_5, (Object) 0L);
        TXCStatus.a(this.B, R2.dimen.dp_501, (Object) 0L);
    }

    public a c() {
        Uri uri;
        try {
            uri = Uri.parse(this.f19287z);
        } catch (Exception e2) {
            TXCLog.e(f19262a, "check stream failed.", e2);
        }
        if (uri == null) {
            return a.PENDING;
        }
        final String host = uri.getHost();
        if (TextUtils.isEmpty(host)) {
            return a.PENDING;
        }
        String scheme = uri.getScheme();
        if (scheme == null) {
            return a.PENDING;
        }
        if (!scheme.equals("rtmp") && !scheme.equals("http") && !scheme.equals("https")) {
            return a.PENDING;
        }
        if (c(host)) {
            return a.CONFIRM;
        }
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        if (queryParameterNames != null && (queryParameterNames.contains("bizid") || queryParameterNames.contains("txTime") || queryParameterNames.contains("txSecret"))) {
            return a.CONFIRM;
        }
        if (A.containsKey(host)) {
            return A.get(host);
        }
        A.put(host, a.PENDING);
        new Thread(new Runnable() { // from class: com.tencent.liteav.e.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    boolean z2 = true;
                    com.tencent.liteav.network.a.e[] eVarArrA = com.tencent.liteav.network.a.a.a.c().a(new com.tencent.liteav.network.a.b(host, true), null);
                    int length = eVarArrA.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            z2 = false;
                            break;
                        }
                        com.tencent.liteav.network.a.e eVar = eVarArrA[i2];
                        if (eVar.a() && e.c(eVar.f19517a)) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    e.A.put(host, z2 ? a.CONFIRM : a.NEGATIVE);
                    TXCLog.d(e.f19262a, host + " isTencent " + z2);
                } catch (Exception e3) {
                    TXCLog.e(e.f19262a, "check dns failed.", e3);
                }
            }
        }).start();
        return a.PENDING;
    }

    public void d(String str) {
        this.B = str;
    }

    private void f(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith("room://")) {
            return;
        }
        str.split("/")[r3.length - 1].split(StrPool.UNDERLINE);
        TXCStatus.a(this.B, R2.dimen.dp_505, (Object) 3L);
    }

    public void d() {
        g();
        if (!this.f19273l) {
            long jA = TXCStatus.a(this.B, 6001);
            long jA2 = TXCStatus.a(this.B, R2.dimen.dp_4_5);
            long jA3 = TXCStatus.a(this.B, R2.attr.implementationMode);
            long jA4 = TXCStatus.a(this.B, R2.dimen.dp_501);
            if (jA > 0 && jA2 > 0 && jA4 > 0 && jA3 > 0) {
                a(this.f19278q ? com.tencent.liteav.basic.datareport.a.X : com.tencent.liteav.basic.datareport.a.U);
                this.f19275n = 5000;
                this.f19273l = true;
            }
            String strB = TXCStatus.b(this.B, R2.dimen.dp_510);
            if (strB != null) {
                b(strB);
            }
        }
        if (this.f19285x >= 3 && !this.f19273l) {
            a(this.f19278q ? com.tencent.liteav.basic.datareport.a.X : com.tencent.liteav.basic.datareport.a.U);
            this.f19275n = 5000;
            this.f19273l = true;
        }
        this.f19285x++;
        if (this.f19274m <= 0) {
            this.f19274m = TXCTimeUtil.getTimeTick();
        }
        if (TXCTimeUtil.getTimeTick() > this.f19274m + this.f19275n) {
            if (this.f19278q) {
                c(com.tencent.liteav.basic.datareport.a.Y);
                this.f19275n = 5000;
            } else {
                if (c() == a.NEGATIVE) {
                    return;
                }
                c(com.tencent.liteav.basic.datareport.a.V);
                int statusReportInterval = TXCDRApi.getStatusReportInterval();
                this.f19275n = statusReportInterval;
                if (statusReportInterval < 5000) {
                    this.f19275n = 5000;
                }
            }
            this.f19268g = TXCStatus.a(this.B, 6004);
            this.f19272k = TXCStatus.c(this.B, 2002);
            this.f19274m = TXCTimeUtil.getTimeTick();
        }
    }

    public void e(String str) {
        this.C = str;
    }

    private int g(String str) {
        Number number = (Number) this.f19263b.get(str);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    public void a(boolean z2) {
        this.f19278q = z2;
    }

    public void a(String str) {
        this.f19264c = str;
        b(str);
    }

    public void a(int i2, int i3, int i4) {
        if (i2 == 6002) {
            double d3 = TXCStatus.d(this.B, i2);
            if (d3 < 0.001d) {
                return;
            }
            double d4 = TXCStatus.d(this.B, i3);
            int iC = TXCStatus.c(this.B, i4) + 1;
            TXCStatus.a(this.B, i3, Double.valueOf(d4 + ((d3 - d4) / iC)));
            TXCStatus.a(this.B, i4, Integer.valueOf(iC));
            return;
        }
        double dC = TXCStatus.c(this.B, i2);
        if (dC < 0.001d) {
            return;
        }
        double d5 = TXCStatus.d(this.B, i3);
        int iC2 = TXCStatus.c(this.B, i4) + 1;
        TXCStatus.a(this.B, i3, Double.valueOf(d5 + ((dC - d5) / iC2)));
        TXCStatus.a(this.B, i4, Integer.valueOf(iC2));
    }

    public void b(String str) {
        if (str == null) {
            return;
        }
        this.f19287z = str;
    }

    private void b(int i2) {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f19264c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f19263b.get("token");
        TXCDRApi.InitEvent(this.f19265d, str, i2, com.tencent.liteav.basic.datareport.a.an, tXCDRExtInfo);
        a(i2, str);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, i2, "u64_end_timestamp", utcTimeTick);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_timestamp", utcTimeTick);
        double d3 = TXCStatus.d(this.B, 9002);
        TXCDRApi.txSetEventValue(str, i2, "u32_avg_cpu_usage", String.valueOf(d3));
        double d4 = TXCStatus.d(this.B, 9005);
        TXCDRApi.txSetEventValue(str, i2, "u32_avg_memory", String.valueOf(d4));
        String strValueOf = String.valueOf(this.f19283v);
        TXCDRApi.txSetEventValue(str, i2, "u64_begin_timestamp", strValueOf);
        long timeTick = (TXCTimeUtil.getTimeTick() - TXCStatus.a(this.B, R2.dimen.dp_500)) / 1000;
        TXCDRApi.txSetEventIntValue(str, i2, "u64_playtime", timeTick < 0 ? -1L : timeTick);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_result", timeTick < 0 ? -1L : timeTick);
        int iC = TXCStatus.c(this.B, R2.dimen.dp_5);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_err_code", iC);
        int iC2 = TXCStatus.c(this.B, 2004);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_speed_cnt", iC2);
        int iC3 = TXCStatus.c(this.B, 2008);
        long j2 = iC3;
        TXCDRApi.txSetEventIntValue(str, i2, "u64_audio_cache_avg", j2);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_avg_cache_time", j2);
        long jC = TXCStatus.c(this.B, 2003);
        TXCDRApi.txSetEventValue(str, i2, "u32_max_load", String.valueOf(jC));
        long jC2 = TXCStatus.c(this.B, 2001);
        TXCDRApi.txSetEventValue(str, i2, "u32_avg_load", String.valueOf(jC2));
        long jC3 = TXCStatus.c(this.B, 2002);
        TXCDRApi.txSetEventValue(str, i2, "u32_load_cnt", String.valueOf(jC3));
        int iC4 = TXCStatus.c(this.B, 2005);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_nodata_cnt", iC4);
        long j3 = jC2 * jC3;
        TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_block_time", j3);
        long j4 = this.f19267f;
        if (j4 < 0) {
            j4 = -1;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_i_frame", j4);
        int iC5 = TXCStatus.c(this.B, R2.dimen.abc_action_bar_overflow_padding_end_material);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_width", iC5);
        int iC6 = TXCStatus.c(this.B, R2.dimen.abc_action_bar_overflow_padding_start_material);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_height", iC6);
        double d5 = TXCStatus.d(this.B, 6007);
        TXCDRApi.txSetEventValue(str, i2, "u32_video_avg_fps", String.valueOf(d5));
        long jA = TXCStatus.a(this.B, 6003);
        long jA2 = TXCStatus.a(this.B, 6005);
        long jA3 = TXCStatus.a(this.B, 6006);
        long j5 = jA > 0 ? jA3 / jA : 0L;
        TXCDRApi.txSetEventIntValue(str, i2, "u64_block_duration_avg", j5);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_avg_block_time", j5);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_block_count", jA);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_block_time", jA3);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_block_duration_max", jA2);
        long jA4 = TXCStatus.a(this.B, R2.dimen.alivc_common_font_29);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_jitter_cache_max", jA4);
        long jA5 = TXCStatus.a(this.B, R2.dimen.alivc_common_font_28);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_jitter_cache_avg", jA5);
        TXCDRApi.txSetEventValue(str, i2, "u32_link_type", String.valueOf(TXCStatus.c(this.B, R2.dimen.dp_505)));
        int iC7 = TXCStatus.c(this.B, R2.dimen.dp_504);
        TXCDRApi.txSetEventValue(str, i2, "u32_channel_type", String.valueOf(iC7));
        int iC8 = TXCStatus.c(this.B, R2.dimen.dp_506);
        TXCDRApi.txSetEventValue(str, i2, "u32_ip_count_quic", String.valueOf(iC8));
        int iC9 = TXCStatus.c(this.B, R2.dimen.dp_507);
        TXCDRApi.txSetEventValue(str, i2, "u32_connect_count_quic", String.valueOf(iC9));
        int iC10 = TXCStatus.c(this.B, R2.dimen.dp_508);
        TXCDRApi.txSetEventValue(str, i2, "u32_connect_count_tcp", String.valueOf(iC10));
        TXCDRApi.txSetEventValue(str, i2, "str_app_version", this.f19266e);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_is_real_time", this.f19278q ? 1L : 0L);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_frame_black", TXCStatus.a(this.B, R2.dimen.abc_action_bar_stacked_tab_max_width));
        TXCDRApi.nativeReportEvent(str, i2);
        TXCLog.d(f19262a, "report evt " + i2 + ": token=" + str + "\nstr_user_id=" + this.f19263b.get("str_user_id") + "\ndev_uuid=" + this.f19263b.get("dev_uuid") + "\nstr_session_id=" + this.f19263b.get("str_session_id") + "\nstr_device_type=" + this.f19263b.get("str_device_type") + "\nstr_os_info=" + this.f19263b.get("str_os_info") + "\nstr_package_name=" + this.f19263b.get("str_package_name") + "\nu32_network_type=" + this.f19263b.get("u32_network_type") + "\nu32_server_ip=" + this.f19263b.get("u32_server_ip") + "\nstr_stream_url=" + this.f19263b.get("str_stream_url") + "\nu64_timestamp=" + this.f19263b.get("u64_timestamp") + "\nu32_avg_cpu_usage=" + d3 + "\nu32_avg_memory=" + d4 + "\nu32_first_i_frame=" + this.f19267f + "\nu32_video_width=" + iC5 + "\nu32_video_height=" + iC6 + "\nu32_video_avg_fps=" + d5 + "\nu32_speed_cnt=" + iC2 + "\nu32_nodata_cnt=" + iC4 + "\nu32_avg_cache_time=" + iC3 + "\nu32_avg_block_time=" + j5 + "\nu32_avg_load=" + jC2 + "\nu32_max_load=" + jC + "\nu32_video_block_time=" + jA3 + "\nu32_audio_block_time=" + j3 + "\nu32_load_cnt=" + jC3 + "\nu32_result=" + timeTick + "\nu64_err_code=" + iC + "\nu32_channel_type=" + iC7 + "\nu32_ip_count_quic=" + iC8 + "\nu32_connect_count_quic=" + iC9 + "\nu32_connect_count_tcp=" + iC10 + "\nu64_block_count=" + jA + "\nu64_jitter_cache_max=" + jA4 + "\nu64_jitter_cache_avg=" + jA5 + "\nu64_begin_timestamp=" + strValueOf + "\nu32_is_real_time=" + TXCStatus.a(this.B, 2009) + "\nstr_app_version=" + this.f19266e);
    }

    private void a(int i2) {
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        long j9;
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f19264c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.f19263b.get("token");
        TXCDRApi.InitEvent(this.f19265d, str, i2, com.tencent.liteav.basic.datareport.a.an, tXCDRExtInfo);
        a(i2, str);
        Object obj = this.f19263b.get("u64_timestamp");
        TXCDRApi.txSetEventIntValue(str, i2, "u64_timestamp", obj != null ? ((Long) obj).longValue() : TXCTimeUtil.getUtcTimeTick());
        long jA = TXCStatus.a(this.B, R2.dimen.dp_500);
        long jA2 = TXCStatus.a(this.B, R2.dimen.dp_501);
        if (jA2 != -1) {
            jA2 -= jA;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_dns_time", jA2 < 0 ? -1L : jA2);
        long jA3 = TXCStatus.a(this.B, R2.dimen.dp_502);
        if (jA3 != -1) {
            jA3 -= jA;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_connect_server_time", jA3 < 0 ? -1L : jA3);
        int iC = TXCStatus.c(this.B, 5004);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_decode_type", iC);
        long jA4 = TXCStatus.a(this.B, 6001) - this.f19286y;
        this.f19267f = jA4;
        if (jA4 < 0) {
            jA4 = -1;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_i_frame", jA4);
        long jA5 = TXCStatus.a(this.B, R2.dimen.dp_4_) - this.f19286y;
        if (jA5 < 0) {
            j3 = jA5;
            j2 = -1;
        } else {
            j2 = jA5;
            j3 = j2;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_frame_down", j2);
        long jA6 = TXCStatus.a(this.B, 5005) - this.f19286y;
        if (jA6 < 0) {
            j5 = jA6;
            j4 = -1;
        } else {
            j4 = jA6;
            j5 = j4;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_video_decode_time", j4);
        long jA7 = TXCStatus.a(this.B, R2.dimen.dp_4_5) - this.f19286y;
        if (jA7 < 0) {
            j7 = jA7;
            j6 = -1;
        } else {
            j6 = jA7;
            j7 = j6;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_audio_frame_down", j6);
        long jA8 = TXCStatus.a(this.B, R2.attr.implementationMode) - this.f19286y;
        if (jA8 < 0) {
            j9 = jA8;
            j8 = -1;
        } else {
            j8 = jA8;
            j9 = j8;
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_first_audio_render_time", j8);
        int iC2 = TXCStatus.c(this.B, R2.dimen.dp_5);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_err_code", iC2);
        int iC3 = TXCStatus.c(this.B, R2.dimen.dp_50);
        TXCDRApi.txSetEventIntValue(str, i2, "str_err_info", iC3);
        int iC4 = TXCStatus.c(this.B, R2.dimen.dp_505);
        TXCDRApi.txSetEventValue(str, i2, "u32_link_type", String.valueOf(iC4));
        int iC5 = TXCStatus.c(this.B, R2.dimen.dp_504);
        TXCDRApi.txSetEventValue(str, i2, "u32_channel_type", String.valueOf(iC5));
        TXCDRApi.txSetEventValue(str, i2, "str_app_version", this.f19266e);
        TXCDRApi.nativeReportEvent(str, i2);
        TXCLog.d(f19262a, "report evt " + i2 + ": token=" + str + "\nstr_user_id=" + this.f19263b.get("str_user_id") + "\ndev_uuid=" + this.f19263b.get("dev_uuid") + "\nstr_session_id=" + this.f19263b.get("str_session_id") + "\nstr_device_type=" + this.f19263b.get("str_device_type") + "\nstr_os_info=" + this.f19263b.get("str_os_info") + "\nstr_package_name=" + this.f19263b.get("str_package_name") + "\nu32_network_type=" + this.f19263b.get("u32_network_type") + "\nu32_server_ip=" + this.f19263b.get("u32_server_ip") + "\nstr_stream_url=" + this.f19263b.get("str_stream_url") + "\nu64_timestamp=" + this.f19263b.get("u64_timestamp") + "\nu32_dns_time=" + jA2 + "\nu32_connect_server_time=" + jA3 + "\nu32_video_decode_type=" + iC + "\nu32_first_frame_down=" + j3 + "\nu32_first_video_decode_time=" + j5 + "\nu32_first_i_frame=" + this.f19267f + "\nu32_first_audio_frame_down=" + j7 + "\nu32_first_audio_render_time=" + j9 + "\nu64_err_code=" + iC2 + "\nstr_err_info=" + iC3 + "\nu32_link_type=" + iC4 + "\nu32_channel_type=" + iC5 + "\nstr_app_version=" + this.f19266e);
    }

    public static boolean c(String str) {
        if (str == null || !str.contains("myqcloud")) {
            return com.tencent.liteav.basic.c.c.a().a(str);
        }
        return true;
    }

    private void c(int i2) {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.f19264c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = true;
        String str = (String) this.f19263b.get("token");
        TXCDRApi.InitEvent(this.f19265d, str, i2, com.tencent.liteav.basic.datareport.a.an, tXCDRExtInfo);
        a(i2, str);
        TXCDRApi.txSetEventIntValue(str, i2, "u64_timestamp", TXCTimeUtil.getUtcTimeTick());
        int[] iArrA = com.tencent.liteav.basic.util.h.a();
        TXCDRApi.txSetEventIntValue(str, i2, "u32_cpu_usage", iArrA[1]);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_app_cpu_usage", iArrA[0]);
        TXCDRApi.txSetEventValue(str, i2, "u32_avg_cpu_usage", String.valueOf(TXCStatus.d(this.B, 9002)));
        TXCDRApi.txSetEventValue(str, i2, "u32_avg_memory", String.valueOf(TXCStatus.d(this.B, 9005)));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_recv_av_diff_time", TXCStatus.a(this.B, R2.dimen.alivc_common_height_group_100));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_play_av_diff_time", TXCStatus.a(this.B, R2.dimen.alivc_common_height_cv_150));
        TXCDRApi.txSetEventValue(str, i2, "u64_playtime", String.valueOf((TXCTimeUtil.getUtcTimeTick() - this.f19283v) / 1000));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_decode_type", TXCStatus.c(this.B, 2015) == 0 ? 2 : 1);
        long jA = TXCStatus.a(this.B, 2002);
        long j2 = this.f19272k;
        if (j2 == -1) {
            Long l2 = 0L;
            TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_block_count", l2.longValue());
        } else if (jA >= j2) {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_block_count", jA - j2);
        } else {
            Long l3 = -1L;
            TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_block_count", l3.longValue());
        }
        this.f19272k = jA;
        TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_cache_time", TXCStatus.c(this.B, 2010));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_drop", TXCStatus.c(this.B, 2014));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_decode_type", TXCStatus.c(this.B, 5004));
        int iD = (int) TXCStatus.d(this.B, 5007);
        if (iD == 0) {
            iD = h();
        }
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_recv_fps", iD);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_fps", (int) TXCStatus.d(this.B, 6002));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_cache_time", TXCStatus.c(this.B, R2.dimen.alivc_common_font_25));
        long jA2 = TXCStatus.a(this.B, R2.dimen.alivc_common_font_28);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_cache_count", jA2);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_avg_cache_count", jA2);
        long jA3 = TXCStatus.a(this.B, 6004);
        long j3 = this.f19268g;
        if (j3 != -1 && jA3 >= j3) {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_video_block_count", jA3 - j3);
        } else {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_video_block_count", 0L);
        }
        this.f19268g = jA3;
        int iC = TXCStatus.c(this.B, R2.dimen.dp_499);
        int iC2 = TXCStatus.c(this.B, R2.dimen.dp_498);
        long j4 = iC + iC2;
        TXCDRApi.txSetEventIntValue(str, i2, "u32_net_speed", j4);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_avg_net_speed", j4);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_avg_audio_bitrate", iC);
        TXCDRApi.txSetEventIntValue(str, i2, "u32_avg_video_bitrate", iC2);
        TXCDRApi.txSetEventValue(str, i2, "u32_link_type", String.valueOf(TXCStatus.c(this.B, R2.dimen.dp_505)));
        TXCDRApi.txSetEventValue(str, i2, "u32_channel_type", String.valueOf(TXCStatus.c(this.B, R2.dimen.dp_504)));
        TXCDRApi.txSetEventValue(str, i2, "str_app_version", this.f19266e);
        long jA4 = TXCStatus.a(this.B, R2.dimen.abc_action_bar_icon_vertical_padding_material);
        long j5 = this.f19269h;
        if (jA4 > j5) {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_video_light_block_count", jA4 - j5);
        } else {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_video_light_block_count", 0L);
        }
        this.f19269h = jA4;
        long jA5 = TXCStatus.a(this.B, 6003);
        long j6 = this.f19270i;
        if (jA5 > j6) {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_video_large_block_count", jA5 - j6);
        } else {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_video_large_block_count", 0L);
        }
        this.f19270i = jA5;
        long jC = TXCStatus.c(this.B, R2.attr.in_circle_color);
        long j7 = this.f19271j;
        if (jC > j7) {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_jitter_60ms_count", jC - j7);
        } else {
            TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_jitter_60ms_count", 0L);
        }
        this.f19271j = jC;
        TXCDRApi.txSetEventIntValue(str, i2, "u32_video_decode_fail", TXCStatus.c(this.B, 5006));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_audio_decode_fail", TXCStatus.c(this.B, R2.attr.indeterminateAnimationType));
        TXCDRApi.nativeReportEvent(str, i2);
        if (this.f19278q) {
            this.f19282u++;
            this.f19281t += jA2;
            if (jA2 > this.f19280s) {
                this.f19280s = jA2;
            }
        }
    }

    private void a(int i2, String str) {
        String strB = TXCStatus.b(this.B, R2.dimen.dp_512);
        if (TextUtils.isEmpty(strB)) {
            strB = str;
        }
        this.f19263b.put("str_session_id", strB);
        this.f19263b.put("u32_server_ip", TXCStatus.b(this.B, R2.dimen.dp_503));
        if (this.f19278q) {
            this.f19263b.put("str_stream_url", TXCStatus.b(this.B, R2.dimen.dp_509));
            f((String) this.f19263b.get("str_stream_url"));
        } else {
            this.f19263b.put("str_stream_url", TXCStatus.b(this.B, R2.dimen.dp_510));
        }
        TXCDRApi.txSetEventValue(str, i2, "str_user_id", (String) this.f19263b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, i2, "dev_uuid", (String) this.f19263b.get("dev_uuid"));
        TXCDRApi.txSetEventValue(str, i2, "str_session_id", (String) this.f19263b.get("str_session_id"));
        TXCDRApi.txSetEventValue(str, i2, "str_device_type", (String) this.f19263b.get("str_device_type"));
        TXCDRApi.txSetEventValue(str, i2, "str_os_info", (String) this.f19263b.get("str_os_info"));
        TXCDRApi.txSetEventValue(str, i2, "str_package_name", (String) this.f19263b.get("str_package_name"));
        TXCDRApi.txSetEventIntValue(str, i2, "u32_network_type", g("u32_network_type"));
        TXCDRApi.txSetEventValue(str, i2, "u32_server_ip", (String) this.f19263b.get("u32_server_ip"));
        TXCDRApi.txSetEventValue(str, i2, "str_stream_url", (String) this.f19263b.get("str_stream_url"));
    }
}
