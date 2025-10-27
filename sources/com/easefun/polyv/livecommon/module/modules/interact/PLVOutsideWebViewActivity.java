package com.easefun.polyv.livecommon.module.modules.interact;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVSimpleUrlWebViewActivity;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVOutsideWebViewActivity extends PLVSimpleUrlWebViewActivity {
    public static void start(Context context, @NonNull String url) {
        Intent intent = new Intent(context, (Class<?>) PLVOutsideWebViewActivity.class);
        intent.putExtra(PLVSimpleUrlWebViewActivity.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity, com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(com.easefun.polyv.livecommon.R.drawable.plv_back_ic);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ConvertUtils.dp2px(32.0f), ConvertUtils.dp2px(32.0f));
        int iDp2px = ConvertUtils.dp2px(4.0f);
        layoutParams.topMargin = iDp2px;
        layoutParams.leftMargin = iDp2px;
        imageView.setPadding(iDp2px, iDp2px, iDp2px, iDp2px);
        imageView.setLayoutParams(layoutParams);
        frameLayout.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVOutsideWebViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVOutsideWebViewActivity.this.onBackPressed();
            }
        });
    }
}
