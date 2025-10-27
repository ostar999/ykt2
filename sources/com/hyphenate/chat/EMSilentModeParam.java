package com.hyphenate.chat;

import com.hyphenate.chat.EMPushManager;
import com.hyphenate.chat.adapter.EMASilentModeParam;
import com.hyphenate.chat.adapter.EMASilentModeTime;

/* loaded from: classes4.dex */
public class EMSilentModeParam extends EMBase<EMASilentModeParam> {

    public enum EMSilentModeParamType {
        REMIND_TYPE,
        SILENT_MODE_DURATION,
        SILENT_MODE_INTERVAL
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMASilentModeParam] */
    public EMSilentModeParam(EMSilentModeParamType eMSilentModeParamType) {
        ?? eMASilentModeParam = new EMASilentModeParam();
        this.emaObject = eMASilentModeParam;
        eMASilentModeParam.setParamType(eMSilentModeParamType.ordinal());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeParam(EMASilentModeParam eMASilentModeParam) {
        this.emaObject = eMASilentModeParam;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeParam setParamType(EMSilentModeParamType eMSilentModeParamType) {
        ((EMASilentModeParam) this.emaObject).setParamType(eMSilentModeParamType.ordinal());
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeParam setRemindType(EMPushManager.EMPushRemindType eMPushRemindType) {
        ((EMASilentModeParam) this.emaObject).setRemindType(eMPushRemindType.ordinal() + 1);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeParam setSilentModeDuration(int i2) {
        ((EMASilentModeParam) this.emaObject).setSilentDuration(i2);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeParam setSilentModeInterval(EMSilentModeTime eMSilentModeTime, EMSilentModeTime eMSilentModeTime2) {
        ((EMASilentModeParam) this.emaObject).setStartTime((EMASilentModeTime) eMSilentModeTime.emaObject);
        ((EMASilentModeParam) this.emaObject).setEndTime((EMASilentModeTime) eMSilentModeTime2.emaObject);
        return this;
    }
}
