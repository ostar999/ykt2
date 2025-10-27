package com.alipay.android.phone.mrpc.core;

import com.aliyun.vod.log.struct.AliyunLogKey;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes2.dex */
public final class j extends a {

    /* renamed from: g, reason: collision with root package name */
    private g f2955g;

    public j(g gVar, Method method, int i2, String str, byte[] bArr, boolean z2) {
        super(method, i2, str, bArr, "application/x-www-form-urlencoded", z2);
        this.f2955g = gVar;
    }

    @Override // com.alipay.android.phone.mrpc.core.v
    public final Object a() throws ExecutionException, InterruptedException {
        o oVar = new o(this.f2955g.a());
        oVar.a(this.f2924b);
        oVar.a(this.f2927e);
        oVar.a(this.f2928f);
        oVar.a("id", String.valueOf(this.f2926d));
        oVar.a("operationType", this.f2925c);
        oVar.a(HttpHeaderValues.GZIP, String.valueOf(this.f2955g.d()));
        oVar.a(new BasicHeader(AliyunLogKey.KEY_UUID, UUID.randomUUID().toString()));
        List<Header> listB = this.f2955g.c().b();
        if (listB != null && !listB.isEmpty()) {
            Iterator<Header> it = listB.iterator();
            while (it.hasNext()) {
                oVar.a(it.next());
            }
        }
        Thread.currentThread().getId();
        oVar.toString();
        try {
            u uVar = this.f2955g.b().a(oVar).get();
            if (uVar != null) {
                return uVar.b();
            }
            throw new RpcException((Integer) 9, "response is null");
        } catch (InterruptedException e2) {
            throw new RpcException(13, "", e2);
        } catch (CancellationException e3) {
            throw new RpcException(13, "", e3);
        } catch (ExecutionException e4) {
            Throwable cause = e4.getCause();
            if (cause == null || !(cause instanceof HttpException)) {
                throw new RpcException(9, "", e4);
            }
            HttpException httpException = (HttpException) cause;
            int code = httpException.getCode();
            switch (code) {
                case 1:
                    code = 2;
                    break;
                case 2:
                    code = 3;
                    break;
                case 3:
                    code = 4;
                    break;
                case 4:
                    code = 5;
                    break;
                case 5:
                    code = 6;
                    break;
                case 6:
                    code = 7;
                    break;
                case 7:
                    code = 8;
                    break;
                case 8:
                    code = 15;
                    break;
                case 9:
                    code = 16;
                    break;
            }
            throw new RpcException(Integer.valueOf(code), httpException.getMsg());
        }
    }
}
