package com.hyphenate.chat;

import com.hyphenate.chat.core.EMChatConfigPrivate;
import com.hyphenate.push.EMPushConfig;

/* loaded from: classes4.dex */
public class EMOptions {
    private int imPort;
    private String imServer;
    private EMPushConfig pushConfig;
    private String reportServer;
    private String restServer;
    private String rtcConfigUrl;
    private int areaCode = -1;
    private boolean acceptInvitationAlways = true;
    private boolean autoAcceptGroupInvitation = true;
    private boolean useEncryption = false;
    private boolean requireReadAck = true;
    private boolean requireDeliveryAck = false;
    private boolean fpaEnable = false;
    private boolean deleteMessagesAsExitGroup = true;
    private boolean isChatroomOwnerLeaveAllowed = true;
    private boolean deleteMessagesAsExitChatRoom = true;
    private String appkey = "";
    private EMChatConfigPrivate config = null;
    private boolean enableAutoLogin = true;
    private String fcmNumber = null;
    private boolean useFCM = true;
    private boolean enableDNSConfig = true;
    private boolean sortMessageByServerTime = true;
    private boolean useHttps = false;
    private String dnsUrl = "";
    private boolean usingHttpsOnly = true;
    private boolean serverTransfer = true;
    private boolean isAutodownload = true;
    private boolean useStereoInput = false;
    private boolean enableStatistics = true;
    private boolean enableUseRtcConfig = false;
    private int fixedInterval = -1;

    public static class AreaCode {
        public static final int AREA_CODE_AS = 8;
        public static final int AREA_CODE_CN = 1;
        public static final int AREA_CODE_EU = 4;
        public static final int AREA_CODE_GLOB = -1;
        public static final int AREA_CODE_IN = 32;
        public static final int AREA_CODE_JP = 16;
        public static final int AREA_CODE_NA = 2;
    }

