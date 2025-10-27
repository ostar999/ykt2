package com.hyphenate.easeui.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import com.hyphenate.easeui.utils.EaseCompat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class EaseShowNormalFileActivity extends EaseBaseActivity {
    private static final String TAG = "EaseShowNormalFileActivity";
    private ProgressBar progressBar;

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_show_file);
        setFitSystemForTheme(true, R.color.transparent, true);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final EMMessage eMMessage = (EMMessage) getIntent().getParcelableExtra("msg");
        if (eMMessage.getBody() instanceof EMFileMessageBody) {
            eMMessage.setMessageStatusCallback(new EMCallBack() { // from class: com.hyphenate.easeui.ui.EaseShowNormalFileActivity.1
                @Override // com.hyphenate.EMCallBack
                public void onError(final int i2, String str) {
                    EaseShowNormalFileActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowNormalFileActivity.1.2
                        @Override // java.lang.Runnable
                        public void run() throws Resources.NotFoundException {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            EaseCompat.deleteFile(EaseShowNormalFileActivity.this, ((EMFileMessageBody) eMMessage.getBody()).getLocalUri());
                            String string = EaseShowNormalFileActivity.this.getResources().getString(R.string.Failed_to_download_file);
                            if (i2 == 400) {
                                string = EaseShowNormalFileActivity.this.getResources().getString(R.string.File_expired);
                            }
                            Toast.makeText(EaseShowNormalFileActivity.this.getApplicationContext(), string + eMMessage, 0).show();
                            EaseShowNormalFileActivity.this.finish();
                        }
                    });
                }

                @Override // com.hyphenate.EMCallBack
                public void onProgress(final int i2, String str) {
                    EaseShowNormalFileActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowNormalFileActivity.1.3
                        @Override // java.lang.Runnable
                        public void run() {
                            EaseShowNormalFileActivity.this.progressBar.setProgress(i2);
                        }
                    });
                }

                @Override // com.hyphenate.EMCallBack
                public void onSuccess() {
                    EaseShowNormalFileActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.EaseShowNormalFileActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            EaseCompat.openFile(EaseShowNormalFileActivity.this, ((EMFileMessageBody) eMMessage.getBody()).getLocalUri());
                            EaseShowNormalFileActivity.this.finish();
                        }
                    });
                }
            });
            EMClient.getInstance().chatManager().downloadAttachment(eMMessage);
        } else {
            Toast.makeText(this, getApplicationContext().getString(R.string.unsupported_message_body), 0).show();
            finish();
        }
    }
}
