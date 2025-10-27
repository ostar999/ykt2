package com.beizi.ad.internal;

import com.beizi.ad.RewardItem;

/* loaded from: classes2.dex */
public class o implements RewardItem {

    /* renamed from: a, reason: collision with root package name */
    private String f4396a;

    /* renamed from: b, reason: collision with root package name */
    private int f4397b;

    public o(String str, int i2) {
        this.f4396a = str;
        this.f4397b = i2;
    }

    @Override // com.beizi.ad.RewardItem
    public int getAmount() {
        return this.f4397b;
    }

    @Override // com.beizi.ad.RewardItem
    public String getType() {
        return this.f4396a;
    }
}
