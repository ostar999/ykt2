package com.unity3d.player;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class k extends RelativeLayout {

    /* renamed from: a, reason: collision with root package name */
    UnityPlayer f24135a;

    /* renamed from: b, reason: collision with root package name */
    j f24136b;

    /* renamed from: c, reason: collision with root package name */
    Context f24137c;

    /* renamed from: d, reason: collision with root package name */
    int f24138d;

    /* renamed from: e, reason: collision with root package name */
    VideoView f24139e;

    /* renamed from: f, reason: collision with root package name */
    ImageView f24140f;

    /* renamed from: g, reason: collision with root package name */
    ImageView f24141g;

    /* renamed from: h, reason: collision with root package name */
    TextView f24142h;

    /* renamed from: i, reason: collision with root package name */
    Timer f24143i;

    /* renamed from: j, reason: collision with root package name */
    int f24144j;

    /* renamed from: k, reason: collision with root package name */
    int f24145k;

    /* renamed from: l, reason: collision with root package name */
    boolean f24146l;

    /* renamed from: m, reason: collision with root package name */
    TimerTask f24147m;

    public class a extends AsyncTask {

        /* renamed from: b, reason: collision with root package name */
        private String[] f24154b;

        public a() {
        }

        private static void a(String str) throws Throwable {
            HttpURLConnection httpURLConnection;
            HttpURLConnection httpURLConnection2 = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL("https://check.unity.cn/api/diagnosis").openConnection();
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
            }
            try {
                httpURLConnection.setConnectTimeout(30000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", MimeTypes.APP_JSON);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "tracking");
                jSONObject.put("reason", str);
                jSONObject.put("platform", "android");
                byte[] bytes = jSONObject.toString().getBytes();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
                httpURLConnection.getResponseCode();
                httpURLConnection.disconnect();
            } catch (Exception unused2) {
                httpURLConnection2 = httpURLConnection;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
            } catch (Throwable th2) {
                th = th2;
                httpURLConnection2 = httpURLConnection;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                throw th;
            }
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(String... strArr) throws Throwable {
            HttpURLConnection httpURLConnection;
            this.f24154b = strArr;
            HttpURLConnection httpURLConnection2 = null;
            if (strArr == null) {
                return null;
            }
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.f24154b;
                if (i2 >= strArr2.length) {
                    return null;
                }
                String str = strArr2[i2];
                if (str != null) {
                    try {
                        httpURLConnection = (HttpURLConnection) new URL(k.f(str)).openConnection();
                    } catch (Exception e2) {
                        e = e2;
                        httpURLConnection = null;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        try {
                            httpURLConnection.setConnectTimeout(30000);
                            httpURLConnection.setRequestMethod("GET");
                            int responseCode = httpURLConnection.getResponseCode();
                            if (responseCode >= 400) {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                                String str2 = "";
                                while (true) {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    str2 = str2 + line;
                                }
                                a(str + " - " + responseCode + " - " + str2);
                                bufferedReader.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            httpURLConnection2 = httpURLConnection;
                            if (httpURLConnection2 != null) {
                                httpURLConnection2.disconnect();
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        a(str + " - " + e.toString());
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        i2++;
                    }
                    httpURLConnection.disconnect();
                }
                i2++;
            }
        }
    }

    public k(Context context, UnityPlayer unityPlayer, j jVar) throws SecurityException, IllegalArgumentException {
        super(context);
        this.f24138d = 5;
        this.f24144j = 0;
        this.f24145k = 5;
        this.f24147m = new TimerTask() { // from class: com.unity3d.player.k.4
            @Override // java.util.TimerTask, java.lang.Runnable
            public final void run() {
                k.this.f24135a.runOnUiThread(new Runnable() { // from class: com.unity3d.player.k.4.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        k kVar = k.this;
                        if (kVar.f24146l) {
                            kVar.f24145k--;
                            kVar.f24142h.setText("Skip " + k.this.f24145k);
                            k kVar2 = k.this;
                            if (kVar2.f24145k <= 0) {
                                kVar2.i();
                            }
                        }
                    }
                });
            }
        };
        this.f24137c = context;
        this.f24135a = unityPlayer;
        this.f24136b = jVar;
        d();
        e();
        g();
    }

    private static String a(String str, String str2) {
        if (str == null || str.indexOf(63) == -1) {
            return null;
        }
        for (String str3 : str.split("\\?")[1].split("&")) {
            String[] strArrSplit = str3.split("=");
            if (str2.equals(strArrSplit[0])) {
                return strArrSplit[1];
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (!str.startsWith("deeplinker")) {
            if (e(str)) {
                c(str);
                return;
            }
            return;
        }
        String strF = f(a(str, "primaryUrl"));
        String strF2 = f(a(str, "fallbackUrl"));
        if (d(strF) && strF != null && e(strF)) {
            if (this.f24136b.h() != null) {
                new a().execute(this.f24136b.h());
            }
            c(strF);
        } else {
            if (strF2 == null || !e(strF2)) {
                return;
            }
            if (this.f24136b.i() != null) {
                new a().execute(this.f24136b.i());
            }
            c(strF2);
        }
    }

    private void c(String str) {
        if (str == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        this.f24137c.startActivity(intent);
        if (this.f24136b.e() != null) {
            new a().execute(this.f24136b.e());
        }
    }

    private void d() throws SecurityException, IllegalArgumentException {
        if (!"VIDEO".equals(this.f24136b.j())) {
            String strB = this.f24136b.b();
            if (this.f24136b.b() == null || this.f24136b.b() == "" || this.f24140f != null) {
                return;
            }
            if (strB.startsWith("file://")) {
                strB = strB.substring(7);
            }
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(strB);
            if (bitmapDecodeFile == null) {
                return;
            }
            this.f24140f = new ImageView(this.f24137c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(11);
            layoutParams.addRule(9);
            this.f24140f.setLayoutParams(layoutParams);
            this.f24140f.setImageBitmap(bitmapDecodeFile);
            this.f24140f.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(this.f24140f);
            return;
        }
        if (this.f24136b.k() == null || this.f24136b.k() == "" || this.f24139e != null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams2.addRule(11);
        layoutParams2.addRule(9);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.f24137c, Uri.parse(this.f24136b.k()));
        Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L, 2);
        ImageView imageView = new ImageView(this.f24137c);
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageBitmap(frameAtTime);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(imageView);
        VideoView videoView = new VideoView(this.f24137c);
        this.f24139e = videoView;
        videoView.setLayoutParams(layoutParams2);
        this.f24139e.setVideoPath(this.f24136b.k());
        addView(this.f24139e);
    }

    private boolean d(String str) {
        if (str == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        return this.f24137c.getPackageManager().resolveActivity(intent, 0) != null;
    }

    private void e() {
        if (this.f24135a.getShowSplashSlogan().booleanValue()) {
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("unity_splash_slogan", "drawable", this.f24137c.getPackageName()), new BitmapFactory.Options());
            this.f24141g = new ImageView(this.f24137c);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.f24135a.getShowSplashSloganHeight());
            layoutParams.addRule(11);
            layoutParams.addRule(9);
            layoutParams.addRule(12);
            this.f24141g.setLayoutParams(layoutParams);
            this.f24141g.setImageBitmap(bitmapDecodeResource);
            this.f24141g.setScaleType(ImageView.ScaleType.CENTER);
            this.f24141g.setBackgroundColor(-1);
            addView(this.f24141g);
        }
    }

    private static boolean e(String str) {
        if (str == null) {
            return false;
        }
        return str.startsWith("http");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String f(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Uri.encode(URLDecoder.decode(str, "UTF-8"), h.a.f27009d);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private void f() {
        this.f24142h = new TextView(this.f24137c);
        String str = "Skip " + this.f24138d;
        if (this.f24139e != null) {
            str = "Skip";
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.addRule(10);
        layoutParams.rightMargin = 48;
        layoutParams.topMargin = 72;
        this.f24142h.setLayoutParams(layoutParams);
        this.f24142h.setText(str);
        this.f24142h.setTextSize(15.0f);
        this.f24142h.setTextColor(-1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-7829368);
        gradientDrawable.setCornerRadius(12.0f);
        gradientDrawable.setStroke(3, -1);
        this.f24142h.setBackground(gradientDrawable);
        this.f24142h.setPadding(20, 5, 20, 5);
        this.f24142h.setAlpha(0.8f);
        this.f24142h.setOnClickListener(new View.OnClickListener() { // from class: com.unity3d.player.k.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                k.this.h();
            }
        });
        addView(this.f24142h);
    }

    private void g() {
        TextView textView = new TextView(this.f24137c);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(9);
        layoutParams.addRule(10);
        layoutParams.leftMargin = 32;
        layoutParams.topMargin = 64;
        textView.setLayoutParams(layoutParams);
        textView.setText("ADS");
        textView.setTextSize(8.0f);
        textView.setTextColor(-7829368);
        textView.setAlpha(0.8f);
        textView.setPadding(10, 5, 10, 5);
        addView(textView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        VideoView videoView = this.f24139e;
        if (videoView != null) {
            videoView.stopPlayback();
        }
        Timer timer = this.f24143i;
        if (timer != null) {
            timer.cancel();
        }
        this.f24135a.NotifySplashAdsFinished();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        if (this.f24136b.l() != null) {
            new a().execute(this.f24136b.l());
        }
        h();
    }

    public final void a() {
        if (this.f24136b.f() > 0) {
            int iF = this.f24136b.f();
            this.f24138d = iF;
            this.f24145k = iF;
        }
        f();
        VideoView videoView = this.f24139e;
        if (videoView != null) {
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.unity3d.player.k.2
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    k.this.i();
                }
            });
            this.f24139e.start();
        } else {
            Timer timer = new Timer();
            this.f24143i = timer;
            this.f24146l = true;
            timer.schedule(this.f24147m, 1000L, 1000L);
        }
        if (this.f24136b.d() != null) {
            new a().execute(this.f24136b.d());
        }
        setOnTouchListener(new View.OnTouchListener() { // from class: com.unity3d.player.k.3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return true;
                }
                k kVar = k.this;
                kVar.b(kVar.f24136b.c());
                return true;
            }
        });
    }

    public final void b() {
        VideoView videoView = this.f24139e;
        if (videoView != null) {
            videoView.pause();
            this.f24144j = this.f24139e.getCurrentPosition();
        }
        if (this.f24143i != null) {
            this.f24146l = false;
        }
    }

    public final void c() {
        VideoView videoView = this.f24139e;
        if (videoView != null) {
            videoView.start();
            this.f24139e.seekTo(this.f24144j);
        }
        if (this.f24143i != null) {
            this.f24146l = true;
        }
    }
}
