package com.hyphenate.easeui.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.util.EMLog;
import java.io.File;

/* loaded from: classes4.dex */
public class EaseShowVideoActivity extends EaseBaseActivity {
    private static final String TAG = "ShowVideoActivity";
    private RelativeLayout loadingLayout;
    private Uri localFilePath;
    private ProgressBar progressBar;

    private void downloadVideo(final EMMessage eMMessage) {
        this.loadingLayout.setVisibility(0);
        eMMessage.setMessageStatusCallback(new EMCallBack() { // from class: com.hyphenate.easeui.ui.EaseShowVideoActivity.1
            @Override // com.hyphenate.EMCallBack
            public void onError(final int i2, String str) {
                Log.e("###", "offline file transfer error:" + str);
                Uri localUri = ((EMVideoMessageBody) eMMessage.getBody()).getLocalUri();
                String filePath = EaseFileUtils.getFilePath(EaseShowVideoActivity.this, localUri);
                if (TextUtils.isEmpty(filePath)) {
                    EaseShowVideoActivity.this.getContentResolver().delete(localUri, null, null);
                } else {
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                EaseShowVideoActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowVideoActivity.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (i2 == 400) {
                            Toast.makeText(EaseShowVideoActivity.this.getApplicationContext(), R.string.Video_expired, 0).show();
                        }
                    }
                });
            }

            @Override // com.hyphenate.EMCallBack
            public void onProgress(final int i2, String str) {
                Log.d("ease", "video progress:" + i2);
                EaseShowVideoActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowVideoActivity.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        EaseShowVideoActivity.this.progressBar.setProgress(i2);
                    }
                });
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
                EaseShowVideoActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowVideoActivity.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        EaseShowVideoActivity.this.loadingLayout.setVisibility(8);
                        EaseShowVideoActivity.this.progressBar.setProgress(0);
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        EaseShowVideoActivity.this.showLocalVideo(((EMVideoMessageBody) eMMessage.getBody()).getLocalUri());
                    }
                });
            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(eMMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLocalVideo(Uri uri) {
        EaseShowLocalVideoActivity.actionStart(this, uri.toString());
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        finish();
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.ease_showvideo_activity);
        this.loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        EMMessage eMMessage = (EMMessage) getIntent().getParcelableExtra("msg");
        if (!(eMMessage.getBody() instanceof EMVideoMessageBody)) {
            Toast.makeText(this, getApplicationContext().getString(R.string.unsupported_message_body), 0).show();
            finish();
            return;
        }
        EMVideoMessageBody eMVideoMessageBody = (EMVideoMessageBody) eMMessage.getBody();
        this.localFilePath = eMVideoMessageBody.getLocalUri();
        EMLog.d(TAG, "localFilePath = " + this.localFilePath);
        EMLog.d(TAG, "local filename = " + eMVideoMessageBody.getFileName());
        EaseFileUtils.takePersistableUriPermission(this, this.localFilePath);
        if (EaseFileUtils.isFileExistByUri(this, this.localFilePath)) {
            showLocalVideo(this.localFilePath);
        } else {
            EMLog.d(TAG, "download remote video file");
            downloadVideo(eMMessage);
        }
    }
}
