package com.tencent.connect.emotion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import com.alipay.sdk.util.h;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class QQEmotion extends BaseApi {

    /* renamed from: a, reason: collision with root package name */
    private IUiListener f18104a;

    public QQEmotion(QQToken qQToken) {
        super(qQToken);
    }

    private boolean a(Context context, ArrayList<Uri> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return false;
        }
        if (arrayList.size() > 9) {
            SLog.i("QQEMOTION", "isLegality -->illegal, file count > 9, count = " + arrayList.size());
            return false;
        }
        long j2 = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            long jA = k.a(context, arrayList.get(i2));
            if (jA > 1048576) {
                SLog.i("QQEMOTION", "isLegality -->illegal, fileSize: " + jA);
                return false;
            }
            j2 += jA;
        }
        if (j2 > 3145728) {
            SLog.i("QQEMOTION", "isLegality -->illegal, totalSize: " + j2);
            return false;
        }
        SLog.i("QQEMOTION", "isLegality -->legal, totalSize: " + j2);
        return true;
    }

    public void setEmotions(Activity activity, ArrayList<Uri> arrayList, IUiListener iUiListener) {
        IUiListener iUiListener2 = this.f18104a;
        if (iUiListener2 != null) {
            iUiListener2.onCancel();
        }
        this.f18104a = iUiListener;
        if (!i.b(activity)) {
            Toast.makeText(activity.getApplicationContext(), "当前手机未安装QQ，请安装最新版QQ后再试。", 1).show();
            return;
        }
        if (i.c(activity, "8.0.0") < 0) {
            Toast.makeText(activity.getApplicationContext(), "当前手机QQ版本过低，不支持设置表情功能。", 1).show();
            return;
        }
        if (!a(activity.getApplicationContext(), arrayList)) {
            Toast.makeText(activity.getApplicationContext(), "图片不符合要求，不支持设置表情功能。", 1).show();
            return;
        }
        String strA = k.a(activity);
        StringBuffer stringBuffer = new StringBuffer("mqqapi://profile/sdk_face_collection?");
        if (!TextUtils.isEmpty(strA)) {
            if (strA.length() > 20) {
                strA = strA.substring(0, 20) + "...";
            }
            stringBuffer.append("&app_name=" + Base64.encodeToString(k.j(strA), 2));
        }
        String appId = this.f18095c.getAppId();
        String openId = this.f18095c.getOpenId();
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(k.j(openId), 2));
        }
        stringBuffer.append("&sdk_version=" + Base64.encodeToString(k.j(Constants.SDK_VERSION), 2));
        String strA2 = a(activity, arrayList);
        if (TextUtils.isEmpty(strA2)) {
            iUiListener.onError(new UiError(-6, Constants.MSG_UNKNOWN_ERROR, "picPathList is null"));
            return;
        }
        stringBuffer.append("&set_uri_list=" + strA2);
        SLog.v("QQEMOTION", "-->set avatar, url: " + stringBuffer.toString());
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(stringBuffer.toString()));
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        if (a(intent)) {
            UIListenerManager.getInstance().setListenerWithRequestcode(10109, iUiListener);
            a(activity, 10109, intent, false);
        }
    }

    private String a(Activity activity, ArrayList<Uri> arrayList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Uri> it = arrayList.iterator();
        while (it.hasNext()) {
            Uri uriA = k.a(activity, this.f18095c.getAppId(), k.b(activity, it.next()));
            if (uriA == null) {
                SLog.e("QQEmotion", "getFilePathListJson: grantedUri = null");
            } else {
                sb.append(uriA);
                sb.append(h.f3376b);
            }
        }
        String string = sb.toString();
        SLog.i("QQEmotion", "-->getFilePathListJson listStr : " + string);
        return Base64.encodeToString(k.j(string), 2);
    }
}
