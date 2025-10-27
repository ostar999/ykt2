package com.umeng.socialize.net.analytics;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.utils.SocializeUtils;

/* loaded from: classes6.dex */
public class AnalyticsReqeust extends SocializeRequest {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23743a = "/share/multi_add/";

    /* renamed from: b, reason: collision with root package name */
    private static final int f23744b = 9;

    /* renamed from: c, reason: collision with root package name */
    private String f23745c;

    /* renamed from: d, reason: collision with root package name */
    private String f23746d;

    /* renamed from: e, reason: collision with root package name */
    private String f23747e;

    /* renamed from: f, reason: collision with root package name */
    private String f23748f;

    /* renamed from: g, reason: collision with root package name */
    private String f23749g;

    /* renamed from: h, reason: collision with root package name */
    private String f23750h;

    /* renamed from: i, reason: collision with root package name */
    private String f23751i;

    /* renamed from: j, reason: collision with root package name */
    private UMediaObject f23752j;

    public AnalyticsReqeust(Context context, String str, String str2) {
        super(context, "", AnalyticsResponse.class, 9, URequest.RequestMethod.POST);
        this.mContext = context;
        this.f23746d = str;
        this.f23751i = str2;
    }

    @Override // com.umeng.socialize.net.base.SocializeRequest
    public String getPath() {
        return f23743a + SocializeUtils.getAppkey(this.mContext) + "/" + Config.EntityKey + "/";
    }

    @Override // com.umeng.socialize.net.base.SocializeRequest, com.umeng.socialize.net.utils.URequest
    public void onPrepareRequest() throws PackageManager.NameNotFoundException {
        super.onPrepareRequest();
        Object[] objArr = new Object[2];
        objArr[0] = this.f23746d;
        String str = this.f23745c;
        if (str == null) {
            str = "";
        }
        objArr[1] = str;
        String str2 = String.format("{\"%s\":\"%s\"}", objArr);
        String appkey = SocializeUtils.getAppkey(this.mContext);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, Config.Descriptor);
        addStringParams("to", str2);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_SHARE_SNS, str2);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, appkey);
        addStringParams("type", this.f23748f);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_SHARE_USID, this.f23745c);
        addStringParams("ct", this.f23751i);
        if (!TextUtils.isEmpty(this.f23750h)) {
            addStringParams("url", this.f23750h);
        }
        if (!TextUtils.isEmpty(this.f23749g)) {
            addStringParams("title", this.f23749g);
        }
        addMediaParams(this.f23752j);
    }

    public void setMedia(UMediaObject uMediaObject) {
        if (uMediaObject instanceof UMImage) {
            this.f23752j = uMediaObject;
            return;
        }
        if (uMediaObject instanceof UMusic) {
            UMusic uMusic = (UMusic) uMediaObject;
            this.f23749g = uMusic.getTitle();
            this.f23750h = uMusic.toUrl();
            this.f23751i = uMusic.getDescription();
            this.f23752j = uMusic.getThumbImage();
            return;
        }
        if (uMediaObject instanceof UMVideo) {
            UMVideo uMVideo = (UMVideo) uMediaObject;
            this.f23749g = uMVideo.getTitle();
            this.f23750h = uMVideo.toUrl();
            this.f23751i = uMVideo.getDescription();
            this.f23752j = uMVideo.getThumbImage();
            return;
        }
        if (uMediaObject instanceof UMWeb) {
            UMWeb uMWeb = (UMWeb) uMediaObject;
            this.f23749g = uMWeb.getTitle();
            this.f23750h = uMWeb.toUrl();
            this.f23751i = uMWeb.getDescription();
            this.f23752j = uMWeb.getThumbImage();
            return;
        }
        if (uMediaObject instanceof UMMin) {
            UMMin uMMin = (UMMin) uMediaObject;
            this.f23749g = uMMin.getTitle();
            this.f23750h = uMMin.toUrl();
            this.f23751i = uMMin.getDescription();
            this.f23752j = uMMin.getThumbImage();
        }
    }

    public void setPlatform(String str) {
        this.f23746d = str;
    }

    public void setText(String str) {
        this.f23751i = str;
    }

    public void setType(String str) {
        this.f23748f = str;
    }

    public void setUID(String str) {
        this.f23747e = str;
    }

    public void setmUsid(String str) {
        this.f23745c = str;
    }
}
