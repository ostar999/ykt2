package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mobile.auth.gatewayauth.Constant;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.b.e;
import com.tencent.open.b.h;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.f;
import com.tencent.open.utils.k;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class ImageActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    RelativeLayout f18028a;

    /* renamed from: b, reason: collision with root package name */
    private QQToken f18029b;

    /* renamed from: c, reason: collision with root package name */
    private String f18030c;

    /* renamed from: d, reason: collision with root package name */
    private Handler f18031d;

    /* renamed from: e, reason: collision with root package name */
    private c f18032e;

    /* renamed from: f, reason: collision with root package name */
    private Button f18033f;

    /* renamed from: g, reason: collision with root package name */
    private Button f18034g;

    /* renamed from: h, reason: collision with root package name */
    private b f18035h;

    /* renamed from: i, reason: collision with root package name */
    private TextView f18036i;

    /* renamed from: j, reason: collision with root package name */
    private ProgressBar f18037j;

    /* renamed from: r, reason: collision with root package name */
    private String f18045r;

    /* renamed from: s, reason: collision with root package name */
    private Bitmap f18046s;

    /* renamed from: k, reason: collision with root package name */
    private int f18038k = 0;

    /* renamed from: l, reason: collision with root package name */
    private boolean f18039l = false;

    /* renamed from: m, reason: collision with root package name */
    private long f18040m = 0;

    /* renamed from: n, reason: collision with root package name */
    private int f18041n = 0;

    /* renamed from: o, reason: collision with root package name */
    private final int f18042o = 640;

    /* renamed from: p, reason: collision with root package name */
    private final int f18043p = 640;

    /* renamed from: q, reason: collision with root package name */
    private Rect f18044q = new Rect();

    /* renamed from: t, reason: collision with root package name */
    private final View.OnClickListener f18047t = new View.OnClickListener() { // from class: com.tencent.connect.avatar.ImageActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ImageActivity.this.f18037j.setVisibility(0);
            ImageActivity.this.f18034g.setEnabled(false);
            ImageActivity.this.f18034g.setTextColor(Color.rgb(21, 21, 21));
            ImageActivity.this.f18033f.setEnabled(false);
            ImageActivity.this.f18033f.setTextColor(Color.rgb(36, 94, 134));
            new Thread(new Runnable() { // from class: com.tencent.connect.avatar.ImageActivity.2.1
                @Override // java.lang.Runnable
                public void run() {
                    ImageActivity.this.c();
                }
            }).start();
            if (ImageActivity.this.f18039l) {
                ImageActivity.this.a("10657", 0L);
                return;
            }
            ImageActivity.this.a("10655", System.currentTimeMillis() - ImageActivity.this.f18040m);
            if (ImageActivity.this.f18032e.f18068b) {
                ImageActivity.this.a("10654", 0L);
            }
        }
    };

    /* renamed from: u, reason: collision with root package name */
    private final View.OnClickListener f18048u = new View.OnClickListener() { // from class: com.tencent.connect.avatar.ImageActivity.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ImageActivity.this.a("10656", System.currentTimeMillis() - ImageActivity.this.f18040m);
            ImageActivity.this.setResult(0);
            ImageActivity.this.d();
        }
    };

    /* renamed from: v, reason: collision with root package name */
    private final IUiListener f18049v = new DefaultUiListener() { // from class: com.tencent.connect.avatar.ImageActivity.5
        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onCancel() {
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onComplete(Object obj) throws JSONException {
            ImageActivity.this.f18034g.setEnabled(true);
            int i2 = -1;
            ImageActivity.this.f18034g.setTextColor(-1);
            ImageActivity.this.f18033f.setEnabled(true);
            ImageActivity.this.f18033f.setTextColor(-1);
            ImageActivity.this.f18037j.setVisibility(8);
            JSONObject jSONObject = (JSONObject) obj;
            try {
                i2 = jSONObject.getInt("ret");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (i2 != 0) {
                ImageActivity.this.a("设置出错了，请重新登录再尝试下呢：）", 1);
                e.a().a(ImageActivity.this.f18029b.getOpenId(), ImageActivity.this.f18029b.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, Constants.VIA_REPORT_TYPE_SET_AVATAR, Constants.VIA_ACT_TYPE_NINETEEN, "1");
                return;
            }
            ImageActivity.this.a("设置成功", 0);
            ImageActivity.this.a("10658", 0L);
            e.a().a(ImageActivity.this.f18029b.getOpenId(), ImageActivity.this.f18029b.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, Constants.VIA_REPORT_TYPE_SET_AVATAR, "3", "0");
            ImageActivity imageActivity = ImageActivity.this;
            if (imageActivity.f18030c != null && !"".equals(ImageActivity.this.f18030c)) {
                Intent intent = new Intent();
                intent.setClassName(imageActivity, ImageActivity.this.f18030c);
                if (imageActivity.getPackageManager().resolveActivity(intent, 0) != null) {
                    imageActivity.startActivity(intent);
                }
            }
            ImageActivity.this.a(0, jSONObject.toString(), null, null);
            ImageActivity.this.d();
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onError(UiError uiError) {
            ImageActivity.this.f18034g.setEnabled(true);
            ImageActivity.this.f18034g.setTextColor(-1);
            ImageActivity.this.f18033f.setEnabled(true);
            ImageActivity.this.f18033f.setTextColor(-1);
            ImageActivity.this.f18033f.setText("重试");
            ImageActivity.this.f18037j.setVisibility(8);
            ImageActivity.this.f18039l = true;
            ImageActivity.this.a(uiError.errorMessage, 1);
            ImageActivity.this.a("10660", 0L);
        }
    };

    /* renamed from: w, reason: collision with root package name */
    private final IUiListener f18050w = new DefaultUiListener() { // from class: com.tencent.connect.avatar.ImageActivity.6
        private void a(int i2) {
            if (ImageActivity.this.f18038k < 2) {
                ImageActivity.this.e();
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onCancel() {
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onComplete(Object obj) throws JSONException {
            JSONObject jSONObject = (JSONObject) obj;
            int i2 = -1;
            try {
                i2 = jSONObject.getInt("ret");
                if (i2 == 0) {
                    final String string = jSONObject.getString("nickname");
                    ImageActivity.this.f18031d.post(new Runnable() { // from class: com.tencent.connect.avatar.ImageActivity.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ImageActivity.this.c(string);
                        }
                    });
                    ImageActivity.this.a("10659", 0L);
                } else {
                    ImageActivity.this.a("10661", 0L);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (i2 != 0) {
                a(i2);
            }
        }

        @Override // com.tencent.tauth.DefaultUiListener, com.tencent.tauth.IUiListener
        public void onError(UiError uiError) {
            a(0);
        }
    };

    public class QQAvatarImp extends BaseApi {
        public QQAvatarImp(QQToken qQToken) {
            super(qQToken);
        }

        public void setAvator(Bitmap bitmap, IUiListener iUiListener) {
            Bundle bundleA = a();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            bitmap.recycle();
            BaseApi.TempRequestListener tempRequestListener = new BaseApi.TempRequestListener(iUiListener);
            bundleA.putByteArray("picture", byteArray);
            HttpUtils.requestAsync(this.f18095c, f.a(), "user/set_user_face", bundleA, "POST", tempRequestListener);
            e.a().a(this.f18095c.getOpenId(), this.f18095c.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, Constants.VIA_REPORT_TYPE_SET_AVATAR, Constants.VIA_ACT_TYPE_NINETEEN, "0");
        }
    }

    public class a extends View {
        public a(Context context) {
            super(context);
        }

        public void a(Button button) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            Drawable drawableB = ImageActivity.this.b("com.tencent.plus.blue_normal.png");
            Drawable drawableB2 = ImageActivity.this.b("com.tencent.plus.blue_down.png");
            Drawable drawableB3 = ImageActivity.this.b("com.tencent.plus.blue_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, drawableB2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, drawableB);
            stateListDrawable.addState(View.ENABLED_STATE_SET, drawableB);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, drawableB);
            stateListDrawable.addState(View.EMPTY_STATE_SET, drawableB3);
            button.setBackgroundDrawable(stateListDrawable);
        }

        public void b(Button button) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            Drawable drawableB = ImageActivity.this.b("com.tencent.plus.gray_normal.png");
            Drawable drawableB2 = ImageActivity.this.b("com.tencent.plus.gray_down.png");
            Drawable drawableB3 = ImageActivity.this.b("com.tencent.plus.gray_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, drawableB2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, drawableB);
            stateListDrawable.addState(View.ENABLED_STATE_SET, drawableB);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, drawableB);
            stateListDrawable.addState(View.EMPTY_STATE_SET, drawableB3);
            button.setBackgroundDrawable(stateListDrawable);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        setResult(0);
        d();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) throws NumberFormatException, IOException {
        requestWindowFeature(1);
        super.onCreate(bundle);
        setRequestedOrientation(1);
        setContentView(a());
        this.f18031d = new Handler();
        Bundle bundleExtra = getIntent().getBundleExtra(Constants.KEY_PARAMS);
        this.f18045r = bundleExtra.getString("picture");
        this.f18030c = bundleExtra.getString("return_activity");
        String string = bundleExtra.getString("appid");
        String string2 = bundleExtra.getString("access_token");
        long j2 = bundleExtra.getLong("expires_in");
        String string3 = bundleExtra.getString("openid");
        this.f18041n = bundleExtra.getInt(Constant.LOGIN_ACTIVITY_EXIT_ANIM);
        QQToken qQToken = new QQToken(string);
        this.f18029b = qQToken;
        qQToken.setAccessToken(string2, ((j2 - System.currentTimeMillis()) / 1000) + "");
        this.f18029b.setOpenId(string3);
        b();
        e();
        this.f18040m = System.currentTimeMillis();
        a("10653", 0L);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.f18032e.setImageBitmap(null);
        Bitmap bitmap = this.f18046s;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.f18046s.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        float fWidth = this.f18044q.width();
        Matrix imageMatrix = this.f18032e.getImageMatrix();
        float[] fArr = new float[9];
        imageMatrix.getValues(fArr);
        float f2 = fArr[2];
        float f3 = fArr[5];
        float f4 = fArr[0];
        float f5 = 640.0f / fWidth;
        Rect rect = this.f18044q;
        int i2 = (int) ((rect.left - f2) / f4);
        int i3 = i2 < 0 ? 0 : i2;
        int i4 = (int) ((rect.top - f3) / f4);
        int i5 = i4 < 0 ? 0 : i4;
        Matrix matrix = new Matrix();
        matrix.set(imageMatrix);
        matrix.postScale(f5, f5);
        int i6 = (int) (650.0f / f4);
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.f18046s, i3, i5, Math.min(this.f18046s.getWidth() - i3, i6), Math.min(this.f18046s.getHeight() - i5, i6), matrix, true);
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, 640, 640);
            bitmapCreateBitmap.recycle();
            a(bitmapCreateBitmap2);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            a(Constants.MSG_IMAGE_ERROR, 1);
            a(-5, null, Constants.MSG_IMAGE_ERROR, e2.getMessage());
            d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        finish();
        int i2 = this.f18041n;
        if (i2 != 0) {
            overridePendingTransition(0, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.f18038k++;
        new UserInfo(this, this.f18029b).getUserInfo(this.f18050w);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable b(String str) {
        return k.a(str, this);
    }

    private void b() throws IOException {
        Bitmap bitmapA;
        try {
            bitmapA = a(this.f18045r);
            this.f18046s = bitmapA;
        } catch (IOException e2) {
            e2.printStackTrace();
            a(Constants.MSG_IMAGE_ERROR, 1);
            a(-5, null, Constants.MSG_IMAGE_ERROR, e2.getMessage());
            d();
        }
        if (bitmapA != null) {
            this.f18032e.setImageBitmap(bitmapA);
            this.f18033f.setOnClickListener(this.f18047t);
            this.f18034g.setOnClickListener(this.f18048u);
            this.f18028a.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tencent.connect.avatar.ImageActivity.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ImageActivity.this.f18028a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ImageActivity imageActivity = ImageActivity.this;
                    imageActivity.f18044q = imageActivity.f18035h.a();
                    ImageActivity.this.f18032e.a(ImageActivity.this.f18044q);
                }
            });
            return;
        }
        throw new IOException("cannot read picture: '" + this.f18045r + "'!");
    }

    private String d(String str) {
        return str.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&amp;", "&");
    }

    private Bitmap a(String str) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i2 = 1;
        options.inJustDecodeBounds = true;
        Uri uri = Uri.parse(str);
        InputStream inputStreamOpenInputStream = getContentResolver().openInputStream(uri);
        if (inputStreamOpenInputStream == null) {
            return null;
        }
        try {
            BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        }
        inputStreamOpenInputStream.close();
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        while (i3 * i4 > 4194304) {
            i3 /= 2;
            i4 /= 2;
            i2 *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = i2;
        try {
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
        } catch (OutOfMemoryError e3) {
            e3.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, int i2) {
        Toast toastMakeText = Toast.makeText(this, str, 1);
        LinearLayout linearLayout = (LinearLayout) toastMakeText.getView();
        ((TextView) linearLayout.getChildAt(0)).setPadding(8, 0, 0, 0);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(com.tencent.connect.avatar.a.a(this, 16.0f), com.tencent.connect.avatar.a.a(this, 16.0f)));
        if (i2 == 0) {
            imageView.setImageDrawable(b("com.tencent.plus.ic_success.png"));
        } else {
            imageView.setImageDrawable(b("com.tencent.plus.ic_error.png"));
        }
        linearLayout.addView(imageView, 0);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        toastMakeText.setView(linearLayout);
        toastMakeText.setGravity(17, 0, 0);
        toastMakeText.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        String strD = d(str);
        if ("".equals(strD)) {
            return;
        }
        this.f18036i.setText(strD);
        this.f18036i.setVisibility(0);
    }

    private View a() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(-1, -1);
        ViewGroup.LayoutParams layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        this.f18028a = relativeLayout;
        relativeLayout.setLayoutParams(layoutParams);
        this.f18028a.setBackgroundColor(-16777216);
        RelativeLayout relativeLayout2 = new RelativeLayout(this);
        relativeLayout2.setLayoutParams(layoutParams3);
        this.f18028a.addView(relativeLayout2);
        c cVar = new c(this);
        this.f18032e = cVar;
        cVar.setLayoutParams(layoutParams2);
        this.f18032e.setScaleType(ImageView.ScaleType.MATRIX);
        relativeLayout2.addView(this.f18032e);
        this.f18035h = new b(this);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(layoutParams2);
        layoutParams4.addRule(14, -1);
        layoutParams4.addRule(15, -1);
        this.f18035h.setLayoutParams(layoutParams4);
        relativeLayout2.addView(this.f18035h);
        LinearLayout linearLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-2, com.tencent.connect.avatar.a.a(this, 80.0f));
        layoutParams5.addRule(14, -1);
        linearLayout.setLayoutParams(layoutParams5);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        this.f18028a.addView(linearLayout);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(com.tencent.connect.avatar.a.a(this, 24.0f), com.tencent.connect.avatar.a.a(this, 24.0f)));
        imageView.setImageDrawable(b("com.tencent.plus.logo.png"));
        linearLayout.addView(imageView);
        this.f18036i = new TextView(this);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(layoutParams3);
        layoutParams6.leftMargin = com.tencent.connect.avatar.a.a(this, 7.0f);
        this.f18036i.setLayoutParams(layoutParams6);
        this.f18036i.setEllipsize(TextUtils.TruncateAt.END);
        this.f18036i.setSingleLine();
        this.f18036i.setTextColor(-1);
        this.f18036i.setTextSize(24.0f);
        this.f18036i.setVisibility(8);
        linearLayout.addView(this.f18036i);
        RelativeLayout relativeLayout3 = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(-1, com.tencent.connect.avatar.a.a(this, 60.0f));
        layoutParams7.addRule(12, -1);
        layoutParams7.addRule(9, -1);
        relativeLayout3.setLayoutParams(layoutParams7);
        relativeLayout3.setBackgroundDrawable(b("com.tencent.plus.bar.png"));
        int iA = com.tencent.connect.avatar.a.a(this, 10.0f);
        relativeLayout3.setPadding(iA, iA, iA, 0);
        this.f18028a.addView(relativeLayout3);
        a aVar = new a(this);
        int iA2 = com.tencent.connect.avatar.a.a(this, 14.0f);
        int iA3 = com.tencent.connect.avatar.a.a(this, 7.0f);
        this.f18034g = new Button(this);
        this.f18034g.setLayoutParams(new RelativeLayout.LayoutParams(com.tencent.connect.avatar.a.a(this, 78.0f), com.tencent.connect.avatar.a.a(this, 45.0f)));
        this.f18034g.setText("取消");
        this.f18034g.setTextColor(-1);
        this.f18034g.setTextSize(18.0f);
        this.f18034g.setPadding(iA2, iA3, iA2, iA3);
        aVar.b(this.f18034g);
        relativeLayout3.addView(this.f18034g);
        this.f18033f = new Button(this);
        RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(com.tencent.connect.avatar.a.a(this, 78.0f), com.tencent.connect.avatar.a.a(this, 45.0f));
        layoutParams8.addRule(11, -1);
        this.f18033f.setLayoutParams(layoutParams8);
        this.f18033f.setTextColor(-1);
        this.f18033f.setTextSize(18.0f);
        this.f18033f.setPadding(iA2, iA3, iA2, iA3);
        this.f18033f.setText("选取");
        aVar.a(this.f18033f);
        relativeLayout3.addView(this.f18033f);
        TextView textView = new TextView(this);
        RelativeLayout.LayoutParams layoutParams9 = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams9.addRule(13, -1);
        textView.setLayoutParams(layoutParams9);
        textView.setText("移动和缩放");
        textView.setPadding(0, com.tencent.connect.avatar.a.a(this, 3.0f), 0, 0);
        textView.setTextSize(18.0f);
        textView.setTextColor(-1);
        relativeLayout3.addView(textView);
        this.f18037j = new ProgressBar(this);
        RelativeLayout.LayoutParams layoutParams10 = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams10.addRule(14, -1);
        layoutParams10.addRule(15, -1);
        this.f18037j.setLayoutParams(layoutParams10);
        this.f18037j.setVisibility(8);
        this.f18028a.addView(this.f18037j);
        return this.f18028a;
    }

    private void a(Bitmap bitmap) {
        new QQAvatarImp(this.f18029b).setAvator(bitmap, this.f18049v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final int i2) {
        this.f18031d.post(new Runnable() { // from class: com.tencent.connect.avatar.ImageActivity.4
            @Override // java.lang.Runnable
            public void run() {
                ImageActivity.this.b(str, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_ERROR_CODE, i2);
        intent.putExtra(Constants.KEY_ERROR_MSG, str2);
        intent.putExtra(Constants.KEY_ERROR_DETAIL, str3);
        intent.putExtra(Constants.KEY_RESPONSE, str);
        setResult(-1, intent);
    }

    public void a(String str, long j2) {
        a(str, j2, this.f18029b.getAppId());
    }

    public static void a(String str, long j2, String str2) {
        HashMap map = new HashMap();
        map.put("strValue", str2);
        map.put("nValue", str);
        map.put("qver", Constants.SDK_VERSION);
        if (j2 != 0) {
            map.put("elt", String.valueOf(j2));
        }
        h.a().a("https://cgi.qplus.com/report/report", map);
    }
}
