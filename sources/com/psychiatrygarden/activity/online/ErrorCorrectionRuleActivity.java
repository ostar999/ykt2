package com.psychiatrygarden.activity.online;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.transition.Transition;
import com.lxj.xpopup.util.ImageDownloadTarget;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;
import java.io.File;

/* loaded from: classes5.dex */
public class ErrorCorrectionRuleActivity extends BaseActivity {
    private ImageView ivImg;

    /* JADX INFO: Access modifiers changed from: private */
    public BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("title");
        String stringExtra2 = getIntent().getStringExtra("rulesUrl");
        setTitle(stringExtra);
        this.ivImg = (ImageView) findViewById(R.id.ivImg);
        showProgressDialog();
        Glide.with(this.ivImg).downloadOnly().load((Object) GlideUtils.generateUrl(stringExtra2)).into((RequestBuilder<File>) new ImageDownloadTarget() { // from class: com.psychiatrygarden.activity.online.ErrorCorrectionRuleActivity.1
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.manager.LifecycleListener
            public void onDestroy() {
                super.onDestroy();
                ErrorCorrectionRuleActivity.this.hideProgressDialog();
            }

            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
                ErrorCorrectionRuleActivity.this.hideProgressDialog();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(@NonNull File resource, Transition<? super File> transition) {
                ErrorCorrectionRuleActivity.this.hideProgressDialog();
                try {
                    ErrorCorrectionRuleActivity.this.ivImg.setImageBitmap(BitmapFactory.decodeFile(resource.getAbsolutePath(), ErrorCorrectionRuleActivity.this.getBitmapOption(1)));
                } catch (Exception e2) {
                    ErrorCorrectionRuleActivity.this.hideProgressDialog();
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_restore_rule);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
