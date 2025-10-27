package com.hjq.http;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.request.DeleteRequest;
import com.hjq.http.request.DownloadRequest;
import com.hjq.http.request.GetRequest;
import com.hjq.http.request.HeadRequest;
import com.hjq.http.request.OptionsRequest;
import com.hjq.http.request.PatchRequest;
import com.hjq.http.request.PostRequest;
import com.hjq.http.request.PutRequest;
import com.hjq.http.request.TraceRequest;
import java.util.Iterator;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/* loaded from: classes4.dex */
public final class EasyHttp {
    public static void cancel(LifecycleOwner lifecycleOwner) {
        cancel(String.valueOf(lifecycleOwner));
    }

    public static DeleteRequest delete(LifecycleOwner lifecycleOwner) {
        return new DeleteRequest(lifecycleOwner);
    }

    public static DownloadRequest download(LifecycleOwner lifecycleOwner) {
        return new DownloadRequest(lifecycleOwner);
    }

    public static GetRequest get(LifecycleOwner lifecycleOwner) {
        return new GetRequest(lifecycleOwner);
    }

    public static HeadRequest head(LifecycleOwner lifecycleOwner) {
        return new HeadRequest(lifecycleOwner);
    }

    public static OptionsRequest options(LifecycleOwner lifecycleOwner) {
        return new OptionsRequest(lifecycleOwner);
    }

    public static PatchRequest patch(LifecycleOwner lifecycleOwner) {
        return new PatchRequest(lifecycleOwner);
    }

    public static PostRequest post(LifecycleOwner lifecycleOwner) {
        return new PostRequest(lifecycleOwner);
    }

    public static PutRequest put(LifecycleOwner lifecycleOwner) {
        return new PutRequest(lifecycleOwner);
    }

    public static TraceRequest trace(LifecycleOwner lifecycleOwner) {
        return new TraceRequest(lifecycleOwner);
    }

    public static void cancel(Object obj) {
        if (obj == null) {
            return;
        }
        OkHttpClient client = EasyConfig.getInstance().getClient();
        for (Call call : client.dispatcher().queuedCalls()) {
            if (obj.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call2 : client.dispatcher().runningCalls()) {
            if (obj.equals(call2.request().tag())) {
                call2.cancel();
            }
        }
    }

    public static void cancel() {
        OkHttpClient client = EasyConfig.getInstance().getClient();
        Iterator<Call> it = client.dispatcher().queuedCalls().iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
        Iterator<Call> it2 = client.dispatcher().runningCalls().iterator();
        while (it2.hasNext()) {
            it2.next().cancel();
        }
    }
}
