package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes6.dex */
public class ShowVideoDialog {
    private boolean chartIntroduce;
    private String id;
    private String knowledgeId;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private CustomAliPlayerView mVideoPlayerView;
    private String seeDuration;
    private String vId;

    public ShowVideoDialog(@NonNull Context context, String vId, String seeDuration) {
        this.vId = vId;
        this.seeDuration = seeDuration;
    }

    private int getContextRect(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialog$0(Context context, View view) {
        if (!TextUtils.isEmpty(this.knowledgeId)) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("video_id", this.id);
            AliyunVodPlayerView aliyunVodPlayerView = this.mVideoPlayerView.mAliyunVodPlayerView;
            if (aliyunVodPlayerView != null) {
                long j2 = aliyunVodPlayerView.getmCurrentPosition() / 1000;
                ajaxParams.put("duration", String.valueOf(j2));
                SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_RECOMMEND_VIDEO + CommonParameter.App_Id + StrPool.UNDERLINE + this.knowledgeId + StrPool.UNDERLINE + this.id, this.vId + StrPool.UNDERLINE + j2, context);
                YJYHttpUtils.post(context, NetworkRequestsURL.recommendVideoSeeRecord, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.ShowVideoDialog.1
                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onFailure(Throwable t2, int errorNo, String strMsg) {
                        super.onFailure(t2, errorNo, strMsg);
                        LogUtils.d("onSuccess", " false === 保存推荐视频观看记录失败");
                    }

                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onSuccess(String s2) {
                        super.onSuccess((AnonymousClass1) s2);
                        LogUtils.d("onSuccess", " true === 保存推荐视频观看记录成功");
                    }
                });
            }
            PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }
        PopupWindow.OnDismissListener onDismissListener2 = this.mOnDismissListener;
        if (onDismissListener2 != null) {
            onDismissListener2.onDismiss();
        }
        CustomAliPlayerView customAliPlayerView = this.mVideoPlayerView;
        if (customAliPlayerView != null) {
            customAliPlayerView.onDestory();
        }
        view.animate().alpha(1.0f).setDuration(300L).start();
    }

    public static ShowVideoDialog newInstance(Context context, String vId, String seeDuration) {
        return new ShowVideoDialog(context, vId, seeDuration);
    }

    public ShowVideoDialog setSeeDuration(String duration) {
        if (duration != null && duration.matches(RegexPool.NUMBERS)) {
            this.seeDuration = duration;
        }
        return this;
    }

    public void showDialog(final Context context, final View anchorView) {
        View viewInflate = View.inflate(context, this.chartIntroduce ? R.layout.layout_show_video_pop_chart_introduce : R.layout.layout_show_video_pop_home, null);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, context.getResources().getDisplayMetrics().widthPixels - SizeUtil.dp2px(context, 32), -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        anchorView.animate().alpha(0.5f).setDuration(200L).start();
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.oh
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f16769c.lambda$showDialog$0(context, anchorView);
            }
        });
        CustomAliPlayerView customAliPlayerView = (CustomAliPlayerView) viewInflate.findViewById(R.id.player_view);
        this.mVideoPlayerView = customAliPlayerView;
        customAliPlayerView.setBackClickListener(new CustomAliPlayerView.OnBackClickListener() { // from class: com.psychiatrygarden.widget.ph
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnBackClickListener
            public final void onBackClick() {
                popupWindow.dismiss();
            }
        });
        String str = this.seeDuration;
        if (str != null && str.matches(RegexPool.NUMBERS)) {
            this.mVideoPlayerView.setSeeDuration(this.seeDuration);
        }
        this.mVideoPlayerView.setExpire_str("");
        this.mVideoPlayerView.setWatch_permission("1");
        this.mVideoPlayerView.setVids(this.vId);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, false, context);
        this.mVideoPlayerView.initView(context, this.vId, UserConfig.isCanPlayBy4g(context));
        this.mVideoPlayerView.mAliyunVodPlayerView.showBackAndFullView(false);
        CommonUtil.mPlayerData(this.vId, this.mVideoPlayerView, true);
        popupWindow.showAtLocation(anchorView, 17, 0, 0);
    }

    public ShowVideoDialog(String vId) {
        this.vId = vId;
        this.chartIntroduce = true;
    }

    public ShowVideoDialog(String vId, String knowledgeId, boolean recommendVideo, String id, PopupWindow.OnDismissListener listener) {
        this.vId = vId;
        this.id = id;
        this.knowledgeId = knowledgeId;
        this.mOnDismissListener = listener;
    }
}
