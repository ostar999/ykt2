package com.xiaomi.mipush.sdk;

import com.xiaomi.push.service.module.PushChannelRegion;

/* loaded from: classes6.dex */
public class PushConfiguration {
    private boolean mGeoEnable;
    private boolean mOpenCOSPush;
    private boolean mOpenFCMPush;
    private boolean mOpenHmsPush;
    private PushChannelRegion mRegion;

    public static class PushConfigurationBuilder {
        private boolean mGeoEnable;
        private boolean mOpenCOSPush;
        private boolean mOpenFCMPush;
        private boolean mOpenHmsPush;
        private PushChannelRegion mRegion;

        public PushConfiguration build() {
            return new PushConfiguration(this);
        }

        public PushConfigurationBuilder geoEnable(boolean z2) {
            this.mGeoEnable = z2;
            return this;
        }

        public PushConfigurationBuilder openCOSPush(boolean z2) {
            this.mOpenCOSPush = z2;
            return this;
        }

        public PushConfigurationBuilder openFCMPush(boolean z2) {
            this.mOpenFCMPush = z2;
            return this;
        }

        public PushConfigurationBuilder openHmsPush(boolean z2) {
            this.mOpenHmsPush = z2;
            return this;
        }

        public PushConfigurationBuilder region(PushChannelRegion pushChannelRegion) {
            this.mRegion = pushChannelRegion;
            return this;
        }
    }

    public PushConfiguration() {
        this.mRegion = PushChannelRegion.China;
        this.mGeoEnable = false;
        this.mOpenHmsPush = false;
        this.mOpenFCMPush = false;
        this.mOpenCOSPush = false;
    }

    private PushConfiguration(PushConfigurationBuilder pushConfigurationBuilder) {
        this.mRegion = pushConfigurationBuilder.mRegion == null ? PushChannelRegion.China : pushConfigurationBuilder.mRegion;
        this.mGeoEnable = pushConfigurationBuilder.mGeoEnable;
        this.mOpenHmsPush = pushConfigurationBuilder.mOpenHmsPush;
        this.mOpenFCMPush = pushConfigurationBuilder.mOpenFCMPush;
        this.mOpenCOSPush = pushConfigurationBuilder.mOpenCOSPush;
    }

    public boolean getGeoEnable() {
        return this.mGeoEnable;
    }

    public boolean getOpenCOSPush() {
        return this.mOpenCOSPush;
    }

    public boolean getOpenFCMPush() {
        return this.mOpenFCMPush;
    }

    public boolean getOpenHmsPush() {
        return this.mOpenHmsPush;
    }

    public PushChannelRegion getRegion() {
        return this.mRegion;
    }

    public void setGeoEnable(boolean z2) {
        this.mGeoEnable = z2;
    }

    public void setOpenCOSPush(boolean z2) {
        this.mOpenCOSPush = z2;
    }

    public void setOpenFCMPush(boolean z2) {
        this.mOpenFCMPush = z2;
    }

    public void setOpenHmsPush(boolean z2) {
        this.mOpenHmsPush = z2;
    }

    public void setRegion(PushChannelRegion pushChannelRegion) {
        this.mRegion = pushChannelRegion;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("PushConfiguration{");
        stringBuffer.append("Region:");
        PushChannelRegion pushChannelRegion = this.mRegion;
        stringBuffer.append(pushChannelRegion == null ? "null" : pushChannelRegion.name());
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
