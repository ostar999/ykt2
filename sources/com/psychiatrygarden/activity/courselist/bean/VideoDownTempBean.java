package com.psychiatrygarden.activity.courselist.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class VideoDownTempBean implements Serializable {
    public String acId;
    public String akSceret;
    public String securityToken;
    public String vid;

    public VideoDownTempBean(String vid, String acId, String akSceret, String securityToken) {
        this.vid = vid;
        this.acId = acId;
        this.akSceret = akSceret;
        this.securityToken = securityToken;
    }
}
