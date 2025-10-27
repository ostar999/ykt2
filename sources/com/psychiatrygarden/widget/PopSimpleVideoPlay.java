package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PopSimpleVideoPlay extends CenterPopupView {
    private CustomAliPlayerView mVideoPlayerView;
    private String vid;

    public PopSimpleVideoPlay(@NonNull Context context, String vid) {
        super(context);
        this.vid = vid;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_show_video_pop_chart_introduce;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, false, getContext());
        CustomAliPlayerView customAliPlayerView = (CustomAliPlayerView) findViewById(R.id.player_view);
        this.mVideoPlayerView = customAliPlayerView;
        customAliPlayerView.setClipToOutline(true);
        final int iDp2px = SizeUtil.dp2px(getContext(), 12);
        this.mVideoPlayerView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.psychiatrygarden.widget.PopSimpleVideoPlay.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), iDp2px);
            }
        });
        this.mVideoPlayerView.setBackClickListener(new CustomAliPlayerView.OnBackClickListener() { // from class: com.psychiatrygarden.widget.vc
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnBackClickListener
            public final void onBackClick() {
                this.f16996a.dismiss();
            }
        });
        this.mVideoPlayerView.setExpire_str("");
        this.mVideoPlayerView.setWatch_permission("1");
        this.mVideoPlayerView.setVids(this.vid);
        this.mVideoPlayerView.initView(getContext(), this.vid, UserConfig.isCanPlayBy4g(getContext()));
        this.mVideoPlayerView.mAliyunVodPlayerView.showBackAndFullView(false);
        CommonUtil.mPlayerData(this.vid, this.mVideoPlayerView, true);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        CustomAliPlayerView customAliPlayerView = this.mVideoPlayerView;
        if (customAliPlayerView != null) {
            customAliPlayerView.onDestory();
        }
    }
}
