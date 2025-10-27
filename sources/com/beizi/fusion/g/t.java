package com.beizi.fusion.g;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.beizi.fusion.R;
import com.beizi.fusion.g.u;
import com.qq.e.comm.compliance.DownloadConfirmCallBack;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

/* loaded from: classes2.dex */
public class t extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private Context f5240a;

    /* renamed from: b, reason: collision with root package name */
    private int f5241b;

    /* renamed from: c, reason: collision with root package name */
    private DownloadConfirmCallBack f5242c;

    /* renamed from: d, reason: collision with root package name */
    private TextView f5243d;

    /* renamed from: e, reason: collision with root package name */
    private ImageView f5244e;

    /* renamed from: f, reason: collision with root package name */
    private Button f5245f;

    /* renamed from: g, reason: collision with root package name */
    private ViewGroup f5246g;

    /* renamed from: h, reason: collision with root package name */
    private ProgressBar f5247h;

    /* renamed from: i, reason: collision with root package name */
    private Button f5248i;

    /* renamed from: j, reason: collision with root package name */
    private String f5249j;

    public t(Context context, String str, DownloadConfirmCallBack downloadConfirmCallBack) {
        super(context, R.style.DownloadConfirmDialogFullScreen);
        this.f5240a = context;
        this.f5242c = downloadConfirmCallBack;
        this.f5249j = str;
        this.f5241b = context.getResources().getConfiguration().orientation;
        requestWindowFeature(1);
        setCanceledOnTouchOutside(true);
        b();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void cancel() {
        super.cancel();
        DownloadConfirmCallBack downloadConfirmCallBack = this.f5242c;
        if (downloadConfirmCallBack != null) {
            downloadConfirmCallBack.onCancel();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.f5244e) {
            DownloadConfirmCallBack downloadConfirmCallBack = this.f5242c;
            if (downloadConfirmCallBack != null) {
                downloadConfirmCallBack.onCancel();
            }
            dismiss();
            return;
        }
        if (view != this.f5245f) {
            if (view == this.f5248i) {
                a(this.f5249j);
            }
        } else {
            DownloadConfirmCallBack downloadConfirmCallBack2 = this.f5242c;
            if (downloadConfirmCallBack2 != null) {
                downloadConfirmCallBack2.onConfirm();
            }
            dismiss();
        }
    }

    @Override // android.app.Dialog
    public void onStart() {
        int iP = as.p(this.f5240a);
        int iO = as.o(this.f5240a);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        int i2 = this.f5241b;
        if (i2 == 1) {
            attributes.width = -1;
            attributes.height = (int) (iP * 0.6d);
            attributes.gravity = 80;
            attributes.windowAnimations = R.style.DownloadConfirmDialogAnimationUp;
        } else if (i2 == 2) {
            attributes.width = (int) (iO * 0.5d);
            attributes.height = -1;
            attributes.gravity = 5;
            attributes.windowAnimations = R.style.DownloadConfirmDialogAnimationRight;
        }
        attributes.dimAmount = 0.5f;
        window.setAttributes(attributes);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.beizi.fusion.g.t.2
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                try {
                    t.this.getWindow().setWindowAnimations(0);
                } catch (Throwable unused) {
                }
            }
        });
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        try {
            a(this.f5249j);
        } catch (Exception e2) {
            Log.e("BeiZis", "load error url:" + this.f5249j, e2);
        }
    }

    private void b() {
        setContentView(R.layout.download_confirm_dialog);
        View viewFindViewById = findViewById(R.id.download_confirm_root);
        int i2 = this.f5241b;
        if (i2 == 1) {
            viewFindViewById.setBackgroundResource(R.drawable.download_confirm_background_portrait);
        } else if (i2 == 2) {
            viewFindViewById.setBackgroundResource(R.drawable.download_confirm_background_landscape);
        }
        ImageView imageView = (ImageView) findViewById(R.id.download_confirm_close);
        this.f5244e = imageView;
        imageView.setOnClickListener(this);
        Button button = (Button) findViewById(R.id.download_confirm_reload_button);
        this.f5248i = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.download_confirm_confirm);
        this.f5245f = button2;
        button2.setOnClickListener(this);
        this.f5247h = (ProgressBar) findViewById(R.id.download_confirm_progress_bar);
        this.f5246g = (ViewGroup) findViewById(R.id.download_confirm_content);
        c();
    }

    private void c() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.download_confirm_holder);
        this.f5243d = new TextView(this.f5240a);
        ScrollView scrollView = new ScrollView(this.f5240a);
        scrollView.addView(this.f5243d);
        frameLayout.addView(scrollView);
    }

    public void a() {
        this.f5245f.setText("立即安装");
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            this.f5247h.setVisibility(8);
            this.f5246g.setVisibility(8);
            this.f5248i.setVisibility(0);
            this.f5248i.setText("抱歉，应用信息获取失败");
            this.f5248i.setEnabled(false);
            return;
        }
        new ae() { // from class: com.beizi.fusion.g.t.1
            @Override // android.os.AsyncTask
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onPostExecute(String str2) {
                t.this.f5247h.setVisibility(8);
                t.this.f5248i.setVisibility(8);
                t.this.f5246g.setVisibility(0);
                u.a aVarB = u.b(str2);
                if (aVarB == null) {
                    t.this.f5247h.setVisibility(8);
                    t.this.f5248i.setVisibility(0);
                    t.this.f5246g.setVisibility(8);
                    return;
                }
                t.this.f5243d.append("icon链接:\n");
                t.this.f5243d.append(aVarB.f5257a);
                t.this.f5243d.append("\n应用名:\n");
                t.this.f5243d.append(StrPool.TAB + aVarB.f5258b);
                t.this.f5243d.append("\n应用版本:\n");
                t.this.f5243d.append(StrPool.TAB + aVarB.f5259c);
                t.this.f5243d.append("\n开发者:\n");
                t.this.f5243d.append(StrPool.TAB + aVarB.f5260d);
                t.this.f5243d.append("\n应用大小:\n");
                t.this.f5243d.append(StrPool.TAB + t.a(aVarB.f5264h));
                t.this.f5243d.append("\n更新时间:\n");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                t.this.f5243d.append(StrPool.TAB + simpleDateFormat.format(new Date(aVarB.f5263g)));
                t.this.f5243d.append("\n隐私条款链接:\n");
                t.this.f5243d.append(aVarB.f5262f);
                t.this.f5243d.append("\n权限信息:\n");
                for (String str3 : aVarB.f5261e) {
                    t.this.f5243d.append(StrPool.TAB + str3 + "\n");
                }
                Linkify.addLinks(t.this.f5243d, Patterns.WEB_URL, (String) null, (Linkify.MatchFilter) null, new Linkify.TransformFilter() { // from class: com.beizi.fusion.g.t.1.1
                    @Override // android.text.util.Linkify.TransformFilter
                    public final String transformUrl(Matcher matcher, String str4) {
                        return matcher.group();
                    }
                });
                t.this.f5247h.setVisibility(8);
                t.this.f5248i.setVisibility(8);
                t.this.f5246g.setVisibility(0);
            }
        }.execute(str);
    }

    public static String a(long j2) {
        if (j2 <= 0) {
            return "0";
        }
        double d3 = j2;
        int iLog10 = (int) (Math.log10(d3) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.##").format(d3 / Math.pow(1024.0d, iLog10)) + " " + new String[]{"B", "kB", "MB", "GB", "TB"}[iLog10];
    }
}
