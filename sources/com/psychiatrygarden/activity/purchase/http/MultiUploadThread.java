package com.psychiatrygarden.activity.purchase.http;

import android.os.Handler;
import android.os.Message;
import com.psychiatrygarden.activity.purchase.beans.FormFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class MultiUploadThread implements Runnable {
    private FormFile[] formFile;
    private Handler mHandler;
    Map<String, String> map;

    public MultiUploadThread(FormFile[] formFile, Handler mHandler, Map<String, String> map) {
        this.formFile = formFile;
        this.mHandler = mHandler;
        this.map = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strUploadFile = uploadFile(this.formFile);
        Message messageObtainMessage = this.mHandler.obtainMessage();
        if (messageObtainMessage != null) {
            messageObtainMessage.obj = strUploadFile;
            messageObtainMessage.arg1 = 11;
            this.mHandler.sendMessage(messageObtainMessage);
        }
    }

    public String uploadFile(FormFile[] formFiles) {
        try {
            new HashMap();
            return SocketHttpRequester.post("http://shop.letiku.net:8000/GoodsComment/upload", this.map, formFiles, this.mHandler);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public MultiUploadThread(List<FormFile> formFile, Handler mHandler, Map<String, String> map) {
        if (formFile != null && !formFile.isEmpty()) {
            this.formFile = (FormFile[]) formFile.toArray(new FormFile[formFile.size()]);
            this.mHandler = mHandler;
            this.map = map;
            return;
        }
        throw new NullPointerException("param formFile is null or empty");
    }
}
