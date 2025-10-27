package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.caverock.androidsvg.SVGParser;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a implements Parcelable {
    public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>() { // from class: com.meizu.cloud.pushsdk.notification.model.a.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a createFromParcel(Parcel parcel) {
            return new a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a[] newArray(int i2) {
            return new a[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private int f9495a;

    /* renamed from: b, reason: collision with root package name */
    private String f9496b;

    public a() {
        this.f9495a = 0;
    }

    public a(Parcel parcel) {
        this.f9495a = 0;
        this.f9495a = parcel.readInt();
        this.f9496b = parcel.readString();
    }

    public static a a(MessageV3 messageV3) {
        a aVarC;
        try {
            aVarC = !TextUtils.isEmpty(messageV3.getNotificationMessage()) ? a(new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data").getJSONObject(PushConstants.EXTRA).getJSONObject(SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO)) : null;
        } catch (Exception e2) {
            DebugLogger.e("NotifyOption", "parse flyme NotifyOption setting error " + e2.getMessage() + " so get from notificationMessage");
            aVarC = c(messageV3.getNotificationMessage());
        }
        DebugLogger.i("NotifyOption", "current notify option is " + aVarC);
        return aVarC;
    }

    public static a a(JSONObject jSONObject) {
        String str;
        a aVar = new a();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("ni")) {
                    aVar.a(jSONObject.getInt("ni"));
                }
                if (!jSONObject.isNull("nk")) {
                    aVar.a(jSONObject.getString("nk"));
                }
            } catch (JSONException e2) {
                str = "parse json obj error " + e2.getMessage();
            }
            return aVar;
        }
        str = "no such tag NotifyOption";
        DebugLogger.e("NotifyOption", str);
        return aVar;
    }

    public static int b(MessageV3 messageV3) {
        a aVarA = a(messageV3);
        if (aVarA != null) {
            return aVarA.a();
        }
        return 0;
    }

    public static a b(String str) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            jSONObject = null;
        } else {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e2) {
                DebugLogger.e("NotifyOption", "parse json string error " + e2.getMessage());
            }
        }
        return a(jSONObject);
    }

    private static a c(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return b(new JSONObject(str).getString(SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO));
        } catch (JSONException e2) {
            DebugLogger.e("NotifyOption", "parse notificationMessage error " + e2.getMessage());
            return null;
        }
    }

    public int a() {
        return this.f9495a;
    }

    public void a(int i2) {
        this.f9495a = i2;
    }

    public void a(String str) {
        this.f9496b = str;
    }

    public String b() {
        return this.f9496b;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "NotifyOption{notifyId=" + this.f9495a + ", notifyKey='" + this.f9496b + CharPool.SINGLE_QUOTE + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f9495a);
        parcel.writeString(this.f9496b);
    }
}
