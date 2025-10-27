package com.alipay.android.phone.mrpc.core.a;

import com.alipay.android.phone.mrpc.core.RpcException;
import java.util.ArrayList;
import java.util.Objects;
import okhttp3.HttpUrl;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* loaded from: classes2.dex */
public final class e extends b {

    /* renamed from: c, reason: collision with root package name */
    private int f2933c;

    /* renamed from: d, reason: collision with root package name */
    private Object f2934d;

    public e(int i2, String str, Object obj) {
        super(str, obj);
        this.f2933c = i2;
    }

    @Override // com.alipay.android.phone.mrpc.core.a.f
    public final void a(Object obj) {
        this.f2934d = obj;
    }

    @Override // com.alipay.android.phone.mrpc.core.a.f
    public final byte[] a() {
        try {
            ArrayList arrayList = new ArrayList();
            Object obj = this.f2934d;
            if (obj != null) {
                arrayList.add(new BasicNameValuePair("extParam", com.alipay.a.a.f.a(obj)));
            }
            arrayList.add(new BasicNameValuePair("operationType", this.f2931a));
            StringBuilder sb = new StringBuilder();
            sb.append(this.f2933c);
            arrayList.add(new BasicNameValuePair("id", sb.toString()));
            Objects.toString(this.f2932b);
            Object obj2 = this.f2932b;
            arrayList.add(new BasicNameValuePair("requestData", obj2 == null ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : com.alipay.a.a.f.a(obj2)));
            return URLEncodedUtils.format(arrayList, "utf-8").getBytes();
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder("request  =");
            sb2.append(this.f2932b);
            sb2.append(":");
            sb2.append(e2);
            throw new RpcException(9, sb2.toString() == null ? "" : e2.getMessage(), e2);
        }
    }
}
