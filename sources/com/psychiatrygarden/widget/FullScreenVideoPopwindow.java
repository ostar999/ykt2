package com.psychiatrygarden.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class FullScreenVideoPopwindow extends CenterPopupView {
    public String vid;
    public CustomAliPlayerView videovidew;

    public FullScreenVideoPopwindow(@NonNull @NotNull Context context, String vid) {
        super(context);
        this.vid = vid;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_full_land_screen;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        CustomAliPlayerView customAliPlayerView = (CustomAliPlayerView) findViewById(R.id.videovidew);
        this.videovidew = customAliPlayerView;
        customAliPlayerView.initView(getContext(), this.vid, UserConfig.isCanPlayBy4g(getContext()));
        this.videovidew.fullorori(true);
        CommonUtil.getCourseDownAk(this.vid, this.videovidew, true);
    }
}
