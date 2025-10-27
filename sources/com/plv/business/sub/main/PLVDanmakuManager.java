package com.plv.business.sub.main;

import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.plv.business.net.PLVCommonApiManager;
import com.plv.business.sub.danmaku.auxiliary.BiliDanmukuParser;
import com.plv.business.sub.danmaku.auxiliary.BilibiliDanmakuTransfer;
import com.plv.business.sub.danmaku.entity.PLVDanmakuEntity;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import javax.xml.transform.TransformerException;
import net.polyv.danmaku.danmaku.loader.ILoader;
import net.polyv.danmaku.danmaku.loader.IllegalDataException;
import net.polyv.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;
import okhttp3.ResponseBody;
import org.json.JSONException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class PLVDanmakuManager {

    public static class GetDanmakuCallBack implements Callback<List<PLVDanmakuInfo>> {
        GetDanmakuListener getDanmakuListener;

        public GetDanmakuCallBack(GetDanmakuListener getDanmakuListener) {
            this.getDanmakuListener = getDanmakuListener;
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<List<PLVDanmakuInfo>> call, Throwable th) {
            GetDanmakuListener getDanmakuListener = this.getDanmakuListener;
            if (getDanmakuListener != null) {
                getDanmakuListener.fail(th);
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<List<PLVDanmakuInfo>> call, Response<List<PLVDanmakuInfo>> response) {
            int iCode = response.code();
            if (iCode != 200 && iCode != 206) {
                onFailure(call, new Exception("response code is " + iCode));
                return;
            }
            List<PLVDanmakuInfo> listBody = response.body();
            int i2 = 0;
            while (i2 < listBody.size()) {
                if (listBody.get(i2).getMsg() == null || listBody.get(i2).getMsg().trim().length() == 0) {
                    listBody.remove(i2);
                    i2--;
                }
                i2++;
            }
            PLVDanmakuEntity pLVDanmakuEntity = new PLVDanmakuEntity();
            pLVDanmakuEntity.setAllDanmaku(listBody);
            try {
                GetDanmakuListener getDanmakuListener = this.getDanmakuListener;
                if (getDanmakuListener != null) {
                    getDanmakuListener.success(PLVDanmakuManager.createParser(BilibiliDanmakuTransfer.transferToInputStream(listBody)), pLVDanmakuEntity);
                }
            } catch (IOException e2) {
                onFailure(call, e2);
            }
        }
    }

    public interface GetDanmakuListener {
        void fail(Throwable th);

        void success(BaseDanmakuParser baseDanmakuParser, PLVDanmakuEntity pLVDanmakuEntity);
    }

    public static class SendDanmakuCallBack implements Callback<ResponseBody> {
        SendDanmakuListener sendDanmakuListener;

        public SendDanmakuCallBack(SendDanmakuListener sendDanmakuListener) {
            this.sendDanmakuListener = sendDanmakuListener;
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<ResponseBody> call, Throwable th) {
            SendDanmakuListener sendDanmakuListener = this.sendDanmakuListener;
            if (sendDanmakuListener != null) {
                sendDanmakuListener.fail(th);
            }
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            int iCode = response.code();
            if (iCode != 200 && iCode != 206) {
                onFailure(call, new Exception("response code is " + iCode));
                return;
            }
            try {
                SendDanmakuListener sendDanmakuListener = this.sendDanmakuListener;
                if (sendDanmakuListener != null) {
                    sendDanmakuListener.success(response.body().string());
                }
            } catch (IOException e2) {
                onFailure(call, e2);
            }
        }
    }

    public interface SendDanmakuListener {
        void fail(Throwable th);

        void success(String str);
    }

    public PLVDanmakuManager(Context context) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BaseDanmakuParser createParser(InputStream inputStream) throws JSONException, TransformerException, IllegalArgumentException {
        if (inputStream == null) {
            return new BaseDanmakuParser() { // from class: com.plv.business.sub.main.PLVDanmakuManager.1
                @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
                public Danmakus parse() {
                    return new Danmakus();
                }
            };
        }
        ILoader iLoaderCreate = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
        try {
            iLoaderCreate.load(inputStream);
        } catch (IllegalDataException e2) {
            LogUtils.w("createParser", e2);
        }
        BiliDanmukuParser biliDanmukuParser = new BiliDanmukuParser();
        biliDanmukuParser.load(iLoaderCreate.getDataSource());
        return biliDanmukuParser;
    }

    public Call<List<PLVDanmakuInfo>> getAllDanmaku(@NonNull String str, GetDanmakuListener getDanmakuListener) {
        return getDanmaku(str, 0, getDanmakuListener);
    }

    @Nullable
    public Call<List<PLVDanmakuInfo>> getDanmaku(@NonNull String str, @IntRange(from = 1) int i2, GetDanmakuListener getDanmakuListener) {
        try {
            return getDanmaku_t(str, i2, getDanmakuListener);
        } catch (Exception e2) {
            if (getDanmakuListener == null) {
                return null;
            }
            getDanmakuListener.fail(e2);
            return null;
        }
    }

    public Call<List<PLVDanmakuInfo>> getDanmaku_t(@NonNull String str, @IntRange(from = 1) int i2, GetDanmakuListener getDanmakuListener) throws Exception {
        HashMap map = new HashMap();
        map.put("vid", str);
        if (i2 > 0) {
            map.put("limit", Integer.valueOf(i2));
        }
        Call<List<PLVDanmakuInfo>> danmaku = PLVCommonApiManager.getPlvApiApi().getDanmaku(map);
        danmaku.enqueue(new GetDanmakuCallBack(getDanmakuListener));
        return danmaku;
    }

    public Call<ResponseBody> sendDanmaku(@NonNull PLVDanmakuInfo pLVDanmakuInfo, SendDanmakuListener sendDanmakuListener) {
        try {
            return sendDanmaku_t(pLVDanmakuInfo, sendDanmakuListener);
        } catch (Exception e2) {
            if (sendDanmakuListener == null) {
                return null;
            }
            sendDanmakuListener.fail(e2);
            return null;
        }
    }

    @Nullable
    public Call<ResponseBody> sendDanmaku_t(@NonNull PLVDanmakuInfo pLVDanmakuInfo, SendDanmakuListener sendDanmakuListener) throws Exception {
        HashMap map = new HashMap();
        map.put("vid", pLVDanmakuInfo.getVid());
        map.put("msg", pLVDanmakuInfo.getMsg());
        map.put(CrashHianalyticsData.TIME, pLVDanmakuInfo.getTime());
        map.put(TtmlNode.ATTR_TTS_FONT_SIZE, pLVDanmakuInfo.getFontSize());
        map.put("fontMode", pLVDanmakuInfo.getFontMode());
        map.put("fontColor,", pLVDanmakuInfo.getFontColor());
        Call<ResponseBody> callSendDanmaku = PLVCommonApiManager.getPlvApiApi().sendDanmaku(map);
        callSendDanmaku.enqueue(new SendDanmakuCallBack(sendDanmakuListener));
        return callSendDanmaku;
    }

    public static BaseDanmakuParser createParser(PLVDanmakuEntity pLVDanmakuEntity) {
        try {
            return createParser(BilibiliDanmakuTransfer.transferToInputStream(pLVDanmakuEntity.getAllDanmaku()));
        } catch (IOException unused) {
            return new BaseDanmakuParser() { // from class: com.plv.business.sub.main.PLVDanmakuManager.2
                @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
                public Danmakus parse() {
                    return new Danmakus();
                }
            };
        }
    }
}
