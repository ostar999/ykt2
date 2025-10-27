package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class UMMin extends BaseMediaObject {

    /* renamed from: f, reason: collision with root package name */
    private String f23719f;

    /* renamed from: g, reason: collision with root package name */
    private String f23720g;

    public UMMin(String str) {
        super(str);
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public UMediaObject.MediaType getMediaType() {
        return UMediaObject.MediaType.WEBPAGE;
    }

    public String getPath() {
        return this.f23720g;
    }

    public String getUserName() {
        return this.f23719f;
    }

    public void setPath(String str) {
        this.f23720g = str;
    }

    public void setUserName(String str) {
        this.f23719f = str;
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
