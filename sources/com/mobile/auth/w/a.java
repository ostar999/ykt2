package com.mobile.auth.w;

import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.i;
import com.nirvana.tools.logger.model.ACMRecord;
import com.nirvana.tools.logger.upload.ACMUpload;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a<T extends ACMRecord> implements ACMUpload<T> {

    /* renamed from: a, reason: collision with root package name */
    protected com.mobile.auth.gatewayauth.b f10575a;

    public void a(com.mobile.auth.gatewayauth.b bVar) {
        try {
            this.f10575a = bVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public abstract boolean a(String str);

    @Override // com.nirvana.tools.logger.upload.ACMUpload
    public boolean upload(List<T> list) {
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                String content = it.next().getContent();
                if (!TextUtils.isEmpty(content)) {
                    try {
                        jSONArray.put(new JSONObject(content));
                    } catch (Exception e2) {
                        i.a(e2);
                    }
                }
            }
            return a(jSONArray.toString());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }
}
