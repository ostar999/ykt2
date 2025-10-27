package com.psychiatrygarden.activity.courselist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.database.LoadDbDatasListener;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.source.VidSts;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.bean.ViedeoStatusChangeBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class YkBManager {
    private static volatile YkBManager instance;
    public List<AliyunDownloadMediaInfo> mDownloadMediaLists = new ArrayList();
    public MediatorLiveData<AliyunDownloadMediaInfo> videoDownBeanMediatorLiveData = new MediatorLiveData<>();
    public MediatorLiveData<ViedeoStatusChangeBean> mViedeoStatusChangeBean = new MediatorLiveData<>();

    public static YkBManager getInstance() {
        if (instance == null) {
            synchronized (YkBManager.class) {
                if (instance == null) {
                    instance = new YkBManager();
                }
            }
        }
        return instance;
    }

    public LiveData<ViedeoStatusChangeBean> changeVideoStatus() {
        return this.mViedeoStatusChangeBean;
    }

    public void getCaCheVideoData() {
        ProjectApp.downloadManager.findDatasByDb(new LoadDbDatasListener() { // from class: com.psychiatrygarden.activity.courselist.YkBManager.2
            @Override // com.aliyun.player.alivcplayerexpand.util.database.LoadDbDatasListener
            public void onLoadSuccess(List<AliyunDownloadMediaInfo> dataList) {
                YkBManager.this.mDownloadMediaLists.addAll(dataList);
            }
        });
    }

    public void getCourseAk(final String vid) {
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.YkBManager.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId"));
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret"));
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st"));
                        VidSts vidSts = new VidSts();
                        vidSts.setQuality(QualityValue.QUALITY_FLUENT, false);
                        vidSts.setVid(vid);
                        vidSts.setAccessKeyId(strDecode);
                        vidSts.setAccessKeySecret(strDecode2);
                        vidSts.setSecurityToken(strDecode3);
                        vidSts.setRegion(GlobalPlayerConfig.mRegion);
                        ProjectApp.downloadManager.setmVidSts(vidSts);
                        ProjectApp.downloadManager.prepareDownload(vidSts);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public LiveData<AliyunDownloadMediaInfo> getVideoDownBean() {
        return this.videoDownBeanMediatorLiveData;
    }
}