    public void allowChatroomOwnerLeave(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.isChatroomOwnerLeaveAllowed = z2;
        } else {
            eMChatConfigPrivate.i(z2);
        }
    }

    public boolean autoAcceptGroupInvitations() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.autoAcceptGroupInvitation : eMChatConfigPrivate.s();
    }

    public boolean canChatroomOwnerLeave() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.isChatroomOwnerLeaveAllowed : eMChatConfigPrivate.t();
    }

    public boolean deleteMessagesOnLeaveChatroom() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.deleteMessagesAsExitChatRoom : eMChatConfigPrivate.u();
    }

    public boolean deleteMessagesOnLeaveGroup() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.deleteMessagesAsExitGroup : eMChatConfigPrivate.r();
    }

    public void enableDNSConfig(boolean z2) {
        this.enableDNSConfig = z2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.a(z2);
    }

    public boolean getAcceptInvitationAlways() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.acceptInvitationAlways : eMChatConfigPrivate.q();
    }

    public String getAccessToken() {
        return this.config.m();
    }

    public String getAccessToken(boolean z2) {
        return this.config.b(z2);
    }

    public String getAppKey() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.appkey : eMChatConfigPrivate.k();
    }

    public int getAreaCode() {
        return this.areaCode;
    }

    public boolean getAutoLogin() {
        return this.enableAutoLogin;
    }

    public boolean getAutoTransferMessageAttachments() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.serverTransfer : eMChatConfigPrivate.A();
    }

    public boolean getAutodownloadThumbnail() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.isAutodownload : eMChatConfigPrivate.B();
    }

    public String getDnsUrl() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate != null ? eMChatConfigPrivate.G() : this.dnsUrl;
    }

    public boolean getEnableDNSConfig() {
        return this.enableDNSConfig;
    }

    public int getFixedInterval() {
        return this.fixedInterval;
    }

    public boolean getFpaEnable() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.fpaEnable : eMChatConfigPrivate.F();
    }

    public int getImPort() {
        return this.imPort;
    }

    public String getImServer() {
        return this.imServer;
    }

    public EMPushConfig getPushConfig() {
        return this.pushConfig;
    }

    public String getReportServer() {
        return this.reportServer;
    }

    public boolean getRequireAck() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.requireReadAck : eMChatConfigPrivate.p();
    }

    public boolean getRequireDeliveryAck() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.requireDeliveryAck : eMChatConfigPrivate.o();
    }

    public String getRestServer() {
        return this.restServer;
    }

    public boolean getUsingHttpsOnly() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate != null ? eMChatConfigPrivate.z() : this.usingHttpsOnly;
    }

    public String getVersion() {
        return this.config.e();
    }

    @Deprecated
    public boolean isAutoAcceptGroupInvitation() {
        return autoAcceptGroupInvitations();
    }

    @Deprecated
    public boolean isChatroomOwnerLeaveAllowed() {
        return canChatroomOwnerLeave();
    }

    @Deprecated
    public boolean isDeleteMessagesAsExitChatRoom() {
        return deleteMessagesOnLeaveChatroom();
    }

    @Deprecated
    public boolean isDeleteMessagesAsExitGroup() {
        return deleteMessagesOnLeaveGroup();
    }

    public boolean isEnableStatistics() {
        return this.enableStatistics;
    }

    public boolean isSortMessageByServerTime() {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        return eMChatConfigPrivate == null ? this.sortMessageByServerTime : eMChatConfigPrivate.w();
    }

    public void setAcceptInvitationAlways(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.acceptInvitationAlways = z2;
        } else {
            eMChatConfigPrivate.f(z2);
        }
    }

    public void setAppKey(String str) {
        this.appkey = str;
        updatePath(str);
    }

    public void setAreaCode(int i2) {
        this.areaCode = i2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.b(i2);
    }

    public void setAutoAcceptGroupInvitation(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.autoAcceptGroupInvitation = z2;
        } else {
            eMChatConfigPrivate.h(z2);
        }
    }

    public void setAutoDownloadThumbnail(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.isAutodownload = z2;
        } else {
            eMChatConfigPrivate.o(z2);
        }
    }

    public void setAutoLogin(boolean z2) {
        this.enableAutoLogin = z2;
    }

    public void setAutoTransferMessageAttachments(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.serverTransfer = z2;
        } else {
            eMChatConfigPrivate.n(z2);
        }
    }

    public void setConfig(EMChatConfigPrivate eMChatConfigPrivate) {
        this.config = eMChatConfigPrivate;
    }

    public void setDeleteMessagesAsExitChatRoom(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.deleteMessagesAsExitChatRoom = z2;
        } else {
            eMChatConfigPrivate.j(z2);
        }
    }

    public void setDeleteMessagesAsExitGroup(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.deleteMessagesAsExitGroup = z2;
        } else {
            eMChatConfigPrivate.g(z2);
        }
    }

    public void setDnsUrl(String str) {
        this.dnsUrl = str;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.j(str);
    }

    public void setEnableStatistics(boolean z2) {
        this.enableStatistics = z2;
    }

    public void setFixedHBInterval(int i2) {
        if (i2 < 10) {
            this.fixedInterval = 10;
        }
        if (i2 > 300) {
            this.fixedInterval = 300;
        }
        this.fixedInterval = i2;
    }

    public void setFpaEnable(boolean z2) {
        this.fpaEnable = z2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.q(z2);
    }

    public void setIMServer(String str) {
        if (str == null) {
            return;
        }
        this.imServer = str;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.c(str);
    }

    public void setImPort(int i2) {
        this.imPort = i2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.a(i2);
    }

    public void setPushConfig(EMPushConfig eMPushConfig) {
        this.pushConfig = eMPushConfig;
    }

    public void setReportServer(String str) {
        if (str == null) {
            return;
        }
        this.reportServer = str;
    }

    public void setRequireAck(boolean z2) {
        this.requireReadAck = z2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.e(z2);
    }

    public void setRequireDeliveryAck(boolean z2) {
        this.requireDeliveryAck = z2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.d(z2);
    }

    public void setRestServer(String str) {
        if (str == null) {
            return;
        }
        this.restServer = str;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.d(str);
    }

    public void setSortMessageByServerTime(boolean z2) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            this.sortMessageByServerTime = z2;
        } else {
            eMChatConfigPrivate.k(z2);
        }
    }

    public void setUsingHttpsOnly(boolean z2) {
        this.usingHttpsOnly = z2;
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate == null) {
            return;
        }
        eMChatConfigPrivate.m(z2);
    }

    public void updatePath(String str) {
        EMChatConfigPrivate eMChatConfigPrivate = this.config;
        if (eMChatConfigPrivate != null) {
            eMChatConfigPrivate.a(str);
        }
    }
}
