package com.meizu.cloud.pushsdk.handler.a.b;

import android.os.Parcel;
import android.os.Parcelable;
import cn.hutool.core.text.CharPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class f implements Parcelable {
    public static final Parcelable.Creator<f> CREATOR = new Parcelable.Creator<f>() { // from class: com.meizu.cloud.pushsdk.handler.a.b.f.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public f createFromParcel(Parcel parcel) {
            return new f(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public f[] newArray(int i2) {
            return new f[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private int f9465a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f9466b;

    /* renamed from: c, reason: collision with root package name */
    private List<String> f9467c;

    /* renamed from: d, reason: collision with root package name */
    private b f9468d;

    /* renamed from: e, reason: collision with root package name */
    private String f9469e;

    public f(Parcel parcel) {
        this.f9465a = parcel.readInt();
        this.f9466b = parcel.readByte() != 0;
        this.f9467c = parcel.createStringArrayList();
        this.f9468d = (b) parcel.readParcelable(b.class.getClassLoader());
        this.f9469e = parcel.readString();
    }

    public f(String str, String str2, String str3, String str4) throws JSONException {
        this.f9469e = str;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("max_size")) {
                this.f9465a = jSONObject.getInt("max_size");
            }
            if (!jSONObject.isNull("wifi_upload")) {
                this.f9466b = jSONObject.getBoolean("wifi_upload");
            }
            if (!jSONObject.isNull("upload_files")) {
                JSONArray jSONArray = jSONObject.getJSONArray("upload_files");
                this.f9467c = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    this.f9467c.add(jSONArray.getString(i2));
                }
            }
        } catch (JSONException e2) {
            DebugLogger.e("UploadLogMessage", "parse upload message error " + e2.getMessage());
        }
        this.f9468d = new b(str2, str3, str4);
    }

    public int a() {
        return this.f9465a;
    }

    public boolean b() {
        return this.f9466b;
    }

    public List<String> c() {
        return this.f9467c;
    }

    public b d() {
        return this.f9468d;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "UploadLogMessage{maxSize=" + this.f9465a + ", wifiUpload=" + this.f9466b + ", fileList=" + this.f9467c + ", controlMessage=" + this.f9468d + ", uploadMessage='" + this.f9469e + CharPool.SINGLE_QUOTE + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f9465a);
        parcel.writeByte(this.f9466b ? (byte) 1 : (byte) 0);
        parcel.writeStringList(this.f9467c);
        parcel.writeParcelable(this.f9468d, i2);
        parcel.writeString(this.f9469e);
    }
}
