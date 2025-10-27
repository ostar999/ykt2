package com.ykb.ebook;

import android.app.Application;
import com.hjq.http.EasyConfig;
import com.ykb.ebook.common.ConstantKt;
import com.ykb.ebook.network.RequestHandler;
import kotlin.Metadata;
import okhttp3.OkHttpClient;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/App;", "Landroid/app/Application;", "()V", "initEasyHttp", "", "onCreate", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class App extends Application {
    private final void initEasyHttp() {
        OkHttpClient okHttpClientBuild = new OkHttpClient.Builder().build();
        EasyConfig.with(okHttpClientBuild).setLogEnabled(ConstantKt.getIS_DEBUG()).setServer("https://www.wanandroid.com/").setHandler(new RequestHandler()).into();
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        initEasyHttp();
    }
}
