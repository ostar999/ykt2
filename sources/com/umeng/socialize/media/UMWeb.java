package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class UMWeb extends BaseMediaObject {
    public UMWeb(String str) {
        super(str);
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public UMediaObject.MediaType getMediaType() {
        return UMediaObject.MediaType.WEBPAGE;
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public byte[] toByte() {
        UMImage uMImage = this.f23686e;
        if (uMImage != null) {
            return uMImage.toByte();
        }
        return null;
    }

    @Override // com.umeng.socialize.media.BaseMediaObject
    public String toString() {
        return "UMWEB [media_url=" + this.f23682a + ", title=" + this.f23683b + "media_url=" + this.f23682a + ", des=" + this.f23685d + ", qzone_thumb=]";
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public Map<String, Object> toUrlExtraParams() {
        HashMap map = new HashMap();
        if (isUrlMedia()) {
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.f23682a);
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
        }
        return map;
    }

    public UMWeb(String str, String str2, String str3, UMImage uMImage) {
        this.f23682a = str;
        setThumb(uMImage);
        this.f23685d = str3;
        setTitle(str2);
    }
}
