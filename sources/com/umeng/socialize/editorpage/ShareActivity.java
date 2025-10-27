package com.umeng.socialize.editorpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import java.io.File;

/* loaded from: classes6.dex */
public class ShareActivity extends Activity implements View.OnClickListener {

    /* renamed from: c, reason: collision with root package name */
    private static final String f23663c = "ShareActivity";

    /* renamed from: d, reason: collision with root package name */
    private static int f23664d = 140;

    /* renamed from: a, reason: collision with root package name */
    protected ImageView f23665a;

    /* renamed from: f, reason: collision with root package name */
    private String f23668f;

    /* renamed from: g, reason: collision with root package name */
    private String f23669g;

    /* renamed from: h, reason: collision with root package name */
    private String f23670h;

    /* renamed from: i, reason: collision with root package name */
    private ResContainer f23671i;

    /* renamed from: j, reason: collision with root package name */
    private EditText f23672j;

    /* renamed from: k, reason: collision with root package name */
    private TextView f23673k;

    /* renamed from: l, reason: collision with root package name */
    private Context f23674l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f23675m;

    /* renamed from: n, reason: collision with root package name */
    private SHARE_MEDIA f23676n;

    /* renamed from: p, reason: collision with root package name */
    private ImageView f23678p;

    /* renamed from: q, reason: collision with root package name */
    private TextView f23679q;

    /* renamed from: e, reason: collision with root package name */
    private String f23667e = "7.1.7";

    /* renamed from: o, reason: collision with root package name */
    private boolean f23677o = false;

