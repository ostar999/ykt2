package com.hyphenate.easeui.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.hyphenate.util.EMLog;
import com.tencent.smtt.sdk.TbsVideoCacheTask;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class EaseShowBigImageActivity extends EaseBaseActivity {
    private static final String TAG = "ShowBigImage";
    private Bitmap bitmap;
    private int default_res = R.drawable.ease_default_image;
    private String filename;
    private EasePhotoView image;
    private boolean isDownloaded;
    private ProgressDialog pd;

    @SuppressLint({"NewApi"})
    private void downloadImage(String str) throws Resources.NotFoundException {
        EMLog.e(TAG, "download with messageId: " + str);
        String string = getResources().getString(R.string.Download_the_pictures);
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pd = progressDialog;
        progressDialog.setProgressStyle(0);
        this.pd.setCanceledOnTouchOutside(false);
        this.pd.setMessage(string);
        this.pd.show();
        final EMMessage message = EMClient.getInstance().chatManager().getMessage(str);
        message.setMessageStatusCallback(new EMCallBack() { // from class: com.hyphenate.easeui.ui.EaseShowBigImageActivity.2
            @Override // com.hyphenate.EMCallBack
            public void onError(final int i2, String str2) {
                EMLog.e(EaseShowBigImageActivity.TAG, "offline file transfer error:" + str2);
                EaseShowBigImageActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowBigImageActivity.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
                        EaseShowBigImageActivity.this.image.setImageResource(EaseShowBigImageActivity.this.default_res);
                        EaseShowBigImageActivity.this.pd.dismiss();
                        if (i2 == 400) {
                            Toast.makeText(EaseShowBigImageActivity.this.getApplicationContext(), R.string.Image_expired, 0).show();
                        }
                    }
                });
            }

            @Override // com.hyphenate.EMCallBack
            public void onProgress(final int i2, String str2) throws Resources.NotFoundException {
                EMLog.d(EaseShowBigImageActivity.TAG, "Progress: " + i2);
                final String string2 = EaseShowBigImageActivity.this.getResources().getString(R.string.Download_the_pictures_new);
                EaseShowBigImageActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowBigImageActivity.2.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
                        EaseShowBigImageActivity.this.pd.setMessage(string2 + i2 + "%");
                    }
                });
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
                EMLog.e(EaseShowBigImageActivity.TAG, "onSuccess");
                EaseShowBigImageActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowBigImageActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
                        if (EaseShowBigImageActivity.this.pd != null) {
                            EaseShowBigImageActivity.this.pd.dismiss();
                        }
                        EaseShowBigImageActivity.this.isDownloaded = true;
                        Glide.with((FragmentActivity) EaseShowBigImageActivity.this).load(((EMImageMessageBody) message.getBody()).getLocalUri()).apply((BaseRequestOptions<?>) new RequestOptions().error(EaseShowBigImageActivity.this.default_res)).into(EaseShowBigImageActivity.this.image);
                    }
                });
            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(message);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.isDownloaded) {
            setResult(-1);
        }
        finish();
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"NewApi"})
    public void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, Resources.NotFoundException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        setContentView(R.layout.ease_activity_show_big_image);
        super.onCreate(bundle);
        setFitSystemForTheme(true, R.color.black, false);
        this.image = (EasePhotoView) findViewById(R.id.image);
        this.default_res = getIntent().getIntExtra("default_image", R.drawable.ease_default_avatar);
        Uri uri = (Uri) getIntent().getParcelableExtra("uri");
        this.filename = getIntent().getExtras().getString(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME);
        String string = getIntent().getExtras().getString("messageId");
        EMLog.d(TAG, "show big msgId:" + string);
        if (EaseFileUtils.isFileExistByUri(this, uri)) {
            Glide.with((FragmentActivity) this).load(uri).into(this.image);
        } else if (string != null) {
            downloadImage(string);
        } else {
            this.image.setImageResource(this.default_res);
        }
        this.image.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.ui.EaseShowBigImageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EaseShowBigImageActivity.this.finish();
            }
        });
    }
}
