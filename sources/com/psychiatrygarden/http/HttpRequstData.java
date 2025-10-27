package com.psychiatrygarden.http;

import android.content.Context;
import com.psychiatrygarden.utils.NewToast;
import java.lang.ref.WeakReference;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class HttpRequstData {
    private static WeakReference<HttpRequstData> mWeakReference;
    private Context mContext;

    private HttpRequstData(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static HttpRequstData getIntance(Context mContext) {
        WeakReference<HttpRequstData> weakReference = mWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            mWeakReference = new WeakReference<>(new HttpRequstData(mContext.getApplicationContext()));
        }
        return mWeakReference.get();
    }

    public void postBannedData(String target_user_id, String reason_id, String days) {
        postBannedData(ClientCookie.COMMENT_ATTR, target_user_id, reason_id, days);
    }

    public void postReportData(String id, String reason_id, String module_type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("reason_id", reason_id);
        ajaxParams.put("type", ClientCookie.COMMENT_ATTR);
        ajaxParams.put("module_type", module_type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mreportUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.http.HttpRequstData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    NewToast.showShort(HttpRequstData.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void postReportSourceData(String id, String reason) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("review_id", id);
        ajaxParams.put("reason_id", reason);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.soutceCommentReport, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.http.HttpRequstData.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    NewToast.showShort(HttpRequstData.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void postBannedData(String type, String target_user_id, String reason_id, String days) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", target_user_id);
        ajaxParams.put("reason_id", reason_id);
        ajaxParams.put("days", days);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mBanUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.http.HttpRequstData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    NewToast.showShort(HttpRequstData.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
