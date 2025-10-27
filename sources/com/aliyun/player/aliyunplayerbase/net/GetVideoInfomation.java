package com.aliyun.player.aliyunplayerbase.net;

import android.content.Context;
import com.aliyun.player.aliyunplayerbase.bean.AliyunUserInfo;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.aliyun.svideo.common.okhttp.AlivcOkHttpClient;
import com.google.gson.Gson;
import com.tencent.connect.common.Constants;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.Request;

/* loaded from: classes2.dex */
public class GetVideoInfomation {

    public interface OnGetListPlayerVideoInfosListener {
        void onGetError(Request request, IOException iOException);

        void onGetSuccess(Request request, String str);
    }

    public interface OnGetRandomUserListener {
        void onGetError(Request request, IOException iOException);

        void onGetSuccess(AliyunUserInfo aliyunUserInfo);
    }

    public void getListPlayerVideoInfos(Context context, String str, String str2, int i2, final OnGetListPlayerVideoInfosListener onGetListPlayerVideoInfosListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put(ServiceCommon.RequestKey.FORM_KEY_PACKAGE_NAME, context.getPackageName());
        map.put(ServiceCommon.RequestKey.FORM_KEY_PAGE_INDEX, str);
        map.put(ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        map.put("token", str2);
        if (i2 > 0) {
            map.put("id", i2 + "");
        }
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_LIST_INFO, map, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetVideoInfomation.2
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetListPlayerVideoInfosListener onGetListPlayerVideoInfosListener2 = onGetListPlayerVideoInfosListener;
                if (onGetListPlayerVideoInfosListener2 != null) {
                    onGetListPlayerVideoInfosListener2.onGetError(request, iOException);
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str3) {
                OnGetListPlayerVideoInfosListener onGetListPlayerVideoInfosListener2 = onGetListPlayerVideoInfosListener;
                if (onGetListPlayerVideoInfosListener2 != null) {
                    onGetListPlayerVideoInfosListener2.onGetSuccess(request, str3);
                }
            }
        });
    }

    public void getRandomUser(final OnGetRandomUserListener onGetRandomUserListener) {
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_RANDOM_USER, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetVideoInfomation.1
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetRandomUserListener onGetRandomUserListener2 = onGetRandomUserListener;
                if (onGetRandomUserListener2 != null) {
                    onGetRandomUserListener2.onGetError(request, iOException);
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str) {
                OnGetRandomUserListener onGetRandomUserListener2;
                AliyunUserInfo aliyunUserInfo = (AliyunUserInfo) new Gson().fromJson(str, AliyunUserInfo.class);
                if (aliyunUserInfo == null || (onGetRandomUserListener2 = onGetRandomUserListener) == null) {
                    return;
                }
                onGetRandomUserListener2.onGetSuccess(aliyunUserInfo);
            }
        });
    }
}
