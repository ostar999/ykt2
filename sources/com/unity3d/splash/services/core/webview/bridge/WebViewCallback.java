package com.unity3d.splash.services.core.webview.bridge;

import android.os.Parcel;
import android.os.Parcelable;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class WebViewCallback implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.unity3d.splash.services.core.webview.bridge.WebViewCallback.1
        @Override // android.os.Parcelable.Creator
        public final WebViewCallback createFromParcel(Parcel parcel) {
            return new WebViewCallback(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final WebViewCallback[] newArray(int i2) {
            return new WebViewCallback[i2];
        }
    };
    private String _callbackId;
    private int _invocationId;
    private boolean _invoked;

    public WebViewCallback(Parcel parcel) {
        this._callbackId = parcel.readString();
        this._invoked = parcel.readByte() != 0;
        this._invocationId = parcel.readInt();
    }

    public WebViewCallback(String str, int i2) {
        this._callbackId = str;
        this._invocationId = i2;
    }

    private void invoke(CallbackStatus callbackStatus, Enum r4, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String str;
        if (this._invoked || (str = this._callbackId) == null || str.length() == 0) {
            return;
        }
        this._invoked = true;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(objArr));
        arrayList.add(0, this._callbackId);
        Invocation invocationById = Invocation.getInvocationById(this._invocationId);
        if (invocationById != null) {
            invocationById.setInvocationResponse(callbackStatus, r4, arrayList.toArray());
            return;
        }
        DeviceLog.error("Couldn't get batch with id: " + getInvocationId());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 45678;
    }

    public void error(Enum r2, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        invoke(CallbackStatus.ERROR, r2, objArr);
    }

    public String getCallbackId() {
        return this._callbackId;
    }

    public int getInvocationId() {
        return this._invocationId;
    }

    public void invoke(Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        invoke(CallbackStatus.OK, null, objArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this._callbackId);
        parcel.writeByte(this._invoked ? (byte) 1 : (byte) 0);
        parcel.writeInt(this._invocationId);
    }
}
