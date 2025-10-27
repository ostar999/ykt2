package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.psychiatrygarden.utils.MimeTypes;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;

/* loaded from: classes6.dex */
public class UMMoreHandler extends UMSSOHandler {
    private Activity mAct;

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public String getVersion() {
        return this.VERSION;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void onCreate(Context context, PlatformConfig.Platform platform) {
        super.onCreate(context, platform);
        this.mAct = (Activity) context;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public void release() {
        super.release();
        this.mAct = null;
    }

    @Override // com.umeng.socialize.handler.UMSSOHandler
    public boolean share(ShareContent shareContent, UMShareListener uMShareListener) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        UMediaObject uMediaObject = shareContent.mMedia;
        if (uMediaObject == null || !(uMediaObject instanceof UMImage)) {
            intent.setType("text/plain");
        } else {
            intent.setType(MimeTypes.IMAGE_ALL);
            UMImage uMImage = (UMImage) shareContent.mMedia;
            if (uMImage.asFileImage().getPath() != null) {
                intent.putExtra("android.intent.extra.STREAM", SocializeUtils.insertImage(getContext(), uMImage.asFileImage().getPath()));
            }
        }
        intent.putExtra("android.intent.extra.SUBJECT", shareContent.subject);
        intent.putExtra("android.intent.extra.TEXT", shareContent.mText);
        Intent intentCreateChooser = Intent.createChooser(intent, Config.MORE_TITLE);
        intentCreateChooser.addFlags(268435456);
        try {
            Activity activity = this.mAct;
            if (activity != null && !activity.isFinishing()) {
                this.mAct.startActivity(intentCreateChooser);
            }
            uMShareListener.onResult(SHARE_MEDIA.MORE);
            return true;
        } catch (Exception e2) {
            SLog.error(UmengText.INTER.MOREERROR, e2);
            uMShareListener.onError(SHARE_MEDIA.MORE, e2);
            return true;
        }
    }
}
