package com.alibaba.sdk.android.oss.model;

import cn.hutool.core.text.StrPool;
import java.util.Date;

/* loaded from: classes2.dex */
public class OSSBucketSummary {
    private CannedAccessControlList acl;
    public Date createDate;
    public String extranetEndpoint;
    public String intranetEndpoint;
    public String location;
    public String name;
    public Owner owner;
    public String storageClass;

    public String getAcl() {
        CannedAccessControlList cannedAccessControlList = this.acl;
        if (cannedAccessControlList != null) {
            return cannedAccessControlList.toString();
        }
        return null;
    }

    public void setAcl(String str) {
        this.acl = CannedAccessControlList.parseACL(str);
    }

    public String toString() {
        if (this.storageClass == null) {
            return "OSSBucket [name=" + this.name + ", creationDate=" + this.createDate + ", owner=" + this.owner.toString() + ", location=" + this.location + StrPool.BRACKET_END;
        }
        return "OSSBucket [name=" + this.name + ", creationDate=" + this.createDate + ", owner=" + this.owner.toString() + ", location=" + this.location + ", storageClass=" + this.storageClass + StrPool.BRACKET_END;
    }
}
