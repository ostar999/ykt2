package net.tsz.afinal.http.entityhandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;

/* loaded from: classes9.dex */
public class StringEntityHandler {
    public Object handleEntity(HttpEntity httpEntity, EntityCallBack entityCallBack, String str) throws IllegalStateException, IOException {
        if (httpEntity == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        long contentLength = httpEntity.getContentLength();
        InputStream content = httpEntity.getContent();
        long j2 = 0;
        while (true) {
            int i2 = content.read(bArr);
            if (i2 == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, i2);
            long j3 = j2 + i2;
            if (entityCallBack != null) {
                entityCallBack.callBack(contentLength, j3, false);
            }
            j2 = j3;
        }
        if (entityCallBack != null) {
            entityCallBack.callBack(contentLength, j2, true);
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        content.close();
        return new String(byteArray, str);
    }
}