    /* renamed from: b, reason: collision with root package name */
    TextWatcher f23666b = new TextWatcher() { // from class: com.umeng.socialize.editorpage.ShareActivity.1
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            ShareActivity shareActivity = ShareActivity.this;
            shareActivity.f23675m = shareActivity.e();
        }
    };

    private String b(String str) {
        return str.equals("TENCENT") ? getResources().getString(this.f23671i.string("umeng_socialize_sharetotencent")) : str.equals("RENREN") ? getResources().getString(this.f23671i.string("umeng_socialize_sharetorenren")) : str.equals("DOUBAN") ? getResources().getString(this.f23671i.string("umeng_socialize_sharetodouban")) : str.equals("TWITTER") ? getResources().getString(this.f23671i.string("umeng_socialize_sharetotwitter")) : str.equals("LINKEDIN") ? getResources().getString(this.f23671i.string("umeng_socialize_sharetolinkin")) : getResources().getString(this.f23671i.string("umeng_socialize_sharetosina"));
    }

    private void c() {
        SHARE_MEDIA share_media;
        String string = this.f23672j.getText().toString();
        if (TextUtils.isEmpty(string.trim()) && this.f23676n == SHARE_MEDIA.SINA && (TextUtils.isEmpty(this.f23670h) || this.f23670h.equals("web") || this.f23670h.equals("video") || this.f23670h.equals("music"))) {
            Toast.makeText(this.f23674l, UmengText.SHARE.CONTEXT_EMPTY, 0).show();
            return;
        }
        if (SocializeUtils.countContentLength(string) <= f23664d || (share_media = this.f23676n) == SHARE_MEDIA.TWITTER || share_media == SHARE_MEDIA.LINKEDIN) {
            if (this.f23675m && this.f23676n != SHARE_MEDIA.TWITTER) {
                Toast.makeText(this.f23674l, UmengText.SHARE.CONTEXT_LONG, 0).show();
                return;
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("txt", string);
            bundle.putString("pic", this.f23670h);
            intent.putExtras(bundle);
            setResult(-1, intent);
            a();
        }
    }

    private void d() {
        this.f23670h = null;
        findViewById(this.f23671i.id("root")).setBackgroundColor(Color.parseColor("#D4E0E5"));
        findViewById(this.f23671i.id("umeng_socialize_share_bottom_area")).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        int iCountContentLength = f23664d - SocializeUtils.countContentLength(this.f23672j.getText().toString());
        this.f23673k.setText(SocializeUtils.countContentLength(this.f23672j.getText().toString()) + "/" + f23664d);
        return iCountContentLength < 0;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!SocializeConstants.BACKKEY_COMPLETE_CLOSE || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        new Handler().postDelayed(new Runnable() { // from class: com.umeng.socialize.editorpage.ShareActivity.2
            @Override // java.lang.Runnable
            public void run() {
                ShareActivity.this.setResult(1000);
                ShareActivity.this.finish();
            }
        }, 400L);
        return true;
    }

    public void onCancel(View view) {
        setResult(1000);
        a();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == this.f23671i.id("umeng_back")) {
            onCancel(view);
        } else if (id == this.f23671i.id("umeng_share_btn")) {
            c();
        } else if (id == this.f23671i.id("umeng_del")) {
            d();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        this.f23671i = ResContainer.get(this);
        this.f23677o = SocializeUtils.isFloatWindowStyle(this);
        super.onCreate(bundle);
        this.f23674l = this;
        setContentView(this.f23671i.layout("umeng_socialize_share"));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.softInputMode = 32;
        if (this.f23677o) {
            int[] floatWindowSize = SocializeUtils.getFloatWindowSize(this.f23674l);
            attributes.width = floatWindowSize[0];
            attributes.height = floatWindowSize[1];
        }
        getWindow().setAttributes(attributes);
        SLog.E(UmengText.SHARE.SHAREVIEWV + this.f23667e);
        Bundle extras = getIntent().getExtras();
        SHARE_MEDIA share_mediaA = a(extras.getString("media"));
        this.f23676n = share_mediaA;
        if (share_mediaA == SHARE_MEDIA.RENREN) {
            f23664d = 120;
        } else {
            f23664d = 140;
        }
        this.f23668f = extras.getString("txt");
        this.f23670h = extras.getString("pic");
        this.f23669g = extras.getString("title");
        b();
        this.f23678p = (ImageView) findViewById(this.f23671i.id("umeng_del"));
        this.f23672j.addTextChangedListener(this.f23666b);
        ((TextView) findViewById(this.f23671i.id("umeng_title"))).setText(b(extras.getString("media")));
        findViewById(this.f23671i.id("umeng_back")).setOnClickListener(this);
        findViewById(this.f23671i.id("umeng_share_btn")).setOnClickListener(this);
        this.f23678p.setOnClickListener(this);
        this.f23673k = (TextView) findViewById(this.f23671i.id("umeng_socialize_share_word_num"));
        this.f23675m = e();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 4 && keyEvent.getRepeatCount() == 0) {
            setResult(1000);
        }
        return super.onKeyDown(i2, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        return super.onKeyUp(i2, keyEvent);
    }

    @Override // android.app.Activity
    public void onResume() {
        this.f23672j.requestFocus();
        super.onResume();
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
    }

    private SHARE_MEDIA a(String str) {
        if (str.equals("TENCENT")) {
            return SHARE_MEDIA.TENCENT;
        }
        if (str.equals("RENREN")) {
            return SHARE_MEDIA.RENREN;
        }
        if (str.equals("DOUBAN")) {
            return SHARE_MEDIA.DOUBAN;
        }
        if (str.equals("TWITTER")) {
            return SHARE_MEDIA.TWITTER;
        }
        if (str.equals("LINKEDIN")) {
            return SHARE_MEDIA.LINKEDIN;
        }
        return SHARE_MEDIA.SINA;
    }

    private void b() {
        this.f23672j = (EditText) findViewById(this.f23671i.id("umeng_socialize_share_edittext"));
        if (!TextUtils.isEmpty(this.f23668f)) {
            this.f23672j.setText(this.f23668f);
            this.f23672j.setSelection(this.f23668f.length());
        }
        this.f23679q = (TextView) findViewById(this.f23671i.id("umeng_web_title"));
        this.f23665a = (ImageView) findViewById(this.f23671i.id("umeng_share_icon"));
        if (this.f23670h != null) {
            findViewById(this.f23671i.id("umeng_socialize_share_bottom_area")).setVisibility(0);
            ImageView imageView = (ImageView) findViewById(this.f23671i.id("umeng_share_icon"));
            this.f23665a = imageView;
            imageView.setVisibility(0);
            if (this.f23670h.equals("video")) {
                this.f23665a.setImageResource(ResContainer.getResourceId(this.f23674l, "drawable", "umeng_socialize_share_video"));
            } else if (this.f23670h.equals("music")) {
                this.f23665a.setImageResource(ResContainer.getResourceId(this.f23674l, "drawable", "umeng_socialize_share_music"));
            } else if (this.f23670h.equals("web")) {
                this.f23665a.setImageResource(ResContainer.getResourceId(this.f23674l, "drawable", "umeng_socialize_share_web"));
            } else {
                this.f23665a.setImageURI(Uri.fromFile(new File(this.f23670h)));
            }
            if (!TextUtils.isEmpty(this.f23669g)) {
                this.f23679q.setVisibility(0);
                this.f23679q.setText(this.f23669g);
            }
            findViewById(this.f23671i.id("root")).setBackgroundColor(-1);
            return;
        }
        if (TextUtils.isEmpty(this.f23669g)) {
            return;
        }
        this.f23665a.setImageResource(ResContainer.getResourceId(this.f23674l, "drawable", "umeng_socialize_share_web"));
        this.f23679q.setVisibility(0);
        this.f23679q.setText(this.f23669g);
    }

    public void a() {
        finish();
    }
}
