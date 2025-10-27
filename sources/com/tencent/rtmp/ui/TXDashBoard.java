package com.tencent.rtmp.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.rtmp.TXLiveConstants;
import java.text.SimpleDateFormat;

/* loaded from: classes6.dex */
public class TXDashBoard extends LinearLayout {

    /* renamed from: a, reason: collision with root package name */
    protected TextView f20765a;

    /* renamed from: b, reason: collision with root package name */
    protected TextView f20766b;

    /* renamed from: c, reason: collision with root package name */
    protected ScrollView f20767c;

    /* renamed from: d, reason: collision with root package name */
    protected StringBuffer f20768d;

    /* renamed from: e, reason: collision with root package name */
    protected int f20769e;

    /* renamed from: f, reason: collision with root package name */
    private final SimpleDateFormat f20770f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20771g;

    public TXDashBoard(Context context) {
        this(context, null);
    }

    private void b() {
        if (this.f20765a != null) {
            return;
        }
        this.f20765a = new TextView(getContext());
        this.f20766b = new TextView(getContext());
        this.f20767c = new ScrollView(getContext());
        this.f20765a.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.f20765a.setTextColor(-49023);
        this.f20765a.setTypeface(Typeface.MONOSPACE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        this.f20767c.setPadding(0, 10, 0, 0);
        this.f20767c.setLayoutParams(layoutParams);
        this.f20767c.setVerticalScrollBarEnabled(true);
        this.f20767c.setScrollbarFadingEnabled(true);
        this.f20766b.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.f20766b.setTextColor(-49023);
        this.f20767c.addView(this.f20766b);
        addView(this.f20765a);
        addView(this.f20767c);
        if (this.f20768d.length() <= 0) {
            this.f20768d.append("liteav sdk version:" + TXCCommonUtil.getSDKVersionStr() + "\n");
        }
        this.f20766b.setText(this.f20768d.toString());
    }

    public void a(int i2, int i3, int i4, int i5) {
        TextView textView = this.f20765a;
        if (textView != null) {
            textView.setPadding(i2, i3, i4, 0);
        }
        ScrollView scrollView = this.f20767c;
        if (scrollView != null) {
            scrollView.setPadding(i2, 0, i4, i5);
        }
    }

    public void setEventTextSize(float f2) {
        TextView textView = this.f20766b;
        if (textView != null) {
            textView.setTextSize(f2);
        }
    }

    public void setLogMsgLenLimit(int i2) {
        this.f20769e = i2;
    }

    public void setShowLevel(int i2) {
        if (i2 == 0) {
            TextView textView = this.f20765a;
            if (textView != null) {
                textView.setVisibility(4);
            }
            ScrollView scrollView = this.f20767c;
            if (scrollView != null) {
                scrollView.setVisibility(4);
            }
            setVisibility(4);
            return;
        }
        if (i2 != 1) {
            b();
            this.f20765a.setVisibility(0);
            this.f20767c.setVisibility(0);
            setVisibility(0);
            return;
        }
        b();
        this.f20765a.setVisibility(0);
        this.f20767c.setVisibility(4);
        setVisibility(0);
    }

    public void setStatusTextSize(float f2) {
        TextView textView = this.f20765a;
        if (textView != null) {
            textView.setTextSize(f2);
        }
    }

    public TXDashBoard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f20768d = new StringBuffer("");
        this.f20769e = 3000;
        this.f20770f = new SimpleDateFormat("HH:mm:ss.SSS");
        this.f20771g = false;
        setOrientation(1);
        setVisibility(4);
    }

