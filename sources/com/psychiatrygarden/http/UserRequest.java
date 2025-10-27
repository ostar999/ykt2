package com.psychiatrygarden.http;

import android.content.Context;
import android.text.TextUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.lang.ref.WeakReference;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes4.dex */
public class UserRequest {
    private static WeakReference<UserRequest> mWeakReference;
    private Context mContext;

    private UserRequest(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static UserRequest getIntance(Context mContext) {
        WeakReference<UserRequest> weakReference = mWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            mWeakReference = new WeakReference<>(new UserRequest(mContext.getApplicationContext()));
        }
        return mWeakReference.get();
    }

    public <T> void changeUserInfo(String nickname, String avaUrl, String sexStr, final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.changeUserInfo;
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(nickname)) {
            ajaxParams.put("nickname", nickname);
        }
        if (!TextUtils.isEmpty(avaUrl)) {
            ajaxParams.put("avatar", avaUrl);
        }
        if (!TextUtils.isEmpty(sexStr)) {
            ajaxParams.put(CommonNetImpl.SEX, sexStr);
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.UserRequest.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }

    public <T> void checkChangeNickname(final QuestionDataCallBack<T> callBack) {
        final String str = NetworkRequestsURL.checkChangeNickname;
        YJYHttpUtils.post(this.mContext, str, new AjaxParams(), new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.UserRequest.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                callBack.onFailure(t2, errorNo, strMsg, str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                callBack.onStart(str);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T s2) {
                super.onSuccess(s2);
                callBack.onSuccess(s2, str);
            }
        });
    }
}
