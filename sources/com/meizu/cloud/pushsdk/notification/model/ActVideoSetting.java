package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActVideoSetting implements Parcelable {
    public static final String ACT_URL = "au";
    public static final String ACT_VIDEO_SETTING = "acts";
    public static final Parcelable.Creator<ActVideoSetting> CREATOR = new Parcelable.Creator<ActVideoSetting>() { // from class: com.meizu.cloud.pushsdk.notification.model.ActVideoSetting.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public ActVideoSetting createFromParcel(Parcel parcel) {
            return new ActVideoSetting(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public ActVideoSetting[] newArray(int i2) {
            return new ActVideoSetting[i2];
        }
    };
    public static final String TAG = "ActVideoSetting";
    public static final String WIFI_DISPLAY = "wd";
    private String actUrl;
    private boolean wifiDisplay;

    public ActVideoSetting() {
    }

    public ActVideoSetting(Parcel parcel) {
        this.wifiDisplay = parcel.readByte() != 0;
        this.actUrl = parcel.readString();
    }

    public static ActVideoSetting parse(String str) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            jSONObject = null;
        } else {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e2) {
                DebugLogger.e(TAG, "parse json string error " + e2.getMessage());
            }
        }
        return parse(jSONObject);
    }

    public static ActVideoSetting parse(JSONObject jSONObject) {
        String str;
        ActVideoSetting actVideoSetting = new ActVideoSetting();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("wd")) {
                    actVideoSetting.setWifiDisplay(jSONObject.getInt("wd") != 0);
                }
                if (!jSONObject.isNull(ACT_URL)) {
                    actVideoSetting.setActUrl(jSONObject.getString(ACT_URL));
                }
            } catch (JSONException e2) {
                str = "parse json obj error " + e2.getMessage();
            }
            return actVideoSetting;
        }
        str = "no such tag ActVideoSetting";
        DebugLogger.e(TAG, str);
        return actVideoSetting;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getActUrl() {
        return this.actUrl;
    }

    public boolean isWifiDisplay() {
        return this.wifiDisplay;
    }

    public void setActUrl(String str) {
        this.actUrl = str;
    }

    public void setWifiDisplay(boolean z2) {
        this.wifiDisplay = z2;
    }

    public String toString() {
        return "ActVideoSetting{wifiDisplay=" + this.wifiDisplay + ", actUrl='" + this.actUrl + CharPool.SINGLE_QUOTE + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByte(this.wifiDisplay ? (byte) 1 : (byte) 0);
        parcel.writeString(this.actUrl);
    }
}
