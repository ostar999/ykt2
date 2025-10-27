package com.alibaba.sdk.android.oss.model;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class PutBucketLifecycleRequest extends OSSRequest {
    ArrayList<BucketLifecycleRule> lifecycleRules;
    private String mBucketName;

    public String getBucketName() {
        return this.mBucketName;
    }

    public ArrayList<BucketLifecycleRule> getLifecycleRules() {
        return this.lifecycleRules;
    }

    public void setBucketName(String str) {
        this.mBucketName = str;
    }

    public void setLifecycleRules(ArrayList<BucketLifecycleRule> arrayList) {
        this.lifecycleRules = arrayList;
    }
}
