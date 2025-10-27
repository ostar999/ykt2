package com.psychiatrygarden.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.glideUtil.GlideImageView;
import com.psychiatrygarden.widget.glideUtil.transformation.BlurTransformation;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class RecdationDetailActivity extends Activity {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    public void init() {
        GlideImageView glideImageView = (GlideImageView) findViewById(R.id.backdroup);
        GlideImageView glideImageView2 = (GlideImageView) findViewById(R.id.header);
        TextView textView = (TextView) findViewById(R.id.title);
        TextView textView2 = (TextView) findViewById(R.id.time);
        TextView textView3 = (TextView) findViewById(R.id.desc);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.guanbi);
        textView.setText(getIntent().getStringExtra("title"));
        textView3.setText(getIntent().getStringExtra("description"));
        if (TextUtils.isEmpty(getIntent().getExtras().getString("times"))) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
        }
        textView2.setText(getIntent().getExtras().getString("times"));
        glideImageView2.load(getIntent().getStringExtra("cover_img"), R.drawable.app_icon, 5);
        glideImageView.fitCenter().load(getIntent().getStringExtra("cover_img"), R.drawable.imgplacehodel, new BlurTransformation(this, 25, 10));
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13543c.lambda$init$0(view);
            }
        });
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        SkinManager.onActivityCreateSetSkin(this);
        StatusBarUtil.setTransparent(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recdation_detail);
        init();
    }
}
