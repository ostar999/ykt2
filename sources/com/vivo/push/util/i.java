package com.vivo.push.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import com.google.android.exoplayer2.text.ttml.TtmlNode;

/* loaded from: classes6.dex */
public final class i implements BaseNotifyLayoutAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Resources f24448a;

    /* renamed from: b, reason: collision with root package name */
    private String f24449b;

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final int getNotificationLayout() {
        return this.f24448a.getIdentifier("push_notify", TtmlNode.TAG_LAYOUT, this.f24449b);
    }

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final int getSuitIconId() {
        Resources resources;
        String str;
        if (j.f24452c) {
            resources = this.f24448a;
            str = "notify_icon_rom30";
        } else if (j.f24451b) {
            resources = this.f24448a;
            str = "notify_icon_rom20";
        } else {
            resources = this.f24448a;
            str = "notify_icon";
        }
        return resources.getIdentifier(str, "id", this.f24449b);
    }

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final int getTitleColor() {
        int iIntValue;
        try {
            iIntValue = ((Integer) z.a("com.android.internal.R$color", "vivo_notification_title_text_color")).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            iIntValue = 0;
        }
        if (iIntValue > 0) {
            return this.f24448a.getColor(iIntValue);
        }
        boolean z2 = j.f24452c;
        if (z2) {
            return -1;
        }
        if (!j.f24451b) {
            return -16777216;
        }
        if (z2) {
            return Color.parseColor("#ff999999");
        }
        return -1;
    }

    @Override // com.vivo.push.util.BaseNotifyLayoutAdapter
    public final void init(Context context) {
        this.f24449b = context.getPackageName();
        this.f24448a = context.getResources();
    }
}
