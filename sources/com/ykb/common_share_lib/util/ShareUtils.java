package com.ykb.common_share_lib.util;

import android.app.Activity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.ykb.common_share_lib.CommonConfig;
import com.ykb.common_share_lib.R;
import com.ykb.common_share_lib.bean.ClickType;

/* loaded from: classes4.dex */
public class ShareUtils {
    public static SnsPlatform PLATFORM_WX = SHARE_MEDIA.WEIXIN.toSnsPlatform();
    public static SnsPlatform PLATFORM_WX_CIRCLE = SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform();
    public static SnsPlatform PLATFORM_QQ = SHARE_MEDIA.QQ.toSnsPlatform();
    public static SnsPlatform PLATFORM_QQ_ZONE = SHARE_MEDIA.QZONE.toSnsPlatform();
    public static SnsPlatform PLATFORM_SINA = SHARE_MEDIA.SINA.toSnsPlatform();
    public static SnsPlatform PLATFORM_FAVORITE = SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform();

    /* renamed from: com.ykb.common_share_lib.util.ShareUtils$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ykb$common_share_lib$bean$ClickType;

        static {
            int[] iArr = new int[ClickType.values().length];
            $SwitchMap$com$ykb$common_share_lib$bean$ClickType = iArr;
            try {
                iArr[ClickType.WX_CIRCLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ykb$common_share_lib$bean$ClickType[ClickType.WX_FRIEND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ykb$common_share_lib$bean$ClickType[ClickType.SINA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ykb$common_share_lib$bean$ClickType[ClickType.QQ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static void shareAppPicControl(String str, String str2, String str3, Activity activity, ClickType clickType, UMShareListener uMShareListener) {
        int i2 = R.drawable.app_icon_ykb;
        if (!CommonConfig.INSTANCE.getYI_KAO_BANG()) {
            i2 = R.drawable.app_icon_hkb;
        }
        UMImage uMImage = new UMImage(activity, i2);
        UMWeb uMWeb = new UMWeb(str);
        uMWeb.setTitle(str2);
        uMWeb.setThumb(uMImage);
        if (clickType != ClickType.SINA) {
            str = str3;
        }
        uMWeb.setDescription(str);
        int i3 = AnonymousClass1.$SwitchMap$com$ykb$common_share_lib$bean$ClickType[clickType.ordinal()];
        new ShareAction(activity).withMedia(uMWeb).setPlatform((i3 != 1 ? i3 != 2 ? i3 != 3 ? i3 != 4 ? null : PLATFORM_QQ : PLATFORM_SINA : PLATFORM_WX : PLATFORM_WX_CIRCLE).mPlatform).setCallback(uMShareListener).share();
    }
}
