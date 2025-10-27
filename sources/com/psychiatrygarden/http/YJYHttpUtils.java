package com.psychiatrygarden.http;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.Sha1Utils;
import com.psychiatrygarden.utils.ToastUtil;
import com.tencent.open.SocialOperation;
import com.yikaobang.yixue.BuildConfig;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.Header;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.message.BasicHeader;

/* loaded from: classes4.dex */
public class YJYHttpUtils {
    private static final String TAG = "YJYHttpUtils";
    static FinalHttp mFinalHttp;

    private static boolean checkNet(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            try {
                context = ProjectApp.instance();
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) {
            return false;
        }
        return activeNetworkInfo.isConnectedOrConnecting();
    }

    public static <T> void get(Context context, final String url, AjaxParams params, final AjaxCallBack<T> icallBack) {
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArrInitNormalHeaders = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(params, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        mFinalHttp.configTimeout(15000);
        LogUtils.e(TAG, "请求参数: " + new Gson().toJson(params));
        mFinalHttp.get(url, basicHeaderArrInitNormalHeaders, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LogUtils.e(YJYHttpUtils.TAG, "GET 请求失败 ----------" + url + ":" + strMsg);
                if (errorNo == 500 || errorNo == 502) {
                    ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                } else {
                    icallBack.onFailure(t2, errorNo, strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
                LogUtils.e(YJYHttpUtils.TAG, url);
                LogUtils.e(YJYHttpUtils.TAG, t2.toString());
            }
        });
    }

    public static synchronized FinalHttp getFinalHttp() {
        if (mFinalHttp == null) {
            mFinalHttp = new FinalHttp();
        }
        return mFinalHttp;
    }

    public static <T> void getMethod(Context context, final String url, AjaxParams params, final AjaxCallBack<T> icallBack) {
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArrInitNormalHeaders = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(params, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        mFinalHttp.configTimeout(15000);
        LogUtils.e(TAG, "GET 请求开始 ----------");
        LogUtils.e(TAG, "请求参数: " + new Gson().toJson(params));
        LogUtils.e(TAG, url);
        mFinalHttp.get(url, basicHeaderArrInitNormalHeaders, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.17
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LogUtils.e(YJYHttpUtils.TAG, "GET 请求失败 ----------" + strMsg);
                LogUtils.e(YJYHttpUtils.TAG, url);
                if (errorNo != 500 && errorNo != 502) {
                    icallBack.onFailure(t2, errorNo, strMsg);
                } else {
                    icallBack.onFailure(t2, errorNo, "网络连接失败");
                    ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
                LogUtils.e(YJYHttpUtils.TAG, "GET 请求成功 ----------");
                LogUtils.e(YJYHttpUtils.TAG, url);
                LogUtils.e(YJYHttpUtils.TAG, t2.toString());
            }
        });
    }

    public static void getNoteFile(Context context, String url, String tarurl, AjaxParams params, final AjaxCallBack<File> icallBack) {
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        FinalHttp finalHttp = new FinalHttp();
        BasicHeader[] basicHeaderArr = new BasicHeader[8];
        try {
            String paramStr = getParamStr(params.getParam(), "");
            String str = (System.currentTimeMillis() / 1000) + "";
            basicHeaderArr[0] = new BasicHeader("timestamp", str);
            StringBuilder sb = new StringBuilder();
            sb.append("bfde83c3208f4bfe97a57765ee824e92");
            sb.append(Md5Util.MD5Encode(paramStr + str));
            basicHeaderArr[1] = new BasicHeader(SocialOperation.GAME_SIGNATURE, Md5Util.MD5Encode(sb.toString()));
            basicHeaderArr[2] = new BasicHeader("client-type", "android");
            basicHeaderArr[3] = new BasicHeader("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
            basicHeaderArr[4] = new BasicHeader(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
            basicHeaderArr[5] = new BasicHeader("User-Agent", AndroidBaseUtils.getDeviceName() + "");
            basicHeaderArr[6] = new BasicHeader("Content-type", "application/x-www-form-urlencoded");
            basicHeaderArr[7] = new BasicHeader("app-type", BuildConfig.FLAVOR);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (UserConfig.getUserId() == null || "".equals(UserConfig.getUserId())) {
            icallBack.onFailure(null, 0, "请重新登录!");
            return;
        }
        try {
            finalHttp.download2(url, params, basicHeaderArr, tarurl, true, new AjaxCallBack<File>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.16
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    if (errorNo == 500 || errorNo == 502) {
                        ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                    } else {
                        icallBack.onFailure(t2, errorNo, strMsg);
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onStart() {
                    super.onStart();
                    icallBack.onStart();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(File file) {
                    super.onSuccess((AnonymousClass16) file);
                    icallBack.onSuccess(file);
                }
            });
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static String getParamStr(Map<String, String> map, String app_id) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (UserConfig.isLogin()) {
            map.put("token", UserConfig.getInstance().getUser().getToken());
            map.put("secret", UserConfig.getInstance().getUser().getSecret());
            if (TextUtils.isEmpty(map.get("user_id"))) {
                map.put("user_id", UserConfig.getUserId());
            }
        }
        if (TextUtils.isEmpty(app_id)) {
            map.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
        } else {
            map.put("app_id", app_id);
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, ProjectApp.instance());
        if (!TextUtils.isEmpty(strConfig)) {
            map.put(HmsMessageService.SUBJECT_ID, strConfig);
        }
        initIdentityId(map);
        String str = "";
        for (Map.Entry entry : new TreeMap(map).entrySet()) {
            str = str + ((String) entry.getKey()) + "=" + ((String) entry.getValue());
        }
        return str;
    }

    public static <T> void getgoodsmd5(Context context, String url, AjaxParams params, final AjaxCallBack<T> icallBack) {
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArrInitNormalHeaders = new BasicHeader[9];
        if (params == null) {
            params = new AjaxParams();
        }
        AjaxParams ajaxParams = params;
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(ajaxParams, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        BasicHeader[] basicHeaderArr = basicHeaderArrInitNormalHeaders;
        LogUtils.e(TAG, "GET:getgoodsmd5 请求开始 ----------");
        LogUtils.e(TAG, "请求参数: " + new Gson().toJson(ajaxParams));
        mFinalHttp.configTimeout(15000);
        mFinalHttp.get(url, basicHeaderArr, null, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (errorNo == 500 || errorNo == 502) {
                    ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                } else {
                    icallBack.onFailure(t2, errorNo, strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
                LogUtils.e(YJYHttpUtils.TAG, "GET:getgoodsmd5 请求结束 ----------");
                LogUtils.e(YJYHttpUtils.TAG, "请求结果--" + t2);
            }
        });
    }

    public static <T> void getmd5(Context context, String url, AjaxParams params, final AjaxCallBack<T> icallBack) {
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArrInitNormalHeaders = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(params, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        BasicHeader[] basicHeaderArr = basicHeaderArrInitNormalHeaders;
        mFinalHttp.configTimeout(15000);
        mFinalHttp.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, Boolean.TRUE);
        mFinalHttp.get(url, basicHeaderArr, null, params, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (errorNo == 500 || errorNo == 502) {
                    ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                } else {
                    icallBack.onFailure(t2, errorNo, strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                LogUtils.e(YJYHttpUtils.TAG, "onSuccess");
                icallBack.onSuccess(t2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initIdentityId(Map<String, String> map) {
        try {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, ProjectApp.instance());
            if (TextUtils.isEmpty(map.get("identity_id"))) {
                map.put("identity_id", strConfig);
            }
        } catch (Exception e2) {
            Log.e(TAG, "initIdentityId: " + e2.getMessage());
        }
    }

    private static BasicHeader[] initNormalHeaders(AjaxParams params, int len) throws PackageManager.NameNotFoundException {
        String str;
        String paramStr;
        if (UserConfig.isLogin() && UserConfig.getInstance().getUser() != null) {
            params.put("is_vip", UserConfig.getInstance().getUser().getIs_vip());
            params.put("is_svip", UserConfig.getInstance().getUser().getIs_svip());
        }
        if (params == null) {
            paramStr = "";
            str = paramStr;
        } else {
            str = params.getParam().get("app_id");
            paramStr = getParamStr(params.getParam(), str);
        }
        String str2 = (System.currentTimeMillis() / 1000) + "";
        String str3 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1") + str2;
        if (!TextUtils.isEmpty(str)) {
            str3 = str + str2;
        }
        BasicHeader[] basicHeaderArr = new BasicHeader[len];
        basicHeaderArr[0] = new BasicHeader("timestamp", str2);
        StringBuilder sb = new StringBuilder();
        sb.append(Md5Util.MD5Encode(paramStr + str3));
        sb.append("bfde83c3208f4bfe97a57765ee824e92");
        basicHeaderArr[1] = new BasicHeader(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
        basicHeaderArr[2] = new BasicHeader("client-type", "android");
        basicHeaderArr[3] = new BasicHeader("app-version", AndroidBaseUtils.getAPPVersionCode(ProjectApp.instance()) + "");
        basicHeaderArr[4] = new BasicHeader(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(ProjectApp.instance()) + "");
        basicHeaderArr[5] = new BasicHeader("User-Agent", AndroidBaseUtils.getDeviceName() + "");
        if (len == 9) {
            basicHeaderArr[6] = new BasicHeader("Content-type", "application/x-www-form-urlencoded");
            basicHeaderArr[7] = new BasicHeader("channel", "10000");
            basicHeaderArr[8] = new BasicHeader("app-type", BuildConfig.FLAVOR);
        } else {
            basicHeaderArr[6] = new BasicHeader("channel", "10000");
            basicHeaderArr[7] = new BasicHeader("app-type", BuildConfig.FLAVOR);
        }
        Log.e(TAG, "请求头-header:" + new Gson().toJson(basicHeaderArr));
        return basicHeaderArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$get$4(Response.Listener listener, String str, String str2) {
        LogUtils.e(TAG, str + "");
        listener.onResponse(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postmd5$0(Response.Listener listener, String str, String str2) {
        LogUtils.e(TAG, "onResponse" + str);
        listener.onResponse(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postmd5$1(Response.ErrorListener errorListener, VolleyError volleyError, String str) {
        LogUtils.e(TAG, "onErrorResponse" + volleyError.getMessage());
        errorListener.onErrorResponse(volleyError, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postpaymd5$2(Response.Listener listener, String str, String str2) {
        LogUtils.e(TAG, "onResponse" + str);
        listener.onResponse(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postpaymd5$3(Response.ErrorListener errorListener, VolleyError volleyError, String str) {
        LogUtils.e(TAG, "onErrorResponse" + volleyError.getMessage());
        errorListener.onErrorResponse(volleyError, str);
    }

    public static <T> void post(Context context, final String url, AjaxParams params, final AjaxCallBack<T> icallBack) {
        BasicHeader[] basicHeaderArrInitNormalHeaders;
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        if (TextUtils.equals(url, NetworkRequestsURL.getCoupon) && !UserConfig.isLogin()) {
            context.startActivity(new Intent(context, (Class<?>) LoginActivity.class));
            ProjectApp.instance.dismissAllPop();
            return;
        }
        if (params == null) {
            params = new AjaxParams();
        }
        AjaxParams ajaxParams = params;
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArr = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(ajaxParams, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
            basicHeaderArrInitNormalHeaders = basicHeaderArr;
        }
        mFinalHttp.configTimeout(60000);
        LogUtils.e(TAG, "请求参数:" + new Gson().toJson(ajaxParams));
        mFinalHttp.post(url, (Header[]) basicHeaderArrInitNormalHeaders, (String) null, ajaxParams, (AjaxCallBack<? extends Object>) new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LogUtils.e(YJYHttpUtils.TAG, "POST 请求失败 ----------------" + url + ":" + strMsg);
                if (errorNo != 500 && errorNo != 502) {
                    icallBack.onFailure(t2, errorNo, strMsg);
                } else {
                    ProjectApp.instance().hideDialogWindow();
                    ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
                LogUtils.e(YJYHttpUtils.TAG, "POST 请求成功 ----------------");
                LogUtils.e(YJYHttpUtils.TAG, url);
                LogUtils.e(YJYHttpUtils.TAG, t2.toString());
            }
        });
    }

    public static <T> void postImage(Context context, String url, AjaxParams params, final AjaxCallBack<T> icallBack) {
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArrInitNormalHeaders = new BasicHeader[8];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(params, 8);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        BasicHeader[] basicHeaderArr = basicHeaderArrInitNormalHeaders;
        mFinalHttp.configTimeout(30000);
        LogUtils.e("url", new Gson().toJson(params));
        LogUtils.e("url", url);
        mFinalHttp.post(url, (Header[]) basicHeaderArr, (String) null, params, (AjaxCallBack<? extends Object>) new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                icallBack.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
            }
        });
    }

    public static <T> void postMethod(Context context, final String url, AjaxParams params, final AjaxCallBack<T> icallBack) throws PackageManager.NameNotFoundException {
        BasicHeader[] basicHeaderArrInitNormalHeaders;
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        if (params == null) {
            params = new AjaxParams();
        }
        AjaxParams ajaxParams = params;
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArr = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(ajaxParams, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
            basicHeaderArrInitNormalHeaders = basicHeaderArr;
        }
        mFinalHttp.configTimeout(60000);
        LogUtils.e(TAG, "POST 请求开始----------------");
        LogUtils.e(TAG, url);
        LogUtils.e(TAG, "请求参数:" + new Gson().toJson(ajaxParams));
        mFinalHttp.post(url, (Header[]) basicHeaderArrInitNormalHeaders, (String) null, ajaxParams, (AjaxCallBack<? extends Object>) new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.18
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LogUtils.e(YJYHttpUtils.TAG, "POST 请求失败 ----------------" + strMsg);
                LogUtils.e(YJYHttpUtils.TAG, url);
                if (errorNo != 500 && errorNo != 502) {
                    icallBack.onFailure(t2, errorNo, strMsg);
                    return;
                }
                ProjectApp.instance().hideDialogWindow();
                icallBack.onFailure(t2, errorNo, "网络连接失败");
                ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
                LogUtils.e(YJYHttpUtils.TAG, "POST 请求成功 ----------------");
                LogUtils.e(YJYHttpUtils.TAG, url);
                LogUtils.e(YJYHttpUtils.TAG, t2.toString());
            }
        });
    }

    public static <T> void postTemp(Context context, String url, AjaxParams params, final AjaxCallBack<T> icallBack) throws PackageManager.NameNotFoundException {
        BasicHeader[] basicHeaderArrInitNormalHeaders;
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        if (params == null) {
            params = new AjaxParams();
        }
        AjaxParams ajaxParams = params;
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArr = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(ajaxParams, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
            basicHeaderArrInitNormalHeaders = basicHeaderArr;
        }
        mFinalHttp.configTimeout(15000);
        mFinalHttp.post(url, (Header[]) basicHeaderArrInitNormalHeaders, (String) null, ajaxParams, (AjaxCallBack<? extends Object>) new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                icallBack.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                icallBack.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(T t2) {
                super.onSuccess(t2);
                icallBack.onSuccess(t2);
            }
        });
    }

    public static void postTmpe(final Context context, String url, final String app_id, final Map<String, String> map, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        if (!checkNet(context)) {
            errorListener.onErrorResponse(new VolleyError("当前网络不可用"), url);
            return;
        }
        StringRequest stringRequest = new StringRequest(1, url, listener, errorListener) { // from class: com.psychiatrygarden.http.YJYHttpUtils.11
            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map2 = new HashMap();
                try {
                    String paramStr = YJYHttpUtils.getParamStr(map, app_id);
                    String str = (System.currentTimeMillis() / 1000) + "";
                    map2.put("timestamp", str);
                    String str2 = app_id + str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Md5Util.MD5Encode(paramStr + str2));
                    sb.append("bfde83c3208f4bfe97a57765ee824e92");
                    map2.put(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
                    map2.put("client-type", "android");
                    map2.put("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
                    map2.put(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
                    map2.put("User-Agent", AndroidBaseUtils.getDeviceName() + "");
                    map2.put("channel", "10000");
                    map2.put("app-type", BuildConfig.FLAVOR);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return map2;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getParams() throws AuthFailureError {
                if (UserConfig.isLogin()) {
                    map.put("token", UserConfig.getInstance().getUser().getToken());
                    map.put("secret", UserConfig.getInstance().getUser().getSecret());
                    map.put("user_id", UserConfig.getUserId());
                }
                map.put("app_id", app_id);
                YJYHttpUtils.initIdentityId(map);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public static void postgoodsmd5(final Context context, String url, final Map<String, String> map, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        if (!checkNet(context)) {
            errorListener.onErrorResponse(new VolleyError("当前网络不可用"), url);
            return;
        }
        LogUtils.e(TAG, "POST 请求开始 ----------");
        LogUtils.e(TAG, "url： ----------" + url);
        StringRequest stringRequest = new StringRequest(1, url, listener, errorListener) { // from class: com.psychiatrygarden.http.YJYHttpUtils.9
            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map2 = new HashMap();
                try {
                    String paramStr = YJYHttpUtils.getParamStr(map, "");
                    String str = (System.currentTimeMillis() / 1000) + "";
                    map2.put("timestamp", str);
                    String str2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "1") + str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Md5Util.MD5Encode(paramStr + str2));
                    sb.append("bfde83c3208f4bfe97a57765ee824e92");
                    map2.put(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
                    map2.put("client-type", "android");
                    map2.put("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
                    map2.put(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
                    map2.put("User-Agent", AndroidBaseUtils.getDeviceName() + "");
                    map2.put("channel", "10000");
                    map2.put("app-type", BuildConfig.FLAVOR);
                    LogUtils.e(YJYHttpUtils.TAG, "请求Headers: " + new Gson().toJson(map2));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return map2;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getParams() throws AuthFailureError {
                if (UserConfig.isLogin()) {
                    map.put("token", UserConfig.getInstance().getUser().getToken());
                    map.put("secret", UserConfig.getInstance().getUser().getSecret());
                    map.put("user_id", UserConfig.getUserId());
                }
                YJYHttpUtils.initIdentityId(map);
                map.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
                LogUtils.e(YJYHttpUtils.TAG, "请求参数: " + new Gson().toJson(map));
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public static void postmd5(final Context context, String url, final Map<String, String> map, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        if (!checkNet(context)) {
            errorListener.onErrorResponse(new VolleyError("当前网络不可用"), url);
            return;
        }
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener() { // from class: com.psychiatrygarden.http.b
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                YJYHttpUtils.lambda$postmd5$0(listener, (String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.http.c
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                YJYHttpUtils.lambda$postmd5$1(errorListener, volleyError, str);
            }
        }) { // from class: com.psychiatrygarden.http.YJYHttpUtils.12
            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map2 = new HashMap();
                try {
                    String paramStr = YJYHttpUtils.getParamStr(map, "");
                    String str = (System.currentTimeMillis() / 1000) + "";
                    map2.put("timestamp", str);
                    String str2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "1") + str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Md5Util.MD5Encode(paramStr + str2));
                    sb.append("bfde83c3208f4bfe97a57765ee824e92");
                    map2.put(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
                    map2.put("client-type", "android");
                    map2.put("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
                    map2.put(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
                    map2.put("User-Agent", AndroidBaseUtils.getDeviceName() + "");
                    map2.put("channel", "10000");
                    map2.put("app-type", BuildConfig.FLAVOR);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return map2;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getParams() throws AuthFailureError {
                if (UserConfig.isLogin()) {
                    map.put("token", UserConfig.getInstance().getUser().getToken());
                    map.put("secret", UserConfig.getInstance().getUser().getSecret());
                    map.put("user_id", UserConfig.getUserId());
                }
                map.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
                YJYHttpUtils.initIdentityId(map);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public static void postpaymd5(final Context context, String url, final Map<String, String> map, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        if (!checkNet(context)) {
            errorListener.onErrorResponse(new VolleyError("当前网络不可用"), url);
            return;
        }
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener() { // from class: com.psychiatrygarden.http.d
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                YJYHttpUtils.lambda$postpaymd5$2(listener, (String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.http.e
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                YJYHttpUtils.lambda$postpaymd5$3(errorListener, volleyError, str);
            }
        }) { // from class: com.psychiatrygarden.http.YJYHttpUtils.13
            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map2 = new HashMap();
                try {
                    String paramStr = YJYHttpUtils.getParamStr(map, "");
                    String str = (System.currentTimeMillis() / 1000) + "";
                    map2.put("timestamp", str);
                    String str2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "1") + str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Md5Util.MD5Encode(paramStr + str2));
                    sb.append("bfde83c3208f4bfe97a57765ee824e92");
                    map2.put(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
                    map2.put("client-type", "android");
                    map2.put("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
                    map2.put(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
                    map2.put("User-Agent", AndroidBaseUtils.getDeviceName() + "");
                    map2.put("channel", "10000");
                    map2.put("app-type", BuildConfig.FLAVOR);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return map2;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getParams() throws AuthFailureError {
                if (UserConfig.isLogin()) {
                    map.put("token", UserConfig.getInstance().getUser().getToken());
                    map.put("secret", UserConfig.getInstance().getUser().getSecret());
                    map.put("user_id", UserConfig.getUserId());
                }
                map.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
                YJYHttpUtils.initIdentityId(map);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public static String postsync(Context context, String url, AjaxParams params) {
        if (params == null) {
            params = new AjaxParams();
        }
        mFinalHttp = getFinalHttp();
        Header[] headerArrInitNormalHeaders = new Header[9];
        try {
            headerArrInitNormalHeaders = initNormalHeaders(params, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        mFinalHttp.configTimeout(15000);
        return (String) mFinalHttp.postSync(url, headerArrInitNormalHeaders, params, (String) null);
    }

    public static <T> void request(String method, Context context, final String url, AjaxParams params, final AjaxCallBack<T> icallBack) throws PackageManager.NameNotFoundException {
        BasicHeader[] basicHeaderArrInitNormalHeaders;
        if (!checkNet(context)) {
            icallBack.onFailure(null, 10100, "当前网络不可用");
            return;
        }
        if (params == null) {
            params = new AjaxParams();
        }
        AjaxParams ajaxParams = params;
        mFinalHttp = getFinalHttp();
        BasicHeader[] basicHeaderArr = new BasicHeader[9];
        try {
            basicHeaderArrInitNormalHeaders = initNormalHeaders(ajaxParams, 9);
        } catch (Exception e2) {
            e2.printStackTrace();
            basicHeaderArrInitNormalHeaders = basicHeaderArr;
        }
        mFinalHttp.configTimeout(60000);
        LogUtils.e(TAG, method + " 请求开始----------------");
        LogUtils.e(TAG, url);
        LogUtils.e(TAG, "请求参数:" + new Gson().toJson(ajaxParams));
        if ("GET".equals(method)) {
            mFinalHttp.get(url, basicHeaderArrInitNormalHeaders, null, ajaxParams, new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    LogUtils.e(YJYHttpUtils.TAG, "POST 请求失败 ----------------" + strMsg);
                    LogUtils.e(YJYHttpUtils.TAG, url);
                    if (errorNo != 500 && errorNo != 502) {
                        icallBack.onFailure(t2, errorNo, strMsg);
                    } else {
                        ProjectApp.instance().hideDialogWindow();
                        ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onStart() {
                    super.onStart();
                    icallBack.onStart();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(T t2) {
                    super.onSuccess(t2);
                    icallBack.onSuccess(t2);
                    LogUtils.e(YJYHttpUtils.TAG, "POST 请求成功 ----------------");
                    LogUtils.e(YJYHttpUtils.TAG, url);
                    LogUtils.e(YJYHttpUtils.TAG, t2.toString());
                }
            });
        } else {
            mFinalHttp.post(url, (Header[]) basicHeaderArrInitNormalHeaders, (String) null, ajaxParams, (AjaxCallBack<? extends Object>) new AjaxCallBack<T>() { // from class: com.psychiatrygarden.http.YJYHttpUtils.3
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    LogUtils.e(YJYHttpUtils.TAG, "POST 请求失败 ----------------" + strMsg);
                    LogUtils.e(YJYHttpUtils.TAG, url);
                    if (errorNo != 500 && errorNo != 502) {
                        icallBack.onFailure(t2, errorNo, strMsg);
                    } else {
                        ProjectApp.instance().hideDialogWindow();
                        ToastUtil.shortToast(ProjectApp.instance(), "网络连接失败");
                    }
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onStart() {
                    super.onStart();
                    icallBack.onStart();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(T t2) {
                    super.onSuccess(t2);
                    icallBack.onSuccess(t2);
                    LogUtils.e(YJYHttpUtils.TAG, "POST 请求成功 ----------------");
                    LogUtils.e(YJYHttpUtils.TAG, url);
                    LogUtils.e(YJYHttpUtils.TAG, t2.toString());
                }
            });
        }
    }

    public static void get(final Context context, String url, final Map<String, String> map, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        if (!checkNet(context)) {
            errorListener.onErrorResponse(new VolleyError("当前网络不可用"), url);
            return;
        }
        StringRequest stringRequest = new StringRequest(0, url, new Response.Listener() { // from class: com.psychiatrygarden.http.a
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                YJYHttpUtils.lambda$get$4(listener, (String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.http.YJYHttpUtils.14
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError arg0, String requestType) {
                LogUtils.e(YJYHttpUtils.TAG, requestType + "");
                errorListener.onErrorResponse(arg0, requestType);
            }
        }) { // from class: com.psychiatrygarden.http.YJYHttpUtils.15
            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map2 = new HashMap();
                try {
                    String paramStr = YJYHttpUtils.getParamStr(map, "");
                    String str = (System.currentTimeMillis() / 1000) + "";
                    map2.put("timestamp", str);
                    String str2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "1") + str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Md5Util.MD5Encode(paramStr + str2));
                    sb.append("bfde83c3208f4bfe97a57765ee824e92");
                    map2.put(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
                    map2.put("client-type", "android");
                    map2.put("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
                    map2.put(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
                    map2.put("User-Agent", AndroidBaseUtils.getDeviceName() + "");
                    map2.put("channel", "10000");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return map2;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getParams() throws AuthFailureError {
                if (UserConfig.isLogin()) {
                    map.put("token", UserConfig.getInstance().getUser().getToken());
                    map.put("secret", UserConfig.getInstance().getUser().getSecret());
                    map.put("user_id", UserConfig.getUserId());
                }
                map.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
                YJYHttpUtils.initIdentityId(map);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public static void post(final Context context, String url, final Map<String, String> map, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        if (!checkNet(context)) {
            errorListener.onErrorResponse(new VolleyError("当前网络不可用"), url);
            return;
        }
        StringRequest stringRequest = new StringRequest(1, url, listener, errorListener) { // from class: com.psychiatrygarden.http.YJYHttpUtils.10
            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map2 = new HashMap();
                try {
                    String paramStr = YJYHttpUtils.getParamStr(map, "");
                    String str = (System.currentTimeMillis() / 1000) + "";
                    map2.put("timestamp", str);
                    String str2 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "1") + str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Md5Util.MD5Encode(paramStr + str2));
                    sb.append("bfde83c3208f4bfe97a57765ee824e92");
                    map2.put(SocialOperation.GAME_SIGNATURE, Sha1Utils.encode(sb.toString()));
                    map2.put("client-type", "android");
                    map2.put("app-version", AndroidBaseUtils.getAPPVersionCode(context) + "");
                    map2.put(AliyunLogKey.KEY_UUID, AndroidBaseUtils.getIMEI(context) + "");
                    map2.put("User-Agent", AndroidBaseUtils.getDeviceName() + "");
                    map2.put("channel", "10000");
                    map2.put("app-type", BuildConfig.FLAVOR);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return map2;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getParams() throws AuthFailureError {
                if (UserConfig.isLogin()) {
                    map.put("token", UserConfig.getInstance().getUser().getToken());
                    map.put("secret", UserConfig.getInstance().getUser().getSecret());
                    map.put("user_id", UserConfig.getUserId());
                }
                map.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
                YJYHttpUtils.initIdentityId(map);
                return map;
            }
        };
        LogUtils.e("url", url);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        if (context == null) {
            return;
        }
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
