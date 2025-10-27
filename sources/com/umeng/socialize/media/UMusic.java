package com.umeng.socialize.media;

import android.os.Parcel;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class UMusic extends BaseMediaObject {

    /* renamed from: f, reason: collision with root package name */
    private String f23730f;

    /* renamed from: g, reason: collision with root package name */
    private String f23731g;

    /* renamed from: h, reason: collision with root package name */
    private String f23732h;

    /* renamed from: i, reason: collision with root package name */
    private String f23733i;

    /* renamed from: j, reason: collision with root package name */
    private int f23734j;

    /* renamed from: k, reason: collision with root package name */
    private String f23735k;

    public UMusic(String str) {
        super(str);
    }

    public int getDuration() {
        return this.f23734j;
    }

    public String getH5Url() {
        return this.f23732h;
    }

    public String getHighBandDataUrl() {
        return this.f23731g;
    }

    public String getLowBandDataUrl() {
        return this.f23730f;
    }

    public String getLowBandUrl() {
        return this.f23733i;
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public UMediaObject.MediaType getMediaType() {
        return UMediaObject.MediaType.MUSIC;
    }

    @Override // com.umeng.socialize.media.BaseMediaObject
    public UMImage getThumbImage() {
        return this.f23686e;
    }

    public String getmTargetUrl() {
        return this.f23735k;
    }

    public void setDuration(int i2) {
        this.f23734j = i2;
    }

    public void setH5Url(String str) {
        this.f23732h = str;
    }

    public void setHighBandDataUrl(String str) {
        this.f23731g = str;
    }

    public void setLowBandDataUrl(String str) {
        this.f23730f = str;
    }

    public void setLowBandUrl(String str) {
        this.f23733i = str;
    }

    public void setmTargetUrl(String str) {
        this.f23735k = str;
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
        return "UMusic [title=" + this.f23683b + "media_url=" + this.f23682a + ", qzone_title=" + this.f23683b + ", qzone_thumb=]";
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public final Map<String, Object> toUrlExtraParams() {
        HashMap map = new HashMap();
        if (isUrlMedia()) {
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.f23682a);
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, this.f23683b);
        }
        return map;
    }

    public UMusic(Parcel parcel) {
        super(parcel);
    }
}
