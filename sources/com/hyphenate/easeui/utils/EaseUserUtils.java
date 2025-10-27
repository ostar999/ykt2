package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.easeui.widget.EaseImageView;

/* loaded from: classes4.dex */
public class EaseUserUtils {
    public static EaseUser getUserInfo(String str) {
        EaseUserProfileProvider userProvider = EaseIM.getInstance().getUserProvider();
        if (userProvider == null) {
            return null;
        }
        return userProvider.getUser(str);
    }

    public static void setUserAvatar(Context context, String str, ImageView imageView) throws NumberFormatException {
        EaseUser userInfo = getUserInfo(str);
        if (userInfo == null || userInfo.getAvatar() == null) {
            Glide.with(context).load(Integer.valueOf(R.drawable.ease_default_avatar)).into(imageView);
            return;
        }
        try {
            Glide.with(context).load(Integer.valueOf(Integer.parseInt(userInfo.getAvatar()))).into(imageView);
        } catch (Exception unused) {
            Glide.with(context).load(userInfo.getAvatar()).apply((BaseRequestOptions<?>) RequestOptions.placeholderOf(R.drawable.ease_default_avatar).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
        }
    }

    public static void setUserAvatarStyle(EaseImageView easeImageView) {
        EaseAvatarOptions avatarOptions = EaseIM.getInstance().getAvatarOptions();
        if (avatarOptions == null || easeImageView == null) {
            return;
        }
        if (avatarOptions.getAvatarShape() != 0) {
            easeImageView.setShapeType(avatarOptions.getAvatarShape());
        }
        if (avatarOptions.getAvatarBorderWidth() != 0) {
            easeImageView.setBorderWidth(avatarOptions.getAvatarBorderWidth());
        }
        if (avatarOptions.getAvatarBorderColor() != 0) {
            easeImageView.setBorderColor(avatarOptions.getAvatarBorderColor());
        }
        if (avatarOptions.getAvatarRadius() != 0) {
            easeImageView.setRadius(avatarOptions.getAvatarRadius());
        }
    }

    public static void setUserNick(String str, TextView textView) {
        if (textView != null) {
            EaseUser userInfo = getUserInfo(str);
            if (userInfo == null || userInfo.getNickname() == null) {
                textView.setText(str);
            } else {
                textView.setText(userInfo.getNickname());
            }
        }
    }

    public static void showUserAvatar(Context context, String str, ImageView imageView) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            Glide.with(context).load(Integer.valueOf(R.drawable.ease_default_avatar)).into(imageView);
            return;
        }
        try {
            Glide.with(context).load(Integer.valueOf(Integer.parseInt(str))).into(imageView);
        } catch (Exception unused) {
            Glide.with(context).load(str).apply((BaseRequestOptions<?>) RequestOptions.placeholderOf(R.drawable.ease_default_avatar).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
        }
    }
}
