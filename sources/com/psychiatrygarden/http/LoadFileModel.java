package com.psychiatrygarden.http;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.concurrent.Executors;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes4.dex */
public class LoadFileModel {
    private static LoadFileModel retrofitManager;
    private Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://www.baidu.com").callbackExecutor(Executors.newFixedThreadPool(1)).build();

    public static LoadFileModel getRetrofitManager() {
        if (retrofitManager == null) {
            synchronized (LoadFileModel.class) {
                if (retrofitManager == null) {
                    retrofitManager = new LoadFileModel();
                }
            }
        }
        return retrofitManager;
    }

    public void loadPdfFile(String url, Callback<ResponseBody> callback) {
        LoadFileApi loadFileApi = (LoadFileApi) this.retrofit.create(LoadFileApi.class);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        loadFileApi.loadPdfFile(url).enqueue(callback);
    }

    public void loadZipFile(String url, HashMap<String, String> params, String range, Callback<ResponseBody> callback) {
        LoadFileApi loadFileApi = (LoadFileApi) this.retrofit.create(LoadFileApi.class);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        loadFileApi.loadZipFile(url, params, range).enqueue(callback);
    }
}
