package x0;

import com.hjq.http.EasyConfig;
import com.hjq.http.config.IRequestClient;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public final /* synthetic */ class b {
    public static OkHttpClient a(IRequestClient iRequestClient) {
        return EasyConfig.getInstance().getClient();
    }
}
