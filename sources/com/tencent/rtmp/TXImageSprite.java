package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.rtmp.a.b;
import java.util.List;

/* loaded from: classes6.dex */
public class TXImageSprite implements com.tencent.rtmp.a.a {
    private Context mContext;
    private com.tencent.rtmp.a.a mImageSprite;

    public TXImageSprite(Context context) {
        this.mContext = context.getApplicationContext();
        TXCDRApi.initCrashReport(context);
    }

    @Override // com.tencent.rtmp.a.a
    public Bitmap getThumbnail(float f2) {
        com.tencent.rtmp.a.a aVar = this.mImageSprite;
        if (aVar != null) {
            return aVar.getThumbnail(f2);
        }
        return null;
    }

    @Override // com.tencent.rtmp.a.a
    public void release() {
        com.tencent.rtmp.a.a aVar = this.mImageSprite;
        if (aVar != null) {
            aVar.release();
            this.mImageSprite = null;
        }
    }

    @Override // com.tencent.rtmp.a.a
    public void setVTTUrlAndImageUrls(String str, List<String> list) {
        if (this.mImageSprite != null) {
            release();
        }
        if (str == null || list == null || list.size() == 0) {
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bD);
        b bVar = new b();
        this.mImageSprite = bVar;
        bVar.setVTTUrlAndImageUrls(str, list);
    }
}
