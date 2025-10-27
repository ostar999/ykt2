package com.umeng.socialize.media;

import android.os.Parcel;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public abstract class BaseMediaObject implements UMediaObject {

    /* renamed from: a, reason: collision with root package name */
    protected String f23682a;

    /* renamed from: b, reason: collision with root package name */
    protected String f23683b;

    /* renamed from: c, reason: collision with root package name */
    protected Map<String, Object> f23684c;

    /* renamed from: d, reason: collision with root package name */
    protected String f23685d;

    /* renamed from: e, reason: collision with root package name */
    protected UMImage f23686e;
    public String mText;

    public BaseMediaObject() {
        this.mText = null;
        this.f23682a = "";
        this.f23683b = "";
        this.f23684c = new HashMap();
        this.f23685d = "";
    }

    public String getDescription() {
        return this.f23685d;
    }

    public UMImage getThumbImage() {
        return this.f23686e;
    }

    public String getTitle() {
        return this.f23683b;
    }

    public Map<String, Object> getmExtra() {
        return this.f23684c;
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public boolean isUrlMedia() {
        return !TextUtils.isEmpty(this.f23682a);
    }

    public void setDescription(String str) {
        this.f23685d = str;
    }

    public void setThumb(UMImage uMImage) {
        this.f23686e = uMImage;
    }

    public void setTitle(String str) {
        this.f23683b = str;
    }

    public void setmExtra(String str, Object obj) {
        this.f23684c.put(str, obj);
    }

    public String toString() {
        return "BaseMediaObject [media_url=" + this.f23682a + ", qzone_title=" + this.f23683b + ", qzone_thumb=]";
    }

    @Override // com.umeng.socialize.media.UMediaObject
    public String toUrl() {
        return this.f23682a;
    }

    public BaseMediaObject(String str) {
        this.mText = null;
        this.f23682a = "";
        this.f23683b = "";
        this.f23684c = new HashMap();
        this.f23685d = "";
        this.f23682a = str;
    }

    public BaseMediaObject(Parcel parcel) {
        this.mText = null;
        this.f23682a = "";
        this.f23683b = "";
        this.f23684c = new HashMap();
        this.f23685d = "";
        if (parcel != null) {
            this.f23682a = parcel.readString();
            this.f23683b = parcel.readString();
        }
    }
}