    public void a(CharSequence charSequence) {
        TextView textView = this.f20765a;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String str2 = StrPool.BRACKET_START + this.f20770f.format(Long.valueOf(System.currentTimeMillis())) + StrPool.BRACKET_END + str + "\n";
        if (this.f20768d.length() <= 0) {
            this.f20768d.append("liteav sdk version:" + TXCCommonUtil.getSDKVersionStr() + "\n");
        }
        while (this.f20768d.length() > this.f20769e) {
            int iIndexOf = this.f20768d.indexOf("\n");
            if (iIndexOf == 0) {
                iIndexOf = 1;
            }
            this.f20768d = this.f20768d.delete(0, iIndexOf);
        }
        StringBuffer stringBuffer = this.f20768d;
        stringBuffer.append(str2);
        this.f20768d = stringBuffer;
        TextView textView = this.f20766b;
        if (textView != null) {
            textView.setText(stringBuffer.toString());
        }
    }

    public void a(boolean z2) {
        this.f20771g = z2;
    }

    public void a() {
        this.f20768d.setLength(0);
        TextView textView = this.f20765a;
        if (textView != null) {
            textView.setText("");
        }
        TextView textView2 = this.f20766b;
        if (textView2 != null) {
            textView2.setText("");
        }
    }

    public void a(Bundle bundle, Bundle bundle2, int i2) {
        String string;
        ScrollView scrollView;
        TextView textView;
        TextView textView2;
        if (this.f20771g || i2 == 2011 || i2 == 2012) {
            return;
        }
        if (bundle != null && (textView2 = this.f20765a) != null) {
            textView2.setText(a(bundle));
        }
        if (this.f20768d.length() <= 0) {
            this.f20768d.append("liteav sdk version:" + TXCCommonUtil.getSDKVersionStr() + "\n");
        }
        if (bundle2 == null || (string = bundle2.getString(TXLiveConstants.EVT_DESCRIPTION)) == null || string.isEmpty()) {
            return;
        }
        a(i2, string);
        TextView textView3 = this.f20766b;
        if (textView3 != null) {
            textView3.setText(this.f20768d.toString());
        }
        if (getVisibility() != 0 || (scrollView = this.f20767c) == null || (textView = this.f20766b) == null) {
            return;
        }
        a(scrollView, textView);
    }

    public void a(int i2, String str) {
        if (i2 == 1020) {
            return;
        }
        String str2 = new SimpleDateFormat("HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis()));
        while (this.f20768d.length() > this.f20769e) {
            int iIndexOf = this.f20768d.indexOf("\n");
            if (iIndexOf == 0) {
                iIndexOf = 1;
            }
            this.f20768d = this.f20768d.delete(0, iIndexOf);
        }
        StringBuffer stringBuffer = this.f20768d;
        stringBuffer.append("\n[" + str2 + StrPool.BRACKET_END + str);
        this.f20768d = stringBuffer;
    }

    public String a(Bundle bundle) {
        String str;
        if (TextUtils.isEmpty(bundle.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO))) {
            str = "";
        } else {
            str = "AUDIO:" + bundle.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO);
        }
        return String.format("%-16s %-16s %-16s\n%-12s %-12s %-12s %-12s\n%-14s %-14s %-14s\n%-16s %-16s", "CPU:" + bundle.getString(TXLiveConstants.NET_STATUS_CPU_USAGE), "RES:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT), "SPD:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps", "JIT:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_JITTER), "FPS:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS), "GOP:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s", "ARA:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps", "QUE:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE) + " | " + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_V_SUM_CACHE_SIZE) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE) + " | " + bundle.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL) + "," + String.format("%.1f", Float.valueOf(bundle.getFloat(TXLiveConstants.NET_STATUS_AUDIO_CACHE_THRESHOLD))).toString(), "VRA:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps", "DRP:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_DROP) + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_DROP), "SVR:" + bundle.getString(TXLiveConstants.NET_STATUS_SERVER_IP), str);
    }

    private void a(ScrollView scrollView, View view) {
        if (scrollView == null || view == null) {
            return;
        }
        int measuredHeight = view.getMeasuredHeight() - scrollView.getMeasuredHeight();
        if (measuredHeight < 0) {
            measuredHeight = 0;
        }
        scrollView.scrollTo(0, measuredHeight);
    }
}
