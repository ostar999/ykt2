package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class UMVideo extends BaseMediaObject {

    /* renamed from: f, reason: collision with root package name */
    private String f23724f;

    /* renamed from: g, reason: collision with root package name */
    private String f23725g;

    /* renamed from: h, reason: collision with root package name */
    private String f23726h;

    /* renamed from: i, reason: collision with root package name */
    private String f23727i;

    /* renamed from: j, reason: collision with root package name */
    private int f23728j;

    /* renamed from: k, reason: collision with root package name */
    private File f23729k;

    public UMVideo(String str) {
        super(str);
    }

    public int getDuration() {
        return this.f23728j;
    }

    public String getH5Url() {
        return this.f23727i;
    }

    public String getHighBandDataUrl() {
        return this.f23726h;
    }

    public File getLocalVideoFile() {
        return this.f23729k;
    }

    public String getLowBandDataUrl() {
        return this.f23725g;
    }

    public String getLowBandUrl() {
        return this.f23724f;
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public UMediaObject.MediaType getMediaType() {
        return UMediaObject.MediaType.VEDIO;
    }

    public void setDuration(int i2) {
        this.f23728j = i2;
    }

    public void setH5Url(String str) {
        this.f23727i = str;
    }

    public void setHighBandDataUrl(String str) {
        this.f23726h = str;
    }

    public void setLowBandDataUrl(String str) {
        this.f23725g = str;
    }

    public void setLowBandUrl(String str) {
        this.f23724f = str;
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
        return "UMVedio [media_url=" + this.f23682a + ", qzone_title=" + this.f23683b + ", qzone_thumb=media_url=" + this.f23682a + ", qzone_title=" + this.f23683b + ", qzone_thumb=]";
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public final Map<String, Object> toUrlExtraParams() {
        HashMap map = new HashMap();
        if (isUrlMedia()) {
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.f23682a);
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
        }
        return map;
    }

    public UMVideo(File file) {
        this.f23729k = file;
    }
}
