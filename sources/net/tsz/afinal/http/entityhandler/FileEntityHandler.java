package net.tsz.afinal.http.entityhandler;

import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;

/* loaded from: classes9.dex */
public class FileEntityHandler {
    private boolean mStop = false;

    public Object handleEntity(HttpEntity httpEntity, EntityCallBack entityCallBack, String str, boolean z2) throws IllegalStateException, IOException {
        FileOutputStream fileOutputStream;
        long length;
        int i2;
        if (TextUtils.isEmpty(str) || str.trim().length() == 0) {
            return null;
        }
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        if (this.mStop) {
            return file;
        }
        if (z2) {
            length = file.length();
            fileOutputStream = new FileOutputStream(str, true);
        } else {
            fileOutputStream = new FileOutputStream(str);
            length = 0;
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        if (this.mStop) {
            return file;
        }
        InputStream content = httpEntity.getContent();
        long contentLength = httpEntity.getContentLength() + length;
        if (length < contentLength && !this.mStop) {
            byte[] bArr = new byte[1024];
            while (!this.mStop && length < contentLength && (i2 = content.read(bArr, 0, 1024)) > 0) {
                bufferedOutputStream.write(bArr, 0, i2);
                length += i2;
                entityCallBack.callBack(contentLength, length, false);
                bufferedOutputStream.flush();
            }
            entityCallBack.callBack(contentLength, length, true);
            bufferedOutputStream.close();
            if (this.mStop && length < contentLength) {
                throw new IOException("user stop download thread");
            }
        }
        return file;
    }

    public boolean isStop() {
        return this.mStop;
    }

    public void setStop(boolean z2) {
        this.mStop = z2;
    }
}
