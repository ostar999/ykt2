package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class UMQQMini extends BaseMediaObject {

    /* renamed from: f, reason: collision with root package name */
    private String f23721f;

    /* renamed from: g, reason: collision with root package name */
    private String f23722g;

    /* renamed from: h, reason: collision with root package name */
    private String f23723h;

    public UMQQMini(String str) {
        super(str);
        this.f23721f = "";
        this.f23722g = "";
        this.f23723h = "";
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public UMediaObject.MediaType getMediaType() {
        return UMediaObject.MediaType.WEBPAGE;
    }

    public String getMiniAppId() {
        return this.f23721f;
    }

    public String getPath() {
        return this.f23722g;
    }

    public String getType() {
        return this.f23723h;
    }

    public void setMiniAppId(String str) {
        this.f23721f = str;
    }

    public void setPath(String str) {
        this.f23722g = str;
    }

    public void setType(String str) {
        this.f23723h = str;
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public byte[] toByte() {
        UMImage uMImage = this.f23686e;
        if (uMImage != null) {
            return uMImage.toByte();
        }
        return null;
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public Map<String, Object> toUrlExtraParams() {
        HashMap map = new HashMap();
        if (isUrlMedia()) {
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.f23682a);
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, this.f23683b);
        }
        return map;
    }
}
